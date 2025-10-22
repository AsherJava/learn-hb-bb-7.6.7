/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 */
package com.jiuqi.nr.survey.model.cell;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ChoicesByUrl;
import com.jiuqi.nr.survey.model.OtherChoice;
import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import java.util.List;

public class CellRow
extends ValueBean
implements IChoicesQuestion {
    private String value;
    private String text;
    private ChoicesByUrl choicesByUrl;
    private List<Choice> choices;
    private List<Choice> choiceFormulas;
    private boolean showOtherItem;
    private String otherPlaceholder;
    private String otherText;
    private OtherChoice other;
    private String filterFormula;

    @JsonCreator
    public static CellRow fromText(String text) {
        CellRow cellRow = new CellRow();
        cellRow.setText(text);
        cellRow.setValue(text);
        return cellRow;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
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
    public List<Choice> getChoices() {
        return this.choices;
    }

    @Override
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
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

