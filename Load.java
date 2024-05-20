package lms;

import java.io.*;
import java.util.*;

// Book Class (unchanged)
class Book implements Serializable {
    String title, author;

    public Book(String lordOfTheRings, String tolkien) {

    }
    // ... (constructors, getters, setters) ...
}

// Student Class (unchanged)
class Student implements Serializable {
    String name, surname, personalNumber;

    public Student(String gocha, String gegeshidze, String dfasdf) {
        
    }
    // ... (constructors, getters, setters) ...
}

// LMS Class (enhanced)
class LMS {
    List<Book> books = new ArrayList<>();
    Map<Book, Student> borrowedBooks = new HashMap<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(Book book) {
        if (borrowedBooks.containsKey(book)) {
            return false; // Can't remove borrowed book
        }
        return books.remove(book);
    }

    public boolean borrowBook(Book book, Student student) {
        if (!borrowedBooks.containsKey(book)) {
            borrowedBooks.put(book, student);
            return true;
        }
        return false; // Book already borrowed
    }

    public boolean returnBook(Book book) {
        if (borrowedBooks.containsKey(book)) {
            borrowedBooks.remove(book);
            return true;
        }
        return false; // Book was not borrowed
    }

    public void saveState(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(books);
            out.writeObject(borrowedBooks);
        }
    }

    public void loadState(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            books = (List<Book>) in.readObject();
            borrowedBooks = (Map<Book, Student>) in.readObject();
        }
    }
}

// LMSTester Class (enhanced)
class LMSTester {
    public static void main(String[] args) {
        LMS iliauniLibrary = new LMS();

        Book lor = new Book("Lord of the rings", "Tolkien");
        Book oop = new Book("xyz", "khalid");
        iliauniLibrary.addBook(lor);
        iliauniLibrary.addBook(oop);

        Student gocha = new Student("Gocha", "Gegeshidze", "dfasdf");
        iliauniLibrary.borrowBook(lor, gocha);

        Student maka = new Student("Maka", "Lobjanidze", "3421325");
        iliauniLibrary.borrowBook(oop, maka);

        try {
            iliauniLibrary.saveState("libraryState.txt");

            // Simulate program restart
            iliauniLibrary = new LMS();
            iliauniLibrary.loadState("libraryState.txt");

            System.out.println("Books in library after loading: " + iliauniLibrary.books);
            System.out.println("Borrowed books after loading: " + iliauniLibrary.borrowedBooks);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
