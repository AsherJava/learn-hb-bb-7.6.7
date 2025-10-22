/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.SurveyErrorType;
import java.util.Map;

public class SurveyProblemHandleVO {
    private String id;
    private String message;
    private SurveyErrorType errorType;
    private Map<String, Object> datas;

    public SurveyProblemHandleVO() {
    }

    public SurveyProblemHandleVO(String id, String message, SurveyErrorType errorType) {
        this.id = id;
        this.message = message;
        this.errorType = errorType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public SurveyErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(SurveyErrorType errorType) {
        this.errorType = errorType;
    }
}

