/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.validator.Validator;
import java.util.List;

public class Item
extends ValueBean {
    private String name;
    private Boolean isRequired;
    private String placeholder;
    private String inputType;
    private String title;
    private int maxLength;
    private int size;
    private String requiredErrorText;
    private List<Validator> validators;
    private String width;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getInputType() {
        return this.inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}

