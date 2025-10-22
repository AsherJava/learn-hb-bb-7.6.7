/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.survey.model.Element;
import java.util.List;

public class Panel
extends Element {
    private List<Element> elements;
    private boolean showNumber;
    private String showQuestionNumbers;
    private String questionStartIndex;
    private String questionTitleLocation;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean startWithNewLine = true;
    private String state;
    private int innerIndent;

    public List<Element> getElements() {
        return this.elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public boolean isShowNumber() {
        return this.showNumber;
    }

    public void setShowNumber(boolean showNumber) {
        this.showNumber = showNumber;
    }

    public String getShowQuestionNumbers() {
        return this.showQuestionNumbers;
    }

    public void setShowQuestionNumbers(String showQuestionNumbers) {
        this.showQuestionNumbers = showQuestionNumbers;
    }

    public String getQuestionStartIndex() {
        return this.questionStartIndex;
    }

    public void setQuestionStartIndex(String questionStartIndex) {
        this.questionStartIndex = questionStartIndex;
    }

    public String getQuestionTitleLocation() {
        return this.questionTitleLocation;
    }

    public void setQuestionTitleLocation(String questionTitleLocation) {
        this.questionTitleLocation = questionTitleLocation;
    }

    public boolean isStartWithNewLine() {
        return this.startWithNewLine;
    }

    public void setStartWithNewLine(boolean startWithNewLine) {
        this.startWithNewLine = startWithNewLine;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    public int getInnerIndent() {
        return this.innerIndent;
    }

    public void setInnerIndent(int innerIndent) {
        this.innerIndent = innerIndent;
    }
}

