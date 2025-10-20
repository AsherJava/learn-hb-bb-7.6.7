/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.RuleSummaryQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.task.ManualOffsetTaskImpl;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class GcOffSetAppInputDataItemServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements GcOffSetAppInputDataItemService {
    @Autowired
    private RuleSummaryQueryAction ruleSummaryQueryAction;
    @Autowired
    private ManualOffsetTaskImpl manualOffsetTask;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public void handleUnitAndOppUnitParam(QueryParamsVO queryParamsVO) {
        List oppUnitIdList;
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, queryParamsVO.getPeriodStr()));
        List unitIdList = queryParamsVO.getUnitIdList();
        if (!CollectionUtils.isEmpty(unitIdList)) {
            queryParamsVO.setEnableTempTableFilterUnitOrOppUnit(Boolean.valueOf(true));
            queryParamsVO.setUnitIdList(this.getAllChildrenOrgByOrgList(unitIdList, tool));
        }
        if (!CollectionUtils.isEmpty(oppUnitIdList = queryParamsVO.getOppUnitIdList())) {
            queryParamsVO.setEnableTempTableFilterUnitOrOppUnit(Boolean.valueOf(true));
            queryParamsVO.setOppUnitIdList(this.getAllChildrenOrgByOrgList(oppUnitIdList, tool));
        }
    }

    private List<String> getAllChildrenOrgByOrgList(List<String> orgCodeList, GcOrgCenterService tool) {
        HashSet allOrgCodeSet = new HashSet();
        for (String code : orgCodeList) {
            if (allOrgCodeSet.contains(code)) continue;
            allOrgCodeSet.addAll(tool.listAllOrgByParentIdContainsSelf(code).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
        return new ArrayList<String>(allOrgCodeSet);
    }

    @Override
    public List<Map<String, Object>> handleRuleSpanMethod(List<Map<String, Object>> recordListMap) {
        return this.ruleSummaryQueryAction.handleRuleSpanMethod(recordListMap);
    }

    @Override
    public List<Map<String, Object>> manualRecordsMergeCalc(GcCalcArgmentsDTO paramsVO, List<InputDataEO> originRecords, List<String> resultOriginRecordIds) {
        return this.manualOffsetTask.manualRecordsMergeCalc(paramsVO, originRecords, resultOriginRecordIds);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void manualoffset(ManalOffsetParamsVO manalOffsetParamsVO) {
        this.manualOffsetTask.manualOffset(manalOffsetParamsVO);
    }

    @Override
    public BusinessResponseEntity<String> listEntryDetails(String recordId, String taskId, List<String> otherColumnCodes) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setId(recordId);
        inputData.setBizkeyOrder(recordId);
        InputDataEO inputDataEO = (InputDataEO)inputDataSqlDao.selectByEntity((BaseEntity)inputData);
        String jsonObjectStr = inputDataEO == null ? this.getOnlyOffsetRecordsJsonStr(recordId) : this.getOffSetAndUnOffsetRecordsJsonStr(inputDataEO, otherColumnCodes);
        return BusinessResponseEntity.ok((Object)jsonObjectStr);
    }

    @Override
    public BusinessResponseEntity<List<String>> queryInputDataOtherShowColumns(String taskId, List<String> otherColumnCodes) {
        Set<String> inputDataOtherColumn = this.getInputDataOtherShowColumns(taskId, otherColumnCodes);
        return BusinessResponseEntity.ok(new ArrayList<String>(inputDataOtherColumn));
    }

    private String getOnlyOffsetRecordsJsonStr(String recordId) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = this.offsetCoreService.getGcOffSetVchrItemDTO(recordId);
        if (gcOffSetVchrItemDTO == null) {
            return "{}";
        }
        String[] columnNamesInDB = new String[]{"DATATIME", "mrecId"};
        Object[] values = new Object[]{gcOffSetVchrItemDTO.getDefaultPeriod(), gcOffSetVchrItemDTO.getmRecid()};
        List recordEOs = this.gcOffSetAppOffsetService.listOffsetRecordsByWhere(columnNamesInDB, values);
        List records = this.gcOffSetAppOffsetService.convertAdjustData2View(recordEOs);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("offsetRecords", this.setRowSpanAndSort(records));
        return jsonObject.toString();
    }

    public List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
                    sortedRecords.addAll(oneEntryRecords);
                    ((Map)oneEntryRecords.get(0)).put("rowspan", size);
                    ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    private String getOffSetAndUnOffsetRecordsJsonStr(InputDataEO inputDataEO, List<String> inputDataOtherColumn) {
        if ("0".equals(inputDataEO.getOffsetState()) || inputDataEO.getOffsetGroupId() == null) {
            return "{}";
        }
        String[] columnNamesInDB = new String[]{"DATATIME", "srcOffsetGroupId"};
        Object[] values = new Object[]{inputDataEO.getPeriod(), inputDataEO.getOffsetGroupId()};
        List recordEOs = this.gcOffSetAppOffsetService.listOffsetRecordsByWhere(columnNamesInDB, values);
        if (null == recordEOs) {
            return "{}";
        }
        List records = this.gcOffSetAppOffsetService.convertAdjustData2View(recordEOs);
        String systemId = this.consolidatedTaskService.getSystemIdByTaskId(inputDataEO.getTaskId(), inputDataEO.getPeriod());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataEO.getTaskId());
        Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(inputDataOtherColumn, tableName);
        boolean showDictCode = "1".equals(this.optionService.getOptionData(systemId).getShowDictCode());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("offsetRecords", this.setRowSpanAndSort(records));
        List<Map<String, Object>> unOffsetRecords = this.queryUnOffsetRecordsByOffsetGroupId(systemId, inputDataEO.getTaskId(), inputDataEO.getOffsetGroupId(), inputDataEO.getCurrency(), inputDataOtherColumn);
        for (Map<String, Object> record : unOffsetRecords) {
            this.setOtherShowColumnDictTitle(record, inputDataOtherColumn, fieldCode2DictTableMap, showDictCode);
        }
        jsonObject.put("unOffsetRecords", unOffsetRecords);
        return jsonObject.toString();
    }

    private Set<String> getInputDataOtherShowColumns(String taskId, List<String> otherShowColumns) {
        Set<String> inputDataOtherColumn = this.convertColumn(otherShowColumns);
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        EntTableDefine tableDefine = entTableDefineProvider.getTableDefine(tableName);
        if (tableDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
        }
        List allInputdataFieldList = tableDefine.getAllFields();
        Set allInputdataFieldsSet = allInputdataFieldList.stream().map(EntFieldDefine::getCode).collect(Collectors.toSet());
        inputDataOtherColumn.retainAll(allInputdataFieldsSet);
        return inputDataOtherColumn;
    }

    private Set<String> convertColumn(List<String> otherShowColumns) {
        if (otherShowColumns.contains("CHKSTATE")) {
            otherShowColumns.remove("CHKSTATE");
            otherShowColumns.add("CHECKSTATE");
        }
        return new HashSet<String>(otherShowColumns);
    }

    private List<Map<String, Object>> queryUnOffsetRecordsByOffsetGroupId(String systemId, String taskId, String offsetGroupId, String currencyCode, List<String> inputDataOtherColumnList) {
        HashSet<String> inputDataOtherColumn = new HashSet<String>(inputDataOtherColumnList);
        if (null == offsetGroupId) {
            return new ArrayList<Map<String, Object>>();
        }
        HashSet<String> selectColumnNamesInDB = new HashSet<String>();
        selectColumnNamesInDB.add("ID");
        selectColumnNamesInDB.add("MDCODE");
        selectColumnNamesInDB.add("OPPUNITID");
        selectColumnNamesInDB.add("SUBJECTCODE");
        selectColumnNamesInDB.add("AMT");
        selectColumnNamesInDB.add("MEMO");
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        selectColumnNamesInDB.addAll(inputDataOtherColumn);
        String[] whereColumnNamesInDB = new String[]{"OffsetGroupId", "MD_CURRENCY"};
        Object[] whereValues = new Object[]{offsetGroupId, currencyCode};
        List<InputDataEO> inputDataEOs = this.inputdataDao.queryUnOffsetRecordsByWhere(null, whereColumnNamesInDB, whereValues, tableName);
        if (null == inputDataEOs) {
            return new ArrayList<Map<String, Object>>();
        }
        HashMap unitId2Title = new HashMap();
        HashMap subject2TitleCache = new HashMap();
        List<DesignFieldDefineVO> designFieldDefineVOS = this.inputdataDao.queryUnOffsetColumnSelect(tableName);
        Set<String> numberColumnSet = this.getNumberColumnSetByColumnCodes(inputDataOtherColumn, designFieldDefineVOS);
        return inputDataEOs.stream().map(temp -> {
            Map record = temp.getFields();
            record.put("UNITTITLE", this.getUnitTitle((String)record.get("MDCODE"), unitId2Title));
            record.put("OPPUNITTITLE", this.getUnitTitle((String)record.get("OPPUNITID"), unitId2Title));
            this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            record.put("UNCHECKEDAMT", NumberUtils.doubleToString((Double)((Double)record.get("AMT"))));
            for (String column : numberColumnSet) {
                record.put(column, NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble(record.get(column))));
            }
            return record;
        }).collect(Collectors.toList());
    }

    private Set<String> getNumberColumnSetByColumnCodes(Collection<String> otherColumnCodes, List<DesignFieldDefineVO> designFieldDefineVOS) {
        Map key2IdentityMap = designFieldDefineVOS.stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, Function.identity(), (o1, o2) -> o1));
        HashSet<String> numberColumnSet = new HashSet<String>();
        for (String column : otherColumnCodes) {
            DesignFieldDefineVO designFieldDefineVO;
            if (!key2IdentityMap.containsKey(column) || !ColumnModelType.BIGDECIMAL.equals((Object)(designFieldDefineVO = (DesignFieldDefineVO)key2IdentityMap.get(column)).getType()) && !ColumnModelType.DOUBLE.equals((Object)designFieldDefineVO.getType())) continue;
            numberColumnSet.add(column);
        }
        return numberColumnSet;
    }

    private List<String> getOtherShowColumns(String tableName, String systemId) {
        List dimensionVOs = this.optionService.getAllDimensionsByTableName(tableName, systemId);
        if (CollectionUtils.isEmpty(dimensionVOs)) {
            return new ArrayList<String>();
        }
        return dimensionVOs.stream().map(dim -> dim.getCode()).collect(Collectors.toList());
    }
}

