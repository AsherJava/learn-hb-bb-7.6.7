/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataFormulaScheme;
import nr.single.para.compare.internal.defintion.CompareDataFormulaSchemeDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFormulaSchemeDTO
extends CompareDataFormulaSchemeDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaSchemeDTO clone() {
        return (CompareDataFormulaSchemeDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaSchemeDTO valueOf(ICompareDataFormulaScheme o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaSchemeDTO) {
            return (CompareDataFormulaSchemeDTO)o;
        }
        CompareDataFormulaSchemeDTO t = new CompareDataFormulaSchemeDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

