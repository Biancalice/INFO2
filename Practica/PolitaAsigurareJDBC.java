package accessjdbc;

import java.sql.*;

public class PolitaAsigurareJDBC {
    public static void main(String[] args) {
        try {
            // Pasul 1: Încărcarea driverului JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Pasul 2: Stabilirea conexiunii
            String dbURL = "jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb";
            Connection con = DriverManager.getConnection(dbURL);

            // Pasul 3: Crearea și executarea instrucțiunii SQL
            Statement stmt = con.createStatement();

            // Selectarea tuturor polițelor de asigurare
            String selectPoliteSql = "SELECT * FROM PolitaAsigurare";
            ResultSet politeRs = stmt.executeQuery(selectPoliteSql);
            while (politeRs.next()) {
                String numarPolita = politeRs.getString("NumarPolita");
                Date dataInceput = politeRs.getDate("DataInceput");
                Date dataSfarsit = politeRs.getDate("DataSfarsit");
                Date dataSemnare = politeRs.getDate("DataSemnare");
                Date dataScadenta = politeRs.getDate("DataScadenta");
                String cost = politeRs.getString("Cost");
                System.out.println("Număr Poliță: " + numarPolita + ", Data Început: " + dataInceput + ", Data Sfârșit: " + dataSfarsit + ", Data Semnare: " + dataSemnare + ", Data Scadență: " + dataScadenta + ", Cost: " + cost);
            }
            politeRs.close();

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
