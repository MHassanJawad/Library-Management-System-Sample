    import javax.swing.*;
    import java.awt.*;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;

    public class DisplayBookInfo {
        private JFrame frame;
        private JPanel mainPanel;

        public DisplayBookInfo(ArrayList<String> bookIDs) {
            frame = new JFrame("Borrowed Books Information");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            mainPanel = new JPanel(new GridLayout(0, 1));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            for (String bookID : bookIDs) {
                Book book = findBookByID(bookID);
                if (book != null) {
                    mainPanel.add(createBookPanel(book));
                }
            }

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        private Book findBookByID(String bookID) {
            try (BufferedReader reader = new BufferedReader(new FileReader("bookInfo.txt"))) {
                String title = null;
                String author = null;
                String genre = null;
                String id = null;
                String line;
                while ((line = reader.readLine()) != null) {
                    // Read the title, id, author, and genre for each block
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
                    // If all fields are populated, check if the IDs match
                    if (title != null && id != null && author != null && genre != null) {
                        if (id.equals(bookID)) {
                            return new Book(id, title, author, genre);
                        }
                        // Reset the fields for the next book
                        title = null;
                        author = null;
                        genre = null;
                        id = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        private JPanel createBookPanel(Book book) {
            JPanel panel = new JPanel(new GridLayout(4, 1));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            JLabel titleLabel = new JLabel("Title: " + book.getTitle());
            JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
            JLabel genreLabel = new JLabel("Genre: " + book.getGenre());
            JLabel idLabel = new JLabel("ID: " + book.getID());

            panel.add(titleLabel);
            panel.add(authorLabel);
            panel.add(genreLabel);
            panel.add(idLabel);

            return panel;
        }

        public static void main(String[] args) {
            // Test DisplayBookInfo
            ArrayList<String> bookIDs = new ArrayList<>();
            bookIDs.add("1"); // Add book IDs for testing
            bookIDs.add("2");
            new DisplayBookInfo(bookIDs);
        }
    }
