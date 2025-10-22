/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.paramInfo.CustomCheckFilter;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchCheckInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private String asyncTaskKey;
    private boolean allDimResult = false;
    private JtableContext context;
    private String formulaSchemeKeys;
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    private PagerInfo pagerInfo;
    private String orderField;
    private boolean affectCommit = false;
    private List<Integer> checkTypes = new ArrayList<Integer>();
    private List<Integer> uploadCheckTypes = new ArrayList<Integer>();
    private boolean checkDesNull = true;
    private boolean checkDesPass = true;
    private boolean checkDesMustPass = true;
    private Map<Integer, Integer> checkDesCheckTypes;
    private boolean effectUpload = false;
    private boolean noMatterDes;
    private String filterCondition;
    private boolean workFlowCheck = false;
    private String dwScope;
    private CustomCheckFilter customCheckFilter;
    private List<Integer> chooseTypes = new ArrayList<Integer>();
    private DUserActionParam dUserActionParam;
    private boolean changeMonitorState = true;
    private String descriptionFilterType;
    private String descriptionFilterContent;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getDescriptionFilterType() {
        return this.descriptionFilterType;
    }

    public void setDescriptionFilterType(String descriptionFilterType) {
        this.descriptionFilterType = descriptionFilterType;
    }

    public String getDescriptionFilterContent() {
        return this.descriptionFilterContent;
    }

    public void setDescriptionFilterContent(String descriptionFilterContent) {
        this.descriptionFilterContent = descriptionFilterContent;
    }

    public boolean isCheckDesPass() {
        return this.checkDesPass;
    }

    public void setCheckDesPass(boolean checkDesPass) {
        this.checkDesPass = checkDesPass;
    }

    public boolean isCheckDesMustPass() {
        return this.checkDesMustPass;
    }

    public void setCheckDesMustPass(boolean checkDesMustPass) {
        this.checkDesMustPass = checkDesMustPass;
    }

    public Map<Integer, Integer> getCheckDesCheckTypes() {
        return this.checkDesCheckTypes;
    }

    public void setCheckDesCheckTypes(Map<Integer, Integer> checkDesCheckTypes) {
        this.checkDesCheckTypes = checkDesCheckTypes;
    }

    public boolean isEffectUpload() {
        return this.effectUpload;
    }

    public void setEffectUpload(boolean effectUpload) {
        this.effectUpload = effectUpload;
    }

    @JsonIgnore
    public List<Variable> getVariables() {
        return this.context.getVariables();
    }

    public JtableContext getContext() {
        this.context.setFormulaSchemeKey(this.formulaSchemeKeys);
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public Map<String, List<String>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<String>> formulas) {
        this.formulas = formulas;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public String getOrderField() {
        return this.orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean isAffectCommit() {
        return this.affectCommit;
    }

    public void setAffectCommit(boolean affectCommit) {
        this.affectCommit = affectCommit;
    }

    public List<Integer> getCheckTypes() {
        return this.checkTypes;
    }

    public void setCheckTypes(List<Integer> checkTypes) {
        this.checkTypes = checkTypes;
    }

    public List<Integer> getUploadCheckTypes() {
        return this.uploadCheckTypes;
    }

    public void setUploadCheckTypes(List<Integer> uploadCheckTypes) {
        this.uploadCheckTypes = uploadCheckTypes;
    }

    public boolean isCheckDesNull() {
        return this.checkDesNull;
    }

    public void setCheckDesNull(boolean checkDesNull) {
        this.checkDesNull = checkDesNull;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public boolean isWorkFlowCheck() {
        return this.workFlowCheck;
    }

    public void setWorkFlowCheck(boolean workFlowCheck) {
        this.workFlowCheck = workFlowCheck;
    }

    public String getDwScope() {
        return this.dwScope;
    }

    public void setDwScope(String dwScope) {
        this.dwScope = dwScope;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6267\u884c\u6279\u91cf\u5ba1\u6838");
        Map other = logParam.getOrherMsg();
        if (!this.formulas.isEmpty()) {
            Set<String> formKeys = this.formulas.keySet();
            StringBuffer form = new StringBuffer();
            formKeys.forEach(e -> form.append((String)e).append(";"));
            other.put("formKeys", form.substring(0, form.length() - 1));
        }
        return logParam;
    }

    public List<Integer> getChooseTypes() {
        return this.chooseTypes;
    }

    public void setChooseTypes(List<Integer> chooseTypes) {
        this.chooseTypes = chooseTypes;
    }

    public CustomCheckFilter getCustomCheckFilter() {
        return this.customCheckFilter;
    }

    public void setCustomCheckFilter(CustomCheckFilter customCheckFilter) {
        this.customCheckFilter = customCheckFilter;
    }

    public boolean isNoMatterDes() {
        return this.noMatterDes;
    }

    public void setNoMatterDes(boolean noMatterDes) {
        this.noMatterDes = noMatterDes;
    }

    public boolean isChangeMonitorState() {
        return this.changeMonitorState;
    }

    public void setChangeMonitorState(boolean changeMonitorState) {
        this.changeMonitorState = changeMonitorState;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }

    public boolean isAllDimResult() {
        return this.allDimResult;
    }

    public void setAllDimResult(boolean allDimResult) {
        this.allDimResult = allDimResult;
    }

    public DUserActionParam getdUserActionParam() {
        return this.dUserActionParam;
    }

    public void setdUserActionParam(DUserActionParam dUserActionParam) {
        this.dUserActionParam = dUserActionParam;
    }
}

