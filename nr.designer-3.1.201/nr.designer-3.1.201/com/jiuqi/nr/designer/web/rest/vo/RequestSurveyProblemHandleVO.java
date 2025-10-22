/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.RequestSurveySaveVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckDTO;
import java.util.List;

public class RequestSurveyProblemHandleVO
extends RequestSurveySaveVO {
    private static final long serialVersionUID = 1L;
    private List<ResponseSurveyCheckDTO> items;

    public List<ResponseSurveyCheckDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ResponseSurveyCheckDTO> items) {
        this.items = items;
    }
}

