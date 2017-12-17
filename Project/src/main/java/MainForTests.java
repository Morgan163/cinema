import model.Theater;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import repository.impl.TheaterRepositoryImpl;
import specifications.factory.impl.SQLSpecificationFactory;
import specifications.sql.SqlSpecification;

import java.util.List;

/**
 * Created by niict on 16.12.2017.
 */
public class MainForTests {
    public static void main(String[] args) throws ClassNotFoundException {
        testUpdateTheater();
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
