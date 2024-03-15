import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BookSearchResult {

    public BookSearchResult(ArrayList<Book> searchResult) {
        JFrame frame = new JFrame("Search Result");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(searchResult.size(), 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Book book : searchResult) {
            mainPanel.add(createBookPanel(book));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
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
}
