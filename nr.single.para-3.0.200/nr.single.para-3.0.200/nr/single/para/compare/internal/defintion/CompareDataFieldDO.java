/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataField;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FIELD")
public class CompareDataFieldDO
extends CompareDataDO
implements ICompareDataField {
    @DBAnno.DBField(dbField="CD_FORM_COMPAREKEY")
    protected String formCompareKey;
    @DBAnno.DBField(dbField="CD_SINGLE_POSX")
    protected int singlePosX;
    @DBAnno.DBField(dbField="CD_SINGLE_POSY")
    protected int singlePosY;
    @DBAnno.DBField(dbField="CD_SINGLE_FLOATINGINDEX")
    protected int singleFloatingIndex;
    @DBAnno.DBField(dbField="CD_SINGLE_FLOATINGID")
    protected Integer singleFloatingId;
    @DBAnno.DBField(dbField="CD_SINGLE_MATCHTITLE")
    protected String singleMatchTitle;
    @DBAnno.DBField(dbField="CD_NET_POSX")
    protected int netPosX;
    @DBAnno.DBField(dbField="CD_NET_POSY")
    protected int netPosY;
    @DBAnno.DBField(dbField="CD_NET_MATCHTITLE")
    protected String netMatchTitle;
    @DBAnno.DBField(dbField="CD_NET_LINKKEY")
    protected String netLinkKey;
    @DBAnno.DBField(dbField="CD_NET_REGIONKEY")
    protected String netRegionKey;
    @DBAnno.DBField(dbField="CD_NET_FORMKEY")
    protected String netFormKey;
    @DBAnno.DBField(dbField="CD_NET_TABLEKEY")
    protected String netTableKey;
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFieldDO clone() {
        return (CompareDataFieldDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFieldDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + 39 + ",formCompareKey='" + this.formCompareKey + '\'' + ",singlePosX=" + this.singlePosX + ",singlePosY=" + this.singlePosY + ",singleMatchTitle='" + this.singleMatchTitle + '\'' + ",netPosX=" + this.netPosX + ",netPosY=" + this.netPosY + ",netMatchTitle='" + this.netMatchTitle + '\'' + ",netLinkKey='" + this.netLinkKey + '\'' + ",netRegionKey='" + this.netRegionKey + '\'' + ",netFormKey='" + this.netFormKey + '\'' + ",netTableKey='" + this.netTableKey + '\'' + '}';
    }

    public static CompareDataFieldDO valueOf(CompareDataFieldDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFieldDO) {
            return o;
        }
        CompareDataFieldDO t = new CompareDataFieldDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getFormCompareKey() {
        return this.formCompareKey;
    }

    @Override
    public int getSinglePosX() {
        return this.singlePosX;
    }

    @Override
    public int getSinglePosY() {
        return this.singlePosY;
    }

    @Override
    public Integer getSingleFloatingId() {
        return this.singleFloatingId;
    }

    @Override
    public String getSingleMatchTitle() {
        return this.singleMatchTitle;
    }

    @Override
    public int getNetPosX() {
        return this.netPosX;
    }

    @Override
    public int getNetPosY() {
        return this.netPosY;
    }

    @Override
    public String getNetMatchTitle() {
        return this.netMatchTitle;
    }

    @Override
    public String getNetLinkKey() {
        return this.netLinkKey;
    }

    @Override
    public String getNetRegionKey() {
        return this.netRegionKey;
    }

    @Override
    public String getNetFormKey() {
        return this.netFormKey;
    }

    public void setSingleFloatingId(Integer singleFoatingId) {
        this.singleFloatingId = singleFoatingId;
    }

    public void setFormCompareKey(String formCompareKey) {
        this.formCompareKey = formCompareKey;
    }

    public void setSinglePosX(int singlePosX) {
        this.singlePosX = singlePosX;
    }

    public void setSinglePosY(int singlePosY) {
        this.singlePosY = singlePosY;
    }

    public void setSingleMatchTitle(String singleMatchTitle) {
        this.singleMatchTitle = singleMatchTitle;
    }

    public void setNetPosX(int netPosX) {
        this.netPosX = netPosX;
    }

    public void setNetPosY(int netPosY) {
        this.netPosY = netPosY;
    }

    public void setNetMatchTitle(String netMatchTitle) {
        this.netMatchTitle = netMatchTitle;
    }

    public void setNetLinkKey(String netLinkKey) {
        this.netLinkKey = netLinkKey;
    }

    public void setNetRegionKey(String netRegionKey) {
        this.netRegionKey = netRegionKey;
    }

    public void setNetFormKey(String netFormKey) {
        this.netFormKey = netFormKey;
    }

    @Override
    public int getSingleFloatingIndex() {
        return this.singleFloatingIndex;
    }

    public void setSingleFloatingIndex(int singleFloatingIndex) {
        this.singleFloatingIndex = singleFloatingIndex;
    }

    @Override
    public String getNetTableKey() {
        return this.netTableKey;
    }

    public void setNetTableKey(String netTableKey) {
        this.netTableKey = netTableKey;
    }
}

