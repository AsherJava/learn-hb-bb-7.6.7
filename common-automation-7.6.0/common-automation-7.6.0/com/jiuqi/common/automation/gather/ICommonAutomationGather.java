/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 */
package com.jiuqi.common.automation.gather;

import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import java.util.List;

public interface ICommonAutomationGather {
    public List<AutomationFolder> getFolderList(String var1);

    public List<AutomationInstance> getAutomationInstanceList(String var1);
}

