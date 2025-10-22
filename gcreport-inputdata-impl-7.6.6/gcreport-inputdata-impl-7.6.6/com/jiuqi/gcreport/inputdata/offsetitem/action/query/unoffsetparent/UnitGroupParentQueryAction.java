/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 *  com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.organization.service.join.OrgDataClientImpl
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffsetparent;

import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.UnitGroupQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.join.OrgDataClientImpl;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class UnitGroupParentQueryAction
implements GcOffSetItemAction {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private UnitGroupQueryAction unitGroupQueryAction;
    @Autowired
    OrgDataClientImpl orgDataClient;
    @Autowired
    private GcUnOffsetSelectOptionService gcUnOffsetSelectOptionService;
    @Autowired
    private QueryActionUtils queryActionUtils;
    private static final String NO_OFF_SET_PARENT = "noOffSetParent";
    private final Map<String, Integer> notOffset2PageSize = new ConcurrentHashMap<String, Integer>();

    public String code() {
        return "query";
    }

    public String title() {
        return "\u6309\u76f4\u63a5\u4e0b\u7ea7\u5206\u7ec4\u53cc\u4efd\u5c55\u793a";
    }

    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        GcOffsetItemUtils.logOffsetEntryFilterCondition((QueryParamsVO)queryParamsVO, (String)"\u4e0a\u7ea7\u672a\u62b5\u9500");
        if (StringUtils.isEmpty(queryParamsVO.getOrgId())) {
            return this.unitGroupQueryAction.execute(gcOffsetExecutorVO);
        }
        this.notOffset2PageSize.put(NO_OFF_SET_PARENT, queryParamsVO.getPageSize());
        queryParamsVO.setPageSize(-1);
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params);
        Pagination<Map<String, Object>> pagination = this.gcOffsetAppInputDataService.listUnOffsetRecords(queryParamsVO, sql, params);
        if (CollectionUtils.isEmpty(pagination.getContent())) {
            return pagination;
        }
        if (this.queryActionUtils.isUnitTreeSort(FilterMethodEnum.UNIT.getCode(), queryParamsVO.getDataSourceCode(), GcOffsetItemNotOffsetPageImpl.newInstance().getPageCode())) {
            pagination.setContent(this.queryActionUtils.sortListByUnitTree(pagination.getContent(), queryParamsVO));
        }
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map<String, Map<String, List<Map<String, Object>>>> comUnitIdMap = this.initComUnitMap(queryParamsVO, pagination, tool);
        Set<String> comUnitIdSet = comUnitIdMap.keySet();
        comUnitIdSet.forEach(comUnitId -> {
            LinkedHashMap unitIdLinkedMap = new LinkedHashMap();
            unitIdLinkedMap.putAll((Map)comUnitIdMap.get(comUnitId));
        });
        queryParamsVO.setPageSize(this.notOffset2PageSize.get(NO_OFF_SET_PARENT).intValue());
        YearPeriodDO period = YearPeriodUtil.transform(null, (String)queryParamsVO.getPeriodStr());
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(queryParamsVO.getOrgId());
        orgDTO.setVersionDate(period.getEndDate());
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname(queryParamsVO.getOrgType());
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        PageVO orgDOPageVO = this.orgDataClient.listSubordinate(orgDTO);
        List orgDOList = orgDOPageVO.getRows();
        this.parentUnitGroupFilter(pagination, comUnitIdMap, queryParamsVO, tool, orgDOList);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u4e0a\u7ea7\u672a\u62b5\u9500\u67e5\u770b-\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        return pagination;
    }

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params) {
        StringBuffer sql = this.gcOffsetAppInputDataService.getQueryUnOffsetRecordsSql(queryParamsVO, params, true);
        if (sql == null) {
            return null;
        }
        sql.append("order by (CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.unionRuleId,record.DC desc \n");
        return String.format(sql.toString(), "MDCODE");
    }

    private void parentUnitGroupFilter(Pagination<Map<String, Object>> pagination, Map<String, Map<String, List<Map<String, Object>>>> comUnitIdMap, QueryParamsVO queryParamsVO, GcOrgCenterService tool, List<OrgDO> orgDOList) {
        pagination.setTotalElements(Integer.valueOf(pagination.getTotalElements() * 2));
        pagination.setContent(new LinkedList());
        boolean isUnitTreeSort = new Integer(1).equals(((Map)this.gcUnOffsetSelectOptionService.listSelectOptionForTab(queryParamsVO.getDataSourceCode()).get(0)).get("sorttype"));
        Iterator<String> comUnitMapIterator = comUnitIdMap.keySet().iterator();
        while (comUnitMapIterator.hasNext()) {
            String comUnitId = comUnitMapIterator.next();
            if (comUnitIdMap.get(comUnitId).size() != 0) continue;
            comUnitMapIterator.remove();
        }
        if (queryParamsVO.getPageNum() == -1) {
            this.getParentAllData(comUnitIdMap, pagination, tool, orgDOList, isUnitTreeSort);
        } else {
            this.getParentPageData(queryParamsVO, comUnitIdMap, pagination, tool, orgDOList, isUnitTreeSort);
        }
    }

    private void getParentAllData(Map<String, Map<String, List<Map<String, Object>>>> comUnitIdMap, Pagination<Map<String, Object>> pagination, GcOrgCenterService tool, List<OrgDO> orgDOList, boolean isUnitTreeSort) {
        LinkedList unitGroupResult = new LinkedList();
        Set<String> comUnitSet = comUnitIdMap.keySet();
        comUnitSet.forEach(comUnitId -> {
            unitGroupResult.add(this.getComUnitTitleItem(tool, (String)comUnitId));
            for (String unitId : ((Map)comUnitIdMap.get(comUnitId)).keySet()) {
                GcOrgCacheVO vo = tool.getOrgByID(unitId);
                HashMap<String, String> unitGroupItem = new HashMap<String, String>();
                unitGroupItem.put("UNIONRULETITLE", vo == null ? unitId : vo.getTitle());
                unitGroupResult.add(unitGroupItem);
                if (isUnitTreeSort) {
                    unitGroupResult.addAll(this.sortList((List)((Map)comUnitIdMap.get(comUnitId)).get(unitId), orgDOList));
                    continue;
                }
                unitGroupResult.addAll(this.sortList((List)((Map)comUnitIdMap.get(comUnitId)).get(unitId)));
            }
        });
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

    private void getParentPageData(QueryParamsVO queryParamsVO, Map<String, Map<String, List<Map<String, Object>>>> comUnitIdMap, Pagination<Map<String, Object>> pagination, GcOrgCenterService tool, List<OrgDO> orgDOList, boolean isUnitTreeSort) {
        LinkedList<Map<Object, Object>> unitGroupResult = new LinkedList<Map<Object, Object>>();
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        this.jumpStartPosition(pageNum, pageSize, comUnitIdMap);
        int resultListSize = 0;
        for (String comUnitId : comUnitIdMap.keySet()) {
            unitGroupResult.add(this.getComUnitTitleItem(tool, comUnitId));
            for (String unitId : comUnitIdMap.get(comUnitId).keySet()) {
                LinkedList<Map<String, Object>> resultList = new LinkedList<Map<String, Object>>();
                GcOrgCacheVO vo = tool.getOrgByID(unitId);
                HashMap<String, String> unitGroupItem = new HashMap<String, String>();
                unitGroupItem.put("UNIONRULETITLE", vo == null ? unitId : vo.getTitle());
                unitGroupResult.add(unitGroupItem);
                List<Map<String, Object>> unitMapList = comUnitIdMap.get(comUnitId).get(unitId);
                for (Map<String, Object> item : unitMapList) {
                    resultList.add(item);
                    if (++resultListSize != pageSize) continue;
                    if (isUnitTreeSort) {
                        unitGroupResult.addAll(this.sortList(resultList, orgDOList));
                    } else {
                        unitGroupResult.addAll(this.sortList(resultList));
                    }
                    pagination.setContent(unitGroupResult);
                    return;
                }
                if (isUnitTreeSort) {
                    unitGroupResult.addAll(this.sortList(resultList, orgDOList));
                    continue;
                }
                unitGroupResult.addAll(this.sortList(resultList));
            }
        }
        pagination.setContent(unitGroupResult);
    }

    private HashMap<String, Object> getComUnitTitleItem(GcOrgCenterService tool, String comUnitId) {
        StringBuffer comUnitTitle = new StringBuffer("\u5408\u5e76\u5c42\u7ea7\uff1a");
        GcOrgCacheVO comVo = tool.getOrgByID(comUnitId);
        String[] parents = comVo.getParents();
        for (int i = 0; i < parents.length; ++i) {
            GcOrgCacheVO parent = tool.getOrgByID(parents[i]);
            comUnitTitle.append(parent == null ? parents[i] : parent.getTitle());
            if (i >= parents.length - 1) continue;
            comUnitTitle.append("\\");
        }
        HashMap<String, Object> comUnitGroupItem = new HashMap<String, Object>();
        comUnitGroupItem.put("UNIONRULETITLE", comUnitTitle);
        return comUnitGroupItem;
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

    private Map<String, Map<String, List<Map<String, Object>>>> initComUnitMap(QueryParamsVO queryParamsVO, Pagination<Map<String, Object>> pagination, GcOrgCenterService tool) {
        GcOrgCacheVO comUnit = tool.getOrgByID(queryParamsVO.getOrgId());
        List orgTree = tool.getOrgTree();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        this.getOrgSortMap(orgTree, map, 0);
        List<String> comParents = Arrays.asList(comUnit.getParents());
        LinkedHashMap<String, Map<String, List<Map<String, Object>>>> comUnitIdMap = new LinkedHashMap<String, Map<String, List<Map<String, Object>>>>();
        for (int i = comParents.size() - 2; i >= 0; --i) {
            TreeMap unitIdMap = new TreeMap((o1, o2) -> {
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
            comUnitIdMap.put(comParents.get(i), unitIdMap);
        }
        for (Map record : pagination.getContent()) {
            GcOrgCacheVO unitid = tool.getOrgByID((String)record.get("UNITID"));
            GcOrgCacheVO oppUnitid = tool.getOrgByID((String)record.get("OPPUNITID"));
            LinkedList<String> unitParents = new LinkedList<String>(Arrays.asList(unitid.getParents()));
            String[] tempParents = unitParents.containsAll(comParents) ? oppUnitid.getParents() : unitid.getParents();
            boolean flag = true;
            for (int i = tempParents.length - 1; i >= 0; --i) {
                if (!comParents.contains(tempParents[i])) continue;
                LinkedList<Map> unitRecordList = new LinkedList<Map>();
                LinkedList oppUnitRecordList = new LinkedList();
                if (((Map)comUnitIdMap.get(tempParents[i])).get(record.get("UNITID").toString()) == null) {
                    unitRecordList.add(record);
                } else {
                    unitRecordList.addAll((Collection)((Map)comUnitIdMap.get(tempParents[i])).get(record.get("UNITID").toString()));
                    unitRecordList.add(record);
                }
                if (((Map)comUnitIdMap.get(tempParents[i])).get(record.get("OPPUNITID").toString()) == null) {
                    oppUnitRecordList.add(new HashMap(record));
                } else {
                    oppUnitRecordList.addAll((Collection)((Map)comUnitIdMap.get(tempParents[i])).get(record.get("OPPUNITID").toString()));
                    oppUnitRecordList.add(new HashMap(record));
                }
                ((Map)comUnitIdMap.get(tempParents[i])).put(record.get("UNITID").toString(), unitRecordList);
                ((Map)comUnitIdMap.get(tempParents[i])).put(record.get("OPPUNITID").toString(), oppUnitRecordList);
                flag = false;
                break;
            }
            if (!flag) continue;
            System.out.println("\u672a\u62b5\u9500\u4e0a\u7ea7\u67e5\u8be2\uff1a\u8bb0\u5f55 " + new JSONObject(record).toString() + "\u7684\u5171\u540c\u4e0a\u7ea7\u4e0d\u5728" + comParents + "\u4e2d");
        }
        return comUnitIdMap;
    }

    private void jumpStartPosition(int pageNum, int pageSize, Map<String, Map<String, List<Map<String, Object>>>> comUnitIdMap) {
        if (pageNum == 1) {
            return;
        }
        Iterator<String> iterator = comUnitIdMap.keySet().iterator();
        int removeListSize = 0;
        int startIndex = pageSize * (pageNum - 1);
        while (iterator.hasNext()) {
            String comUnitId = iterator.next();
            Iterator<String> unitIdIterator = comUnitIdMap.get(comUnitId).keySet().iterator();
            while (unitIdIterator.hasNext()) {
                String unitId = unitIdIterator.next();
                int oldListSize = removeListSize;
                if ((removeListSize += comUnitIdMap.get(comUnitId).get(unitId).size()) > startIndex) {
                    List<Map<String, Object>> unitMapList = comUnitIdMap.get(comUnitId).get(unitId);
                    Iterator<Map<String, Object>> mapIterator = unitMapList.iterator();
                    for (int needRemoveNumber = startIndex - oldListSize; needRemoveNumber > 0 && mapIterator.hasNext(); --needRemoveNumber) {
                        mapIterator.next();
                        mapIterator.remove();
                    }
                    return;
                }
                unitIdIterator.remove();
                if (removeListSize != startIndex) continue;
                if (CollectionUtils.isEmpty(comUnitIdMap.get(comUnitId))) {
                    iterator.remove();
                }
                return;
            }
            iterator.remove();
        }
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
}

