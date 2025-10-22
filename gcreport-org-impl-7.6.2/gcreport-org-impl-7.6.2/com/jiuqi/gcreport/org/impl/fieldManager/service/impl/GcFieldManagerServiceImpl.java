/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcOrgConst
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.datamodel.template.OrgTemplate
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.organization.domain.ZBDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcOrgConst;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.impl.check.enums.FieldComponentEnum;
import com.jiuqi.gcreport.org.impl.fieldManager.dao.GcFieldManagerDao;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgFieldEO;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.datamodel.template.OrgTemplate;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcFieldManagerServiceImpl
implements GcFieldManagerService {
    @Autowired
    private GcFieldManagerDao fieldManagerDao;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private OrgTemplate orgTemplate;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public GcOrgFieldVO convertFieldEOToVO(GcOrgFieldEO eo) {
        GcOrgFieldVO gcOrgFieldVO = new GcOrgFieldVO();
        BeanUtils.copyProperties((Object)eo, gcOrgFieldVO);
        gcOrgFieldVO.setChecked(eo.getBoxChecked());
        gcOrgFieldVO.setNullable(eo.getEnableNull());
        return gcOrgFieldVO;
    }

    @Override
    public GcOrgFieldEO convertFieldVOToEO(GcOrgFieldVO vo) {
        GcOrgFieldEO gcOrgFieldEO = new GcOrgFieldEO();
        BeanUtils.copyProperties(vo, (Object)gcOrgFieldEO);
        gcOrgFieldEO.setBoxChecked(vo.getChecked());
        gcOrgFieldEO.setEnableNull(vo.getNullable());
        return gcOrgFieldEO;
    }

    @Override
    public List<OrgFiledComponentVO> getFieldComponent(String tableName) {
        return this.getFieldComponent(tableName, true);
    }

    @Override
    public List<GcOrgFieldVO> queryAllFieldsByTableName(String tableName) {
        return this.queryAllFieldsByTableName(tableName, true);
    }

    private List<GcOrgFieldVO> queryAllFieldsByTableName(String tableName, boolean isFilterField) {
        Map<String, String> systemCode = Arrays.stream(GcOrgConst.SYSTEMFIELD).collect(Collectors.toMap(o -> o, o -> o, (v1, v2) -> v1));
        Map<String, GcOrgFieldEO> fieldEOMap = this.fieldManagerDao.queryListByTableName(tableName).stream().collect(Collectors.toMap(GcOrgFieldEO::getCode, o -> o, (p, n) -> n));
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName("__default_tenant__");
        dataModelDTO.setName(tableName);
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = ((DataModelClient)SpringContextUtils.getBean(DataModelClient.class)).get(dataModelDTO);
        List cols = dataModel.getColumns();
        ArrayList result = new ArrayList();
        Map<String, String> excludeMap = Stream.of("RECOVERYFLAG", "STOPFLAG").collect(Collectors.toMap(o -> o, o -> o, (v1, v2) -> v1));
        cols.forEach(modelColumn -> {
            GcOrgFieldEO exist = (GcOrgFieldEO)((Object)((Object)fieldEOMap.get(modelColumn.getColumnName())));
            GcOrgFieldVO vo = new GcOrgFieldVO();
            if (exist == null) {
                boolean checked = false;
                if (!(!systemCode.containsKey(modelColumn.getColumnName()) && modelColumn.isNullable().booleanValue() || excludeMap.containsKey(modelColumn.getColumnName()))) {
                    checked = true;
                }
                vo.setChecked(Integer.valueOf(checked ? 1 : 0));
                vo.setInitName(modelColumn.getColumnTitle());
                vo.setName(modelColumn.getColumnTitle());
                vo.setId(UUIDUtils.newUUIDStr());
                vo.setSortOrder(Double.valueOf(2.147483647E9));
            } else {
                vo.setChecked(exist.getBoxChecked());
                vo.setName(exist.getName());
                vo.setId(exist.getId());
                vo.setSortOrder(exist.getSortOrder());
            }
            vo.setCode(modelColumn.getColumnName());
            vo.setNullable(Integer.valueOf(modelColumn.isNullable() != false ? 1 : 0));
            vo.setRefTableName(tableName);
            result.add(vo);
        });
        return result.stream().filter(gcOrgFieldVO -> {
            List<String> filedNames = Arrays.asList(GcOrgConst.FILTERFIELD);
            if (tableName.equalsIgnoreCase("MD_ORG") && gcOrgFieldVO.getCode().equalsIgnoreCase("ORGTYPEID")) {
                return false;
            }
            if (!isFilterField) {
                return true;
            }
            return !filedNames.contains(gcOrgFieldVO.getCode().toUpperCase());
        }).sorted((o1, o2) -> {
            if (o1.getSortOrder() == null || o2.getSortOrder() == null) {
                return 1;
            }
            return (int)(o1.getSortOrder() - o2.getSortOrder());
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchUpdateFieldVOS(String tableName, List<GcOrgFieldVO> fieldVOS) {
        List<GcOrgFieldEO> eoList = fieldVOS.stream().map(this::convertFieldVOToEO).collect(Collectors.toList());
        List<GcOrgFieldEO> gcOrgFieldEOS = this.fieldManagerDao.queryListByTableName(tableName);
        this.fieldManagerDao.deleteByIds(gcOrgFieldEOS.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        this.fieldManagerDao.save(eoList);
    }

    @Override
    public List<OrgFiledComponentVO> getFieldComponent(String tableName, boolean isFilterField) {
        Map<String, String> stringMap = Arrays.stream(GcOrgConst.SYSTEMFIELD).collect(Collectors.toMap(o -> o, o -> o, (v1, v2) -> v1));
        List<GcOrgFieldEO> gcOrgFieldEOS = this.fieldManagerDao.queryListByTableName(tableName);
        Map<String, GcOrgFieldVO> codeMap = gcOrgFieldEOS.stream().collect(Collectors.toMap(GcOrgFieldEO::getCode, this::convertFieldEOToVO, (v1, v2) -> v1));
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(tableName);
        List zbdtos = this.orgCategoryService.listZB(orgCategoryDO);
        ArrayList<OrgFiledComponentVO> ret = new ArrayList<OrgFiledComponentVO>();
        List<String> readonlyFields = Arrays.asList(GcOrgConst.READONLYFIELD);
        OrgFiledComponentVO codeField = new OrgFiledComponentVO(FieldComponentEnum.INPUT.getCode(), "\u4ee3\u7801", "CODE", false);
        codeField.setReadOnly(Boolean.valueOf(true));
        ret.add(codeField);
        ret.add(new OrgFiledComponentVO(FieldComponentEnum.INPUT.getCode(), "\u540d\u79f0", "NAME", false));
        ret.add(new OrgFiledComponentVO(FieldComponentEnum.INPUT.getCode(), "\u7b80\u79f0", "SHORTNAME", true));
        ret.add(new OrgFiledComponentVO(FieldComponentEnum.ORGBASEDATA.getCode(), "\u7236\u7ea7", "PARENTCODE", true));
        if (codeMap.containsKey("STOPFLAG") && codeMap.get("STOPFLAG").getChecked() == 1) {
            ret.add(new OrgFiledComponentVO(FieldComponentEnum.RADIOGROUP.getCode(), "\u505c\u7528\u72b6\u6001", "STOPFLAG", true));
        }
        if (codeMap.containsKey("VER") && codeMap.get("VER").getChecked() == 1) {
            OrgFiledComponentVO ver = new OrgFiledComponentVO(FieldComponentEnum.DATEPICKER.getCode(), "\u884c\u7248\u672c", "VER", true);
            ver.setReadOnly(Boolean.valueOf(true));
            ver.setFormat("yyyy-MM-dd HH:mm:ss");
            ret.add(ver);
        }
        if (codeMap.containsKey("RECOVERYFLAG") && codeMap.get("RECOVERYFLAG").getChecked() == 1) {
            ret.add(new OrgFiledComponentVO(FieldComponentEnum.RADIOGROUP.getCode(), "\u4f5c\u5e9f\u72b6\u6001", "RECOVERYFLAG", true));
        }
        if (codeMap.containsKey("ORDINAL") && codeMap.get("ORDINAL").getChecked() == 1) {
            ret.add(new OrgFiledComponentVO(FieldComponentEnum.INPUT.getCode(), "\u6392\u5e8f", "ORDINAL", true));
        }
        for (ZBDTO zbdto : zbdtos) {
            GcOrgFieldVO saveFieldVO = codeMap.get(zbdto.getName());
            OrgFiledComponentVO orgFiledComponentVO2 = new OrgFiledComponentVO();
            orgFiledComponentVO2.setCode(zbdto.getName());
            orgFiledComponentVO2.setLabel(zbdto.getTitle());
            String reltablename = zbdto.getReltablename();
            orgFiledComponentVO2.setRefTableName(reltablename);
            if (zbdto.getName().equalsIgnoreCase("BASEUNITID") || zbdto.getName().equalsIgnoreCase("DIFFUNITID")) {
                orgFiledComponentVO2.setRefTableName(tableName);
            }
            if (zbdto.getDatatype() == 1) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.INPUT.getCode());
            } else if (zbdto.getDatatype() == 2) {
                if (zbdto.getName().equalsIgnoreCase("BASEUNITID") || zbdto.getName().equalsIgnoreCase("DIFFUNITID")) {
                    reltablename = tableName;
                }
                if (StringUtils.isEmpty((CharSequence)reltablename)) {
                    orgFiledComponentVO2.setComponentType(FieldComponentEnum.INPUT.getCode());
                } else if (reltablename.toUpperCase().startsWith("MD_ORG")) {
                    orgFiledComponentVO2.setComponentType(FieldComponentEnum.ORGBASEDATA.getCode());
                } else {
                    orgFiledComponentVO2.setComponentType(FieldComponentEnum.BASEDATA.getCode());
                }
            } else if (zbdto.getDatatype() == 4) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.INPUT.getCode());
            } else if (zbdto.getDatatype() == 3) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.INPUT.getCode());
            } else if (zbdto.getDatatype() == 5) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.DATEPICKER.getCode());
            } else if (zbdto.getDatatype() == 6) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.DATEPICKER.getCode());
            } else if (zbdto.getDatatype() == 8) {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.RADIOGROUP.getCode());
            } else {
                orgFiledComponentVO2.setComponentType(FieldComponentEnum.INPUT.getCode());
            }
            orgFiledComponentVO2.setAllowMultiple(Boolean.valueOf(zbdto.getMultiple() != null && BooleanUtils.toBoolean((int)zbdto.getMultiple())));
            if (readonlyFields.contains(zbdto.getName().toUpperCase())) {
                orgFiledComponentVO2.setReadOnly(Boolean.valueOf(true));
            } else {
                orgFiledComponentVO2.setReadOnly(Boolean.valueOf(zbdto.getReadonly() != null && BooleanUtils.toBoolean((int)zbdto.getReadonly())));
            }
            orgFiledComponentVO2.setNullable(Boolean.valueOf(zbdto.getRequiredflag() == null || !BooleanUtils.toBoolean((int)zbdto.getRequiredflag())));
            orgFiledComponentVO2.setSortOrder(Double.valueOf(zbdto.getOrdernum() == null ? 0.0 : zbdto.getOrdernum().doubleValue()));
            boolean fold = true;
            if (saveFieldVO != null) {
                fold = saveFieldVO.getChecked() == 0;
            } else {
                saveFieldVO = new GcOrgFieldVO();
                String systemCode = stringMap.get(zbdto.getName());
                if (StringUtils.isNotEmpty((CharSequence)systemCode)) {
                    saveFieldVO.setSortOrder(Double.valueOf(1.0));
                    fold = false;
                } else {
                    saveFieldVO.setSortOrder(Double.valueOf(99.0));
                }
            }
            if (zbdto.getRequiredflag() != null && BooleanUtils.toBoolean((int)zbdto.getRequiredflag())) {
                fold = false;
            }
            orgFiledComponentVO2.setFold(Boolean.valueOf(fold));
            orgFiledComponentVO2.setSortOrder(saveFieldVO.getSortOrder());
            ret.add(orgFiledComponentVO2);
        }
        return ret.stream().filter(orgFiledComponentVO -> {
            if (!isFilterField) {
                return true;
            }
            return !BooleanUtils.toBoolean((Boolean)orgFiledComponentVO.getFold());
        }).sorted((o1, o2) -> {
            if (o1.getSortOrder() == null || o2.getSortOrder() == null) {
                return 1;
            }
            return (int)(o1.getSortOrder() - o2.getSortOrder());
        }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<Map<String, Object>> listOrgValuesByPage(Boolean isAll, String parentId, Integer pageNum, Integer pageSize, boolean isConvertValue) {
        GcOrgBaseTool tool = GcOrgBaseTool.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<Object> orgAll = isAll != false ? tool.listAllSubordinates(parentId) : tool.listOrg().stream().filter(orgToJsonVO -> orgToJsonVO.getParentid().equals(parentId)).collect(Collectors.toList());
            List<Object> orgs = (pageNum - 1) * pageSize + pageSize > orgAll.size() ? orgAll.subList((pageNum - 1) * pageSize, orgAll.size()) : orgAll.subList((pageNum - 1) * pageSize, (pageNum - 1) * pageSize + pageSize);
            Map<String, OrgToJsonVO> idMap = orgs.stream().collect(Collectors.toMap(OrgToJsonVO::getId, o -> o));
            idMap.put(parentId, tool.getOrgById(parentId));
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode("MD_ORG");
            Assert.isNotNull((Object)tableDefine, (String)"\u672a\u627e\u5230\u76f8\u5173\u8868\u5b9a\u4e49\uff01", (Object[])new Object[0]);
            List fields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            HashMap<String, Object> tempCache = new HashMap<String, Object>();
            if (fields.isEmpty()) {
                return PageInfo.empty();
            }
            ArrayList content = new ArrayList();
            for (OrgToJsonVO orgToJsonVO2 : orgs) {
                ConcurrentHashMap<String, Object> retMap = new ConcurrentHashMap<String, Object>(16);
                for (ColumnModelDefine field : fields) {
                    Object val = orgToJsonVO2.getFieldValue(field.getCode());
                    if (StringUtils.isNotEmpty((CharSequence)field.getReferTableID())) {
                        TableModelDefine fieldDefine = this.dataModelService.getTableModelDefineById(field.getReferTableID());
                        String tableName = fieldDefine.getName();
                        val = this.getBaseDataObject(tempCache, tableName, val);
                        val = this.getOrgDataByCodeFromCache(idMap, field.getCode(), val);
                    } else {
                        val = this.getDateValue(df, val);
                    }
                    if (isConvertValue && (field.getColumnType().equals((Object)ColumnModelType.BOOLEAN) || "MERGERACCOUNTS".equalsIgnoreCase(field.getCode()))) {
                        val = BooleanUtils.toBoolean((String)String.valueOf(val)) ? "\u662f" : "\u5426";
                    }
                    retMap.put(field.getCode().toUpperCase(), val == null ? "" : val);
                }
                content.add(retMap);
            }
            return PageInfo.of(content, (int)orgAll.size());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    @Override
    public Map<String, Object> getOrgFormData(GcOrgApiParam param) {
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(param.getOrgType(), param.getOrgVerName());
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setCategoryname(param.getOrgType());
        orgDTO.setVersionDate(version.getValidTime());
        orgDTO.setCode(param.getOrgCode());
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL_WITH_REF);
        PageVO pageVO = this.orgDataService.list(orgDTO);
        if (CollectionUtils.isEmpty((Collection)pageVO.getRows())) {
            return new HashMap<String, Object>();
        }
        List rows = pageVO.getRows();
        OrgDO orgDO = (OrgDO)rows.get(0);
        return this.orgDO2Map(orgDO, param);
    }

    private Map<String, Object> orgDO2Map(OrgDO orgDO, GcOrgApiParam param) {
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setName(orgDO.getCategoryname());
        List zbList = this.orgCategoryService.listZB(categoryDO);
        Map<String, ZBDTO> zbMap = zbList.stream().collect(Collectors.toMap(zbdto -> zbdto.getName().toUpperCase(), o -> o));
        Set entries = orgDO.entrySet();
        HashMap<String, Object> showMap = new HashMap<String, Object>();
        Map<String, DataModelColumn> templateFieldMap = this.orgTemplate.getTemplateFields().stream().collect(Collectors.toMap(DataModelColumn::getColumnName, o -> o));
        Map showTitleMap = (Map)orgDO.get((Object)"showTitleMap");
        for (Map.Entry entry : entries) {
            String fieldName = (String)entry.getKey();
            Object value = entry.getValue();
            ZBDTO zb = zbMap.get(fieldName.toUpperCase());
            if (fieldName.equalsIgnoreCase("Ver")) {
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(((BigDecimal)value).longValue()), ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = dateTime.format(formatter);
                showMap.put(fieldName.toUpperCase(), formattedDateTime);
                continue;
            }
            if (zb == null) {
                if (!templateFieldMap.containsKey(fieldName.toUpperCase())) continue;
                this.getSystemFixedFieldValue(param, value, showMap, templateFieldMap.get(fieldName.toUpperCase()));
                continue;
            }
            if (value == null && zb.getDatatype() != 8) continue;
            if (zb.getName().equalsIgnoreCase("BASEUNITID") || zb.getName().equalsIgnoreCase("DIFFUNITID")) {
                GcOrgMangerCenterTool centerTool = GcOrgMangerCenterTool.getInstance(param.getOrgType(), param.getOrgVerCode());
                OrgToJsonVO orgByCode = centerTool.getOrgByCode((String)value);
                if (orgByCode == null) continue;
                orgByCode.setTitle(orgByCode.getCode() + " " + orgByCode.getTitle());
                showMap.put(fieldName.toUpperCase(), orgByCode);
                continue;
            }
            if (zb.getMultiple() != null && zb.getMultiple() == 1 && value instanceof ArrayList && StringUtils.isNotEmpty((CharSequence)zb.getReltablename()) && 1 == zb.getRelatetype()) {
                ArrayList values = (ArrayList)value;
                String titleMulit = (String)showTitleMap.get(fieldName);
                String[] valueArray = titleMulit.split(",");
                ArrayList<Map> list = new ArrayList<Map>();
                int valuesSize = values.size();
                for (int i = 0; i < valuesSize; ++i) {
                    String baseDataValue = (String)values.get(i);
                    String title = valueArray[i];
                    list.add(new JSONObject().put("code", (Object)baseDataValue).put("title", (Object)title).toMap());
                }
                showMap.put(fieldName.toUpperCase(), list);
                continue;
            }
            if (StringUtils.isNotEmpty((CharSequence)zb.getReltablename())) {
                String baseDataValue = (String)value;
                String title = (String)showTitleMap.get(fieldName);
                showMap.put(fieldName.toUpperCase(), new JSONObject().put("code", (Object)baseDataValue).put("title", (Object)title).toMap());
                continue;
            }
            showMap.put(fieldName.toUpperCase(), value);
        }
        return showMap;
    }

    private void getSystemFixedFieldValue(GcOrgApiParam param, Object value, Map<String, Object> showMap, DataModelColumn dataModelColumn) {
        GcOrgMangerCenterTool centerTool = GcOrgMangerCenterTool.getInstance(param.getOrgType(), param.getOrgVerCode());
        if (dataModelColumn.getColumnName().equals("PARENTCODE")) {
            OrgToJsonVO orgByCode = centerTool.getOrgByCode((String)value);
            if (orgByCode != null) {
                showMap.put(dataModelColumn.getColumnName(), orgByCode);
            }
        } else {
            showMap.put(dataModelColumn.getColumnName(), value);
        }
    }

    @Override
    public List<Map<String, Object>> exportExcel(ExportConditionVO conditionVO) {
        List<OrgFiledComponentVO> gcOrgFieldEOS = null;
        try {
            gcOrgFieldEOS = this.getFieldComponent(conditionVO.getTableName());
            GcOrgMangerCenterTool instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
            List<OrgToJsonVO> orgTree = instance.getOrgTree(conditionVO.getParentId());
            List<OrgToJsonVO> orgToJsonVOS = instance.listOrg();
            HashMap<String, OrgToJsonVO> idMap = new HashMap();
            try {
                idMap = orgToJsonVOS.stream().collect(Collectors.toMap(OrgToJsonVO::getId, o -> o, (existing, replacement) -> existing));
            }
            catch (IllegalStateException e) {
                throw new BusinessRuntimeException("\u5355\u4f4d\u91cd\u590d\uff1a" + e.getMessage());
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            ArrayList<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
            HashMap<String, Object> tempCache = new HashMap<String, Object>(16);
            List<OrgFiledComponentVO> finalGcOrgFieldEOS = gcOrgFieldEOS;
            this.convertAllChildrenData(orgTree, idMap, df, content, tempCache, finalGcOrgFieldEOS);
            return content;
        }
        catch (Exception e) {
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5bfc\u51fa\u7ec4\u7ec7\u673a\u6784" + conditionVO.getOrgType()), (String)JsonUtils.writeValueAsString((Object)e.getMessage()));
            throw new BusinessRuntimeException("\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    private void convertAllChildrenData(List<OrgToJsonVO> orgTree, Map<String, OrgToJsonVO> idMap, SimpleDateFormat df, List<Map<String, Object>> content, Map<String, Object> tempCache, List<OrgFiledComponentVO> finalGcOrgFieldEOS) {
        orgTree.forEach(orgToJsonVO -> {
            ConcurrentHashMap retMap = new ConcurrentHashMap(16);
            finalGcOrgFieldEOS.forEach(fieldEO -> {
                Object val = orgToJsonVO.getFieldValue(fieldEO.getCode());
                String refTableName = fieldEO.getRefTableName();
                if (StringUtils.isNotEmpty((CharSequence)refTableName)) {
                    try {
                        val = this.getBaseData(tempCache, fieldEO.getRefTableName(), val);
                    }
                    catch (Exception e) {
                        String message = "\u5355\u4f4d\u3010" + orgToJsonVO.getCode() + "\u3011\u7684\u3010" + fieldEO.getLabel() + "\u3011\u503c\u5728\u6240\u5173\u8054\u7684\u57fa\u7840\u6570\u636e\u4e2d\u4e0d\u5b58\u5728";
                        LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)"\u5bfc\u51fa\u7ec4\u7ec7\u673a\u6784", (String)JsonUtils.writeValueAsString((Object)(message + "\n" + e.getMessage())));
                        throw new RuntimeException(message);
                    }
                } else {
                    val = this.getDateValue(df, val);
                }
                val = this.getOrgDataByCodeFromCache(idMap, fieldEO.getCode(), val);
                if ("STOPFLAG".equalsIgnoreCase(fieldEO.getCode())) {
                    val = val == null ? "\u5426" : ("1".equals(val.toString()) ? "\u662f" : "\u5426");
                }
                retMap.put(fieldEO.getCode(), val == null ? "" : val);
            });
            content.add(retMap);
            if (!CollectionUtils.isEmpty((Collection)orgToJsonVO.getChildren())) {
                this.convertAllChildrenData(orgToJsonVO.getChildren(), idMap, df, content, tempCache, finalGcOrgFieldEOS);
            }
        });
    }

    private Object getOrgDataByCodeFromCache(Map<String, OrgToJsonVO> idMap, String fieldCode, Object value) {
        OrgToJsonVO orgToJsonVO;
        if (value == null) {
            return "";
        }
        if (("PARENTCODE".equalsIgnoreCase(fieldCode) || "BASEUNITID".equalsIgnoreCase(fieldCode) || "DIFFUNITID".equalsIgnoreCase(fieldCode)) && (orgToJsonVO = idMap.get(value)) != null) {
            value = idMap.get(value).getCode() + "|" + idMap.get(value).getTitle();
        }
        return value;
    }

    private BaseDataDO getBaseDataFromCache(Map<String, Object> cache, String refTableName, Object val) {
        BaseDataDO iBaseData;
        Object baseData = cache.get(refTableName + val.toString());
        if (null == baseData) {
            iBaseData = this.getBaseDataByCode(refTableName, val.toString());
            BaseDataDO notExist = null;
            if (iBaseData == null) {
                notExist = new BaseDataDO();
                notExist.setCode(val.toString());
                notExist.setName(val + "<error>");
            } else {
                iBaseData.setShowTitle(iBaseData.getCode() + "|" + iBaseData.getName());
                iBaseData.put("title", (Object)(iBaseData.getCode() + "|" + iBaseData.getName()));
            }
            cache.put(refTableName + val.toString(), iBaseData == null ? notExist : iBaseData);
        }
        iBaseData = null;
        try {
            iBaseData = (BaseDataDO)cache.get(refTableName + val.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return iBaseData;
    }

    private String getBaseDataTitle(Map<String, Object> cache, Object key, String refTableName) {
        ArrayList vals = new ArrayList();
        if (null != key) {
            vals = CollectionUtils.newArrayList((Object[])key.toString().split(";"));
            return vals.stream().map(s -> this.getBaseDataFromCache(cache, refTableName, s)).map(BaseDataDO::getName).collect(Collectors.joining(";"));
        }
        return "";
    }

    private Object getDateValue(SimpleDateFormat df, Object val) {
        if (val instanceof Date) {
            val = df.format(val);
        }
        return val;
    }

    private Object getBaseData(Map<String, Object> tempCache, String refTableName, Object val) {
        if (StringUtils.isNotEmpty((CharSequence)refTableName) && !refTableName.toUpperCase().startsWith("MD_ORG") && val != null) {
            val = this.getBaseDataTitle(tempCache, val, refTableName);
        }
        return val;
    }

    private Object getBaseDataObject(Map<String, Object> tempCache, String refTableName, Object val) {
        if (StringUtils.isNotEmpty((CharSequence)refTableName) && !refTableName.toUpperCase().startsWith("MD_ORG") && val != null) {
            val = this.getBaseDataMuilt(tempCache, val, refTableName);
        }
        return val;
    }

    private List<BaseDataDO> getBaseDataMuilt(Map<String, Object> cache, Object key, String refTableName) {
        if (null != key) {
            ArrayList vals = CollectionUtils.newArrayList((Object[])key.toString().split(";"));
            return vals.stream().map(s -> this.getBaseDataFromCache(cache, refTableName, s)).collect(Collectors.toList());
        }
        return CollectionUtils.newArrayList();
    }

    private BaseDataDO getBaseDataByCode(String tableName, String code) {
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableName);
        queryParam.setCode(code);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        queryParam.setIgnoreShareFields(Boolean.valueOf(true));
        queryParam.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO list = this.baseDataClient.list(queryParam);
        if (list.getRs().getCode() == R.ok().getCode()) {
            Assert.isNotEmpty((Collection)list.getRows(), (String)("\u67e5\u8be2" + tableName + "\u57fa\u7840\u6570\u636e\u7684" + code + "\u503c\u5931\u8d25"), (Object[])new Object[0]);
            BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
            if (CollectionUtils.isEmpty((Collection)list.getRows())) {
                return null;
            }
            BaseDataDO dataDO = new BaseDataDO();
            dataDO.putAll((Map)baseDataDO);
            return dataDO;
        }
        return null;
    }
}

