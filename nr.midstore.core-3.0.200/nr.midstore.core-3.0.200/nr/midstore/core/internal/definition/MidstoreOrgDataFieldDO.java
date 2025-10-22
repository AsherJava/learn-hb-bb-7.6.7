/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreOrgDataField;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_ORGDATA_FIELD")
public class MidstoreOrgDataFieldDO
extends MidstoreDataDO
implements IMidstoreOrgDataField {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDOF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDOF_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDOF_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDOF_SRC_ORGDATAKEY")
    protected String srcOrgDataKey;
    @DBAnno.DBField(dbField="MDOF_SRC_FIELDKEY")
    protected String srcFieldKey;
    @DBAnno.DBField(dbField="MDOF_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDOF_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDOF_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
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
    public MidstoreOrgDataFieldDO clone() {
        return (MidstoreOrgDataFieldDO)super.clone();
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
        MidstoreOrgDataFieldDO that = (MidstoreOrgDataFieldDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreOrgDataFieldDO valueOf(MidstoreOrgDataFieldDO o) {
        if (o == null) {
            return null;
        }
        MidstoreOrgDataFieldDO t = new MidstoreOrgDataFieldDO();
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
    public String getSrcOrgDataKey() {
        return this.srcOrgDataKey;
    }

    @Override
    public String getSrcFieldKey() {
        return this.srcFieldKey;
    }

    public void setSrcOrgDataKey(String srcOrgDataKey) {
        this.srcOrgDataKey = srcOrgDataKey;
    }

    public void setSrcFieldKey(String srcFieldKey) {
        this.srcFieldKey = srcFieldKey;
    }
}

