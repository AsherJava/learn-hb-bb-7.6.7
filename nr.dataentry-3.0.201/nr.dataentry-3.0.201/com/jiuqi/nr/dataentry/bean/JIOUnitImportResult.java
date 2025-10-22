/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JIOUnitImportResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unitKey;
    private String unitCode;
    private String unitTitle;
    private String message;
    private Map<String, JIOFormImportResult> formMapResults = new HashMap<String, JIOFormImportResult>();
    private List<JIOFormImportResult> formResults = new ArrayList<JIOFormImportResult>();

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public Map<String, JIOFormImportResult> getFormMapResults() {
        return this.formMapResults;
    }

    public void setFormMapResults(Map<String, JIOFormImportResult> formMapResults) {
        this.formMapResults = formMapResults;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public List<JIOFormImportResult> getFormResults() {
        return this.formResults;
    }

    public void setFormResults(List<JIOFormImportResult> formResults) {
        this.formResults = formResults;
    }
}

