import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchBook {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton searchByAuthorButton;
    private JButton searchByTitleButton;

    public SearchBook() {
        frame = new JFrame("Search Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(2, 1, 0, 20));

        searchByAuthorButton = new JButton("Search by Author");
        searchByTitleButton = new JButton("Search by Title");

        searchByAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchWindow("author");
            }
        });

        searchByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchWindow("title");
            }
        });

        mainPanel.add(searchByAuthorButton);
        mainPanel.add(searchByTitleButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void openSearchWindow(String searchType) {
        JFrame searchFrame = new JFrame("Search by " + searchType);
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
    
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
    
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.SOUTH);
    
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    // Call the search function and pass the result to BookSearchResult
                    ArrayList<Book> searchResult = searchBooks(searchType, searchText);
                    if (searchResult.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No results found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        new BookSearchResult(searchResult);
                    }
                }
            }
        });
    
        searchFrame.add(searchPanel);
        searchFrame.setSize(400, 150);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
    }
    
    private ArrayList<Book> searchBooks(String searchType, String searchText) {
        ArrayList<Book> searchResult = new ArrayList<>();
        String fileName = "bookInfo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String title = null;
            String author = null;
            String genre = null;
            String id = null;
            String line;
            while ((line = reader.readLine()) != null) {
                title = line.trim(); // First line is always the title
                if ((line = reader.readLine()) != null) {
                    id = line.trim(); // Second line is the ID
                }
                if ((line = reader.readLine()) != null) {
                    author = line.trim(); // Third line is the author
                }
                if ((line = reader.readLine()) != null) {
                    genre = line.trim(); // Fourth line is the genre
                }
                // Check if the search text matches the appropriate field
                if (searchType.equals("title") && title.toLowerCase().contains(searchText.toLowerCase())) {
                    searchResult.add(new Book(id, title, author, genre));
                } else if (searchType.equals("author") && author.toLowerCase().contains(searchText.toLowerCase())) {
                    searchResult.add(new Book(id, title, author, genre));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public static void main(String[] args) {
        new SearchBook();
    }
}
