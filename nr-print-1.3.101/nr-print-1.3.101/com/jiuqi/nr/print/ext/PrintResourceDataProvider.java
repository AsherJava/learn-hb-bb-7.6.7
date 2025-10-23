/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 */
package com.jiuqi.nr.print.ext;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class PrintResourceDataProvider
implements IResourceDataProvider {
    private IDesignTimePrintController designTimeController;

    public PrintResourceDataProvider(IDesignTimePrintController designTimeController) {
        this.designTimeController = designTimeController;
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        ArrayList<ResourceSearchResultDO> resourceSearchResults = new ArrayList<ResourceSearchResultDO>();
        List printSchemes = this.designTimeController.listPrintTemplateSchemeByFormScheme(searchParam.getFormSchemeKey());
        String keyWords = searchParam.getKeyWords().trim();
        if (StringUtils.hasText(keyWords)) {
            keyWords = keyWords.toLowerCase(Locale.ROOT);
        }
        for (DesignPrintTemplateSchemeDefine printScheme : printSchemes) {
            if (!printScheme.getTitle().toLowerCase(Locale.ROOT).contains(keyWords)) continue;
            ResourceSearchResultDO resourceSearchResult = new ResourceSearchResultDO("PRINT_MANAGE");
            resourceSearchResult.setKey(printScheme.getKey());
            resourceSearchResult.setTitle(printScheme.getTitle());
            resourceSearchResult.setPath(printScheme.getTitle() + "/" + "\u6253\u5370\u65b9\u6848");
            resourceSearchResults.add(resourceSearchResult);
        }
        return resourceSearchResults;
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        ArrayList<ResourceCategoryDO> resourceCategories = new ArrayList<ResourceCategoryDO>();
        if (resourceCategoryDTO.getQueryType() != Constants.QueryType.ROOT) {
            return resourceCategories;
        }
        ResourceCategoryDO categoryDO = new ResourceCategoryDO("PRINT_MANAGE");
        categoryDO.setKey("PRINT_MANAGE");
        categoryDO.setTitle("\u6253\u5370\u65b9\u6848");
        categoryDO.setIcon("#icon-J_GJ_A_NR_dayinfanganleixing");
        categoryDO.setParent("");
        resourceCategories.add(categoryDO);
        return resourceCategories;
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = new ResourceDO("PRINT_MANAGE");
        List printSchemes = this.designTimeController.listPrintTemplateSchemeByFormScheme(resourceDTO.getFormSchemeKey());
        String[] fields = new String[]{"key", "formSchemeKey", "title", "describe", "updateTime", "total"};
        ArrayList<Object[]> values = new ArrayList<Object[]>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!CollectionUtils.isEmpty(printSchemes)) {
            for (int i = 0; i < printSchemes.size(); ++i) {
                Object[] value = new Object[]{((DesignPrintTemplateSchemeDefine)printSchemes.get(i)).getKey(), resourceDTO.getFormSchemeKey(), ((DesignPrintTemplateSchemeDefine)printSchemes.get(i)).getTitle(), ((DesignPrintTemplateSchemeDefine)printSchemes.get(i)).getDescription(), sdf.format(((DesignPrintTemplateSchemeDefine)printSchemes.get(i)).getUpdateTime()), printSchemes.size()};
                values.add(value);
            }
        }
        resourceDO.setFields(fields);
        resourceDO.setValues(values);
        return resourceDO;
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        ResourceCategoryDO categoryDO = new ResourceCategoryDO("PRINT_MANAGE");
        categoryDO.setKey("PRINT_MANAGE");
        categoryDO.setTitle("\u6253\u5370\u65b9\u6848");
        categoryDO.setParent("");
        return categoryDO;
    }
}

