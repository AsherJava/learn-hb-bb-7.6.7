/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.dimension.internal.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DimensionManageController {
    private static final String DIMENSIONAPI_API_BASE_PATH = "/api/gcreport/v1/dimensions";
    @Autowired
    private DimensionManageService dimensionService;

    @GetMapping(value={"/api/gcreport/v1/dimensions"})
    @RequiresPermissions(value={"gc:dimension:base"})
    public BusinessResponseEntity<List<DimensionQueryVO>> list() {
        List<DimensionQueryVO> dimensionQueryVOS = this.dimensionService.listDimensions();
        return BusinessResponseEntity.ok(dimensionQueryVOS);
    }
}

