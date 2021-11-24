import java.util.*;

abstract class LibrarySystem {
    public String bookName, bookAuthor, issuedTo;
    Date issuedOn;
    Calendar c = Calendar.getInstance();

    public LibrarySystem() {
        this.bookName = null;
        this.bookAuthor = null;
        this.issuedTo = null;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public Date getIssuedOn() {
        issuedOn = c.getTime(); 
        return issuedOn;
    }

    abstract void addBook();

    abstract void returnBook();

    abstract void issueBook();
}

class Librarian extends LibrarySystem {
    ArrayList<Librarian> availableBooks, issuedBooks;
    Scanner sc = new Scanner(System.in);

    public Librarian() {
        availableBooks = new ArrayList<Librarian>();
        issuedBooks = new ArrayList<Librarian>();
    }

    String getNameOfBook() {
        String bn;
        System.out.print("Enter Name of Book : ");
        bn = sc.nextLine();
        Librarian chk = isBookAvailable(bn);
        Librarian chk1 = isBookIssued(bn);
        if(chk1 != null) {
            System.out.println("Sorry! Book Can't be Added , it " + bn + " is Already Issued and it's Details are : ");
            printDetails(chk1);
            return null;
            }
        else if (chk != null) {
            System.out.println("Book "+bn+" is Already Present so Book  " + bn + " is Refused to Add and it's Details are : ");
            printDetails(chk);
            return null;
        } 
        else
            return bn;
    }

    String getAuthorOfBook() {
        System.out.print("Enter Name of Author : ");
        String an = sc.nextLine();
        return an;
    }

    void printDetails(Librarian l) {
        System.out.println("Name of Book : " + l.getBookName());
        System.out.println("Author of Book : " + l.getBookAuthor());
        if (l.getIssuedTo() != null) {
            System.out.println("Book issued to : " + l.getIssuedTo());
            System.out.println("Book issued on : " + l.getIssuedOn());
        }
        System.out.println();
    }

    Librarian isBookAvailable(String bk) {
        for (Librarian book : availableBooks) {
            if (book.getBookName().equalsIgnoreCase(bk)) {
                return book;
            }
        }
        return null;
    }

    Librarian isBookIssued(String bk) {
        for (Librarian book : issuedBooks) {
            if (book.getBookName().equalsIgnoreCase(bk)) {
                return book;
            }
        }
        return null;
    }

    void printAllBooks(String input) {
        if (input.equals("available")) {
            if (availableBooks.size() == 0) {
                System.out.println("No books Available");
            } else {
                for (Librarian l : availableBooks) {
                    printDetails(l);
                }
            }
        } 
        else {
            if (issuedBooks.size() == 0) {
                System.out.println("No books Issued");
            } else {
                for (Librarian l : issuedBooks) {
                    printDetails(l);
                }
            }
        }
    }

    @Override
    void addBook() {
        Librarian l1 = new Librarian();
        String book =getNameOfBook();
        l1.setBookName(book);
        if (book==null){
            return;
        }
        else{
        l1.setBookAuthor(getAuthorOfBook());
        availableBooks.add(l1);
        System.out.println("Book Added Successfully");
        }
    }

    @Override
    void returnBook() {
        Librarian l3 = new Librarian();
        System.out.print("Enter Name of Book to return : ");
        String rb = sc.nextLine();
        Librarian chk = isBookAvailable(rb);
        Librarian chk1 = isBookIssued(rb);
        if (chk == null && chk1 == null) {
            System.out.println("Book '" + rb + "' is neither issued nor available");
        } else if (chk != null) {
            System.out.println("Book '" + rb + "' is already returned and it's details are ");
            printDetails(chk);
        } else {
            l3.setBookName(chk1.getBookName());
            l3.setBookAuthor(chk1.getBookAuthor());
            availableBooks.add(l3);
            issuedBooks.remove(chk1);
            System.out.println("Book '" + rb + "' is Successfully returned ");
        }

    }

    @Override
    void issueBook() {
        Librarian l2 = new Librarian();
        System.out.print("Enter Name of Book to be issue : ");
        String ib = sc.nextLine();
        Librarian chk = isBookAvailable(ib);
        Librarian chk1 = isBookIssued(ib);

        if (chk == null && chk1 == null) {
            System.out.println("Sorry! Book '" + ib + "' is not Available ");
        } else if (chk1 != null) {
            System.out.println("Sorry! Book '" + ib + "' is already issued and it's details are ");
            printDetails(chk1);
        } else {
            System.out.print("Enter Name of issuer : ");
            l2.setIssuedTo(sc.nextLine());
            l2.setBookName(chk.getBookName());
            l2.setBookAuthor(chk.getBookAuthor());
            issuedBooks.add(l2);
            availableBooks.remove(chk);
            System.out.println("Book '" + ib + "' is Successfully issued to " + l2.getIssuedTo());
        }

    }

}

public class Library_Management_System {
    public static void main(String[] args) {
        Librarian lib = new Librarian();
        Scanner sc = new Scanner(System.in);
        char choice;
        do {
            System.out.println("\n1. Add a book.\n2. Issue a book.\n3. Return a book.\n4. Available books.\n5. Issued books.\n6. Exit  ");
            System.out.print("Enter Choice : ");
            choice = sc.next().charAt(0);
            System.out.println();
            switch (choice) {
            case '1':
                lib.addBook();
                break;

            case '2':
                lib.issueBook();
                break;

            case '3':
                lib.returnBook();
                break;

            case '4':
                lib.printAllBooks("available");
                break;

            case '5':
                lib.printAllBooks("issued");
                break;

            case '6':
                System.out.println("Exited Successfully.....");
                break;
                
            default:
                System.out.println("Invalid Input");
            }
            
        } while (choice!='6');
        sc.close();
    }
}