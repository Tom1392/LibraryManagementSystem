package library.management.system;
import java.util.*;

public class LibraryManagementSystem
{
    protected final Scanner kb = new Scanner(System.in);
    protected ArrayList<Book> allBooks=new ArrayList<Book>();
    protected HashMap<String,Boolean> isIssued=new HashMap<String,Boolean>();
    protected Book selectedBook;

    //Created a comparator for comparing book titles
    class titleComparator implements Comparator<Book>
    {
        //Overrides the compare method
        public int compare(Book book1, Book book2)
        {
            if(book1.getTitle().toLowerCase().startsWith("the")&&book2.getTitle().toLowerCase().startsWith("the"))
            {
                String noThe1=book1.getTitle().substring(4);
                String noThe2=book2.getTitle().substring(4);
                return noThe1.compareTo(noThe2);
            }
             else if(book1.getTitle().toLowerCase().startsWith("the"))
            {

                String noThe = book1.getTitle().substring(4);
                return noThe.compareTo(book2.getTitle());
            }
             else if(book2.getTitle().toLowerCase().startsWith("the"))
            {
                String noThe = book2.getTitle().substring(4);
                return book1.getTitle().compareTo(noThe);
            }
            else
            {
                return book1.getTitle().compareTo(book2.getTitle());
            }
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
                if (i==1 && j==21)
                {
                    System.out.print(" EduBooks ");
                    j+=9;
                }
                else if(i==3 && j==12)
                {
                    System.out.print(" Library Management System ");
                    j+=26;
                }

                else {System.out.print("/");}
            }
        }
    }

    public void displayHeader()
    {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("|N"+(char)176+"|  Title                         | Author                   | IBSN         | Issued");
        System.out.println("----------------------------------------------------------------------------------------");
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
                        "|3| Display Book\n|4| Remove Book\n|0| Exit");
                System.out.println("/////////////////////////////////////////////////////");
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
                removeBook();
            case 5:
                issueBook(selectedBook);
            case 0:
                System.out.println("Program shutting down...");
                System.exit(0);
                break;
        }
    }
    //Returns user to main menu
    public void cancel()
    {
        mainMenu();
    }

    public void addBook()
    {
        int cancel;
        boolean valid = false;
        while (!valid)
        {
            try {
                System.out.println("Enter the book title:                     |0| Cancel");
                String title = kb.nextLine().trim();
                if(title.equals("0")) cancel();
                if (title.isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the authors name:                   |0| Cancel");
                String author = kb.nextLine().trim();
                if(author.equals("0")) cancel();
                if (author.isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the books ISBN:                     |0| Cancel\n(Must be 10 or 13 digits)");
                String ISBN = kb.nextLine();
                if(ISBN.equals("0")) cancel();
                if (ISBN.length() != 10 && ISBN.length() != 13) throw new InputMismatchException();
                valid = true;
                Book newBook = new Book(title,author,ISBN);
                System.out.println("Title  | "+newBook.getTitle());
                System.out.println("Author | "+newBook.getAuthor());
                System.out.println("IBSN   | "+newBook.getIBSN());
                System.out.println("Please confirm book:");
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


    public void searchBook()
    {
        int bookOption;
        String titleOrAuthor;
        ArrayList<Book> searchresults = new ArrayList<Book>();
        ArrayList<Book> bookOfAuthor=new ArrayList<Book>();
        ArrayList<Book> booksWithTitle=new ArrayList<Book>();
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Enter book title or authors name:          |0| Cancel");
                titleOrAuthor = kb.nextLine();
                if(titleOrAuthor.equals("0")) cancel();
                if (titleOrAuthor.isEmpty()) throw new InputMismatchException();
                valid = true;


                for (Book book : allBooks)
                {
                    if(book.getTitle().equalsIgnoreCase(titleOrAuthor))
                    {
                        booksWithTitle.add(book);
                    }
                    else if(book.getAuthor().equalsIgnoreCase(titleOrAuthor))
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
                System.out.println("All results");
                displayHeader();
                    for (int j=0;j<searchresults.size();j++)
                    {
                        bookDisplay(searchresults.get(j),j+1);
                    }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input");
            }

        }

        valid=false;
                while(!valid)
                {
                    try
                    {
                        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
                        System.out.println("Please select book:                                            " +
                                "              |0| Cancel");
                        bookOption = Integer.parseInt(kb.nextLine());
                        if (bookOption == 0) {cancel();}
                        if (bookOption - 1 > searchresults.size() || bookOption < 0)
                        {
                            throw new InputMismatchException("Error: Invalid selection");
                        }
                        valid = true;
                        selectedBook = searchresults.get(bookOption-1);
                        searchresults.clear();
                        nextBookAction(selectedBook);
                        }
                        catch (InputMismatchException | NumberFormatException e)
                        {
                            System.out.println("Error: " + e.getMessage());
                        }
                }
    }




    public void nextBookAction(Book selectedBook)
    {
        boolean valid=false;
        int bookAction;
        while(!valid)
        {
            try
            {
                System.out.println("Select next step for "+selectedBook.getTitle()+" by "+selectedBook.getAuthor()+
                        "                              |0| Cancel");
                System.out.println("|1| Issue Book\n|2| Remove Book");
                bookAction = Integer.parseInt(kb.nextLine());
                if(bookAction<0||bookAction>2)
                {
                    throw new InputMismatchException();
                }
                valid=true;
                if(bookAction==0) mainMenu();
                else if(bookAction==1)issueBook(selectedBook);
                else removeBook(selectedBook);
            }
            catch (InputMismatchException | NumberFormatException e)
            {
                System.out.println("Error: Invalid selection");
            }
        }
    }

    public void displayBooks()
    {
        displayHeader();
        //Use Comparator to sort book alphabetically by title.
        allBooks.sort(new titleComparator());
        //Iterate over each book in allBooks ArrayList (Every book in the library).
        for(int i=0; i<allBooks.size();i++)
        {
            //Iterate over each value key in isIssued HashMap (Every book's issued status)
            for(String j : isIssued.keySet())
            {
                //If book's key in isIssued matches that of the current book iterated over;
                if(j.equals(allBooks.get(i).getIBSN()))
                {
                    //Assign bookStatus the value of the current key,
                    Boolean bookStatus=isIssued.get(j);
                    //and print the values of the book along with that of bookStatus
                    //System.out.println(book.getTitle()+" "+book.getAuthor()+" "+book.getIBSN()+" "+bookStatus);
                    bookDisplay(allBooks.get(i),i+1);
                }
            }
        }
        System.out.println("----------------------------------------------------------------------------------------");
        next(3);
    }



    public void bookDisplay(Book currentBook,int label)
    {
        Boolean issueStatus;
        String titlePadded=currentBook.getTitle();
        String authorPad=currentBook.getAuthor();
        String IBSNPad=currentBook.getIBSN();
        for(int i = 0; i<30-currentBook.getTitle().length();i++)
        {
            titlePadded+=" ";
        }
        for(int i=0;i<25-currentBook.getAuthor().length();i++)
        {
            authorPad+=" ";
        }
        for(int i=0; i<14-currentBook.getIBSN().length();i++)
        {
            IBSNPad+=" ";
        }
        for(String IBSN : isIssued.keySet())
        {
            if(IBSN.equals(currentBook.getIBSN()))
            {
                issueStatus=isIssued.get(IBSN);
                System.out.println("|"+label+"|   "+titlePadded+"| "+authorPad+"| "
                        +IBSNPad+"| "+issueStatus);
            }
        }

    }



public void removeBook()
{
    boolean valid=false;
    String removeIBSN;
    while(!valid)
    {
        try
        {
            System.out.println("Enter IBSN of book to remove:                   |0| Cancel");
            removeIBSN=kb.nextLine();
            if(removeIBSN.equals("0")) cancel();
            if (removeIBSN.length() != 10 && removeIBSN.length() != 13) throw new InputMismatchException();
            valid=true;
            for(Book book : allBooks)
            {
                if (book.getIBSN().equals(removeIBSN))
                {
                    System.out.println("Confirm you wish to remove the following book:                   |0| Cancel");
                    System.out.println(book.getTitle()+" by "+book.getAuthor());
                    if(yOrN())
                    {
                    allBooks.remove(book);
                    for(String i : isIssued.keySet())
                    {
                        if(i.equals(removeIBSN))
                        {
                            isIssued.remove(i);
                            System.out.println("Book has been removed successfully");
                            next(4);
                        }
                        }
                    }
                    else next(4);
                }
                else System.out.println("No such book found"); break;
            }
        }
        catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid input");
            }
    }
next(4);
}





    public void removeBook(Book selectedBook)
    {
        for(Book book : allBooks)
        {
            if (book.getIBSN().equals(selectedBook.getIBSN()))
            {
                System.out.println("Confirm you wish to remove the following book:");
                System.out.println(selectedBook.getTitle()+" by "+selectedBook.getAuthor());
                if(yOrN())
                {
                    allBooks.remove(book);
                    for(String i : isIssued.keySet())
                    {
                        if(i.equals(selectedBook.getIBSN()))
                        {
                            isIssued.remove(i);
                            System.out.println("Book has been removed successfully");
                            next(2);
                        }
                    }
                }
                else next(2);
            }
        }
    }
    public void issueBook(Book selectedBook)
    {
        boolean valid=false;
        int userOption;
        boolean requestBorrow=false;
        boolean requestReturn=false;
        for(String IBSN : isIssued.keySet())
        {
            if (IBSN.equals(selectedBook.getIBSN()))
            {
                while(!valid) {
                    try {
                        System.out.println(selectedBook.getTitle()+" "+selectedBook.getAuthor()+" "+
                                selectedBook.getIBSN());
                        System.out.println("Select option:                        |0| Cancel\n|1| Borrow\n|2| Return");
                        userOption = Integer.parseInt(kb.nextLine());
                        if (userOption < 0 || userOption > 2) throw new InputMismatchException();
                        else if (userOption == 1) {
                            requestBorrow = true;
                            if (isIssued.get(IBSN)) {
                                System.out.println("Book is currently unavailable");
                                next(2);
                            } else if (!isIssued.get(IBSN)) {
                                System.out.println("Confirm you wish to borrow " + selectedBook.getTitle()+"   |0| Cancel");
                                if (yOrN()) {
                                    isIssued.replace(IBSN, true);
                                    System.out.println("Book has been successfully issued.");
                                    next(2);
                                } else next(2);
                            } else {
                                requestReturn = true;
                                if (!isIssued.get(IBSN)) {
                                    System.out.println("Confirm you wish to return " + selectedBook.getTitle());
                                    if (yOrN())
                                    {
                                        isIssued.replace(IBSN, false);
                                        System.out.println("Book has successfully been returned.");
                                        next(2);
                                    } else next(2);
                                } else System.out.println("Book is not issued and cannot be returned");
                                next(2);
                            }
                        }
                        valid = true;
                    }
                    catch (InputMismatchException | NumberFormatException e)
                    {
                        System.out.println("Error: Invalid input");
                    }
                }

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
    System.out.println("////////////////////////////////////////////////////");
    System.out.println("Next:\n|0| Menu\n|1| Repeat");
    int userOption=Integer.parseInt(kb.nextLine());
    if(userOption<0||userOption>4)
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
            System.out.println("                                         y | n");
            String userConfirm=kb.nextLine().trim();
            if(userConfirm.equals("0")) cancel();
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
        newLibrary.allBooks.add(new Book("The road","Comac Mcarther","1679435286"));
        newLibrary.allBooks.add(new Book("the Player of Games","Iain M Banks","6798246384"));
        newLibrary.allBooks.add(new Book("HandMaids Tale","Margret Atwood","4981674358"));
        newLibrary.allBooks.add(new Book("Lexicon","Max Barry", "4678153948"));
        newLibrary.isIssued.put("1679435286",false);
        newLibrary.isIssued.put("6798246384",false);
        newLibrary.isIssued.put("4981674358",false);
        newLibrary.isIssued.put("4678153948",false);
        newLibrary.mainMenu();
    }
}