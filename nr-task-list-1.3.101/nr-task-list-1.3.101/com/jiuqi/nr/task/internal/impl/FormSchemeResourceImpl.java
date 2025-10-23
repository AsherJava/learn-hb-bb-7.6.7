/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IFormSchemeResource
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 */
package com.jiuqi.nr.task.internal.impl;

import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IFormSchemeResource;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.common.TaskI18nUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Lazy(value=false)
public class FormSchemeResourceImpl
implements IFormSchemeResource {
    private final List<AbstractFormSchemeResourceFactory> formSchemeResourceFactory;

    @Autowired
    public FormSchemeResourceImpl(List<AbstractFormSchemeResourceFactory> formSchemeResourceList) {
        if (!CollectionUtils.isEmpty(formSchemeResourceList)) {
            formSchemeResourceList.sort(Comparator.comparing(AbstractFormSchemeResourceFactory::order));
        }
        this.formSchemeResourceFactory = formSchemeResourceList;
    }

    public List<AbstractFormSchemeResourceFactory> getFactory(String formSchemeKey) {
        if (StringUtils.hasText(formSchemeKey)) {
            return this.formSchemeResourceFactory;
        }
        return this.getFormSchemeResourceList(formSchemeKey);
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        AbstractFormSchemeResourceFactory resourceFactory = this.getFormSchemeResource(resourceDTO.getFormSchemeKey(), resourceDTO.getCategoryType());
        IResourceDataProvider dataProvider = resourceFactory.dataProvider();
        if (dataProvider != null) {
            return dataProvider.getCategory(resourceDTO);
        }
        return null;
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        ArrayList<ResourceSearchResultDO> searchResult = new ArrayList<ResourceSearchResultDO>(16);
        List<AbstractFormSchemeResourceFactory> abstractFormSchemeResourceFactories = this.getFormSchemeResourceList(searchParam.getFormSchemeKey());
        List collect = abstractFormSchemeResourceFactories.stream().filter(e -> e.code().equals(searchParam.getCategory())).collect(Collectors.toList());
        if (collect.size() == 0) {
            searchParam.setCategory("FORM_EXT");
        }
        for (AbstractFormSchemeResourceFactory resourceExt : abstractFormSchemeResourceFactories) {
            List search;
            IResourceDataProvider dataProvider;
            if (!resourceExt.code().equals(searchParam.getCategory()) || (dataProvider = resourceExt.dataProvider()) == null || CollectionUtils.isEmpty(search = dataProvider.search(searchParam))) continue;
            searchResult.addAll(search);
        }
        return searchResult;
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        ArrayList<ResourceCategoryDO> categoryList = new ArrayList<ResourceCategoryDO>(16);
        for (AbstractFormSchemeResourceFactory resourceExt : this.getFormSchemeResourceList(resourceCategoryDTO.getFormSchemeKey())) {
            List resourceCategory;
            IResourceDataProvider dataProvider = resourceExt.dataProvider();
            if (dataProvider == null || CollectionUtils.isEmpty(resourceCategory = dataProvider.getResourceCategory(resourceCategoryDTO))) continue;
            categoryList.addAll(resourceCategory);
        }
        return categoryList;
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        AbstractFormSchemeResourceFactory formSchemeResource = this.getFormSchemeResource(resourceDTO.getFormSchemeKey(), resourceDTO.getCategoryType());
        if (formSchemeResource == null) {
            throw new RuntimeException(TaskI18nUtil.getMessage("resource.error.resourceType.illegal", resourceDTO.getCategoryType()));
        }
        IResourceDataProvider dataProvider = formSchemeResource.dataProvider();
        return dataProvider.getResource(resourceDTO);
    }

    public void exportParam(OutputStream os) {
    }

    public void importParam(InputStream is) {
    }

    public void exportData(OutputStream os) {
    }

    public void importData(InputStream is) {
    }

    private List<AbstractFormSchemeResourceFactory> getFormSchemeResourceList(String formSchemeKey) {
        return this.formSchemeResourceFactory.stream().filter(e -> e.enable(formSchemeKey)).collect(Collectors.toList());
    }

    private AbstractFormSchemeResourceFactory getFormSchemeResource(String formSchemeKey, String resourceType) {
        return this.formSchemeResourceFactory.stream().filter(e -> e.enable(formSchemeKey) && e.code().equals(resourceType)).findFirst().orElse(null);
    }
}

