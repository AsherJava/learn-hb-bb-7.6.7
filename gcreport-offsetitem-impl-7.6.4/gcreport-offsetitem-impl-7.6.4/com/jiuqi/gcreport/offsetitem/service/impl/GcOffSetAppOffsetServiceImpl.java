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
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.task.SumTabTaskImpl;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class GcOffSetAppOffsetServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements GcOffSetAppOffsetService {
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcInputAdjustService inputAdjustService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private SumTabTaskImpl sumTabTask;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private UnionRuleDao unionRuleDao;
    @Autowired
    private GcOffSetItemAdjustCoreDao coreDao;
    @Autowired
    private GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;
    @Autowired(required=false)
    private GcInputDataOffsetItemService gcInputDataOffsetItemService;
    private final Logger logger = LoggerFactory.getLogger(GcOffSetAppOffsetServiceImpl.class);

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Collection<GcOffSetVchrDTO> batchSave(Collection<GcOffSetVchrDTO> offSetItemDTOs) {
        offSetItemDTOs.forEach(offSetItemDTO -> this.inputAdjustService.exeConsFormulaCalcOneGroup((GcOffSetVchrDTO)offSetItemDTO));
        return this.offsetCoreService.batchSave(offSetItemDTOs);
    }

    @Override
    public Pagination<Map<String, Object>> listSumTabRecords(QueryParamsVO queryParamsVO) {
        if (!this.hasOrahAuth(queryParamsVO).booleanValue()) {
            throw new BusinessRuntimeException("\u65e0\u6743\u9650\u67e5\u770b\u6570\u636e");
        }
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        Assert.isNotNull((Object)systemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        queryParamsVO.setSystemId(systemId);
        List subjectVos = (List)queryParamsVO.getFilterCondition().get("subjectVo");
        List allSubjects = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        Integer subjectSummaryLevel = this.optionService.getOptionData(systemId).getSubjectSummaryLevel();
        Map<String, String> subjectCodeToSecSubjectCodeMap = this.getSubjectCodeToSecSubjectCodeMap(systemId, subjectSummaryLevel == null ? 2 : subjectSummaryLevel);
        Map<String, List<String>> subjectCodeToParentCodesMap = this.sumTabTask.getSubjectCodeToChildCodesMap(allSubjects);
        if (subjectVos != null && subjectVos.size() != 0) {
            return this.sumTabTask.getSumTabDataForSelectSubject(subjectVos, systemId, queryParamsVO, allSubjects, subjectCodeToParentCodesMap);
        }
        return this.sumTabTask.getSumTabDataForUnSelectSubject(queryParamsVO, systemId, subjectCodeToSecSubjectCodeMap, allSubjects);
    }

    private Map<String, String> getSubjectCodeToSecSubjectCodeMap(String systemId, int level) {
        List allSubjectList = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        HashMap<String, String> result = new HashMap<String, String>();
        for (ConsolidatedSubjectEO consolidatedSubjectEO : allSubjectList) {
            String[] split;
            String parents = consolidatedSubjectEO.getParents();
            if (StringUtils.isEmpty(parents) || (split = parents.split("\\/")).length < level) continue;
            result.put(consolidatedSubjectEO.getCode(), split[level - 1]);
        }
        return result;
    }

    @Override
    public GcOffSetVchrDTO save(GcOffSetVchrDTO dto) {
        this.inputAdjustService.exeConsFormulaCalcOneGroup(dto);
        this.offsetCoreService.save(dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDelete(Collection<String> mrecids, String taskId, Integer acctYear, Integer acctPeriod, String orgTypeId, String currencyCode) {
        if (mrecids == null) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u5220\u9664\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f[\u62b5\u9500\u5206\u5f55\u5206\u7ec4ID\u53c2\u6570\u4e0d\u5141\u8bb8\u4e3a\u7a7a]\u3002");
        }
        try {
            this.doLogByMrecIdListAndTitle(mrecids, "\u53d6\u6d88\u62b5\u9500");
            Collection srcOffsetGroupIds = this.offsetCoreService.listOffsetGroupIdsByMrecid(mrecids);
            this.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
            this.offsetCoreService.deleteByMrecId(mrecids);
        }
        catch (Exception e) {
            e.printStackTrace();
            String message = String.format("\u5220\u9664\u64cd\u4f5c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:%s", e.getMessage());
            throw new BusinessRuntimeException(message);
        }
    }

    private void doLogByMrecIdListAndTitle(Collection<String> mrecids, String title) {
        try {
            List gcOffSetVchrItemAdjustEOS = this.offsetCoreService.listWithFullGroupByMrecids(mrecids);
            if (CollectionUtils.isEmpty(gcOffSetVchrItemAdjustEOS)) {
                return;
            }
            String taskTitle = this.iRunTimeViewController.queryTaskDefine(((GcOffSetVchrItemAdjustEO)gcOffSetVchrItemAdjustEOS.get(0)).getTaskId()).getTitle();
            String defaultPeriod = ((GcOffSetVchrItemAdjustEO)gcOffSetVchrItemAdjustEOS.get(0)).getDefaultPeriod();
            HashSet<String> mrecIdSet = new HashSet<String>();
            StringBuilder message = new StringBuilder(title + mrecids.size() + "\u7ec4\uff1a\n");
            for (GcOffSetVchrItemAdjustEO eo : gcOffSetVchrItemAdjustEOS) {
                if (mrecIdSet.contains(eo.getmRecid())) continue;
                mrecIdSet.add(eo.getmRecid());
                AbstractUnionRule abstractUnionRule = this.unionRuleService.selectUnionRuleDTOById(eo.getRuleId());
                String ruleTitle = abstractUnionRule != null ? abstractUnionRule.getLocalizedName() : eo.getRuleId();
                message.append("\u4efb\u52a1-").append(taskTitle).append("\uff1b\u65f6\u671f-").append(defaultPeriod).append("\uff1b\u5408\u5e76\u89c4\u5219-").append(ruleTitle).append("\uff1b\u672c\u65b9\u5355\u4f4d-").append(eo.getUnitId()).append("\uff1b\u5bf9\u65b9\u5355\u4f4d-").append(eo.getOppUnitId()).append("\n");
            }
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)(title + "-\u4efb\u52a1" + taskTitle + "-\u65f6\u671f" + defaultPeriod), (String)message.toString());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u62b5\u9500\u5206\u5f55\u65e5\u5fd7\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    @Override
    public void cancelInputOffsetByOffsetGroupId(Collection<String> srcOffsetGroupIds, String taskId) {
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return;
        }
        if (Objects.nonNull(this.gcInputDataOffsetItemService)) {
            this.gcInputDataOffsetItemService.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
        }
    }

    @Override
    public GcOrgCacheVO getHBOrgByCE(String orgType, String periodStr, String orgCode) {
        String[] parents;
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO ceOrgCacheVO = orgTool.getOrgByCode(orgCode);
        if (!GcOrgKindEnum.DIFFERENCE.equals((Object)ceOrgCacheVO.getOrgKind())) {
            return null;
        }
        for (String parentCode : parents = ceOrgCacheVO.getParents()) {
            GcOrgCacheVO parentVO = orgTool.getOrgByCode(parentCode);
            if (parentVO == null || !orgCode.equals(parentVO.getDiffUnitId())) continue;
            return parentVO;
        }
        return null;
    }

    @Override
    public GcOffSetVchrItemVO convertDTO2VO(GcOffSetVchrItemDTO itemDTO) {
        return OffsetConvertUtil.getInstance().convertDTO2VO(itemDTO);
    }

    @Override
    public GcOffSetVchrItemDTO convertEO2DTO(GcOffSetVchrItemAdjustEO itemEO) {
        return OffsetConvertUtil.getInstance().convertEO2DTO(itemEO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Pagination<Map<String, Object>> listOffsetEntrys(QueryParamsVO queryParamsVO, boolean setBoundSubjectCodes) {
        Pagination offsetEntry;
        if (!queryParamsVO.isWhenOneUnitIsAllChild()) {
            this.handleUnitAndOppUnitParam(queryParamsVO);
        }
        try {
            Pagination offsetPagination = new Pagination();
            BeanUtils.copyProperties(this.offsetCoreService.listWithFullGroup(this.convertQueryVO2DTO(queryParamsVO)), offsetPagination);
            offsetEntry = this.assembleOffsetEntry(offsetPagination, queryParamsVO);
        }
        finally {
            if (!CollectionUtils.isEmpty(queryParamsVO.getTempGroupIdList())) {
                IdTemporaryTableUtils.deteteByGroupIds((Collection)queryParamsVO.getTempGroupIdList());
            }
        }
        if (setBoundSubjectCodes) {
            this.setBoundSubjectCodes((Pagination<Map<String, Object>>)offsetEntry, queryParamsVO);
        }
        return offsetEntry;
    }

    public void handleUnitAndOppUnitParam(QueryParamsVO queryParamsVO) {
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

    @Override
    public Pagination<Map<String, Object>> listOffsetEntrys(QueryParamsVO queryParamsVO) {
        GcOffsetItemUtils.logOffsetEntryFilterCondition(queryParamsVO, "\u672c\u7ea7\u5df2\u62b5\u9500");
        if (!this.hasOrahAuth(queryParamsVO).booleanValue()) {
            throw new BusinessRuntimeException("\u65e0\u6743\u9650\u67e5\u770b\u6570\u636e");
        }
        return this.listOffsetEntrys(queryParamsVO, true);
    }

    private Boolean hasOrahAuth(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Set orgCodes = orgTool.listAllOrgByParentIdContainsSelf(null).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        HashSet<String> ids = new HashSet<String>();
        if (StringUtils.hasLength(queryParamsVO.getOrgId())) {
            ids.add(queryParamsVO.getOrgId());
        }
        if (queryParamsVO.getUnitIdList() != null) {
            ids.addAll(queryParamsVO.getUnitIdList());
        }
        return ids.stream().allMatch(orgCodes::contains);
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
    public Set<String> deleteAllOffsetEntrys(QueryParamsVO queryParamsVO) {
        try {
            QueryParamsDTO queryParamsDto = this.convertQueryVO2DTO(queryParamsVO);
            HashSet srcOffsetGroupIdResults = new HashSet();
            HashSet<String> mrecids = new HashSet<String>();
            this.coreDao.fillMrecids(queryParamsDto, srcOffsetGroupIdResults, mrecids);
            GcTaskBaseArguments gcTaskBaseArguments = this.getGcTaskBaseArguments(queryParamsVO.getTaskId(), queryParamsVO.getAcctYear(), queryParamsVO.getAcctPeriod(), null, queryParamsVO.getCurrency());
            Collection srcOffsetGroupIds = this.offsetCoreService.listOffsetGroupIdsByMrecid(mrecids);
            if (!CollectionUtils.isEmpty(srcOffsetGroupIds) && Objects.nonNull(this.gcInputDataOffsetItemService)) {
                this.gcInputDataOffsetItemService.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, queryParamsVO.getTaskId());
                this.offsetCoreService.delete(queryParamsDto);
            }
            return mrecids;
        }
        catch (Exception e) {
            String message = String.format("\u5220\u9664\u64cd\u4f5c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:%s", e.getMessage());
            this.logger.error(message, e);
            throw new BusinessRuntimeException(message);
        }
    }

    @Override
    public void cancelRuleData(String taskId, String systemId, String periodStr, String ruleId, List<String> subjectCode, String orgType, String selectAdjustCode) {
        UnionRuleEO rule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)ruleId));
        if (Objects.isNull(rule)) {
            throw new BusinessRuntimeException("\u89c4\u5219\u4e0d\u5b58\u5728\u6216\u5df2\u88ab\u5220\u9664\uff0c\u8bf7\u91cd\u8bd5");
        }
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Integer acctYear = periodUtil.getYear();
        Integer acctPeriod = periodUtil.getPeriod();
        Set subjectAllChildrenCode = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(subjectCode, systemId);
        QueryParamsDTO dto = new QueryParamsDTO();
        dto.setSystemId(systemId);
        dto.setPeriodStr(periodStr);
        dto.setOrgType(orgType);
        Collection mrecids = this.offsetCoreService.listMrecidsByRuleId(ruleId, subjectAllChildrenCode, dto);
        if (!CollectionUtils.isEmpty(mrecids)) {
            this.batchDelete(mrecids, taskId, acctYear, acctPeriod, null, null);
        }
        if (Objects.nonNull(this.gcInputDataOffsetItemService)) {
            this.gcInputDataOffsetItemService.mappingRule(taskId, systemId, ruleId, periodStr, selectAdjustCode);
        }
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = this.consolidatedSystemService.getConsolidatedSystemEO(systemId);
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u53d6\u6d88\u5206\u5f55-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + rule.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)"");
    }

    @Override
    public Set<String> deleteOffsetEntrys(QueryParamsVO queryParamsVO) {
        return this.offsetCoreService.delete(this.convertQueryVO2DTO(queryParamsVO));
    }

    @Override
    public List<DesignFieldDefineVO> listOffsetColumnSelects() {
        String[] notColumnSelectPartStr = new String[]{"ID", "MRECID", "ELMMODE", "RECVER", "TASKID", "SCHEMEID", "ACCTYEAR", "ACCTPERIOD", "RULEID", "UNITID", "OPPUNITID", "SUBJECTCODE", "MEMO", "CREATETIME", "INPUTUNITID", "GCBUSINESSTYPECODE", "ORIENT", "SRCOFFSETGROUPID"};
        HashSet<String> notColumnSelectPart = new HashSet<String>();
        for (String code : notColumnSelectPartStr) {
            notColumnSelectPart.add(code);
        }
        return this.gcInputDataOffsetItemService.getAllFieldsByTableName("GC_OFFSETVCHRITEM", notColumnSelectPart);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listOffsetRecordsByWhere(String[] columnNamesInDB, Object[] values) {
        return this.offsetCoreService.listByWhere(columnNamesInDB, values);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listFairValueFuncRecords(QueryParamsVO queryParamsVO) {
        List<String> orgTypes = Arrays.asList("NONE", queryParamsVO.getOrgType());
        if (StringUtils.isEmpty(queryParamsVO.getSystemId())) {
            String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            queryParamsVO.setSystemId(systemId);
        }
        ArrayList<String> columnNamesList = new ArrayList<String>(Arrays.asList("SYSTEMID", "DATATIME", "offsetCurr", "ruleId", "MD_GCORGTYPE"));
        ArrayList<Object> columnValueList = new ArrayList<Object>(Arrays.asList(queryParamsVO.getSystemId(), queryParamsVO.getPeriodStr(), queryParamsVO.getCurrency(), queryParamsVO.getRules(), orgTypes));
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            columnNamesList.add("ADJUST");
            columnValueList.add(queryParamsVO.getSelectAdjustCode());
        }
        return this.offsetCoreService.listByWhere(columnNamesList.toArray(new String[0]), columnValueList.toArray());
    }

    @Override
    public List<Map<String, Object>> convertAdjustData2View(List<GcOffSetVchrItemAdjustEO> adjustEOS) {
        if (CollectionUtils.isEmpty(adjustEOS)) {
            return Collections.emptyList();
        }
        HashMap unitId2Title = new HashMap();
        HashMap ruleId2TitleCache = new HashMap();
        HashMap subject2TitleCache = new HashMap();
        HashMap businessTypeCode2TitleCache = new HashMap();
        String systemId = this.consolidatedTaskService.getSystemIdByTaskId(adjustEOS.get(0).getTaskId(), adjustEOS.get(0).getDefaultPeriod());
        List<String> otherShowColumns = this.getOtherShowColumns("GC_OFFSETVCHRITEM", systemId);
        Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherShowColumns);
        Map numberFieldCode2Decimal = this.getUnSysNumberField2Decimal(systemId);
        for (GcOffSetVchrItemAdjustEO recordEO : adjustEOS) {
            Map record = recordEO.getFields();
            record.put("UNITTITLE", this.getUnitTitle((String)record.get("UNITID"), unitId2Title));
            record.put("OPPUNITTITLE", this.getUnitTitle((String)record.get("OPPUNITID"), unitId2Title));
            Double diffD = recordEO.getDiffd();
            Double diffC = recordEO.getDiffc();
            record.put("DIFF", NumberUtils.doubleToString((double)NumberUtils.sum((Double)diffD, (Double)diffC)));
            this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            this.setRuleTitle(record, ruleId2TitleCache);
            this.setBusinessTypeCodeTitle(record, businessTypeCode2TitleCache);
            Integer elmMode = ConverterUtils.getAsInteger(record.get("ELMMODE"));
            if (null == elmMode) {
                elmMode = 0;
            }
            record.put("ELMMODETITLE", OffsetElmModeEnum.getElmModeTitle((Integer)elmMode));
            if (record.containsKey("CHKSTATE") && record.get("CHKSTATE") != null) {
                String chkState = CheckStateEnum.getTitleForCode((String)record.get("CHKSTATE").toString());
                record.put("CHKSTATE", chkState);
                record.put("CHKSTATETITLE", chkState);
            }
            this.formatOtherShowNumberField(record, numberFieldCode2Decimal);
        }
        boolean showDictCode = "1".equals(this.optionService.getOptionData(systemId).getShowDictCode());
        List<Map<String, Object>> records = adjustEOS.stream().map(temp -> {
            Map<String, Object> record = this.setOffsetViewProps((GcOffSetVchrItemAdjustEO)temp);
            this.setOtherShowColumnDictTitle(record, otherShowColumns, fieldCode2DictTableMap, showDictCode);
            if (temp.getModifyTime() != null) {
                record.put("MODIFYTIME", DateUtils.format((Date)temp.getModifyTime(), (String)"yyyy-MM-dd HH:mm:ss"));
            }
            return record;
        }).collect(Collectors.toList());
        return records;
    }

    private Set<String> getNumberColumnSetByColumnCodes(Collection<String> otherColumnCodes, List<DesignFieldDefineVO> designFieldDefineVOS) {
        Map key2IdentityMap = designFieldDefineVOS.stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, Function.identity(), (o1, o2) -> o1));
        HashSet<String> numberColumnSet = new HashSet<String>();
        for (String column : otherColumnCodes) {
            DesignFieldDefineVO designFieldDefineVO;
            if (!key2IdentityMap.containsKey(column) || !ColumnModelType.BIGDECIMAL.equals((Object)(designFieldDefineVO = (DesignFieldDefineVO)key2IdentityMap.get(column)).getType()) && !ColumnModelType.DOUBLE.equals((Object)designFieldDefineVO.getType())) continue;
            numberColumnSet.add(column);
        }
        return numberColumnSet;
    }

    private Map<String, Object> setOffsetViewProps(GcOffSetVchrItemAdjustEO temp) {
        Map record = temp.getFields();
        Double offSetCredit = temp.getOffSetCredit();
        Double offSetDebit = temp.getOffSetDebit();
        if (offSetDebit != null && offSetDebit != 0.0) {
            record.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)offSetDebit));
            record.put("OFFSETCREDIT", "");
        } else if (offSetCredit != null && offSetCredit != 0.0) {
            record.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)offSetCredit));
            record.put("OFFSETDEBIT", "");
        } else if (Integer.valueOf(OrientEnum.C.getValue()).equals(record.get("SUBJECTORIENT"))) {
            record.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)offSetDebit));
            record.put("OFFSETCREDIT", "");
        } else {
            record.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)offSetCredit));
            record.put("OFFSETDEBIT", "");
        }
        return record;
    }

    @Override
    public List<GcOrgCacheVO> getOffsetOrgData(QueryParamsVO queryParamsVO) {
        LinkedList records = new LinkedList();
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        if (Objects.nonNull(this.gcInputDataOffsetItemService)) {
            if ("NOT_OFFSETPAGE".equals(queryParamsVO.getTabName())) {
                records.addAll(this.gcInputDataOffsetItemService.queryUnOffsetRecords(queryParamsVO, false).getContent());
            } else if ("NOT_OFFSET_PARENTPAGE".equals(queryParamsVO.getTabName())) {
                records.addAll(this.gcInputDataOffsetItemService.queryUnOffsetRecords(queryParamsVO, true).getContent());
            }
        } else {
            records.addAll(this.offsetCoreService.listWithFullGroup(this.convertQueryVO2DTO(queryParamsVO)).getContent());
        }
        if (records.isEmpty()) {
            return null;
        }
        HashSet<String> oppUnitset = new HashSet<String>();
        for (Map record : records) {
            oppUnitset.add((String)record.get("OPPUNITID"));
            oppUnitset.add((String)record.get("UNITID"));
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashSet<String> parentCodeSet = new HashSet<String>();
        ArrayList<GcOrgCacheVO> orgList = new ArrayList<GcOrgCacheVO>();
        for (String oppUnit : oppUnitset) {
            GcOrgCacheVO gcOrgCacheVO = orgTool.getOrgByCode(oppUnit);
            if (gcOrgCacheVO == null) {
                throw new RuntimeException("\u5bf9\u65b9\u5355\u4f4d" + oppUnit + "\u6ca1\u6709\u5728\u7ec4\u7ec7\u673a\u6784\u6811\u4e2d\u627e\u5230");
            }
            parentCodeSet.addAll(Arrays.asList(gcOrgCacheVO.getParents()));
        }
        for (String parentCode : parentCodeSet) {
            GcOrgCacheVO parentOrgCache = orgTool.getOrgByCode(parentCode);
            if (parentOrgCache == null) continue;
            orgList.add(parentOrgCache);
        }
        return orgTool.collectionToTree(orgList);
    }

    @Override
    public List<GcOrgCacheVO> getMergeUnitOrgData(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List unitIdList = queryParamsVO.getUnitIdList();
        String unitId = unitIdList == null ? null : (String)unitIdList.get(0);
        GcOrgCacheVO gcOrgCacheVO = orgTool.getOrgByID(unitId);
        ArrayList<GcOrgCacheVO> orgList = new ArrayList<GcOrgCacheVO>();
        for (String parentId : gcOrgCacheVO.getParents()) {
            if (parentId.equals(unitId)) continue;
            orgList.add(orgTool.getOrgByID(parentId));
        }
        return orgTool.collectionToTree(orgList);
    }

    @Override
    public List<GcOffSetVchrItemVO> writeOffShow(@PathVariable(value="mrecids") @RequestBody List<String> mrecids, @RequestBody QueryParamsVO queryParamsVO) {
        ArrayList<GcOffSetVchrItemVO> result = new ArrayList<GcOffSetVchrItemVO>();
        List offSetVchrItemAdjusts = this.offsetCoreService.listWithFullGroupByMrecids(mrecids);
        for (GcOffSetVchrItemAdjustEO eo : offSetVchrItemAdjusts) {
            GcOffSetVchrItemDTO dto = this.convertEO2DTO(eo);
            dto.setUnitVersion(queryParamsVO.getOrgType());
            dto.setDefaultPeriod(queryParamsVO.getPeriodStr());
            dto.setOrient(eo.getOrient() == null ? dto.getOrient() : OrientEnum.valueOf((Integer)eo.getOrient()));
            GcOffSetVchrItemVO vo = this.convertDTO2VO(dto);
            AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)vo.getRuleId());
            if (null != rule) {
                vo.setRuleTitle(rule.getLocalizedName());
                vo.setRuleParentId(rule.getParentId());
            }
            vo.setDebit(vo.getOffSetDebit());
            vo.setCredit(vo.getOffSetCredit());
            if (OrientEnum.D.getValue().equals(eo.getOrient()) && vo.getDebit() == null) {
                vo.setDebit(new Double(0.0));
            }
            if (OrientEnum.C.getValue().equals(eo.getOrient()) && vo.getCredit() == null) {
                vo.setCredit(new Double(0.0));
            }
            result.add(vo);
        }
        return result;
    }

    @Override
    public void writeOffSave(List<String> mrecids, @RequestBody QueryParamsVO queryParamsVO) {
        this.offsetCoreService.deleteByOffsetGroupIdsAndSrcType(mrecids, OffSetSrcTypeEnum.WRITE_OFF.getSrcTypeValue(), (GcTaskBaseArguments)this.convertQueryVO2DTO(queryParamsVO));
        for (String mrecid : mrecids) {
            ArrayList<String> oneMrecid = new ArrayList<String>();
            oneMrecid.add(mrecid);
            List itemEOs = this.offsetCoreService.listWithFullGroupByMrecids(oneMrecid);
            GcOffSetVchrDTO offSetItemDTO = new GcOffSetVchrDTO();
            List itemDTOs = itemEOs.stream().map(itemEO -> {
                GcOffSetVchrItemDTO itemDTO = this.convertEO2DTO((GcOffSetVchrItemAdjustEO)itemEO);
                itemDTO.setSrcId(itemDTO.getId());
                itemDTO.setOriginSrcId(itemDTO.getSrcId());
                Assert.isNotNull((Object)itemDTO.getSrcOffsetGroupId(), (String)("\u539f\u59cb\u62b5\u9500\u5206\u5f55srcOffsetGroupId\u5b57\u6bb5\u7f3a\u5931:" + itemDTO.getSrcOffsetGroupId()), (Object[])new Object[0]);
                itemDTO.setSrcOffsetGroupId(itemDTO.getmRecid());
                itemDTO.setOriginSrcOffsetGroupId(itemDTO.getSrcOffsetGroupId());
                itemDTO.setId(UUIDOrderUtils.newUUIDStr());
                itemDTO.setOffSetCredit(Double.valueOf(-itemDTO.getOffSetCredit().doubleValue()));
                itemDTO.setOffSetDebit(Double.valueOf(-itemDTO.getOffSetDebit().doubleValue()));
                itemDTO.setDiffc(Double.valueOf(-itemDTO.getDiffc().doubleValue()));
                itemDTO.setDiffd(Double.valueOf(-itemDTO.getDiffd().doubleValue()));
                itemDTO.setOrgType(queryParamsVO.getOrgType());
                itemDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.WRITE_OFF_ITEM.getValue()));
                itemDTO.setMemo("\u51b2\u9500");
                return itemDTO;
            }).collect(Collectors.toList());
            offSetItemDTO.setItems(itemDTOs);
            this.save(offSetItemDTO);
        }
    }

    private List<String> getOtherShowColumns(String tableName, String systemId) {
        List dimensionVOs = this.optionService.getDimensionsByTableName(tableName, systemId);
        if (CollectionUtils.isEmpty(dimensionVOs)) {
            return new ArrayList<String>();
        }
        return dimensionVOs.stream().map(dim -> dim.getCode()).collect(Collectors.toList());
    }

    @Override
    public void updateOffsetDisabledFlag(List<String> mrecids, boolean isDisabled) {
        this.offsetCoreService.updateDisabledFlagByMrecid(mrecids, isDisabled);
        this.doLogByMrecIdListAndTitle(mrecids, isDisabled ? "\u7981\u7528" : "\u542f\u7528");
    }

    @Override
    public void updateMemo(Map<String, Object> params) {
        if (params == null) {
            throw new BusinessRuntimeException("\u66f4\u65b0\u53c2\u6570\u4e3a\u7a7a\uff01");
        }
        if (params.get("ID") == null) {
            throw new BusinessRuntimeException("\u66f4\u65b0\u53c2\u6570ID\u4e3a\u7a7a\uff01");
        }
        if (params.get("MEMO") == null) {
            throw new BusinessRuntimeException("\u66f4\u65b0\u53c2\u6570\u63cf\u8ff0\u5b57\u6bb5\u4e3a\u7a7a\uff01");
        }
        if (params.get("tabName") == null) {
            throw new BusinessRuntimeException("\u66f4\u65b0\u53c2\u6570\u9875\u7b7e\u540d\u79f0\u4e3a\u7a7a\uff01");
        }
        String id = params.get("ID").toString();
        String memo = params.get("MEMO").toString();
        String tabName = params.get("tabName").toString();
        if ("OFFSETPAGE".equals(tabName)) {
            this.offsetCoreService.updateMemoById(id, memo);
        } else {
            if (params.get("taskId") == null) {
                throw new BusinessRuntimeException("\u66f4\u65b0\u53c2\u6570\u4efb\u52a1\u4fe1\u606f\u4e3a\u7a7a\uff01");
            }
            String taskId = params.get("taskId").toString();
            if (Objects.nonNull(this.gcInputDataOffsetItemService)) {
                this.gcInputDataOffsetItemService.updateMemoById(id, memo, taskId);
            }
        }
    }

    @Override
    public int getMemoLength(String tabName) {
        if (StringUtils.isEmpty(tabName)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u63cf\u8ff0\u5b57\u6bb5\u957f\u5ea6\u5931\u8d25\uff1a\u9875\u7b7e\u540d\u79f0\u4e3a\u7a7a\uff01");
        }
        String tableCode = "OFFSETPAGE".equals(tabName) ? "GC_OFFSETVCHRITEM" : "GC_INPUTDATATEMPLATE";
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode(tableCode);
            ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), "MEMO");
            return columnModelDefine.getPrecision();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6[" + tableCode + "]\u8868\u63cf\u8ff0\u5b57\u6bb5\u957f\u5ea6\u5931\u8d25", (Throwable)e);
        }
    }

    @Override
    public List<Map<String, Object>> getPartFieldOffsetEntrys(QueryParamsDTO queryParamsDTO) {
        return this.offsetCoreService.getPartFieldOffsetEntrys(queryParamsDTO);
    }

    @Override
    public List<Map<String, Object>> sumOffsetValueGroupBySubjectcode(QueryParamsVO queryParamsVO) {
        String selectFields = " record.unitid,record.subjectCode,record.OFFSETSRCTYPE,record.ELMMODE , sum(record.offset_DEBIT) as debitValue,sum(record.offset_Credit) as creditValue, count(1) as OFFSETCOUNT ";
        String groupStr = " record.unitid,record.subjectCode,record.OFFSETSRCTYPE,record.ELMMODE\n";
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        return this.offsetCoreService.sumOffsetValueGroupBy(queryParamsDTO, selectFields, groupStr);
    }

    @Override
    public Pagination<Map<String, Object>> listOffsetRecordsForAction(QueryParamsVO queryParamsVO) {
        GcOffsetExecutorVO gcOffsetExecutorVO = new GcOffsetExecutorVO(queryParamsVO.getActionCode(), queryParamsVO.getPageCode(), queryParamsVO.getDataSourceCode(), queryParamsVO.getFilterMethod(), (Object)queryParamsVO);
        GcOffSetItemAction action = this.gcOffSetItemAdjustExecutor.getActionForCode(gcOffsetExecutorVO);
        return (Pagination)action.execute(gcOffsetExecutorVO);
    }

    @Override
    public ReadWriteAccessDesc writeableByOrgCodeAndDiffCode(DimensionParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = instance.getOrgByCode(queryParamsVO.getOrgId());
        ReadWriteAccessDesc readWriteAccessDesc = new UploadStateTool().writeable(queryParamsVO);
        if (!readWriteAccessDesc.getAble().booleanValue()) {
            if (orgCacheVO != null) {
                readWriteAccessDesc.setDesc("\u3010" + orgCacheVO.getTitle() + "\u3011" + readWriteAccessDesc.getDesc());
            }
            return readWriteAccessDesc;
        }
        if (orgCacheVO == null || orgCacheVO.getDiffUnitId() == null) {
            return readWriteAccessDesc;
        }
        queryParamsVO.setOrgId(orgCacheVO.getDiffUnitId());
        readWriteAccessDesc = new UploadStateTool().writeable(queryParamsVO);
        GcOrgCacheVO diffCacheVO = instance.getOrgByCode(orgCacheVO.getDiffUnitId());
        if (diffCacheVO != null) {
            readWriteAccessDesc.setDesc("\u3010" + diffCacheVO.getTitle() + "\u3011" + readWriteAccessDesc.getDesc());
        }
        return readWriteAccessDesc;
    }

    private GcTaskBaseArguments getGcTaskBaseArguments(String taskId, Integer acctYear, Integer acctPeriod, String orgType, String offSetCurr) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setTaskId(taskId);
        arguments.setAcctYear(acctYear);
        arguments.setAcctPeriod(acctPeriod);
        arguments.setOrgType(orgType);
        arguments.setCurrency(offSetCurr);
        return arguments;
    }

    private QueryParamsDTO convertQueryVO2DTO(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        return queryDTO;
    }
}

