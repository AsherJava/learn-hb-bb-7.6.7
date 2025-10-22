/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreScheme;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.StorageModeType;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_SCHEME")
public class MidstoreSchemeDO
extends MidstoreDataDO
implements IMidstoreScheme {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDS_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDS_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDS_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDS_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="MDS_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDS_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="MDS_EXCHANGEMODE", tranWith="transExchangeModeType", dbType=Integer.class, appType=ExchangeModeType.class)
    protected ExchangeModeType exchangeMode;
    @DBAnno.DBField(dbField="MDS_STORAGEMODE", tranWith="transStorageModeType", dbType=Integer.class, appType=StorageModeType.class)
    protected StorageModeType storageMode;
    @DBAnno.DBField(dbField="MDS_STORAGEINFO")
    protected String storageInfo;
    @DBAnno.DBField(dbField="MDS_DATABASELINK")
    protected String dataBaseLink;
    @DBAnno.DBField(dbField="MDS_TABLEPREFIX")
    protected String tablePrefix;
    @DBAnno.DBField(dbField="MDS_TASKKEY")
    protected String taskKey;
    @DBAnno.DBField(dbField="MDS_GROUPKEY")
    protected String groupKey;
    @DBAnno.DBField(dbField="MDS_CONFIGKEY")
    protected String configKey;
    @DBAnno.DBField(dbField="MDS_DIMENSIONS")
    protected String dimensions;
    @DBAnno.DBField(dbField="MDS_SCENECODE")
    protected String sceneCode;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public MidstoreSchemeDO clone() {
        return (MidstoreSchemeDO)super.clone();
    }

    @Override
    public String toString() {
        return "MidstoreSchemeDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreSchemeDO that = (MidstoreSchemeDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreSchemeDO valueOf(MidstoreSchemeDO o) {
        if (o == null) {
            return null;
        }
        MidstoreSchemeDO t = new MidstoreSchemeDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public ExchangeModeType getExchangeMode() {
        return this.exchangeMode;
    }

    @Override
    public StorageModeType getStorageMode() {
        return this.storageMode;
    }

    @Override
    public String getStorageInfo() {
        return this.storageInfo;
    }

    @Override
    public String getDataBaseLink() {
        return this.dataBaseLink;
    }

    @Override
    public String getTablePrefix() {
        return this.tablePrefix;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getConfigKey() {
        return this.configKey;
    }

    @Override
    public String getDimensions() {
        return this.dimensions;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public void setExchangeMode(ExchangeModeType exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public void setStorageMode(StorageModeType storageMode) {
        this.storageMode = storageMode;
    }

    public void setStorageInfo(String storageInfo) {
        this.storageInfo = storageInfo;
    }

    public void setDataBaseLink(String dataBaseLink) {
        this.dataBaseLink = dataBaseLink;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public String getSceneCode() {
        return this.sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }
}

