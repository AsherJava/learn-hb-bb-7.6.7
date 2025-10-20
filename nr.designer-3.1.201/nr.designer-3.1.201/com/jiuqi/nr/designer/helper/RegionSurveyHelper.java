/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyQuestionLink
 */
package com.jiuqi.nr.designer.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionSurveyHelper {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    private static final Logger log = LoggerFactory.getLogger(RegionSurveyHelper.class);

    public String formCopyRegionSurvey3(String survey, Map<String, DesignDataLinkDefine> linkMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(survey, SurveyModel.class);
            List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
            HashMap<String, String> fieldMap = new HashMap<String, String>();
            HashMap<String, String> tableMap = new HashMap<String, String>();
            for (SurveyQuestion surveyQuestion : surveyQuestions) {
                for (SurveyQuestionLink questionLink : surveyQuestion.getLinks()) {
                    DesignDataField dataField;
                    if (null == linkMap.get(questionLink.getLinkId())) continue;
                    DesignDataLinkDefine linkDefine = linkMap.get(questionLink.getLinkId());
                    questionLink.getQuestion().setLinkId(linkDefine.getKey());
                    List oldZBs = questionLink.getZb();
                    if (oldZBs.size() != 3) continue;
                    if (fieldMap.get(linkDefine.getLinkExpression()) == null && null != (dataField = this.designDataSchemeService.getDataField(linkDefine.getLinkExpression()))) {
                        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
                        tableMap.put(questionLink.getLinkId(), dataTable.getCode());
                        fieldMap.put(questionLink.getLinkId(), dataField.getCode());
                    }
                    String newFieldCode = (String)fieldMap.get(questionLink.getLinkId());
                    String newTableCode = (String)tableMap.get(questionLink.getLinkId());
                    if (StringUtils.isEmpty((String)newFieldCode) || StringUtils.isEmpty((String)newTableCode)) continue;
                    if (!((String)oldZBs.get(0)).equals(newTableCode)) {
                        oldZBs.set(0, newTableCode);
                    }
                    if (!((String)oldZBs.get(1)).equals(newFieldCode)) {
                        oldZBs.set(1, newFieldCode);
                    }
                    questionLink.getQuestion().setZb(oldZBs);
                }
            }
            survey = this.toStyle(surveyModel);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return survey;
    }

    public String formCopyRegionSurvey(String survey, Map<String, DesignDataLinkDefine> linkMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(survey, SurveyModel.class);
            List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
            for (SurveyQuestion surveyQuestion : surveyQuestions) {
                for (SurveyQuestionLink questionLink : surveyQuestion.getLinks()) {
                    if (null == linkMap.get(questionLink.getLinkId())) continue;
                    questionLink.getQuestion().setLinkId(linkMap.get(questionLink.getLinkId()).getKey());
                }
            }
            survey = this.toStyle(surveyModel);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return survey;
    }

    public String formCopyRegionSurvey2(String survey, Map<String, String> linkMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(survey, SurveyModel.class);
            List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
            for (SurveyQuestion surveyQuestion : surveyQuestions) {
                for (SurveyQuestionLink questionLink : surveyQuestion.getLinks()) {
                    if (null == linkMap.get(questionLink.getLinkId())) continue;
                    questionLink.getQuestion().setLinkId(linkMap.get(questionLink.getLinkId()));
                }
            }
            survey = this.toStyle(surveyModel);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return survey;
    }

    public String toStyle(SurveyModel surveyModel) throws JQException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            return objectMapper.writeValueAsString((Object)surveyModel);
        }
        catch (Exception e) {
            log.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231);
        }
    }
}

