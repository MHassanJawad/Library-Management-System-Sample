import java.util.ArrayList;

public class User{
    private String name, contactNum, id;
    private ArrayList<Book> booksBorrowed = new ArrayList<Book>();
    public String errorMessage;
    

    User(String id, String name, String contactNum){
        this.id = id;
        this.name = name;
        this.contactNum = contactNum;
    }

    public void addBook(Book book){
        this.booksBorrowed.add(book);
        
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }

    public String getContactNum(){
        return this.contactNum;
    }


}