import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CreateDB DB = new CreateDB();
        DB.conn();
        Output out = new Output();
        out.region();
        out.snow();
        out.lang();
        out.area();
        Mod mod1 = new Mod();
        mod1.modweather();
    }

    private static class CreateDB {
        private void conn() {
            try {
                String url = "jdbc:mysql://localhost:3306/kurs";
                String username = "root";
                String password = "ukr436504";

                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                
                String sqlCommand = "CREATE TABLE IF NOT EXISTS weather (weather_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(30), date DATE, temp INT, precipitation VARCHAR(30))";
                String sqlCommand1 = "CREATE TABLE IF NOT EXISTS region (region_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, region_name VARCHAR(30), area INT)";
                String sqlCommand2 = "CREATE TABLE IF NOT EXISTS person (person_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name_nation VARCHAR(30), language VARCHAR(30))";
                String sqlCommand3 = "CREATE TABLE IF NOT EXISTS weather_region_person (weather_fk INT, region_fk INT, person_fk INT, FOREIGN KEY (weather_fk) REFERENCES weather (weather_id), FOREIGN KEY (region_fk) REFERENCES region (region_id), FOREIGN KEY (person_fk) REFERENCES person (person_id))";
                
                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(sqlCommand);
                    statement.executeUpdate(sqlCommand1);
                    statement.executeUpdate(sqlCommand2);
                    statement.executeUpdate(sqlCommand3);
                    System.out.println("Databases have been created!");
                    
                    statement.executeUpdate("INSERT INTO weather(name, date, temp, precipitation) VALUES ('Kherson', STR_TO_DATE('10-02-22', '%d-%m-%Y'), 2, 'Snow')");
                    statement.executeUpdate("INSERT INTO region(region_name, area) VALUES ('Kherson', 135)");
                    statement.executeUpdate("INSERT INTO person(name_nation, language) VALUES ('UA', 'UA')");
                    System.out.println("Databases have been filled with information!");
                }
            } catch (Exception ex) {
                System.out.println("Connection failed...");
                System.out.println(ex);
            }
        }
    }

    private static class Output {
        String url = "jdbc:mysql://localhost:3306/kurs";
        String username = "root";
        String password = "ukr436504";

        void region() {
            String sqlCommand = "SELECT * FROM weather WHERE name = 'Kherson'";

            try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                Statement statement = DBconn.createStatement();
                ResultSet res = statement.executeQuery(sqlCommand);
                while (res.next()) {
                    System.out.println(res.getString("name") + ", " + res.getDate("date") + ", " + res.getInt("temp") + ", " + res.getString("precipitation"));
                }
            } catch (Exception ex) {
                System.out.println("Output1 failed...");
                System.out.println(ex);
            }
        }

        void snow() {
            String sqlCommand = "SELECT * FROM weather WHERE precipitation = 'Snow' AND temp <= 2";

            try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                Statement statement = DBconn.createStatement();
                ResultSet res = statement.executeQuery(sqlCommand);
                while (res.next()) {
                    System.out.println(res.getString("name") + ", " + res.getDate("date") + ", " + res.getInt("temp") + ", " + res.getString("precipitation"));
                }
            } catch (Exception ex) {
                System.out.println("Output2 failed...");
                System.out.println(ex);
            }
        }

        void lang() {
            String sqlCommand = "SELECT r.region_name, w.temp, w.precipitation, p.language FROM weather w, person p, weather_region_person wrp, region r WHERE w.weather_id = wrp.weather_fk AND p.person_id = wrp.person_fk AND r.region_id = wrp.region_fk AND p.language = 'UA' AND w.date = STR_TO_DATE('10-02-22', '%d-%m-%Y')";

            try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                Statement statement = DBconn.createStatement();
                ResultSet res = statement.executeQuery(sqlCommand);
                while (res.next()) {
                    System.out.println(res.getString("region_name") + ", " + res.getInt("temp") + ", " + res.getString("precipitation") + ", " + res.getString("language"));
                }
            } catch (Exception ex) {
                System.out.println("Output3 failed...");
                System.out.println(ex);
            }
        }

        void area() {
            String sqlCommand = "SELECT r.region_name, w.temp, r.area FROM weather w, region r, weather_region_person wrp WHERE w.weather_id = wrp.weather_fk AND r.region_id = wrp.region_fk AND r.area >= 120";

            try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                Statement statement = DBconn.createStatement();
                ResultSet res = statement.executeQuery(sqlCommand);
                while (res.next()) {
                    System.out.println(res.getString("region_name") + ", " + res.getInt("temp") + ", " + res.getInt("area"));
                }
            } catch (Exception ex) {
                System.out.println("Output4 failed...");
                System.out.println(ex);
            }
        }
    }

    private static class Mod {
        String url = "jdbc:mysql://localhost:3306/kurs";
        String username = "root";
        String password = "ukr436504";

        void modweather() {
            try (Scanner newScanner = new Scanner(System.in)) {
                System.out.println("Type the region name to mod.");
                String reg = newScanner.next();

                String sqlCommand = "INSERT INTO weather(name, date, temp, precipitation) VALUES (?, STR_TO_DATE('20-02-22', '%d-%m-%Y'), 1, 'Rain')";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    PreparedStatement preparedStatement = DBconn.prepareStatement(sqlCommand);
                    preparedStatement.setString(1, reg);
                    preparedStatement.executeUpdate();
                    System.out.println("Data has been inserted.");
                } catch (Exception ex) {
                    System.out.println("Modification failed...");
                    System.out.println(ex);
                }
            }
        }
    }
}
