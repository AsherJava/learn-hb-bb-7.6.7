/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.anal;

import com.jiuqi.nr.single.core.para.FormulaInfo;
import com.jiuqi.nr.single.core.para.FormulaVariableInfo;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalGetInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnalParaInfo {
    List<AnalTableSet> rootTableSets;
    Map<String, AnalTableSet> analTabsetList;
    Map<String, AnalTableInfo> analTableList;
    private Map<String, List<FormulaInfo>> formulas;
    private List<FormulaVariableInfo> formulaVariables;
    private Map<String, List<FormulaInfo>> fetchFormulas;
    private List<String> fmZbList;
    private AnalGetInfo analGetInfo;
    private ParaInfo analParaInfo;
    private List<AnalParaInfo> subAnalInfos;
    private ParaInfo baseParaInfo;
    private String filterFormula;

    public List<AnalTableSet> getRootTableSets() {
        if (this.rootTableSets == null) {
            this.rootTableSets = new ArrayList<AnalTableSet>();
        }
        return this.rootTableSets;
    }

    public void setRootTableSets(List<AnalTableSet> rootTableSets) {
        this.rootTableSets = rootTableSets;
    }

    public Map<String, AnalTableSet> getAnalTabsetList() {
        if (this.analTabsetList == null) {
            this.analTabsetList = new LinkedHashMap<String, AnalTableSet>();
        }
        return this.analTabsetList;
    }

    public void setAnalTabsetList(Map<String, AnalTableSet> analTabsetList) {
        this.analTabsetList = analTabsetList;
    }

    public Map<String, AnalTableInfo> getAnalTableList() {
        if (this.analTableList == null) {
            this.analTableList = new LinkedHashMap<String, AnalTableInfo>();
        }
        return this.analTableList;
    }

    public void setAnalTableList(Map<String, AnalTableInfo> analTableList) {
        this.analTableList = analTableList;
    }

    public Map<String, List<FormulaInfo>> getFormulas() {
        if (this.formulas == null) {
            this.formulas = new LinkedHashMap<String, List<FormulaInfo>>();
        }
        return this.formulas;
    }

    public void setFormulas(Map<String, List<FormulaInfo>> formulas) {
        this.formulas = formulas;
    }

    public List<String> getFmZbList() {
        if (this.fmZbList == null) {
            this.fmZbList = new ArrayList<String>();
        }
        return this.fmZbList;
    }

    public void setFmZbList(List<String> fmZbList) {
        this.fmZbList = fmZbList;
    }

    public ParaInfo getAnalParaInfo() {
        return this.analParaInfo;
    }

    public void setAnalParaInfo(ParaInfo analParaInfo) {
        this.analParaInfo = analParaInfo;
    }

    public ParaInfo getBaseParaInfo() {
        return this.baseParaInfo;
    }

    public void setBaseParaInfo(ParaInfo baseParaInfo) {
        this.baseParaInfo = baseParaInfo;
    }

    public Map<String, List<FormulaInfo>> getFetchFormulas() {
        if (this.fetchFormulas == null) {
            this.fetchFormulas = new LinkedHashMap<String, List<FormulaInfo>>();
        }
        return this.fetchFormulas;
    }

    public void setFetchFormulas(Map<String, List<FormulaInfo>> fetchFormulas) {
        if (this.fetchFormulas == null) {
            this.fetchFormulas = new LinkedHashMap<String, List<FormulaInfo>>();
        }
        this.fetchFormulas = fetchFormulas;
    }

    public List<AnalParaInfo> getSubAnalInfos() {
        if (this.subAnalInfos == null) {
            this.subAnalInfos = new ArrayList<AnalParaInfo>();
        }
        return this.subAnalInfos;
    }

    public void setSubAnalInfos(List<AnalParaInfo> subAnalInfos) {
        this.subAnalInfos = subAnalInfos;
    }

    public AnalGetInfo getAnalGetInfo() {
        return this.analGetInfo;
    }

    public void setAnalGetInfo(AnalGetInfo analGetInfo) {
        this.analGetInfo = analGetInfo;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public List<FormulaVariableInfo> getFormulaVariables() {
        if (this.formulaVariables == null) {
            this.formulaVariables = new ArrayList<FormulaVariableInfo>();
        }
        return this.formulaVariables;
    }

    public void setFormulaVariables(List<FormulaVariableInfo> formulaVariables) {
        this.formulaVariables = formulaVariables;
    }
}

