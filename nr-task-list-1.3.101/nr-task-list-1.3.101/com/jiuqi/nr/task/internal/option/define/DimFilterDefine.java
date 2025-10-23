/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.core.VisibleType
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.task.internal.option.define;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DimFilterDefine
implements TaskOptionDefine {
    public String getKey() {
        return "DIM_FILTER";
    }

    public String getTitle() {
        return "\u60c5\u666f\u8fc7\u6ee4\u6761\u4ef6";
    }

    public String getDefaultValue() {
        return null;
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_CUSTOM;
    }

    public List<OptionItem> getOptionItems() {
        return Collections.emptyList();
    }

    public String getRegex() {
        return "";
    }

    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public String getGroupTitle() {
        return "\u8fc7\u6ee4\u6761\u4ef6";
    }

    public VisibleType getVisibleType(String taskKey) {
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
        DesignTaskDefine task = designTimeViewController.getTask(taskKey);
        if (task == null || "1.0".equals(task.getVersion())) {
            return VisibleType.HIDE;
        }
        return VisibleType.DEFAULT;
    }
}

