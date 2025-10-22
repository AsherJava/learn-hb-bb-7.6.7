/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_FIELD_DES")
public class DesignDataFieldDO
extends DataFieldDO
implements DesignDataField {
    private static final long serialVersionUID = -9143372268655185552L;

    public static DesignDataFieldDO valueOf(DataField o) {
        if (o == null) {
            return null;
        }
        DesignDataFieldDO t = new DesignDataFieldDO();
        DesignDataFieldDO.copyProperties(o, t);
        return t;
    }

    @Override
    public DesignDataFieldDO clone() {
        return (DesignDataFieldDO)super.clone();
    }

    @Override
    public String toString() {
        return "DesignDataFieldDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", alias='" + this.alias + '\'' + ", title='" + this.title + '\'' + ", order='" + this.order + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataTableKey='" + this.dataTableKey + '\'' + ", dataFieldApplyType=" + this.dataFieldApplyType + ", dataFieldKind=" + this.dataFieldKind + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", updateTime=" + this.updateTime + ", desc='" + this.desc + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", precision=" + this.precision + ", dataFieldType=" + this.dataFieldType + ", decimal=" + this.decimal + ", refDataEntityKey='" + this.refDataEntityKey + '\'' + ", refDataFieldKey='" + this.refDataFieldKey + '\'' + ", validationRules=" + this.validationRules + ", measureUnit='" + this.measureUnit + '\'' + ", dataFieldGatherType=" + this.dataFieldGatherType + ", allowMultipleSelect=" + this.allowMultipleSelect + ", onlyLeaf=" + this.onlyLeaf + ", formatProperties=" + this.formatProperties + ", secretLevel=" + this.secretLevel + ", useAuthority=" + this.useAuthority + ", allowUndefinedCode=" + this.allowUndefinedCode + ", changeWithPeriod=" + this.changeWithPeriod + ", generateVersion=" + this.generateVersion + ", allowTreeSum=" + this.allowTreeSum + '}';
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

