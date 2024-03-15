public class Book{

    private String author, title, genre, id;
    private boolean isAvailable;

    Book(String id,String title,String author,String genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;

    }
    public void setAvailable(boolean available){
        this.isAvailable = available;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getTitle(){
        return this.title;
    }

    public String getGenre(){
        return this.genre;
    }

    public String getID(){
        return this.id;
    }

    public boolean isAvailable(){
        return this.isAvailable;
    }

    public void displayInfo(){
        System.out.printf("Book Title = %s%nBook ID = %d%nAuthor = %s%nGenre = %s%nAvailable = %b%n%n", title, id, author, genre, isAvailable);
    }

}