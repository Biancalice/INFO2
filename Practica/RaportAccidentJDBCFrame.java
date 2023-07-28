package accessjdbc;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class RaportAccidentJDBCFrame {
    private JFrame frame;
    private JTextField numarRaportField;
    private JSpinner dataSpinner;
    private JTextArea descriereArea;
    private JTable rapoarteTable;

    public void createAndShowGUI() {
        frame = new JFrame("RaportAccidentJDBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Panel pentru introducerea raportului de accident
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(3, 2));
        insertPanel.add(new JLabel("Număr raport:"));
        numarRaportField = new JTextField();
        insertPanel.add(numarRaportField);
        insertPanel.add(new JLabel("Data:"));
        dataSpinner = createDataSpinner();
        insertPanel.add(dataSpinner);
        insertPanel.add(new JLabel("Descriere:"));
        descriereArea = new JTextArea();
        JScrollPane descriereScrollPane = new JScrollPane(descriereArea);
        insertPanel.add(descriereScrollPane);

        // Panel pentru butoanele de acțiune
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton insertButton = new JButton("Adăugare raport");
        insertButton.addActionListener(new InsertButtonListener());
        buttonPanel.add(insertButton);
        JButton modifyButton = new JButton("Modificare raport");
        modifyButton.addActionListener(new ModifyButtonListener());
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Ștergere raport");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);

        // Panel pentru afișarea tabelului de rapoarte
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        rapoarteTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(rapoarteTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(insertPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Inițializează tabelul cu datele din baza de date
        refreshRapoarteTable();
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
        Date startDate = calendar.getTime();
        calendar.set(currentYear + 1, 0, 1); // 1 ianuarie anul curent + 1
        Date endDate = calendar.getTime();

        spinnerModel.setStart(startDate);
        spinnerModel.setEnd(endDate);
        spinnerModel.setCalendarField(Calendar.DAY_OF_MONTH);

        return spinner;
    }

    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertRaportAccident();
        }
    }
     private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modifyRaportAccident();
        }
    }
    
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRaportAccident();
        }
    }

    private void insertRaportAccident() {
        String numarRaport = numarRaportField.getText();
        Date data = (Date) dataSpinner.getValue();
        String descriere = descriereArea.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            // Formatare data pentru inserare în baza de date
            java.sql.Date sqlData = new java.sql.Date(data.getTime());

            String insertRaportSql = "INSERT INTO RaportAccident (NumarRaport, Data, Descriere) VALUES ('" + numarRaport + "', '" + sqlData + "', '" + descriere + "')";
            stmt.executeUpdate(insertRaportSql);
            JOptionPane.showMessageDialog(frame, "Inserare raport realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după inserarea unui raport nou
            refreshRapoarteTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la inserarea raportului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void modifyRaportAccident() {
    int selectedRow = rapoarteTable.getSelectedRow();
    if (selectedRow != -1) {
        int raportId = (int) rapoarteTable.getValueAt(selectedRow, 0);
        String numarRaport = numarRaportField.getText();
        Date data = (Date) dataSpinner.getValue();
        String descriere = descriereArea.getText();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            String updateRaportSql = "UPDATE RaportAccident SET NumarRaport = ?, Data = ?, Descriere = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(updateRaportSql);
            stmt.setString(1, numarRaport);
            stmt.setDate(2, new java.sql.Date(data.getTime()));
            stmt.setString(3, descriere);
            stmt.setInt(4, raportId);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Modificare raport realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

            stmt.close();
            con.close();

            // Reîmprospătează tabelul după modificarea unui raport
            refreshRapoarteTable();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la modificarea raportului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Selectați un raport pentru a-l modifica.", "Avertisment", JOptionPane.WARNING_MESSAGE);
    }
}


    private void deleteRaportAccident() {
        int selectedRow = rapoarteTable.getSelectedRow();
        if (selectedRow != -1) {
            int raportId = (int) rapoarteTable.getValueAt(selectedRow, 0);

            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

                Statement stmt = con.createStatement();

                String deleteRaportSql = "DELETE FROM RaportAccident WHERE ID = " + raportId;
                stmt.executeUpdate(deleteRaportSql);
                JOptionPane.showMessageDialog(frame, "Ștergere raport realizată cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

                stmt.close();
                con.close();

                // Reîmprospătează tabelul după ștergerea unui raport
                refreshRapoarteTable();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Eroare la ștergerea raportului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Selectați un raport pentru a-l șterge.", "Avertisment", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshRapoarteTable() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\40773\\Documente\\NetBeansProjects\\AccessJDBC\\proiectBD.accdb");

            Statement stmt = con.createStatement();

            String selectRapoarteSql = "SELECT * FROM RaportAccident";
            ResultSet rapoarteRs = stmt.executeQuery(selectRapoarteSql);

            // Construiește modelul tabelului
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Număr raport");
            tableModel.addColumn("Data");
            tableModel.addColumn("Descriere");

            while (rapoarteRs.next()) {
                int raportId = rapoarteRs.getInt("ID");
                String numarRaport = rapoarteRs.getString("NumarRaport");
                Date data = rapoarteRs.getDate("Data");
                String descriere = rapoarteRs.getString("Descriere");

                Object[] row = {raportId, numarRaport, data, descriere};
                tableModel.addRow(row);
            }

            rapoarteRs.close();
            stmt.close();
            con.close();

            // Setează modelul tabelului
            rapoarteTable.setModel(tableModel);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

