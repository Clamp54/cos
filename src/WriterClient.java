import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WriterClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ILibrary lib = (ILibrary) registry.lookup("Library");
            String name = "Pisarz1";

            lib.addBook(name, "Tytul1", "2026", "Tresc");
            lib.lockBook(1, name);
            if (lib.isReturned(1)) {
                lib.updateBook(1, "Nowa Tresc", name);
            }
            System.out.println(lib.getReport());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}