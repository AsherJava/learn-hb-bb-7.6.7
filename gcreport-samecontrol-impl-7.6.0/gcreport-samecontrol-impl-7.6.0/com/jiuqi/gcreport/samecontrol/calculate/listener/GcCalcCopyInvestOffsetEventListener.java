/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.samecontrol.calculate.listener;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class GcCalcCopyInvestOffsetEventListener
implements ApplicationListener<GcCalcExecuteSameOffsetItemEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private SameCtrlChgSettingService sameCtrlChgSettingService;

    @Override
    public void onApplicationEvent(GcCalcExecuteSameOffsetItemEvent event) {
        try {
            QueryParamsDTO queryParamsDTO = event.getQueryParamsDTO();
            String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(queryParamsDTO.getSchemeId(), queryParamsDTO.getPeriodStr());
            if (StringUtils.isEmpty((String)systemId)) {
                this.logger.error("\u5408\u5e76\u8ba1\u7b97\uff1a\u76f4\u63a5\u6295\u8d44\u62b5\u9500\u5206\u5f55\u590d\u5236-\u62a5\u8868\u65b9\u6848\u3010" + queryParamsDTO.getSchemeId() + "\u3011\u65f6\u671f\u3010" + queryParamsDTO.getPeriodStr() + "\u3011\u672a\u67e5\u8be2\u5230\u4f53\u7cfb");
                return;
            }
            queryParamsDTO.setSystemId(systemId);
            SameCtrlChagSettingOptionVO optionData = this.sameCtrlChgSettingService.getOptionData(queryParamsDTO.getTaskId(), queryParamsDTO.getSchemeId(), systemId);
            if (optionData == null) {
                this.logger.error("\u5408\u5e76\u8ba1\u7b97\uff1a\u76f4\u63a5\u6295\u8d44\u62b5\u9500\u5206\u5f55\u590d\u5236-\u4efb\u52a1\u3010" + queryParamsDTO.getTaskId() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + queryParamsDTO.getSchemeId() + "\u3011\u65f6\u671f\u3010" + queryParamsDTO.getPeriodStr() + "\u3011\u4f53\u7cfb\u3010" + systemId + "\u3011\u672a\u67e5\u8be2\u5230\u4f53\u7cfb");
                return;
            }
            if (optionData.getEnableInvestDisposeCopy() == null || !optionData.getEnableInvestDisposeCopy().booleanValue()) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\uff1a\u76f4\u63a5\u6295\u8d44\u62b5\u9500\u5206\u5f55\u590d\u5236-\u4efb\u52a1\u3010" + queryParamsDTO.getTaskId() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + queryParamsDTO.getSchemeId() + "\u3011\u65f6\u671f\u3010" + queryParamsDTO.getPeriodStr() + "\u3011\u4f53\u7cfb\u3010" + systemId + "\u3011\u672a\u8bbe\u7f6e\u590d\u5236\u9009\u9879");
                return;
            }
            YearPeriodObject yp = new YearPeriodObject(queryParamsDTO.getSchemeId(), queryParamsDTO.getPeriodStr());
            GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)queryParamsDTO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO gcOrgCacheVO = gcOrgCenterService.getOrgByCode(queryParamsDTO.getOrgId());
            if (gcOrgCacheVO == null || StringUtils.isEmpty((String)gcOrgCacheVO.getBaseUnitId())) {
                this.logger.error("\u5408\u5e76\u8ba1\u7b97\uff1a\u76f4\u63a5\u6295\u8d44\u62b5\u9500\u5206\u5f55\u590d\u5236-\u4efb\u52a1\u3010" + queryParamsDTO.getTaskId() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + queryParamsDTO.getSchemeId() + "\u3011\u65f6\u671f\u3010" + queryParamsDTO.getPeriodStr() + "\u3011\u4f53\u7cfb\u3010" + systemId + "\u3011\u5355\u4f4d\u3010" + queryParamsDTO.getOrgId() + "\u3011\u672a\u67e5\u8be2\u5230\u672c\u90e8\u5355\u4f4d");
                return;
            }
            PeriodWrapper ps = PeriodUtil.getPeriodWrapper((String)queryParamsDTO.getPeriodStr());
            List<DefaultTableEntity> indirectInvestEntityList = this.getMastByUnitCode(ps.getYear(), ps.getPeriod(), gcOrgCacheVO.getBaseUnitId());
            Map<String, String> changeCode2VirtualCodeMap = this.getSameCtrlOrgMap(queryParamsDTO);
            List<DefaultTableEntity> filterMastInvestIsChangeCodeList = this.filterInvestBySameCtrlChangeCode(changeCode2VirtualCodeMap, indirectInvestEntityList);
            List<DefaultTableEntity> fvchFixedAndFvchOtherList = this.listFvchFixedAndFvchOtherByInvestMasterSrcId(filterMastInvestIsChangeCodeList);
            List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList = this.getOffsetItemByInvestSrcIdList(filterMastInvestIsChangeCodeList, fvchFixedAndFvchOtherList, queryParamsDTO);
            this.saveOffsetItem(gcOffSetVchrItemAdjustEOList, changeCode2VirtualCodeMap, queryParamsDTO);
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u590d\u5236\u5df2\u5904\u7f6e\u4e0a\u671f\u62b5\u9500\u5206\u5f55\u6570\u636e\u5f02\u5e38\uff1a", e);
        }
    }

    private List<DefaultTableEntity> listFvchFixedAndFvchOtherByInvestMasterSrcId(List<DefaultTableEntity> indirectInvestEntityList) {
        ArrayList<DefaultTableEntity> fvchFixedAndFvchOtherList = new ArrayList<DefaultTableEntity>();
        indirectInvestEntityList.forEach(masterInvest -> {
            if (masterInvest == null || masterInvest.getFieldValue("SRCID") == null) {
                return;
            }
            fvchFixedAndFvchOtherList.addAll(SameCtrlManageUtil.getFvchFixedByInvestMasterSrcId(String.valueOf(masterInvest.getFieldValue("SRCID"))));
            fvchFixedAndFvchOtherList.addAll(SameCtrlManageUtil.getFvchOtherByInvestMasterSrcId(String.valueOf(masterInvest.getFieldValue("SRCID"))));
        });
        return fvchFixedAndFvchOtherList;
    }

    private void saveOffsetItem(List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList, Map<String, String> changeCode2VirtualCodeMap, QueryParamsDTO queryParamsDTO) {
        PeriodWrapper ps = PeriodUtil.getPeriodWrapper((String)queryParamsDTO.getPeriodStr());
        List<String> srcIdList = gcOffSetVchrItemAdjustEOList.stream().map(GcOffSetVchrItemAdjustEO::getSrcId).collect(Collectors.toList());
        List listOffsetItemBySrcId = this.listOffsetItemBySrcId(srcIdList).stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2EoList = gcOffSetVchrItemAdjustEOList.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        mRecid2EoList.forEach((mRecid, eoList) -> {
            String mRecidNew = UUIDOrderUtils.newUUIDStr();
            Date time = Calendar.getInstance().getTime();
            GcOffSetVchrDTO group = new GcOffSetVchrDTO();
            ArrayList gcOffSetVchrItemDTOList = new ArrayList();
            eoList.forEach(eo -> {
                if (Objects.equals(eo.getDisableFlag(), 1)) {
                    return;
                }
                eo.setmRecid(mRecidNew);
                if (!listOffsetItemBySrcId.contains(eo.getSrcId())) {
                    eo.setSrcId(eo.getId());
                    String addMemo = ps.getPeriod() + "\u671f\u590d\u5236";
                    String memo = StringUtils.isEmpty((String)eo.getMemo()) ? addMemo : eo.getMemo() + "-" + addMemo;
                    eo.setMemo(memo);
                }
                eo.setId(UUIDOrderUtils.newUUIDStr());
                if (changeCode2VirtualCodeMap.containsKey(eo.getUnitId())) {
                    eo.setUnitId((String)changeCode2VirtualCodeMap.get(eo.getUnitId()));
                }
                if (changeCode2VirtualCodeMap.containsKey(eo.getOppUnitId())) {
                    eo.setOppUnitId((String)changeCode2VirtualCodeMap.get(eo.getOppUnitId()));
                }
                eo.setCreateTime(time);
                eo.setModifyTime(time);
                eo.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
                eo.setOffSetSrcType(Integer.valueOf(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue()));
                eo.setDefaultPeriod(queryParamsDTO.getPeriodStr());
                eo.setTaskId(queryParamsDTO.getTaskId());
                GcOffSetVchrItemDTO gcOffSetVchrItemDTO = OffsetCoreConvertUtil.convertEO2DTO((GcOffSetVchrItemAdjustEO)eo);
                gcOffSetVchrItemDTO.setAcctYear(Integer.valueOf(ps.getYear()));
                gcOffSetVchrItemDTO.setAcctPeriod(Integer.valueOf(ps.getPeriod()));
                gcOffSetVchrItemDTOList.add(gcOffSetVchrItemDTO);
            });
            group.setMrecid(mRecidNew);
            group.setItems(gcOffSetVchrItemDTOList);
            group.setConsFormulaCalcType("autoFlag");
            this.adjustingEntryService.save(group);
        });
    }

    private List<GcOffSetVchrItemAdjustEO> getOffsetItemByInvestSrcIdList(List<DefaultTableEntity> filterMastInvestIsChangeCodeList, List<DefaultTableEntity> fvchFixedAndFvchOtherList, QueryParamsDTO queryParamsDTO) {
        QueryParamsDTO queryOffsetParamsDTO = new QueryParamsDTO();
        List srcIdList = filterMastInvestIsChangeCodeList.stream().map(invest -> String.valueOf(invest.getFieldValue("SRCID"))).collect(Collectors.toList());
        srcIdList.addAll(fvchFixedAndFvchOtherList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(srcIdList)) {
            return new ArrayList<GcOffSetVchrItemAdjustEO>();
        }
        queryOffsetParamsDTO.setCurrency(queryParamsDTO.getCurrency());
        queryOffsetParamsDTO.setTaskId(queryParamsDTO.getTaskId());
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = PeriodUtil.getPeriodWrapper((String)queryParamsDTO.getPeriodStr());
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        queryOffsetParamsDTO.setPeriodStr(periodWrapper.toString());
        queryOffsetParamsDTO.setSrcOffsetGroupIds(srcIdList.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        ArrayList<String> investRuleList = new ArrayList<String>();
        investRuleList.add(RuleTypeEnum.DIRECT_INVESTMENT.getCode());
        investRuleList.add(RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        queryOffsetParamsDTO.setRules(SameCtrlManageUtil.getRuleIdListByType(queryParamsDTO.getSchemeId(), queryParamsDTO.getPeriodStr(), investRuleList));
        queryOffsetParamsDTO.setSystemId(queryParamsDTO.getSystemId());
        queryOffsetParamsDTO.setQueryAllColumns(true);
        queryOffsetParamsDTO.setOrgId(queryParamsDTO.getOrgId());
        this.deleteOffsetItemByBaseArgument(queryOffsetParamsDTO, queryParamsDTO.getPeriodStr());
        return this.listOffsetItemByBaseArgument(queryOffsetParamsDTO);
    }

    private void deleteOffsetItemByBaseArgument(QueryParamsDTO queryOffsetParamsDTO, String periodStr) {
        String sql = "delete from GC_OFFSETVCHRITEM record \n  where " + SqlUtils.getConditionOfIdsUseOr((Collection)queryOffsetParamsDTO.getSrcOffsetGroupIds(), (String)"record.srcOffsetGroupId") + "  and  \n" + SqlUtils.getConditionOfIdsUseOr((Collection)queryOffsetParamsDTO.getRules(), (String)"record.ruleid") + this.whereSql(queryOffsetParamsDTO, periodStr);
        GcOffSetItemAdjustCoreDao coreDao = (GcOffSetItemAdjustCoreDao)SpringContextUtils.getBean(GcOffSetItemAdjustCoreDao.class);
        coreDao.execute(sql);
    }

    private List<GcOffSetVchrItemAdjustEO> listOffsetItemBySrcId(List<String> srcIdList) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)"record") + " from " + "GC_OFFSETVCHRITEM" + " record \n  where " + SqlUtils.getConditionOfIdsUseOr(srcIdList, (String)"record.id");
        GcOffSetItemAdjustCoreDao coreDao = (GcOffSetItemAdjustCoreDao)SpringContextUtils.getBean(GcOffSetItemAdjustCoreDao.class);
        return coreDao.selectEntity(sql, new Object[0]);
    }

    public List<GcOffSetVchrItemAdjustEO> listOffsetItemByBaseArgument(QueryParamsDTO queryOffsetParamsDTO) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)"record") + " from " + "GC_OFFSETVCHRITEM" + " record \n  where " + SqlUtils.getConditionOfIdsUseOr((Collection)queryOffsetParamsDTO.getSrcOffsetGroupIds(), (String)"record.srcOffsetGroupId") + "  and  \n" + SqlUtils.getConditionOfIdsUseOr((Collection)queryOffsetParamsDTO.getRules(), (String)"record.ruleid") + this.whereSql(queryOffsetParamsDTO, queryOffsetParamsDTO.getPeriodStr());
        GcOffSetItemAdjustCoreDao coreDao = (GcOffSetItemAdjustCoreDao)SpringContextUtils.getBean(GcOffSetItemAdjustCoreDao.class);
        return coreDao.selectEntity(sql, new Object[0]);
    }

    private StringBuffer whereSql(QueryParamsDTO queryOffsetParamsDTO, String periodStr) {
        StringBuffer whereSql = new StringBuffer(64);
        if (queryOffsetParamsDTO == null) {
            return whereSql;
        }
        if (!StringUtils.isEmpty((String)queryOffsetParamsDTO.getPeriodStr())) {
            whereSql.append(" and record.DATATIME = '").append(periodStr).append("'\n");
        }
        if (!StringUtils.isEmpty((String)queryOffsetParamsDTO.getCurrency())) {
            whereSql.append(" and record.offSetCurr = '").append(queryOffsetParamsDTO.getCurrency()).append("'\n");
        }
        if (!StringUtils.isEmpty((String)queryOffsetParamsDTO.getSystemId())) {
            whereSql.append(" and record.systemId = '").append(queryOffsetParamsDTO.getSystemId()).append("'\n");
        }
        return whereSql;
    }

    private ChangeOrgCondition initChangeOrgCondition(QueryParamsDTO queryParamsDTO) {
        ChangeOrgCondition condition = new ChangeOrgCondition();
        condition.setSchemeId(queryParamsDTO.getSchemeId());
        condition.setPeriodStr(queryParamsDTO.getPeriodStr());
        condition.setOrgType(queryParamsDTO.getOrgType());
        condition.setOrgCode(queryParamsDTO.getOrgId());
        return condition;
    }

    private Map<String, String> getSameCtrlOrgMap(QueryParamsDTO queryParamsDTO) {
        ChangeOrgCondition condition = this.initChangeOrgCondition(queryParamsDTO);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)condition.getPeriodStr());
        condition.setPeriodDate(yearPeriodUtil.getEndDate());
        List<SameCtrlChgOrgEO> eos = this.sameCtrlChgOrgDao.listSameCtrlChgOrgByParents(condition);
        Map<String, List<SameCtrlChgOrgEO>> mRecid2SameCtrlEO = eos.stream().collect(Collectors.groupingBy(SameCtrlChgOrgEO::getmRecid));
        HashMap<String, String> changeCode2VirtualCode = new HashMap<String, String>();
        mRecid2SameCtrlEO.forEach((mRecid, sameCtrlChgOrgEO) -> sameCtrlChgOrgEO.forEach(eo -> {
            if (SameCtrlExtractTypeEnum.VIRTUAL.getCode().equals(eo.getVirtualCodeType())) {
                changeCode2VirtualCode.put(eo.getChangedCode(), eo.getVirtualCode());
            }
        }));
        return changeCode2VirtualCode;
    }

    private List<DefaultTableEntity> filterInvestBySameCtrlChangeCode(Map<String, String> changeCode2VirtualCodeMap, List<DefaultTableEntity> mastInvestList) {
        Set<String> changeCodeSet = changeCode2VirtualCodeMap.keySet();
        return mastInvestList.stream().filter(mast -> changeCodeSet.contains(String.valueOf(mast.getFieldValue("INVESTEDUNIT")))).collect(Collectors.toList());
    }

    private List<DefaultTableEntity> getMastByUnitCode(int acctYear, int period, String unitCode) {
        String sql = " select %1$s from GC_INVESTBILL e  where e.acctYear=? and e.period = ? and e.MERGETYPE = 'DIRECT' and e.DISPOSEFLAG = 1 and e.unitcode = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e");
        String formatSql = String.format(sql, columns);
        return this.queryBySql(formatSql, acctYear, period, unitCode);
    }

    private List<DefaultTableEntity> queryBySql(String sql, Object ... params) {
        ArrayList<DefaultTableEntity> result = new ArrayList<DefaultTableEntity>();
        List fields = EntNativeSqlDefaultDao.getInstance().selectMap(sql, params);
        for (Map field : fields) {
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.setId(String.valueOf(field.get("ID")));
            entity.resetFields(field);
            result.add(entity);
        }
        return result;
    }
}

