/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataPrintItem;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_PRINTITEM")
public class CompareDataPrintItemDO
extends CompareDataDO
implements ICompareDataPrintItem {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_SINGLE_PRINTSCHEME")
    protected String singlePrintScheme;
    @DBAnno.DBField(dbField="CD_SINGLE_FORMCODE")
    protected String singleFormCode;
    @DBAnno.DBField(dbField="CD_SINGLE_FORMTITLE")
    protected String singleFormTitile;
    @DBAnno.DBField(dbField="CD_NET_PRINTSCHEME")
    protected String netPrintScheme;
    @DBAnno.DBField(dbField="CD_NET_PRINTSCHEME_KEY")
    protected String netPrintSchemeKey;
    @DBAnno.DBField(dbField="CD_NET_FORMKEY")
    protected String netFormKey;
    @DBAnno.DBField(dbField="CD_NET_FORMCODE")
    protected String netFormCode;
    @DBAnno.DBField(dbField="CD_NET_FORMTITLE")
    protected String netFormTitle;
    @DBAnno.DBField(dbField="CD_SCHEME_COMPAREKEY")
    protected String schemeCompareKey;

    @Override
    public CompareDataPrintItemDO clone() {
        return (CompareDataPrintItemDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataPrintItemDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ",singlePrintScheme='" + this.singlePrintScheme + '\'' + ",singleFormCode='" + this.singleFormCode + '\'' + ",singleFormTitile='" + this.singleFormTitile + '\'' + ",netPrintScheme='" + this.netPrintScheme + '\'' + ",netPrintSchemeKey='" + this.netPrintSchemeKey + '\'' + ",netFormKey='" + this.netFormKey + '\'' + ",netFormKey='" + this.netFormKey + '\'' + ",netFormCode='" + this.netFormCode + '\'' + ",netFormTitle='" + this.netFormTitle + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataPrintItemDO valueOf(CompareDataPrintItemDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataPrintItemDO) {
            return o;
        }
        CompareDataPrintItemDO t = new CompareDataPrintItemDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getSinglePrintScheme() {
        return this.singlePrintScheme;
    }

    @Override
    public String getSingleFormCode() {
        return this.singleFormCode;
    }

    @Override
    public String getSingleFormTitile() {
        return this.singleFormTitile;
    }

    @Override
    public String getNetPrintScheme() {
        return this.netPrintScheme;
    }

    @Override
    public String getNetPrintSchemeKey() {
        return this.netPrintSchemeKey;
    }

    @Override
    public String getNetFormKey() {
        return this.netFormKey;
    }

    @Override
    public String getNetFormCode() {
        return this.netFormCode;
    }

    @Override
    public String getNetFormTitle() {
        return this.netFormTitle;
    }

    public void setSinglePrintScheme(String singlePrintScheme) {
        this.singlePrintScheme = singlePrintScheme;
    }

    public void setSingleFormCode(String singleFormCode) {
        this.singleFormCode = singleFormCode;
    }

    public void setSingleFormTitile(String singleFormTitile) {
        this.singleFormTitile = singleFormTitile;
    }

    public void setNetPrintScheme(String netPrintScheme) {
        this.netPrintScheme = netPrintScheme;
    }

    public void setNetPrintSchemeKey(String netPrintSchemeKey) {
        this.netPrintSchemeKey = netPrintSchemeKey;
    }

    public void setNetFormKey(String netFormKey) {
        this.netFormKey = netFormKey;
    }

    public void setNetFormCode(String netFormCode) {
        this.netFormCode = netFormCode;
    }

    public void setNetFormTitle(String netFormTitle) {
        this.netFormTitle = netFormTitle;
    }

    @Override
    public String getSchemeCompareKey() {
        return this.schemeCompareKey;
    }

    public void setSchemeCompareKey(String schemeCompareKey) {
        this.schemeCompareKey = schemeCompareKey;
    }
}

