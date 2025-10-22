/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package nr.single.map.data.bean;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;

public class FormulaSchemeData {
    private String key;
    private String title;
    private String type;
    private boolean defaultScheme;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDefaultScheme() {
        return this.defaultScheme;
    }

    public void setDefaultScheme(boolean defaultScheme) {
        this.defaultScheme = defaultScheme;
    }

    public void init(FormulaSchemeDefine formulaSchemeDefine) {
        this.key = formulaSchemeDefine.getKey();
        this.title = formulaSchemeDefine.getTitle();
        this.type = formulaSchemeDefine.getFormulaSchemeType().name();
        this.defaultScheme = formulaSchemeDefine.isDefault();
    }
}

