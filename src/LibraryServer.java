import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LibraryServer extends UnicastRemoteObject implements ILibrary {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    protected LibraryServer() throws RemoteException {
        super();
    }

    public synchronized void addBook(String author, String title, String date, String content) {
        books.add(new Book(nextId++, author, title, date, content));
        System.out.println(author + " dodal ksiazke " + title);
    }

    public synchronized boolean lockBook(int id, String writer) {
        for (Book b : books) {
            if (b.id == id && b.author.equals(writer)) {
                b.locked = true;
                System.out.println(writer + " zablokowal ksiazke " + id);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isReturned(int id) {
        for (Book b : books) {
            if (b.id == id) return b.readers.isEmpty();
        }
        return false;
    }

    public synchronized void destroyBook(int id, String writer) {
        if (books.removeIf(b -> b.id == id && b.author.equals(writer) && b.locked && b.readers.isEmpty())) {
            System.out.println(writer + " usunal ksiazke " + id);
        }
    }

    public synchronized void updateBook(int id, String content, String writer) {
        for (Book b : books) {
            if (b.id == id && b.author.equals(writer) && b.locked && b.readers.isEmpty()) {
                b.content = content;
                b.locked = false;
                System.out.println(writer + " zaktualizowal ksiazke " + id);
            }
        }
    }

    public synchronized Book findBook(String keyword) {
        for (Book b : books) {
            if (b.title.contains(keyword) || b.content.contains(keyword)) return b;
        }
        return null;
    }

    public synchronized boolean borrowBook(int id, String reader) {
        for (Book b : books) {
            if (b.id == id && !b.locked) {
                b.readers.add(reader);
                System.out.println(reader + " wypozyczyl ksiazke " + id);
                return true;
            }
        }
        return false;
    }

    public synchronized void returnBook(int id, String reader) {
        for (Book b : books) {
            if (b.id == id) {
                b.readers.remove(reader);
                System.out.println(reader + " zwrocil ksiazke " + id);
            }
        }
    }

    public synchronized String getReport() {
        StringBuilder sb = new StringBuilder("--- RAPORT ---\n");
        for (Book b : books) {
            sb.append("ID: ").append(b.id)
              .append(" | Tytul: ").append(b.title)
              .append(" | Blokada: ").append(b.locked)
              .append(" | Czytelnicy: ").append(b.readers).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Library", new LibraryServer());
            System.out.println("Serwer gotowy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
