/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataPrintItem;
import nr.single.para.compare.internal.defintion.CompareDataPrintItemDO;
import org.springframework.beans.BeanUtils;

public class CompareDataPrintItemDTO
extends CompareDataPrintItemDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataPrintItemDTO clone() {
        return (CompareDataPrintItemDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataPrintItemDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ",singlePrintScheme='" + this.singlePrintScheme + '\'' + ",singleFormCode='" + this.singleFormCode + '\'' + ",singleFormTitile='" + this.singleFormTitile + '\'' + ",netPrintScheme='" + this.netPrintScheme + '\'' + ",netPrintSchemeKey='" + this.netPrintSchemeKey + '\'' + ",netNetFormKey='" + this.netFormKey + '\'' + ",netNetFormKey='" + this.netFormKey + '\'' + ",netNetFormCode='" + this.netFormCode + '\'' + ",netNetFormTitle='" + this.netFormTitle + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataPrintItemDTO valueOf(ICompareDataPrintItem o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataPrintItemDTO) {
            return (CompareDataPrintItemDTO)o;
        }
        CompareDataPrintItemDTO t = new CompareDataPrintItemDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

