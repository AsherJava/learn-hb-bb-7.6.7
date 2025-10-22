/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataFormula;
import nr.single.para.compare.internal.defintion.CompareDataFormulaDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFormulaDTO
extends CompareDataFormulaDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaDTO clone() {
        return (CompareDataFormulaDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaDTO valueOf(ICompareDataFormula o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaDTO) {
            return (CompareDataFormulaDTO)o;
        }
        CompareDataFormulaDTO t = new CompareDataFormulaDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

