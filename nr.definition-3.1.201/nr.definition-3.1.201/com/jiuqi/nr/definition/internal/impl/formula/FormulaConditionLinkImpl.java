/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLinks
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl.formula;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_PARAM_CONDITION_LINK")
@DBAnno.DBLinks(value={@DBAnno.DBLink(linkWith=FormulaConditionLinkImpl.class, linkField="key", field="conditionKey"), @DBAnno.DBLink(linkWith=FormulaDefine.class, linkField="key", field="formulaKey"), @DBAnno.DBLink(linkWith=FormulaSchemeDefine.class, linkField="key", field="formulaSchemeKey")})
public class FormulaConditionLinkImpl
implements FormulaConditionLink {
    @DBAnno.DBField(dbField="CL_KEY")
    private String conditionKey;
    @DBAnno.DBField(dbField="CL_FM_KEY")
    private String formulaKey;
    @DBAnno.DBField(dbField="CL_FORMULA_SCHEME")
    private String formulaSchemeKey;

    @Override
    public String getConditionKey() {
        return this.conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    @Override
    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FormulaConditionLinkImpl that = (FormulaConditionLinkImpl)o;
        return Objects.equals(this.conditionKey, that.conditionKey) && Objects.equals(this.formulaKey, that.formulaKey) && Objects.equals(this.formulaSchemeKey, that.formulaSchemeKey);
    }

    public int hashCode() {
        return Objects.hash(this.conditionKey, this.formulaKey, this.formulaSchemeKey);
    }
}

