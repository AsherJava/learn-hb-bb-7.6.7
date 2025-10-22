/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareMapField;
import nr.single.para.compare.internal.defintion.CompareMapFieldDO;
import org.springframework.beans.BeanUtils;

public class CompareMapFieldDTO
extends CompareMapFieldDO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CompareMapFieldDO{key='" + this.key + '\'' + ", fieldKey='" + this.fieldKey + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", matchTitle='" + this.matchTitle + '}';
    }

    public static CompareMapFieldDTO valueOf(ICompareMapField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareMapFieldDTO) {
            return (CompareMapFieldDTO)o;
        }
        CompareMapFieldDTO t = new CompareMapFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

