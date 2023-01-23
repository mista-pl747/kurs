import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CreateDB DB = new Main.CreateDB();
        DB.conn();
        Output out = new Main.Output();
        out.region();
        out.snow();
        out.lang();
        out.area();
        mod mod1 = new Main.mod();
        mod1.modweather();
    }
        private static class CreateDB{
        private void conn(){
            try {

                    String url;
                    url = "jdbc:mysql://localhost:3306/kurs";
                    String username = "root";
                    String password = "ukr436504";




                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                // команда создания таблицы
                String sqlCommand = "CREATE TABLE IF NOT EXISTS weather (weather_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(30), date DATE, temp INT, precipitation VARCHAR(30))";
                String sqlCommand1 = "CREATE TABLE IF NOT EXISTS region  (region_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, region_name VARCHAR(30), area INT)";
                String sqlCommand2 = "CREATE TABLE IF NOT EXISTS person (person_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name_nation VARCHAR(30), language VARCHAR(30))";
                String sqlCommand3 = "CREATE TABLE  IF NOT EXISTS weather_region_person (weather_fk INT, region_fk INT, person_fk INT, FOREIGN KEY (weather_fk) REFERENCES weather (weather_id), FOREIGN KEY (region_fk) REFERENCES region (region_id), FOREIGN KEY (person_fk) REFERENCES person (person_id))";
                //String sqlCommand = "Create TABLE region (region_id INT PRIMARY KEY AUTO_INCREMENT, region_name VARCHAR(30), area INT"

                try (Connection conn = DriverManager.getConnection(url, username, password)) {

                    Statement statement = conn.createStatement();
                    // создание таблицы
                    statement.executeUpdate(sqlCommand);
                    statement.executeUpdate(sqlCommand1);
                    statement.executeUpdate(sqlCommand2);
                    statement.executeUpdate(sqlCommand3);
                    System.out.println("Databases has been created!");
                    statement.executeUpdate("INSERT weather(name, date, temp, precipitation) VALUES ('Kherson', STR_TO_DATE('10-02-22', '%m-%d-%Y'), 2, 'Snow')");
                    statement.executeUpdate("INSERT region(region_name, area) VALUES ('Kherson', 135)");
                    statement.executeUpdate("INSERT person(name_nation, language) VALUES ('UA', 'UA')");
                    System.out.println("Databases has been filled by information!");
                }


            } catch (Exception ex) {
                System.out.println("Connection failed...");

                System.out.println(ex);

            }
            return;
        }

        }
        private static class Output {

            String url = "jdbc:mysql://localhost:3306/kurs";
            String username = "root";
            String password = "ukr436504";

            void region() {
                //java.util.Scanner newScanner = new java.util.Scanner(System.in);
                //System.out.println("Type the region name");
                //String region = newScanner.next();
                //newScanner.close();
                //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                //String sqlCommand="SELECT * FROM 'weather' WHERE region = '?'";
                String sqlCommand = "SELECT * FROM 'weather' WHERE region = 'Kherson'";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = DBconn.createStatement();
                    //statement.setString(1, region);
                    ResultSet res = statement.executeQuery(sqlCommand);
                    while (res.next()) {
                        System.out.println(res.getString("weather"));
                    }

                } catch (Exception ex2) {
                    System.out.println("Output1 failed...");

                    System.out.println(ex2);
                }
            }

            void snow() {
                //java.util.Scanner newScanner = new java.util.Scanner(System.in);
                //Scanner newScanner =new Scanner(System.in);
                //System.out.println("Type the temp");
                //int temp = newScanner.nextInt();
                //newScanner.close();
                //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                //String sqlCommand="SELECT * FROM 'weather' WHERE precipitation = 'Snow' temp <= '?' ";
                String sqlCommand = "SELECT * FROM 'weather' WHERE precipitation = 'Snow' temp <= 2 ";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = DBconn.createStatement();
                    //statement.setInt(1, temp);
                    ResultSet res = statement.executeQuery(sqlCommand);
                    while (res.next()) {
                        System.out.println(res.getString("weather"));
                    }

                } catch (Exception ex2) {
                    System.out.println("Output2 failed...");

                    System.out.println(ex2);
                }
            }

            void lang() {

                //java.util.Scanner newScanner = new java.util.Scanner(System.in);
                //Scanner newScanner =new Scanner(System.in);
                //System.out.println("Type the lang");
                //String lang = newScanner.nextString();
                //newScanner.close();
                //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                //String sqlCommand="SELECT region, temp, precipitation, language FROM 'weather', 'person', 'weather_person_region' WHERE weather_fk = person_fk  lang = '?' date = STR_TO_DATE('10-02-22', '%m-%d-%Y')";
                String sqlCommand = "SELECT region, temp, precipitation, language FROM 'weather', 'person', 'weather_person_region' WHERE weather_fk = person_fk  lang = 'UA' date = STR_TO_DATE('10-02-22', '%m-%d-%Y')";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = DBconn.createStatement();
                    //statement.setString(1, lang);
                    ResultSet res = statement.executeQuery(sqlCommand);
                    while (res.next()) {
                        System.out.println(res.getString("weather"));
                    }

                } catch (Exception ex3) {
                    System.out.println("Output3 failed...");

                    System.out.println(ex3);
                }
            }
            void area(){
                //java.util.Scanner newScanner = new java.util.Scanner(System.in);
                //Scanner newScanner =new Scanner(System.in);
                //System.out.println("Type the area");
                //Int area = newScanner.nextInt();
                //newScanner.close();
                //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                //String sqlCommand="SELECT region, temp, area FROM 'weather', 'region', 'weather_person_region' WHERE weather_fk = region_fk  area >= '?')";
                String sqlCommand = "SELECT region, temp, area FROM 'weather', 'region', 'weather_person_region' WHERE weather_fk = region_fk  area >= 120 )";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = DBconn.createStatement();
                    //statement.setString(1, area);
                    ResultSet res = statement.executeQuery(sqlCommand);
                    while (res.next()) {
                        System.out.println(res.getString("weather"));
                    }

                } catch (Exception ex4) {
                    System.out.println("Output4 failed...");

                    System.out.println(ex4);
                }
            }

        }
        private static class mod{
            String url = "jdbc:mysql://localhost:3306/kurs";
            String username = "root";
            String password = "ukr436504";
        void modweather(){
                java.util.Scanner newScanner = new java.util.Scanner(System.in);
                System.out.println("Type the region name to mod.");
                String reg = newScanner.next();
                newScanner.close();
                //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                String sqlCommand="INSERT INTO weather(name, date, temp, precipitation) values ('?', STR_TO_DATE('20-02-22', '%m-%d-%Y'), 1, 'Rain')";
                //String sqlCommand = "SELECT * FROM 'weather' WHERE region = 'Kherson'";

                try (Connection DBconn = DriverManager.getConnection(url, username, password)) {
                    Statement statement = DBconn.createStatement();
                    statement.setString(reg, 20-02-2022, 1, "Rain");
                    ResultSet res = statement.executeQuery(sqlCommand);
                    while (res.next()) {
                        System.out.println(res.getString("weather"));
                    }

                } catch (Exception ex5) {
                    System.out.println("Modification failed...");

                    System.out.println(ex5);
                }
            }

        }

    }
