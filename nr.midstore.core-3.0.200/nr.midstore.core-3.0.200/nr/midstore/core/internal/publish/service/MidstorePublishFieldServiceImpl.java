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
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package nr.midstore.core.internal.publish.service;

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
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.mapping.ZBMappingInfo;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import nr.midstore.core.internal.publish.service.MidstoreSDKLib;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.publish.service.IMidstorePublishFieldService;
import nr.midstore.core.util.IMidstoreEncryptedFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstorePublishFieldServiceImpl
implements IMidstorePublishFieldService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishFieldServiceImpl.class);
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;

    @Override
    public void publishFields(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        MidstoreFieldDTO queryParam = new MidstoreFieldDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        List<MidstoreFieldDTO> fields = this.fieldService.list(queryParam);
        Map<String, MidstoreFieldDTO> midstoreFieldsDic = fields.stream().collect(Collectors.toMap(MidstoreFieldDO::getSrcFieldKey, dEZBInfo -> dEZBInfo, (v1, v2) -> v1));
        HashMap floatFieldMap = new HashMap();
        HashMap fixFieldMap = new HashMap();
        ArrayList<String> fixFieldIds = new ArrayList<String>();
        ArrayList<String> fixFieldCodes = new ArrayList<String>();
        this.initZbMapping(context);
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
                DataTable dataTable = this.dataSchemeSevice.getDataTable(dataField.getDataTableKey());
                if (dataTable.getDataTableType() == DataTableType.TABLE) {
                    ZBMappingInfo zbMapingInfo;
                    String zbCode = dataField.getCode();
                    String zbFindCode = String.format("%s[%s]", dataTable.getCode(), dataField.getCode());
                    if (context.getMappingCache().getZbMapingInfosOld().containsKey(zbCode)) {
                        zbMapingInfo = context.getMappingCache().getZbMapingInfosOld().get(zbCode);
                        zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
                    } else if (context.getMappingCache().getZbMapingInfos().containsKey(zbFindCode)) {
                        zbMapingInfo = context.getMappingCache().getZbMapingInfos().get(zbFindCode);
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
                    DEZBInfo deZbInfo = new DEZBInfo(zbId, zbCode, dataField.getTitle(), MidstoreSDKLib.getDEDataType(dataField.getDataFieldType()), precisoin, decima);
                    if (field.isEncrypted()) {
                        deZbInfo.setIsEncrypted(field.isEncrypted());
                        deZbInfo.setPrecision(this.encryptedFieldService.getCiphertextMaxLength(precisoin));
                    }
                    deZbInfo.setTaskName(midstoreScheme.getTablePrefix());
                    dezbinfos.add(deZbInfo);
                    if (isNew) {
                        addDezbinfos.add(deZbInfo);
                    } else {
                        updateDezbinfos.add(deZbInfo);
                    }
                    List<String> zbIdList = null;
                    if (fixFieldMap.containsKey(dataField.getDataTableKey())) {
                        zbIdList = (List)fixFieldMap.get(dataField.getDataTableKey());
                    } else {
                        zbIdList = new ArrayList();
                        fixFieldMap.put(dataField.getDataTableKey(), zbIdList);
                    }
                    zbIdList.add(deZbInfo.getName());
                    fixFieldIds.add(deZbInfo.getId());
                    fixFieldCodes.add(deZbInfo.getName());
                    continue;
                }
                if (dataTable.getDataTableType() != DataTableType.DETAIL && dataTable.getDataTableType() != DataTableType.ACCOUNT || dataTable.getDataTableType() == DataTableType.ACCOUNT && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                List<DataField> fieldList = null;
                if (floatFieldMap.containsKey(dataField.getDataTableKey())) {
                    fieldList = (List)floatFieldMap.get(dataField.getDataTableKey());
                } else {
                    fieldList = new ArrayList();
                    floatFieldMap.put(dataField.getDataTableKey(), fieldList);
                }
                fieldList.add(dataField);
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
            throw new MidstoreException(e.getMessage(), e);
        }
        logger.info("\u53d1\u5e03\u6307\u6807\u8868\uff1aFIXDATA");
        this.publishFixTable("FIXDATA", "\u56fa\u5b9a\u6307\u6807\u8868", fixFieldCodes, dataExchangeTask);
        for (String floatTableKey : floatFieldMap.keySet()) {
            DataTable dataTable = this.dataSchemeSevice.getDataTable(floatTableKey);
            List fieldList = (List)floatFieldMap.get(floatTableKey);
            logger.info("\u53d1\u5e03\u6307\u6807\u660e\u7ec6\u8868\uff1a" + dataTable.getCode(), (Object)("\u5c5e\u4e8e" + dataTable.getDataTableType().getTitle()));
            this.publishFloatFields(context, dataTable, fieldList, dataExchangeTask, midstoreFieldsDic);
        }
    }

    private void initZbMapping(MidstoreContext context) {
        this.midstoreMappingService.initZbMapping(context);
    }

    private void publishFixTable(String deTableCode, String deTableTtile, List<String> zbIdList, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        try {
            DETableModel deZBTableModel;
            DETableInfo zbTableInfo = dataExchangeTask.getTableInfoByName(deTableCode);
            if (zbTableInfo == null) {
                zbTableInfo = new DETableInfo(UUID.randomUUID().toString(), deTableCode, deTableTtile, TableType.ZB);
            }
            if ((deZBTableModel = dataExchangeTask.createZBTable(zbTableInfo, zbIdList)) == null) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u8868\u53d1\u5e03\u5931\u8d25");
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private void publishFixTables(DataTable dataTable, List<String> zbIdList, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        try {
            DETableInfo zbTableInfo = dataExchangeTask.getTableInfoByName(dataTable.getCode());
            if (zbTableInfo == null) {
                zbTableInfo = new DETableInfo(UUID.randomUUID().toString(), dataTable.getCode(), dataTable.getTitle(), TableType.ZB);
            }
            DETableModel dETableModel = dataExchangeTask.createZBTable(zbTableInfo, zbIdList);
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private void publishFloatFields(MidstoreContext context, DataTable dataTable, List<DataField> fieldList, IDataExchangeTask dataExchangeTask, Map<String, MidstoreFieldDTO> midstoreFieldsDic) throws MidstoreException {
        try {
            DETableInfo bizTableInfo = dataExchangeTask.getTableInfoByName(dataTable.getCode());
            if (bizTableInfo == null) {
                bizTableInfo = new DETableInfo(UUID.randomUUID().toString(), dataTable.getCode(), dataTable.getTitle(), TableType.BIZ);
            }
            if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                logger.info("\u53d1\u5e03\u53f0\u5361\u8868\uff1a" + dataTable.getCode());
            }
            ArrayList<DEFieldInfo> deFieldList = new ArrayList<DEFieldInfo>();
            Map<String, DataField> oldFieldMap = fieldList.stream().collect(Collectors.toMap(Basic::getKey, DataField2 -> DataField2));
            String[] bizKeys = dataTable.getBizKeys();
            if (bizKeys != null && bizKeys.length > 0) {
                for (String bizKey : bizKeys) {
                    DataField dataField;
                    if (oldFieldMap.containsKey(bizKey) || (dataField = this.dataSchemeSevice.getDataField(bizKey)).getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                    fieldList.add(dataField);
                }
            }
            for (DataField dataField : fieldList) {
                MidstoreFieldDTO mField;
                ZBMappingInfo zbMapingInfo;
                int precisoin = 0;
                if (dataField.getPrecision() != null) {
                    precisoin = dataField.getPrecision();
                }
                if (precisoin < 0) {
                    precisoin = 22;
                }
                int decima = 0;
                if (dataField.getDecimal() != null) {
                    decima = dataField.getDecimal();
                }
                DEDataType zbDataType = MidstoreSDKLib.getDEDataType(dataField.getDataFieldType());
                FieldType fieldType = FieldType.GENERAL;
                String zbCode = dataField.getCode();
                String zbFindCode = String.format("%s[%s]", dataTable.getCode(), dataField.getCode());
                if (context.getMappingCache().getZbMapingInfos().containsKey(zbFindCode)) {
                    zbMapingInfo = context.getMappingCache().getZbMapingInfos().get(zbFindCode);
                    zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
                } else if (context.getMappingCache().getZbMapingInfosOld().containsKey(zbCode)) {
                    zbMapingInfo = context.getMappingCache().getZbMapingInfosOld().get(zbCode);
                    if (dataTable.getCode().equalsIgnoreCase(zbMapingInfo.getZbMapping().getTable())) {
                        zbCode = this.midstoreMappingService.getMapFieldCode(zbMapingInfo.getMappingzb());
                    }
                } else if ("MDCODE".equalsIgnoreCase(zbCode)) {
                    zbCode = "MDCODE";
                    zbDataType = DEDataType.STRING;
                    fieldType = FieldType.ORG;
                } else if ("DATATIME".equalsIgnoreCase(zbCode)) {
                    zbCode = "DATATIME";
                    zbDataType = DEDataType.STRING;
                    fieldType = FieldType.PERIOD;
                    if (precisoin < 20) {
                        precisoin = 20;
                    }
                }
                DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), zbCode, dataField.getTitle(), zbDataType, precisoin, decima);
                deFieldInfo.setType(fieldType);
                if (midstoreFieldsDic.containsKey(dataField.getKey()) && (mField = midstoreFieldsDic.get(dataField.getKey())).isEncrypted()) {
                    deFieldInfo.setIsEncrypted(mField.isEncrypted());
                    deFieldInfo.setPrecision(this.encryptedFieldService.getCiphertextMaxLength(precisoin));
                }
                deFieldList.add(deFieldInfo);
            }
            DETableModel bizTableModel = dataExchangeTask.createBizTable(bizTableInfo, deFieldList);
            ArrayList<String> AIndexNames = new ArrayList<String>();
            AIndexNames.add("DATATIME");
            AIndexNames.add("MDCODE");
            boolean needIndex = true;
            List indexlist = bizTableModel.getDeIndexInfos();
            for (DEIndexInfo deIndexInfo : indexlist) {
                if (!StringUtils.isNotEmpty((String)deIndexInfo.getIndexName()) || !deIndexInfo.getIndexName().startsWith("IDX_FLOATDATA_")) continue;
                needIndex = false;
                break;
            }
            if (needIndex) {
                dataExchangeTask.createIndex("IDX_FLOATDATA_" + OrderGenerator.newOrder(), bizTableInfo.getName(), AIndexNames);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }
}

