import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class WriterClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ILibrary lib = (ILibrary) registry.lookup("Library");
            String name = "Pisarz_" + new Random().nextInt(100);
            int bookId = 1;

            while (true) {
                lib.addBook(name, "Tytul_" + new Random().nextInt(1000), "2026", "Tresc");
                
                Thread.sleep(2000);
                
                if (lib.lockBook(bookId, name)) {
                    while (!lib.isReturned(bookId)) {
                        Thread.sleep(1000);
                    }
                    lib.updateBook(bookId, "Nowa Tresc", name);
                }
                
                System.out.println(lib.getReport());
                bookId++;
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
