/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.resource.exception.ResourceException
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.datascheme.auth;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.resource.exception.ResourceException;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class FieldAuthResource
extends DefaultResourceCategory {
    public static final String FIELDS_ID = "FieldAuthResource-f4c9diced8a";
    private static final String SUBSTRING = "_";
    private static final long serialVersionUID = 7586784461578078907L;
    @Autowired
    private IRuntimeDataSchemeService iruntimeDataSchemeService;
    @Autowired
    private PrivilegeService privilegeService;

    public String getId() {
        return FIELDS_ID;
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getSeq() {
        return 700;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("datascheme_auth_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem readItem = new PrivilegeDefinitionItem();
        readItem.setPrivilegeId("datascheme_auth_resource_read");
        readItem.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)readItem);
        PrivilegeDefinitionItem writeItem = new PrivilegeDefinitionItem();
        writeItem.setPrivilegeId("datascheme_auth_resource_write");
        writeItem.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)writeItem);
        PrivilegeDefinitionItem grandDefinition = new PrivilegeDefinitionItem();
        grandDefinition.setPrivilegeId("22222222-2222-2222-2222-222222222222");
        grandDefinition.setPrivilegeTitle("\u6388\u6743");
        list.add((PrivilegeDefinition)grandDefinition);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        return this.iruntimeDataSchemeService.getAllDataScheme().stream().filter(Objects::nonNull).filter(r -> this.privilegeService.hasDelegateAuth("datascheme_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(r.getKey()))).map(item -> ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(item.getKey()), (String)item.getTitle(), (boolean)true)).collect(Collectors.toList());
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo, Object param) {
        ArrayList<Resource> resource = new ArrayList<Resource>();
        try {
            if (DataSchemeAuthResourceType.DATA_SCHEME.getPrefix().equals(this.getResourcePrefixTypeById(resourceGroupId))) {
                resource.addAll(this.iruntimeDataSchemeService.getDataGroupByScheme(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(item -> {
                    boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("datascheme_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)DataSchemeAuthResourceType.DATA_GROUP.toResourceId(item.getKey()));
                    if (!hasDelegateAuth) {
                        return null;
                    }
                    return ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_GROUP.toResourceId(item.getKey()), (String)item.getTitle(), (boolean)true);
                }).filter(Objects::nonNull).collect(Collectors.toList()));
                resource.addAll(this.iruntimeDataSchemeService.getDataTableByScheme(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(this.getDataTableResourceGroupItemFunction()).filter(Objects::nonNull).collect(Collectors.toList()));
                return resource;
            }
            if (DataSchemeAuthResourceType.DATA_GROUP.getPrefix().equals(this.getResourcePrefixTypeById(resourceGroupId))) {
                resource.addAll(this.iruntimeDataSchemeService.getDataTableByGroup(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(this.getDataTableResourceGroupItemFunction()).filter(Objects::nonNull).collect(Collectors.toList()));
                resource.addAll(this.iruntimeDataSchemeService.getDataGroupByParent(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(item -> {
                    boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("datascheme_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)DataSchemeAuthResourceType.DATA_GROUP.toResourceId(item.getKey()));
                    if (!hasDelegateAuth) {
                        return null;
                    }
                    return ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_GROUP.toResourceId(item.getKey()), (String)item.getTitle(), (boolean)true);
                }).filter(Objects::nonNull).collect(Collectors.toList()));
                return resource;
            }
            return this.allDataField(this.getIdByResourceId(resourceGroupId));
        }
        catch (Exception e) {
            throw new ResourceException("get resource error");
        }
    }

    private Function<DataTable, ResourceGroupItem> getDataTableResourceGroupItemFunction() {
        return t -> {
            boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("datascheme_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)DataSchemeAuthResourceType.DATA_TABLE.toResourceId(t.getKey()));
            if (!hasDelegateAuth) {
                return null;
            }
            if (this.allDataField(t.getKey()).size() == 0) {
                return null;
            }
            return ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_TABLE.toResourceId(t.getKey()), (String)t.getTitle(), (boolean)true);
        };
    }

    private List<Resource> allDataField(String resourceId) {
        return this.iruntimeDataSchemeService.getDataFieldByTable(resourceId).stream().filter(DataField::isUseAuthority).map(r -> {
            boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("datascheme_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)DataSchemeAuthResourceType.DATA_FIELD.toResourceId(r.getKey()));
            if (!hasDelegateAuth) {
                return null;
            }
            return ResourceItem.createResourceItem((String)DataSchemeAuthResourceType.DATA_FIELD.toResourceId(r.getKey()), (String)r.getTitle());
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private String getResourcePrefixTypeById(String resourceId) {
        return resourceId.substring(0, resourceId.indexOf(SUBSTRING) + 1);
    }

    private String getIdByResourceId(String resourceId) {
        return resourceId.substring(resourceId.indexOf(SUBSTRING) + 1);
    }
}

