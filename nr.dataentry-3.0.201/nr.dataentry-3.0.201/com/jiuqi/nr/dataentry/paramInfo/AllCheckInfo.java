/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.paramInfo.CustomCheckFilter;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCheckInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private String asyncTaskKey;
    private JtableContext context;
    private String formulaSchemeKeys;
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    private PagerInfo pagerInfo;
    private boolean affectCommit = false;
    private List<Integer> checkTypes = new ArrayList<Integer>();
    private List<Integer> uploadCheckTypes = new ArrayList<Integer>();
    private boolean checkDesNull = true;
    private String filterCondition;
    private boolean workFlowCheck = false;
    private CustomCheckFilter customCheckFilter;
    private List<Integer> chooseTypes = new ArrayList<Integer>();
    private String actionCode;
    private DUserActionParam dUserActionParam;
    private boolean searchByFormula;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public boolean isSearchByFormula() {
        return this.searchByFormula;
    }

    public void setSearchByFormula(boolean searchByFormula) {
        this.searchByFormula = searchByFormula;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }

    public JtableContext getContext() {
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

    public CustomCheckFilter getCustomCheckFilter() {
        return this.customCheckFilter;
    }

    public void setCustomCheckFilter(CustomCheckFilter customCheckFilter) {
        this.customCheckFilter = customCheckFilter;
    }

    public List<Integer> getChooseTypes() {
        return this.chooseTypes;
    }

    public void setChooseTypes(List<Integer> chooseTypes) {
        this.chooseTypes = chooseTypes;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public DUserActionParam getdUserActionParam() {
        return this.dUserActionParam;
    }

    public void setdUserActionParam(DUserActionParam dUserActionParam) {
        this.dUserActionParam = dUserActionParam;
    }
}

