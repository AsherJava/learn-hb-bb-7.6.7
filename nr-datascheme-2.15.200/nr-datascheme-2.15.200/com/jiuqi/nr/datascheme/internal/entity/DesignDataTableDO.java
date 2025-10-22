/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import java.util.Arrays;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE_DES")
public class DesignDataTableDO
extends DataTableDO
implements DesignDataTable {
    private static final long serialVersionUID = 8510850485794139381L;

    public static DesignDataTableDO valueOf(DataTable o) {
        if (o == null) {
            return null;
        }
        DesignDataTableDO t = new DesignDataTableDO();
        DesignDataTableDO.copyProperties(o, t);
        return t;
    }

    @Override
    public DesignDataTableDO clone() {
        return (DesignDataTableDO)super.clone();
    }

    @Override
    public String toString() {
        return "DesignDataTableDO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", dataTableType=" + this.dataTableType + ", desc='" + this.desc + '\'' + ", bizKeys=" + Arrays.toString(this.bizKeys) + ", dataTableGatherType=" + this.dataTableGatherType + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", repeatCode=" + this.repeatCode + ", updateTime=" + this.updateTime + '}';
    }
}

