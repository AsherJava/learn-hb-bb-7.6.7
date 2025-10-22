/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.dataresource.entity;

import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@DBAnno.DBTable(dbTable="NR_DATARESOURCE_DIMATTR")
public class DimAttributeDO
implements Ordered {
    @DBAnno.DBField(dbField="DIM_KEY", notUpdate=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{dimKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{dimKey.notBlank}") String dimKey;
    @DBAnno.DBField(dbField="DM_KEY", isPk=true, notUpdate=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{key.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{key.notBlank}") String key;
    @DBAnno.DBField(dbField="DD_KEY", isPk=true, notUpdate=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{resourceDefineKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{resourceDefineKey.notBlank}") String resourceDefineKey;
    @DBAnno.DBField(dbField="DM_HIDDEN", tranWith="transBoolean", dbType=Integer.class, appType=boolean.class)
    private boolean hidden;
    @DBAnno.DBField(dbField="DM_ORDER", isOrder=true)
    @Size(max=10, message="{text.size}")
    @NotBlank(message="{order.notBlank}")
    private @Size(max=10, message="{text.size}") @NotBlank(message="{order.notBlank}") String order;

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    public void setResourceDefineKey(String resourceDefineKey) {
        this.resourceDefineKey = resourceDefineKey;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String toString() {
        return "DimAttributeDO{, dimKey='" + this.dimKey + '\'' + ", key='" + this.key + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", hidden=" + this.hidden + ", order='" + this.order + '\'' + '}';
    }

    public DimAttributeDO clone() {
        try {
            return (DimAttributeDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DimAttributeDO that = (DimAttributeDO)o;
        if (!Objects.equals(this.key, that.key)) {
            return false;
        }
        return Objects.equals(this.resourceDefineKey, that.resourceDefineKey);
    }

    public int hashCode() {
        int result = this.key != null ? this.key.hashCode() : 0;
        result = 31 * result + (this.resourceDefineKey != null ? this.resourceDefineKey.hashCode() : 0);
        return result;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return -1;
        }
        if (this.order == null) {
            return 1;
        }
        return this.order.compareTo(o.getOrder());
    }
}

