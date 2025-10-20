/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="sameCtrlOffSetItemServiceImpl")
public class SameCtrlOffSetItemServiceImpl
implements SameCtrlOffSetItemService {
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired
    private UnionRuleDao ruleDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedTaskService taskCacheService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private RuleManagerFactory ruleManagerFactory;
    @Autowired
    private SameCtrlExtractLogService sameCtrlExtractLogService;
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlOffSetItemServiceImpl.class);
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");

    protected SameCtrlOffSetItemVO convertEO2VO(SameCtrlOffSetItemEO itemEO, String systemId) {
        SameCtrlOffSetItemVO vo = new SameCtrlOffSetItemVO();
        BeanUtils.copyProperties((Object)itemEO, vo);
        ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectByCode(systemId, vo.getSubjectCode());
        vo.setSubjectVo(subjectEO);
        vo.setSubjectTitle(subjectEO.getTitle());
        String sourceMethodTtile = SameCtrlSrcTypeEnum.getSourceMethodTtile((String)itemEO.getSameCtrlSrcType());
        vo.setSourceMethodTtile(sourceMethodTtile);
        if (OrientEnum.D.getValue().equals(vo.getOrient())) {
            vo.setOffsetDebitStr(NumberUtils.doubleToString((Double)vo.getOffSetDebit()));
            vo.setDiffStr(NumberUtils.doubleToString((Double)itemEO.getDiffd()));
        } else {
            vo.setOffsetCreditStr(NumberUtils.doubleToString((Double)vo.getOffSetCredit()));
            vo.setDiffStr(NumberUtils.doubleToString((Double)itemEO.getDiffc()));
        }
        return vo;
    }

    @Override
    public Pagination<SameCtrlOffSetItemVO> listOffsets(SameCtrlOffsetCond cond) {
        List<String> inputUnitCodes;
        List<String> srcTypeCodes = this.listSrcTypeCodes(cond.getShowTabType());
        Pagination<SameCtrlOffSetItemEO> pagination = this.sameCtrlOffSetItemDao.listOffsets(cond, srcTypeCodes, inputUnitCodes = this.listInputUnitCodes(cond));
        List itemEOs = pagination.getContent();
        if (!CollectionUtils.isEmpty((Collection)itemEOs)) {
            YearPeriodObject yp = new YearPeriodObject(null, ((SameCtrlOffSetItemEO)((Object)itemEOs.get(0))).getDefaultPeriod());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)((SameCtrlOffSetItemEO)((Object)itemEOs.get(0))).getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            String systemId = this.taskCacheService.getSystemIdBySchemeId(cond.getSchemeId(), cond.getPeriodStr());
            List<SameCtrlOffSetItemVO> itemVOs = itemEOs.stream().map(item -> {
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
            pagination.setContent(this.setRowSpanAndSort(itemVOs));
        }
        return pagination;
    }

    private List<String> listInputUnitCodes(SameCtrlOffsetCond cond) {
        ArrayList<String> inputUnitCodes = new ArrayList<String>();
        if (cond.getMergeUnitCode().equals(cond.getSameParentCode())) {
            inputUnitCodes = cond.getInputUnitCodes();
        } else {
            inputUnitCodes.add(cond.getMergeUnitCode());
        }
        return inputUnitCodes;
    }

    private List<SameCtrlOffSetItemVO> setRowSpanAndSort(List<SameCtrlOffSetItemVO> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<SameCtrlOffSetItemVO> sortedRecords = new ArrayList<SameCtrlOffSetItemVO>();
        ArrayList<SameCtrlOffSetItemVO> oneEntryRecords = new ArrayList<SameCtrlOffSetItemVO>();
        String mrecid = null;
        int entryIndex = 1;
        for (SameCtrlOffSetItemVO record : unSortedRecords) {
            String tempMrecid = record.getmRecid();
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    sortedRecords.addAll(oneEntryRecords);
                    ((SameCtrlOffSetItemVO)oneEntryRecords.get(0)).setRowspan(size);
                    ((SameCtrlOffSetItemVO)oneEntryRecords.get(0)).setIndex(entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            sortedRecords.addAll(oneEntryRecords);
            ((SameCtrlOffSetItemVO)oneEntryRecords.get(0)).setRowspan(size);
            ((SameCtrlOffSetItemVO)oneEntryRecords.get(0)).setIndex(entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
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
    public void extractData(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteOffsetEntrysByMrecid(SameCtrlOffsetCond condition) {
        if (condition.getmRecids() == null) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u5220\u9664\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f[\u62b5\u9500\u5206\u5f55\u5206\u7ec4ID\u53c2\u6570\u4e0d\u5141\u8bb8\u4e3a\u7a7a]\u3002");
        }
        List<SameCtrlOffSetItemEO> offSetItemEOList = this.sameCtrlOffSetItemDao.listSameCtrlOffsets(condition.getmRecids());
        this.addInputAdjustDeleteLogs(condition, offSetItemEOList);
        YearPeriodDO yp = new YearPeriodObject(null, condition.getPeriodStr()).formatYP();
        this.deleteInputAdjustBySrcGroupIds(offSetItemEOList, yp);
        this.deleteOffsetEntrys(offSetItemEOList);
    }

    private void deleteOffsetEntrys(List<SameCtrlOffSetItemEO> offSetItemEOList) {
        Set mRecids = offSetItemEOList.stream().filter(item -> !SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode().equals(item.getSameCtrlSrcType()) && !SameCtrlSrcTypeEnum.END_INPUT.getCode().equals(item.getSameCtrlSrcType())).map(SameCtrlOffSetItemEO::getmRecid).collect(Collectors.toSet());
        this.sameCtrlOffSetItemDao.deleteOffsetEntrysByMrecid(new ArrayList<String>(mRecids));
    }

    private void deleteInputAdjustBySrcGroupIds(List<SameCtrlOffSetItemEO> offSetItemEOList, YearPeriodDO yearPeriodUtil) {
        List<String> periodStrs = this.listPeriods(yearPeriodUtil, offSetItemEOList.get(0).getSameCtrlChgId());
        Set<String> inputAdjustSrcGroupIds = offSetItemEOList.stream().filter(item -> SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode().equals(item.getSameCtrlSrcType()) || SameCtrlSrcTypeEnum.END_INPUT.getCode().equals(item.getSameCtrlSrcType())).map(SameCtrlOffSetItemEO::getSrcOffsetGroupId).collect(Collectors.toSet());
        this.sameCtrlOffSetItemDao.deleteOffsetEntrysBySrcOffsetGroupId(inputAdjustSrcGroupIds, periodStrs);
    }

    private List<String> listPeriods(YearPeriodDO yearPeriodUtil, String sameCtrlChgId) {
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlChgId));
        Date disposeDate = sameCtrlChgOrgEO.getDisposalDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(disposeDate);
        cal.add(1, 1);
        Integer[] periods = this.getAllPeriodByType(yearPeriodUtil.getType());
        ArrayList<String> periodStrs = new ArrayList<String>();
        for (Integer period : periods) {
            YearPeriodObject periodUtil = period >= yearPeriodUtil.getPeriod() ? new YearPeriodObject(null, yearPeriodUtil.getYear(), yearPeriodUtil.getType(), period.intValue()) : new YearPeriodObject(null, yearPeriodUtil.getYear() + 1, yearPeriodUtil.getType(), period.intValue());
            if (periodUtil.formatYP().getEndDate().getTime() > cal.getTime().getTime()) continue;
            periodStrs.add(periodUtil.formatYP().getFormatValue());
        }
        return periodStrs;
    }

    @Override
    public List<SameCtrlOffSetItemVO> queryInputAdjustment(String mrecid) {
        List<SameCtrlOffSetItemEO> eos = this.sameCtrlOffSetItemDao.queryInputAdjustment(mrecid);
        if (CollectionUtils.isEmpty(eos)) {
            return new ArrayList<SameCtrlOffSetItemVO>();
        }
        YearPeriodObject yp = new YearPeriodObject(null, eos.get(0).getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)eos.get(0).getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        String systemId = this.taskCacheService.getSystemIdBySchemeId(eos.get(0).getSchemeId(), eos.get(0).getDefaultPeriod());
        UnionRuleEO unionRuleEO = this.ruleDao.findUnionRuleEOByTitle(systemId, eos.get(0).getRuleTitle());
        return eos.stream().map(item -> {
            SameCtrlOffSetItemVO vo = this.convertEO2VO((SameCtrlOffSetItemEO)((Object)item), systemId);
            vo.setRuleId(unionRuleEO.getId());
            vo.setRuleParentId(unionRuleEO.getParentId());
            GcOrgCacheVO unitVo = tool.getOrgByCode(vo.getUnitCode());
            vo.setUnitVo(unitVo);
            if (unitVo != null) {
                vo.setUnitTitle(unitVo.getTitle());
            }
            GcOrgCacheVO oppUnitVo = tool.getOrgByCode(vo.getOppUnitCode());
            vo.setOppUnitVo(oppUnitVo);
            if (oppUnitVo != null) {
                vo.setOppUnitTitle(oppUnitVo.getTitle());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveInputAdjustment(List<List<SameCtrlOffSetItemVO>> batchlist) {
        Collections.reverse(batchlist);
        if (CollectionUtils.isEmpty(batchlist)) {
            return;
        }
        this.checkSubject(batchlist);
        YearPeriodDO yearPeriodUtil = new YearPeriodObject(null, batchlist.get(0).get(0).getDefaultPeriod()).formatYP();
        ArrayList<String> periodStrs = new ArrayList<String>();
        String logs = this.addInputAdjustLogs(batchlist.get(0).get(0));
        HashSet<String> srcOffsetGroupIdSet = new HashSet<String>();
        ArrayList addList = new ArrayList();
        batchlist.forEach(list -> this.assemeInputAdjustmentOffsets(addList, (Set<String>)srcOffsetGroupIdSet, (List<String>)periodStrs, yearPeriodUtil, (List<SameCtrlOffSetItemVO>)list, logs));
        this.sameCtrlOffSetItemDao.deleteOffsetEntrysBySrcOffsetGroupId(srcOffsetGroupIdSet, periodStrs);
        this.sameCtrlOffSetItemDao.saveAll(addList);
    }

    private String addInputAdjustLogs(SameCtrlOffSetItemVO offSetItemVO) {
        StringBuilder logs = new StringBuilder();
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)offSetItemVO.getSameCtrlChgId()));
        logs.append(" \u4efb\u52a1:").append(this.getTaskTitle(offSetItemVO.getTaskId()));
        logs.append(";\n \u62a5\u8868\u65b9\u6848:").append(this.getSchemeDefineTitle(offSetItemVO.getSchemeId()));
        logs.append(";\n \u5408\u5e76\u5355\u4f4d:").append(offSetItemVO.getInputUnitCode());
        logs.append(";\n \u65f6\u671f:").append(new PeriodWrapper(offSetItemVO.getDefaultPeriod()).toTitleString());
        logs.append(";\n \u53d8\u52a8\u5355\u4f4d:").append(sameCtrlChgOrgEO.getChangedCode());
        logs.append(";\n \u53d8\u52a8\u65f6\u95f4:").append(new SimpleDateFormat("yyyy-MM-dd").format(sameCtrlChgOrgEO.getChangeDate()));
        logs.append(";\n \u53d8\u52a8\u7c7b\u578b:").append(ChangedOrgTypeEnum.getChangeTypeNameByCode((Integer)sameCtrlChgOrgEO.getChangedOrgType()));
        logs.append(";\n \u540c\u63a7\u6765\u6e90\u7c7b\u578b:").append(SameCtrlSrcTypeEnum.getTitleByCode((String)offSetItemVO.getSameCtrlSrcType()));
        logs.append(";\n \u53d8\u52a8\u524d\u540e\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d:").append(offSetItemVO.getSameParentCode());
        return logs.toString();
    }

    private void assemeInputAdjustmentOffsets(List<SameCtrlOffSetItemEO> addList, Set<String> srcOffsetGroupIds, List<String> periodStrs, YearPeriodDO yearPeriodUtil, List<SameCtrlOffSetItemVO> list, String initLogs) {
        boolean isBeginInput;
        SameCtrlOffSetItemVO offSetItemVO = list.get(0);
        String srcOffsetGroupId = offSetItemVO.getSrcOffsetGroupId();
        srcOffsetGroupIds.add(srcOffsetGroupId);
        List<SameCtrlOffSetItemEO> eoList = this.convertVoList2EoList(list);
        String mid = UUIDOrderSnowUtils.newUUIDStr();
        StringBuilder logs = new StringBuilder(initLogs);
        logs.append(";\n \u5408\u5e76\u89c4\u5219:").append(offSetItemVO.getRuleTitle());
        logs.append(";\n \u5f71\u54cd\u671f\u95f4:").append(EFFECTTYPE.getTitleByCode((String)offSetItemVO.getEffectType()));
        for (int i = 0; i < eoList.size(); ++i) {
            SameCtrlOffSetItemEO eo = eoList.get(i);
            Assert.isTrue((null != eo.getInputUnitCode() ? 1 : 0) != 0, (String)"\u5f55\u5165\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            eo.setmRecid(mid);
            eo.setSrcOffsetGroupId(srcOffsetGroupId);
            eo.setSrcId(!StringUtils.isEmpty((String)eo.getSrcId()) ? eo.getSrcId() : eo.getId());
            addList.add(eo);
            OrientEnum orientEnum = OrientEnum.valueOf((Integer)eo.getOrient());
            Double amount = orientEnum == OrientEnum.D ? eo.getOffSetDebit() : eo.getOffSetCredit();
            logs.append(";\n \u5206\u5f55").append(i + 1).append(": \u672c\u65b9\u5355\u4f4d:").append(eo.getUnitCode()).append("; \u5bf9\u65b9\u5355\u4f4d:").append(eo.getOppUnitCode()).append("; \u79d1\u76ee:").append(eo.getSubjectCode()).append("; ").append(orientEnum.getTitle()).append("\u65b9\u91d1\u989d:").append(df.format(amount));
        }
        String operateType = !StringUtils.isEmpty((String)offSetItemVO.getmRecid()) ? "\u4fee\u6539" : "\u65b0\u589e";
        LogHelper.info((String)"\u540c\u63a7\u8f93\u5165\u8c03\u6574", (String)operateType, (String)logs.toString());
        boolean bl = isBeginInput = "effectLongTerm".equals(eoList.get(0).getEffectType()) && SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode().equals(eoList.get(0).getSameCtrlSrcType()) && (SubjectAttributeEnum.ASSET.getValue() == list.get(0).getSubjectVo().getAttri().intValue() || SubjectAttributeEnum.DEBT.getValue() == list.get(0).getSubjectVo().getAttri().intValue() || SubjectAttributeEnum.RIGHT.getValue() == list.get(0).getSubjectVo().getAttri().intValue());
        if ("effectCurrYear".equals(eoList.get(0).getEffectType()) || isBeginInput) {
            periodStrs.add(yearPeriodUtil.getFormatValue());
            this.listEffectCurrYearOffsets(addList, yearPeriodUtil, list, eoList, periodStrs);
        } else if ("effectLongTerm".equals(eoList.get(0).getEffectType())) {
            if (CollectionUtils.isEmpty(periodStrs)) {
                periodStrs.addAll(this.listPeriods(yearPeriodUtil, eoList.get(0).getSameCtrlChgId()));
            }
            this.listEffectLongTermOffsets(addList, yearPeriodUtil, list, eoList, periodStrs);
        }
    }

    private void listEffectCurrYearOffsets(List<SameCtrlOffSetItemEO> addList, YearPeriodDO yearPeriodUtil, List<SameCtrlOffSetItemVO> list, List<SameCtrlOffSetItemEO> eoList, List<String> periodStrs) {
        Integer[] laterPeriods;
        Integer[] integerArray = laterPeriods = this.getLaterPeriodByType(list.get(0).getPeriodType(), yearPeriodUtil.getPeriod());
        int n = integerArray.length;
        for (int i = 0; i < n; ++i) {
            int period = integerArray[i];
            String defaultPeriod = new YearPeriodObject(null, yearPeriodUtil.getYear(), yearPeriodUtil.getType(), period).formatYP().getFormatValue();
            periodStrs.add(defaultPeriod);
            String mid2 = UUIDOrderUtils.newUUIDStr();
            for (SameCtrlOffSetItemEO eo2 : eoList) {
                SameCtrlOffSetItemEO laterEo = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties((Object)eo2, (Object)laterEo);
                laterEo.setId(UUIDOrderUtils.newUUIDStr());
                laterEo.setmRecid(mid2);
                laterEo.setDefaultPeriod(defaultPeriod);
                addList.add(laterEo);
            }
        }
    }

    private void listEffectLongTermOffsets(List<SameCtrlOffSetItemEO> addList, YearPeriodDO yearPeriodUtil, List<SameCtrlOffSetItemVO> list, List<SameCtrlOffSetItemEO> eoList, List<String> periodStrs) {
        for (String defaultPeriod : periodStrs) {
            if (defaultPeriod.equals(yearPeriodUtil.getFormatValue())) continue;
            String mid2 = UUIDOrderUtils.newUUIDStr();
            for (SameCtrlOffSetItemEO eo2 : eoList) {
                SameCtrlOffSetItemEO laterEo = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties((Object)eo2, (Object)laterEo);
                laterEo.setId(UUIDOrderUtils.newUUIDStr());
                laterEo.setmRecid(mid2);
                laterEo.setDefaultPeriod(defaultPeriod);
                addList.add(laterEo);
            }
        }
    }

    private void checkSubject(List<List<SameCtrlOffSetItemVO>> batchlist) {
        if (SameCtrlSrcTypeEnum.END_INPUT.getCode().equals(batchlist.get(0).get(0).getSameCtrlSrcType())) {
            this.checkEndInputSubject(batchlist);
        } else if (SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode().equals(batchlist.get(0).get(0).getSameCtrlSrcType())) {
            this.checkBeginInputSubject(batchlist);
        }
    }

    private void checkBeginInputSubject(List<List<SameCtrlOffSetItemVO>> batchlist) {
        String systemId = this.getSystemId(batchlist);
        for (List<SameCtrlOffSetItemVO> voList : batchlist) {
            int assetOrDebit = 0;
            int cash = 0;
            int profitLoss = 0;
            for (SameCtrlOffSetItemVO vo : voList) {
                ConsolidatedSubjectEO subject = vo.getSubjectVo();
                if (subject == null || subject.getAttri() == null && (subject = this.consolidatedSubjectService.getSubjectByCode(systemId, vo.getSubjectCode())) == null) continue;
                if (SubjectAttributeEnum.ASSET.getValue() == subject.getAttri().intValue() || SubjectAttributeEnum.DEBT.getValue() == subject.getAttri().intValue() || SubjectAttributeEnum.RIGHT.getValue() == subject.getAttri().intValue()) {
                    ++assetOrDebit;
                    continue;
                }
                if (SubjectAttributeEnum.CASH.getValue() == subject.getAttri().intValue()) {
                    ++cash;
                    continue;
                }
                if (SubjectAttributeEnum.PROFITLOSS.getValue() == subject.getAttri().intValue()) {
                    ++profitLoss;
                    continue;
                }
                throw new BusinessRuntimeException("\u7b2c" + (voList.get(0).getGroupIndex() + 1) + "\u7ec4,\u7b2c" + (voList.indexOf(vo) + 1) + "\u884c\u5206\u5f55\u4e0d\u6ee1\u8db3\u79d1\u76ee\u5f55\u5165\u89c4\u5219\uff0c\u53ea\u80fd\u5f55\u5165\u8d44\u4ea7\u7c7b\u3001\u8d1f\u503a\u7c7b\u3001\u6743\u76ca\u7c7b\u3001\u635f\u76ca\u7c7b\u3001\u73b0\u6d41\u7c7b\u79d1\u76ee");
            }
            if (assetOrDebit == voList.size() || cash == voList.size() || profitLoss == voList.size() || assetOrDebit + cash + profitLoss != voList.size()) continue;
            String subjectAttriTtile = assetOrDebit == 0 ? "\u73b0\u91d1\u6d41\u91cf\u7c7b\u548c\u635f\u76ca\u7c7b" : (cash == 0 ? "\u8d44\u4ea7\u8d1f\u503a\u7c7b\u548c\u635f\u76ca\u7c7b" : (profitLoss == 0 ? "\u8d44\u4ea7\u8d1f\u503a\u7c7b\u548c\u73b0\u91d1\u6d41\u91cf\u7c7b" : "\u8d44\u4ea7\u8d1f\u503a\u7c7b\u3001\u635f\u76ca\u7c7b\u3001\u73b0\u91d1\u6d41\u91cf\u7c7b"));
            throw new BusinessRuntimeException("\u7b2c" + (voList.get(0).getGroupIndex() + 1) + "\u7ec4\u5206\u5f55\u4e0d\u5141\u8bb8\u540c\u65f6\u5f55\u5165" + subjectAttriTtile + "\u79d1\u76ee");
        }
    }

    private void checkEndInputSubject(List<List<SameCtrlOffSetItemVO>> batchlist) {
        String systemId = this.getSystemId(batchlist);
        for (List<SameCtrlOffSetItemVO> voList : batchlist) {
            int cash = 0;
            int profitLoss = 0;
            for (SameCtrlOffSetItemVO vo : voList) {
                ConsolidatedSubjectEO subject = vo.getSubjectVo();
                if (subject == null || subject.getAttri() == null && (subject = this.consolidatedSubjectService.getSubjectByCode(systemId, vo.getSubjectCode())) == null) continue;
                if (SubjectAttributeEnum.CASH.getValue() == subject.getAttri().intValue()) {
                    ++cash;
                    continue;
                }
                if (SubjectAttributeEnum.PROFITLOSS.getValue() == subject.getAttri().intValue()) {
                    ++profitLoss;
                    continue;
                }
                if (SubjectAttributeEnum.RIGHT.getValue() == subject.getAttri().intValue()) continue;
                throw new BusinessRuntimeException("\u7b2c" + (voList.get(0).getGroupIndex() + 1) + "\u7ec4,\u7b2c" + (voList.indexOf(vo) + 1) + "\u884c\u5206\u5f55\u4e0d\u6ee1\u8db3\u79d1\u76ee\u5f55\u5165\u89c4\u5219\uff0c\u53ea\u80fd\u5f55\u5165\u635f\u76ca\u7c7b\u3001\u73b0\u6d41\u7c7b\u79d1\u76ee");
            }
            if (cash <= 0 || profitLoss <= 0) continue;
            throw new BusinessRuntimeException("\u7b2c" + (voList.get(0).getGroupIndex() + 1) + "\u7ec4\u5206\u5f55\u4e0d\u5141\u8bb8\u540c\u65f6\u5f55\u5165\u635f\u76ca\u7c7b\u548c\u73b0\u91d1\u6d41\u91cf\u7c7b\u79d1\u76ee");
        }
    }

    private String getSystemId(List<List<SameCtrlOffSetItemVO>> batchlist) {
        if (batchlist == null || batchlist.size() == 0 || batchlist.get(0) == null || batchlist.get(0).size() == 0) {
            throw new BusinessRuntimeException("\u8bf7\u6dfb\u52a0\u5f55\u5165\u5206\u5f55\u540e\u518d\u8fdb\u884c\u4fdd\u5b58\uff01");
        }
        String schemeId = batchlist.get(0).get(0).getSchemeId();
        String periodStr = batchlist.get(0).get(0).getDefaultPeriod();
        return this.taskCacheService.getSystemIdBySchemeId(schemeId, periodStr);
    }

    public Integer[] getLaterPeriodByType(int periodType, int currPeriod) {
        Integer[] periods = this.getAllPeriodByType(periodType);
        ArrayList<Integer> laterPeriods = new ArrayList<Integer>();
        for (Integer period : periods) {
            if (period <= currPeriod) continue;
            laterPeriods.add(period);
        }
        return laterPeriods.toArray(new Integer[laterPeriods.size()]);
    }

    private Integer[] getAllPeriodByType(int periodType) {
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
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + periodType);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    public List<SameCtrlOffSetItemEO> convertVoList2EoList(List<SameCtrlOffSetItemVO> list) {
        ArrayList<SameCtrlOffSetItemEO> eoList = new ArrayList<SameCtrlOffSetItemEO>();
        HashSet<String> unitIdSet = new HashSet<String>();
        for (SameCtrlOffSetItemVO vo : list) {
            if (vo.getUnitVo() != null) {
                vo.setUnitCode(vo.getUnitVo().getCode());
                unitIdSet.add(vo.getUnitVo().getCode());
            }
            if (vo.getOppUnitVo() != null) {
                vo.setOppUnitCode(vo.getOppUnitVo().getCode());
                unitIdSet.add(vo.getOppUnitVo().getCode());
            }
            if (vo.getOffSetDebit() != null && vo.getOffSetDebit() != 0.0) {
                vo.setOrient(Integer.valueOf(1));
            } else if (vo.getOffSetCredit() != null && vo.getOffSetCredit() != 0.0) {
                vo.setOrient(Integer.valueOf(-1));
            }
            this.checkItemValid(vo);
            SameCtrlOffSetItemEO eo = new SameCtrlOffSetItemEO();
            BeanUtils.copyProperties(vo, (Object)eo);
            if (vo.getSubjectVo() != null) {
                eo.setSubjectCode(vo.getSubjectVo().getCode());
            }
            eo.setId(UUIDOrderUtils.newUUIDStr());
            eo.setCreateUser(NpContextHolder.getContext().getUser().getName());
            eo.setCreateTime(Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+8"))));
            eoList.add(eo);
        }
        this.checkGlobalValid(list);
        if (unitIdSet.size() == 2) {
            ArrayList unitIdList = new ArrayList(unitIdSet);
            for (SameCtrlOffSetItemEO eo : eoList) {
                if (eo.getUnitCode() != null && eo.getOppUnitCode() == null) {
                    eo.setOppUnitCode(eo.getUnitCode().equals(unitIdList.get(0)) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                    continue;
                }
                if (eo.getOppUnitCode() == null || eo.getUnitCode() != null) continue;
                eo.setUnitCode(eo.getOppUnitCode().equals(unitIdList.get(0)) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
            }
        }
        return eoList;
    }

    public void checkItemValid(SameCtrlOffSetItemVO vo) {
        if (vo.getTaskId() == null) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getSchemeId() == null) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u65b9\u6848\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getRuleId() == null) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u5408\u5e76\u89c4\u5219\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (!(SameCtrlSrcTypeEnum.DISPOSER_INPUT_ADJUST.getCode().equals(vo.getSameCtrlSrcType()) || SameCtrlSrcTypeEnum.DISPOSER_PARENT_INPUT_ADJUST.getCode().equals(vo.getSameCtrlSrcType()) || SameCtrlSrcTypeEnum.ACQUIRER_INPUT_ADJUST.getCode().equals(vo.getSameCtrlSrcType()) || SameCtrlSrcTypeEnum.ACQUIRER_PARENT_INPUT_ADJUST.getCode().equals(vo.getSameCtrlSrcType()) || SameCtrlSrcTypeEnum.MERGE_UNIT_PARENT_INPUT_ADJUST.getCode().equals(vo.getSameCtrlSrcType()) || vo.getUnitVo() != null || vo.getOppUnitVo() != null)) {
            throw new BusinessRuntimeException("\u7b2c" + vo.getGroupIndex() + 1 + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u8d37\u65b9\u5355\u4f4d\u5fc5\u987b\u6709\u4e00\u65b9\u6709\u503c\u3002");
        }
        if (vo.getSubjectVo() == null) {
            throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
    }

    public void checkGlobalValid(List<SameCtrlOffSetItemVO> list) {
        BigDecimal debitSum = new BigDecimal(0);
        BigDecimal creditSum = new BigDecimal(0);
        for (SameCtrlOffSetItemVO vo : list) {
            if (vo.getOffSetDebit() != null) {
                debitSum = NumberUtils.sum((BigDecimal)debitSum, (double)NumberUtils.round((double)vo.getOffSetDebit()));
            }
            if (vo.getOffSetCredit() == null) continue;
            creditSum = NumberUtils.sum((BigDecimal)creditSum, (double)NumberUtils.round((double)vo.getOffSetCredit()));
        }
        if (NumberUtils.sub((BigDecimal)debitSum, (BigDecimal)creditSum).compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u7b2c" + (list.get(0).getGroupIndex() + 1) + "\u7ec4\uff0c\u5206\u5f55\u501f\u8d37\u4e0d\u5e73\u3002");
        }
    }

    @Override
    public SameCtrlChgOrgEO getSameCtrlChgOrg(SameCtrlOffsetCond cond, GcOrgCenterService orgTool) {
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)cond.getSameCtrlChgId()));
        if (sameCtrlChgOrgEO == null || sameCtrlChgOrgEO.getChangeDate() == null || sameCtrlChgOrgEO.getDisposalDate() == null) {
            throw new RuntimeException("\u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u4e0d\u5168");
        }
        Integer changedOrgType = sameCtrlChgOrgEO.getChangedOrgType();
        if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(changedOrgType) && cond.getSameParentCode().equals(cond.getMergeUnitCode())) {
            GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(cond.getMergeUnitCode());
            String orgTitle = null == orgCacheVO ? "" : orgCacheVO.getTitle();
            throw new RuntimeException(String.format("\u5408\u5e76\u5355\u4f4d\uff1a%1s | %2s\u4e3a\u5904\u7f6e\u548c\u91c7\u8d2d\u65b9\u7684\u5171\u540c\u4e0a\u7ea7\u4e0d\u652f\u6301\u63d0\u53d6\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), orgTitle));
        }
        cond.setDisposalDate(sameCtrlChgOrgEO.getDisposalDate());
        return sameCtrlChgOrgEO;
    }

    @Override
    public List<GcOrgCacheVO> listUnitPaths(SameCtrlOffsetCond cond) {
        boolean existsSameParentOrg;
        ArrayList<GcOrgCacheVO> unitPaths = new ArrayList<GcOrgCacheVO>();
        String mergeUnitCode = cond.getMergeUnitCode();
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodObject);
        GcOrgCacheVO mergeUnitVo = tool.getOrgByCode(mergeUnitCode);
        unitPaths.add(mergeUnitVo);
        GcOrgCacheVO sameParentOrg = tool.getOrgByCode(cond.getSameParentCode());
        if (sameParentOrg == null) {
            return unitPaths;
        }
        boolean bl = existsSameParentOrg = mergeUnitVo != null && mergeUnitVo.getParentStr().contains(sameParentOrg.getParentStr());
        if (!existsSameParentOrg) {
            throw new BusinessRuntimeException("\u672a\u67e5\u8be2\u5230\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d");
        }
        String[] parents = mergeUnitVo.getParents();
        if (!cond.getSameParentCode().equals(mergeUnitCode)) {
            for (int i = parents.length - 1; i >= 0; --i) {
                String currOrgCode = parents[i];
                if (currOrgCode.equals(mergeUnitCode)) continue;
                GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(currOrgCode);
                if (gcOrgCacheVO != null) {
                    unitPaths.add(gcOrgCacheVO);
                }
                if (cond.getSameParentCode().equals(currOrgCode)) break;
            }
        }
        return unitPaths;
    }

    @Override
    public void deleteInputAdjustment(SameCtrlOffsetCond condition) {
        List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList = this.sameCtrlOffSetItemDao.listSameCtrlOffsets(condition.getmRecids());
        if (CollectionUtils.isEmpty(sameCtrlOffSetItemEOList)) {
            return;
        }
        SameCtrlOffSetItemEO offSetItem = sameCtrlOffSetItemEOList.get(0);
        this.addInputAdjustDeleteLogs(condition, sameCtrlOffSetItemEOList);
        if ("effectCurrMonth".equals(offSetItem.getEffectType())) {
            this.sameCtrlOffSetItemDao.deleteOffsetEntrysByMrecid(condition.getmRecids());
        } else {
            String srcOffsetGroupId = offSetItem.getSrcOffsetGroupId();
            YearPeriodDO currPeriodUtil = new YearPeriodObject(null, condition.getPeriodStr()).formatYP();
            List<SameCtrlOffSetItemEO> offSetItemEOList = this.sameCtrlOffSetItemDao.listOffsetEntrysBySrcOffsetGroupIds(Arrays.asList(srcOffsetGroupId));
            HashSet<String> periodStrSet = new HashSet<String>();
            for (SameCtrlOffSetItemEO itemEO : offSetItemEOList) {
                YearPeriodDO periodUtil;
                String defaultPeriod = itemEO.getDefaultPeriod();
                if (periodStrSet.contains(itemEO.getDefaultPeriod()) || (periodUtil = new YearPeriodObject(null, defaultPeriod).formatYP()).getYear() <= currPeriodUtil.getYear() && (periodUtil.getYear() != currPeriodUtil.getYear() || periodUtil.getPeriod() < currPeriodUtil.getPeriod())) continue;
                periodStrSet.add(defaultPeriod);
            }
            this.sameCtrlOffSetItemDao.deleteOffsetEntrysBySrcOffsetGroupId(new HashSet<String>(Arrays.asList(srcOffsetGroupId)), new ArrayList<String>(periodStrSet));
        }
    }

    private void addInputAdjustDeleteLogs(SameCtrlOffsetCond condition, List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOList) {
        StringBuilder initLogs = new StringBuilder();
        SameCtrlOffSetItemEO offSetItem = sameCtrlOffSetItemEOList.get(0);
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)offSetItem.getSameCtrlChgId()));
        initLogs.append(" \u4efb\u52a1:").append(this.getTaskTitle(condition.getTaskId()));
        initLogs.append(";\n \u62a5\u8868\u65b9\u6848:").append(this.getSchemeDefineTitle(condition.getSchemeId()));
        initLogs.append(";\n \u5408\u5e76\u5355\u4f4d:").append(condition.getMergeUnitCode());
        initLogs.append(";\n \u65f6\u671f:").append(new PeriodWrapper(condition.getPeriodStr()).toTitleString());
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

    protected void convertAndSaveSameCtrlOffset(List<GcOffSetVchrItemAdjustEO> gcOffSetItemEOS, SameCtrlOffsetCond cond, String sameCtrlSrcType, String schemeId) {
        HashMap ruleId2TitleMap = new HashMap();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(schemeId, cond.getPeriodStr(), null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getTitle()));
        Map<String, List<GcOffSetVchrItemAdjustEO>> mecid2OffsetItemMap = gcOffSetItemEOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)cond.getSameCtrlChgId()));
        Assert.isNotNull((Object)((Object)sameCtrlChgOrgEO), (String)"\u672a\u67e5\u8be2\u5230\u540c\u63a7\u53d8\u52a8\u6570\u636e", (Object[])new Object[0]);
        for (Map.Entry<String, List<GcOffSetVchrItemAdjustEO>> entry : mecid2OffsetItemMap.entrySet()) {
            List<GcOffSetVchrItemAdjustEO> offsetItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderUtils.newUUIDStr();
            double sumOffsetValue = 0.0;
            for (GcOffSetVchrItemAdjustEO gcOffSetItemEO : offsetItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties(gcOffSetItemEO, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setUnitCode(gcOffSetItemEO.getUnitId());
                sameCtrlOffSetItemEO.setOppUnitCode(gcOffSetItemEO.getOppUnitId());
                sameCtrlOffSetItemEO.setInputUnitCode(cond.getMergeUnitCode());
                YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
                GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(cond.getMergeUnitCode());
                sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
                sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
                sameCtrlOffSetItemEO.setChangedParentCode(sameCtrlChgOrgEO.getChangedParentCode());
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)cond.getDisposalDate()));
                sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(sameCtrlSrcType);
                sameCtrlOffSetItemEO.setOrgType(cond.getOrgType());
                sameCtrlOffSetItemEO.setRuleTitle(StringUtils.isEmpty((String)((String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()))) ? gcOffSetItemEO.getRuleId() : (String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()));
                sameCtrlOffSetItemEO.setSrcId(gcOffSetItemEO.getId());
                sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
                sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
            Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffSetItemEOS);
    }

    @Override
    public List<Map<String, Object>> sumOffsetsBySameSubjectCode(SameCtrlOffsetCond cond, List<String> sameCtrlChgIds) {
        List<GcOrgCacheVO> disposeOrgAndParentOrgList = this.listUnitPaths(cond);
        List<String> inputUnitCodes = disposeOrgAndParentOrgList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        List<String> srcTypeCodes = this.listSrcTypeCodes("endOffsetTab");
        return this.sameCtrlOffSetItemDao.sumOffsetsBySameSubjectCode(cond, sameCtrlChgIds, srcTypeCodes, inputUnitCodes);
    }

    @Override
    public List<ExportExcelSheet> exportSameCtrlOffsetDatas(SameCtrlOffsetCond sameCtrlOffsetCond, Map<String, CellStyle> cellStyleMap, ExportContext context) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        String pentrateType = sameCtrlOffsetCond.getPentrateType();
        String showTabType = sameCtrlOffsetCond.getShowTabType();
        if ("sameParentName".equals(pentrateType)) {
            SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlOffsetCond.getSameCtrlChgId()));
            String currMergeUnitCode = sameCtrlOffsetCond.getMergeUnitCode();
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u671f\u521d\u62b5\u9500");
            sameCtrlOffsetCond.setShowTabType("beginOffsetTab");
            sameCtrlOffsetCond.setMergeUnitCode(sameCtrlChgOrgEO.getChangedParentCode());
            List<GcOrgCacheVO> changedParentMergeOrgs = this.listUnitPaths(sameCtrlOffsetCond);
            sameCtrlOffsetCond.setMergeUnitCode(currMergeUnitCode);
            sameCtrlOffsetCond.setInputUnitCodes(changedParentMergeOrgs.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            this.exportSameCtrlOffsetDatasSheet(sameCtrlOffsetCond, cellStyleMap, context, exportExcelSheet);
            exportExcelSheets.add(exportExcelSheet);
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(1), "\u671f\u672b\u62b5\u9500");
            sameCtrlOffsetCond.setShowTabType("endOffsetTab");
            sameCtrlOffsetCond.setMergeUnitCode(sameCtrlChgOrgEO.getVirtualParentCode());
            List<GcOrgCacheVO> virtualParentMergeOrgs = this.listUnitPaths(sameCtrlOffsetCond);
            sameCtrlOffsetCond.setMergeUnitCode(currMergeUnitCode);
            sameCtrlOffsetCond.setInputUnitCodes(virtualParentMergeOrgs.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            this.exportSameCtrlOffsetDatasSheet(sameCtrlOffsetCond, cellStyleMap, context, exportExcelSheet);
            exportExcelSheets.add(exportExcelSheet);
        } else {
            String sheetName = "endOffsetTab".equals(showTabType) ? "\u671f\u672b\u62b5\u9500" : "\u671f\u521d\u62b5\u9500";
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), sheetName);
            this.exportSameCtrlOffsetDatasSheet(sameCtrlOffsetCond, cellStyleMap, context, exportExcelSheet);
            exportExcelSheets.add(exportExcelSheet);
        }
        return exportExcelSheets;
    }

    private void exportSameCtrlOffsetDatasSheet(SameCtrlOffsetCond sameCtrlOffsetCond, Map<String, CellStyle> cellStyleMap, ExportContext context, ExportExcelSheet exportExcelSheet) {
        String[] titles = new String[]{"\u5e8f\u53f7", "\u6765\u6e90\u65b9\u5f0f", "\u5408\u5e76\u89c4\u5219", "\u672c\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d", "\u79d1\u76ee", "\u501f\u65b9\u91d1\u989d", "\u8d37\u65b9\u91d1\u989d", "\u5dee\u989d", "\u63cf\u8ff0"};
        exportExcelSheet.getRowDatas().add(titles);
        String mRecid = null;
        int entryIndex = 1;
        HashSet<String> mrecids = new HashSet<String>();
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(sameCtrlOffsetCond.getSystemId());
        List sameCtrlOffSetItems = this.listOffsets(sameCtrlOffsetCond).getContent();
        for (int i = 0; i < sameCtrlOffSetItems.size(); ++i) {
            SameCtrlOffSetItemVO sameCtrlOffSetItem = (SameCtrlOffSetItemVO)sameCtrlOffSetItems.get(i);
            String tempMrecid = sameCtrlOffSetItem.getmRecid();
            mrecids.add(tempMrecid);
            if (null == mRecid || !mRecid.equals(tempMrecid)) {
                exportExcelSheet.getCellRangeAddresses().add(new CellRangeAddress(i + 1, i + sameCtrlOffSetItem.getRowspan(), 0, 0));
                exportExcelSheet.getCellRangeAddresses().add(new CellRangeAddress(i + 1, i + sameCtrlOffSetItem.getRowspan(), 1, 1));
                exportExcelSheet.getCellRangeAddresses().add(new CellRangeAddress(i + 1, i + sameCtrlOffSetItem.getRowspan(), 2, 2));
                mRecid = sameCtrlOffSetItem.getmRecid();
                entryIndex = sameCtrlOffSetItem.getIndex();
            }
            boolean showDictCode = "1".equals(optionVO.getShowDictCode());
            String unitTitle = showDictCode ? sameCtrlOffSetItem.getUnitCode() + "|" + sameCtrlOffSetItem.getUnitTitle() : sameCtrlOffSetItem.getUnitTitle();
            String oppUnitTitle = showDictCode ? sameCtrlOffSetItem.getOppUnitCode() + "|" + sameCtrlOffSetItem.getOppUnitTitle() : sameCtrlOffSetItem.getOppUnitTitle();
            String subjectTitle = showDictCode ? sameCtrlOffSetItem.getSubjectCode() + "|" + sameCtrlOffSetItem.getSubjectTitle() : sameCtrlOffSetItem.getSubjectTitle();
            exportExcelSheet.getRowDatas().add(new Object[]{String.valueOf(entryIndex), sameCtrlOffSetItem.getSourceMethodTtile(), sameCtrlOffSetItem.getRuleTitle(), unitTitle, oppUnitTitle, subjectTitle, sameCtrlOffSetItem.getOffSetDebit(), sameCtrlOffSetItem.getOffSetCredit(), sameCtrlOffSetItem.getDiffStr(), sameCtrlOffSetItem.getMemo()});
            if (mrecids.size() % 2 != 1) continue;
            rowNumber.add(i + 1);
        }
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        CellStyle[] headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headAmt, headAmt, headAmt, headString};
        CellStyle[] contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentAmt, contentString};
        CellType[] cellTypes = new CellType[]{CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC, CellType.NUMERIC, CellType.STRING};
        for (int i = 0; i < headStyles.length; ++i) {
            exportExcelSheet.getHeadCellStyleCache().put(i, headStyles[i]);
            exportExcelSheet.getContentCellStyleCache().put(i, contentStyles[i]);
            exportExcelSheet.getContentCellTypeCache().put(i, cellTypes[i]);
        }
        String intervalRowKey = exportExcelSheet.getSheetNo() == 1 ? "sameParentEndOffsetIntervalRows" : "offsetIntervalRows";
        context.getVarMap().put(intervalRowKey, rowNumber);
    }

    protected SameCtrlExtractLogVO getSameCtrlExtractLog(SameCtrlOffsetCond sameCtrlOffsetCond, SameCtrlExtractOperateEnum sameCtrlExtractOperateEnum) {
        SameCtrlExtractLogVO sameCtrlExtractLog = new SameCtrlExtractLogVO();
        BeanUtils.copyProperties(sameCtrlOffsetCond, sameCtrlExtractLog);
        sameCtrlExtractLog.setChangedCode(sameCtrlOffsetCond.getChangedUnitCode());
        sameCtrlExtractLog.setChangedParentCode(null);
        sameCtrlExtractLog.setVirtualParentCode(null);
        sameCtrlExtractLog.setLatestFlag(Integer.valueOf(1));
        sameCtrlExtractLog.setOperate(sameCtrlExtractOperateEnum);
        sameCtrlExtractLog.setEndTime(null);
        sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.EXECUTING);
        Date currentDate = new Date();
        ContextUser currentUser = NpContextHolder.getContext().getUser();
        String userName = currentUser == null ? "" : (org.springframework.util.StringUtils.isEmpty(currentUser.getFullname()) ? currentUser.getName() : currentUser.getFullname());
        sameCtrlExtractLog.setBeginTime(Long.valueOf(currentDate.getTime()));
        sameCtrlExtractLog.setUserName(userName);
        return sameCtrlExtractLog;
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

    protected SameCtrlExtractLogVO addSameCtrlExtractLogVO(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlExtractOperateEnum SameCtrlExtractType) {
        SameCtrlExtractLogVO sameCtrlExtractLog = this.getSameCtrlExtractLog(sameCtrlChgEnvContext.getSameCtrlOffsetCond(), SameCtrlExtractType);
        sameCtrlChgEnvContext.addResultItem("\u6267\u884c\u65f6\u95f4\uff1a" + DateUtils.format((Date)new Date(sameCtrlExtractLog.getBeginTime()), (String)"yyyy-MM-dd HH:mm:ss"));
        sameCtrlChgEnvContext.addResultItem("\u6267\u884c\u7528\u6237\uff1a" + sameCtrlExtractLog.getUserName());
        this.sameCtrlExtractLogService.insertSameCtrlExtractLog(sameCtrlExtractLog);
        return sameCtrlExtractLog;
    }

    protected GcOrgCenterService getGcOrgTool(SameCtrlOffsetCond cond) {
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        return GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
    }

    protected void updateSameCtrlExtractLog(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlExtractLogVO sameCtrlExtractLog) {
        sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.SUCCESS);
        if (!sameCtrlChgEnvContext.isSuccessFlag()) {
            sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.ERROR);
        }
        sameCtrlExtractLog.setEndTime(Long.valueOf(System.currentTimeMillis()));
        sameCtrlExtractLog.setInfo(String.join((CharSequence)";\n", (Iterable<? extends CharSequence>)sameCtrlChgEnvContext.getResult()));
        this.sameCtrlExtractLogService.updateSamrCtrlLogById(sameCtrlExtractLog);
    }

    protected SameCtrlChangeOrgDateTypeEnum checkSameCtrlOrgDateType(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        Date changeDate = sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlChgOrgEO().getChangeDate();
        String periodStr = sameCtrlChgEnvContext.getSameCtrlOffsetCond().getPeriodStr();
        int changeYear = DateUtils.getDateFieldValue((Date)changeDate, (int)1);
        int changeMonth = DateUtils.getDateFieldValue((Date)changeDate, (int)2);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform((String)sameCtrlChgEnvContext.getSameCtrlOffsetCond().getSchemeId(), (String)periodStr);
        int extractYear = yearPeriodUtil.getYear();
        int extractPeriod = yearPeriodUtil.getPeriod();
        switch (yearPeriodUtil.getType()) {
            case 2: {
                extractPeriod *= 6;
                break;
            }
            case 3: {
                extractPeriod *= 3;
                break;
            }
            case 1: {
                extractPeriod = 12;
                break;
            }
        }
        if (extractYear == changeYear) {
            if (extractPeriod == changeMonth) {
                return SameCtrlChangeOrgDateTypeEnum.CURR_YEAR_CURR_MONTH;
            }
            if (extractPeriod > changeMonth) {
                return SameCtrlChangeOrgDateTypeEnum.CURR_YEAR_AFTER_MONTH;
            }
        }
        if (extractYear > changeYear) {
            if (extractPeriod <= changeMonth) {
                return SameCtrlChangeOrgDateTypeEnum.AFTER_YEAR_CURR_MONTH;
            }
            if (extractPeriod > changeMonth) {
                return SameCtrlChangeOrgDateTypeEnum.AFTER_YEAR_AFTER_MONTH;
            }
        }
        return SameCtrlChangeOrgDateTypeEnum.OTHER;
    }

    public void deleteSameCtrlOffset(SameCtrlOffsetCond cond, SameCtrlSrcTypeEnum sameCtrlSrcTypeEnum) {
        int count = this.sameCtrlOffSetItemDao.deleteSameCtrlByCondition(cond, sameCtrlSrcTypeEnum != null ? Collections.singletonList(sameCtrlSrcTypeEnum.getCode()) : new ArrayList());
        String info = "\u5220\u9664\u540c\u63a7\u62b5\u9500\u6570\u636e\u3010%1s\u3011\u6761\u6570\u636e\uff0c\u53c2\u6570\u4fe1\u606f\uff1a\u4efb\u52a1\u3010%2s\u3011\uff0c\u62a5\u8868\u65b9\u6848\u3010%3s\u3011\uff0c\u65f6\u671f\u3010%4s\u3011\uff0c\u5e01\u79cd\u3010%5s\u3011\uff0c\u5355\u4f4d\u53d8\u52a8\u4fe1\u606fid\u3010%6s\u3011\uff0c\u5355\u4f4d\u7c7b\u578b\u3010%7s\u3011\uff0c\u62b5\u9500\u6765\u6e90\u7c7b\u578b\u3010%8s\u3011";
        logger.info(String.format(info, count, this.getTaskTitle(cond.getTaskId()), this.getSchemeDefineTitle(cond.getSchemeId()), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getSameCtrlChgId(), cond.getOrgType(), sameCtrlSrcTypeEnum != null ? SameCtrlSrcTypeEnum.getTitleByCode((String)sameCtrlSrcTypeEnum.getCode()) : SameCtrlExtractOperateEnum.GO_BACK.getTitle()));
    }

    public SameCtrlQueryParamsVO getQueryParams(SameCtrlExtractDataVO condition, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlQueryParamsVO queryParamsVO = new SameCtrlQueryParamsVO();
        BeanUtils.copyProperties(sameCtrlOffsetCond, queryParamsVO);
        queryParamsVO.setUnitIdList(sameCtrlOffsetCond.getChangedUnitCodeChilds());
        queryParamsVO.setOrgType(sameCtrlOffsetCond.getOrgType());
        queryParamsVO.setSchemeId(sameCtrlOffsetCond.getSchemeId());
        queryParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        sameCtrlOffsetCond.setSelectAdjustCode(condition.getSelectAdjustCode());
        return queryParamsVO;
    }

    protected String updateLogMessage(SameCtrlExtractLogVO sameCtrlExtractLog, SameCtrlChgEnvContext sameCtrlChgEnvContext, String log, String vueLog) {
        sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.ERROR);
        sameCtrlChgEnvContext.getResult().add(log);
        sameCtrlChgEnvContext.setSuccessFlag(false);
        vueLog = vueLog + log;
        return vueLog;
    }
}

