package accessjdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MasinaJDBCFrame {
    private JFrame frame;
    private JTextField nrInmatriculareField;
    private JTextField modelField;
    private JTextField anFabricatieField;
    private JTable masiniTable;

    public void createAndShowGUI() {
        frame = new JFrame("MasinaJDBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Panel pentru inserarea de mașini
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(3, 2));
        insertPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        insertPanel.add(new JLabel("Nr. înmatriculare:"));
        nrInmatriculareField = new JTextField();
        insertPanel.add(nrInmatriculareField);
        insertPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        insertPanel.add(modelField);
        insertPanel.add(new JLabel("An fabricație:"));
        anFabricatieField = new JTextField();
        insertPanel.add(anFabricatieField);

        // Panel pentru afișarea tabelului de mașini
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        masiniTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(masiniTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel pentru butoanele de acțiune
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton insertButton = new JButton("Adăugare mașină");
        insertButton.addActionListener(new InsertButtonListener());
        buttonPanel.add(insertButton);
        JButton modifyButton = new JButton("Modificare mașină");
        modifyButton.addActionListener(new ModifyButtonListener());
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Ștergere mașină");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);

        contentPane.add(insertPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Inițializează tabelul cu datele din baza de date
        refreshMasinaTable();
    }

    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertMasina();
        }
    }

    private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modifyMasina();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteMasina();
        }
    }

    private void insertMasina() {
        String nrInmatriculare = nrInmatriculareField.getText();
        String model = modelField.getText();
        String anFabricatie = anFabricatieField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");
            DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Masina (nrInmatriculare, model, anFabricatie) VALUES (?, ?, ?)");
            pstmt.setString(1, nrInmatriculare);
            pstmt.setString(2, model);
            pstmt.setString(3, anFabricatie);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Inserare mașină realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            pstmt.close();
            con.close();

            // Reîmprospătează tabelul după inserarea unei mașini noi
            refreshMasinaTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la inserarea mașinii: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void modifyMasina() {
    int selectedRow = masiniTable.getSelectedRow();
    if (selectedRow != -1) {
        String masinaId = (String) masiniTable.getValueAt(selectedRow, 0);

        String nrInmatriculare = nrInmatriculareField.getText();
        String model = modelField.getText();
        String anFabricatieStr = anFabricatieField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            PreparedStatement pstmt = con.prepareStatement("UPDATE Masina SET nrInmatriculare = ?, model = ?, anFabricatie = ? WHERE ID = ?");
            pstmt.setString(1, nrInmatriculare);
            pstmt.setString(2, model);
            pstmt.setString(3, anFabricatieStr);
            pstmt.setString(4, masinaId);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Modificare mașină realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            pstmt.close();
            con.close();

            // Reîmprospătează tabelul după modificarea unei mașini
            refreshMasinaTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la modificarea mașinii: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selectează o mașină din tabel.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}


    private void deleteMasina() {
        int selectedRow = masiniTable.getSelectedRow();
        if (selectedRow != -1) {
            String masinaId = (String) masiniTable.getValueAt(selectedRow, 0);

            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

                PreparedStatement pstmt = con.prepareStatement("DELETE FROM Masina WHERE ID = ?");
                pstmt.setString(1, masinaId);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Ștergere mașină realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

                pstmt.close();
                con.close();

            
                // Reîmprospătează tabelul după ștergerea unei mașini
                refreshMasinaTable();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Eroare la ștergerea mașinii: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Selectați o mașină pentru a o șterge.", "Avertisment", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshMasinaTable() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String selectMasinaSql = "SELECT ID, nrInmatriculare, model, anFabricatie FROM Masina";
            ResultSet masinaRs = stmt.executeQuery(selectMasinaSql);

            // Creează un model de tabel și adaugă datele din ResultSet
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Nr. înmatriculare");
            tableModel.addColumn("Model");
            tableModel.addColumn("An fabricație");
            while (masinaRs.next()) {
                String id = masinaRs.getString("ID");
                String nrInmatriculare = masinaRs.getString("nrInmatriculare");
                String model = masinaRs.getString("model");
                String anFabricatie = masinaRs.getString("anFabricatie");
                tableModel.addRow(new Object[]{id, nrInmatriculare, model, anFabricatie});
            }
            masinaRs.close();

            // Setează modelul de tabel pentru JTable
            masiniTable.setModel(tableModel);

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
