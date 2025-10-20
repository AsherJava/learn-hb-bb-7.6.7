/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.datasource.vo.SelectOptionVO
 *  com.jiuqi.va.query.datasource.web.QueryDataSourceClient
 */
package com.jiuqi.va.query.datasource.service.join;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.gather.DataBaseHandleGather;
import com.jiuqi.va.query.datasource.service.QueryDataSourceService;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.vo.SelectOptionVO;
import com.jiuqi.va.query.datasource.web.QueryDataSourceClient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class QueryDataSourceClientImpl
implements QueryDataSourceClient {
    @Autowired
    private QueryDataSourceService queryDataSourceService;
    @Autowired
    private DataBaseHandleGather dataBaseHandleGather;

    public BusinessResponseEntity<List<SelectOptionVO>> getSupportDataBase() {
        List<QueryDataBaseHandler> queryDataBaseHandlerList = this.dataBaseHandleGather.getAllHandle();
        ArrayList typeNameList = new ArrayList();
        queryDataBaseHandlerList.forEach(handler -> typeNameList.add(new SelectOptionVO(handler.getTypeName(), handler.getDescription())));
        return BusinessResponseEntity.ok(typeNameList);
    }

    public BusinessResponseEntity<ArrayNode> getSimpleDataSources() {
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

    public BusinessResponseEntity<List<DataSourceInfoVO>> getAllDataSource() {
        List<DataSourceInfoVO> list = this.queryDataSourceService.getAllDataSources();
        return BusinessResponseEntity.ok(list);
    }
}

