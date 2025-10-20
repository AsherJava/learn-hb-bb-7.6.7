/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 */
package com.jiuqi.gcreport.inputdata.gcinputdataquery;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import org.springframework.stereotype.Component;

@Component
public class InputDataQueryFilterAction
extends AbstractGcActionItem {
    protected InputDataQueryFilterAction(AsyncTaskManager asyncTaskManager) {
        super("gcInputDataQueryFilterAction", "\u6570\u636e\u5c42\u7ea7\u8fc7\u6ee4", "", "#icon-_GJTshaixuan");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

