/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;

public class MidstoreResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String message;
    private String resultKey;
    private List<MistoreWorkResultObject> workResults;

    public MidstoreResultObject() {
    }

    public MidstoreResultObject(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MidstoreResultObject(boolean success, String message, String resultKey) {
        this.success = success;
        this.message = message;
        this.resultKey = resultKey;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultKey() {
        return this.resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public List<MistoreWorkResultObject> getWorkResults() {
        if (this.workResults == null) {
            this.workResults = new ArrayList<MistoreWorkResultObject>();
        }
        return this.workResults;
    }

    public void setWorkResults(List<MistoreWorkResultObject> workResults) {
        this.workResults = workResults;
    }
}

