/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreBaseDataField;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_BASEDATA_FIELD")
public class MidstoreBaseDataFieldDO
extends MidstoreDataDO
implements IMidstoreBaseDataField {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDBF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDBF_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDBF_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDBF_BASEDATAKEY")
    protected String baseDataKey;
    @DBAnno.DBField(dbField="MDBF_SRC_BASEDATAKEY")
    protected String srcBaseDataKey;
    @DBAnno.DBField(dbField="MDBF_SRC_FIELDKEY")
    protected String srcFieldKey;
    @DBAnno.DBField(dbField="MDBF_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDBF_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDBF_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
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
    public MidstoreBaseDataFieldDO clone() {
        return (MidstoreBaseDataFieldDO)super.clone();
    }

    @Override
    public String toString() {
        return "MidstoreBaseDataFieldDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreBaseDataFieldDO that = (MidstoreBaseDataFieldDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreBaseDataFieldDO valueOf(MidstoreBaseDataFieldDO o) {
        if (o == null) {
            return null;
        }
        MidstoreBaseDataFieldDO t = new MidstoreBaseDataFieldDO();
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
    public String getBaseDataKey() {
        return this.baseDataKey;
    }

    @Override
    public String getSrcBaseDataKey() {
        return this.srcBaseDataKey;
    }

    public void setSrcBaseDataKey(String srcBaseDataKey) {
        this.srcBaseDataKey = srcBaseDataKey;
    }

    public void setBaseDataKey(String baseDataKey) {
        this.baseDataKey = baseDataKey;
    }
}

