package library.management.system;


import java.util.*;



public class LibraryManagementSystem
{
    Scanner kb = new Scanner(System.in);
    ArrayList<Book> allBooks=new ArrayList<Book>();
    HashMap<String,Boolean> isIssued=new HashMap<String,Boolean>();



//CLass for book objects
    class Book
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
    //Created a comparator for comparing book titles
    class titleComparator implements Comparator<Book>
    {
        //Overrides the compare method
        public int compare(Book book1, Book book2)
        {
            return book1.title.compareTo(book2.title);
        }
    }

    //Method for displaying program logo with nested for loop.
    public void logo()
    {
        for(int i=0; i<5; i++)
        {
            System.out.println();
            for(int j=0;j<53;j++)
            {
                if (i==1 && j==18)
                {
                    System.out.print(" EduBooks ");
                    j+=12;
                }
                else if(i==3 && j==10)
                {
                    System.out.print(" Library Management System ");
                    j+=10;
                }

                else {System.out.print("/");}
            }
        }
    }

    public void mainMenu()
    {
        int userOp=0;
        boolean valid=false;
        logo();
        while(!valid)
        {
            try {
                System.out.println("\n|1| Add Book\n|2| Search Book\n" +
                        "|3| Display Book\n|4| Remove Book\n|5| Issue Book\n|0| Exit");
                System.out.println("/////////////////////////////////////////////////");
                String strUserOp=kb.nextLine();
                userOp = Integer.parseInt(strUserOp);

                if(userOp>5||userOp<0)
                {
                    throw new InputMismatchException();
                }
                valid=true;
            }
            catch (InputMismatchException|NumberFormatException e)
            {
                System.out.println("Error: Invalid selection");
                kb.nextLine();
            }
        }
        menuOptions(userOp);
    }
    //Takes user input passed to it and calls corresponding method.
    public void menuOptions(int userOp)
    {
        switch (userOp)
        {
            case 1:
                addBook();
            case 2:
                searchBook();
            case 3:
                displayBooks();
            case 4:
               // removeBook();
            case 5:
               // issueBook();
            case 0:
                System.out.println("Program shutting down...");
                System.exit(0);
                break;
        }
    }

    public void addBook() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Enter the book title;");
                String title = kb.nextLine();
                if (title.isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the authors name;");
                String author = kb.nextLine();
                if (author.isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the books ISBN;");
                String ISBN = kb.nextLine();
                if (ISBN.length() != 10 && ISBN.length() != 13) throw new InputMismatchException();
                valid = true;
                Book newBook = new Book(title,author,ISBN);
                System.out.println(newBook.getTitle());
                System.out.println(newBook.getAuthor());
                System.out.println(newBook.getIBSN());
                System.out.println("Please confirm book;             y | n");
                boolean userConfirm=yOrN();
                if(userConfirm)
                {
                    allBooks.add(newBook);
                    isIssued.put(ISBN, false);
                    System.out.println("Book has been successfully added.");
                    next(1);
                }
                else
                        {
                            System.out.println("Please renter the correct book:");
                            addBook();
                        }
            } catch (InputMismatchException e)
            {
                    System.out.println("Error: Invalid input");
            }
        }
    }

    public void displayBooks()
    {
        //Use Comparator to sort book alphabetically by title.
        allBooks.sort(new titleComparator());
        //Iterate over each book in allBooks ArrayList (Every book in the library).
        for(Book book : allBooks)
        {
            //Iterate over each value key in isIssued HashMap (Every book's issued status)
            for(String i : isIssued.keySet())
            {
                //If book's key in isIssued matches that of the current book iterated over;
                if(i.equals(book.IBSN))
                {
                    //Assign bookStatus the value of the current key,
                    Boolean bookStatus=isIssued.get(i);
                    //and print the values of the book along with that of bookStatus
                    System.out.println(book.title+" "+book.author+" "+book.IBSN+" "+bookStatus);
                }
            }
        }
    next(3);
    }

    public void searchBook()
    {
        ArrayList<Book> searchresults = new ArrayList<Book>();
        ArrayList<Book> bookOfAuthor=new ArrayList<Book>();
        ArrayList<Book> booksWithTitle=new ArrayList<Book>();
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Enter book title or authors name:");
                String titleOrAuthor=kb.nextLine();
                if (titleOrAuthor.isEmpty()) throw new InputMismatchException();
                for (Book book : allBooks)
                {
                    if(book.title.equals(titleOrAuthor))
                    {
                        booksWithTitle.add(book);
                    }
                    else if(book.author.equals(titleOrAuthor))
                    {
                        bookOfAuthor.add(book);
                    }
                }
                if(booksWithTitle.isEmpty() && bookOfAuthor.isEmpty())
                {
                    System.out.println("No results found");
                    next(2);
                }
                else
                if(booksWithTitle.size()>bookOfAuthor.size())
                {
                    searchresults=booksWithTitle;
                }
                else
                {
                    searchresults=booksWithTitle;
                }
                {
                    System.out.println("All results");
                    for (int j=0;j<searchresults.size();j++)
                    {
                        for(String i : isIssued.keySet())
                        {
                            if(i.equals(searchresults.get(j).IBSN))
                            {
                                boolean bookStatus=isIssued.get(i);
                                System.out.println("|"+(j-1)+"| "+searchresults.get(j).title
                                        +" " +searchresults.get(j).author+" "+searchresults.get(j).IBSN+" " +bookStatus);
                            }
                        }
                    }
                }
                }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid input");
            }
        }
    }



    
    public void next(int repeatedOption)
{
    boolean valid=false;
    while(!valid)
    {
    try
    {
    System.out.println("/////////////////////////////////////////////////");
    System.out.println("Next:\n|0| Menu\n|1| Repeat");
    int userOption=Integer.parseInt(kb.nextLine());
    if(userOption<0||userOption>1)
    {
        throw new InputMismatchException();
    }
    switch (userOption) {
        case 1:
            switch (repeatedOption) {
                case 1:
                    menuOptions(1);
                case 2:
                    menuOptions(2);
                case 3:
                    menuOptions(3);
                case 4:
                    menuOptions(4);
                case 5:
                    menuOptions(5);
            }
        case 0:
            mainMenu();
    }
    valid=true;
    } catch (InputMismatchException|NumberFormatException e)
    {
        System.out.println("Error: Invalid input");
    }
}
}

public boolean yOrN()
{
    boolean confirmed=false;
    boolean valid=false;
    while(!valid)
    {
        try
        {
            String userConfirm=kb.nextLine().trim();
            if(userConfirm.equalsIgnoreCase("y"))
            {
                confirmed=true;
                valid=true;
            }
            else if (userConfirm.equalsIgnoreCase("n"))
            {
                valid=true;
            }
            else throw new InputMismatchException();
        }
          catch (InputMismatchException e)
        {
            System.out.println("Error: Invalid input\nRenter either y or n:");
        }
    }
    return confirmed;
}


public static void main(String[] args)
    {
LibraryManagementSystem newLibrary = new LibraryManagementSystem();
newLibrary.mainMenu();
    }
}