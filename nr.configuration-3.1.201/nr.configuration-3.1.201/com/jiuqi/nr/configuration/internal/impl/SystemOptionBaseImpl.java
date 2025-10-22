/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.internal.impl;

import com.jiuqi.nr.configuration.common.OptionItem;
import com.jiuqi.nr.configuration.common.SystemOptionEditMode;
import com.jiuqi.nr.configuration.common.SystemOptionType;
import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import java.util.List;

@Deprecated
public class SystemOptionBaseImpl
implements SystemOptionBase {
    private String key;
    private String group;
    private String title;
    private Object defaultValue;
    private SystemOptionType optionType;
    private SystemOptionEditMode editMode;
    private List<OptionItem> optionItems;
    private String regex;
    private int order;

    public SystemOptionBaseImpl() {
    }

    public SystemOptionBaseImpl(String key, String title, Object defaultValue) {
        this.key = key;
        this.title = title;
        this.defaultValue = defaultValue;
        this.group = "";
        this.optionType = SystemOptionType.SYSTEM_OPTION;
        this.editMode = SystemOptionEditMode.OPTION_EDIT_MODE_INPUT;
        this.regex = "";
    }

    public SystemOptionBaseImpl(String key, String group, String title, Object defaultValue, SystemOptionType optionType, SystemOptionEditMode editMode, String regex) {
        this.key = key;
        this.group = group;
        this.title = title;
        this.defaultValue = defaultValue;
        this.optionType = optionType == null ? SystemOptionType.SYSTEM_OPTION : optionType;
        this.editMode = editMode == null ? SystemOptionEditMode.OPTION_EDIT_MODE_INPUT : editMode;
        this.regex = regex;
    }

    public SystemOptionBaseImpl(String key, String group, String title, Object defaultValue, SystemOptionType optionType, SystemOptionEditMode editMode, List<OptionItem> optionItems, String regex, int order) {
        this.key = key;
        this.group = group;
        this.title = title;
        this.defaultValue = defaultValue;
        this.optionType = optionType == null ? SystemOptionType.SYSTEM_OPTION : optionType;
        this.editMode = editMode == null ? SystemOptionEditMode.OPTION_EDIT_MODE_INPUT : editMode;
        this.optionItems = optionItems;
        this.regex = regex;
        this.order = order;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public SystemOptionType getOptionType() {
        return this.optionType;
    }

    @Override
    public SystemOptionEditMode getOptionEditMode() {
        return this.editMode;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        return this.optionItems;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    public void setOptionType(SystemOptionType optionType) {
        this.optionType = optionType;
    }

    public SystemOptionEditMode getEditMode() {
        return this.editMode;
    }

    public void setEditMode(SystemOptionEditMode editMode) {
        this.editMode = editMode;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setOptionItems(List<OptionItem> optionItems) {
        this.optionItems = optionItems;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

