package Game.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:sqlite:SAVED.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static int getScore() {
        String sql = "SELECT score FROM game_state WHERE id = 1";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("score");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public static void saveCurrentScore(int score) {
        String sql = "UPDATE game_state SET score = ? WHERE id = 1";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("Scor salvat cu succes!");
        }
    }
    public static void saveCurrentLevel(int levelIndex) {
        String sql = "UPDATE game_state SET current_level = ? WHERE id = 1";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, levelIndex);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("Nivel salvat cu succes!");
        }
    }

    public static int loadCurrentLevel() {
        String sql = "SELECT current_level FROM game_state WHERE id = 1";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("current_level");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Nivel incarcat cu succes!");
        }

        return 0;
    }
}
