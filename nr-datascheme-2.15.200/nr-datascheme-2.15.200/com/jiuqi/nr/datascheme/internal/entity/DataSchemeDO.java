/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_SCHEME")
public class DataSchemeDO
implements DataScheme {
    private static final long serialVersionUID = 5278168941663683739L;
    @DBAnno.DBField(dbField="DS_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="DS_CODE")
    protected String code;
    @DBAnno.DBField(dbField="DS_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="DS_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="DS_DG_KEY")
    protected String dataGroupKey;
    @DBAnno.DBField(dbField="DS_PREFIX")
    protected String prefix;
    @DBAnno.DBField(dbField="DS_CREATOR")
    protected String creator;
    @DBAnno.DBField(dbField="DS_TYPE", tranWith="transDataSchemeType", dbType=Integer.class, appType=DataSchemeType.class)
    protected DataSchemeType type;
    @DBAnno.DBField(dbField="DS_IS_AUTO", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean auto;
    @DBAnno.DBField(dbField="DS_VERSION")
    protected String version;
    @DBAnno.DBField(dbField="DS_LEVEL")
    protected String level;
    @DBAnno.DBField(dbField="DS_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="DS_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="DS_BIZCODE")
    protected String bizCode;
    @DBAnno.DBField(dbField="DS_GATHER_DB", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean gatherDB;
    @DBAnno.DBField(dbField="DS_ENCRYPT_SCENE")
    protected String encryptScene;
    @DBAnno.DBField(dbField="DS_ZS_KEY")
    protected String zbSchemeKey;
    @DBAnno.DBField(dbField="DS_ZS_VERSION")
    protected String zbSchemeVersion;
    protected String calibre;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getAuto() {
        return this.auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
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

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public DataSchemeType getType() {
        return this.type;
    }

    public void setType(DataSchemeType type) {
        this.type = type;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Boolean getGatherDB() {
        if (Objects.isNull(this.gatherDB)) {
            this.gatherDB = Boolean.FALSE;
        }
        return this.gatherDB;
    }

    public void setGatherDB(Boolean gatherDB) {
        this.gatherDB = Objects.isNull(gatherDB) ? Boolean.FALSE : gatherDB;
    }

    public String getEncryptScene() {
        return this.encryptScene;
    }

    public void setEncryptScene(String encryptScene) {
        this.encryptScene = encryptScene;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeVersion() {
        return this.zbSchemeVersion;
    }

    public void setZbSchemeVersion(String versionKey) {
        this.zbSchemeVersion = versionKey;
    }

    public String getCalibre() {
        return this.calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
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

    public DataSchemeDO clone() {
        try {
            return (DataSchemeDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "DataSchemeDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", prefix='" + this.prefix + '\'' + ", creator='" + this.creator + '\'' + ", type=" + this.type + ", auto=" + this.auto + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + ", bizCode='" + this.bizCode + '\'' + ", gatherDB=" + this.gatherDB + ", encryptScene='" + this.encryptScene + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataSchemeDO that = (DataSchemeDO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static DataSchemeDO valueOf(DataScheme o) {
        if (o == null) {
            return null;
        }
        DataSchemeDO t = new DataSchemeDO();
        DataSchemeDO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataScheme o, DataSchemeDO t) {
        t.setKey(o.getKey());
        t.setCode(o.getCode());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setTitle(o.getTitle());
        t.setUpdateTime(o.getUpdateTime());
        t.setAuto(o.getAuto());
        t.setDataGroupKey(o.getDataGroupKey());
        t.setDesc(o.getDesc());
        t.setPrefix(o.getPrefix());
        t.setVersion(o.getVersion());
        t.setCreator(o.getCreator());
        t.setType(o.getType());
        t.setBizCode(o.getBizCode());
        t.setGatherDB(o.getGatherDB());
        t.setEncryptScene(o.getEncryptScene());
        t.setZbSchemeKey(o.getZbSchemeKey());
        t.setZbSchemeVersion(o.getZbSchemeVersion());
        t.setCalibre(o.getCalibre());
    }
}

