package com.fitee.search.interfaces;

import java.util.Map;

public interface QueryMap {

    default Map<String, String> clean(Map<String, String> map) {
        // remove pageable entries that should not be queried
        map.remove("page");
        map.remove("size");
        map.remove("sort");
        return map;
    }
}
