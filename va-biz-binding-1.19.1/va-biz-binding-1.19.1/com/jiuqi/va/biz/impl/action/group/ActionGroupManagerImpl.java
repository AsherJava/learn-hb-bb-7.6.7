/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action.group;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.action.group.ActionGroup;
import com.jiuqi.va.biz.intf.action.group.ActionGroupManager;
import org.springframework.stereotype.Component;

@Component
public class ActionGroupManagerImpl
extends NamedManagerImpl<ActionGroup>
implements ActionGroupManager {
    @Override
    public ActionGroup getActionGroupById(String id) {
        return this.stream().filter(actionGroup -> actionGroup.getId().equals(id)).findFirst().orElse(null);
    }
}

