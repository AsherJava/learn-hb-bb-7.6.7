/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.dto.ResourceExtDTO
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IFormSchemeResource
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.dto.ResourceExtDTO;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IFormSchemeResource;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.service.IFormSchemeResourceService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FormSchemeResourceServiceImpl
implements IFormSchemeResourceService {
    @Autowired
    private IFormSchemeResource formSchemeResource;

    @Override
    public List<ResourceExtDTO> queryResourceType(String formSchemeKey) {
        List formSchemeResourceList = this.formSchemeResource.getFactory(formSchemeKey);
        ArrayList<ResourceExtDTO> formSchemeResourceDTOS = new ArrayList<ResourceExtDTO>(formSchemeResourceList.size());
        for (AbstractFormSchemeResourceFactory formSchemeResourceExt : formSchemeResourceList) {
            ResourceExtDTO dto = new ResourceExtDTO();
            dto.setCode(formSchemeResourceExt.code());
            dto.setTitle(formSchemeResourceExt.title());
            ComponentDefine component = formSchemeResourceExt.component();
            dto.setComponentName(component.getComponentName());
            dto.setProductLine(component.getProductLine());
            dto.setEntryName(component.getEntry());
            formSchemeResourceDTOS.add(dto);
        }
        return formSchemeResourceDTOS;
    }

    @Override
    public List<ResourceSearchResultDO> search(SearchParam resourceSearch) {
        return this.formSchemeResource.search(resourceSearch);
    }

    @Override
    public List<UITreeNode<ResourceCategoryDO>> getRootCategory(ResourceCategoryDTO categoryDTO) {
        ArrayList<UITreeNode<ResourceCategoryDO>> rootNodes = new ArrayList<UITreeNode<ResourceCategoryDO>>(16);
        categoryDTO.setQueryType(Constants.QueryType.ROOT);
        List roots = this.formSchemeResource.getResourceCategory(categoryDTO);
        int index = 0;
        for (ResourceCategoryDO root : roots) {
            UITreeNode rootNode = new UITreeNode((TreeData)root);
            rootNode.setIcon(root.getIcon());
            ResourceCategoryDTO instance = ResourceCategoryDTO.getInstance((ResourceCategoryDO)root);
            instance.setFormSchemeKey(categoryDTO.getFormSchemeKey());
            instance.setLocationKey(categoryDTO.getLocationKey());
            instance.setQueryType(Constants.QueryType.DIRECT_CHILDREN);
            List<UITreeNode<ResourceCategoryDO>> childrenCategory = this.getChildrenCategory(instance);
            if (categoryDTO.getLocationKey() == null) {
                if (index == 0 && !childrenCategory.isEmpty()) {
                    childrenCategory.get(0).setChecked(true);
                    rootNode.setExpand(true);
                }
            } else if (categoryDTO.getLocationKey().equals(root.getKey())) {
                rootNode.setSelected(true);
                rootNode.setExpand(true);
            } else {
                childrenCategory.forEach(a -> {
                    if (a.getKey().equals(categoryDTO.getLocationKey())) {
                        rootNode.setExpand(true);
                        a.setSelected(true);
                    }
                });
            }
            if (!childrenCategory.isEmpty()) {
                rootNode.setChildren(childrenCategory);
                rootNode.setLeaf(childrenCategory.isEmpty());
            }
            rootNode.setKey(root.getCategoryType());
            rootNodes.add((UITreeNode<ResourceCategoryDO>)rootNode);
            ++index;
        }
        return rootNodes;
    }

    @Override
    public List<UITreeNode<ResourceCategoryDO>> getChildrenCategory(ResourceCategoryDTO categoryDTO) {
        ArrayList<UITreeNode<ResourceCategoryDO>> tree = new ArrayList<UITreeNode<ResourceCategoryDO>>(16);
        ResourceCategoryDTO resourceCategoryDTO = new ResourceCategoryDTO();
        resourceCategoryDTO.setQueryType(Constants.QueryType.DIRECT_CHILDREN);
        resourceCategoryDTO.setKey(categoryDTO.getKey());
        resourceCategoryDTO.setFormSchemeKey(categoryDTO.getFormSchemeKey());
        List resourceCategory = this.formSchemeResource.getResourceCategory(resourceCategoryDTO);
        if (!CollectionUtils.isEmpty(resourceCategory)) {
            for (ResourceCategoryDO categoryDO : resourceCategory) {
                UITreeNode childrenNode = new UITreeNode((TreeData)categoryDO);
                resourceCategoryDTO.setKey(categoryDO.getKey());
                if (categoryDTO.getLocationKey() != null && categoryDTO.getLocationKey().equals(categoryDO.getKey())) {
                    childrenNode.setSelected(true);
                }
                childrenNode.setKey(categoryDO.getKey());
                childrenNode.setIcon(categoryDO.getIcon());
                childrenNode.setLeaf(CollectionUtils.isEmpty(this.formSchemeResource.getResourceCategory(resourceCategoryDTO)));
                tree.add((UITreeNode<ResourceCategoryDO>)childrenNode);
            }
        }
        return tree;
    }

    @Override
    public ResourceDTO queryResource(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = this.formSchemeResource.getResource(resourceDTO);
        resourceDTO.setFields(resourceDO.getFields());
        resourceDTO.setValues(resourceDO.getValues());
        return resourceDTO;
    }

    @Override
    public List<UITreeNode<ResourceCategoryDO>> queryCategoryByResource(ResourceDTO resourceDTO) {
        ResourceCategoryDO category = this.formSchemeResource.getCategory(resourceDTO);
        if (category == null) {
            return Collections.emptyList();
        }
        ResourceCategoryDTO resourceCategoryDTO = new ResourceCategoryDTO();
        resourceCategoryDTO.setFormSchemeKey(resourceDTO.getFormSchemeKey());
        resourceCategoryDTO.setLocationKey(category.getKey());
        return this.getRootCategory(resourceCategoryDTO);
    }
}

