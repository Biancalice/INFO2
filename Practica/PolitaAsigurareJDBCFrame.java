package accessjdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class PolitaAsigurareJDBCFrame {
    private JFrame frame;
    private JTextField numarPolitaField;
    private JSpinner dataInceputSpinner;
    private JSpinner dataSfarsitSpinner;
    private JSpinner dataSemnareSpinner;
    private JSpinner dataScadentaSpinner;
    private JTextField costField;
    private JTable politeTable;
 
 
    public void createAndShowGUI() {
        frame = new JFrame("PolitaAsigurareJDBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Panel pentru introducerea poliței de asigurare
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(7, 2));
        insertPanel.add(new JLabel("Număr Poliță:"));
        numarPolitaField = new JTextField();
        insertPanel.add(numarPolitaField);
        insertPanel.add(new JLabel("Data Început:"));
        dataInceputSpinner = createDataSpinner();
        insertPanel.add(dataInceputSpinner);
        insertPanel.add(new JLabel("Data Sfârșit:"));
        dataSfarsitSpinner = createDataSpinner();
        insertPanel.add(dataSfarsitSpinner);
        insertPanel.add(new JLabel("Data Semnare:"));
        dataSemnareSpinner = createDataSpinner();
        insertPanel.add(dataSemnareSpinner);
        insertPanel.add(new JLabel("Data Scadență:"));
        dataScadentaSpinner = createDataSpinner();
        insertPanel.add(dataScadentaSpinner);
        insertPanel.add(new JLabel("Cost:"));
        costField = new JTextField();
        insertPanel.add(costField);

        // Panel pentru butoanele de acțiune
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton insertButton = new JButton("Adăugare poliță");
        insertButton.addActionListener(new InsertButtonListener());
        buttonPanel.add(insertButton);
        JButton modifyButton = new JButton("Modificare poliță");
        modifyButton.addActionListener(new ModifyButtonListener());
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Ștergere poliță");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);

        // Panel pentru afișarea tabelului cu politele de asigurare
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        politeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(politeTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(insertPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Inițializează tabelul cu datele din baza de date
        refreshPoliteTable();
    }

    private JSpinner createDataSpinner() {
        SpinnerDateModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
       JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);

        // Setează limitele inferioare și superioare pentru data selectată
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.set(2000, 0, 1); // 1 ianuarie 2000
        java.util.Date startDate = calendar.getTime();
        calendar.set(currentYear + 1, 0, 1); // 1 ianuarie anul curent + 1
        java.util.Date endDate = calendar.getTime();

        spinnerModel.setStart(startDate);
        spinnerModel.setEnd(endDate);
        spinnerModel.setCalendarField(Calendar.DAY_OF_MONTH);

        return spinner;
    }

    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertPolita();
        }
    }
    
    private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modifyPolita();
        }
    }
    
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deletePolita();
        }
    }

    private void insertPolita() {
        String numarPolita = numarPolitaField.getText();
        java.util.Date dataInceput = (java.util.Date) dataInceputSpinner.getValue();
        java.util.Date dataSfarsit = (java.util.Date) dataSfarsitSpinner.getValue();
        java.util.Date dataSemnare = (java.util.Date) dataSemnareSpinner.getValue();
        java.util.Date dataScadenta = (java.util.Date) dataScadentaSpinner.getValue();
        String cost = costField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String insertPolitaSql = "INSERT INTO PolitaAsigurare (NumarPolita, DataInceput, DataSfarsit, DataSemnare, DataScadenta, Cost) VALUES ('" + numarPolita + "', '" + new java.sql.Date(dataInceput.getTime()) + "', '" + new java.sql.Date(dataSfarsit.getTime()) + "', '" + new java.sql.Date(dataSemnare.getTime()) + "', '" + new java.sql.Date(dataScadenta.getTime()) + "', '" + cost + "')";
            stmt.executeUpdate(insertPolitaSql);
            JOptionPane.showMessageDialog(frame, "Inserare poliță realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după inserarea unei polițe noi
            refreshPoliteTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la inserarea poliței: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    private void modifyPolita() {
        int selectedRow = politeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selectați o poliță pentru a o modifica.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int politaId = (int) politeTable.getValueAt(selectedRow, 0);
        String numarPolita = numarPolitaField.getText();
        java.util.Date dataInceput = (java.util.Date) dataInceputSpinner.getValue();
        java.util.Date dataSfarsit = (java.util.Date) dataSfarsitSpinner.getValue();
        java.util.Date dataSemnare = (java.util.Date) dataSemnareSpinner.getValue();
        java.util.Date dataScadenta = (java.util.Date) dataScadentaSpinner.getValue();
        String cost = costField.getText();
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String updatePolitaSql = "UPDATE PolitaAsigurare SET NumarPolita='" + numarPolita + "', DataInceput='" + new java.sql.Date(dataInceput.getTime()) + "', DataSfarsit='" + new java.sql.Date(dataSfarsit.getTime()) + "', DataSemnare='" + new java.sql.Date(dataSemnare.getTime()) + "', DataScadenta='" + new java.sql.Date(dataScadenta.getTime()) + "', Cost='" + cost + "' WHERE ID=" + politaId;
            stmt.executeUpdate(updatePolitaSql);
            JOptionPane.showMessageDialog(frame, "Modificare poliță realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după modificarea poliței
            refreshPoliteTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la modificarea poliței: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deletePolita() {
        int selectedRow = politeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selectați o poliță pentru a o șterge.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int politaId = (int) politeTable.getValueAt(selectedRow, 0);
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String deletePolitaSql = "DELETE FROM PolitaAsigurare WHERE ID=" + politaId;
            stmt.executeUpdate(deletePolitaSql);
            JOptionPane.showMessageDialog(frame, "Ștergere poliță realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după ștergerea poliței
            refreshPoliteTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();      
                    } 
        catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la ștergerea poliței: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshPoliteTable() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String selectPoliteSql = "SELECT * FROM PolitaAsigurare";
            ResultSet politeRs = stmt.executeQuery(selectPoliteSql);

            // Construiește modelul tabelului
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Număr Poliță");
            tableModel.addColumn("Data Început");
            tableModel.addColumn("Data Sfârșit");
            tableModel.addColumn("Data Semnare");
            tableModel.addColumn("Data Scadență");
            tableModel.addColumn("Cost");

            while (politeRs.next()) {
                int politaId = politeRs.getInt("ID");
                String numarPolita = politeRs.getString("NumarPolita");
                Date dataInceput = politeRs.getDate("DataInceput");
                Date dataSfarsit = politeRs.getDate("DataSfarsit");
                Date dataSemnare = politeRs.getDate("DataSemnare");
                Date dataScadenta = politeRs.getDate("DataScadenta");
                String cost = politeRs.getString("Cost");

                Object[] row = {politaId, numarPolita, dataInceput, dataSfarsit, dataSemnare, dataScadenta, cost};
                tableModel.addRow(row);
            }

            politeRs.close();
            stmt.close();
            con.close();

            
            politeTable.setModel(tableModel);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
