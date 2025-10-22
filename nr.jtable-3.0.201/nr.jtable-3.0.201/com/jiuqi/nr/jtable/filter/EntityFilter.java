/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.internal.Filter
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 */
package com.jiuqi.nr.jtable.filter;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.internal.Filter;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityFilter
implements Filter<IEntityRow> {
    private static final Logger logger = LoggerFactory.getLogger(EntityFilter.class);
    private String search;
    private Set<String> searchList;
    private List<String> fields = new ArrayList<String>();
    private boolean matchAll = false;
    private boolean useDefaultSearch = true;

    public EntityFilter(String search, boolean fullPath, List<String> fields) {
        this.search = search;
        String[] splits = search.split("\\|");
        if (null != splits) {
            this.searchList = new HashSet<String>(Arrays.asList(splits));
            if (fullPath) {
                for (String oneSearch : this.searchList) {
                    String[] oneSearchList = oneSearch.split("/");
                    this.searchList.add(oneSearchList[oneSearchList.length - 1]);
                }
            }
        }
        if (fields != null && !fields.isEmpty()) {
            this.fields = fields;
        }
    }

    public boolean accept(IEntityRow entityRow) {
        if (this.useDefaultSearch) {
            if (this.filterFieldValue(entityRow.getCode())) {
                return true;
            }
            if (this.filterFieldValue(entityRow.getTitle())) {
                return true;
            }
        }
        IFieldsInfo fieldsInfo = entityRow.getFieldsInfo();
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            AbstractData value;
            String fieldValue;
            String fieldCode = fieldsInfo.getFieldByIndex(i).getCode();
            if (!this.fields.contains(fieldCode) || !this.filterFieldValue(fieldValue = (value = entityRow.getValue(fieldCode)).getAsString())) continue;
            return true;
        }
        return false;
    }

    private boolean filterFieldValue(String fieldValue) {
        if (StringUtils.isNotEmpty((String)fieldValue)) {
            if (this.matchAll) {
                if (fieldValue.equalsIgnoreCase(this.search)) {
                    return true;
                }
            } else {
                if (fieldValue.toUpperCase().contains(this.search.toUpperCase())) {
                    return true;
                }
                if (this.searchList != null && this.searchList.size() > 0) {
                    for (String searchStr : this.searchList) {
                        if (!fieldValue.toUpperCase().contains(searchStr.trim().toUpperCase())) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    public boolean isUseDefaultSearch() {
        return this.useDefaultSearch;
    }

    public void setUseDefaultSearch(boolean useDefaultSearch) {
        this.useDefaultSearch = useDefaultSearch;
    }
}

