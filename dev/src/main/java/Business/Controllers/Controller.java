package Business.Controllers;
import java.util.List;

public interface Controller<T> {
    List<T> Load();
}
