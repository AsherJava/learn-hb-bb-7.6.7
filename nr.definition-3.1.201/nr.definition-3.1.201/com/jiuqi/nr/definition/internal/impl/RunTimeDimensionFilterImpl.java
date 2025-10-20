/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_DIMFILTER")
public class RunTimeDimensionFilterImpl
implements IDimensionFilter {
    @DBAnno.DBField(dbField="DF_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="DF_ENTITY_ID")
    private String entityId;
    @DBAnno.DBField(dbField="DF_FORM_SCHEME_KEY")
    private String formSchemeKey;
    @DBAnno.DBField(dbField="DF_LIST_TYPE", tranWith="transDimensionFilterListType", dbType=Integer.class, appType=DimensionFilterListType.class)
    private DimensionFilterListType listType;
    @DBAnno.DBField(dbField="DF_TYPE", tranWith="transDimensionFilterType", dbType=Integer.class, appType=DimensionFilterType.class)
    private DimensionFilterType type;
    @DBAnno.DBField(dbField="DF_VALUE", dbType=Clob.class)
    private String value;
    private List<String> list;

    public RunTimeDimensionFilterImpl() {
    }

    public RunTimeDimensionFilterImpl(String formSchemeKey, String entityId) {
        this.entityId = entityId;
        this.formSchemeKey = formSchemeKey;
        this.listType = DimensionFilterListType.BLACK_LIST;
        this.type = DimensionFilterType.LIST_SELECT;
    }

    public String getKey() {
        return this.key;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public String getTaskKey() {
        return null;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setListType(DimensionFilterListType listType) {
        this.listType = listType;
    }

    public void setType(DimensionFilterType type) {
        this.type = type;
    }

    public void setList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(";");
        }
        this.value = builder.toString();
        this.list = list;
    }

    public void setValue(String value) {
        this.value = value;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RunTimeDimensionFilterImpl that = (RunTimeDimensionFilterImpl)o;
        if (!this.entityId.equals(that.entityId)) {
            return false;
        }
        return this.formSchemeKey.equals(that.formSchemeKey);
    }

    public int hashCode() {
        int result = this.entityId.hashCode();
        result = 31 * result + this.formSchemeKey.hashCode();
        return result;
    }
}

