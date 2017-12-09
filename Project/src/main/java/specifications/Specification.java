package specifications;

/**
 * Created by niict on 09.12.2017.
 */
public interface Specification<T> {
    boolean specified(T source);
}
