import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class LoginSystem extends JFrame {

    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

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

        // Nastavení akce pro tlačítko Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Můžeš zde přidat své vlastní ověřování a další logiku
                if (jeUzivatelVDatabazi(username, password)) {
                    // Po úspěšném přihlášení zobrazí nové okno nebo dialogové okno
                    zobrazHlavniOknoPoPrihlaseni(username);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Chybné přihlašovací údaje", "Chyba přihlášení", JOptionPane.ERROR_MESSAGE);
                }

                // Bezpečné smazání hesla
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

                // Zde můžete přidat vlastní logiku pro registraci
                // Například zápis do databáze, kontrola duplicity, atd.

                // Zobrazí prázdné okno s pozdravem a jménem a heslem uživatele
                zobrazPrazdneOkno("Registrace úspěšná!\nJméno: " + username + "\nHeslo: " + password);

                // Přidá jméno a heslo uživatele do textové databáze
                pridejUzivateleDoDatabaze(username, password);

                // Vrátí se zpět na úvodní obrazovku
                zobrazUvodniObrazovku();
            }
        });

        // Nastavení zavírání okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Pro umístění do středu obrazovky
        setVisible(true);
    }

    // Metoda pro ověření uživatele v textové databázi
    private boolean jeUzivatelVDatabazi(String username, String password) {
        String cestaKsouboru = "java.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cestaKsouboru))) {
            String radek;
            while ((radek = bufferedReader.readLine()) != null) {
                // Rozdělení řádku na jméno a heslo
                String[] uzivatelHeslo = radek.split(":");
                if (uzivatelHeslo.length == 2) {
                    String jmenoVDatabazi = uzivatelHeslo[0];
                    String hesloVDatabazi = uzivatelHeslo[1];

                    // Porovnání s aktuálním uživatelem
                    if (jmenoVDatabazi.equals(username) && hesloVDatabazi.equals(password)) {
                        return true; // Uživatel nalezen
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Uživatel nenalezen
    }

    // Metoda pro zobrazení prázdného okna s pozdravem
    private void zobrazPrazdneOkno(String pozdrav) {
        // Vytvoření prázdného panelu s pozdravem
        JPanel prazdnyPanel = new JPanel();
        prazdnyPanel.setLayout(new BorderLayout());

        JLabel pozdravLabel = new JLabel(pozdrav);
        prazdnyPanel.add(pozdravLabel, BorderLayout.CENTER);

        // Nastavení prázdného panelu jako obsahu hlavního okna
        setContentPane(prazdnyPanel);

        // Aktualizace rozhraní
        revalidate();

        // Nastavení velikosti a umístění okna
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    // Metoda pro přidání uživatele do textové databáze
    private void pridejUzivateleDoDatabaze(String username, String password) {
        String cestaKsouboru = "java.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cestaKsouboru, true))) {
            // Přidání nového uživatele na konec souboru
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda pro zobrazení úvodní obrazovky
    private void zobrazUvodniObrazovku() {
        // Vytvoření nového hlavního okna (úvodní obrazovky)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginSystem();
            }
        });

        // Zavření stávajícího okna
        dispose();
    }

    // Metoda pro zobrazení hlavního okna po přihlášení
// Metoda pro zobrazení hlavního okna po přihlášení
    private void zobrazHlavniOknoPoPrihlaseni(String username) {
        // Vytvoření nového okna po přihlášení
        JFrame hlavniOkno = new JFrame("Hlavní Okno");
        hlavniOkno.setLayout(new BorderLayout());

        // Přidání komponent do hlavního okna po přihlášení
        JLabel uvitaciText = new JLabel("Vítejte, " + username + "!");
        hlavniOkno.add(uvitaciText, BorderLayout.CENTER);

        // Přidání tlačítek s písmeny a textem
        JButton buttonA = vytvorTlacitko("A", "a_icon.png", "Text pro tlačítko A", 100, 100);
        JButton buttonB = vytvorTlacitko("B", "kalkulacka.png", "Text pro tlačítko B",100,100);

        // Přidání tlačítek do spodního panelu
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(buttonA);
        bottomPanel.add(buttonB);
        hlavniOkno.add(bottomPanel, BorderLayout.SOUTH);

        // Nastavení zavírání okna
        hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hlavniOkno.setSize(600, 600);
        hlavniOkno.setLocationRelativeTo(null); // Pro umístění do středu obrazovky
        hlavniOkno.setVisible(true);

        // Zavření stávajícího okna přihlášení
        dispose();
    }
    // Metoda pro vytvoření tlačítka s ikonou a písmenem
    // Metoda pro vytvoření tlačítka s ikonou a písmenem
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
                    // Vytvoření nového okna s názvem tlačítka A
                    JFrame podokno = new JFrame(text);
                    podokno.setSize(300, 200);
                    podokno.setLocationRelativeTo(null);
                    podokno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    // Přidání textu do nového okna v závislosti na tlačítku
                    JLabel label = new JLabel(buttonText);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    podokno.add(label, BorderLayout.CENTER);

                    podokno.setVisible(true);
                } else if (text.equals("B")) {
                    // Spuštění rozšířené kalkulačky
                    RozsirenaKalkulacka kalkulacka = new RozsirenaKalkulacka();
                    kalkulacka.setVisible(true);
                }
            }
        });
        return button;
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
