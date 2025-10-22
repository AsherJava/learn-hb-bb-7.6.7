/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataForm;
import nr.single.para.compare.internal.defintion.CompareDataFormDO;
import org.springframework.beans.BeanUtils;

public class CompareDataFormDTO
extends CompareDataFormDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormDTO clone() {
        return (CompareDataFormDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + ", compareType='" + (Object)((Object)this.compareType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormDTO valueOf(ICompareDataForm o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormDTO) {
            return (CompareDataFormDTO)o;
        }
        CompareDataFormDTO t = new CompareDataFormDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

