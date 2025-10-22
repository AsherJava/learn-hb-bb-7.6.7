/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nr.single.map.configurations.deserializer.SingleFileFormulaDeserializer;
import nr.single.map.data.facade.SingleFileFormulaItem;

@JsonDeserialize(using=SingleFileFormulaDeserializer.class)
public class SingleFileFormulaItemImpl
implements SingleFileFormulaItem {
    private static final long serialVersionUID = 2735073954606831857L;
    private String singleFormulaCode;
    private String netFormulaCode;
    private String singleFormulaExp;
    private String netFormulaExp;
    private String netFormulaKey;
    private String singleSchemeName;
    private String netSchemeName;
    private String netSchemeKey;
    private String singleTableCode;
    private String netFormCode;
    private String netFormKey;
    private String importIndex;

    @Override
    public String getSingleFormulaCode() {
        return this.singleFormulaCode;
    }

    @Override
    public void setSingleFormulaCode(String formulaCode) {
        this.singleFormulaCode = formulaCode;
    }

    @Override
    public String getSingleFormulaExp() {
        return this.singleFormulaExp;
    }

    @Override
    public void setSingleFormulaExp(String formulaExp) {
        this.singleFormulaExp = formulaExp;
    }

    @Override
    public String getNetFormulaCode() {
        return this.netFormulaCode;
    }

    @Override
    public void setNetFormulaCode(String formulaCode) {
        this.netFormulaCode = formulaCode;
    }

    @Override
    public String getNetFormulaExp() {
        return this.netFormulaExp;
    }

    @Override
    public void setNetFormulaExp(String formulaExp) {
        this.netFormulaExp = formulaExp;
    }

    @Override
    public String getNetFormulaKey() {
        return this.netFormulaKey;
    }

    @Override
    public void setNetFormulaKey(String formulaKey) {
        this.netFormulaKey = formulaKey;
    }

    @Override
    public void copyFrom(SingleFileFormulaItem info) {
        this.singleFormulaCode = info.getSingleFormulaCode();
        this.singleFormulaExp = info.getSingleFormulaExp();
        this.netFormulaCode = info.getNetFormulaCode();
        this.netFormulaExp = info.getNetFormulaExp();
        this.netFormulaKey = info.getNetFormulaKey();
        this.netSchemeKey = info.getNetSchemeKey();
        this.netSchemeName = info.getNetSchemeName();
        this.singleSchemeName = info.getSingleSchemeName();
        this.singleTableCode = info.getSingleTableCode();
        this.netFormCode = info.getNetFormCode();
        this.netFormKey = info.getNetFormKey();
        this.importIndex = info.getImportIndex();
    }

    @Override
    public String getSingleSchemeName() {
        return this.singleSchemeName;
    }

    @Override
    public void setSingleSchemeName(String schemeName) {
        this.singleSchemeName = schemeName;
    }

    @Override
    public String getNetSchemeName() {
        return this.netSchemeName;
    }

    @Override
    public void setNetSchemeName(String schemeName) {
        this.netSchemeName = schemeName;
    }

    @Override
    public String getNetSchemeKey() {
        return this.netSchemeKey;
    }

    @Override
    public void setNetSchemeKey(String schemeKey) {
        this.netSchemeKey = schemeKey;
    }

    @Override
    public String getSingleTableCode() {
        return this.singleTableCode;
    }

    @Override
    public void setSingleTableCode(String singleTableCode) {
        this.singleTableCode = singleTableCode;
    }

    @Override
    public String getNetFormCode() {
        return this.netFormCode;
    }

    @Override
    public void setNetFormCode(String formCode) {
        this.netFormCode = formCode;
    }

    @Override
    public String getNetFormKey() {
        return this.netFormKey;
    }

    @Override
    public void setNetFormKey(String formKey) {
        this.netFormKey = formKey;
    }

    @Override
    public String getImportIndex() {
        return this.importIndex;
    }

    @Override
    public void setImportIndex(String importIndex) {
        this.importIndex = importIndex;
    }
}

