package specifications.sql;

import java.util.List;

/**
 * Created by niict on 10.12.2017.
 */
public class TableNamesResolver {
    private static TableNamesResolver tableNamesResolver = new TableNamesResolver();

    private TableNamesResolver(){

    }

    public static TableNamesResolver getInstance() {
        return tableNamesResolver;
    }
    public String resolveNamesForSqlQuery(SqlSpecification sqlSpecification){
        StringBuilder tableNames = new StringBuilder();
        List sourceTables = sqlSpecification.getSourceTables();
        for (int i = 0; i < sourceTables.size() - 1; i++){
            tableNames.append(sourceTables.get(i))
                    .append(", ");
        }
        tableNames.append(sourceTables.get(sourceTables.size()-1));
        return tableNames.toString();
    }
}
