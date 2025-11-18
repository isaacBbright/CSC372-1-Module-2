
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankBalanceGUI extends JFrame {

    private BankAccount account;

    private JTextField initialBalanceField;
    private JTextField amountField;
    private JLabel balanceLabel;

    private JButton setBalanceButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton showBalanceButton;
    private JButton exitButton;

    public BankBalanceGUI() {
        setTitle("Bank Balance Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Initial Balance:"));
        initialBalanceField = new JTextField();
        panel.add(initialBalanceField);

        setBalanceButton = new JButton("Set Initial Balance");
        panel.add(setBalanceButton);
        panel.add(new JLabel(""));

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        depositButton = new JButton("Deposit");
        panel.add(depositButton);

        withdrawButton = new JButton("Withdraw");
        panel.add(withdrawButton);

        showBalanceButton = new JButton("Show Balance");
        panel.add(showBalanceButton);

        exitButton = new JButton("Exit");
        panel.add(exitButton);

        panel.add(new JLabel("Current Balance:"));
        balanceLabel = new JLabel("$0.00");
        panel.add(balanceLabel);

        add(panel);

        registerListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerListeners() {

        setBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double initialBalance = Double.parseDouble(initialBalanceField.getText());
                    if (initialBalance < 0) {
                        JOptionPane.showMessageDialog(BankBalanceGUI.this, "Initial balance cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    account = new BankAccount(initialBalance);
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Account created with balance: $" + String.format("%.2f", initialBalance));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please enter a valid numeric initial balance.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (account == null) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please set the initial balance first.", "No Account", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(BankBalanceGUI.this, "Deposit amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    account.deposit(amount);
                    updateBalanceLabel();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please enter a valid numeric amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (account == null) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please set the initial balance first.", "No Account", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(BankBalanceGUI.this, "Withdrawal amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean success = account.withdraw(amount);
                    if (!success) {
                        JOptionPane.showMessageDialog(BankBalanceGUI.this, "Insufficient funds for this withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateBalanceLabel();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please enter a valid numeric amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (account == null) {
                    JOptionPane.showMessageDialog(BankBalanceGUI.this, "Please set the initial balance first.", "No Account", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                updateBalanceLabel();
                JOptionPane.showMessageDialog(BankBalanceGUI.this, "Current balance: $" + String.format("%.2f", account.getBalance()), "Balance", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double finalBalance = (account == null) ? 0.0 : account.getBalance();
                JOptionPane.showMessageDialog(BankBalanceGUI.this, "Final balance before exit: $" + String.format("%.2f", finalBalance), "Final Balance", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }

    private void updateBalanceLabel() {
        if (account != null) {
            balanceLabel.setText("$" + String.format("%.2f", account.getBalance()));
        } else {
            balanceLabel.setText("$0.00");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankBalanceGUI();
            }
        });
    }
}
