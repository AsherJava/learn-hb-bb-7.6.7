/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.spi;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.core.VisibleType;
import java.util.List;

public interface TaskOptionDefine {
    public String getKey();

    public String getTitle();

    public String getDefaultValue();

    default public String getDefaultValue(String taskKey) {
        return this.getDefaultValue();
    }

    public OptionEditMode getOptionEditMode();

    public List<OptionItem> getOptionItems();

    default public List<OptionItem> getOptionItems(String taskKey) {
        return this.getOptionItems();
    }

    public String getRegex();

    default public double getOrder() {
        return Double.MAX_VALUE;
    }

    default public VisibleType getVisibleType(String taskKey) {
        return VisibleType.DEFAULT;
    }

    public String getPageTitle();

    public String getGroupTitle();

    default public String getDescription() {
        return null;
    }

    default public String getRegexMsg() {
        return null;
    }

    default public boolean canEmpty() {
        return true;
    }

    default public String getPluginName() {
        return null;
    }

    default public String getExpose() {
        return null;
    }

    default public RelationTaskOptionItem getRelationTaskOptionItem() {
        return null;
    }

    default public boolean isVertical() {
        return true;
    }

    default public String getPlaceholder() {
        return null;
    }
}

