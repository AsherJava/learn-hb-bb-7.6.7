/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import java.util.List;

public class TaskOptionDefineDTO
implements TaskOptionDefine,
Comparable<TaskOptionDefineDTO> {
    private String key;
    private String title;
    private String defaultValue;
    private OptionEditMode optionEditMode;
    private List<OptionItem> optionItems;
    private String regex;
    private double order;
    private String value;
    private VisibleType visibleType;
    private String pageTitle;
    private String groupTitle;
    private String pageAndGroup;
    private RelationTaskOptionItem relationTaskOptionItem;
    private boolean visible = true;
    private boolean editable = true;
    private TaskOptionGroup taskOptionGroup;
    private String regexMsg;
    private String pluginName;
    private String expose;
    private boolean canEmpty;
    private String placeholder;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return this.optionEditMode;
    }

    public void setOptionEditMode(OptionEditMode optionEditMode) {
        this.optionEditMode = optionEditMode;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        return this.optionItems;
    }

    public void setOptionItems(List<OptionItem> optionItems) {
        this.optionItems = optionItems;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public double getOrder() {
        return this.order;
    }

    @Override
    public String getPageTitle() {
        return this.pageTitle;
    }

    @Override
    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public VisibleType getVisibleType(String taskKey) {
        return this.visibleType;
    }

    public void setVisibleType(VisibleType visibleType) {
        this.visibleType = visibleType;
    }

    public String getPageAndGroup() {
        return this.pageAndGroup;
    }

    public void setPageAndGroup(String pageAndGroup) {
        this.pageAndGroup = pageAndGroup;
    }

    @Override
    public RelationTaskOptionItem getRelationTaskOptionItem() {
        return this.relationTaskOptionItem;
    }

    public void setRelationTaskOptionItem(RelationTaskOptionItem relationTaskOptionItem) {
        this.relationTaskOptionItem = relationTaskOptionItem;
    }

    public String toString() {
        return "TaskOptionDefineDTO{key='" + this.key + '\'' + ", title='" + this.title + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", optionEditMode=" + (Object)((Object)this.optionEditMode) + ", optionItems=" + this.optionItems + ", regex='" + this.regex + '\'' + ", order=" + this.order + ", value='" + this.value + '\'' + '}';
    }

    public TaskOptionDefineDTO() {
    }

    public TaskOptionDefineDTO(TaskOptionDefine optionDefine) {
        this(optionDefine, null);
    }

    public TaskOptionDefineDTO(TaskOptionDefine optionDefine, String taskKey) {
        this.key = optionDefine.getKey();
        this.defaultValue = optionDefine.getDefaultValue(taskKey);
        this.optionEditMode = optionDefine.getOptionEditMode();
        this.optionItems = optionDefine.getOptionItems(taskKey);
        this.order = optionDefine.getOrder();
        this.regex = optionDefine.getRegex();
        this.regexMsg = optionDefine.getRegexMsg();
        this.title = optionDefine.getTitle();
        this.visibleType = optionDefine.getVisibleType(taskKey);
        this.groupTitle = optionDefine.getGroupTitle();
        this.pageTitle = optionDefine.getPageTitle();
        this.pageAndGroup = optionDefine.getPageTitle() + ";" + (optionDefine.getGroupTitle() == null ? "" : optionDefine.getGroupTitle());
        this.relationTaskOptionItem = optionDefine.getRelationTaskOptionItem();
        this.pluginName = optionDefine.getPluginName();
        this.expose = optionDefine.getExpose();
        this.canEmpty = optionDefine.canEmpty();
        this.placeholder = optionDefine.getPlaceholder();
    }

    @Override
    public int compareTo(TaskOptionDefineDTO o) {
        return Double.compare(this.order, o.order);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public TaskOptionGroup getTaskOptionGroup() {
        return this.taskOptionGroup;
    }

    public void setTaskOptionGroup(TaskOptionGroup taskOptionGroup) {
        this.taskOptionGroup = taskOptionGroup;
    }

    @Override
    public String getRegexMsg() {
        return this.regexMsg;
    }

    @Override
    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public String getExpose() {
        return this.expose;
    }

    public void setExpose(String expose) {
        this.expose = expose;
    }

    public boolean isCanEmpty() {
        return this.canEmpty;
    }

    public void setCanEmpty(boolean canEmpty) {
        this.canEmpty = canEmpty;
    }

    @Override
    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}

