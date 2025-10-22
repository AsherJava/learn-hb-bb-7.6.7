/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.survey.model.common.QuestionType
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckItem;
import com.jiuqi.nr.survey.model.common.QuestionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SurveyCheckQuestion
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private QuestionType type;
    private List<SurveyCheckItem> items;
    private List<String> zb;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuestionType getType() {
        return this.type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<SurveyCheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<SurveyCheckItem> items) {
        this.items = items;
    }

    public void addError(SurveyCheckItem item) {
        if (this.items == null) {
            this.items = new ArrayList<SurveyCheckItem>();
        }
        this.items.add(item);
    }

    public List<String> getZb() {
        return this.zb;
    }

    public void setZb(List<String> zb) {
        this.zb = zb;
    }
}

