/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.bde.bizmodel.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.bde.bizmodel.client.FetchDimensionClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface FetchDimensionClient {
    public static final String FETCH_SETTING_API = "/api/bde/v1/dimension";

    @GetMapping(value={"/api/bde/v1/dimension/list"})
    public BusinessResponseEntity<List<DimensionVO>> listDimension();

    @GetMapping(value={"/api/bde/v1/dimension/listByDataModel/{bizDataModel}"})
    public BusinessResponseEntity<List<DimensionVO>> listDimensionByDataModel(@PathVariable(value="bizDataModel") String var1);

    @GetMapping(value={"/api/bde/v1/dimension/listAllByDataModel/{bizDataModel}"})
    public BusinessResponseEntity<List<DimensionVO>> listAllDimensionByDataModel(@PathVariable(value="bizDataModel") String var1);

    @GetMapping(value={"/api/bde/v1/dimension/listAll"})
    public BusinessResponseEntity<List<DimensionVO>> listAllDimension();

    @GetMapping(value={"/api/bde/v1/fetch/listAllDimension"})
    public BusinessResponseEntity<List<SelectOptionVO>> listAllSelectOptionVO();
}

