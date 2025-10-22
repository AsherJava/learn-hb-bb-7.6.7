/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.BlankQuestion;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.Item;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlankQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        return QuestionType.BLANK.value().equals(element.getType());
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        String questionTitle;
        BlankQuestion blank = (BlankQuestion)element;
        List<Item> items = blank.getItems();
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setName(blank.getName());
        surveyQuestion.setType(QuestionType.BLANK);
        String string = questionTitle = null != blank.getTitle() && blank.getTitle().length() > 0 ? blank.getTitle() : blank.getName();
        if (null != items && !items.isEmpty()) {
            ArrayList<SurveyQuestionLink> links = new ArrayList<SurveyQuestionLink>();
            surveyQuestion.setLinks(links);
            String blankText = blank.getBlankText();
            if (null == blankText || blankText.length() == 0) {
                blankText = "\u59d3\u540d\uff1a<input type=\\\"text\\\" kd=\\\"default_1\\\" link=\\\"default_1\\\" aria-label=\\\"\u7b2c1\u4e2a\u7a7a\u683c\\\" style=\\\"width: 63.5px\\\"><br> \u5e74\u9f84\uff1a<input type=\\\"text\\\" kd=\\\"default_2\\\" link=\\\"default_2\\\" aria-label=\\\"\u7b2c2\u4e2a\u7a7a\u683c\\\" style=\\\"width: 63.5px\\\"><br> \u8054\u7cfb\u65b9\u5f0f\uff1a<input type=\\\"text\\\" kd=\\\"default_3\\\" link=\\\"default_3\\\" aria-label=\\\"\u7b2c3\u4e2a\u7a7a\u683c\\\" style=\\\"width: 63.5px\\\">";
            }
            Pattern pattern = Pattern.compile("(?:<br>\\s*)?(.*?)(?=<input)");
            Matcher matcher = pattern.matcher(blankText);
            ArrayList<String> results = new ArrayList<String>();
            while (matcher.find()) {
                String result = matcher.group();
                if (null == result || result.length() <= 0) continue;
                String[] split = result.split("<br>");
                String trim = split[split.length - 1].trim().replace(":", "").replace("\uff1a", "");
                results.add(trim);
            }
            for (int i = 0; i < items.size(); ++i) {
                Item blankItem = items.get(i);
                String name = blankItem.getName();
                List<String> zb = blankItem.getZb();
                String linkId = blankItem.getLinkId();
                String contentType = blankItem.getInputType();
                QuestionType questionType = null;
                if ("text".equals(contentType) || null == contentType || "".equals(contentType)) {
                    questionType = QuestionType.TEXT;
                } else if ("number".equals(contentType)) {
                    questionType = QuestionType.NUMBER;
                }
                String title = blankItem.getTitle();
                if (null == title || title.length() == 0) {
                    title = i < results.size() ? (String)results.get(i) : name;
                    title = questionTitle + "#" + title;
                }
                SurveyQuestionLink link = new SurveyQuestionLink(blankItem, questionType, name, zb, linkId, title);
                links.add(link);
            }
        }
        return surveyQuestion;
    }
}

