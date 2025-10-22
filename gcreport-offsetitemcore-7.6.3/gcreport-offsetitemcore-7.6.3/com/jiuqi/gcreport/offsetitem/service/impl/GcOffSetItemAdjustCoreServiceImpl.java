/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.offsetitem.check.IOffsetGroupChecker;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckGather;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcOffSetItemAdjustCoreServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements GcOffSetItemAdjustCoreService {
    private final Logger logger = LoggerFactory.getLogger(GcOffSetItemAdjustCoreServiceImpl.class);
    @Resource
    private GcOffSetItemAdjustCoreDao coreDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private UnionRuleService unionRuleService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Collection<GcOffSetVchrDTO> batchSave(Collection<GcOffSetVchrDTO> offSetItemDTOCollection) {
        ArrayList<GcOffSetVchrDTO> offSetItemDTOs = new ArrayList<GcOffSetVchrDTO>(offSetItemDTOCollection);
        Collections.reverse(offSetItemDTOs);
        offSetItemDTOs.forEach(offSetItemDTO -> this.save((GcOffSetVchrDTO)offSetItemDTO));
        return offSetItemDTOs;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public GcOffSetVchrDTO save(GcOffSetVchrDTO offSetItemDTO) {
        List<GcOffSetVchrItemDTO> itemDTOs;
        this.checkGroupDTO(offSetItemDTO);
        if (offSetItemDTO.isNeedDelete()) {
            this.coreDao.deleteByMrecId(Arrays.asList(offSetItemDTO.getMrecid()));
        }
        if (CollectionUtils.isEmpty(itemDTOs = offSetItemDTO.getItems())) {
            return offSetItemDTO;
        }
        Date date = new Date();
        List<GcOffSetVchrItemAdjustEO> GcOffSetVchrItemAdjustEOs = itemDTOs.stream().map(itemDTO -> {
            if (StringUtils.isEmpty((String)itemDTO.getId())) {
                itemDTO.setId(UUIDOrderSnowUtils.newUUIDStr());
            }
            itemDTO.setmRecid(offSetItemDTO.getMrecid());
            itemDTO.setVchrCode(offSetItemDTO.getVchrCode());
            itemDTO.setCreateTime(date);
            return OffsetCoreConvertUtil.convertDTO2EO(itemDTO);
        }).collect(Collectors.toList());
        this.coreDao.addBatch(GcOffSetVchrItemAdjustEOs);
        return offSetItemDTO;
    }

    @Override
    public Set<String> delete(QueryParamsDTO queryParamsDto) {
        Assert.isNotNull((Object)queryParamsDto.getOrgId(), (String)"\u5408\u5e76\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsDto.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsDto.getAcctYear(), (String)"\u5e74\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        HashSet<String> srcOffsetGroupIdResults = new HashSet<String>();
        HashSet<String> mrecids = new HashSet<String>();
        this.coreDao.fillMrecids(queryParamsDto, srcOffsetGroupIdResults, mrecids);
        if (CollectionUtils.isEmpty(mrecids)) {
            return mrecids;
        }
        this.coreDao.deleteByMrecId(mrecids);
        this.logger.info("\u5220\u9664\u62b5\u9500\u5206\u5f55\u7684\u6761\u6570\uff1a" + mrecids.size());
        return mrecids;
    }

    @Override
    public void deleteByMrecId(Collection<String> mrecIds) {
        if (CollectionUtils.isEmpty(mrecIds)) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u5220\u9664\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f[\u62b5\u9500\u5206\u5f55\u5206\u7ec4ID\u53c2\u6570\u4e0d\u5141\u8bb8\u4e3a\u7a7a]\u3002");
        }
        try {
            this.doLogByMrecIdListAndTitle(mrecIds, "\u53d6\u6d88\u62b5\u9500");
            this.coreDao.deleteByMrecId(mrecIds);
        }
        catch (Exception e) {
            String message = String.format("\u5220\u9664\u64cd\u4f5c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:%s", e.getMessage());
            this.logger.error(message, e);
            throw new BusinessRuntimeException(message, (Throwable)e);
        }
    }

    @Override
    public void deleteByOffsetGroupIds(Collection<String> srcOffsetGroupIds, GcTaskBaseArguments baseArguments) {
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return;
        }
        this.coreDao.deleteByOffsetGroupIds(srcOffsetGroupIds, baseArguments);
    }

    @Override
    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> srcOffsetGroupIds, int offSetSrcType, GcTaskBaseArguments baseArguments) {
        this.coreDao.deleteByOffsetGroupIdsAndSrcType(srcOffsetGroupIds, offSetSrcType, baseArguments);
    }

    @Override
    public void updateDisabledFlagByMrecid(List<String> mrecids, boolean isDisabled) {
        this.coreDao.updateDisabledFlag(mrecids, isDisabled);
        this.doLogByMrecIdListAndTitle(mrecids, isDisabled ? "\u7981\u7528" : "\u542f\u7528");
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO queryParamsDto) {
        return this.coreDao.listWithOnlyItems(queryParamsDto);
    }

    @Override
    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO queryParamsDto) {
        return this.coreDao.listEOWithFullGroup(queryParamsDto);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listByWhere(String[] columnNamesInDB, Object[] values) {
        return this.coreDao.listByWhere(columnNamesInDB, values);
    }

    @Override
    public GcOffSetVchrItemDTO getGcOffSetVchrItemDTO(String id) {
        GcOffSetVchrItemAdjustEO adjustEO = (GcOffSetVchrItemAdjustEO)this.coreDao.get((Serializable)((Object)id));
        return OffsetCoreConvertUtil.convertEO2DTO(adjustEO);
    }

    @Override
    public void updateOffsetGroupId(String oldSrcGroupId, String newSrcGroupId, Integer elmMode, GcTaskBaseArguments baseArguments) {
        this.coreDao.updateOffsetGroupId(oldSrcGroupId, newSrcGroupId, elmMode, baseArguments);
    }

    @Override
    public Collection<String> listOffsetGroupIdsByMrecid(Collection<String> mrecids) {
        return this.coreDao.listOffsetGroupIdsByMrecid(mrecids);
    }

    @Override
    public Collection<String> listOffsetGroupIdsById(Collection<String> ids) {
        return this.coreDao.listOffsetGroupIdsById(ids);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithFullGroupByMrecids(Collection<String> mRecids) {
        return this.coreDao.listWithFullGroupByMrecids(mRecids);
    }

    @Override
    public PaginationDto<Map<String, Object>> listWithFullGroup(QueryParamsDTO queryParamsDto) {
        return this.coreDao.listWithFullGroup(queryParamsDto);
    }

    @Override
    public List<Map<String, Object>> listWithFullGroupByMrecids(QueryParamsDTO queryParamsDto, Set<String> mRecids) {
        return this.coreDao.listWithFullGroupByMrecids(queryParamsDto, mRecids);
    }

    @Override
    public List<Map<String, Object>> listWithFullGroupBySrcOffsetGroupIdsAndSystemId(QueryParamsDTO queryParamsDto, Set<String> offsetGroupIds) {
        return this.coreDao.listWithFullGroupBySrcOffsetGroupIdsAndSystemId(queryParamsDto, offsetGroupIds);
    }

    @Override
    public int fillMrecids(QueryParamsDTO queryParamsDto, Set<String> srcOffsetGroupIdResults, Set<String> mrecids) {
        return this.coreDao.fillMrecids(queryParamsDto, srcOffsetGroupIdResults, mrecids);
    }

    @Override
    public void updateMemoById(String id, String memo) {
        this.coreDao.updateMemoById(id, memo);
    }

    @Override
    public String mergeUnitRangeSql(QueryParamsDTO queryParamsDto, List<Object> params) {
        return this.coreDao.mergeUnitRangeSql(queryParamsDto, params);
    }

    @Override
    public Map<String, String> getMrecid2FetchSetIdByMrecids(Set<String> mRecids) {
        return this.coreDao.getMrecid2FetchSetIdByMrecids(mRecids);
    }

    @Override
    public Collection<String> listMrecidsByRuleId(String ruleId, Set<String> subjectCode, QueryParamsDTO queryParamsVO) {
        return this.coreDao.listMrecidsByRuleId(ruleId, subjectCode, queryParamsVO);
    }

    @Override
    public List<Map<String, Object>> sumOffsetValueGroupBy(QueryParamsDTO queryParamsVO, String selectFieldSql, String groupFieldSql) {
        return this.coreDao.sumOffsetValueGroupBy(queryParamsVO, selectFieldSql, groupFieldSql);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String lockId) {
        return this.coreDao.listIdsByLockId(lockId);
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String lockId, List<String> currencyList) {
        return this.coreDao.listIdsByLockId(lockId, currencyList);
    }

    @Override
    public void deleteByLockId(String lockId) {
        this.coreDao.deleteByLockId(lockId);
    }

    @Override
    public void deleteByLockId(String lockId, List<String> currencyList) {
        this.coreDao.deleteByLockId(lockId, currencyList);
    }

    @Override
    public List<String> listExistsSubjectCodes(String systemId, Set<String> keySet) {
        return this.coreDao.listExistsSubjectCodes(systemId, keySet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> getPartFieldOffsetEntrys(QueryParamsDTO queryParamsDTO) {
        String queryFields = "record.mrecid,record.elmMode,record.srcOffsetGroupId, record.MODIFYTIME";
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = this.coreDao.getFillMrecidSql(queryParamsDTO, queryFields, params);
        if (StringUtils.isEmpty((String)sql)) {
            return Collections.emptyList();
        }
        int begin = -1;
        int range = -1;
        int pageNum = queryParamsDTO.getPageNum();
        int pageSize = queryParamsDTO.getPageSize();
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            range = pageNum * pageSize;
        }
        try {
            List list = this.coreDao.selectMapByPaging(sql, begin, range, params);
            return list;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    private void handleUnitAndOppUnitParam(QueryParamsDTO queryParamsDto) {
        List<String> oppUnitIdList;
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsDto.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, queryParamsDto.getPeriodStr()));
        List<String> unitIdList = queryParamsDto.getUnitIdList();
        if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
            queryParamsDto.setEnableTempTableFilterUnitOrOppUnit(true);
            queryParamsDto.setUnitIdList(this.listAllChildrenOrgByOrgList(unitIdList, tool));
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(oppUnitIdList = queryParamsDto.getOppUnitIdList())) {
            queryParamsDto.setEnableTempTableFilterUnitOrOppUnit(true);
            queryParamsDto.setOppUnitIdList(this.listAllChildrenOrgByOrgList(oppUnitIdList, tool));
        }
    }

    private List<String> listAllChildrenOrgByOrgList(List<String> orgCodeList, GcOrgCenterService tool) {
        HashSet allOrgCodeSet = new HashSet();
        for (String code : orgCodeList) {
            if (allOrgCodeSet.contains(code)) continue;
            allOrgCodeSet.addAll(tool.listAllOrgByParentIdContainsSelf(code).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
        return new ArrayList<String>(allOrgCodeSet);
    }

    private void checkGroupDTO(GcOffSetVchrDTO dto) {
        for (IOffsetGroupChecker groupValidator : OffsetItemCheckGather.getGroupValidatorList()) {
            OffsetItemCheckResult offsetItemCheckResult = groupValidator.saveCheck(dto);
            if (offsetItemCheckResult == null || offsetItemCheckResult.isSuccess()) continue;
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u4fdd\u5b58:[" + groupValidator.validatorName() + "]\u5206\u7ec4\u6821\u9a8c\u5668\u6821\u9a8c\u5931\u8d25" + offsetItemCheckResult.getMessage());
        }
    }

    private void doLogByMrecIdListAndTitle(Collection<String> mrecids, String title) {
        try {
            List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOS = this.coreDao.listWithFullGroupByMrecids(mrecids);
            if (org.springframework.util.CollectionUtils.isEmpty(gcOffSetVchrItemAdjustEOS)) {
                return;
            }
            String taskTitle = this.iRunTimeViewController.queryTaskDefine(gcOffSetVchrItemAdjustEOS.get(0).getTaskId()).getTitle();
            String defaultPeriod = gcOffSetVchrItemAdjustEOS.get(0).getDefaultPeriod();
            HashSet<String> mrecIdSet = new HashSet<String>();
            StringBuilder message = new StringBuilder(title + mrecids.size() + "\u7ec4\uff1a\n");
            for (GcOffSetVchrItemAdjustEO eo : gcOffSetVchrItemAdjustEOS) {
                if (mrecIdSet.contains(eo.getmRecid())) continue;
                mrecIdSet.add(eo.getmRecid());
                AbstractUnionRule rule = this.unionRuleService.selectUnionRuleDTOById(eo.getRuleId());
                String ruleTitle = rule == null ? eo.getRuleId() : rule.getTitle();
                message.append("\u4efb\u52a1-").append(taskTitle).append("\uff1b\u65f6\u671f-").append(defaultPeriod).append("\uff1b\u5408\u5e76\u89c4\u5219-").append(ruleTitle).append("\uff1b\u672c\u65b9\u5355\u4f4d-").append(eo.getUnitId()).append("\uff1b\u5bf9\u65b9\u5355\u4f4d-").append(eo.getOppUnitId()).append("\n");
            }
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)(title + "-\u4efb\u52a1" + taskTitle + "-\u65f6\u671f" + defaultPeriod), (String)message.toString());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u62b5\u9500\u5206\u5f55\u65e5\u5fd7\u51fa\u9519:" + e.getMessage(), e);
        }
    }
}

