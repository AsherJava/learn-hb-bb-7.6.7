/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreField;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_FIELD")
public class MidstoreFieldDO
extends MidstoreDataDO
implements IMidstoreField {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDF_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDF_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDF_SRC_TABLEKEY")
    protected String srcTableKey;
    @DBAnno.DBField(dbField="MDF_SRC_FIELDKEY")
    protected String srcFieldKey;
    @DBAnno.DBField(dbField="MDF_REMARK")
    protected String remark;
    @DBAnno.DBField(dbField="MDF_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDF_ENCRYPTED", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean encrypted;
    @DBAnno.DBField(dbField="MDF_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDF_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;

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
    public MidstoreFieldDO clone() {
        return (MidstoreFieldDO)super.clone();
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
        MidstoreFieldDO that = (MidstoreFieldDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreFieldDO valueOf(MidstoreFieldDO o) {
        if (o == null) {
            return null;
        }
        MidstoreFieldDO t = new MidstoreFieldDO();
        BeanUtils.copyProperties(o, t);
        return t;
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

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
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
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public String getSrcFieldKey() {
        return this.srcFieldKey;
    }

    public void setSrcFieldKey(String srcFieldKey) {
        this.srcFieldKey = srcFieldKey;
    }

    @Override
    public String getSrcTableKey() {
        return this.srcTableKey;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    public void setSrcTableKey(String srcTableKey) {
        this.srcTableKey = srcTableKey;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean isEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}

