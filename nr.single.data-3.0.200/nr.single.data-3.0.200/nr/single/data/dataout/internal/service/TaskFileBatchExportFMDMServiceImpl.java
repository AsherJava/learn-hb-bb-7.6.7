/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureData
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureFieldValueConvert
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureService
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datacrud.spi.TypeFormatStrategy
 *  com.jiuqi.nr.datacrud.util.TypeStrategyUtil
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.data.DateData
 *  com.jiuqi.nr.entity.engine.data.DateTimeData
 *  com.jiuqi.nr.entity.engine.data.TimeData
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.SingleOrderUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 *  nr.single.map.data.internal.service.TaskDataServiceNewImpl
 */
package nr.single.data.dataout.internal.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureFieldValueConvert;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DateData;
import com.jiuqi.nr.entity.engine.data.DateTimeData;
import com.jiuqi.nr.entity.engine.data.TimeData;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nr.single.data.dataout.service.ITaskFileBatchExportFMDMService;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.SingleOrderUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import nr.single.map.data.internal.service.TaskDataServiceNewImpl;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileBatchExportFMDMServiceImpl
implements ITaskFileBatchExportFMDMService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileBatchExportFMDMServiceImpl.class);
    @Autowired
    private ITaskFileExportDataService exportDataSevice;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private MeasureService measureService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private DataValueFormatterBuilderFactory formatterBuilderFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchOperRegionData(TaskDataContext context, String filePath, String netformKey, String netFormCode, Map<String, DimensionValue> dimMap, RegionData regionData, SingleFileTableInfo table, SingleFileRegionInfo singleRegion) throws Exception {
        List regions;
        ArrayList<FieldData> netFieldList = new ArrayList<FieldData>();
        SingleFileFmdmInfo fmTable = null;
        boolean isFMDM = true;
        int bizOrderField = -1;
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(context.getDwEntityId());
        fmdmAttributeDTO.setFormSchemeKey(context.getFormSchemeKey());
        List fieldList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        List dataLinks = this.runTimeViewController.getAllLinksInForm(netformKey);
        HashMap<String, DataLinkDefine> dataLinkFieldMap = new HashMap<String, DataLinkDefine>();
        for (DataLinkDefine link : dataLinks) {
            if (link.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                dataLinkFieldMap.put(link.getLinkExpression(), link);
                continue;
            }
            if (link.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
                dataLinkFieldMap.put(link.getLinkExpression(), link);
                continue;
            }
            if (link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                dataLinkFieldMap.put(link.getLinkExpression(), link);
                continue;
            }
            if (link.getType() != DataLinkType.DATA_LINK_TYPE_INFO) continue;
            dataLinkFieldMap.put(link.getLinkExpression(), link);
        }
        for (IFMDMAttribute netfield : fieldList) {
            FieldData netFieldData = new FieldData();
            String fieldCode = netfield.getCode();
            String tableName = netFormCode;
            int idPos = fieldCode.indexOf(".");
            if (idPos >= 0) {
                tableName = fieldCode.substring(0, idPos);
                fieldCode = fieldCode.substring(idPos + 1, fieldCode.length());
            } else if (StringUtils.isEmpty((String)netfield.getEntityId())) {
                TableModelDefine tableModel;
                ColumnModelDefine columnModel;
                String tableId = netfield.getTableID();
                if (StringUtils.isEmpty((String)tableId) && StringUtils.isNotEmpty((String)netfield.getID()) && (columnModel = this.dataModelService.getColumnModelDefineByID(netfield.getID())) != null) {
                    tableId = columnModel.getTableID();
                }
                if (StringUtils.isNotEmpty((String)tableId) && (tableModel = this.dataModelService.getTableModelDefineById(tableId)) != null) {
                    tableName = tableModel.getCode();
                }
            } else {
                tableName = EntityUtils.getId((String)context.getDwEntityId());
            }
            netFieldData.setFieldCode(fieldCode);
            netFieldData.setFieldName(fieldCode);
            netFieldData.setTableName(tableName);
            netFieldData.setFieldType(this.getFieldTypeByColumType(netfield.getColumnType()).getValue());
            if (regionData != null) {
                netFieldData.setRegionKey(regionData.getKey());
            }
            if (StringUtils.isNotEmpty((String)netfield.getZBKey()) && dataLinkFieldMap.containsKey(netfield.getZBKey())) {
                netFieldData.setDataLinkKey(((DataLinkDefine)dataLinkFieldMap.get(netfield.getZBKey())).getKey());
            }
            netFieldList.add(netFieldData);
        }
        DimensionValue unitDim = dimMap.get(context.getEntityCompanyType());
        String unitCodes = unitDim.getValue();
        List<String> unitList = null;
        if (StringUtils.isNotEmpty((String)unitCodes)) {
            String[] unitArrays = unitCodes.split(",");
            unitList = Arrays.asList(unitArrays);
        }
        FMDMDataDTO queryParam = new FMDMDataDTO();
        queryParam.setFormSchemeKey(context.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)context.getNetPeriodCode());
        dimensionValueSet.setValue(context.getEntityCompanyType(), unitList);
        if (context.getOtherDims().size() > 0) {
            for (DimensionValue dim : context.getOtherDims().values()) {
                dimensionValueSet.setValue(dim.getName(), (Object)dim.getValue());
            }
        }
        if (context.getDimEntityCache().getEntitySingleDims().size() > 0) {
            for (String dimName : context.getDimEntityCache().getEntitySingleDims()) {
                dimensionValueSet.setValue(dimName, null);
            }
        }
        queryParam.setDimensionValueSet(dimensionValueSet);
        queryParam.setSortedByQuery(false);
        queryParam.setFilter(true);
        DataValueFormatter valueFormatter = null;
        if (StringUtils.isNotEmpty((String)context.getMeasureCode()) && (regions = this.runTimeViewController.getAllRegionsInForm(netformKey)) != null && !regions.isEmpty()) {
            RegionRelation relation = this.regionRelationFactory.getRegionRelation(((DataRegionDefine)regions.get(0)).getKey());
            ExecutorContext context1 = this.executorContextFactory.getExecutorContext((ParamRelation)relation, dimensionValueSet);
            this.measureBalance(context1, context.getMeasureKey(), context.getMeasureCode(), relation);
            queryParam.setContext((IContext)context1);
            if (StringUtils.isNotEmpty((String)context.getMeasureDecimal())) {
                DataValueFormatterBuilder formatBuilder = this.getFormatBuilder(context.getMeasureKey(), context.getMeasureCode(), context.getMeasureDecimal());
                valueFormatter = formatBuilder.build();
            }
        }
        List queryRes = this.fmdmDataService.list(queryParam);
        HashMap<String, IFMDMData> dataMap = new HashMap<String, IFMDMData>();
        if (queryRes != null && queryRes.size() > 0) {
            SingleFileFieldInfo field;
            ReportRegionDataSetList olddataSetList;
            for (IFMDMData data : queryRes) {
                dataMap.put(data.getValue("CODE").getAsString(), data);
            }
            HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
            HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
            int floatingRow = regionData.getRegionTop();
            if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                floatingRow = -1;
            }
            if (null != singleRegion) {
                for (SingleFileFieldInfo field2 : singleRegion.getFields()) {
                    mapNetFieldList.put(field2.getNetTableCode() + "." + field2.getNetFieldCode(), field2);
                    mapSingleFieldList.put(field2.getTableCode() + "." + field2.getFieldCode(), field2);
                }
            }
            ReportRegionDataSetList dataSetList = olddataSetList = TaskFileDataOperateUtil.getRegionDataSetList(context, filePath, netFormCode, netFieldList, floatingRow, netFieldList.size() * queryRes.size() > 1000000);
            if (olddataSetList.isVirtualFloat()) {
                dataSetList = olddataSetList.getVirtualDatasets();
                mapNetFieldList.clear();
                mapSingleFieldList.clear();
                for (Object obj : dataSetList.getFieldMap().values()) {
                    field = (SingleFileFieldInfo)obj;
                    mapNetFieldList.put(field.getNetTableCode() + "." + field.getNetFieldCode(), field);
                    mapSingleFieldList.put(field.getTableCode() + "." + field.getFieldCode(), field);
                }
            }
            try {
                boolean hasData;
                String zdm;
                HashMap<String, String> fieldValueMap = new HashMap<String, String>();
                Map unitFieldMap = null;
                if (isFMDM) {
                    fmTable = (SingleFileFmdmInfo)table;
                    for (String code : fmTable.getZdmFieldCodes()) {
                        fieldValueMap.put(code, "");
                    }
                    if (mapSingleFieldList.containsKey(fmTable.getPeriodField()) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                        field = (SingleFileFieldInfo)mapSingleFieldList.get(fmTable.getPeriodField());
                        String fieldValue = context.getNetPeriodCode();
                        TaskDataServiceNewImpl taskService2 = new TaskDataServiceNewImpl();
                        String newFieldValue = taskService2.getSinglePeriodCode(context, context.getNetPeriodCode(), field.getFieldSize());
                        fieldValue = StringUtils.isEmpty((String)newFieldValue) ? fieldValue.substring(fieldValue.length() - field.getFieldSize(), fieldValue.length()) : newFieldValue;
                        fieldValueMap.put(field.getFieldCode(), fieldValue);
                        context.setMapCurrentPeriod(fieldValue);
                    }
                }
                boolean hasMapFields = true;
                if (dataSetList == null || dataSetList.getDataSetList().size() <= 0) {
                    hasMapFields = false;
                    if (isFMDM) {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u65e0\u6620\u5c04\u7684\u5355\u673a\u7248\u6307\u6807");
                        dataSetList = TaskFileDataOperateUtil.getRegionDataSetListByTable(context, filePath, "FMDM", -1, dataSetList);
                    } else {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6570\u636e\u533a\u57df" + regionData.getRegionTop() + "\u65e0\u6620\u5c04\u7684\u5355\u673a\u7248\u6307\u6807,\u4e0d\u5bfc\u51fa");
                        return;
                    }
                }
                StringBuilder noMapFields = new StringBuilder();
                for (int i = 0; i < netFieldList.size(); ++i) {
                    String fieldName = ((FieldData)netFieldList.get(i)).getFieldName();
                    String fieldFlag = ((FieldData)netFieldList.get(i)).getTableName() + "." + fieldName;
                    if (dataSetList.getFieldDataSetMap().containsKey(fieldFlag) || Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equalsIgnoreCase(fieldName) || context.getEntityCompanyType().equalsIgnoreCase(fieldName)) continue;
                    noMapFields.append(fieldFlag).append(",");
                }
                if (noMapFields.length() > 0) {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6570\u636e\u533a\u57df" + regionData.getRegionTop() + "\u6307\u6807\u65e0\u6620\u5c04\uff1a" + noMapFields.toString());
                }
                CaseInsensitiveMap<String, UnitCustomMapping> singleMapingUnitList = new CaseInsensitiveMap<String, UnitCustomMapping>();
                CaseInsensitiveMap<String, UnitCustomMapping> netMapingUnitList = new CaseInsensitiveMap<String, UnitCustomMapping>();
                ArrayList<String> skipUnitKeys = new ArrayList<String>();
                if (null != context.getMapingCache().getMapConfig().getMapping() && context.getMapingCache().getMapConfig().getConfig().isConfigParentNode()) {
                    List list = context.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                    for (UnitCustomMapping unitMap : list) {
                        if (StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) && StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) {
                            skipUnitKeys.add(unitMap.getNetUnitKey());
                        } else if (StringUtils.isNotEmpty((String)unitMap.getSingleUnitCode())) {
                            if (singleMapingUnitList.containsKey(unitMap.getSingleUnitCode())) {
                                context.info(logger, "\u5bfc\u51fa\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u76ee\u6807\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getSingleUnitCode());
                                continue;
                            }
                            singleMapingUnitList.put(unitMap.getSingleUnitCode(), unitMap);
                        }
                        if (StringUtils.isNotEmpty((String)unitMap.getNetUnitKey())) {
                            if (netMapingUnitList.containsKey(unitMap.getNetUnitKey())) {
                                context.info(logger, "\u5bfc\u51fa\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u6765\u6e90\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getSingleUnitCode());
                                continue;
                            }
                            netMapingUnitList.put(unitMap.getNetUnitKey(), unitMap);
                        }
                        if (!StringUtils.isNotEmpty((String)unitMap.getSingleParentUnitCode()) || unitMap.getSingleParentUnitCode().length() == context.getEntityCache().getSingleZdmOutPeriodLen()) continue;
                        context.info(logger, "\u5bfc\u51fa\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u76ee\u6807\u5355\u4f4d\u7236\u8282\u70b9\u4ee3\u7801\u957f\u5ea6\u4e0d\u5bf9\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getSingleParentUnitCode());
                    }
                }
                HashMap<String, String> floatKeyCodes = new HashMap<String, String>();
                ArrayList<String> hasDataZdms = new ArrayList<String>();
                ArrayList<DataEntityInfo> writeEntityList = new ArrayList<DataEntityInfo>();
                for (IFMDMData fmdmData : queryRes) {
                    String fmCode = fmdmData.getValue("CODE").getAsString();
                    Iterator floatDatas2 = new ArrayList();
                    for (FieldData field3 : netFieldList) {
                        AbstractData fmValue = fmdmData.getValue(field3.getFieldCode());
                        Object fieldValue = fmValue.getAsObject();
                        if (valueFormatter != null && fieldValue != null && StringUtils.isNotEmpty((String)field3.getDataLinkKey())) {
                            try {
                                String fieldValue2 = valueFormatter.format(field3.getDataLinkKey(), fieldValue);
                                floatDatas2.add((String)fieldValue2);
                            }
                            catch (Exception e) {
                                floatDatas2.add((Object)fieldValue);
                            }
                            continue;
                        }
                        if (fmValue instanceof DateData || fmValue instanceof DateTimeData || fmValue instanceof TimeData) {
                            fieldValue = fmValue.getAsString();
                        }
                        floatDatas2.add((Object)fieldValue);
                    }
                    if (floatDatas2.size() == 0) {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                        break;
                    }
                    if (!hasMapFields) {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u65e0\u6620\u5c04\u5355\u673a\u7248\u6307\u6807\uff0c\u8bf7\u68c0\u67e5");
                        break;
                    }
                    Object[] floatDatas = floatDatas2.toArray();
                    ArrayList<FieldData> fields = netFieldList;
                    zdm = null;
                    String zdmKey = null;
                    String entityKey = null;
                    String entityCode = null;
                    if (StringUtils.isNotEmpty((String)fmCode)) {
                        zdmKey = fmCode;
                        DataEntityInfo entity = context.getEntityCache().findEntityByCode(zdmKey);
                        DataEntityInfo entity2 = context.getEntityCache().findEntityByKey(zdmKey);
                        if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                            zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                        } else if (null != entity) {
                            zdm = entity.getSingleZdm();
                        } else if (null != entity2) {
                            zdm = entity2.getSingleZdm();
                        }
                        context.setCurrentEntintyKey(zdmKey);
                        context.setCurrentZDM(zdm);
                        if (isFMDM) {
                            if (null != entity) {
                                entityKey = entity.getEntityKey();
                                entityCode = entity.getEntityCode();
                                writeEntityList.add(entity);
                            } else if (null != entity2) {
                                entityKey = entity2.getEntityKey();
                                entityCode = entity2.getEntityCode();
                                writeEntityList.add(entity2);
                            } else {
                                entityCode = zdmKey;
                            }
                        }
                    }
                    if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        dataSetList.locateDataRowByZdm(zdm);
                    } else {
                        dataSetList.AppendDataRowByZdm(zdm);
                    }
                    DataRow dataRow = null;
                    this.exportDataSevice.setRowValueByFields(context, fields, dataRow, floatDatas, mapNetFieldList, dataSetList);
                    this.exportDataSevice.setDataAfterExport(context, dataRow, dataSetList, zdm);
                    String floatOrder = SingleOrderUtil.newOrder6();
                    ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).setFloatOrder(floatOrder);
                    if (isFMDM) {
                        String fieldValue;
                        dataRow = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getCurDataRow();
                        unitFieldMap = context.getMapingCache().getUnitFieldMap().containsKey(zdmKey) ? (Map)context.getMapingCache().getUnitFieldMap().get(zdmKey) : new HashMap();
                        for (Object fieldName : unitFieldMap.keySet()) {
                            dataRow.setValue((String)fieldName, unitFieldMap.get(fieldName));
                        }
                        for (Object fieldName : fieldValueMap.keySet()) {
                            Object value = dataRow.getValue((String)fieldName);
                            if (null != value) {
                                fieldValue = value.toString();
                                if (context.getMapingCache().isMapConfig() && context.getMapingCache().getFmdmInfo2() != null && ((String)fieldName).equals(context.getMapingCache().getFmdmInfo2().getPeriodField()) && StringUtils.isEmpty((String)fieldValue) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                                    fieldValue = context.getMapCurrentPeriod();
                                }
                                fieldValueMap.put((String)fieldName, fieldValue);
                                continue;
                            }
                            fieldValueMap.put((String)fieldName, "");
                        }
                        DataEntityInfo entity = context.getEntityCache().findEntityByCode(zdmKey);
                        if (null == entity) {
                            entity = context.getEntityCache().findEntityByKey(zdmKey);
                        }
                        if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                            zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                        } else if (null != entity) {
                            zdm = entity.getSingleZdm();
                        } else if (fmTable != null) {
                            Object fieldName;
                            fieldName = fmTable.getZdmFieldCodes().iterator();
                            while (fieldName.hasNext()) {
                                String code = (String)fieldName.next();
                                fieldValue = (String)fieldValueMap.get(code);
                                zdm = zdm + fieldValue;
                            }
                            if (StringUtils.isNotEmpty((String)zdm) && zdm.length() > fmTable.getZdmLength()) {
                                zdm = zdm.substring(0, fmTable.getZdmLength());
                            }
                            if (!context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                                context.getEntityKeyZdmMap().put(zdmKey, zdm);
                            }
                            if (!context.getEntityZdmKeyMap().containsKey(zdm)) {
                                context.getEntityZdmKeyMap().put(zdm, zdmKey);
                            }
                        }
                        context.setCurrentZDM(zdm);
                        if (dataRow.getValue("SYS_FJD") == null || StringUtils.isEmpty((String)dataRow.getValue("SYS_FJD").toString())) {
                            UnitCustomMapping unitMap;
                            boolean fjdUseMap = false;
                            if (!netMapingUnitList.isEmpty() && netMapingUnitList.containsKey(zdmKey) && StringUtils.isNotEmpty((String)(unitMap = (UnitCustomMapping)netMapingUnitList.get(zdmKey)).getSingleParentUnitCode())) {
                                String fjdCode = unitMap.getSingleParentUnitCode();
                                fjdCode = context.getEntityCache().getNewZdmFromOutPeriod(fjdCode, context.getMapCurrentPeriod());
                                dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                fjdUseMap = true;
                            }
                            if (!fjdUseMap) {
                                Object fjdKey = null;
                                if (context.getEntityKeyParentMap().containsKey(zdmKey)) {
                                    fjdKey = (String)context.getEntityKeyParentMap().get(zdmKey);
                                } else if (entity != null) {
                                    fjdKey = entity.getEntityParentKey();
                                }
                                if (StringUtils.isNotEmpty(fjdKey)) {
                                    String fjdCode;
                                    DataEntityInfo entity2 = context.getEntityCache().findEntityByCode((String)fjdKey);
                                    if (context.getEntityKeyZdmMap().containsKey(fjdKey)) {
                                        fjdCode = (String)context.getEntityKeyZdmMap().get(fjdKey);
                                        dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                    } else if (null != entity2) {
                                        fjdCode = entity2.getSingleZdm();
                                        dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                    }
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty((String)fmTable.getDWMCField()) && (dataRow.getValue(fmTable.getDWMCField()) == null || StringUtils.isEmpty((String)dataRow.getValue(fmTable.getDWMCField()).toString())) && null != entity) {
                            dataRow.setValue(fmTable.getDWMCField(), (Object)entity.getEntityTitle());
                        }
                        if (StringUtils.isNotEmpty((String)fmTable.getPeriodField())) {
                            dataRow.setValue(fmTable.getPeriodField(), (Object)context.getMapCurrentPeriod());
                        }
                        if (StringUtils.isNotEmpty((String)zdm)) {
                            Map mapFieldvalues = context.getEntityCache().getFieldValueByZdm(zdm);
                            for (String mapfieldCode : mapFieldvalues.keySet()) {
                                String mapFieldValue = (String)mapFieldvalues.get(mapfieldCode);
                                if (!StringUtils.isNotEmpty((String)mapFieldValue) || !StringUtils.isEmpty((String)dataRow.getValueString(mapfieldCode))) continue;
                                dataRow.setValue(mapfieldCode, (Object)mapFieldValue);
                            }
                        }
                        dataRow.setValue("SYS_ZDM", (Object)zdm);
                    } else if (singleRegion.getFloatingIndex() > 0) {
                        String floatCode = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getFloatCodeValues();
                        String floatCode2 = floatCode + "," + floatOrder;
                        String rowkey = "";
                        rowkey = bizOrderField >= 0 ? floatDatas[bizOrderField].toString() : floatCode;
                        floatKeyCodes.put(rowkey, floatCode2);
                    }
                    dataSetList.setAllFieldValue("SYS_ZDM", zdm);
                    dataSetList.saveRowData();
                    hasDataZdms.add(zdm);
                }
                boolean bl = hasData = hasDataZdms.size() > 0;
                if (isFMDM) {
                    context.setSuccessCorpCount(0);
                    if (context.getDownloadEntityKeyZdmMap().size() != writeEntityList.size()) {
                        HashMap writeEntityCodes = new HashMap();
                        HashMap<String, DataEntityInfo> writeEntityKeys = new HashMap<String, DataEntityInfo>();
                        for (DataEntityInfo entity : writeEntityList) {
                            writeEntityCodes.put(entity.getEntityCode(), entity);
                            writeEntityKeys.put(entity.getEntityKey(), entity);
                        }
                        for (String zdmKey : context.getDownloadEntityKeyZdmMap().keySet()) {
                            if (writeEntityCodes.containsKey(zdmKey) || writeEntityKeys.containsKey(zdmKey)) continue;
                            DataEntityInfo entity = context.getEntityCache().findEntityByKey(zdmKey);
                            if (entity == null) {
                                entity = context.getEntityCache().findEntityByCode(zdmKey);
                            }
                            if (entity == null) continue;
                            zdm = null;
                            if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                                zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                            } else if (null != entity) {
                                zdm = entity.getSingleZdm();
                            }
                            if (StringUtils.isEmpty((String)zdm)) continue;
                            dataSetList.locateDataRowByZdm(zdm);
                            DataRow dataRow = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getCurDataRow();
                            unitFieldMap = context.getMapingCache().getUnitFieldMap().containsKey(zdmKey) ? (Map)context.getMapingCache().getUnitFieldMap().get(zdmKey) : new HashMap();
                            for (String fieldName : unitFieldMap.keySet()) {
                                dataRow.setValue(fieldName, unitFieldMap.get(fieldName));
                            }
                            for (String fieldName : fieldValueMap.keySet()) {
                                Object value = dataRow.getValue(fieldName);
                                if (null != value) {
                                    String fieldValue = value.toString();
                                    if (context.getMapingCache().isMapConfig() && context.getMapingCache().getFmdmInfo2() != null && fieldName.equals(context.getMapingCache().getFmdmInfo2().getPeriodField()) && StringUtils.isEmpty((String)fieldValue) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                                        fieldValue = context.getMapCurrentPeriod();
                                    }
                                    fieldValueMap.put(fieldName, fieldValue);
                                    continue;
                                }
                                fieldValueMap.put(fieldName, "");
                            }
                            context.setCurrentEntintyKey(zdmKey);
                            context.setCurrentZDM(zdm);
                            if (dataRow.getValue("SYS_FJD") == null || StringUtils.isEmpty((String)dataRow.getValue("SYS_FJD").toString())) {
                                UnitCustomMapping unitMap;
                                boolean fjdUseMap = false;
                                if (!netMapingUnitList.isEmpty() && netMapingUnitList.containsKey(zdmKey) && StringUtils.isNotEmpty((String)(unitMap = (UnitCustomMapping)netMapingUnitList.get(zdmKey)).getSingleParentUnitCode())) {
                                    String fjdCode = unitMap.getSingleParentUnitCode();
                                    fjdCode = context.getEntityCache().getNewZdmFromOutPeriod(fjdCode, context.getMapCurrentPeriod());
                                    dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                    fjdUseMap = true;
                                }
                                if (!fjdUseMap) {
                                    String fjdKey = null;
                                    if (context.getEntityKeyParentMap().containsKey(zdmKey)) {
                                        fjdKey = (String)context.getEntityKeyParentMap().get(zdmKey);
                                    } else if (entity != null) {
                                        fjdKey = entity.getEntityParentKey();
                                    }
                                    if (StringUtils.isNotEmpty(fjdKey)) {
                                        String fjdCode;
                                        DataEntityInfo entity2 = context.getEntityCache().findEntityByCode(fjdKey);
                                        if (context.getEntityKeyZdmMap().containsKey(fjdKey)) {
                                            fjdCode = (String)context.getEntityKeyZdmMap().get(fjdKey);
                                            dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                        } else if (null != entity2) {
                                            fjdCode = entity2.getSingleZdm();
                                            dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                        }
                                    }
                                }
                            }
                            if (StringUtils.isNotEmpty((String)fmTable.getPeriodField())) {
                                dataRow.setValue(fmTable.getPeriodField(), (Object)context.getMapCurrentPeriod());
                            }
                            if (StringUtils.isNotEmpty((String)fmTable.getDWMCField()) && (dataRow.getValue(fmTable.getDWMCField()) == null || StringUtils.isEmpty((String)dataRow.getValue(fmTable.getDWMCField()).toString())) && null != entity) {
                                dataRow.setValue(fmTable.getDWMCField(), (Object)entity.getEntityTitle());
                            }
                            if (StringUtils.isNotEmpty((String)zdm)) {
                                Map mapFieldvalues = context.getEntityCache().getFieldValueByZdm(zdm);
                                for (String mapfieldCode : mapFieldvalues.keySet()) {
                                    String mapFieldValue = (String)mapFieldvalues.get(mapfieldCode);
                                    if (!StringUtils.isNotEmpty((String)mapFieldValue) || !StringUtils.isEmpty((String)dataRow.getValueString(mapfieldCode))) continue;
                                    dataRow.setValue(mapfieldCode, (Object)mapFieldValue);
                                }
                            }
                            dataRow.setValue("SYS_ZDM", (Object)zdm);
                            dataSetList.setAllFieldValue("SYS_ZDM", zdm);
                            dataSetList.saveRowData();
                            hasData = true;
                        }
                    }
                }
                if (hasData) {
                    if (olddataSetList.isVirtualFloat()) {
                        dataSetList.saveData();
                        TaskFileDataOperateUtil.ConvertDataSetListFromvirtual(olddataSetList);
                        dataSetList = olddataSetList;
                    }
                    dataSetList.saveData();
                    if (olddataSetList.isVirtualFloat()) {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u865a\u62df\u6d6e\u52a8\u884c");
                    } else if (regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        for (ReportRegionDataSet dataSet : dataSetList.getDataSetList()) {
                            ReportRegionDataSet parentDataSet = dataSet.getParentDataSet();
                            if (null == parentDataSet) continue;
                            boolean hasNewData = false;
                            for (String aZdm : hasDataZdms) {
                                parentDataSet.locateDataRowByZdm(aZdm);
                                boolean isNewRow = parentDataSet.getIsNewRow();
                                if (isNewRow) {
                                    parentDataSet.saveRowData();
                                    hasNewData = true;
                                    continue;
                                }
                                if (parentDataSet.getDataSet() == null || parentDataSet.getDataSet().isHasLoadAllRec()) continue;
                                parentDataSet.saveRowData();
                            }
                            if (!hasNewData) continue;
                            parentDataSet.saveData();
                        }
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6d6e\u52a8\u533a\u57df\uff1a" + regionData.getRegionTop() + "\u5bfc\u51fa\u6570\u636e\u884c\u6570\uff1a" + hasDataZdms.size());
                        this.exportDataSevice.updateFormCheckInfo(context, netFormCode, floatKeyCodes, null);
                    } else if (isFMDM) {
                        int downLoadCount = 0;
                        if (dataSetList.getDataSetList().size() > 0) {
                            downLoadCount = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getDataSet().getDataRowCount();
                        }
                        context.setSuccessCorpCount(downLoadCount);
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u8bb0\u5f55\u6570" + hasDataZdms.size() + "\uff0c\u5bfc\u51fa\u5230JIO\u6587\u4ef6\u4e2d\u7684\u5355\u4f4d\u4e2a\u6570" + downLoadCount);
                    } else {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u5bfc\u51fa\u6570\u636e\u884c\u6570\uff1a" + hasDataZdms.size());
                    }
                } else if (isFMDM) {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u5bf9\u5e94\u5c01\u9762\u4ee3\u7801\u6570\u636e\u96c6\u65e0\u6570\u636e\uff0c\u8bf7\u68c0\u67e5");
                }
            }
            finally {
                if (dataSetList != null) {
                    dataSetList.close();
                    dataSetList = null;
                    olddataSetList = null;
                }
            }
        }
    }

    public FieldType getFieldTypeByColumType(ColumnModelType zbType) {
        FieldType atype = FieldType.FIELD_TYPE_STRING;
        if (zbType == ColumnModelType.STRING) {
            atype = FieldType.FIELD_TYPE_STRING;
        } else if (zbType == ColumnModelType.INTEGER) {
            atype = FieldType.FIELD_TYPE_INTEGER;
        } else if (zbType == ColumnModelType.BIGDECIMAL) {
            atype = FieldType.FIELD_TYPE_DECIMAL;
        } else if (zbType == ColumnModelType.DATETIME) {
            atype = FieldType.FIELD_TYPE_DATE;
        } else if (zbType == ColumnModelType.CLOB) {
            atype = FieldType.FIELD_TYPE_TEXT;
        } else if (zbType == ColumnModelType.BLOB) {
            atype = FieldType.FIELD_TYPE_FILE;
        } else if (zbType == ColumnModelType.BOOLEAN) {
            atype = FieldType.FIELD_TYPE_LOGIC;
        } else if (zbType == ColumnModelType.UUID) {
            atype = FieldType.FIELD_TYPE_UUID;
        }
        return atype;
    }

    protected void measureBalance(ExecutorContext context, String measureKey, String measureCode, RegionRelation relation) {
        IFmlExecEnvironment env = context.getEnv();
        if (env instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment fmlExecEnvironment = (ReportFmlExecEnvironment)env;
            MeasureData selectMeasure = this.measureService.getByMeasure(measureKey, measureCode);
            MeasureFieldValueConvert measureFieldValueConvert = new MeasureFieldValueConvert(selectMeasure, relation.getMeasureView(), relation.getMeasureData());
            measureFieldValueConvert.setMeasureService(this.measureService);
            measureFieldValueConvert.setRuntimeDataSchemeService(this.dataSchemeSevice);
            fmlExecEnvironment.setFieldValueProcessor((IFieldValueProcessor)measureFieldValueConvert);
        }
    }

    private DataValueFormatterBuilder getFormatBuilder(String measureKey, String measureCode, String decimal) {
        Measure measure = new Measure();
        measure.setKey(measureKey);
        measure.setCode(measureCode);
        DataValueFormatterBuilder formatterBuilder = this.formatterBuilderFactory.createFormatterBuilder();
        TypeStrategyUtil strategyutil = (TypeStrategyUtil)BeanUtil.getBean(TypeStrategyUtil.class);
        SysNumberTypeStrategy sysNumberTypestrategy = strategyutil.initSysNumberTypeStrategy();
        if (StringUtils.isNotEmpty((String)decimal)) {
            sysNumberTypestrategy.setNumDecimalPlaces(Integer.valueOf(Integer.parseInt(decimal)));
        }
        if (measure != null) {
            sysNumberTypestrategy.setSelectMeasure(measure);
            sysNumberTypestrategy.setEnableBalanceFormula(true);
        }
        formatterBuilder.registerFormatStrategy(DataFieldType.INTEGER.getValue(), (TypeFormatStrategy)sysNumberTypestrategy);
        formatterBuilder.registerFormatStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeFormatStrategy)sysNumberTypestrategy);
        return formatterBuilder;
    }
}

