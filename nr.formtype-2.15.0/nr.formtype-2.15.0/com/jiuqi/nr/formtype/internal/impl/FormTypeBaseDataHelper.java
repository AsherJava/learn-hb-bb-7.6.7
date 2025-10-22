/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$MoveType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.StorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.bi.util.Html;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.common.RelatedState;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDataDefineImpl;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.StorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormTypeBaseDataHelper {
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private OrgDataClient orgDataClient;

    public String saveDefine(FormTypeDefine formType) {
        String baseDataId = this.saveBaseDataDefine(formType);
        DataModelDO origalDataModel = this.createDataModel(formType);
        DataModelDO dataModelDO = this.getDataModel(formType);
        origalDataModel.setColumns(StorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        this.dataModelClient.push(origalDataModel);
        return baseDataId;
    }

    private String saveBaseDataDefine(FormTypeDefine formType) {
        BaseDataDefineDO oldDefine = this.getBaseDataDefine(formType.getCode());
        if (oldDefine == null) {
            return this.doAddBaseDataDefine(formType);
        }
        return this.doUpdateBaseDataDefine(formType, oldDefine);
    }

    private String doUpdateBaseDataDefine(FormTypeDefine formType, BaseDataDefineDO oldDefine) {
        BaseDataDefineDTO defineDTO = this.createBaseData(formType);
        defineDTO.addExtInfo("onlyEditBasicInfo", (Object)true);
        this.baseDataDefineClient.upate(defineDTO);
        return oldDefine.getId().toString();
    }

    private String doAddBaseDataDefine(FormTypeDefine formType) {
        BaseDataDefineDTO baseDataDefineDTO = this.createBaseData(formType);
        UUID id = UUID.randomUUID();
        baseDataDefineDTO.setId(id);
        this.baseDataDefineClient.add(baseDataDefineDTO);
        return id.toString();
    }

    private BaseDataDefineDTO createBaseData(FormTypeDefine formType) {
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setName(formType.getCode());
        baseDataDefineDTO.setTitle(formType.getTitle());
        baseDataDefineDTO.setRemark(formType.getDesc());
        baseDataDefineDTO.setTenantName("__default_tenant__");
        baseDataDefineDTO.setGroupname("FORMTYPE");
        baseDataDefineDTO.setStructtype(Integer.valueOf(0));
        baseDataDefineDTO.setSharetype(Integer.valueOf(0));
        baseDataDefineDTO.setVersionflag(Integer.valueOf(0));
        baseDataDefineDTO.setSolidifyflag(Integer.valueOf(1));
        baseDataDefineDTO.setCachedisabled(Integer.valueOf(0));
        baseDataDefineDTO.setReadonly(Integer.valueOf(1));
        baseDataDefineDTO.setDefaultShowColumns(this.getDefaultShowColumns());
        baseDataDefineDTO.setModifytime(new Date());
        return baseDataDefineDTO;
    }

    public BaseDataDefineDO getBaseDataDefine(String code) {
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName("__default_tenant__");
        baseDataDefineDTO.setName(code);
        return this.baseDataDefineClient.get(baseDataDefineDTO);
    }

    private List<Map<String, Object>> getDefaultShowColumns() {
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)"\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)true, (Boolean)true, (Boolean)true));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)"\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"SHORTNAME", (String)"\u7b80\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"ICON", (String)"\u56fe\u6807", (String)DataModelType.ColumnType.CLOB.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"UNIT_NATURE", (String)"\u5355\u4f4d\u6027\u8d28", (String)DataModelType.ColumnType.INTEGER.name(), (Boolean)true, (Boolean)true, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"UPDATETIME", (String)"\u66f4\u65b0\u65f6\u95f4", (String)DataModelType.ColumnType.TIMESTAMP.name(), (Boolean)false, (Boolean)true, (Boolean)false, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME_EN_US", (String)"\u82f1\u6587\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        return fields;
    }

    private DataModelDO createDataModel(FormTypeDefine formType) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setTenantName("__default_tenant__");
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setGroupcode("FORMTYPE");
        dataModelDO.setName(formType.getCode());
        dataModelDO.setTitle(formType.getTitle());
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate((String)"basedata", (String)formType.getCode());
        dataModelDO.setColumns(template.getTemplateFields());
        dataModelDO.addColumn("ICON").columnTitle("\u56fe\u6807").columnType(DataModelType.ColumnType.CLOB).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("UNIT_NATURE").columnTitle("\u5355\u4f4d\u6027\u8d28").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.FIXED).nullable(Boolean.valueOf(false)).defaultVal("0");
        dataModelDO.addColumn("UPDATETIME").columnTitle("\u66f4\u65b0\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("NAME_EN_US").columnTitle("\u82f1\u6587\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.setIndexConsts(template.getTemplateIndexs());
        return dataModelDO;
    }

    private DataModelDO getDataModel(FormTypeDefine formType) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(formType.getCode());
        dataModelDTO.setTenantName("__default_tenant__");
        return this.dataModelClient.get(dataModelDTO);
    }

    public void deleteDefine(FormTypeDefine formType) {
        DataModelDO dataModelDO = this.getDataModel(formType);
        if (null != dataModelDO) {
            this.dataModelClient.remove(dataModelDO);
        }
        BaseDataDefineDTO basedataDO = new BaseDataDefineDTO();
        basedataDO.setTenantName("__default_tenant__");
        basedataDO.setName(formType.getCode());
        this.baseDataDefineClient.remove(basedataDO);
    }

    public boolean checkCode(String code) {
        return null != this.getBaseDataDefine(code.toUpperCase());
    }

    public void addData(FormTypeDataDefine data) throws JQException {
        BaseDataDO baseData = this.getData(data.getFormTypeCode(), data.getCode(), true);
        if (null != baseData) {
            this.updateData(data);
        } else {
            this.addBaseData(data);
        }
    }

    private void addBaseData(FormTypeDataDefine data) throws JQException {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setId(data.getId());
        basedataDTO.setTableName(data.getFormTypeCode());
        basedataDTO.setCode(data.getCode());
        basedataDTO.setName(data.getName());
        basedataDTO.setShortname(data.getShortname());
        basedataDTO.put("ICON".toLowerCase(), (Object)data.getIcon());
        if (null != data.getUnitNatrue()) {
            basedataDTO.put("UNIT_NATURE".toLowerCase(), (Object)data.getUnitNatrue().getValue());
        }
        basedataDTO.put("UPDATETIME".toLowerCase(), (Object)new Date());
        R result = this.baseDataClient.add(basedataDTO);
        if (0 != result.getCode()) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, result.getMsg());
        }
    }

    public void addData(String formTypeCode, List<FormTypeDataDefine> datas) throws JQException {
        BaseDataBatchOptDTO batchOpt = new BaseDataBatchOptDTO();
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(formTypeCode);
        batchOpt.setQueryParam(basedataDTO);
        List dataList = datas.stream().map(d -> {
            BaseDataDO data = new BaseDataDO();
            data.setId(d.getId());
            data.setCode(d.getCode());
            data.setName(d.getName());
            data.setShortname(d.getShortname());
            data.put("ICON".toLowerCase(), (Object)d.getIcon());
            if (null != d.getUnitNatrue()) {
                data.put("UNIT_NATURE".toLowerCase(), (Object)d.getUnitNatrue().getValue());
            }
            basedataDTO.put("UPDATETIME".toLowerCase(), (Object)new Date());
            return data;
        }).collect(Collectors.toList());
        batchOpt.setDataList(dataList);
        R result = this.baseDataClient.batchAdd(batchOpt);
        if (0 != result.getCode()) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, result.getMsg());
        }
    }

    public void batchUpdateData(List<FormTypeDataDefine> datas) throws JQException {
        BaseDataBatchOptDTO opt = new BaseDataBatchOptDTO();
        opt.setDataList(datas.stream().map(this::toBaseData).collect(Collectors.toList()));
        R result = this.baseDataClient.batchUpdate(opt);
        if (0 != result.getCode()) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_DATA_ERROR, result.getMsg());
        }
    }

    public void updateData(FormTypeDataDefine data) throws JQException {
        BaseDataDTO basedataDTO = this.toBaseData(data);
        R result = this.baseDataClient.update(basedataDTO);
        if (0 != result.getCode()) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_DATA_ERROR, result.getMsg());
        }
    }

    private BaseDataDTO toBaseData(FormTypeDataDefine data) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(data.getFormTypeCode());
        basedataDTO.setCode(data.getCode());
        basedataDTO.setName(data.getName());
        basedataDTO.setShortname(data.getShortname());
        if (null != data.getOrdinal()) {
            basedataDTO.setOrdinal(data.getOrdinal());
        }
        basedataDTO.setRecoveryflag(Integer.valueOf(0));
        basedataDTO.put("ICON".toLowerCase(), (Object)data.getIcon());
        if (null != data.getUnitNatrue()) {
            basedataDTO.put("UNIT_NATURE".toLowerCase(), (Object)data.getUnitNatrue().getValue());
        }
        basedataDTO.put("UPDATETIME".toLowerCase(), (Object)new Date());
        basedataDTO.put("NAME_EN_US".toLowerCase(), (Object)data.getNameEnUS());
        return basedataDTO;
    }

    public void deleteData(FormTypeDataDefine data) throws JQException {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(data.getFormTypeCode());
        basedataDTO.setId(data.getId());
        basedataDTO.setConfrimRelatedRemove(Boolean.valueOf(true));
        R result = this.baseDataClient.remove(basedataDTO);
        if (0 != result.getCode()) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DELETE_DATA_ERROR, result.getMsg());
        }
    }

    public BaseDataDO getData(String formTypeCode, String dataCode, boolean removed) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(formTypeCode);
        baseDataDTO.setCode(dataCode);
        if (removed) {
            baseDataDTO.setRecoveryflag(Integer.valueOf(1));
        }
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        if (pageVO != null && pageVO.getTotal() > 0) {
            return (BaseDataDO)pageVO.getRows().get(0);
        }
        return null;
    }

    public BaseDataDO getData(String formTypeCode, String dataCode) {
        return this.getData(formTypeCode, dataCode, false);
    }

    public void deleteData(List<FormTypeDataDefine> datas) throws JQException {
        for (FormTypeDataDefine data : datas) {
            this.deleteData(data);
        }
    }

    public List<FormTypeDataDefine> getFormTypeDataDefines(String formTypeCode) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(formTypeCode);
        baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        if (pageVO != null && pageVO.getTotal() > 0) {
            return pageVO.getRows().stream().map(data -> this.toFormTypeDataDefine((BaseDataDO)data, formTypeCode)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public FormTypeDataDefine getFormTypeDataDefine(String formTypeCode, String dataCode) {
        BaseDataDO data = this.getData(formTypeCode, dataCode);
        if (null == data) {
            return null;
        }
        return this.toFormTypeDataDefine(data, formTypeCode);
    }

    private FormTypeDataDefine toFormTypeDataDefine(BaseDataDO baseData, String formTypeCode) {
        FormTypeDataDefineImpl define = new FormTypeDataDefineImpl();
        define.setId(baseData.getId());
        define.setCode(baseData.getCode());
        define.setName(baseData.getName());
        define.setNameEnUS((String)FormTypeBaseDataHelper.getOrDefaultPlus((Map<String, Object>)baseData, "NAME_EN_US".toLowerCase(), ""));
        define.setShortname(baseData.getShortname());
        define.setOrdinal(baseData.getOrdinal());
        define.setIcon((String)FormTypeBaseDataHelper.getOrDefaultPlus((Map<String, Object>)baseData, "ICON".toLowerCase(), ""));
        define.setUnitNatrue(UnitNature.valueOf(FormTypeBaseDataHelper.getIntOrDefaultPlus((Map<String, Object>)baseData, "UNIT_NATURE".toLowerCase(), 0)));
        define.setUpdateTime((Date)FormTypeBaseDataHelper.getOrDefaultPlus((Map<String, Object>)baseData, "UPDATETIME".toLowerCase(), new Date()));
        define.setFormTypeCode(formTypeCode);
        return define;
    }

    private static int getIntOrDefaultPlus(Map<String, Object> map, String key, int defaultValue) {
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal)value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    private static Object getOrDefaultPlus(Map<String, Object> map, String key, Object defaultValue) {
        if (null == map) {
            return defaultValue;
        }
        Object object = map.get(key);
        return null == object ? defaultValue : object;
    }

    public void getFormTypeFullInfo(FormTypeDefine formType) throws JQException {
        BaseDataDefineDO baseDataDefine = this.getBaseDataDefine(formType.getCode());
        if (null == baseDataDefine) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.BASEDATA_NOTEXISTS_ERROR);
        }
        formType.setTitle(baseDataDefine.getTitle());
        formType.setDesc(baseDataDefine.getRemark());
        formType.setUpdateTime(baseDataDefine.getModifytime());
    }

    public void moveData(UUID id, String tableName, boolean isUp) {
        BaseDataMoveDTO dto = new BaseDataMoveDTO();
        dto.setId(id);
        dto.setTableName(tableName);
        dto.setMoveType(isUp ? BaseDataOption.MoveType.UP : BaseDataOption.MoveType.DOWN);
        BaseDataDTO baseDataDto = new BaseDataDTO();
        baseDataDto.setTableName(tableName);
        dto.setQueryParam(baseDataDto);
        this.baseDataClient.move(dto);
    }

    public boolean checkDataCode(String tableName, String dataCode) {
        BaseDataDTO baseDataDto = new BaseDataDTO();
        baseDataDto.setTableName(tableName.toUpperCase());
        baseDataDto.setCode(dataCode);
        baseDataDto.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO list = this.baseDataClient.list(baseDataDto);
        return null != list && !CollectionUtils.isEmpty(list.getRows());
    }

    public boolean checkRelated(String baseDataCode, boolean checkOrgData) {
        if (!StringUtils.hasText(baseDataCode)) {
            return false;
        }
        OrgCategoryDO params = new OrgCategoryDO();
        List orgCategorys = this.orgCategoryClient.list(params).getRows();
        block0: for (OrgCategoryDO orgCategory : orgCategorys) {
            for (ZB zb : orgCategory.getZbs()) {
                if (!baseDataCode.equalsIgnoreCase(zb.getReltablename())) continue;
                if (!checkOrgData) {
                    return true;
                }
                if (!this.checkOrgExsitData(orgCategory.getName())) continue block0;
                return true;
            }
        }
        return false;
    }

    public RelatedState checkRelated(String baseDataCode) {
        RelatedState state = RelatedState.NONE;
        if (!StringUtils.hasText(baseDataCode)) {
            return state;
        }
        OrgCategoryDO params = new OrgCategoryDO();
        List orgCategorys = this.orgCategoryClient.list(params).getRows();
        block0: for (OrgCategoryDO orgCategory : orgCategorys) {
            for (ZB zb : orgCategory.getZbs()) {
                if (!baseDataCode.equalsIgnoreCase(zb.getReltablename())) continue;
                state = RelatedState.RELATED;
                if (!this.checkOrgExsitData(orgCategory.getName())) continue;
                state = RelatedState.USED;
                continue block0;
            }
        }
        return state;
    }

    public boolean checkOrgExsitData(String categoryname) {
        Html.cleanName((String)categoryname, (char[])new char[0]);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(categoryname);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setLimit(Integer.valueOf(2));
        PageVO list = this.orgDataClient.list(orgDTO);
        return !list.getRows().isEmpty();
    }

    public void addDefaultBaseDataGroup() {
        BaseDataGroupDTO groupDTO = new BaseDataGroupDTO();
        groupDTO.setName("NR_GROUP");
        PageVO list = this.baseDataDefineClient.list(groupDTO);
        if (list.getTotal() == 0) {
            groupDTO.setTitle("\u62a5\u8868");
            groupDTO.setParentname("-");
            this.baseDataDefineClient.add(groupDTO);
        }
        groupDTO = new BaseDataGroupDTO();
        groupDTO.setName("FORMTYPE");
        list = this.baseDataDefineClient.list(groupDTO);
        if (list.getTotal() == 0) {
            groupDTO.setTitle("\u62a5\u8868\u7c7b\u578b");
            groupDTO.setParentname("NR_GROUP");
            this.baseDataDefineClient.add(groupDTO);
        }
    }
}

