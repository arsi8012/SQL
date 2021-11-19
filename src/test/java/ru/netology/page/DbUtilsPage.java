package ru.netology.page;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtilsPage {
    public DbUtilsPage() {
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

    public static String getStatusDb() {
        val statusSQL = "SELECT status FROM users WHERE login = ?;";
        val runner = new QueryRunner();
        String status = "blocked";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val statusDb = runner.query(conn, statusSQL, new ScalarHandler<>());
            status = (String) statusDb;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }


    public static void cleanCards() {
        val delCards = "DELETE FROM cards;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val cards = runner.update(conn, delCards, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void cleanAuthCodes() {
        val delAuthCodes = "DELETE FROM auth_codes;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val authCodes = runner.update(conn, delAuthCodes, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void cleanUsers() {
        val delUsers = "DELETE FROM users;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val users = runner.update(conn, delUsers, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}