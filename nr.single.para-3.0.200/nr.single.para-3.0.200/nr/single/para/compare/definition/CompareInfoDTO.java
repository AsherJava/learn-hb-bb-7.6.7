/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareInfo;
import nr.single.para.compare.internal.defintion.CompareInfoDO;
import org.springframework.beans.BeanUtils;

public class CompareInfoDTO
extends CompareInfoDO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CompareInfoDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", status=" + (Object)((Object)this.status) + ", jioData='" + this.jioData + '\'' + ", mapData='" + this.mapData + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareInfoDTO valueOf(ICompareInfo o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareInfoDTO) {
            return (CompareInfoDTO)o;
        }
        CompareInfoDTO t = new CompareInfoDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

