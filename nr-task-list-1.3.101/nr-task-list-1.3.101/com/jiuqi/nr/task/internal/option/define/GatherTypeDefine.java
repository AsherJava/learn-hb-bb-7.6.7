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
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GatherTypeDefine
implements TaskOptionDefine {
    public String getKey() {
        return "TASK_GATHER_TYPE";
    }

    public String getTitle() {
        return "\u6c47\u603b\u65b9\u5f0f";
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_RADIO_BUTTON;
    }

    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            public String getTitle() {
                return "\u624b\u52a8\u6c47\u603b";
            }

            public String getValue() {
                return "0";
            }
        });
        optionItems.add(new OptionItem(){

            public String getTitle() {
                return "\u81ea\u52a8\u6c47\u603b";
            }

            public String getValue() {
                return "1";
            }
        });
        return optionItems;
    }

    public String getRegex() {
        return "";
    }

    public String getPageTitle() {
        return "\u6c47\u603b\u76f8\u5173";
    }

    public String getGroupTitle() {
        return "";
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

