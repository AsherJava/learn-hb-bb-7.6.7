/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.nvwa.authority.extend.ResourceCategory
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.common.resource;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.bean.AuthzAction;
import com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan;
import com.jiuqi.nr.common.resource.bean.NrResourceGroupItem;
import com.jiuqi.nvwa.authority.extend.ResourceCategory;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public interface NrResourceCategory
extends ResourceCategory {
    public static final String ROW_GRANT_ID = "22222222-2222-2222-2222-222222222222";
    public static final String ROW_GRANT_NAME = "grant";
    public static final String ROW_GRANT_TITLE = "\u6388\u6743";
    public static final String ROW_SUPERIOR_ID = "22222222-1111-1111-1111-222222222222";
    public static final String ROW_SUPERIOR_NAME = "superior";
    public static final String ROW_SUPERIOR_TITLE = "\u540c\u4e0a\u7ea7";

    default public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getPrivilegeType();

    public List<NrPrivilegeAuthority> getPrivilegeAuthority();

    default public List<Resource> getRootResources(GranteeInfo granteeInfo, Object params) {
        ArrayList<Resource> resourceList = new ArrayList<Resource>();
        HashMap<String, Boolean> param = new HashMap<String, Boolean>();
        param.put("isDuty", granteeInfo.isDuty() == null ? false : granteeInfo.isDuty());
        List<NrResource> resources = this.getRoot(null, this.getPrivilegeType(), param);
        for (NrResource resource : resources) {
            resourceList.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resource.getId(), (String)resource.getTitle(), (boolean)true));
        }
        return resourceList;
    }

    default public List<NrResource> getRoot(String granteeId, Object param) {
        return this.getRoot(granteeId, this.getPrivilegeType(), param);
    }

    public List<NrResource> getRoot(String var1, int var2, Object var3);

    default public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        ArrayList<Resource> resourceList = new ArrayList<Resource>();
        HashMap<String, Boolean> param = new HashMap<String, Boolean>();
        param.put("isDuty", granteeInfo.isDuty() == null ? false : granteeInfo.isDuty());
        List<NrResource> childrens = this.getChild(resourceGroupId, null, param);
        for (NrResource children : childrens) {
            if (children instanceof NrResourceGroupItem) {
                resourceList.add((Resource)ResourceGroupItem.createResourceGroupItem((String)children.getId(), (String)children.getTitle(), (boolean)true));
                continue;
            }
            resourceList.add((Resource)ResourceItem.createResourceItem((String)children.getId(), (String)children.getTitle()));
        }
        return resourceList;
    }

    default public List<NrResource> getChild(String resourceGroupId, String granteeId) {
        return this.getChild(resourceGroupId, granteeId, null);
    }

    public List<NrResource> getChild(String var1, String var2, Object var3);

    default public List<Authority> linkage(AuthzAction authzAction) {
        return Collections.emptyList();
    }

    default public int getNrAuthRightAreaPlan() {
        return NrAuthzRightAreaPlan.CHILDREN.getValue();
    }

    default public List<NrResource> getNrResourcesAuthority(List<NrResource> rs, String granteeId, Object param) {
        return rs;
    }

    default public List<NrResource> getNrResourcesAuthorityById(List<String> rs, String granteeId, boolean isDuty) {
        return Collections.emptyList();
    }
}

