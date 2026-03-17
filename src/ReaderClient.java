import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ReaderClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ILibrary lib = (ILibrary) registry.lookup("Library");
            String name = "Czytelnik1";

            Book b = lib.findBook("Tytul1");
            if (b != null) {
                lib.borrowBook(b.id, name);
                lib.returnBook(b.id, name);
            }
            System.out.println(lib.getReport());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}