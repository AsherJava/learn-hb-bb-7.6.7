/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.paramInfo.CustomCheckFilter;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchCheckResultGroupInfo
extends NRContext {
    private String asyncTaskKey;
    private JtableContext context;
    private String formulaSchemeKeys;
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    private PagerInfo pagerInfo;
    private String orderField;
    private boolean affectCommit = false;
    private boolean isBatchUpload;
    private List<Integer> checkTypes = new ArrayList<Integer>();
    private List<Integer> uploadCheckTypes = new ArrayList<Integer>();
    private boolean checkDesNull = true;
    private boolean checkDesPass = true;
    private boolean checkDesMustPass = true;
    private Map<Integer, Integer> checkDesCheckTypes;
    private boolean effectUpload = false;
    private CustomCheckFilter customCheckFilter;
    private List<Integer> chooseTypes = new ArrayList<Integer>();
    private DUserActionParam dUserActionParam;
    private String descriptionFilterType;
    private String descriptionFilterContent;

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

    public String getOrderField() {
        return this.orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
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

    public boolean isAffectCommit() {
        return this.affectCommit;
    }

    public void setAffectCommit(boolean affectCommit) {
        this.affectCommit = affectCommit;
    }

    public boolean getIsBatchUpload() {
        return this.isBatchUpload;
    }

    public void setIsBatchUpload(boolean batchUpload) {
        this.isBatchUpload = batchUpload;
    }

    public CustomCheckFilter getCustomCheckFilter() {
        return this.customCheckFilter;
    }

    public void setCustomCheckFilter(CustomCheckFilter customCheckFilter) {
        this.customCheckFilter = customCheckFilter;
    }

    public boolean isBatchUpload() {
        return this.isBatchUpload;
    }

    public void setBatchUpload(boolean batchUpload) {
        this.isBatchUpload = batchUpload;
    }

    public List<Integer> getChooseTypes() {
        return this.chooseTypes;
    }

    public void setChooseTypes(List<Integer> chooseTypes) {
        this.chooseTypes = chooseTypes;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }

    public DUserActionParam getdUserActionParam() {
        return this.dUserActionParam;
    }

    public void setdUserActionParam(DUserActionParam dUserActionParam) {
        this.dUserActionParam = dUserActionParam;
    }
}

