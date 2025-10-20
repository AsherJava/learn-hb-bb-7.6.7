/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOffSetVchrItemAdjustDao;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgFilterCustomFormulaSettingDao;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgFilterSettingDao;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgTemporaryBatchDao;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeQuerySchemeDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterCustomFormulaSettingEO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterSettingEO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeQuerySchemeEO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.impl.ArbitrarilyMergeQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeOffSetItemAdjustService;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeService;
import com.jiuqi.gcreport.workingpaper.utils.ArbitrarilyMergeInputAdjustConvertUtil;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArbitrarilyMergeServiceImpl
implements ArbitrarilyMergeService {
    @Autowired
    private ArbitrarilyMergeQuerySchemeDao arbitrarilyMergeQuerySchemeDao;
    @Autowired
    private ArbitrarilyMergeOrgFilterSettingDao arbitrarilyMergeOrgFilterSettingDao;
    @Autowired
    private ArbitrarilyMergeOrgFilterCustomFormulaSettingDao formulaSettingDao;
    @Autowired
    private ArbitrarilyMergeOffSetItemAdjustService ryOffSetItemAdjustService;
    @Autowired
    private ArbitrarilyMergeOffSetVchrItemAdjustDao arbitrarilyMergeOffSetVchrItemAdjustDao;
    @Autowired
    private ArbitrarilyMergeQueryTaskImpl arbitrarilyMergeQueryTask;
    @Autowired
    private ArbitrarilyMergeOrgTemporaryBatchDao orgTemporaryBatchDao;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedTaskCacheService consolidatedTaskCacheService;
    @Autowired
    private ConsolidatedFormulaService consolidatedFormulaService;
    @Autowired
    private ConsolidatedSubjectService subjectService;

    @Override
    public String checkOrgIdSupSubRelation(WorkingPaperQueryCondition info) {
        if (CollectionUtils.isEmpty((Collection)info.getOrgIds())) {
            return "";
        }
        Set<String> a = this.getUnitCodeOnlyParent(info.getOrgIds(), info.getPeriodStr(), GcAuthorityType.ACCESS);
        if (info.getOrgIds().size() != a.size()) {
            return "\u4e0d\u5141\u8bb8\u540c\u65f6\u9009\u62e9\u4e0a\u7ea7\u5355\u4f4d\u548c\u4e0b\u7ea7\u5355\u4f4d\u3002";
        }
        return "";
    }

    @Override
    public String getViewKeyByTaskIdAndOrgType(String taskId, String orgType) {
        return GcOrgTypeUtils.getEntityIdByTaskIdAndOrgType((String)taskId, (String)orgType);
    }

    @Override
    public boolean addQueryScheme(ArbitrarilyMergeQuerySchemeVO arbitrarilyMergeQuerySchemevO) {
        ArbitrarilyMergeQuerySchemeEO eo = new ArbitrarilyMergeQuerySchemeEO();
        BeanUtils.copyProperties(arbitrarilyMergeQuerySchemevO, (Object)eo);
        int i = this.arbitrarilyMergeQuerySchemeDao.add((BaseEntity)eo);
        return i > 0;
    }

    @Override
    public List<ArbitrarilyMergeQuerySchemeVO> getQuerySchemeByResourceId(String resourceId) {
        ArbitrarilyMergeQuerySchemeEO arbitrarilyMergeQuerySchemeEO = new ArbitrarilyMergeQuerySchemeEO();
        arbitrarilyMergeQuerySchemeEO.setResourceId(resourceId);
        List arbitrarilyMergeQuerySchemeEOS = this.arbitrarilyMergeQuerySchemeDao.selectList((BaseEntity)arbitrarilyMergeQuerySchemeEO);
        ArrayList<ArbitrarilyMergeQuerySchemeVO> list = new ArrayList(arbitrarilyMergeQuerySchemeEOS.size());
        for (ArbitrarilyMergeQuerySchemeEO eo : arbitrarilyMergeQuerySchemeEOS) {
            if (eo == null) continue;
            ArbitrarilyMergeQuerySchemeVO vo = new ArbitrarilyMergeQuerySchemeVO();
            BeanUtils.copyProperties((Object)eo, vo);
            String settingJsonString = eo.getSettingJson();
            List settingList = JSONUtil.parseArray((String)settingJsonString, ArbitrarilyMergeOrgFilterSettingVO.class);
            vo.setSettingList(settingList);
            list.add(vo);
        }
        list = list.stream().sorted(Comparator.comparing(ArbitrarilyMergeQuerySchemeVO::getOrderNum)).collect(Collectors.toList());
        return list;
    }

    @Override
    public ArbitrarilyMergeQuerySchemeVO getQuerySchemeById(String id) {
        ArbitrarilyMergeQuerySchemeEO arbitrarilyMergeQuerySchemeEO = (ArbitrarilyMergeQuerySchemeEO)this.arbitrarilyMergeQuerySchemeDao.get((Serializable)((Object)id));
        ArbitrarilyMergeQuerySchemeVO arbitrarilyMergeQuerySchemeVO = new ArbitrarilyMergeQuerySchemeVO();
        BeanUtils.copyProperties((Object)arbitrarilyMergeQuerySchemeEO, arbitrarilyMergeQuerySchemeVO);
        return arbitrarilyMergeQuerySchemeVO;
    }

    @Override
    public boolean updateQueryScheme(ArbitrarilyMergeQuerySchemeVO arbitrarilyMergeQuerySchemeVO) {
        ArbitrarilyMergeQuerySchemeEO arbitrarilyMergeQuerySchemeEO = new ArbitrarilyMergeQuerySchemeEO();
        BeanUtils.copyProperties(arbitrarilyMergeQuerySchemeVO, (Object)arbitrarilyMergeQuerySchemeEO);
        int i = this.arbitrarilyMergeQuerySchemeDao.updateSelective((BaseEntity)arbitrarilyMergeQuerySchemeEO);
        return i > 0;
    }

    @Override
    public boolean deleteQuerySchemeById(String id) {
        ArbitrarilyMergeQuerySchemeEO eo = new ArbitrarilyMergeQuerySchemeEO();
        eo.setId(id);
        int i = this.arbitrarilyMergeQuerySchemeDao.delete((BaseEntity)eo);
        return i > 0;
    }

    @Override
    public boolean addOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO arbitrarilyMergeOrgFilterSettingVO) {
        ArbitrarilyMergeOrgFilterSettingEO eo = new ArbitrarilyMergeOrgFilterSettingEO();
        BeanUtils.copyProperties(arbitrarilyMergeOrgFilterSettingVO, (Object)eo);
        eo.setEnableFlag(ConverterUtils.getAsBoolean((Object)arbitrarilyMergeOrgFilterSettingVO.getEnableFlag(), (Boolean)false) != false ? 1 : 0);
        int i = this.arbitrarilyMergeOrgFilterSettingDao.add((BaseEntity)eo);
        return i > 0;
    }

    @Override
    public boolean updateOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO arbitrarilyMergeOrgFilterSettingVO) {
        ArbitrarilyMergeOrgFilterSettingEO arbitrarilyMergeOrgFilterSettingEO = new ArbitrarilyMergeOrgFilterSettingEO();
        BeanUtils.copyProperties(arbitrarilyMergeOrgFilterSettingVO, (Object)arbitrarilyMergeOrgFilterSettingEO);
        arbitrarilyMergeOrgFilterSettingEO.setEnableFlag(ConverterUtils.getAsBoolean((Object)arbitrarilyMergeOrgFilterSettingVO.getEnableFlag(), (Boolean)false) != false ? 1 : 0);
        int i = this.arbitrarilyMergeOrgFilterSettingDao.updateSelective((BaseEntity)arbitrarilyMergeOrgFilterSettingEO);
        return i > 0;
    }

    @Override
    public boolean deleteOrgFilterSetting(String id) {
        ArbitrarilyMergeOrgFilterSettingEO eo = new ArbitrarilyMergeOrgFilterSettingEO();
        eo.setId(id);
        int i = this.arbitrarilyMergeOrgFilterSettingDao.delete((BaseEntity)eo);
        return i > 0;
    }

    @Override
    public ArbitrarilyMergeOrgFilterSettingVO getOrgFilterSettingById(String id) {
        ArbitrarilyMergeOrgFilterSettingEO arbitrarilyMergeOrgFilterSettingEO = (ArbitrarilyMergeOrgFilterSettingEO)this.arbitrarilyMergeOrgFilterSettingDao.get((Serializable)((Object)id));
        ArbitrarilyMergeOrgFilterSettingVO arbitrarilyMergeOrgFilterSettingVO = new ArbitrarilyMergeOrgFilterSettingVO();
        BeanUtils.copyProperties((Object)arbitrarilyMergeOrgFilterSettingEO, arbitrarilyMergeOrgFilterSettingVO);
        arbitrarilyMergeOrgFilterSettingVO.setEnableFlag(Boolean.valueOf(arbitrarilyMergeOrgFilterSettingEO.getEnableFlag() == 1));
        return arbitrarilyMergeOrgFilterSettingVO;
    }

    @Override
    public List<ArbitrarilyMergeOrgFilterSettingVO> getOrgFilterSettingList() {
        List gcOrgFilterSettingList = this.arbitrarilyMergeOrgFilterSettingDao.loadAll();
        ArrayList<ArbitrarilyMergeOrgFilterSettingVO> list = new ArrayList<ArbitrarilyMergeOrgFilterSettingVO>(gcOrgFilterSettingList.size());
        for (ArbitrarilyMergeOrgFilterSettingEO eo : gcOrgFilterSettingList) {
            ArbitrarilyMergeOrgFilterSettingVO vo = new ArbitrarilyMergeOrgFilterSettingVO();
            BeanUtils.copyProperties((Object)eo, vo);
            vo.setEnableFlag(Boolean.valueOf(eo.getEnableFlag() == 1));
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<ArbitrarilyMergeOrgFilterSettingVO> getEnableList() {
        ArbitrarilyMergeOrgFilterSettingEO eo = new ArbitrarilyMergeOrgFilterSettingEO();
        eo.setEnableFlag(1);
        List orgFilterSettingEOList = this.arbitrarilyMergeOrgFilterSettingDao.selectList((BaseEntity)eo);
        ArrayList<ArbitrarilyMergeOrgFilterSettingVO> list = new ArrayList<ArbitrarilyMergeOrgFilterSettingVO>(orgFilterSettingEOList.size());
        for (ArbitrarilyMergeOrgFilterSettingEO eo1 : orgFilterSettingEOList) {
            ArbitrarilyMergeOrgFilterSettingVO vo = new ArbitrarilyMergeOrgFilterSettingVO();
            BeanUtils.copyProperties((Object)eo1, vo);
            list.add(vo);
        }
        return list;
    }

    @Override
    public boolean addBatchFormulaSettingByDataId(List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> list, String dataId) {
        ArrayList<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO> formulaSettingEOList = new ArrayList<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO>(list.size());
        for (ArbitrarilyMergeOrgFilterCustomFormulaSettingVO formulaSettingVO : list) {
            if (StringUtil.isEmpty((String)formulaSettingVO.getId())) {
                formulaSettingVO.setId(UUID.randomUUID().toString());
            }
            formulaSettingVO.setDataId(dataId);
            formulaSettingVO.setOrderNum(Double.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue()));
            ArbitrarilyMergeOrgFilterCustomFormulaSettingEO eo = new ArbitrarilyMergeOrgFilterCustomFormulaSettingEO();
            BeanUtils.copyProperties(formulaSettingVO, (Object)eo);
            formulaSettingEOList.add(eo);
        }
        int[] batch = this.formulaSettingDao.addBatch(formulaSettingEOList);
        return !Arrays.asList(new int[][]{batch}).contains(0);
    }

    @Override
    public boolean deleteFormulaSettingByDataId(String dataId) {
        try {
            ArbitrarilyMergeOrgFilterCustomFormulaSettingEO eo = new ArbitrarilyMergeOrgFilterCustomFormulaSettingEO();
            eo.setDataId(dataId);
            List list = this.formulaSettingDao.selectList((BaseEntity)eo);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); ++i) {
                    this.formulaSettingDao.delete((BaseEntity)list.get(i));
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> getFormulaSettingByDataId(String dataId) {
        ArbitrarilyMergeOrgFilterCustomFormulaSettingEO eo = new ArbitrarilyMergeOrgFilterCustomFormulaSettingEO();
        eo.setDataId(dataId);
        List list = this.formulaSettingDao.selectList((BaseEntity)eo);
        ArrayList<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> voList = new ArrayList<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO>(list.size());
        for (ArbitrarilyMergeOrgFilterCustomFormulaSettingEO eo1 : list) {
            ArbitrarilyMergeOrgFilterCustomFormulaSettingVO vo = new ArbitrarilyMergeOrgFilterCustomFormulaSettingVO();
            BeanUtils.copyProperties((Object)eo1, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public ArbitrarilyMergeOrgFilterCustomFormulaSettingVO getFormulaById(String id) {
        ArbitrarilyMergeOrgFilterCustomFormulaSettingEO arbitrarilyMergeOrgFilterCustomFormulaSettingEO = (ArbitrarilyMergeOrgFilterCustomFormulaSettingEO)this.formulaSettingDao.get((Serializable)((Object)id));
        ArbitrarilyMergeOrgFilterCustomFormulaSettingVO arbitrarilyMergeOrgFilterCustomFormulaSettingVO = new ArbitrarilyMergeOrgFilterCustomFormulaSettingVO();
        BeanUtils.copyProperties((Object)arbitrarilyMergeOrgFilterCustomFormulaSettingEO, arbitrarilyMergeOrgFilterCustomFormulaSettingVO);
        return arbitrarilyMergeOrgFilterCustomFormulaSettingVO;
    }

    @Override
    public void addRyInputAdjust(List<List<ArbitrarilyMergeInputAdjustVO>> batchlist) {
        for (List<ArbitrarilyMergeInputAdjustVO> list2 : batchlist) {
            for (ArbitrarilyMergeInputAdjustVO oneRow : list2) {
                Map unSysFields = oneRow.getUnSysFields();
                for (Map.Entry unSysField : unSysFields.entrySet()) {
                    if (unSysField.getValue() instanceof Map) {
                        unSysFields.put(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                        continue;
                    }
                    unSysFields.put(unSysField.getKey(), unSysField.getValue());
                }
            }
        }
        this.checkSubject(batchlist);
        ArrayList<ArbitrarilyMergeOffSetVchrDTO> batchDtoList = new ArrayList<ArbitrarilyMergeOffSetVchrDTO>();
        ArrayList<String> srcOffsetGroupIds = new ArrayList<String>();
        HashMap mrecidMap = new HashMap();
        ArbitrarilyMergeInputAdjustVO initInputAdjustVO = batchlist.get(0).get(0);
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskBySchemeId(initInputAdjustVO.getSchemeId(), initInputAdjustVO.getDefaultPeriod());
        List<ConsolidatedFormulaVO> consFormulaCalc = this.getConsFormulaCalc(taskVO.getSystemId());
        batchlist.forEach(list -> {
            ArbitrarilyMergeOffSetVchrDTO vchrDTO = new ArbitrarilyMergeOffSetVchrDTO();
            String mrecid = UUIDOrderUtils.newUUIDStr();
            String srcOffsetGroupId = UUIDOrderUtils.newUUIDStr();
            vchrDTO.setMrecid(mrecid);
            if (!StringUtils.isEmpty((String)((ArbitrarilyMergeInputAdjustVO)list.get(0)).getmRecid())) {
                mrecidMap.put(((ArbitrarilyMergeInputAdjustVO)list.get(0)).getmRecid(), mrecid);
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
                for (ArbitrarilyMergeInputAdjustVO item : list) {
                    if (StringUtils.isEmpty((String)item.getUnitId())) {
                        item.setUnitId(((String)unitIdList.get(0)).equals(item.getOppUnitId()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                        continue;
                    }
                    if (!StringUtils.isEmpty((String)item.getOppUnitId())) continue;
                    item.setOppUnitId(((String)unitIdList.get(0)).equals(item.getUnitId()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                }
            }
            ArrayList<ArbitrarilyMergeOffSetVchrItemDTO> itemList = new ArrayList<ArbitrarilyMergeOffSetVchrItemDTO>();
            for (ArbitrarilyMergeInputAdjustVO inputAdjustVO2 : list) {
                if (inputAdjustVO2.getSrcID() != null && !srcOffsetGroupIds.contains(inputAdjustVO2.getSrcID())) {
                    srcOffsetGroupIds.add(inputAdjustVO2.getSrcID());
                    srcOffsetGroupId = inputAdjustVO2.getSrcID();
                }
                inputAdjustVO2.setmRecid(mrecid);
                inputAdjustVO2.setSrcID(srcOffsetGroupId);
                ArbitrarilyMergeOffSetVchrItemDTO offsetItemDto = this.convertVOToRyOffsetDTO(inputAdjustVO2, taskVO);
                offsetItemDto.setmRecid(mrecid);
                this.exeConsFormulaCalcSingle(offsetItemDto, consFormulaCalc);
                itemList.add(offsetItemDto);
            }
            vchrDTO.setItems(itemList);
            batchDtoList.add(vchrDTO);
            if (!((ArbitrarilyMergeInputAdjustVO)list.get(0)).getEffectType().equals(EFFECTTYPE.MONTH.getCode())) {
                Integer[] laterPeriods;
                Integer[] integerArray = laterPeriods = this.getLaterPeriodByType(((ArbitrarilyMergeInputAdjustVO)list.get(0)).getPeriodType(), ((ArbitrarilyMergeInputAdjustVO)list.get(0)).getAcctPeriod());
                int n = integerArray.length;
                for (int i = 0; i < n; ++i) {
                    int period = integerArray[i];
                    String mid2 = UUID.randomUUID().toString();
                    ArbitrarilyMergeOffSetVchrDTO vchrDTO2 = new ArbitrarilyMergeOffSetVchrDTO();
                    vchrDTO2.setMrecid(mid2);
                    ArrayList<ArbitrarilyMergeOffSetVchrItemDTO> itemList2 = new ArrayList<ArbitrarilyMergeOffSetVchrItemDTO>();
                    for (ArbitrarilyMergeInputAdjustVO inputAdjustVO3 : list) {
                        ArbitrarilyMergeInputAdjustVO laterVo = new ArbitrarilyMergeInputAdjustVO();
                        BeanUtils.copyProperties(inputAdjustVO3, laterVo);
                        laterVo.setId(UUID.randomUUID().toString());
                        laterVo.setmRecid(mid2);
                        laterVo.setAcctPeriod(period);
                        String defaultPeriod = YearPeriodUtil.transform(null, (int)laterVo.getAcctYear(), (int)((ArbitrarilyMergeInputAdjustVO)list.get(0)).getPeriodType(), (int)period).toString();
                        laterVo.setDefaultPeriod(defaultPeriod);
                        laterVo.setSrcID(((ArbitrarilyMergeInputAdjustVO)list.get(0)).getSrcID());
                        ArbitrarilyMergeOffSetVchrItemDTO offsetItemDto2 = this.convertVOToRyOffsetDTO(laterVo, taskVO);
                        offsetItemDto2.setmRecid(vchrDTO2.getMrecid());
                        itemList2.add(offsetItemDto2);
                    }
                    vchrDTO2.setItems(itemList2);
                    batchDtoList.add(vchrDTO2);
                }
            }
        });
        int acctYear = ((ArbitrarilyMergeOffSetVchrDTO)batchDtoList.get(0)).getItems().get(0).getAcctYear();
        int acctPeriod = ((ArbitrarilyMergeOffSetVchrDTO)batchDtoList.get(0)).getItems().get(0).getAcctPeriod();
        String selectAdjustCode = ((ArbitrarilyMergeOffSetVchrDTO)batchDtoList.get(0)).getItems().get(0).getSelectAdjustCode();
        if (srcOffsetGroupIds.size() > 0) {
            this.ryOffSetItemAdjustService.deleteRyBySrcOffsetGroupIds(null, srcOffsetGroupIds, acctYear, 1, acctPeriod, null, selectAdjustCode);
        }
        this.ryOffSetItemAdjustService.batchRySaveBySrcGroupId(batchDtoList);
    }

    @Override
    public void deleteRyAdjustInputByMredid(String taskId, List<String> mrecids, int acctYear, int effectType, int acctPeriod, String orgType, String currencyCode, int offSetSrcType, String adjustCode) {
        if (mrecids.size() == 0) {
            return;
        }
        this.arbitrarilyMergeOffSetVchrItemAdjustDao.deleteRyByMrecids(mrecids, taskId, acctYear, acctPeriod, orgType, currencyCode, adjustCode);
    }

    @Override
    public List<ArbitrarilyMergeInputAdjustVO> queryRyDetailByMrecid(ArbitrarilyMergeInputAdjustQueryCondi condi) {
        String[] columnNamesInDB = new String[]{"mrecid", "DATATIME"};
        Object[] values = new Object[]{condi.getmRecid(), condi.getDefaultPeriod()};
        List<ArbitrarilyMergeOffSetVchrItemAdjustEO> ryOffSetVchrItemAdjustEOS = this.ryOffSetItemAdjustService.queryRyOffsetRecordsByWhere(columnNamesInDB, values, condi);
        List<ArbitrarilyMergeInputAdjustVO> ryInputAdjustVOS = ryOffSetVchrItemAdjustEOS.stream().map(offsetEOItem -> {
            ArbitrarilyMergeInputAdjustVO arbitrarilyMergeInputAdjustVO = this.convertRyEOToVO((ArbitrarilyMergeOffSetVchrItemAdjustEO)((Object)offsetEOItem), condi.getOrgTypeId());
            return arbitrarilyMergeInputAdjustVO;
        }).collect(Collectors.toList());
        return ryInputAdjustVOS;
    }

    @Override
    public ArbitrarilyMergeInputAdjustVO convertRyEOToVO(ArbitrarilyMergeOffSetVchrItemAdjustEO ryOffSetEO, String orgType) {
        return ArbitrarilyMergeInputAdjustConvertUtil.getInstance().convertRyEOToVO(ryOffSetEO, orgType);
    }

    @Override
    public Pagination<Map<String, Object>> listRyPentrationDatasOther(WorkingPaperPentrationQueryCondtion ryPenInfo) {
        if (Boolean.TRUE.equals(ryPenInfo.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary((WorkingPaperQueryCondition)ryPenInfo, orgTypeMap);
        }
        Pagination<Map<String, Object>> offsetPage = this.getRyOffsetPageOther(ryPenInfo, new HashSet<String>(), new HashSet<String>());
        if (Boolean.TRUE.equals(ryPenInfo.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)ryPenInfo.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(ryPenInfo.getOrgBatchId());
        }
        return offsetPage;
    }

    @Override
    public Pagination<Map<String, Object>> listRyPentrationDatas(WorkingPaperPentrationQueryCondtion penInfo) {
        if (Boolean.TRUE.equals(penInfo.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary((WorkingPaperQueryCondition)penInfo, orgTypeMap);
        }
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)penInfo.getWorkPaperVoJson(), WorkingPaperTableDataVO.class);
        List<String> subjectCodes = WorkingPaperQueryUtils.listSubjectCodes(penInfo, workingPaperTableDataVO);
        HashSet<String> totalColumnCodes = new HashSet<String>(Arrays.asList("formSubjectTitle", "ryhbtzTotalStr"));
        Pagination<Map<String, Object>> offsetPage = this.getRyOffsetPage(penInfo, new HashSet<String>(subjectCodes), totalColumnCodes);
        if (Boolean.TRUE.equals(penInfo.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)penInfo.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(penInfo.getOrgBatchId());
        }
        return offsetPage;
    }

    @Override
    public List<GcOrgCacheVO> getOrgByOrgCodes(ArbitrarilyMergeOrgParamVO orgParamVo) {
        YearPeriodObject yp = new YearPeriodObject(null, orgParamVo.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgParamVo.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        ArrayList<GcOrgCacheVO> gcOrgCacheVOS = new ArrayList<GcOrgCacheVO>();
        for (String orgCode : orgParamVo.getOrgCodes()) {
            GcOrgCacheVO org = tool.getOrgByCode(orgCode);
            if (org == null) continue;
            gcOrgCacheVOS.add(org);
        }
        return gcOrgCacheVOS;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Pagination<Map<String, Object>> getUnOffsetPentrationDatas(WorkingPaperPentrationQueryCondtion penInfo) {
        if (Boolean.TRUE.equals(penInfo.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary((WorkingPaperQueryCondition)penInfo, orgTypeMap);
        }
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.covertQueryParamsVO((WorkingPaperQueryCondition)penInfo);
        queryParamsVO.setNotOffsetOtherColumns(penInfo.getOtherShowColumnKeys());
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)penInfo.getWorkPaperVoJson(), WorkingPaperTableDataVO.class);
        List<String> subjectCodes = WorkingPaperQueryUtils.listSubjectCodes(penInfo, workingPaperTableDataVO);
        ArbitrarilyMergeQueryTaskImpl queryTask = (ArbitrarilyMergeQueryTaskImpl)SpringContextUtils.getBean(ArbitrarilyMergeQueryTaskImpl.class);
        List<Map<String, Object>> datas = queryTask.getUnOffsetItem(queryParamsVO);
        ArrayList tempOffsetDatas = new ArrayList();
        GcOffsetAppInputDataService inputDataService = (GcOffsetAppInputDataService)SpringContextUtils.getBean(GcOffsetAppInputDataService.class);
        datas.forEach(row -> tempOffsetDatas.add(inputDataService.getObject(row)));
        List unOffsetList = tempOffsetDatas.stream().filter(unOffset -> subjectCodes.contains(unOffset.get("SUBJECTCODE"))).collect(Collectors.toList());
        Pagination offsetPage = new Pagination();
        offsetPage.setTotalElements(Integer.valueOf(unOffsetList.size()));
        offsetPage.setContent(unOffsetList);
        offsetPage.setPageSize(Integer.valueOf(penInfo.getPageSize()));
        offsetPage.setCurrentPage(Integer.valueOf(penInfo.getPageNum()));
        if (Boolean.TRUE.equals(penInfo.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)penInfo.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(penInfo.getOrgBatchId());
        }
        GcOffsetAppInputDataService offsetAppInputDataService = (GcOffsetAppInputDataService)SpringContextUtils.getBean(GcOffsetAppInputDataService.class);
        offsetAppInputDataService.setTitles(offsetPage, queryParamsVO, queryParamsVO.getSystemId());
        return offsetPage;
    }

    private Pagination<Map<String, Object>> getRyOffsetPage(WorkingPaperPentrationQueryCondtion penInfo, Set<String> subjectCodes, Set<String> totalColumnCodes) {
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(penInfo, false);
        queryParamsVO.setSubjectCodes(new ArrayList<String>(subjectCodes));
        ArbitrarilyMergeOffSetItemAdjustService ryOffSetItemAdjustService = (ArbitrarilyMergeOffSetItemAdjustService)SpringContextUtils.getBean(ArbitrarilyMergeOffSetItemAdjustService.class);
        Pagination<Map<String, Object>> offsetPage = ryOffSetItemAdjustService.getRyOffsetEntry(queryParamsVO, false);
        List<Map<String, Object>> offsetDatas = offsetPage.getContent();
        String pentrateType = penInfo.getCurrShowTypeValue();
        String columnProperty = penInfo.getPentraColumnName();
        List tempOffsetDatas = offsetDatas.stream().filter(offset -> subjectCodes.contains(offset.get("SUBJECTCODE"))).collect(Collectors.toList());
        Set mRecids = totalColumnCodes.contains(columnProperty) ? tempOffsetDatas.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet()) : tempOffsetDatas.stream().filter(offset -> this.isCurrColumnOffset((Map<String, Object>)offset, columnProperty)).map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
        offsetPage.setTotalElements(Integer.valueOf(mRecids.size()));
        offsetDatas = offsetDatas.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        if ("2".equals(pentrateType)) {
            offsetDatas = offsetDatas.stream().filter(offset -> subjectCodes.contains(offset.get("SUBJECTCODE"))).collect(Collectors.toList());
        }
        offsetDatas = WorkingPaperQueryUtils.setRowSpanAndSort(offsetDatas);
        offsetPage.setContent(offsetDatas);
        return offsetPage;
    }

    private void checkSubject(List<List<ArbitrarilyMergeInputAdjustVO>> batchlist) {
        for (List<ArbitrarilyMergeInputAdjustVO> voList : batchlist) {
            boolean hasCASH = false;
            boolean hasNotCASH = false;
            for (ArbitrarilyMergeInputAdjustVO vo : voList) {
                ConsolidatedSubjectEO subject = this.subjectService.getSubjectByCode(vo.getSchemeId(), vo.getSubjectCode());
                if (subject == null) continue;
                if (SubjectAttributeEnum.CASH.getValue() == subject.getAttri().intValue()) {
                    hasCASH = true;
                } else {
                    hasNotCASH = true;
                }
                if (!hasCASH || !hasNotCASH) continue;
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.cashFlowNotOffset"));
            }
        }
    }

    private Pagination<Map<String, Object>> getRyOffsetPageOther(WorkingPaperPentrationQueryCondtion ryPenInfo, Set<String> subjectCodes, Set<String> totalColumnCodes) {
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(ryPenInfo, false);
        queryParamsVO.setSubjectCodes(new ArrayList<String>(subjectCodes));
        ArbitrarilyMergeOffSetItemAdjustService ryOffSetItemAdjustService = (ArbitrarilyMergeOffSetItemAdjustService)SpringContextUtils.getBean(ArbitrarilyMergeOffSetItemAdjustService.class);
        Pagination<Map<String, Object>> offsetPage = ryOffSetItemAdjustService.getRyOffsetEntry(queryParamsVO, false);
        return offsetPage;
    }

    private ArbitrarilyMergeOffSetVchrItemDTO convertVOToRyOffsetDTO(ArbitrarilyMergeInputAdjustVO inputAdjustVO, ConsolidatedTaskVO taskVO) {
        ArbitrarilyMergeOffSetVchrItemDTO dto = new ArbitrarilyMergeOffSetVchrItemDTO();
        BeanUtils.copyProperties(inputAdjustVO, (Object)dto);
        dto.setId(UUIDOrderUtils.newUUIDStr());
        dto.setOrgType(inputAdjustVO.getUnitVersion());
        if (null != taskVO && null != taskVO.getInputTaskInfo() && (inputAdjustVO.getUnitVersion().equals(taskVO.getInputTaskInfo().getUnitDefine()) || inputAdjustVO.getManageCalcUnitCode().booleanValue())) {
            dto.setInputUnitId(null);
        }
        if (Boolean.TRUE.equals(inputAdjustVO.getManageCalcUnitCode())) {
            dto.setInputUnitId(null);
        }
        if (inputAdjustVO.getOffSetSrcType() == OffSetSrcTypeEnum.WRITE_OFF.getSrcTypeValue()) {
            dto.setInputUnitId(inputAdjustVO.getInputUnitId());
        }
        dto.setRuleId(inputAdjustVO.getUnionRuleId());
        dto.setElmMode(inputAdjustVO.getElmMode());
        dto.setSortOrder(Double.valueOf(inputAdjustVO.getSortOrder()));
        if (inputAdjustVO.getDebit() != null) {
            dto.setOffSetDebit(inputAdjustVO.getDebit());
        } else if (inputAdjustVO.getCredit() != null) {
            dto.setOffSetCredit(inputAdjustVO.getCredit());
        }
        dto.setSelectAdjustCode(inputAdjustVO.getSelectAdjustCode());
        dto.setPostFlag(true);
        dto.setOffSetCurr(inputAdjustVO.getCurrencyCode());
        dto.setSrcOffsetGroupId(inputAdjustVO.getSrcID());
        dto.setSrcId(inputAdjustVO.getId());
        dto.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)inputAdjustVO.getOffSetSrcType()));
        dto.setEffectType(inputAdjustVO.getEffectType());
        dto.setCreateUser(NpContextHolder.getContext().getUserName());
        dto.setSelectAdjustCode(inputAdjustVO.getSelectAdjustCode());
        return dto;
    }

    private void exeConsFormulaCalcSingle(ArbitrarilyMergeOffSetVchrItemDTO offsetDTO, List<ConsolidatedFormulaVO> consFormulaVOS) {
        if (StringUtils.isEmpty((String)offsetDTO.getRuleId())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.pleaseSelectRule"));
        }
        OffSetUnSysDimensionCache.load();
        List dimensionVOs = OffSetUnSysDimensionCache.allDimValue((String)this.getSystemId(offsetDTO));
        Set<String> dimensionKeySet = dimensionVOs.stream().map(dimensionVO -> dimensionVO.getCode()).collect(Collectors.toSet());
        ArbitrarilyMergeOffSetVchrItemAdjustEO offsetEO = new ArbitrarilyMergeOffSetVchrItemAdjustEO();
        BeanUtils.copyProperties((Object)offsetDTO, (Object)offsetEO);
        Map<String, Object> unSysField = offsetDTO.getUnSysFields();
        dimensionKeySet.forEach(dimensionKey -> {
            if (!unSysField.containsKey(dimensionKey)) {
                unSysField.put((String)dimensionKey, null);
            }
        });
        unSysField.forEach((key, value) -> {
            offsetDTO.getFields().put(key, offsetDTO.getUnSysFieldValue((String)key));
            offsetEO.getFields().put(key, offsetDTO.getUnSysFieldValue((String)key));
        });
        consFormulaVOS.forEach(consFormulaVo -> {
            String formula = consFormulaVo.getFormula();
            List ruleIdList = consFormulaVo.getRuleIds();
            if (CollectionUtils.isEmpty((Collection)ruleIdList) || ruleIdList.contains(offsetDTO.getRuleId())) {
                String offSetCurr;
                GcFormulaUtils.execute((GcTaskBaseArguments)this.getTaskBaseArguments(offsetEO), (DefaultTableEntity)offsetEO, (String)formula);
                if (formula.contains("CREDIT") && null == offsetDTO.getOrient() || formula.contains("CREDIT") && offsetDTO.getOrient().equals((Object)OrientEnum.C)) {
                    offSetCurr = offsetEO.getOffSetCurr();
                    Double offsetCredit = ConverterUtils.getAsDouble(offsetEO.getFields().get("OFFSET_CREDIT_" + offSetCurr));
                    offsetDTO.setCredit(offsetCredit);
                    offsetDTO.setOffSetCredit(offsetCredit);
                } else if (formula.contains("DEBIT") && null == offsetDTO.getOrient() || formula.contains("DEBIT") && offsetDTO.getOrient().equals((Object)OrientEnum.D)) {
                    offSetCurr = offsetEO.getOffSetCurr();
                    Double offsetDebit = ConverterUtils.getAsDouble(offsetEO.getFields().get("OFFSET_DEBIT_" + offSetCurr));
                    offsetDTO.setDebit(offsetDebit);
                    offsetDTO.setOffSetDebit(offsetDebit);
                }
                Map<String, Object> unSysFields = offsetDTO.getUnSysFields();
                if (!CollectionUtils.isEmpty(unSysFields.keySet())) {
                    unSysFields.keySet().forEach(key -> {
                        if (formula.contains((CharSequence)key)) {
                            unSysFields.put((String)key, offsetEO.getFieldValue((String)key));
                            offsetDTO.getFields().put(key, offsetEO.getFieldValue((String)key));
                        }
                    });
                }
                BeanUtils.copyProperties((Object)offsetEO, (Object)offsetDTO);
            }
        });
    }

    private List<ConsolidatedFormulaVO> getConsFormulaCalc(String systemId) {
        List consolidatedFormulaVOS = this.consolidatedFormulaService.listConsFormulas(systemId);
        List<ConsolidatedFormulaVO> filtedFormulaVOS = consolidatedFormulaVOS.stream().filter(item -> item.getInputFlag() == 1).collect(Collectors.toList());
        return filtedFormulaVOS;
    }

    private GcTaskBaseArguments getTaskBaseArguments(ArbitrarilyMergeOffSetVchrItemAdjustEO offsetEO) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setPeriodStr(offsetEO.getDefaultPeriod());
        arguments.setCurrency(null == offsetEO.getOffSetCurr() ? "CNY" : offsetEO.getOffSetCurr());
        arguments.setOrgType(null == offsetEO.getOrgType() ? "MD_ORG_CORPORATE" : offsetEO.getOrgType());
        arguments.setOrgId(offsetEO.getUnitId());
        arguments.setTaskId(offsetEO.getTaskId());
        return arguments;
    }

    private String getSystemId(ArbitrarilyMergeOffSetVchrItemDTO gcOffSetVchrItemDTO) {
        String systemId = gcOffSetVchrItemDTO.getSystemId();
        if (StringUtils.isEmpty((String)gcOffSetVchrItemDTO.getSystemId())) {
            ConsolidatedTaskVO taskVO = this.consolidatedTaskCacheService.getTaskByTaskId(gcOffSetVchrItemDTO.getTaskId(), gcOffSetVchrItemDTO.getDefaultPeriod());
            systemId = taskVO.getSystemId();
        }
        return systemId;
    }

    private Integer[] getLaterPeriodByType(int periodType, int currPeriod) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (periodType != 1) {
            if (periodType == 2) {
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
        }
        Integer[] periods = result.toArray(new Integer[result.size()]);
        ArrayList<Integer> laterPeriods = new ArrayList<Integer>();
        for (Integer period : periods) {
            if (period <= currPeriod) continue;
            laterPeriods.add(period);
        }
        return laterPeriods.toArray(new Integer[laterPeriods.size()]);
    }

    private boolean isCurrColumnOffset(Map<String, Object> offset, String columnProperty) {
        switch (columnProperty) {
            case "dxjStr": 
            case "RYTZS_JD_DEBIT_SHOWVALUE": {
                if (1 != ConverterUtils.getAsInteger((Object)offset.get("ORIENT")) || ConverterUtils.getAsInteger((Object)offset.get("SRCTYPE")) < 20) break;
                return true;
            }
            case "RYTZS_JD_TOTAL_SHOWVALUE": {
                if (ConverterUtils.getAsInteger((Object)offset.get("SRCTYPE")) < 20) break;
                return true;
            }
            case "dxdStr": 
            case "RYTZS_JD_CREBIT_SHOWVALUE": {
                if (-1 != ConverterUtils.getAsInteger((Object)offset.get("ORIENT")) || ConverterUtils.getAsInteger((Object)offset.get("SRCTYPE")) < 20) break;
                return true;
            }
            case "tzjStr": {
                if (1 != ConverterUtils.getAsInteger((Object)offset.get("ORIENT")) || ConverterUtils.getAsInteger((Object)offset.get("SRCTYPE")) >= 20) break;
                return true;
            }
            case "tzdStr": {
                if (-1 != ConverterUtils.getAsInteger((Object)offset.get("ORIENT")) || ConverterUtils.getAsInteger((Object)offset.get("SRCTYPE")) >= 20) break;
                return true;
            }
        }
        return false;
    }

    public Set<String> getUnitCodeOnlyParent(List<String> orgIds, String dateStr, GcAuthorityType authorityType) {
        if (CollectionUtils.isEmpty(orgIds)) {
            return CollectionUtils.newHashSet();
        }
        HashSet<String> orgCodes = new HashSet<String>(orgIds);
        ArrayList removedCodes = new ArrayList();
        YearPeriodObject yp = new YearPeriodObject(null, dateStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)authorityType, (YearPeriodObject)yp);
        orgIds.forEach(orgCode -> {
            if (removedCodes.contains(orgCode)) {
                return;
            }
            List childrenAndSelf = tool.listAllOrgByParentIdContainsSelf(orgCode);
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            childrenAndSelf.forEach(org -> {
                if (!org.getCode().equals(orgCode) && orgCodes.contains(org.getCode())) {
                    thisTimeNeedRemovedCodes.add(org.getCode());
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            orgCodes.removeAll(thisTimeNeedRemovedCodes);
        });
        return orgCodes;
    }
}

