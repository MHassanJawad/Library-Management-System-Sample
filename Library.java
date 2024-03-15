import java.util.ArrayList;
import java.util.Scanner;

public class Library{
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<User> users = new ArrayList<User>();

    ArrayList<Book> booksByAuthor = new ArrayList<Book>();
    
    Scanner sc = new Scanner(System.in);

    public void addBook(String id, String name, String author, String genre){
        books.add(new Book(id, name, author, genre));
    }

    public ArrayList<Book> getBooks(){
        return books;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

        
    public void addUser(String id, String name, String contactNum){
        users.add(new User(id, name, contactNum));
    }

    public void checkout(){
        return;
    }

    public void returnBook(){
        return;
    }

       
    public static void main(String[] args){
        System.out.println("heloo");
    }

       
    
}