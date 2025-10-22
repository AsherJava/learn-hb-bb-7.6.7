/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.FieldType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEIndexInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.core.definition.IMidstoreScheme
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService
 *  com.jiuqi.nvwa.midstore.publish.internal.service.MidstorePublishFieldServiceImpl
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService
 */
package nr.midstore2.data.publish.internal;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.FieldType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEIndexInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.core.definition.IMidstoreScheme;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService;
import com.jiuqi.nvwa.midstore.publish.internal.service.MidstorePublishFieldServiceImpl;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.param.IReportMidstoreMappingService;
import nr.midstore2.data.param.IReportMidstoreParamService;
import nr.midstore2.data.publish.IReportMidstorePublishFieldService;
import nr.midstore2.data.publish.internal.MidstorePublishFieldCache;
import nr.midstore2.data.publish.internal.ReportMidstoreSDKLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstorePublishFieldServiceImpl
implements IReportMidstorePublishFieldService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishFieldServiceImpl.class);
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IReportMidstoreMappingService midstoreMappingService;
    @Autowired
    private IReportMidstoreParamService midstoreParamService;
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;

    @Override
    public MidstoreResultObject publishFields(MidstoreExeContext context, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        String midstoreSchemeKey = context.getMidstoreScheme().getKey();
        IMidstoreScheme midstoreScheme = context.getMidstoreScheme();
        MidstoreFieldDTO queryParam = new MidstoreFieldDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        queryParam.setSourceType(context.getSourceTypeId());
        List fields = this.fieldService.list(queryParam);
        MidstorePublishFieldCache fieldCache = new MidstorePublishFieldCache();
        Map<String, List<DataField>> floatFieldMap = fieldCache.getFloatFieldMap();
        Map<String, MidstoreFieldDTO> midstoreFieldMap = fieldCache.getMidstoreFieldMap();
        List<String> fixFieldCodes = fieldCache.getFixFieldCodes();
        List<String> fixDimFieldCodes = fieldCache.getFixDimFieldCodes();
        ReportMidstoreContext reportContext = this.midstoreParamService.getReportContext(context);
        this.midstoreParamService.doCheckParamsBeforePulish(reportContext);
        this.initZbMapping(reportContext);
        try {
            List oldDeZbInfos = dataExchangeTask.getAllZBInfos();
            List oldDeZbInfos2 = oldDeZbInfos.stream().filter(s -> s.getTaskName().equalsIgnoreCase(dataExchangeTask.getTaskInfo().getName())).collect(Collectors.toList());
            Map<String, DEZBInfo> oldDeZbInfoMap = oldDeZbInfos2.stream().collect(Collectors.toMap(DEZBInfo::getName, dEZBInfo -> dEZBInfo));
            ArrayList<DEZBInfo> dezbinfos = new ArrayList<DEZBInfo>();
            ArrayList<DEZBInfo> addDezbinfos = new ArrayList<DEZBInfo>();
            ArrayList<DEZBInfo> updateDezbinfos = new ArrayList<DEZBInfo>();
            for (MidstoreFieldDTO field : fields) {
                if (StringUtils.isEmpty((String)field.getSrcTableKey())) continue;
                DataField dataField = this.dataSchemeSevice.getDataField(field.getSrcFieldKey());
                if (dataField == null) {
                    logger.error("\u53d1\u5e03\u9519\u8bef\uff1a\u6307\u6807\u4e0d \u5b58\u5728-" + field.getCode() + "," + field.getSrcFieldKey());
                    continue;
                }
                if (midstoreFieldMap.containsKey(field.getSrcFieldKey())) {
                    logger.error("\u53d1\u5e03\u9519\u8bef\uff1a\u6307\u6807KEY\uff0c\u5b58\u5728\u4e24\u4e2a\u6307\u6807CODE-" + field.getCode() + "," + field.getSrcFieldKey() + ",\u53e6\u5916\u4e00\u4e2a\u6307\u6a19CODE\uff1a" + midstoreFieldMap.get(field.getSrcFieldKey()).getCode());
                    continue;
                }
                this.analFieldAndTable(reportContext, midstoreScheme, field, dataField, oldDeZbInfoMap, dezbinfos, addDezbinfos, updateDezbinfos, fieldCache);
                midstoreFieldMap.put(field.getSrcFieldKey(), field);
            }
            if (addDezbinfos.size() > 0) {
                logger.info("\u65b0\u589e\u56fa\u5b9a\u6307\u6807\uff1a" + addDezbinfos.size());
                dataExchangeTask.putZBInfos(addDezbinfos);
            }
            if (updateDezbinfos.size() > 0) {
                logger.info("\u66f4\u65b0\u56fa\u5b9a\u6307\u6807\uff1a" + updateDezbinfos.size());
                dataExchangeTask.putZBInfos(updateDezbinfos);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
        if (!fixFieldCodes.isEmpty()) {
            logger.info("\u53d1\u5e03\u6307\u6807\u8868\uff1aFIXDATA");
            this.publishFixTable(reportContext, "FIXDATA", "\u56fa\u5b9a\u6307\u6807\u8868", fixFieldCodes, dataExchangeTask);
        }
        if (!fixDimFieldCodes.isEmpty()) {
            logger.info("\u53d1\u5e03\u7ef4\u5ea6\u6307\u6807\u8868\uff1aDIMFIXDATA");
            this.publishDimFixTable(reportContext, "DIMFIXDATA", "\u7ef4\u5ea6\u56fa\u5b9a\u6307\u6807\u8868", fixDimFieldCodes, dataExchangeTask);
        }
        HashMap<String, DETableInfo> floatDeTables = new HashMap<String, DETableInfo>();
        for (String floatTableKey : floatFieldMap.keySet()) {
            DataTable dataTable = this.dataSchemeSevice.getDataTable(floatTableKey);
            List<DataField> fieldList = floatFieldMap.get(floatTableKey);
            logger.info("\u53d1\u5e03\u6307\u6807\u660e\u7ec6\u8868\uff1a" + dataTable.getCode(), (Object)("\u5c5e\u4e8e" + dataTable.getDataTableType().getTitle()));
            DETableInfo floatTable = this.publishFloatFields(reportContext, dataTable, fieldList, midstoreFieldMap, dataExchangeTask);
            if (floatTable == null) continue;
            floatDeTables.put(floatTable.getName(), floatTable);
        }
        this.deleteNoUseTableInfos(reportContext, dataExchangeTask, floatDeTables);
        return new MidstoreResultObject(true, "");
    }

    private void analFieldAndTable(ReportMidstoreContext context, IMidstoreScheme midstoreScheme, MidstoreFieldDTO midstoreField, DataField dataField, Map<String, DEZBInfo> oldDeZbInfoMap, List<DEZBInfo> dezbinfos, List<DEZBInfo> addDezbinfos, List<DEZBInfo> updateDezbinfos, MidstorePublishFieldCache fieldCache) {
        boolean isNeedDims;
        Map<String, List<DataField>> floatFieldMap = fieldCache.getFloatFieldMap();
        Map<String, List<String>> fixFieldMap = fieldCache.getFixFieldMap();
        List<String> fixFieldCodes = fieldCache.getFixFieldCodes();
        List<String> fixDimFieldCodes = fieldCache.getFixDimFieldCodes();
        Set<String> fixFieldcodesMap = fieldCache.getFixFieldcodesMap();
        Set<String> fixDimFieldcodesMap = fieldCache.getFixDimFieldcodesMap();
        DataTable dataTable = this.dataSchemeSevice.getDataTable(dataField.getDataTableKey());
        boolean bl = isNeedDims = context.getDataSourceDTO().isUseDimensionField() && dataTable.getDataTableType() == DataTableType.TABLE;
        if (dataTable.getDataTableType() == DataTableType.TABLE || dataTable.getDataTableType() == DataTableType.MD_INFO) {
            ZBMappingInfo zbMapingInfo;
            String zbCode = dataField.getCode();
            String zbFindCode = String.format("%s[%s]", dataTable.getCode(), dataField.getCode());
            if (context.getMappingCache().getZbMapingInfosOld().containsKey(zbCode)) {
                zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getZbMapingInfosOld().get(zbCode);
                zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
            } else if (context.getMappingCache().getZbMapingInfos().containsKey(zbFindCode)) {
                zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getZbMapingInfos().get(zbFindCode);
                zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
            }
            String zbId = null;
            boolean isNew = false;
            if (oldDeZbInfoMap.containsKey(zbCode)) {
                zbId = oldDeZbInfoMap.get(zbCode).getId();
            } else {
                zbId = UUID.randomUUID().toString();
                isNew = true;
            }
            int precisoin = 0;
            if (dataField.getPrecision() != null) {
                precisoin = dataField.getPrecision();
            }
            int decima = 0;
            if (dataField.getDecimal() != null) {
                decima = dataField.getDecimal();
            }
            DEZBInfo deZbInfo = new DEZBInfo(zbId, zbCode, dataField.getTitle(), ReportMidstoreSDKLib.getDEDataType(dataField.getDataFieldType()), precisoin, decima);
            if (midstoreField.isEncrypted()) {
                deZbInfo.setIsEncrypted(midstoreField.isEncrypted());
                deZbInfo.setPrecision(this.encryptedFieldService.getCiphertextMaxLength(precisoin));
            }
            deZbInfo.setTaskName(midstoreScheme.getTablePrefix());
            deZbInfo.setCategory(context.getExeContext().getSourceTypeId());
            dezbinfos.add(deZbInfo);
            if (isNew) {
                addDezbinfos.add(deZbInfo);
            } else {
                updateDezbinfos.add(deZbInfo);
            }
            List<Object> zbIdList = null;
            if (fixFieldMap.containsKey(dataField.getDataTableKey())) {
                zbIdList = fixFieldMap.get(dataField.getDataTableKey());
            } else {
                zbIdList = new ArrayList();
                fixFieldMap.put(dataField.getDataTableKey(), zbIdList);
            }
            zbIdList.add(deZbInfo.getName());
            if (isNeedDims) {
                if (!fixDimFieldcodesMap.contains(deZbInfo.getName())) {
                    fixDimFieldCodes.add(deZbInfo.getName());
                    fixDimFieldcodesMap.add(deZbInfo.getName());
                }
            } else if (!fixFieldcodesMap.contains(deZbInfo.getName())) {
                fixFieldCodes.add(deZbInfo.getName());
                fixFieldcodesMap.add(deZbInfo.getName());
            }
        } else if (dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.ACCOUNT) {
            if (dataTable.getDataTableType() == DataTableType.ACCOUNT && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) {
                return;
            }
            List<Object> fieldList = null;
            if (floatFieldMap.containsKey(dataField.getDataTableKey())) {
                fieldList = floatFieldMap.get(dataField.getDataTableKey());
            } else {
                fieldList = new ArrayList();
                floatFieldMap.put(dataField.getDataTableKey(), fieldList);
            }
            fieldList.add(dataField);
        }
    }

    private void initZbMapping(ReportMidstoreContext context) {
        this.midstoreMappingService.initZbMapping(context);
    }

    private void publishFixTable(ReportMidstoreContext context, String deTableCode, String deTableTtile, List<String> zbCodesList, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        try {
            HashSet<String> hasZbSet = new HashSet<String>(zbCodesList);
            DETableInfo zbTableInfo = dataExchangeTask.getTableInfoByName(deTableCode);
            if (zbTableInfo == null) {
                zbTableInfo = new DETableInfo(UUID.randomUUID().toString(), deTableCode, deTableTtile, TableType.ZB);
                zbTableInfo.setCategory(context.getExeContext().getSourceTypeId());
            } else {
                DETableModel oldTableModel = dataExchangeTask.getTableModelByName(deTableCode);
                for (DEZBInfo zb : oldTableModel.getRefZBs()) {
                    if (context.getExeContext().getSourceTypeId().equalsIgnoreCase(zb.getCategory()) || hasZbSet.contains(zb.getName())) continue;
                    zbCodesList.add(zb.getName());
                }
            }
            DETableModel deZBTableModel = dataExchangeTask.createZBTable(zbTableInfo, zbCodesList);
            if (deZBTableModel == null) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u8868\u53d1\u5e03\u5931\u8d25" + deTableCode);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
    }

    private void publishDimFixTable(ReportMidstoreContext context, String deTableCode, String deTableTtile, List<String> zbCodesList, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        try {
            DETableInfo zbTableInfo = dataExchangeTask.getTableInfoByName(deTableCode);
            HashMap<String, DEFieldInfo> oldDimDeFields = new HashMap<String, DEFieldInfo>();
            HashSet<String> zbCodeDic = new HashSet<String>(zbCodesList);
            if (zbTableInfo == null) {
                zbTableInfo = new DETableInfo(UUID.randomUUID().toString(), deTableCode, deTableTtile, TableType.MDZB);
                zbTableInfo.setCategory(context.getExeContext().getSourceTypeId());
            } else {
                DETableModel oldTableModel = dataExchangeTask.getTableModelByName(deTableCode);
                for (DEZBInfo zb : oldTableModel.getRefZBs()) {
                    if (context.getExeContext().getSourceTypeId().equalsIgnoreCase(zb.getCategory()) || zbCodeDic.contains(zb.getName())) continue;
                    zbCodesList.add(zb.getName());
                    zbCodeDic.add(zb.getName());
                }
                for (Object dimField : oldTableModel.getFields()) {
                    oldDimDeFields.put(dimField.getName(), (DEFieldInfo)dimField);
                }
            }
            DETableModel deZBTableModel = dataExchangeTask.createZBTable(zbTableInfo, zbCodesList);
            if (deZBTableModel == null) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u8868\u589e\u52a0\u6307\u6807\u5931\u8d25" + deTableCode);
            }
            ArrayList<DEFieldInfo> deFields = new ArrayList<DEFieldInfo>();
            for (String dimName : context.getDimEntityCache().getEntityDimAndEntityIds().keySet()) {
                String zbCode;
                if (context.getDimEntityCache().getEntitySingleDims().contains(dimName) || "MDCODE".equalsIgnoreCase(zbCode = dimName) || "MD_ORG".equalsIgnoreCase(zbCode) || "DATATIME".equalsIgnoreCase(zbCode)) continue;
                DEDataType zbDataType = DEDataType.STRING;
                int precisoin = 50;
                int decima = 0;
                String zbId = null;
                zbId = oldDimDeFields.containsKey(zbCode) ? ((DEFieldInfo)oldDimDeFields.get(zbCode)).getId() : UUID.randomUUID().toString();
                DEFieldInfo deFieldInfo = new DEFieldInfo(zbId, zbCode, zbCode, zbDataType, precisoin, decima);
                deFieldInfo.setTaskName(context.getMidstoreScheme().getTablePrefix());
                deFields.add(deFieldInfo);
            }
            DETableModel deZBTableModel2 = dataExchangeTask.createMDZBTable(zbTableInfo, deFields);
            if (deZBTableModel2 == null) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u8868\u8bbe\u7f6e\u7ef4\u5ea6\u5931\u8d25" + deTableCode);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
    }

    private DETableInfo publishFloatFields(ReportMidstoreContext context, DataTable dataTable, List<DataField> fieldList, Map<String, MidstoreFieldDTO> midstoreFieldMap, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        try {
            boolean isTableNew = false;
            DETableInfo bizTableInfo = dataExchangeTask.getTableInfoByName(dataTable.getCode());
            HashMap<String, DEFieldInfo> oldDeFieldMap = new HashMap<String, DEFieldInfo>();
            if (bizTableInfo == null) {
                bizTableInfo = new DETableInfo(UUID.randomUUID().toString(), dataTable.getCode(), dataTable.getTitle(), TableType.BIZ);
                bizTableInfo.setCategory(context.getExeContext().getSourceTypeId());
                isTableNew = true;
            } else {
                DETableModel deTableModel = dataExchangeTask.getTableModelByName(dataTable.getCode());
                if (deTableModel != null && deTableModel.getFields() != null) {
                    for (DEFieldInfo field : deTableModel.getFields()) {
                        oldDeFieldMap.put(field.getName(), field);
                    }
                }
            }
            if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                logger.info("\u53d1\u5e03\u53f0\u5361\u8868\uff1a" + dataTable.getCode());
            }
            ArrayList<DEFieldInfo> deFieldList = new ArrayList<DEFieldInfo>();
            ArrayList bizFields = new ArrayList();
            Map<String, DataField> oldFieldMap = fieldList.stream().collect(Collectors.toMap(Basic::getKey, dataField -> dataField));
            Map<String, DataField> oldFieldCodeMap = fieldList.stream().collect(Collectors.toMap(Basic::getCode, dataField -> dataField));
            String[] bizKeys = dataTable.getBizKeys();
            if (bizKeys != null && bizKeys.length > 0) {
                for (String bizKey : bizKeys) {
                    DataField dataField2;
                    if (oldFieldMap.containsKey(bizKey) || (dataField2 = this.dataSchemeSevice.getDataField(bizKey)).getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                    fieldList.add(dataField2);
                }
            }
            ArrayList<String> indexName2 = new ArrayList<String>();
            for (DataField dataField3 : fieldList) {
                ZBMappingInfo zbMapingInfo;
                int precisoin = 0;
                if (dataField3.getPrecision() != null) {
                    precisoin = dataField3.getPrecision();
                }
                if (precisoin < 0) {
                    precisoin = 22;
                }
                int decima = 0;
                if (dataField3.getDecimal() != null) {
                    decima = dataField3.getDecimal();
                }
                DEDataType zbDataType = ReportMidstoreSDKLib.getDEDataType(dataField3.getDataFieldType());
                FieldType fieldType = FieldType.GENERAL;
                String zbCode = dataField3.getCode();
                String zbFindCode = String.format("%s[%s]", dataTable.getCode(), dataField3.getCode());
                boolean nullable = true;
                if (context.getMappingCache().getZbMapingInfos().containsKey(zbFindCode)) {
                    zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getZbMapingInfos().get(zbFindCode);
                    zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
                } else if (context.getMappingCache().getZbMapingInfosOld().containsKey(zbCode)) {
                    zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getZbMapingInfosOld().get(zbCode);
                    if (dataTable.getCode().equalsIgnoreCase(zbMapingInfo.getTable())) {
                        zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
                    }
                } else if ("MDCODE".equalsIgnoreCase(zbCode)) {
                    zbCode = "MDCODE";
                    zbDataType = DEDataType.STRING;
                    fieldType = FieldType.ORG;
                    nullable = false;
                } else if ("DATATIME".equalsIgnoreCase(zbCode)) {
                    zbCode = "DATATIME";
                    zbDataType = DEDataType.STRING;
                    fieldType = FieldType.PERIOD;
                    if (precisoin < 20) {
                        precisoin = 20;
                    }
                    nullable = false;
                } else if (!context.getDataSourceDTO().isUseDimensionField()) {
                    if (context.getDimEntityCache().getEntityDimAndEntityIds().containsKey(zbCode)) {
                        continue;
                    }
                } else {
                    if (context.getDimEntityCache().getEntitySingleDims().contains(zbCode)) continue;
                    if (context.getDimEntityCache().getEntityDimAndEntityIds().containsKey(zbCode)) {
                        indexName2.add(zbCode);
                    }
                }
                MidstoreFieldDTO midstoreField = midstoreFieldMap.get(dataField3.getKey());
                DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), zbCode, dataField3.getTitle(), zbDataType, precisoin, decima);
                if (midstoreField != null && midstoreField.isEncrypted()) {
                    deFieldInfo.setIsEncrypted(midstoreField.isEncrypted());
                    deFieldInfo.setPrecision(this.encryptedFieldService.getCiphertextMaxLength(precisoin));
                }
                if (isTableNew) {
                    deFieldInfo.setNullable(nullable);
                } else if (oldDeFieldMap.containsKey(zbCode)) {
                    DEFieldInfo oldFied = (DEFieldInfo)oldDeFieldMap.get(zbCode);
                    deFieldInfo.setNullable(oldFied.isNullable());
                } else {
                    deFieldInfo.setNullable(nullable);
                }
                deFieldInfo.setType(fieldType);
                deFieldList.add(deFieldInfo);
            }
            DETableModel bizTableModel = dataExchangeTask.createBizTable(bizTableInfo, deFieldList);
            if (isTableNew) {
                bizTableInfo.setName(bizTableModel.getTableInfo().getName());
            }
            ArrayList<String> aIndexNames = new ArrayList<String>();
            aIndexNames.add("DATATIME");
            aIndexNames.add("MDCODE");
            if (!indexName2.isEmpty()) {
                aIndexNames.addAll(indexName2);
            }
            boolean needIndex = true;
            List indexlist = bizTableModel.getDeIndexInfos();
            for (DEIndexInfo deIndexInfo : indexlist) {
                if (!StringUtils.isNotEmpty((String)deIndexInfo.getIndexName()) || !deIndexInfo.getIndexName().startsWith("IDX_FLOATDATA_")) continue;
                if (deIndexInfo.getFdNames().size() != aIndexNames.size()) {
                    needIndex = false;
                    break;
                }
                needIndex = false;
                break;
            }
            if (needIndex) {
                dataExchangeTask.createIndex("IDX_FLOATDATA_" + OrderGenerator.newOrder(), bizTableInfo.getName(), aIndexNames);
            }
            return bizTableInfo;
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
    }

    private void deleteNoUseTableInfos(ReportMidstoreContext context, IDataExchangeTask dataExchangeTask, Map<String, DETableInfo> updateFloatTables) throws MidstoreExecutionException {
        try {
            List deTables = dataExchangeTask.getAllTables();
            String taskName = dataExchangeTask.getTaskInfo().getName();
            ArrayList<DETableInfo> floatDETables = new ArrayList<DETableInfo>();
            for (DETableInfo deTable : deTables) {
                if (deTable.getType() != TableType.BIZ || !taskName.equalsIgnoreCase(deTable.getTaskName()) || !StringUtils.isNotEmpty((String)deTable.getCategory()) || !deTable.getCategory().equalsIgnoreCase(context.getExeContext().getSourceTypeId())) continue;
                floatDETables.add(deTable);
            }
            for (DETableInfo deTable : floatDETables) {
                String tableCode = deTable.getName();
                if (updateFloatTables.containsKey(tableCode)) continue;
                dataExchangeTask.dropTable(tableCode);
                logger.info("\u5220\u9664\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6570\u636e\u8868\uff1a" + tableCode);
            }
        }
        catch (DataExchangeException e) {
            logger.error("\u5220\u9664\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6570\u636e\u8868\uff1a" + e.getMessage(), e);
        }
    }
}

