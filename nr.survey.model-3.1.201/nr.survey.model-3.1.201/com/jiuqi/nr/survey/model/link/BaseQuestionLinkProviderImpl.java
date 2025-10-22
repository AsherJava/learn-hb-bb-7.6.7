/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.common.QuestionCellType;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.List;

public class BaseQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        String type = element.getType();
        return QuestionType.NUMBER.value().equals(type) || QuestionType.TEXT.value().equals(type) || QuestionType.PERIOD.value().equals(type) || QuestionType.BOOLEAN.value().equals(type) || QuestionType.PICTURE.value().equals(type) || QuestionType.FILE.value().equals(type) || QuestionType.FILEPOOL.value().equals(type) || QuestionType.COMMENT.value().equals(type);
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        String linkId = element.getLinkId();
        List<String> zb = element.getZb();
        String type = element.getType();
        String title = null != element.getTitle() && element.getTitle().length() > 0 ? element.getTitle() : element.getName();
        SurveyQuestionLink link = new SurveyQuestionLink(element, QuestionType.fromValue(type), element.getName(), zb, linkId, title);
        String filterFormula = "";
        if (QuestionCellType.DROPDOWN.value().equals(type) || QuestionCellType.CHECKBOX.value().equals(type) || QuestionCellType.RADIOGROUP.value().equals(type) || QuestionCellType.TAGBOX.value().equals(type)) {
            filterFormula = ((IChoicesQuestion)((Object)element)).getFilterFormula();
        }
        link.setFilterFormula(filterFormula);
        return new SurveyQuestion(element.getName(), QuestionType.fromValue(type), link);
    }
}

