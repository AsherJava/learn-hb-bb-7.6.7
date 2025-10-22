/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_SCHEME_DES")
public class DesignDataSchemeDO
extends DataSchemeDO
implements DesignDataScheme {
    private static final long serialVersionUID = -682743943422933447L;

    @Override
    public DesignDataSchemeDO clone() {
        return (DesignDataSchemeDO)super.clone();
    }

    @Override
    public String toString() {
        return "DesignDataSchemeDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", prefix='" + this.prefix + '\'' + ", creator='" + this.creator + '\'' + ", type=" + this.type + ", auto=" + this.auto + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + ", bizCode='" + this.bizCode + '\'' + ", gatherDB=" + this.gatherDB + ", encryptScene='" + this.encryptScene + '\'' + '}';
    }

    public static DesignDataSchemeDO valueOf(DesignDataScheme o) {
        if (o == null) {
            return null;
        }
        DesignDataSchemeDO t = new DesignDataSchemeDO();
        DesignDataSchemeDO.copyProperties((DataScheme)o, t);
        return t;
    }
}

