/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ChildrenUnitGroupQueryAction
implements GcOffSetItemAction {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Resource
    private GcOffSetAppInputDataItemService adjustingEntryService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        Pagination<Map<String, Object>> pagination;
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        this.adjustingEntryService.handleUnitAndOppUnitParam(queryParamsVO);
        try {
            ArrayList<Object> params = new ArrayList<Object>();
            String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params);
            pagination = this.gcOffsetAppInputDataService.listUnOffsetRecords(queryParamsVO, sql, params);
        }
        finally {
            if (!CollectionUtils.isEmpty(queryParamsVO.getTempGroupIdList())) {
                IdTemporaryTableUtils.deteteByGroupIds((Collection)queryParamsVO.getTempGroupIdList());
            }
        }
        this.handleChildrenUnitGroup(queryParamsVO, pagination);
        this.handleSumData(pagination);
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        return pagination;
    }

    public void handleSumData(Pagination<Map<String, Object>> pagination) {
        List recordList = pagination.getContent();
        ArrayList<Map> recordAfterList = new ArrayList<Map>();
        for (int index = 0; index < recordList.size(); ++index) {
            Map record = (Map)recordList.get(index);
            if (record.containsKey("ISSUMHANDLE")) continue;
            record.put("ISSUMHANDLE", true);
            recordAfterList.add(record);
            for (int count = index + 1; count < recordList.size(); ++count) {
                Map countRecord = (Map)recordList.get(count);
                if (countRecord.containsKey("ISSUMHANDLE") || (!record.get("OPPUNITID").equals(countRecord.get("OPPUNITID")) || !record.get("UNITID").equals(countRecord.get("UNITID"))) && (!record.get("OPPUNITID").equals(countRecord.get("UNITID")) || !record.get("UNITID").equals(countRecord.get("OPPUNITID")))) continue;
                countRecord.put("ISSUMHANDLE", true);
                recordAfterList.add(countRecord);
            }
            HashMap<String, Boolean> addMap = new HashMap<String, Boolean>();
            addMap.put("even", true);
            recordAfterList.add(addMap);
        }
        pagination.setContent(recordAfterList);
    }

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params) {
        StringBuffer sql = this.gcOffsetAppInputDataService.getQueryUnOffsetRecordsSql(queryParamsVO, params, false);
        if (!org.springframework.util.StringUtils.hasLength(sql)) {
            return "";
        }
        sql.append("order by record.unionRuleId,(CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END) asc,record.DC desc\n");
        return String.format(sql.toString(), "MDCODE");
    }

    public void handleChildrenUnitGroup(QueryParamsVO queryParamsVO, Pagination<Map<String, Object>> pagination) {
        List recordListMap = pagination.getContent();
        if (recordListMap == null || recordListMap.size() == 0) {
            return;
        }
        ArrayList<Map<String, Object>> recordListMapInit = new ArrayList<Map<String, Object>>();
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map orgCode2ParentOrgMap = tool.getOrgCode2ParentOrgMap();
        Map orgCode2DirectChildOrgCodesMap = tool.getOrgId2DirectChildOrgCodesMap();
        if (!(!StringUtils.isEmpty((String)queryParamsVO.getOrgId()) || CollectionUtils.isEmpty(queryParamsVO.getOppUnitIdList()) && CollectionUtils.isEmpty(queryParamsVO.getUnitIdList()))) {
            for (int index = 0; index < recordListMap.size(); ++index) {
                if (((Map)recordListMap.get(index)).containsKey("ISHANDLE")) continue;
                ((Map)recordListMap.get(index)).put("ISHANDLE", true);
                recordListMapInit.add((Map<String, Object>)recordListMap.get(index));
                GcOrgCacheVO gcOrgCacheVO = tool.getCommonUnit(tool.getOrgByCode(((Map)recordListMap.get(index)).get("UNITID").toString()), tool.getOrgByCode(((Map)recordListMap.get(index)).get("OPPUNITID").toString()));
                List directChildOrgCodes = (List)orgCode2DirectChildOrgCodesMap.get(gcOrgCacheVO.getId());
                String parentUnitId = this.getParentUnitId(directChildOrgCodes, (Map)recordListMap.get(index), orgCode2ParentOrgMap, "UNITID");
                String parentOppUnitId = this.getParentUnitId(directChildOrgCodes, (Map)recordListMap.get(index), orgCode2ParentOrgMap, "OPPUNITID");
                this.handleChildrenUnit(index, recordListMap, recordListMapInit, orgCode2ParentOrgMap, tool, directChildOrgCodes, parentUnitId, parentOppUnitId);
            }
        } else if (!StringUtils.isEmpty((String)queryParamsVO.getOrgId())) {
            List directChildOrgCodes = (List)orgCode2DirectChildOrgCodesMap.get(queryParamsVO.getOrgId());
            for (int index = 0; index < recordListMap.size(); ++index) {
                if (((Map)recordListMap.get(index)).containsKey("ISHANDLE")) continue;
                ((Map)recordListMap.get(index)).put("ISHANDLE", true);
                recordListMapInit.add((Map<String, Object>)recordListMap.get(index));
                String parentUnitId = this.getParentUnitId(directChildOrgCodes, (Map)recordListMap.get(index), orgCode2ParentOrgMap, "UNITID");
                String parentOppUnitId = this.getParentUnitId(directChildOrgCodes, (Map)recordListMap.get(index), orgCode2ParentOrgMap, "OPPUNITID");
                this.handleChildrenUnit(index, recordListMap, recordListMapInit, orgCode2ParentOrgMap, tool, directChildOrgCodes, parentUnitId, parentOppUnitId);
            }
        }
        pagination.setContent(recordListMapInit);
    }

    private void handleChildrenUnit(int index, List<Map<String, Object>> recordListMap, List<Map<String, Object>> recordListMapInit, Map<String, List<GcOrgCacheVO>> orgCode2ParentOrgMap, GcOrgCenterService tool, List<String> directChildOrgCodes, String parentUnitId, String parentOppUnitId) {
        for (int count = index + 1; count < recordListMap.size(); ++count) {
            String parentUnitIdTrans = this.getParentUnitId(directChildOrgCodes, recordListMap.get(count), orgCode2ParentOrgMap, "UNITID");
            String parentOppUnitIdTrans = this.getParentUnitId(directChildOrgCodes, recordListMap.get(count), orgCode2ParentOrgMap, "OPPUNITID");
            if (recordListMap.get(count).containsKey("ISHANDLE") || !parentUnitId.equals(parentUnitIdTrans) || !parentOppUnitId.equals(parentOppUnitIdTrans)) continue;
            recordListMap.get(count).put("ISHANDLE", true);
            recordListMapInit.add(recordListMap.get(count));
        }
    }

    private String getParentUnitId(List<String> directChildOrgCodes, Map<String, Object> recordMap, Map<String, List<GcOrgCacheVO>> orgCode2ParentOrgMap, String unitName) {
        String parentUnit = "";
        if (StringUtils.isEmpty((String)recordMap.get(unitName).toString())) {
            return "";
        }
        List<GcOrgCacheVO> parentOppUnitList = orgCode2ParentOrgMap.get(recordMap.get(unitName).toString());
        if (directChildOrgCodes.contains(recordMap.get(unitName).toString())) {
            return recordMap.get(unitName).toString();
        }
        for (GcOrgCacheVO gcOrgCacheVO : parentOppUnitList) {
            if (!directChildOrgCodes.contains(gcOrgCacheVO.getCode())) continue;
            parentUnit = gcOrgCacheVO.getCode();
        }
        return parentUnit;
    }
}

