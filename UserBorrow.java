import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserBorrow implements ActionListener {

    JFrame userFrame = new JFrame();
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
    JPanel userPanel = new JPanel(new BorderLayout());
    JTextField userID = new JTextField();
    JButton submit = new JButton();

    JLabel userIDLabel = new JLabel("Qalam ID:");

    public static boolean successfullyBorrowed = false;

    UserBorrow() {
        submit.addActionListener(this);
        userIDLabel.setFont(new Font("Consolas", Font.BOLD, 16));

        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(800, 600);
        userFrame.getContentPane().setBackground(Color.orange);
        userFrame.setTitle("Check User");

        userID.setPreferredSize(new Dimension(400, 30));
        userID.setFont(new Font("Consolas", Font.PLAIN, 16));
        userID.setForeground(new Color(0x808080)); // Set default text color to gray
        userID.setBackground(Color.lightGray);
        userID.setCaretColor(Color.black);

        userPanel.setPreferredSize(new Dimension(400, 120));

        JPanel textFieldsPanel = new JPanel(new GridLayout(2, 2, -100, 20));
        textFieldsPanel.add(userIDLabel);
        textFieldsPanel.add(userID);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String cnic = userID.getText();

            // Check if the ID is valid
            if (!checkID(cnic)) {
                displayErrorMessage("Invalid ID");
                return;
            }

            // Check if the ID exists
            if (!checkIfIDExists(cnic)) {
                displayErrorMessage("ID not found");
                return;
            }

            // Append the borrowed book data to the file
            boolean success = appendToBorrowedBooks(cnic);
            if (success) {
                successfullyBorrowed = true;
                
                displaySuccessMessage("Checkout successful!");
            }
        }
    }

    public boolean checkID(String id) {
        if (id.length() != 6) {
            return false;
        }
        return id.matches("\\d+"); // Check if ID is a number
    }

    private boolean checkIfIDExists(String idToCheck) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains only digits and is exactly 6 characters long
                if (line.length() == 6 && line.matches("\\d+")) {
                    // Extract the ID from the line
                    String idFromLine = line.trim(); // Trim the line before extracting ID
                    // Compare the extracted ID with the entered ID
                    if (idFromLine.equals(idToCheck)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean appendToBorrowedBooks(String userID) {
        String lineToAppend = userID + "," + DisplayBook.tempID; // Line to append
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowedBook.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line already exists
                if (line.equals(lineToAppend)) {
                    // Line already exists, so display a dialog indicating book is unavailable
                    displayErrorMessage("This book is currently unavailable.");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrowedBook.txt", true))) {
            // Append the line to the file if it doesn't already exist
            writer.write(lineToAppend);
            writer.newLine();
            return true; // Return true to indicate success
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Return false if there was an error
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(userFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccessMessage(String successMessage) {
        JOptionPane.showMessageDialog(userFrame, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        UserBorrow userBorrow = new UserBorrow();
    }
}
