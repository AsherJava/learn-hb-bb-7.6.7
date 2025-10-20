/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.template.dto.print.QueryPrintPluginDTO;
import com.jiuqi.va.query.template.plugin.QueryFormulaImpl;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class TemplateContentVO {
    private TemplateInfoVO template;
    private TemplateDataSourceSetVO dataSourceSet;
    private List<TemplateParamsVO> params;
    private List<TemplateFieldSettingVO> fields;
    private List<TemplateToolbarInfoVO> tools;
    private List<TemplateRelateQueryVO> relateQuerys;
    private Map<String, Object> viewSets;
    private String designSets = "";
    private List<QueryFormulaImpl> formulas = new ArrayList<QueryFormulaImpl>();
    private Map<String, Object> formulaValues = new HashMap<String, Object>();
    private QueryPrintPluginDTO queryPrintPluginDto;

    public List<TemplateParamsVO> getParams() {
        return this.params;
    }

    public void setParams(List<TemplateParamsVO> params) {
        this.params = params;
    }

    public List<TemplateFieldSettingVO> getFields() {
        return this.fields;
    }

    public void setFields(List<TemplateFieldSettingVO> fields) {
        this.fields = fields;
    }

    public TemplateInfoVO getTemplate() {
        return this.template;
    }

    public void setTemplate(TemplateInfoVO template) {
        this.template = template;
    }

    public TemplateDataSourceSetVO getDataSourceSet() {
        return this.dataSourceSet;
    }

    public void setDataSourceSet(TemplateDataSourceSetVO dataSourceSet) {
        this.dataSourceSet = dataSourceSet;
    }

    public List<TemplateToolbarInfoVO> getTools() {
        return this.tools;
    }

    public void setTools(List<TemplateToolbarInfoVO> tools) {
        this.tools = tools;
    }

    public List<TemplateRelateQueryVO> getRelateQuerys() {
        return this.relateQuerys;
    }

    public void setRelateQuerys(List<TemplateRelateQueryVO> relateQuerys) {
        this.relateQuerys = relateQuerys;
    }

    public Map<String, Object> getViewSets() {
        return this.viewSets;
    }

    public void setViewSets(Map<String, Object> viewSets) {
        this.viewSets = viewSets;
    }

    public QueryPrintPluginDTO getQueryPrintPluginDto() {
        return this.queryPrintPluginDto;
    }

    public void setQueryPrintPluginDto(QueryPrintPluginDTO queryPrintPluginDto) {
        this.queryPrintPluginDto = queryPrintPluginDto;
    }

    public String getDesignSets() {
        return this.designSets;
    }

    public void setDesignSets(String designSets) {
        this.designSets = designSets;
    }

    public List<QueryFormulaImpl> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<QueryFormulaImpl> formulas) {
        this.formulas = formulas;
    }

    public Map<String, Object> getFormulaValues() {
        return this.formulaValues;
    }

    public void setFormulaValues(Map<String, Object> formulaValues) {
        this.formulaValues = formulaValues;
    }

    public String toString() {
        return "TemplateContentVO{template=" + this.template + ", dataSourceSet=" + this.dataSourceSet + ", params=" + this.params + ", fields=" + this.fields + ", tools=" + this.tools + ", relateQuerys=" + this.relateQuerys + ", viewSets=" + this.viewSets + ", designSets='" + this.designSets + '\'' + ", formulas=" + this.formulas + ", formulaValues=" + this.formulaValues + ", queryPrintPluginDto=" + this.queryPrintPluginDto + '}';
    }
}

