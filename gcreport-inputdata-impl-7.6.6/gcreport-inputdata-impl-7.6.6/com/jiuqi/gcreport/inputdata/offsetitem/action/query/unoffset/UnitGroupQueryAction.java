/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 *  com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.organization.service.join.OrgDataClientImpl
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.organization.service.join.OrgDataClientImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class UnitGroupQueryAction
implements GcOffSetItemAction {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Resource
    private GcOffSetAppInputDataItemService adjustingEntryService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    OrgDataClientImpl orgDataClient;
    @Autowired
    private GcUnOffsetSelectOptionService gcUnOffsetSelectOptionService;
    @Autowired
    private QueryActionUtils queryActionUtils;
    private final Map<String, Integer> notOffset2PageSize = new ConcurrentHashMap<String, Integer>();
    private static final String NO_OFF_SET = "noOffSet";

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
        this.notOffset2PageSize.put(NO_OFF_SET, queryParamsVO.getPageSize());
        queryParamsVO.setPageSize(-1);
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
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List orgTree = tool.getOrgTree();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        this.getOrgSortMap(orgTree, map, 0);
        TreeMap<String, Object> unitIdMap = new TreeMap<String, Object>((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            if (map.get(o1) == null) {
                return 1;
            }
            if (map.get(o2) == null) {
                return -1;
            }
            return (Integer)map.get(o1) - (Integer)map.get(o2);
        });
        for (Map record : pagination.getContent()) {
            unitIdMap.put(record.get("UNITID").toString(), null);
            unitIdMap.put(record.get("OPPUNITID").toString(), null);
        }
        LinkedHashMap<String, List<Map<String, Object>>> unitIdLinkedMap = new LinkedHashMap<String, List<Map<String, Object>>>();
        unitIdLinkedMap.putAll(unitIdMap);
        queryParamsVO.setPageSize(this.notOffset2PageSize.get(NO_OFF_SET).intValue());
        this.unitGroupFilter(pagination, unitIdLinkedMap, queryParamsVO, tool);
        this.unitGroupChildrenUnitFilter(pagination, queryParamsVO);
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        return pagination;
    }

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params) {
        StringBuffer sql = this.gcOffsetAppInputDataService.getQueryUnOffsetRecordsSql(queryParamsVO, params, false);
        if (!org.springframework.util.StringUtils.hasLength(sql)) {
            return "";
        }
        sql.append("order by (CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.unionRuleId,record.DC desc \n");
        return String.format(sql.toString(), "MDCODE");
    }

    private int getOrgSortMap(List<GcOrgCacheVO> orgTree, Map<String, Integer> orgSortMap, int index) {
        int i;
        for (i = 0; i < orgTree.size(); ++i) {
            orgSortMap.put(orgTree.get(i).getId().toString(), i + index);
            if (CollectionUtils.isEmpty(orgTree.get(i).getChildren())) continue;
            index += i + 1;
            index = this.getOrgSortMap(orgTree.get(i).getChildren(), orgSortMap, index);
        }
        return index += i;
    }

    private void unitGroupFilter(Pagination<Map<String, Object>> pagination, Map<String, List<Map<String, Object>>> unitIdMap, QueryParamsVO queryParamsVO, GcOrgCenterService tool) {
        List content = pagination.getContent();
        boolean isUnitTreeSort = this.queryActionUtils.isUnitTreeSort(FilterMethodEnum.UNITGROUP.getCode(), queryParamsVO.getDataSourceCode(), GcOffsetItemNotOffsetPageImpl.newInstance().getPageCode());
        this.initUnitGroupMap(unitIdMap);
        for (Map record : content) {
            unitIdMap.get(record.get("UNITID").toString()).add(record);
            unitIdMap.get(record.get("OPPUNITID").toString()).add(new HashMap(record));
        }
        pagination.setTotalElements(Integer.valueOf(pagination.getTotalElements() * 2));
        if (queryParamsVO.getPageNum() == -1) {
            this.getAllData(unitIdMap, pagination, tool, isUnitTreeSort, queryParamsVO);
            return;
        }
        this.getPageData(queryParamsVO, unitIdMap, pagination, tool, isUnitTreeSort);
    }

    public void unitGroupChildrenUnitFilter(Pagination<Map<String, Object>> pagination, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List recordListMap = pagination.getContent();
        LinkedHashMap<String, List> unitCode2record = new LinkedHashMap<String, List>();
        if (queryParamsVO.getOrgId() == null && (queryParamsVO.getOppUnitIdList() != null || queryParamsVO.getUnitIdList() != null)) {
            for (int index = 0; index < recordListMap.size(); ++index) {
                if (((Map)recordListMap.get(index)).containsKey("ISHANDLE")) continue;
                ((Map)recordListMap.get(index)).put("ISHANDLE", true);
                ArrayList recordList2 = new ArrayList();
                recordList2.add(recordListMap.get(index));
                List<GcOrgCacheVO> childrenTree = new ArrayList<GcOrgCacheVO>();
                String indexParentUnitId = "";
                boolean parentUnitSame = true;
                for (int count = index + 1; count < recordListMap.size(); ++count) {
                    if (((Map)recordListMap.get(count)).containsKey("ISHANDLE")) continue;
                    if (((Map)recordListMap.get(count)).containsKey("ORGCODE")) {
                        String parentUnitId;
                        if (!CollectionUtils.isEmpty(childrenTree)) {
                            String commonUnit = tool.getCommonUnit(tool.getOrgByCode(((Map)recordListMap.get(index)).get("ORGCODE").toString()), tool.getOrgByCode(((Map)recordListMap.get(count)).get("ORGCODE").toString())).getCode();
                            childrenTree = this.getChildrenTree(tool, commonUnit);
                            indexParentUnitId = this.getParentUnitId(tool, childrenTree, (Map)recordListMap.get(index), "ORGCODE");
                        }
                        if (indexParentUnitId.equals(parentUnitId = this.getParentUnitId(tool, childrenTree, (Map)recordListMap.get(count), "ORGCODE"))) {
                            ((Map)recordListMap.get(count)).put("ISHANDLE", true);
                            recordList2.add(recordListMap.get(count));
                            parentUnitSame = true;
                            continue;
                        }
                        parentUnitSame = false;
                        continue;
                    }
                    if (!parentUnitSame) continue;
                    ((Map)recordListMap.get(count)).put("ISHANDLE", true);
                    recordList2.add(recordListMap.get(count));
                }
                if (StringUtils.isEmpty((String)indexParentUnitId)) {
                    unitCode2record.put(tool.getOrgByCode(((Map)recordListMap.get(index)).get("ORGCODE").toString()).getParentId(), recordList2);
                    continue;
                }
                unitCode2record.put(indexParentUnitId, recordList2);
            }
        } else if (queryParamsVO.getOrgId() != null) {
            List<GcOrgCacheVO> childrenTree = this.getChildrenTree(tool, queryParamsVO.getOrgId());
            String mapKeyUnitCode = "";
            for (Map recordMap : recordListMap) {
                if (recordMap.containsKey("ORGCODE")) {
                    mapKeyUnitCode = this.getParentUnitId(tool, childrenTree, recordMap, "ORGCODE");
                }
                List listMap = unitCode2record.getOrDefault(mapKeyUnitCode, new ArrayList());
                listMap.add(recordMap);
                unitCode2record.put(mapKeyUnitCode, listMap);
            }
        }
        ArrayList recordListMapAfter = new ArrayList();
        unitCode2record.forEach((unitCode, recordList) -> {
            HashMap<String, Object> addMap = new HashMap<String, Object>();
            GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(unitCode);
            if (gcOrgCacheVO == null) {
                addMap.put("UNITTITLE", unitCode);
            } else {
                addMap.put("UNITTITLE", gcOrgCacheVO.getTitle());
            }
            addMap.put("even", true);
            recordListMapAfter.add(addMap);
            recordListMapAfter.addAll(recordList);
        });
        pagination.setContent(recordListMapAfter);
    }

    private void initUnitGroupMap(Map<String, List<Map<String, Object>>> unitIdMap) {
        Set<String> unitIdSet = unitIdMap.keySet();
        unitIdSet.forEach(unitId -> unitIdMap.put((String)unitId, new LinkedList()));
    }

    private void getAllData(Map<String, List<Map<String, Object>>> unitIdMap, Pagination<Map<String, Object>> pagination, GcOrgCenterService tool, boolean isUnitTreeSort, QueryParamsVO queryParamsVO) {
        LinkedList<Map<Object, Object>> unitGroupResult = new LinkedList<Map<Object, Object>>();
        for (String unitId : unitIdMap.keySet()) {
            GcOrgCacheVO vo = tool.getOrgByID(unitId);
            HashMap<String, String> unitGroupItem = new HashMap<String, String>();
            unitGroupItem.put("UNIONRULETITLE", vo == null ? unitId : vo.getTitle());
            unitGroupItem.put("ORGCODE", vo == null ? unitId : vo.getCode());
            unitGroupResult.add(unitGroupItem);
            if (isUnitTreeSort) {
                unitGroupResult.addAll(this.queryActionUtils.sortListByUnitTree(unitIdMap.get(unitId), queryParamsVO));
                continue;
            }
            unitGroupResult.addAll(this.sortList(unitIdMap.get(unitId)));
        }
        pagination.setContent(unitGroupResult);
    }

    private void getPageData(QueryParamsVO queryParamsVO, Map<String, List<Map<String, Object>>> unitIdMap, Pagination<Map<String, Object>> pagination, GcOrgCenterService tool, boolean isUnitTreeSort) {
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        LinkedList<Map<Object, Object>> unitGroupResult = new LinkedList<Map<Object, Object>>();
        Set<String> unitIdSet = unitIdMap.keySet();
        this.getUnitIdSetIterator(unitIdSet, pageNum, pageSize, unitIdMap);
        Iterator<String> iterator = unitIdSet.iterator();
        int resultListSize = 0;
        boolean isBreak = false;
        while (iterator.hasNext()) {
            String unitId = iterator.next();
            int oldListSize = resultListSize;
            if ((resultListSize += unitIdMap.get(unitId).size()) > pageSize) {
                LinkedList<Map<String, Object>> resultList = new LinkedList<Map<String, Object>>();
                List<Map<String, Object>> unitMapList = unitIdMap.get(unitId);
                for (int i = 0; i < pageSize - oldListSize; ++i) {
                    resultList.add(unitMapList.get(i));
                }
                GcOrgCacheVO vo = tool.getOrgByID(unitId);
                HashMap<String, String> unitGroupItem = new HashMap<String, String>();
                unitGroupItem.put("UNIONRULETITLE", vo == null ? unitId : vo.getTitle());
                unitGroupItem.put("ORGCODE", vo == null ? unitId : vo.getCode());
                unitGroupResult.add(unitGroupItem);
                if (isUnitTreeSort) {
                    unitGroupResult.addAll(this.queryActionUtils.sortListByUnitTree(resultList, queryParamsVO));
                    break;
                }
                unitGroupResult.addAll(this.sortList(resultList));
                break;
            }
            GcOrgCacheVO vo = tool.getOrgByID(unitId);
            HashMap<String, String> unitGroupItem = new HashMap<String, String>();
            unitGroupItem.put("UNIONRULETITLE", vo == null ? unitId : vo.getTitle());
            unitGroupItem.put("ORGCODE", vo == null ? unitId : vo.getCode());
            unitGroupResult.add(unitGroupItem);
            if (isUnitTreeSort) {
                unitGroupResult.addAll(this.queryActionUtils.sortListByUnitTree(unitIdMap.get(unitId), queryParamsVO));
            } else {
                unitGroupResult.addAll(this.sortList(unitIdMap.get(unitId)));
            }
            if (resultListSize != pageSize) continue;
            break;
        }
        if (!isBreak && pageNum == 1) {
            pagination.setCurrentPage(Integer.valueOf(-2));
        } else if (!isBreak) {
            pagination.setCurrentPage(Integer.valueOf(0));
        }
        pagination.setContent(unitGroupResult);
    }

    private List<Map<String, Object>> sortList(List<Map<String, Object>> resultList, List<OrgDO> orgDOList) {
        List orgCodes = orgDOList.stream().map(OrgDO::getOrgcode).collect(Collectors.toList());
        resultList.sort((o1, o2) -> {
            int io2;
            int io1 = Math.min(orgCodes.indexOf(o1.get("UNITID")), orgCodes.indexOf(o1.get("OPPUNITID")));
            if (io1 == (io2 = Math.min(orgCodes.indexOf(o2.get("UNITID")), orgCodes.indexOf(o2.get("OPPUNITID"))))) {
                io1 = Math.max(orgCodes.indexOf(o1.get("UNITID")), orgCodes.indexOf(o1.get("OPPUNITID")));
                io2 = Math.max(orgCodes.indexOf(o2.get("UNITID")), orgCodes.indexOf(o2.get("OPPUNITID")));
            }
            if (io1 != -1) {
                io1 = resultList.size() - io1;
            }
            if (io2 != -1) {
                io2 = resultList.size() - io2;
            }
            return io2 - io1;
        });
        return resultList;
    }

    private List<Map<String, Object>> sortList(List<Map<String, Object>> resultList) {
        resultList.sort((item1, item2) -> {
            String unitId1 = item1.get("UNITID").toString();
            String oppunitId1 = item1.get("OPPUNITID").toString();
            String unitId2 = item2.get("UNITID").toString();
            String oppunitId2 = item2.get("OPPUNITID").toString();
            String itemUnit1 = unitId1.compareTo(oppunitId1) > 0 ? unitId1 + oppunitId1 : oppunitId1 + unitId1;
            String itemUnit2 = unitId2.compareTo(oppunitId2) > 0 ? unitId2 + oppunitId2 : oppunitId2 + unitId2;
            return itemUnit1.compareTo(itemUnit2);
        });
        return resultList;
    }

    private void getUnitIdSetIterator(Set<String> unitSet, int pageNum, int pageSize, Map<String, List<Map<String, Object>>> unitIdMap) {
        Iterator<String> iterator = unitSet.iterator();
        if (pageNum == 1) {
            return;
        }
        int listSize = 0;
        while (iterator.hasNext()) {
            String unitId = iterator.next();
            int oldListSize = listSize;
            if ((listSize += unitIdMap.get(unitId).size()) > pageSize) {
                List<Map<String, Object>> unitMaps = unitIdMap.get(unitId);
                Iterator<Map<String, Object>> mapIterator = unitMaps.iterator();
                for (int i = 0; i < pageSize - oldListSize; ++i) {
                    mapIterator.next();
                    mapIterator.remove();
                }
                break;
            }
            iterator.remove();
            if (listSize != pageSize) continue;
            break;
        }
        this.getUnitIdSetIterator(unitSet, pageNum - 1, pageSize, unitIdMap);
    }

    private List<GcOrgCacheVO> getChildrenTree(GcOrgCenterService tool, String orgId) {
        ArrayList<GcOrgCacheVO> childrenTree = new ArrayList<GcOrgCacheVO>();
        List orgTree = tool.getOrgTree(orgId);
        if (!CollectionUtils.isEmpty(orgTree)) {
            childrenTree.add((GcOrgCacheVO)orgTree.get(0));
            if (!CollectionUtils.isEmpty(((GcOrgCacheVO)orgTree.get(0)).getChildren())) {
                childrenTree.addAll(((GcOrgCacheVO)orgTree.get(0)).getChildren());
            }
        }
        return childrenTree;
    }

    private String getParentUnitId(GcOrgCenterService tool, List<GcOrgCacheVO> childrenTree, Map<String, Object> recordMap, String unitName) {
        String parentUnit = "";
        if (StringUtils.isEmpty((String)recordMap.get(unitName).toString())) {
            return "";
        }
        List parentOppUnitList = tool.getParentOrg(recordMap.get(unitName).toString());
        List childrenIdTree = childrenTree.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        if (childrenIdTree.contains(recordMap.get(unitName).toString())) {
            return recordMap.get(unitName).toString();
        }
        for (GcOrgCacheVO gcOrgCacheVO : parentOppUnitList) {
            if (!childrenIdTree.contains(gcOrgCacheVO.getCode())) continue;
            parentUnit = gcOrgCacheVO.getCode();
        }
        return parentUnit;
    }
}

