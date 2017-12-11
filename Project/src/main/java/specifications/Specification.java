package specifications;

public interface Specification<T> {
    boolean specified(T source);
}
