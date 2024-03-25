import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntegralCalculatorGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField lowerLimitField;
    private JTextField upperLimitField;
    private JTextField stepField;

    public IntegralCalculatorGUI() {
        setTitle("Integral Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Lower Limit");
        tableModel.addColumn("Upper Limit");
        tableModel.addColumn("Step");
        tableModel.addColumn("Result");
// создание таблицы
        table = new JTable(tableModel);

        lowerLimitField = new JTextField(2);
        upperLimitField = new JTextField(2);
        stepField = new JTextField(2);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{lowerLimitField.getText(), upperLimitField.getText(), stepField.getText(), ""});
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        JButton calculateButton = new JButton("Calculate");
        //проверка введеных значений
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double lowerLimit, upperLimit, step;

                try {
                    int selectedRow = table.getSelectedRow();
                    lowerLimit = Double.parseDouble(tableModel.getValueAt(selectedRow, 0).toString());
                    upperLimit = Double.parseDouble(tableModel.getValueAt(selectedRow, 1).toString());
                    step = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());

                    double integralResult = calculateIntegral(lowerLimit, upperLimit, step);

                    if (selectedRow != -1) {
                        tableModel.setValueAt(integralResult, selectedRow, 3);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numerical values.");
                }
            }
        });
// создание полей и кнопок
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Lower Limit:"));
        inputPanel.add(lowerLimitField);
        inputPanel.add(new JLabel("Upper Limit:"));
        inputPanel.add(upperLimitField);
        inputPanel.add(new JLabel("Step:"));
        inputPanel.add(stepField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(calculateButton);

        JPanel mainPanel = new JPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(new JScrollPane(table));

        getContentPane().add(mainPanel);
    }

    // вычисление интеграла cos(x^2)
    public static double calculateIntegral(double lowerLimit, double upperLimit, double step) {
        double x1, x2, sum = 0;
        int amountSteps = (int)((upperLimit - lowerLimit) / step);   //округляется в меньшую сторону
        x1 = lowerLimit;

        for (int i = 0; i < amountSteps; i++){
            x2 = x1 + step;
            sum += 0.5 * step * (Math.cos(x1*x1) + Math.cos(x2*x2));
            x1 = x2;
        }
        if ((upperLimit - lowerLimit) % step != 0)
            sum += 0.5 * (upperLimit - x1) * (Math.cos(x1*x1) + Math.cos(upperLimit*upperLimit));

        return sum;
    }



    // запуск GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IntegralCalculatorGUI gui = new IntegralCalculatorGUI();
            gui.setVisible(true);
        });
    }
}
