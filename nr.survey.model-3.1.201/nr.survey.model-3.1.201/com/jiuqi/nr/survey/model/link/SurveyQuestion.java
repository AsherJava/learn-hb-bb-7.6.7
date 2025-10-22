/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.List;

public class SurveyQuestion {
    private int number;
    private String name;
    private QuestionType type;
    private List<String> zb;
    private String linkId;
    private List<SurveyQuestionLink> links;
    private String title;
    private ValueBean question;

    public SurveyQuestion() {
    }

    public SurveyQuestion(String name, QuestionType type, SurveyQuestionLink link) {
        this.name = name;
        this.type = type;
        this.links = new ArrayList<SurveyQuestionLink>();
        this.links.add(link);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SurveyQuestionLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<SurveyQuestionLink> links) {
        this.links = links;
    }

    public QuestionType getType() {
        return this.type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getZb() {
        return this.zb;
    }

    public void setZb(List<String> zb) {
        this.zb = zb;
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ValueBean getQuestion() {
        return this.question;
    }

    public void setQuestion(ValueBean question) {
        this.question = question;
    }
}

