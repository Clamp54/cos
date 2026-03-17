import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILibrary extends Remote {
    void addBook(String author, String title, String date, String content) throws RemoteException;
    boolean lockBook(int id, String writer) throws RemoteException;
    boolean isReturned(int id) throws RemoteException;
    void destroyBook(int id, String writer) throws RemoteException;
    void updateBook(int id, String content, String writer) throws RemoteException;
    Book findBook(String keyword) throws RemoteException;
    boolean borrowBook(int id, String reader) throws RemoteException;
    void returnBook(int id, String reader) throws RemoteException;
    String getReport() throws RemoteException;
}