package accessjdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class PlataJDBCFrame {
    private JFrame frame;
    private JSpinner dataSpinner;
    private JTextField sumaField;
    private JTextField nrChitantaField;
    private JTextField politaAsigurareField;
    private JTable platiTable;

    public void createAndShowGUI() {
        frame = new JFrame("PlataJDBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Panel pentru introducerea plății
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(4, 2));
        insertPanel.add(new JLabel("Data:"));
        dataSpinner = createDataSpinner();
        insertPanel.add(dataSpinner);
        insertPanel.add(new JLabel("Suma:"));
        sumaField = new JTextField();
        insertPanel.add(sumaField);
        insertPanel.add(new JLabel("Nr. Chitanta:"));
        nrChitantaField = new JTextField();
        insertPanel.add(nrChitantaField);
        insertPanel.add(new JLabel("Polita de asigurare:"));
        politaAsigurareField = new JTextField();
        insertPanel.add(politaAsigurareField);

        // Panel pentru butoanele de acțiune
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton insertButton = new JButton("Adăugare plată");
        insertButton.addActionListener(new InsertButtonListener());
        buttonPanel.add(insertButton);
        JButton modifyButton = new JButton("Modificare plata");
        modifyButton.addActionListener(new PlataJDBCFrame.ModifyButtonListener());
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Ștergere plata");
        deleteButton.addActionListener(new PlataJDBCFrame.DeleteButtonListener());
        buttonPanel.add(deleteButton);

        // Panel pentru afișarea tabelului de plăți
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        platiTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(platiTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(insertPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Inițializează tabelul cu datele din baza de date
        refreshPlatiTable();
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
        calendar.set(currentYear, 11, 31); // 31 decembrie anul curent
        java.util.Date endDate = calendar.getTime();

        spinnerModel.setStart(startDate);
        spinnerModel.setEnd(endDate);
        spinnerModel.setCalendarField(Calendar.DAY_OF_MONTH);

        return spinner;
    }


    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertPlata();
        }
    }
    private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modifyPlata();
        }
    }
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deletePlata();
        }
    }

    private void insertPlata() {
        java.util.Date selectedDate = (java.util.Date) dataSpinner.getValue();
        String data = new java.sql.Date(selectedDate.getTime()).toString();
        String suma = sumaField.getText();
        String nrChitanta = nrChitantaField.getText();
        String politaAsigurare = politaAsigurareField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String insertPlataSql = "INSERT INTO Plata (Data, Suma, NrChitanta, PolitaAsigurare) VALUES ('" + data + "', '" + suma + "', '" + nrChitanta + "', '" + politaAsigurare + "')";
            stmt.executeUpdate(insertPlataSql);
            JOptionPane.showMessageDialog(frame, "Inserare plată realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după inserarea unei plăți noi
            refreshPlatiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la inserarea plății: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyPlata() {
    int selectedRow = platiTable.getSelectedRow();
    if (selectedRow != -1) {
        int plataId = (int) platiTable.getValueAt(selectedRow, 0);
        java.util.Date selectedDate = (java.util.Date) dataSpinner.getValue();
        String data = new java.sql.Date(selectedDate.getTime()).toString();
        String suma = sumaField.getText();
        String nrChitanta = nrChitantaField.getText();
        String politaAsigurare = politaAsigurareField.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String updatePlataSql = "UPDATE Plata SET Data = '" + data + "', Suma = '" + suma + "', NrChitanta = '" + nrChitanta + "', PolitaAsigurare = '" + politaAsigurare + "' WHERE ID = " + plataId;
            stmt.executeUpdate(updatePlataSql);
            JOptionPane.showMessageDialog(frame, "Modificare plată realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după modificarea unei plăți
            refreshPlatiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la modificarea plății: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selectează o plată din tabel.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}

private void deletePlata() {
    int selectedRow = platiTable.getSelectedRow();
    if (selectedRow != -1) {
        int plataId = (int) platiTable.getValueAt(selectedRow, 0);

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String deletePlataSql = "DELETE FROM Plata WHERE ID = " + plataId;
            stmt.executeUpdate(deletePlataSql);
            JOptionPane.showMessageDialog(frame, "Ștergere plată realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după ștergerea unei plăți
            refreshPlatiTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la ștergerea plății: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selectează o plată din tabel.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}   
    private void refreshPlatiTable() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String selectPlatiSql = "SELECT * FROM Plata";
            ResultSet platiRs = stmt.executeQuery(selectPlatiSql);

            // Construiește modelul tabelului
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Data");
            tableModel.addColumn("Suma");
            tableModel.addColumn("NrChitanta");
            tableModel.addColumn("Polita de asigurare");

            while (platiRs.next()) {
                int plataId = platiRs.getInt("ID");
                String data = platiRs.getString("Data");
                String suma = platiRs.getString("Suma");
                String nrChitanta = platiRs.getString("NrChitanta");
                String politaAsigurare = platiRs.getString("PolitaAsigurare");

                Object[] row = {plataId, data, suma, nrChitanta, politaAsigurare};
                tableModel.addRow(row);
            }

            platiRs.close();
            stmt.close();
            con.close();

            // Setează modelul tabelului
            platiTable.setModel(tableModel);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
