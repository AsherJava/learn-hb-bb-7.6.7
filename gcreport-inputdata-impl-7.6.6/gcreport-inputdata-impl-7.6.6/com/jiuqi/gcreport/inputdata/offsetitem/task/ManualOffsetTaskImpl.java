/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 *  com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffsetItemCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.task;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleOffsetItemGenerateUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffsetItemCoreService;
import com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ManualOffsetTaskImpl {
    @Autowired
    private ManagementDimensionCacheService managementDimensionCacheService;
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private InputDataLockService inputDataLockService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired(required=false)
    private GcOffsetItemCoreService gcOffsetItemCoreService;
    @Autowired
    private ConsolidatedTaskCacheService consolidatedTaskCacheService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Map<String, Object>> manualRecordsMergeCalc(GcCalcArgmentsDTO paramsVO, List<InputDataEO> originRecords, List<String> resultOriginRecordIds) {
        List<GcOffSetVchrItemDTO> offSetVchrItemDTOs;
        String ruleId = null;
        for (InputDataEO record : originRecords) {
            record.getFields().put("PROJECTTITLE", record.getFields().get("PROJECTCODE"));
            String tempRuleid = record.getUnionRuleId();
            if (null == ruleId) {
                ruleId = tempRuleid;
            }
            if (null != tempRuleid && ruleId.equals(tempRuleid)) continue;
            ruleId = null;
            break;
        }
        for (InputDataEO inputDataEO : originRecords) {
            Double ammount;
            inputDataEO.addFieldValue("AMMOUNT", inputDataEO.getAmt());
            Integer direct = inputDataEO.getDc();
            if (null == direct) {
                direct = OrientEnum.D.getValue();
            }
            if (null == (ammount = inputDataEO.getAmt())) {
                ammount = 0.0;
            }
            if (direct == OrientEnum.D.getValue()) {
                inputDataEO.addFieldValue("DEBITVALUE", NumberUtils.doubleToString((Double)ammount));
                continue;
            }
            inputDataEO.addFieldValue("CREDITVALUE", NumberUtils.doubleToString((Double)ammount));
        }
        if (null == resultOriginRecordIds) {
            resultOriginRecordIds = new ArrayList<String>();
        }
        for (InputDataEO inputDataEO : originRecords) {
            resultOriginRecordIds.add(inputDataEO.getId());
        }
        if (null == ruleId) {
            return originRecords.stream().map(temp -> temp.getFields()).collect(Collectors.toList());
        }
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById(ruleId);
        if (null == rule || !RuleTypeEnum.FLEXIBLE.getCode().equals(rule.getRuleType())) {
            return originRecords.stream().map(temp -> temp.getFields()).collect(Collectors.toList());
        }
        try {
            offSetVchrItemDTOs = RuleOffsetItemGenerateUtils.generateOffsetItem(ruleId, originRecords, paramsVO);
        }
        catch (Exception e) {
            this.logger.warn(e.getMessage(), e);
            throw new BusinessRuntimeException("\u7075\u6d3b\u89c4\u5219\u751f\u6210\u62b5\u9500\u5206\u5f55\u65f6\u62a5\u9519:" + e.getMessage(), (Throwable)e);
        }
        if (offSetVchrItemDTOs == null || offSetVchrItemDTOs.size() == 0) {
            List<Map<String, Object>> result = originRecords.stream().map(temp -> temp.getFields()).collect(Collectors.toList());
            this.logger.info("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u7075\u6d3b\u89c4\u5219\uff0c\u8fd4\u56de\u539f\u59cb\u6570\u636e\uff1a{}", (Object)result);
            return result;
        }
        ArrayList<Map<String, Object>> resultRecords = new ArrayList<Map<String, Object>>();
        for (GcOffSetVchrItemDTO gcOffSetVchrItemDTO : offSetVchrItemDTOs) {
            Map record = gcOffSetVchrItemDTO.getFields();
            Double offSetCredit = gcOffSetVchrItemDTO.getOffSetCredit();
            Double offSetDebit = gcOffSetVchrItemDTO.getOffSetDebit();
            int dc = OrientEnum.D.getValue();
            if (offSetDebit == null || offSetDebit != null && offSetCredit != null && offSetDebit == 0.0) {
                dc = OrientEnum.C.getValue();
            }
            if (dc == OrientEnum.C.getValue()) {
                record.put("CREDITVALUE", NumberUtils.doubleToString((Double)offSetCredit));
                record.put("AMMOUNT", offSetCredit);
                record.put("DC", OrientEnum.C.getValue());
            } else {
                record.put("DEBITVALUE", NumberUtils.doubleToString((Double)offSetDebit));
                record.put("AMMOUNT", offSetDebit);
                record.put("DC", OrientEnum.D.getValue());
            }
            record.put("BUSINESSTYPECODE", gcOffSetVchrItemDTO.getGcBusinessTypeCode());
            record.put("OFFSETSRCTYPE", null);
            record.put("ID", null);
            record.put("fromRuleFlex", true);
            resultRecords.add(record);
        }
        return resultRecords;
    }

    @Transactional(rollbackFor={Exception.class})
    public void manualOffset(ManalOffsetParamsVO manalOffsetParamsVO) {
        boolean thanTwoUnit;
        this.checkSubject(manalOffsetParamsVO.getRecords(), manalOffsetParamsVO.getTaskId(), manalOffsetParamsVO.getPeriodStr());
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(manalOffsetParamsVO.getTaskId(), manalOffsetParamsVO.getPeriodStr());
        boolean bl = thanTwoUnit = this.listUnitForRecords(manalOffsetParamsVO.getRecords()).size() > 2;
        if (FilterMethodEnum.RULESUMMARY.getCode().equals(manalOffsetParamsVO.getFilterMethod()) || thanTwoUnit && ManualOffsetTypeEnum.SUPPORT_BASE_TO_DIFFERENCE.getValue().equals(consolidatedTaskVO.getMoreUnitOffset())) {
            YearPeriodObject yp = new YearPeriodObject(null, manalOffsetParamsVO.getPeriodStr());
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)manalOffsetParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            GcOrgCacheVO gcOrgCacheVO = instance.getOrgByCode(manalOffsetParamsVO.getOrgId());
            String diffUnitId = gcOrgCacheVO.getDiffUnitId();
            ArrayList sumRecords = new ArrayList();
            Iterator manalOffsetRecordGroupByDC = new ArrayList<List<Map>>(manalOffsetParamsVO.getRecords().stream().collect(Collectors.groupingBy(record -> (String)record.get("DC"))).values());
            Iterator iterator = manalOffsetRecordGroupByDC.iterator();
            while (iterator.hasNext()) {
                List manalOffsetRecords = (List)iterator.next();
                if (OrientEnum.D.getValue().equals(new Integer((String)((Map)manalOffsetRecords.get(0)).get("DC")))) {
                    manalOffsetRecords.forEach(record -> {
                        record.put("UNITID", diffUnitId);
                        record.put("OPPUNITID", gcOrgCacheVO.getId());
                    });
                }
                if (OrientEnum.C.getValue().equals(new Integer((String)((Map)manalOffsetRecords.get(0)).get("DC")))) {
                    manalOffsetRecords.forEach(record -> {
                        record.put("UNITID", gcOrgCacheVO.getId());
                        record.put("OPPUNITID", diffUnitId);
                    });
                }
                sumRecords.addAll(manalOffsetRecords);
            }
            manalOffsetParamsVO.setRecords(sumRecords);
        }
        HashMap<String, Double> needModifyRecordId2offsetAmtMap = new HashMap<String, Double>(16);
        ArrayList<GcOffSetVchrItemDTO> needInsertOffRecords = new ArrayList<GcOffSetVchrItemDTO>();
        String entryGuid = UUIDOrderSnowUtils.newUUIDStr();
        boolean isFlexRuleWay = false;
        String ruleId = null;
        for (Map record2 : manalOffsetParamsVO.getRecords()) {
            double offsetValueDouble;
            GcOffSetVchrItemDTO offSetRecordDTO = this.initManualRecordDTO((QueryParamsVO)manalOffsetParamsVO, record2);
            if (FilterMethodEnum.RULESUMMARY.getCode().equals(manalOffsetParamsVO.getFilterMethod())) {
                offSetRecordDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.BATCH_MANUAL_ITEM.getValue()));
            }
            String offsetValue = (String)record2.get("OFFSETVALUE");
            String recid = (String)record2.get("ID");
            if (null == recid) {
                isFlexRuleWay = true;
                offSetRecordDTO.setId(UUIDOrderSnowUtils.newUUIDStr());
                MapUtils.add(needModifyRecordId2offsetAmtMap, (Object)offSetRecordDTO.getSrcId(), (Double)new Double(offsetValue.replace(",", "")));
            } else {
                offSetRecordDTO.setId(recid);
                needModifyRecordId2offsetAmtMap.put(offSetRecordDTO.getId(), new Double(offsetValue.replace(",", "")));
            }
            offSetRecordDTO.setmRecid(entryGuid);
            offSetRecordDTO.setSrcOffsetGroupId(entryGuid);
            if (record2.containsKey("CHECKSTATE") && record2.get("CHECKSTATE") != null) {
                offSetRecordDTO.setChkState((String)record2.get("CHECKSTATE"));
            }
            String debitValue = (String)record2.get("DEBITVALUE");
            String creditValue = (String)record2.get("CREDITVALUE");
            if (StringUtils.isEmpty((String)debitValue)) {
                double creditValueDouble = new Double(creditValue.replace(",", ""));
                offsetValueDouble = new Double(offsetValue.replace(",", ""));
                offSetRecordDTO.setCredit(Double.valueOf(creditValueDouble));
                offSetRecordDTO.setOffSetCredit(Double.valueOf(offsetValueDouble));
                offSetRecordDTO.setDiffc(Double.valueOf(creditValueDouble - offsetValueDouble));
            } else {
                double debitValueDouble = new Double(debitValue.replace(",", ""));
                offsetValueDouble = new Double(offsetValue.replace(",", ""));
                offSetRecordDTO.setDebit(Double.valueOf(debitValueDouble));
                offSetRecordDTO.setOffSetDebit(Double.valueOf(offsetValueDouble));
                offSetRecordDTO.setDiffd(Double.valueOf(debitValueDouble - offsetValueDouble));
            }
            needInsertOffRecords.add(offSetRecordDTO);
            if (null != ruleId || StringUtils.isEmpty((String)offSetRecordDTO.getRuleId())) continue;
            ruleId = offSetRecordDTO.getRuleId();
        }
        if (isFlexRuleWay) {
            this.repairManualOffsetAmt(needModifyRecordId2offsetAmtMap, manalOffsetParamsVO);
        }
        if (FilterMethodEnum.RULESUMMARY.getCode().equals(manalOffsetParamsVO.getFilterMethod()) || thanTwoUnit && ManualOffsetTypeEnum.SUPPORT_BASE_TO_DIFFERENCE.getValue().equals(consolidatedTaskVO.getMoreUnitOffset())) {
            needModifyRecordId2offsetAmtMap.clear();
            for (String recordId : manalOffsetParamsVO.getRecordIds()) {
                needModifyRecordId2offsetAmtMap.put(recordId, 0.0);
            }
        }
        this.repairRuleIfRowWithoutRule(needInsertOffRecords, ruleId);
        GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO();
        offSetVchrDTO.setItems(needInsertOffRecords);
        offSetVchrDTO.setMrecid(entryGuid);
        offSetVchrDTO.setAllowMoreUnit(true, manalOffsetParamsVO.getOrgType());
        this.save(needModifyRecordId2offsetAmtMap, offSetVchrDTO, manalOffsetParamsVO.getTaskId(), manalOffsetParamsVO.getDataSourceCode());
        this.doManualOffsetLog(needInsertOffRecords);
    }

    private Set<Object> listUnitForRecords(List<Map<String, String>> records) {
        HashSet<Object> unitGuids = new HashSet<Object>();
        for (Map<String, String> record : records) {
            unitGuids.add(record.get("UNITID"));
            unitGuids.add(record.get("OPPUNITID"));
        }
        return unitGuids;
    }

    private void doManualOffsetLog(List<GcOffSetVchrItemDTO> needInsertOffRecords) {
        try {
            if (CollectionUtils.isEmpty(needInsertOffRecords)) {
                return;
            }
            GcOffSetVchrItemDTO dto = needInsertOffRecords.get(0);
            String taskTitle = this.iRunTimeViewController.queryTaskDefine(dto.getTaskId()).getTitle();
            String defaultPeriod = dto.getDefaultPeriod();
            String ruleTitle = this.unionRuleService.selectUnionRuleDTOById(dto.getRuleId()).getLocalizedName();
            StringBuilder message = new StringBuilder();
            message.append("\u4efb\u52a1-").append(taskTitle).append("\uff1b\u65f6\u671f-").append(defaultPeriod).append("\uff1b\u5408\u5e76\u89c4\u5219-").append(ruleTitle).append("\uff1b\u672c\u65b9\u5355\u4f4d-").append(dto.getUnitId()).append("\uff1b\u5bf9\u65b9\u5355\u4f4d-").append(dto.getOppUnitId()).append("\n");
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u624b\u52a8\u62b5\u9500-\u4efb\u52a1" + taskTitle + "-\u65f6\u671f" + defaultPeriod), (String)message.toString());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u62b5\u9500\u5206\u5f55\u65e5\u5fd7\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    private void repairRuleIfRowWithoutRule(List<GcOffSetVchrItemDTO> needInsertOffRecords, String ruleId) {
        if (StringUtils.isEmpty((String)ruleId)) {
            return;
        }
        for (GcOffSetVchrItemDTO offSetRecordDTO : needInsertOffRecords) {
            if (!StringUtils.isEmpty((String)offSetRecordDTO.getRuleId())) continue;
            offSetRecordDTO.setRuleId(ruleId);
            UnionRuleVO ruleVO = this.getRule(ruleId);
            if (null == ruleVO || null == ruleVO.getBusinessTypeCode()) continue;
            offSetRecordDTO.setGcBusinessTypeCode(ruleVO.getBusinessTypeCode().getCode());
        }
    }

    private void save(Map<String, Double> needModifyRecordId2offsetAmtMap, GcOffSetVchrDTO offSetVchrDTO, String taskId, String dataSource) {
        if (DataSourceEnum.INPUT_DATA.getCode().equals(dataSource)) {
            this.saveInput(needModifyRecordId2offsetAmtMap, offSetVchrDTO, taskId);
        } else {
            this.gcOffsetItemCoreService.save(offSetVchrDTO);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveInput(Map<String, Double> needModifyRecordId2offsetAmtMap, GcOffSetVchrDTO offSetVchrDTO, String taskId) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List<InputDataEO> inputItems = this.inputdataDao.queryByIds(needModifyRecordId2offsetAmtMap.keySet(), tableName);
        if (CollectionUtils.isEmpty(inputItems) || inputItems.size() != needModifyRecordId2offsetAmtMap.size()) {
            throw new RuntimeException("\u6570\u636e\u5df2\u88ab\u5176\u5b83\u64cd\u4f5c\u66f4\u65b0\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u3002");
        }
        InputDataEO inputItem = inputItems.get(0);
        this.insertAdjust(inputItem, offSetVchrDTO);
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(inputItem.getTaskId(), String.valueOf(inputItem.getFieldValue("DATATIME")), String.valueOf(inputItem.getFieldValue("MD_CURRENCY")));
        String lockId = this.inputDataLockService.tryLock(needModifyRecordId2offsetAmtMap.keySet(), limitCondition, "\u53d6\u6d88\u62b5\u9500");
        if (StringUtils.isEmpty((String)lockId)) {
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u5176\u5b83\u64cd\u4f5c\u4f7f\u7528\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        boolean hasDiff = offSetVchrDTO.getItems().stream().filter(item -> NumberUtils.sum((Double)item.getDiffd(), (Double)item.getDiffc()) != 0.0).count() > 0L;
        try {
            if (hasDiff) {
                this.inputdataDao.updateInputDataToOffset(needModifyRecordId2offsetAmtMap, offSetVchrDTO.getMrecid(), tableName);
            } else {
                this.inputdataDao.updateInputDataToOffsetNoDiff(needModifyRecordId2offsetAmtMap.keySet(), offSetVchrDTO.getMrecid(), tableName);
            }
            offSetVchrDTO.setConsFormulaCalcType("manualFlag");
            this.gcOffSetAppOffsetService.save(offSetVchrDTO);
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
    }

    private void insertAdjust(InputDataEO inputItem, GcOffSetVchrDTO offSetVchrDTO) {
        List gcOffSetVchrItemDTOS = offSetVchrDTO.getItems();
        gcOffSetVchrItemDTOS.forEach(dto -> dto.setSelectAdjustCode((String)inputItem.getFieldValue("ADJUST")));
    }

    private void repairManualOffsetAmt(Map<String, Double> needModifyRecordId2offsetAmtMap, ManalOffsetParamsVO manalOffsetParamsVO) {
        Assert.isNotEmpty((Collection)manalOffsetParamsVO.getRecordIds(), (String)"\u53c2\u6570\u975e\u6cd5:\u672a\u627e\u89c1recordIds", (Object[])new Object[0]);
        needModifyRecordId2offsetAmtMap.remove(null);
        needModifyRecordId2offsetAmtMap.remove("");
        HashSet<String> ids = new HashSet<String>(needModifyRecordId2offsetAmtMap.keySet());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(manalOffsetParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> sqlDefaultDao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        for (String id : ids) {
            InputDataEO inputData = new InputDataEO();
            inputData.setId(id);
            inputData.setBizkeyOrder(id);
            InputDataEO inputDataEO = (InputDataEO)sqlDefaultDao.selectByEntity((BaseEntity)inputData);
            if (null != inputDataEO) continue;
            needModifyRecordId2offsetAmtMap.remove(id);
        }
        List needModifyRecordIds = manalOffsetParamsVO.getRecordIds();
        for (String id : needModifyRecordIds) {
            if (needModifyRecordId2offsetAmtMap.containsKey(id)) continue;
            InputDataEO inputData = new InputDataEO();
            inputData.setId(id);
            inputData.setBizkeyOrder(id);
            InputDataEO inputDataEO = (InputDataEO)sqlDefaultDao.selectByEntity((BaseEntity)inputData);
            if (null == inputDataEO) continue;
            needModifyRecordId2offsetAmtMap.put(id, inputDataEO.getAmt());
        }
    }

    private void checkSubject(List<Map<String, String>> records, String taskId, String periodStr) {
        boolean hasCASH = false;
        boolean hasNotCASH = false;
        for (Map<String, String> item : records) {
            String reportSystemId = this.consolidatedTaskCacheService.getSystemIdByTaskId(taskId, periodStr);
            Assert.isNotNull((Object)reportSystemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(reportSystemId, item.get("SUBJECTCODE"));
            if (SubjectAttributeEnum.CASH.getValue() == subject.getAttri().intValue()) {
                hasCASH = true;
            } else {
                hasNotCASH = true;
            }
            if (!hasCASH || !hasNotCASH) continue;
            throw new BusinessRuntimeException("\u73b0\u91d1\u6d41\u4e0e\u975e\u73b0\u91d1\u6d41\u79d1\u76ee\u4e0d\u5141\u8bb8\u62b5\u9500");
        }
    }

    private GcOffSetVchrItemDTO initRecordDTO(QueryParamsVO arg) {
        GcOffSetVchrItemDTO record = new GcOffSetVchrItemDTO();
        record.setAcctPeriod(arg.getAcctPeriod());
        record.setAcctYear(arg.getAcctYear());
        record.setDefaultPeriod(arg.getPeriodStr());
        record.setTaskId(arg.getTaskId());
        record.setSchemeId(arg.getSchemeId());
        record.setCreateTime(new Date());
        record.setOffSetCurr(arg.getCurrencyUpperCase());
        return record;
    }

    private GcOffSetVchrItemDTO initManualRecordDTO(QueryParamsVO arg, Map<String, String> record) {
        UnionRuleVO ruleVO;
        GcOffSetVchrItemDTO offSetRecordDTO = this.initRecordDTO(arg);
        String offSetSrcType = record.get("offSetSrcType");
        offSetRecordDTO.setOffSetSrcType(StringUtils.isEmpty((String)offSetSrcType) ? OffSetSrcTypeEnum.MANUAL_OFFSET_INPUT : OffSetSrcTypeEnum.getEnumByValue((int)Integer.parseInt(offSetSrcType)));
        offSetRecordDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.MANUAL_ITEM.getValue()));
        offSetRecordDTO.setUnitId(record.get("UNITID"));
        offSetRecordDTO.setOppUnitId(record.get("OPPUNITID"));
        offSetRecordDTO.setSubjectCode(record.get("SUBJECTCODE"));
        offSetRecordDTO.setGcBusinessTypeCode(record.get("BUSINESSTYPECODE"));
        if (record.containsKey("RULEID")) {
            offSetRecordDTO.setRuleId(record.get("RULEID"));
        } else {
            offSetRecordDTO.setRuleId(record.get("UNIONRULEID"));
        }
        if (!StringUtils.isEmpty((String)offSetRecordDTO.getRuleId()) && null != (ruleVO = this.getRule(offSetRecordDTO.getRuleId())) && null != ruleVO.getBusinessTypeCode()) {
            offSetRecordDTO.setGcBusinessTypeCode(ruleVO.getBusinessTypeCode().getCode());
        }
        offSetRecordDTO.setSrcId(record.get("SRCID"));
        offSetRecordDTO.setMemo(record.get("MEMO"));
        List managementDims = this.managementDimensionCacheService.getOptionalManagementDims();
        for (ManagementDim dim : managementDims) {
            if (StringUtils.isEmpty((String)dim.getCode())) continue;
            offSetRecordDTO.addUnSysFieldValue(dim.getCode().toUpperCase(), (Object)record.get(dim.getCode().toUpperCase()));
        }
        offSetRecordDTO.setSelectAdjustCode(arg.getSelectAdjustCode());
        offSetRecordDTO.addFieldValue("DATASOURCESID", (Object)record.get("DATASOURCESID"));
        return offSetRecordDTO;
    }

    private UnionRuleVO getRule(String id) {
        if (id == null) {
            return null;
        }
        return this.unionRuleService.selectUnionRuleById(id);
    }
}

