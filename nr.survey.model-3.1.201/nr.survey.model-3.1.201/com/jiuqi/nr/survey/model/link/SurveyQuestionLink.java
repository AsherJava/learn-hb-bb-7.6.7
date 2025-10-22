/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.common.QuestionType;
import java.util.List;

public class SurveyQuestionLink {
    private boolean matrix;
    private String linkId;
    private List<String> zb;
    private QuestionType type;
    private String name;
    private ValueBean question;
    private String title;
    private String filterFormula;

    public SurveyQuestionLink(ValueBean question, QuestionType type, String name, List<String> zb, String linkId, String title) {
        this.zb = zb;
        this.linkId = linkId;
        this.type = type;
        this.name = name;
        this.question = question;
        this.title = title;
    }

    public boolean isMatrix() {
        return this.matrix;
    }

    public void setMatrix(boolean matrix) {
        this.matrix = matrix;
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public List<String> getZb() {
        return this.zb;
    }

    public void setZb(List<String> zb) {
        this.zb = zb;
    }

    public QuestionType getType() {
        return this.type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValueBean getQuestion() {
        return this.question;
    }

    public void setQuestion(ValueBean question) {
        this.question = question;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }
}

