/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataFMDMField;
import nr.single.para.compare.internal.defintion.CompareDataFMDMFieldDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFMDMFieldDTO
extends CompareDataFMDMFieldDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFMDMFieldDTO clone() {
        return (CompareDataFMDMFieldDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFMDMFieldDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + ",singleUseType=" + (Object)((Object)this.singleUseType) + ",ownerTableType=" + (Object)((Object)this.ownerTableType) + ",ownerTableKey='" + this.ownerTableKey + '\'' + '}';
    }

    public static CompareDataFMDMFieldDTO valueOf(ICompareDataFMDMField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFMDMFieldDTO) {
            return (CompareDataFMDMFieldDTO)o;
        }
        CompareDataFMDMFieldDTO t = new CompareDataFMDMFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

