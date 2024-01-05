import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RozsirenaKalkulacka extends JFrame {

    private JTextField textField;
    private double prvniOperand;
    private String operace;

    public RozsirenaKalkulacka() {
        // Nastavení vzhledu okna
        setTitle("Kalkulačka");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Textové pole pro zobrazení a zadání čísel
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        add(textField, BorderLayout.NORTH);

        // Panel s tlačítky
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] buttonLabels = {
                "sin", "cos", "tan", "√",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "Delete"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        // Zobrazit okno
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            String buttonText = source.getText();

            if (buttonText.matches("[0-9]")) {
                textField.setText(textField.getText() + buttonText);
            } else if (buttonText.equals(".")) {
                if (!textField.getText().contains(".")) {
                    textField.setText(textField.getText() + buttonText);
                }
            } else if (buttonText.equals("=")) {
                vypocet();
            } else if (buttonText.equals("√")) {
                double operand = Double.parseDouble(textField.getText());
                textField.setText(String.valueOf(Math.sqrt(operand)));
            } else if (buttonText.equals("sin")) {
                double operand = Double.parseDouble(textField.getText());
                textField.setText(String.valueOf(Math.sin(Math.toRadians(operand))));
            } else if (buttonText.equals("cos")) {
                double operand = Double.parseDouble(textField.getText());
                textField.setText(String.valueOf(Math.cos(Math.toRadians(operand))));
            } else if (buttonText.equals("tan")) {
                double operand = Double.parseDouble(textField.getText());
                textField.setText(String.valueOf(Math.tan(Math.toRadians(operand))));
            } else if (buttonText.equals("Delete")) {
                textField.setText("");
            } else {
                operace = buttonText;
                prvniOperand = Double.parseDouble(textField.getText());
                textField.setText("");
            }
        }

        private void vypocet() {
            if (operace != null && !operace.isEmpty()) {
                double druhyOperand = Double.parseDouble(textField.getText());
                switch (operace) {
                    case "+":
                        textField.setText(String.valueOf(prvniOperand + druhyOperand));
                        break;
                    case "-":
                        textField.setText(String.valueOf(prvniOperand - druhyOperand));
                        break;
                    case "*":
                        textField.setText(String.valueOf(prvniOperand * druhyOperand));
                        break;
                    case "/":
                        if (druhyOperand != 0) {
                            textField.setText(String.valueOf(prvniOperand / druhyOperand));
                        } else {
                            textField.setText("Chyba");
                        }
                        break;
                }
                operace = null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RozsirenaKalkulacka();
            }
        });
    }
}
