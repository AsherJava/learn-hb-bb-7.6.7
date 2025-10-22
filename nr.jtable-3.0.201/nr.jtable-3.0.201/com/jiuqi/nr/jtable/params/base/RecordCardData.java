/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.survey.model.SurveyModel
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.survey.model.SurveyModel;

public class RecordCardData {
    private RecordCard recordCard;
    private String surveyCard;
    private String cardType;
    private String cardTitle;

    public RecordCardData(RecordCard cardRecord) {
        this.recordCard = cardRecord;
        this.cardType = "1";
        this.cardTitle = "\u5361\u7247\u5f55\u5165";
    }

    public RecordCardData(String surveyCard) {
        this.surveyCard = surveyCard;
        this.cardType = "2";
        this.cardTitle = "\u5361\u7247\u5f55\u5165";
        this.setSurveryTitle();
    }

    private void setSurveryTitle() {
        if (StringUtils.isNotEmpty((String)this.surveyCard)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            try {
                SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(this.surveyCard, SurveyModel.class);
                String title = surveyModel.getTitle();
                if (StringUtils.isNotEmpty((String)title)) {
                    this.cardTitle = surveyModel.getTitle();
                }
            }
            catch (Exception e) {
                this.cardTitle = "\u5361\u7247\u5f55\u5165";
            }
        }
    }

    public RecordCardData() {
    }

    public RecordCard getRecordCard() {
        return this.recordCard;
    }

    public void setRecordCard(RecordCard recordCard) {
        this.recordCard = recordCard;
    }

    public String getSurveyCard() {
        return this.surveyCard;
    }

    public void setSurveyCard(String surveyCard) {
        this.surveyCard = surveyCard;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTitle() {
        return this.cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }
}

