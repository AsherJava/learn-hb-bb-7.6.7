/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.option.core.VisibleType
 *  com.jiuqi.nr.definition.option.internal.DimensionGroupOption
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 */
package com.jiuqi.nr.task.internal.option.define;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.internal.DimensionGroupOption;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionGroupOptionV3
extends DimensionGroupOption {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public VisibleType getVisibleType(String taskKey) {
        String dw;
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        if (task != null && (dw = task.getDw()) != null) {
            return BaseDataAdapterUtil.isBaseData((String)dw) ? VisibleType.DEFAULT : VisibleType.HIDE;
        }
        return VisibleType.HIDE;
    }
}

