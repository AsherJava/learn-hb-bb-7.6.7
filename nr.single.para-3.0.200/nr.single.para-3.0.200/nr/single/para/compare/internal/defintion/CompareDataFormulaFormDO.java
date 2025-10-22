/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataFormulaForm;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FORMULAFRM")
public class CompareDataFormulaFormDO
extends CompareDataDO
implements ICompareDataFormulaForm {
    @DBAnno.DBField(dbField="CD_ITEM_CHANGETYPE", tranWith="transCompareChangeType", dbType=Integer.class, appType=CompareChangeType.class)
    protected CompareChangeType itemChangeType;
    @DBAnno.DBField(dbField="CD_SCHEME_COMPAREKEY")
    protected String fmlSchemeCompareKey;
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaFormDO clone() {
        return (CompareDataFormulaFormDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaFormDO valueOf(CompareDataFormulaFormDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaFormDO) {
            return o;
        }
        CompareDataFormulaFormDO t = new CompareDataFormulaFormDO();
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

    @Override
    public String getFmlSchemeCompareKey() {
        return this.fmlSchemeCompareKey;
    }

    public void setFmlSchemeCompareKey(String fmlSchemeCompareKey) {
        this.fmlSchemeCompareKey = fmlSchemeCompareKey;
    }
}

