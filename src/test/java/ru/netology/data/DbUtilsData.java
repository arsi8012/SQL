package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtilsData {
    private DbUtilsData() {
    }

    public static String getVerificationCode() {
        val codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        val runner = new QueryRunner();
        String verificationCode = "";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationCode;
    }

    public static String getStatusDb(String authInfo) {
        val statusSQL = "SELECT status FROM users WHERE login = ?;";
        val runner = new QueryRunner();
        String status = "blocked";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val statusDb = runner.query(conn, statusSQL, new ScalarHandler<>(), authInfo);
            status = (String) statusDb;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }


    public static void cleanDatabase() {
        val delCards = "DELETE FROM cards;";
        val delAuthCodes = "DELETE FROM auth_codes;";
        val delUsers = "DELETE FROM users;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val cards = runner.update(conn, delCards, new ScalarHandler<>());
            val authCodes = runner.update(conn, delAuthCodes, new ScalarHandler<>());
            val users = runner.update(conn, delUsers, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}