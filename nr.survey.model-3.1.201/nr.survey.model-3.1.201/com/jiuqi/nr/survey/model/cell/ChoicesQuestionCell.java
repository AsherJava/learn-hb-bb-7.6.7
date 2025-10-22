/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.cell;

import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ChoicesByUrl;
import com.jiuqi.nr.survey.model.OtherChoice;
import com.jiuqi.nr.survey.model.cell.CellColumn;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import java.util.List;

public class ChoicesQuestionCell
extends CellColumn
implements IChoicesQuestion {
    private List<Choice> choices;
    private List<Choice> choiceFormulas;
    private ChoicesByUrl choicesByUrl;
    private boolean showOtherItem;
    private String otherPlaceholder;
    private String otherText;
    private OtherChoice other;
    private String defaultValue;
    private String filterFormula;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

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
    public List<Choice> getChoiceFormulas() {
        return this.choiceFormulas;
    }

    @Override
    public void setChoiceFormulas(List<Choice> choiceFormulas) {
        this.choiceFormulas = choiceFormulas;
    }

    @Override
    public String getFilterFormula() {
        return this.filterFormula;
    }

    @Override
    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }
}

