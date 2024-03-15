import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayBook {
    private JFrame frame;
    private JPanel mainPanel;

    private ArrayList<Book> bookList;
    private Map<JButton, String> bookIdMap; // Map to store button and corresponding book ID

    public static String tempID;

    DisplayBook() {
        frame = new JFrame("Book Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        bookIdMap = new HashMap<>();
        bookList = readBookDataFromFile("bookInfo.txt");

        // Read borrowed book IDs and update availability status
        updateAvailabilityStatus();

        displayBooks(gbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void updateAvailabilityStatus() {
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowedBooks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String borrowedBookID = parts[1].trim(); // Extract the book ID

                // Update availability status and disable issue button
                for (Book book : bookList) {
                    if (book.getID().equals(borrowedBookID)) {
                        book.setAvailable(false);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayBooks(GridBagConstraints gbc) {
        Font labelFont = new Font("Arial", Font.PLAIN, 18); // Set font for labels
        Font buttonFont = new Font("Arial", Font.BOLD, 14); // Set font for buttons

        for (Book book : bookList) {
            JPanel bookPanel = new JPanel(new GridBagLayout());
            GridBagConstraints innerGbc = new GridBagConstraints();
            innerGbc.insets = new Insets(5, 5, 5, 5);
            innerGbc.anchor = GridBagConstraints.WEST;

            JLabel titleLabel = createLabel("Book Title:", labelFont);
            innerGbc.gridx = 0;
            innerGbc.gridy = 0;
            bookPanel.add(titleLabel, innerGbc);

            JLabel titleValueLabel = createLabel(book.getTitle(), labelFont);
            innerGbc.gridx = 1;
            bookPanel.add(titleValueLabel, innerGbc);

            JLabel genreLabel = createLabel("Genre:", labelFont);
            innerGbc.gridx = 0;
            innerGbc.gridy = 1;
            bookPanel.add(genreLabel, innerGbc);

            JLabel genreValueLabel = createLabel(book.getGenre(), labelFont);
            innerGbc.gridx = 1;
            bookPanel.add(genreValueLabel, innerGbc);

            JLabel authorLabel = createLabel("Author:", labelFont);
            innerGbc.gridx = 0;
            innerGbc.gridy = 2;
            bookPanel.add(authorLabel, innerGbc);

            JLabel authorValueLabel = createLabel(book.getAuthor(), labelFont);
            innerGbc.gridx = 1;
            bookPanel.add(authorValueLabel, innerGbc);

            JLabel isbnLabel = createLabel("ISBN:", labelFont);
            innerGbc.gridx = 0;
            innerGbc.gridy = 3;
            bookPanel.add(isbnLabel, innerGbc);

            JLabel isbnValueLabel = createLabel(book.getID(), labelFont);
            innerGbc.gridx = 1;
            bookPanel.add(isbnValueLabel, innerGbc);

            JLabel availableLabel = createLabel("Available:", labelFont);
            innerGbc.gridx = 0;
            innerGbc.gridy = 4;
            bookPanel.add(availableLabel, innerGbc);

            JLabel availableValueLabel = createLabel(String.valueOf(book.isAvailable()), labelFont);
            innerGbc.gridx = 1;
            bookPanel.add(availableValueLabel, innerGbc);

            JButton issueButton = new JButton("Issue");
            issueButton.setPreferredSize(new Dimension(100, 30)); // Adjusting button size
            issueButton.setFont(buttonFont);

            // Store the book ID corresponding to the button
            bookIdMap.put(issueButton, book.getID());

            issueButton.addActionListener(new IssueButtonListener());

            // Check availability and disable the button if not available
            if (!book.isAvailable()) {
                issueButton.setEnabled(false);
            }

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(bookPanel, gbc);

            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            mainPanel.add(issueButton, gbc);    
        }
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private ArrayList<Book> readBookDataFromFile(String fileName) {
        ArrayList<Book> bookList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String title = line.trim();
                String id = reader.readLine();
                String author = reader.readLine();
                String genre = reader.readLine();

                // Check for null values
                if (id == null || author == null || genre == null) {
                    break;
                }

                bookList.add(new Book(id.trim(), title.trim(), author.trim(), genre.trim()));
                reader.readLine(); // Read the empty line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private class IssueButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Get the button that triggered the action
            JButton button = (JButton) e.getSource();
            // Get the book ID associated with the button
            tempID = bookIdMap.get(button);
            // Do whatever you need to do with the book ID
            
            UserBorrow borrowBook = new UserBorrow();

            // Update availability status and disable issue button
            for (Book book : bookList) {
                if (book.getID().equals(tempID)) {
                    book.setAvailable(false);
                    button.setEnabled(false);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        DisplayBook displaybook = new DisplayBook();
    }
}
