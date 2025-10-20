/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.resource.bean;

import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResourceGroup;
import com.jiuqi.nr.common.resource.bean.NrResourceItem;
import com.jiuqi.nr.common.resource.exception.ResourceException;
import java.util.List;

public class NrResourceGroupItem
extends NrResourceItem
implements NrResourceGroup {
    private final boolean isAuthorisable;

    @Override
    public boolean isAuthorisable() {
        return this.isAuthorisable;
    }

    private NrResourceGroupItem(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan) {
        super(id, title, privilegeType, privilegeIds, nrPrivilegeAuthorities, authRightAreaPlan);
        this.isAuthorisable = true;
    }

    public NrResourceGroupItem() {
        this.isAuthorisable = true;
    }

    private NrResourceGroupItem(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan, Object param) {
        super(id, title, privilegeType, privilegeIds, nrPrivilegeAuthorities, authRightAreaPlan, param);
        this.isAuthorisable = true;
    }

    private NrResourceGroupItem(String id, String title) {
        super(id, title, 0, null, null, 0);
        this.isAuthorisable = false;
    }

    public static NrResourceGroupItem create(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan) {
        return new NrResourceGroupItem(id, title, privilegeType, privilegeIds, nrPrivilegeAuthorities, authRightAreaPlan);
    }

    public static NrResourceGroupItem create(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan, Object param) {
        return new NrResourceGroupItem(id, title, privilegeType, privilegeIds, nrPrivilegeAuthorities, authRightAreaPlan, param);
    }

    public static NrResourceGroupItem createUnauthorisable(String id, String title) {
        return new NrResourceGroupItem(id, title);
    }

    @Override
    public String getId() {
        if (!this.isAuthorisable) {
            throw new ResourceException("can not get resource id from unauthorisable resource group.");
        }
        return super.getId();
    }

    @Override
    public List<String> getPrivilegeIds() {
        if (!this.isAuthorisable) {
            throw new ResourceException("can not get privileges from unauthorisable resource group.");
        }
        return super.getPrivilegeIds();
    }
}

