/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_GROUP_DES")
public class DesignDataGroupDO
extends DataGroupDO
implements DesignDataGroup {
    private static final long serialVersionUID = 6375890835775300235L;

    public static DesignDataGroupDO valueOf(DesignDataGroup o) {
        if (o == null) {
            return null;
        }
        DesignDataGroupDO t = new DesignDataGroupDO();
        DesignDataGroupDO.copyProperties((DataGroup)o, t);
        return t;
    }

    @Override
    public DesignDataGroupDO clone() {
        return (DesignDataGroupDO)super.clone();
    }

    @Override
    public String toString() {
        return "DesignDataGroupDO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKind=" + this.dataGroupKind + ", parentKey='" + this.parentKey + '\'' + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }
}

