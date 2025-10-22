/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.survey.model.BlankQuestion;
import com.jiuqi.nr.survey.model.BooleanQuestion;
import com.jiuqi.nr.survey.model.CheckBoxQuestion;
import com.jiuqi.nr.survey.model.CommentQuestion;
import com.jiuqi.nr.survey.model.DorpDownQuestion;
import com.jiuqi.nr.survey.model.FilePoolQuestion;
import com.jiuqi.nr.survey.model.FileQuestion;
import com.jiuqi.nr.survey.model.MatrixdynamicQuestion;
import com.jiuqi.nr.survey.model.MatrixfloatQuestion;
import com.jiuqi.nr.survey.model.NumberQuestion;
import com.jiuqi.nr.survey.model.Panel;
import com.jiuqi.nr.survey.model.PanelDynamic;
import com.jiuqi.nr.survey.model.PeriodQuestion;
import com.jiuqi.nr.survey.model.PictureQuestion;
import com.jiuqi.nr.survey.model.RadioGroupQuestion;
import com.jiuqi.nr.survey.model.TagboxQuestion;
import com.jiuqi.nr.survey.model.TextQuestion;
import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.validator.Validator;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="type", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=BlankQuestion.class, name="blank"), @JsonSubTypes.Type(value=NumberQuestion.class, name="number"), @JsonSubTypes.Type(value=PeriodQuestion.class, name="period"), @JsonSubTypes.Type(value=TextQuestion.class, name="text"), @JsonSubTypes.Type(value=CommentQuestion.class, name="comment"), @JsonSubTypes.Type(value=BooleanQuestion.class, name="boolean"), @JsonSubTypes.Type(value=RadioGroupQuestion.class, name="radiogroup"), @JsonSubTypes.Type(value=TagboxQuestion.class, name="tagbox"), @JsonSubTypes.Type(value=CheckBoxQuestion.class, name="checkbox"), @JsonSubTypes.Type(value=DorpDownQuestion.class, name="dropdown"), @JsonSubTypes.Type(value=PictureQuestion.class, name="picture"), @JsonSubTypes.Type(value=FileQuestion.class, name="file"), @JsonSubTypes.Type(value=FilePoolQuestion.class, name="filepool"), @JsonSubTypes.Type(value=MatrixdynamicQuestion.class, name="matrixdynamic"), @JsonSubTypes.Type(value=MatrixfloatQuestion.class, name="matrixfloat"), @JsonSubTypes.Type(value=Panel.class, name="panel"), @JsonSubTypes.Type(value=PanelDynamic.class, name="paneldynamic")})
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class Element
extends ValueBean {
    private String title;
    private String name;
    private String type;
    private String description;
    private Boolean isRequired;
    private Boolean readOnly;
    private Boolean visible = true;
    private String autocomplete;
    private String placeholder;
    private String[] dataList;
    private String minWidth;
    private String width;
    private String maxWidth;
    private String visibleIf;
    private String enableIf;
    private String requiredIf;
    private String defaultValueExpression;
    private Integer indent;
    private String titleLocation;
    private String descriptionLocation;
    private Boolean hideNumber;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private Boolean startWithNewLine = true;
    private String clearIfInvisible;
    private String requiredErrorText;
    private List<Validator> validators;
    private String textUpdateMode;
    private int maxLength;
    private String state;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public void setIsRequired(Boolean required) {
        this.isRequired = required;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getAutocomplete() {
        return this.autocomplete;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String[] getDataList() {
        return this.dataList;
    }

    public void setDataList(String[] dataList) {
        this.dataList = dataList;
    }

    public String getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(String minWidth) {
        this.minWidth = minWidth;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getVisibleIf() {
        return this.visibleIf;
    }

    public void setVisibleIf(String visibleIf) {
        this.visibleIf = visibleIf;
    }

    public String getEnableIf() {
        return this.enableIf;
    }

    public void setEnableIf(String enableIf) {
        this.enableIf = enableIf;
    }

    public String getRequiredIf() {
        return this.requiredIf;
    }

    public void setRequiredIf(String requiredIf) {
        this.requiredIf = requiredIf;
    }

    public String getDefaultValueExpression() {
        return this.defaultValueExpression;
    }

    public void setDefaultValueExpression(String defaultValueExpression) {
        this.defaultValueExpression = defaultValueExpression;
    }

    public Integer getIndent() {
        return this.indent;
    }

    public void setIndent(Integer indent) {
        this.indent = indent;
    }

    public String getTitleLocation() {
        return this.titleLocation;
    }

    public void setTitleLocation(String titleLocation) {
        this.titleLocation = titleLocation;
    }

    public String getDescriptionLocation() {
        return this.descriptionLocation;
    }

    public void setDescriptionLocation(String descriptionLocation) {
        this.descriptionLocation = descriptionLocation;
    }

    public Boolean getHideNumber() {
        return this.hideNumber;
    }

    public void setHideNumber(Boolean hideNumber) {
        this.hideNumber = hideNumber;
    }

    public String getClearIfInvisible() {
        return this.clearIfInvisible;
    }

    public void setClearIfInvisible(String clearIfInvisible) {
        this.clearIfInvisible = clearIfInvisible;
    }

    public String getRequiredErrorText() {
        return this.requiredErrorText;
    }

    public void setRequiredErrorText(String requiredErrorText) {
        this.requiredErrorText = requiredErrorText;
    }

    public List<Validator> getValidators() {
        return this.validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public String getTextUpdateMode() {
        return this.textUpdateMode;
    }

    public void setTextUpdateMode(String textUpdateMode) {
        this.textUpdateMode = textUpdateMode;
    }

    public Boolean getStartWithNewLine() {
        return this.startWithNewLine;
    }

    public void setStartWithNewLine(Boolean startWithNewLine) {
        this.startWithNewLine = startWithNewLine;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxWidth(String maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

