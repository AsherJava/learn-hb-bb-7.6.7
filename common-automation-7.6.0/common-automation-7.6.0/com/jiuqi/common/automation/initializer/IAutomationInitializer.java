/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 */
package com.jiuqi.common.automation.initializer;

import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import java.util.List;
import java.util.Map;

public interface IAutomationInitializer {
    public void init(Map<String, String> var1, Map<String, Map<String, AutomationFolder>> var2, Map<String, List<AutomationInstance>> var3);
}

