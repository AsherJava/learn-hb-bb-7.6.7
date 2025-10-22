/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils
 *  com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.GcOffsetItemCoreService;
import com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil;
import com.jiuqi.gcreport.offsetitem.utils.GcInputAdjustConvertUtil;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcInputAdjustServiceImpl
implements GcInputAdjustService {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcOffSetAppOffsetService GcOffsetItemService;
    @Autowired
    private GcOffSetItemAdjustCoreService coreService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedFormulaService consolidatedFormulaService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired(required=false)
    private GcOffsetItemCoreService gcOffsetItemCoreService;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired
    private IRuntimeTaskService taskService;
    private static DecimalFormat df = new DecimalFormat("###,##0.00");
    private static DecimalFormat periodFormat = new DecimalFormat("0000");

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addInputAdjust(List<List<GcInputAdjustVO>> batchlist) {
        Collections.reverse(batchlist);
        for (List<GcInputAdjustVO> list2 : batchlist) {
            for (GcInputAdjustVO oneRow : list2) {
                if (oneRow.getDebit() != null) {
                    oneRow.setDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)oneRow.getDebit())));
                }
                if (oneRow.getCredit() != null) {
                    oneRow.setCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)oneRow.getCredit())));
                }
                Map unSysFields = oneRow.getUnSysFields();
                for (Map.Entry unSysField : unSysFields.entrySet()) {
                    if (unSysField.getValue() instanceof Map) {
                        unSysFields.put(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                    } else {
                        unSysFields.put(unSysField.getKey(), unSysField.getValue());
                    }
                    if (!StringUtils.isEmpty((String)ConverterUtils.getAsString(unSysFields.get(unSysField.getKey())))) continue;
                    unSysFields.put(unSysField.getKey(), null);
                }
            }
        }
        this.checkSubject(batchlist);
        ArrayList<GcOffSetVchrDTO> batchDtoList = new ArrayList<GcOffSetVchrDTO>();
        ArrayList<String> srcOffsetGroupIds = new ArrayList<String>();
        HashMap<String, String> mrecidMap = new HashMap<String, String>();
        GcInputAdjustVO initInputAdjustVO = batchlist.get(0).get(0);
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskBySchemeId(initInputAdjustVO.getSchemeId(), initInputAdjustVO.getDefaultPeriod());
        List<ConsolidatedFormulaVO> consFormulaCalc = this.getConsFormulaCalc(taskVO.getSystemId());
        boolean isExistAdjust = DimensionUtils.isExistAdjust((String)batchlist.get(0).get(0).getTaskId());
        Map<String, List<AdjustPeriodVO>> period2VO = this.getLaterAdjustByType(batchlist.get(0).get(0), isExistAdjust);
        batchlist.forEach(list -> {
            GcOffSetVchrDTO vchrDTO = new GcOffSetVchrDTO();
            String mrecid = UUIDOrderSnowUtils.newUUIDStr();
            String srcOffsetGroupId = UUIDOrderUtils.newUUIDStr();
            OffsetVchrCodeDTO vchrCodeDTO = new OffsetVchrCodeDTO();
            vchrCodeDTO.setPeriodType(initInputAdjustVO.getPeriodType());
            vchrCodeDTO.setAcctYear(initInputAdjustVO.getAcctYear());
            vchrCodeDTO.setAcctPeriod(initInputAdjustVO.getAcctPeriod());
            String vchrCode = OffsetVchrCodeUtil.createVchrCode((OffsetVchrCodeDTO)vchrCodeDTO);
            vchrDTO.setMrecid(mrecid);
            vchrDTO.setVchrCode(vchrCode);
            if (!StringUtils.isEmpty((String)((GcInputAdjustVO)list.get(0)).getmRecid())) {
                mrecidMap.put(((GcInputAdjustVO)list.get(0)).getmRecid(), mrecid);
            }
            HashSet unitIdSet = new HashSet();
            list.forEach(inputAdjustVO -> {
                if (inputAdjustVO.getUnitVo() != null) {
                    inputAdjustVO.setUnitId(inputAdjustVO.getUnitVo().getCode());
                    unitIdSet.add(inputAdjustVO.getUnitId());
                }
                if (inputAdjustVO.getOppUnitVo() != null) {
                    inputAdjustVO.setOppUnitId(inputAdjustVO.getOppUnitVo().getCode());
                    unitIdSet.add(inputAdjustVO.getOppUnitId());
                }
            });
            if (unitIdSet.size() == 2) {
                ArrayList unitIdList = new ArrayList(unitIdSet);
                for (GcInputAdjustVO item : list) {
                    if (StringUtils.isEmpty((String)item.getUnitId())) {
                        item.setUnitId(((String)unitIdList.get(0)).equals(item.getOppUnitId()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                        continue;
                    }
                    if (!StringUtils.isEmpty((String)item.getOppUnitId())) continue;
                    item.setOppUnitId(((String)unitIdList.get(0)).equals(item.getUnitId()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                }
            }
            ArrayList<GcOffSetVchrItemDTO> itemList = new ArrayList<GcOffSetVchrItemDTO>();
            for (GcInputAdjustVO inputAdjustVO2 : list) {
                if (inputAdjustVO2.getSrcID() != null && !srcOffsetGroupIds.contains(inputAdjustVO2.getSrcID())) {
                    srcOffsetGroupIds.add(inputAdjustVO2.getSrcID());
                    srcOffsetGroupId = inputAdjustVO2.getSrcID();
                }
                Assert.isTrue((null != inputAdjustVO2.getInputUnitId() ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.unitCannotBeEmpty"), (Object[])new Object[0]);
                inputAdjustVO2.setmRecid(mrecid);
                inputAdjustVO2.setSrcID(srcOffsetGroupId);
                GcOffSetVchrItemDTO offsetItemDto = this.convertVOToOffsetDTO(inputAdjustVO2, taskVO);
                offsetItemDto.setmRecid(mrecid);
                offsetItemDto.setVchrCode(vchrCode);
                offsetItemDto.setSelectAdjustCode(inputAdjustVO2.getSelectAdjustCode());
                this.exeConsFormulaCalcSingle(offsetItemDto, consFormulaCalc, this.convertVO2EO((List<GcInputAdjustVO>)list));
                itemList.add(offsetItemDto);
            }
            vchrDTO.setItems(itemList);
            batchDtoList.add(vchrDTO);
            if (!((GcInputAdjustVO)list.get(0)).getEffectType().equals(EFFECTTYPE.MONTH.getCode())) {
                List<LaterPeriodVO> laterPeriodVOList = this.getLaterPeriodByType((GcInputAdjustVO)list.get(0), isExistAdjust, period2VO);
                for (LaterPeriodVO laterPeriodVO : laterPeriodVOList) {
                    this.add((List<GcInputAdjustVO>)list, laterPeriodVO, taskVO, (List<GcOffSetVchrDTO>)batchDtoList);
                }
            }
        });
        int acctYear = ((GcOffSetVchrItemDTO)((GcOffSetVchrDTO)batchDtoList.get(0)).getItems().get(0)).getAcctYear();
        int acctPeriod = ((GcOffSetVchrItemDTO)((GcOffSetVchrDTO)batchDtoList.get(0)).getItems().get(0)).getAcctPeriod();
        if (srcOffsetGroupIds.size() > 0) {
            this.deleteBySrcOffsetGroupIds(null, srcOffsetGroupIds, acctYear, 1, acctPeriod, null);
        }
        this.batchSaveBySrcGroupId(batchDtoList);
        this.addInputAdjustLogs(mrecidMap, batchlist);
        if (mrecidMap.size() != 0) {
            this.updateWriteOffSrcGroupId(mrecidMap);
        }
    }

    private List<DefaultTableEntity> convertVO2EO(List<GcInputAdjustVO> voList) {
        ArrayList<DefaultTableEntity> gcOffSetVchrItemAdjustEOList = new ArrayList<DefaultTableEntity>();
        if (CollectionUtils.isEmpty(voList)) {
            return null;
        }
        voList.forEach(vo -> {
            GcOffSetVchrItemAdjustEO eo = new GcOffSetVchrItemAdjustEO();
            BeanUtils.copyProperties(vo, eo);
            eo.setOffSetCredit(vo.getCredit());
            eo.setOffSetDebit(vo.getDebit());
            eo.setOffSetCurr(vo.getCurrencyCode());
            eo.setAdjust(vo.getSelectAdjustCode());
            vo.getUnSysFields().forEach((arg_0, arg_1) -> ((GcOffSetVchrItemAdjustEO)eo).addFieldValue(arg_0, arg_1));
            gcOffSetVchrItemAdjustEOList.add((DefaultTableEntity)eo);
        });
        return gcOffSetVchrItemAdjustEOList;
    }

    private List<DefaultTableEntity> convertDTO2EO(List<GcOffSetVchrItemDTO> dtoList) {
        ArrayList<DefaultTableEntity> gcOffSetVchrItemAdjustEOList = new ArrayList<DefaultTableEntity>();
        if (CollectionUtils.isEmpty(dtoList)) {
            return null;
        }
        dtoList.forEach(dto -> {
            GcOffSetVchrItemAdjustEO eo = new GcOffSetVchrItemAdjustEO();
            BeanUtils.copyProperties(dto, eo);
            eo.setAdjust(dto.getSelectAdjustCode());
            gcOffSetVchrItemAdjustEOList.add((DefaultTableEntity)eo);
        });
        return gcOffSetVchrItemAdjustEOList;
    }

    private void add(List<GcInputAdjustVO> list, LaterPeriodVO laterPeriodVO, ConsolidatedTaskVO taskVO, List<GcOffSetVchrDTO> batchDtoList) {
        String mid2 = UUIDOrderSnowUtils.newUUIDStr();
        GcOffSetVchrDTO vchrDTO2 = new GcOffSetVchrDTO();
        vchrDTO2.setMrecid(mid2);
        vchrDTO2.setVchrCode(batchDtoList.get(0).getVchrCode());
        ArrayList<GcOffSetVchrItemDTO> itemList2 = new ArrayList<GcOffSetVchrItemDTO>();
        for (GcInputAdjustVO inputAdjustVO : list) {
            GcInputAdjustVO laterVo = new GcInputAdjustVO();
            BeanUtils.copyProperties(inputAdjustVO, laterVo);
            laterVo.setId(UUIDOrderUtils.newUUIDStr());
            laterVo.setmRecid(mid2);
            laterVo.setAcctPeriod(laterPeriodVO.getCurrPeriod().intValue());
            String defaultPeriod = YearPeriodUtil.transform(null, (int)laterVo.getAcctYear(), (int)list.get(0).getPeriodType(), (int)laterPeriodVO.getCurrPeriod()).toString();
            laterVo.setDefaultPeriod(defaultPeriod);
            laterVo.setSrcID(list.get(0).getSrcID());
            GcOffSetVchrItemDTO offsetItemDto2 = this.convertVOToOffsetDTO(laterVo, taskVO);
            offsetItemDto2.setmRecid(vchrDTO2.getMrecid());
            offsetItemDto2.setSelectAdjustCode(laterPeriodVO.getAdjustPeriodVO().getCode());
            itemList2.add(offsetItemDto2);
        }
        vchrDTO2.setItems(itemList2);
        batchDtoList.add(vchrDTO2);
    }

    @Override
    public void exeConsFormulaCalcOneGroup(GcOffSetVchrDTO offSetVchrDTO) {
        String consFormulaCalcType = offSetVchrDTO.getConsFormulaCalcType();
        if (StringUtils.isEmpty((String)consFormulaCalcType)) {
            return;
        }
        List consolidatedFormulaVOS = this.consolidatedFormulaService.listConsFormulas(this.getSystemId((GcOffSetVchrItemDTO)offSetVchrDTO.getItems().get(0)));
        List filtedFormulaVOS = consolidatedFormulaVOS.stream().filter(item -> {
            if ("inputFlag".equals(consFormulaCalcType)) {
                return Objects.equals(1, item.getInputFlag());
            }
            if ("autoFlag".equals(consFormulaCalcType)) {
                return Objects.equals(1, item.getAntoFlag());
            }
            if ("manualFlag".equals(consFormulaCalcType)) {
                return Objects.equals(1, item.getManualFlag());
            }
            return false;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filtedFormulaVOS)) {
            offSetVchrDTO.getItems().forEach(offsetDTO -> this.exeConsFormulaCalcSingle((GcOffSetVchrItemDTO)offsetDTO, filtedFormulaVOS, this.convertDTO2EO(offSetVchrDTO.getItems())));
        }
    }

    @Override
    public void exeConsFormulaCalcSingle(GcOffSetVchrItemDTO offsetDTO, List<ConsolidatedFormulaVO> consFormulaVOS, List<DefaultTableEntity> list) {
        OffSetUnSysDimensionCache.load();
        List dimensionVOs = OffSetUnSysDimensionCache.allDimValue((String)this.getSystemId(offsetDTO));
        Set<String> dimensionKeySet = dimensionVOs.stream().map(dimensionVO -> dimensionVO.getCode()).collect(Collectors.toSet());
        GcOffSetVchrItemAdjustEO offsetEO = new GcOffSetVchrItemAdjustEO();
        BeanUtils.copyProperties(offsetDTO, offsetEO);
        offsetEO.setDisableFlag(Integer.valueOf(Objects.equals(Boolean.TRUE, offsetDTO.getDisableFlag()) ? 1 : 0));
        offsetEO.setAdjust(offsetDTO.getSelectAdjustCode());
        Map unSysField = offsetDTO.getUnSysFields();
        dimensionKeySet.forEach(dimensionKey -> {
            if (!unSysField.containsKey(dimensionKey)) {
                unSysField.put(dimensionKey, offsetDTO.getFields().get(dimensionKey));
            }
        });
        unSysField.forEach((key, value) -> {
            offsetDTO.getFields().put(key, offsetDTO.getUnSysFieldValue(key));
            offsetEO.getFields().put(key, offsetDTO.getUnSysFieldValue(key));
        });
        consFormulaVOS.forEach(consFormulaVo -> {
            String formula = consFormulaVo.getFormula();
            List ruleIdList = consFormulaVo.getRuleIds();
            if (CollectionUtils.isEmpty((Collection)ruleIdList) || !StringUtils.isEmpty((String)offsetDTO.getRuleId()) && ruleIdList.contains(offsetDTO.getRuleId())) {
                GcFormulaUtils.execute((GcTaskBaseArguments)this.getTaskBaseArguments(offsetEO), (DefaultTableEntity)offsetEO, (String)formula, (List)list);
                if (formula.contains("CREDIT") && null == offsetDTO.getOrient() || formula.contains("CREDIT") && offsetDTO.getOrient().equals((Object)OrientEnum.C)) {
                    Double offsetCredit = ConverterUtils.getAsDouble((Object)offsetEO.getOffSetCredit());
                    offsetDTO.setCredit(offsetCredit);
                    offsetDTO.setOffSetCredit(offsetCredit);
                } else if (formula.contains("DEBIT") && null == offsetDTO.getOrient() || formula.contains("DEBIT") && offsetDTO.getOrient().equals((Object)OrientEnum.D)) {
                    Double offsetDebit = ConverterUtils.getAsDouble((Object)offsetEO.getOffSetDebit());
                    offsetDTO.setDebit(offsetDebit);
                    offsetDTO.setOffSetDebit(offsetDebit);
                }
                Map unSysFields = offsetDTO.getUnSysFields();
                if (!CollectionUtils.isEmpty(unSysFields.keySet())) {
                    unSysFields.keySet().forEach(key -> {
                        if (formula.contains((CharSequence)key)) {
                            unSysFields.put(key, offsetEO.getFieldValue(key));
                            offsetDTO.getFields().put(key, offsetEO.getFieldValue(key));
                        }
                    });
                }
                BeanUtils.copyProperties(offsetEO, offsetDTO);
            }
        });
    }

    private String getSystemId(GcOffSetVchrItemDTO gcOffSetVchrItemDTO) {
        String systemId = gcOffSetVchrItemDTO.getSystemId();
        if (StringUtils.isEmpty((String)gcOffSetVchrItemDTO.getSystemId())) {
            ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(gcOffSetVchrItemDTO.getTaskId(), gcOffSetVchrItemDTO.getDefaultPeriod());
            systemId = taskVO.getSystemId();
        }
        return systemId;
    }

    private GcTaskBaseArguments getTaskBaseArguments(GcOffSetVchrItemAdjustEO offsetEO) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setPeriodStr(offsetEO.getDefaultPeriod());
        arguments.setCurrency(null == offsetEO.getOffSetCurr() ? "CNY" : offsetEO.getOffSetCurr());
        arguments.setOrgType(null == offsetEO.getOrgType() ? "MD_ORG_CORPORATE" : offsetEO.getOrgType());
        arguments.setOrgId(offsetEO.getUnitId());
        arguments.setTaskId(offsetEO.getTaskId());
        arguments.setSelectAdjustCode(offsetEO.getAdjust());
        return arguments;
    }

    private void addInputAdjustLogs(Map<String, String> mrecidMap, List<List<GcInputAdjustVO>> batchlist) {
        if (CollectionUtils.isEmpty(batchlist) || CollectionUtils.isEmpty((Collection)batchlist.get(0))) {
            return;
        }
        GcInputAdjustVO adjustVO = batchlist.get(0).get(0);
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(adjustVO.getDefaultPeriod()));
        StringBuilder logs = new StringBuilder();
        logs.append(String.format("\u8f93\u5165\u8c03\u6574%1$d\u7ec4;", batchlist.size()));
        logs.append(" \u4efb\u52a1-").append(GcOffsetItemUtils.getTaskTitle(adjustVO.getTaskId()));
        logs.append(";\n \u65f6\u671f-").append(periodTitle);
        logs.append(";\n \u5408\u5e76\u5355\u4f4d-").append(adjustVO.getInputUnitId());
        for (int i = 0; i < batchlist.size(); ++i) {
            List<GcInputAdjustVO> adjustVOs = batchlist.get(i);
            adjustVO = adjustVOs.get(0);
            String operateType = mrecidMap.values().contains(adjustVO.getmRecid()) ? "\u4fee\u6539" : "\u65b0\u589e";
            logs.append(";\n :").append(operateType).append("  ");
            UnionRuleVO unionRuleVO = this.unionRuleService.selectUnionRuleById(adjustVO.getUnionRuleId());
            logs.append("; \u89c4    \u5219-").append(unionRuleVO.getTitle());
            logs.append(";  \u672c\u65b9\u5355\u4f4d-").append(adjustVOs.get(0).getUnitId());
            logs.append("; \u5bf9\u65b9\u5355\u4f4d-").append(adjustVOs.get(0).getOppUnitId());
            logs.append("; \u5f71\u54cd\u671f\u95f4-").append(EFFECTTYPE.getTitleByCode((String)adjustVO.getEffectType()));
        }
        String operationTitle = String.format("\u8f93\u5165\u8c03\u6574-\u4efb\u52a1%1$s-\u65f6\u671f%2$s", GcOffsetItemUtils.getTaskTitle(adjustVO.getTaskId()), periodTitle);
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)operationTitle, (String)logs.toString());
    }

    @Override
    public List<GcInputAdjustVO> queryDetailByMrecid(GcInputAdjustQueryCondi condi) {
        String[] columnNamesInDB = new String[]{"mrecid", "DATATIME"};
        Object[] values = new Object[]{condi.getmRecid(), condi.getDefaultPeriod()};
        List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS = this.GcOffsetItemService.listOffsetRecordsByWhere(columnNamesInDB, values);
        return offSetVchrItemAdjustEOS.stream().map(offsetEOItem -> this.convertEOToVO((GcOffSetVchrItemAdjustEO)offsetEOItem, condi.getOrgTypeId())).collect(Collectors.toList());
    }

    @Override
    public List<List<GcInputAdjustVO>> queryDetailByMrecidList(GcInputAdjustQueryCondi condi) {
        ArrayList<List<GcInputAdjustVO>> gcInputAdjustVOS = new ArrayList<List<GcInputAdjustVO>>();
        condi.getmRecidList().forEach(mRecid -> {
            condi.setmRecid(mRecid);
            List<GcInputAdjustVO> inputAdjustVOS = this.queryDetailByMrecid(condi);
            if (!CollectionUtils.isEmpty(inputAdjustVOS)) {
                gcInputAdjustVOS.add(inputAdjustVOS);
            }
        });
        return gcInputAdjustVOS;
    }

    private void setAgingForSubjectCode(GcInputAdjustVO gcInputAdjustVO, GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO) {
        List<String> agingCodeList = this.gcOffsetItemCoreService.getAgingBySubjectCode(gcInputAdjustVO.getSubjectCode(), gcInputAdjustVO.getTaskId());
        agingCodeList.forEach(agingCode -> {
            if (gcOffSetVchrItemAdjustEO.getFieldValue("AGE_" + agingCode) != null) {
                gcInputAdjustVO.addUnSysFieldValue("AGE_" + agingCode, gcOffSetVchrItemAdjustEO.getFieldValue("AGE_" + agingCode));
            }
        });
    }

    @Override
    public void deleteBySrcId(GcInputAdjustDelCondi condi) {
        int effectType;
        ArrayList<String> srcOffsetGroupIds = new ArrayList();
        if (condi.getSrcidsYear().size() > 0) {
            srcOffsetGroupIds = condi.getSrcidsYear();
            effectType = 1;
        } else {
            srcOffsetGroupIds = condi.getSrcidsMonth();
            effectType = -1;
        }
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return;
        }
        int acctYear = condi.getAcctYear();
        this.addAdjustInputDeleteLogs(condi, srcOffsetGroupIds);
        this.deleteBySrcOffsetGroupIds(condi.getTaskId(), srcOffsetGroupIds, acctYear, effectType, condi.acctPeriod, null);
    }

    @Override
    public Map<String, Map<String, Object>> queryByGroupIds(Set<String> groupIds) {
        String[] columnNamesInDB = new String[]{"srcOffsetGroupId"};
        Object[] values = new Object[]{groupIds};
        List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjusts = this.GcOffsetItemService.listOffsetRecordsByWhere(columnNamesInDB, values);
        if (Objects.isNull(offSetVchrItemAdjusts)) {
            return new HashMap<String, Map<String, Object>>();
        }
        Map<String, Map<String, Object>> collect = offSetVchrItemAdjusts.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getSrcOffsetGroupId, Collectors.collectingAndThen(Collectors.toList(), list -> (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString(list.get(0)), Map.class))));
        return collect;
    }

    @Override
    public GcInputAdjustVO consFormulaCalc(GcInputAdjustVO gcInputAdjustVO) {
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskBySchemeId(gcInputAdjustVO.getSchemeId(), gcInputAdjustVO.getDefaultPeriod());
        GcOffSetVchrItemDTO offSetDTO = this.convertVOToOffsetDTO(gcInputAdjustVO, taskVO);
        if (StringUtils.isEmpty((String)offSetDTO.getRuleId())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.pleaseSelectRule"));
        }
        List<ConsolidatedFormulaVO> filtedFormulaVOS = this.getConsFormulaCalc(offSetDTO.getSystemId());
        this.exeConsFormulaCalcSingle(offSetDTO, filtedFormulaVOS, null);
        return this.convertDTOToVO(offSetDTO, gcInputAdjustVO.getUnitVersion());
    }

    private List<ConsolidatedFormulaVO> getConsFormulaCalc(String systemId) {
        List consolidatedFormulaVOS = this.consolidatedFormulaService.listConsFormulas(systemId);
        List<ConsolidatedFormulaVO> filtedFormulaVOS = consolidatedFormulaVOS.stream().filter(item -> item.getInputFlag() == 1).collect(Collectors.toList());
        return filtedFormulaVOS;
    }

    private void addAdjustInputDeleteLogs(GcInputAdjustDelCondi condi, List<String> srcOffsetGroupIds) {
        List vchrItemAdjustEOList = this.coreService.listByWhere(new String[]{"srcOffsetGroupId"}, new Object[]{srcOffsetGroupIds});
        if (CollectionUtils.isEmpty((Collection)vchrItemAdjustEOList)) {
            return;
        }
        Set mRecidSet = vchrItemAdjustEOList.stream().map(GcOffSetVchrItemAdjustEO::getmRecid).collect(Collectors.toSet());
        StringBuilder logs = new StringBuilder();
        GcOffSetVchrItemAdjustEO itemEo = (GcOffSetVchrItemAdjustEO)vchrItemAdjustEOList.get(0);
        YearPeriodObject yp = new YearPeriodObject(null, itemEo.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condi.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(itemEo.getUnitId()), tool.getOrgByCode(itemEo.getOppUnitId()));
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(itemEo.getDefaultPeriod()));
        logs.append(String.format("\u8f93\u5165\u8c03\u6574\u5220\u9664%1$d\u7ec4;", mRecidSet.size()));
        logs.append(" \u4efb\u52a1-").append(GcOffsetItemUtils.getTaskTitle(itemEo.getTaskId()));
        logs.append(";\u65f6\u671f-").append(periodTitle);
        logs.append(";\u5408\u5e76\u5355\u4f4d-").append(commonUnit.getCode());
        UnionRuleVO unionRuleVO = this.unionRuleService.selectUnionRuleById(itemEo.getRuleId());
        logs.append(";\u89c4    \u5219-").append(unionRuleVO.getTitle());
        logs.append(";\u672c\u65b9\u5355\u4f4d-").append(itemEo.getUnitId());
        logs.append(";\u5bf9\u65b9\u5355\u4f4d-").append(itemEo.getOppUnitId());
        logs.append(";\u5f71\u54cd\u671f\u95f4-").append(EFFECTTYPE.getTitleByCode((String)itemEo.getEffectType()));
        String operationTitle = String.format("\u8f93\u5165\u8c03\u6574\u5220\u9664-\u4efb\u52a1%1$s-\u65f6\u671f%2$s", GcOffsetItemUtils.getTaskTitle(itemEo.getTaskId()), periodTitle);
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)operationTitle, (String)logs.toString());
    }

    @Override
    public GcInputAdjustVO convertDTOToVO(GcOffSetVchrItemDTO offSetDTO, String orgType) {
        return GcInputAdjustConvertUtil.getInstance().convertDTOToVO(offSetDTO, orgType);
    }

    @Override
    public GcInputAdjustVO convertEOToVO(GcOffSetVchrItemAdjustEO offSetEO, String orgType) {
        return GcInputAdjustConvertUtil.getInstance().convertEOToVO(offSetEO, orgType);
    }

    private void checkSubject(List<List<GcInputAdjustVO>> batchlist) {
        for (List<GcInputAdjustVO> voList : batchlist) {
            boolean hasCASH = false;
            boolean hasNotCASH = false;
            for (GcInputAdjustVO vo : voList) {
                ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(vo.getSchemeId(), vo.getSubjectCode());
                if (subject == null) continue;
                if (subject.getAttri() != null && SubjectAttributeEnum.CASH.getValue() == subject.getAttri().intValue()) {
                    hasCASH = true;
                } else {
                    hasNotCASH = true;
                }
                if (!hasCASH || !hasNotCASH) continue;
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.cashFlowNotOffset"));
            }
        }
    }

    private GcOffSetVchrItemDTO convertVOToOffsetDTO(GcInputAdjustVO inputAdjustVO, ConsolidatedTaskVO taskVO) {
        GcOffSetVchrItemDTO dto = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties(inputAdjustVO, dto);
        dto.setId(UUIDOrderUtils.newUUIDStr());
        dto.setOrgType(inputAdjustVO.getUnitVersion());
        if (null != taskVO && null != taskVO.getInputTaskInfo() && inputAdjustVO.getUnitVersion().equals(taskVO.getInputTaskInfo().getUnitDefine())) {
            dto.setOrgType("NONE");
        }
        if (Boolean.TRUE.equals(inputAdjustVO.getManageCalcUnitCode())) {
            dto.setOrgType("NONE");
        }
        if (inputAdjustVO.getOffSetSrcType() == OffSetSrcTypeEnum.WRITE_OFF.getSrcTypeValue()) {
            dto.setOrgType(inputAdjustVO.getUnitVersion());
        }
        dto.setRuleId(inputAdjustVO.getUnionRuleId());
        dto.setElmMode(inputAdjustVO.getElmMode());
        dto.setSortOrder(Double.valueOf(inputAdjustVO.getSortOrder()));
        if (inputAdjustVO.getDebit() != null) {
            dto.setOffSetDebit(inputAdjustVO.getDebit());
        } else if (inputAdjustVO.getCredit() != null) {
            dto.setOffSetCredit(inputAdjustVO.getCredit());
        }
        dto.setPostFlag(Boolean.valueOf(true));
        dto.setOffSetCurr(inputAdjustVO.getCurrencyCode());
        dto.setSrcOffsetGroupId(inputAdjustVO.getSrcID());
        dto.setSrcId(inputAdjustVO.getId());
        dto.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)inputAdjustVO.getOffSetSrcType()));
        dto.setEffectType(inputAdjustVO.getEffectType());
        dto.setCreateUser(NpContextHolder.getContext().getUserName());
        return dto;
    }

    public List<LaterPeriodVO> getLaterPeriodByType(GcInputAdjustVO gcInputAdjustVO, boolean isExistAdjust, Map<String, List<AdjustPeriodVO>> period2VO) {
        Integer[] periods;
        ArrayList<LaterPeriodVO> laterPeriodVOList = new ArrayList<LaterPeriodVO>();
        int periodType = gcInputAdjustVO.getPeriodType();
        int currPeriod = gcInputAdjustVO.getAcctPeriod();
        int currYear = gcInputAdjustVO.getAcctYear();
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (periodType == 1) {
            result.add(1);
        } else if (periodType == 2) {
            result.add(1);
            result.add(2);
        } else if (periodType == 3) {
            result.add(1);
            result.add(2);
            result.add(3);
            result.add(4);
        } else if (periodType == 4) {
            for (int i = 1; i <= 12; ++i) {
                result.add(i);
            }
        } else {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.notSupportPeriodType", (Object[])new Object[]{periodType}));
        }
        for (Integer period : periods = result.toArray(new Integer[0])) {
            LaterPeriodVO laterPeriodVO;
            AdjustPeriodVO adjustPeriodVO;
            int index;
            if (period == currPeriod && period2VO.containsKey(gcInputAdjustVO.getDefaultPeriod())) {
                boolean hasAdjust = false;
                for (index = 0; index < period2VO.get(gcInputAdjustVO.getDefaultPeriod()).size(); ++index) {
                    adjustPeriodVO = period2VO.get(gcInputAdjustVO.getDefaultPeriod()).get(index);
                    if (gcInputAdjustVO.getSelectAdjustCode().equals("0")) {
                        hasAdjust = true;
                    }
                    if (hasAdjust) {
                        laterPeriodVO = new LaterPeriodVO();
                        laterPeriodVO.setCurrPeriod(period);
                        laterPeriodVO.setAdjustPeriodVO(adjustPeriodVO);
                        laterPeriodVOList.add(laterPeriodVO);
                    }
                    if (!adjustPeriodVO.getCode().equals(gcInputAdjustVO.getSelectAdjustCode())) continue;
                    hasAdjust = true;
                }
            }
            if (period <= currPeriod) continue;
            PeriodWrapper nextPeriod = new PeriodWrapper(currYear, periodType, period.intValue());
            if (period2VO.containsKey(nextPeriod.toString())) {
                for (index = 0; index < period2VO.get(nextPeriod.toString()).size(); ++index) {
                    adjustPeriodVO = period2VO.get(nextPeriod.toString()).get(index);
                    laterPeriodVO = new LaterPeriodVO();
                    laterPeriodVO.setCurrPeriod(period);
                    laterPeriodVO.setAdjustPeriodVO(adjustPeriodVO);
                    laterPeriodVOList.add(laterPeriodVO);
                }
            }
            LaterPeriodVO laterPeriodVO2 = new LaterPeriodVO();
            laterPeriodVO2.setCurrPeriod(period);
            adjustPeriodVO = new AdjustPeriodVO();
            if (isExistAdjust) {
                adjustPeriodVO.setCode("0");
            } else {
                adjustPeriodVO.setCode(null);
            }
            laterPeriodVO2.setAdjustPeriodVO(adjustPeriodVO);
            laterPeriodVOList.add(laterPeriodVO2);
        }
        return laterPeriodVOList;
    }

    private Map<String, List<AdjustPeriodVO>> getLaterAdjustByType(GcInputAdjustVO gcInputAdjustVO, boolean isExistAdjust) {
        if (isExistAdjust) {
            TaskDefine taskDefine = this.taskService.queryTaskDefine(gcInputAdjustVO.getTaskId());
            Assert.isNotNull((Object)taskDefine, (String)"taskDefine must not be null!", (Object[])new Object[0]);
            List adjustPeriods = this.adjustPeriodDesignService.query(taskDefine.getDataScheme());
            return adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(AdjustPeriodVO::convertToVO).collect(Collectors.groupingBy(AdjustPeriodVO::getPeriod));
        }
        return new HashMap<String, List<AdjustPeriodVO>>();
    }

    private void deleteBySrcOffsetGroupIds(String taskId, List<String> srcOffsetGroupIds, int acctYear, int effectType, int acctPeriod, String orgType) {
        GcTaskBaseArguments gcTaskBaseArguments = this.getGcTaskBaseArguments(taskId, acctYear, acctPeriod, orgType, null);
        if (effectType > 0) {
            this.deleteFutureMonthByOffsetGroupId(srcOffsetGroupIds, OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue(), gcTaskBaseArguments);
        } else {
            this.coreService.deleteByOffsetGroupIdsAndSrcType(srcOffsetGroupIds, OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue(), gcTaskBaseArguments);
        }
    }

    private List<GcOffSetVchrDTO> batchSaveBySrcGroupId(List<GcOffSetVchrDTO> offSetItemDTOs) {
        for (GcOffSetVchrDTO dto : offSetItemDTOs) {
            GcOffSetVchrItemDTO itemDTO = (GcOffSetVchrItemDTO)dto.getItems().get(0);
            String srcOffsetGroupId = itemDTO.getSrcOffsetGroupId();
            int acctYear = itemDTO.getAcctYear();
            int acctPeriod = itemDTO.getAcctPeriod();
            String taskId = itemDTO.getTaskId();
            String offSetCurr = itemDTO.getOffSetCurr();
            int offSetSrcType = itemDTO.getOffSetSrcType().getSrcTypeValue();
            ArrayList<String> srcOffsetGroupIds = new ArrayList<String>();
            srcOffsetGroupIds.add(srcOffsetGroupId);
            if (srcOffsetGroupId == null || itemDTO.getElmMode() >= 2 && OffsetElmModeEnum.BATCH_MANUAL_ITEM.getValue() != itemDTO.getElmMode().intValue()) continue;
            String orgType = acctPeriod == 0 ? null : itemDTO.getOrgType();
            this.coreService.deleteByOffsetGroupIdsAndSrcType(srcOffsetGroupIds, offSetSrcType, this.getGcTaskBaseArguments(taskId, acctYear, acctPeriod, orgType, offSetCurr));
        }
        this.coreService.batchSave(offSetItemDTOs);
        return offSetItemDTOs;
    }

    private void updateWriteOffSrcGroupId(Map<String, String> mrecidMap) {
        for (Map.Entry<String, String> entry : mrecidMap.entrySet()) {
            this.coreService.updateOffsetGroupId(entry.getKey(), entry.getValue(), null, null);
        }
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteFutureMonthByOffsetGroupId(Collection<String> srcOffsetGroupIds, Integer offSetSrcType, GcTaskBaseArguments baseArguments) {
        if (srcOffsetGroupIds == null || srcOffsetGroupIds.size() <= 0) {
            return;
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcOffsetGroupIds, (String)"srcOffsetGroupId");
        String sql = "select mrecid from GC_OFFSETVCHRITEM  \n   where \n" + tempTableCondition.getCondition() + " and substr(dataTime,1,4) = '" + baseArguments.getAcctYear() + "' and substr(dataTime,6,4) >= '" + periodFormat.format(baseArguments.getAcctPeriod()) + "'\n";
        if (baseArguments.getOrgType() != null) {
            sql = sql + "and md_gcorgtype = '" + baseArguments.getOrgType() + "'\n";
        }
        if (!StringUtils.isEmpty((String)baseArguments.getCurrency())) {
            sql = sql + "and offSetCurr = '" + baseArguments.getCurrency() + "'\n";
        }
        if (offSetSrcType != null) {
            sql = sql + "and offSetSrcType = '" + offSetSrcType + "'\n";
        }
        EntNativeSqlDefaultDao adjustDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
        try {
            List idList = adjustDao.selectFirstList(String.class, sql, new Object[0]);
            if (idList == null || idList.size() <= 0) {
                return;
            }
            this.coreService.deleteByMrecId(new HashSet(idList));
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    private class LaterPeriodVO {
        private Integer currPeriod;
        private AdjustPeriodVO adjustPeriodVO;

        private LaterPeriodVO() {
        }

        public Integer getCurrPeriod() {
            return this.currPeriod;
        }

        public void setCurrPeriod(Integer currPeriod) {
            this.currPeriod = currPeriod;
        }

        public AdjustPeriodVO getAdjustPeriodVO() {
            return this.adjustPeriodVO;
        }

        public void setAdjustPeriodVO(AdjustPeriodVO adjustPeriodVO) {
            this.adjustPeriodVO = adjustPeriodVO;
        }
    }
}

