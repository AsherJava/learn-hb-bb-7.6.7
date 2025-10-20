/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.querytask.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgTemporaryBatchDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgTemporaryEO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractArbitrarilyMergeQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperPrimaryQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperRytzItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperUnDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.impl.ArbitrarilyMergeOffSetItemAdjustServiceImpl;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
public class ArbitrarilyMergeQueryTaskImpl
extends AbstractArbitrarilyMergeQueryTask {
    @Autowired
    private ArbitrarilyMergeOrgTemporaryBatchDao orgTemporaryBatchDao;
    @Autowired
    private ArbitrarilyMergeOffSetItemAdjustServiceImpl arbitrarilyMergeOffSetItemAdjustService;
    @Autowired
    private ConsolidatedSubjectClient subjectClient;

    @Override
    public List<WorkingPaperTableHeaderVO> getHeaderVO(WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        ArrayList<WorkingPaperTableHeaderVO> titles = new ArrayList<WorkingPaperTableHeaderVO>();
        titles.add(new WorkingPaperTableHeaderVO("formSubjectTitle", GcI18nUtil.getMessage((String)"gc.workingpaper.header.project"), "left", (Object)"left", 250));
        titles.add(new WorkingPaperTableHeaderVO("kmorient", GcI18nUtil.getMessage((String)"gc.workingpaper.header.amountdirection"), "center", (Object)"left", 100));
        List mergeColumnSelectKeys = queryCondition.getMergeColumnSelectKeys();
        AtomicInteger containQmsMxAndHj = new AtomicInteger();
        if (mergeColumnSelectKeys.contains("QMMXDATA") && mergeColumnSelectKeys.contains("QMHJDATA")) {
            containQmsMxAndHj.set(2);
        } else if (mergeColumnSelectKeys.contains("QMMXDATA") || mergeColumnSelectKeys.contains("QMHJDATA")) {
            containQmsMxAndHj.set(1);
        } else {
            containQmsMxAndHj.set(0);
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryCondition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        WorkingPaperTableHeaderVO qmsVO2 = new WorkingPaperTableHeaderVO("QMS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closing"), "center");
        mergeColumnSelectKeys.forEach(columnKey -> {
            switch (columnKey) {
                case "QMMXDATA": {
                    for (String orgId : queryCondition.getOrgIds()) {
                        GcOrgCacheVO gcOrgCaches = tool.getOrgByCode(orgId);
                        if (ObjectUtils.isEmpty(gcOrgCaches)) continue;
                        qmsVO2.addChildren(new WorkingPaperTableHeaderVO("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + gcOrgCaches.getCode(), gcOrgCaches.getTitle(), "right"));
                    }
                    containQmsMxAndHj.set(containQmsMxAndHj.get() - 1);
                    if (containQmsMxAndHj.get() != 0) break;
                    titles.add(qmsVO2);
                    break;
                }
                case "QMHJDATA": {
                    qmsVO2.addChildren(new WorkingPaperTableHeaderVO("QMS_NOT_CONTAIN_DXS_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closingsum"), "right"));
                    containQmsMxAndHj.set(containQmsMxAndHj.get() - 1);
                    if (containQmsMxAndHj.get() != 0) break;
                    titles.add(qmsVO2);
                    break;
                }
                case "DXDATA": {
                    WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetamt"), "center");
                    dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_DEBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.borrower"), "right"));
                    dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_CREBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.lender"), "right"));
                    dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.diff"), "right", (Object)false, 150));
                    titles.add(dxsVO);
                    break;
                }
                case "UNDXDATA": {
                    WorkingPaperTableHeaderVO undxsVO = new WorkingPaperTableHeaderVO("UN_DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.unoffsetamt"), "center");
                    undxsVO.addChildren(new WorkingPaperTableHeaderVO("UN_DXS_JD_DEBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.borrower"), "right"));
                    undxsVO.addChildren(new WorkingPaperTableHeaderVO("UN_DXS_JD_CREBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.lender"), "right"));
                    titles.add(undxsVO);
                    break;
                }
                case "RYHBTZDATA": {
                    WorkingPaperTableHeaderVO tzsVO = new WorkingPaperTableHeaderVO("RYTZS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.adjust"), "center");
                    tzsVO.addChildren(new WorkingPaperTableHeaderVO("RYTZS_JD_DEBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.borrower"), "right"));
                    tzsVO.addChildren(new WorkingPaperTableHeaderVO("RYTZS_JD_CREBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.lender"), "right"));
                    tzsVO.addChildren(new WorkingPaperTableHeaderVO("RYTZS_JD_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.adjustsum"), "right", (Object)false, 150));
                    titles.add(tzsVO);
                    break;
                }
                case "HBDATA": {
                    titles.add(new WorkingPaperTableHeaderVO("HBS_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combining"), "right", (Object)false, 200));
                    break;
                }
                default: {
                    throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u5217\u7c7b\u578b");
                }
            }
        });
        return titles;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition condition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap = this.getPrimaryWorkpaperSettingVOS(condition);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        LinkedHashMap<String, GcOrgCacheVO> allChildOrgCode2DataMap = new LinkedHashMap<String, GcOrgCacheVO>();
        LinkedHashMap<String, GcOrgCacheVO> allOrgCode2DataMap = new LinkedHashMap<String, GcOrgCacheVO>();
        for (String orgId : condition.getOrgIds()) {
            GcOrgCacheVO orgByCode = tool.getOrgByCode(orgId);
            if (ObjectUtils.isEmpty(orgByCode)) {
                LOGGER.info("\u5355\u4f4d{}\u5728\u5f53\u524d\u7248\u672c\u4e2d\u4e0d\u5b58\u5728\uff0c\u8df3\u8fc7\u3002", (Object)orgId);
                continue;
            }
            List gcOrgCacheVOS = tool.listAllOrgByParentIdContainsSelf(orgId);
            Map allChildOrgCode = gcOrgCacheVOS.stream().collect(Collectors.toMap(GcOrgCacheVO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
            allOrgCode2DataMap.put(orgId, orgByCode);
            allChildOrgCode2DataMap.putAll(allChildOrgCode);
        }
        List<WorkingPaperTableDataVO> workingPaperTableDataVOS = this.buildBlankWorkingPaperTableDataByPrimary(primaryCode2DataMap, condition);
        List<WorkingPaperPrimaryQmsItemDTO> workingPaperQmsDTOs = this.getWorkingPaperQmsDTOs(condition, workingPaperTableDataVOS, allOrgCode2DataMap);
        Map<String, WorkingPaperPrimaryQmsItemDTO> mergeQmsItemDatasByPrimaryAndOrgMap = this.mergeWorkingPaperQmsDTOsByPrimaryAndOrg(workingPaperQmsDTOs);
        HashMap<String, String> orgTypeMap = new HashMap<String, String>();
        this.handleSelectUnitToTemporary(condition, orgTypeMap);
        Map<String, Set<String>> primaryId2BoundSubjectCodes = this.getPrimaryId2BoundSubjectCodes(condition, workingPaperTableDataVOS, primaryCode2DataMap);
        Map<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap = this.getPrimaryWorkingPaperOffsetItemDTOs(condition, workingPaperTableDataVOS, primaryId2BoundSubjectCodes);
        Map<String, List<WorkingPaperUnDxsItemDTO>> primaryWorkingPaperUnOffsetItemsMap = this.getPrimaryWorkingPaperUnOffsetItemDTOs(condition, workingPaperTableDataVOS, primaryId2BoundSubjectCodes);
        Map<String, List<WorkingPaperRytzItemDTO>> primaryWorkingPaperRyOffsetItemsMap = this.getPrimaryWorkingPaperRyOffsetItemDTOs(condition, workingPaperTableDataVOS, primaryId2BoundSubjectCodes);
        Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryMap = this.mergeOffsetItemDatasByPrimary(primaryWorkingPaperOffsetItemDTOsMap, primaryCode2DataMap);
        Map<String, WorkingPaperUnDxsItemDTO> mergeUnOffsetItemDatasByPrimaryMap = this.mergeUnOffsetItemDatasByPrimary(primaryWorkingPaperUnOffsetItemsMap, primaryCode2DataMap);
        Map<String, WorkingPaperRytzItemDTO> mergeRyOffsetItemDatasByPrimaryMap = this.mergeRyOffsetItemDatasByPrimary(primaryWorkingPaperRyOffsetItemsMap, primaryCode2DataMap);
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            dataVO.getZbvalueStr().put("formSubjectTitle", dataVO.getFormSubjectTitle());
            dataVO.getZbvalueStr().put("kmorient", dataVO.getKmorient());
            ArrayList unitTotalItems = new ArrayList();
            allChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                WorkingPaperPrimaryQmsItemDTO workingPaperQmsItemDTO = (WorkingPaperPrimaryQmsItemDTO)mergeQmsItemDatasByPrimaryAndOrgMap.get(dataVO.getPrimarySettingId() + "_" + orgCode);
                if (workingPaperQmsItemDTO == null) {
                    workingPaperQmsItemDTO = WorkingPaperPrimaryQmsItemDTO.empty();
                }
                dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_" + orgCode, workingPaperQmsItemDTO.getZbValue());
                dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + orgCode, ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(workingPaperQmsItemDTO.getZbValue()));
                if (condition.getOrgIds().contains(orgCode)) {
                    unitTotalItems.add(workingPaperQmsItemDTO.getZbValue());
                }
            });
            BigDecimal unitQmsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])unitTotalItems.toArray(new BigDecimal[unitTotalItems.size()]));
            dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_TOTAL", unitQmsTotal);
            dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_TOTAL_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(unitQmsTotal));
            WorkingPaperUnDxsItemDTO jdMergeUnOffSetVchrItemDTO = (WorkingPaperUnDxsItemDTO)mergeUnOffsetItemDatasByPrimaryMap.get(dataVO.getPrimarySettingId());
            if (jdMergeUnOffSetVchrItemDTO == null) {
                jdMergeUnOffSetVchrItemDTO = WorkingPaperUnDxsItemDTO.empty();
            }
            BigDecimal jdUnOffSetCredit = jdMergeUnOffSetVchrItemDTO.getUnOffSetCredit();
            BigDecimal jdUnOffSetDebit = jdMergeUnOffSetVchrItemDTO.getUnOffSetDebit();
            dataVO.getZbvalue().put("UN_DXS_JD_DEBIT", jdUnOffSetDebit);
            dataVO.getZbvalueStr().put("UN_DXS_JD_DEBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdUnOffSetDebit));
            dataVO.getZbvalue().put("UN_DXS_JD_CREBIT", jdUnOffSetCredit);
            dataVO.getZbvalueStr().put("UN_DXS_JD_CREBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdUnOffSetCredit));
            WorkingPaperDxsItemDTO jdMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasByPrimaryMap.get(dataVO.getPrimarySettingId());
            if (jdMergeOffSetVchrItemDTO == null) {
                jdMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
            }
            BigDecimal jdOffSetCredit = jdMergeOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jdOffSetDebit = jdMergeOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jdDxTotal = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)jdOffSetDebit, (BigDecimal)jdOffSetCredit) : NumberUtils.sub((BigDecimal)jdOffSetCredit, (BigDecimal)jdOffSetDebit);
            dataVO.getZbvalue().put("DXS_JD_DEBIT", jdOffSetDebit);
            dataVO.getZbvalueStr().put("DXS_JD_DEBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdOffSetDebit));
            dataVO.getZbvalue().put("DXS_JD_CREBIT", jdOffSetCredit);
            dataVO.getZbvalueStr().put("DXS_JD_CREBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdOffSetCredit));
            dataVO.getZbvalue().put("DXS_JD_TOTAL", jdDxTotal);
            dataVO.getZbvalueStr().put("DXS_JD_TOTAL_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdDxTotal));
            WorkingPaperRytzItemDTO jdMergeRyOffSetVchrItemDTO = (WorkingPaperRytzItemDTO)mergeRyOffsetItemDatasByPrimaryMap.get(dataVO.getPrimarySettingId());
            if (jdMergeRyOffSetVchrItemDTO == null) {
                jdMergeRyOffSetVchrItemDTO = WorkingPaperRytzItemDTO.empty();
            }
            BigDecimal jdRyOffSetCredit = jdMergeRyOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jdRyOffSetDebit = jdMergeRyOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jdRyDxTotal = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)jdRyOffSetDebit, (BigDecimal)jdRyOffSetCredit) : NumberUtils.sub((BigDecimal)jdRyOffSetCredit, (BigDecimal)jdRyOffSetDebit);
            dataVO.getZbvalue().put("RYTZS_JD_DEBIT", jdRyOffSetDebit);
            dataVO.getZbvalueStr().put("RYTZS_JD_DEBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdRyOffSetDebit));
            dataVO.getZbvalue().put("RYTZS_JD_CREBIT", jdRyOffSetCredit);
            dataVO.getZbvalueStr().put("RYTZS_JD_CREBIT_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdRyOffSetCredit));
            dataVO.getZbvalue().put("RYTZS_JD_TOTAL", jdRyDxTotal);
            dataVO.getZbvalueStr().put("RYTZS_JD_TOTAL_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(jdRyDxTotal));
            BigDecimal dxsTotal = BigDecimal.ZERO;
            BigDecimal rytzTotal = BigDecimal.ZERO;
            switch (dxsTypeEnum) {
                case JD: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_JD_TOTAL");
                    rytzTotal = (BigDecimal)dataVO.getZbvalue().get("RYTZS_JD_TOTAL");
                    break;
                }
                case NO_SHOW: {
                    break;
                }
                default: {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + dxsTypeEnum.getTitle());
                }
            }
            BigDecimal hbsTotal = BigDecimal.ZERO;
            switch (qmsTypeEnum) {
                case NOT_CONTAIN_DXS: {
                    BigDecimal qmsTotal = (BigDecimal)dataVO.getZbvalue().get("QMS_NOT_CONTAIN_DXS_TOTAL");
                    hbsTotal = NumberUtils.add((BigDecimal)qmsTotal, (BigDecimal[])new BigDecimal[]{dxsTotal, rytzTotal});
                    break;
                }
                case NO_SHOW: {
                    hbsTotal = dxsTotal;
                    break;
                }
                default: {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + qmsTypeEnum.getTitle());
                }
            }
            dataVO.getZbvalue().put("HBS", hbsTotal);
            dataVO.getZbvalueStr().put("HBS_SHOWVALUE", ArbitrarilyMergeQueryTaskImpl.formatBigDecimal(hbsTotal));
        });
        List<WorkingPaperTableDataVO> rebuildWorkPaperVos = this.rebuildWorkPaperResultVos(condition, qmsTypeEnum, dxsTypeEnum, allChildOrgCode2DataMap, workingPaperTableDataVOS);
        if (Boolean.TRUE.equals(condition.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)condition.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(condition.getOrgBatchId());
        }
        return rebuildWorkPaperVos;
    }

    public Pagination<Map<String, Object>> getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)pentrationQueryCondtion.getWorkPaperVoJson(), WorkingPaperTableDataVO.class);
        List<String> subjectCodes = WorkingPaperQueryUtils.listSubjectCodes(pentrationQueryCondtion, workingPaperTableDataVO);
        WorkingPaperPentrationQueryCondtion newPaperQueryCondtion = new WorkingPaperPentrationQueryCondtion();
        BeanUtils.copyProperties(pentrationQueryCondtion, newPaperQueryCondtion);
        this.handleSelectUnitToTemporary((WorkingPaperQueryCondition)newPaperQueryCondtion, new HashMap<String, String>(16));
        Pagination<Map<String, Object>> offsetPage = this.getOffsetPage(newPaperQueryCondtion, new HashSet<String>(subjectCodes));
        if (Boolean.TRUE.equals(newPaperQueryCondtion.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)newPaperQueryCondtion.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(newPaperQueryCondtion.getOrgBatchId());
        }
        return offsetPage;
    }

    public void handleSelectUnitToTemporary(WorkingPaperQueryCondition queryCondition, Map<String, String> orgTypeMap) {
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryCondition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        ArrayList<ArbitrarilyMergeOrgTemporaryEO> orgTemporaryData = new ArrayList<ArbitrarilyMergeOrgTemporaryEO>();
        if (CollectionUtils.isEmpty((Collection)queryCondition.getOrgIds())) {
            return;
        }
        Integer[] orgComSupLength = new Integer[]{100};
        String batchId = UUIDUtils.newUUIDStr();
        for (String orgId : queryCondition.getOrgIds()) {
            List gcOrgCaches = tool.listAllOrgByParentIdContainsSelf(orgId);
            if (gcOrgCaches.size() == 1) {
                GcOrgCacheVO orgCacheVO2 = (GcOrgCacheVO)gcOrgCaches.get(0);
                ArbitrarilyMergeOrgTemporaryEO arbitrarilyMergeOrgTemporaryEO = new ArbitrarilyMergeOrgTemporaryEO();
                arbitrarilyMergeOrgTemporaryEO.setCode(orgCacheVO2.getFields().get("CODE").toString());
                arbitrarilyMergeOrgTemporaryEO.setParents(orgCacheVO2.getFields().get("CODE").toString());
                arbitrarilyMergeOrgTemporaryEO.setId(UUIDUtils.newUUIDStr());
                arbitrarilyMergeOrgTemporaryEO.setBatchId(batchId);
                orgTemporaryData.add(arbitrarilyMergeOrgTemporaryEO);
                orgTypeMap.put(orgCacheVO2.getCode(), orgCacheVO2.getOrgTypeId());
            } else {
                gcOrgCaches.forEach(orgCacheVO -> {
                    ArbitrarilyMergeOrgTemporaryEO arbitrarilyMergeOrgTemporaryEO = new ArbitrarilyMergeOrgTemporaryEO();
                    arbitrarilyMergeOrgTemporaryEO.setCode(orgCacheVO.getFields().get("CODE").toString());
                    String parents = orgCacheVO.getFields().get("PARENTS").toString();
                    arbitrarilyMergeOrgTemporaryEO.setParents(parents.substring(parents.indexOf(orgId)));
                    arbitrarilyMergeOrgTemporaryEO.setBatchId(batchId);
                    arbitrarilyMergeOrgTemporaryEO.setId(UUIDUtils.newUUIDStr());
                    orgTemporaryData.add(arbitrarilyMergeOrgTemporaryEO);
                    orgTypeMap.put(orgCacheVO.getCode(), orgCacheVO.getOrgTypeId());
                });
            }
            orgComSupLength[0] = orgId.length();
        }
        queryCondition.setOrgBatchId(batchId);
        queryCondition.setOrgComSupLength(orgComSupLength[0]);
        if (!CollectionUtils.isEmpty(orgTemporaryData)) {
            this.orgTemporaryBatchDao.addBatch(orgTemporaryData);
        }
        queryCondition.setOrgIds(queryCondition.getOrgIds().stream().filter(orgCode -> !Objects.isNull(tool.getOrgByCode(orgCode))).filter(orgCode -> !GcOrgKindEnum.DIFFERENCE.equals((Object)tool.getOrgByCode(orgCode).getOrgKind())).collect(Collectors.toList()));
    }

    protected Pagination<Map<String, Object>> getOffsetPage(WorkingPaperPentrationQueryCondtion penInfo, Set<String> subjectCodes) {
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(penInfo, true);
        queryParamsVO.setSubjectCodes(new ArrayList<String>(subjectCodes));
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        Pagination<Map<String, Object>> offsetPage = this.arbitrarilyMergeOffSetItemAdjustService.assembleOffsetEntry(this.getOffsetPage(queryParamsDTO), queryParamsVO);
        List<Map<String, Object>> offsetDatas = offsetPage.getContent();
        Set mRecids = offsetDatas.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
        offsetPage.setTotalElements(offsetPage.getTotalElements());
        offsetDatas = offsetDatas.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        offsetDatas = WorkingPaperQueryUtils.setRowSpanAndSort(offsetDatas);
        offsetPage.setContent(offsetDatas);
        return offsetPage;
    }

    private Pagination<Map<String, Object>> getOffsetPage(QueryParamsDTO queryParamsDTO) {
        EntNativeSqlDefaultDao sqlDefaultDao = EntNativeSqlDefaultDao.getInstance();
        GcOffSetItemAdjustCoreDao offSetItemAdjustCoreDao = (GcOffSetItemAdjustCoreDao)SpringContextUtils.getBean(GcOffSetItemAdjustCoreDao.class);
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(queryParamsDTO.getPageNum()), Integer.valueOf(queryParamsDTO.getPageSize()));
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = this.getQueryOffsetPageSql(queryParamsDTO, params);
        String countSql = String.format("select count(*) from ( %1$s )  t", sql);
        int count = sqlDefaultDao.count(countSql, params);
        if (count < 1) {
            page.setContent(new ArrayList());
            return page;
        }
        int begin = -1;
        int range = -1;
        int pageNum = queryParamsDTO.getPageNum();
        int pageSize = queryParamsDTO.getPageSize();
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            range = pageNum * pageSize;
        }
        List rs = sqlDefaultDao.selectMapByPaging(sql, begin, range, params);
        HashSet<String> mRecids = new HashSet<String>();
        for (Map d : rs) {
            mRecids.add(String.valueOf(d.get("MRECID")));
        }
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setContent(new ArrayList());
            return page;
        }
        List datas = offSetItemAdjustCoreDao.listWithFullGroupByMrecids(queryParamsDTO, mRecids);
        page.setTotalElements(Integer.valueOf(count));
        page.setContent(datas);
        return page;
    }

    private String getQueryOffsetPageSql(QueryParamsDTO queryParamsDTO, List<Object> params) {
        StringBuilder sql = new StringBuilder();
        String queryFields = "record.mrecid";
        sql.append("select ").append(queryFields);
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
        if (!CollectionUtils.isEmpty((Collection)queryParamsDTO.getSubjectCodes())) {
            HashSet<String> allChildrenCodeAndSelf = new HashSet<String>();
            if (!StringUtils.isEmpty((String)queryParamsDTO.getSystemId())) {
                List subjectCodes = queryParamsDTO.getSubjectCodes();
                List allSubjects = this.subjectClient.listSubjectsBySystemIdNoSortOrder(queryParamsDTO.getSystemId());
                Map parentCode2DirectChildrenCodesMap = allSubjects.stream().collect(Collectors.groupingBy(ConsolidatedSubjectVO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                for (String subjectCode : subjectCodes) {
                    if (allChildrenCodeAndSelf.contains(subjectCode)) continue;
                    allChildrenCodeAndSelf.addAll(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
                    allChildrenCodeAndSelf.add(subjectCode);
                }
            }
            if (!allChildrenCodeAndSelf.isEmpty()) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(allChildrenCodeAndSelf, (String)"record.SUBJECTCODE"));
            }
            if (!CollectionUtils.isEmpty((Collection)queryParamsDTO.getEffectTypes())) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)queryParamsDTO.getEffectTypes(), (String)"record.EFFECTTYPE"));
            }
        }
        String groupStr = "record.mrecid \n";
        sql.append((CharSequence)whereSql).append("\n").append(" group by ").append(groupStr);
        String orderBy = "record.mrecid desc \n";
        sql.append(" order by ").append(orderBy);
        return sql.toString();
    }
}

