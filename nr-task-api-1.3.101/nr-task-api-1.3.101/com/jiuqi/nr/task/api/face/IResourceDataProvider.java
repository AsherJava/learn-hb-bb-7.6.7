/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.face;

import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import java.util.List;

public interface IResourceDataProvider {
    public List<ResourceSearchResultDO> search(SearchParam var1);

    public List<ResourceCategoryDO> getResourceCategory(ResourceCategoryDTO var1);

    public ResourceDO getResource(ResourceDTO var1);

    public ResourceCategoryDO getCategory(ResourceDTO var1);
}

