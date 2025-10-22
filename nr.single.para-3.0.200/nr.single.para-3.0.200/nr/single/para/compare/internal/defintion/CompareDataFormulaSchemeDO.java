/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataFormulaScheme;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FORMULASCM")
public class CompareDataFormulaSchemeDO
extends CompareDataDO
implements ICompareDataFormulaScheme {
    @DBAnno.DBField(dbField="CD_ITEM_CHANGETYPE", tranWith="transCompareChangeType", dbType=Integer.class, appType=CompareChangeType.class)
    protected CompareChangeType itemChangeType;
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaSchemeDO clone() {
        return (CompareDataFormulaSchemeDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaSchemeDO valueOf(CompareDataFormulaSchemeDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaSchemeDO) {
            return o;
        }
        CompareDataFormulaSchemeDO t = new CompareDataFormulaSchemeDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public CompareChangeType getItemChangeType() {
        return this.itemChangeType;
    }

    public void setItemChangeType(CompareChangeType itemChangeType) {
        this.itemChangeType = itemChangeType;
    }
}

