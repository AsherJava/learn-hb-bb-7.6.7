/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizationType
 *  com.jiuqi.nvwa.encryption.desensitization.service.DesensitizationStrategyManager
 *  com.jiuqi.nvwa.encryption.desensitization.service.impl.SubstringDesensitization
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.dto.MoveDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.SearchDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;
import com.jiuqi.nr.datascheme.web.facade.BatUpDataFieldVO;
import com.jiuqi.nr.datascheme.web.facade.DataFieldPageVO;
import com.jiuqi.nr.datascheme.web.facade.DataFieldParamVO;
import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;
import com.jiuqi.nr.datascheme.web.facade.DesensitizationInfoVO;
import com.jiuqi.nr.datascheme.web.facade.DimSetVO;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.SearchDataFieldVO;
import com.jiuqi.nr.datascheme.web.facade.SummarySetVO;
import com.jiuqi.nr.datascheme.web.param.DataFieldMovePM;
import com.jiuqi.nr.datascheme.web.param.DataSchemeQueryPM;
import com.jiuqi.nr.datascheme.web.param.NextFieldPM;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizationType;
import com.jiuqi.nvwa.encryption.desensitization.service.DesensitizationStrategyManager;
import com.jiuqi.nvwa.encryption.desensitization.service.impl.SubstringDesensitization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u6570\u636e\u6307\u6807\u670d\u52a1"})
public class DataFieldRestController {
    private final Logger logger = LoggerFactory.getLogger(DataFieldRestController.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataFieldDesignService dataFieldDesignService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private DataTableValidator dataTableValidator;
    @Autowired
    private DesensitizationStrategyManager desensitizationStrategyManager;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u8868-\u5206\u9875\u67e5\u8be2\u6307\u6807")
    @PostMapping(value={"field/query"})
    @RequiresPermissions(value={"nr:dataScheme_field:query"})
    public DataFieldPageVO<List<DataFieldVO>> queryDataField(@RequestBody DataSchemeQueryPM param) {
        String table = param.getTable();
        if (table == null) {
            return null;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(table);
        if (dataTable == null) {
            return this.queryFmdmFields(param);
        }
        DataFieldPageVO<List<DataFieldVO>> page = this.createPageVO(param);
        Integer pageCount = param.getPageCount();
        Integer pageNum = param.getPageNum();
        FieldSearchQuery fieldSearchQuery = param.toQuery();
        if (DataTableType.ACCOUNT == dataTable.getDataTableType()) {
            fieldSearchQuery.setOrder("DF_KIND DESC , DF_CHANGE_WITH_PERIOD , DF_ORDER");
        } else {
            fieldSearchQuery.setOrder("DF_KIND DESC , DF_ORDER");
        }
        List<DesignDataField> designDataFields = this.dataFieldDesignService.filterField(fieldSearchQuery);
        this.fillFieldInfo(table, designDataFields, page.getValue(), this.getSkipIndex(pageCount, pageNum));
        int total = this.dataFieldDesignService.countBy(fieldSearchQuery);
        page.setTotal(total);
        return page;
    }

    private DataFieldPageVO<List<DataFieldVO>> createPageVO(DataSchemeQueryPM param) {
        DataFieldPageVO<List<DataFieldVO>> page = new DataFieldPageVO<List<DataFieldVO>>();
        page.setPageCount(param.getPageCount());
        page.setPageNum(param.getPageNum());
        page.setValue(new ArrayList());
        return page;
    }

    private int getSkipIndex(Integer pageCount, Integer pageNum) {
        int index = 0;
        if (pageCount != null && pageNum != null) {
            index = pageCount * (pageNum - 1);
        }
        return index;
    }

    private DataFieldPageVO<List<DataFieldVO>> queryFmdmFields(DataSchemeQueryPM param) {
        DataFieldPageVO<List<DataFieldVO>> page = this.createPageVO(param);
        Integer pageCount = param.getPageCount();
        Integer pageNum = param.getPageNum();
        int index = this.getSkipIndex(pageCount, pageNum);
        try {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(param.getTable());
            if (entityModel == null) {
                return null;
            }
            TableModelDefine tableModel = this.entityMetaService.getTableModel(param.getTable());
            String tableName = tableModel.getCode();
            List showFields = entityModel.getShowFields();
            page.setTotal(showFields.size());
            if (pageCount != null && pageNum != null) {
                showFields = showFields.stream().skip(index).limit(pageCount.intValue()).collect(Collectors.toList());
            }
            for (IEntityAttribute next : showFields) {
                TableModelDefine tableModelDefineById;
                DataFieldVO dataFieldVO = new DataFieldVO(next, tableName);
                String referTable = next.getReferTableID();
                if (referTable != null && (tableModelDefineById = this.modelService.getTableModelDefineById(referTable)) != null) {
                    dataFieldVO.setRefDataEntityTitle(tableModelDefineById.getTitle());
                }
                DataFieldRestController.setDimension(dataFieldVO);
                dataFieldVO.setIndex(++index);
                page.getValue().add(dataFieldVO);
            }
        }
        catch (Exception e) {
            throw new SchemeDataException((Throwable)e);
        }
        return page;
    }

    private static void setDimension(DataFieldVO dataFieldVO) {
        DataFieldType dataFieldType = dataFieldVO.getDataFieldType();
        if (dataFieldType == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL) {
            if (!StringUtils.hasLength(dataFieldVO.getMeasureUnit()) || "NotDimession".equals(dataFieldVO.getMeasureUnit())) {
                dataFieldVO.setDimension(1);
            } else {
                dataFieldVO.setDimension(0);
            }
        }
    }

    private <DF extends DataField> void fillFieldInfo(String tableKey, List<DF> designDataFields, List<DataFieldVO> allFields, Integer index) {
        if (null != designDataFields && !designDataFields.isEmpty()) {
            Map<String, DataFieldDeployInfo> deployInfoMap = this.getDeployInfoMap(tableKey);
            Map<String, DataField> dataFieldMap = this.getDataFieldMap(tableKey);
            HashMap<String, String> entityInfoMap = new HashMap<String, String>(5);
            List allStrategys = this.desensitizationStrategyManager.getAllStrategys();
            Map<String, String> strategyMap = allStrategys.stream().collect(Collectors.toMap(DesensitizationInfo::getCode, e -> e.getName()));
            for (DataField v : designDataFields) {
                DataField dataField;
                DataFieldVO fieldEntity2VO = EntityUtil.fieldEntity2VO(v);
                if (deployInfoMap.containsKey(fieldEntity2VO.getKey())) {
                    DataFieldDeployInfo deployInfo = deployInfoMap.get(fieldEntity2VO.getKey());
                    fieldEntity2VO.setTableName(deployInfo.getTableName());
                    fieldEntity2VO.setFieldName(deployInfo.getFieldName());
                }
                if (null != (dataField = dataFieldMap.get(v.getKey()))) {
                    fieldEntity2VO.setRunEncrypted(dataField.isEncrypted());
                }
                if (StringUtils.hasLength(v.getRefDataEntityKey()) && DataFieldKind.PUBLIC_FIELD_DIM.getValue() != v.getDataFieldKind().getValue()) {
                    if (!entityInfoMap.containsKey(v.getRefDataEntityKey())) {
                        if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(v.getRefDataEntityKey())) {
                            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(v.getRefDataEntityKey());
                            entityInfoMap.put(v.getRefDataEntityKey(), periodEntity.getTitle());
                        } else {
                            IEntityDefine queryEntity = this.entityMetaService.queryEntity(v.getRefDataEntityKey());
                            entityInfoMap.put(v.getRefDataEntityKey(), queryEntity.getTitle());
                        }
                    }
                    fieldEntity2VO.setRefDataEntityTitle((String)entityInfoMap.get(v.getRefDataEntityKey()));
                }
                if (index != null) {
                    index = index + 1;
                    fieldEntity2VO.setIndex(index);
                }
                DataFieldRestController.setDimension(fieldEntity2VO);
                if (fieldEntity2VO.getDataMaskCode() != null) {
                    fieldEntity2VO.setDataMaskName(strategyMap.get(fieldEntity2VO.getDataMaskCode()));
                }
                allFields.add(fieldEntity2VO);
            }
        }
    }

    private Map<String, DataField> getDataFieldMap(String tableKey) {
        HashMap<String, DataField> map = new HashMap<String, DataField>();
        List fields = this.runtimeDataSchemeService.getDataFieldByTable(tableKey);
        if (fields != null && !fields.isEmpty()) {
            for (DataField field : fields) {
                map.put(field.getKey(), field);
            }
        }
        return map;
    }

    private Map<String, DataFieldDeployInfo> getDeployInfoMap(String tableKey) {
        HashMap<String, DataFieldDeployInfo> deployInfoMap = new HashMap<String, DataFieldDeployInfo>();
        List dataFieldDeployInfo = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
        if (null != dataFieldDeployInfo && !dataFieldDeployInfo.isEmpty()) {
            for (DataFieldDeployInfo deployInfo : dataFieldDeployInfo) {
                if (!deployInfoMap.containsKey(deployInfo.getDataFieldKey())) {
                    deployInfoMap.put(deployInfo.getDataFieldKey(), deployInfo);
                    continue;
                }
                if (deployInfo.getTableName().contains("_RPT")) continue;
                deployInfoMap.put(deployInfo.getDataFieldKey(), deployInfo);
            }
        }
        return deployInfoMap;
    }

    private void fillFieldInfo(List<DesignDataField> designDataFields, List<DataFieldVO> allFields) {
        if (null != designDataFields && !designDataFields.isEmpty()) {
            HashSet<String> dataFieldKeys = new HashSet<String>();
            HashSet<String> refEntityKeys = new HashSet<String>();
            for (DesignDataField f : designDataFields) {
                dataFieldKeys.add(f.getKey());
                if (!StringUtils.hasLength(f.getRefDataEntityKey()) || DataFieldKind.PUBLIC_FIELD_DIM.getValue() == f.getDataFieldKind().getValue()) continue;
                refEntityKeys.add(f.getRefDataEntityKey());
            }
            Map<String, DataFieldDeployInfo> deployInfoMap = this.getDeployInfoMap(dataFieldKeys);
            Map<String, String> entityInfoMap = this.getEntityInfoMap(refEntityKeys);
            for (DesignDataField v : designDataFields) {
                allFields.add(this.toVO(v, deployInfoMap, entityInfoMap));
            }
        }
    }

    private DataFieldVO toVO(DesignDataField v, Map<String, DataFieldDeployInfo> deployInfoMap, Map<String, String> entityInfoMap) {
        DataFieldVO fieldEntity2VO = EntityUtil.fieldEntity2VO((DataField)v);
        if (deployInfoMap.containsKey(fieldEntity2VO.getKey())) {
            DataFieldDeployInfo deployInfo = deployInfoMap.get(fieldEntity2VO.getKey());
            fieldEntity2VO.setTableName(deployInfo.getTableName());
            fieldEntity2VO.setFieldName(deployInfo.getFieldName());
        }
        if (StringUtils.hasLength(v.getRefDataEntityKey()) && entityInfoMap.containsKey(v.getRefDataEntityKey())) {
            fieldEntity2VO.setRefDataEntityTitle(entityInfoMap.get(v.getRefDataEntityKey()));
        }
        return fieldEntity2VO;
    }

    private Map<String, String> getEntityInfoMap(Set<String> refEntityKeys) {
        HashMap<String, String> entityInfoMap = new HashMap<String, String>(refEntityKeys.size());
        for (String entityKey : refEntityKeys) {
            if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(entityKey)) {
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(entityKey);
                entityInfoMap.put(entityKey, periodEntity.getTitle());
                continue;
            }
            IEntityDefine queryEntity = this.entityMetaService.queryEntity(entityKey);
            entityInfoMap.put(entityKey, queryEntity.getTitle());
        }
        return entityInfoMap;
    }

    private Map<String, DataFieldDeployInfo> getDeployInfoMap(Set<String> dataFieldKeys) {
        HashMap<String, DataFieldDeployInfo> deployInfoMap = new HashMap<String, DataFieldDeployInfo>();
        List dataFieldDeployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(dataFieldKeys.toArray(new String[0]));
        if (null != dataFieldDeployInfo && !dataFieldDeployInfo.isEmpty()) {
            for (DataFieldDeployInfo deployInfo : dataFieldDeployInfo) {
                deployInfoMap.put(deployInfo.getDataFieldKey(), deployInfo);
            }
        }
        return deployInfoMap;
    }

    private List<String> getUnClassifyFields(DataTable tableDefine) {
        ArrayList<String> gatherKeys = new ArrayList<String>();
        String[] gatherFieldKey = tableDefine.getGatherFieldKeys();
        if (gatherFieldKey == null || gatherFieldKey.length == 0) {
            return gatherKeys;
        }
        for (String curr : gatherFieldKey) {
            gatherKeys.add(curr);
        }
        return gatherKeys;
    }

    @ApiOperation(value="\u67e5\u8be2\u5f85\u9009\u7ef4\u5ea6\u6307\u6807")
    @GetMapping(value={"field/candidate"})
    public List<DataFieldVO> queryCandidateDataField(String table) {
        List<DesignDataField> designDataFields = this.dataFieldDesignService.queryCandidate(table);
        ArrayList<DataFieldVO> vo = new ArrayList<DataFieldVO>();
        HashMap<String, String> entityInfoMap = new HashMap<String, String>(5);
        for (DesignDataField v : designDataFields) {
            DataFieldVO fieldEntity2VO = EntityUtil.fieldEntity2VO((DataField)v);
            if (StringUtils.hasLength(v.getRefDataEntityKey()) && DataFieldKind.PUBLIC_FIELD_DIM.getValue() != v.getDataFieldKind().getValue()) {
                if (!entityInfoMap.containsKey(v.getRefDataEntityKey())) {
                    if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(v.getRefDataEntityKey())) {
                        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(v.getRefDataEntityKey());
                        entityInfoMap.put(v.getRefDataEntityKey(), periodEntity.getTitle());
                    } else {
                        IEntityDefine queryEntity = this.entityMetaService.queryEntity(v.getRefDataEntityKey());
                        entityInfoMap.put(v.getRefDataEntityKey(), queryEntity.getTitle());
                    }
                }
                fieldEntity2VO.setRefDataEntityTitle((String)entityInfoMap.get(v.getRefDataEntityKey()));
            }
            vo.add(fieldEntity2VO);
        }
        return vo;
    }

    private void updateRepeatCode(String table, boolean repeatCode) throws JQException {
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(table);
        dataTable.setRepeatCode(Boolean.valueOf(repeatCode));
        dataTable.setUpdateTime(Instant.now());
        try {
            this.dataTableValidator.levelCheckTable(dataTable);
            this.designDataSchemeService.updateDataTable(dataTable);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u8bbe\u7f6e\u7ef4\u5ea6\u6307\u6807")
    @PostMapping(value={"field/candidate"})
    public void successfulCandidates(@RequestBody DimSetVO dimSetVO) throws JQException {
        this.updateRepeatCode(dimSetVO.getTable(), dimSetVO.isRepeatCode());
        List<String> keys = dimSetVO.getKeys();
        if (keys == null) {
            return;
        }
        String tableKey = dimSetVO.getTable();
        Assert.notNull((Object)tableKey, "table must not be null.");
        List dataFields = keys.isEmpty() ? new ArrayList() : this.designDataSchemeService.getDataFields(keys);
        List bizFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        Iterator iterator = dataFields.iterator();
        Map<String, DesignDataField> old = bizFields.stream().collect(Collectors.toMap(Basic::getKey, r -> r, (r1, r2) -> r1));
        while (iterator.hasNext()) {
            DesignDataField next = (DesignDataField)iterator.next();
            next.setUpdateTime(null);
            old.remove(next.getKey());
            if (next.getDataFieldKind().equals((Object)DataFieldKind.FIELD) || !DataFieldKind.TABLE_FIELD_DIM.equals((Object)next.getDataFieldKind())) {
                next.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                continue;
            }
            iterator.remove();
        }
        try {
            if (!old.isEmpty()) {
                Collection<DesignDataField> oldValues = old.values();
                for (DesignDataField oldValue : oldValues) {
                    oldValue.setDataFieldKind(DataFieldKind.FIELD);
                    oldValue.setUpdateTime(null);
                    dataFields.add(oldValue);
                    this.fieldValidator.levelCheckField(oldValue);
                }
            }
            this.designDataSchemeService.updateDataFields(dataFields);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    private void updateFieldWithKeys(List<String> keys, String tableKey) throws JQException {
        List dataFields = keys.isEmpty() ? new ArrayList() : this.designDataSchemeService.getDataFields(keys);
        List bizFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        Iterator iterator = dataFields.iterator();
        Map<String, DesignDataField> old = bizFields.stream().collect(Collectors.toMap(Basic::getKey, r -> r, (r1, r2) -> r1));
        while (iterator.hasNext()) {
            DesignDataField next = (DesignDataField)iterator.next();
            next.setUpdateTime(null);
            old.remove(next.getKey());
            if (next.getDataFieldKind().equals((Object)DataFieldKind.FIELD) || !DataFieldKind.TABLE_FIELD_DIM.equals((Object)next.getDataFieldKind())) {
                next.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                continue;
            }
            iterator.remove();
        }
        try {
            if (!old.isEmpty()) {
                Collection<DesignDataField> oldValues = old.values();
                for (DesignDataField oldValue : oldValues) {
                    oldValue.setDataFieldKind(DataFieldKind.FIELD);
                    oldValue.setUpdateTime(null);
                    dataFields.add(oldValue);
                    this.fieldValidator.levelCheckField(oldValue);
                }
            }
            this.designDataSchemeService.updateDataFields(dataFields);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    private void updateFieldProperties(DesignDataField field, DataFieldVO dataFieldVO) {
        if (dataFieldVO != null) {
            field.setUpdateTime(Instant.now());
            field.setTitle(dataFieldVO.getTitle());
            field.setCode(dataFieldVO.getCode());
            field.setPrecision(dataFieldVO.getPrecision());
            field.setDefaultValue(dataFieldVO.getDefaultValue());
            field.setRefDataEntityKey(dataFieldVO.getRefDataEntityKey());
            field.setAllowMultipleSelect(dataFieldVO.getAllowMultipleSelect());
        }
    }

    private Map<String, DataFieldVO> getUpdateFieldMap(List<DataFieldVO> update) {
        if (CollectionUtils.isEmpty(update)) {
            return Collections.emptyMap();
        }
        return update.stream().collect(Collectors.toMap(BaseDataVO::getKey, r -> r, (r1, r2) -> r1));
    }

    @ApiOperation(value="\u66f4\u65b0\u5173\u8054\u6307\u6807\u4f53\u7cfb\u7684\u6570\u636e\u8868\u5185\u7ef4\u5ea6\u4fe1\u606f")
    @PostMapping(value={"zb-scheme/update-dimension"})
    public void updateZbDimension(@RequestBody DimSetVO dimSetVO) throws JQException {
        String tableKey = dimSetVO.getTable();
        Assert.notNull((Object)tableKey, "table must not be null.");
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        this.updateRepeatCode(dimSetVO.getTable(), dimSetVO.isRepeatCode());
        List<String> keys = dimSetVO.getKeys();
        if (keys == null) {
            return;
        }
        List<DataFieldVO> update = dimSetVO.getUpdate();
        if (!CollectionUtils.isEmpty(update)) {
            update.forEach(dataFieldVO -> dataFieldVO.setFormatVO(new FormatVO()));
            this.updateDataFields(update);
            return;
        }
        List<String> delete = dimSetVO.getDelete();
        if (delete != null) {
            boolean b = this.runtimeDataSchemeService.dataTableCheckData(new String[]{tableKey});
            if (b) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DM_2, "\u5df2\u6709\u6570\u636e\uff0c\u65e0\u6cd5\u5220\u9664\uff01");
            }
            this.deleteDataFields(delete, tableKey);
            return;
        }
        List<DataFieldVO> insert = dimSetVO.getInsert();
        if (insert != null) {
            for (DataFieldVO dataFieldVO2 : insert) {
                dataFieldVO2.setFormatVO(new FormatVO());
                dataFieldVO2.setDataSchemeKey(dataTable.getDataSchemeKey());
                dataFieldVO2.setDataTableKey(tableKey);
                dataFieldVO2.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                dataFieldVO2.setChangeWithPeriod(false);
                dataFieldVO2.setAllowTreeSum(false);
                dataFieldVO2.setGenerateVersion(true);
                dataFieldVO2.setNullable(false);
                dataFieldVO2.setValidationRules(Collections.emptyList());
                this.addDataField(dataFieldVO2);
            }
        }
    }

    @ApiOperation(value="\u5173\u8054\u6307\u6807\u4f53\u7cfb\u7684\u6570\u636e\u65b9\u6848\u6dfb\u52a0\u7ef4\u5ea6")
    @PostMapping(value={"zb-scheme/add-dimension"})
    public void addZbDimension(@RequestBody DimSetVO dimSetVO) throws JQException {
        String defaultValue = dimSetVO.getDefaultValue();
        if (!StringUtils.hasText(defaultValue)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DM_1, "\u9ed8\u8ba4\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        DataFieldVO dimField = dimSetVO.getDimField();
        dimField.setKey(UUID.randomUUID().toString());
        dimField.setOrder(OrderGenerator.newOrder());
        dimField.setFormatVO(new FormatVO());
        dimField.setDataTableKey(dimSetVO.getTable());
        dimField.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
        dimField.setChangeWithPeriod(false);
        dimField.setAllowTreeSum(false);
        dimField.setGenerateVersion(true);
        dimField.setNullable(false);
        dimField.setValidationRules(Collections.emptyList());
        DesignDataField dataField = EntityUtil.fieldVO2Entity(this.designDataSchemeService, dimField);
        try {
            this.designDataSchemeService.addTableDimToTable(dataField, defaultValue);
        }
        catch (SchemeDataException e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ADD_DIMENSION, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ADD_DIMENSION, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5f85\u9009\u6c47\u603b\u5b57\u6bb5")
    @GetMapping(value={"gatherfieldKey/candidate"})
    public List<DataFieldVO> queryCandidateUnclassifiedKey(String table) {
        DesignDataTable tableDefine = this.designDataSchemeService.getDataTable(table);
        List<String> unClassifyKeys = this.getUnClassifyFields((DataTable)tableDefine);
        List designDataFields = this.designDataSchemeService.getDataFields(unClassifyKeys);
        ArrayList<DataFieldVO> vo = new ArrayList<DataFieldVO>();
        for (DesignDataField designDataField : designDataFields) {
            DataFieldVO dataFieldVO = EntityUtil.fieldEntity2VO((DataField)designDataField);
            vo.add(dataFieldVO);
        }
        return vo;
    }

    @ApiOperation(value="\u8bbe\u7f6e\u6c47\u603b\u5b57\u6bb5")
    @PostMapping(value={"gatherfieldKey/candidate"})
    public void successfulCandidatesUnclassifiedKey(@RequestBody SummarySetVO summarySetVO) throws JQException {
        List<String> keys = summarySetVO.getKeys();
        if (keys == null) {
            return;
        }
        String tableKey = summarySetVO.getTable();
        Assert.notNull((Object)tableKey, "table must not be null.");
        try {
            DesignDataTable designDataTable = EntityUtil.tableVO2Entity(this.designDataSchemeService, summarySetVO.getTableInfo(), null);
            this.designDataSchemeService.updateGatherFieldKeys(tableKey, keys, designDataTable);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u65b0\u589e\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/add"})
    public int addDataField(@RequestBody DataFieldVO fieldVO) throws JQException {
        this.logger.debug("\u65b0\u589e\u6570\u636e\u6307\u6807\uff1a{}[{}]{}\u3002", fieldVO.getTitle(), fieldVO.getCode(), fieldVO.getDataFieldKind());
        if (!this.dataSchemeAuthService.canWriteScheme(fieldVO.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            DesignDataField dataField = EntityUtil.fieldVO2Entity(this.designDataSchemeService, fieldVO);
            this.designDataSchemeService.insertDataField(dataField);
            FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
            fieldSearchQuery.setTable(fieldVO.getDataTableKey());
            return this.dataFieldDesignService.countBy(fieldSearchQuery);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/update"})
    public void updateDataField(@RequestBody DataFieldVO fieldVO) throws JQException {
        this.logger.debug("\u66f4\u65b0\u6570\u636e\u6307\u6807\uff1a{}[{}]{}\u3002", fieldVO.getTitle(), fieldVO.getCode(), fieldVO.getDataFieldKind());
        if (!this.dataSchemeAuthService.canWriteScheme(fieldVO.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            DesignDataField dataField = EntityUtil.fieldVO2Entity(this.designDataSchemeService, fieldVO);
            dataField.setUpdateTime(null);
            this.fieldValidator.levelCheckField(dataField);
            this.designDataSchemeService.updateDataField(dataField);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/batch-update"})
    public void updateDataFields(@RequestBody List<DataFieldVO> fields) throws JQException {
        this.logger.debug("\u6279\u91cf\u66f4\u65b0\u6570\u636e\u6307\u6807");
        Set schemes = fields.stream().map(DataFieldVO::getDataSchemeKey).collect(Collectors.toSet());
        for (String scheme : schemes) {
            if (this.dataSchemeAuthService.canWriteScheme(scheme)) continue;
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        ArrayList<DesignDataField> fieldsDf = new ArrayList<DesignDataField>(fields.size());
        DesignDataTable dataTable = null;
        FormatPropertiesBuilder builder = new FormatPropertiesBuilder();
        try {
            for (DataFieldVO field : fields) {
                String dataTableKey = field.getDataTableKey();
                if (dataTable == null) {
                    dataTable = this.designDataSchemeService.getDataTable(dataTableKey);
                }
                DesignDataField dataField = EntityUtil.fieldVO2Entity(field, dataTable, this.designDataSchemeService, builder);
                dataField.setUpdateTime(null);
                this.fieldValidator.levelCheckField(dataField);
                fieldsDf.add(dataField);
            }
            this.designDataSchemeService.updateDataFields(fieldsDf);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/delete"})
    public int deleteDataField(@RequestParam String key, @RequestParam(required=false) String tableKey) throws JQException {
        this.logger.debug("\u5220\u9664\u6570\u636e\u6307\u6807\uff1a{}\u3002", (Object)key);
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            this.fieldValidator.levelCheckField(key);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
        try {
            this.designDataSchemeService.deleteDataField(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
        if (tableKey != null) {
            FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
            fieldSearchQuery.setTable(tableKey);
            return this.dataFieldDesignService.countBy(fieldSearchQuery);
        }
        return 0;
    }

    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/batch-delete"})
    public int deleteDataFields(@RequestBody List<String> keys, @RequestParam(required=false) String tableKey) throws JQException {
        this.logger.debug("\u5220\u9664\u6570\u636e\u6307\u6807\uff1a{}\u3002", (Object)keys);
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        if (keys.isEmpty()) {
            return 0;
        }
        try {
            List dataFields = this.designDataSchemeService.getDataFields(keys);
            for (DesignDataField dataField : dataFields) {
                this.fieldValidator.levelCheckField(dataField);
            }
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
        this.designDataSchemeService.deleteDataFields(keys);
        if (tableKey != null) {
            FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
            fieldSearchQuery.setTable(tableKey);
            return this.dataFieldDesignService.countBy(fieldSearchQuery);
        }
        return 0;
    }

    @ApiOperation(value="\u6839\u636e\u65b9\u6848(\u5206\u7ec4/\u6307\u6807)\u641c\u7d22\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/search"})
    public List<SearchDataFieldVO> filterDataField(@RequestBody DataSchemeQueryPM param) {
        String scheme = param.getScheme();
        if (scheme == null) {
            return null;
        }
        FieldSearchQuery fieldSearchQuery = param.toQuery();
        List<SearchDataFieldDTO> fields = this.dataFieldDesignService.searchFieldByScheme(fieldSearchQuery);
        if (fields.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<SearchDataFieldVO> values = new ArrayList<SearchDataFieldVO>(fields.size());
        for (SearchDataFieldDTO field : fields) {
            SearchDataFieldVO vo = new SearchDataFieldVO(field);
            values.add(vo);
        }
        return values;
    }

    @ApiOperation(value="\u6839\u636e\u641c\u7d22\u7ed3\u679c\u6307\u6807\u5b9a\u4f4d")
    @PostMapping(value={"field/position"})
    public DataFieldPageVO<List<DataFieldVO>> positionField(@RequestBody DataFieldPageVO<String> pageVO) throws JQException {
        String value = pageVO.getValue();
        Assert.notNull((Object)value, "key must not be null.");
        Integer pageCount = pageVO.getPageCount();
        if (pageCount == null) {
            return null;
        }
        if (pageCount <= 0) {
            return null;
        }
        DataFieldPageVO<List<DataFieldVO>> result = new DataFieldPageVO<List<DataFieldVO>>();
        DesignDataField dataField = this.designDataSchemeService.getDataField(value);
        if (dataField == null) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, "\u6307\u6807\u672a\u627e\u5230\u6216\u5df2\u88ab\u5220\u9664,\u5b9a\u4f4d\u5931\u8d25!");
        }
        String dataTableKey = dataField.getDataTableKey();
        List fields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(dataTableKey, new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.FIELD_ZB});
        ArrayList<DesignDataField> pageList = new ArrayList<DesignDataField>(pageCount);
        boolean post = false;
        int page = 1;
        for (DesignDataField field : fields) {
            pageList.add(field);
            if (field.getKey().equals(value)) {
                post = true;
            }
            if (post) {
                if (pageList.size() != pageCount.intValue()) continue;
                break;
            }
            if (pageList.size() != pageCount.intValue()) continue;
            pageList.clear();
            ++page;
        }
        if (!post) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, "\u6307\u6807\u672a\u627e\u5230\u6216\u5df2\u88ab\u5220\u9664,\u5b9a\u4f4d\u5931\u8d25!");
        }
        result.setPageCount(pageCount);
        result.setTotal(fields.size());
        result.setPageNum(page);
        return result;
    }

    @ApiOperation(value="\u6307\u6807\u4e0a\u4e0b\u79fb\u52a8")
    @PostMapping(value={"field/move"})
    public DataFieldPageVO<List<DataFieldVO>> moveField(@RequestBody DataFieldMovePM move) throws JQException {
        this.logger.debug("\u79fb\u52a8\u6307\u6807 {}", (Object)move);
        List<String> keys = move.getKeys();
        if (CollectionUtils.isEmpty(keys)) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        boolean moveUp = move.isMoveUp();
        Integer limit = move.getLimit();
        if (limit == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        if (limit < 1) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        move.setLimit(limit - 1);
        Integer skip = move.getSkip();
        if (skip == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        if (skip < 1) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        move.setSkip(skip - 1);
        String table = move.getTable();
        if (table == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(table);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            List<MoveDataFieldDTO> designDataFields = this.dataFieldDesignService.move(move);
            ArrayList<DataFieldVO> list = new ArrayList<DataFieldVO>();
            this.fillFieldInfo(table, designDataFields, list, null);
            DataFieldPageVO<List<DataFieldVO>> pageVO = new DataFieldPageVO<List<DataFieldVO>>();
            pageVO.setValue(list);
            return pageVO;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5173\u8054\u679a\u4e3e\u6307\u6807\u5927\u5c0f")
    @GetMapping(value={"field/refenum/size"})
    public int getEntityCodeFieldSize(String entityId) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        TableModelDefine tableModel = null;
        tableModel = periodAdapter.isPeriodEntity(entityId) ? periodAdapter.getPeriodEntityTableModel(entityId) : this.entityMetaService.getTableModel(entityId);
        String bizKeys = tableModel.getBizKeys();
        ColumnModelDefine columnModel = this.modelService.getColumnModelDefineByID(bizKeys);
        if (null != columnModel) {
            return columnModel.getPrecision();
        }
        return 60;
    }

    @ApiOperation(value="\u6839\u636e\u5b57\u6bb5\u6807\u8bc6\u83b7\u53d6\u4e0b\u4e00\u4e2a\u5b57\u6bb5\u7684\u6807\u8bc6")
    @PostMapping(value={"next-field/code/get"})
    public String getNextFieldCode(@RequestBody NextFieldPM nextFieldPM) {
        List designDataFields;
        DataFieldKind dataFieldKind = nextFieldPM.getDataFieldKind();
        String code = nextFieldPM.getCode();
        Set collect = null;
        if (dataFieldKind == DataFieldKind.FIELD_ZB) {
            designDataFields = this.designDataSchemeService.getAllDataFieldByKind(nextFieldPM.getSchemeKey(), new DataFieldKind[]{dataFieldKind});
            collect = designDataFields.stream().map(Basic::getCode).collect(Collectors.toSet());
        } else if (dataFieldKind == DataFieldKind.FIELD) {
            designDataFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(nextFieldPM.getTableKey(), new DataFieldKind[]{dataFieldKind});
            collect = designDataFields.stream().map(Basic::getCode).collect(Collectors.toSet());
        }
        try {
            if (collect != null) {
                while (collect.contains(code)) {
                    code = NextFieldPM.getNextCode(code);
                }
                return code;
            }
            return NextFieldPM.getNextCode(code);
        }
        catch (Exception e) {
            return code;
        }
    }

    @ApiOperation(value="\u6307\u6807/\u5b57\u6bb5\u6279\u91cf\u68c0\u67e5\u662f\u5426\u6709\u6570\u636e")
    @PostMapping(value={"field/batch/check-data"})
    public void batchCheckData(@RequestBody List<String> keys) throws JQException {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        boolean checkData = this.runtimeDataSchemeService.dataFieldCheckData(keys.toArray(new String[keys.size()]));
        if (checkData) {
            this.logger.debug("\u8868\u4e0b\u6709\u6570\u636e");
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_D_1);
        }
        this.logger.debug("\u8868\u4e0b\u65e0\u6570\u636e");
    }

    @ApiOperation(value="\u6307\u6807/\u5b57\u6bb5\u6279\u91cf\u66f4\u65b0")
    @PostMapping(value={"field/batch/update"})
    public void batchUpdateDataField(@RequestBody BatUpDataFieldVO vo) throws JQException {
        List<String> fieldKeys = vo.getKeys();
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return;
        }
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        Set schemes = dataFields.stream().map(DataField::getDataSchemeKey).collect(Collectors.toSet());
        for (String scheme : schemes) {
            if (this.dataSchemeAuthService.canWriteScheme(scheme)) continue;
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            for (DesignDataField designDataField : dataFields) {
                EntityUtil.updateDataField(designDataField, vo);
                this.fieldValidator.levelCheckField(designDataField);
                designDataField.setUpdateTime(null);
            }
            this.designDataSchemeService.updateDataFields(dataFields);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6279\u91cf\u67e5\u8be2\u6307\u6807\u548c\u8868\u7684\u76f8\u5173\u4fe1\u606f")
    @PostMapping(value={"field-param/batch/query"})
    public List<DataFieldParamVO> fieldParamBatchQuery(@RequestBody List<String> fieldKeys) {
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return Collections.emptyList();
        }
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        List tableKeys = dataFields.stream().map(DataField::getDataTableKey).distinct().collect(Collectors.toList());
        List dataTables = this.designDataSchemeService.getDataTables(tableKeys);
        Map<String, DesignDataTable> tableMap = dataTables.stream().collect(Collectors.toMap(Basic::getKey, f -> f));
        ArrayList<DataFieldParamVO> res = new ArrayList<DataFieldParamVO>();
        for (DesignDataField designDataField : dataFields) {
            DataFieldParamVO dataFieldVO = new DataFieldParamVO();
            EntityUtil.fieldEntity2VO(dataFieldVO, (DataField)designDataField);
            DesignDataTable designDataTable = tableMap.get(dataFieldVO.getDataTableKey());
            if (designDataTable != null) {
                dataFieldVO.setDataTableType(designDataTable.getDataTableType());
            }
            DataFieldRestController.setDimension(dataFieldVO);
            res.add(dataFieldVO);
        }
        return res;
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u8131\u654f\u7b56\u7565")
    @GetMapping(value={"query-desensitization-strategy"})
    public List<DesensitizationInfoVO> queryDesensitizationStrategy() {
        String substringCode = new SubstringDesensitization().getCode();
        return this.desensitizationStrategyManager.getAllStrategys().stream().filter(info -> DesensitizationType.STRING == info.getType()).filter(info -> !substringCode.equals(info.getCode())).map(DesensitizationInfoVO::new).collect(Collectors.toList());
    }
}

