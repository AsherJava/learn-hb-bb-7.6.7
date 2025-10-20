/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.impl.dimension.controller;

import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class FetchDimensionWeb
implements FetchDimensionClient {
    @Autowired
    @Lazy
    private FetchDimensionService fetchDimensionService;

    @GetMapping(value={"/api/bde/v1/dimension/list"})
    public BusinessResponseEntity<List<DimensionVO>> listDimension() {
        return BusinessResponseEntity.ok(this.fetchDimensionService.listDimension());
    }

    @GetMapping(value={"/api/bde/v1/dimension/listByDataModel/{bizDataModel}"})
    public BusinessResponseEntity<List<DimensionVO>> listDimensionByDataModel(@PathVariable String bizDataModel) {
        return BusinessResponseEntity.ok(this.fetchDimensionService.listDimensionByDataModel(bizDataModel));
    }

    @GetMapping(value={"/api/bde/v1/dimension/listAllByDataModel/{bizDataModel}"})
    public BusinessResponseEntity<List<DimensionVO>> listAllDimensionByDataModel(@PathVariable String bizDataModel) {
        return BusinessResponseEntity.ok(this.fetchDimensionService.listAllDimensionByDataModel(bizDataModel));
    }

    public BusinessResponseEntity<List<DimensionVO>> listAllDimension() {
        return BusinessResponseEntity.ok(this.fetchDimensionService.listAllDimension());
    }

    public BusinessResponseEntity<List<SelectOptionVO>> listAllSelectOptionVO() {
        return BusinessResponseEntity.ok(this.fetchDimensionService.listAllSelectOptionVO());
    }
}

