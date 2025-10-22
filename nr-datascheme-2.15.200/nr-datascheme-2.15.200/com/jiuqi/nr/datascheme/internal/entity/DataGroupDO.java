/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_GROUP")
public class DataGroupDO
implements DataGroup {
    private static final long serialVersionUID = -7209398129059354000L;
    @DBAnno.DBField(dbField="DG_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="DG_DS_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="DG_CODE")
    protected String code;
    @DBAnno.DBField(dbField="DG_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="DG_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="DG_KIND", tranWith="transDataGroupKind", appType=DataGroupKind.class, dbType=Integer.class)
    protected DataGroupKind dataGroupKind;
    @DBAnno.DBField(dbField="DG_PARENT_KEY")
    protected String parentKey;
    @DBAnno.DBField(dbField="DG_VERSION")
    protected String version;
    @DBAnno.DBField(dbField="DG_LEVEL")
    protected String level;
    @DBAnno.DBField(dbField="DG_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="DG_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    protected Instant updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public DataGroupKind getDataGroupKind() {
        return this.dataGroupKind;
    }

    public void setDataGroupKind(DataGroupKind dataGroupKind) {
        this.dataGroupKind = dataGroupKind;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public DataGroupDO clone() {
        try {
            return (DataGroupDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "DataGroupDO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKind=" + this.dataGroupKind + ", parentKey='" + this.parentKey + '\'' + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataGroupDO that = (DataGroupDO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static DataGroupDO valueOf(DataGroup o) {
        if (o == null) {
            return null;
        }
        DataGroupDO t = new DataGroupDO();
        DataGroupDO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataGroup o, DataGroupDO t) {
        t.setKey(o.getKey());
        t.setCode(o.getCode());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setTitle(o.getTitle());
        t.setUpdateTime(o.getUpdateTime());
        t.setDesc(o.getDesc());
        t.setVersion(o.getVersion());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setDataGroupKind(o.getDataGroupKind());
        t.setParentKey(o.getParentKey());
    }
}

