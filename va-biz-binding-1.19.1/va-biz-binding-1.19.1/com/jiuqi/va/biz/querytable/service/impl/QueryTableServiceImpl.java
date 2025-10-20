/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.service.impl;

import com.jiuqi.va.biz.querytable.intf.QueryTable;
import com.jiuqi.va.biz.querytable.service.QueryTableService;
import com.jiuqi.va.biz.querytable.vo.QueryItemVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryTableServiceImpl
implements QueryTableService,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(QueryTableServiceImpl.class);
    private final Map<String, QueryTable> queryTableMap = new ConcurrentHashMap<String, QueryTable>();
    private final List<QueryItemVO> queryItems = new ArrayList<QueryItemVO>();
    @Autowired(required=false)
    private List<QueryTable> queryTables;

    @Override
    public void afterPropertiesSet() {
        if (this.queryTables == null || this.queryTables.isEmpty()) {
            return;
        }
        for (QueryTable queryTable : this.queryTables) {
            if (this.queryTableMap.containsKey(queryTable.getName())) {
                logger.debug("\u91cd\u590d\u6ce8\u518c\u7684\u67e5\u8be2\u8868\u683c\u5b9a\u4e49{}", (Object)queryTable.getName());
            }
            this.queryTableMap.put(queryTable.getName(), queryTable);
            this.queryItems.add(new QueryItemVO(queryTable.getName(), queryTable.getTitle(), queryTable.getQueryParams(), queryTable.getColumns()));
        }
    }

    @Override
    public List<QueryItemVO> getAllQueryItems() {
        return this.queryItems;
    }

    @Override
    public List<QueryTableDataDTO> getQueryTableDataByName(String queryTableName, Map<String, Object> params) {
        QueryTable queryTable = this.queryTableMap.get(queryTableName);
        return queryTable.getQueryTableDataDTO(params);
    }

    @Override
    public List<QueryTableColumnVO> getQueryTableColumns(String queryTableName) {
        QueryTable queryTable = this.queryTableMap.get(queryTableName);
        return queryTable.getColumns();
    }
}

