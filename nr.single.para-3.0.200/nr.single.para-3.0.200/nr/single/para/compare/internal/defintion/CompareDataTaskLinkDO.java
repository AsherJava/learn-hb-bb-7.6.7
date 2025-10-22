/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.single.para.compare.internal.defintion;

import com.jiuqi.np.period.PeriodType;
import nr.single.para.compare.definition.ICompareDataTaskLink;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_TASKLINK")
public class CompareDataTaskLinkDO
extends CompareDataDO
implements ICompareDataTaskLink {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_SINGLE_LINKALIAS")
    protected String singleLinkAlias;
    @DBAnno.DBField(dbField="CD_SINGLE_TASKYEAR")
    protected String singleTaskYear;
    @DBAnno.DBField(dbField="CD_SINGLE_TASKTYPE", tranWith="transPeriodType", dbType=Integer.class, appType=PeriodType.class)
    protected PeriodType singleTaskType;
    @DBAnno.DBField(dbField="CD_SINGLE_CURRENTEXP")
    protected String singleCurrentExp;
    @DBAnno.DBField(dbField="CD_SINGLE_LINKEXP")
    protected String SingleLinkExp;
    @DBAnno.DBField(dbField="CD_NET_LINKALIAS")
    protected String netLinkAlias;
    @DBAnno.DBField(dbField="CD_NET_TASKKEY")
    protected String netTaskKey;
    @DBAnno.DBField(dbField="CD_NET_FORMSCHEME_KEY")
    protected String netFormSchemeKey;
    @DBAnno.DBField(dbField="CD_NET_CURRENTEXP")
    protected String netCurrentExp;
    @DBAnno.DBField(dbField="CD_NET_LINKEXP")
    protected String netLinkExp;

    @Override
    public CompareDataTaskLinkDO clone() {
        return (CompareDataTaskLinkDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataTaskLinkDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + "', netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType=" + (Object)((Object)this.updateType) + ", changeType=" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + ", singleTaskYear='" + this.singleTaskYear + '\'' + ",singleTaskType=" + this.singleTaskType + ",singleCurrentExp='" + this.singleCurrentExp + '\'' + ",SingleLinkExp='" + this.SingleLinkExp + '\'' + ",netTaskKey='" + this.netTaskKey + '\'' + ",netFormSchemeKey='" + this.netFormSchemeKey + '\'' + ",netCurrentExp='" + this.netCurrentExp + '\'' + ",netLinkExp='" + this.netLinkExp + '\'' + '}';
    }

    public static CompareDataTaskLinkDO valueOf(CompareDataTaskLinkDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataTaskLinkDO) {
            return o;
        }
        CompareDataTaskLinkDO t = new CompareDataTaskLinkDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    @Override
    public PeriodType getSingleTaskType() {
        return this.singleTaskType;
    }

    @Override
    public String getSingleCurrentExp() {
        return this.singleCurrentExp;
    }

    @Override
    public String getSingleLinkExp() {
        return this.SingleLinkExp;
    }

    @Override
    public String getNetTaskKey() {
        return this.netTaskKey;
    }

    @Override
    public String getNetCurrentExp() {
        return this.netCurrentExp;
    }

    @Override
    public String getNetLinkExp() {
        return this.netLinkExp;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public void setSingleTaskType(PeriodType singleTaskType) {
        this.singleTaskType = singleTaskType;
    }

    public void setSingleCurrentExp(String singleCurrentExp) {
        this.singleCurrentExp = singleCurrentExp;
    }

    public void setSingleLinkExp(String singleLinkExp) {
        this.SingleLinkExp = singleLinkExp;
    }

    public void setNetTaskKey(String netTaskKey) {
        this.netTaskKey = netTaskKey;
    }

    public void setNetCurrentExp(String netCurrentExp) {
        this.netCurrentExp = netCurrentExp;
    }

    public void setNetLinkExp(String netLinkExp) {
        this.netLinkExp = netLinkExp;
    }

    @Override
    public String getNetFormSchemeKey() {
        return this.netFormSchemeKey;
    }

    public void setNetFormSchemeKey(String netFormSchemeKey) {
        this.netFormSchemeKey = netFormSchemeKey;
    }

    @Override
    public String getSingleLinkAlias() {
        return this.singleLinkAlias;
    }

    @Override
    public String getNetLinkAlias() {
        return this.netLinkAlias;
    }

    public void setSingleLinkAlias(String singleLinkAlias) {
        this.singleLinkAlias = singleLinkAlias;
    }

    public void setNetLinkAlias(String netLinkAlias) {
        this.netLinkAlias = netLinkAlias;
    }
}

