import model.Theater;
import model.user.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import repository.impl.TheaterRepositoryImpl;
import specifications.CompositeSpecification;
import specifications.factory.SpecificationFactory;
import specifications.factory.impl.SQLSpecificationFactory;
import specifications.sql.SqlSpecification;

import java.util.List;

/**
 * Created by niict on 16.12.2017.
 */
public class MainForTests {
    public static void main(String[] args) throws ClassNotFoundException {
//        testUpdateTheater();
        SpecificationFactory specificationFactory = new SQLSpecificationFactory();
        User user = new User(1, "login", "pass", null);
        SqlSpecification loginSpecification = (SqlSpecification)specificationFactory.getUserByLoginSpecification(user.getLogin());
        SqlSpecification passwordSpecification = (SqlSpecification)specificationFactory.getUserByPasswordSpecification(user.getPassword());
        CompositeSpecification userSpecification = specificationFactory.getCompositeSpecification(loginSpecification, passwordSpecification);
        userSpecification.setOperation(CompositeSpecification.Operation.AND);
        SqlSpecification roleSpecification = (SqlSpecification)specificationFactory.getRoleIdEqualsUserRoleIdSpecification();
        CompositeSpecification resultSpecification = specificationFactory.getCompositeSpecification(roleSpecification, userSpecification);
        resultSpecification.setOperation(CompositeSpecification.Operation.AND);
        System.out.println(((SqlSpecification)resultSpecification).toSqlClause());
    }

    private static void testSelectTheater() throws ClassNotFoundException {
        Class.forName("javax.enterprise.inject.se.SeContainerInitializer");
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();

        TheaterRepositoryImpl theaterRepository = weldContainer.instance().select(TheaterRepositoryImpl.class).get();
        SQLSpecificationFactory factory = new SQLSpecificationFactory();
        SqlSpecification sqlSpecification = factory.getTheaterByIdSpecification(1);
        List<Theater> theaters = theaterRepository.query(sqlSpecification);
        for (Theater theater: theaters){
            System.out.println(theater.getTheaterID());
            System.out.println(theater.getTheaterNumber());
        }
    }

    private static void testInsertTheater() throws ClassNotFoundException {
        Class.forName("javax.enterprise.inject.se.SeContainerInitializer");
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();

        TheaterRepositoryImpl theaterRepository = weldContainer.instance().select(TheaterRepositoryImpl.class).get();
        Theater theater = new Theater(1000);
        theaterRepository.add(theater);
    }

    private static void testUpdateTheater() throws ClassNotFoundException {
        Class.forName("javax.enterprise.inject.se.SeContainerInitializer");
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();

        TheaterRepositoryImpl theaterRepository = weldContainer.instance().select(TheaterRepositoryImpl.class).get();
        Theater theater = new Theater(1,1000);
        theaterRepository.update(theater);
    }
}
