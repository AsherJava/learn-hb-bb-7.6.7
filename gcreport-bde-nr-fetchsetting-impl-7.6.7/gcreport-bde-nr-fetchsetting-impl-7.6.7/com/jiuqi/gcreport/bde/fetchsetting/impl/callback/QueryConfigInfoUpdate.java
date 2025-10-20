/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.gcreport.bde.common.vo.MappingInfoVO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.gcreport.bde.common.vo.MappingInfoVO;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.common.JSONUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class QueryConfigInfoUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(QueryConfigInfoUpdate.class);
    private static final String FILED_STRING = " id,definedSql,querydefinecode,mappinginfo ";

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u6570\u636e");
        this.updateByTableName("BDE_FETCHFLOATSETTING_DES", jdbcTemplate);
        logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590d\u8fd0\u884c\u671f\u6570\u636e");
        this.updateByTableName("BDE_FETCHFLOATSETTING", jdbcTemplate);
        logger.info("\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void updateByTableName(String tableName, JdbcTemplate jdbcTemplate) {
        String querSql = "  SELECT  id,definedSql,querydefinecode,mappinginfo  \n FROM " + tableName + "  fs \n";
        Map idToQueryConfigMap = (Map)jdbcTemplate.query(querSql, rs -> this.getIdToQueryConfigMap(rs));
        idToQueryConfigMap.forEach((key, value) -> {
            System.out.println(key + "\uff1a");
            System.out.println((String)value);
        });
        String updateSql = " UPDATE " + tableName + " SET QUERYCONFIGINFO = ? WHERE ID = ?";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (String id : idToQueryConfigMap.keySet()) {
            argsList.add(new Object[]{idToQueryConfigMap.get(id), id});
        }
        jdbcTemplate.batchUpdate(updateSql, argsList);
    }

    private Map<String, String> getIdToQueryConfigMap(ResultSet rs) throws SQLException {
        HashMap<String, String> idToQueryConfigMap = new HashMap<String, String>();
        while (rs.next()) {
            String id = rs.getString(1);
            String definedSql = rs.getString(2);
            String queryDefineCode = rs.getString(3);
            String mappingInfo = rs.getString(4);
            idToQueryConfigMap.put(id, QueryConfigInfoUpdate.getQueryConfigInfoStr(definedSql, queryDefineCode, mappingInfo));
        }
        return idToQueryConfigMap;
    }

    public static String getQueryConfigInfoStr(String definedSql, String queryDefineCode, String mappingInfoStr) {
        MappingInfoVO mappingInfoVO = (MappingInfoVO)JSONUtil.parseObject((String)mappingInfoStr, MappingInfoVO.class);
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        queryConfigInfo.setQueryFields(mappingInfoVO.getQueryFields());
        queryConfigInfo.setUsedFields(mappingInfoVO.getUsedFields());
        queryConfigInfo.setZbMapping(mappingInfoVO.getZbMapping());
        HashMap<String, Object> pluginData = new HashMap<String, Object>();
        pluginData.put("definedSql", definedSql);
        pluginData.put("queryDefineCode", queryDefineCode);
        pluginData.put("argsMapping", mappingInfoVO.getArgsMapping());
        queryConfigInfo.setPluginData(JSONUtil.toJSONString(pluginData));
        return JSONUtil.toJSONString((Object)queryConfigInfo);
    }
}

