import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LoginSystem extends JFrame {

    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JLabel textLabel;
    private JLabel hodinyLabel;
    private JMenuBar menuBar;

    private boolean prihlaseny = false;

    public LoginSystem() {
        super("Login System");

        loginFrame = this;

        // Vytvoření komponent
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Nastavení layoutu
        setLayout(new GridLayout(4, 2));

        // Přidání komponent
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Prázdný prostor pro oddělení
        add(loginButton);
        add(new JLabel()); // Prázdný prostor pro oddělení
        add(registerButton);

        // Přidání textu a hodin do navigačního panelu
        textLabel = new JLabel("Vítejte!");
        hodinyLabel = new JLabel();

        // Vytvoření navigačního menu
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Nastavení akce pro tlačítko Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (jeUzivatelVDatabazi(username, password)) {
                    // Po úspěšném přihlášení aktualizujeme stav na přihlášený
                    prihlaseny = true;
                    // Aktualizujeme obsah menu
                    createMenuBar();

                    zobrazHlavniOknoPoPrihlaseni(username);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Chybné přihlašovací údaje", "Chyba přihlášení", JOptionPane.ERROR_MESSAGE);
                }

                Arrays.fill(passwordChars, ' ');
                passwordField.setText("");
            }
        });

        // Nastavení akce pro tlačítko Register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                zobrazPrazdneOkno("Registrace úspěšná!\nJméno: " + username + "\nHeslo: " + password);

                pridejUzivateleDoDatabaze(username, password);

                zobrazUvodniObrazovku();
            }
        });

        // Aktualizujeme obsah menu na začátku
      createMenuBar();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean jeUzivatelVDatabazi(String username, String password) {
        String cestaKsouboru = "java.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cestaKsouboru))) {
            String radek;
            while ((radek = bufferedReader.readLine()) != null) {
                String[] uzivatelHeslo = radek.split(":");
                if (uzivatelHeslo.length == 2) {
                    String jmenoVDatabazi = uzivatelHeslo[0];
                    String hesloVDatabazi = uzivatelHeslo[1];

                    if (jmenoVDatabazi.equals(username) && hesloVDatabazi.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void zobrazPrazdneOkno(String pozdrav) {
        JPanel prazdnyPanel = new JPanel();
        prazdnyPanel.setLayout(new BorderLayout());

        JLabel pozdravLabel = new JLabel(pozdrav);
        prazdnyPanel.add(pozdravLabel, BorderLayout.CENTER);

        setContentPane(prazdnyPanel);

        revalidate();

        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    private void pridejUzivateleDoDatabaze(String username, String password) {
        String cestaKsouboru = "java.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cestaKsouboru, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zobrazUvodniObrazovku() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginSystem();
            }
        });

        dispose();
    }

    private void zobrazHlavniOknoPoPrihlaseni(String username) {
        JFrame hlavniOkno = new JFrame("Hlavní Okno");
        hlavniOkno.setLayout(new BorderLayout());
        JMenuBar menuBar = createMenuBar();

        // Nastavení menu baru hlavnímu oknu
        hlavniOkno.setJMenuBar(menuBar);
        JLabel uvitaciText = new JLabel("Vítejte, " + username + "!");
        hlavniOkno.add(uvitaciText, BorderLayout.CENTER);

        JButton buttonA = vytvorTlacitko("A", "a_icon.png", "Text pro tlačítko A", 100, 100);
        JButton buttonB = vytvorTlacitko("B", "kalkulacka.png", "Text pro tlačítko B", 100, 100);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(buttonA);
        bottomPanel.add(buttonB);
        hlavniOkno.add(bottomPanel, BorderLayout.SOUTH);

        hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hlavniOkno.setSize(600, 600);
        hlavniOkno.setLocationRelativeTo(null);
        hlavniOkno.setVisible(true);

        dispose();
    }

    private JButton vytvorTlacitko(String text, String iconName, String buttonText, int buttonWidth, int buttonHeight) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(iconName)).getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
            button.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("A")) {
                    JFrame podokno = new JFrame(text);
                    podokno.setSize(300, 200);
                    podokno.setLocationRelativeTo(null);
                    podokno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JLabel label = new JLabel(buttonText);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    podokno.add(label, BorderLayout.CENTER);

                    podokno.setVisible(true);
                } else if (text.equals("B")) {
                    RozsirenaKalkulacka kalkulacka = new RozsirenaKalkulacka();
                    kalkulacka.setVisible(true);
                }
            }
        });
        return button;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Vytvoření menu
        JMenu menu = new JMenu("\uF8FF");

        // Položka A akce
        JMenuItem menuItemA = new JMenuItem("A akce");
        menuItemA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provedAkciA();
            }
        });

        // Položka B akce
        JMenuItem menuItemB = new JMenuItem("B akce");
        menuItemB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provedAkciB();
            }
        });

        // Přidání položek do menu
        menu.add(menuItemA);
        menu.add(menuItemB);

        // Přidání menu do menu baru
        menuBar.add(menu);

        // Prázdný panel pro posunutí dalších položek na nový řádek
        JPanel fillerPanel = new JPanel();
        menuBar.add(fillerPanel);

        // Přidání položek do menu baru pro datum a čas
        menuBar.add(vytvorDatumLabel());
        menuBar.add(Box.createHorizontalStrut(10)); // Horizontální mezeru pro oddělení
        menuBar.add(vytvorCasLabel());

        return menuBar;
    }

    private void provedAkciA() {
        // Zde můžete implementovat akci pro A
        JOptionPane.showMessageDialog(loginFrame, "Provedena akce A");
    }

    private void provedAkciB() {
        // Zde můžete implementovat akci pro B
        JOptionPane.showMessageDialog(loginFrame, "Provedena akce B");
    }



    private JLabel vytvorCasLabel() {
        JLabel casLabel = new JLabel("Čas: ");
        Timer casTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                casLabel.setText("Čas: " + sdf.format(new Date()));
            }
        });
        casTimer.start();
        return casLabel;
    }

    private JLabel vytvorDatumLabel() {
        JLabel datumLabel = new JLabel("Datum: ");
        Timer datumTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                datumLabel.setText("Datum: " + sdf.format(new Date()));
            }
        });
        datumTimer.start();
        return datumLabel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginSystem();
            }
        });
    }
}
