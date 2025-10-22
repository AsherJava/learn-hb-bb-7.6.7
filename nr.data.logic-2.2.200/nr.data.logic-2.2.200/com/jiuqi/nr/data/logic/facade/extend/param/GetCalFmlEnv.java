/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import java.util.List;
import java.util.Map;

public class GetCalFmlEnv {
    private String formulaSchemeKey;
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private Mode mode;
    private Map<String, List<String>> formulaMaps;
    private List<String> accessForms;
    private boolean wholeBetween;
    private List<IParsedExpression> parsedExpressions;
    private IProviderStore providerStore;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public GetCalFmlEnv setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
        return this;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public GetCalFmlEnv setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
        return this;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public GetCalFmlEnv setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
        return this;
    }

    public Mode getMode() {
        return this.mode;
    }

    public GetCalFmlEnv setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public Map<String, List<String>> getFormulaMaps() {
        return this.formulaMaps;
    }

    public GetCalFmlEnv setFormulaMaps(Map<String, List<String>> formulaMaps) {
        this.formulaMaps = formulaMaps;
        return this;
    }

    public List<String> getAccessForms() {
        return this.accessForms;
    }

    public GetCalFmlEnv setAccessForms(List<String> accessForms) {
        this.accessForms = accessForms;
        return this;
    }

    public boolean isWholeBetween() {
        return this.wholeBetween;
    }

    public GetCalFmlEnv setWholeBetween(boolean wholeBetween) {
        this.wholeBetween = wholeBetween;
        return this;
    }

    public List<IParsedExpression> getParsedExpressions() {
        return this.parsedExpressions;
    }

    public GetCalFmlEnv setParsedExpressions(List<IParsedExpression> parsedExpressions) {
        this.parsedExpressions = parsedExpressions;
        return this;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public GetCalFmlEnv setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
        return this;
    }
}

