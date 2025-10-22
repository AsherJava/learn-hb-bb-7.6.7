/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import nr.single.para.compare.definition.ICompareDataFormula;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_FORMULAFRM")
public class CompareDataFormulaDO
extends CompareDataDO
implements ICompareDataFormula {
    @DBAnno.DBField(dbField="CD_SCHEME_COMPAREKEY")
    protected String fmlSchemeCompareKey;
    @DBAnno.DBField(dbField="CD_FORM_COMPAREKEY")
    protected String fmlFormCompareKey;
    @DBAnno.DBField(dbField="CD_MESSAGE")
    protected String message;
    private static final long serialVersionUID = 1L;

    @Override
    public CompareDataFormulaDO clone() {
        return (CompareDataFormulaDO)super.clone();
    }

    @Override
    public String toString() {
        return "CompareDataFormulaSchemeDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType='" + (Object)((Object)this.dataType) + '\'' + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey=" + this.netKey + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static CompareDataFormulaDO valueOf(CompareDataFormulaDO o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CompareDataFormulaDO) {
            return o;
        }
        CompareDataFormulaDO t = new CompareDataFormulaDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getFmlShcemeCompareKey() {
        return this.fmlSchemeCompareKey;
    }

    public String getFmlSchemeCompareKey() {
        return this.fmlSchemeCompareKey;
    }

    public void setFmlSchemeCompareKey(String fmlSchemeCompareKey) {
        this.fmlSchemeCompareKey = fmlSchemeCompareKey;
    }

    @Override
    public String getFmlFormCompareKey() {
        return this.fmlFormCompareKey;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setFmlFormCompareKey(String fmlFormCompareKey) {
        this.fmlFormCompareKey = fmlFormCompareKey;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

