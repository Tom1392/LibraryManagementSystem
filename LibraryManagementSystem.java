package library.management.system;
import java.util.*;

public class LibraryManagementSystem
{
    //instance variables
    protected final Scanner kb = new Scanner(System.in);
    protected ArrayList<Book> allBooks=new ArrayList<Book>();
    protected HashMap<String,Boolean> allIssued=new HashMap<String,Boolean>();
    protected Book selectedBook;

    //Comparator for comparing book titles
    class titleComparator implements Comparator<Book>
    {
        //Overrides the compare method
        public int compare(Book book1, Book book2)
        {
            //Checks for the inclusion of the word "the" and disregards from alphabetic sorting.
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
    //Header for table displaying books.
    public void displayHeader()
    {
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("|N"+(char)176+"|  Title                         | Author                   | IBSN          | Issued");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
    //Main menu method for displaying menu options, with error handling. 
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
    //Takes user input passed to it and calls corresponding method or ends program.
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
    //creates book object and collects the values for the instance variables and adds to the ArrayList allBooks and HashMap isIssued.
    public void addBook()
    {
        int cancel;
        boolean valid = false;
        while (!valid)
        {
            try {
                Book newBook = new Book();
                System.out.println("Enter the book title:                     |0| Cancel");
                newBook.setTitle(kb.nextLine().trim());
                if(Objects.equals(newBook.getTitle(), "0")) cancel();
                if(newBook.getTitle().isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the authors name:                   |0| Cancel");
                newBook.setAuthor(kb.nextLine().trim());
                if(newBook.getAuthor().equals("0")) cancel();
                if(newBook.getAuthor().isEmpty()) throw new InputMismatchException();
                System.out.println("Enter the books ISBN:                     |0| Cancel\n(Must be 10 or 13 digits)");
                long longISBN=Long.parseLong(kb.nextLine());
                newBook.setIBSN(String.valueOf(longISBN));
                if(Objects.equals(newBook.getIBSN(), "0")) cancel();
                if (newBook.getIBSN().length() != 10 && newBook.getIBSN().length() != 13) throw new InputMismatchException();
                valid = true;
                System.out.println("Title  | "+newBook.getTitle());
                System.out.println("Author | "+newBook.getAuthor());
                System.out.println("IBSN   | "+newBook.getIBSN());
                System.out.println("Please confirm book:");
                boolean userConfirm=yOrN();
                if(userConfirm)
                {
                    allBooks.add(newBook);
                    allIssued.put(newBook.getIBSN(), newBook.getisIssued());
                    System.out.println("Book has been successfully added.");
                    next(1);
                }
                else
                        {
                            System.out.println("Please renter the correct book:");
                            addBook();
                        }
            } catch (InputMismatchException|NumberFormatException e)
            {
                    System.out.println("Error: Invalid input");
            }
        }
    }
//Prompts user for title/ author name or keyword and searches allBooks for a match and calls BookDisplay to display all the results.
    public void searchBook()
    {
        int bookOption;
        String userSearch;
        ArrayList<Book> searchresults = new ArrayList<Book>();
        ArrayList<Book> bookOfAuthor=new ArrayList<Book>();
        ArrayList<Book> booksWithTitle=new ArrayList<Book>();
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Enter either title, authors name or keyword:          |0| Cancel");
                userSearch = kb.nextLine();
                if(userSearch.equals("0")) cancel();
                if (userSearch.isEmpty()) throw new InputMismatchException();
                valid = true;


                for (Book book : allBooks)
                {
                    if(book.getTitle().toLowerCase().contains(userSearch.toLowerCase()))
                    {
                        booksWithTitle.add(book);
                    }
                    else if(book.getAuthor().toLowerCase().contains(userSearch.toLowerCase()))
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

                    booksWithTitle.addAll(bookOfAuthor);
                    searchresults=booksWithTitle;
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
//The user is prompted to select a book from those displayed from the search results list.
        valid=false;
                while(!valid)
                {
                    try
                    {
                        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
                        System.out.println("Please select book:                                            " +
                                "                |0| Cancel");
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
    //User is prompted to either issue or remove the selected book and the corresponding method is called.
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
            for(String j : allIssued.keySet())
            {
                //If book's key in isIssued matches that of the current book iterated over;
                if(j.equals(allBooks.get(i).getIBSN()))
                {
                    //Assign bookStatus the value of the current key,
                    Boolean bookStatus=allIssued.get(j);
                    bookDisplay(allBooks.get(i),i+1);
                }
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------");
        next(3);
    }
//Uses the length of the values of Book object to pad with empty strings to format and print a table of books.
    public void bookDisplay(Book currentBook,int label)
    {
        String issueStatus;
        String titlePad=currentBook.getTitle();
        String authorPad=currentBook.getAuthor();
        String IBSNPad=currentBook.getIBSN();
        for(int i = 0; i<30-currentBook.getTitle().length();i++)
        {
            titlePad+=" ";
        }
        for(int i=0;i<25-currentBook.getAuthor().length();i++)
        {
            authorPad+=" ";
        }
        for(int i=0; i<14-currentBook.getIBSN().length();i++)
        {
            IBSNPad+=" ";
        }
        for(String IBSN : allIssued.keySet())
        {
            if(IBSN.equals(currentBook.getIBSN()))
            {
                if(allIssued.get(IBSN))
                {
                    issueStatus="Issued";
                }
                else issueStatus="Available";
                System.out.println("|"+label+"|   "+titlePad+"| "+authorPad+"| "
                        +IBSNPad+"| "+issueStatus);
            }
        }
    }
//Prompts the user for the ISBN of the book to be removed and then searches both allBooks and isIssued and removes the matching book.
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
                    for(String i : allIssued.keySet())
                    {
                        if(i.equals(removeIBSN))
                        {
                            allIssued.remove(i);
                            System.out.println("Book has been removed successfully");
                            next(4);
                        }
                        }
                    }
                    else next(4);
                };
            }
             System.out.println("No such book found"); break;
        }
        catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid input");
            }
    }
next(4);
}
//Overloaded method that removes the book that has been found through search method.
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
                    for(String i : allIssued.keySet())
                    {
                        if(i.equals(selectedBook.getIBSN()))
                        {
                            allIssued.remove(i);
                            System.out.println("Book has been removed successfully");
                            next(2);
                        }
                    }
                }
                else next(2);
            }
        }
    }
    //uses the book found through search methods and prompts the user to either borrow or return it. The boolean value stored
    // in isIssued is then used to check whether the book is available to be either returned or borrowed and either informs the
    // user or changes the value accordingly.
    public void issueBook(Book selectedBook)
    {
        boolean valid=false;
        int userOption;
        boolean requestBorrow=false;
        boolean requestReturn=false;
        for(String IBSN : allIssued.keySet())
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
                            if (allIssued.get(IBSN)) {
                                System.out.println("Book is currently unavailable");
                                next(2);
                            } else if (!allIssued.get(IBSN)) {
                                System.out.println("Confirm you wish to borrow " + selectedBook.getTitle() + "   |0| Cancel");
                                if (yOrN()) {
                                    allIssued.replace(IBSN, selectedBook.setisIssued(true));
                                    System.out.println("Book has been successfully issued.");
                                    next(2);
                                } else next(2);
                            }
                            } else if (userOption == 2){
                                //requestReturn = true;
                                if (allIssued.get(IBSN)) {
                                    System.out.println("Confirm you wish to return " + selectedBook.getTitle());
                                    if (yOrN())
                                    {
                                        allIssued.replace(IBSN, false);
                                        System.out.println("Book has successfully been returned.");
                                        next(2);
                                    } else next(2);
                                } else System.out.println("Book is not issued and cannot be returned");
                                next(2);
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
    //Prompts the user to select to either repeat the previous method that has been passed to it. or returns to the main menu.
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
//Used for any yes/no confirmation and return the result as a boolean value.
public boolean yOrN() {
    boolean confirmed = false;
    boolean valid = false;
    while (!valid) {
        try {
            System.out.println("                                         y | n");
            String userConfirm = kb.nextLine().trim();
            if (userConfirm.equals("0")) cancel();
            if (userConfirm.equalsIgnoreCase("y")) {
                confirmed = true;
                valid = true;
            } else if (userConfirm.equalsIgnoreCase("n")) {
                valid = true;
            } else throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input\nRenter either y or n:");
        }
    }
    return confirmed;
}

public static void main(String[] args)
    {
        //book objects for testing.
       LibraryManagementSystem newLibrary = new LibraryManagementSystem();
       newLibrary.allBooks.add(new Book("The road","Comac Mcarther","1679435286"));
       newLibrary.allBooks.add(new Book("the Player of Games","Iain M Banks","6798246384"));
       newLibrary.allBooks.add(new Book("HandMaids Tale","Margret Atwood","4981674358"));
       newLibrary.allBooks.add(new Book("Lexicon","Max Barry", "4678153948"));
       newLibrary.allBooks.add(new Book("The Life and times of Tom","John Smith", "7648315896"));
       newLibrary.allBooks.add(new Book("Remainder","Tom McCarthy", "9456381284"));
       newLibrary.allIssued.put("1679435286",false);
       newLibrary.allIssued.put("6798246384",false);
       newLibrary.allIssued.put("4981674358",false);
       newLibrary.allIssued.put("4678153948",false);
       newLibrary.allIssued.put("7648315896",false);
       newLibrary.allIssued.put("9456381284",false);
       newLibrary.mainMenu();
    }
}