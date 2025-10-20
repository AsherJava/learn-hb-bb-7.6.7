/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.internal.entity.DataDimDO
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import java.io.Serializable;

public class DesignDataDimVO
extends DataDimDO
implements DesignDataDimension,
Serializable {
    private static final long serialVersionUID = 4356053136152285016L;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static DesignDataDimVO valueOf(DesignDataDimension dim) {
        if (dim == null) {
            return null;
        }
        if (dim instanceof DesignDataDimVO) {
            return (DesignDataDimVO)dim;
        }
        DesignDataDimVO t = new DesignDataDimVO();
        t.setDataSchemeKey(dim.getDataSchemeKey());
        t.setDimensionType(dim.getDimensionType());
        t.setDimKey(dim.getDimKey());
        t.setPeriodType(dim.getPeriodType());
        t.setVersion(dim.getVersion());
        t.setLevel(dim.getLevel());
        t.setOrder(dim.getOrder());
        t.setUpdateTime(dim.getUpdateTime());
        return t;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }
}

