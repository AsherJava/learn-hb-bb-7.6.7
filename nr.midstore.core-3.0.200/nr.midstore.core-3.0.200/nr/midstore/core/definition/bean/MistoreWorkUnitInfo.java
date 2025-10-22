/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;

public class MistoreWorkUnitInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean success;
    private String periodCode;
    private String unitCode;
    private String unitTitle;
    Map<String, MistoreWorkFormInfo> formInfos;
    Map<String, MistoreWorkFormInfo> tableInfos;

    public MistoreWorkUnitInfo() {
    }

    public MistoreWorkUnitInfo(String unitCode, String unitTitle) {
        this.unitCode = unitCode;
        this.unitTitle = unitTitle;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public Map<String, MistoreWorkFormInfo> getFormInfos() {
        if (this.formInfos == null) {
            this.formInfos = new HashMap<String, MistoreWorkFormInfo>();
        }
        return this.formInfos;
    }

    public void setFormInfos(Map<String, MistoreWorkFormInfo> formInfos) {
        this.formInfos = formInfos;
    }

    public Map<String, MistoreWorkFormInfo> getTableInfos() {
        if (this.tableInfos == null) {
            this.tableInfos = new HashMap<String, MistoreWorkFormInfo>();
        }
        return this.tableInfos;
    }

    public void setTableInfos(Map<String, MistoreWorkFormInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

