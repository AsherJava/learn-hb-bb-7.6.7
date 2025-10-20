/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.auth.AuthDO
 *  com.jiuqi.va.domain.task.AuthRegisterTask
 */
package com.jiuqi.va.bill.task;

import com.jiuqi.va.domain.auth.AuthDO;
import com.jiuqi.va.domain.task.AuthRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBillAuthRegisterTask
implements AuthRegisterTask {
    public List<AuthDO> getAuths() {
        ArrayList<AuthDO> list = new ArrayList<AuthDO>();
        list.add(this.initAuth("BillActionAuthDetail", "\u5355\u636e\u52a8\u4f5c", "/api/bill/anon/bill-action-auth.js", "VaBillAuth", "VaBillActionAuthDetail", null, 1, -1));
        return list;
    }
}

