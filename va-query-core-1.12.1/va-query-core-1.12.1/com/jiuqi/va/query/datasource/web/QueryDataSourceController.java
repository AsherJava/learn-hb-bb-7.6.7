/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.datasource.vo.SelectOptionVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.datasource.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.gather.DataBaseHandleGather;
import com.jiuqi.va.query.datasource.service.QueryDataSourceService;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.vo.SelectOptionVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryDataSourceController {
    private static final String QUERY_TEMPLATE_BASE_API = "/api/datacenter/v1/userDefined/dataSource";
    @Autowired
    private QueryDataSourceService queryDataSourceService;
    @Autowired
    private DataBaseHandleGather dataBaseHandleGather;

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getSupportDataBase"})
    public BusinessResponseEntity<List<SelectOptionVO>> getSupportDataBase() {
        List<QueryDataBaseHandler> queryDataBaseHandlerList = this.dataBaseHandleGather.getAllHandle();
        ArrayList typeNameList = new ArrayList();
        queryDataBaseHandlerList.forEach(handler -> typeNameList.add(new SelectOptionVO(handler.getTypeName(), handler.getDescription())));
        return BusinessResponseEntity.ok(typeNameList);
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getSimpleDataSources"})
    public BusinessResponseEntity<Object> getSimpleDataSources() {
        List<DataSourceInfoVO> list = this.queryDataSourceService.getAllDataSources();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode jsonArray = objectMapper.createArrayNode();
        ObjectNode currentDataSource = objectMapper.createObjectNode();
        currentDataSource.put("label", "\u5f53\u524d\u6570\u636e\u6e90");
        currentDataSource.put("value", DataSourceEnum.CURRENT.getName());
        jsonArray.add((JsonNode)currentDataSource);
        list.forEach(item -> {
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("label", item.getName());
            jsonObject.put("value", item.getCode());
            jsonArray.add((JsonNode)jsonObject);
        });
        return BusinessResponseEntity.ok((Object)jsonArray);
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/dataSource/getAllDataSource"})
    public BusinessResponseEntity<Object> getAllDataSource() {
        UserLoginDTO user = ShiroUtil.getUser();
        NvwaSystemUserClient nvwaSystemUserClient = DCQuerySpringContextUtils.getBean(NvwaSystemUserClient.class);
        SystemUserDTO byName = nvwaSystemUserClient.get(user.getId());
        if (byName == null) {
            return BusinessResponseEntity.error((String)"\u60a8\u6ca1\u6709\u6743\u9650\u64cd\u4f5c");
        }
        List<DataSourceInfoVO> list = this.queryDataSourceService.getAllDataSources();
        return BusinessResponseEntity.ok(list);
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/dataSource/updateDataSource"})
    public BusinessResponseEntity<Object> updateDataSource(@RequestBody DataSourceInfoVO dataSourceInfo) {
        this.queryDataSourceService.updateDataSource(dataSourceInfo);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/dataSource/deleteDataSource"})
    public BusinessResponseEntity<Object> deleteDataSource(@RequestBody List<String> codes) {
        this.queryDataSourceService.deleteDataSourceByCode(codes);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/dataSource/testDataSource"})
    public BusinessResponseEntity<Object> testDataSource(@RequestBody DataSourceInfoVO dataSourceInfo) {
        this.queryDataSourceService.testDataSource(dataSourceInfo);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/dataSource/enableTempTable/{datasourceCode}"})
    public BusinessResponseEntity<String> enableTempTable(@PathVariable(value="datasourceCode") String datasourceCode) {
        return BusinessResponseEntity.ok((Object)this.queryDataSourceService.enableTempTable(datasourceCode));
    }
}

