/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.attachment.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentCoreMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaContentMgrGroup", "\u5185\u5bb9\u7ba1\u7406", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaContentAttachmentScheme", "\u9644\u4ef6\u65b9\u6848\u7ba1\u7406", "VaContentMgrGroup", 1, "/attachment/scheme", "vaContent:attachment:mgr", 9L));
        return menus;
    }
}

