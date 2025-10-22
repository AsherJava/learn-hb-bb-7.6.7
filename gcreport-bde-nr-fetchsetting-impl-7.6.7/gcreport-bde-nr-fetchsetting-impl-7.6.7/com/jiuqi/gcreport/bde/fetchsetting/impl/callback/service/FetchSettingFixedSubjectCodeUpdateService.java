/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSettingFixedSubjectCodeUpdateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class})
    public void doUpdate(String tableName) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        List<FetchSettingVO> fetchSettingVOS = this.listFetchSettingByFetchSchemeId(tableName);
        for (FetchSettingVO fetchSettingVO : fetchSettingVOS) {
            boolean updateFlag = false;
            for (FixedAdaptSettingVO fixedSettingData : fetchSettingVO.getFixedSettingData()) {
                HashMap<String, String> codeReplaceMap = new HashMap<String, String>();
                for (Map.Entry entry : fixedSettingData.getBizModelFormula().entrySet()) {
                    for (FixedFetchSourceRowSettingVO rowSettingVO : (List)entry.getValue()) {
                        if (StringUtils.isEmpty((String)rowSettingVO.getSubjectCode()) || !rowSettingVO.getSubjectCode().contains(":")) continue;
                        if (codeReplaceMap.containsKey(rowSettingVO.getSubjectCode())) {
                            rowSettingVO.setSubjectCode((String)codeReplaceMap.get(rowSettingVO.getSubjectCode()));
                            updateFlag = true;
                            continue;
                        }
                        String calcCode = this.calcScopeCode(rowSettingVO.getSubjectCode());
                        if (calcCode.equals(rowSettingVO.getSubjectCode())) continue;
                        codeReplaceMap.put(rowSettingVO.getSubjectCode(), calcCode);
                        rowSettingVO.setSubjectCode(calcCode);
                        updateFlag = true;
                    }
                }
                if (!updateFlag) continue;
                fixedSettingData.setMemo(this.calcMemo(codeReplaceMap, fixedSettingData.getMemo()));
            }
            if (!updateFlag) continue;
            batchArgs.add(new Object[]{JsonUtils.writeValueAsString((Object)fetchSettingVO.getFixedSettingData()), fetchSettingVO.getId()});
        }
        this.updateBatch(tableName, batchArgs);
    }

    private String calcMemo(Map<String, String> codeReplaceMap, String memo) {
        if (StringUtils.isEmpty((String)memo)) {
            return memo;
        }
        for (Map.Entry<String, String> replaceMapEntry : codeReplaceMap.entrySet()) {
            memo = memo.replace(replaceMapEntry.getKey(), replaceMapEntry.getValue());
        }
        return memo;
    }

    private void updateBatch(String tableName, List<Object[]> batchArgs) {
        if (CollectionUtils.isEmpty(batchArgs)) {
            return;
        }
        String sql = "update " + tableName + " set fixedSettingData = ? where id = ? ";
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
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

    public List<FetchSettingVO> listFetchSettingByFetchSchemeId(String tableName) {
        String sql = "SELECT  id, fixedSettingData FROM BK240529_" + tableName + " fs where 1 = 1   \n";
        List fetchSettingDesEOS = this.jdbcTemplate.query(sql.toString(), (rs, row) -> {
            FetchSettingDesEO eo = new FetchSettingDesEO();
            eo.setId(rs.getString(1));
            eo.setFixedSettingData(rs.getString(2));
            return eo;
        }, new Object[0]);
        return fetchSettingDesEOS.stream().map(item -> FetchSettingNrUtil.convertFetchSettingDesEOToVo(item)).collect(Collectors.toList());
    }
}

