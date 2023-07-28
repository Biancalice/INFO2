package accessjdbc;

import java.sql.*;

public class RaportAccidentJDBC {
    public static void main(String[] args) {
        try {
            // Pasul 1: Încărcarea driverului JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Pasul 2: Stabilirea conexiunii
            String dbURL = "jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb";
            Connection con = DriverManager.getConnection(dbURL);

            // Pasul 3: Crearea și executarea instrucțiunii SQL
            Statement stmt = con.createStatement();

            // Selectarea tuturor rapoartelor de accident
            String selectRapoartSql = "SELECT * FROM RaportAccident";
            ResultSet rapoartRs = stmt.executeQuery(selectRapoartSql);
            while (rapoartRs.next()) {
                int numarRaport = rapoartRs.getInt("NumarRaport");
                String data = rapoartRs.getString("Data");
                String descriere = rapoartRs.getString("Descriere");
                System.out.println("Număr raport: " + numarRaport + ", Data: " + data + ", Descriere: " + descriere);
            }
            rapoartRs.close();

            // Pasul 4: Închiderea resurselor
            stmt.close();
            con.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
