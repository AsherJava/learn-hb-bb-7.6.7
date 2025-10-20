/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset.textprocessor;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.textprocessor.IDataSourceProvider;
import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.RangeValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextFormulaContext
implements IContext {
    private String userGuid;
    private String i18nLang;
    private Map<String, List<FilterItem>> filterMap = new HashMap<String, List<FilterItem>>();
    private Map<String, RangeValues> timekeyFilterMap = new HashMap<String, RangeValues>();
    private Map<String, String> currTimekeyMap = new HashMap<String, String>();
    private Map<String, com.jiuqi.nvwa.framework.parameter.IParameterEnv> paramEnvMap = new HashMap<String, com.jiuqi.nvwa.framework.parameter.IParameterEnv>();
    private IDataSourceProvider dsProvider;
    private boolean filterOnDsOpen = false;
    private IASTNode ast;

    public TextFormulaContext(String userGuid) {
        this.userGuid = userGuid;
    }

    public void setDSFilters(String dsName, List<FilterItem> restricts) {
        this.filterMap.put(dsName.toUpperCase(), restricts);
    }

    public void setDSTimekeyRange(String dsName, RangeValues range) {
        this.timekeyFilterMap.put(dsName.toUpperCase(), range);
    }

    public void setCurrTimekey(String dsName, String currTimekey) {
        this.currTimekeyMap.put(dsName.toUpperCase(), currTimekey);
    }

    public String getCurrTimekey(String dsName) {
        return this.currTimekeyMap.get(dsName.toUpperCase());
    }

    @Deprecated
    public void setParameterEnv(String dsName, IParameterEnv paramEnv) {
        this.setParameterEnv(dsName, new EnhancedParameterEnvAdapter(paramEnv));
    }

    public void setParameterEnv(String dsName, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) {
        this.paramEnvMap.put(dsName.toUpperCase(), paramEnv);
    }

    public void setFilterOnDsOpen(boolean filterOnDsOpen) {
        this.filterOnDsOpen = filterOnDsOpen;
    }

    public List<FilterItem> getFormulaFilter(String dsName) {
        if (dsName == null) {
            return new ArrayList<FilterItem>();
        }
        List<FilterItem> filters = this.filterMap.get(dsName.toUpperCase());
        if (filters == null) {
            return new ArrayList<FilterItem>();
        }
        return new ArrayList<FilterItem>(filters);
    }

    public RangeValues getTimekeyRange(String dsName) {
        if (dsName == null) {
            return null;
        }
        return this.timekeyFilterMap.get(dsName.toUpperCase());
    }

    public void setDsProvider(IDataSourceProvider dsProvider) {
        this.dsProvider = dsProvider;
    }

    public void _setAST(IASTNode ast) {
        this.ast = ast;
    }

    public IASTNode _getAST() {
        return this.ast;
    }

    @Deprecated
    public IParameterEnv getParameterEnv(String dsName) {
        com.jiuqi.nvwa.framework.parameter.IParameterEnv v = this.getEnhancedParameterEnv(dsName);
        return v == null ? null : ((EnhancedParameterEnvAdapter)v).getOriginalParameterEnv();
    }

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getEnhancedParameterEnv(String dsName) {
        return this.paramEnvMap.get(dsName.toUpperCase());
    }

    @Deprecated
    public Iterator<IParameterEnv> paramEnvItor() {
        ArrayList<IParameterEnv> list = new ArrayList<IParameterEnv>();
        for (com.jiuqi.nvwa.framework.parameter.IParameterEnv env : this.paramEnvMap.values()) {
            list.add(((EnhancedParameterEnvAdapter)env).getOriginalParameterEnv());
        }
        return list.iterator();
    }

    public Iterator<com.jiuqi.nvwa.framework.parameter.IParameterEnv> enhancedParamEnvItor() {
        return this.paramEnvMap.values().iterator();
    }

    public boolean isFilterOnDsOpen() {
        return this.filterOnDsOpen;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public IDataSourceProvider getDsProvider() {
        return this.dsProvider;
    }

    public void setI18nLang(String i18nLang) {
        this.i18nLang = i18nLang;
    }

    public String getI18nLang() {
        return this.i18nLang;
    }

    public TextFormulaContext clone() {
        TextFormulaContext tfc = new TextFormulaContext(this.userGuid);
        Set<Map.Entry<String, List<FilterItem>>> filterMaps = this.filterMap.entrySet();
        for (Map.Entry<String, List<FilterItem>> entry : filterMaps) {
            tfc.filterMap.put(entry.getKey(), entry.getValue());
        }
        Set<Map.Entry<String, RangeValues>> timekeyFilters = this.timekeyFilterMap.entrySet();
        for (Map.Entry<String, RangeValues> entry : timekeyFilters) {
            tfc.timekeyFilterMap.put(entry.getKey(), entry.getValue());
        }
        Set<Map.Entry<String, String>> set = this.currTimekeyMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            tfc.currTimekeyMap.put(entry.getKey(), entry.getValue());
        }
        Set<Map.Entry<String, com.jiuqi.nvwa.framework.parameter.IParameterEnv>> set2 = this.paramEnvMap.entrySet();
        for (Map.Entry<String, com.jiuqi.nvwa.framework.parameter.IParameterEnv> set3 : set2) {
            tfc.paramEnvMap.put(set3.getKey(), set3.getValue());
        }
        tfc.dsProvider = this.dsProvider;
        tfc.filterOnDsOpen = this.filterOnDsOpen;
        tfc.ast = this.ast;
        tfc.i18nLang = this.i18nLang;
        return tfc;
    }

    protected void clone(TextFormulaContext cxt) {
        this.userGuid = cxt.userGuid;
        this.filterMap = cxt.filterMap;
        this.timekeyFilterMap = cxt.timekeyFilterMap;
        this.currTimekeyMap = cxt.currTimekeyMap;
        this.paramEnvMap = cxt.paramEnvMap;
        this.dsProvider = cxt.dsProvider;
        this.filterOnDsOpen = cxt.filterOnDsOpen;
        this.ast = cxt.ast;
        this.i18nLang = cxt.i18nLang;
    }
}

