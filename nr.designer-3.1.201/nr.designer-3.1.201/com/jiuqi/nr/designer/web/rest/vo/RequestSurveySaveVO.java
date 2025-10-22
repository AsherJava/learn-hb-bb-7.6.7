/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.survey.model.SurveyModel
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.survey.model.SurveyModel;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestSurveySaveVO
implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(RequestSurveySaveVO.class);
    private static final long serialVersionUID = 1L;
    private String style;
    @JsonIgnore
    private SurveyModel _surveyModel;
    private String dataSchemeId;
    private String taskId;
    private String formId;

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDataSchemeId() {
        return this.dataSchemeId;
    }

    public void setDataSchemeId(String dataSchemeId) {
        this.dataSchemeId = dataSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public SurveyModel get_surveyModel() {
        if (null == this._surveyModel) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                this._surveyModel = (SurveyModel)objectMapper.readValue(this.getStyle(), SurveyModel.class);
            }
            catch (Exception e) {
                log.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e);
            }
        }
        return this._surveyModel;
    }

    @JsonIgnore
    public void _surveyConvertStyle() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            this.setStyle(objectMapper.writeValueAsString((Object)this._surveyModel));
        }
        catch (Exception e) {
            log.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e);
        }
    }

    public void set_surveyModel(SurveyModel _surveyModel) {
        this._surveyModel = _surveyModel;
    }
}

