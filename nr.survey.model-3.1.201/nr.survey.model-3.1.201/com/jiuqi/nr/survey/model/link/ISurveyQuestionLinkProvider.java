/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;

public interface ISurveyQuestionLinkProvider {
    public boolean accept(Element var1);

    public SurveyQuestion getLinks(Element var1);
}

