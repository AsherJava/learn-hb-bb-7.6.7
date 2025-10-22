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
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.mapping.web.vo.BaseDataVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package nr.midstore.core.internal.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataFieldService;
import nr.midstore.core.definition.service.IMidstoreBaseDataService;
import nr.midstore.core.internal.publish.service.MidstoreSDKLib;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.publish.service.IMidstorePublishBaseDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstorePublishBaseDataServiceImpl
implements IMidstorePublishBaseDataService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishBaseDataServiceImpl.class);
    @Autowired
    private IMidstoreBaseDataService baseDataService;
    @Autowired
    private IMidstoreBaseDataFieldService baseDataFieldService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;

    @Override
    public void publishBaseDatas(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        MidstoreBaseDataDTO queryParam = new MidstoreBaseDataDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        List<MidstoreBaseDataDTO> baseDatas = this.baseDataService.list(queryParam);
        Map<String, List<MidstoreBaseDataFieldDTO>> baseDataFieldMap = this.getBaseDataFieldByScheme(midstoreScheme.getKey());
        for (MidstoreBaseDataDTO baseData : baseDatas) {
            List<Object> fieldList = null;
            fieldList = baseDataFieldMap.containsKey(baseData.getKey()) ? baseDataFieldMap.get(baseData.getKey()) : new ArrayList();
            this.publishBaseData(baseData, fieldList, dataExchangeTask);
        }
    }

    private void publishBaseData(MidstoreBaseDataDTO baseData, List<MidstoreBaseDataFieldDTO> fieldList, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        BaseDataDefineDO baseDataDo = this.queryBaseDatadefine(baseData.getCode());
        DataModelDO baseDataMode = this.queryBaseDataMode(baseData.getCode());
        HashMap<String, DataModelColumn> coolumnMap = new HashMap<String, DataModelColumn>();
        for (DataModelColumn column : baseDataMode.getColumns()) {
            coolumnMap.put(column.getColumnName(), column);
        }
        HashMap<String, DEFieldInfo> deFieldsMap = new HashMap<String, DEFieldInfo>();
        ArrayList<DEFieldInfo> deFields = new ArrayList<DEFieldInfo>();
        DEFieldInfo deFieldInfo1 = new DEFieldInfo(UUID.randomUUID().toString(), "CODE", "\u4ee3\u7801", DEDataType.STRING, 60, 0);
        DEFieldInfo deFieldInfo2 = new DEFieldInfo(UUID.randomUUID().toString(), "NAME", "\u540d\u79f0", DEDataType.STRING, 200, 0);
        DEFieldInfo deFieldInfo3 = new DEFieldInfo(UUID.randomUUID().toString(), "SHORTNAME", "\u7b80\u79f0", DEDataType.STRING, 100, 0);
        DEFieldInfo deFieldInfo4 = new DEFieldInfo(UUID.randomUUID().toString(), "PARENTCODE", "\u4e0a\u7ea7\u4ee3\u7801", DEDataType.STRING, 60, 0);
        DEFieldInfo deFieldInfo5 = new DEFieldInfo(UUID.randomUUID().toString(), "UNITCODE", "\u552f\u4e00\u4ee3\u7801", DEDataType.STRING, 60, 0);
        deFields.add(deFieldInfo1);
        deFields.add(deFieldInfo2);
        deFields.add(deFieldInfo3);
        deFields.add(deFieldInfo4);
        deFields.add(deFieldInfo5);
        for (DEFieldInfo dETableModel : deFields) {
            deFieldsMap.put(dETableModel.getName(), dETableModel);
        }
        for (MidstoreBaseDataFieldDTO midstoreBaseDataFieldDTO : fieldList) {
            DataModelColumn column = (DataModelColumn)coolumnMap.get(midstoreBaseDataFieldDTO.getCode());
            if (column == null || deFieldsMap.containsKey(midstoreBaseDataFieldDTO.getCode())) continue;
            int precisoin = 0;
            if (column.getLengths() != null && column.getLengths().length > 0) {
                precisoin = column.getLengths()[0];
            }
            if (precisoin <= 0) {
                precisoin = 22;
            }
            int decima = 0;
            if (column.getLengths() != null && column.getLengths().length > 1) {
                decima = column.getLengths()[1];
            }
            DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), column.getColumnName(), column.getColumnTitle(), MidstoreSDKLib.getDEDataTypeByColumn(column.getColumnType()), precisoin, decima);
            deFields.add(deFieldInfo);
            deFieldsMap.put(deFieldInfo.getName(), deFieldInfo);
        }
        try {
            DETableInfo deTableInfo = dataExchangeTask.getTableInfoByName(baseData.getCode());
            if (deTableInfo == null) {
                deTableInfo = new DETableInfo(UUID.randomUUID().toString(), baseData.getCode(), baseData.getTitle(), TableType.MASTER);
            }
            DETableModel dETableModel = dataExchangeTask.createMasterTable(deTableInfo, deFields);
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private BaseDataDefineDO queryBaseDatadefine(String baseName) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(baseName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        BaseDataDefineDO baseDefine = this.baseDataDefineClient.get(param);
        return baseDefine;
    }

    private DataModelDO queryBaseDataMode(String baseName) {
        DataModelDTO param = new DataModelDTO();
        param.setName(baseName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        DataModelDO baseDataModel = this.dataModelClient.get(param);
        return baseDataModel;
    }

    private Map<String, List<MidstoreBaseDataFieldDTO>> getBaseDataFieldByScheme(String schemeKey) {
        MidstoreBaseDataFieldDTO queryFieldParam = new MidstoreBaseDataFieldDTO();
        queryFieldParam.setSchemeKey(schemeKey);
        List<MidstoreBaseDataFieldDTO> baseDataFields = this.baseDataFieldService.list(queryFieldParam);
        HashMap<String, List<MidstoreBaseDataFieldDTO>> baseDataFieldMap = new HashMap<String, List<MidstoreBaseDataFieldDTO>>();
        for (MidstoreBaseDataFieldDTO field : baseDataFields) {
            List<MidstoreBaseDataFieldDTO> fieldList = null;
            if (baseDataFieldMap.containsKey(field.getBaseDataKey())) {
                fieldList = (List)baseDataFieldMap.get(field.getBaseDataKey());
            } else {
                fieldList = new ArrayList();
                baseDataFieldMap.put(field.getBaseDataKey(), fieldList);
            }
            fieldList.add(field);
        }
        return baseDataFieldMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void saveBaseDatas(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        MidstoreBaseDataDTO queryParam = new MidstoreBaseDataDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        List<MidstoreBaseDataDTO> baseDatas = this.baseDataService.list(queryParam);
        Map<String, List<MidstoreBaseDataFieldDTO>> baseDataFieldMap = this.getBaseDataFieldByScheme(midstoreScheme.getKey());
        try {
            for (MidstoreBaseDataDTO baseData : baseDatas) {
                ArrayList<String> fieldNames = new ArrayList<String>();
                fieldNames.add("CODE");
                fieldNames.add("NAME");
                fieldNames.add("SHORTNAME");
                fieldNames.add("PARENTCODE");
                fieldNames.add("UNITCODE");
                List<MidstoreBaseDataFieldDTO> fieldList = null;
                if (baseDataFieldMap.containsKey(baseData.getKey())) {
                    fieldList = baseDataFieldMap.get(baseData.getKey());
                    for (MidstoreBaseDataFieldDTO bField : fieldList) {
                        fieldNames.add(bField.getCode());
                    }
                }
                BaseDataDTO queryParam2 = new BaseDataDTO();
                queryParam2.setTableName(baseData.getCode());
                queryParam2.setStopflag(Integer.valueOf(-1));
                queryParam2.setRecoveryflag(Integer.valueOf(-1));
                queryParam2.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                PageVO queryRes = this.baseDataClient.list(queryParam2);
                Map<String, BaseDataVO> oldbaseDataItems = this.getBaseDataMapping(context, baseData.getCode());
                dataExchangeTask.deleteData(baseData.getCode(), "1=1");
                try (IDataWriter tableWriter = dataExchangeTask.createTableWriter(baseData.getCode(), fieldNames);){
                    for (BaseDataDO oldData : queryRes.getRows()) {
                        String itemMapCode;
                        ArrayList<Object> values = new ArrayList<Object>();
                        String itemCode = oldData.getCode();
                        if (oldbaseDataItems.containsKey(itemCode) && StringUtils.isNotEmpty((String)(itemMapCode = oldbaseDataItems.get(itemCode).getMpCode()))) {
                            itemCode = itemMapCode;
                        }
                        values.add(itemCode);
                        values.add(oldData.getName());
                        values.add(oldData.get((Object)"SHORTNAME".toLowerCase()));
                        values.add(oldData.get((Object)"PARENTCODE".toLowerCase()));
                        values.add(oldData.get((Object)"UNITCODE".toLowerCase()));
                        if (fieldList != null && fieldList.size() > 0) {
                            for (MidstoreBaseDataFieldDTO bField : fieldList) {
                                values.add(oldData.get((Object)bField.getCode().toLowerCase()));
                            }
                        }
                        tableWriter.insert(values.toArray());
                    }
                }
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private Map<String, BaseDataVO> getBaseDataMapping(MidstoreContext context, String baseName) {
        return this.midstoreMappingService.getBaseDataItemMapping(context, baseName);
    }
}

