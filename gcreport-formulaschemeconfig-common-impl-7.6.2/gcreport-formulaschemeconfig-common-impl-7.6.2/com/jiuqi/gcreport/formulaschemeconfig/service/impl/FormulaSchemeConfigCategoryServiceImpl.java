/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory
 */
package com.jiuqi.gcreport.formulaschemeconfig.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO;
import com.jiuqi.gcreport.formulaschemeconfig.gather.IFormulaSchemeConfigCategoryGather;
import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigCategoryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigCategoryServiceImpl
implements FormulaSchemeConfigCategoryService {
    @Autowired
    private IFormulaSchemeConfigCategoryGather iFormulaSchemeConfigCategoryGather;

    @Override
    public List<FormulaSchemeConfigCategoryDTO> listAllCategoryAppInfo() {
        List<IFormulaSchemeConfigCategory> formulaSchemeConfigCategoryList = this.iFormulaSchemeConfigCategoryGather.list();
        if (CollectionUtils.isEmpty(formulaSchemeConfigCategoryList)) {
            return Collections.emptyList();
        }
        ArrayList<FormulaSchemeConfigCategoryDTO> result = new ArrayList<FormulaSchemeConfigCategoryDTO>();
        HashMap prodLineToAppNameMap = new HashMap(8);
        for (IFormulaSchemeConfigCategory handler : formulaSchemeConfigCategoryList) {
            if (prodLineToAppNameMap.containsKey(handler.getProdLine()) && ((Set)prodLineToAppNameMap.get(handler.getProdLine())).contains(handler.getAppName())) continue;
            FormulaSchemeConfigCategoryDTO vo = new FormulaSchemeConfigCategoryDTO();
            vo.setProdLine(handler.getProdLine());
            vo.setAppName(handler.getAppName());
            vo.setCode(handler.getCode());
            vo.setName(handler.getName());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<FormulaSchemeConfigCategoryDTO> listCategory() {
        List<IFormulaSchemeConfigCategory> bizModelCategoryList = this.iFormulaSchemeConfigCategoryGather.list();
        if (CollectionUtils.isEmpty(bizModelCategoryList)) {
            return Collections.emptyList();
        }
        return bizModelCategoryList.stream().map(item -> {
            FormulaSchemeConfigCategoryDTO dto = new FormulaSchemeConfigCategoryDTO();
            dto.setCode(item.getCode());
            dto.setName(item.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}

