/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_DIM_DES")
public class DesignDataDimDO
extends DataDimDO
implements DesignDataDimension {
    private static final long serialVersionUID = 4356053136152285016L;

    public static DesignDataDimDO valueOf(DesignDataDimension dim) {
        if (dim == null) {
            return null;
        }
        DesignDataDimDO t = new DesignDataDimDO();
        DesignDataDimDO.copyProperties((DataDimension)dim, t);
        return t;
    }

    @Override
    public DesignDataDimDO clone() {
        return (DesignDataDimDO)super.clone();
    }

    @Override
    public String toString() {
        return "DesignDataDimDO{dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dimensionType=" + this.dimensionType + ", dimKey='" + this.dimKey + '\'' + ", periodType=" + this.periodType + ", periodPattern=" + this.periodPattern + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

