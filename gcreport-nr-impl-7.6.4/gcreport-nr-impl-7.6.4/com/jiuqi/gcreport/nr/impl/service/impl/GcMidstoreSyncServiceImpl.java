/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.nr.dto.GcMidStoreTableDataDTO
 *  com.jiuqi.gcreport.nr.dto.GcMidstoreSyncDTO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nr.mapping.service.BaseDataMappingService
 *  com.jiuqi.nr.mapping.service.ZBMappingService
 *  nr.midstore.core.definition.bean.MidstoreContext
 *  nr.midstore.core.definition.dto.MidstoreSchemeDTO
 *  nr.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  nr.midstore.core.definition.service.IMidstoreSchemeService
 *  nr.midstore.core.param.service.IMistoreExchangeTaskService
 *  nr.midstore.core.util.IMidstoreDimensionService
 *  nr.midstore.design.domain.SchemeFieldDTO
 *  nr.midstore.design.service.IFileReadService
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
import com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.nr.dto.GcMidStoreTableDataDTO;
import com.jiuqi.gcreport.nr.dto.GcMidstoreSyncDTO;
import com.jiuqi.gcreport.nr.impl.dto.GcMidstoreSyncFixTableDataDTO;
import com.jiuqi.gcreport.nr.impl.dto.GcMidstoreSyncFloatTableDTO;
import com.jiuqi.gcreport.nr.impl.service.GcMidstoreSyncService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.service.BaseDataMappingService;
import com.jiuqi.nr.mapping.service.ZBMappingService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.service.IFileReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcMidstoreSyncServiceImpl
implements GcMidstoreSyncService {
    private final Logger LOGGER = LoggerFactory.getLogger(GcMidstoreSyncServiceImpl.class);
    private MidstoreContext context;
    private String tablePrefix;
    private String midStoreFixTableName;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreSchemeService midstoreSchemeService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IFileReadService fileReadService;
    private static final String MDCODE = "mdcode";
    private static final String PERIOD = "period";
    private static final String YEAR = "year";
    private static final String ZBNAME = "ZB_NAME";

    @Override
    public void saveData2MidStore(GcMidstoreSyncDTO gcMidstoreSyncDTO) {
        String taskCode = gcMidstoreSyncDTO.getTaskCode();
        String midStoreSchemeCode = gcMidstoreSyncDTO.getMidStoreSchemeCode();
        MidstoreSchemeDTO midstoreScheme = this.midstoreSchemeService.getByCode(midStoreSchemeCode);
        if (midstoreScheme == null) {
            this.LOGGER.error("\u62a5\u6587\u4e2d\u7684 midStoreSchemeCode\u3010" + midStoreSchemeCode + "\u3011\u65e0\u6548");
            throw new BusinessRuntimeException("\u62a5\u6587\u4e2d\u7684 midStoreSchemeCode\u3010" + midStoreSchemeCode + "\u3011\u65e0\u6548");
        }
        this.tablePrefix = midstoreScheme.getTablePrefix();
        this.midStoreFixTableName = this.tablePrefix + "_" + "FIXDATA";
        this.context = this.getContext(midstoreScheme);
        IDataExchangeTask dataExchangeTask = this.buildiDataExchangeTask(this.context);
        this.checkTaskCode(taskCode, midstoreScheme);
        List schemeFieldDTOS = this.fileReadService.listMidstoreFieldBySheme(midstoreScheme.getKey());
        Map<String, SchemeFieldDTO> msBindAllZb2TableMap = schemeFieldDTOS.stream().collect(Collectors.toMap(t -> ConverterUtils.getAsString(t.getValues().get("DataTable")) + "[" + t.getCode() + "]", t -> t));
        String configKey = midstoreScheme.getConfigKey();
        ZBMappingService zbMappingService = (ZBMappingService)SpringContextUtils.getBean(ZBMappingService.class);
        List zbMappingList = zbMappingService.findByMS(configKey);
        HashMap<String, String> externalZbName2JqZbNameMap = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)zbMappingList)) {
            externalZbName2JqZbNameMap = zbMappingList.stream().collect(Collectors.toMap(t -> !StringUtils.isEmpty((String)t.getMapping()) ? t.getMapping() : t.getZbCode(), t -> t.getTable() + "[" + t.getZbCode() + "]"));
        }
        BaseDataMappingService baseDataMappingService = (BaseDataMappingService)SpringContextUtils.getBean(BaseDataMappingService.class);
        List baseDataItemMappingList = baseDataMappingService.getBaseDataItemMappingByMSKey(configKey);
        Map<String, BaseDataItemMapping> baseDataMappingTitle2Self = baseDataItemMappingList.stream().collect(Collectors.toMap(BaseDataItemMapping::getMappingTitle, Function.identity()));
        ArrayList<DETableModel> fixTableList = new ArrayList<DETableModel>();
        ArrayList<DETableModel> floatTableList = new ArrayList<DETableModel>();
        try {
            List allTableModel = dataExchangeTask.getAllTableModel();
            for (DETableModel tableModel : allTableModel) {
                DETableInfo tableInfo = tableModel.getTableInfo();
                TableType tableType = tableInfo.getType();
                if (tableType == TableType.ZB) {
                    fixTableList.add(tableModel);
                    continue;
                }
                if (tableType != TableType.BIZ) continue;
                floatTableList.add(tableModel);
            }
        }
        catch (DataExchangeException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        HashMap<String, DEFieldInfo> field2Self = new HashMap<String, DEFieldInfo>();
        HashMap<String, DEZBInfo> zb2Self = new HashMap<String, DEZBInfo>();
        for (DETableModel tableModel : fixTableList) {
            List refZBs = tableModel.getRefZBs();
            zb2Self.putAll(refZBs.stream().collect(Collectors.toMap(DEZBInfo::getName, Function.identity())));
        }
        for (DETableModel tableModel : floatTableList) {
            List fields = tableModel.getFields();
            field2Self.putAll(fields.stream().collect(Collectors.toMap(DEFieldInfo::getName, Function.identity())));
        }
        List gcMidStoreTableDataDTOS = gcMidstoreSyncDTO.getTableDataList();
        HashMap<String, GcMidStoreTableDataDTO> fixTableMap = new HashMap<String, GcMidStoreTableDataDTO>();
        HashMap<String, GcMidStoreTableDataDTO> floatTableMap = new HashMap<String, GcMidStoreTableDataDTO>();
        for (GcMidStoreTableDataDTO tableDataDTO : gcMidStoreTableDataDTOS) {
            this.checkTableDataInfo(externalZbName2JqZbNameMap, msBindAllZb2TableMap, tableDataDTO, field2Self, fixTableMap, floatTableMap);
        }
        if (!fixTableMap.isEmpty()) {
            this.LOGGER.info("\u5f00\u59cb\u4fdd\u5b58\u56fa\u5b9a\u8868\u6570\u636e\u3002\u3002\u3002");
            this.saveFixTableData(baseDataMappingTitle2Self, msBindAllZb2TableMap, zb2Self, fixTableMap, this.context, dataExchangeTask);
            this.LOGGER.info("\u4fdd\u5b58\u56fa\u5b9a\u8868\u6570\u636e\u5230\u4e2d\u95f4\u5e93\u5b8c\u6210\uff0c\u56fa\u5b9a\u8868\u4e2a\u6570\uff1a{}\u4e2a", (Object)fixTableMap.size());
        }
        if (!floatTableMap.isEmpty()) {
            this.LOGGER.info("\u5f00\u59cb\u4fdd\u5b58\u6d6e\u52a8\u8868\u6570\u636e\u3002\u3002\u3002");
            this.saveFloatTableData(baseDataMappingTitle2Self, msBindAllZb2TableMap, floatTableMap, this.context, dataExchangeTask, field2Self);
            this.LOGGER.info("\u4fdd\u5b58\u6d6e\u52a8\u8868\u6570\u636e\u5230\u4e2d\u95f4\u5e93\u5b8c\u6210\uff0c\u6d6e\u52a8\u8868\u4e2a\u6570\uff1a{}\u4e2a", (Object)floatTableMap.size());
        }
        this.LOGGER.info("\u7b2c\u4e09\u65b9\u63a8\u9001\u7684\u6570\u636e\u4fdd\u5b58\u5230\u4e2d\u95f4\u5e93\u5b8c\u6210");
    }

    private void checkTaskCode(String taskCode, MidstoreSchemeDTO midstoreScheme) {
        String midstoreSchemeBindTaskCode = midstoreScheme.getTaskKey();
        if (!taskCode.equals(midstoreSchemeBindTaskCode)) {
            DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefineByCode(taskCode);
            if (ObjectUtils.isEmpty(taskDefine)) {
                this.LOGGER.error("\u8be5\u4efb\u52a1\u6807\u8bc6\u3010{}\u3011\u65e0\u6548", (Object)taskCode);
                throw new BusinessRuntimeException("\u8be5\u4efb\u52a1\u6807\u8bc6[" + taskCode + "]\u65e0\u6548\uff01");
            }
            String taskKey = taskDefine.getKey();
            if (!taskKey.equals(midstoreSchemeBindTaskCode)) {
                this.LOGGER.error("\u62a5\u6587\u4e2d\u7684taskCode\u3010{}\u3011\u4e0emidStoreScheme\u7ed1\u5b9a\u7684taskCode\u3010{}\u3011\u4e0d\u4e00\u81f4\uff01", (Object)taskCode, (Object)midstoreSchemeBindTaskCode);
                throw new BusinessRuntimeException("\u62a5\u6587\u4e2d\u7684taskCode\u4e0emidStoreScheme\u7ed1\u5b9a\u7684taskCode\u4e0d\u4e00\u81f4\uff01");
            }
        }
    }

    private void saveFixTableData(Map<String, BaseDataItemMapping> baseDataMappingTitle2Self, Map<String, SchemeFieldDTO> msBindAllZb2TableMap, Map<String, DEZBInfo> zb2Self, Map<String, GcMidStoreTableDataDTO> fixTableMap, MidstoreContext context, IDataExchangeTask dataExchangeTask) {
        for (Map.Entry<String, GcMidStoreTableDataDTO> entry : fixTableMap.entrySet()) {
            String tableName = entry.getKey();
            GcMidStoreTableDataDTO tableData = entry.getValue();
            List<GcMidstoreSyncFixTableDataDTO> midStoreFixTableDataDTOS = this.buildMidStoreFixTableDataDTOs(msBindAllZb2TableMap, context, tableData);
            this.saveFixTableData(baseDataMappingTitle2Self, tableName, midStoreFixTableDataDTOS, dataExchangeTask, zb2Self);
        }
    }

    private void saveFloatTableData(Map<String, BaseDataItemMapping> baseDataMappingTitle2Self, Map<String, SchemeFieldDTO> msBindAllZb2TableMap, Map<String, GcMidStoreTableDataDTO> floatTableMap, MidstoreContext context, IDataExchangeTask dataExchangeTask, Map<String, DEFieldInfo> field2Self) {
        for (Map.Entry<String, GcMidStoreTableDataDTO> entry : floatTableMap.entrySet()) {
            String tableName = entry.getKey();
            GcMidStoreTableDataDTO tableData = entry.getValue();
            List<GcMidstoreSyncFloatTableDTO> midStoreFloatTableDataDTOS = this.buildMidStoreFloatTableDataDTOs(msBindAllZb2TableMap, context, tableData);
            Set datatimes = midStoreFloatTableDataDTOS.stream().map(GcMidstoreSyncFloatTableDTO::getDataTime).collect(Collectors.toSet());
            Set unitCodes = midStoreFloatTableDataDTOS.stream().map(GcMidstoreSyncFloatTableDTO::getMdCode).collect(Collectors.toSet());
            String condition = SqlUtils.getConditionOfIdsUseOr(datatimes, (String)"DATATIME").concat(" and ").concat(SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"MDCODE"));
            try {
                dataExchangeTask.deleteData(tableName, condition);
            }
            catch (DataExchangeException e) {
                this.LOGGER.error("\u5220\u9664\u4e2d\u95f4\u5e93\u6570\u636e\u5931\u8d25\uff1a{}", (Object)tableName, (Object)e);
                throw new BusinessRuntimeException("\u5220\u9664\u4e2d\u95f4\u5e93\u6570\u636e\u5931\u8d25\uff1a" + tableName, (Throwable)e);
            }
            for (GcMidstoreSyncFloatTableDTO data : midStoreFloatTableDataDTOS) {
                this.saveFloatTableData(baseDataMappingTitle2Self, tableName, data, dataExchangeTask, field2Self);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveFloatTableData(Map<String, BaseDataItemMapping> baseDataMappingTitle2Self, String midStoreTableName, GcMidstoreSyncFloatTableDTO tableDataDTO, IDataExchangeTask dataExchangeTask, Map<String, DEFieldInfo> field2Self) {
        ArrayList<String> fieldNames = new ArrayList<String>();
        List<String> zbNames = tableDataDTO.getZbNames();
        fieldNames.add("DATATIME");
        fieldNames.add("MDCODE");
        fieldNames.addAll(zbNames);
        String tableName = midStoreTableName;
        if (!StringUtils.isEmpty((String)this.tablePrefix) && midStoreTableName.startsWith(this.tablePrefix + "_")) {
            tableName = midStoreTableName.substring(this.tablePrefix.length() + 1, midStoreTableName.length());
        }
        try (IDataWriter tableWriter = dataExchangeTask.createTableWriter(midStoreTableName, fieldNames);){
            List<Object> zbValues = tableDataDTO.getZbValues();
            Object[] dataRow = new Object[zbNames.size() + 2];
            dataRow[0] = tableDataDTO.getDataTime();
            dataRow[1] = tableDataDTO.getMdCode();
            for (int j = 0; j < zbValues.size(); ++j) {
                if (Objects.isNull(zbValues.get(j))) {
                    this.LOGGER.info("\u7269\u7406\u8868[{}]-->\u6307\u6807[{}]\u7684\u6570\u636e\u4e3anull", (Object)tableName, (Object)zbNames.get(j));
                    continue;
                }
                String jqZbName = zbNames.get(j);
                Object value = this.handleDataType(null, field2Self, jqZbName, zbValues.get(j));
                dataRow[j + 2] = value = this.handleBaseDataName2Code(tableName, jqZbName, value, baseDataMappingTitle2Self);
            }
            tableWriter.insert(dataRow);
        }
        catch (DataExchangeException e) {
            this.LOGGER.error("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u6d6e\u52a8\u8868\u4fdd\u5b58\u6570\u636e\u5931\u8d25\uff0c\u8868\u540d\uff1a" + midStoreTableName, e);
            throw new BusinessRuntimeException("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u6d6e\u52a8\u8868\u4fdd\u5b58\u6570\u636e\u5931\u8d25\uff0c\u8868\u540d\uff1a" + midStoreTableName, (Throwable)e);
        }
        this.LOGGER.info("\u4e2d\u95f4\u5e93-\u6d6e\u52a8\u8868\u3010{}\u3011\u4fdd\u5b58\u6570\u636e\u6210\u529f\u3002", (Object)midStoreTableName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveFixTableData(Map<String, BaseDataItemMapping> baseDataMappingTitle2Self, String tableName, List<GcMidstoreSyncFixTableDataDTO> midStoreFixTableDataDTOS, IDataExchangeTask dataExchangeTask, Map<String, DEZBInfo> zb2Self) {
        Set datatimeList = midStoreFixTableDataDTOS.stream().map(GcMidstoreSyncFixTableDataDTO::getDataTime).collect(Collectors.toSet());
        Set unitCodeList = midStoreFixTableDataDTOS.stream().map(GcMidstoreSyncFixTableDataDTO::getMdCode).collect(Collectors.toSet());
        Set zbNameList = midStoreFixTableDataDTOS.stream().map(GcMidstoreSyncFixTableDataDTO::getZbName).collect(Collectors.toSet());
        String condition = SqlUtils.getConditionOfIdsUseOr(datatimeList, (String)"DATATIME").concat(" and ").concat(SqlUtils.getConditionOfIdsUseOr(unitCodeList, (String)"MDCODE")).concat(" and ").concat(SqlUtils.getConditionOfIdsUseOr(zbNameList, (String)ZBNAME));
        try {
            dataExchangeTask.deleteData(this.midStoreFixTableName, condition);
        }
        catch (DataExchangeException e) {
            this.LOGGER.error("\u5220\u9664\u4e2d\u95f4\u5e93\u6570\u636e\u5931\u8d25\uff1a{}", (Object)this.midStoreFixTableName, (Object)e);
            throw new BusinessRuntimeException("\u5220\u9664\u4e2d\u95f4\u5e93\u6570\u636e\u5931\u8d25\uff1a" + this.midStoreFixTableName, (Throwable)e);
        }
        try (IZBWriter zbWriter = dataExchangeTask.createZBWriter(this.midStoreFixTableName);){
            for (GcMidstoreSyncFixTableDataDTO data : midStoreFixTableDataDTOS) {
                if (Objects.isNull(data.getZbValue())) {
                    this.LOGGER.info("\u7269\u7406\u8868[{}]-->\u6307\u6807[{}]\u7684\u6570\u636e\u4e3anull\uff0c\u4e0d\u4fdd\u5b58\u8be5\u6570\u636e\u3002", (Object)tableName, (Object)data.getZbName());
                    continue;
                }
                String externalZbName = data.getZbName();
                Object value = this.handleDataType(zb2Self, null, externalZbName, data.getZbValue());
                value = this.handleBaseDataName2Code(tableName, externalZbName, value, baseDataMappingTitle2Self);
                zbWriter.insert(data.getDataTime(), data.getMdCode(), externalZbName, value);
            }
        }
        catch (DataExchangeException | SQLException e) {
            this.LOGGER.error("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u56fa\u5b9a\u8868\u4fdd\u5b58\u6570\u636e\u5931\u8d25\uff0c\u8868\u540d\uff1a" + this.midStoreFixTableName, e);
            throw new BusinessRuntimeException("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u56fa\u5b9a\u8868\u4fdd\u5b58\u6570\u636e\u5931\u8d25\uff0c\u8868\u540d\uff1a" + this.midStoreFixTableName, e);
        }
        this.LOGGER.info("\u4e2d\u95f4\u5e93-\u56fa\u5b9a\u8868[{}]-\u7269\u7406\u8868[{}]\u4fdd\u5b58\u6570\u636e\u6210\u529f\u3002", (Object)this.midStoreFixTableName, (Object)tableName);
    }

    private List<GcMidstoreSyncFixTableDataDTO> buildMidStoreFixTableDataDTOs(Map<String, SchemeFieldDTO> msBindAllZb2TableMap, MidstoreContext context, GcMidStoreTableDataDTO gcMidStoreTableDataDTO) {
        ArrayList<GcMidstoreSyncFixTableDataDTO> midStoreFixTableDataDTOS = new ArrayList<GcMidstoreSyncFixTableDataDTO>();
        List zbNames = gcMidStoreTableDataDTO.getZbNames();
        List zbValues = gcMidStoreTableDataDTO.getZbValues();
        int mdcodeIndex = zbNames.indexOf(MDCODE);
        int periodIndex = zbNames.indexOf(PERIOD);
        int yearIndex = zbNames.indexOf(YEAR);
        Map<String, List<List<Object>>> period2ZbValueMap = this.buildTableDataGroupByPeriod(periodIndex, yearIndex, zbNames, zbValues);
        for (Map.Entry<String, List<List<Object>>> entry : period2ZbValueMap.entrySet()) {
            List<List<Object>> zbVals = entry.getValue();
            String year = ConverterUtils.getAsString((Object)zbVals.get(0).get(yearIndex));
            String period = ConverterUtils.getAsString((Object)zbVals.get(0).get(periodIndex));
            PeriodWrapper pw = new PeriodWrapper(Integer.parseInt(year), PeriodConsts.titleToType((String)context.getTaskDefine().getDateTime()), Integer.parseInt(period));
            String periodStr = pw.toString();
            String dePeriodFromNr = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), periodStr);
            for (List<Object> vals : zbVals) {
                String mdcode = ConverterUtils.getAsString((Object)vals.get(mdcodeIndex));
                for (int i = 0; i < zbNames.size(); ++i) {
                    if (i == mdcodeIndex || i == periodIndex || i == yearIndex) continue;
                    GcMidstoreSyncFixTableDataDTO midStoreData = new GcMidstoreSyncFixTableDataDTO();
                    midStoreData.setDataTime(dePeriodFromNr);
                    midStoreData.setMdCode(mdcode);
                    String zbName = (String)zbNames.get(i);
                    if (msBindAllZb2TableMap.containsKey(zbName)) {
                        SchemeFieldDTO schemeFieldDTO = msBindAllZb2TableMap.get(zbName);
                        midStoreData.setZbName(schemeFieldDTO.getCode());
                    } else {
                        midStoreData.setZbName(zbName);
                    }
                    midStoreData.setZbValue(vals.get(i));
                    midStoreFixTableDataDTOS.add(midStoreData);
                }
            }
        }
        return midStoreFixTableDataDTOS;
    }

    private List<GcMidstoreSyncFloatTableDTO> buildMidStoreFloatTableDataDTOs(Map<String, SchemeFieldDTO> msBindAllZb2TableMap, MidstoreContext context, GcMidStoreTableDataDTO gcMidStoreTableDataDTO) {
        List zbNames = gcMidStoreTableDataDTO.getZbNames();
        int mdcodeIndex = zbNames.indexOf(MDCODE);
        int yearIndex = zbNames.indexOf(YEAR);
        int periodIndex = zbNames.indexOf(PERIOD);
        List zbValues = gcMidStoreTableDataDTO.getZbValues();
        ArrayList<GcMidstoreSyncFloatTableDTO> midStoreFloatTableDataDTOS = new ArrayList<GcMidstoreSyncFloatTableDTO>();
        Map<String, List<List<Object>>> period2ZbValueMap = this.buildTableDataGroupByPeriod(periodIndex, yearIndex, zbNames, zbValues);
        for (Map.Entry<String, List<List<Object>>> entry : period2ZbValueMap.entrySet()) {
            List<List<Object>> values = entry.getValue();
            String year = ConverterUtils.getAsString((Object)values.get(0).get(yearIndex));
            String period = ConverterUtils.getAsString((Object)values.get(0).get(periodIndex));
            PeriodWrapper pw = new PeriodWrapper(Integer.parseInt(year), PeriodConsts.titleToType((String)context.getTaskDefine().getDateTime()), Integer.parseInt(period));
            String periodStr = pw.toString();
            String dePeriodFromNr = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), periodStr);
            for (List zbVals : zbValues) {
                String mdcode = ConverterUtils.getAsString(zbVals.get(mdcodeIndex));
                GcMidstoreSyncFloatTableDTO midStoreData = new GcMidstoreSyncFloatTableDTO();
                midStoreData.setDataTime(dePeriodFromNr);
                midStoreData.setMdCode(mdcode);
                midStoreData.setZbNames(IntStream.range(0, zbNames.size()).filter(i -> i != mdcodeIndex && i != periodIndex && i != yearIndex).mapToObj(i -> {
                    SchemeFieldDTO schemeFieldDTO = (SchemeFieldDTO)msBindAllZb2TableMap.get(zbNames.get(i));
                    return schemeFieldDTO.getCode();
                }).collect(Collectors.toList()));
                midStoreData.setZbValues(IntStream.range(0, zbVals.size()).filter(i -> i != mdcodeIndex && i != periodIndex && i != yearIndex).mapToObj(zbVals::get).collect(Collectors.toList()));
                midStoreFloatTableDataDTOS.add(midStoreData);
            }
        }
        return midStoreFloatTableDataDTOS;
    }

    private Map<String, List<List<Object>>> buildTableDataGroupByPeriod(int periodIndex, int yearIndex, List<String> zbNames, List<List<Object>> zbValues) {
        HashMap<String, List<List<Object>>> period2ZbValueMap = new HashMap<String, List<List<Object>>>();
        for (List<Object> zbVals : zbValues) {
            List<List<Object>> lists;
            String year = ConverterUtils.getAsString((Object)zbVals.get(yearIndex));
            String period = ConverterUtils.getAsString((Object)zbVals.get(periodIndex));
            String yearAndPeriod = year + period;
            if (period2ZbValueMap.containsKey(yearAndPeriod)) {
                lists = (List)period2ZbValueMap.get(yearAndPeriod);
                lists.add(zbVals);
                period2ZbValueMap.put(yearAndPeriod, lists);
                continue;
            }
            lists = new ArrayList();
            lists.add(zbVals);
            period2ZbValueMap.put(yearAndPeriod, lists);
        }
        return period2ZbValueMap;
    }

    private void checkTableDataInfo(Map<String, String> externalZbName2JqZbNameMap, Map<String, SchemeFieldDTO> msBindAllZb2TableMap, GcMidStoreTableDataDTO tableDataDTO, Map<String, DEFieldInfo> field2Self, Map<String, GcMidStoreTableDataDTO> fixTableMap, Map<String, GcMidStoreTableDataDTO> floatTableMap) {
        String tableName = "";
        List zbNames = tableDataDTO.getZbNames();
        HashSet<String> tableNameSet = new HashSet<String>();
        boolean isFixtable = true;
        for (String zbName : zbNames) {
            if (MDCODE.equals(zbName) || YEAR.equals(zbName) || PERIOD.equals(zbName)) continue;
            String tableAndZbName = zbName;
            if (!externalZbName2JqZbNameMap.isEmpty() && externalZbName2JqZbNameMap.containsKey(zbName)) {
                tableAndZbName = externalZbName2JqZbNameMap.get(zbName);
            }
            if (!msBindAllZb2TableMap.containsKey(tableAndZbName)) {
                this.LOGGER.error("\u4e2d\u95f4\u5e93\u65b9\u6848\u4e2d\u672a\u914d\u7f6e\u8be5\u6307\u6807\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e\uff0c\u7b2c\u4e09\u65b9\u6307\u6807[" + zbName + "]");
                throw new BusinessRuntimeException("\u4e2d\u95f4\u5e93\u65b9\u6848\u4e2d\u672a\u914d\u7f6e\u8be5\u6307\u6807\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e\uff0c\u7b2c\u4e09\u65b9\u6307\u6807[" + zbName + "]");
            }
            SchemeFieldDTO schemeFieldDTO = msBindAllZb2TableMap.get(tableAndZbName);
            String jqZbName = schemeFieldDTO.getCode();
            tableName = ConverterUtils.getAsString(schemeFieldDTO.getValues().get("DataTable"));
            tableNameSet.add(tableName);
            this.LOGGER.info("\u7b2c\u4e09\u65b9\u6307\u6807[{}]-->\u4e45\u5176\u6307\u6807[{}]-->\u7269\u7406\u8868[{}]", zbName, jqZbName, tableName);
            if (!field2Self.containsKey(jqZbName)) continue;
            isFixtable = false;
        }
        if (tableNameSet.size() > 1) {
            this.LOGGER.error("\u62a5\u6587\u4e2d[{}]\u7684\u6307\u6807\u4e0d\u5728\u4e00\u5f20\u7269\u7406\u8868\u4e2d", (Object)String.join((CharSequence)",", zbNames));
            throw new BusinessRuntimeException("\u62a5\u6587\u4e2d[" + String.join((CharSequence)",", zbNames) + "]\u7684\u6307\u6807\u4e0d\u5728\u4e00\u5f20\u7269\u7406\u8868\u4e2d");
        }
        if (StringUtils.isEmpty((String)tableName)) {
            this.LOGGER.error("\u65e0\u6cd5\u6839\u636e\u6307\u6807\u627e\u5230\u7269\u7406\u8868\uff0c\u5bf9\u5e94\u4e8e\u62a5\u6587\u4e2d\u7684[{}]", (Object)String.join((CharSequence)",", zbNames));
            throw new BusinessRuntimeException("\u65e0\u6cd5\u6839\u636e\u6307\u6807\u627e\u5230\u7269\u7406\u8868\uff0c\u5bf9\u5e94\u4e8e\u62a5\u6587\u4e2d\u7684 " + String.join((CharSequence)",", zbNames));
        }
        if (isFixtable) {
            fixTableMap.put(tableName, tableDataDTO);
        } else {
            floatTableMap.put(tableName, tableDataDTO);
        }
    }

    private IDataExchangeTask buildiDataExchangeTask(MidstoreContext context) {
        try {
            return this.exchangeTaskService.getExchangeTask(context);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u521b\u5efadataExchangeTask\u5931\u8d25" + e);
        }
    }

    private MidstoreContext getContext(MidstoreSchemeDTO midstoreSchemeDTO) {
        return this.getContext(midstoreSchemeDTO, new AsyncTaskMonitor(){

            public String getTaskId() {
                return null;
            }

            public String getTaskPoolTask() {
                return null;
            }

            public void progressAndMessage(double progress, String message) {
            }

            public boolean isCancel() {
                return false;
            }

            public void finish(String result, Object detail) {
            }

            public void canceling(String result, Object detail) {
            }

            public void canceled(String result, Object detail) {
            }

            public void error(String result, Throwable t) {
            }

            public boolean isFinish() {
                return false;
            }
        });
    }

    private MidstoreContext getContext(MidstoreSchemeDTO midstoreSchemeDTO, AsyncTaskMonitor monitor) {
        MidstoreContext context = new MidstoreContext();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(midstoreSchemeDTO.getTaskKey());
        context.setTaskDefine(taskDefine);
        context.setSchemeKey(midstoreSchemeDTO.getKey());
        context.setAsyncMonitor(monitor);
        context.setMidstoreScheme(midstoreSchemeDTO);
        context.setSchemeInfo(this.schemeInfoService.getBySchemeKey(midstoreSchemeDTO.getKey()));
        return context;
    }

    private Object handleDataType(Map<String, DEZBInfo> zbInfoMap, Map<String, DEFieldInfo> field2Self, String code, Object value) {
        DEDataType dataType = null;
        if (zbInfoMap != null) {
            DEZBInfo dezbInfo = zbInfoMap.get(code);
            if (Objects.isNull(dezbInfo)) {
                return null;
            }
            dataType = dezbInfo.getDataType();
        } else if (field2Self != null) {
            DEFieldInfo deFieldInfo = field2Self.get(code);
            if (Objects.isNull(deFieldInfo)) {
                return null;
            }
            dataType = deFieldInfo.getDataType();
        }
        if (DEDataType.FLOAT.equals(dataType) || DEDataType.INTEGER.equals(dataType) || DEDataType.LONG.equals((Object)dataType)) {
            return Double.parseDouble(value.toString());
        }
        if (DEDataType.STRING.equals((Object)dataType) || DEDataType.CLOB.equals((Object)dataType) || DEDataType.BLOB.equals((Object)dataType)) {
            return value.toString();
        }
        this.LOGGER.error("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + dataType.name());
        throw new BusinessRuntimeException("\u4ece\u7b2c\u4e09\u65b9\u7cfb\u7edf\u53d6\u6570\u63a5\u53e3\u5f02\u5e38\uff1a\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + dataType.name());
    }

    private Object handleBaseDataName2Code(String nrTableCode, String nrZBCode, Object value, Map<String, BaseDataItemMapping> baseDataMappingTitle2Self) {
        BaseDataItemMapping baseDataItemMapping;
        if (Objects.isNull(value)) {
            return value;
        }
        String valueStr = ConverterUtils.getAsString((Object)value);
        if (!StringUtils.isEmpty((String)this.tablePrefix) && nrTableCode.startsWith(this.tablePrefix + "_")) {
            nrTableCode = nrTableCode.substring(this.tablePrefix.length() + 1, nrTableCode.length());
        }
        if (baseDataMappingTitle2Self.containsKey(valueStr) && (baseDataItemMapping = baseDataMappingTitle2Self.get(valueStr)) != null) {
            this.LOGGER.info("\u7269\u7406\u8868[{}]-->\u6307\u6807[{}]\u4ece\u6620\u5c04\u65b9\u6848\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u6620\u5c04\uff0c\u6307\u6807\u503c\uff1aNAME\u3010{}\u3011--->CODE\u3010{}\u3011", nrTableCode, nrZBCode, value, baseDataItemMapping.getBaseItemCode());
            return baseDataItemMapping.getBaseItemCode();
        }
        DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
        List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        for (DataField dataField : dataFieldList) {
            if (StringUtils.isEmpty((String)dataField.getRefDataEntityKey())) continue;
            String baseDataCode = dataField.getRefDataEntityKey();
            baseDataCode = EntityUtils.getId((String)baseDataCode);
            nrFieldMapBaseDatas.put(dataField.getCode(), baseDataCode);
        }
        if (nrFieldMapBaseDatas.containsKey(nrZBCode)) {
            String baseDataCode = (String)nrFieldMapBaseDatas.get(nrZBCode);
            List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(baseDataCode);
            if (!CollectionUtils.isEmpty((Collection)baseData)) {
                Map<String, String> title2CodeMap = baseData.stream().collect(Collectors.toMap(GcBaseData::getTitle, GcBaseData::getCode, (k1, k2) -> k1));
                if (title2CodeMap.containsKey(valueStr)) {
                    this.LOGGER.info("\u7269\u7406\u8868[{}]-->\u6307\u6807[{}]\u5173\u8054\u4e86\u57fa\u7840\u6570\u636e\uff0c\u6307\u6807\u503c\uff1aNAME\u3010{}\u3011--->CODE\u3010{}\u3011", nrTableCode, nrZBCode, value, title2CodeMap.get(valueStr));
                    return title2CodeMap.get(valueStr);
                }
                this.LOGGER.error("\u6307\u6807[" + nrZBCode + "]\u5173\u8054\u7684\u57fa\u7840\u6570\u636e[" + baseDataCode + "]\u4e2d\u672a\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u503c\u662f\u5426\u6b63\u786e\uff1a" + valueStr);
                throw new BusinessRuntimeException("\u6307\u6807[" + nrZBCode + "]\u5173\u8054\u7684\u57fa\u7840\u6570\u636e[" + baseDataCode + "]\u4e2d\u672a\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u503c\u662f\u5426\u6b63\u786e\uff1a" + valueStr);
            }
        }
        this.LOGGER.info("\u7269\u7406\u8868[{}]-->\u6307\u6807[{}]\u672a\u5173\u8054\u57fa\u7840\u6570\u636e\uff0c\u6307\u6807\u503c\uff1a{}", nrTableCode, nrZBCode, valueStr);
        return value;
    }
}

