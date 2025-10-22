/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataEnumItem;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_ENUMITEM")
public class CompareDataEnumItemDO
extends CompareDataDO
implements ICompareDataEnumItem {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_ENUM_COMPAREKEY")
    protected String enumCompareKey;
    @DBAnno.DBField(dbField="CD_SINGLEPARENTCODE")
    protected String singleParentCode;
    @DBAnno.DBField(dbField="CD_NETPARENTCODE")
    protected String netParentCode;

    @Override
    public CompareDataEnumItemDO clone() {
        return (CompareDataEnumItemDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataEnumItemDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + '\'' + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '\'' + ", enumCompareKey='" + this.enumCompareKey + '\'' + '\'' + ", singleParentCode='" + this.singleParentCode + '\'' + '\'' + ", netParentCode='" + this.netParentCode + '\'' + '}';
    }

    public static CompareDataEnumItemDO valueOf(CompareDataEnumItemDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataEnumItemDO) {
            return o;
        }
        CompareDataEnumItemDO t = new CompareDataEnumItemDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getEnumCompareKey() {
        return this.enumCompareKey;
    }

    public void setEnumCompareKey(String enumCompareKey) {
        this.enumCompareKey = enumCompareKey;
    }

    @Override
    public String getSingleParentCode() {
        return this.singleParentCode;
    }

    public void setSingleParentCode(String singleParentCode) {
        this.singleParentCode = singleParentCode;
    }

    @Override
    public String getNetParentCode() {
        return this.netParentCode;
    }

    public void setNetParentCode(String netParentCode) {
        this.netParentCode = netParentCode;
    }
}

