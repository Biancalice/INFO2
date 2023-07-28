package accessjdbc;

import java.sql.*;

public class MasinaJDBC {
    public static void main(String[] args) {
        try {
            // Pasul 1: Încărcarea driverului JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Pasul 2: Stabilirea conexiunii
            String dbURL = "jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb";
            Connection con = DriverManager.getConnection(dbURL);

            // Pasul 3: Crearea și executarea instrucțiunii SQL
            Statement stmt = con.createStatement();

            // Selectarea tuturor mașinilor
            String selectMasinaSql = "SELECT * FROM Masina";
            ResultSet masinaRs = stmt.executeQuery(selectMasinaSql);
            while (masinaRs.next()) {
                String nrInmatriculare = masinaRs.getString("nrInmatriculare");
                String model = masinaRs.getString("model");
                int anFabricatie = masinaRs.getInt("anFabricatie");
                System.out.println("Nr. înmatriculare: " + nrInmatriculare + ", Model: " + model + ", An fabricație: " + anFabricatie);
            }
            masinaRs.close();

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
