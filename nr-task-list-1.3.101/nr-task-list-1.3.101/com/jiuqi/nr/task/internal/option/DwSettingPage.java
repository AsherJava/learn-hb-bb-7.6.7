/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.option.core.VisibleType
 *  com.jiuqi.nr.definition.option.spi.TaskOptionPage
 */
package com.jiuqi.nr.task.internal.option;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class DwSettingPage
implements TaskOptionPage {
    public static final String TITLE = "\u7ef4\u5ea6";

    public String getTitle() {
        return TITLE;
    }

    public Double getOrder() {
        return 450.0;
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

