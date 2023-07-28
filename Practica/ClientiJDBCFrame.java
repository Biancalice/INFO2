package accessjdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClientiJDBCFrame {
    private JFrame frame;
    private JTextField numeField;
    private JTextField cnpField;
    private JTextField NrtelField;
    private JTable clientiTable;


    public void createAndShowGUI() {
        frame = new JFrame("ClientiJDBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Panel pentru inserarea de clienți
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(4, 2));
        insertPanel.add(new JLabel("Nume:"));
        numeField = new JTextField();
        insertPanel.add(numeField);
        insertPanel.add(new JLabel("CNP:"));
        cnpField = new JTextField();
        insertPanel.add(cnpField);
        insertPanel.add(new JLabel("Nr tel:"));
        NrtelField = new JTextField();
        insertPanel.add(NrtelField);
        

        // Panel pentru butoanele de acțiune
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton insertButton = new JButton("Adăugare client");
        insertButton.addActionListener(new InsertButtonListener());
        insertPanel.add(insertButton);
        JButton modifyButton = new JButton("Modificare client");
        modifyButton.addActionListener(new ModifyButtonListener());
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Ștergere client");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);

        // Panel pentru afișarea tabelului de clienți
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        clientiTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(clientiTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(insertPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Inițializează tabelul cu datele din baza de date
        refreshClientiTable();
    }

    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertClient();
        }
    }

    private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modifyClient();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteClient();
        }
    }

    private void insertClient() {
        String nume = numeField.getText();
        String cnp = cnpField.getText();
        String Nrtel = NrtelField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String insertClientSql = "INSERT INTO Clienti (Nume, CNP, NumarTelefon) VALUES ('" + nume + "', '" + cnp + "', '" + Nrtel + "')";
            stmt.executeUpdate(insertClientSql);
            JOptionPane.showMessageDialog(frame, "Inserare client realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după inserarea unui client nou
            refreshClientiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la inserarea clientului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void modifyClient() {
    int selectedRow = clientiTable.getSelectedRow();
    if (selectedRow != -1) {
        int clientId = (int) clientiTable.getValueAt(selectedRow, 0);

        String nume = numeField.getText();
        String cnp = cnpField.getText();
        String Nrtel = NrtelField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String updateClientSql = "UPDATE Clienti SET Nume = '" + nume + "', CNP = '" + cnp + "', NumarTelefon = '" + Nrtel + "' WHERE ID = " + clientId;
            stmt.executeUpdate(updateClientSql);
            JOptionPane.showMessageDialog(frame, "Modificare client realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după modificarea unui client
            refreshClientiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la modificarea clientului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selectează un client din tabel.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}


    private void deleteClient() {
    int selectedRow = clientiTable.getSelectedRow();
    if (selectedRow != -1) {
        int clientId = (int) clientiTable.getValueAt(selectedRow, 0);

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String deleteClientSql = "DELETE FROM Clienti WHERE ID = " + clientId;
            stmt.executeUpdate(deleteClientSql);
            JOptionPane.showMessageDialog(frame, "Ștergere client realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după ștergerea unui client
            refreshClientiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la ștergerea clientului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selecteatunci când doriți să ștergeți un client, trebuie să selectați mai întâi un rând din tabel.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}


    private void refreshClientiTable() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String selectClientiSql = "SELECT * FROM Clienti";
            ResultSet clientiRs = stmt.executeQuery(selectClientiSql);

            // Creează un model de tabel și adaugă datele din ResultSet
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Nume");
            tableModel.addColumn("CNP");
            tableModel.addColumn("Numar telefon");
            while (clientiRs.next()) {
                int clientId = clientiRs.getInt("ID");
                String Nume = clientiRs.getString("Nume");
                String CNP = clientiRs.getString("CNP");
                String numarTelefon = clientiRs.getString("NumarTelefon");
                tableModel.addRow(new Object[]{clientId, Nume, CNP, numarTelefon});
            }
            clientiRs.close();

            // Setează modelul de tabel pentru JTable
            clientiTable.setModel(tableModel);

            stmt.close();
            con.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la afișarea datelor: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}
