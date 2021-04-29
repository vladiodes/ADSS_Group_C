package Data.DAO;
import java.util.List;

public interface DAO<T> {
    boolean insert(T Ob);
    boolean update(T updatedOb, String colName);
    List<T> getAll();
    boolean delete(T ob);
}
