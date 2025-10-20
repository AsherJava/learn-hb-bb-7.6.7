/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO
 *  com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather
 *  com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO;
import com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather;
import com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelCategoryService;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelCategoryServiceImpl
implements BizModelCategoryService {
    @Autowired
    private IBizModelCategoryGather iBizModelCategoryGather;

    @Override
    public List<BizModelCategoryDTO> listAllCategoryAppInfo() {
        List bizModelCategoryList = this.iBizModelCategoryGather.list();
        if (CollectionUtils.isEmpty((Collection)bizModelCategoryList)) {
            return Collections.emptyList();
        }
        ArrayList<BizModelCategoryDTO> result = new ArrayList<BizModelCategoryDTO>();
        HashMap prodLineToAppNameMap = new HashMap(8);
        for (IBizModelCategory handler : bizModelCategoryList) {
            if (prodLineToAppNameMap.containsKey(handler.getProdLine()) && ((Set)prodLineToAppNameMap.get(handler.getProdLine())).contains(handler.getAppName())) continue;
            BizModelCategoryDTO vo = new BizModelCategoryDTO();
            vo.setProdLine(handler.getProdLine());
            vo.setAppName(handler.getAppName());
            vo.setCode(handler.getCode());
            vo.setName(handler.getName());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<BizModelCategoryDTO> listCategory() {
        List bizModelCategoryList = this.iBizModelCategoryGather.list();
        if (CollectionUtils.isEmpty((Collection)bizModelCategoryList)) {
            return Collections.emptyList();
        }
        return bizModelCategoryList.stream().map(item -> {
            BizModelCategoryDTO dto = new BizModelCategoryDTO();
            dto.setCode(item.getCode());
            dto.setName(item.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}

