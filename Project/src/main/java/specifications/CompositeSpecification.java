package specifications;

public interface CompositeSpecification extends Specification
{
    void setOperation(Operation operation);
    enum Operation{
        AND,
        OR;
    }
}
