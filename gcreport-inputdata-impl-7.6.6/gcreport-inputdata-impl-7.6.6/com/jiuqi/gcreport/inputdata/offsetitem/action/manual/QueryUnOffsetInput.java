/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  org.apache.commons.lang3.tuple.Pair
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetManualService;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryUnOffsetInput
implements GcOffSetItemAction {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffsetManualService gcOffsetManualService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcOffSetAppInputDataItemService gcOffSetAppOffsetService;
    private final Logger logger = LoggerFactory.getLogger(QueryUnOffsetInput.class);

    public String code() {
        return "manualQuery";
    }

    public String title() {
        return "\u624b\u52a8\u62b5\u9500\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(gcOffsetExecutorVO.getParamObject());
            GcCalcArgmentsDTO gcCalcArgmentsDTO = (GcCalcArgmentsDTO)JsonUtils.readValue((String)jsonString, GcCalcArgmentsDTO.class);
            boolean showOriginalRecord = Boolean.parseBoolean((String)gcOffsetExecutorVO.getExtend("showOriginalRecord"));
            Assert.isNotEmpty((Collection)gcCalcArgmentsDTO.getRecordIds(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.noDataSelected"), (Object[])new Object[0]);
            InputDataService inputDataService = (InputDataService)SpringContextUtils.getBean(InputDataService.class);
            List<InputDataEO> records = inputDataService.queryByIds(gcCalcArgmentsDTO.getRecordIds(), gcCalcArgmentsDTO.getTaskId());
            records.forEach(item -> item.addFieldValue("UNITID", item.getUnitId()));
            ArrayList<Map<String, Object>> resultRecords = new ArrayList<Map>();
            ArrayList<String> resultOrignRecordIds = new ArrayList<String>();
            boolean isSupportCenterToDiff = false;
            boolean thanTwoUnit = this.getThanTwoUnit(records);
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
                ConsolidatedTaskVO taskBySchemeId = this.consolidatedTaskService.getTaskBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr());
                if (!"0".equals(taskBySchemeId.getMoreUnitOffset())) {
                    if (ManualOffsetTypeEnum.SUPPORT_BASE_TO_DIFFERENCE.getValue().equals(taskBySchemeId.getMoreUnitOffset())) {
                        isSupportCenterToDiff = true;
                    }
                    for (InputDataEO inputDataEO : records) {
                        ArrayList<String> unitGuids = new ArrayList<String>();
                        unitGuids.add(inputDataEO.getUnitId());
                        unitGuids.add(inputDataEO.getOppUnitId());
                        this.gcOffsetManualService.getInputUnitStatus(new ArrayList<Object>(unitGuids), records, gcCalcArgmentsDTO);
                    }
                } else {
                    Assert.isTrue((!thanTwoUnit ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.moreThanTwo"), (Object[])new Object[0]);
                    this.gcOffsetManualService.getInputUnitStatus(new ArrayList<Object>(this.listUnitForRecords(records)), records, gcCalcArgmentsDTO);
                }
                resultRecords = this.gcOffSetAppOffsetService.manualRecordsMergeCalc(gcCalcArgmentsDTO, records, resultOrignRecordIds);
            }
            if (thanTwoUnit && isSupportCenterToDiff) {
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
            }
            resultRecords.sort((m1, m2) -> (Integer)m2.get("DC") - (Integer)m1.get("DC"));
            String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr());
            Assert.isNotEmpty((String)systemId, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"), (Object[])new Object[0]);
            boolean showDictCode = this.optionService.getOptionItemBooleanBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr(), "showDictCode");
            this.gcOffsetManualService.setTitles(resultRecords, systemId, gcCalcArgmentsDTO.getOrgType(), gcCalcArgmentsDTO.getPeriodStr());
            this.gcOffsetManualService.setZjTitles(resultRecords, showDictCode);
            JSONObject jSONObject = this.gcOffsetManualService.calcDxje(resultRecords);
            jSONObject.put("recordIds", resultOrignRecordIds);
            return jSONObject.toString();
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u5931\u8d25:" + e.getMessage());
        }
    }

    private boolean getThanTwoUnit(List<InputDataEO> records) {
        return this.getThanTwoUnit(this.listUnitForRecords(records));
    }

    private Set<Object> listUnitForRecords(List<InputDataEO> records) {
        HashSet<Object> unitGuids = new HashSet<Object>();
        for (InputDataEO record : records) {
            unitGuids.add(record.getUnitId());
            unitGuids.add(record.getOppUnitId());
        }
        return unitGuids;
    }

    private boolean getThanTwoUnit(Set<Object> unitGuids) {
        return unitGuids.size() > 2;
    }
}

