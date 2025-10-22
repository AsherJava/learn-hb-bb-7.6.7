/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE")
public class DataTableDO
implements DataTable {
    private static final long serialVersionUID = -8107780909976241121L;
    @DBAnno.DBField(dbField="DT_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="DT_DS_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="DT_DG_KEY")
    protected String dataGroupKey;
    @DBAnno.DBField(dbField="DT_CODE")
    protected String code;
    @DBAnno.DBField(dbField="DT_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="DT_TYPE", tranWith="transDataTableType", dbType=Integer.class, appType=DataTableType.class)
    protected DataTableType dataTableType;
    @DBAnno.DBField(dbField="DT_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="DT_BIZKEYS", tranWith="transBizKeys", dbType=String.class, appType=String[].class)
    protected String[] bizKeys;
    @DBAnno.DBField(dbField="DT_GATHERFIELDKEYS", tranWith="transGatherFieldKeys", dbType=String.class, appType=String[].class)
    protected String[] gatherFieldKeys;
    @DBAnno.DBField(dbField="DT_GATHER_TYPE", tranWith="transDataTableGatherType", dbType=Integer.class, appType=DataTableGatherType.class)
    protected DataTableGatherType dataTableGatherType;
    @DBAnno.DBField(dbField="DT_VERSION")
    protected String version;
    @DBAnno.DBField(dbField="DT_LEVEL")
    protected String level;
    @DBAnno.DBField(dbField="DT_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="DT_REPEAT_CODE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean repeatCode;
    @DBAnno.DBField(dbField="DT_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="DT_OWNER")
    protected String owner;
    @DBAnno.DBField(dbField="DT_TRACK_HISTORY", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean trackHistory;
    @DBAnno.DBField(dbField="DT_SYNC_ERROR", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean syncError = false;
    @DBAnno.DBField(dbField="DT_EXPRESSION")
    protected String expression;
    @DBAnno.DBField(dbField="DT_ALIAS")
    protected String alias;

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

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
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

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataTableGatherType getDataTableGatherType() {
        return this.dataTableGatherType;
    }

    public void setDataTableGatherType(DataTableGatherType dataTableGatherType) {
        this.dataTableGatherType = dataTableGatherType;
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

    public Boolean getRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(Boolean repeatCode) {
        this.repeatCode = repeatCode;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String[] getBizKeys() {
        return this.bizKeys;
    }

    public void setBizKeys(String[] bizKeys) {
        this.bizKeys = bizKeys;
    }

    public Boolean getTrackHistory() {
        return this.trackHistory;
    }

    public void setTrackHistory(Boolean trackHistory) {
        this.trackHistory = trackHistory;
    }

    public String[] getGatherFieldKeys() {
        return this.gatherFieldKeys;
    }

    public void setGatherFieldKeys(String[] gatherFieldKeys) {
        this.gatherFieldKeys = gatherFieldKeys;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public DataTableDO clone() {
        try {
            return (DataTableDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String getOwner() {
        if (!StringUtils.hasText(this.owner)) {
            this.owner = "NR";
        }
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getSyncError() {
        return this.syncError;
    }

    public void setSyncError(Boolean syncError) {
        this.syncError = syncError;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String toString() {
        return "DataTableDO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", dataTableType=" + this.dataTableType + ", desc='" + this.desc + '\'' + ", bizKeys=" + Arrays.toString(this.bizKeys) + ", dataTableGatherType=" + this.dataTableGatherType + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", repeatCode=" + this.repeatCode + ", trackHistory=" + this.trackHistory + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataTableDO that = (DataTableDO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static DataTableDO valueOf(DataTable o) {
        if (o == null) {
            return null;
        }
        DataTableDO t = new DataTableDO();
        DataTableDO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataTable o, DataTableDO t) {
        t.setKey(o.getKey());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setDataGroupKey(o.getDataGroupKey());
        t.setCode(o.getCode());
        t.setTitle(o.getTitle());
        t.setDataTableType(o.getDataTableType());
        t.setDesc(o.getDesc());
        t.setBizKeys(o.getBizKeys());
        t.setGatherFieldKeys(o.getGatherFieldKeys());
        t.setDataTableGatherType(o.getDataTableGatherType());
        t.setVersion(o.getVersion());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setRepeatCode(o.getRepeatCode());
        t.setUpdateTime(o.getUpdateTime());
        t.setOwner(o.getOwner());
        t.setTrackHistory(o.getTrackHistory());
        t.setSyncError(o.getSyncError());
        t.setExpression(o.getExpression());
        t.setAlias(o.getAlias());
    }
}

