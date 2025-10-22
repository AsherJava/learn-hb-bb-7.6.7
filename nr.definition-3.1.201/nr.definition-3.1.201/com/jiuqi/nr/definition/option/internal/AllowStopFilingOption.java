/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.impl.group.StopFillingGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AllowStopFilingOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "ALLOW_STOP_FILING";
    }

    @Override
    public String getTitle() {
        return "\u5141\u8bb8\u7ec8\u6b62\u586b\u62a5";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u662f";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u5426";
            }

            @Override
            public String getValue() {
                return "0";
            }
        });
        return optionItems;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 40.0;
    }

    @Override
    public String getPageTitle() {
        return new StopFillingGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new StopFillingGroup().getTitle();
    }

    @Override
    public VisibleType getVisibleType(String taskKey) {
        IDesignTimeViewController designTimeViewController = BeanUtil.getBean(IDesignTimeViewController.class);
        DesignTaskDefine task = designTimeViewController.getTask(taskKey);
        if (task != null && "2.0".equals(task.getVersion())) {
            return VisibleType.HIDE;
        }
        return VisibleType.DEFAULT;
    }
}

