/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE_REL_DES")
public class DesignDataTableRelDO
extends DataTableRelDO
implements DesignDataTableRel {
    private static final long serialVersionUID = -2593092508099690080L;

    public static DesignDataTableRelDO valueOf(DataTableRel o) {
        if (null == o) {
            return null;
        }
        DesignDataTableRelDO t = new DesignDataTableRelDO();
        DesignDataTableRelDO.copyProperties(o, t);
        return t;
    }
}

