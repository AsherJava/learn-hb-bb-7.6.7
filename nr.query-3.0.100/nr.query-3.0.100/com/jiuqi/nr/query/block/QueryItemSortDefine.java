/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.block.QueryFilterDefine;
import com.jiuqi.nr.query.block.QuerySortType;
import com.jiuqi.nr.query.deserializer.QueryItemSortDefineDeserializer;
import com.jiuqi.nr.query.serializer.QueryItemSortDefineSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=QueryItemSortDefineSerializer.class)
@JsonDeserialize(using=QueryItemSortDefineDeserializer.class)
public class QueryItemSortDefine {
    private static final Logger log = LoggerFactory.getLogger(QueryItemSortDefine.class);
    public static final String SORT_TYPE = "sorttype";
    public static final String SORT_FILTER = "filters";
    public static final String SORT_DATA = "data";
    public static final String SORT_FR = "relation";
    public static final String SORT_FILTERVALUES = "filtervalues";
    private QuerySortType sortType;
    private List<QueryFilterDefine> filterConditions;
    private String filterRelation;
    private Map<String, String> data;
    private ArrayList<Object> filtervalues;

    public QuerySortType getSortType() {
        return this.sortType;
    }

    public void setSortType(QuerySortType type) {
        this.sortType = type;
    }

    public List<QueryFilterDefine> getFilterCondition() {
        return this.filterConditions;
    }

    public void setFilterCondition(List<QueryFilterDefine> filterConditions) {
        this.filterConditions = filterConditions;
    }

    public void setFilterConditionStr(String filterConditions) {
        try {
            if (StringUtil.isNullOrEmpty((String)filterConditions) || "{}".equals(filterConditions)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryFilterDefine.class});
            this.setFilterCondition((List)mapper.readValue(filterConditions, javaType));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setFilterRelation(String r) {
        this.filterRelation = r;
    }

    public String getFilterRelation() {
        return this.filterRelation;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void setDataStr(String data) {
        try {
            if (StringUtil.isNullOrEmpty((String)data) || "{}".equals(data)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            this.setData((Map)mapper.readValue(data, Map.class));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setFilterValues(ArrayList<Object> filtervalues) {
        this.filtervalues = filtervalues;
    }

    public ArrayList<Object> getFilterValues() {
        return this.filtervalues;
    }

    public void setFilterValues(String data) {
        try {
            if (StringUtil.isNullOrEmpty((String)data) || "{}".equals(data)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            this.setFilterValues((ArrayList)mapper.readValue(data, ArrayList.class));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

