package accessjdbc;

import java.sql.*;

public class ClientiJDBC {
    public static void main(String[] args) {
        try {
            // Pasul 1: Încărcarea driverului JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Pasul 2: Stabilirea conexiunii
            String dbURL = "jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb";
            Connection con = DriverManager.getConnection(dbURL);

            // Pasul 3: Crearea și executarea instrucțiunii SQL
            Statement stmt = con.createStatement();

            // Exemplu 3: Selectarea tuturor clienților
            String selectClientiSql = "SELECT * FROM Clienti";
            ResultSet clientiRs = stmt.executeQuery(selectClientiSql);
            while (clientiRs.next()) {
                int clientId = clientiRs.getInt("ID");
                String Nume = clientiRs.getString("Nume");
                String CNP = clientiRs.getString("CNP");
                String numarTelefon = clientiRs.getString("NumarTelefon");
                System.out.println("ClientID: " + clientId + ", Nume: " + Nume + ", CNP: " + CNP + ", Numar telefon: " + numarTelefon);
            }
            clientiRs.close();

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
