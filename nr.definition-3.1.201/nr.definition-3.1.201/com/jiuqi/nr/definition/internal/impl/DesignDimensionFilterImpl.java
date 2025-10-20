/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_DIMFILTER_DES")
public class DesignDimensionFilterImpl
implements DesignDimensionFilter {
    @DBAnno.DBField(dbField="DF_KEY", isPk=true, notUpdate=true)
    private String key;
    @DBAnno.DBField(dbField="DF_ENTITY_ID")
    private String entityId;
    @DBAnno.DBField(dbField="DF_TASK_KEY")
    private String taskKey;
    @DBAnno.DBField(dbField="DF_LIST_TYPE", tranWith="transDimensionFilterListType", dbType=Integer.class, appType=DimensionFilterListType.class)
    private DimensionFilterListType listType;
    @DBAnno.DBField(dbField="DF_TYPE", tranWith="transDimensionFilterType", dbType=Integer.class, appType=DimensionFilterType.class)
    private DimensionFilterType type;
    @DBAnno.DBField(dbField="DF_VALUE", dbType=Clob.class)
    private String value;
    private List<String> list;

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public void setType(DimensionFilterType type) {
        this.type = type;
    }

    @Override
    public void setListType(DimensionFilterListType listType) {
        this.listType = listType;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list == null) {
            return;
        }
        for (String s : list) {
            builder.append(s).append(";");
        }
        this.value = builder.toString();
        this.list = list;
    }

    public String getKey() {
        return this.key;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return null;
    }

    public DimensionFilterType getType() {
        return this.type;
    }

    public DimensionFilterListType getListType() {
        return this.listType;
    }

    public List<String> getList() {
        if (StringUtils.hasLength(this.value)) {
            this.list = Arrays.stream(this.value.split(";")).filter(StringUtils::hasLength).collect(Collectors.toList());
        }
        return this.list;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DesignDimensionFilter that = (DesignDimensionFilter)o;
        if (!this.entityId.equals(that.getEntityId())) {
            return false;
        }
        return this.taskKey.equals(that.getTaskKey());
    }

    public int hashCode() {
        int result = this.entityId.hashCode();
        result = 31 * result + this.taskKey.hashCode();
        return result;
    }

    public Date getUpdateTime() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public DesignDimensionFilterImpl() {
    }

    public DesignDimensionFilterImpl(String entityId, String taskKey) {
        this.taskKey = taskKey;
        this.entityId = entityId;
        this.listType = DimensionFilterListType.BLACK_LIST;
        this.type = DimensionFilterType.LIST_SELECT;
    }
}

