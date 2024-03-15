import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReturnBook implements ActionListener {

    JFrame userFrame = new JFrame();
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
    JPanel userPanel = new JPanel(new BorderLayout());
    JTextField userIDField = new JTextField();
    JTextField bookIDField = new JTextField();
    JButton submit = new JButton();

    JLabel userIDLabel = new JLabel("User ID:");
    JLabel bookIDLabel = new JLabel("Book ID:");

    ReturnBook() {
        submit.addActionListener(this);

        userIDLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        bookIDLabel.setFont(new Font("Consolas", Font.BOLD, 16));

        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(1080, 720);
        userFrame.getContentPane().setBackground(Color.orange);
        userFrame.setTitle("Return Book");

        setDefaultText(userIDField, "Enter User ID");
        setDefaultText(bookIDField, "Enter Book ID");

        userIDField.setPreferredSize(new Dimension(400, 30));
        userIDField.setFont(new Font("Consolas", Font.PLAIN, 16));
        userIDField.setForeground(new Color(0x808080));
        userIDField.setBackground(Color.lightGray);
        userIDField.setCaretColor(Color.black);

        bookIDField.setPreferredSize(new Dimension(400, 30));
        bookIDField.setFont(new Font("Consolas", Font.PLAIN, 16));
        bookIDField.setForeground(new Color(0x808080));
        bookIDField.setBackground(Color.lightGray);
        bookIDField.setCaretColor(Color.black);

        userPanel.setPreferredSize(new Dimension(300, 180));

        JPanel textFieldsPanel = new JPanel(new GridLayout(3, 2, -100, 10));
        textFieldsPanel.add(userIDLabel);
        textFieldsPanel.add(userIDField);
        textFieldsPanel.add(bookIDLabel);
        textFieldsPanel.add(bookIDField);
        textFieldsPanel.add(new JLabel());
        textFieldsPanel.add(new JLabel());

        submit.setText("Submit");
        submit.setFont(new Font("Consolas", Font.PLAIN, 18));
        submit.setFocusable(false);

        userPanel.add(textFieldsPanel, BorderLayout.NORTH);
        userPanel.add(submit, BorderLayout.SOUTH);

        mainPanel.add(userPanel);

        userFrame.add(mainPanel, BorderLayout.CENTER);
        userFrame.setVisible(true);
        userFrame.pack();
    }

    private void setDefaultText(JTextField textField, String defaultText) {
        textField.setText(defaultText);
        textField.setForeground(new Color(0x808080)); // Set default text color to gray
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                    textField.setForeground(new Color(0x000000));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                    textField.setForeground(new Color(0x808080));
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String userID = userIDField.getText().trim();
            String bookID = bookIDField.getText().trim();

            if (!checkID(userID)) {
                displayErrorMessage("Invalid User ID");
                return;
            }


            if (!returnBook(userID, bookID)) {
                displayErrorMessage("Book not borrowed by this user");
                return;
            }

            displaySuccessMessage("Book returned successfully!");
        }
    }

    private boolean checkID(String id) {
        return id.length() == 6 && id.matches("\\d+");
    }


    private boolean returnBook(String userID, String bookID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowedBook.txt"))) {
            StringBuilder newFileContent = new StringBuilder();
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equals(userID) && parts[1].trim().equals(bookID)) {
                    found = true;
                } else {
                    newFileContent.append(line).append("\n");
                }
            }
            if (!found) {
                return false;
            }
            try (FileWriter writer = new FileWriter("borrowedBook.txt")) {
                writer.write(newFileContent.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(userFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccessMessage(String successMessage) {
        JOptionPane.showMessageDialog(userFrame, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        ReturnBook returnBook = new ReturnBook();
    }
}
