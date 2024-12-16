package library.management.system;

import java.util.InputMismatchException;

//CLass for book objects
class Book extends LibraryManagementSystem
{

    //Instance fields for book objects.
    private final String title;
    private final String author;
    private final String IBSN;
    //Constructor for book objects with parameters to fill instance fields.
    public Book(String title, String author, String IBSN)
    {
        this.title=title;
        this.author=author;
        this.IBSN=IBSN;
    }
    //Getter and setter methods for instance fields.
    public String getTitle()
    {
        return this.title;
    }
    public String getAuthor()
    {
        return this.author;
    }
    public String getIBSN()
    {
        return this.IBSN;
    }






}






