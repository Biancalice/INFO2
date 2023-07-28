package accessjdbc;

import java.sql.*;

public class PlataJDBC {
    public static void main(String[] args) {
        try {
            // Pasul 1: Încărcarea driverului JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Pasul 2: Stabilirea conexiunii
            String dbURL = "jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb";
            Connection con = DriverManager.getConnection(dbURL);

            // Pasul 3: Crearea și executarea instrucțiunii SQL
            Statement stmt = con.createStatement();

            // Selectarea tuturor plăților
            String selectPlatiSql = "SELECT * FROM Plata";
            ResultSet platiRs = stmt.executeQuery(selectPlatiSql);
            while (platiRs.next()) {
                int plataId = platiRs.getInt("ID");
                String daca = platiRs.getString("Data");
                String suma = platiRs.getString("Suma");
                String chitanta = platiRs.getString("Chitanta");
                String politaAsigurare = platiRs.getString("PolitaAsigurare");
                System.out.println("ID: " + plataId + ", Data: " + daca + ", Suma: " + suma + ", Chitanta: " + chitanta + ", Polita de asigurare: " + politaAsigurare);
            }
            platiRs.close();

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
