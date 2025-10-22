/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

public class CompareDataDTO
extends CompareDataDO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CompareDataDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType=" + (Object)((Object)this.dataType) + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + '\'' + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataDTO valueOf(ICompareData o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataDTO) {
            return (CompareDataDTO)o;
        }
        CompareDataDTO t = new CompareDataDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

