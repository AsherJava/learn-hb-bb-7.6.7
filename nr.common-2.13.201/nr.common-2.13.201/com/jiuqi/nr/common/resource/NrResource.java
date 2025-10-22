/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 */
package com.jiuqi.nr.common.resource;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan;
import java.util.Collections;
import java.util.List;

public interface NrResource
extends Identifiable {
    public String getTitle();

    default public int getPrivilegeType() {
        return PrivilegeType.UNKNOW.getValue();
    }

    default public List<String> getPrivilegeIds() {
        return Collections.emptyList();
    }

    public List<NrPrivilegeAuthority> getPrivilegeAuthority();

    default public Object getParam() {
        return null;
    }

    default public int getAuthRightAreaPlan() {
        return NrAuthzRightAreaPlan.ALL.getValue();
    }
}

