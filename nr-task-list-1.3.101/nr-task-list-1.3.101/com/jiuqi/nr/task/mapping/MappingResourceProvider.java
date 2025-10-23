/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.Constants$QueryType
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 */
package com.jiuqi.nr.task.mapping;

import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.mapping.dao.OldMappingSchemeDao;
import com.jiuqi.nr.task.mapping.dto.MappingSchemeDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.StringUtils;

public class MappingResourceProvider
implements IResourceDataProvider {
    private final OldMappingSchemeDao mappingSchemeDao;

    public MappingResourceProvider(OldMappingSchemeDao mappingSchemeDao) {
        this.mappingSchemeDao = mappingSchemeDao;
    }

    public List<ResourceSearchResultDO> search(SearchParam searchParam) {
        return Collections.emptyList();
    }

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        ArrayList<ResourceCategoryDO> dos = new ArrayList<ResourceCategoryDO>();
        if (resourceCategoryDTO.getQueryType() == Constants.QueryType.ROOT) {
            dos.add(this.getCategory(null));
        }
        return dos;
    }

    public ResourceDO getResource(ResourceDTO resourceDTO) {
        String formSchemeKey = resourceDTO.getFormSchemeKey();
        if (!StringUtils.hasText(formSchemeKey)) {
            return null;
        }
        ResourceDTO resourceDO = new ResourceDTO("MAPPING_SCHEME");
        String[] fields = new String[]{"key", "title", "index", "total"};
        resourceDO.setFields(fields);
        List<MappingSchemeDTO> query = this.mappingSchemeDao.query(formSchemeKey);
        ArrayList<Object[]> values = new ArrayList<Object[]>(query.size());
        for (int i = 0; i < query.size(); ++i) {
            MappingSchemeDTO mappingSchemeDTO = query.get(i);
            Object[] row = new Object[fields.length];
            row[0] = mappingSchemeDTO.getKey();
            row[1] = mappingSchemeDTO.getTitle();
            row[2] = i;
            row[3] = query.size();
            values.add(row);
        }
        resourceDO.setValues(values);
        return resourceDO;
    }

    public ResourceCategoryDO getCategory(ResourceDTO resourceDTO) {
        ResourceCategoryDO categoryDO = new ResourceCategoryDO("MAPPING_SCHEME");
        categoryDO.setKey("MAPPING_SCHEME");
        categoryDO.setTitle("\u6620\u5c04\u65b9\u6848\uff08\u65e7\uff09");
        categoryDO.setIcon("#icon-_GJZduibi");
        return categoryDO;
    }
}

