/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveySaveVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyProblemHandleVO;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import java.util.List;

public interface ISurveyDesignerService {
    public DesignDataField getFieldByValueName(List<String> var1);

    public List<SurveyQuestion> saveOrUpdateLinkId(RequestSurveySaveVO var1);

    public List<ResponseSurveyCheckVO> checkSurveyModel(RequestSurveySaveVO var1);

    public ResponseSurveyProblemHandleVO problemHandle(RequestSurveyProblemHandleVO var1);
}

