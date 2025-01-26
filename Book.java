package library.management.system;

import java.time.LocalDate;

//CLass for book objects
class Book
{

    //Instance fields for book objects.
    private String title;
    private String author;
    private String IBSN;
    private boolean isIssued=false;
    private LocalDate returnData;

    public Book(){}

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
    public void setTitle(String titleValue)
    {
        this.title=titleValue;
    }
    public String getAuthor()
    {
        return this.author;
    }
    public void setAuthor(String authorValue)
    {
        this.author=authorValue;
    }
    public String getIBSN()
    {
        return this.IBSN;
    }
    public void setIBSN(String IBSNValue)
    {
        this.IBSN=IBSNValue;
    }
    public boolean getisIssued()
    {
        return this.isIssued;
    }
    public Boolean setisIssued(Boolean isIssuedValue)
    {
        return this.isIssued=isIssuedValue;
    }
public void setReturnDate(LocalDate returnDataValue)
{
    this.returnData=returnDataValue;
}
public LocalDate getReturnDate()
{
    return this.returnData;
}








}






