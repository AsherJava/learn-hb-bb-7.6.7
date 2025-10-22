/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.helper.FcCheckData2OffsetDataHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.RelatedItemOffsetRelAgent;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FinancialCheckOffsetServiceImpl
implements FinancialCheckOffsetService {
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private OffsetRelatedItemDao offsetRelatedItemDao;
    @Autowired
    private GcDbLockService gcDbLockService;
    @Autowired
    private GcRelatedItemDao gcRelatedItemDao;
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    private final Logger logger = LoggerFactory.getLogger(FinancialCheckOffsetServiceImpl.class);

    @Override
    public Pagination<Map<String, Object>> listFinancialCheckOffset(QueryParamsVO queryParamsVO, Boolean isQueryParent) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer sql = this.getQueryFinancialCheckOffsetSql(queryParamsVO, params, isQueryParent);
        if (sql == null || StringUtils.isEmpty((String)sql.toString())) {
            Pagination pagination = new Pagination();
            pagination.setContent(new ArrayList());
            pagination.setTotalElements(Integer.valueOf(0));
            return pagination;
        }
        Pagination<Map<String, Object>> pagination = this.listUnOffsetRecords(queryParamsVO, sql.toString(), params);
        return pagination;
    }

    public StringBuffer getQueryFinancialCheckOffsetSql(QueryParamsVO queryParamsVO, List<Object> params, Boolean isQueryParent) {
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        int codeLength = tool.getOrgCodeLength();
        StringBuffer whereSql = new StringBuffer(64);
        Date date = yp.formatYP().getEndDate();
        if (!StringUtils.isEmpty((String)queryParamsVO.getOrgId())) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(queryParamsVO.getOrgId());
            if (null == orgCacheVO || orgCacheVO.getParentStr() == null) {
                return null;
            }
            String parentGuids = orgCacheVO.getParentStr();
            String gcParentsStr = orgCacheVO.getGcParentStr();
            if (isQueryParent.booleanValue()) {
                if (gcParentsStr.length() < codeLength + 1) {
                    return null;
                }
                this.initParentMergeUnitCondition(whereSql, params, parentGuids, date, queryParamsVO.getOrgType());
            } else {
                this.initMergeUnitCondition(whereSql, params, parentGuids, gcParentsStr, date, codeLength);
            }
        } else {
            if (CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
                return null;
            }
            this.initValidtimeCondition(whereSql, params, date);
        }
        String orgTable = tool.getCurrOrgType().getTable();
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append(SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_OFFSETRELATEDITEM", (String)"record"));
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETRELATEDITEM").append("  record\n");
        sql.append("left join ").append("GC_RELATED_ITEM").append(" item on item.id = record.RELATEDITEMID\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.GCUNITID = bfUnitTable.code)\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.GCOPPUNITID = dfUnitTable.code)\n");
        if (queryParamsVO.getSumTabPenetrateCondition() != null) {
            if (queryParamsVO.getSumTabPenetrateCondition().get("gcsubjectCode") != null) {
                ArrayList<String> gcSubjectCodes = new ArrayList<String>();
                gcSubjectCodes.add((String)queryParamsVO.getSumTabPenetrateCondition().get("gcsubjectCode"));
                whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(gcSubjectCodes, (String)"record.gcsubjectCode "));
            }
            if (queryParamsVO.getSumTabPenetrateCondition().get("gcBusinessTypeCode") != null) {
                ArrayList<String> gcBusinessCodes = new ArrayList<String>();
                gcBusinessCodes.add((String)queryParamsVO.getSumTabPenetrateCondition().get("gcBusinessTypeCode"));
                whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(gcBusinessCodes, (String)"unionRule.businesstypecode"));
                sql.append("left join ").append("GC_UNIONRULE").append("  unionRule on record.unionRuleId = unionRule.id \n");
            }
        }
        if (queryParamsVO.getSumTabPenetrateCondition() != null && queryParamsVO.getSumTabPenetrateCondition().get("isExistGcBusinessType") != null && !((Boolean)queryParamsVO.getSumTabPenetrateCondition().get("isExistGcBusinessType")).booleanValue()) {
            whereSql.append(" and ( unionRule.businesstypecode is  null or record.unionRuleId is null ) ");
            sql.append("left join ").append("GC_UNIONRULE").append("  unionRule on record.unionRuleId = unionRule.id \n");
        }
        whereSql.append("and record.offsetGroupId is null \n ");
        whereSql.append("and record.systemId =? \n ");
        sql.append("where ");
        sql.append(whereSql);
        params.add(systemId);
        return sql;
    }

    public void initOtherCondition(StringBuffer whereSql, Map<String, Object> filterCondition, String schemeId, String periodStr) {
        if (!CollectionUtils.isEmpty(filterCondition)) {
            for (String key : filterCondition.keySet()) {
                Object subjectEOs;
                Object tempValue = filterCondition.get(key);
                if ("subjectId".equals(key)) {
                    List selectedSubjects = (List)tempValue;
                    ArrayList<String> subjectIds = new ArrayList<String>();
                    for (String uuid : selectedSubjects) {
                        ConsolidatedSubjectEO subjectEO = this.subjectService.getSubjectById(uuid);
                        subjectEOs = this.subjectService.listAllChildrenSubjects(subjectEO.getSystemId(), subjectEO.getCode());
                        subjectIds.add(subjectEO.getCode());
                        Iterator iterator = subjectEOs.iterator();
                        while (iterator.hasNext()) {
                            ConsolidatedSubjectEO consolidatedSubjectEO = (ConsolidatedSubjectEO)iterator.next();
                            subjectIds.add(consolidatedSubjectEO.getCode());
                        }
                    }
                    if (subjectIds.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectIds, (String)"record.SUBJECTCODE")).append("\n");
                    continue;
                }
                if ("subjectVo".equals(key)) {
                    String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
                    if (StringUtils.isEmpty((String)systemId)) continue;
                    HashSet subjectCodes = new HashSet();
                    List selectedSubjects = (List)tempValue;
                    List allSubjectEos = this.subjectService.listAllSubjectsBySystemId(systemId);
                    Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                    subjectEOs = selectedSubjects.iterator();
                    while (subjectEOs.hasNext()) {
                        Map selectedSubjectVo = (Map)subjectEOs.next();
                        String subjectCode = (String)selectedSubjectVo.get("code");
                        if (StringUtils.isEmpty((String)subjectCode) || subjectCodes.contains(subjectCode)) continue;
                        HashSet allChildrenSubjectCodes = new HashSet(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
                        subjectCodes.addAll(allChildrenSubjectCodes);
                        subjectCodes.add(subjectCode);
                    }
                    if (subjectCodes.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.GCSUBJECTCODE")).append("\n");
                    continue;
                }
                if ("ruleId".equals(key)) {
                    List selectedRules = (List)tempValue;
                    ArrayList<String> ruleIds = new ArrayList<String>();
                    for (String uuid : selectedRules) {
                        if (StringUtils.isEmpty((String)uuid)) continue;
                        String id = uuid;
                        ruleIds.add(id);
                        List ruleVOs = this.unionRuleService.selectUnionRuleChildrenByGroup(id);
                        for (UnionRuleVO ruleVO : ruleVOs) {
                            ruleIds.add(ruleVO.getId());
                        }
                    }
                    if (ruleIds.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append("\n");
                    continue;
                }
                if ("ruleVo".equals(key)) {
                    this.addRuleWhereSql(whereSql, (List)tempValue);
                    continue;
                }
                if (tempValue instanceof List) {
                    List valueList = (List)tempValue;
                    List values = valueList.stream().map(item -> ConverterUtils.getAsString((Object)item)).collect(Collectors.toList());
                    if (key.endsWith("_number")) {
                        String biggerValue;
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty(values)) continue;
                        String smallerValue = (String)values.get(0);
                        if (!StringUtils.isEmpty((String)smallerValue) && smallerValue.matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append(smallerValue).append("\n");
                        }
                        if (values.size() <= 1 || StringUtils.isEmpty((String)(biggerValue = (String)values.get(1))) || !biggerValue.matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append(biggerValue).append("\n");
                        continue;
                    }
                    if (values == null || values.size() <= 0) continue;
                    String fieldName = "record.";
                    if (key.equals("chkState")) {
                        fieldName = "item.";
                    }
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(values, (String)(fieldName + key))).append("\n");
                    continue;
                }
                String strValue = String.valueOf(tempValue).trim();
                if (StringUtils.isEmpty((String)strValue)) continue;
                if (!strValue.matches("[^'\\s]+")) {
                    whereSql.append(" and 1=2 ");
                }
                if ("memo".equals(key)) {
                    whereSql.append(" and record.").append(key).append(" like '%%").append(strValue).append("%%'\n");
                    continue;
                }
                whereSql.append(" and record.").append(key).append("='").append(strValue).append("'").append("\n");
            }
        }
    }

    public void addRuleWhereSql(StringBuffer whereSql, List tempValue) {
        List selectedRules = tempValue;
        if (CollectionUtils.isEmpty(selectedRules)) {
            return;
        }
        boolean hasEmpty = false;
        HashSet<String> ruleIds = new HashSet<String>();
        for (Map selectedRuleVo : selectedRules) {
            String ruleId = (String)selectedRuleVo.get("id");
            if (StringUtils.isEmpty((String)ruleId)) continue;
            if ("empty".equals(ruleId)) {
                hasEmpty = true;
                continue;
            }
            if (ruleIds.contains(ruleId)) continue;
            ruleIds.add(ruleId);
            List ruleVOs = this.unionRuleService.selectUnionRuleChildrenByGroup(ruleId);
            for (UnionRuleVO ruleVO : ruleVOs) {
                ruleIds.add(ruleVO.getId());
            }
        }
        if (hasEmpty && !ruleIds.isEmpty()) {
            whereSql.append(" and (( record.UNIONRULEID is null or record.UNIONRULEID='') or ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append(" ) \n");
            return;
        }
        if (hasEmpty) {
            whereSql.append(" and ( record.UNIONRULEID is null or record.UNIONRULEID='') \n");
            return;
        }
        if (!ruleIds.isEmpty()) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append(" \n");
            return;
        }
    }

    public void initPeriodCondition(QueryParamsVO queryParamsVO, List<Object> params, StringBuffer whereSql) {
        whereSql.append(" and record.datatime =? ");
        params.add(queryParamsVO.getPeriodStr());
    }

    public Pagination<Map<String, Object>> listUnOffsetRecords(QueryParamsVO queryParamsVO, String sql, List<Object> params) {
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        ArrayList datas = new ArrayList();
        page.setContent(datas);
        EntNativeSqlDefaultDao dao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETRELATEDITEM", GcOffsetRelatedItemEO.class);
        try {
            List rs;
            if (StringUtils.isEmpty((String)sql)) {
                return page;
            }
            if (pageNum == -1 || pageSize == -1) {
                rs = dao.selectMap(sql, params);
            } else {
                int firstResult = (pageNum - 1) * pageSize;
                int count = dao.count(sql, params);
                if (count < 1) {
                    return page;
                }
                page.setTotalElements(Integer.valueOf(count));
                rs = dao.selectMapByPaging(sql, firstResult, pageNum * pageSize, params);
            }
            if (rs != null && !rs.isEmpty()) {
                rs.forEach(row -> datas.add(this.getObject((Map<String, Object>)row)));
            }
            page.setContent(datas);
            page.setPageSize(Integer.valueOf(pageSize));
            page.setCurrentPage(Integer.valueOf(pageNum));
            if (pageNum == -1 || pageSize == -1) {
                page.setTotalElements(Integer.valueOf(datas.size()));
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u672a\u62b5\u9500\u6570\u636e\u5f02\u5e38\uff1a", e);
        }
        return page;
    }

    public Map<String, Object> getObject(Map<String, Object> rowData) {
        HashMap<String, Object> result = new HashMap<String, Object>(rowData);
        Double debitConversionValue = ConverterUtils.getAsDouble(result.get("DEBITCONVERSIONVALUE"));
        Double creditConversionValue = ConverterUtils.getAsDouble(result.get("CREDITCONVERSIONVALUE"));
        if (creditConversionValue != null) {
            result.put("DC", OrientEnum.C.getValue());
            result.put("CREDITVALUE", NumberUtils.doubleToString((Double)creditConversionValue));
            result.put("AMMOUNT", creditConversionValue);
        } else {
            result.put("DC", OrientEnum.D.getValue());
            result.put("DEBITVALUE", NumberUtils.doubleToString((Double)debitConversionValue));
            result.put("AMMOUNT", debitConversionValue);
        }
        for (Map.Entry entry : result.entrySet()) {
            if ("AMMOUNT".equals(entry.getKey()) || !(entry.getValue() instanceof Double)) continue;
            result.put((String)entry.getKey(), NumberUtils.doubleToString((Double)((Double)entry.getValue())));
        }
        return result;
    }

    @Override
    public List<GcOffsetRelatedItemEO> batchMatchRule(List<GcOffsetRelatedItemEO> items, String systemId) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        this.logger.info("\u5f00\u59cb\u6279\u91cf\u5339\u914d\u5173\u8054\u4ea4\u6613\u6570\u636e\u7684\u5408\u5e76\u89c4\u5219\u3002");
        Date nowDate = DateUtils.now();
        Map<String, String> matchResult = FcCheckData2OffsetDataHelper.newInstance("").matchUnionRuleBySystem(items, systemId);
        if (CollectionUtils.isEmpty(matchResult)) {
            return Collections.emptyList();
        }
        ArrayList<GcOffsetRelatedItemEO> ruleMatchedItems = new ArrayList<GcOffsetRelatedItemEO>();
        items.forEach(item -> {
            if (matchResult.containsKey(item.getRelatedItemId())) {
                item.setUnionRuleId((String)matchResult.get(item.getRelatedItemId()));
                ruleMatchedItems.add((GcOffsetRelatedItemEO)item);
            }
        });
        if (CollectionUtils.isEmpty(ruleMatchedItems)) {
            return ruleMatchedItems;
        }
        Set ids = ruleMatchedItems.stream().map(FinancialCheckOffsetUtils::getItemKey).collect(Collectors.toSet());
        String lockId = this.gcDbLockService.tryLock(ids, "\u4fdd\u5b58\u89c4\u5219\u5339\u914d\u7ed3\u679c", "check-center-offset");
        if (StringUtils.isEmpty((String)lockId)) {
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u5176\u5b83\u64cd\u4f5c\u4f7f\u7528\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            this.offsetRelatedItemDao.updateItemRuleInfo(ruleMatchedItems);
        }
        catch (Exception e) {
            this.logger.error("\u4fdd\u5b58\u89c4\u5219\u5339\u914d\u7ed3\u679c\u5931\u8d25\u3002", e);
            throw new BusinessRuntimeException("\u4fdd\u5b58\u89c4\u5219\u5339\u914d\u7ed3\u679c\u5931\u8d25\u3002", (Throwable)e);
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
        this.logger.info("\u6279\u91cf\u5339\u914d\u5173\u8054\u4ea4\u6613\u6570\u636e\u7684\u5408\u5e76\u89c4\u5219\u7ed3\u675f\uff0c\u8017\u65f6:" + DateUtils.diffOf((Date)nowDate, (Date)DateUtils.now(), (int)13) + "s");
        return ruleMatchedItems;
    }

    @Override
    public void syncCheckData2OffsetData(List<GcRelatedItemEO> items, String dataTime) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        this.logger.info("\u5f00\u59cb\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u6570\u636e\u8fdb\u62b5\u6d88\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u3002");
        Date nowDate = DateUtils.now();
        Set<String> itemIds = items.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        List<GcOffsetRelatedItemEO> offsetRelatedItemS = this.offsetRelatedItemDao.listByRelatedId(itemIds);
        List<GcRelatedItemEO> needSkipItems = this.checkSrcTimeStamp(items, offsetRelatedItemS);
        if (!CollectionUtils.isEmpty(needSkipItems)) {
            items.removeAll(needSkipItems);
        }
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        List<GcOffsetRelatedItemEO> offsetRelatedItems = FcCheckData2OffsetDataHelper.newInstance(dataTime).convert(items);
        Set OffsetRelatedItemIds = offsetRelatedItemS.stream().map(FinancialCheckOffsetUtils::getItemKey).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(OffsetRelatedItemIds)) {
            this.saveConvertResult(offsetRelatedItems, items, nowDate);
        } else {
            String lockId = this.gcDbLockService.tryLock(OffsetRelatedItemIds, "\u4fdd\u5b58\u62b5\u9500\u5173\u8054\u4ea4\u6613\u5206\u5f55", "check-center-offset");
            if (StringUtils.isEmpty((String)lockId)) {
                throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u5176\u5b83\u64cd\u4f5c\u4f7f\u7528\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            }
            try {
                this.saveConvertResult(offsetRelatedItems, items, nowDate);
            }
            catch (Exception e) {
                this.logger.error("\u4fdd\u5b58\u62b5\u9500\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u5931\u8d25\u3002", e);
                throw new BusinessRuntimeException("\u4fdd\u5b58\u62b5\u9500\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
            finally {
                this.gcDbLockService.unlock(lockId);
            }
        }
    }

    private void saveConvertResult(List<GcOffsetRelatedItemEO> offsetRelatedItems, List<GcRelatedItemEO> items, Date nowDate) {
        Set<String> needCheckItemIds = offsetRelatedItems.stream().map(GcOffsetRelatedItemEO::getRelatedItemId).collect(Collectors.toSet());
        List<GcRelatedItemEO> needCheckItems = items.stream().filter(item -> needCheckItemIds.contains(item.getId())).collect(Collectors.toList());
        List<GcOffsetRelatedItemEO> lastOffsetRelatedItemS = this.offsetRelatedItemDao.listByRelatedId(needCheckItemIds);
        ArrayList<GcOffsetRelatedItemEO> needUpdateItems = new ArrayList<GcOffsetRelatedItemEO>();
        ArrayList<GcOffsetRelatedItemEO> needAddItems = new ArrayList<GcOffsetRelatedItemEO>();
        if (!CollectionUtils.isEmpty(lastOffsetRelatedItemS)) {
            List<GcRelatedItemEO> lastNeedSkipItems = this.checkSrcTimeStamp(needCheckItems, lastOffsetRelatedItemS);
            if (!CollectionUtils.isEmpty(lastNeedSkipItems)) {
                Set lastNeedSkipItemIds = lastNeedSkipItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
                offsetRelatedItems = offsetRelatedItems.stream().filter(item -> !lastNeedSkipItemIds.contains(item.getRelatedItemId())).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(offsetRelatedItems)) {
                Map<String, GcOffsetRelatedItemEO> group = lastOffsetRelatedItemS.stream().collect(Collectors.groupingBy(item -> item.getSystemId() + item.getRelatedItemId(), Collectors.collectingAndThen(Collectors.toList(), list -> (GcOffsetRelatedItemEO)list.get(0))));
                offsetRelatedItems.forEach(item -> {
                    String key = item.getSystemId() + item.getRelatedItemId();
                    if (group.keySet().contains(key)) {
                        GcOffsetRelatedItemEO oldItem = (GcOffsetRelatedItemEO)group.get(key);
                        item.setId(oldItem.getId());
                        needUpdateItems.add((GcOffsetRelatedItemEO)item);
                    } else {
                        needAddItems.add((GcOffsetRelatedItemEO)item);
                    }
                });
                ((FinancialCheckOffsetServiceImpl)SpringContextUtils.getBean(FinancialCheckOffsetServiceImpl.class)).saveOffsetRelatedItem(needUpdateItems, needAddItems);
            }
        } else {
            ((FinancialCheckOffsetServiceImpl)SpringContextUtils.getBean(FinancialCheckOffsetServiceImpl.class)).saveOffsetRelatedItem(Collections.emptyList(), offsetRelatedItems);
        }
        this.logger.info("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u6570\u636e\u8fdb\u62b5\u6d88\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868\u7ed3\u675f\uff0c\u8017\u65f6:" + DateUtils.diffOf((Date)nowDate, (Date)DateUtils.now(), (int)13) + "s, \u5171\u5904\u7406" + items.size() + "\u6761\u6570\u636e\uff0c\u4fee\u6539" + needUpdateItems.size() + "\u6761\u6570\u636e\uff0c\u65b0\u589e" + needAddItems.size() + "\u6761\u6570\u636e");
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveOffsetRelatedItem(List<GcOffsetRelatedItemEO> needUpdateItems, List<GcOffsetRelatedItemEO> needAddItems) {
        if (!CollectionUtils.isEmpty(needUpdateItems)) {
            this.offsetRelatedItemDao.updateOffsetRelatedItemInfo(needUpdateItems);
        }
        if (!CollectionUtils.isEmpty(needAddItems)) {
            this.offsetRelatedItemDao.addBatch(needAddItems);
        }
    }

    private List<GcRelatedItemEO> checkSrcTimeStamp(List<GcRelatedItemEO> items, List<GcOffsetRelatedItemEO> offsetRelatedItemS) {
        Map<String, Long> itemTimeStampMap = offsetRelatedItemS.stream().collect(Collectors.groupingBy(GcOffsetRelatedItemEO::getRelatedItemId, Collectors.collectingAndThen(Collectors.toList(), list -> ((GcOffsetRelatedItemEO)list.get(0)).getSrcTimestamp())));
        ArrayList<GcRelatedItemEO> needSkipItems = new ArrayList<GcRelatedItemEO>();
        for (GcRelatedItemEO item : items) {
            Long offsetExistTimeStamp = itemTimeStampMap.get(item.getId());
            if (!Objects.nonNull(offsetExistTimeStamp) || offsetExistTimeStamp < item.getRecordTimestamp()) continue;
            needSkipItems.add(item);
        }
        return needSkipItems;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchSaveOffsetData(GcOffSetVchrDTO offSetVchrDTO) {
        Map<String, RelatedItemGcOffsetRelDTO> relatedItemId2DTOMap = this.listRelatedItemId2DTOMap(offSetVchrDTO);
        if (relatedItemId2DTOMap.isEmpty()) {
            throw new RuntimeException("\u6570\u636e\u67e5\u8be2\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        Set<String> ids = relatedItemId2DTOMap.keySet();
        String lockId = this.gcDbLockService.tryLock(ids, "\u4fdd\u5b58\u62b5\u9500\u7ed3\u679c", "checkCenter");
        try {
            if (StringUtils.isEmpty((String)lockId) && !CollectionUtils.isEmpty(ids)) {
                String userName = this.gcDbLockService.queryUserNameByInputItemId(ids, "checkCenter");
                throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            }
            this.adjustingEntryService.save(offSetVchrDTO);
            List<GcOffsetRelatedItemEO> vchrOffsetRelEOS = this.decorateOffsetGroupId(offSetVchrDTO);
            this.offsetRelatedItemDao.mergeOffsetGroupId(vchrOffsetRelEOS);
            this.checkTime(relatedItemId2DTOMap);
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    private List<GcOffsetRelatedItemEO> decorateOffsetGroupId(GcOffSetVchrDTO offSetVchrDTO) {
        ArrayList<GcOffsetRelatedItemEO> relatedItemOffsetRelEOList = new ArrayList<GcOffsetRelatedItemEO>();
        offSetVchrDTO.getItems().stream().forEach(gcOffSetVchrItemDTO -> {
            String dataSourcesIds = gcOffSetVchrItemDTO.getFieldValue("DATASOURCESID").toString();
            for (String dataSourceId : dataSourcesIds.split(",")) {
                relatedItemOffsetRelEOList.add(this.initRelatedItemOffsetRelDTO((GcOffSetVchrItemDTO)gcOffSetVchrItemDTO, dataSourceId));
            }
        });
        return relatedItemOffsetRelEOList;
    }

    private GcOffsetRelatedItemEO initRelatedItemOffsetRelDTO(GcOffSetVchrItemDTO gcOffSetVchrItemDTO, String dataSourceId) {
        GcOffsetRelatedItemEO relatedItemOffset = new GcOffsetRelatedItemEO();
        relatedItemOffset.setRelatedItemId(dataSourceId);
        relatedItemOffset.setOffsetGroupId(gcOffSetVchrItemDTO.getSrcOffsetGroupId());
        relatedItemOffset.setDataTime(gcOffSetVchrItemDTO.getDefaultPeriod());
        relatedItemOffset.setCheckState(gcOffSetVchrItemDTO.getChkState());
        relatedItemOffset.setSystemId(gcOffSetVchrItemDTO.getSystemId());
        relatedItemOffset.setUnionRuleId(gcOffSetVchrItemDTO.getRuleId());
        relatedItemOffset.setGcOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        relatedItemOffset.setGcUnitId(gcOffSetVchrItemDTO.getUnitId());
        relatedItemOffset.setGcSubjectCode(gcOffSetVchrItemDTO.getSubjectCode());
        relatedItemOffset.setRtOffsetCanDel(Integer.valueOf(0));
        return relatedItemOffset;
    }

    private void checkTime(Map<String, RelatedItemGcOffsetRelDTO> relatedItemId2DTOMap) {
        Set<String> ids = relatedItemId2DTOMap.keySet();
        List relatedItemList = this.gcRelatedItemDao.queryByIds(ids);
        Assert.isTrue((relatedItemId2DTOMap.size() == relatedItemList.size() ? 1 : 0) != 0, (String)("\u6570\u636e\u6761\u6570\u53d1\u751f\u53d8\u5316:" + ids), (Object[])new Object[0]);
        for (GcRelatedItemEO gcRelatedItemEO : relatedItemList) {
            RelatedItemGcOffsetRelDTO oldData = relatedItemId2DTOMap.get(gcRelatedItemEO.getId());
            Assert.isNotNull((Object)((Object)oldData), (String)("\u6570\u636e\u5df2\u88ab\u5220\u9664\uff1a" + gcRelatedItemEO.getId()), (Object[])new Object[0]);
            boolean notModified = oldData.notModified(gcRelatedItemEO.getUpdateTime(), gcRelatedItemEO.getRecordTimestamp());
            Assert.isTrue((boolean)notModified, (String)("\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316\uff1a" + gcRelatedItemEO.getId()), (Object[])new Object[0]);
        }
    }

    private Map<String, RelatedItemGcOffsetRelDTO> listRelatedItemId2DTOMap(GcOffSetVchrDTO offSetVchrDTO) {
        ArrayList ids = new ArrayList();
        offSetVchrDTO.getItems().forEach(gcOffSetVchrItemDTO -> {
            if (gcOffSetVchrItemDTO.hasField("DATASOURCESID")) {
                String dataSourcesIds = gcOffSetVchrItemDTO.getFieldValue("DATASOURCESID").toString();
                ids.addAll(Arrays.asList(dataSourcesIds.split(",")));
            }
        });
        List relatedItemList = this.gcRelatedItemDao.queryByIds(ids);
        return relatedItemList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, this::getRelatedItemOffsetRelDTO, (v1, v2) -> v2));
    }

    private RelatedItemGcOffsetRelDTO getRelatedItemOffsetRelDTO(GcRelatedItemEO relatedItemEO) {
        RelatedItemGcOffsetRelDTO relatedItemOffsetRelDTO = new RelatedItemGcOffsetRelDTO();
        relatedItemOffsetRelDTO.setId(UUIDOrderUtils.newUUIDStr());
        relatedItemOffsetRelDTO.setRelatedItemId(relatedItemEO.getId());
        relatedItemOffsetRelDTO.setUpdateTime(relatedItemEO.getUpdateTime());
        relatedItemOffsetRelDTO.setRecordTimestamp(relatedItemEO.getRecordTimestamp());
        relatedItemOffsetRelDTO.setChkState(relatedItemEO.getChkState());
        return relatedItemOffsetRelDTO;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void deleteOffsetItemAndRel(String ruleId, Boolean delCheckedOffsetFlag, GcCalcArgmentsDTO arg) {
        if (null == ruleId) {
            return;
        }
        QueryParamsDTO queryParamsVO = new QueryParamsDTO();
        queryParamsVO.setTaskId(arg.getTaskId());
        queryParamsVO.setAcctYear(arg.getAcctYear());
        queryParamsVO.setAcctPeriod(arg.getAcctPeriod());
        queryParamsVO.setPeriodStr(arg.getPeriodStr());
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.CONSOLIDATE.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.PHS.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.EQUITY_METHOD_ADJ.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.FAIRVALUE_ADJ.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX_RULE.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.FINANCIAL_CHECK.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.FINANCIAL_CHECK_NOCHECK.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        ArrayList<String> rules = new ArrayList<String>();
        rules.add(ruleId);
        queryParamsVO.setRules(rules);
        queryParamsVO.setOrgId(arg.getOrgId());
        ArrayList<Integer> elmModes = new ArrayList<Integer>();
        elmModes.add(OffsetElmModeEnum.AUTO_ITEM.getValue());
        queryParamsVO.setElmModes(elmModes);
        queryParamsVO.setOrgType(arg.getOrgType());
        queryParamsVO.setCurrency(arg.getCurrency());
        queryParamsVO.setSelectAdjustCode(arg.getSelectAdjustCode());
        Assert.isNotNull((Object)queryParamsVO.getOrgId(), (String)"\u5408\u5e76\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsVO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsVO.getAcctYear(), (String)"\u5e74\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Set<String> srcOffsetGroupIdResults = new HashSet<String>();
        HashSet mrecids = new HashSet();
        CalcLogUtil.getInstance().log(this.getClass(), "deleteAutoOffsetEntrysByRule-\u5408\u5e76\u8ba1\u7b97\u7b49\u53d6\u6d88\u89c4\u5219", (Object)arg);
        this.offsetCoreService.fillMrecids(queryParamsVO, srcOffsetGroupIdResults, mrecids);
        if (!Boolean.TRUE.equals(delCheckedOffsetFlag)) {
            srcOffsetGroupIdResults = this.offsetRelatedItemDao.filterByUnChecked(srcOffsetGroupIdResults);
        }
        this.batchDeleteByOffsetGroupId(srcOffsetGroupIdResults, (GcTaskBaseArguments)queryParamsVO);
    }

    @Override
    public List<GcOffsetRelatedItemEO> listByRelatedId(Collection<String> relatedItemId) {
        return this.offsetRelatedItemDao.listByRelatedId(relatedItemId);
    }

    @Override
    public List<GcOffsetRelatedItemEO> listById(Collection<String> ids) {
        return this.offsetRelatedItemDao.listById(ids);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteByOffsetGroupIdAndLock(Collection<String> groupIdSet, Collection<String> lockIds, GcTaskBaseArguments baseArguments) {
        String lockId;
        if (CollectionUtils.isEmpty(groupIdSet)) {
            return;
        }
        if (CollectionUtils.isEmpty(lockIds)) {
            this.offsetCoreService.deleteByOffsetGroupIds(groupIdSet, baseArguments);
            return;
        }
        String isolationField = "checkCenter";
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            isolationField = "check-center-offset";
        }
        if (StringUtils.isEmpty((String)(lockId = this.gcDbLockService.tryLock(lockIds, "\u5220\u9664\u62b5\u9500\u7ed3\u679c", isolationField)))) {
            String userName = this.gcDbLockService.queryUserNameByInputItemId(lockIds, isolationField);
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            this.offsetCoreService.deleteByOffsetGroupIds(groupIdSet, baseArguments);
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteByOffsetGroupId(Collection<String> groupIdSet, GcTaskBaseArguments baseArguments) {
        List<GcOffsetRelatedItemEO> items = this.offsetRelatedItemDao.listByOffsetGroupId(groupIdSet);
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        HashSet<String> lockIds = new HashSet<String>();
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            items.forEach(item -> lockIds.add(FinancialCheckOffsetUtils.getItemKey(item)));
        }
        this.batchDeleteByOffsetGroupIdAndLock(groupIdSet, lockIds, baseArguments);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void saveOffsetData(FinancialCheckRuleExecutorImpl.OffsetResult offsetResult, boolean rtOffsetCanDel) {
        if (CollectionUtils.isEmpty(offsetResult.getOffsetVchr().getItems())) {
            return;
        }
        List<GcOffsetRelatedItemEO> vchrOffsetRelEOS = RelatedItemOffsetRelAgent.decorateOffsetGroupId(offsetResult);
        vchrOffsetRelEOS.forEach(item -> item.setRtOffsetCanDel(Integer.valueOf(rtOffsetCanDel ? 1 : 0)));
        if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            this.adjustingEntryService.save(offsetResult.getOffsetVchr());
            this.offsetRelatedItemDao.addBatch(vchrOffsetRelEOS);
        } else if (CollectionUtils.isEmpty(vchrOffsetRelEOS)) {
            this.adjustingEntryService.save(offsetResult.getOffsetVchr());
        } else {
            List unionIds = vchrOffsetRelEOS.stream().map(FinancialCheckOffsetUtils::getItemKey).collect(Collectors.toList());
            String lockId = this.gcDbLockService.tryLock(unionIds, "\u4fdd\u5b58\u62b5\u9500\u7ed3\u679c", "check-center-offset");
            try {
                if (StringUtils.isEmpty((String)lockId)) {
                    String userName = this.gcDbLockService.queryUserNameByInputItemId(unionIds, "check-center-offset");
                    throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
                }
                this.adjustingEntryService.save(offsetResult.getOffsetVchr());
                this.offsetRelatedItemDao.mergeOffsetGroupId(vchrOffsetRelEOS);
            }
            finally {
                this.gcDbLockService.unlock(lockId);
            }
        }
    }

    @Override
    public List<GcOffsetRelatedItemEO> listByOffsetCondition(RelationToMergeArgDTO queryCondition) {
        return this.offsetRelatedItemDao.listByOffsetCondition(queryCondition);
    }

    private void initParentMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, Date date, String orgType) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        whereSql.append("((bfUnitTable.parents like ? and dfUnitTable.parents not like ? ) \n");
        whereSql.append("or (dfUnitTable.parents like ? and bfUnitTable.parents not like ? )) \n");
        this.initValidtimeCondition(whereSql, params, date);
    }

    private void initValidtimeCondition(StringBuffer whereSql, List<Object> params, Date date) {
        if (whereSql.length() > 0) {
            whereSql.append("and ");
        }
        whereSql.append("bfUnitTable.validtime<=? and bfUnitTable.invalidtime>? \n");
        whereSql.append("and dfUnitTable.validtime<=? and dfUnitTable.invalidtime>? \n");
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
    }

    private void initMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, String gcParentsStr, Date date, int codeLength) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        int len = gcParentsStr.length();
        whereSql.append(" substr(bfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(")\n");
        whereSql.append("and bfUnitTable.parents like ?\n");
        whereSql.append("and dfUnitTable.parents like ?\n");
        this.initValidtimeCondition(whereSql, params, date);
    }

    private void initUnitCondition(QueryParamsVO queryParamsVO, StringBuffer whereSql, GcOrgCenterService service) {
        List unitIdList = queryParamsVO.getUnitIdList();
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
        if (queryParamsVO.isFixedUnitQueryPosition()) {
            whereSql.append(this.getUnitWhere(unitIdList, queryParamsVO, service, " and "));
            whereSql.append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, " and "));
        } else {
            String leftSign = "(";
            String orSign = " or ";
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getUnitWhere(unitIdList, queryParamsVO, service, ""));
                leftSign = "";
            }
            if (!CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                whereSql.append(orSign).append(this.getUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                orSign = " and ";
            }
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(orSign).append(this.getOppUnitWhere(unitIdList, queryParamsVO, service, ""));
            }
            if (!CollectionUtils.isEmpty(unitIdList) || !CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(")\n");
            }
        }
    }

    private String getUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "bfUnitTable.parents like '" + unitParents + "%' ";
            }
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"bfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
            }
            return prefix + SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)"bfUnitTable.code");
        }
        return "";
    }

    private String getUnitParents(String unitId, GcOrgCenterService service) {
        if (unitId == null) {
            return null;
        }
        GcOrgCacheVO organization = service.getOrgByID(unitId);
        if (organization == null) {
            return null;
        }
        return organization.getParentStr();
    }

    private String getOppUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "dfUnitTable.parents like '" + unitParents + "%' ";
            }
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"dfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
            }
            return prefix + SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)"dfUnitTable.code");
        }
        return "";
    }
}

