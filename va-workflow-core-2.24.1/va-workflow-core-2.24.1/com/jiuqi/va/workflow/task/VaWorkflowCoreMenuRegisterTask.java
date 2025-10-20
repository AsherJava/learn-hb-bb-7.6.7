/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.workflow.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowCoreMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaWorkFlowGroup", "\u5de5\u4f5c\u6d41\u7ba1\u7406", "-", 0, null, "VaWorkFlowGroup", 1L));
        menus.add(this.initMenu("VaWorkflow", "\u5de5\u4f5c\u6d41\u7ba1\u7406", "VaWorkFlowGroup", 1, "/meta/workflow", "VaWorkflow", 1L));
        menus.add(this.initMenu("VaWorkflowBinding", "\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a", "VaWorkFlowGroup", 1, "/billWorkflow", "VaWorkflowBinding", 2L));
        menus.add(this.initMenu("VaWorkflowMonitor", "\u6d41\u7a0b\u76d1\u63a7", "VaWorkFlowGroup", 1, "/VaWorkflowMonitor", "VaWorkflowMonitor", 3L));
        return menus;
    }
}

