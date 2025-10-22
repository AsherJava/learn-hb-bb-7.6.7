/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FloatFetchSettingUpdateService {
    private static final String SELECT_SQL_TEMP = "SELECT ID,FIXEDSETTINGDATA FROM %1$s";
    private static final String UPDATE_SQL_TEMP = "UPDATE %1$s SET FIXEDSETTINGDATA=? WHERE ID=? ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class})
    public void updateCustomFetchOptionByTableName(String tableName) {
        List fetchSettingDesEOs = this.jdbcTemplate.query(String.format(SELECT_SQL_TEMP, tableName), (RowMapper)new BeanPropertyRowMapper(FetchSettingDesEO.class));
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesEOs) {
            Boolean needUpdate = false;
            List fixedAdaptSettingVOS = (List)JsonUtils.readValue((String)fetchSettingDesEO.getFixedSettingData(), (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
            for (FixedAdaptSettingVO fixedAdaptSettingVO : fixedAdaptSettingVOS) {
                for (String key : fixedAdaptSettingVO.getBizModelFormula().keySet()) {
                    List fixedFetchSourceRowSettingVOS = (List)fixedAdaptSettingVO.getBizModelFormula().get(key);
                    for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : fixedFetchSourceRowSettingVOS) {
                        if (!"CUSTOMFETCH".equals(fetchSourceRowSettingVO.getBizModelCode())) continue;
                        fetchSourceRowSettingVO.setOptimizeRuleGroup(this.getOptimizeRuleGroup(fetchSourceRowSettingVO));
                        needUpdate = true;
                    }
                }
            }
            if (!needUpdate.booleanValue()) continue;
            fetchSettingDesEO.setFixedSettingData(JsonUtils.writeValueAsString((Object)fixedAdaptSettingVOS));
            Object[] args = new Object[]{fetchSettingDesEO.getFixedSettingData(), fetchSettingDesEO.getId()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(String.format(UPDATE_SQL_TEMP, tableName), argsList);
    }

    private String getOptimizeRuleGroup(FixedFetchSourceRowSettingVO fetchSourceRowSettingVO) {
        LinkedHashMap<String, String> groupMap = new LinkedHashMap<String, String>();
        LinkedHashMap formulaMap = (LinkedHashMap)JsonUtils.readValue((String)fetchSourceRowSettingVO.getFormula(), (TypeReference)new TypeReference<LinkedHashMap<String, String>>(){});
        LinkedHashMap oldOptimizeRuleGroup = (LinkedHashMap)JsonUtils.readValue((String)fetchSourceRowSettingVO.getOptimizeRuleGroup(), (TypeReference)new TypeReference<LinkedHashMap<String, String>>(){});
        groupMap.put("FETCH_SOURCE_CODE", fetchSourceRowSettingVO.getFetchSourceCode());
        for (Map.Entry entry : formulaMap.entrySet()) {
            if (oldOptimizeRuleGroup.get(entry.getKey()) == null) continue;
            groupMap.put((String)entry.getKey(), (String)oldOptimizeRuleGroup.get(entry.getKey()));
        }
        groupMap.put(OptionItemEnum.DATASOURCECODE.getCode(), (String)oldOptimizeRuleGroup.get(OptionItemEnum.DATASOURCECODE.getCode()));
        groupMap.put("funcCode", (String)oldOptimizeRuleGroup.get("funcCode"));
        return JsonUtils.writeValueAsString(groupMap);
    }

    public void updateQueryConfigInfo(String tableName) {
        String queryFloatSettingSql = "SELECT ID, QUERYCONFIGINFO FROM %1$s WHERE QUERYTYPE = 'SIMPLE_FETCHSOURCE' ";
        List floatSettingList = (List)this.jdbcTemplate.query(String.format(queryFloatSettingSql, tableName), (ResultSetExtractor)new ResultSetExtractor<List<Pair<String, QueryConfigInfo>>>(){

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
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(floatSettingList.size());
        for (Pair pair : floatSettingList) {
            for (FloatQueryFieldVO floatQueryFieldVO : ((QueryConfigInfo)pair.getSecond()).getQueryFields()) {
                if (!FetchTypeEnum.NC.getCode().equals(floatQueryFieldVO.getName()) && !FetchTypeEnum.WNC.getCode().equals(floatQueryFieldVO.getName())) continue;
                floatQueryFieldVO.setTitle(floatQueryFieldVO.getTitle().replace("\u5e74\u521d\u6570", "\u5e74\u521d\u4f59\u989d"));
            }
            batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
        }
        String updateFieldDefineTypeSql = "UPDATE %1$s SET QUERYCONFIGINFO = ? WHERE 1 = 1  AND ID = ? ";
        this.jdbcTemplate.batchUpdate(String.format(updateFieldDefineTypeSql, tableName), batchArgs);
    }
}

