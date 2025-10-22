/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.OtherChoice;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.List;

public class ChoiceQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        String type = element.getType();
        return QuestionType.RADIOGROUP.value().equals(type) || QuestionType.CHECKBOX.value().equals(type) || QuestionType.DROPDOWN.value().equals(type) || QuestionType.TAGBOX.value().equals(type);
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        IChoicesQuestion choicesQuestion = (IChoicesQuestion)((Object)element);
        String linkId = element.getLinkId();
        List<String> zb = element.getZb();
        String type = element.getType();
        String name = element.getName();
        String title = null != element.getTitle() && element.getTitle().length() > 0 ? element.getTitle() : element.getName();
        SurveyQuestionLink surveyQuestionLink = new SurveyQuestionLink(element, QuestionType.fromValue(type), name, zb, linkId, title);
        surveyQuestionLink.setFilterFormula(choicesQuestion.getFilterFormula());
        SurveyQuestion question = new SurveyQuestion(name, QuestionType.fromValue(type), surveyQuestionLink);
        if (choicesQuestion.isShowOtherItem()) {
            OtherChoice other = choicesQuestion.getOther();
            if (null == other) {
                other = new OtherChoice();
                choicesQuestion.setOther(other);
            }
            List<SurveyQuestionLink> links = question.getLinks();
            SurveyQuestionLink otherQuestionLink = new SurveyQuestionLink(other, QuestionType.TEXT, name + "_other", other.getZb(), other.getLinkId(), title + "_\u5176\u4ed6\u9009\u9879");
            links.add(otherQuestionLink);
        }
        return question;
    }
}

