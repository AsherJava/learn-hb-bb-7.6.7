/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer
 *  com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao
 *  com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO
 *  com.jiuqi.nr.datascheme.internal.dto.DataDimDesignDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDesignDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO
 *  com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.param.transfer.ChangeObj;
import com.jiuqi.nr.param.transfer.FieldChangeObj;
import com.jiuqi.nr.param.transfer.datascheme.TransferId;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import com.jiuqi.nr.param.transfer.datascheme.TransferSchemeDTO;
import com.jiuqi.nr.param.transfer.datascheme.TransferTableDTO;
import com.jiuqi.nr.param.transfer.datascheme.dto.DataFieldLanguageDTO;
import com.jiuqi.nr.param.transfer.datascheme.spi.DataSchemeTransfer;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeModelTransfer.class);
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IParamLevelManager paramLevelManager;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired(required=false)
    private List<DataSchemeTransfer> dataSchemeTransfers;
    @Autowired
    private DesignDataSchemeI18nDao i18nDesignDao;
    private List<FieldChangeObj> fieldChangeFormulaObjs = new ArrayList<FieldChangeObj>();
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public List<FieldChangeObj> getFieldChangeFormulaObjs() {
        return this.fieldChangeFormulaObjs;
    }

    public void setFieldChangeFormulaObjs(List<FieldChangeObj> fieldChangeFormulaObjs) {
        this.fieldChangeFormulaObjs = fieldChangeFormulaObjs;
    }

    public void cleanFieldChangeFormulaObjs() {
        this.fieldChangeFormulaObjs = new ArrayList<FieldChangeObj>();
    }

    private void moduleRegister(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(DataScheme.class, DataSchemeDesignDTO.class);
        module.addAbstractTypeMapping(DataDimension.class, DataDimDesignDTO.class);
        module.addAbstractTypeMapping(ValidationRule.class, ValidationRuleDTO.class);
        module.addDeserializer(Instant.class, (JsonDeserializer)new InstantJsonDeserializer());
        module.addSerializer(Instant.class, (JsonSerializer)new InstantJsonSerializer());
        module.addAbstractTypeMapping(DataTable.class, DataTableDesignDTO.class);
        module.addAbstractTypeMapping(DataField.class, DataFieldDesignDTO.class);
        module.addAbstractTypeMapping(DesignDataDimension.class, DataDimDesignDTO.class);
        module.addAbstractTypeMapping(DesignDataField.class, DataFieldDesignDTO.class);
        objectMapper.registerModule((Module)module);
    }

    public DataSchemeModelTransfer() {
        this.moduleRegister(objectMapper);
    }

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext context, byte[] bytes) throws TransferException {
        String targetGuid = context.getTargetGuid();
        TransferId transferId = TransferIdParse.parseId(targetGuid);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        try {
            switch (nodeType) {
                case GROUP: 
                case SCHEME_GROUP: {
                    break;
                }
                case SCHEME: {
                    this.importDataScheme(context, bytes, key);
                    break;
                }
                case TABLE: 
                case MD_INFO: 
                case ACCOUNT_TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: {
                    this.importDataTable(context, bytes, key, nodeType);
                    break;
                }
                default: {
                    throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25");
                }
            }
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u77e5\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u548c\u6570\u636e\u5e93", (Throwable)e);
        }
    }

    private void setFieldMessage(boolean collectImportDetail, List<DesignDataField> insertFields, DesignDataTableDO dataTableDesignDTO) {
        if (collectImportDetail) {
            DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(dataTableDesignDTO.getDataSchemeKey());
            ArrayList<ChangeObj> updateFields = new ArrayList<ChangeObj>();
            ArrayList<ChangeObj> addFields = new ArrayList<ChangeObj>();
            ArrayList<ChangeObj> deleteFields = new ArrayList<ChangeObj>();
            List existFields = this.iDesignDataSchemeService.getDataFieldByTable(dataTableDesignDTO.getKey());
            HashMap existFieldMap = CollectionUtils.isEmpty(existFields) ? new HashMap() : existFields.stream().collect(Collectors.toMap(Basic::getKey, a -> a, (k1, k2) -> k1));
            HashSet<String> dad = new HashSet<String>();
            for (DesignDataField designDataField : insertFields) {
                DesignDataField designDataField2 = (DesignDataField)existFieldMap.get(designDataField.getKey());
                if (designDataField2 != null) {
                    dad.add(designDataField.getKey());
                    if (designDataField.getUpdateTime().compareTo(designDataField2.getUpdateTime()) == 0) continue;
                    updateFields.add(ChangeObj.getChangeObj(designDataField, "1"));
                    continue;
                }
                addFields.add(ChangeObj.getChangeObj(designDataField, "0"));
            }
            Set existFieldSet = existFieldMap.keySet();
            existFieldSet.removeAll(dad);
            if (!CollectionUtils.isEmpty(existFieldSet)) {
                for (String s : existFieldSet) {
                    DesignDataField designDataField = (DesignDataField)existFieldMap.get(s);
                    if (designDataField == null) continue;
                    deleteFields.add(ChangeObj.getChangeObj(designDataField, "2"));
                }
            }
            if (!(CollectionUtils.isEmpty(updateFields) && CollectionUtils.isEmpty(addFields) && CollectionUtils.isEmpty(deleteFields))) {
                FieldChangeObj fieldChangeObj = new FieldChangeObj(dataScheme.getKey(), dataScheme.getTitle(), dataTableDesignDTO.getKey(), dataTableDesignDTO.getTitle(), addFields, updateFields, deleteFields);
                this.fieldChangeFormulaObjs.add(fieldChangeObj);
            }
        }
    }

    private void importDataTable(IImportContext context, byte[] bytes, String key, NodeType nodeType) throws TransferException {
        TransferTableDTO tableDTO;
        boolean collectImportDetail = context.isCollectImportDetail();
        String log = nodeType.getTitle() + "\u3010%s\u3011\u5bfc\u5165\uff0c";
        try {
            tableDTO = this.toData(bytes, TransferTableDTO.class);
        }
        catch (TransferException e) {
            throw new TransferException(String.format(log, key) + e.getMessage(), (Throwable)e);
        }
        DesignDataTableDO dataTableDesignDTO = DesignDataTableDO.valueOf((DataTable)tableDTO.getDataTable());
        String groupKey = dataTableDesignDTO.getDataGroupKey();
        this.getMessage("\u6570\u636e\u65b9\u6848\u4e0b\u6570\u636e\u8868", key, dataTableDesignDTO.getKey(), dataTableDesignDTO.getTitle());
        this.checkDataTableExist(key);
        if (groupKey == null) {
            if (this.iDesignDataSchemeService.getDataScheme(dataTableDesignDTO.getDataSchemeKey()) == null) {
                throw new TransferException(String.format(log, dataTableDesignDTO.getTitle()) + "\u672a\u627e\u5230\u4e0a\u7ea7\u6570\u636e\u65b9\u6848");
            }
        } else if (this.iDesignDataSchemeService.getDataGroup(groupKey) == null) {
            throw new TransferException(String.format(log, dataTableDesignDTO.getTitle()) + "\u672a\u627e\u5230\u4e0a\u7ea7\u5206\u7ec4");
        }
        this.setFieldMessage(collectImportDetail, tableDTO.getFields(), dataTableDesignDTO);
        this.iDesignDataSchemeService.deleteDataTable(key);
        try {
            boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
            if (openParamLevel && !this.isSet(dataTableDesignDTO.getLevel())) {
                dataTableDesignDTO.setLevel(String.valueOf(context.getSrcPacketLevel()));
            }
            this.iDesignDataSchemeService.insertDataTable((DesignDataTable)dataTableDesignDTO, false);
            List<DesignDataField> list = tableDTO.getFields();
            for (DesignDataField fieldDO : list) {
                String refDataEntityKey = fieldDO.getRefDataEntityKey();
                if (openParamLevel && !this.isSet(fieldDO.getLevel())) {
                    fieldDO.setLevel(String.valueOf(context.getSrcPacketLevel()));
                }
                if (refDataEntityKey == null || AdjustUtils.isAdjust((String)refDataEntityKey).booleanValue()) continue;
                try {
                    String bizKeys;
                    TableModelDefine model;
                    if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(refDataEntityKey)) {
                        model = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(refDataEntityKey);
                        bizKeys = model.getBizKeys();
                    } else {
                        model = this.entityMetaService.getTableModel(refDataEntityKey);
                        bizKeys = model.getBizKeys();
                    }
                    fieldDO.setRefDataFieldKey(bizKeys);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    fieldDO.setRefDataFieldKey(null);
                }
            }
            this.importPropertyNumMessage("\u6570\u636e\u65b9\u6848\u4e0b\u6570\u636e\u8868", "\u6570\u636e\u8868\u4e0b\u6307\u6807fields", list != null ? list.size() : 0);
            this.iDesignDataSchemeService.insertDataFields(list, false);
        }
        catch (SchemeDataException e) {
            throw new TransferException(String.format(log, dataTableDesignDTO.getTitle()) + e.getMessage(), (Throwable)e);
        }
        if (context.isImportMultiLanguage()) {
            try {
                List<DataFieldLanguageDTO> fieldLanguages = tableDTO.getFieldLanguages();
                if (!CollectionUtils.isEmpty(fieldLanguages)) {
                    ArrayList<DesignDataSchemeI18nDO> insertFieldLanguage = new ArrayList<DesignDataSchemeI18nDO>();
                    String dataSchemeKey = dataTableDesignDTO.getDataSchemeKey();
                    for (DataFieldLanguageDTO fieldLanguage : fieldLanguages) {
                        DesignDataSchemeI18nDO designDataSchemeI18nDO = new DesignDataSchemeI18nDO();
                        fieldLanguage.value2Define(designDataSchemeI18nDO);
                        designDataSchemeI18nDO.setDataSchemeKey(dataSchemeKey);
                        insertFieldLanguage.add(designDataSchemeI18nDO);
                    }
                    this.i18nDesignDao.insert(insertFieldLanguage.toArray());
                }
            }
            catch (Exception e) {
                throw new TransferException(String.format(log, dataTableDesignDTO.getTitle()) + "\u6570\u636e\u8868\u591a\u8bed\u8a00" + e.getMessage(), (Throwable)e);
            }
        }
    }

    private void importDataScheme(IImportContext context, byte[] bytes, String key) throws TransferException {
        List<DesignDataDimension> dims;
        TransferSchemeDTO dataSchemeDTO;
        DesignDataScheme oldDataScheme = this.iDesignDataSchemeService.getDataScheme(key);
        this.oldResourceExistMessage(oldDataScheme != null, "\u6570\u636e\u65b9\u6848", oldDataScheme != null ? oldDataScheme.getTitle() : "");
        String log = NodeType.SCHEME.getTitle() + "\u3010%s\u3011\u5bfc\u5165\uff0c";
        try {
            dataSchemeDTO = this.toData(bytes, TransferSchemeDTO.class);
        }
        catch (TransferException e) {
            throw new TransferException(String.format(log, key) + e.getMessage(), (Throwable)e);
        }
        DataSchemeDesignDTO dataScheme = DataSchemeDesignDTO.valueOf((DataScheme)dataSchemeDTO.getDataScheme());
        this.getMessage("\u6570\u636e\u65b9\u6848", key, dataScheme.getKey(), dataScheme.getTitle());
        String groupKey = dataScheme.getDataGroupKey();
        if (!"00000000-0000-0000-0000-000000000000".equals(groupKey) && groupKey != null && this.iDesignDataSchemeService.getDataGroup(groupKey) == null) {
            throw new TransferException(String.format(log, dataScheme.getTitle()) + "\u672a\u627e\u5230\u4e0a\u7ea7\u5206\u7ec4");
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && !this.isSet(dataScheme.getLevel())) {
            dataScheme.setLevel(String.valueOf(context.getSrcPacketLevel()));
        }
        this.importPropertyNumMessage("\u6570\u636e\u65b9\u6848", "\u7ef4\u5ea6dims", (dims = dataSchemeDTO.getDims()) != null ? dims.size() : 0);
        ArrayList<DataDimDesignDTO> dtoList = new ArrayList<DataDimDesignDTO>();
        for (DataDimension dataDimension : dims) {
            DataDimDesignDTO dataDimDesignDTO = DataDimDesignDTO.valueOf((DataDimension)dataDimension);
            dtoList.add(dataDimDesignDTO);
            if (!openParamLevel || this.isSet(dataDimDesignDTO.getLevel())) continue;
            dataDimDesignDTO.setLevel(String.valueOf(context.getSrcPacketLevel()));
        }
        try {
            if (oldDataScheme != null) {
                dataScheme.setCreator(oldDataScheme.getCreator());
                this.iDesignDataSchemeService.updateDataScheme((DesignDataScheme)dataScheme, dtoList);
            } else {
                dataScheme.setCreator(context.getOperator());
                this.iDesignDataSchemeService.insertDataScheme((DesignDataScheme)dataScheme, dtoList);
            }
        }
        catch (SchemeDataException e) {
            throw new TransferException(String.format(log, dataScheme.getTitle()) + e.getMessage(), (Throwable)e);
        }
        try {
            this.importPropertyNumMessage("\u6570\u636e\u65b9\u6848", "\u8c03\u6574\u671fadjusts", dataSchemeDTO.getAdjusts() != null ? dataSchemeDTO.getAdjusts().size() : 0);
            this.adjustPeriodDesignService.updateAdjust(dataSchemeDTO.getAdjusts());
        }
        catch (Exception e) {
            throw new TransferException(String.format(log, "\u8c03\u6574\u671f\u6570\u636e") + e.getMessage(), (Throwable)e);
        }
        this.importExpandParam(context, dataSchemeDTO, key);
    }

    private void importExpandParam(IImportContext context, TransferSchemeDTO dataSchemeDTO, String key) {
        block7: {
            if (this.dataSchemeTransfers != null) {
                try {
                    Map<String, byte[]> params = dataSchemeDTO.getParams();
                    if (params != null) {
                        Logger log = context.getLogger();
                        for (Map.Entry<String, byte[]> entry : params.entrySet()) {
                            DataSchemeTransfer transfer = this.getDataSchemeTransfer(entry.getKey());
                            if (transfer == null) {
                                if (log == null) continue;
                                log.warn("\u53c2\u6570\u5bfc\u5165\u6570\u636e\u65b9\u6848\u672a\u627e\u5230\u6269\u5c55\u8d44\u6e90\u5904\u7406\u5668\u3010" + entry.getKey() + "\u3011\u8df3\u8fc7");
                                continue;
                            }
                            if (logger.isDebugEnabled()) {
                                logger.debug(String.format("\u5bfc\u5165\u6570\u636e\u65b9\u6848\u5176\u4ed6\u53c2\u6570\uff0c\u6269\u5c55\u8d44\u6e90\u5904\u7406\u5668id\u662f\uff1a %s ", entry.getKey()));
                            }
                            transfer.importTaskData(context, key, entry.getValue());
                        }
                    }
                }
                catch (Exception e) {
                    Logger logger = context.getLogger();
                    if (logger == null) break block7;
                    logger.warn("\u5bfc\u5165\u4efb\u52a1\u6269\u5c55\u8d44\u6e90\u5931\u8d25\uff0c\u8df3\u8fc7", e);
                }
            }
        }
    }

    private DataSchemeTransfer getDataSchemeTransfer(String id) {
        DataSchemeTransfer transfer = null;
        for (DataSchemeTransfer dataSchemeTransfer : this.dataSchemeTransfers) {
            if (!id.equals(dataSchemeTransfer.getId())) continue;
            transfer = dataSchemeTransfer;
        }
        return transfer;
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String s) throws TransferException {
        byte[] bytes;
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        MetaExportModel exportModel = new MetaExportModel();
        switch (nodeType) {
            case GROUP: 
            case SCHEME_GROUP: {
                DesignDataGroup dataGroup = this.iDesignDataSchemeService.getDataGroup(key);
                bytes = this.toBytes(dataGroup);
                break;
            }
            case SCHEME: {
                bytes = this.getDataSchemeBusiness(iExportContext, key);
                break;
            }
            case TABLE: 
            case MD_INFO: 
            case ACCOUNT_TABLE: 
            case DETAIL_TABLE: 
            case MUL_DIM_TABLE: {
                DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(key);
                if (dataTable == null) {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                }
                List fields = this.iDesignDataSchemeService.getDataFieldByTable(key);
                TransferTableDTO tableDTO = new TransferTableDTO();
                tableDTO.setDataTable((DataTable)dataTable);
                tableDTO.setFields(fields);
                List<String> fieldKeys = fields.stream().map(Basic::getKey).collect(Collectors.toList());
                List<DesignDataSchemeI18nDO> dataFieldLanguages = this.getDataSchemeI18nDOs(fieldKeys);
                if (dataFieldLanguages.size() > 0) {
                    ArrayList<DataFieldLanguageDTO> dataFieldLanguageDTOS = new ArrayList<DataFieldLanguageDTO>();
                    for (DesignDataSchemeI18nDO designDataSchemeI18nDO : dataFieldLanguages) {
                        DataFieldLanguageDTO dataFieldLanguageDTO = DataFieldLanguageDTO.valueOf(designDataSchemeI18nDO);
                        dataFieldLanguageDTOS.add(dataFieldLanguageDTO);
                    }
                    tableDTO.setFieldLanguages(dataFieldLanguageDTOS);
                }
                bytes = this.toBytes(tableDTO);
                break;
            }
            default: {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + nodeType);
            }
        }
        exportModel.setData(bytes);
        return exportModel;
    }

    private List<DesignDataSchemeI18nDO> getDataSchemeI18nDOs(List<String> fieldKeys) {
        ArrayList<DesignDataSchemeI18nDO> dataFieldLanguages = new ArrayList<DesignDataSchemeI18nDO>();
        IDatabase database = DatabaseInstance.getDatabase();
        int maxInSize = DataEngineUtil.getMaxInSize((IDatabase)database);
        int fieldNum = fieldKeys.size();
        int queryNum = fieldNum / maxInSize + (fieldNum % maxInSize != 0 ? 1 : 0);
        StringBuilder whereSql = new StringBuilder();
        for (int i = 0; i < queryNum; ++i) {
            List<Object> queryData = new ArrayList();
            if (i == queryNum - 1) {
                whereSql = new StringBuilder();
                whereSql.append("DI_KEY").append(" in (?");
                for (int j = maxInSize * i + 1; j < fieldNum; ++j) {
                    whereSql.append(",?");
                }
                whereSql.append(")");
                queryData = fieldKeys.subList(i * maxInSize, fieldNum);
            } else {
                if (i == 0) {
                    whereSql.append("DI_KEY").append(" in (?");
                    for (int j = 1; j < maxInSize; ++j) {
                        whereSql.append(",?");
                    }
                    whereSql.append(")");
                }
                queryData = fieldKeys.subList(i * maxInSize, (i + 1) * maxInSize);
            }
            Object[] array = queryData.toArray(new String[0]);
            List list = this.i18nDesignDao.list(whereSql.toString(), array, DesignDataSchemeI18nDO.class);
            dataFieldLanguages.addAll(list);
        }
        return dataFieldLanguages;
    }

    private byte[] getDataSchemeBusiness(IExportContext iExportContext, String key) throws TransferException {
        TransferSchemeDTO schemeDTO;
        block5: {
            DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(key);
            if (dataScheme == null) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
            }
            List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(key);
            schemeDTO = new TransferSchemeDTO();
            schemeDTO.setDataScheme((DataScheme)dataScheme);
            schemeDTO.setDims(dataSchemeDimension);
            schemeDTO.setAdjusts(this.getAdjusts(key));
            if (this.dataSchemeTransfers != null) {
                try {
                    HashMap<String, byte[]> params = new HashMap<String, byte[]>(this.dataSchemeTransfers.size());
                    for (DataSchemeTransfer dataSchemeTransfer : this.dataSchemeTransfers) {
                        byte[] data = dataSchemeTransfer.exportTaskData(iExportContext, key);
                        params.put(dataSchemeTransfer.getId(), data);
                    }
                    schemeDTO.setParams(params);
                }
                catch (Exception e) {
                    Logger logger = iExportContext.getLogger();
                    if (logger == null) break block5;
                    logger.warn("\u53c2\u6570\u5bfc\u5165\u6570\u636e\u65b9\u6848\u83b7\u53d6\u6269\u5c55\u8d44\u6e90\u5931\u8d25,\u8df3\u8fc7", e);
                }
            }
        }
        return this.toBytes(schemeDTO);
    }

    private List<DesignAdjustPeriodDTO> getAdjusts(String key) {
        List query = this.adjustPeriodDesignService.query(key);
        return query.stream().filter(AdjustUtils::isAdjustData).collect(Collectors.toList());
    }

    public byte[] toBytes(Object basic) throws TransferException {
        if (basic == null) {
            throw new TransferException("\u6253\u5305\u8d44\u6e90\u5931\u8d25\uff1a\u8d44\u6e90\u4e3a\u7a7a");
        }
        try {
            return objectMapper.writeValueAsBytes(basic);
        }
        catch (IOException e) {
            throw new TransferException("\u6253\u5305\u8d44\u6e90\u5931\u8d25:" + basic, (Throwable)e);
        }
    }

    public <T> T toData(byte[] basic, Class<T> dtoClass) throws TransferException {
        if (basic == null) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25\uff1a\u6570\u636e\u4e3a\u7a7a");
        }
        try {
            return (T)objectMapper.readValue(basic, dtoClass);
        }
        catch (IOException e) {
            String value = new String(basic, StandardCharsets.UTF_8);
            throw new TransferException("\u89e3\u6790\u5931\u8d25:" + value, (Throwable)e);
        }
    }

    private boolean isSet(String level) {
        if (level == null || "0".equals(level)) {
            return false;
        }
        return StringUtils.hasLength(level);
    }

    private void getMessage(String resourceTypeName, String key, String resourceKey, String resourceTitle) {
        if (logger.isDebugEnabled()) {
            String message = String.format("\u53c2\u6570\u5bfc\u5165 %s \u4fe1\u606f\uff0c\u5165\u53c2key\u662f\uff1a%s \uff0c\u89e3\u6790\u51fa\u6765\u7684\u6570\u636e\u5bf9\u8c61\u7684key\u662f\uff1a%s \uff0ctitle\u662f\uff1a%s \uff01", resourceTypeName, key, resourceKey, resourceTitle);
            logger.debug(message);
            if (!resourceKey.equals(key)) {
                logger.debug("\u5f53\u524d\u8d44\u6e90\u5bfc\u5165\u5165\u53c2\u7684key\u548c\u53c2\u6570\u5305\u89e3\u6790\u51fa\u6765\u7684key\u4e0d\u4e00\u6837\uff0c\u8bf7\u6838\u5bf9\u51fa\u9519\u539f\u56e0");
            }
        }
    }

    private void importPropertyNumMessage(String resourceTypeName, String propertyName, int num) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165 %s \u7684 %s \u5c5e\u6027\uff0c\u5176\u6570\u91cf\u662f\uff1a%d \uff01", resourceTypeName, propertyName, num));
        }
    }

    private void oldResourceExistMessage(boolean oldResourceNotEmpty, String resourceTypeName, String messageTitle) {
        if (logger.isDebugEnabled()) {
            if (oldResourceNotEmpty) {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u66f4\u65b0\uff0c\u5176title\u662f\uff1a %s", resourceTypeName, messageTitle));
            } else {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u4e0d\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u65b0\u589e\uff01", resourceTypeName));
            }
        }
    }

    private void checkDataTableExist(String key) {
        if (logger.isDebugEnabled() && StringUtils.hasText(key)) {
            DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(key);
            if (dataTable == null) {
                logger.debug(String.format("\u6570\u636e\u65b9\u6848\u5bfc\u5165\u6570\u636e\u8868\uff0ckey\u4e3a%s \u7684\u6570\u636e\u8868\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff01", key));
            } else {
                logger.debug(String.format("\u6570\u636e\u65b9\u6848\u5bfc\u5165\u6570\u636e\u8868\uff0ckey\u4e3a%s \u7684\u6570\u636e\u8868\u5728\u5f53\u524d\u670d\u52a1\u5b58\u5728\uff0ctitle\u4e3a%s", key, dataTable.getTitle()));
            }
        }
    }
}

