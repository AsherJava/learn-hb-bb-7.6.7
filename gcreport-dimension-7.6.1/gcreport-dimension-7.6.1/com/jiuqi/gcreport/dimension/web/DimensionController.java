/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.dimension.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimSelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DimensionController {
    final String DIMENSIONAPI_API_BASE_PATH = "/api/gcreport/v1/dimensions";
    @Autowired
    private DimensionService dimensionService;

    @GetMapping(value={"/api/gcreport/v1/dimensions/dimFieldsVO/{tableName}"})
    public BusinessResponseEntity<List<DimensionVO>> getDimFieldsVOByTableName(@PathVariable(value="tableName") String tableName) {
        return BusinessResponseEntity.ok(this.dimensionService.findDimFieldsVOByTableName(tableName));
    }

    @GetMapping(value={"/api/gcreport/v1/dimensions/AllDimFieldsVO/{tableName}"})
    public BusinessResponseEntity<List<DimensionVO>> getGroupDimFieldsVOByTableName(@PathVariable(value="tableName") String tableName) {
        return BusinessResponseEntity.ok(this.dimensionService.findAllDimFieldsVOByTableName(tableName));
    }

    @GetMapping(value={"/api/gcreport/v1/dimensions/getManagementDimension"})
    public BusinessResponseEntity<List<DimSelectOptionVO>> getManagementDimensionVO() {
        return BusinessResponseEntity.ok(this.dimensionService.findManagementDimensionVO());
    }
}

