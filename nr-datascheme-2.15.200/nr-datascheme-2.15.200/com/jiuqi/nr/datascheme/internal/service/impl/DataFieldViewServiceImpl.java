/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.AmountUnit
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IDataFieldViewService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.zb.scheme.core.PropInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  com.jiuqi.nr.zb.scheme.utils.ZbInfoUtils
 *  com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.AmountUnit;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IDataFieldViewService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.utils.ZbInfoUtils;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataFieldViewServiceImpl
implements IDataFieldViewService {
    public static final String KEY = "key";
    public static final String TITLE = "title";
    public static final String CODE = "code";
    public static final String DATATYPE = "dataType";
    public static final String PRECISION = "precision";
    public static final String DECIMAL = "decimal";
    public static final String NULLABLE = "nullable";
    public static final String REF_ENTITY = "refEntity";
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String DESC = "desc";
    public static final String FIELD_NAME = "fieldName";
    public static final String TABLE_NAME = "tableName";
    public static final String ZB_TYPE = "zbType";
    public static final String GATHER_TYPE = "gatherType";
    public static final String MEASURE_TYPE = "measureType";
    public static final String MEASURE_UNIT = "measureUnit";
    public static final String FORMULA = "formula";
    public static final String FORMULA_DESC = "formulaDesc";
    public static final String APPLY_TYPE = "applyType";
    public static final String ORDER = "order";
    private static final Map<String, String> FIELD_ATTRS = new LinkedHashMap<String, String>();
    private static final Logger log = LoggerFactory.getLogger(DataFieldViewServiceImpl.class);
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    public Map<String, String> getAllFieldViewColumns(boolean containExtProp) {
        if (containExtProp) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(FIELD_ATTRS);
            List propInfos = this.zbSchemeService.listAllPropInfo();
            for (PropInfo propInfo : propInfos) {
                map.put(propInfo.getFieldName(), propInfo.getTitle());
            }
            return Collections.unmodifiableMap(map);
        }
        return Collections.unmodifiableMap(FIELD_ATTRS);
    }

    public List<Map<String, Object>> getFieldViewData(List<String> fieldKeys) {
        throw new UnsupportedOperationException();
    }

    private Map<String, Map<String, ZbInfo>> getSchemeAndZbInfoMap(List<DataField> dataFields, String period) {
        LinkedHashMap<String, Map<String, ZbInfo>> zbMap = new LinkedHashMap<String, Map<String, ZbInfo>>();
        HashSet<String> schemes = new HashSet<String>();
        for (DataField dataField : dataFields) {
            schemes.add(dataField.getDataSchemeKey());
        }
        List dataSchemes = this.runtimeDataSchemeService.getDataSchemes(new ArrayList(schemes));
        for (DataScheme dataScheme : dataSchemes) {
            ZbSchemeVersion version = this.zbSchemeService.getZbSchemeVersion(dataScheme.getZbSchemeKey(), period);
            if (version != null) {
                List zbInfos = this.zbSchemeService.listZbInfoByVersion(version.getKey());
                zbMap.put(dataScheme.getKey(), zbInfos.stream().filter(Objects::nonNull).collect(Collectors.toMap(ZbInfo::getCode, f -> f, (o1, o2) -> o1)));
                continue;
            }
            log.warn("\u672a\u627e\u5230\u7248\u672c\u4fe1\u606f\uff0czbSchemeKey\uff1a{}\uff0cperiod\uff1a{}", (Object)dataScheme.getZbSchemeKey(), (Object)period);
            zbMap.put(dataScheme.getKey(), Collections.emptyMap());
        }
        return zbMap;
    }

    public List<Map<String, Object>> getFieldViewData(List<DataField> dataFields, String period) {
        log.debug("\u67e5\u8be2\u6307\u6807\u4fe1\u606f\uff0cperiod\uff1a{}", (Object)period);
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyList();
        }
        String[] keys = (String[])dataFields.stream().map(Basic::getKey).toArray(String[]::new);
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(keys);
        Map<String, DataFieldDeployInfo> deployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, f -> f, (o1, o2) -> o1));
        Map<Object, Object> zbMap = StringUtils.hasLength(period) ? this.getSchemeAndZbInfoMap(dataFields, period) : Collections.emptyMap();
        ArrayList<Map<String, Object>> res = new ArrayList<Map<String, Object>>(dataFields.size());
        ZbInfoUtils zbInfoUtils = new ZbInfoUtils(this.entityMetaService, this.dataDefinitionRuntimeController, this.entityDataService, this.entityViewRunTimeController);
        for (DataField dataField : dataFields) {
            Object o;
            String refEntityId;
            Map infoMap;
            ZbInfo zbInfo = null;
            if (zbMap.containsKey(dataField.getDataSchemeKey()) && (infoMap = (Map)zbMap.get(dataField.getDataSchemeKey())).containsKey(dataField.getCode())) {
                zbInfo = (ZbInfo)infoMap.get(dataField.getCode());
            }
            Map<String, Object> row = this.getFieldViewRow(dataField, zbInfo);
            if (zbInfo != null) {
                List cvo = zbInfoUtils.cvo(zbInfo.getExtProp());
                for (PropInfoVO propInfoVO : cvo) {
                    row.put(propInfoVO.getFieldName(), propInfoVO.getValueName());
                }
            }
            row.put(ORDER, dataField.getOrder());
            if (deployInfoMap.containsKey(dataField.getKey())) {
                row.put(FIELD_NAME, deployInfoMap.get(dataField.getKey()).getFieldName());
                row.put(TABLE_NAME, deployInfoMap.get(dataField.getKey()).getTableName());
            }
            String string = refEntityId = (o = row.get(REF_ENTITY)) != null ? (String)o : null;
            if (StringUtils.hasLength(refEntityId)) {
                String entityTitle = zbInfoUtils.getEntityTitle(refEntityId);
                row.put(REF_ENTITY, entityTitle);
            }
            res.add(row);
        }
        log.debug("\u67e5\u8be2\u6570\u636e\u5b57\u6bb5\u89c6\u56fe\uff0c\u7ed3\u679c\uff1a{}", (Object)res);
        return res;
    }

    private Map<String, Object> getFieldViewRow(DataField dataField, ZbInfo zbInfo) {
        if (dataField == null) {
            return Collections.emptyMap();
        }
        if (zbInfo == null) {
            return this.getFieldViewRow(dataField);
        }
        HashMap<String, Object> map = new HashMap<String, Object>(20);
        map.put(KEY, dataField.getKey());
        map.put(TITLE, zbInfo.getTitle());
        map.put(CODE, zbInfo.getCode());
        map.put(FORMULA, zbInfo.getFormula());
        map.put(FORMULA_DESC, zbInfo.getFormulaDesc());
        if (null != zbInfo.getType()) {
            map.put(ZB_TYPE, zbInfo.getType().getTitle());
        }
        if (null != zbInfo.getDataType()) {
            map.put(DATATYPE, zbInfo.getDataType().getTitle());
        }
        if (null != zbInfo.getApplyType()) {
            map.put(APPLY_TYPE, zbInfo.getApplyType().getTitle());
        }
        if (null != zbInfo.getGatherType()) {
            map.put(GATHER_TYPE, zbInfo.getGatherType().getTitle());
        }
        map.put(PRECISION, zbInfo.getPrecision());
        map.put(DECIMAL, zbInfo.getDecimal());
        map.put(NULLABLE, zbInfo.isNullable() ? "\u662f" : "\u5426");
        map.put(DEFAULT_VALUE, zbInfo.getDefaultValue());
        map.put(DESC, zbInfo.getDesc());
        map.put(REF_ENTITY, zbInfo.getRefEntityId());
        if (null != zbInfo.getMeasureUnit()) {
            AmountUnit unit;
            String replace = zbInfo.getMeasureUnit().replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
            if (!"NotDimession".equals(replace) && !"-".equals(replace)) {
                map.put(MEASURE_TYPE, "\u91d1\u989d");
            }
            if ((unit = AmountUnit.getByCode((String)replace)) != null) {
                map.put(MEASURE_UNIT, unit.getTitle());
            }
        }
        return map;
    }

    private Map<String, Object> getFieldViewRow(DataField dataField) {
        HashMap<String, Object> map = new HashMap<String, Object>(20);
        map.put(KEY, dataField.getKey());
        map.put(TITLE, dataField.getTitle());
        map.put(CODE, dataField.getCode());
        map.put(FORMULA, dataField.getFormula());
        map.put(FORMULA_DESC, dataField.getFormulaDesc());
        if (null != dataField.getZbType()) {
            map.put(ZB_TYPE, dataField.getZbType().getTitle());
        }
        if (null != dataField.getDataFieldType()) {
            map.put(DATATYPE, dataField.getDataFieldType().getTitle());
        }
        if (null != dataField.getDataFieldApplyType()) {
            map.put(APPLY_TYPE, dataField.getDataFieldApplyType().getTitle());
        }
        if (null != dataField.getDataFieldGatherType()) {
            map.put(GATHER_TYPE, dataField.getDataFieldGatherType().getTitle());
        }
        map.put(PRECISION, dataField.getPrecision());
        map.put(DECIMAL, dataField.getDecimal());
        map.put(NULLABLE, dataField.isNullable() ? "\u662f" : "\u5426");
        map.put(DEFAULT_VALUE, dataField.getDefaultValue());
        map.put(DESC, dataField.getDesc());
        map.put(REF_ENTITY, dataField.getRefDataEntityKey());
        if (null != dataField.getMeasureUnit()) {
            AmountUnit unit;
            String replace = dataField.getMeasureUnit().replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
            if (!"NotDimession".equals(replace) && !"-".equals(replace)) {
                map.put(MEASURE_TYPE, "\u91d1\u989d");
            }
            if ((unit = AmountUnit.getByCode((String)replace)) != null) {
                map.put(MEASURE_UNIT, unit.getTitle());
            }
        }
        return map;
    }

    static {
        FIELD_ATTRS.put(TITLE, "\u540d\u79f0");
        FIELD_ATTRS.put(CODE, "\u4ee3\u7801");
        FIELD_ATTRS.put(DATATYPE, "\u6570\u636e\u7c7b\u578b");
        FIELD_ATTRS.put(PRECISION, "\u957f\u5ea6");
        FIELD_ATTRS.put(DECIMAL, "\u5c0f\u6570\u4f4d");
        FIELD_ATTRS.put(NULLABLE, "\u662f\u5426\u4e3a\u7a7a");
        FIELD_ATTRS.put(REF_ENTITY, "\u5173\u8054\u679a\u4e3e");
        FIELD_ATTRS.put(DEFAULT_VALUE, "\u9ed8\u8ba4\u503c");
        FIELD_ATTRS.put(DESC, "\u63cf\u8ff0");
        FIELD_ATTRS.put(FIELD_NAME, "\u7269\u7406\u5b57\u6bb5\u540d");
        FIELD_ATTRS.put(TABLE_NAME, "\u7269\u7406\u8868\u540d");
        FIELD_ATTRS.put(ZB_TYPE, "\u6307\u6807\u7c7b\u578b");
        FIELD_ATTRS.put(GATHER_TYPE, "\u6c47\u603b\u65b9\u5f0f");
        FIELD_ATTRS.put(MEASURE_TYPE, "\u91cf\u7eb2");
        FIELD_ATTRS.put(MEASURE_UNIT, "\u8ba1\u91cf\u5355\u4f4d");
        FIELD_ATTRS.put(FORMULA, "\u8ba1\u7b97\u516c\u5f0f");
        FIELD_ATTRS.put(FORMULA_DESC, "\u516c\u5f0f\u63cf\u8ff0");
        FIELD_ATTRS.put(APPLY_TYPE, "\u5e94\u7528\u7c7b\u578b");
        FIELD_ATTRS.put(ORDER, "\u6392\u5e8f");
    }
}

