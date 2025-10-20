/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigManageClient
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.formulaschemeconfig.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigManageClient;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormulaSchemeConfigManageController
implements FormulaSchemeConfigManageClient {
    @Autowired
    private FormulaSchemeConfigCategoryService formulaSchemeConfigCategoryService;

    @GetMapping(value={"/api/gcreport/v1//formulaSchemeConfig/listCategory"})
    public BusinessResponseEntity<List<FormulaSchemeConfigCategoryDTO>> listCategory() {
        return BusinessResponseEntity.ok(this.formulaSchemeConfigCategoryService.listCategory());
    }

    @GetMapping(value={"/api/gcreport/v1//formulaSchemeConfig/listAllCategoryAppInfo"})
    public BusinessResponseEntity<List<FormulaSchemeConfigCategoryDTO>> listAllCategoryAppInfo() {
        return BusinessResponseEntity.ok(this.formulaSchemeConfigCategoryService.listAllCategoryAppInfo());
    }
}

