/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.sumtab.SumTabResultVo
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.sumtab.SumTabResultVo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SumTabTaskImpl
extends GcOffSetItemAdjustServiceAbstract {
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private GcOffSetItemAdjustCoreService adjustingEntryCoreService;
    @Autowired
    private GcOffsetVchrQueryImpl offsetVchrQuery;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcInputDataOffsetItemService gcInputDataOffsetItemService;

    public List<List<Map<String, Object>>> joinData(List<List<Map<String, Object>>> offsetDatas, List<List<Map<String, Object>>> unOffsetDatas) {
        SumTabResultVo sumTabResultVo;
        DoubleKeyMap bizTypeCode2subjectCode2sumTabResultMap = new DoubleKeyMap();
        for (List<Map<String, Object>> offsetData : offsetDatas) {
            for (Map<String, Object> record : offsetData) {
                sumTabResultVo = new SumTabResultVo().copyFromOffset(record);
                bizTypeCode2subjectCode2sumTabResultMap.put((Object)sumTabResultVo.getGcBusinessTypeCode(), (Object)sumTabResultVo.getSecondSub(), (Object)sumTabResultVo);
            }
        }
        for (List<Map<String, Object>> offsetData : unOffsetDatas) {
            for (Map<String, Object> record : offsetData) {
                sumTabResultVo = new SumTabResultVo().copyFromUnOffset(record);
                SumTabResultVo oldSumTabResultVo = (SumTabResultVo)bizTypeCode2subjectCode2sumTabResultMap.get((Object)sumTabResultVo.getGcBusinessTypeCode(), (Object)sumTabResultVo.getSecondSub());
                bizTypeCode2subjectCode2sumTabResultMap.put((Object)sumTabResultVo.getGcBusinessTypeCode(), (Object)sumTabResultVo.getSecondSub(), (Object)sumTabResultVo.add(oldSumTabResultVo));
            }
        }
        ArrayList<List<Map<String, Object>>> sameBizTypeResult = new ArrayList<List<Map<String, Object>>>();
        for (String bizTypeCode : bizTypeCode2subjectCode2sumTabResultMap.keySet()) {
            ArrayList<Map> sameSubjectResult = new ArrayList<Map>();
            Map subjectCode2sumTabResultMap = bizTypeCode2subjectCode2sumTabResultMap.get((Object)bizTypeCode);
            for (String subjectCode : subjectCode2sumTabResultMap.keySet()) {
                sameSubjectResult.add(((SumTabResultVo)subjectCode2sumTabResultMap.get(subjectCode)).convert2Map());
            }
            sameBizTypeResult.add(sameSubjectResult);
        }
        sameBizTypeResult.sort((o1, o2) -> {
            String bizTypeCode1 = (String)((Map)o1.get(0)).get("GCBUSINESSTYPECODE");
            String bizTypeCode2 = (String)((Map)o2.get(0)).get("GCBUSINESSTYPECODE");
            return bizTypeCode1.compareTo(bizTypeCode2);
        });
        return sameBizTypeResult;
    }

    public Pagination<Map<String, Object>> getSumTabDataForSelectSubject(List<Map<String, Object>> subjectVos, String systemId, QueryParamsVO queryParamsVO, List<ConsolidatedSubjectEO> allSubjects, Map<String, List<String>> subjectCodeToParentCodesMap) {
        Pagination sumTabRecords = new Pagination();
        ArrayList<String> commonConditions = new ArrayList<String>();
        ArrayList<String> allConditions = new ArrayList<String>();
        this.handleSelectColumnFields(queryParamsVO, commonConditions, allConditions, systemId);
        Map<String, String> fieldCode2DictTableMap = this.gcInputDataOffsetItemService.initFieldCode2DictTableMap(allConditions, queryParamsVO.getTaskId());
        Map unSysNumberField2Decimal = this.getUnSysNumberField2Decimal(systemId);
        List<List<Map<String, Object>>> offsetDatasGroupByBizTypeCode = this.queryOffsetBySelectSubject(queryParamsVO, subjectVos, systemId, subjectCodeToParentCodesMap);
        List<Map<String, Object>> otherOffsetDatas = this.queryOtherOffsetBySelect(queryParamsVO, subjectVos, systemId, subjectCodeToParentCodesMap);
        if (queryParamsVO.getFilterCondition().get("elmMode") != null) {
            this.handleElmMode(queryParamsVO, offsetDatasGroupByBizTypeCode, otherOffsetDatas);
        }
        queryParamsVO.setFilterCondition(queryParamsVO.getNotOffsetFilterCondition());
        List<List<Map<String, Object>>> unOffsetDatasGroupByBizTypeCode = this.queryUnOffsetByselectSubject(queryParamsVO, subjectVos, systemId, subjectCodeToParentCodesMap);
        List<Map<String, Object>> otherUnOffsetDatas = this.queryOtherUnOffsetBySelect(queryParamsVO, subjectVos, systemId, subjectCodeToParentCodesMap);
        List<Map<String, Object>> sumTabDatas = this.arrangeForSelectData(offsetDatasGroupByBizTypeCode, unOffsetDatasGroupByBizTypeCode, subjectVos, allSubjects, commonConditions, queryParamsVO, true);
        List<Map<String, Object>> otherDatas = this.arrangeForOtherData(otherOffsetDatas, otherUnOffsetDatas, subjectVos, allSubjects, commonConditions, queryParamsVO);
        sumTabDatas.addAll(otherDatas);
        sumTabRecords.setTotalElements(Integer.valueOf(sumTabDatas.size()));
        sumTabRecords.setContent(this.handlePageData(sumTabDatas, queryParamsVO));
        sumTabRecords.setCurrentPage(Integer.valueOf(queryParamsVO.getPageNum()));
        sumTabRecords.setPageSize(Integer.valueOf(queryParamsVO.getPageSize()));
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        for (Map record : sumTabRecords.getContent()) {
            Map fieldCode2OrgToolMap = this.initFieldCode2OrgToolMap(fieldCode2DictTableMap, queryParamsVO.getPeriodStr());
            this.setOtherShowColumnDictTitle(record, allConditions, fieldCode2DictTableMap, showDictCode);
            this.formatOtherShowNumberField(record, unSysNumberField2Decimal);
            this.setOtherShowColumnOrgTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2OrgToolMap, showDictCode);
        }
        return sumTabRecords;
    }

    public Pagination<Map<String, Object>> getSumTabDataForUnSelectSubject(QueryParamsVO queryParamsVO, String systemId, Map<String, String> subjectCodeToSecSubjectCodeMap, List<ConsolidatedSubjectEO> allSubjects) {
        Pagination sumTabRecords = new Pagination();
        if (StringUtils.isEmpty((String)queryParamsVO.getOrgId()) && CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
            return sumTabRecords;
        }
        ArrayList<String> commonConditions = new ArrayList<String>();
        ArrayList<String> allConditions = new ArrayList<String>();
        this.handleSelectColumnFields(queryParamsVO, commonConditions, allConditions, systemId);
        Map<String, String> fieldCode2DictTableMap = this.gcInputDataOffsetItemService.initFieldCode2DictTableMap(allConditions, queryParamsVO.getTaskId());
        Map unSysNumberField2Decimal = this.getUnSysNumberField2Decimal(systemId);
        List<List<Map<String, Object>>> offsetDatasGroupByBizTypeCode = this.queryOffsetBySecondAccount(queryParamsVO, subjectCodeToSecSubjectCodeMap);
        List<Map<String, Object>> otherOffsetDatas = this.queryOtherOffsetBySecondAccount(queryParamsVO, subjectCodeToSecSubjectCodeMap);
        if (queryParamsVO.getFilterCondition().get("elmMode") != null) {
            this.handleElmMode(queryParamsVO, offsetDatasGroupByBizTypeCode, otherOffsetDatas);
        }
        queryParamsVO.setFilterCondition(queryParamsVO.getNotOffsetFilterCondition());
        List<List<Map<String, Object>>> unOffsetDatasGroupByBizTypeCode = this.queryUnOffsetBySecondAccount(queryParamsVO, subjectCodeToSecSubjectCodeMap);
        List<Map<String, Object>> otherUnOffsetDatas = this.queryOtherUnOffsetBySecondAccount(queryParamsVO, subjectCodeToSecSubjectCodeMap);
        List<Map<String, Object>> sumTabDatas = this.arrangeForUnSelectData(offsetDatasGroupByBizTypeCode, unOffsetDatasGroupByBizTypeCode, allSubjects, commonConditions, queryParamsVO);
        List<Map<String, Object>> otherDatas = this.arrangeForOtherData(otherOffsetDatas, otherUnOffsetDatas, null, allSubjects, commonConditions, queryParamsVO);
        sumTabDatas.addAll(otherDatas);
        sumTabRecords.setTotalElements(Integer.valueOf(sumTabDatas.size()));
        sumTabRecords.setContent(this.handlePageData(sumTabDatas, queryParamsVO));
        sumTabRecords.setCurrentPage(Integer.valueOf(queryParamsVO.getPageNum()));
        sumTabRecords.setPageSize(Integer.valueOf(queryParamsVO.getPageSize()));
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        for (Map record : sumTabRecords.getContent()) {
            Map fieldCode2OrgToolMap = this.initFieldCode2OrgToolMap(fieldCode2DictTableMap, queryParamsVO.getPeriodStr());
            this.setOtherShowColumnDictTitle(record, allConditions, fieldCode2DictTableMap, showDictCode);
            this.formatOtherShowNumberField(record, unSysNumberField2Decimal);
            this.setOtherShowColumnOrgTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2OrgToolMap, showDictCode);
        }
        return sumTabRecords;
    }

    private List<List<Map<String, Object>>> queryOffsetBySelectSubject(QueryParamsVO queryParamsVO, List<Map<String, Object>> subjectVos, String systemId, Map<String, List<String>> subjectCodeToParentCodesMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> offsetDatas = this.queryOffsetForAllEntry(queryParamsVO);
        offsetDatas = offsetDatas.stream().filter(offsetData -> offsetData.get("GCBUSINESSTYPECODE") != null).collect(Collectors.toList());
        return this.summarySumTabDataSelectSubject(offsetDatas, queryParamsVO.getOtherShowColumns(), subjectVos, subjectCodeToParentCodesMap, true, queryParamsVO);
    }

    private List<List<Map<String, Object>>> summarySumTabDataSelectSubject(List<Map<String, Object>> offsetDatas, List<String> selectColumns, List<Map<String, Object>> subjectVos, Map<String, List<String>> subjectCodeToParentCodesMap, boolean isOffset, QueryParamsVO queryParamsVO) {
        ArrayList<List<Map<String, Object>>> finalOffsetDatas = new ArrayList<List<Map<String, Object>>>();
        ArrayList<List<Map>> groupByGcBussTypeDatas = new ArrayList<List<Map>>(offsetDatas.stream().collect(Collectors.groupingBy(item -> item.get("GCBUSINESSTYPECODE"))).values());
        groupByGcBussTypeDatas.forEach(groupByGcBussTypeData -> {
            ArrayList datas = new ArrayList();
            ArrayList<List<Map>> groupByOtherColumnLists = new ArrayList<List<Map>>(groupByGcBussTypeData.stream().collect(Collectors.groupingBy(item -> {
                StringBuilder groupStr = new StringBuilder("");
                selectColumns.forEach(offsetkey -> {
                    if (item.get(offsetkey) != null && !(item.get(offsetkey) instanceof Double)) {
                        groupStr.append(item.get(offsetkey));
                    }
                });
                return groupStr.toString();
            })).values());
            groupByOtherColumnLists.forEach((Consumer<List<Map>>)((Consumer<List>)groupByOtherColumnData -> {
                for (Map subjectVO : subjectVos) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    BigDecimal offsetCreditValue = BigDecimal.ZERO;
                    BigDecimal offsetDebitValue = BigDecimal.ZERO;
                    BigDecimal offsetDiffValue = BigDecimal.ZERO;
                    HashMap<String, BigDecimal> otherDoubleColumn2Value = new HashMap<String, BigDecimal>();
                    for (String offsetselectColumn : selectColumns) {
                        if (!(((Map)groupByOtherColumnData.get(0)).get(offsetselectColumn) instanceof Double)) continue;
                        otherDoubleColumn2Value.put(offsetselectColumn, BigDecimal.ZERO);
                    }
                    List subjectCodes = (List)subjectCodeToParentCodesMap.get((String)subjectVO.get("code"));
                    ArrayList<String> allSubjectCodes = new ArrayList<String>();
                    allSubjectCodes.add((String)subjectVO.get("code"));
                    allSubjectCodes.addAll(subjectCodes);
                    boolean isExist = false;
                    for (String codes : allSubjectCodes) {
                        for (Map offsetData : groupByOtherColumnData) {
                            if (!codes.equals(offsetData.get("SUBJECTCODE"))) continue;
                            isExist = true;
                            offsetCreditValue = offsetCreditValue.add(ConverterUtils.getAsBigDecimal(offsetData.get("CREDITVALUE")));
                            offsetDebitValue = offsetDebitValue.add(ConverterUtils.getAsBigDecimal(offsetData.get("DEBITVALUE")));
                            if (isOffset) {
                                String diff = (String)offsetData.get("DIFF");
                                BigDecimal diffVal = BigDecimal.valueOf(Double.valueOf(diff.replace(",", "")));
                                offsetDiffValue = offsetDiffValue.add(diffVal);
                            }
                            for (String otherDoubleColumn : otherDoubleColumn2Value.keySet()) {
                                if (offsetData.get(otherDoubleColumn) == null) continue;
                                BigDecimal otherDoubleColumnValue = ConverterUtils.getAsBigDecimal(offsetData.get(otherDoubleColumn));
                                otherDoubleColumn2Value.put(otherDoubleColumn, ((BigDecimal)otherDoubleColumn2Value.get(otherDoubleColumn)).add(otherDoubleColumnValue));
                            }
                        }
                    }
                    if (!isExist) continue;
                    for (String otherDoubleColumn : otherDoubleColumn2Value.keySet()) {
                        data.put(otherDoubleColumn, NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble(otherDoubleColumn2Value.get(otherDoubleColumn))));
                    }
                    selectColumns.forEach(otherShowColumn -> {
                        if (!otherDoubleColumn2Value.containsKey(otherShowColumn)) {
                            data.put((String)otherShowColumn, ((Map)groupByOtherColumnData.get(0)).get(otherShowColumn));
                        }
                    });
                    data.put("SECONDSUB", subjectVO.get("code"));
                    data.put("subTitle", subjectVO.get("title"));
                    data.put("GCBUSINESSTYPECODE", ((Map)groupByGcBussTypeData.get(0)).get("GCBUSINESSTYPECODE"));
                    data.put("CREDITVALUE", offsetCreditValue.doubleValue());
                    data.put("DEBITVALUE", offsetDebitValue.doubleValue());
                    if (isOffset) {
                        data.put("DIFFVALUE", offsetDiffValue.doubleValue());
                    }
                    datas.add(data);
                }
            }));
            finalOffsetDatas.add(datas);
        });
        return finalOffsetDatas;
    }

    private List<Map<String, Object>> summaryOtherSumTabDataSelectSubject(List<Map<String, Object>> offsetDatas, List<String> selectColumns, List<Map<String, Object>> subjectVos, String systemId, Map<String, List<String>> subjectCodeToParentCodesMap, boolean isOffset) {
        ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        ArrayList<List<Map>> groupByOtherColumnLists = new ArrayList<List<Map>>(offsetDatas.stream().collect(Collectors.groupingBy(item -> {
            StringBuilder groupStr = new StringBuilder("");
            selectColumns.forEach(offsetkey -> {
                if (item.get(offsetkey) != null) {
                    groupStr.append(item.get(offsetkey));
                }
            });
            return groupStr.toString();
        })).values());
        groupByOtherColumnLists.forEach((Consumer<List<Map>>)((Consumer<List>)groupByOtherColumnData -> {
            for (Map subjectVO : subjectVos) {
                HashMap data = new HashMap();
                BigDecimal offsetCreditValue = BigDecimal.ZERO;
                BigDecimal offsetDebitValue = BigDecimal.ZERO;
                BigDecimal offsetDiffValue = BigDecimal.ZERO;
                List childCodes = (List)subjectCodeToParentCodesMap.get((String)subjectVO.get("code"));
                ArrayList<String> allSubjectCodes = new ArrayList<String>();
                allSubjectCodes.add((String)subjectVO.get("code"));
                allSubjectCodes.addAll(childCodes);
                boolean isExist = false;
                for (String childCode : allSubjectCodes) {
                    for (Map offsetData : offsetDatas) {
                        if (!childCode.equals(offsetData.get("SUBJECTCODE"))) continue;
                        isExist = true;
                        offsetCreditValue = offsetCreditValue.add(ConverterUtils.getAsBigDecimal(offsetData.get("CREDITVALUE")));
                        offsetDebitValue = offsetDebitValue.add(ConverterUtils.getAsBigDecimal(offsetData.get("DEBITVALUE")));
                        if (!isOffset) continue;
                        String diff = (String)offsetData.get("DIFF");
                        BigDecimal diffVal = BigDecimal.valueOf(Double.valueOf(diff.replace(",", "")));
                        offsetDiffValue = offsetDiffValue.add(diffVal);
                    }
                }
                if (!isExist) continue;
                selectColumns.forEach(otherShowColumn -> data.put((String)otherShowColumn, ((Map)groupByOtherColumnData.get(0)).get(otherShowColumn)));
                data.put("SECONDSUB", subjectVO.get("code"));
                data.put("subTitle", subjectVO.get("title"));
                data.put("GCBUSINESSTYPECODE", ((Map)offsetDatas.get(0)).get("GCBUSINESSTYPECODE"));
                data.put("CREDITVALUE", offsetCreditValue.doubleValue());
                data.put("DEBITVALUE", offsetDebitValue.doubleValue());
                if (isOffset) {
                    data.put("DIFFVALUE", offsetDiffValue.doubleValue());
                }
                datas.add(data);
            }
        }));
        return datas;
    }

    private List<Map<String, Object>> queryOtherOffsetBySelect(QueryParamsVO queryParamsVO, List<Map<String, Object>> subjectVos, String systemId, Map<String, List<String>> subjectCodeToParentCodesMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> offsetDatas = this.queryOffsetForAllEntry(queryParamsVO);
        offsetDatas = offsetDatas.stream().filter(offsetData -> offsetData.get("GCBUSINESSTYPECODE") == null).collect(Collectors.toList());
        return this.summaryOtherSumTabDataSelectSubject(offsetDatas, queryParamsVO.getOtherShowColumns(), subjectVos, systemId, subjectCodeToParentCodesMap, true);
    }

    private List<List<Map<String, Object>>> queryUnOffsetByselectSubject(QueryParamsVO queryParamsVO, List<Map<String, Object>> subjectVos, String systemId, Map<String, List<String>> subjectCodeToParentCodesMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> unOffsetDatas = this.gcInputDataOffsetItemService.queryUnOffset(queryParamsVO, false);
        return this.summarySumTabDataSelectSubject(unOffsetDatas, queryParamsVO.getNotOffsetOtherColumns(), subjectVos, subjectCodeToParentCodesMap, false, queryParamsVO);
    }

    private List<Map<String, Object>> queryOtherUnOffsetBySelect(QueryParamsVO queryParamsVO, List<Map<String, Object>> subjectVos, String systemId, Map<String, List<String>> subjectCodeToParentCodesMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> unOffsetDatas = this.gcInputDataOffsetItemService.queryUnOffset(queryParamsVO, true);
        return this.summaryOtherSumTabDataSelectSubject(unOffsetDatas, queryParamsVO.getNotOffsetOtherColumns(), subjectVos, systemId, subjectCodeToParentCodesMap, false);
    }

    private List<List<Map<String, Object>>> queryOffsetBySecondAccount(QueryParamsVO queryParamsVO, Map<String, String> subjectCodeToSecSubjectCodeMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        ArrayList<List<Map<String, Object>>> finalOffsetDatas = new ArrayList<List<Map<String, Object>>>();
        List<Map<String, Object>> offsetDatas = this.queryOffsetForAllEntry(queryParamsVO);
        offsetDatas = offsetDatas.stream().filter(offsetData -> offsetData.get("GCBUSINESSTYPECODE") != null).collect(Collectors.toList());
        ArrayList<List<Map>> groupByGcBussTypeDatas = new ArrayList<List<Map>>(offsetDatas.stream().collect(Collectors.groupingBy(item -> item.get("GCBUSINESSTYPECODE"))).values());
        groupByGcBussTypeDatas.forEach(datas -> {
            datas.forEach(offsetData -> offsetData.put("SECONDSUB", subjectCodeToSecSubjectCodeMap.get((String)offsetData.get("SUBJECTCODE"))));
            finalOffsetDatas.add(this.groupSummaryData((List<Map<String, Object>>)datas, true, queryParamsVO.getOtherShowColumns(), queryParamsVO.getNotOffsetOtherColumns()));
        });
        return finalOffsetDatas;
    }

    private List<Map<String, Object>> queryOtherOffsetBySecondAccount(QueryParamsVO queryParamsVO, Map<String, String> subjectCodeToSecSubjectCodeMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> offsetDatas = this.queryOffsetForAllEntry(queryParamsVO);
        offsetDatas = offsetDatas.stream().filter(offsetData -> offsetData.get("GCBUSINESSTYPECODE") == null).collect(Collectors.toList());
        offsetDatas.forEach(offsetData -> offsetData.put("SECONDSUB", subjectCodeToSecSubjectCodeMap.get((String)offsetData.get("SUBJECTCODE"))));
        return this.groupSummaryData(offsetDatas, true, queryParamsVO.getOtherShowColumns(), queryParamsVO.getNotOffsetOtherColumns());
    }

    private List<List<Map<String, Object>>> queryUnOffsetBySecondAccount(QueryParamsVO queryParamsVO, Map<String, String> subjectCodeToSecSubjectCodeMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        ArrayList<List<Map<String, Object>>> finalUnoffsetDatas = new ArrayList<List<Map<String, Object>>>();
        Map filterCondition = queryParamsVO.getFilterCondition();
        if (filterCondition != null) {
            queryParamsVO.setGcBusinessTypeQueryRule(ConverterUtils.getAsString(filterCondition.get("gcBusinessTypeQueryRule")));
            if (filterCondition.get("gcbusinesstypecode") != null) {
                queryParamsVO.setGcBusinessTypeCodes((List)filterCondition.get("gcbusinesstypecode"));
            }
            filterCondition.remove("gcbusinesstypecode");
            filterCondition.remove("gcBusinessTypeQueryRule");
        }
        List<Map<String, Object>> unOffsetDatas = this.gcInputDataOffsetItemService.queryUnOffset(queryParamsVO, false);
        ArrayList<List<Map>> groupByGcBussTypeDatas = new ArrayList<List<Map>>(unOffsetDatas.stream().collect(Collectors.groupingBy(item -> item.get("GCBUSINESSTYPECODE"))).values());
        groupByGcBussTypeDatas.forEach(datas -> {
            datas.forEach(unOffsetData -> unOffsetData.put("SECONDSUB", subjectCodeToSecSubjectCodeMap.get((String)unOffsetData.get("SUBJECTCODE"))));
            finalUnoffsetDatas.add(this.groupSummaryData((List<Map<String, Object>>)datas, false, queryParamsVO.getOtherShowColumns(), queryParamsVO.getNotOffsetOtherColumns()));
        });
        return finalUnoffsetDatas;
    }

    private List<Map<String, Object>> queryOtherUnOffsetBySecondAccount(QueryParamsVO queryParamsVO, Map<String, String> subjectCodeToSecSubjectCodeMap) {
        this.handleUnitAndOppUnitParam(queryParamsVO);
        List<Map<String, Object>> unOffsetDatas = this.gcInputDataOffsetItemService.queryUnOffset(queryParamsVO, true);
        unOffsetDatas.forEach(unOffsetData -> unOffsetData.put("SECONDSUB", subjectCodeToSecSubjectCodeMap.get((String)unOffsetData.get("SUBJECTCODE"))));
        return this.groupSummaryData(unOffsetDatas, false, queryParamsVO.getOtherShowColumns(), queryParamsVO.getNotOffsetOtherColumns());
    }

    public List<Map<String, Object>> arrangeForSelectData(List<List<Map<String, Object>>> offsetDatasGroupByBizTypeCode, List<List<Map<String, Object>>> unOffsetDatasGroupByBizTypeCode, List<Map<String, Object>> subjectVos, List<ConsolidatedSubjectEO> allSubjects, List<String> commonConditions, QueryParamsVO queryParamsVO, boolean isBySubject) {
        ArrayList<String> codes = new ArrayList<String>();
        if (subjectVos != null) {
            for (Map<String, Object> sunjectVo : subjectVos) {
                String code = (String)sunjectVo.get("code");
                codes.add(code);
            }
        }
        HashMap<String, String> subjectCodeToTitleMap = new HashMap<String, String>();
        List<Map<String, Object>> finalDatas = this.mergeHasBizTypeCodeSumData(offsetDatasGroupByBizTypeCode, unOffsetDatasGroupByBizTypeCode, commonConditions, queryParamsVO, isBySubject, subjectCodeToTitleMap, allSubjects, codes);
        int index = 1;
        for (Map<String, Object> finalData : finalDatas) {
            finalData.put("index", index++);
        }
        return finalDatas;
    }

    public List<Map<String, Object>> arrangeForUnSelectData(List<List<Map<String, Object>>> offsetDatasGroupByBizTypeCode, List<List<Map<String, Object>>> unOffsetDatasGroupByBizTypeCode, List<ConsolidatedSubjectEO> allSubjects, List<String> commonConditions, QueryParamsVO queryParamsVO) {
        HashMap<String, String> subjectCodeToTitleMap = new HashMap<String, String>();
        return this.mergeHasBizTypeCodeSumData(offsetDatasGroupByBizTypeCode, unOffsetDatasGroupByBizTypeCode, commonConditions, queryParamsVO, false, subjectCodeToTitleMap, allSubjects, new ArrayList<String>());
    }

    public List<Map<String, Object>> arrangeForOtherData(List<Map<String, Object>> otherOffsetDatas, List<Map<String, Object>> otherUnOffsetDatas, List<Map<String, Object>> subjectVos, List<ConsolidatedSubjectEO> allSubjects, List<String> commonConditions, QueryParamsVO queryParamsVO) {
        ArrayList<String> codes = new ArrayList<String>();
        if (subjectVos != null) {
            for (Map<String, Object> sunjectVo : subjectVos) {
                String code = (String)sunjectVo.get("code");
                codes.add(code);
            }
        }
        ArrayList<Map<String, Object>> finalDatas = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mergedOtherDatas = this.mergeOtherData(otherOffsetDatas, otherUnOffsetDatas, commonConditions, queryParamsVO);
        HashMap subjectCodeToTitleMap = new HashMap();
        if (mergedOtherDatas.size() != 0) {
            BigDecimal offsetCreditValue = BigDecimal.ZERO;
            BigDecimal offsetDebitValue = BigDecimal.ZERO;
            BigDecimal offsetDiffValue = BigDecimal.ZERO;
            BigDecimal unOffsetCreditValue = BigDecimal.ZERO;
            BigDecimal unOffsetDebitValue = BigDecimal.ZERO;
            BigDecimal unOffsetDiffValue = BigDecimal.ZERO;
            for (Map<String, Object> data : mergedOtherDatas) {
                offsetCreditValue = offsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETCREDITVALUE")));
                offsetDebitValue = offsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDEBITVALUE")));
                offsetDiffValue = offsetDiffValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDIFFVALUE")));
                unOffsetCreditValue = unOffsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETCREDITVALUE")));
                unOffsetDebitValue = unOffsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETDEBITVALUE")));
                data.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((Double)((Double)data.get("OFFSETCREDITVALUE"))));
                data.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((Double)((Double)data.get("OFFSETDEBITVALUE"))));
                data.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((Double)((Double)data.get("OFFSETDIFFVALUE"))));
                data.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((Double)((Double)data.get("UNOFFSETCREDITVALUE"))));
                data.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((Double)((Double)data.get("UNOFFSETDEBITVALUE"))));
                this.setSubjectTitle(queryParamsVO.getSystemId(), data, subjectCodeToTitleMap, "SUBJECTTITLE", "SECONDSUB");
                data.put("subCode", data.get("SECONDSUB"));
            }
            HashMap<String, String> rootColumn = new HashMap<String, String>();
            unOffsetDiffValue = unOffsetDebitValue.subtract(unOffsetCreditValue);
            rootColumn.put("SECONDSUB", "\u5176\u4ed6");
            rootColumn.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((double)offsetCreditValue.doubleValue()));
            rootColumn.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((double)offsetDebitValue.doubleValue()));
            rootColumn.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((double)offsetDiffValue.doubleValue()));
            rootColumn.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((double)unOffsetCreditValue.doubleValue()));
            rootColumn.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((double)unOffsetDebitValue.doubleValue()));
            rootColumn.put("UNOFFSETDIFFVALUE", NumberUtils.doubleToString((double)unOffsetDiffValue.doubleValue()));
            finalDatas.add(rootColumn);
            if (subjectVos != null) {
                List<Map<String, Object>> finalMergedDatas = mergedOtherDatas;
                Collections.sort(mergedOtherDatas, (o1, o2) -> {
                    int io1 = codes.indexOf(o1.get("SECONDSUB"));
                    int io2 = codes.indexOf(o2.get("SECONDSUB"));
                    if (io1 != -1) {
                        io1 = finalMergedDatas.size() - io1;
                    }
                    if (io2 != -1) {
                        io2 = finalMergedDatas.size() - io2;
                    }
                    return io2 - io1;
                });
                finalDatas.addAll(finalMergedDatas);
            } else {
                List subJectCodes = allSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList());
                Collections.sort(mergedOtherDatas, (o1, o2) -> {
                    int io1 = subJectCodes.indexOf(o1.get("SECONDSUB"));
                    int io2 = subJectCodes.indexOf(o2.get("SECONDSUB"));
                    if (io1 != -1) {
                        io1 = mergedOtherDatas.size() - io1;
                    }
                    if (io2 != -1) {
                        io2 = mergedOtherDatas.size() - io2;
                    }
                    return io2 - io1;
                });
                finalDatas.addAll(mergedOtherDatas);
            }
        }
        return finalDatas;
    }

    private List<Map<String, Object>> groupSummaryData(List<Map<String, Object>> datas, boolean isOffset, List<String> offsetselectColumns, List<String> unOffsetselectColumns) {
        if (CollectionUtils.isEmpty(datas)) {
            return Collections.emptyList();
        }
        ArrayList<Map<String, Object>> groupSummaryDatas = new ArrayList<Map<String, Object>>();
        ArrayList<List<Map>> secondSubDatas = new ArrayList<List<Map>>(datas.stream().filter(item -> item.get("SECONDSUB") != null).collect(Collectors.groupingBy(item -> item.get("SECONDSUB"))).values());
        secondSubDatas.forEach(secondSubData -> {
            if (secondSubData.isEmpty()) {
                return;
            }
            ArrayList<List<Map>> groupByOtherColumnLists = new ArrayList<List<Map>>(secondSubData.stream().collect(Collectors.groupingBy(item -> {
                StringBuilder groupStr = new StringBuilder("");
                if (isOffset) {
                    offsetselectColumns.forEach(offsetkey -> {
                        if (item.get(offsetkey) != null && !(item.get(offsetkey) instanceof Double)) {
                            groupStr.append(item.get(offsetkey));
                        }
                    });
                } else {
                    unOffsetselectColumns.forEach(unOffsetkey -> {
                        if (item.get(unOffsetkey) != null) {
                            groupStr.append(item.get(unOffsetkey));
                        }
                    });
                }
                return groupStr.toString();
            })).values());
            groupByOtherColumnLists.forEach((Consumer<List<Map>>)((Consumer<List>)groupByOtherColumnData -> {
                HashMap data = new HashMap();
                data.put("SECONDSUB", ((Map)groupByOtherColumnData.get(0)).get("SECONDSUB"));
                data.put("GCBUSINESSTYPECODE", ((Map)groupByOtherColumnData.get(0)).get("GCBUSINESSTYPECODE"));
                BigDecimal creditValue = BigDecimal.ZERO;
                BigDecimal debitValue = BigDecimal.ZERO;
                BigDecimal diffValue = BigDecimal.ZERO;
                HashMap<String, BigDecimal> otherDoubleColumn2Value = new HashMap<String, BigDecimal>();
                if (isOffset) {
                    for (String offsetselectColumn : offsetselectColumns) {
                        if (!(((Map)groupByOtherColumnData.get(0)).get(offsetselectColumn) instanceof Double)) continue;
                        otherDoubleColumn2Value.put(offsetselectColumn, BigDecimal.ZERO);
                    }
                } else {
                    for (String unOffsetselectColumn : unOffsetselectColumns) {
                        if (!(((Map)groupByOtherColumnData.get(0)).get(unOffsetselectColumn) instanceof Double)) continue;
                        otherDoubleColumn2Value.put(unOffsetselectColumn, BigDecimal.ZERO);
                    }
                }
                for (Map item : groupByOtherColumnData) {
                    if (item.get("CREDITVALUE") != null) {
                        BigDecimal creValue = ConverterUtils.getAsBigDecimal(item.get("CREDITVALUE"));
                        creditValue = creditValue.add(creValue);
                    }
                    if (item.get("DEBITVALUE") != null) {
                        BigDecimal debValue = ConverterUtils.getAsBigDecimal(item.get("DEBITVALUE"));
                        debitValue = debitValue.add(debValue);
                    }
                    if (item.get("DIFF") != null) {
                        String diff = (String)item.get("DIFF");
                        BigDecimal diffVal = BigDecimal.valueOf(Double.parseDouble(diff.replace(",", "")));
                        diffValue = diffValue.add(diffVal);
                    }
                    for (String otherDoubleColumn : otherDoubleColumn2Value.keySet()) {
                        if (item.get(otherDoubleColumn) == null) continue;
                        BigDecimal otherDoubleColumnValue = ConverterUtils.getAsBigDecimal(item.get(otherDoubleColumn));
                        otherDoubleColumn2Value.put(otherDoubleColumn, ((BigDecimal)otherDoubleColumn2Value.get(otherDoubleColumn)).add(otherDoubleColumnValue));
                    }
                }
                if (isOffset) {
                    data.put("DIFFVALUE", diffValue.doubleValue());
                    for (String otherDoubleColumn : otherDoubleColumn2Value.keySet()) {
                        data.put(otherDoubleColumn, NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble(otherDoubleColumn2Value.get(otherDoubleColumn))));
                    }
                    offsetselectColumns.forEach(otherShowColumn -> {
                        if (!otherDoubleColumn2Value.containsKey(otherShowColumn)) {
                            data.put((String)otherShowColumn, ((Map)groupByOtherColumnData.get(0)).get(otherShowColumn));
                        }
                    });
                } else {
                    for (String otherDoubleColumn : otherDoubleColumn2Value.keySet()) {
                        data.put(otherDoubleColumn, otherDoubleColumn2Value.get(otherDoubleColumn));
                    }
                    unOffsetselectColumns.forEach(otherShowColumn -> {
                        if (!otherDoubleColumn2Value.containsKey(otherShowColumn)) {
                            data.put((String)otherShowColumn, ((Map)groupByOtherColumnData.get(0)).get(otherShowColumn));
                        }
                    });
                }
                data.put("CREDITVALUE", creditValue.doubleValue());
                data.put("DEBITVALUE", debitValue.doubleValue());
                groupSummaryDatas.add(data);
            }));
        });
        return groupSummaryDatas;
    }

    private List<Map<String, Object>> mergeHasBizTypeCodeSumData(List<List<Map<String, Object>>> offsetDatasGroupByBizTypeCode, List<List<Map<String, Object>>> unOffsetDatasGroupByBizTypeCode, List<String> commonConditions, QueryParamsVO queryParams, boolean isBySubject, Map<String, String> subjectCodeToTitleMap, List<ConsolidatedSubjectEO> allSubjects, List<String> codes) {
        ArrayList<Map<String, Object>> finalDatas = new ArrayList<Map<String, Object>>();
        ArrayList<String> hasMergeGcBussTypeCodes = new ArrayList<String>();
        for (List<Map<String, Object>> offsetDatas : offsetDatasGroupByBizTypeCode) {
            if (offsetDatas.isEmpty()) continue;
            for (List<Map<String, Object>> unOffsetDatas : unOffsetDatasGroupByBizTypeCode) {
                if (unOffsetDatas.isEmpty() || !offsetDatas.get(0).get("GCBUSINESSTYPECODE").equals(unOffsetDatas.get(0).get("GCBUSINESSTYPECODE"))) continue;
                hasMergeGcBussTypeCodes.add((String)offsetDatas.get(0).get("GCBUSINESSTYPECODE"));
                List<Map<String, Object>> mergedEqualsGcBizTypeCodeDatas = this.mergeEqualsGcBizTypeCodeDatas(offsetDatas, unOffsetDatas, commonConditions, queryParams);
                if (isBySubject) {
                    this.getFinalDataListBySubject(mergedEqualsGcBizTypeCodeDatas, finalDatas, subjectCodeToTitleMap, allSubjects, codes, queryParams);
                    continue;
                }
                this.getFinalDataDataList(mergedEqualsGcBizTypeCodeDatas, finalDatas, subjectCodeToTitleMap, allSubjects, queryParams);
            }
        }
        for (List<Map<String, Object>> offsetDatas : offsetDatasGroupByBizTypeCode) {
            if (offsetDatas.isEmpty() || hasMergeGcBussTypeCodes.contains((String)offsetDatas.get(0).get("GCBUSINESSTYPECODE"))) continue;
            List<Map<String, Object>> remainOfDatas = this.mergeEqualsGcBizTypeCodeDatas(offsetDatas, new ArrayList<Map<String, Object>>(), commonConditions, queryParams);
            if (isBySubject) {
                this.getFinalDataListBySubject(remainOfDatas, finalDatas, subjectCodeToTitleMap, allSubjects, codes, queryParams);
                continue;
            }
            this.getFinalDataDataList(remainOfDatas, finalDatas, subjectCodeToTitleMap, allSubjects, queryParams);
        }
        for (List<Map<String, Object>> unOffsetDatas : unOffsetDatasGroupByBizTypeCode) {
            if (unOffsetDatas.isEmpty() || hasMergeGcBussTypeCodes.contains((String)unOffsetDatas.get(0).get("GCBUSINESSTYPECODE"))) continue;
            List<Map<String, Object>> remainUnOfDatas = this.mergeEqualsGcBizTypeCodeDatas(new ArrayList<Map<String, Object>>(), unOffsetDatas, commonConditions, queryParams);
            if (isBySubject) {
                this.getFinalDataListBySubject(remainUnOfDatas, finalDatas, subjectCodeToTitleMap, allSubjects, codes, queryParams);
                continue;
            }
            this.getFinalDataDataList(remainUnOfDatas, finalDatas, subjectCodeToTitleMap, allSubjects, queryParams);
        }
        return finalDatas;
    }

    private void getFinalDataDataList(List<Map<String, Object>> bussTypeDatas, List<Map<String, Object>> finalDatas, Map<String, String> subjectCodeToTitleMap, List<ConsolidatedSubjectEO> allSubjects, QueryParamsVO queryParamsVO) {
        if (bussTypeDatas.isEmpty()) {
            return;
        }
        HashMap<String, Object> recodeMap = new HashMap<String, Object>();
        recodeMap.put("GCBUSINESSTYPECODE", (String)bussTypeDatas.get(0).get("GCBUSINESSTYPECODE"));
        HashMap<String, String> businessTypeCode2TitleCache = new HashMap<String, String>();
        this.setBusinessTypeCodeTitle(recodeMap, businessTypeCode2TitleCache);
        BigDecimal offsetCreditValue = BigDecimal.ZERO;
        BigDecimal offsetDebitValue = BigDecimal.ZERO;
        BigDecimal offsetDiffValue = BigDecimal.ZERO;
        BigDecimal unOffsetCreditValue = BigDecimal.ZERO;
        BigDecimal unOffsetDebitValue = BigDecimal.ZERO;
        BigDecimal unOffsetDiffValue = BigDecimal.ZERO;
        for (Map<String, Object> data : bussTypeDatas) {
            offsetCreditValue = offsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETCREDITVALUE")));
            offsetDebitValue = offsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDEBITVALUE")));
            offsetDiffValue = offsetDiffValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDIFFVALUE")));
            unOffsetCreditValue = unOffsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETCREDITVALUE")));
            unOffsetDebitValue = unOffsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETDEBITVALUE")));
            data.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETCREDITVALUE"))));
            data.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETDEBITVALUE"))));
            data.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETDIFFVALUE"))));
            data.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("UNOFFSETCREDITVALUE"))));
            data.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("UNOFFSETDEBITVALUE"))));
            this.setSubjectTitle(queryParamsVO.getSystemId(), data, subjectCodeToTitleMap, "SUBJECTTITLE", "SECONDSUB");
            data.put("subCode", data.get("SECONDSUB"));
            data.put("GCBUSINESSTYPETITLE", recodeMap.get("GCBUSINESSTYPE"));
        }
        HashMap rootColumn = new HashMap();
        unOffsetDiffValue = unOffsetDebitValue.subtract(unOffsetCreditValue);
        rootColumn.put("SECONDSUB", recodeMap.get("GCBUSINESSTYPE"));
        rootColumn.put("GCBUSINESSTYPECODE", recodeMap.get("GCBUSINESSTYPECODE"));
        rootColumn.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((double)offsetCreditValue.doubleValue()));
        rootColumn.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((double)offsetDebitValue.doubleValue()));
        rootColumn.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((double)offsetDiffValue.doubleValue()));
        rootColumn.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((double)unOffsetCreditValue.doubleValue()));
        rootColumn.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((double)unOffsetDebitValue.doubleValue()));
        rootColumn.put("UNOFFSETDIFFVALUE", NumberUtils.doubleToString((double)unOffsetDiffValue.doubleValue()));
        finalDatas.add(rootColumn);
        List subJectCodes = allSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList());
        Collections.sort(bussTypeDatas, (o1, o2) -> {
            int io1 = subJectCodes.indexOf(o1.get("SECONDSUB"));
            int io2 = subJectCodes.indexOf(o2.get("SECONDSUB"));
            if (io1 != -1) {
                io1 = bussTypeDatas.size() - io1;
            }
            if (io2 != -1) {
                io2 = bussTypeDatas.size() - io2;
            }
            return io2 - io1;
        });
        finalDatas.addAll(bussTypeDatas);
    }

    private void getFinalDataListBySubject(List<Map<String, Object>> bussTypeDatas, List<Map<String, Object>> finalDatas, Map<String, String> subjectCodeToTitleMap, List<ConsolidatedSubjectEO> allSubjects, List<String> codes, QueryParamsVO queryParamsVO) {
        if (bussTypeDatas.isEmpty()) {
            return;
        }
        HashMap<String, Object> recodeMap = new HashMap<String, Object>();
        recodeMap.put("GCBUSINESSTYPECODE", (String)bussTypeDatas.get(0).get("GCBUSINESSTYPECODE"));
        HashMap<String, String> businessTypeCode2TitleCache = new HashMap<String, String>();
        this.setBusinessTypeCodeTitle(recodeMap, businessTypeCode2TitleCache);
        BigDecimal offsetCreditValue = BigDecimal.ZERO;
        BigDecimal offsetDebitValue = BigDecimal.ZERO;
        BigDecimal offsetDiffValue = BigDecimal.ZERO;
        BigDecimal unOffsetCreditValue = BigDecimal.ZERO;
        BigDecimal unOffsetDebitValue = BigDecimal.ZERO;
        BigDecimal unOffsetDiffValue = BigDecimal.ZERO;
        for (Map<String, Object> data : bussTypeDatas) {
            List<String> parentIds = this.getALlParentId((String)data.get("SECONDSUB"), allSubjects);
            boolean isRepeat = false;
            for (String parentId : parentIds) {
                for (Map<String, Object> bussTypeData : bussTypeDatas) {
                    if (!parentId.equals(bussTypeData.get("SECONDSUB"))) continue;
                    isRepeat = true;
                }
            }
            if (!isRepeat) {
                offsetCreditValue = offsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETCREDITVALUE")));
                offsetDebitValue = offsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDEBITVALUE")));
                offsetDiffValue = offsetDiffValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("OFFSETDIFFVALUE")));
                unOffsetCreditValue = unOffsetCreditValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETCREDITVALUE")));
                unOffsetDebitValue = unOffsetDebitValue.add(ConverterUtils.getAsBigDecimal((Object)data.get("UNOFFSETDEBITVALUE")));
            }
            data.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETCREDITVALUE"))));
            data.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETDEBITVALUE"))));
            data.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("OFFSETDIFFVALUE"))));
            data.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("UNOFFSETCREDITVALUE"))));
            data.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)data.get("UNOFFSETDEBITVALUE"))));
            this.setSubjectTitle(queryParamsVO.getSystemId(), data, subjectCodeToTitleMap, "SUBJECTTITLE", "SECONDSUB");
            data.put("subCode", data.get("SECONDSUB"));
            data.put("GCBUSINESSTYPETITLE", recodeMap.get("GCBUSINESSTYPE"));
        }
        HashMap rootColumn = new HashMap();
        unOffsetDiffValue = unOffsetDebitValue.subtract(unOffsetCreditValue);
        rootColumn.put("SECONDSUB", recodeMap.get("GCBUSINESSTYPE"));
        rootColumn.put("GCBUSINESSTYPECODE", recodeMap.get("GCBUSINESSTYPECODE"));
        rootColumn.put("OFFSETCREDITVALUE", NumberUtils.doubleToString((double)offsetCreditValue.doubleValue()));
        rootColumn.put("OFFSETDEBITVALUE", NumberUtils.doubleToString((double)offsetDebitValue.doubleValue()));
        rootColumn.put("OFFSETDIFFVALUE", NumberUtils.doubleToString((double)offsetDiffValue.doubleValue()));
        rootColumn.put("UNOFFSETCREDITVALUE", NumberUtils.doubleToString((double)unOffsetCreditValue.doubleValue()));
        rootColumn.put("UNOFFSETDEBITVALUE", NumberUtils.doubleToString((double)unOffsetDebitValue.doubleValue()));
        rootColumn.put("UNOFFSETDIFFVALUE", NumberUtils.doubleToString((double)unOffsetDiffValue.doubleValue()));
        finalDatas.add(rootColumn);
        Collections.sort(bussTypeDatas, (o1, o2) -> {
            int io1 = codes.indexOf(o1.get("SECONDSUB"));
            int io2 = codes.indexOf(o2.get("SECONDSUB"));
            if (io1 != -1) {
                io1 = bussTypeDatas.size() - io1;
            }
            if (io2 != -1) {
                io2 = bussTypeDatas.size() - io2;
            }
            return io2 - io1;
        });
        finalDatas.addAll(bussTypeDatas);
    }

    private List<Map<String, Object>> mergeOtherData(List<Map<String, Object>> offsetDatas, List<Map<String, Object>> unOffsetDatas, List<String> commonConditions, QueryParamsVO queryParamsVO) {
        ArrayList<Map<String, Object>> allDatas = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : offsetDatas) {
            for (Map<String, Object> unOffsetData : unOffsetDatas) {
                boolean equals = true;
                for (String string : commonConditions) {
                    map.putIfAbsent(string, "");
                    unOffsetData.putIfAbsent(string, "");
                    equals = equals && map.get(string).equals(unOffsetData.get(string));
                }
                if (!map.get("SECONDSUB").equals(unOffsetData.get("SECONDSUB")) || !equals) continue;
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("GCBUSINESSTYPECODE", map.get("GCBUSINESSTYPECODE"));
                hashMap.put("SECONDSUB", map.get("SECONDSUB"));
                hashMap.put("OFFSETCREDITVALUE", map.get("CREDITVALUE"));
                hashMap.put("OFFSETDEBITVALUE", map.get("DEBITVALUE"));
                hashMap.put("OFFSETDIFFVALUE", map.get("DIFFVALUE"));
                hashMap.put("UNOFFSETCREDITVALUE", unOffsetData.get("CREDITVALUE"));
                hashMap.put("UNOFFSETDEBITVALUE", unOffsetData.get("DEBITVALUE"));
                queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> hashMap.put((String)otherOffsetShowColumn, map.get(otherOffsetShowColumn)));
                queryParamsVO.getNotOffsetOtherColumns().forEach(otherUnOffsetShowColumn -> {
                    boolean isRepeat = false;
                    for (String commonCondition : commonConditions) {
                        if (!otherUnOffsetShowColumn.equals(commonCondition)) continue;
                        isRepeat = true;
                    }
                    if (!isRepeat) {
                        allData2.put((String)otherUnOffsetShowColumn, unOffsetData.get(otherUnOffsetShowColumn));
                    }
                });
                allDatas.add(hashMap);
            }
        }
        ArrayList remainOffsetDatas = new ArrayList();
        for (Map<String, Object> map : offsetDatas) {
            boolean isRepeat = false;
            for (Map map2 : allDatas) {
                boolean bl = true;
                for (String commonCondition : commonConditions) {
                    map.putIfAbsent(commonCondition, "");
                    bl = bl && map.get(commonCondition).equals(map2.get(commonCondition));
                }
                if (!map.get("SECONDSUB").equals(map2.get("SECONDSUB")) || !bl) continue;
                isRepeat = true;
            }
            if (isRepeat) continue;
            HashMap<String, Object> allData3 = new HashMap<String, Object>();
            allData3.put("GCBUSINESSTYPECODE", map.get("GCBUSINESSTYPECODE"));
            allData3.put("SECONDSUB", map.get("SECONDSUB"));
            allData3.put("OFFSETCREDITVALUE", map.get("CREDITVALUE"));
            allData3.put("OFFSETDEBITVALUE", map.get("DEBITVALUE"));
            allData3.put("OFFSETDIFFVALUE", map.get("DIFFVALUE"));
            allData3.put("UNOFFSETCREDITVALUE", 0.0);
            allData3.put("UNOFFSETDEBITVALUE", 0.0);
            queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> allData3.put((String)otherOffsetShowColumn, offsetData.get(otherOffsetShowColumn)));
            queryParamsVO.getNotOffsetOtherColumns().forEach(otherUnOffsetShowColumn -> {
                boolean isColumnRepeat = false;
                for (String commonCondition : commonConditions) {
                    if (!otherUnOffsetShowColumn.equals(commonCondition)) continue;
                    isColumnRepeat = true;
                    break;
                }
                if (!isColumnRepeat) {
                    allData3.put((String)otherUnOffsetShowColumn, "");
                }
            });
            remainOffsetDatas.add(allData3);
        }
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> unoffsetData : unOffsetDatas) {
            boolean isRepeat = false;
            for (Map map : allDatas) {
                boolean equals = true;
                for (String commonCondition : commonConditions) {
                    unoffsetData.putIfAbsent(commonCondition, "");
                    equals = equals && unoffsetData.get(commonCondition).equals(map.get(commonCondition));
                }
                if (!unoffsetData.get("SECONDSUB").equals(map.get("SECONDSUB"))) continue;
                isRepeat = true;
            }
            if (isRepeat) continue;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("GCBUSINESSTYPECODE", unoffsetData.get("GCBUSINESSTYPECODE"));
            hashMap.put("SECONDSUB", unoffsetData.get("SECONDSUB"));
            hashMap.put("OFFSETCREDITVALUE", 0.0);
            hashMap.put("OFFSETDEBITVALUE", 0.0);
            hashMap.put("OFFSETDIFFVALUE", 0.0);
            hashMap.put("UNOFFSETCREDITVALUE", unoffsetData.get("CREDITVALUE"));
            hashMap.put("UNOFFSETDEBITVALUE", unoffsetData.get("DEBITVALUE"));
            queryParamsVO.getNotOffsetOtherColumns().forEach(otherNotOffsetShowColumns -> allData2.put((String)otherNotOffsetShowColumns, unoffsetData.get(otherNotOffsetShowColumns)));
            queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> {
                boolean isColumnRepeat = false;
                for (String commonCondition : commonConditions) {
                    if (!otherOffsetShowColumn.equals(commonCondition)) continue;
                    isColumnRepeat = true;
                    break;
                }
                if (!isColumnRepeat) {
                    allData2.put((String)otherOffsetShowColumn, null);
                }
            });
            arrayList.add(hashMap);
        }
        allDatas.addAll(remainOffsetDatas);
        allDatas.addAll(arrayList);
        return allDatas;
    }

    private List<List<Map<String, Object>>> mergeDataByBussTypeCode(List<List<Map<String, Object>>> offsetDatas, List<List<Map<String, Object>>> unOffsetDatas) {
        return this.joinData(offsetDatas, unOffsetDatas);
    }

    protected void setBusinessTypeCodeTitle(Map<String, Object> record, Map<String, String> businessTypeCode2TitleCache) {
        String businessTypeCode = (String)record.get("GCBUSINESSTYPECODE");
        if (null != businessTypeCode) {
            List baseDatas;
            if (CollectionUtils.isEmpty(businessTypeCode2TitleCache) && !CollectionUtils.isEmpty(baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_GCBUSINESSTYPE"))) {
                baseDatas.forEach(baseData -> businessTypeCode2TitleCache.put(baseData.getCode(), baseData.getTitle()));
            }
            record.put("GCBUSINESSTYPE", businessTypeCode2TitleCache.get(businessTypeCode));
        }
    }

    private List<String> getALlParentId(String subjectCodes, List<ConsolidatedSubjectEO> allSubjects) {
        ArrayList<String> parentCodes = new ArrayList<String>();
        Map<String, String> subjectCodeToParentCodeMap = allSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getParentCode, (o1, o2) -> o1));
        String parentCode = subjectCodeToParentCodeMap.get(subjectCodes);
        while (!"-".equals(parentCode)) {
            parentCodes.add(parentCode);
            parentCode = subjectCodeToParentCodeMap.get(parentCode);
        }
        return parentCodes;
    }

    public Map<String, List<String>> getSubjectCodeToChildCodesMap(List<ConsolidatedSubjectEO> allSubjects) {
        Map<String, String> subjectCodeToParentCodeMap = allSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getParentCode, (o1, o2) -> o1));
        HashMap<String, List<String>> subjectCodeToParentCodesMap = new HashMap<String, List<String>>();
        for (String subjectCode : subjectCodeToParentCodeMap.keySet()) {
            ArrayList<String> childCodes = new ArrayList<String>();
            this.handleSubjectCodeToChildCodeMap(subjectCode, subjectCodeToParentCodeMap, childCodes);
            subjectCodeToParentCodesMap.put(subjectCode, childCodes);
        }
        return subjectCodeToParentCodesMap;
    }

    private void handleSubjectCodeToChildCodeMap(String subjectCode, Map<String, String> subjectCodeToParentCodeMap, List<String> childCodes) {
        for (String key : subjectCodeToParentCodeMap.keySet()) {
            if (!subjectCode.equals(subjectCodeToParentCodeMap.get(key))) continue;
            childCodes.add(key);
            this.handleSubjectCodeToChildCodeMap(key, subjectCodeToParentCodeMap, childCodes);
        }
    }

    private List<Map<String, Object>> mergeEqualsGcBizTypeCodeDatas(List<Map<String, Object>> offsetDatas, List<Map<String, Object>> unOffsetDatas, List<String> commonConditions, QueryParamsVO queryParamsVO) {
        StringBuilder equalsCondition;
        ArrayList<Map<String, Object>> sumEqualsGcBizTypeCodeDatas = new ArrayList<Map<String, Object>>();
        ArrayList<String> equalsConditions = new ArrayList<String>();
        for (Map<String, Object> offsetData : offsetDatas) {
            for (Map<String, Object> map : unOffsetDatas) {
                StringBuilder equalsCondition2 = new StringBuilder();
                equalsCondition2.append(offsetData.get("SECONDSUB"));
                boolean isConditionEquals = offsetData.get("SECONDSUB").equals(map.get("SECONDSUB"));
                for (String commonCondition : commonConditions) {
                    offsetData.putIfAbsent(commonCondition, "");
                    map.putIfAbsent(commonCondition, "");
                    equalsCondition2.append(offsetData.get(commonCondition));
                    isConditionEquals = isConditionEquals && offsetData.get(commonCondition).equals(map.get(commonCondition));
                }
                if (!isConditionEquals) continue;
                equalsConditions.add(equalsCondition2.toString());
                HashMap<String, Object> conditionEqualsData = new HashMap<String, Object>();
                conditionEqualsData.put("GCBUSINESSTYPECODE", offsetData.get("GCBUSINESSTYPECODE"));
                conditionEqualsData.put("SECONDSUB", offsetData.get("SECONDSUB"));
                conditionEqualsData.put("OFFSETCREDITVALUE", offsetData.get("CREDITVALUE"));
                conditionEqualsData.put("OFFSETDEBITVALUE", offsetData.get("DEBITVALUE"));
                conditionEqualsData.put("OFFSETDIFFVALUE", offsetData.get("DIFFVALUE"));
                conditionEqualsData.put("UNOFFSETCREDITVALUE", map.get("CREDITVALUE"));
                conditionEqualsData.put("UNOFFSETDEBITVALUE", map.get("DEBITVALUE"));
                queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> conditionEqualsData.put((String)otherOffsetShowColumn, offsetData.get(otherOffsetShowColumn)));
                queryParamsVO.getNotOffsetOtherColumns().forEach(otherUnOffsetShowColumn -> {
                    boolean isRepeat = false;
                    for (String commonCondition : commonConditions) {
                        if (!otherUnOffsetShowColumn.equals(commonCondition)) continue;
                        isRepeat = true;
                    }
                    if (!isRepeat) {
                        conditionEqualsData.put((String)otherUnOffsetShowColumn, map.get(otherUnOffsetShowColumn));
                    }
                });
                sumEqualsGcBizTypeCodeDatas.add(conditionEqualsData);
            }
        }
        for (Map<String, Object> offsetData : offsetDatas) {
            equalsCondition = new StringBuilder();
            equalsCondition.append(offsetData.get("SECONDSUB"));
            for (String commonCondition : commonConditions) {
                offsetData.putIfAbsent(commonCondition, "");
                equalsCondition.append(offsetData.get(commonCondition));
            }
            if (equalsConditions.contains(equalsCondition.toString())) continue;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("GCBUSINESSTYPECODE", offsetData.get("GCBUSINESSTYPECODE"));
            hashMap.put("SECONDSUB", offsetData.get("SECONDSUB"));
            hashMap.put("OFFSETCREDITVALUE", offsetData.get("CREDITVALUE"));
            hashMap.put("OFFSETDEBITVALUE", offsetData.get("DEBITVALUE"));
            hashMap.put("OFFSETDIFFVALUE", offsetData.get("DIFFVALUE"));
            hashMap.put("UNOFFSETCREDITVALUE", "0.00");
            hashMap.put("UNOFFSETDEBITVALUE", "0.00");
            queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> offsetUnEqualsData.put(otherOffsetShowColumn, offsetData.get(otherOffsetShowColumn)));
            queryParamsVO.getNotOffsetOtherColumns().forEach(otherUnOffsetShowColumn -> {
                boolean isColumnRepeat = false;
                for (String commonCondition : commonConditions) {
                    if (!otherUnOffsetShowColumn.equals(commonCondition)) continue;
                    isColumnRepeat = true;
                    break;
                }
                if (!isColumnRepeat) {
                    offsetUnEqualsData.put(otherUnOffsetShowColumn, "");
                }
            });
            sumEqualsGcBizTypeCodeDatas.add(hashMap);
        }
        for (Map<String, Object> unoffsetData : unOffsetDatas) {
            equalsCondition = new StringBuilder();
            equalsCondition.append(unoffsetData.get("SECONDSUB"));
            for (String commonCondition : commonConditions) {
                unoffsetData.putIfAbsent(commonCondition, "");
                equalsCondition.append(unoffsetData.get(commonCondition));
            }
            if (equalsConditions.contains(equalsCondition.toString())) continue;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("GCBUSINESSTYPECODE", unoffsetData.get("GCBUSINESSTYPECODE"));
            hashMap.put("SECONDSUB", unoffsetData.get("SECONDSUB"));
            hashMap.put("OFFSETCREDITVALUE", "0.00");
            hashMap.put("OFFSETDEBITVALUE", "0.00");
            hashMap.put("OFFSETDIFFVALUE", "0.00");
            hashMap.put("UNOFFSETCREDITVALUE", unoffsetData.get("CREDITVALUE"));
            hashMap.put("UNOFFSETDEBITVALUE", unoffsetData.get("DEBITVALUE"));
            queryParamsVO.getNotOffsetOtherColumns().forEach(otherNotOffsetShowColumns -> unOffsetUnEqualsData.put((String)otherNotOffsetShowColumns, unoffsetData.get(otherNotOffsetShowColumns)));
            queryParamsVO.getOtherShowColumns().forEach(otherOffsetShowColumn -> {
                boolean isColumnRepeat = false;
                for (String commonCondition : commonConditions) {
                    if (!otherOffsetShowColumn.equals(commonCondition)) continue;
                    isColumnRepeat = true;
                    break;
                }
                if (!isColumnRepeat) {
                    unOffsetUnEqualsData.put((String)otherOffsetShowColumn, null);
                }
            });
            sumEqualsGcBizTypeCodeDatas.add(hashMap);
        }
        return sumEqualsGcBizTypeCodeDatas;
    }

    public void handleElmMode(QueryParamsVO queryParamsVO, List<List<Map<String, Object>>> offsetDatas, List<Map<String, Object>> otherOffsetDatas) {
        ArrayList subjectVos = new ArrayList();
        offsetDatas.forEach(offsetData -> offsetData.forEach(datas -> {
            HashMap subjectVoKey2ValueMap = new HashMap();
            subjectVoKey2ValueMap.put("code", datas.get("SECONDSUB"));
            subjectVos.add(subjectVoKey2ValueMap);
        }));
        otherOffsetDatas.forEach(otherOffsetData -> {
            HashMap subjectVoKey2ValueMap = new HashMap();
            subjectVoKey2ValueMap.put("code", otherOffsetData.get("SECONDSUB"));
            subjectVos.add(subjectVoKey2ValueMap);
        });
        queryParamsVO.getFilterCondition().put("subjectVo", subjectVos);
    }

    public void handleSelectColumnFields(QueryParamsVO queryParamsVO, List<String> commonConditions, List<String> allConditions, String systemId) {
        HashMap offsetFilterCondition = new HashMap();
        HashMap unOffsetFilterCondition = new HashMap();
        List offsetColumns = this.adjustingEntryService.listOffsetColumnSelects().stream().map(DesignFieldDefineVO::getKey).collect(Collectors.toList());
        List unOffsetColumns = this.gcInputDataOffsetItemService.listUnOffsetColumnSelects(systemId, queryParamsVO.getDataSourceCode()).stream().map(DesignFieldDefineVO::getKey).collect(Collectors.toList());
        queryParamsVO.setOtherShowColumns(new ArrayList());
        queryParamsVO.setNotOffsetOtherColumns(new ArrayList());
        if (!CollectionUtils.isEmpty(queryParamsVO.getSumTabOtherColumns())) {
            queryParamsVO.getSumTabOtherColumns().forEach(sumTabOtherColumns -> {
                allConditions.add((String)sumTabOtherColumns);
                boolean hasOffsetColumn = false;
                if (offsetColumns.contains(sumTabOtherColumns)) {
                    hasOffsetColumn = true;
                    queryParamsVO.getOtherShowColumns().add(sumTabOtherColumns);
                    if (queryParamsVO.getFilterCondition().get(sumTabOtherColumns) != null) {
                        offsetFilterCondition.put(sumTabOtherColumns, queryParamsVO.getFilterCondition().get(sumTabOtherColumns));
                    }
                    if (this.hasNumberEndOfMapColumns(queryParamsVO.getFilterCondition(), (String)sumTabOtherColumns)) {
                        offsetFilterCondition.put(sumTabOtherColumns + "_number", queryParamsVO.getFilterCondition().get(sumTabOtherColumns + "_number"));
                    }
                }
                if (unOffsetColumns.contains(sumTabOtherColumns)) {
                    if (queryParamsVO.getFilterCondition().get(sumTabOtherColumns) != null) {
                        unOffsetFilterCondition.put(sumTabOtherColumns, queryParamsVO.getFilterCondition().get(sumTabOtherColumns));
                    }
                    if (this.hasNumberEndOfMapColumns(queryParamsVO.getFilterCondition(), (String)sumTabOtherColumns)) {
                        unOffsetFilterCondition.put(sumTabOtherColumns, queryParamsVO.getFilterCondition().get(sumTabOtherColumns + "_number"));
                    }
                    queryParamsVO.getNotOffsetOtherColumns().add(sumTabOtherColumns);
                    if (hasOffsetColumn) {
                        commonConditions.add((String)sumTabOtherColumns);
                    }
                }
            });
        }
        for (Map.Entry filterCondition : queryParamsVO.getFilterCondition().entrySet()) {
            if ("subjectVo".equals(filterCondition.getKey()) || "gcbusinesstypecode".equals(filterCondition.getKey()) || "ruleVo".equals(filterCondition.getKey()) || "elmMode".equals(filterCondition.getKey())) {
                offsetFilterCondition.put(filterCondition.getKey(), filterCondition.getValue());
            }
            if (!"subjectVo".equals(filterCondition.getKey()) && !"ruleVo".equals(filterCondition.getKey())) continue;
            unOffsetFilterCondition.put(filterCondition.getKey(), filterCondition.getValue());
        }
        queryParamsVO.setFilterCondition(offsetFilterCondition);
        queryParamsVO.setNotOffsetFilterCondition(unOffsetFilterCondition);
    }

    private boolean hasNumberEndOfMapColumns(Map<String, Object> filterConditionMap, String sumTabOtherColumns) {
        AtomicBoolean hasNumberEndColumns = new AtomicBoolean(false);
        filterConditionMap.forEach((key, value) -> {
            String newColumns;
            if (key.endsWith("_number") && sumTabOtherColumns.equals(newColumns = key.replace("_number", ""))) {
                hasNumberEndColumns.set(true);
            }
        });
        return hasNumberEndColumns.get();
    }

    private List<Map<String, Object>> handlePageData(List<Map<String, Object>> allOffsetDatas, QueryParamsVO queryParamsVO) {
        if (queryParamsVO.getPageNum() == -1) {
            return allOffsetDatas;
        }
        return allOffsetDatas.stream().skip((long)(queryParamsVO.getPageNum() - 1) * (long)queryParamsVO.getPageSize()).limit(queryParamsVO.getPageSize()).collect(Collectors.toList());
    }

    private void handleUnitAndOppUnitParam(QueryParamsVO queryParamsVO) {
        List oppUnitIdList;
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, queryParamsVO.getPeriodStr()));
        List unitIdList = queryParamsVO.getUnitIdList();
        if (!CollectionUtils.isEmpty(unitIdList)) {
            queryParamsVO.setEnableTempTableFilterUnitOrOppUnit(Boolean.valueOf(true));
            queryParamsVO.setUnitIdList(this.getAllChildrenOrgByOrgList(unitIdList, tool));
        }
        if (!CollectionUtils.isEmpty(oppUnitIdList = queryParamsVO.getOppUnitIdList())) {
            queryParamsVO.setEnableTempTableFilterUnitOrOppUnit(Boolean.valueOf(true));
            queryParamsVO.setOppUnitIdList(this.getAllChildrenOrgByOrgList(oppUnitIdList, tool));
        }
    }

    private List<String> getAllChildrenOrgByOrgList(List<String> orgCodeList, GcOrgCenterService tool) {
        HashSet allOrgCodeSet = new HashSet();
        for (String code : orgCodeList) {
            if (allOrgCodeSet.contains(code)) continue;
            allOrgCodeSet.addAll(tool.listAllOrgByParentIdContainsSelf(code).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
        return new ArrayList<String>(allOrgCodeSet);
    }

    public List<Map<String, Object>> queryOffsetForAllEntry(QueryParamsVO queryParamsVO) {
        Map filterCondition = queryParamsVO.getFilterCondition();
        if (filterCondition != null) {
            queryParamsVO.setGcBusinessTypeQueryRule(ConverterUtils.getAsString(filterCondition.get("gcBusinessTypeQueryRule")));
            if (filterCondition.get("gcbusinesstypecode") != null) {
                queryParamsVO.setGcBusinessTypeCodes((List)filterCondition.get("gcbusinesstypecode"));
            }
            filterCondition.remove("gcbusinesstypecode");
            filterCondition.remove("gcBusinessTypeQueryRule");
        }
        List<Map<String, Object>> datas = this.sumOffsetValueGroupBySubjectAndBusinessTypeCode(queryParamsVO);
        datas.forEach(item -> this.offsetVchrQuery.convert(item));
        return datas;
    }

    private List<Map<String, Object>> sumOffsetValueGroupBySubjectAndBusinessTypeCode(QueryParamsVO queryParamsVO) {
        String queryFields = "record.subjectcode,record.gcBusinessTypeCode,sum(record.offset_Credit) as CREDITVALUE,sum(record.offset_DEBIT) as DEBITVALUE,sum(record.DIFFD) as DIFFD,sum(record.DIFFC) as DIFFC";
        String selectColumnFields = this.handleSelectColumnFields(queryParamsVO.getOtherShowColumns());
        String groupStr = " record.subjectcode,record.gcBusinessTypeCode" + selectColumnFields;
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        return this.adjustingEntryCoreService.sumOffsetValueGroupBy(queryDTO, queryFields + selectColumnFields, groupStr);
    }

    private String handleSelectColumnFields(List<String> offsetselectColumns) {
        StringBuilder selectFields = new StringBuilder("");
        offsetselectColumns.forEach(offsetVchrQuery -> selectFields.append(" ,record.").append((String)offsetVchrQuery));
        return selectFields.toString();
    }
}

