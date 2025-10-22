/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package nr.single.map.configurations.vo;

import com.jiuqi.nr.definition.facade.FormulaDefine;

public class FormulaDefineVO {
    private String key;
    private String code;
    private String expression;
    private String formKey;
    private String formulaSchemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public static FormulaDefineVO getInstance(FormulaDefine formulaDefine) {
        FormulaDefineVO vo = new FormulaDefineVO();
        vo.setKey(formulaDefine.getKey());
        vo.setCode(formulaDefine.getCode());
        vo.setExpression(formulaDefine.getExpression());
        vo.setFormKey(formulaDefine.getFormKey());
        vo.setFormulaSchemeKey(formulaDefine.getFormulaSchemeKey());
        return vo;
    }
}

