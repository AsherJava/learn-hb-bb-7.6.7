/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.PanelDynamic;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.List;

public class PanelDynamicQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        return QuestionType.PANELDYNAMIC.value().equals(element.getType());
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        PanelDynamic question = (PanelDynamic)element;
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setName(question.getName());
        surveyQuestion.setType(QuestionType.PANELDYNAMIC);
        List<Element> templateElements = question.getTemplateElements();
        if (null != templateElements && !templateElements.isEmpty()) {
            ArrayList<SurveyQuestionLink> links = new ArrayList<SurveyQuestionLink>();
            surveyQuestion.setLinks(links);
            List<ISurveyQuestionLinkProvider> providers = SurveyModelLinkHelp.getProviders();
            for (Element templateElement : templateElements) {
                for (ISurveyQuestionLinkProvider provider : providers) {
                    SurveyQuestion tempSurveyQuestion;
                    if (!provider.accept(templateElement) || null == (tempSurveyQuestion = provider.getLinks(templateElement))) continue;
                    List<SurveyQuestionLink> tempLinks = tempSurveyQuestion.getLinks();
                    for (SurveyQuestionLink tempLink : tempLinks) {
                        tempLink.setMatrix(true);
                    }
                    links.addAll(tempLinks);
                }
            }
        }
        return surveyQuestion;
    }
}

