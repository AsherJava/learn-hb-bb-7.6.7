/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.core.VisibleType
 *  com.jiuqi.nr.definition.option.impl.group.StopFillingGroup
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.designer.optionentry.taskoption;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllowStopFilingRole
implements TaskOptionDefine {
    @Autowired
    private RoleService service;

    public String getKey() {
        return "ALLOW_STOP_FILING_ROLE";
    }

    public String getTitle() {
        return "\u7ec8\u6b62\u586b\u62a5\u89d2\u8272";
    }

    public String getDefaultValue() {
        return "";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        List roles = this.service.getAllRoles();
        for (final Role role : roles) {
            if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equals(role.getId()) || "ffffffff-ffff-ffff-aaaa-ffffffffffff".equals(role.getId())) continue;
            optionItems.add(new OptionItem(){

                public String getTitle() {
                    return role.getTitle();
                }

                public String getValue() {
                    return role.getId();
                }
            });
        }
        return optionItems;
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 50.0;
    }

    public String getPageTitle() {
        return new StopFillingGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new StopFillingGroup().getTitle();
    }

    public VisibleType getVisibleType(String taskKey) {
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
        DesignTaskDefine task = designTimeViewController.getTask(taskKey);
        if (task != null && "2.0".equals(task.getVersion())) {
            return VisibleType.HIDE;
        }
        return VisibleType.DEFAULT;
    }
}

