/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataFMDMField;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.FieldUseType;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FMDMFIELD")
public class CompareDataFMDMFieldDO
extends CompareDataDO
implements ICompareDataFMDMField {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_SINGLE_USETYPE", tranWith="transFieldUseType", dbType=Integer.class, appType=FieldUseType.class)
    protected FieldUseType singleUseType;
    @DBAnno.DBField(dbField="CD_OWNER_TABLETYPE", tranWith="transCompareTableType", dbType=Integer.class, appType=CompareTableType.class)
    protected CompareTableType ownerTableType;
    @DBAnno.DBField(dbField="CD_OWNER_TABLEKEY")
    protected String ownerTableKey;

    @Override
    public CompareDataFMDMFieldDO clone() {
        return (CompareDataFMDMFieldDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFMDMFieldDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + ",singleUseType=" + (Object)((Object)this.singleUseType) + ",ownerTableType=" + (Object)((Object)this.ownerTableType) + ",ownerTableKey='" + this.ownerTableKey + '\'' + '}';
    }

    public static CompareDataFMDMFieldDO valueOf(CompareDataFMDMFieldDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFMDMFieldDO) {
            return o;
        }
        CompareDataFMDMFieldDO t = new CompareDataFMDMFieldDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public FieldUseType getSingleUseType() {
        return this.singleUseType;
    }

    @Override
    public CompareTableType getOwnerTableType() {
        return this.ownerTableType;
    }

    @Override
    public String getOwnerTableKey() {
        return this.ownerTableKey;
    }

    public void setSingleUseType(FieldUseType singleUseType) {
        this.singleUseType = singleUseType;
    }

    public void setOwnerTableType(CompareTableType ownerTableType) {
        this.ownerTableType = ownerTableType;
    }

    public void setOwnerTableKey(String ownerTableKey) {
        this.ownerTableKey = ownerTableKey;
    }
}

