/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service;

import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSettingFloatSubjectCodeUpdateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class})
    public void doUpdate(String tableName) {
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
        SimpleCustomComposePluginDataVO pluginDataVO = null;
        HashMap<String, String> codeReplaceMap = new HashMap<String, String>();
        block2: for (Pair pair : floatSettingList) {
            try {
                pluginDataVO = this.getCustomComposePluginDataVO(((QueryConfigInfo)pair.getSecond()).getPluginData());
            }
            catch (Exception e) {
                continue;
            }
            if (CollectionUtils.isEmpty((Collection)pluginDataVO.getDimensionMapping())) continue;
            for (Dimension dimension : pluginDataVO.getDimensionMapping()) {
                String calcCode;
                if (!"SUBJECTCODE".equals(dimension.getDimCode())) continue;
                if (StringUtils.isEmpty((String)dimension.getDimValue()) && StringUtils.isEmpty((String)dimension.getExcludeValue())) continue block2;
                if (!StringUtils.isEmpty((String)dimension.getDimValue()) && dimension.getDimValue().contains(":")) {
                    if (codeReplaceMap.containsKey(dimension.getDimValue())) {
                        dimension.setDimValue((String)codeReplaceMap.get(dimension.getDimValue()));
                        ((QueryConfigInfo)pair.getSecond()).setPluginData(JsonUtils.writeValueAsString((Object)pluginDataVO));
                        batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
                    } else {
                        calcCode = this.calcScopeCode(dimension.getDimValue());
                        if (!calcCode.equals(dimension.getDimValue())) {
                            codeReplaceMap.put(dimension.getDimValue(), calcCode);
                            dimension.setDimValue(calcCode);
                            ((QueryConfigInfo)pair.getSecond()).setPluginData(JsonUtils.writeValueAsString((Object)pluginDataVO));
                            batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
                        }
                    }
                }
                if (StringUtils.isEmpty((String)dimension.getExcludeValue()) || !dimension.getExcludeValue().contains(":")) continue;
                if (codeReplaceMap.containsKey(dimension.getExcludeValue())) {
                    dimension.setExcludeValue((String)codeReplaceMap.get(dimension.getExcludeValue()));
                    ((QueryConfigInfo)pair.getSecond()).setPluginData(JsonUtils.writeValueAsString((Object)pluginDataVO));
                    batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
                    continue;
                }
                calcCode = this.calcScopeCode(dimension.getExcludeValue());
                if (calcCode.equals(dimension.getExcludeValue())) continue;
                codeReplaceMap.put(dimension.getExcludeValue(), calcCode);
                dimension.setExcludeValue(calcCode);
                ((QueryConfigInfo)pair.getSecond()).setPluginData(JsonUtils.writeValueAsString((Object)pluginDataVO));
                batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)pair.getSecond()), pair.getFirst()});
            }
        }
        String updateFieldDefineTypeSql = "UPDATE %1$s SET QUERYCONFIGINFO = ? WHERE 1 = 1  AND ID = ? ";
        this.jdbcTemplate.batchUpdate(String.format(updateFieldDefineTypeSql, tableName), batchArgs);
    }

    private String calcScopeCode(String code) {
        String[] codeArr = code.split(":");
        if (codeArr.length != 2) {
            return code;
        }
        if (StringUtils.isEmpty((String)codeArr[1])) {
            return code;
        }
        String endCode = codeArr[1];
        String sCode = endCode.substring(0, endCode.length() - 1);
        String eCode = endCode.substring(endCode.length() - 1, endCode.length());
        try {
            Integer eCodeInt = Integer.valueOf(eCode);
            eCode = String.valueOf(eCodeInt - 1) + "ZZ";
            endCode = sCode + eCode;
        }
        catch (NumberFormatException e) {
            return code;
        }
        return codeArr[0] + ":" + endCode;
    }

    private SimpleCustomComposePluginDataVO getCustomComposePluginDataVO(String pluginDataStr) {
        if (StringUtils.isEmpty((String)pluginDataStr)) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u68c0\u6d4b\u5230\u914d\u7f6e,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f");
        }
        return (SimpleCustomComposePluginDataVO)JSONUtil.parseObject((String)pluginDataStr, SimpleCustomComposePluginDataVO.class);
    }
}

