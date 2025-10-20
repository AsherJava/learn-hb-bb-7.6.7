/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.va.query.datasource.web;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.vo.SelectOptionVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(primary=false, contextId="queryDataSourceClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface QueryDataSourceClient {
    public static final String QUERY_TEMPLATE_BASE_API = "/api/datacenter/v1/userDefined/dataSource";

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getSupportDataBase"})
    public BusinessResponseEntity<List<SelectOptionVO>> getSupportDataBase();

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getSimpleDataSources"})
    public BusinessResponseEntity<ArrayNode> getSimpleDataSources();

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getAllDataSource"})
    public BusinessResponseEntity<List<DataSourceInfoVO>> getAllDataSource();
}

