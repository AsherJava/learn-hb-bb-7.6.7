/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataPrintScheme;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_PRINTSCHEME")
public class CompareDataPrintSchemeDO
extends CompareDataDO
implements ICompareDataPrintScheme {
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataPrintSchemeDO clone() {
        return (CompareDataPrintSchemeDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataPrintSchemeDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataPrintSchemeDO valueOf(CompareDataPrintSchemeDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataPrintSchemeDO) {
            return o;
        }
        CompareDataPrintSchemeDO t = new CompareDataPrintSchemeDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

