/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.transmission.data.intf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataImportResult
implements Serializable {
    int syncErrorNum = 0;
    boolean result = true;
    String log;
    List<DataImportMessage> successEntity = new ArrayList<DataImportMessage>();
    Map<String, List<DataImportMessage>> failUnits = new HashMap<String, List<DataImportMessage>>();
    Map<String, List<DataImportMessage>> failForms = new HashMap<String, List<DataImportMessage>>();
    List<DataImportMessage> successForm = new ArrayList<DataImportMessage>();
    Map<String, List<DataImportMessage>> failFormulaCheckSchemes = new HashMap<String, List<DataImportMessage>>();
    List<DataImportMessage> successFormulaCheckScheme = new ArrayList<DataImportMessage>();
    List<DataImportMessage> failUploadEntity = new ArrayList<DataImportMessage>();

    public DataImportResult() {
    }

    public DataImportResult(boolean result) {
        this.result = result;
    }

    public int getSyncErrorNum() {
        return this.syncErrorNum;
    }

    public void setSyncErrorNum(int syncErrorNum) {
        this.syncErrorNum = syncErrorNum;
    }

    public void setSyncErrorNumInc() {
        ++this.syncErrorNum;
    }

    public void sum(DataImportResult sourceResult) {
        this.setSyncErrorNum(this.syncErrorNum + sourceResult.getSyncErrorNum());
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<DataImportMessage> getSuccessEntity() {
        return this.successEntity;
    }

    public void setSuccessEntity(List<DataImportMessage> successEntity) {
        this.successEntity = successEntity;
    }

    public Map<String, List<DataImportMessage>> getFailUnits() {
        return this.failUnits;
    }

    public void setFailUnits(Map<String, List<DataImportMessage>> failUnits) {
        this.failUnits = failUnits;
    }

    public Map<String, List<DataImportMessage>> getFailForms() {
        return this.failForms;
    }

    public void setFailForms(Map<String, List<DataImportMessage>> failForms) {
        this.failForms = failForms;
    }

    public List<DataImportMessage> getSuccessForm() {
        return this.successForm;
    }

    public void setSuccessForm(List<DataImportMessage> successForm) {
        this.successForm = successForm;
    }

    public Map<String, List<DataImportMessage>> getFailFormulaCheckSchemes() {
        return this.failFormulaCheckSchemes;
    }

    public void setFailFormulaCheckSchemes(Map<String, List<DataImportMessage>> failFormulaCheckSchemes) {
        this.failFormulaCheckSchemes = failFormulaCheckSchemes;
    }

    public List<DataImportMessage> getSuccessFormulaCheckScheme() {
        return this.successFormulaCheckScheme;
    }

    public void setSuccessFormulaCheckScheme(List<DataImportMessage> successFormulaCheckScheme) {
        this.successFormulaCheckScheme = successFormulaCheckScheme;
    }

    public List<DataImportMessage> getFailUploadEntity() {
        return this.failUploadEntity;
    }

    public void setFailUploadEntity(List<DataImportMessage> failUploadEntity) {
        this.failUploadEntity = failUploadEntity;
    }

    public void addSuccessEntity(String title, String code, String message) {
        DataImportMessage dataImportMessage = new DataImportMessage(title, code, message);
        this.successEntity.add(dataImportMessage);
    }

    public void addFailUnits(Map<String, List<DataImportMessage>> failUnit) {
        if (!CollectionUtils.isEmpty(failUnit)) {
            for (Map.Entry<String, List<DataImportMessage>> stringListEntry : failUnit.entrySet()) {
                if (CollectionUtils.isEmpty((Collection)stringListEntry.getValue())) continue;
                this.failUnits.computeIfAbsent(stringListEntry.getKey(), key -> new ArrayList()).addAll((Collection)stringListEntry.getValue());
            }
        }
    }

    public void addFailForms(Map<String, List<DataImportMessage>> failForm) {
        if (!CollectionUtils.isEmpty(failForm)) {
            for (Map.Entry<String, List<DataImportMessage>> stringListEntry : failForm.entrySet()) {
                if (CollectionUtils.isEmpty((Collection)stringListEntry.getValue())) continue;
                this.failForms.computeIfAbsent(stringListEntry.getKey(), key -> new ArrayList()).addAll((Collection)stringListEntry.getValue());
            }
        }
    }

    public void addSuccessForm(String title, String code, String formKey, String message) {
        DataImportMessage dataImportMessage = new DataImportMessage(title, code, formKey, message);
        this.successForm.add(dataImportMessage);
    }

    public void addFailFormulaCheckSchemes(Map<String, List<DataImportMessage>> failFormulaCheckScheme) {
        if (!CollectionUtils.isEmpty(failFormulaCheckScheme)) {
            for (Map.Entry<String, List<DataImportMessage>> stringListEntry : failFormulaCheckScheme.entrySet()) {
                if (CollectionUtils.isEmpty((Collection)stringListEntry.getValue())) continue;
                this.failFormulaCheckSchemes.computeIfAbsent(stringListEntry.getKey(), key -> new ArrayList()).addAll((Collection)stringListEntry.getValue());
            }
        }
    }

    public void addSuccessFormulaCheckScheme(String title, String code, String formKey, String message) {
        DataImportMessage dataImportMessage = new DataImportMessage(title, code, formKey, message);
        this.successFormulaCheckScheme.add(dataImportMessage);
    }

    public void addFailUploadEntity(String title, String code, String message) {
        DataImportMessage dataImportMessage = new DataImportMessage(title, code, message);
        this.failUploadEntity.add(dataImportMessage);
    }
}

