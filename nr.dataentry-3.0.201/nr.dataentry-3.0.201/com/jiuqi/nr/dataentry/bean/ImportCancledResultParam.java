/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ImportCancledResultParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CANCLED_RESULT_KEY = "importCancledResult";
    private static final String SUCCESS_COUNT = "successCount";
    private static final String SUCCESS_TYPE = "successType";
    private final Map<String, String> param = new HashMap<String, String>();

    public ImportCancledResultParam(String successCount, String successType) {
        this.param.put(SUCCESS_COUNT, successCount);
        this.param.put(SUCCESS_TYPE, successType);
    }

    public String getCode() {
        return CANCLED_RESULT_KEY;
    }

    public Map<String, String> getParam() {
        return this.param;
    }
}

