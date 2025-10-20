/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractRegistry;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlOffsetItemComparatorUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlExtractServiceImpl
implements SameCtrlExtractService {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlExtractServiceImpl.class);
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
    @Autowired
    private SameCtrlExtractRegistry sameCtrlExtractRegistry;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static Map<String, List<String>> extractType2SameCtrlSrcType = new HashMap<String, List<String>>(){
        {
            this.put(SameCtrlExtractTypeEnum.DISPOSER.getCode(), Arrays.asList(SameCtrlSrcTypeEnum.DISPOSER_INVEST.getCode(), SameCtrlSrcTypeEnum.DISPOSER_LAST_DATE_INVEST.getCode(), SameCtrlSrcTypeEnum.DISPOSER_INPUT_ADJUST.getCode(), SameCtrlSrcTypeEnum.DISPOSER_PROFITLOSS.getCode()));
            this.put(SameCtrlExtractTypeEnum.ACQUIRER.getCode(), Arrays.asList(SameCtrlSrcTypeEnum.ACQUIRER_BEGIN_EXTRACT.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_INVEST.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_BEFORE_EXTRACT.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_LAST_DATE_EXTRACT.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_INPUT_ADJUST.getCode()));
            this.put(SameCtrlExtractTypeEnum.ACQUIRER_PARENT.getCode(), Arrays.asList(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEGIN_EXTRACT.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEFORE_EXTRACT.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_PARENT_INPUT_ADJUST.getCode(), SameCtrlSrcTypeEnum.ACQUIRER_PARENT_DATE_EXTRACT.getCode()));
            this.put(SameCtrlExtractTypeEnum.COMMON_UNIT_OFFSET_GO_BACK.getCode(), Collections.singletonList(SameCtrlSrcTypeEnum.MERGE_UNIT_PARENT_INPUT_ADJUST.getCode()));
        }
    };

    @Override
    public void extractSameCtrlData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        GcOrgTypeUtils.setContextEntityId((String)sameCtrlExtractDataVO.getOrgType());
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlExtractDataVO.getSameCtrlChgOrg().getId()));
        SameCtrlManageUtil.checkSameCtrlChgOrgAuthority(sameCtrlExtractDataVO.getSchemeId(), sameCtrlExtractDataVO.getPeriodStr(), sameCtrlExtractDataVO.getOrgType(), sameCtrlChgOrgEO.getVirtualCode());
        SameCtrlChgOrgVO sameCtrlChgOrgVO = new SameCtrlChgOrgVO();
        BeanUtils.copyProperties((Object)sameCtrlChgOrgEO, sameCtrlChgOrgVO);
        sameCtrlExtractDataVO.setSameCtrlChgOrg(sameCtrlChgOrgVO);
        sameCtrlChgOrgVO.setDisposalDate(sameCtrlChgOrgEO.getChangeDate());
        SameCtrlExtractActuator sameCtrlExtractActuator = this.sameCtrlExtractRegistry.getSameCtrlExtractActuator(sameCtrlExtractDataVO.getSameCtrlChgOrg().getVirtualCodeType());
        sameCtrlExtractActuator.sameCtrlExtractData(sameCtrlExtractDataVO);
    }

    @Override
    public Pagination<SameCtrlOffSetItemVO> listOffsetSameCtrlManage(SameCtrlOffsetCond sameCtrlOffsetCond) {
        Set<String> mRecids;
        Pagination paginationVO = new Pagination(null, Integer.valueOf(0), Integer.valueOf(sameCtrlOffsetCond.getPageNum()), Integer.valueOf(sameCtrlOffsetCond.getPageSize()));
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlOffsetCond.getSameCtrlChgId()));
        if (SameCtrlExtractTypeEnum.COMMON_UNIT_OFFSET_GO_BACK.getCode().equals(sameCtrlChgOrgEO.getVirtualCodeType())) {
            ArrayList goBackEoList = new ArrayList();
            List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgByMRecid(sameCtrlChgOrgEO.getmRecid());
            ArrayList<SameCtrlChgOrgEO> sameCtrlChgOrgEOQueryList = new ArrayList<SameCtrlChgOrgEO>();
            sameCtrlChgOrgEOQueryList.add(sameCtrlChgOrgEO);
            sameCtrlChgOrgEOQueryList.addAll(this.filterSameCtrlChgOrgEOList(sameCtrlChgOrgEOList, sameCtrlChgOrgEO));
            sameCtrlChgOrgEOQueryList.forEach(EO -> {
                sameCtrlOffsetCond.setSameCtrlChgId(EO.getId());
                List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOQueryList = this.sameCtrlOffSetItemDao.listOffsetsByParams(sameCtrlOffsetCond, extractType2SameCtrlSrcType.get(EO.getVirtualCodeType()));
                goBackEoList.addAll(sameCtrlOffSetItemEOQueryList);
            });
            mRecids = goBackEoList.stream().map(SameCtrlOffSetItemEO::getmRecid).collect(Collectors.toSet());
            paginationVO.setTotalElements(Integer.valueOf(mRecids.size()));
        } else {
            mRecids = new HashSet<String>();
            int totalCount = this.sameCtrlOffSetItemDao.listOffsetsByParams(sameCtrlOffsetCond, mRecids);
            paginationVO.setTotalElements(Integer.valueOf(totalCount));
        }
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList = new ArrayList<SameCtrlOffSetItemEO>(this.sameCtrlOffSetItemDao.listSameCtrlOffsetsOrderByMrecidAndId(mRecids));
        if (SameCtrlExtractTypeEnum.COMMON_UNIT_OFFSET_GO_BACK.getCode().equals(sameCtrlChgOrgEO.getVirtualCodeType())) {
            this.saveAllSameCtrlGoBackNegation(sameCtrlOffSetItemEOList);
        }
        if (!CollectionUtils.isEmpty(sameCtrlOffSetItemEOList)) {
            List<SameCtrlOffSetItemVO> itemVOs = this.convertEO2VO(sameCtrlOffsetCond, sameCtrlOffSetItemEOList);
            paginationVO.setContent(this.setRowSpanAndSort(itemVOs));
        } else {
            paginationVO.setContent(new ArrayList());
        }
        return paginationVO;
    }

    private void saveAllSameCtrlGoBackNegation(List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList) {
        sameCtrlOffSetItemEOList.forEach(sameCtrlOffSetItemEO -> {
            if (!SameCtrlSrcTypeEnum.MERGE_UNIT_PARENT_INPUT_ADJUST.getCode().equals(sameCtrlOffSetItemEO.getSameCtrlSrcType())) {
                if (sameCtrlOffSetItemEO.getOffSetCredit() != null && sameCtrlOffSetItemEO.getOffSetCredit() != 0.0) {
                    sameCtrlOffSetItemEO.setOffSetCredit(0.0 - sameCtrlOffSetItemEO.getOffSetCredit());
                }
                if (sameCtrlOffSetItemEO.getOffSetDebit() != null && sameCtrlOffSetItemEO.getOffSetDebit() != 0.0) {
                    sameCtrlOffSetItemEO.setOffSetDebit(0.0 - sameCtrlOffSetItemEO.getOffSetDebit());
                }
            }
        });
    }

    private List<SameCtrlChgOrgEO> filterSameCtrlChgOrgEOList(List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList, SameCtrlChgOrgEO sameCtrlChgOrgEO) {
        return sameCtrlChgOrgEOList.stream().filter(EO -> Arrays.asList(sameCtrlChgOrgEO.getCorrespondVirtualCode().split(";")).contains(EO.getVirtualCode()) && !SameCtrlExtractTypeEnum.VIRTUAL.getCode().equals(EO.getVirtualCodeType()) && !SameCtrlExtractTypeEnum.COMMON_UNIT_GO_BACK.getCode().equals(EO.getVirtualCodeType())).collect(Collectors.toList());
    }

    private List<SameCtrlOffSetItemVO> convertEO2VO(SameCtrlOffsetCond sameCtrlOffsetCond, List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList) {
        YearPeriodObject yp = new YearPeriodObject(sameCtrlOffsetCond.getSchemeId(), sameCtrlOffsetCond.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)sameCtrlOffsetCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String systemId = this.taskService.getSystemIdByTaskId(sameCtrlOffsetCond.getTaskId(), sameCtrlOffsetCond.getPeriodStr());
        return sameCtrlOffSetItemEOList.stream().map(item -> {
            GcOrgCacheVO oppUnitVo;
            SameCtrlOffSetItemVO vo = this.convertEO2VO((SameCtrlOffSetItemEO)((Object)item), systemId);
            GcOrgCacheVO unitVo = tool.getOrgByCode(vo.getUnitCode());
            if (unitVo != null) {
                vo.setUnitVo(unitVo);
                vo.setUnitTitle(unitVo.getTitle());
            }
            if ((oppUnitVo = tool.getOrgByCode(vo.getOppUnitCode())) != null) {
                vo.setOppUnitVo(oppUnitVo);
                vo.setOppUnitTitle(oppUnitVo.getTitle());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    private List<SameCtrlOffSetItemVO> setRowSpanAndSort(List<SameCtrlOffSetItemVO> unSortedRecords) {
        ArrayList<SameCtrlOffSetItemVO> rowSpanRecords = new ArrayList<SameCtrlOffSetItemVO>();
        AtomicInteger index = new AtomicInteger(1);
        ArrayList groupedList = new ArrayList(unSortedRecords.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemVO::getmRecid, LinkedHashMap::new, Collectors.toList())).values());
        groupedList.forEach(list -> {
            if (((SameCtrlOffSetItemVO)list.get(0)).getElmMode() != null && OffsetElmModeEnum.INPUT_ITEM.getValue() != ((SameCtrlOffSetItemVO)list.get(0)).getElmMode().intValue()) {
                list.sort(SameCtrlOffsetItemComparatorUtil.mapUniversalComparator());
            }
            ((SameCtrlOffSetItemVO)list.get(0)).setIndex(index.getAndIncrement());
            ((SameCtrlOffSetItemVO)list.get(0)).setRowspan(list.size());
            rowSpanRecords.addAll((Collection<SameCtrlOffSetItemVO>)list);
        });
        return rowSpanRecords;
    }

    private SameCtrlOffSetItemVO convertEO2VO(SameCtrlOffSetItemEO itemEO, String systemId) {
        SameCtrlOffSetItemVO vo = new SameCtrlOffSetItemVO();
        BeanUtils.copyProperties((Object)itemEO, vo);
        ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectByCode(systemId, vo.getSubjectCode());
        vo.setSubjectVo(subjectEO);
        vo.setSubjectTitle(subjectEO.getTitle());
        String sourceMethodTtile = SameCtrlSrcTypeEnum.getSourceMethodTtile((String)itemEO.getSameCtrlSrcType());
        vo.setSourceMethodTtile(sourceMethodTtile);
        vo.setSameCtrlSrcTypeTitle(SameCtrlSrcTypeEnum.getTitleByCode((String)itemEO.getSameCtrlSrcType()));
        if (OrientEnum.D.getValue().equals(vo.getOrient())) {
            vo.setOffsetDebitStr(NumberUtils.doubleToString((Double)vo.getOffSetDebit()));
            vo.setDiffStr(NumberUtils.doubleToString((Double)itemEO.getDiffd()));
        } else {
            vo.setOffsetCreditStr(NumberUtils.doubleToString((Double)vo.getOffSetCredit()));
            vo.setDiffStr(NumberUtils.doubleToString((Double)itemEO.getDiffc()));
        }
        return vo;
    }

    private List<String> listSrcTypeCodes(String showTabTypeCode) {
        ArrayList<String> srcTypeCodes = new ArrayList<String>();
        if ("endOffsetTab".equals(showTabTypeCode)) {
            srcTypeCodes.add(SameCtrlSrcTypeEnum.END_EXTRACT.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.END_INPUT.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.END_INVEST_CALC_INIT.getCode());
        } else if ("beginOffsetTab".equals(showTabTypeCode)) {
            srcTypeCodes.add(SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode());
            srcTypeCodes.add(SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode());
        }
        return srcTypeCodes;
    }

    @Override
    public void disEnable(List<String> mRecids) {
        this.sameCtrlChgOrgDao.disEnable(mRecids);
    }

    @Override
    public void enable(List<String> mRecids) {
        this.sameCtrlChgOrgDao.enable(mRecids);
    }

    @Override
    public void delOffset(List<String> mRecids) {
        String[] columnNamesInDB = new String[]{"mRecid"};
        Object[] values = new Object[]{mRecids};
        List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList = this.sameCtrlOffSetItemDao.queryOffsetRecordsByWhere(columnNamesInDB, values);
        this.addInputAdjustDeleteLogs(sameCtrlOffSetItemEOList);
        this.sameCtrlOffSetItemDao.deleteByMRecid(mRecids);
    }

    private void addInputAdjustDeleteLogs(List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList) {
        if (CollectionUtils.isEmpty(sameCtrlOffSetItemEOList)) {
            return;
        }
        SameCtrlOffSetItemEO sameCtrlOffSetItemEO = sameCtrlOffSetItemEOList.get(0);
        StringBuilder initLogs = new StringBuilder();
        SameCtrlOffSetItemEO offSetItem = sameCtrlOffSetItemEOList.get(0);
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)offSetItem.getSameCtrlChgId()));
        initLogs.append(" \u4efb\u52a1:").append(this.getTaskTitle(sameCtrlOffSetItemEO.getTaskId()));
        initLogs.append(";\n \u62a5\u8868\u65b9\u6848:").append(this.getSchemeDefineTitle(sameCtrlOffSetItemEO.getSchemeId()));
        initLogs.append(";\n \u5408\u5e76\u5355\u4f4d:").append(sameCtrlOffSetItemEO.getInputUnitCode());
        initLogs.append(";\n \u65f6\u671f:").append(new PeriodWrapper(sameCtrlOffSetItemEO.getDefaultPeriod()).toTitleString());
        initLogs.append(";\n \u53d8\u52a8\u5355\u4f4d:").append(sameCtrlChgOrgEO.getChangedCode());
        initLogs.append(";\n \u53d8\u52a8\u65f6\u95f4:").append(new SimpleDateFormat("yyyy-MM-dd").format(sameCtrlChgOrgEO.getChangeDate()));
        initLogs.append(";\n \u53d8\u52a8\u7c7b\u578b:").append(ChangedOrgTypeEnum.getChangeTypeNameByCode((Integer)sameCtrlChgOrgEO.getChangedOrgType()));
        initLogs.append(";\n \u540c\u63a7\u6765\u6e90\u7c7b\u578b:").append(SameCtrlSrcTypeEnum.getTitleByCode((String)offSetItem.getSameCtrlSrcType()));
        initLogs.append(";\n \u53d8\u52a8\u524d\u540e\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d:").append(offSetItem.getSameParentCode());
        Map<String, List<SameCtrlOffSetItemEO>> offsetItemGroupsMap = sameCtrlOffSetItemEOList.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemEO::getmRecid));
        for (List<SameCtrlOffSetItemEO> offSetItemEOList : offsetItemGroupsMap.values()) {
            StringBuilder logs = new StringBuilder(initLogs.toString());
            logs.append(";\n \u5408\u5e76\u89c4\u5219:").append(offSetItem.getRuleTitle());
            logs.append(";\n \u5f71\u54cd\u671f\u95f4:").append(EFFECTTYPE.getTitleByCode((String)offSetItem.getEffectType()));
            for (int i = 0; i < offSetItemEOList.size(); ++i) {
                SameCtrlOffSetItemEO eo = offSetItemEOList.get(i);
                OrientEnum orientEnum = OrientEnum.valueOf((Integer)eo.getOrient());
                Double amount = orientEnum == OrientEnum.D ? eo.getOffSetDebit() : eo.getOffSetCredit();
                logs.append(";\n \u5206\u5f55").append(i + 1).append(": \u672c\u65b9\u5355\u4f4d:").append(eo.getUnitCode()).append("; \u5bf9\u65b9\u5355\u4f4d:").append(eo.getOppUnitCode()).append("; \u79d1\u76ee:").append(eo.getSubjectCode()).append("; ").append(orientEnum.getTitle()).append("\u65b9\u91d1\u989d:").append(df.format(amount));
            }
            String sourceMethodTtile = SameCtrlSrcTypeEnum.getSourceMethodTtile((String)offSetItemEOList.get(0).getSameCtrlSrcType());
            LogHelper.info((String)("\u540c\u63a7" + sourceMethodTtile), (String)"\u53d6\u6d88\u62b5\u9500", (String)logs.toString());
        }
    }

    private String getTaskTitle(String taskId) {
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            if (taskDefine != null) {
                return taskDefine.getTitle();
            }
        }
        catch (Exception e) {
            return "\u83b7\u53d6\u4efb\u52a1\u6807\u9898\u5f02\u5e38" + e.getMessage();
        }
        return "";
    }

    private String getSchemeDefineTitle(String schemeId) {
        try {
            FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(schemeId);
            if (schemeDefine != null) {
                return schemeDefine.getTitle();
            }
        }
        catch (Exception e) {
            return "\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38" + e.getMessage();
        }
        return null;
    }
}

