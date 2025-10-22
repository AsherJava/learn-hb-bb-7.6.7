/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  javax.annotation.Resource
 */
package nr.midstore.core.internal.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import nr.midstore.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.dataset.MidstoreTableData;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.internal.dataset.AbstractMidstoreDataSet;
import nr.midstore.core.internal.dataset.MidstoreDataSet;
import nr.midstore.core.internal.dataset.tz.MidstoreSBDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MidstoreBatchImportDataServiceImpl
implements IMidstoreBatchImportDataService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreBatchImportDataServiceImpl.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private DataSource dataSoure;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public Map<String, DimensionValue> getNewDimensionSet(Map<String, DimensionValue> dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (String code : dimensionValueSet.keySet()) {
            DimensionValue value = new DimensionValue();
            value.setName(code);
            value.setValue(dimensionValueSet.get(code) != null ? dimensionValueSet.get(code).getValue() : "");
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    @Override
    public IMidstoreDataSet getImportBatchRegionDataSet(MidsotreTableContext tableContext, String TaskKey, String tableKey, List<String> fieldsArr) {
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        for (String field : fieldsArr) {
            ExportFieldDefine efd = new ExportFieldDefine();
            efd.setCode(field);
            if (StringUtils.isNotEmpty((String)field) && field.contains(".")) {
                int id = field.indexOf(".");
                String fieldCode = field.substring(id + ".".length(), field.length());
                efd.setCode(fieldCode);
            }
            fields.add(efd);
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        MidstoreTableData regionData = new MidstoreTableData();
        regionData.setDataTableKey(tableKey);
        regionData.setKey(tableKey);
        regionData.setTablekey(tableKey);
        regionData.setTaskKey(TaskKey);
        regionData.setType(dataTable.getDataTableType().getValue());
        AbstractMidstoreDataSet regionDataSet = null;
        regionDataSet = null;
        regionDataSet = dataTable != null && dataTable.getDataTableType() == DataTableType.ACCOUNT ? new MidstoreSBDataSet(tableContext, regionData, fields) : new MidstoreDataSet(tableContext, regionData, fields);
        return regionDataSet;
    }

    @Override
    public IMidstoreDataSet getBatchExportRegionDataSet(MidsotreTableContext tableContext, String TaskKey, String tableKey) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        MidstoreTableData regionData = new MidstoreTableData();
        regionData.setDataTableKey(tableKey);
        regionData.setKey(tableKey);
        regionData.setTablekey(TaskKey);
        regionData.setTaskKey(TaskKey);
        regionData.setType(dataTable.getDataTableType().getValue());
        MidstoreDataSet regionDataSet = new MidstoreDataSet(tableContext, regionData);
        return regionDataSet;
    }

    @Override
    public void openTempTable(MidstoreContext importContext, List<String> corpKeys) {
        try {
            int dataType = DataTypesConvert.fieldTypeToDataType((FieldType)FieldType.FIELD_TYPE_STRING);
            TempAssistantTable tempTable = new TempAssistantTable(corpKeys, dataType);
            tempTable.createTempTable();
            tempTable.insertIntoTempTable();
            importContext.getIntfObjects().put("FMDMtempTable", tempTable);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void closeTempTable(MidstoreContext importContext) {
        TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get("FMDMtempTable");
        if (tempTable != null) {
            try {
                tempTable.close();
                importContext.getIntfObjects().remove("FMDMtempTable");
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void setTempTable(MidstoreContext importContext, MidsotreTableContext tableContext) {
        TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get("FMDMtempTable");
        if (tempTable != null) {
            tableContext.setTempAssistantTable(importContext.getEntityTypeName(), tempTable);
        }
    }

    @Override
    public IRunTimeViewController getViewController() {
        return this.viewController;
    }

    @Override
    public MidsotreTableContext getTableContext(Map<String, DimensionValue> dimSetMap, String taskKey, String dataSchemeKey, String tableKey, String syncTaskID) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        if (null != dimSetMap) {
            Map<String, String> uCache = this.getStringKey();
            for (String key : dimSetMap.keySet()) {
                try {
                    String value = null;
                    ArrayList<String> ids = null;
                    DimensionValue dimValue = dimSetMap.get(key);
                    String dimensionValue = dimValue.getValue();
                    if (dimensionValue instanceof List) {
                        ids = (ArrayList<String>)((Object)dimensionValue);
                        for (String code : ids) {
                            uCache.put(code, code);
                        }
                    } else if (dimensionValue instanceof String) {
                        String vCode = dimValue.getValue();
                        if (uCache.containsKey(vCode)) {
                            value = uCache.get(vCode);
                        } else {
                            String[] codes = vCode.split(",");
                            if (codes.length > 1) {
                                ids = new ArrayList<String>();
                                for (String code : codes) {
                                    ids.add(code);
                                    uCache.put(code, code);
                                }
                            } else {
                                uCache.put(vCode, vCode);
                            }
                        }
                    }
                    if (null != value) {
                        dimensionSet.setValue(key, value);
                        continue;
                    }
                    if (ids != null) {
                        dimensionSet.setValue(key, (Object)ids);
                        continue;
                    }
                    dimensionSet.setValue(key, (Object)dimValue.getValue());
                }
                catch (Exception e) {
                    dimensionSet.setValue(key, (Object)dimSetMap.get(key).getValue());
                    logger.error(e.getMessage(), e);
                }
            }
        }
        MidsotreTableContext context = new MidsotreTableContext(taskKey, dataSchemeKey, tableKey, dimensionSet, OptTypes.FORM, "", "", syncTaskID);
        context.setAttachment(false);
        context.setAttachmentArea("JTABLEAREA");
        context.setExpEntryFields(ExpViewFields.valueOf((String)"code".toUpperCase()));
        context.setExpEnumFields(ExpViewFields.valueOf((String)"code".toUpperCase()));
        return context;
    }

    private Map<String, String> getStringKey() {
        return new HashMap<String, String>();
    }
}

