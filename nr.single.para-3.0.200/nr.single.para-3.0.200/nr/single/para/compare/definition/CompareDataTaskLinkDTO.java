/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareDataTaskLink;
import nr.single.para.compare.internal.defintion.CompareDataTaskLinkDO;
import org.springframework.beans.BeanUtils;

public class CompareDataTaskLinkDTO
extends CompareDataTaskLinkDO {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataTaskLinkDTO clone() {
        return (CompareDataTaskLinkDTO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataTaskLinkDTO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + "', netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType=" + (Object)((Object)this.updateType) + ", changeType=" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + ", singleTaskYear='" + this.singleTaskYear + '\'' + ",singleTaskType=" + this.singleTaskType + ",singleCurrentExp='" + this.singleCurrentExp + '\'' + ",SingleLinkExp='" + this.SingleLinkExp + '\'' + ",netTaskKey='" + this.netTaskKey + '\'' + ",netCurrentExp='" + this.netCurrentExp + '\'' + ",netLinkExp='" + this.netLinkExp + '\'' + '}';
    }

    public static CompareDataTaskLinkDTO valueOf(ICompareDataTaskLink o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataTaskLinkDTO) {
            return (CompareDataTaskLinkDTO)o;
        }
        CompareDataTaskLinkDTO t = new CompareDataTaskLinkDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

