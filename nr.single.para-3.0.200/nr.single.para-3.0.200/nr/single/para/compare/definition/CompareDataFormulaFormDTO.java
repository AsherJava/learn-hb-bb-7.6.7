/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataFormulaForm;
import nr.single.para.compare.internal.defintion.CompareDataFormulaFormDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFormulaFormDTO
extends CompareDataFormulaFormDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaFormDTO clone() {
        return (CompareDataFormulaFormDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaFormDTO valueOf(ICompareDataFormulaForm o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaFormDTO) {
            return (CompareDataFormulaFormDTO)o;
        }
        CompareDataFormulaFormDTO t = new CompareDataFormulaFormDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

