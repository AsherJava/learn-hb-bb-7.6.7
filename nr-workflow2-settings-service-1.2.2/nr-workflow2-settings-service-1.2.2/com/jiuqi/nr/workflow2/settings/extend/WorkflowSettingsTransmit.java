/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 */
package com.jiuqi.nr.workflow2.settings.extend;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.nr.workflow2.settings.vo.source.CustomWorkflowDefine;
import java.util.List;

public interface WorkflowSettingsTransmit {
    public List<CustomWorkflowDefine> getCustomConfigSource(String var1);

    public List<String> convertUserRoleList(List<String> var1, List<String> var2);

    public List<Role> getRoleSource();
}

