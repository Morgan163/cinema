package repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by niict on 16.12.2017.
 */
public class ObjectColumnValues {
    private Map<String, String> params;
    private String idColumnName;
    private String objectId;

    public ObjectColumnValues(){
        params = new HashMap<String, String>();
    }

    public Set<String> getColumnNamesSet(){
        return params.keySet();
    }

    public void setValueByColumnName(String columnName, String value){
        params.put(columnName, value);
    }

    public String getValueByColumnName(String columnName){
        return params.get(columnName);
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
