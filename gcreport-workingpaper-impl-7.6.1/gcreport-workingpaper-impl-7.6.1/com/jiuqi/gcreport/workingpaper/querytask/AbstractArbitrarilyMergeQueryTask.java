/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO
 *  com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService
 *  com.jiuqi.gcreport.inputdata.util.InputDataNameProvider
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.dao.impl.ArbitrarilyMergeOffsetVchrQueryImpl;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperPrimaryQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperRytzItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperUnDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeOffSetItemAdjustService;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractArbitrarilyMergeQueryTask
extends AbstractWorkingPaperPrimaryQueryTask {
    @Autowired
    private ArbitrarilyMergeOffSetItemAdjustService arbitrarilyMergeOffSetItemAdjustService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ArbitrarilyMergeOffsetVchrQueryImpl ryOffsetVchrQuery;
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractArbitrarilyMergeQueryTask.class);
    private static final String ORG_TEMPORARY_TABLENAME = "GC_ORGTEMPORARY_AM";

    protected Map<String, Set<String>> getPrimaryId2BoundSubjectCodes(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        HashMap<String, Set<String>> primaryId2BoundSubjectCodes = new HashMap<String, Set<String>>();
        workingPaperTableDataVOS.stream().forEach(workingPaperTableDataVO -> {
            String primaryId = workingPaperTableDataVO.getPrimarySettingId();
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            List boundSubjects = primaryWorkpaperSettingVO.getBoundSubjects();
            List boundSubjectCodes = boundSubjects.stream().map(ConsolidatedSubjectVO::getCode).collect(Collectors.toList());
            Set boundSubjectAndChildrenCodes = subjectService.listAllChildrenCodesContainsSelf(boundSubjectCodes, this.getSystemId(condition.getSchemeID(), condition.getPeriodStr()));
            primaryId2BoundSubjectCodes.put(primaryId, boundSubjectAndChildrenCodes);
        });
        return primaryId2BoundSubjectCodes;
    }

    protected Map<String, List<WorkingPaperDxsItemDTO>> getPrimaryWorkingPaperOffsetItemDTOs(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, Set<String>> primaryId2BoundSubjectCodes) {
        HashMap<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperDxsItemDTO>>();
        List<WorkingPaperDxsItemDTO> workingPaperOffsetItemDTOs = this.getWorkingPaperOffsetItemDTOs(condition);
        workingPaperTableDataVOS.stream().forEach(workingPaperTableDataVO -> {
            String primaryId = workingPaperTableDataVO.getPrimarySettingId();
            Set boundSubjectAndChildrenCodes = (Set)primaryId2BoundSubjectCodes.get(primaryId);
            List currPrimaryTypeOffsetItems = workingPaperOffsetItemDTOs.stream().filter(offset -> boundSubjectAndChildrenCodes.contains(offset.getSubjectCode())).collect(Collectors.toList());
            primaryWorkingPaperOffsetItemDTOsMap.put(primaryId, currPrimaryTypeOffsetItems);
        });
        return primaryWorkingPaperOffsetItemDTOsMap;
    }

    protected Map<String, List<WorkingPaperUnDxsItemDTO>> getPrimaryWorkingPaperUnOffsetItemDTOs(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, Set<String>> primaryId2BoundSubjectCodes) {
        HashMap<String, List<WorkingPaperUnDxsItemDTO>> primaryWorkingPaperUnOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperUnDxsItemDTO>>();
        List<WorkingPaperUnDxsItemDTO> workingPaperUnOffsetItemDTOs = this.getWorkingPaperUnOffsetItemDTOs(condition);
        workingPaperTableDataVOS.stream().forEach(workingPaperTableDataVO -> {
            String primaryId = workingPaperTableDataVO.getPrimarySettingId();
            Set boundSubjectAndChildrenCodes = (Set)primaryId2BoundSubjectCodes.get(primaryId);
            List currPrimaryTypeOffsetItems = workingPaperUnOffsetItemDTOs.stream().filter(offset -> boundSubjectAndChildrenCodes.contains(offset.getSubjectCode())).collect(Collectors.toList());
            primaryWorkingPaperUnOffsetItemDTOsMap.put(primaryId, currPrimaryTypeOffsetItems);
        });
        return primaryWorkingPaperUnOffsetItemDTOsMap;
    }

    protected Map<String, WorkingPaperUnDxsItemDTO> mergeUnOffsetItemDatasByPrimary(Map<String, List<WorkingPaperUnDxsItemDTO>> primaryWorkingPaperUnOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap) {
        HashMap<String, WorkingPaperUnDxsItemDTO> mergeUnOffsetItemDatasByPrimaryMap = new HashMap<String, WorkingPaperUnDxsItemDTO>();
        primaryWorkingPaperUnOffsetItemDTOsMap.forEach((primaryId, offsetItemDatas) -> offsetItemDatas.forEach(orignOffsetItemData -> {
            String mergeKey = primaryId;
            if (mergeUnOffsetItemDatasByPrimaryMap.get(mergeKey) == null) {
                mergeUnOffsetItemDatasByPrimaryMap.put(mergeKey, WorkingPaperUnDxsItemDTO.empty());
            }
            WorkingPaperUnDxsItemDTO mergeWorkingPaperUnOffsetItemDTO = (WorkingPaperUnDxsItemDTO)mergeUnOffsetItemDatasByPrimaryMap.get(mergeKey);
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            this.mergeUnOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperUnDxsItemDTO)orignOffsetItemData, mergeWorkingPaperUnOffsetItemDTO);
        }));
        return mergeUnOffsetItemDatasByPrimaryMap;
    }

    protected Map<String, List<WorkingPaperRytzItemDTO>> getPrimaryWorkingPaperRyOffsetItemDTOs(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, Set<String>> primaryId2BoundSubjectCodes) {
        HashMap<String, List<WorkingPaperRytzItemDTO>> primaryWorkingPaperRyOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperRytzItemDTO>>();
        List<WorkingPaperRytzItemDTO> workingPaperRyOffsetItemDTOs = this.getWorkingPaperRyOffsetItemDTOs(condition);
        workingPaperTableDataVOS.stream().forEach(workingPaperTableDataVO -> {
            String primaryId = workingPaperTableDataVO.getPrimarySettingId();
            Set boundSubjectAndChildrenCodes = (Set)primaryId2BoundSubjectCodes.get(primaryId);
            List currPrimaryTypeOffsetItems = workingPaperRyOffsetItemDTOs.stream().filter(offset -> boundSubjectAndChildrenCodes.contains(offset.getSubjectCode())).collect(Collectors.toList());
            primaryWorkingPaperRyOffsetItemDTOsMap.put(primaryId, currPrimaryTypeOffsetItems);
        });
        return primaryWorkingPaperRyOffsetItemDTOsMap;
    }

    protected Map<String, WorkingPaperRytzItemDTO> mergeRyOffsetItemDatasByPrimary(Map<String, List<WorkingPaperRytzItemDTO>> primaryWorkingPaperRyOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap) {
        HashMap<String, WorkingPaperRytzItemDTO> mergeRyOffsetItemDatasByPrimaryMap = new HashMap<String, WorkingPaperRytzItemDTO>();
        primaryWorkingPaperRyOffsetItemDTOsMap.forEach((primaryId, offsetItemDatas) -> offsetItemDatas.forEach(orignOffsetItemData -> {
            String mergeKey = primaryId;
            if (mergeRyOffsetItemDatasByPrimaryMap.get(mergeKey) == null) {
                mergeRyOffsetItemDatasByPrimaryMap.put(mergeKey, WorkingPaperRytzItemDTO.empty());
            }
            WorkingPaperRytzItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperRytzItemDTO)mergeRyOffsetItemDatasByPrimaryMap.get(mergeKey);
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            this.mergeRyOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperRytzItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
        }));
        return mergeRyOffsetItemDatasByPrimaryMap;
    }

    protected List<WorkingPaperUnDxsItemDTO> getWorkingPaperUnOffsetItemDTOs(WorkingPaperQueryCondition condition) {
        long time1 = System.currentTimeMillis();
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.covertQueryParamsVO(condition);
        List<Map<String, Object>> offsetList = this.sumUnOffSetItemDTOBySubjectAndOrgAndYwlxAndElmmode(queryParamsVO);
        if (CollectionUtils.isEmpty(offsetList)) {
            return Collections.emptyList();
        }
        ArrayList<WorkingPaperUnDxsItemDTO> unOffSetVchrItemDTOS = new ArrayList<WorkingPaperUnDxsItemDTO>(offsetList.size());
        for (Map<String, Object> dataMap : offsetList) {
            WorkingPaperUnDxsItemDTO sumUnOffSetVchrItemDTO = this.buildWorkingPaperUnOffsetItemDTOByMap(condition, dataMap);
            String orgId = StringUtils.isEmpty((String)sumUnOffSetVchrItemDTO.getOrgCode()) ? condition.getOrgid() : sumUnOffSetVchrItemDTO.getOrgCode();
            sumUnOffSetVchrItemDTO.setOrgCode(orgId);
            unOffSetVchrItemDTOS.add(sumUnOffSetVchrItemDTO);
        }
        long time2 = System.currentTimeMillis();
        LOGGER.debug("\u5de5\u4f5c\u5e95\u7a3f\u83b7\u53d6\u672a\u62b5\u9500\u8bb0\u5f55\u8017\u65f6\uff1a{}", (Object)(time2 - time1));
        return unOffSetVchrItemDTOS;
    }

    protected List<WorkingPaperRytzItemDTO> getWorkingPaperRyOffsetItemDTOs(WorkingPaperQueryCondition condition) {
        long time1 = System.currentTimeMillis();
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.covertQueryParamsVO(condition);
        List<Map<String, Object>> ryOffsetList = this.arbitrarilyMergeOffSetItemAdjustService.sumRyOffsetValueGroupBySubjectcode(queryParamsVO);
        if (CollectionUtils.isEmpty(ryOffsetList)) {
            return Collections.emptyList();
        }
        ArrayList<WorkingPaperRytzItemDTO> ryOffSetVchrItemDTOS = new ArrayList<WorkingPaperRytzItemDTO>(ryOffsetList.size());
        for (Map<String, Object> dataMap : ryOffsetList) {
            WorkingPaperRytzItemDTO sumRyOffSetVchrItemDTO = this.buildWorkingPaperRyOffsetItemDTOByMap(condition, dataMap);
            String orgId = StringUtils.isEmpty((String)sumRyOffSetVchrItemDTO.getOrgCode()) ? condition.getOrgid() : sumRyOffSetVchrItemDTO.getOrgCode();
            sumRyOffSetVchrItemDTO.setOrgCode(orgId);
            ryOffSetVchrItemDTOS.add(sumRyOffSetVchrItemDTO);
        }
        long time2 = System.currentTimeMillis();
        LOGGER.debug("\u5de5\u4f5c\u5e95\u7a3f\u83b7\u53d6\u4efb\u610f\u8f93\u5165\u8c03\u6574\u62b5\u9500\u8bb0\u5f55\u8017\u65f6\uff1a{}", (Object)(time2 - time1));
        return ryOffSetVchrItemDTOS;
    }

    public List<Map<String, Object>> sumUnOffSetItemDTOBySubjectAndOrgAndYwlxAndElmmode(QueryParamsVO queryParamsVO) {
        String selectFields = " record.MDCODE as UNITID,record.SUBJECTCODE,record.DC as SUBJECTORIENT,sum(record.AMT) as OFFSET_VALUE,sum(record.DIFFAMT) as DIFF_VALUE, count(1) as OFFSETCOUNT ";
        String groupStr = " record.MDCODE,record.subjectCode,record.DC \n";
        return this.sumUnOffsetValueGroupBy(queryParamsVO, selectFields, groupStr, null);
    }

    public List<Map<String, Object>> getUnOffsetItem(QueryParamsVO queryParamsVO) {
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.UNIONRULEID,record.").append("DATATIME").append(",record.").append("MDCODE").append("  UNITID,record.OPPUNITID,record.SUBJECTCODE,record.DC,record.amt ").append(" AMMOUNT,record.MEMO");
        for (String code : queryParamsVO.getNotOffsetOtherColumns()) {
            selectFields.append(",record.").append(code);
        }
        String orderBySql = " record.amt desc,record.DC desc";
        return this.sumUnOffsetValueGroupBy(queryParamsVO, selectFields.toString(), null, orderBySql);
    }

    private List<Map<String, Object>> sumUnOffsetValueGroupBy(QueryParamsVO queryParamsVO, String selectFieldSql, String groupFieldSql, String orderBySql) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder head = new StringBuilder(32);
        StringBuilder whereSql = new StringBuilder(32);
        List ruleIds = (List)queryParamsVO.getFilterCondition().get("ruleId");
        queryParamsVO.getFilterCondition().remove("ruleId");
        if (!this.getUnOffsetEntryWhereSql(queryParamsVO, params, head, whereSql)) {
            return new ArrayList<Map<String, Object>>();
        }
        if (!CollectionUtils.isEmpty((Collection)ruleIds)) {
            whereSql.append(" and (").append(SqlUtils.getConditionOfIdsUseOr((Collection)ruleIds, (String)"record.RULEID"));
            whereSql.append(" or record.offSetSrcType = ").append(OffSetSrcTypeEnum.SUBJECT_RECLASSIFY.getSrcTypeValue()).append(")\n");
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        String orgTable = Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) ? ORG_TEMPORARY_TABLENAME : orgService.getCurrOrgType().getTable();
        StringBuilder sql = new StringBuilder(512);
        sql.append("select ").append(selectFieldSql).append(" from ").append(tableName).append("  record \n");
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.MDCODE = bfUnitTable.code)\n").append(" and bfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n").append(" and dfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
        } else {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        }
        sql.append((CharSequence)whereSql);
        if (!StringUtils.isEmpty((String)groupFieldSql)) {
            sql.append("group by ").append(groupFieldSql).append(" \n");
        }
        if (!StringUtils.isEmpty((String)orderBySql)) {
            sql.append("order by ").append(orderBySql).append(" \n");
        }
        List datas = dao.selectMap(sql.toString(), params.toArray());
        return datas;
    }

    private boolean getUnOffsetEntryWhereSql(QueryParamsVO queryParamsVO, List<Object> params, StringBuilder head, StringBuilder whereSql) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            this.initUnMergeUnitCondition(queryParamsVO, head, whereSql, params, null, date, orgService.getOrgCodeLength(), null);
        } else {
            Assert.isNotNull((Object)queryParamsVO.getOrgId());
            GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
            if (null == org || org.getParentStr() == null) {
                return false;
            }
            String parentGuids = org.getParentStr();
            String gcParentStr = org.getGcParentStr();
            this.initUnMergeUnitCondition(queryParamsVO, head, whereSql, params, parentGuids, date, orgService.getOrgCodeLength(), gcParentStr);
        }
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        this.ryOffsetVchrQuery.initUnitCondition(queryParamsVO, whereSql, orgService);
        this.ryOffsetVchrQuery.initPeriodConditionForUnOffset(queryParamsVO, params, whereSql);
        this.ryOffsetVchrQuery.initOtherCondition(whereSql, queryParamsVO);
        whereSql.append(" and record.OFFSETSTATE = '0' \n");
        return true;
    }

    private void initUnMergeUnitCondition(QueryParamsVO queryParamsVO, StringBuilder head, StringBuilder whereSql, List<Object> params, String parentGuids, Date date, int orgCodeLength, String gcParentStr) {
        String orgTypeId = queryParamsVO.getOrgType();
        String emptyUUID = "NONE";
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(")\n");
            params.add(orgTypeId);
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) \n");
        } else {
            int len = StringUtils.isEmpty((String)gcParentStr) ? 0 : gcParentStr.length();
            whereSql.append("where (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(")\n");
            whereSql.append("and bfUnitTable.parents like ?\n");
            whereSql.append("and dfUnitTable.parents like ?\n");
            params.add(parentGuids + "%");
            params.add(parentGuids + "%");
            params.add(orgTypeId);
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) and record.inputunitid ='" + emptyUUID + "' \n");
            if (null != orgTypeId) {
                whereSql.append(" or record.md_gcorgtype = ? and record.inputunitid=?\n)\n");
                params.add(orgTypeId);
                params.add(queryParamsVO.getOrgId());
            }
            this.ryOffsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        if (queryParamsVO.isDelete() && !"MD_ORG_CORPORATE".equals(queryParamsVO.getOrgType())) {
            whereSql.append(" and record.inputunitid <> '").append(emptyUUID).append("'\n");
        }
    }

    protected void mergeRyOffSetVchrItem(PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO, WorkingPaperRytzItemDTO offsetItemData, WorkingPaperRytzItemDTO mergeWorkingPaperRyOffsetItemDTO) {
        BigDecimal offSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
        BigDecimal offSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
        BigDecimal debit = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
        BigDecimal credit = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
        BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
        BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperRyOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
        mergeWorkingPaperRyOffsetItemDTO.setOffSetDebit(offSetDebit);
        mergeWorkingPaperRyOffsetItemDTO.setOffSetCredit(offSetCredit);
        mergeWorkingPaperRyOffsetItemDTO.setCredit(credit);
        mergeWorkingPaperRyOffsetItemDTO.setDebit(debit);
        mergeWorkingPaperRyOffsetItemDTO.setDiffd(diffd);
        mergeWorkingPaperRyOffsetItemDTO.setDiffc(diffc);
    }

    protected void mergeUnOffSetVchrItem(PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO, WorkingPaperUnDxsItemDTO offsetItemData, WorkingPaperUnDxsItemDTO mergeWorkingPaperUnOffsetItemDTO) {
        BigDecimal unOffSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperUnOffsetItemDTO.getUnOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getUnOffSetDebit()});
        BigDecimal unOffSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperUnOffsetItemDTO.getUnOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getUnOffSetCredit()});
        BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperUnOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
        BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperUnOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
        mergeWorkingPaperUnOffsetItemDTO.setUnOffSetDebit(unOffSetDebit);
        mergeWorkingPaperUnOffsetItemDTO.setUnOffSetCredit(unOffSetCredit);
        mergeWorkingPaperUnOffsetItemDTO.setDiffd(diffd);
        mergeWorkingPaperUnOffsetItemDTO.setDiffc(diffc);
    }

    @Override
    public List<Map<String, Object>> sumOffSetItemDTOBySubjectAndOrgAndYwlxAndElmmode(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        String selectFields = "record.UNITID, \nrecord.SUBJECTCODE,\nrecord.SUBJECTORIENT,\nrecord.OFFSETSRCTYPE,\nrecord.ELMMODE,\nrecord.GCBUSINESSTYPECODE,\nsum(record.OFFSET_DEBIT) as OFFSET_DEBIT_VALUE,\nsum(record.OFFSET_CREDIT) as OFFSET_CREDIT_VALUE,\nsum(record.DIFFD) as DIFFD_VALUE, \nsum(record.DIFFC) as DIFFC_VALUE, \ncount(1) as OFFSETCOUNT \n";
        sql.append("select ").append(selectFields);
        sql.append(" from ").append("GC_OFFSETVCHRITEM").append(" record \n");
        sql.append("  join GC_ORGTEMPORARY_AM bfUnitTable on (record.unitid = bfUnitTable.code) and bfUnitTable.batchId = ? \n  join GC_ORGTEMPORARY_AM dfUnitTable on (record.oppunitid = dfUnitTable.code) and dfUnitTable.batchId = ? \n");
        params.add(queryParamsDTO.getOrgBatchId());
        params.add(queryParamsDTO.getOrgBatchId());
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(")\n");
        whereSql.append(" and record.md_gcorgtype in('NONE',?) \n");
        params.add(queryParamsDTO.getOrgType());
        whereSql.append("and record.systemid = ? ");
        params.add(queryParamsDTO.getSystemId());
        whereSql.append("and record.DATATIME = ? ");
        params.add(queryParamsDTO.getPeriodStr());
        whereSql.append("and record.offsetCurr = ? ");
        params.add(queryParamsDTO.getCurrencyUpperCase());
        whereSql.append("and (record.disableFlag<> ? or record.disableFlag is null ) ");
        params.add(1);
        String groupStr = "record.unitid,record.subjectCode,record.SUBJECTORIENT,record.OFFSETSRCTYPE,record.ELMMODE,record.GCBUSINESSTYPECODE \n";
        sql.append((CharSequence)whereSql).append("\n").append(" group by ").append(groupStr);
        List results = EntNativeSqlDefaultDao.getInstance().selectMap(sql.toString(), params);
        return results;
    }
}

