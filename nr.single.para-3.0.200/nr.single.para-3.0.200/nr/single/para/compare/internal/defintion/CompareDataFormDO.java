/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataForm;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FORM")
public class CompareDataFormDO
extends CompareDataDO
implements ICompareDataForm {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_COMPARETYPE", tranWith="transCompareContextType", dbType=Integer.class, appType=CompareContextType.class)
    protected CompareContextType compareType;
    @DBAnno.DBField(dbField="CD_INI_FIELDMAP", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean iniFieldMap;

    @Override
    public CompareDataFormDO clone() {
        return (CompareDataFormDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFORMDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormDO valueOf(CompareDataFormDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormDO) {
            return o;
        }
        CompareDataFormDO t = new CompareDataFormDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public CompareContextType getCompareType() {
        return this.compareType;
    }

    public void setCompareType(CompareContextType compareType) {
        this.compareType = compareType;
    }

    @Override
    public boolean isIniFieldMap() {
        return this.iniFieldMap;
    }

    public void setIniFieldMap(boolean iniFieldMap) {
        this.iniFieldMap = iniFieldMap;
    }
}

