package accessjdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame {
    private JFrame frame;
    private JComboBox<String> tableComboBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NewFrame newFrame = new NewFrame();
            newFrame.createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        frame = new JFrame("Baza de date");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(2, 2));

        tableComboBox = new JComboBox<>();
        tableComboBox.addItem("CLIENTI");
        tableComboBox.addItem("MASINA");
        tableComboBox.addItem("POLITA DE ASIGURARE");
        tableComboBox.addItem("PLATA");
        tableComboBox.addItem("RAPORT ACCIDENT");
        contentPane.add(tableComboBox);

        JButton openButton = new JButton("Deschide");
        openButton.addActionListener(new OpenButtonListener());
        contentPane.add(openButton);

        frame.setContentPane(contentPane);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTable = (String) tableComboBox.getSelectedItem();
            if (selectedTable != null) {
                openTable(selectedTable);
            }
        }
    }

    private void openTable(String tableName) {
        if (tableName.equalsIgnoreCase("clienti")) {
            openClientiTable();
        } else if (tableName.equalsIgnoreCase("masina")) {
            openMasinaTable();
        } else if (tableName.equalsIgnoreCase("plata")) {
            openPlataTable();
        } else if (tableName.equalsIgnoreCase("raport accident")) {
            openRaportAccidentTable();
        } else if (tableName.equalsIgnoreCase("polita asigurare")) {
            openPolitaAsigurareTable();
        }
    }

    private void openClientiTable() {
        SwingUtilities.invokeLater(() -> {
            ClientiJDBCFrame clientiJDBC = new ClientiJDBCFrame();
            clientiJDBC.createAndShowGUI();
        });
    }

    private void openMasinaTable() {
        SwingUtilities.invokeLater(() -> {
            MasinaJDBCFrame masinaJDBC = new MasinaJDBCFrame();
            masinaJDBC.createAndShowGUI();
        });
    }

    private void openPlataTable() {
        SwingUtilities.invokeLater(() -> {
            PlataJDBCFrame plataJDBC = new PlataJDBCFrame();
            plataJDBC.createAndShowGUI();
        });
    }

    private void openRaportAccidentTable() {
        SwingUtilities.invokeLater(() -> {
            RaportAccidentJDBCFrame raportaccidentJDBC = new RaportAccidentJDBCFrame();
            raportaccidentJDBC.createAndShowGUI();
        });
    }

    private void openPolitaAsigurareTable() {
        SwingUtilities.invokeLater(() -> {
            PolitaAsigurareJDBCFrame politaAsigurareJDBC = new PolitaAsigurareJDBCFrame();
            politaAsigurareJDBC.createAndShowGUI();
        });
    }
}
