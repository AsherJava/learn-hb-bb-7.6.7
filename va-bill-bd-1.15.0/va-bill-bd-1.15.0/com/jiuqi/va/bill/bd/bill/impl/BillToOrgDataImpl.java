/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
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
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
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
public class BillToOrgDataImpl
implements BillToMasterDataIntf {
    private static final Logger logger = LoggerFactory.getLogger(BillToOrgDataImpl.class);
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    public String getType() {
        return "orgdata";
    }

    @Override
    public boolean isEnable(String defineName) {
        return defineName.startsWith("MD_ORG_") || defineName.equals("MD_ORG");
    }

    @Override
    public void beforeCheckByApply(BasedataApplyBillModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas) {
        OrgCategoryDO defineParam = new OrgCategoryDO();
        defineParam.setName(defineName);
        List defineList = this.orgCategoryClient.list(defineParam).getRows();
        if (defineList == null || defineList.size() <= 0) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(fieldMap.get("CODE"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.mapping.not.empty"));
        }
        if (!StringUtils.hasText(fieldMap.get("NAME"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.mapping.not.empty"));
        }
        DataRow masterData = model.getMaster();
        if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
            this.applyCreateOrgDefineCheck(fieldMap, defineName, masterData);
        } else if (mapDefine.getCreatetype() == 3 || mapDefine.getCreatetype() == 5) {
            if (ObjectUtils.isEmpty(itemData.get(fieldMap.get("CODE")))) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.code.value.not.empty"));
            }
            if (ObjectUtils.isEmpty(itemData.get(fieldMap.get("NAME")))) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.name.value.not.empty"));
            }
            this.checkTreeOrgData(masterData, fieldMap, itemData, treeCodes, applyBillDatas, mapDefine, defineName);
        }
    }

    private void applyCreateOrgDefineCheck(Map<String, String> fieldMap, String defineName, DataRow masterData) {
        if (!StringUtils.hasText(masterData.getString(fieldMap.get("CODE")))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.value.not.empty"));
        }
        if (!StringUtils.hasText(masterData.getString(fieldMap.get("NAME")))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.value.not.empty"));
        }
        if (StringUtils.hasText(fieldMap.get("PARENTCODE")) && StringUtils.hasText(masterData.getString(fieldMap.get("PARENTCODE")))) {
            String parentcode = masterData.getString(fieldMap.get("PARENTCODE"));
            OrgDTO param = new OrgDTO();
            param.setCode(parentcode);
            param.setCategoryname(defineName);
            OrgDO org = this.orgDataClient.get(param);
            if (org == null) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
    }

    private void checkTreeOrgData(DataRow masterData, Map<String, String> reFieldMap, Map<String, Object> applyitemValue, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas, ApplyRegMapDO mapDefine, String basedataname) {
        if (!StringUtils.hasText(reFieldMap.get("PARENTCODE")) || ObjectUtils.isEmpty(applyitemValue.get(reFieldMap.get("PARENTCODE")))) {
            return;
        }
        String parentcode = (String)applyitemValue.get(reFieldMap.get("PARENTCODE"));
        boolean aBoolean = this.checkDataExist(masterData, applyitemValue, null, reFieldMap, basedataname);
        if (aBoolean) {
            return;
        }
        if (!CollectionUtils.isEmpty(treeCodes) && treeCodes.get(mapDefine.getId()).containsKey(basedataname)) {
            List<String> list = treeCodes.get(mapDefine.getId()).get(basedataname);
            if (!list.contains(parentcode)) {
                throw new BillException("\u5b50\u8868PARENTCODE \u4e0d\u5b58\u5728" + parentcode);
            }
            return;
        }
        LinkedList<String> codes = new LinkedList<String>();
        try {
            for (Map<String, Object> applyBillData : applyBillDatas) {
                if (!applyBillData.get(reFieldMap.get("BASEDATANAME")).equals(basedataname)) continue;
                codes.add((String)applyBillData.get(reFieldMap.get("CODE")));
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
            logger.error("\u7ec4\u7ec7\u673a\u6784\u6811\u5f62\u6821\u9a8c\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            throw new BillException("\u7ec4\u7ec7\u673a\u6784\u6811\u5f62\u6821\u9a8c\u5931\u8d25" + e.getMessage());
        }
    }

    @Override
    public void beforeCheckByAlter(BillAlterModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, List<Map<String, Object>> applyBillDatas) {
        DataRow masterData = model.getMaster();
        OrgCategoryDO defineParam = new OrgCategoryDO();
        defineParam.setName(defineName);
        List defineList = this.orgCategoryClient.list(defineParam).getRows();
        if (defineList == null || defineList.size() <= 0) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(fieldMap.get("CODE"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.mapping.not.empty"));
        }
        if (!StringUtils.hasText(fieldMap.get("NAME"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.mapping.not.empty"));
        }
        if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
            this.applyCreateOrgDefineCheck(fieldMap, defineName, masterData);
            if (!this.checkDataExist(masterData, null, true, fieldMap, defineName)) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.basedata.code.not.exist", new Object[]{masterData.getString(fieldMap.get("CODE"))}));
            }
        } else if (mapDefine.getCreatetype() == 3 || mapDefine.getCreatetype() == 5) {
            this.alterOrgChangeDefineCheck(itemData, fieldMap, defineName);
            if (!this.checkDataExist(masterData, itemData, false, fieldMap, defineName)) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.basedata.code.not.exist", new Object[]{itemData.get(fieldMap.get("CODE")).toString()}));
            }
        }
    }

    private void alterOrgChangeDefineCheck(Map<String, Object> itemData, Map<String, String> fieldMap, String defineName) {
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get("CODE")))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.code.value.not.empty"));
        }
        if (ObjectUtils.isEmpty(itemData.get(fieldMap.get("NAME")))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.name.value.not.empty"));
        }
        if (StringUtils.hasText(fieldMap.get("PARENTCODE")) && !ObjectUtils.isEmpty(itemData.get(fieldMap.get("PARENTCODE")))) {
            String parentcode = itemData.get(fieldMap.get("PARENTCODE")).toString();
            OrgDTO param = new OrgDTO();
            param.setCode(parentcode);
            param.setCategoryname(defineName);
            OrgDO org = this.orgDataClient.get(param);
            if (org == null) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
    }

    @Override
    public void beforeCheckByCreate(DataRow dataRow, String defineName) {
        OrgCategoryDO defineParam = new OrgCategoryDO();
        defineParam.setName(defineName);
        List defineList = this.orgCategoryClient.list(defineParam).getRows();
        if (defineList == null || defineList.size() <= 0) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.define.not.exist"));
        }
        if (!StringUtils.hasText(dataRow.getString("NAME"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.name.value.not.empty"));
        }
        if (!StringUtils.hasText(dataRow.getString("CODE"))) {
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.code.value.not.empty"));
        }
        if (StringUtils.hasText(dataRow.getString("PARENTCODE"))) {
            String parentcode = dataRow.getString("PARENTCODE");
            OrgDTO param = new OrgDTO();
            param.setCode(parentcode);
            param.setCategoryname(defineName);
            OrgDO org = this.orgDataClient.get(param);
            if (org == null) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.parent.not.exist"));
            }
        }
    }

    @Override
    public boolean checkDataExist(DataRow dataRow, Map<String, Object> applyitemValue, Boolean bool, Map<String, String> reFieldMap, String defineName) {
        OrgDTO param = new OrgDTO();
        param.setTenantName(ShiroUtil.getTenantName());
        param.setCategoryname(defineName);
        if (bool == null) {
            param.setCode(applyitemValue.get(reFieldMap.get("PARENTCODE")).toString().toUpperCase());
        } else if (bool.booleanValue()) {
            param.setCode(dataRow.getString(reFieldMap.get("CODE")).toUpperCase());
        } else {
            param.setCode(applyitemValue.get(reFieldMap.get("CODE")).toString().toUpperCase());
        }
        OrgDO data = this.orgDataClient.get(param);
        return data != null;
    }

    @Override
    public Object createMasterData(RegistrationBillModel model, String defineName) {
        DataRow dataRow = model.getMaster();
        ArrayList<OrgDO> list = new ArrayList<OrgDO>();
        OrgDO orgData = this.createOrgData(dataRow, model);
        list.add(orgData);
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setDataList(list);
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(defineName);
        orgBatchOptDTO.setQueryParam(orgParam);
        return orgBatchOptDTO;
    }

    private OrgDO createOrgData(DataRow row, RegistrationBillModel model) {
        String baseDataName = row.getString("BASEDATANAME").toUpperCase();
        DataRowImpl dataRow = (DataRowImpl)row;
        OrgDO orgData = new OrgDO();
        orgData.setCategoryname(baseDataName);
        orgData.setTenantName(ShiroUtil.getTenantName());
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
        if (rowData.get("CODE") == null) {
            orgData.setCode(dataRow.getString("BILLCODE"));
        }
        for (String s : colMap.keySet()) {
            BillToOrgDataImpl.setOrgDataFields(s, rowData, orgData, (NamedContainer<? extends DataField>)fields, dataRow, colMap);
        }
        return orgData;
    }

    private static void setOrgDataFields(String s, Map<String, Object> rowData, OrgDO orgData, NamedContainer<? extends DataField> fields, DataRowImpl dataRow, Map<String, DataModelType.ColumnType> colMap) {
        String fieldName = s.toLowerCase();
        Object fieldData = rowData.get(s.toUpperCase());
        if (fieldData == null || "ver".equalsIgnoreCase(s) || "createtime".equalsIgnoreCase(s) || "id".equalsIgnoreCase(s) || "createuser".equalsIgnoreCase(s) || "orgcode".equalsIgnoreCase(s) || "invalidtime".equalsIgnoreCase(s) || "validtime".equalsIgnoreCase(s) || "parents".equalsIgnoreCase(s)) {
            return;
        }
        if ("PARENTCODE".equalsIgnoreCase(s)) {
            String[] split = fieldData.toString().split("\\|\\|");
            orgData.setParentcode(split[0].toUpperCase());
            return;
        }
        if ("CODE".equalsIgnoreCase(s)) {
            orgData.setCode(fieldData.toString().toUpperCase());
            return;
        }
        if (fieldData instanceof UUID && ((DataField)fields.get(s.toUpperCase())).getDefine().isMultiChoice()) {
            List multiValue = dataRow.getMultiValue(s.toUpperCase());
            orgData.put(fieldName, (Object)multiValue);
            return;
        }
        if (fieldData instanceof Boolean) {
            if (colMap.get(fieldName) == DataModelType.ColumnType.INTEGER) {
                orgData.put(fieldName, (Object)((Boolean)fieldData != false ? 1 : 0));
            }
        } else if (fieldData instanceof Map) {
            Map lm = (Map)fieldData;
            orgData.put(fieldName, (Object)lm.get("name").toString());
        } else if (fieldData instanceof Date) {
            orgData.put(fieldName, fieldData);
        } else {
            orgData.put(fieldName, (Object)fieldData.toString());
        }
    }

    @Override
    public void syncMasterData(Object syncData, String defineName) {
        OrgBatchOptDTO batchDTO = null;
        batchDTO = syncData instanceof OrgBatchOptDTO ? (OrgBatchOptDTO)syncData : (OrgBatchOptDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)syncData), OrgBatchOptDTO.class);
        R r = this.orgDataClient.sync(batchDTO);
        if (r.getCode() != 0) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u4fdd\u5b58\u5931\u8d25" + r.getMsg());
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.save.error") + r.get((Object)"msg"));
        }
        Map results = (Map)((List)r.get((Object)"results")).get(0);
        if (!results.get("code").equals(0)) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u4fdd\u5b58\u5931\u8d25" + r.getMsg());
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.save.error") + results.get("msg"));
        }
    }

    @Override
    public void delMasterData(Object delData, String defineName) {
    }

    @Override
    public Object getDelMasterData(DataRow dataRow, String defineName) {
        return null;
    }

    @Override
    public boolean checkMasterDataIsChangeByDataRow(Map<String, String> reFieldMap, DataRow currData, DataRow oldData, String defineName) {
        return false;
    }

    @Override
    public boolean checkMasterDataIsChangeByDataMap(DataRow master, Map<String, String> reFieldMap, Map<String, Object> currData, Map<String, Object> oldData, String defineName) {
        return false;
    }
}

