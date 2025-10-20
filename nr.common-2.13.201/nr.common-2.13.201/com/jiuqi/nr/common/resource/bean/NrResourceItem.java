/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.resource.bean;

import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import java.util.List;

public class NrResourceItem
implements NrResource {
    private String id;
    private String title;
    private int privilegeType = 0;
    private List<String> privilegeIds;
    private List<NrPrivilegeAuthority> nrPrivilegeAuthorities;
    private Object param;
    private int authRightAreaPlan = 3;

    @Override
    public int getAuthRightAreaPlan() {
        return this.authRightAreaPlan;
    }

    public void setAuthRightAreaPlan(int authRightAreaPlan) {
        this.authRightAreaPlan = authRightAreaPlan;
    }

    @Override
    public Object getParam() {
        return this.param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getPrivilegeType() {
        return this.privilegeType;
    }

    @Override
    public List<String> getPrivilegeIds() {
        return this.privilegeIds;
    }

    @Override
    public List<NrPrivilegeAuthority> getPrivilegeAuthority() {
        return this.nrPrivilegeAuthorities;
    }

    public List<NrPrivilegeAuthority> getNrPrivilegeAuthorities() {
        return this.nrPrivilegeAuthorities;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrivilegeType(int privilegeType) {
        this.privilegeType = privilegeType;
    }

    public void setPrivilegeIds(List<String> privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public void setNrPrivilegeAuthorities(List<NrPrivilegeAuthority> nrPrivilegeAuthorities) {
        this.nrPrivilegeAuthorities = nrPrivilegeAuthorities;
    }

    public NrResourceItem() {
    }

    public NrResourceItem(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan) {
        this.id = id;
        this.title = title;
        this.privilegeType = privilegeType;
        this.privilegeIds = privilegeIds;
        this.nrPrivilegeAuthorities = nrPrivilegeAuthorities;
        this.authRightAreaPlan = authRightAreaPlan;
    }

    public NrResourceItem(String id, String title, int privilegeType, List<String> privilegeIds, List<NrPrivilegeAuthority> nrPrivilegeAuthorities, int authRightAreaPlan, Object param) {
        this.id = id;
        this.title = title;
        this.privilegeType = privilegeType;
        this.privilegeIds = privilegeIds;
        this.nrPrivilegeAuthorities = nrPrivilegeAuthorities;
        this.param = param;
        this.authRightAreaPlan = authRightAreaPlan;
    }
}

