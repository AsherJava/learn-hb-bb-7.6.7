/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.api.OffsetItemInputDataClient
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.validation.Valid
 *  org.apache.commons.lang3.tuple.Pair
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.inputdata.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.api.OffsetItemInputDataClient;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.GcInputDataPenetrateService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetItemAdjustReportService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetManualService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.ManualBatchOffsetService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class OffsetItemInputDataController
implements OffsetItemInputDataClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InputDataService inputDataService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffsetManualService gcOffsetManualService;
    @Autowired
    private GcOffSetAppInputDataItemService adjustingEntryService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private ManualBatchOffsetService manualBatchOffsetService;
    @Autowired
    private GcInputDataOffsetItemService gcInputDataOffsetItemService;
    @Autowired
    private GcOffSetItemAdjustReportService gcOffSetItemAdjustReportService;
    @Autowired(required=false)
    private GcInputDataPenetrateService gcInputDataPenetrateService;

    public BusinessResponseEntity<String> queryUnOffsetRecords(@RequestBody GcCalcArgmentsDTO paramsVO, @RequestParam boolean showOriginalRecord) {
        Assert.isNotEmpty((Collection)paramsVO.getRecordIds(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.noDataSelected"), (Object[])new Object[0]);
        List<InputDataEO> records = this.inputDataService.queryByIds(paramsVO.getRecordIds(), paramsVO.getTaskId());
        records.forEach(item -> item.addFieldValue("UNITID", item.getUnitId()));
        ArrayList<Map<String, Object>> resultRecords = new ArrayList<Map>();
        ArrayList<String> resultOrignRecordIds = new ArrayList<String>();
        boolean isSupportCenterToDiff = false;
        if (showOriginalRecord) {
            for (Object record : records) {
                if (MapUtils.getInt((Map)record.getFields(), (Object)"DC") == OrientEnum.C.getValue()) {
                    record.addFieldValue("CREDITVALUE", NumberUtils.doubleToString((Double)record.getAmt()));
                } else {
                    record.addFieldValue("DEBITVALUE", NumberUtils.doubleToString((Double)record.getAmt()));
                }
                resultRecords.add(record.getFields());
            }
        } else {
            ConsolidatedTaskVO taskBySchemeId = this.consolidatedTaskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
            if (!"0".equals(taskBySchemeId.getMoreUnitOffset())) {
                if (ManualOffsetTypeEnum.SUPPORT_BASE_TO_DIFFERENCE.getValue().equals(taskBySchemeId.getMoreUnitOffset())) {
                    isSupportCenterToDiff = true;
                }
                for (InputDataEO inputDataEO : records) {
                    ArrayList<String> unitGuids = new ArrayList<String>();
                    unitGuids.add(inputDataEO.getUnitId());
                    unitGuids.add(inputDataEO.getOppUnitId());
                    this.gcOffsetManualService.getInputUnitStatus(new ArrayList<Object>(unitGuids), records, paramsVO);
                }
            } else {
                HashSet unitGuids = new HashSet();
                for (InputDataEO record : records) {
                    unitGuids.add(record.getUnitId());
                    unitGuids.add(record.getOppUnitId());
                }
                Assert.isTrue((unitGuids.size() <= 2 ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.moreThanTwo"), (Object[])new Object[0]);
                this.gcOffsetManualService.getInputUnitStatus(new ArrayList<Object>(unitGuids), records, paramsVO);
            }
            resultRecords = this.adjustingEntryService.manualRecordsMergeCalc(paramsVO, records, resultOrignRecordIds);
        }
        if (isSupportCenterToDiff) {
            ArrayList<List<Map>> resultRecordsGroupBySubjects = new ArrayList<List<Map>>(resultRecords.stream().collect(Collectors.groupingBy(item -> Pair.of(item.get("SUBJECTCODE"), item.get("DC")))).values());
            resultRecords.clear();
            for (List list : resultRecordsGroupBySubjects) {
                BigDecimal creditValue = BigDecimal.ZERO;
                BigDecimal debitValue = BigDecimal.ZERO;
                for (Map resultRecord : list) {
                    creditValue = creditValue.add(ConverterUtils.getAsBigDecimal(resultRecord.get("CREDITVALUE")));
                    debitValue = debitValue.add(ConverterUtils.getAsBigDecimal(resultRecord.get("DEBITVALUE")));
                }
                if (new Integer(-1).equals(((Map)list.get(0)).get("DC"))) {
                    ((Map)list.get(0)).put("CREDITVALUE", NumberUtils.doubleToString((double)creditValue.doubleValue()));
                    ((Map)list.get(0)).put("AMMOUNT", creditValue.doubleValue());
                    ((Map)list.get(0)).remove("DEBITVALUE");
                }
                if (new Integer(1).equals(((Map)list.get(0)).get("DC"))) {
                    ((Map)list.get(0)).put("DEBITVALUE", NumberUtils.doubleToString((double)debitValue.doubleValue()));
                    ((Map)list.get(0)).put("AMMOUNT", debitValue.doubleValue());
                    ((Map)list.get(0)).remove("CREDITVALUE");
                }
                resultRecords.add((Map<String, Object>)list.get(0));
            }
            resultRecords.sort((m1, m2) -> (Integer)m2.get("DC") - (Integer)m1.get("DC"));
        }
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        Assert.isNotEmpty((String)systemId, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"), (Object[])new Object[0]);
        boolean showDictCode = this.optionService.getOptionItemBooleanBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr(), "showDictCode");
        this.gcOffsetManualService.setTitles(resultRecords, systemId, paramsVO.getOrgType(), paramsVO.getPeriodStr());
        this.gcOffsetManualService.setZjTitles(resultRecords, showDictCode);
        JSONObject jSONObject = this.gcOffsetManualService.calcDxje(resultRecords);
        jSONObject.put("recordIds", resultOrignRecordIds);
        return BusinessResponseEntity.ok((Object)jSONObject.toString());
    }

    public BusinessResponseEntity<Object> queryUnOffsetRecords(@Valid @RequestBody QueryParamsVO queryParamsVO) {
        GcOffsetItemUtils.logOffsetEntryFilterCondition((QueryParamsVO)queryParamsVO, (String)"\u672c\u7ea7\u672a\u62b5\u9500");
        Pagination pagination = this.gcOffSetAppOffsetService.listOffsetRecordsForAction(queryParamsVO);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u672c\u7ea7\u672a\u62b5\u9500\u67e5\u770b-\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        return BusinessResponseEntity.ok((Object)pagination);
    }

    public BusinessResponseEntity<Object> queryParentUnOffsetRecords(@Valid QueryParamsVO queryParamsVO) {
        GcOffsetItemUtils.logOffsetEntryFilterCondition((QueryParamsVO)queryParamsVO, (String)"\u4e0a\u7ea7\u672a\u62b5\u9500");
        if (StringUtils.isEmpty(queryParamsVO.getOrgId())) {
            return this.queryUnOffsetRecords(queryParamsVO);
        }
        Pagination pagination = this.gcOffSetAppOffsetService.listOffsetRecordsForAction(queryParamsVO);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u4e0a\u7ea7\u672a\u62b5\u9500\u67e5\u770b-\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        return BusinessResponseEntity.ok((Object)pagination);
    }

    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryUnOffsetColumnSelect(String systemId, String dataSource) {
        List unOffsetColumnSelects = this.gcInputDataOffsetItemService.listUnOffsetColumnSelects(systemId, dataSource);
        return BusinessResponseEntity.ok((Object)unOffsetColumnSelects);
    }

    public BusinessResponseEntity<String> queryRecordOffsetState(@PathVariable(value="recordId") String recordId, @PathVariable(value="taskId") String taskId) {
        return BusinessResponseEntity.ok((Object)this.inputDataService.queryRecordOffsetState(recordId, taskId));
    }

    public BusinessResponseEntity<String> canOffset(@RequestBody Map<String, Object> params) {
        List inputItemIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("inputItemIds")), (TypeReference)new TypeReference<List<String>>(){});
        boolean realTimeOffset = (Boolean)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("realTimeOffset")), Boolean.class);
        String orgType = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("orgType")), String.class);
        String taskId = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("taskId")), String.class);
        if (StringUtils.hasText(orgType)) {
            GcOrgTypeUtils.setContextEntityId((String)orgType);
        }
        return BusinessResponseEntity.ok((Object)this.inputDataService.canOffset(inputItemIds, realTimeOffset, orgType, taskId));
    }

    public BusinessResponseEntity<String> efdcPenetrableSearch(@RequestBody JtableContext jtableContext, @PathVariable(value="recordId") List<String> recordIds, @PathVariable(value="fieldCode") String fieldCode) {
        JSONObject jsonObject = this.inputDataService.efdcPenetrableSearch(jtableContext, recordIds, fieldCode);
        return BusinessResponseEntity.ok((Object)jsonObject.toString());
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> manualoffset(@RequestBody ManalOffsetParamsVO manalOffsetParamsVO) {
        try {
            this.adjustingEntryService.manualoffset(manalOffsetParamsVO);
        }
        catch (Exception e) {
            this.logger.error("\u624b\u52a8\u62b5\u9500\u5931\u8d25\uff1a", e);
            throw new BusinessRuntimeException("\u624b\u52a8\u62b5\u9500\u5931\u8d25\uff1a" + e.getMessage());
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> manualBatchOffset(ManualBatchOffsetParamsVO manualBatchOffsetParamsVO) {
        if (manualBatchOffsetParamsVO != null) {
            GcOrgTypeUtils.setContextEntityId((String)manualBatchOffsetParamsVO.getOrgType());
        }
        this.manualBatchOffsetService.manualBatchOffset(manualBatchOffsetParamsVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> calc(QueryParamsVO queryParamsVO) {
        if (queryParamsVO != null) {
            GcOrgTypeUtils.setContextEntityId((String)queryParamsVO.getOrgType());
        }
        this.gcOffsetAppInputDataService.calc(queryParamsVO);
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.dataentryquery.inputdataservice.calcsuccessmsg"));
    }

    public BusinessResponseEntity<String> queryEntryDetails(@PathVariable(value="recordId") String recordId, @PathVariable(value="taskId") String taskId, @RequestBody List<String> otherColumnCodes) {
        return this.adjustingEntryService.listEntryDetails(recordId, taskId, otherColumnCodes);
    }

    public BusinessResponseEntity<List<String>> queryInputDataOtherShowColumns(@PathVariable(value="taskId") String taskId, @RequestBody List<String> otherColumnCodes) {
        return this.adjustingEntryService.queryInputDataOtherShowColumns(taskId, otherColumnCodes);
    }

    public BusinessResponseEntity<String> relateRecords(@PathVariable(value="recordId") String recordId, @PathVariable(value="taskId") String taskId, List<String> otherColumnCodes) {
        List<Map<String, Object>> unOffsetRecords = this.gcOffSetItemAdjustReportService.relateRecords(recordId, taskId, otherColumnCodes);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unOffsetRecords", unOffsetRecords);
        return BusinessResponseEntity.ok((Object)jsonObject.toString());
    }

    public BusinessResponseEntity<Object> queryOffsetByRecords(GcCalcArgmentsDTO paramsVO) {
        return BusinessResponseEntity.ok(this.inputDataService.queryByIds(paramsVO.getRecordIds(), paramsVO.getTaskId()));
    }

    public BusinessResponseEntity<Object> isSupportPentration() {
        if (Objects.nonNull(this.gcInputDataPenetrateService)) {
            return BusinessResponseEntity.ok((Object)true);
        }
        return BusinessResponseEntity.ok((Object)false);
    }

    public BusinessResponseEntity<Object> initPenetrateData(String recordId, String taskId) {
        return BusinessResponseEntity.ok((Object)this.gcInputDataPenetrateService.initPenetrateData(recordId, taskId));
    }
}

