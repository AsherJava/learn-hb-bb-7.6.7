/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreOrgData;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_ORGDATA")
public class MidstoreOrgDataDO
extends MidstoreDataDO
implements IMidstoreOrgData {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDO_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDO_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDO_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDO_PARENTCODE")
    protected String parentCode;
    @DBAnno.DBField(dbField="MDO_ORGCODE")
    protected String orgCode;
    @DBAnno.DBField(dbField="MDO_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDO_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDO_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
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
    public MidstoreOrgDataDO clone() {
        return (MidstoreOrgDataDO)super.clone();
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
        MidstoreOrgDataDO that = (MidstoreOrgDataDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreOrgDataDO valueOf(MidstoreOrgDataDO o) {
        if (o == null) {
            return null;
        }
        MidstoreOrgDataDO t = new MidstoreOrgDataDO();
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

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public String getOrgCode() {
        return this.orgCode;
    }

    @Override
    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

