/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.check.IOffsetGroupChecker
 *  com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckGather
 *  com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.enums.GcBusinessTypeQueryRuleEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.check.IOffsetGroupChecker;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckGather;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.enums.GcBusinessTypeQueryRuleEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOffSetVchrItemAdjustDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrItemDTO;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeOffSetItemAdjustService;
import com.jiuqi.gcreport.workingpaper.utils.ArbitrarilyMergeOffsetConvertUtil;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ArbitrarilyMergeOffSetItemAdjustServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements ArbitrarilyMergeOffSetItemAdjustService {
    private Logger logger = LoggerFactory.getLogger(ArbitrarilyMergeOffSetItemAdjustServiceImpl.class);
    @Autowired
    private ArbitrarilyMergeOffSetVchrItemAdjustDao ryAdjustingEntryDao;
    @Autowired
    private ConsolidatedOptionCacheService optionCacheService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    private Map<String, AbstractUnionRule> ruleMap = new HashMap<String, AbstractUnionRule>();

    @Override
    public GcOffSetVchrDTO save(GcOffSetVchrDTO offSetItemDTO) {
        List itemDTOs = offSetItemDTO.getItems();
        List filterOffSetVchrItemAdjustEOs = itemDTOs.stream().filter(item -> !OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.equals((Object)item.getOffSetSrcType())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(filterOffSetVchrItemAdjustEOs)) {
            return offSetItemDTO;
        }
        this.checkGroupDTO(offSetItemDTO);
        if (offSetItemDTO.isNeedDelete()) {
            this.ryAdjustingEntryDao.deleteRyByMrecids(Arrays.asList(offSetItemDTO.getMrecid()));
        }
        Date date = new Date();
        List gcOffSetVchrItemAdjustEOs = filterOffSetVchrItemAdjustEOs.stream().map(itemDTO -> {
            if (StringUtils.isEmpty(itemDTO.getId())) {
                itemDTO.setId(UUIDOrderUtils.newUUIDStr());
            }
            itemDTO.setmRecid(offSetItemDTO.getMrecid());
            itemDTO.setCreateTime(date);
            return OffsetCoreConvertUtil.convertDTO2EO((GcOffSetVchrItemDTO)itemDTO);
        }).collect(Collectors.toList());
        ArrayList<ArbitrarilyMergeOffSetVchrItemAdjustEO> ryOffSetVchrItemAdjustEOs = new ArrayList<ArbitrarilyMergeOffSetVchrItemAdjustEO>(gcOffSetVchrItemAdjustEOs.size());
        for (GcOffSetVchrItemAdjustEO item2 : gcOffSetVchrItemAdjustEOs) {
            ryOffSetVchrItemAdjustEOs.add(this.convertOffset2RyEO(item2));
        }
        this.ryAdjustingEntryDao.addBatch(ryOffSetVchrItemAdjustEOs);
        return offSetItemDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ArbitrarilyMergeOffSetVchrDTO> batchRySaveBySrcGroupId(List<ArbitrarilyMergeOffSetVchrDTO> offSetItemDTOs) {
        for (ArbitrarilyMergeOffSetVchrDTO dto : offSetItemDTOs) {
            ArbitrarilyMergeOffSetVchrItemDTO itemDTO = dto.getItems().get(0);
            String srcOffsetGroupId = itemDTO.getSrcOffsetGroupId();
            ArrayList<String> srcOffsetGroupIds = new ArrayList<String>();
            srcOffsetGroupIds.add(srcOffsetGroupId);
        }
        offSetItemDTOs.forEach(offSetItemDTO -> this.saveSingleGroupBySrcGroupID((ArbitrarilyMergeOffSetVchrDTO)offSetItemDTO));
        return offSetItemDTOs;
    }

    @Override
    public ArbitrarilyMergeOffSetVchrItemAdjustEO convertRyDTO2EO(ArbitrarilyMergeOffSetVchrItemDTO itemDTO) {
        return ArbitrarilyMergeOffsetConvertUtil.getInstance().convertRyDTO2EO(itemDTO);
    }

    @Override
    public void deleteRyBySrcOffsetGroupIds(String taskId, List<String> srcOffsetGroupIds, int acctYear, int effectType, int acctPeriod, String orgType, String selectAdjustCode) {
        this.ryAdjustingEntryDao.deleteRyBySrcOffsetGroupIds(taskId, srcOffsetGroupIds, acctYear, effectType, acctPeriod, orgType, null, 0, selectAdjustCode);
    }

    @Override
    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> queryRyOffsetRecordsByWhere(String[] columnNamesInDB, Object[] values, ArbitrarilyMergeInputAdjustQueryCondi condi) {
        return this.ryAdjustingEntryDao.queryRyOffsetRecordsByWhere(columnNamesInDB, values, condi);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Pagination<Map<String, Object>> getRyOffsetEntry(QueryParamsVO queryParamsVO, boolean setBoundSubjectCodes) {
        Pagination<Map<String, Object>> ryOffsetEntry;
        this.handleUnitAndOppUnitParam(queryParamsVO);
        try {
            ryOffsetEntry = this.assembleOffsetEntry(this.ryAdjustingEntryDao.queryRyOffsetingEntry(queryParamsVO), queryParamsVO);
        }
        finally {
            if (!org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getTempGroupIdList())) {
                IdTemporaryTableUtils.deteteByGroupIds((Collection)queryParamsVO.getTempGroupIdList());
            }
        }
        if (setBoundSubjectCodes) {
            this.setBoundSubjectCodes(ryOffsetEntry, queryParamsVO);
        }
        return ryOffsetEntry;
    }

    @Override
    public List<Map<String, Object>> sumRyOffsetValueGroupBySubjectcode(QueryParamsVO queryParamsVO) {
        return this.ryAdjustingEntryDao.sumRyOffsetValueGroupBySubjectcode(queryParamsVO);
    }

    private void checkGroupDTO(GcOffSetVchrDTO dto) {
        for (IOffsetGroupChecker groupValidator : OffsetItemCheckGather.getGroupValidatorList()) {
            OffsetItemCheckResult offsetItemCheckResult = groupValidator.saveCheck(dto);
            if (offsetItemCheckResult == null || offsetItemCheckResult.isSuccess()) continue;
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u4fdd\u5b58:[" + groupValidator.validatorName() + "]\u5206\u7ec4\u6821\u9a8c\u5668\u6821\u9a8c\u5931\u8d25" + offsetItemCheckResult.getMessage());
        }
    }

    private void handleUnitAndOppUnitParam(QueryParamsVO queryParamsVO) {
        List oppUnitIdList;
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, queryParamsVO.getPeriodStr()));
        List unitIdList = queryParamsVO.getUnitIdList();
        if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
            queryParamsVO.setEnableTempTableFilterUnitOrOppUnit(Boolean.valueOf(true));
            queryParamsVO.setUnitIdList(this.getAllChildrenOrgByOrgList(unitIdList, tool));
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(oppUnitIdList = queryParamsVO.getOppUnitIdList())) {
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

    private ArbitrarilyMergeOffSetVchrDTO saveSingleGroupBySrcGroupID(ArbitrarilyMergeOffSetVchrDTO offSetItemDTO) {
        this.checkRyGroupDTO(offSetItemDTO);
        List<ArbitrarilyMergeOffSetVchrItemDTO> itemDTOs = offSetItemDTO.getItems();
        if (itemDTOs != null && itemDTOs.size() > 0) {
            Date date = new Date();
            List gcOffSetVchrItemAdjustEOs = itemDTOs.stream().map(itemDTO -> {
                itemDTO.setCreateTime(date);
                itemDTO.setAdjustType(GCAdjTypeEnum.BEFOREADJ.getCode());
                ArbitrarilyMergeOffSetVchrItemAdjustEO itemEO = this.convertRyDTO2EO((ArbitrarilyMergeOffSetVchrItemDTO)((Object)itemDTO));
                if (StringUtils.isEmpty(itemEO.getId())) {
                    String id = UUIDOrderUtils.newUUIDStr();
                    itemEO.setId(id);
                    itemDTO.setId(id);
                }
                itemEO.setmRecid(offSetItemDTO.getMrecid());
                itemDTO.setmRecid(itemEO.getmRecid());
                return itemEO;
            }).collect(Collectors.toList());
            this.ryAdjustingEntryDao.saveAll(gcOffSetVchrItemAdjustEOs);
        }
        return offSetItemDTO;
    }

    private void setBoundSubjectCodes(Pagination<Map<String, Object>> offsetEntry, QueryParamsVO queryParamsVO) {
        List offsetEntryPagination = offsetEntry.getContent();
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        HashMap consCodeToBoundSubject = new HashMap();
        offsetEntryPagination.forEach(vo -> {
            if (!(StringUtils.isEmpty(vo.get("SRCTYPE")) || 40 != ConverterUtils.getAsInteger(vo.get("SRCTYPE")) && 41 != ConverterUtils.getAsInteger(vo.get("SRCTYPE")))) {
                if (!consCodeToBoundSubject.containsKey(vo.get("SUBJECTCODE"))) {
                    ArrayList boundSubjects = new ArrayList();
                    consCodeToBoundSubject.put(ConverterUtils.getAsString(vo.get("SUBJECTCODE")), boundSubjects);
                }
                vo.put("boundSubjects", consCodeToBoundSubject.get(vo.get("SUBJECTCODE")));
            }
        });
    }

    @Override
    public Pagination<Map<String, Object>> assembleOffsetEntry(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO) {
        List records = page.getContent();
        if (org.springframework.util.CollectionUtils.isEmpty(records)) {
            return page;
        }
        page.setContent(this.setRowSpanAndSort(records));
        String systemId = queryParamsVO.getSystemId();
        if (StringUtils.isEmpty(systemId)) {
            systemId = this.consolidatedTaskService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        }
        this.setTitles(page, queryParamsVO, systemId);
        return page;
    }

    private void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId) {
        HashMap ruleId2TitleCache = new HashMap();
        HashMap subject2TitleCache = new HashMap();
        HashMap businessTypeCode2TitleCache = new HashMap();
        Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns());
        YearPeriodObject yp = new YearPeriodObject(null, OrgPeriodUtil.getQueryOrgPeriod((String)queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionCacheService.getConOptionBySystemId(systemId);
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
            this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            this.updateUnitAndSubjectTitle(showDictCode, record);
            this.setRuleTitle(record, ruleId2TitleCache);
            Integer elmMode = ConverterUtils.getAsInteger(record.get("ELMMODE"));
            if (null == elmMode) {
                elmMode = 0;
            }
            record.put("ELMMODETITLE", OffsetElmModeEnum.getElmModeTitle((Integer)elmMode));
            this.setBusinessTypeCodeTitle(record, businessTypeCode2TitleCache);
            this.setOtherShowColumnDictTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
        }
    }

    @Override
    public void checkRyGroupDTO(ArbitrarilyMergeOffSetVchrDTO itemDTO) {
        if (null == itemDTO.getMrecid()) {
            itemDTO.setMrecid(UUIDOrderUtils.newUUIDStr());
        }
        double sumOffsetValue = 0.0;
        HashSet<String> unitguids = new HashSet<String>();
        boolean isLossGain = false;
        HashSet<OffSetSrcTypeEnum> lossGainSrcTypeSet = new HashSet<OffSetSrcTypeEnum>(Arrays.asList(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX_RULE, OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY));
        for (ArbitrarilyMergeOffSetVchrItemDTO offSetItemDTO : itemDTO.getItems()) {
            if (!org.springframework.util.CollectionUtils.isEmpty(offSetItemDTO.getUnSysFields())) {
                for (String unSysFieldKey : offSetItemDTO.getUnSysFields().keySet()) {
                    if (offSetItemDTO.getFields().containsKey(unSysFieldKey)) continue;
                    offSetItemDTO.addFieldValue(unSysFieldKey, offSetItemDTO.getUnSysFieldValue(unSysFieldKey));
                }
            }
            this.checkItemDTO(offSetItemDTO);
            unitguids.add(offSetItemDTO.getUnitId());
            unitguids.add(offSetItemDTO.getOppUnitId());
            sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)offSetItemDTO.getOffSetDebit(), (Double)offSetItemDTO.getOffSetCredit()));
            isLossGain = isLossGain || lossGainSrcTypeSet.contains(offSetItemDTO.getOffSetSrcType());
        }
        Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        Assert.isTrue((itemDTO.isAllowMoreUnit() || isLossGain || unitguids.size() == 2 ? 1 : 0) != 0, (String)("\u540c\u4e00\u7ec4\u62b5\u9500\u5206\u5f55\u6d89\u53ca\u7684\u5355\u4f4d\u53ea\u80fd\u4e3a\u4e24\u5bb6,\u76ee\u524d\u4e3a" + unitguids.size() + "\u5bb6"), (Object[])new Object[0]);
    }

    private void checkItemDTO(ArbitrarilyMergeOffSetVchrItemDTO item) {
        Double amt;
        Assert.isNotNull((Object)item.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((null != item.getAcctYear() && null != item.getAcctPeriod() && !StringUtils.isEmpty(item.getDefaultPeriod()) ? 1 : 0) != 0, (String)"\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)item.getOffSetCurr(), (String)"\u5e01\u522b\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((item.getOffSetCurr().length() < 10 ? 1 : 0) != 0, (String)"\u5e01\u522b\u975e\u6cd5", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getUnitId(), (String)"\u672c\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getOppUnitId(), (String)"\u5bf9\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)item.getSubjectCode(), (String)"\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        String subjectTitle = "";
        if (item.getSubjectOrient() == null) {
            String reportSystemId;
            if (item.getSystemId() == null && null != (reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(item.getSchemeId(), item.getDefaultPeriod()))) {
                item.setSystemId(reportSystemId);
            }
            if (null != item.getSystemId()) {
                ConsolidatedSubjectEO consolidatedSubjectEO = this.consolidatedSubjectService.getSubjectByCode(item.getSystemId(), item.getSubjectCode());
                Assert.isNotNull((Object)consolidatedSubjectEO, (String)("\u672a\u627e\u89c1\u79d1\u76ee:" + item.getSubjectCode()), (Object[])new Object[0]);
                item.setSubjectOrient(OrientEnum.valueOf((Integer)consolidatedSubjectEO.getOrient()));
                subjectTitle = consolidatedSubjectEO.getTitle();
            }
            Assert.isNotNull((Object)item.getSubjectOrient(), (String)"\u79d1\u76ee\u501f\u8d37\u65b9\u5411\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        }
        Assert.isNotNull((Object)item.getOrient(), (String)"\u6570\u636e\u501f\u8d37\u65b9\u5411\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Double d = amt = item.getOrient() == OrientEnum.D ? item.getOffSetDebit() : item.getOffSetCredit();
        if (null == item.getCreateTime()) {
            item.setCreateTime(new Date());
        }
        if (item.getOrgType() == null) {
            item.setOrgType("NONE");
        }
        if (item.getInputUnitId() == null) {
            item.setInputUnitId("NONE");
        }
        if ("NONE".equals(item.getInputUnitId())) {
            item.setOrgType("NONE");
        } else if ("NONE".equals(item.getOrgType())) {
            throw new BusinessRuntimeException("\u5f55\u5165\u5355\u4f4d\u6709\u503c\u65f6\uff0c\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a \u672c\u65b9\u5355\u4f4d\uff1a" + item.getUnitId() + " \u79d1\u76ee\uff1a" + subjectTitle + " \u91d1\u989d\uff1a" + amt);
        }
        switch (item.getOffSetSrcType()) {
            case BROUGHT_FORWARD_LOSS_GAIN: 
            case DEFERRED_INCOME_TAX: {
                if (!"NONE".equals(item.getInputUnitId())) break;
                throw new BusinessRuntimeException("\u5f55\u5165\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a \u672c\u65b9\u5355\u4f4d\uff1a" + item.getUnitId() + " \u79d1\u76ee\uff1a" + subjectTitle + " \u91d1\u989d\uff1a" + amt);
            }
        }
        if ("NONE".equals(item.getInputUnitId())) {
            Assert.isTrue((boolean)"NONE".equals(item.getOrgType()), (String)"\u5f55\u5165\u5355\u4f4d\u4e3a\u7a7a\u65f6\uff0corgType\u9700\u8981\u4e3a\u7a7a", (Object[])new Object[0]);
            if (item.getUnitId().equals(item.getOppUnitId())) {
                throw new BusinessRuntimeException("\u672c\u65b9\u5355\u4f4d\u548c\u5bf9\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u76f8\u540c\uff1a" + this.getTitle(item.getUnitId(), item.getDefaultPeriod()));
            }
        }
    }

    private String getTitle(String orgId, String defaultPeriod) {
        YearPeriodObject yp = new YearPeriodObject(null, defaultPeriod);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgId);
        if (org != null) {
            return org.getTitle() + "(" + orgId + ")";
        }
        tool = GcOrgPublicTool.getInstance((String)"MD_ORG_MANAGEMENT", (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        org = tool.getOrgByCode(orgId);
        if (org != null) {
            return org.getTitle() + "(" + orgId + ")";
        }
        org = GcOrgPublicTool.getInstance().getBaseOrgByCode(orgId);
        Assert.isNotNull((Object)org, (String)("\u627e\u4e0d\u5230\u6307\u5b9a\u5355\u4f4d\uff0c\u8bf7\u68c0\u67e5\u5355\u4f4d\u6570\u636e:" + orgId), (Object[])new Object[0]);
        return org.getTitle() + "(" + orgId + ")";
    }

    private AbstractUnionRule getRule(String id) {
        if (id == null) {
            return null;
        }
        if (this.ruleMap.containsKey(id)) {
            return this.ruleMap.get(id);
        }
        AbstractUnionRule ruleVO = this.unionRuleService.selectUnionRuleDTOById(id);
        this.ruleMap.put(id, ruleVO);
        return ruleVO;
    }

    @Override
    public ExportExcelSheet exportRySheet(String offsetSheet, QueryParamsVO queryParamsVO, Pagination<Map<String, Object>> map, Map<String, CellStyle> cellStyleMap, boolean templateExportFlag) {
        CellType[] cellTypes;
        CellStyle[] contentStyles;
        CellStyle[] headStyles;
        String[] keys;
        String[] titles;
        ExportExcelSheet exportExcelSheet;
        SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int rowIndex = 1;
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        ArrayList titleList = new ArrayList();
        ArrayList keyList = new ArrayList();
        if (offsetSheet.equals("offsetSheet")) {
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.offset"));
            titles = this.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.elmMode", "gc.calculate.adjustingentry.showoffset.gcBusinessType", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt", "gc.calculate.adjustingentry.showoffset.diff", "gc.calculate.adjustingentry.showoffset.memo"});
            keys = new String[]{"index", "ELMMODETITLE", "GCBUSINESSTYPE", "RULETITLE", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "OFFSETDEBIT", "OFFSETCREDIT", "DIFF", "MEMO"};
            headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headString, headAmt, headAmt, headAmt, headString};
            contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentAmt, contentString};
            cellTypes = new CellType[]{CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC, CellType.NUMERIC, CellType.STRING};
        } else if ("unOffsetSheet".equals(offsetSheet)) {
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(1), "\u672a\u62b5\u9500");
            titles = new String[]{"\u5e8f\u53f7", "\u5408\u5e76\u89c4\u5219", "\u672c\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d", "\u79d1\u76ee", "\u501f\u65b9\u91d1\u989d", "\u8d37\u65b9\u91d1\u989d", "\u63cf\u8ff0"};
            keys = new String[]{"index", "UNIONRULEID", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "DEBITVALUE", "CREDITVALUE", "MEMO"};
            headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headAmt, headAmt, headString};
            contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentString};
            cellTypes = new CellType[]{CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC, CellType.STRING};
        } else {
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(2), "\u672a\u62b5\u9500\uff08\u4e0a\u7ea7\uff09");
            titles = new String[]{"\u5e8f\u53f7", "\u5408\u5e76\u89c4\u5219", "\u672c\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d", "\u79d1\u76ee", "\u501f\u65b9\u91d1\u989d", "\u8d37\u65b9\u91d1\u989d", "\u63cf\u8ff0"};
            keys = new String[]{"index", "UNIONRULEID", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "DEBITVALUE", "CREDITVALUE", "MEMO"};
            headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headAmt, headAmt, headString};
            contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentString};
            cellTypes = new CellType[]{CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC, CellType.STRING};
        }
        Collections.addAll(titleList, titles);
        Collections.addAll(keyList, keys);
        List gcOffSetVchrItemDTOS = map.getContent();
        List otherShowColumns = queryParamsVO.getOtherShowColumns();
        if (!org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getOtherShowColumns())) {
            keyList.addAll(otherShowColumns);
            titleList.addAll(queryParamsVO.getOtherShowColumnTitles());
        }
        titles = new String[titleList.size()];
        titles = titleList.toArray(titles);
        keys = new String[keyList.size()];
        keys = keyList.toArray(keys);
        for (int i = 0; i < headStyles.length; ++i) {
            exportExcelSheet.getHeadCellStyleCache().put(i, headStyles[i]);
            exportExcelSheet.getContentCellStyleCache().put(i, contentStyles[i]);
            exportExcelSheet.getContentCellTypeCache().put(i, cellTypes[i]);
        }
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titles);
        if (templateExportFlag) {
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            return exportExcelSheet;
        }
        int mergeStart = -1;
        String curMercid = null;
        for (Map gcOffSetVchrItemDTO : gcOffSetVchrItemDTOS) {
            Object[] rowData = new Object[titleList.size()];
            Map stringObjectMap = gcOffSetVchrItemDTO;
            if (stringObjectMap.containsKey("UNIONRULETITLE")) {
                stringObjectMap.put("UNIONRULEID", stringObjectMap.get("UNIONRULETITLE"));
            } else if (stringObjectMap.containsKey("UNIONRULEID") && !StringUtils.isEmpty(stringObjectMap.get("UNIONRULEID"))) {
                AbstractUnionRule unionruleid = this.getRule((String)stringObjectMap.get("UNIONRULEID"));
                stringObjectMap.put("UNIONRULEID", null == unionruleid ? "" : unionruleid.getLocalizedName());
            }
            if (!stringObjectMap.containsKey("index")) {
                stringObjectMap.put("index", rowIndex);
            }
            for (int j = 0; j < keys.length; ++j) {
                Object valueObj = stringObjectMap.get(keys[j]);
                if (valueObj != null) {
                    if (keys[j].equals("OFFSETDEBIT") || keys[j].equals("OFFSETCREDIT") || keys[j].equals("DIFF") || keys[j].equals("DEBITVALUE") || keys[j].equals("CREDITVALUE")) {
                        String value = valueObj.toString();
                        if (value.length() == 0) continue;
                        rowData[j] = Double.valueOf(value.replace(",", ""));
                        continue;
                    }
                    if (valueObj instanceof Date) {
                        rowData[j] = exportDateFormat.format((Date)valueObj);
                        continue;
                    }
                    rowData[j] = valueObj.toString();
                    continue;
                }
                rowData[j] = null;
            }
            String rowMercid = (String)stringObjectMap.get("MRECID");
            if (null != rowMercid) {
                if (null == curMercid) {
                    curMercid = rowMercid.toString();
                    mergeStart = rowIndex;
                } else if (!curMercid.equals(rowMercid)) {
                    this.addMergedRegion(exportExcelSheet, mergeStart, rowIndex - 1, 0, 0);
                    this.addMergedRegion(exportExcelSheet, mergeStart, rowIndex - 1, 1, 1);
                    mergeStart = rowIndex;
                    curMercid = rowMercid.toString();
                } else if (rowIndex == gcOffSetVchrItemDTOS.size()) {
                    this.addMergedRegion(exportExcelSheet, mergeStart, rowIndex, 0, 0);
                    this.addMergedRegion(exportExcelSheet, mergeStart, rowIndex, 1, 1);
                }
            }
            if (FilterMethodEnum.UNITGROUP.equals((Object)queryParamsVO.getFilterMethod()) && !stringObjectMap.containsKey("ID")) {
                this.addMergedRegion(exportExcelSheet, rowIndex, rowIndex, 1, keys.length - 1);
            }
            rowDatas.add(rowData);
            ++rowIndex;
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    @Override
    public PaginationDto<GcOffSetVchrItemAdjustEO> listWithFullGroup(QueryParamsDTO queryParamsDTO) {
        Map filterCondition = queryParamsDTO.getFilterCondition();
        if (filterCondition != null) {
            queryParamsDTO.setGcBusinessTypeQueryRule(ConverterUtils.getAsString(filterCondition.get("gcBusinessTypeQueryRule")));
            if (filterCondition.get("gcbusinesstypecode") != null) {
                queryParamsDTO.setGcBusinessTypeCodes((List)filterCondition.get("gcbusinesstypecode"));
            }
            filterCondition.remove("gcbusinesstypecode");
            filterCondition.remove("gcBusinessTypeQueryRule");
        }
        PaginationDto page = new PaginationDto(null, Integer.valueOf(0), Integer.valueOf(queryParamsDTO.getPageNum()), Integer.valueOf(queryParamsDTO.getPageSize()));
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.fillMrecids(queryParamsDTO, Collections.emptySet(), mRecids);
        if (org.springframework.util.CollectionUtils.isEmpty(mRecids)) {
            page.setContent(new ArrayList());
            return page;
        }
        List<GcOffSetVchrItemAdjustEO> datas = this.listWithFullGroupByMrecids(queryParamsDTO, mRecids);
        page.setTotalElements(Integer.valueOf(totalCount));
        page.setContent(datas);
        return page;
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO queryParamsDTO) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("select ").append(SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)"record"));
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
        sql.append((CharSequence)whereSql);
        return this.getOffsetItemAdjustDao().selectEntity(sql.toString(), params);
    }

    @Override
    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> listWithOnlyItemsByRy(QueryParamsDTO queryParamsDTO) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("select ").append(SqlUtils.getColumnsSqlByEntity(ArbitrarilyMergeOffSetVchrItemAdjustEO.class, (String)"record"));
        sql.append(" from ").append("GC_RY_OFFSETVCHRITEM").append(" record \n");
        sql.append("  join GC_ORGTEMPORARY_AM bfUnitTable on (record.unitid = bfUnitTable.code) and bfUnitTable.batchId = ? \n  join GC_ORGTEMPORARY_AM dfUnitTable on (record.oppunitid = dfUnitTable.code) and dfUnitTable.batchId = ? \n");
        params.add(queryParamsDTO.getOrgBatchId());
        params.add(queryParamsDTO.getOrgBatchId());
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(")\n");
        whereSql.append(" and record.UNITVERSION in('NONE',?) \n");
        params.add(queryParamsDTO.getOrgType());
        whereSql.append("and record.systemid = ? ");
        params.add(queryParamsDTO.getSystemId());
        whereSql.append("and record.DATATIME = ? ");
        params.add(queryParamsDTO.getPeriodStr());
        whereSql.append("and record.offsetCurr = ? ");
        params.add(queryParamsDTO.getCurrencyUpperCase());
        whereSql.append("and (record.disableFlag<> ? or record.disableFlag is null ) ");
        params.add(1);
        sql.append((CharSequence)whereSql);
        return this.ryAdjustingEntryDao.selectEntity(sql.toString(), params);
    }

    @Override
    public Set<String> deleteOffsetEntrys(QueryParamsVO queryParamsVO) {
        Assert.isNotNull((Object)queryParamsVO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsVO.getAcctYear(), (String)"\u5e74\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        HashSet<String> srcOffsetGroupIdResults = new HashSet<String>();
        HashSet<String> mrecids = new HashSet<String>();
        this.ryAdjustingEntryDao.queryRyMrecids(queryParamsVO, srcOffsetGroupIdResults, mrecids);
        if (org.springframework.util.CollectionUtils.isEmpty(mrecids)) {
            return mrecids;
        }
        this.ryAdjustingEntryDao.deleteRyByMrecids(new ArrayList<String>(mrecids));
        this.logger.info("\u4efb\u610f\u5408\u5e76-\u5220\u9664\u62b5\u9500\u5206\u5f55\u7684\u6761\u6570\uff1a" + mrecids.size());
        return mrecids;
    }

    @Override
    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> srcOffsetGroupIds, Integer offSetSrcType, GcTaskBaseArguments baseArguments) {
        this.ryAdjustingEntryDao.deleteByOffsetGroupIdsAndSrcType(srcOffsetGroupIds, offSetSrcType, baseArguments);
    }

    public List<GcOffSetVchrItemAdjustEO> listWithFullGroupByMrecids(QueryParamsDTO queryParamsDTO, Set<String> mRecids) {
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append(SqlUtils.getColumnsSqlByEntity(ArbitrarilyMergeOffSetVchrItemAdjustEO.class, (String)"record"));
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_RY_OFFSETVCHRITEM").append("  record\n");
        sql.append("where ").append(SqlUtils.getConditionOfIdsUseOr(mRecids, (String)" record.mrecid")).append("\n");
        sql.append(" and record.UNITVERSION in('" + GCOrgTypeEnum.NONE.getCode() + "', ? ) \n");
        if (DimensionUtils.isExistAdjust((String)queryParamsDTO.getTaskId())) {
            sql.append(" and record.ADJUST = ").append("'").append(queryParamsDTO.getSelectAdjustCode()).append("'");
        }
        sql.append("order by record.mrecid desc\n");
        List ryAdjustEOS = this.ryAdjustingEntryDao.selectEntity(sql.toString(), new Object[]{queryParamsDTO.getOrgType()});
        ArrayList<GcOffSetVchrItemAdjustEO> adjustEOS = new ArrayList<GcOffSetVchrItemAdjustEO>();
        ryAdjustEOS.stream().forEach(item -> {
            GcOffSetVchrItemAdjustEO adjustEO = new GcOffSetVchrItemAdjustEO();
            BeanUtils.copyProperties(item, adjustEO);
            adjustEO.setOffSetCredit(ConverterUtils.getAsDouble((Object)item.getFieldValue("OFFSET_CREDIT_" + item.getOffSetCurr())));
            adjustEO.setOffSetDebit(ConverterUtils.getAsDouble((Object)item.getFieldValue("OFFSET_DEBIT_" + item.getOffSetCurr())));
            adjustEO.setDiffc(ConverterUtils.getAsDouble((Object)item.getFieldValue("DIFFC_" + item.getOffSetCurr())));
            adjustEO.setDiffd(ConverterUtils.getAsDouble((Object)item.getFieldValue("DIFFD_" + item.getOffSetCurr())));
            adjustEOS.add(adjustEO);
        });
        return adjustEOS;
    }

    private int fillMrecids(QueryParamsDTO queryParamsDTO, Set<String> srcOffsetGroupIdResults, Set<String> mrecidSet) {
        String sql;
        if (null == mrecidSet) {
            mrecidSet = new HashSet<String>();
        }
        ArrayList<Object> params = new ArrayList<Object>();
        String queryFields = "record.mrecid";
        if (!org.springframework.util.CollectionUtils.isEmpty(srcOffsetGroupIdResults)) {
            queryFields = "record.mrecid,record.elmMode,record.srcOffsetGroupId ";
        }
        if (StringUtils.isEmpty(sql = this.getFillMrecidSql(queryParamsDTO, queryFields, params))) {
            return 0;
        }
        String countSql = String.format("select count(*) from ( %1$s )  t", sql);
        int count = this.ryAdjustingEntryDao.count(countSql, params);
        if (count < 1) {
            return 0;
        }
        int begin = -1;
        int range = -1;
        int pageNum = queryParamsDTO.getPageNum();
        int pageSize = queryParamsDTO.getPageSize();
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            range = pageNum * pageSize;
        }
        List rs = this.ryAdjustingEntryDao.selectMapByPaging(sql, begin, range, params);
        for (Map d : rs) {
            String value;
            int elmMode;
            mrecidSet.add(String.valueOf(d.get("MRECID")));
            if (d.size() <= 2 || (elmMode = ((Integer)d.get("ELMMODE")).intValue()) == OffsetElmModeEnum.WRITE_OFF_ITEM.getValue() || null == (value = String.valueOf(d.get("SRCOFFSETGROUPID")))) continue;
            srcOffsetGroupIdResults.add(value);
        }
        return count;
    }

    private String getFillMrecidSql(QueryParamsDTO queryParamsDTO, String queryFields, List<Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(queryFields);
        sql.append(" from ").append("GC_RY_OFFSETVCHRITEM").append(" record \n");
        sql.append("  join GC_ORGTEMPORARY_AM bfUnitTable on (record.unitid = bfUnitTable.code) and bfUnitTable.batchId = ? \n  join GC_ORGTEMPORARY_AM dfUnitTable on (record.oppunitid = dfUnitTable.code) and dfUnitTable.batchId = ? \n");
        params.add(queryParamsDTO.getOrgBatchId());
        params.add(queryParamsDTO.getOrgBatchId());
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsDTO.getOrgComSupLength()).append(")\n");
        whereSql.append(" and record.UNITVERSION in('NONE',?) \n");
        params.add(queryParamsDTO.getOrgType());
        whereSql.append("and record.systemid = ? ");
        params.add(queryParamsDTO.getSystemId());
        whereSql.append("and record.DATATIME = ? ");
        params.add(queryParamsDTO.getPeriodStr());
        whereSql.append("and record.offsetCurr = ? ");
        if (!org.springframework.util.CollectionUtils.isEmpty(queryParamsDTO.getOffSetSrcTypes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble((Collection)queryParamsDTO.getOffSetSrcTypes(), (String)"record.OffSetSrcType"));
        }
        params.add(queryParamsDTO.getCurrencyUpperCase());
        whereSql.append("and (record.disableFlag<> ? or record.disableFlag is null ) ");
        params.add(1);
        sql.append((CharSequence)whereSql);
        sql.append(" group by record.mrecid \n");
        sql.append(" order by record.mrecid desc");
        String sqlStr = this.processingSqlByGcBusinessTypeQueryRule(sql.toString(), queryFields, queryParamsDTO);
        this.logger.debug(sqlStr + "\n");
        return sqlStr;
    }

    private String processingSqlByGcBusinessTypeQueryRule(String sql, String queryFields, QueryParamsDTO queryParamsDto) {
        String operator;
        if (CollectionUtils.isEmpty((Collection)queryParamsDto.getGcBusinessTypeCodes())) {
            return sql;
        }
        GcBusinessTypeQueryRuleEnum enumByCode = GcBusinessTypeQueryRuleEnum.getEnumByCode((String)queryParamsDto.getGcBusinessTypeQueryRule());
        if (enumByCode == null) {
            return sql;
        }
        switch (enumByCode) {
            case ALL: {
                return sql;
            }
            case EQ: {
                operator = " = ";
                break;
            }
            case ACROSS: {
                operator = " <> ";
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u672a\u8bc6\u522b\u7684\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u67e5\u8be2\u89c4\u5219\uff1a" + enumByCode);
            }
        }
        StringBuffer processingSql = new StringBuffer();
        processingSql.append("select ").append(queryFields).append(" from ").append("GC_OFFSETVCHRITEM").append(" record \n");
        processingSql.append(" where mrecid in (select mrecid  from ").append("GC_OFFSETVCHRITEM").append(" t \n");
        processingSql.append("                   where t.mrecid in ( select mrecid  from (").append(sql).append(") temp )      \n");
        processingSql.append("                   group by t.mrecid     \n");
        processingSql.append("                   having max(gcbusinesstypecode)").append(operator).append("min(gcbusinesstypecode) \n");
        processingSql.append("                  )        \n");
        processingSql.append(" group by ").append(queryFields).append("\n");
        processingSql.append(" order by record.mrecid desc\n");
        return processingSql.toString();
    }

    private EntNativeSqlDefaultDao<GcOffSetVchrItemAdjustEO> getOffsetItemAdjustDao() {
        return EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
    }

    private String[] msg(String[] keys) {
        String[] msgs = new String[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            msgs[i] = GcI18nUtil.getMessage((String)keys[i]);
        }
        return msgs;
    }

    private void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    private ArbitrarilyMergeOffSetVchrItemAdjustEO convertOffset2RyEO(GcOffSetVchrItemAdjustEO offSetEO) {
        ArbitrarilyMergeOffSetVchrItemAdjustEO eo = new ArbitrarilyMergeOffSetVchrItemAdjustEO();
        BeanUtils.copyProperties(offSetEO, (Object)eo);
        eo.setOffSetCreditCNY(offSetEO.getOffSetCredit());
        eo.setOffSetDebitCNY(offSetEO.getOffSetDebit());
        eo.setCreditCNY(offSetEO.getOffSetCredit());
        eo.setDebitCNY(offSetEO.getOffSetDebit());
        eo.setDiffcCNY(offSetEO.getDiffc());
        eo.setDiffdCNY(offSetEO.getDiffd());
        eo.getFields().clear();
        eo.getFields().putAll(offSetEO.getFields());
        eo.setUnitVersion(offSetEO.getOrgType());
        return eo;
    }
}

