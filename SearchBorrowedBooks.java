import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchBorrowedBooks {
    private JFrame frame;
    private JPanel mainPanel;

    private JTextField userIDField;
    private JButton searchButton;

    public SearchBorrowedBooks() {
        frame = new JFrame("Search Borrowed Books");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel userIDLabel = new JLabel("Enter Qalam ID:");
        userIDField = new JTextField(10);
        searchButton = new JButton("Search");

        mainPanel.add(userIDLabel);
        mainPanel.add(userIDField);
        mainPanel.add(searchButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        searchButton.addActionListener(new SearchButtonListener());
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userID = userIDField.getText();

            // Check if the ID is valid
            if (!checkID(userID)) {
                displayErrorMessage("Invalid ID");
                return;
            }

            // Check if the ID exists in the userInfo.txt file
            if (!checkIfIDExists(userID)) {
                displayErrorMessage("User does not exist");
                return;
            }

            // Search for borrowed books
            ArrayList<String> borrowedBooks = searchBorrowedBooks(userID);

            if (borrowedBooks.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No books borrowed by this user", "No Books Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Display the borrowed books
                displayBorrowedBooks(borrowedBooks);
            }
        }
    }

    private boolean checkID(String id) {
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

    private ArrayList<String> searchBorrowedBooks(String userID) {
        ArrayList<String> borrowedBooks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowedBook.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String borrowedUserID = parts[0].trim(); // Extract the user ID
                if (borrowedUserID.equals(userID)) {
                    String bookID = parts[1].trim(); // Extract the book ID
                    // Check if the book ID exists in the bookInfo.txt file
                    if (checkBookIDExists(bookID)) {
                        borrowedBooks.add(bookID); // Add the book ID to the list
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }
    
    private boolean checkBookIDExists(String bookID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("bookInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].trim().equals(bookID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void displayBorrowedBooks(ArrayList<String> bookIDs) {
        // Close the previous GUI window
        frame.dispose();

        // Open a new GUI window to display book information
        DisplayBookInfo displayBookInfo = new DisplayBookInfo(bookIDs);
    }

    private void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SearchBorrowedBooks searchBorrowedBooks = new SearchBorrowedBooks();
    }
}
