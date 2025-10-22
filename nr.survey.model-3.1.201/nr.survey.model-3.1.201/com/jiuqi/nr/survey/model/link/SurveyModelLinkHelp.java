/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.Page;
import com.jiuqi.nr.survey.model.Panel;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.BaseQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.BlankQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.ChoiceQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.MatrixdynamicQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.MatrixfloatQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.PanelDynamicQuestionLinkProviderImpl;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import java.util.ArrayList;
import java.util.List;

public class SurveyModelLinkHelp {
    private static final List<ISurveyQuestionLinkProvider> surveyQuestionLinkProviders = new ArrayList<ISurveyQuestionLinkProvider>();

    private SurveyModelLinkHelp() {
    }

    public static List<ISurveyQuestionLinkProvider> getProviders() {
        return surveyQuestionLinkProviders;
    }

    private static void register(ISurveyQuestionLinkProvider surveyQuestionLinkProvider) {
        surveyQuestionLinkProviders.add(surveyQuestionLinkProvider);
    }

    private static void handler(Element element, List<Element> allElements) {
        if (QuestionType.PANEL.value().equals(element.getType())) {
            Panel panel = (Panel)element;
            List<Element> elements = panel.getElements();
            if (null != elements) {
                for (Element one : elements) {
                    SurveyModelLinkHelp.handler(one, allElements);
                }
            }
        } else {
            allElements.add(element);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static List<SurveyQuestion> getAllSurveyQuestion(SurveyModel surveyModel) {
        List<Element> elements;
        ArrayList<SurveyQuestion> questions = new ArrayList<SurveyQuestion>();
        ArrayList<Element> allElements = new ArrayList<Element>();
        List<Page> pages = surveyModel.getPages();
        if (null != pages) {
            for (Page page : pages) {
                List<Element> pageElements = page.getElements();
                if (null == pageElements) continue;
                for (Element one : pageElements) {
                    SurveyModelLinkHelp.handler(one, allElements);
                }
            }
        }
        if (null != (elements = surveyModel.getElements())) {
            for (Element one : elements) {
                SurveyModelLinkHelp.handler(one, allElements);
            }
        }
        if (!allElements.isEmpty()) {
            boolean bl = false;
            for (Element element : allElements) {
                void var5_9;
                ++var5_9;
                for (ISurveyQuestionLinkProvider provider : surveyQuestionLinkProviders) {
                    SurveyQuestion surveyQuestion;
                    if (!provider.accept(element) || null == (surveyQuestion = provider.getLinks(element))) continue;
                    if (surveyQuestion.getQuestion() == null) {
                        surveyQuestion.setQuestion(element);
                    }
                    surveyQuestion.setNumber((int)var5_9);
                    surveyQuestion.setZb(element.getZb());
                    surveyQuestion.setLinkId(element.getLinkId());
                    surveyQuestion.setTitle(null != element.getTitle() && element.getTitle().length() > 0 ? element.getTitle() : element.getName());
                    questions.add(surveyQuestion);
                }
            }
        }
        return questions;
    }

    static {
        SurveyModelLinkHelp.register(new BaseQuestionLinkProviderImpl());
        SurveyModelLinkHelp.register(new BlankQuestionLinkProviderImpl());
        SurveyModelLinkHelp.register(new ChoiceQuestionLinkProviderImpl());
        SurveyModelLinkHelp.register(new MatrixdynamicQuestionLinkProviderImpl());
        SurveyModelLinkHelp.register(new MatrixfloatQuestionLinkProviderImpl());
        SurveyModelLinkHelp.register(new PanelDynamicQuestionLinkProviderImpl());
    }
}

