/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ChoicesByUrl;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.OtherChoice;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import java.util.List;

public class ChoicesQuestion
extends Element
implements IChoicesQuestion {
    private List<Choice> choices;
    private List<Choice> choiceFormulas;
    private String choicesVisibleIf;
    private String choicesEnableIf;
    private ChoicesByUrl choicesByUrl;
    private boolean showOtherItem;
    private String otherPlaceholder;
    private String otherText;
    private OtherChoice other;
    private int colCount;
    private String defaultValue;
    private String filterFormula;

    @Override
    public List<Choice> getChoices() {
        return this.choices;
    }

    @Override
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public ChoicesByUrl getChoicesByUrl() {
        return this.choicesByUrl;
    }

    @Override
    public void setChoicesByUrl(ChoicesByUrl choicesByUrl) {
        this.choicesByUrl = choicesByUrl;
    }

    @Override
    public boolean isShowOtherItem() {
        return this.showOtherItem;
    }

    public void setShowOtherItem(boolean showOtherItem) {
        this.showOtherItem = showOtherItem;
    }

    @Override
    public String getOtherPlaceholder() {
        return this.otherPlaceholder;
    }

    public void setOtherPlaceholder(String otherPlaceholder) {
        this.otherPlaceholder = otherPlaceholder;
    }

    @Override
    public String getOtherText() {
        return this.otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    @Override
    public OtherChoice getOther() {
        return this.other;
    }

    @Override
    public void setOther(OtherChoice other) {
        this.other = other;
    }

    @Override
    public String getFilterFormula() {
        return this.filterFormula;
    }

    @Override
    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public String getChoicesVisibleIf() {
        return this.choicesVisibleIf;
    }

    public void setChoicesVisibleIf(String choicesVisibleIf) {
        this.choicesVisibleIf = choicesVisibleIf;
    }

    public String getChoicesEnableIf() {
        return this.choicesEnableIf;
    }

    public void setChoicesEnableIf(String choicesEnableIf) {
        this.choicesEnableIf = choicesEnableIf;
    }

    public int getColCount() {
        return this.colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public List<Choice> getChoiceFormulas() {
        return this.choiceFormulas;
    }

    @Override
    public void setChoiceFormulas(List<Choice> choiceFormulas) {
        this.choiceFormulas = choiceFormulas;
    }
}

