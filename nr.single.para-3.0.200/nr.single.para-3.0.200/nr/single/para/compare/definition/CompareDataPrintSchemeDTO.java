/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataPrintScheme;
import nr.single.para.compare.internal.defintion.CompareDataPrintSchemeDO;
import org.springframework.beans.BeanUtils;

public class CompareDataPrintSchemeDTO
extends CompareDataPrintSchemeDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataPrintSchemeDTO clone() {
        return (CompareDataPrintSchemeDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataEnumDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataPrintSchemeDTO valueOf(ICompareDataPrintScheme o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataPrintSchemeDTO) {
            return (CompareDataPrintSchemeDTO)o;
        }
        CompareDataPrintSchemeDTO t = new CompareDataPrintSchemeDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

