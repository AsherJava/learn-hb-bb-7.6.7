/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO
 */
package com.jiuqi.bde.bizmodel.impl.model.service;

import com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO;
import java.util.List;

public interface BizModelCategoryService {
    public List<BizModelCategoryDTO> listAllCategoryAppInfo();

    public List<BizModelCategoryDTO> listCategory();
}

