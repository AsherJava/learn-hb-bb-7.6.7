/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifyOtherInfoVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ReduceReclassifySubjectMappingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifyOtherInfoVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReduceReclassifySubjectMappingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.EndCarryForwardDataSourceServiceImpl;
import com.jiuqi.gcreport.offsetitem.task.GcReclassifyTask;
import com.jiuqi.gcreport.offsetitem.utils.GcReduceUtil;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=2)
@Component
public class GcReduceReclassifyTaskImpl
implements GcReclassifyTask {
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcOffSetAppOffsetService adjustService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private EndCarryForwardDataSourceServiceImpl endCarryForwardDataSourceService;

    @Override
    public String name() {
        return "\u62b5\u51cf\u91cd\u5206\u7c7b";
    }

    @Override
    public int doTask(GcActionParamsVO paramsVO, List<String> hbUnitIds, TaskLog taskLog) {
        int totalOffsetEntryCount = 0;
        for (String hbUnitId : hbUnitIds) {
            int count = this.doTask(paramsVO, hbUnitId, taskLog);
            totalOffsetEntryCount += count;
        }
        return totalOffsetEntryCount;
    }

    @Override
    public boolean allow(GcActionParamsVO paramsVO) {
        return this.allow(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
    }

    private boolean allow(String schemeId, String periodStr) {
        ConsolidatedTaskVO taskVO = this.taskService.getTaskBySchemeId(schemeId, periodStr);
        if (null == taskVO || StringUtils.isEmpty((String)taskVO.getSystemId())) {
            return false;
        }
        if (!taskVO.getEnableReduceReclassify().booleanValue()) {
            return false;
        }
        if (!schemeId.equals(ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)taskVO.getTaskKey(), (String)periodStr))) {
            return false;
        }
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(taskVO.getSystemId());
        List reclassifySubjectMappingList = optionVO.getReduceReclassifySubjectMappings();
        return !CollectionUtils.isEmpty((Collection)reclassifySubjectMappingList);
    }

    @Override
    public void queryReclassifyData(QueryParamsVO paramsVO, LossGainOffsetVO lossGainOffsetVO) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (!this.allow(paramsVO.getSchemeId(), paramsVO.getPeriodStr())) {
            return;
        }
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(systemId);
        ArrayList<Map<String, String>> reclassifyDimension = new ArrayList<Map<String, String>>();
        Set<String> dimCodeSet = this.getDimCode(systemId, reclassifyDimension);
        lossGainOffsetVO.setReduceReclassifyDimension(reclassifyDimension);
        List<GcOffSetVchrItemDTO> offSetVchrItemDTOS = this.listCurrReclassifyDataResult(paramsVO, systemId, optionVO, dimCodeSet);
        paramsVO.setOtherShowColumns(new ArrayList<String>(dimCodeSet));
        new GcReduceUtil().setReclassifyViewPropsDTO(paramsVO, offSetVchrItemDTOS, systemId, optionVO);
        List<Map<String, Object>> currReclassifyResult = offSetVchrItemDTOS.stream().map(AbstractFieldDynamicDeclarator::getFields).collect(Collectors.toList());
        lossGainOffsetVO.setCurrReduceReclassifyResult(OffsetConvertUtil.setRowSpanAndSort(currReclassifyResult));
        lossGainOffsetVO.setAllowReduceReclassify(Boolean.valueOf(true));
    }

    private int doTask(GcActionParamsVO paramsVO, String hbUnitId, TaskLog taskLog) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId) || !this.allow(paramsVO.getSchemeId(), paramsVO.getPeriodStr())) {
            return 0;
        }
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(systemId);
        List reclassifySubjectMappingList = optionVO.getReduceReclassifySubjectMappings();
        HashMap<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap = new HashMap<ArrayKey, List<GcOffSetVchrItemAdjustEO>>();
        Set<String> dimCodeSet = this.getDimCode(systemId, new ArrayList<Map<String, String>>());
        List<GcOffSetVchrItemAdjustEO> offsetList = this.listOffset(paramsVO, hbUnitId);
        Map<ArrayKey, Double> dim2OffsetAmtMap = this.groupByDim(offsetList, dimCodeSet, optionVO.getReclassifyOtherInfo(), dim2OffsetEOMap);
        int offsetEntryCount = 0;
        if (!CollectionUtils.isEmpty((Collection)reclassifySubjectMappingList)) {
            Date dateReclassify = DateUtils.now();
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.offsetitem.endcarryforward.reducereclassify.started"), Float.valueOf(25.0f)).syncTaskLog();
            for (ReduceReclassifySubjectMappingVO reclassifySubjectMappingVO : reclassifySubjectMappingList) {
                Map<ArrayKey, List<OffsetItem>> dim2offsetItemMap = this.groupByReclassifyMapping(paramsVO.getSchemeId(), paramsVO.getPeriodStr(), dim2OffsetAmtMap, reclassifySubjectMappingVO, dim2OffsetEOMap);
                int count = this.save(dim2offsetItemMap, reclassifySubjectMappingVO, paramsVO, dimCodeSet, dim2OffsetEOMap);
                offsetEntryCount += count;
            }
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.offsetitem.endcarryforward.reducereclassify.end") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)dateReclassify, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(26.0f));
        }
        return offsetEntryCount;
    }

    private int save(Map<ArrayKey, List<OffsetItem>> dim2offsetItemMap, ReduceReclassifySubjectMappingVO subjectMapping, GcActionParamsVO paramsVO, Set<String> dimCodeSet, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap) {
        int count = 0;
        for (Map.Entry<ArrayKey, List<OffsetItem>> entry : dim2offsetItemMap.entrySet()) {
            GcOffSetVchrDTO offSetVchrDTO = this.getGcOffSetVchrDTO(subjectMapping, paramsVO, dimCodeSet, entry, dim2OffsetEOMap);
            if (offSetVchrDTO == null) continue;
            this.adjustService.save(offSetVchrDTO);
            ++count;
        }
        return count;
    }

    private GcOffSetVchrDTO getGcOffSetVchrDTO(ReduceReclassifySubjectMappingVO subjectMapping, GcActionParamsVO paramsVO, Set<String> dimCodeSet, Map.Entry<ArrayKey, List<OffsetItem>> entry, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap) {
        ArrayKey key = entry.getKey();
        List<OffsetItem> offsetItemList = entry.getValue();
        double totalOffsetAmt = this.calcTotalOffsetAmt(offsetItemList);
        if (totalOffsetAmt == 0.0) {
            return null;
        }
        GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO();
        ArrayList<Object> offSetVchrItemList = new ArrayList<Object>();
        ArrayList<GcOffSetVchrItemDTO> reclassifyItemList = new ArrayList<GcOffSetVchrItemDTO>();
        List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList = dim2OffsetEOMap.get(key);
        if (CollectionUtils.isEmpty(gcOffSetVchrItemAdjustEOList)) {
            return null;
        }
        for (GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO : gcOffSetVchrItemAdjustEOList) {
            double offset = NumberUtils.sub((Double)gcOffSetVchrItemAdjustEO.getOffSetDebit(), (Double)gcOffSetVchrItemAdjustEO.getOffSetCredit());
            if (offset == 0.0) continue;
            GcOffSetVchrItemDTO offSetVchrItemDTO = this.initOffsetItem(paramsVO, gcOffSetVchrItemAdjustEO);
            this.repairReclassifyOffsetItem(offSetVchrItemDTO, subjectMapping, gcOffSetVchrItemAdjustEO);
            offSetVchrItemDTO.setmRecid(offSetVchrDTO.getMrecid());
            offSetVchrItemList.add(offSetVchrItemDTO);
        }
        for (GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO : gcOffSetVchrItemAdjustEOList) {
            GcOffSetVchrItemDTO reclassifyItemDTO = this.initOffsetItem(paramsVO, gcOffSetVchrItemAdjustEO);
            this.repairOriginOffsetItem(reclassifyItemDTO, gcOffSetVchrItemAdjustEO);
            reclassifyItemDTO.setmRecid(offSetVchrDTO.getMrecid());
            reclassifyItemList.add(reclassifyItemDTO);
        }
        offSetVchrItemList.addAll(reclassifyItemList);
        offSetVchrDTO.setItems(offSetVchrItemList);
        if (offSetVchrItemList.size() == 2 && ((GcOffSetVchrItemDTO)offSetVchrItemList.get(0)).getSubjectCode().equals(((GcOffSetVchrItemDTO)offSetVchrItemList.get(1)).getSubjectCode())) {
            return null;
        }
        return offSetVchrDTO;
    }

    private void repairOriginOffsetItem(GcOffSetVchrItemDTO offSetVchrItemDTO, GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO) {
        double offset = NumberUtils.sub((Double)gcOffSetVchrItemAdjustEO.getOffSetDebit(), (Double)gcOffSetVchrItemAdjustEO.getOffSetCredit());
        if (offset > 0.0) {
            offSetVchrItemDTO.setOffSetDebit(Double.valueOf(-offset));
            offSetVchrItemDTO.setOrient(OrientEnum.D);
        } else {
            offSetVchrItemDTO.setOffSetCredit(Double.valueOf(offset));
            offSetVchrItemDTO.setOrient(OrientEnum.C);
        }
        offSetVchrItemDTO.addFieldValue("ORIENT", (Object)offSetVchrItemDTO.getOrient().getValue());
        offSetVchrItemDTO.setMemo("\u539f\u62b5\u9500\u5206\u5f55");
    }

    private void repairReclassifyOffsetItem(GcOffSetVchrItemDTO offSetVchrItemDTO, ReduceReclassifySubjectMappingVO subjectMapping, GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO) {
        if (Objects.equals(gcOffSetVchrItemAdjustEO.getOrient(), OrientEnum.C.getValue())) {
            offSetVchrItemDTO.setOrient(OrientEnum.C);
            offSetVchrItemDTO.setOffSetCredit(gcOffSetVchrItemAdjustEO.getOffSetCredit());
            offSetVchrItemDTO.setSubjectCode(subjectMapping.getReclassifyCreditSubjectCode());
        } else {
            offSetVchrItemDTO.setOrient(OrientEnum.D);
            offSetVchrItemDTO.setOffSetDebit(gcOffSetVchrItemAdjustEO.getOffSetDebit());
            offSetVchrItemDTO.setSubjectCode(subjectMapping.getReclassifyDebitSubjectCode());
        }
        offSetVchrItemDTO.addFieldValue("ORIENT", (Object)offSetVchrItemDTO.getOrient().getValue());
        offSetVchrItemDTO.setMemo("\u62b5\u51cf\u91cd\u5206\u7c7b\u62b5\u9500\u5206\u5f55");
    }

    private GcOffSetVchrItemDTO initOffsetItem(GcActionParamsVO arg, GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO) {
        GcOffSetVchrItemDTO offSetVchrItemDTO = new GcOffSetVchrItemDTO();
        for (String dimCode : gcOffSetVchrItemAdjustEO.getFields().keySet()) {
            if ("RULEID".equals(dimCode) || "GCBUSINESSTYPECODE".equals(dimCode)) continue;
            offSetVchrItemDTO.addFieldValue(dimCode, gcOffSetVchrItemAdjustEO.getFieldValue(dimCode));
        }
        offSetVchrItemDTO.setTaskId(gcOffSetVchrItemAdjustEO.getTaskId());
        offSetVchrItemDTO.setSchemeId(arg.getSchemeId());
        offSetVchrItemDTO.setDefaultPeriod(arg.getPeriodStr());
        offSetVchrItemDTO.setAcctYear(arg.getAcctYear());
        offSetVchrItemDTO.setAcctPeriod(arg.getAcctPeriod());
        offSetVchrItemDTO.setCreateTime(Calendar.getInstance().getTime());
        offSetVchrItemDTO.setOrgType(arg.getOrgType());
        offSetVchrItemDTO.setId(UUIDOrderUtils.newUUIDStr());
        offSetVchrItemDTO.setOffSetSrcType(OffSetSrcTypeEnum.SUBJECT_REDUCE_RECLASSIFY);
        offSetVchrItemDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        offSetVchrItemDTO.setOffSetCurr(arg.getCurrency());
        offSetVchrItemDTO.setUnitId(gcOffSetVchrItemAdjustEO.getUnitId());
        offSetVchrItemDTO.setOppUnitId(gcOffSetVchrItemAdjustEO.getOppUnitId());
        offSetVchrItemDTO.setSubjectCode(gcOffSetVchrItemAdjustEO.getSubjectCode());
        return offSetVchrItemDTO;
    }

    private double calcTotalOffsetAmt(List<OffsetItem> offsetItemList) {
        double amt = 0.0;
        for (OffsetItem offsetItem : offsetItemList) {
            amt += offsetItem.offsetAmt.doubleValue();
        }
        return amt;
    }

    private Map<ArrayKey, List<OffsetItem>> groupByReclassifyMapping(String schemeId, String periodStr, Map<ArrayKey, Double> dim2OffsetAmtMap, ReduceReclassifySubjectMappingVO reclassifySubjectMappingVO, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap) {
        Set originSubjectCodes = this.listAllOriginSubjectCodes(schemeId, periodStr, reclassifySubjectMappingVO.getOriginSubjectCodes());
        Iterator<Map.Entry<ArrayKey, Double>> iterator = dim2OffsetAmtMap.entrySet().iterator();
        HashMap<ArrayKey, List<OffsetItem>> dim2offsetItemMap = new HashMap<ArrayKey, List<OffsetItem>>();
        while (iterator.hasNext()) {
            ArrayKey key = iterator.next().getKey();
            String subjectCode = (String)key.get(key.size() - 1);
            if (!originSubjectCodes.contains(subjectCode)) continue;
            OffsetItem offsetItem = new OffsetItem(subjectCode, dim2OffsetAmtMap.get(key));
            List dims = key.getValues().subList(0, key.size() - 1);
            ArrayKey originArrayKey = new ArrayKey(dims);
            dim2OffsetEOMap.put(new ArrayKey(dims), dim2OffsetEOMap.get(key));
            this.add(dim2offsetItemMap, originArrayKey, offsetItem);
            iterator.remove();
        }
        return dim2offsetItemMap;
    }

    private Set listAllOriginSubjectCodes(String schemeId, String periodStr, List<String> initOriginSubjectCodes) {
        HashSet<String> originSubjectCodes = new HashSet<String>(initOriginSubjectCodes);
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(schemeId, periodStr);
        initOriginSubjectCodes.forEach(subjectCode -> {
            Set subjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(subjectCode, reportSystemId);
            originSubjectCodes.addAll(subjectCodes);
        });
        return originSubjectCodes;
    }

    private <T> void add(Map<T, List<OffsetItem>> cacheMap, T key, OffsetItem offsetItem) {
        if (null == offsetItem) {
            return;
        }
        List<OffsetItem> offsetItemList = cacheMap.get(key);
        if (offsetItemList == null) {
            offsetItemList = new ArrayList<OffsetItem>();
            cacheMap.put(key, offsetItemList);
        }
        offsetItemList.add(offsetItem);
    }

    private Map<ArrayKey, Double> groupByDim(List<GcOffSetVchrItemAdjustEO> offsetList, Set<String> dimCodeSet, ReclassifyOtherInfoVO reclassifyOtherInfo, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap) {
        HashMap<ArrayKey, Double> dim2OffsetAmtMap = new HashMap<ArrayKey, Double>();
        for (GcOffSetVchrItemAdjustEO item : offsetList) {
            ArrayList<Object> dimList = new ArrayList<Object>();
            dimList.add(item.getUnitId());
            dimList.add(item.getOppUnitId());
            if (null == item.getElmMode() || !reclassifyOtherInfo.getElmModes().isEmpty() && !reclassifyOtherInfo.getElmModes().contains(item.getElmMode())) continue;
            for (String dimCode : dimCodeSet) {
                Object value = item.getFieldValue(dimCode);
                if (null == value || "".equals(value)) {
                    dimList.add("");
                    continue;
                }
                dimList.add(value);
            }
            dimList.add(item.getSubjectCode());
            ArrayKey dim = new ArrayKey(dimList);
            MapUtils.add(dim2OffsetAmtMap, (Object)dim, (Double)NumberUtils.sub((Double)item.getOffSetDebit(), (Double)item.getOffSetCredit()));
            List gcOffSetVchrItemAdjustEOList = dim2OffsetEOMap.getOrDefault(dim, new ArrayList());
            gcOffSetVchrItemAdjustEOList.add(item);
            dim2OffsetEOMap.put(dim, gcOffSetVchrItemAdjustEOList);
        }
        return dim2OffsetAmtMap;
    }

    private List<GcOffSetVchrItemAdjustEO> listOffset(GcActionParamsVO paramsVO, String hbUnitId) {
        QueryParamsVO queryParamsVO = this.buildOffseParamVO(paramsVO);
        queryParamsVO.setOrgId(hbUnitId);
        return this.listGcOffSetVchrItemAdjustEOS(queryParamsVO, true);
    }

    public QueryParamsVO buildOffseParamVO(GcActionParamsVO gcActionParamsVO) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setOrgType(gcActionParamsVO.getOrgType());
        queryParamsVO.setTaskId(gcActionParamsVO.getTaskId());
        queryParamsVO.setSchemeId(gcActionParamsVO.getSchemeId());
        queryParamsVO.setPeriodStr(gcActionParamsVO.getPeriodStr());
        queryParamsVO.setAcctPeriod(gcActionParamsVO.getAcctPeriod());
        queryParamsVO.setAcctYear(gcActionParamsVO.getAcctYear());
        queryParamsVO.setCurrency(gcActionParamsVO.getCurrency());
        queryParamsVO.setOrgId(gcActionParamsVO.getOrgId());
        queryParamsVO.setSelectAdjustCode(gcActionParamsVO.getSelectAdjustCode());
        return queryParamsVO;
    }

    private List<GcOffSetVchrItemAdjustEO> listGcOffSetVchrItemAdjustEOS(QueryParamsVO queryParamsVO, boolean deleteLastOffset) {
        boolean needDelAuto;
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.SUBJECT_REDUCE_RECLASSIFY.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        if (deleteLastOffset) {
            this.adjustService.deleteOffsetEntrys(queryParamsVO);
        }
        queryParamsVO.setOffSetSrcTypes(null);
        queryParamsVO.setQueryAllColumns(true);
        ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        queryParamsVO.setRules(optionVO.getReduceReclassifyOtherInfo().getRuleIds());
        ArrayList<Integer> elmModes = new ArrayList<Integer>();
        elmModes.addAll(optionVO.getReduceReclassifyOtherInfo().getElmModes());
        queryParamsVO.setElmModes(elmModes);
        boolean needDelInit = elmModes.contains(OffsetElmModeEnum.AUTO_ITEM.getValue()) && !elmModes.contains(OffsetElmModeEnum.INIT_ITEM.getValue());
        boolean bl = needDelAuto = elmModes.contains(OffsetElmModeEnum.INIT_ITEM.getValue()) && !elmModes.contains(OffsetElmModeEnum.AUTO_ITEM.getValue());
        if (elmModes.contains(OffsetElmModeEnum.INIT_ITEM.getValue())) {
            elmModes.remove((Object)OffsetElmModeEnum.AUTO_ITEM.getValue());
            elmModes.remove((Object)OffsetElmModeEnum.INIT_ITEM.getValue());
            elmModes.add(OffsetElmModeEnum.AUTO_ITEM.getValue());
        }
        List<GcOffSetVchrItemAdjustEO> offsetList = this.endCarryForwardDataSourceService.listWithOnlyItems(queryParamsVO, false);
        boolean isContainPhs = optionVO.getReduceReclassifyOtherInfo().isContainPhs();
        Set allInitOffSetSrcTypeValueSet = OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue();
        return offsetList.stream().filter(item -> {
            if (Objects.equals(1, item.getDisableFlag())) {
                return false;
            }
            if (!isContainPhs && (item.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.PHS.getSrcTypeValue() || "\u5e73\u8861\u6570".equals(item.getMemo()))) {
                return false;
            }
            if (item.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.SUBJECT_REDUCE_RECLASSIFY.getSrcTypeValue()) {
                return false;
            }
            boolean isInit = allInitOffSetSrcTypeValueSet.contains(item.getOffSetSrcType());
            if (needDelInit && isInit) {
                return false;
            }
            return !needDelAuto || isInit;
        }).collect(Collectors.toList());
    }

    private Set<String> getDimCode(String systemId, List<Map<String, String>> reclassifyDimension) {
        Assert.isNotEmpty((String)systemId, (String)"\u672a\u5173\u8054\u4f53\u7cfb", (Object[])new Object[0]);
        ConsolidatedOptionVO consolidatedOption = this.optionService.getOptionData(systemId);
        List dimensionIds = consolidatedOption.getReduceReclassifyOtherInfo().getDimensionIds();
        HashSet<String> dimCodeSet = new HashSet<String>();
        if (CollectionUtils.isEmpty((Collection)dimensionIds)) {
            return dimCodeSet;
        }
        List tableDimensionList = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM");
        Map tableDimId2EoMap = tableDimensionList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        for (String dimensionId : dimensionIds) {
            DimensionEO dimensionEO = (DimensionEO)tableDimId2EoMap.get(dimensionId);
            if (null == dimensionEO) continue;
            dimCodeSet.add(dimensionEO.getCode());
            HashMap<String, String> dimension = new HashMap<String, String>();
            dimension.put("code", dimensionEO.getCode());
            dimension.put("title", dimensionEO.getTitle());
            reclassifyDimension.add(dimension);
        }
        return dimCodeSet;
    }

    private List<GcOffSetVchrItemDTO> listCurrReclassifyDataResult(QueryParamsVO paramsVO, String systemId, ConsolidatedOptionVO optionVO, Set<String> dimCodeSet) {
        List<GcOffSetVchrItemAdjustEO> offsetList = this.listGcOffSetVchrItemAdjustEOS(paramsVO, false);
        HashMap<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap = new HashMap<ArrayKey, List<GcOffSetVchrItemAdjustEO>>();
        Map<ArrayKey, Double> dim2OffsetAmtMap = this.groupByDim(offsetList, dimCodeSet, optionVO.getReduceReclassifyOtherInfo(), dim2OffsetEOMap);
        List reclassifySubjectMappingList = optionVO.getReduceReclassifySubjectMappings();
        TaskLog taskLog = new TaskLog();
        ArrayList<GcOffSetVchrItemDTO> currReclassifyDataResult = new ArrayList<GcOffSetVchrItemDTO>();
        for (ReduceReclassifySubjectMappingVO reclassifySubjectMappingVO : reclassifySubjectMappingList) {
            Map<ArrayKey, List<OffsetItem>> dim2offsetItemMap = this.groupByReclassifyMapping(paramsVO.getSchemeId(), paramsVO.getPeriodStr(), dim2OffsetAmtMap, reclassifySubjectMappingVO, dim2OffsetEOMap);
            currReclassifyDataResult.addAll(this.listCurrReclassifyResult(dim2offsetItemMap, reclassifySubjectMappingVO, paramsVO, dimCodeSet, dim2OffsetEOMap));
        }
        return currReclassifyDataResult;
    }

    private List<GcOffSetVchrItemDTO> listCurrReclassifyResult(Map<ArrayKey, List<OffsetItem>> dim2offsetItemMap, ReduceReclassifySubjectMappingVO reclassifySubjectMappingVO, QueryParamsVO paramsVO, Set<String> dimCodeSet, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> dim2OffsetEOMap) {
        ArrayList<GcOffSetVchrItemDTO> offSetVchrDTOList = new ArrayList<GcOffSetVchrItemDTO>();
        GcActionParamsVO actionParamsVO = new GcActionParamsVO();
        BeanUtils.copyProperties(paramsVO, actionParamsVO);
        for (Map.Entry<ArrayKey, List<OffsetItem>> entry : dim2offsetItemMap.entrySet()) {
            GcOffSetVchrDTO offSetVchrDTO = this.getGcOffSetVchrDTO(reclassifySubjectMappingVO, actionParamsVO, dimCodeSet, entry, dim2OffsetEOMap);
            if (offSetVchrDTO == null) continue;
            offSetVchrDTOList.addAll(offSetVchrDTO.getItems());
        }
        return offSetVchrDTOList;
    }

    class OffsetItem {
        private String subjectCode;
        private Double offsetAmt;

        OffsetItem(String subjectCode, Double offsetAmt) {
            this.subjectCode = subjectCode;
            this.offsetAmt = offsetAmt;
        }
    }
}

