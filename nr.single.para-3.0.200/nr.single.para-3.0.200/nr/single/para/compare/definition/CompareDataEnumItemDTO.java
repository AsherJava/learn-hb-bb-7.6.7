/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataEnumItem;
import nr.single.para.compare.internal.defintion.CompareDataEnumItemDO;
import org.springframework.beans.BeanUtils;

public class CompareDataEnumItemDTO
extends CompareDataEnumItemDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataEnumItemDTO clone() {
        return (CompareDataEnumItemDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataEnumItemDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + '\'' + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '\'' + ", enumCompareKey='" + this.enumCompareKey + '\'' + '\'' + ", singleParentCode='" + this.singleParentCode + '\'' + '\'' + ", netParentCode='" + this.netParentCode + '\'' + '}';
    }

    public static CompareDataEnumItemDTO valueOf(ICompareDataEnumItem o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataEnumItemDTO) {
            return (CompareDataEnumItemDTO)o;
        }
        CompareDataEnumItemDTO t = new CompareDataEnumItemDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

