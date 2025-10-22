/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreBaseData;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_BASEDATA")
public class MidstoreBaseDataDO
extends MidstoreDataDO
implements IMidstoreBaseData {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDB_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDB_CODE")
    protected String code;
    @DBAnno.DBField(dbField="MDB_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDB_SRC_BASEDATAKEY")
    protected String srcBaseDataKey;
    @DBAnno.DBField(dbField="MDB_REMARK")
    protected String remark;
    @DBAnno.DBField(dbField="MDB_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDB_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDB_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
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
    public MidstoreBaseDataDO clone() {
        return (MidstoreBaseDataDO)super.clone();
    }

    @Override
    public String toString() {
        return "MidstoreBaseDataDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreBaseDataDO that = (MidstoreBaseDataDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreBaseDataDO valueOf(MidstoreBaseDataDO o) {
        if (o == null) {
            return null;
        }
        MidstoreBaseDataDO t = new MidstoreBaseDataDO();
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

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    @Override
    public String getSrcBaseDataKey() {
        return this.srcBaseDataKey;
    }

    public void setSrcBaseDataKey(String srcBaseDataKey) {
        this.srcBaseDataKey = srcBaseDataKey;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

