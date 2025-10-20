/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.cache.BaseDataDefineCache
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.value.MissingObjectException
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.va.bill.bd.bill.impl;

import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.intf.BillToMasterDataIntf;
import com.jiuqi.va.bill.bd.bill.model.BasedataApplyBillModel;
import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.bill.bd.bill.model.CommonMethodModel;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.cache.BaseDataDefineCache;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class BillToBaseDataImpl
implements BillToMasterDataIntf {
    private static final Logger logger = LoggerFactory.getLogger(BillToBaseDataImpl.class);
    public static final String BASEDATANAME = "BASEDATANAME";
    public static final String CODE = "CODE";
    public static final String NAME = "NAME";
    public static final String PARENTCODE = "PARENTCODE";
    public static final String UNICODE = "UNITCODE";
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public String getType() {
        return "basedata";
    }

    @Override
    public boolean isEnable(String defineName) {
        return defineName.startsWith("MD_") && !defineName.startsWith("MD_ORG_") && !defineName.equals("MD_ORG");
    }

    @Override
    public void beforeCheckByApply(BasedataApplyBillModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas) {
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        if (baseDataDefine == null) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(fieldMap.get(CODE))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.mapping.not.empty"));
        }
        if (!StringUtils.hasText(fieldMap.get(NAME))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.mapping.not.empty"));
        }
        DataRow masterData = model.getMaster();
        if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
            this.applyCreateDefineCheck(fieldMap, defineName, masterData, baseDataDefine);
        } else if (mapDefine.getCreatetype() == 3 || mapDefine.getCreatetype() == 5) {
            this.applyChangeDefineCheck(itemData, mapDefine, fieldMap, treeCodes, applyBillDatas, baseDataDefine, masterData);
        }
    }

    private void applyChangeDefineCheck(Map<String, Object> itemData, ApplyRegMapDO mapDefine, Map<String, String> fieldMap, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas, BaseDataDefineDO baseDataDefine, DataRow masterData) {
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get(CODE)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.code.value.not.empty"));
        }
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get(NAME)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.name.value.not.empty"));
        }
        Integer structtype = baseDataDefine.getStructtype();
        if (structtype == 2 || structtype == 3) {
            this.checkTreeBaseData(masterData, fieldMap, itemData, treeCodes, applyBillDatas, mapDefine, baseDataDefine);
        }
    }

    private void applyCreateDefineCheck(Map<String, String> fieldMap, String defineName, DataRow masterData, BaseDataDefineDO baseDataDefine) {
        if (!StringUtils.hasText(masterData.getString(fieldMap.get(CODE)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.value.not.empty"));
        }
        if (!StringUtils.hasText(masterData.getString(fieldMap.get(NAME)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.value.not.empty"));
        }
        Integer structtype = baseDataDefine.getStructtype();
        if ((structtype == 2 || structtype == 3) && StringUtils.hasText(fieldMap.get(PARENTCODE)) && StringUtils.hasText(masterData.getString(fieldMap.get(PARENTCODE)))) {
            String parentcode = masterData.getString(fieldMap.get(PARENTCODE));
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setObjectcode(parentcode);
            baseDataDTO.setTableName(defineName);
            R exist = this.baseDataClient.exist(baseDataDTO);
            if (exist.getCode() != 0) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.server.error") + exist.getMsg());
            }
            if (!((Boolean)exist.get((Object)"exist")).booleanValue()) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
    }

    @Override
    public void beforeCheckByAlter(BillAlterModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, List<Map<String, Object>> applyBillDatas) {
        DataRow masterData = model.getMaster();
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        if (baseDataDefine == null) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(fieldMap.get(CODE))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.mapping.not.empty"));
        }
        if (!StringUtils.hasText(fieldMap.get(NAME))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.mapping.not.empty"));
        }
        if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
            this.alterCreateDefineCheck(fieldMap, defineName, masterData, baseDataDefine);
        } else if (mapDefine.getCreatetype() == 3 || mapDefine.getCreatetype() == 5) {
            this.alterChangeDefineCheck(itemData, fieldMap, defineName, baseDataDefine, masterData);
        }
    }

    private void alterChangeDefineCheck(Map<String, Object> itemData, Map<String, String> fieldMap, String defineName, BaseDataDefineDO baseDataDefine, DataRow masterData) {
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get(CODE)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.code.value.not.empty"));
        }
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get(NAME)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.name.value.not.empty"));
        }
        Integer structtype = baseDataDefine.getStructtype();
        if ((structtype == 2 || structtype == 3) && StringUtils.hasText(fieldMap.get(PARENTCODE)) && !ObjectUtils.isEmpty(itemData.get(fieldMap.get(PARENTCODE)))) {
            String parentcode = itemData.get(fieldMap.get(PARENTCODE)).toString();
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setObjectcode(parentcode);
            baseDataDTO.setTableName(defineName);
            R exist = this.baseDataClient.exist(baseDataDTO);
            if (exist.getCode() != 0) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.server.error") + exist.getMsg());
            }
            if (!((Boolean)exist.get((Object)"exist")).booleanValue()) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
        if (!this.checkDataExist(baseDataDefine, masterData, itemData, false, fieldMap)) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.basedata.code.not.exist", new Object[]{itemData.get(fieldMap.get(CODE)).toString()}));
        }
    }

    private void alterCreateDefineCheck(Map<String, String> fieldMap, String defineName, DataRow masterData, BaseDataDefineDO baseDataDefine) {
        if (!StringUtils.hasText(masterData.getString(fieldMap.get(CODE)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.value.not.empty"));
        }
        if (!StringUtils.hasText(masterData.getString(fieldMap.get(NAME)))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.value.not.empty"));
        }
        Integer structtype = baseDataDefine.getStructtype();
        if ((structtype == 2 || structtype == 3) && StringUtils.hasText(fieldMap.get(PARENTCODE)) && StringUtils.hasText(masterData.getString(fieldMap.get(PARENTCODE)))) {
            String parentcode = masterData.getString(fieldMap.get(PARENTCODE));
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setObjectcode(parentcode);
            baseDataDTO.setTableName(defineName);
            R exist = this.baseDataClient.exist(baseDataDTO);
            if (exist.getCode() != 0) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.server.error") + exist.getMsg());
            }
            if (!((Boolean)exist.get((Object)"exist")).booleanValue()) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
        if (!this.checkDataExist(baseDataDefine, masterData, null, true, fieldMap)) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.basedata.code.not.exist", new Object[]{masterData.getString(fieldMap.get(CODE))}));
        }
    }

    private void checkTreeBaseData(DataRow masterData, Map<String, String> reFieldMap, Map<String, Object> applyitemValue, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas, ApplyRegMapDO mapDefine, BaseDataDefineDO baseDataDefineDO) {
        if (!StringUtils.hasText(reFieldMap.get(PARENTCODE)) || ObjectUtils.isEmpty(applyitemValue.get(reFieldMap.get(PARENTCODE)))) {
            return;
        }
        String parentcode = (String)applyitemValue.get(reFieldMap.get(PARENTCODE));
        String basedataname = (String)applyitemValue.get(reFieldMap.get(BASEDATANAME));
        boolean aBoolean = this.checkDataExist(baseDataDefineDO, masterData, applyitemValue, null, reFieldMap);
        if (aBoolean) {
            return;
        }
        if (!CollectionUtils.isEmpty(treeCodes) && treeCodes.get(mapDefine.getId()).containsKey(basedataname)) {
            List<String> list = treeCodes.get(mapDefine.getId()).get(basedataname);
            if (!list.contains(parentcode)) {
                throw new BillException("\u5b50\u8868PARENTCODE \u4e0d\u5b58\u5728" + parentcode);
            }
        } else {
            LinkedList<String> codes = new LinkedList<String>();
            try {
                for (Map<String, Object> applyBillData : applyBillDatas) {
                    if (!applyBillData.get(reFieldMap.get(BASEDATANAME)).equals(basedataname)) continue;
                    codes.add((String)applyBillData.get(reFieldMap.get(CODE)));
                }
                if (treeCodes.containsKey(mapDefine.getId())) {
                    treeCodes.get(mapDefine.getId()).put(basedataname, codes);
                } else {
                    HashMap<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
                    map.put(basedataname, codes);
                    treeCodes.put(mapDefine.getId(), map);
                }
                if (!codes.contains(parentcode)) {
                    throw new BillException("\u5b50\u8868PARENTCODE \u4e0d\u5b58\u5728" + parentcode);
                }
            }
            catch (Exception e) {
                logger.error("\u6811\u5f62\u57fa\u7840\u6570\u636e\u6821\u9a8c\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
                throw new BillException("\u6811\u5f62\u57fa\u7840\u6570\u636e\u6821\u9a8c\u5931\u8d25" + e.getMessage());
            }
        }
    }

    @Override
    public void beforeCheckByCreate(DataRow dataRow, String defineName) {
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        if (baseDataDefine == null) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(dataRow.getString(NAME))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.value.not.empty"));
        }
        if (!StringUtils.hasText(dataRow.getString(CODE))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.value.not.empty"));
        }
        Integer structtype = baseDataDefine.getStructtype();
        if ((structtype == 2 || structtype == 3) && StringUtils.hasText(dataRow.getString(PARENTCODE))) {
            String parentcode = dataRow.getString(PARENTCODE);
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setObjectcode(parentcode.toUpperCase());
            baseDataDTO.setTableName(defineName.toUpperCase());
            R exist = this.baseDataClient.exist(baseDataDTO);
            if (exist.getCode() != 0) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.server.error") + exist.getMsg());
            }
            if (!((Boolean)exist.get((Object)"exist")).booleanValue()) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
    }

    @Override
    public boolean checkDataExist(DataRow dataRow, Map<String, Object> applyitemValue, Boolean bool, Map<String, String> reFieldMap, String defineName) {
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        return this.checkDataExist(baseDataDefine, dataRow, applyitemValue, bool, reFieldMap);
    }

    private boolean checkDataExist(BaseDataDefineDO baseDataDefine, DataRow dataRow, Map<String, Object> applyitemValue, Boolean bool, Map<String, String> reFieldMap) {
        if (baseDataDefine == null) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTenantName(ShiroUtil.getTenantName());
        baseDataDTO.setTableName(baseDataDefine.getName());
        if (bool == null) {
            baseDataDTO.setCode(applyitemValue.get(reFieldMap.get(PARENTCODE)).toString().toUpperCase());
            baseDataDTO.setUnitcode(applyitemValue.get(UNICODE) == null ? dataRow.getString(UNICODE) : applyitemValue.get(UNICODE).toString());
        } else if (bool.booleanValue()) {
            baseDataDTO.setCode(dataRow.getString(reFieldMap.get(CODE)).toUpperCase());
            baseDataDTO.setUnitcode(dataRow.getString(UNICODE));
        } else {
            baseDataDTO.setCode(applyitemValue.get(reFieldMap.get(CODE)).toString().toUpperCase());
            baseDataDTO.setUnitcode(applyitemValue.get(UNICODE) == null ? dataRow.getString(UNICODE) : applyitemValue.get(UNICODE).toString());
        }
        if (!baseDataDefine.getSharetype().equals(0)) {
            BillToBaseDataImpl.handleDimValue(baseDataDefine, dataRow, applyitemValue, bool, baseDataDTO);
        }
        if (!StringUtils.hasText(baseDataDTO.getUnitcode())) {
            baseDataDTO.setUnitcode(ShiroUtil.getUser().getLoginUnit());
        }
        R exist = this.baseDataClient.exist(baseDataDTO);
        return (Boolean)exist.get((Object)"exist");
    }

    private static void handleDimValue(BaseDataDefineDO baseDataDefine, DataRow dataRow, Map<String, Object> applyitemValue, Boolean bool, BaseDataDTO baseDataDTO) {
        String sharefieldnames = baseDataDefine.getSharefieldname();
        String[] strings = sharefieldnames.split(",");
        if (bool == null || !bool.booleanValue()) {
            for (String string : strings) {
                Object value = applyitemValue.get(string);
                if (value == null) {
                    try {
                        value = dataRow.getString(string);
                    }
                    catch (MissingObjectException e) {
                        continue;
                    }
                }
                baseDataDTO.put(string.toLowerCase(), value);
            }
        } else {
            for (String string : strings) {
                try {
                    baseDataDTO.put(string.toLowerCase(), (Object)dataRow.getString(string));
                }
                catch (MissingObjectException missingObjectException) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public Object createMasterData(RegistrationBillModel model, String defineName) {
        DataRow dataRow = model.getMaster();
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        ArrayList<BaseDataDO> list = new ArrayList<BaseDataDO>();
        BaseDataDO baseData = this.createBaseData(dataRow, model);
        list.add(baseData);
        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
        baseDataBatchOptDTO.setDataList(list);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(defineName);
        baseDataBatchOptDTO.setQueryParam(queryParam);
        if (!baseDataDefine.getSharetype().equals(0)) {
            String sharefieldnames = baseDataDefine.getSharefieldname();
            String[] strings = sharefieldnames.split(",");
            for (int i = 0; i < strings.length; ++i) {
                queryParam.put(strings[i].toLowerCase(), (Object)dataRow.getString(strings[i]));
            }
        }
        if (!StringUtils.hasText(queryParam.getUnitcode())) {
            queryParam.setUnitcode(ShiroUtil.getUser().getLoginUnit());
        }
        if (BaseDataConsts.BASEDATA_SHARETYPE_ISOLATIONANDSHARE.equals(baseDataDefine.getSharetype())) {
            boolean isShare = false;
            try {
                isShare = dataRow.getBoolean("SHAREFLAG");
            }
            catch (MissingObjectException missingObjectException) {
                // empty catch block
            }
            if (isShare) {
                queryParam.setUnitcode("-");
            }
        }
        return baseDataBatchOptDTO;
    }

    private BaseDataDO createBaseData(DataRow row, RegistrationBillModel model) {
        String baseDataName = row.getString(BASEDATANAME).toUpperCase();
        DataRowImpl dataRow = (DataRowImpl)row;
        BaseDataDO baseData = new BaseDataDO();
        baseData.setTableName(baseDataName);
        baseData.setTenantName(ShiroUtil.getTenantName());
        Map rowData = dataRow.getData(false);
        NamedContainer fields = model.getMasterTable().getFields();
        HashMap<String, DataModelType.ColumnType> colMap = new HashMap<String, DataModelType.ColumnType>();
        DataModelDO dataModelDO = CommonMethodModel.getDataModelDefine(baseDataName);
        if (dataModelDO == null) {
            logger.error("\u6570\u636e\u5efa\u6a21\u5b9a\u4e49\u4e3a\u7a7a:" + baseDataName);
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.save.error.define.not.exist") + baseDataName);
        }
        List bdColumns = dataModelDO.getColumns();
        for (DataModelColumn col : bdColumns) {
            colMap.put(col.getColumnName().toLowerCase(), col.getColumnType());
        }
        if (rowData.get(CODE) == null) {
            baseData.put("code", rowData.get("BILLCODE"));
        }
        for (String s : colMap.keySet()) {
            BillToBaseDataImpl.setBasedataFields(s, rowData, baseData, (NamedContainer<? extends DataField>)fields, dataRow, colMap);
        }
        return baseData;
    }

    private static void setBasedataFields(String s, Map<String, Object> rowData, BaseDataDO baseData, NamedContainer<? extends DataField> fields, DataRowImpl dataRow, Map<String, DataModelType.ColumnType> colMap) {
        String fieldName = s.toLowerCase();
        Object fieldData = rowData.get(s.toUpperCase());
        if (fieldData == null || "VER".equalsIgnoreCase(s) || "CREATETIME".equalsIgnoreCase(s) || "ID".equalsIgnoreCase(s) || "CREATEUSER".equalsIgnoreCase(s)) {
            return;
        }
        if (PARENTCODE.equalsIgnoreCase(s)) {
            String[] split = fieldData.toString().split("\\|\\|");
            baseData.setParentcode(split[0].toUpperCase());
            return;
        }
        if (CODE.equalsIgnoreCase(s)) {
            baseData.setCode(fieldData.toString().toUpperCase());
            return;
        }
        if (fieldData instanceof UUID && ((DataField)fields.get(s.toUpperCase())).getDefine().isMultiChoice()) {
            List multiValue = dataRow.getMultiValue(s.toUpperCase());
            baseData.put(fieldName, (Object)multiValue);
            return;
        }
        if (fieldData instanceof Boolean) {
            if (colMap.get(fieldName) == DataModelType.ColumnType.INTEGER) {
                baseData.put(fieldName, (Object)((Boolean)fieldData != false ? 1 : 0));
            }
        } else if (fieldData instanceof Map) {
            Map lm = (Map)fieldData;
            baseData.put(fieldName, (Object)lm.get("name").toString());
        } else if (fieldData instanceof Date) {
            baseData.put(fieldName, fieldData);
        } else {
            baseData.put(fieldName, (Object)fieldData.toString());
        }
    }

    @Override
    public void syncMasterData(Object syncData, String defineName) {
        BaseDataBatchOptDTO batchDTO = null;
        batchDTO = syncData instanceof BaseDataBatchOptDTO ? (BaseDataBatchOptDTO)syncData : (BaseDataBatchOptDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)syncData), BaseDataBatchOptDTO.class);
        R r = this.baseDataClient.sync(batchDTO);
        if (r.getCode() != 0) {
            logger.error("\u57fa\u7840\u6570\u636e\u4fdd\u5b58\u5931\u8d25" + r.getMsg());
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.save.error") + r.get((Object)"msg"));
        }
        Map results = (Map)((List)r.get((Object)"results")).get(0);
        if (!results.get("code").equals(0)) {
            logger.error("\u57fa\u7840\u6570\u636e\u4fdd\u5b58\u5931\u8d25" + r.getMsg());
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.save.error") + results.get("msg"));
        }
    }

    @Override
    public void delMasterData(Object delData, String defineName) {
        BaseDataDTO dataDTO = null;
        dataDTO = delData instanceof BaseDataDTO ? (BaseDataDTO)delData : (BaseDataDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)delData), BaseDataDTO.class);
        R remove = this.baseDataClient.remove(dataDTO);
        if (remove.getCode() != 0) {
            throw new BillException("\u5220\u9664\u57fa\u7840\u6570\u636e\u5931\u8d25");
        }
    }

    @Override
    public Object getDelMasterData(DataRow dataRow, String defineName) {
        PageVO list;
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(defineName);
        queryParam.setCode(dataRow.getString(CODE).toUpperCase());
        if (!baseDataDefine.getSharetype().equals(0)) {
            String[] strings;
            String sharefieldnames = baseDataDefine.getSharefieldname();
            for (String string : strings = sharefieldnames.split(",")) {
                queryParam.put(string.toLowerCase(), (Object)dataRow.getString(string));
            }
        }
        if (!StringUtils.hasText(queryParam.getUnitcode())) {
            queryParam.setUnitcode(ShiroUtil.getUser().getLoginUnit());
        }
        if ((list = this.baseDataClient.list(queryParam)).getTotal() == 0) {
            throw new BillException("\u57fa\u7840\u6570\u636e\u9879\u4e0d\u5b58\u5728");
        }
        if (list.getTotal() > 1) {
            logger.error("\u57fa\u7840\u6570\u636e\u9879\u91cd\u590d{}", (Object)JSONUtil.toJSONString((Object)list));
            throw new BillException("\u57fa\u7840\u6570\u636e\u9879\u91cd\u590d");
        }
        BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
        BaseDataDTO delete = new BaseDataDTO();
        delete.setId(baseDataDO.getId());
        delete.setTableName(baseDataDO.getTableName());
        return delete;
    }

    @Override
    public boolean checkMasterDataIsChangeByDataRow(Map<String, String> reFieldMap, DataRow currData, DataRow oldData, String defineName) {
        String defineNameField = reFieldMap.get(BASEDATANAME);
        String currDefineName = currData.getString(defineNameField);
        String oldDefineName = oldData.getString(defineNameField);
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        if (!currDefineName.equals(oldDefineName)) {
            return this.checkDataExist(baseDataDefine, oldData, null, true, reFieldMap);
        }
        StringBuilder masterCode = new StringBuilder();
        StringBuilder masterOriginalCode = new StringBuilder();
        masterCode.append(currDefineName).append(":").append(currData.getString(reFieldMap.get(CODE)));
        masterOriginalCode.append(oldDefineName).append(":").append(oldData.getString(reFieldMap.get(CODE)));
        if (baseDataDefine != null && baseDataDefine.getSharetype() != 0) {
            masterCode.append(":");
            masterOriginalCode.append(":");
            String sharefieldname = baseDataDefine.getSharefieldname();
            for (String s : sharefieldname.split(",")) {
                String field = reFieldMap.get(s);
                if (field == null) continue;
                masterCode.append(currData.getString(field)).append(":");
                masterOriginalCode.append(oldData.getString(field)).append(":");
            }
        }
        if (masterCode.toString().contentEquals(masterOriginalCode)) {
            return false;
        }
        return this.checkDataExist(baseDataDefine, oldData, null, true, reFieldMap);
    }

    @Override
    public boolean checkMasterDataIsChangeByDataMap(DataRow master, Map<String, String> reFieldMap, Map<String, Object> currData, Map<String, Object> oldData, String defineName) {
        String defineNameField = reFieldMap.get(BASEDATANAME);
        String currDefineName = (String)currData.get(defineNameField);
        String oldDefineName = (String)oldData.get(defineNameField);
        BaseDataDefineDO currDefine = BaseDataDefineCache.getBaseDataDefine((String)defineName);
        if (!currDefineName.equals(oldDefineName)) {
            return this.checkDataExist(master, oldData, false, reFieldMap, oldDefineName);
        }
        StringBuilder detailCode = new StringBuilder();
        StringBuilder detailOriginalCode = new StringBuilder();
        detailCode.append(currData.get(reFieldMap.get(BASEDATANAME))).append(":").append(currData.get(reFieldMap.get(CODE))).append(currData.get(UNICODE) == null ? master.getString(UNICODE) : currData.get(UNICODE));
        detailOriginalCode.append(oldData.get(reFieldMap.get(BASEDATANAME))).append(":").append(oldData.get(reFieldMap.get(CODE))).append(oldData.get(UNICODE) == null ? master.getString(UNICODE) : oldData.get(UNICODE));
        if (currDefine != null && currDefine.getSharetype() != 0) {
            detailCode.append(":");
            detailOriginalCode.append(":");
            String sharefieldname = currDefine.getSharefieldname();
            for (String s : sharefieldname.split(",")) {
                detailCode.append(currData.get(reFieldMap.get(s))).append(":");
                detailOriginalCode.append(oldData.get(reFieldMap.get(s))).append(":");
            }
        }
        if (detailCode.toString().contentEquals(detailOriginalCode)) {
            return false;
        }
        return this.checkDataExist(currDefine, master, oldData, false, reFieldMap);
    }
}

