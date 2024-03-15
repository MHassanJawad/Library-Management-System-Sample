import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;

public class AddBookGUI implements ActionListener {

    JFrame addbookFrame = new JFrame();
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
    JPanel addbookPanel = new JPanel(new BorderLayout());
    JTextField bookTitleField = new JTextField();
    JTextField bookIDField = new JTextField();
    JTextField authorField = new JTextField(); // Added for author
    JButton submit = new JButton();
    JComboBox<String> genreDropdown = new JComboBox<>(new String[]{"<Choose Genre>", "Fiction", "Non-Fiction", "Mystery", "Science Fiction", "Fantasy", "Romance", "Thriller", "Horror", "Biography", "History", "Self-Help", "Poetry", "Cookbook", "Art", "Travel", "Science", "Programming", "Education"});

    JLabel bookTitleLabel = new JLabel("Book Title:");
    JLabel bookIDLabel = new JLabel("Book ID:");
    JLabel genreLabel = new JLabel("Genre:");
    JLabel authorLabel = new JLabel("Author:");

    AddBookGUI() {
        submit.addActionListener(this);

        bookTitleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        bookIDLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        genreLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        authorLabel.setFont(new Font("Consolas", Font.BOLD, 16));

        addbookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addbookFrame.setSize(1080, 720);
        addbookFrame.getContentPane().setBackground(Color.orange);
        addbookFrame.setTitle("Add Book");

        setDefaultText(bookTitleField, "Enter Book Title");
        setDefaultText(bookIDField, "e.g: 1234567891234");
        setDefaultText(authorField, "Enter Author");

        bookTitleField.setPreferredSize(new Dimension(400, 30));
        bookTitleField.setFont(new Font("Consolas", Font.PLAIN, 16));
        bookTitleField.setForeground(new Color(0x808080));
        bookTitleField.setBackground(Color.lightGray);
        bookTitleField.setCaretColor(Color.black);

        bookIDField.setPreferredSize(new Dimension(400, 30));
        bookIDField.setFont(new Font("Consolas", Font.PLAIN, 16));
        bookIDField.setForeground(new Color(0x808080));
        bookIDField.setBackground(Color.lightGray);
        bookIDField.setCaretColor(Color.black);

        authorField.setPreferredSize(new Dimension(400, 30));
        authorField.setFont(new Font("Consolas", Font.PLAIN, 16));
        authorField.setForeground(new Color(0x808080));
        authorField.setBackground(Color.lightGray);
        authorField.setCaretColor(Color.black);

        genreDropdown.setPreferredSize(new Dimension(400, 30));
        genreDropdown.setFont(new Font("Consolas", Font.PLAIN, 16));
        genreDropdown.setBackground(Color.lightGray);
        genreDropdown.setSelectedItem("<Choose Genre>");

        addbookPanel.setPreferredSize(new Dimension(400, 320));

        JPanel textFieldsPanel = new JPanel(new GridLayout(6, 2, -100, 20));
        textFieldsPanel.add(bookTitleLabel);
        textFieldsPanel.add(bookTitleField);
        textFieldsPanel.add(bookIDLabel);
        textFieldsPanel.add(bookIDField);
        textFieldsPanel.add(genreLabel);
        textFieldsPanel.add(genreDropdown);
        textFieldsPanel.add(authorLabel);
        textFieldsPanel.add(authorField);
        textFieldsPanel.add(new JLabel());

        submit.setText("Submit");
        submit.setFont(new Font("Consolas", Font.PLAIN, 18));
        submit.setFocusable(false);

        addbookPanel.add(textFieldsPanel, BorderLayout.NORTH);
        addbookPanel.add(submit, BorderLayout.SOUTH);

        mainPanel.add(addbookPanel);

        addbookFrame.add(mainPanel, BorderLayout.CENTER);
        addbookFrame.setVisible(true);
        addbookFrame.pack();
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
            String title = bookTitleField.getText();
            String id = bookIDField.getText();
            String genre = genreDropdown.getSelectedItem().toString();
            String author = authorField.getText();

            if (title.isEmpty() || title.equals("Enter Book Title")) {
                displayErrorMessage("No title entered");
                return;
            }

            if (!checkGenre(genre)) {
                displayErrorMessage("Please select a genre");
                return;
            }

            if (!checkID(id)) {
                displayErrorMessage("Invalid ISBN");
                return;
            }

            Library newBook = new Library();

            // Check if ISBN already exists
            if (checkIfISBNExists(id)) {
                displayErrorMessage("Book with this ISBN already exists");
                return;
            }

            newBook.addBook(id, title, author, genre);

            // Add new data to booklist
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.addAll(newBook.getBooks());

            // Write the entire bookList to the file
            writeBookDataToFile("bookInfo.txt", bookList);

            displaySuccessMessage("Book added successfully");
        }
    }

    private void writeBookDataToFile(String fileName, ArrayList<Book> bookList) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Book book : bookList) {
                bufferedWriter.write(book.getTitle());
                bufferedWriter.newLine();
                bufferedWriter.write(book.getID());
                bufferedWriter.newLine();
                bufferedWriter.write(book.getAuthor());
                bufferedWriter.newLine();
                bufferedWriter.write(book.getGenre());
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkID(String id) {
        if (id.length() != 13) {
            return false;
        }
        return id.matches("\\d+");
    }

    private boolean checkIfISBNExists(String isbnToCheck) {
        ArrayList<Book> bookList = readBookDataFromFile("bookInfo.txt");
        for (Book book : bookList) {
            if (book.getID().equals(isbnToCheck)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGenre(String genre) {
        return !("<Choose Genre>".equals(genre));
    }

    private ArrayList<Book> readBookDataFromFile(String fileName) {
        ArrayList<Book> bookList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String title = line.trim();
                String id = reader.readLine().trim();
                String author = reader.readLine();
                String genre = reader.readLine();

                // Check for null values
                if (id == null || author == null || genre == null) {
                    break;
                }

                bookList.add(new Book(id, title, author.trim(), genre.trim()));
                reader.readLine(); // Read the empty line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(addbookFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccessMessage(String successMessage) {
        JOptionPane.showMessageDialog(addbookFrame, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        AddBookGUI addBookGUI = new AddBookGUI();
    }
}
