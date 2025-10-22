/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;
import org.springframework.lang.NonNull;

public class BatchDelCheckDesParam {
    @NonNull
    private final String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private List<String> formulaSchemeKeys;
    private List<String> formKeys;
    private List<String> parsedFormulaKeys;
    private List<String> recordIds;
    private Boolean desCheckPass;

    public BatchDelCheckDesParam(@NonNull String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Boolean getDesCheckPass() {
        return this.desCheckPass;
    }

    public void setDesCheckPass(Boolean desCheckPass) {
        this.desCheckPass = desCheckPass;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    @NonNull
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public List<String> getParsedFormulaKeys() {
        return this.parsedFormulaKeys;
    }

    public void setParsedFormulaKeys(List<String> parsedFormulaKeys) {
        this.parsedFormulaKeys = parsedFormulaKeys;
    }

    public List<String> getRecordIds() {
        return this.recordIds;
    }

    public void setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
    }
}

