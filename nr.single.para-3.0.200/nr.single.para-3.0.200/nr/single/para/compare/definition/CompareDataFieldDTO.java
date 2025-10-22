/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataField;
import nr.single.para.compare.internal.defintion.CompareDataFieldDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFieldDTO
extends CompareDataFieldDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFieldDTO clone() {
        return (CompareDataFieldDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFieldDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + 39 + ",formCompareKey='" + this.formCompareKey + '\'' + ",singlePosX=" + this.singlePosX + ",singlePosY=" + this.singlePosY + ",singleMatchTitle='" + this.singleMatchTitle + '\'' + ",netPosX=" + this.netPosX + ",netPosY=" + this.netPosY + ",netMatchTitle='" + this.netMatchTitle + '\'' + ",netLinkKey='" + this.netLinkKey + '\'' + ",netRegionKey='" + this.netRegionKey + '\'' + ",netFormKey='" + this.netFormKey + '\'' + ",netTableKey='" + this.netTableKey + '\'' + '}';
    }

    public static CompareDataFieldDTO valueOf(ICompareDataField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFieldDTO) {
            return (CompareDataFieldDTO)o;
        }
        CompareDataFieldDTO t = new CompareDataFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

