/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.definition.db.DBAnno;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_DATA")
public class MidstoreDataDO
implements IMidstoreData {
    private static final long serialVersionUID = 1L;
    protected String key;
    protected String order;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public MidstoreDataDO clone() {
        try {
            return (MidstoreDataDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "MidstoreDataDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreDataDO that = (MidstoreDataDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreDataDO valueOf(MidstoreDataDO o) {
        if (o == null) {
            return null;
        }
        MidstoreDataDO t = new MidstoreDataDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

