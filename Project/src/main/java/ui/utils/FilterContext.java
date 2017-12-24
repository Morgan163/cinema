package ui.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niict on 24.12.2017.
 */
public class FilterContext {
    public final static int FILM_TYPE_PARAMETER = 1;
    public final static int TIME_RANGE_PARAMETER = 2;
    public final static int AGE_LIMIT_PARAMETER = 3;
    private Map<Integer, Object> filterSettings;

    public FilterContext() {
        this.filterSettings = new HashMap<>();
    }

    public void setFilterParameter(Integer key, Object value){
        filterSettings.put(key, value);
    }

    public Object getFilterParameter(Integer key){
        return filterSettings.get(key);
    }
}
