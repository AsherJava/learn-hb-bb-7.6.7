/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataEnum;
import nr.single.para.compare.internal.defintion.CompareDataEnumDO;
import org.springframework.beans.BeanUtils;

public class CompareDataEnumDTO
extends CompareDataEnumDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataEnumDTO clone() {
        return (CompareDataEnumDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataEnumDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + ", compareType=" + (Object)((Object)this.compareType) + '}';
    }

    public static CompareDataEnumDTO valueOf(ICompareDataEnum o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataEnumDTO) {
            return (CompareDataEnumDTO)o;
        }
        CompareDataEnumDTO t = new CompareDataEnumDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

