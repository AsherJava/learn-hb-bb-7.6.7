/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.data.logic.internal.dataup.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchCheckResultGroupInfo {
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

    public boolean isBatchUpload() {
        return this.isBatchUpload;
    }

    public void setBatchUpload(boolean batchUpload) {
        this.isBatchUpload = batchUpload;
    }
}

