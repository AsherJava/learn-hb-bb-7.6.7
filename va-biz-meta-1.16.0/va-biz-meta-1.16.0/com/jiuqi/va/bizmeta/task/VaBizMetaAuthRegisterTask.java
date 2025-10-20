/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.auth.AuthDO
 *  com.jiuqi.va.domain.task.AuthRegisterTask
 */
package com.jiuqi.va.bizmeta.task;

import com.jiuqi.va.domain.auth.AuthDO;
import com.jiuqi.va.domain.task.AuthRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBizMetaAuthRegisterTask
implements AuthRegisterTask {
    public List<AuthDO> getAuths() {
        ArrayList<AuthDO> list = new ArrayList<AuthDO>();
        list.add(this.initAuth("MetaActionAuthDetail", "\u5143\u6570\u636e", "/api/meta/anon/meta-auth.js", "VaMetaAuth", "VaMetaActionAuthDetail", null, 1, -1));
        return list;
    }
}

