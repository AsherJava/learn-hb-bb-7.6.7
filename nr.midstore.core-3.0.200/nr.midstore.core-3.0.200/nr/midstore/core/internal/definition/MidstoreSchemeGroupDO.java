/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreSchemeGroup;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_SCHEME_GROUP")
public class MidstoreSchemeGroupDO
extends MidstoreDataDO
implements IMidstoreSchemeGroup {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDG_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDG_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="MDG_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="MDG_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDG_PARENTKEY")
    protected String parent;
    @DBAnno.DBField(dbField="MDG_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
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
    public MidstoreSchemeGroupDO clone() {
        return (MidstoreSchemeGroupDO)super.clone();
    }

    @Override
    public String toString() {
        return "MidstoreSchemeGroupDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreSchemeGroupDO that = (MidstoreSchemeGroupDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreSchemeGroupDO valueOf(MidstoreSchemeGroupDO o) {
        if (o == null) {
            return null;
        }
        MidstoreSchemeGroupDO t = new MidstoreSchemeGroupDO();
        BeanUtils.copyProperties(o, t);
        return t;
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
    public void setKey(String key) {
        this.key = key;
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

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

