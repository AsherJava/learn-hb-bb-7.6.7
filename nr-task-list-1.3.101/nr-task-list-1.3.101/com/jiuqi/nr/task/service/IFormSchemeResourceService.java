/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.dto.ResourceExtDTO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.nr.task.api.dto.ResourceExtDTO;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface IFormSchemeResourceService {
    public List<ResourceExtDTO> queryResourceType(String var1);

    public List<ResourceSearchResultDO> search(SearchParam var1);

    public List<UITreeNode<ResourceCategoryDO>> getRootCategory(ResourceCategoryDTO var1);

    public List<UITreeNode<ResourceCategoryDO>> getChildrenCategory(ResourceCategoryDTO var1);

    public ResourceDTO queryResource(ResourceDTO var1);

    public List<UITreeNode<ResourceCategoryDO>> queryCategoryByResource(ResourceDTO var1);
}

