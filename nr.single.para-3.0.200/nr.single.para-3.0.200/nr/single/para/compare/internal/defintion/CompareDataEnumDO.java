/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataEnum;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_ENUM")
public class CompareDataEnumDO
extends CompareDataDO
implements ICompareDataEnum {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_COMPARETYPE", tranWith="transCompareContextType", dbType=Integer.class, appType=CompareContextType.class)
    protected CompareContextType compareType;
    @DBAnno.DBField(dbField="CD_SINGLECODELEN")
    protected Integer singleCodeLen;
    @DBAnno.DBField(dbField="CD_SINGLESTRUCTTYPE")
    protected Integer singleStructType;
    @DBAnno.DBField(dbField="CD_SINGLELEVELCODE")
    protected String singleLevelCode;
    @DBAnno.DBField(dbField="CD_SINGLECODEFIX", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean singleCodeFix;
    @DBAnno.DBField(dbField="CD_NETCODELEN")
    protected Integer netCodeLen;
    @DBAnno.DBField(dbField="CD_NETSTRUCTTYPE")
    protected Integer netStructType;
    @DBAnno.DBField(dbField="CD_NETLEVELCODE")
    protected String netLevelCode;
    @DBAnno.DBField(dbField="CD_NETCODEFIX", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean netCodeFix;
    @DBAnno.DBField(dbField="CD_ITEM_CHANGETYPE", tranWith="transCompareChangeType", dbType=Integer.class, appType=CompareChangeType.class)
    protected CompareChangeType itemChangeType;

    @Override
    public CompareDataEnumDO clone() {
        return (CompareDataEnumDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataEnumDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + 39 + ", compareType=" + (Object)((Object)this.compareType) + '}';
    }

    public static CompareDataEnumDO valueOf(CompareDataEnumDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataEnumDO) {
            return o;
        }
        CompareDataEnumDO t = new CompareDataEnumDO();
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
    public Integer getSingleCodeLen() {
        return this.singleCodeLen;
    }

    @Override
    public Integer getSingleStructType() {
        return this.singleStructType;
    }

    @Override
    public String getSingleLevelCode() {
        return this.singleLevelCode;
    }

    @Override
    public Integer getNetCodeLen() {
        return this.netCodeLen;
    }

    @Override
    public Integer getNetStructType() {
        return this.netStructType;
    }

    @Override
    public String getNetLevelCode() {
        return this.netLevelCode;
    }

    public void setSingleCodeLen(Integer singleCodeLen) {
        this.singleCodeLen = singleCodeLen;
    }

    public void setSingleStructType(Integer singleStructType) {
        this.singleStructType = singleStructType;
    }

    public void setSingleLevelCode(String singleLevelCode) {
        this.singleLevelCode = singleLevelCode;
    }

    public void setNetCodeLen(Integer netCodeLen) {
        this.netCodeLen = netCodeLen;
    }

    public void setNetStructType(Integer netStructType) {
        this.netStructType = netStructType;
    }

    public void setNetLevelCode(String netLevelCode) {
        this.netLevelCode = netLevelCode;
    }

    @Override
    public Boolean getSingleCodeFix() {
        return this.singleCodeFix;
    }

    @Override
    public Boolean getNetCodeFix() {
        return this.netCodeFix;
    }

    public void setSingleCodeFix(Boolean singleCodeFix) {
        this.singleCodeFix = singleCodeFix;
    }

    public void setNetCodeFix(Boolean netCodeFix) {
        this.netCodeFix = netCodeFix;
    }

    @Override
    public CompareChangeType getItemChangeType() {
        return this.itemChangeType;
    }

    public void setItemChangeType(CompareChangeType itemChangeType) {
        this.itemChangeType = itemChangeType;
    }
}

