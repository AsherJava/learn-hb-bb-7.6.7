/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 *  com.jiuqi.nr.task.api.util.PathBuilder
 */
package com.jiuqi.nr.task.form.ext;

import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.api.util.PathBuilder;
import com.jiuqi.nr.task.form.common.FomeTypeTitle;
import com.jiuqi.nr.task.form.form.dto.FormGroupDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import com.jiuqi.nr.task.form.service.IFormService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FormResourceDataProvider
implements IResourceDataProvider {
    private static final String ROOT_GROUP_KEY = "FORM_EXT";
    private static final String ROOT_GROUP_TITLE = "\u62a5\u8868";
    private final IFormService formService;
    private final IFormDefineService formDefineService;
    private final IFormGroupService formGroupService;
    private final UserService<User> userUserService;
    private final UserService<SystemUser> systemUserUserService;

    public FormResourceDataProvider(IFormService formService, IFormDefineService formDefineService, IFormGroupService formGroupService, UserService<User> userUserService, UserService<SystemUser> systemUserUserService) {
        this.formService = formService;
        this.formDefineService = formDefineService;
        this.formGroupService = formGroupService;
        this.userUserService = userUserService;
        this.systemUserUserService = systemUserUserService;
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        String keyWords = searchParam.getKeyWords();
        if (StringUtils.hasText(keyWords)) {
            ArrayList<ResourceSearchResultDO> res = new ArrayList<ResourceSearchResultDO>();
            List<FormGroupDTO> formGroupDTOList = this.formGroupService.listFormGroupByScheme(searchParam.getFormSchemeKey());
            if (StringUtils.hasText(searchParam.getFormGroupKey())) {
                formGroupDTOList = formGroupDTOList.stream().filter(a -> a.getKey().equals(searchParam.getFormGroupKey())).collect(Collectors.toList());
            }
            formGroupDTOList.forEach(group -> {
                if (!StringUtils.hasText(searchParam.getFormGroupKey()) && group.getTitle().toLowerCase().contains(keyWords.toLowerCase())) {
                    res.add(this.toSearchResultDO((FormGroupDTO)group));
                }
                List<FormItemDTO> formItemDTOList = this.formDefineService.queryFormByGroup(group.getKey());
                formItemDTOList.forEach(item -> {
                    if (item.getTitle().toLowerCase().contains(keyWords.toLowerCase()) || item.getCode().toLowerCase().contains(keyWords.toLowerCase())) {
                        res.add(this.toSearchResultDO((FormItemDTO)item, group.getTitle()));
                    }
                });
            });
            return res;
        }
        return null;
    }

    private ResourceSearchResultDO toSearchResultDO(FormItemDTO item, String groupTitle) {
        ResourceSearchResultDO searchResultDO = new ResourceSearchResultDO(ROOT_GROUP_KEY);
        searchResultDO.setKey(item.getKey());
        searchResultDO.setTitle(item.getTitle());
        searchResultDO.setCode(item.getCode());
        searchResultDO.setResourceType("FORM");
        searchResultDO.setPath(new PathBuilder().append(item.getTitle()).append(groupTitle).append(ROOT_GROUP_TITLE).toString());
        searchResultDO.setType(item.getType().name(), FomeTypeTitle.forTitle(item.getType().getValue()));
        return searchResultDO;
    }

    private ResourceSearchResultDO toSearchResultDO(FormGroupDTO group) {
        ResourceSearchResultDO searchResultDO = new ResourceSearchResultDO(ROOT_GROUP_KEY);
        searchResultDO.setKey(group.getKey());
        searchResultDO.setTitle(group.getTitle());
        searchResultDO.setCode(group.getCode());
        searchResultDO.setResourceType("FORMGROUP");
        searchResultDO.setType("FORMGROUP", "\u62a5\u8868\u5206\u7ec4");
        searchResultDO.setPath(new PathBuilder().append(group.getTitle()).append(ROOT_GROUP_TITLE).toString());
        return searchResultDO;
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        List<FormGroupDTO> fromGroups;
        ArrayList<ResourceCategoryDO> categoryDOS = new ArrayList<ResourceCategoryDO>();
        if (resourceCategoryDTO.getQueryType().equals((Object)Constants.QueryType.ROOT)) {
            ResourceCategoryDO rootGroup = this.initRootGroup();
            categoryDOS.add(rootGroup);
        } else if (resourceCategoryDTO.getQueryType().equals((Object)Constants.QueryType.DIRECT_CHILDREN) && ROOT_GROUP_KEY.equals(resourceCategoryDTO.getKey()) && !CollectionUtils.isEmpty(fromGroups = this.formGroupService.queryRoot(resourceCategoryDTO.getFormSchemeKey()))) {
            for (FormGroupDTO group : fromGroups) {
                ResourceCategoryDO resourceCategoryDO = new ResourceCategoryDO(ROOT_GROUP_KEY);
                resourceCategoryDO.setKey(group.getKey());
                resourceCategoryDO.setTitle(group.getTitle());
                resourceCategoryDO.setIcon("#icon-folder");
                resourceCategoryDO.setParent(StringUtils.hasText(group.getParent()) ? group.getParent() : ROOT_GROUP_KEY);
                categoryDOS.add(resourceCategoryDO);
            }
        }
        return categoryDOS;
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = !ROOT_GROUP_KEY.equals(resourceDTO.getCategoryKey()) ? this.getForm(resourceDTO) : this.getFormGroup(resourceDTO);
        return resourceDO;
    }

    private ResourceDO getForm(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = new ResourceDO(ROOT_GROUP_KEY);
        String[] fields = new String[]{"key", "code", "title", "formType", "formTypeString", "updateTime", "updateUser", "isFirst", "isLast"};
        resourceDO.setFields(fields);
        List<FormItemDTO> formItemDTOS = ROOT_GROUP_KEY.equals(resourceDTO.getCategoryKey()) ? this.formService.queryFormByScheme(resourceDTO.getFormSchemeKey()) : this.formService.queryFormByGroup(resourceDTO.getCategoryKey());
        if (CollectionUtils.isEmpty(formItemDTOS)) {
            return resourceDO;
        }
        ArrayList<Object[]> values = new ArrayList<Object[]>();
        Map<String, String> allUsers = this.getAllUserName();
        for (int i = 0; i < formItemDTOS.size(); ++i) {
            FormItemDTO itemDTO = formItemDTOS.get(i);
            boolean isFirst = false;
            boolean isLast = false;
            if (i == 0) {
                isFirst = true;
            }
            if (i == formItemDTOS.size() - 1) {
                isLast = true;
            }
            Object[] value = new Object[]{itemDTO.getKey(), itemDTO.getCode(), itemDTO.getTitle(), itemDTO.getType(), FomeTypeTitle.forTitle(itemDTO.getType().getValue()), itemDTO.getUpdateTime(), allUsers.getOrDefault(itemDTO.getUpdateUser(), StringUtils.hasText(itemDTO.getUpdateUser()) ? itemDTO.getUpdateUser() : "\u672a\u77e5\u7528\u6237"), isFirst, isLast};
            values.add(value);
        }
        resourceDO.setValues(values);
        return resourceDO;
    }

    private Map<String, String> getAllUserName() {
        List allUsers = this.userUserService.getAllUsers();
        List systemUsers = this.systemUserUserService.getAllUsers();
        HashMap<String, String> res = new HashMap<String, String>(allUsers.size() + systemUsers.size());
        if (!CollectionUtils.isEmpty(allUsers)) {
            for (User allUser : allUsers) {
                res.put(allUser.getName(), allUser.getFullname());
            }
        }
        if (!CollectionUtils.isEmpty(systemUsers)) {
            for (SystemUser systemUser : systemUsers) {
                res.put(systemUser.getName(), systemUser.getName());
            }
        }
        return res;
    }

    private ResourceDO getFormGroup(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = new ResourceDO(ROOT_GROUP_KEY);
        List<FormGroupDTO> fromGroups = this.formGroupService.queryRoot(resourceDTO.getFormSchemeKey());
        if (CollectionUtils.isEmpty(fromGroups)) {
            return resourceDO;
        }
        String[] fields = new String[]{"key", "code", "title", "parent", "updateTime", "isFirst", "isLast", "categoryType"};
        resourceDO.setFields(fields);
        ArrayList<Object[]> values = new ArrayList<Object[]>();
        for (int i = 0; i < fromGroups.size(); ++i) {
            FormGroupDTO fromGroup = fromGroups.get(i);
            boolean isFirst = false;
            boolean isLast = false;
            if (i == 0) {
                isFirst = true;
            }
            if (i == fromGroups.size() - 1) {
                isLast = true;
            }
            Object[] value = new Object[]{fromGroup.getKey(), fromGroup.getCode(), fromGroup.getTitle(), fromGroup.getParent(), fromGroup.getUpdateTime(), isFirst, isLast, ROOT_GROUP_KEY};
            values.add(value);
        }
        resourceDO.setValues(values);
        return resourceDO;
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        FormGroupDTO formGroupByFrom = this.formGroupService.getFormGroupByFrom(resourceDTO.getCurrentResource());
        if (formGroupByFrom != null) {
            ResourceCategoryDO categoryDO = new ResourceCategoryDO(resourceDTO.getCategoryType());
            categoryDO.setKey(formGroupByFrom.getKey());
            categoryDO.setParent(formGroupByFrom.getParent());
            categoryDO.setTitle(formGroupByFrom.getTitle());
            return categoryDO;
        }
        if (resourceDTO.getCategoryType().equalsIgnoreCase(ROOT_GROUP_KEY)) {
            return this.initRootGroup();
        }
        return null;
    }

    private ResourceCategoryDO initRootGroup() {
        ResourceCategoryDO categoryDO = new ResourceCategoryDO(ROOT_GROUP_KEY);
        categoryDO.setKey(ROOT_GROUP_KEY);
        categoryDO.setIcon("#icon-J_GJ_A_NR_baobiaoleixing");
        categoryDO.setTitle(ROOT_GROUP_TITLE);
        return categoryDO;
    }
}

