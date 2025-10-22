/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.LeaseFetchTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.lease.rule.calculate;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.lease.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.LeaseFetchTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcMasterBillRuleExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcMasterBillRuleExecutor.class);
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcCalcService gcCalcService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;

    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void execute(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        this.doExecute(env, rule);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private void doExecute(GcCalcEnvContext env, @NotNull AbstractUnionRule rule) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        this.deleteHistoryAdjustOffSetItems(env, rule);
        LeaseRuleDTO billRule = (LeaseRuleDTO)rule;
        BillParam billParam = this.getBillParam(billRule, env);
        List<GcBillGroupDTO> billGroupList = this.queryBillData(env, billParam.billInfoVo);
        List<GcBillGroupDTO> acceptDataList = this.filterBillGroupS(billParam, billGroupList);
        if (CollectionUtils.isEmpty(acceptDataList)) {
            return;
        }
        List<GcOffSetVchrDTO> allDTOs = Collections.synchronizedList(new ArrayList());
        try {
            for (GcBillGroupDTO acceptData : acceptDataList) {
                GcOffSetVchrDTO itemDTO = this.executeSingleAcceptData(billParam, acceptData);
                if (itemDTO != null) {
                    allDTOs.add(itemDTO);
                }
                if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
                env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(itemDTO.getItems());
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.bill.rule.error", (Object[])new Object[]{rule.getLocalizedName()}) + e.getMessage());
        }
        if (!CollectionUtils.isEmpty(allDTOs)) {
            allDTOs.forEach(offsetDTO -> offsetDTO.setConsFormulaCalcType("autoFlag"));
            this.offSetItemAdjustService.batchSave(allDTOs);
            allDTOs.stream().forEach(offsetDTO -> {
                List items = offsetDTO.getItems();
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty((Collection)items) ? 0 : items.size()));
                if (CollectionUtils.isEmpty((Collection)items)) {
                    return;
                }
            });
        }
    }

    private BillParam getBillParam(LeaseRuleDTO rule, GcCalcEnvContext env) {
        BillParam billParam = new BillParam();
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo((String)rule.getBillDefineId());
        billParam.billInfoVo = billInfoVo;
        Assert.isNotNull((Object)billInfoVo, (String)"\u89e3\u6790\u5355\u636e\u5931\u8d25", (Object[])new Object[0]);
        billParam.env = env;
        billParam.rule = rule;
        return billParam;
    }

    private List<GcBillGroupDTO> queryBillData(GcCalcEnvContext env, BillInfoVo billInfoVo) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        if (calcArgments.getPreCalcFlag().get()) {
            String billCode = (String)calcArgments.getExtendInfo().get("BILLCODE");
            DefaultTableEntity masterBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)billInfoVo.getMasterTableName());
            if (masterBillEntity == null) {
                return Collections.emptyList();
            }
            Map<String, List<DefaultTableEntity>> subItems = this.listSubDatas(env, billInfoVo.getFirstSubTableName(), Arrays.asList(masterBillEntity.getId()));
            return Arrays.asList(new GcBillGroupDTO(masterBillEntity, subItems.get(masterBillEntity.getId())));
        }
        String masterTableName = billInfoVo.getMasterTableName();
        String subTableName = billInfoVo.getFirstSubTableName();
        GcOrgCacheVO hbOrg = this.getHbOrg(env);
        List<DefaultTableEntity> masterDataList = this.listMasterDatas(env, hbOrg, masterTableName);
        List<String> masterIdList = masterDataList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Map<String, List<DefaultTableEntity>> masterId2SubDataListMap = this.listSubDatas(env, subTableName, masterIdList);
        List<GcBillGroupDTO> billGroupDTOList = masterDataList.stream().map(master -> new GcBillGroupDTO(master, (List)masterId2SubDataListMap.get(master.getId()))).collect(Collectors.toList());
        return billGroupDTOList;
    }

    private Map<String, List<DefaultTableEntity>> listSubDatas(GcCalcEnvContext env, String subTableName, List<String> masterIdList) {
        if (null == subTableName || CollectionUtils.isEmpty(masterIdList)) {
            return Collections.emptyMap();
        }
        String sql = " select %1$s \n from %2$s e \n where %3$s \n ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)subTableName, (String)"e");
        Set fieldDefineNameSet = NrTool.getAllFieldDefineNamesByTableName((String)subTableName);
        String inSql = SqlUtils.getConditionOfIdsUseOr(masterIdList, (String)"MASTERID");
        if (fieldDefineNameSet.contains("CHANGEMONTH")) {
            String currPeriod = env.getCalcArgments().getAcctPeriod() < 10 ? env.getCalcArgments().getAcctYear() + "-0" + env.getCalcArgments().getAcctPeriod() : env.getCalcArgments().getAcctYear() + "-" + env.getCalcArgments().getAcctPeriod();
            inSql = inSql + "and CHANGEMONTH='" + currPeriod + "'\n";
        }
        String formatSQL = String.format(sql, columns, subTableName, inSql);
        ArrayList paramList = new ArrayList();
        List subDataList = InvestBillTool.queryBySql((String)formatSQL, paramList);
        Map<String, List<DefaultTableEntity>> masterId2SubDataListMap = subDataList.stream().collect(Collectors.groupingBy(o -> StringUtils.toViewString((Object)o.getFieldValue("MASTERID"))));
        return masterId2SubDataListMap;
    }

    private GcOrgCacheVO getHbOrg(GcCalcEnvContext env) {
        String hbOrgId = env.getCalcArgments().getOrgId();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgTool.getOrgByCode(hbOrgId);
        return hbOrg;
    }

    private List<DefaultTableEntity> listMasterDatas(GcCalcEnvContext env, GcOrgCacheVO hbOrg, String tableName) {
        String sql = " select %1$s \n from %2$s e \n join %3$s unit on (e.unitCode = unit.code and unit.validTime <=? and unit.invalidTime >= ?)\n join %3$s oppUnit on (e.oppUnitCode = oppUnit.code and oppUnit.validTime <=? and oppUnit.invalidTime >= ?)\n where substr(unit.gcparents, 1, %4$d) <> substr(oppUnit.gcparents, 1, %4$d) \n  and unit.parents like ? and oppUnit.parents like ? \n ";
        String hbUnitParents = hbOrg.getParentStr() + "%";
        int len = hbOrg.getGcParentStr().length() + GcOrgPublicTool.getInstance().getOrgCodeLength() + 1;
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"e");
        String formatSQL = String.format(sql, columns, tableName, env.getCalcArgments().getOrgType(), len);
        ArrayList<Object> paramList = new ArrayList<Object>();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        Date date = yp.formatYP().getEndDate();
        paramList.addAll(Arrays.asList(date, date, date, date));
        paramList.addAll(Arrays.asList(hbUnitParents, hbUnitParents));
        return InvestBillTool.queryBySql((String)formatSQL, paramList);
    }

    private GcOffSetVchrDTO executeSingleAcceptData(BillParam billParam, GcBillGroupDTO billGroupDTO) {
        boolean isCreateGcOffSetVchrDTO;
        GcOffSetVchrItemDTO phsRecord;
        GcOffSetVchrItemDTO offSetVchrItem;
        GcCalcEnvContext env = billParam.env;
        LeaseRuleDTO rule = billParam.rule;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = this.convert2Dim(calcArgments);
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<LeaseRuleDTO.Item> debitPhsFormulaList = new ArrayList<LeaseRuleDTO.Item>();
        ArrayList<LeaseRuleDTO.Item> creditPhsFormulaList = new ArrayList<LeaseRuleDTO.Item>();
        for (LeaseRuleDTO.Item ruleItem : rule.getDebitItemList()) {
            if (StringUtils.isEmpty((String)ruleItem.getFetchFormula())) continue;
            if (ruleItem.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(ruleItem);
                continue;
            }
            offSetVchrItem = this.parseFormula(billParam, billGroupDTO, dset, ruleItem, OrientEnum.D);
            if (offSetVchrItem == null) continue;
            debitSum = NumberUtils.sum((Double)debitSum, (Double)offSetVchrItem.getOffSetDebit());
            records.add(offSetVchrItem);
        }
        for (LeaseRuleDTO.Item ruleItem : rule.getCreditItemList()) {
            if (StringUtils.isEmpty((String)ruleItem.getFetchFormula())) continue;
            if (ruleItem.getFetchFormula().contains("PHS")) {
                creditPhsFormulaList.add(ruleItem);
                continue;
            }
            offSetVchrItem = this.parseFormula(billParam, billGroupDTO, dset, ruleItem, OrientEnum.C);
            if (offSetVchrItem == null) continue;
            creditSum = NumberUtils.sum((Double)creditSum, (Double)offSetVchrItem.getOffSetCredit());
            records.add(offSetVchrItem);
        }
        double diffValue = NumberUtils.sub((Double)debitSum, (Double)creditSum);
        if (!NumberUtils.isZreo((Double)diffValue) && null != (phsRecord = this.phsRecord(billParam, billGroupDTO, dset, debitPhsFormulaList, creditPhsFormulaList, diffValue))) {
            records.add(phsRecord);
        }
        if ((isCreateGcOffSetVchrDTO = GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)rule, records)) || env.getCalcArgments().getPreCalcFlag().get()) {
            GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO();
            offSetVchrDTO.setItems(records);
            return offSetVchrDTO;
        }
        this.logger.info("\u5408\u5e76\u8ba1\u7b97\u5355\u636e\u89c4\u5219[{}]\uff0c\u4e3b\u8868\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u5bb9\u5dee\u7ea6\u675f\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", (Object)rule.getLocalizedName(), (Object)billGroupDTO.getMaster().getId());
        return null;
    }

    private void appendDataInfo(GcOffSetVchrItemDTO offSetItem, GcBillGroupDTO billGroupDTO, String subjectCode, OrientEnum orientEnum, double offsetAmt) {
        String unitId = StringUtils.toViewString((Object)billGroupDTO.getMaster().getFieldValue("UNITCODE"));
        String oppUnitId = StringUtils.toViewString((Object)billGroupDTO.getMaster().getFieldValue("OPPUNITCODE"));
        offSetItem.setUnitId(unitId);
        offSetItem.setOppUnitId(oppUnitId);
        offSetItem.setSrcOffsetGroupId(billGroupDTO.getMaster().getId());
        offSetItem.setSubjectCode(subjectCode);
        if (orientEnum.equals((Object)OrientEnum.D)) {
            offSetItem.setOffSetDebit(Double.valueOf(offsetAmt));
            offSetItem.setDebit(Double.valueOf(offsetAmt));
        } else {
            offSetItem.setOffSetCredit(Double.valueOf(offsetAmt));
            offSetItem.setCredit(Double.valueOf(offsetAmt));
        }
    }

    private DimensionValueSet convert2Dim(GcCalcArgmentsDTO calcArgments) {
        DimensionValueSet dset = DimensionUtils.generateDimSet(null, (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        return dset;
    }

    private GcOffSetVchrItemDTO phsRecord(BillParam billParam, GcBillGroupDTO billGroupDTO, DimensionValueSet dset, List<LeaseRuleDTO.Item> debitPhsFormulaList, List<LeaseRuleDTO.Item> creditPhsFormulaList, double diffValue) {
        GcOffSetVchrItemDTO offSetVchrItem;
        GcCalcEnvContext env = billParam.env;
        env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(-diffValue));
        for (LeaseRuleDTO.Item ruleItem : debitPhsFormulaList) {
            offSetVchrItem = this.parseFormula(billParam, billGroupDTO, dset, ruleItem, OrientEnum.D);
            if (offSetVchrItem == null || NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) continue;
            offSetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.PHS);
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(diffValue));
        for (LeaseRuleDTO.Item ruleItem : creditPhsFormulaList) {
            offSetVchrItem = this.parseFormula(billParam, billGroupDTO, dset, ruleItem, OrientEnum.C);
            if (offSetVchrItem == null || NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) continue;
            offSetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.PHS);
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        return null;
    }

    private GcOffSetVchrItemDTO parseFormula(BillParam billParam, GcBillGroupDTO billGroupDTO, DimensionValueSet dset, LeaseRuleDTO.Item ruleItem, OrientEnum c) {
        double offsetAmt = this.evaluateFormula(billGroupDTO, ruleItem.getFetchFormula(), dset, billParam);
        if (NumberUtils.isZreo((Double)offsetAmt) && !billParam.env.getCalcArgments().getPreCalcFlag().get()) {
            return null;
        }
        String subjectCode = this.getSubjectCode(billParam, ruleItem, dset, billGroupDTO);
        if (subjectCode == null) {
            String unitId = StringUtils.toViewString((Object)billGroupDTO.getMaster().getFieldValue("UNITCODE"));
            String oppUnitId = StringUtils.toViewString((Object)billGroupDTO.getMaster().getFieldValue("OPPUNITCODE"));
            String orgType = dset.getValue("MD_GCORGTYPE").toString();
            YearPeriodObject yp = new YearPeriodObject(null, dset.getValue("DATATIME").toString());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitId);
            GcOrgCacheVO oppOrgCacheVO = tool.getOrgByCode(unitId);
            StringBuffer errorMsg = new StringBuffer();
            errorMsg.append("\u83b7\u53d6\u4e0d\u5230\u51fa\u79df\u65b9\u5355\u4f4d[").append(unitId).append("]").append(orgCacheVO.getTitle()).append(",\u627f\u79df\u65b9\u5355\u4f4d[").append(oppUnitId).append("]").append(oppOrgCacheVO.getTitle()).append("\u7684").append(c.getTitle()).append("\u65b9\u79d1\u76ee\uff0c\u8bf7\u68c0\u67e5\u53f0\u8d26\u6570\u636e\u662f\u5426\u6b63\u786e\u3002");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
        Assert.isNotNull((Object)subjectCode, (String)"\u901a\u8fc7\u89c4\u5219\u89e3\u6790\u79d1\u76ee\u4ee3\u7801\u5931\u8d25", (Object[])new Object[0]);
        GcOffSetVchrItemDTO offSetVchrItem = this.initOffsetBaseInfo(billParam);
        this.appendDataInfo(offSetVchrItem, billGroupDTO, subjectCode, c, offsetAmt);
        this.appendDimensions(dset, offSetVchrItem, billGroupDTO, ruleItem, billParam.env, billParam.getAllTableNames());
        return offSetVchrItem;
    }

    private List<GcBillGroupDTO> filterBillGroupS(BillParam billParam, List<GcBillGroupDTO> billGroupDTOS) {
        GcCalcEnvContext env = billParam.env;
        LeaseRuleDTO rule = billParam.rule;
        if (StringUtils.isEmpty((String)rule.getRuleCondition())) {
            return billGroupDTOS;
        }
        DimensionValueSet dset = this.convert2Dim(env.getCalcArgments());
        List<GcBillGroupDTO> acceptDatas = billGroupDTOS.stream().filter(billGroupDTO -> {
            boolean isAccept = false;
            try {
                AbstractData data = this.billFormulaEvalService.evaluateBillAbstractData(env, dset, rule.getRuleCondition(), (GcBillGroupDTO)billGroupDTO, billParam.getAllTableNames());
                isAccept = GcAbstractData.getBooleanValue((AbstractData)data);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.bill.rule.condition.error", (Object[])new Object[]{rule.getLocalizedName(), rule.getRuleCondition()}) + e.getMessage());
            }
            if (!isAccept) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u5355\u636e\u89c4\u5219[{}]\uff0c\u4e3b\u8868\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55", rule.getLocalizedName(), billGroupDTO.getMaster().getId(), rule.getRuleCondition());
            }
            return isAccept;
        }).collect(Collectors.toList());
        return acceptDatas;
    }

    private void deleteHistoryAdjustOffSetItems(GcCalcEnvContext env, AbstractUnionRule rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), calcArgments);
    }

    private GcOffSetVchrItemDTO initOffsetBaseInfo(BillParam billParam) {
        GcCalcArgmentsDTO calcArgments = billParam.env.getCalcArgments();
        LeaseRuleDTO rule = billParam.rule;
        GcOffSetVchrItemDTO offset = new GcOffSetVchrItemDTO();
        offset.setDefaultPeriod(calcArgments.getPeriodStr());
        offset.setAcctPeriod(calcArgments.getAcctPeriod());
        offset.setAcctYear(calcArgments.getAcctYear());
        offset.setTaskId(calcArgments.getTaskId());
        offset.setSchemeId(calcArgments.getSchemeId());
        offset.setId(UUIDOrderUtils.newUUIDStr());
        offset.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        offset.setCreateTime(Calendar.getInstance().getTime());
        offset.setGcBusinessTypeCode(rule.getBusinessTypeCode());
        offset.setRuleId(rule.getId());
        offset.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        offset.setOffSetCurr(calcArgments.getCurrency());
        offset.setDebit(Double.valueOf(0.0));
        offset.setCredit(Double.valueOf(0.0));
        offset.setOffSetDebit(Double.valueOf(0.0));
        offset.setOffSetCredit(Double.valueOf(0.0));
        offset.setDiffd(Double.valueOf(0.0));
        offset.setDiffc(Double.valueOf(0.0));
        offset.setOrgType(GCOrgTypeEnum.NONE.getCode());
        offset.setSelectAdjustCode(calcArgments.getSelectAdjustCode());
        return offset;
    }

    private double evaluateFormula(GcBillGroupDTO billGroupDTO, String fetchFormula, DimensionValueSet dim, BillParam billParam) {
        if (StringUtils.isEmpty((String)fetchFormula)) {
            return 0.0;
        }
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(fetchFormula), (String)"\u4e0d\u518d\u652f\u6301\u6b64\u5199\u6cd5\uff0cPHS\u9700\u8981\u6539\u6210PHS()", (Object[])new Object[0]);
        return this.billFormulaEvalService.evaluateBillData(billParam.env, dim, fetchFormula, billGroupDTO, billParam.getAllTableNames());
    }

    private void appendDimensions(DimensionValueSet dset, GcOffSetVchrItemDTO offSetItem, GcBillGroupDTO billGroupDTO, LeaseRuleDTO.Item item, GcCalcEnvContext env, List<String> tableNames) {
        Map dimensions = item.getDimensions();
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            if (dimKey.contains("customizeFormula")) continue;
            String dimValueSource = (String)dimensions.get(dimKey);
            if ("UNITCODE".equals(dimValueSource)) {
                offSetItem.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, offSetItem.getUnitId(), dimKey));
                continue;
            }
            if ("OPPUNITCODE".equals(dimValueSource)) {
                offSetItem.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, offSetItem.getOppUnitId(), dimKey));
                continue;
            }
            if ("ACCOUNT".equals(dimValueSource)) {
                Object fieldValue = null;
                if (!CollectionUtils.isEmpty((Collection)billGroupDTO.getItems())) {
                    fieldValue = ((DefaultTableEntity)billGroupDTO.getItems().get(0)).getFieldValue(dimKey);
                }
                if (fieldValue != null) {
                    offSetItem.addFieldValue(dimKey, fieldValue);
                    continue;
                }
                offSetItem.addFieldValue(dimKey, billGroupDTO.getMaster().getFieldValue(dimKey));
                continue;
            }
            if (!"customizeFormula".equals(dimValueSource)) continue;
            if (StringUtils.isEmpty((String)((String)dimensions.get(dimKey + "_customizeFormula")))) {
                offSetItem.addFieldValue(dimKey, null);
                continue;
            }
            AbstractData data = this.billFormulaEvalService.evaluateBillAbstractData(env, dset, (String)dimensions.get(dimKey + "_customizeFormula"), billGroupDTO, tableNames);
            offSetItem.addFieldValue(dimKey, data.getAsObject());
        }
    }

    private String getDimValueByDimCodeAndOrg(GcCalcEnvContext env, String dimOrgId, String dimCode) {
        if (dimOrgId == null) {
            return null;
        }
        GcOrgCenterService orgCenterService = env.getCalcContextExpandVariableCenter().getOrgCenterService();
        GcOrgCacheVO dimOrg = orgCenterService.getOrgByCode(dimOrgId);
        if (dimOrg == null) {
            return null;
        }
        Object dimValue = dimOrg.getBaseFieldValue(dimCode);
        if (dimValue == null) {
            return null;
        }
        return dimValue.toString();
    }

    private String getSubjectCode(BillParam billParam, LeaseRuleDTO.Item item, DimensionValueSet dset, GcBillGroupDTO acceptData) {
        String subjectCode = null;
        LeaseFetchTypeEnum type = item.getType();
        if (type == null) {
            this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[" + billParam.rule.getLocalizedName() + "]\uff0c\u6570\u636e[ID:" + acceptData.getMaster().getId() + "]\u5728\u89c4\u5219\u6761\u76ee[" + item.getFetchFormula() + "]\u4e2d\u83b7\u53d6\u7c7b\u578b\u4e3a\u7a7a\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55");
            return null;
        }
        switch (type) {
            case FORMULA: {
                if (StringUtils.isEmpty((String)item.getSubjectCode())) break;
                AbstractData abstractData = this.billFormulaEvalService.evaluateBillAbstractData(billParam.env, dset, item.getSubjectCode(), acceptData, billParam.getAllTableNames());
                subjectCode = GcAbstractData.getStringValue((AbstractData)abstractData);
                break;
            }
            case CUSTOMIZE: {
                subjectCode = item.getSubjectCode();
            }
        }
        if (StringUtils.isEmpty(subjectCode)) {
            this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[" + billParam.rule.getLocalizedName() + "]\uff0c\u6570\u636e[ID:" + acceptData.getMaster().getId() + "]\u5728\u89c4\u5219\u6761\u76ee[" + item.getFetchFormula() + "]\u4e2d\u83b7\u53d6\u79d1\u76ee\u4ee3\u7801\u4e3a\u7a7a\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55");
        }
        return subjectCode;
    }

    class BillParam {
        private BillInfoVo billInfoVo;
        private GcCalcEnvContext env;
        private LeaseRuleDTO rule;

        BillParam() {
        }

        public List<String> getAllTableNames() {
            return null == this.billInfoVo ? null : this.billInfoVo.getAllTableNames();
        }
    }
}

