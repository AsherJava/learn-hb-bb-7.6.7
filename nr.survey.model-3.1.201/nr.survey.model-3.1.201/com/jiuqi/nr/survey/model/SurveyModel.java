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
import com.jiuqi.nr.survey.model.Page;
import com.jiuqi.nr.survey.model.calculated.CalculatedValue;
import com.jiuqi.nr.survey.model.trigger.Trigger;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class SurveyModel {
    private List<Page> pages;
    private List<Element> elements;
    private String title;
    private String description;
    private String cookieName;
    private boolean showTitle = true;
    private String mode;
    private String widthMode;
    private String width;
    private String logo;
    private String logoWidth;
    private String logoHeight;
    private String logoFit;
    private String logoPosition;
    private String showQuestionNumbers;
    private String questionTitleLocation;
    private String questionDescriptionLocation;
    private String questionErrorLocation;
    private String questionStartIndex;
    private String questionTitlePattern;
    private String questionLeftTitleWidth;
    private List<Trigger> triggers;
    private List<CalculatedValue> calculatedValues;

    public String getLogoPosition() {
        return this.logoPosition;
    }

    public void setLogoPosition(String logoPosition) {
        this.logoPosition = logoPosition;
    }

    public List<Page> getPages() {
        return this.pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCookieName() {
        return this.cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public boolean isShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWidthMode() {
        return this.widthMode;
    }

    public void setWidthMode(String widthMode) {
        this.widthMode = widthMode;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoWidth() {
        return this.logoWidth;
    }

    public void setLogoWidth(String logoWidth) {
        this.logoWidth = logoWidth;
    }

    public String getLogoHeight() {
        return this.logoHeight;
    }

    public void setLogoHeight(String logoHeight) {
        this.logoHeight = logoHeight;
    }

    public String getLogoFit() {
        return this.logoFit;
    }

    public void setLogoFit(String logoFit) {
        this.logoFit = logoFit;
    }

    public String getShowQuestionNumbers() {
        return this.showQuestionNumbers;
    }

    public void setShowQuestionNumbers(String showQuestionNumbers) {
        this.showQuestionNumbers = showQuestionNumbers;
    }

    public String getQuestionTitleLocation() {
        return this.questionTitleLocation;
    }

    public void setQuestionTitleLocation(String questionTitleLocation) {
        this.questionTitleLocation = questionTitleLocation;
    }

    public String getQuestionDescriptionLocation() {
        return this.questionDescriptionLocation;
    }

    public void setQuestionDescriptionLocation(String questionDescriptionLocation) {
        this.questionDescriptionLocation = questionDescriptionLocation;
    }

    public String getQuestionErrorLocation() {
        return this.questionErrorLocation;
    }

    public void setQuestionErrorLocation(String questionErrorLocation) {
        this.questionErrorLocation = questionErrorLocation;
    }

    public String getQuestionStartIndex() {
        return this.questionStartIndex;
    }

    public void setQuestionStartIndex(String questionStartIndex) {
        this.questionStartIndex = questionStartIndex;
    }

    public String getQuestionTitlePattern() {
        return this.questionTitlePattern;
    }

    public void setQuestionTitlePattern(String questionTitlePattern) {
        this.questionTitlePattern = questionTitlePattern;
    }

    public List<Trigger> getTriggers() {
        return this.triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public List<CalculatedValue> getCalculatedValues() {
        return this.calculatedValues;
    }

    public void setCalculatedValues(List<CalculatedValue> calculatedValues) {
        this.calculatedValues = calculatedValues;
    }

    public String getQuestionLeftTitleWidth() {
        return this.questionLeftTitleWidth;
    }

    public void setQuestionLeftTitleWidth(String questionLeftTitleWidth) {
        this.questionLeftTitleWidth = questionLeftTitleWidth;
    }
}

