/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.UnitSceneEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCancelService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.utils.BaseDataUtils
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataAdvanceService;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.dao.impl.UnitScenesTempDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.enums.UnitSceneEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCancelService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.utils.BaseDataUtils;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class GcOffsetAppInputDataServiceImpl
implements GcOffsetAppInputDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private InputDataLockService inputDataLockService;
    @Autowired
    private InputDataAdvanceService inputDataAdvanceService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Pagination<Map<String, Object>> listUnOffsetRecords(QueryParamsVO queryParamsVO, String sql, List<Object> params) {
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        ArrayList datas = new ArrayList();
        page.setContent(datas);
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        try {
            List rs;
            if (StringUtils.isEmpty((String)sql)) {
                Pagination pagination = page;
                return pagination;
            }
            if (pageNum == -1 || pageSize == -1) {
                rs = dao.selectMap(sql, params);
            } else {
                int firstResult = (pageNum - 1) * pageSize;
                int count = dao.count(sql, params);
                if (count < 1) {
                    Pagination pagination = page;
                    return pagination;
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
            Pagination pagination = page;
            return pagination;
        }
        finally {
            if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene()) && !params.isEmpty()) {
                UnitScenesTempDao.newInstance().deleteIdRealTempByBatchId((String)params.get(params.size() - 1));
            }
        }
    }

    @Override
    public Map<String, Object> getObject(Map<String, Object> rowData) {
        Double ammount;
        HashMap<String, Object> result = new HashMap<String, Object>(rowData);
        Integer direct = (Integer)result.get("DC");
        if (null == direct) {
            direct = OrientEnum.D.getValue();
        }
        if (null == (ammount = ConverterUtils.getAsDouble(result.get("AMMOUNT")))) {
            ammount = 0.0;
        }
        if (direct.equals(OrientEnum.D.getValue())) {
            result.put("DEBITVALUE", NumberUtils.doubleToString((Double)ammount));
        } else {
            result.put("CREDITVALUE", NumberUtils.doubleToString((Double)ammount));
        }
        for (Map.Entry entry : result.entrySet()) {
            if ("AMMOUNT".equals(entry.getKey()) || !(entry.getValue() instanceof Double)) continue;
            result.put((String)entry.getKey(), NumberUtils.doubleToString((Double)((Double)entry.getValue())));
        }
        return result;
    }

    @Override
    public void initParentMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, Date date, String orgType) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        whereSql.append("((bfUnitTable.parents like ? and dfUnitTable.parents not like ? ) \n");
        whereSql.append("or (dfUnitTable.parents like ? and bfUnitTable.parents not like ? )) \n");
        this.initValidtimeCondition(whereSql, params, date);
    }

    @Override
    public void initValidtimeCondition(StringBuffer whereSql, List<Object> params, Date date) {
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

    @Override
    public void initMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, String gcParentsStr, Date date, int codeLength) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        int len = gcParentsStr.length();
        whereSql.append(" substr(bfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(")\n");
        whereSql.append("and bfUnitTable.parents like ?\n");
        whereSql.append("and dfUnitTable.parents like ?\n");
        this.initValidtimeCondition(whereSql, params, date);
    }

    @Override
    public void initUnitCondition(QueryParamsVO queryParamsVO, StringBuffer whereSql, GcOrgCenterService service) {
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

    @Override
    public void initPeriodCondition(QueryParamsVO queryParamsVO, List<Object> params, StringBuffer whereSql) {
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        whereSql.append(" and record.reportSystemId = '").append(systemId).append("' \n");
        whereSql.append(" and record.DATATIME=? ");
        params.add(queryParamsVO.getPeriodStr());
        String currentcy = queryParamsVO.getCurrency();
        if (StringUtils.isEmpty((String)currentcy)) {
            currentcy = "CNY";
        }
        whereSql.append(" and record.MD_CURRENCY=? ");
        params.add(currentcy);
    }

    @Override
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
                    List allSubjectEos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
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
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE")).append("\n");
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
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(values, (String)("record." + key))).append("\n");
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

    @Override
    public StringBuffer selectFields(QueryParamsVO queryParamsVO) {
        StringBuffer selectFields = new StringBuffer(32);
        if (queryParamsVO.isQueryAllColumns()) {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
            selectFields.append(SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"record"));
            selectFields.append(",record.amt ").append("  AMMOUNT");
        } else {
            List<String> fieldNames = Arrays.asList("ID", "UNIONRULEID", "DATATIME", "UNITID", "OPPUNITID", "SUBJECTCODE", "DC", "AMMOUNT", "MEMO");
            selectFields.append("record.ID,record.UNIONRULEID,record.").append("DATATIME").append(",record.").append("MDCODE").append("  UNITID,record.OPPUNITID,record.SUBJECTCODE,record.DC,record.amt ").append("  AMMOUNT,record.MEMO");
            for (String code : queryParamsVO.getOtherShowColumns()) {
                if (fieldNames.contains(code)) continue;
                selectFields.append(",record.").append(code);
            }
        }
        return selectFields;
    }

    @Override
    public String getUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
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

    @Override
    public String getOppUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
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

    @Override
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

    @Override
    public String getUnitParents(String unitId, GcOrgCenterService service) {
        if (unitId == null) {
            return null;
        }
        GcOrgCacheVO organization = service.getOrgByID(unitId);
        if (organization == null) {
            return null;
        }
        return organization.getParentStr();
    }

    @Override
    public StringBuffer filterUnitScenes(QueryParamsVO queryParamsVO, List<Object> params, GcOrgCenterService tool, StringBuffer oldSql) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List inputDataList = dao.selectEntity(String.format(oldSql.toString(), "MDCODE"), params);
        HashSet<ArrayKey> unitFirstKeySet = new HashSet<ArrayKey>();
        HashMap<String, String> ruleId2GcBussinessTypeCacheMap = new HashMap<String, String>(32);
        for (InputDataEO inputDataEO : inputDataList) {
            String bussinessType = this.getBussinessType(inputDataEO.getUnionRuleId(), ruleId2GcBussinessTypeCacheMap);
            unitFirstKeySet.add(new ArrayKey(new Object[]{inputDataEO.getUnitId(), inputDataEO.getOppUnitId(), bussinessType}));
        }
        ArrayList<ArrayKey> result = new ArrayList<ArrayKey>(16);
        Assert.isTrue((boolean)UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene()));
        boolean isBilateral = UnitSceneEnum.isBilateral((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene());
        for (ArrayKey unitFirstKey : unitFirstKeySet) {
            ArrayKey oppUnitFirstKey = new ArrayKey(new Object[]{unitFirstKey.get(1), unitFirstKey.get(0), unitFirstKey.get(2)});
            if (unitFirstKeySet.contains(oppUnitFirstKey)) {
                if (!isBilateral) continue;
                result.add(unitFirstKey);
                continue;
            }
            if (isBilateral) continue;
            result.add(unitFirstKey);
        }
        StringBuffer sql = new StringBuffer(512);
        StringBuffer whereSql = new StringBuffer();
        params.clear();
        UnitScenesTempDao unitScenesTempDao = UnitScenesTempDao.newInstance();
        String batchId = unitScenesTempDao.insert(result);
        this.unitScenesInitUnitCondition(queryParamsVO, whereSql);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        sql.append("select ").append(this.selectFields(queryParamsVO)).append(" from ").append(tableName).append("  record\n");
        sql.append(" left join ").append("GC_UNIONRULE").append(" unionRule on (record.reportsystemid = unionRule.reportSystem and record.unionRuleId = unionRule.id)\n");
        sql.append(" join ").append(unitScenesTempDao.getTableName()).append(" unit on (record.mdcode = unit.mdcode and record.oppUnitId = unit.oppUnitCode  and unionRule.businesstypecode = unit.gcBusinessType  )\n");
        sql.append("where 1=1");
        sql.append(whereSql);
        sql.append("and record.offsetState='0' and unit.batchId=?\n");
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST = ").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        params.add(batchId);
        return sql;
    }

    @Override
    public void unitScenesInitUnitCondition(QueryParamsVO queryParamsVO, StringBuffer whereSql) {
        List unitIdList = queryParamsVO.getUnitIdList();
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
        boolean unitEmpty = CollectionUtils.isEmpty(unitIdList);
        boolean oppUnitEmpty = CollectionUtils.isEmpty(oppUnitIdList);
        String orgFieldName = "record.MDCODE";
        if (!unitEmpty && !oppUnitEmpty) {
            whereSql.append(" and ((").append(this.unitScenesGetUnitWhere(orgFieldName, unitIdList, queryParamsVO));
            whereSql.append(" and ").append(this.unitScenesGetUnitWhere("record.oppunitid", oppUnitIdList, queryParamsVO)).append(" )");
            whereSql.append(" or ( ").append(this.unitScenesGetUnitWhere(orgFieldName, oppUnitIdList, queryParamsVO));
            whereSql.append(" and  ").append(this.unitScenesGetUnitWhere("record.oppunitid", unitIdList, queryParamsVO)).append(" ))");
            return;
        }
        if (!unitEmpty) {
            whereSql.append(" and (").append(this.unitScenesGetUnitWhere(orgFieldName, unitIdList, queryParamsVO));
            whereSql.append(" or ").append(this.unitScenesGetUnitWhere("record.oppunitid", unitIdList, queryParamsVO)).append(" )");
            return;
        }
        if (!oppUnitEmpty) {
            whereSql.append(" and (").append(this.unitScenesGetUnitWhere(orgFieldName, oppUnitIdList, queryParamsVO));
            whereSql.append(" or ").append(this.unitScenesGetUnitWhere("record.oppunitid", oppUnitIdList, queryParamsVO)).append(" )");
        }
    }

    @Override
    public String unitScenesGetUnitWhere(String sqlFieldName, List<String> unitIdList, QueryParamsVO queryParamsVO) {
        if (CollectionUtils.isEmpty(unitIdList)) {
            return "";
        }
        if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
            TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)sqlFieldName);
            queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
            return newConditionOfIds.getCondition();
        }
        return SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)sqlFieldName);
    }

    @Override
    public StringBuffer getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params, Boolean isQueryParent) {
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
        if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene())) {
            selectFields.append(" record.UNIONRULEID, record.mdcode UNITID, record.mdcode,record.OPPUNITID");
        } else {
            selectFields = this.selectFields(queryParamsVO);
        }
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append(tableName).append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.%1$s = bfUnitTable.code)\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        if (queryParamsVO.getSumTabPenetrateCondition() != null) {
            if (queryParamsVO.getSumTabPenetrateCondition().get("subjectCode") != null) {
                HashSet<String> gcSubjectCodes = new HashSet<String>();
                String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
                if (!StringUtils.isEmpty((String)systemId)) {
                    List subjectVOs = this.subjectService.listAllChildrenSubjects(systemId, (String)queryParamsVO.getSumTabPenetrateCondition().get("subjectCode"));
                    for (ConsolidatedSubjectEO subjectEO : subjectVOs) {
                        gcSubjectCodes.add(subjectEO.getCode());
                    }
                }
                gcSubjectCodes.add((String)queryParamsVO.getSumTabPenetrateCondition().get("subjectCode"));
                whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(gcSubjectCodes, (String)"record.subjectCode "));
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
        sql.append("where ");
        sql.append(whereSql);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST = ").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        sql.append("and record.offsetState='0'\n");
        if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene())) {
            sql = this.filterUnitScenes(queryParamsVO, params, tool, sql);
        }
        return sql;
    }

    @Override
    public void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId) {
        HashMap<String, AbstractUnionRule> ruleId2TitleCache = new HashMap<String, AbstractUnionRule>();
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        HashMap<String, String> businessTypeCode2TitleCache = new HashMap<String, String>();
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns(), queryParamsVO.getTaskId());
        Map<String, Integer> unSysNumberField2Decimal = this.getUnSysNumberField2Decimal(systemId);
        YearPeriodObject yp = new YearPeriodObject(null, OrgPeriodUtil.getQueryOrgPeriod((String)queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        HashMap unitId2Title = new HashMap();
        HashMap<String, String> unitId2StopFlag = new HashMap<String, String>();
        List gcOrgCacheVOS = orgTool.listAllOrgByParentId(null);
        for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOS) {
            unitId2Title.put(gcOrgCacheVO.getId(), gcOrgCacheVO.getTitle());
            unitId2StopFlag.put(gcOrgCacheVO.getId(), String.valueOf(gcOrgCacheVO.isStopFlag()));
        }
        for (Map record : page.getContent()) {
            GcOrgCacheVO vo;
            if (!record.containsKey("UNITID") || !record.containsKey("OPPUNITID")) continue;
            if (!unitId2Title.containsKey(record.get("UNITID"))) {
                vo = orgTool.getOrgByID((String)record.get("UNITID"));
                unitId2Title.put(record.get("UNITID"), vo == null ? (String)record.get("UNITID") : vo.getTitle());
                unitId2StopFlag.put((String)record.get("UNITID"), vo == null ? String.valueOf(false) : String.valueOf(vo.isStopFlag()));
            }
            if (!unitId2Title.containsKey(record.get("OPPUNITID"))) {
                vo = orgTool.getOrgByID((String)record.get("OPPUNITID"));
                unitId2Title.put(record.get("OPPUNITID"), vo == null ? (String)record.get("OPPUNITID") : vo.getTitle());
                unitId2StopFlag.put((String)record.get("OPPUNITID"), vo == null ? String.valueOf(false) : String.valueOf(vo.isStopFlag()));
            }
            record.put("UNITTITLE", unitId2Title.get(record.get("UNITID")));
            record.put("OPPUNITTITLE", unitId2Title.get(record.get("OPPUNITID")));
            record.put("unitStopFlag", unitId2StopFlag.get(record.get("UNITID")));
            record.put("oppUnitStopFlag", unitId2StopFlag.get(record.get("OPPUNITID")));
            this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE");
            this.updateUnitAndSubjectTitle(showDictCode, record);
            this.setRuleTitle(record, ruleId2TitleCache);
            Integer elmMode = ConverterUtils.getAsInteger(record.get("ELMMODE"));
            if (null == elmMode) {
                elmMode = 0;
            }
            record.put("ELMMODETITLE", OffsetElmModeEnum.getElmModeTitle((Integer)elmMode));
            this.setBusinessTypeCodeTitle(record, businessTypeCode2TitleCache);
            this.setOtherShowColumnDictTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
            this.formatOtherShowNumberField(record, unSysNumberField2Decimal);
        }
    }

    @Override
    public Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns, String taskId) {
        HashMap<String, String> fieldCode2DictTableMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
                List defines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                TableModelDefine inputDataTabelDefine = dataModelService.getTableModelDefineByName(this.inputDataNameProvider.getTableNameByTaskId(taskId));
                List inputDataDefines = dataModelService.getColumnModelDefinesByTable(inputDataTabelDefine.getID());
                defines.addAll(inputDataDefines);
                for (ColumnModelDefine designFieldDefine : defines) {
                    TableModelDefine dictField;
                    if (!org.springframework.util.StringUtils.hasText(designFieldDefine.getReferTableID()) || (dictField = dataModelService.getTableModelDefineById(designFieldDefine.getReferTableID())) == null || !org.springframework.util.StringUtils.hasText(designFieldDefine.getCode())) continue;
                    fieldCode2DictTableMap.put(designFieldDefine.getCode(), dictField.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
            }
        }
        return fieldCode2DictTableMap;
    }

    @Override
    public void setSubjectTitle(String systemId, Map<String, Object> record, Map<String, String> subject2TitleCache, String titleKey) {
        String title;
        List allSubjectEos;
        String subjectCode = (String)record.get("SUBJECTCODE");
        if (null == subjectCode) {
            return;
        }
        if (CollectionUtils.isEmpty(subject2TitleCache) && !CollectionUtils.isEmpty(allSubjectEos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == (title = subject2TitleCache.get(subjectCode))) {
            ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
            title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        record.put(titleKey, subject2TitleCache.get(subjectCode));
    }

    @Override
    public void updateUnitAndSubjectTitle(boolean showDictCode, Map<String, Object> record) {
        if (!showDictCode) {
            return;
        }
        record.put("UNITTITLE", record.get("UNITID") + "|" + record.get("UNITTITLE"));
        record.put("OPPUNITTITLE", record.get("OPPUNITID") + "|" + record.get("OPPUNITTITLE"));
        record.put("SUBJECTTITLE", record.get("SUBJECTCODE") + "|" + record.get("SUBJECTTITLE"));
    }

    @Override
    public void setRuleTitle(Map<String, Object> record, Map<String, AbstractUnionRule> ruleId2TitleCache) {
        String unionRuleId = (String)record.get("RULEID");
        if (org.springframework.util.StringUtils.isEmpty(unionRuleId)) {
            unionRuleId = (String)record.get("UNIONRULEID");
        }
        if (!org.springframework.util.StringUtils.isEmpty(unionRuleId)) {
            AbstractUnionRule vo;
            if (!ruleId2TitleCache.containsKey(unionRuleId)) {
                AbstractUnionRule rule = this.unionRuleService.selectUnionRuleDTOById(unionRuleId);
                ruleId2TitleCache.put(unionRuleId, rule);
            }
            if ((vo = ruleId2TitleCache.get(unionRuleId)) != null) {
                if (!StringUtils.isEmpty((String)this.getLocalizedName(vo.getId()))) {
                    vo.setTitle(this.getLocalizedName(vo.getId()));
                }
                String ruleTitle = vo.getLocalizedName();
                if (RuleTypeEnum.FLEXIBLE.getCode().equals(vo.getRuleType()) || RuleTypeEnum.RELATE_TRANSACTIONS.getCode().equals(vo.getRuleType())) {
                    ruleTitle = this.getFetchSetTitle(record, vo);
                }
                record.put("RULETITLE", ruleTitle);
                record.put("UNIONRULETITLE", ruleTitle);
                record.put("RULETYPE", vo.getRuleType());
            }
        }
    }

    private String getLocalizedName(String id) {
        return ((GcI18nHelper)SpringBeanUtils.getBean(GcI18nHelper.class)).getMessage(id);
    }

    @Override
    public void setBusinessTypeCodeTitle(Map<String, Object> record, Map<String, String> businessTypeCode2TitleCache) {
        String businessTypeCode = (String)record.get("GCBUSINESSTYPECODE");
        if (null != businessTypeCode) {
            List baseDatas;
            if (CollectionUtils.isEmpty(businessTypeCode2TitleCache) && !CollectionUtils.isEmpty(baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_GCBUSINESSTYPE"))) {
                baseDatas.forEach(baseData -> businessTypeCode2TitleCache.put(baseData.getCode(), baseData.getTitle()));
            }
            record.put("GCBUSINESSTYPE", businessTypeCode2TitleCache.get(businessTypeCode));
        }
    }

    @Override
    public void setOtherShowColumnDictTitle(Map<String, Object> record, List<String> otherShowColumns, Map<String, String> fieldCode2DictTableMap, boolean showDictCode) {
        if (CollectionUtils.isEmpty(otherShowColumns)) {
            return;
        }
        for (String otherShowColumn : otherShowColumns) {
            String dictTitle;
            Object value = record.get(otherShowColumn);
            if (Objects.isNull(value)) continue;
            if ("SUBJECTORIENT".equals(otherShowColumn)) {
                record.put(otherShowColumn, Integer.valueOf(value.toString()) == 1 ? "\u501f" : "\u8d37");
                continue;
            }
            if ("EFFECTTYPE".equals(otherShowColumn)) {
                record.put(otherShowColumn, EFFECTTYPE.getTitleByCode((String)value.toString()));
                continue;
            }
            if ("MODIFYTIME".equals(otherShowColumn) && value != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                record.put(otherShowColumn, sdf.format((Date)value));
                continue;
            }
            String dictTableName = fieldCode2DictTableMap.get(otherShowColumn);
            if (dictTableName == null || Objects.isNull(value) || org.springframework.util.StringUtils.isEmpty(dictTitle = BaseDataUtils.getDictTitle((String)dictTableName, (String)((String)value)))) continue;
            record.put(otherShowColumn, showDictCode ? value + "|" + dictTitle : dictTitle);
        }
    }

    @Override
    public String getFetchSetTitle(Map<String, Object> record, AbstractUnionRule vo) {
        String fetchSetGroupId;
        if (!(vo instanceof FlexibleRuleDTO)) {
            return vo.getLocalizedName();
        }
        String string = fetchSetGroupId = org.springframework.util.StringUtils.isEmpty(record.get("FETCHSETGROUPID")) ? "" : record.get("FETCHSETGROUPID").toString();
        if (org.springframework.util.StringUtils.isEmpty(fetchSetGroupId)) {
            return vo.getLocalizedName();
        }
        FlexibleRuleDTO flexDto = (FlexibleRuleDTO)vo;
        if (CollectionUtils.isEmpty(flexDto.getFetchConfigList())) {
            return vo.getLocalizedName();
        }
        if (flexDto.getFetchConfigList().size() <= 1) {
            return vo.getLocalizedName();
        }
        int i = 0;
        for (FlexibleFetchConfig config : flexDto.getFetchConfigList()) {
            if (org.springframework.util.StringUtils.isEmpty(config.getDescription())) {
                ++i;
            }
            if (!fetchSetGroupId.equals(config.getFetchSetGroupId())) continue;
            return org.springframework.util.StringUtils.isEmpty(config.getDescription()) ? flexDto.getLocalizedName() + "--" + i : config.getDescription();
        }
        return vo.getLocalizedName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelInputOffset(Collection<String> inputItemIds, InputWriteNecLimitCondition inputWriteNecLimitCondition) {
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return;
        }
        String lockId = this.inputDataLockService.tryLock(inputItemIds, inputWriteNecLimitCondition, "\u53d6\u6d88\u62b5\u9500");
        if (org.springframework.util.StringUtils.isEmpty(lockId)) {
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u5176\u5b83\u64cd\u4f5c\u4f7f\u7528\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputWriteNecLimitCondition.getTaskId());
            ((GcOffsetAppInputDataService)SpringContextUtils.getBean(GcOffsetAppInputDataService.class)).cancelLockedInputOffset(lockId, tableName);
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void cancelLockedInputOffset(String lockId, String tableName) {
        if (IdTemporaryTableUtils.IS_MYSQL) {
            List gcOffSetVchrItemAdjusts = this.offsetCoreService.listIdsByLockId(lockId);
            EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class).deleteBatch(gcOffSetVchrItemAdjusts);
        } else {
            this.offsetCoreService.deleteByLockId(lockId);
        }
        this.inputdataDao.cancelLockedOffset(lockId, tableName);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void cancelLockedInputOffsetByCurrency(String lockId, String tableName, List<String> currencyList) {
        if (IdTemporaryTableUtils.IS_MYSQL) {
            List gcOffSetVchrItemAdjusts = this.offsetCoreService.listIdsByLockId(lockId, currencyList);
            EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class).deleteBatch(gcOffSetVchrItemAdjusts);
        } else {
            this.offsetCoreService.deleteByLockId(lockId, currencyList);
        }
        this.inputdataDao.cancelLockedOffsetByCurrency(lockId, tableName, currencyList);
    }

    @Override
    public void cancelInputOffsetByOffsetGroupId(Collection<String> srcOffsetGroupIds, String taskId) {
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List<InputDataEO> inputDataItems = this.inputdataDao.queryIdLimitFieldsByOffsetGroupId(srcOffsetGroupIds, tableName);
        this.cancelInputOffsetByInputDataEOList(srcOffsetGroupIds, inputDataItems);
    }

    private void cancelInputOffsetByInputDataEOList(Collection<String> srcOffsetGroupIds, List<InputDataEO> inputDataItems) {
        if (!CollectionUtils.isEmpty(inputDataItems)) {
            InputDataEO inputItem = inputDataItems.get(0);
            InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(inputItem.getTaskId(), String.valueOf(inputItem.getFieldValue("DATATIME")), String.valueOf(inputItem.getFieldValue("MD_CURRENCY")));
            this.cancelInputOffset(inputDataItems.stream().map(InputDataEO::getId).collect(Collectors.toList()), limitCondition);
        }
        Collection offSetItemAdjustCancelServiceList = SpringContextUtils.getBeans(GcOffSetItemAdjustCancelService.class);
        for (GcOffSetItemAdjustCancelService cancelService : offSetItemAdjustCancelServiceList) {
            cancelService.cancelOffsetByOffsetGroupId(srcOffsetGroupIds);
        }
    }

    @Override
    public void calc(QueryParamsVO queryParamsVO) {
        if (CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.dataentryquery.inputdataservice.unitidlistemptymsg"));
        }
        if (queryParamsVO.getUnitIdList().size() > 10) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.dataentryquery.inputdataservice.unitidlistmorethantenmsg"));
        }
        String systemId = this.taskService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (systemId == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.dataentryquery.inputdataservice.systemidemptymsg"));
        }
        List<InputDataEO> inputItems = this.inputdataDao.queryUnOffsetRecordsForCalc(queryParamsVO);
        RuleMappingImplUtils.remappingRule(inputItems, systemId);
        Map<String, Set<String>> offsetedOrgAndItemIdMapping = this.inputDataAdvanceService.realTimeOffset(inputItems, null, false);
        Set<String> offsetItems = offsetedOrgAndItemIdMapping.keySet();
        String unitCodes = CollectionUtils.isEmpty(offsetItems) ? queryParamsVO.getUnitIdList().toString() : offsetItems.toString();
        String taskTitle = GcOffsetItemUtils.getTaskTitle((String)queryParamsVO.getTaskId());
        String message = String.format("\u4efb\u52a1\uff1a%1s\uff1b\u65f6\u671f\uff1a%2s\uff1b\u5408\u5e76\u5355\u4f4d\uff1a%4s\uff1b\u672c\u65b9\u5355\u4f4d:%3s\uff1b", taskTitle, queryParamsVO.getPeriodStr(), unitCodes, queryParamsVO.getOrgId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u81ea\u52a8\u62b5\u9500-" + taskTitle + "-\u65f6\u671f" + queryParamsVO.getPeriodStr()), (String)message);
    }

    private Map<String, Integer> getUnSysNumberField2Decimal(String systemId) {
        List dimensionsByTableName = this.optionService.getDimensionsByTableName("GC_INPUTDATATEMPLATE", systemId);
        if (CollectionUtils.isEmpty(dimensionsByTableName)) {
            return new HashMap<String, Integer>();
        }
        return dimensionsByTableName.stream().filter(item -> {
            if (item.getFieldType() == null) {
                return false;
            }
            return FieldType.FIELD_TYPE_DECIMAL.getValue() == item.getFieldType().intValue() || FieldType.FIELD_TYPE_FLOAT.getValue() == item.getFieldType().intValue();
        }).collect(Collectors.toMap(DimensionVO::getCode, item -> this.getFractionDigits("GC_INPUTDATATEMPLATE", item.getCode()), (o1, o2) -> o1));
    }

    private Integer getFractionDigits(String tableName, String fieldCode) {
        TableModelDefine tableModelDefineByName = this.dataModelService.getTableModelDefineByName(tableName);
        if (Objects.isNull(tableModelDefineByName)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u5b9a\u4e49\u4e0d\u5b58\u5728\u3002");
        }
        ColumnModelDefine columnModelDefineByCode = this.dataModelService.getColumnModelDefineByCode(tableModelDefineByName.getID(), fieldCode);
        if (Objects.isNull(columnModelDefineByCode)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u4e2d" + fieldCode + "\u5b57\u6bb5\u4e0d\u5b58\u5728");
        }
        return columnModelDefineByCode.getDecimal();
    }

    protected void formatOtherShowNumberField(Map<String, Object> record, Map<String, Integer> numberFieldCode2Decimal) {
        if (numberFieldCode2Decimal == null || numberFieldCode2Decimal.size() == 0) {
            return;
        }
        for (String code : numberFieldCode2Decimal.keySet()) {
            if (record.get(code) == null) continue;
            Double value = ConverterUtils.getAsDouble((Object)record.get(code));
            record.put(code, NumberUtils.doubleToString((Double)value, (int)numberFieldCode2Decimal.get(code)));
        }
    }

    private String getBussinessType(String unionRuleId, Map<String, String> ruleId2GcBussinessTypeCacheMap) {
        if (StringUtils.isEmpty((String)unionRuleId)) {
            return null;
        }
        if (!ruleId2GcBussinessTypeCacheMap.containsKey(unionRuleId)) {
            AbstractUnionRule abstractUnionRule = this.unionRuleService.selectUnionRuleDTOById(unionRuleId);
            ruleId2GcBussinessTypeCacheMap.put(unionRuleId, abstractUnionRule == null ? null : abstractUnionRule.getBusinessTypeCode());
        }
        return ruleId2GcBussinessTypeCacheMap.get(unionRuleId);
    }
}

