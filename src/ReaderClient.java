import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ReaderClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ILibrary lib = (ILibrary) registry.lookup("Library");
            String name = "Czytelnik_" + new Random().nextInt(100);

            while (true) {
                Book b = lib.findBook("Tytul");
                if (b != null) {
                    if (lib.borrowBook(b.id, name)) {
                        Thread.sleep(5000);
                        lib.returnBook(b.id, name);
                    }
                }
                
                System.out.println(lib.getReport());
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
