package Business.Objects;

public interface persistentObject {
    boolean insert();
    boolean update();
    boolean delete();
}
