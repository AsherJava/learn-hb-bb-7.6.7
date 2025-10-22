/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

public class FloatSettingFieldDefineTypeUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FloatSettingFieldDefineTypeUpdate.class);

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e[FIELDDEFINETYPE]\u8bbe\u8ba1\u671f\u6570\u636e");
        this.updateQueryConfigInfo(jdbcTemplate, "BDE_FETCHFLOATSETTING_DES");
        logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e[FIELDDEFINETYPE]\u6570\u636e");
        this.updateQueryConfigInfo(jdbcTemplate, "BDE_FETCHFLOATSETTING");
        logger.info("\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void updateQueryConfigInfo(JdbcTemplate jdbcTemplate, String tableName) {
        String queryFloatSettingSql = "SELECT ID, QUERYCONFIGINFO FROM %1$s ";
        List floatSettingList = (List)jdbcTemplate.query(String.format(queryFloatSettingSql, tableName), (ResultSetExtractor)new ResultSetExtractor<List<Pair<String, QueryConfigInfo>>>(){

            public List<Pair<String, QueryConfigInfo>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Pair<String, QueryConfigInfo>> result = new ArrayList<Pair<String, QueryConfigInfo>>(256);
                while (rs.next()) {
                    try {
                        result.add((Pair<String, QueryConfigInfo>)Pair.of((Object)rs.getString(1), (Object)JsonUtils.readValue((String)rs.getString(2), QueryConfigInfo.class)));
                    }
                    catch (Exception exception) {}
                }
                return result;
            }
        });
        if (CollectionUtils.isEmpty((Collection)floatSettingList)) {
            return;
        }
        HashSet<String> fieldDefineKeySet = new HashSet<String>(floatSettingList.size() * 6);
        for (Pair pair : floatSettingList) {
            for (Object zbMappingVO : ((QueryConfigInfo)pair.getSecond()).getZbMapping()) {
                fieldDefineKeySet.add(zbMappingVO.getFieldDefineId());
            }
        }
        String queryFieldDefineTypeSql = "SELECT DF_KEY, DF_DATATYPE FROM NR_DATASCHEME_FIELD WHERE 1 = 1 AND " + SqlBuildUtil.getStrInCondi((String)"DF_KEY", fieldDefineKeySet.stream().collect(Collectors.toList()));
        Map fieldDefineTypeMap = (Map)jdbcTemplate.query(queryFieldDefineTypeSql, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(256);
                while (rs.next()) {
                    try {
                        result.put(rs.getString(1), Integer.valueOf(rs.getString(2)));
                    }
                    catch (Exception exception) {}
                }
                return result;
            }
        });
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(floatSettingList.size());
        for (Pair pair : floatSettingList) {
            for (FloatZbMappingVO zbMappingVO : ((QueryConfigInfo)pair.getSecond()).getZbMapping()) {
                if (!fieldDefineTypeMap.containsKey(zbMappingVO.getFieldDefineId())) continue;
                zbMappingVO.setFieldDefineType((Integer)fieldDefineTypeMap.get(zbMappingVO.getFieldDefineId()));
            }
            batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
        }
        String updateFieldDefineTypeSql = "UPDATE %1$s SET QUERYCONFIGINFO = ? WHERE 1 = 1  AND ID = ? ";
        jdbcTemplate.batchUpdate(String.format(updateFieldDefineTypeSql, tableName), batchArgs);
    }
}

