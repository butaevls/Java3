package HomeWork6.server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
            stmt.execute("create table if not exists main(" +
                        "id integer primary key autoincrement, " +
                        "login text unique, password integer, nickname text unique);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String addUser(String newLogin, String newPass, String newNick) {
        try {
                String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, newLogin);
                ps.setInt(2, newPass.hashCode());
                ps.setString(3, newNick);
                ps.execute();
                return newNick;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public static String updateNick(String oldNick, String newNick) {
        try {
            String query = "UPDATE main SET nickname=? WHERE nickname=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newNick);
            ps.setString(2, oldNick);
            ps.executeUpdate();
            return newNick;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
