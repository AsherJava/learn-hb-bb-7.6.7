/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeValidator
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.midstore.core.internal.publish.service.MidstoreSDKLib
 */
package nr.midstore.design.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.DataSchemeValidator;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import nr.midstore.core.internal.publish.service.MidstoreSDKLib;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.service.IParamQueryService;
import nr.midstore.design.vo.MidstoreFormTreeVO;
import nr.midstore.design.vo.MistoreDataLinkVO;
import nr.midstore.design.vo.MistoreFieldVO;
import nr.midstore.design.vo.MistoreFormVO;
import nr.midstore.design.vo.MistoreGroupVO;
import nr.midstore.design.vo.MistoreTableVO;
import nr.midstore.design.vo.MistoreTaskVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ParamQueryServiceImpl
implements IParamQueryService {
    private static final Logger log = LoggerFactory.getLogger(ParamQueryServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataSchemeValidator dataSchemeValidator;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    public List<MistoreTaskVO> getAllTask() {
        ArrayList<MistoreTaskVO> result = new ArrayList<MistoreTaskVO>();
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            MistoreTaskVO commonParamDTO = new MistoreTaskVO();
            commonParamDTO.setKey(taskDefine.getKey());
            commonParamDTO.setCode(taskDefine.getTaskCode());
            commonParamDTO.setTitle(taskDefine.getTitle());
            commonParamDTO.setDataScheme(taskDefine.getDataScheme());
            commonParamDTO.setDw(taskDefine.getDw());
            commonParamDTO.setDateTime(taskDefine.getDateTime());
            commonParamDTO.setDims(taskDefine.getDims());
            commonParamDTO.setDesc(taskDefine.getDescription());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<MistoreTaskVO> getTaskList(List<String> taskKeys) {
        ArrayList<MistoreTaskVO> result = new ArrayList<MistoreTaskVO>();
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            if (!CollectionUtils.isEmpty(taskKeys) && !taskKeys.contains(taskDefine.getKey())) continue;
            MistoreTaskVO commonParamDTO = new MistoreTaskVO();
            commonParamDTO.setKey(taskDefine.getKey());
            commonParamDTO.setCode(taskDefine.getTaskCode());
            commonParamDTO.setTitle(taskDefine.getTitle());
            commonParamDTO.setDataScheme(taskDefine.getDataScheme());
            commonParamDTO.setDw(taskDefine.getDw());
            commonParamDTO.setDateTime(taskDefine.getDateTime());
            commonParamDTO.setDims(taskDefine.getDims());
            commonParamDTO.setDesc(taskDefine.getDescription());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public MistoreTaskVO getTaskDefine(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            MistoreTaskVO task = new MistoreTaskVO();
            task.setKey(taskDefine.getKey());
            task.setCode(taskDefine.getTaskCode());
            task.setTitle(taskDefine.getTitle());
            task.setDataScheme(taskDefine.getDataScheme());
            task.setDw(taskDefine.getDw());
            task.setDateTime(taskDefine.getDateTime());
            task.setDims(taskDefine.getDims());
            task.setDesc(taskDefine.getDescription());
            return task;
        }
        return null;
    }

    @Override
    public List<CommonParamDTO> listFormScheme(String taskKey) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List designFormSchemeDefines = null;
        try {
            designFormSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5f02\u5e38");
        }
        for (FormSchemeDefine formSchemeDefine : designFormSchemeDefines) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(formSchemeDefine.getKey());
            commonParamDTO.setCode(formSchemeDefine.getFormSchemeCode());
            commonParamDTO.setTitle(formSchemeDefine.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listDataTable(String dataSchemeKey, Boolean tableType) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List dataTableByScheme = this.runtimeDataSchemeService.getAllDataTable(dataSchemeKey);
        for (DataTable dataTable : dataTableByScheme) {
            if (tableType != false ? dataTable.getDataTableType() != DataTableType.TABLE : dataTable.getDataTableType() != DataTableType.DETAIL) continue;
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(dataTable.getKey());
            commonParamDTO.setCode(dataTable.getCode());
            commonParamDTO.setTitle(dataTable.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listZBByTableCode(String tableCode) {
        String[] filter = new String[]{"MDCODE", "DATATIME", "BIZKEYORDER", "FLOATORDER"};
        List<String> list = Arrays.asList(filter);
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List dataFieldByTableCode = this.runtimeDataSchemeService.getDataFieldByTableCode(tableCode);
        for (DataField dataField : dataFieldByTableCode) {
            if (list.contains(dataField.getCode())) continue;
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(dataField.getKey());
            commonParamDTO.setCode(dataField.getCode());
            commonParamDTO.setTitle(dataField.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> getEnumList(String dataSchemeKey) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List<BaseDataDefineDO> list = this.getEnumDefineByDSKey(dataSchemeKey);
        for (BaseDataDefineDO baseDataDefineDO : list) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(baseDataDefineDO.getId().toString());
            commonParamDTO.setCode(baseDataDefineDO.getName());
            commonParamDTO.setTitle(baseDataDefineDO.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listPeriod() {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List iPeriodEntities = this.periodEngineService.getPeriodAdapter().getPeriodEntity();
        for (IPeriodEntity periodEntity : iPeriodEntities) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(periodEntity.getKey());
            commonParamDTO.setCode(periodEntity.getCode());
            commonParamDTO.setTitle(periodEntity.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listEntity(List<String> entityKeys) {
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>(entityKeys.size());
        for (String entityKey : entityKeys) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
            if (entityDefine == null) continue;
            CommonParamDTO paramDTO = new CommonParamDTO();
            paramDTO.setKey(entityDefine.getId());
            paramDTO.setCode(entityDefine.getCode());
            paramDTO.setTitle(entityDefine.getTitle());
            list.add(paramDTO);
        }
        return list;
    }

    private List<BaseDataDefineDO> getEnumDefineByDSKey(String dsKey) {
        if (StringUtils.isEmpty((String)dsKey)) {
            return new ArrayList<BaseDataDefineDO>();
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dsKey);
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName("__default_tenant__");
        baseDataDefineDTO.setSearchKey("MD_" + dataScheme.getPrefix() + "_");
        PageVO list = client.list(baseDataDefineDTO);
        return list.getRows();
    }

    @Override
    public String getEntityIdByTask(String taskKey) {
        TaskDefine task = this.runTimeViewController.queryTaskDefine(taskKey);
        return task.getDw();
    }

    @Override
    public List<MistoreFieldVO> getFieldByBaseData(String baseDataKey) {
        DataModelDO dataModel = this.queryBaseDataMode(baseDataKey);
        ArrayList<MistoreFieldVO> list = new ArrayList<MistoreFieldVO>();
        if (dataModel != null) {
            MistoreFieldVO codeField = new MistoreFieldVO();
            codeField.setKey(null);
            codeField.setCode("CODE");
            codeField.setTitle("\u6807\u8bc6");
            codeField.setPrecisiion(60);
            codeField.setDecimal(0);
            codeField.setFieldType(DataFieldType.STRING);
            codeField.setLinkType(0);
            codeField.setLinkKey(null);
            codeField.getValues().put("isSystemField", true);
            list.add(codeField);
            MistoreFieldVO nameField = new MistoreFieldVO();
            nameField.setKey(null);
            nameField.setCode("NAME");
            nameField.setTitle("\u540d\u79f0");
            nameField.setPrecisiion(200);
            nameField.setDecimal(0);
            nameField.setFieldType(DataFieldType.STRING);
            nameField.setLinkType(0);
            nameField.setLinkKey(null);
            nameField.getValues().put("isSystemField", true);
            list.add(nameField);
            MistoreFieldVO shortnameField = new MistoreFieldVO();
            shortnameField.setKey(null);
            shortnameField.setCode("SHORTNAME");
            shortnameField.setTitle("\u7b80\u79f0");
            shortnameField.setPrecisiion(100);
            shortnameField.setDecimal(0);
            shortnameField.setFieldType(DataFieldType.STRING);
            shortnameField.setLinkType(0);
            shortnameField.setLinkKey(null);
            shortnameField.getValues().put("isSystemField", true);
            list.add(shortnameField);
            MistoreFieldVO parentField = new MistoreFieldVO();
            parentField.setKey(null);
            parentField.setCode("PARENTCODE");
            parentField.setTitle("\u4e0a\u7ea7\u4ee3\u7801");
            parentField.setPrecisiion(60);
            parentField.setDecimal(0);
            parentField.setFieldType(DataFieldType.STRING);
            parentField.setLinkType(0);
            parentField.setLinkKey(null);
            parentField.getValues().put("isSystemField", true);
            list.add(parentField);
            MistoreFieldVO unitCodeField = new MistoreFieldVO();
            unitCodeField.setKey(null);
            unitCodeField.setCode("UNITCODE");
            unitCodeField.setTitle("\u552f\u4e00\u4ee3\u7801");
            unitCodeField.setPrecisiion(60);
            unitCodeField.setDecimal(0);
            unitCodeField.setFieldType(DataFieldType.STRING);
            unitCodeField.setLinkType(0);
            unitCodeField.setLinkKey(null);
            unitCodeField.getValues().put("isSystemField", true);
            list.add(unitCodeField);
            for (DataModelColumn column : dataModel.getColumns()) {
                if (column.getColumnAttr() == DataModelType.ColumnAttr.SYSTEM) continue;
                MistoreFieldVO field = new MistoreFieldVO();
                field.setCode(column.getColumnName());
                field.setTitle(column.getColumnTitle());
                if (column.getLengths() != null && column.getLengths().length > 0) {
                    field.setPrecisiion(column.getLengths()[0]);
                }
                if (column.getLengths() != null && column.getLengths().length > 1) {
                    field.setDecimal(column.getLengths()[1]);
                } else {
                    field.setDecimal(0);
                }
                field.setFieldType(MidstoreSDKLib.getDataTypeByColumn((DataModelType.ColumnType)column.getColumnType()));
                field.setLinkType(0);
                field.setLinkKey(null);
                list.add(field);
            }
        }
        return list;
    }

    @Override
    public List<MistoreFieldVO> getFieldByEntityID(String entityID) {
        String orgCode = EntityUtils.getId((String)entityID);
        return this.getFieldByOrg(orgCode);
    }

    @Override
    public List<MistoreFieldVO> getFieldByOrg(String orgDataName) {
        ArrayList<MistoreFieldVO> list = new ArrayList<MistoreFieldVO>();
        OrgCategoryDO orgDefine = this.queryOrgDatadefine(orgDataName);
        if (orgDefine != null) {
            MistoreFieldVO codeField = new MistoreFieldVO();
            codeField.setKey(null);
            codeField.setCode("CODE");
            codeField.setTitle("\u673a\u6784\u4ee3\u7801");
            codeField.setPrecisiion(60);
            codeField.setDecimal(0);
            codeField.setFieldType(DataFieldType.STRING);
            codeField.setLinkType(0);
            codeField.setLinkKey(null);
            codeField.setTableCode(orgDataName);
            codeField.setTableTitle(orgDefine.getTitle());
            codeField.getValues().put("isSystemField", true);
            list.add(codeField);
            MistoreFieldVO parentField = new MistoreFieldVO();
            parentField.setKey(null);
            parentField.setCode("PARENTCODE");
            parentField.setTitle("\u673a\u6784\u4e0a\u7ea7\u4ee3\u7801");
            parentField.setPrecisiion(60);
            parentField.setDecimal(0);
            parentField.setFieldType(DataFieldType.STRING);
            parentField.setLinkType(0);
            parentField.setLinkKey(null);
            parentField.setTableCode(orgDataName);
            parentField.setTableTitle(orgDefine.getTitle());
            parentField.getValues().put("isSystemField", true);
            list.add(parentField);
            MistoreFieldVO orgCodeField = new MistoreFieldVO();
            orgCodeField.setKey(null);
            orgCodeField.setCode("ORGCODE");
            orgCodeField.setTitle("\u673a\u6784\u7f16\u7801");
            orgCodeField.setPrecisiion(60);
            orgCodeField.setDecimal(0);
            orgCodeField.setFieldType(DataFieldType.STRING);
            orgCodeField.setLinkType(0);
            orgCodeField.setLinkKey(null);
            orgCodeField.setTableCode(orgDataName);
            orgCodeField.setTableTitle(orgDefine.getTitle());
            orgCodeField.getValues().put("isSystemField", true);
            list.add(orgCodeField);
            MistoreFieldVO nameField = new MistoreFieldVO();
            nameField.setKey(null);
            nameField.setCode("NAME");
            nameField.setTitle("\u673a\u6784\u540d\u79f0");
            nameField.setPrecisiion(200);
            nameField.setDecimal(0);
            nameField.setFieldType(DataFieldType.STRING);
            nameField.setLinkType(0);
            nameField.setLinkKey(null);
            nameField.setTableCode(orgDataName);
            nameField.setTableTitle(orgDefine.getTitle());
            nameField.getValues().put("isSystemField", true);
            list.add(nameField);
            MistoreFieldVO shortNameField = new MistoreFieldVO();
            shortNameField.setKey(null);
            shortNameField.setCode("SHORTNAME");
            shortNameField.setTitle("\u673a\u6784\u7b80\u79f0");
            shortNameField.setPrecisiion(100);
            shortNameField.setDecimal(0);
            shortNameField.setFieldType(DataFieldType.STRING);
            shortNameField.setLinkType(0);
            shortNameField.setLinkKey(null);
            shortNameField.setTableCode(orgDataName);
            shortNameField.setTableTitle(orgDefine.getTitle());
            shortNameField.getValues().put("isSystemField", true);
            list.add(shortNameField);
            List orgZbList = orgDefine.getZbs();
            for (ZB orgZb : orgZbList) {
                MistoreFieldVO field = new MistoreFieldVO();
                field.setKey(orgZb.getId().toString());
                field.setCode(orgZb.getName());
                field.setTitle(orgZb.getTitle());
                if (orgZb.getPrecision() != null) {
                    field.setPrecisiion(orgZb.getPrecision());
                }
                if (orgZb.getDecimal() != null) {
                    field.setDecimal(orgZb.getDecimal());
                } else {
                    field.setDecimal(0);
                }
                field.setFieldType(MidstoreSDKLib.getDataTypeByOrg((int)orgZb.getDatatype()));
                if (orgZb.getRelatetype() != null) {
                    field.setLinkType(orgZb.getRelatetype());
                    field.setLinkKey(orgZb.getReltablename());
                } else {
                    field.setLinkType(1);
                    field.setLinkKey(null);
                }
                field.setTableCode(orgDataName);
                field.setTableTitle(orgDefine.getTitle());
                field.getValues().put("isSystemField", false);
                list.add(field);
            }
        }
        return list;
    }

    @Override
    public List<MistoreFieldVO> getFieldByTable(String datatableKey) {
        ArrayList<MistoreFieldVO> list = new ArrayList<MistoreFieldVO>();
        DataTable table = this.runtimeDataSchemeService.getDataTable(datatableKey);
        List qList = this.runtimeDataSchemeService.getDataFieldByTable(datatableKey);
        List bzFieldList = this.runtimeDataSchemeService.getBizDataFieldByTableKey(datatableKey);
        if (qList != null) {
            HashSet<Object> bizKeyList = new HashSet<Object>();
            if (table != null) {
                String[] bizKeys = table.getBizKeys();
                for (String fieldID : bizKeys) {
                    bizKeyList.add(fieldID);
                }
            }
            List<Object> list1 = new ArrayList();
            if (bzFieldList != null && bzFieldList.size() > 0) {
                for (DataField oldField : bzFieldList) {
                    if (oldField.getDataFieldKind() != DataFieldKind.TABLE_FIELD_DIM) continue;
                    MistoreFieldVO field = new MistoreFieldVO();
                    this.setFieldAttr(field, oldField, table);
                    if (bizKeyList.contains(oldField.getKey())) {
                        field.getValues().put("isBizKeyField", true);
                    } else {
                        field.getValues().put("isBizKeyField", false);
                    }
                    list1.add(field);
                }
                if (list1.size() > 0) {
                    list1 = list1.stream().sorted(Comparator.comparing(CommonParamDTO::getCode)).collect(Collectors.toList());
                    list.addAll(list1);
                }
            }
            List<Object> list2 = new ArrayList();
            for (DataField oldField : qList) {
                if (oldField.getDataFieldKind() != DataFieldKind.FIELD_ZB && oldField.getDataFieldKind() != DataFieldKind.FIELD) continue;
                MistoreFieldVO field = new MistoreFieldVO();
                this.setFieldAttr(field, oldField, table);
                if (bizKeyList.contains(oldField.getKey())) {
                    field.getValues().put("isBizKeyField", true);
                } else {
                    field.getValues().put("isBizKeyField", false);
                }
                list2.add(field);
            }
            if (list2.size() > 0) {
                list2 = list2.stream().sorted(Comparator.comparing(CommonParamDTO::getCode)).collect(Collectors.toList());
                list.addAll(list2);
            }
        }
        return list;
    }

    private void setFieldAttr(MistoreFieldVO field, DataField oldField, DataTable table) {
        field.setKey(oldField.getKey());
        field.setCode(oldField.getCode());
        field.setTitle(oldField.getTitle());
        if (oldField.getPrecision() != null) {
            field.setPrecisiion(oldField.getPrecision());
        }
        if (oldField.getDecimal() != null) {
            field.setDecimal(oldField.getDecimal());
        } else {
            field.setDecimal(0);
        }
        field.setFieldType(oldField.getDataFieldType());
        if (StringUtils.isNotEmpty((String)oldField.getRefDataEntityKey())) {
            field.setLinkType(1);
            field.setLinkKey(oldField.getRefDataEntityKey());
        } else {
            field.setLinkType(0);
            field.setLinkKey(null);
        }
        if (table != null) {
            field.setTableCode(table.getCode());
            field.setTableKey(table.getKey());
            field.setTableTitle(table.getTitle());
        }
    }

    private OrgCategoryDO queryOrgDatadefine(String orgName) {
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        PageVO orgDefines = this.orgCategoryClient.list(param);
        OrgCategoryDO orgDefine = null;
        if (orgDefines != null && orgDefines.getRows().size() > 0) {
            orgDefine = (OrgCategoryDO)orgDefines.getRows().get(0);
        }
        return orgDefine;
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

    private List<IEntityAttribute> getEntitiesByOwnTable(String ownTableKey) {
        IEntityModel entityModel;
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(ownTableKey);
        if (entityDefine == null) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u7684\u5b9e\u4f53\u5b9a\u4e49[%s]", ownTableKey));
        }
        try {
            entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u5b9e\u4f53[%s]\u7684\u6a21\u578b", ownTableKey));
        }
        return entityModel.getShowFields();
    }

    @Override
    public List<MistoreGroupVO> getAllBaseDataGroups() {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        PageVO qList = this.baseDataDefineClient.list(param);
        for (BaseDataGroupDO oldGroup : qList.getRows()) {
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getId().toString());
            group.setCode(oldGroup.getName());
            group.setTitle(oldGroup.getTitle());
            if (!"-".equalsIgnoreCase(oldGroup.getParentname())) {
                group.setParent(oldGroup.getParentname());
            }
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getRootBaseDataGroups() {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        PageVO qList = this.baseDataDefineClient.list(param);
        for (BaseDataGroupDO oldGroup : qList.getRows()) {
            if (!StringUtils.isEmpty((String)oldGroup.getParentname())) continue;
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getId().toString());
            group.setCode(oldGroup.getName());
            group.setTitle(oldGroup.getTitle());
            if (!"-".equalsIgnoreCase(oldGroup.getParentname())) {
                group.setParent(oldGroup.getParentname());
            }
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getBaseDataGroupsByParent(String groupCode) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        param.setParentname(groupCode);
        PageVO qList = this.baseDataDefineClient.list(param);
        for (BaseDataGroupDO oldGroup : qList.getRows()) {
            if (!StringUtils.isNotEmpty((String)oldGroup.getParentname()) || !oldGroup.getParentname().equalsIgnoreCase(groupCode)) continue;
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getId().toString());
            group.setCode(oldGroup.getName());
            group.setTitle(oldGroup.getTitle());
            if (!"-".equalsIgnoreCase(oldGroup.getParentname())) {
                group.setParent(oldGroup.getParentname());
            }
            list.add(group);
        }
        return list;
    }

    @Override
    public List<CommonParamDTO> getBaseDatasByGroup(String groupCode) {
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>();
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setGroupname(groupCode);
        PageVO qList = this.baseDataDefineClient.list(param);
        for (BaseDataDefineDO define : qList.getRows()) {
            CommonParamDTO baseData = new CommonParamDTO();
            baseData.setKey(define.getId().toString());
            baseData.setCode(define.getName());
            baseData.setTitle(define.getTitle());
            baseData.setDesc(define.getRemark());
            list.add(baseData);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getAllDataGroupsByScheme(String dataSchemeKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runtimeDataSchemeService.getAllDataGroup(dataSchemeKey);
        for (DataGroup oldGroup : qList) {
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getRootDataGroupsByScheme(String dataSchemeKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runtimeDataSchemeService.getDataGroupByScheme(dataSchemeKey);
        for (DataGroup oldGroup : qList) {
            if (!StringUtils.isEmpty((String)oldGroup.getParentKey())) continue;
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getDataGroupsByParent(String groupKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runtimeDataSchemeService.getDataGroupByParent(groupKey);
        for (DataGroup oldGroup : qList) {
            if (!StringUtils.isEmpty((String)oldGroup.getParentKey())) continue;
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreTableVO> getRootDataTablesByScheme(String dataSchemeKey) {
        ArrayList<MistoreTableVO> list = new ArrayList<MistoreTableVO>();
        List qList = this.runtimeDataSchemeService.getDataTableByScheme(dataSchemeKey);
        for (DataTable define : qList) {
            if (!StringUtils.isEmpty((String)define.getDataGroupKey())) continue;
            MistoreTableVO baseData = new MistoreTableVO();
            baseData.setKey(define.getKey());
            baseData.setCode(define.getCode());
            baseData.setTitle(define.getTitle());
            baseData.setDesc(define.getDesc());
            baseData.setTableType(define.getDataTableType());
            baseData.setGroupKey(null);
            list.add(baseData);
        }
        return list;
    }

    @Override
    public List<MistoreTableVO> getDataTablesByGroup(String groupKey) {
        ArrayList<MistoreTableVO> list = new ArrayList<MistoreTableVO>();
        List qList = this.runtimeDataSchemeService.getDataTableByGroup(groupKey);
        for (DataTable define : qList) {
            MistoreTableVO baseData = new MistoreTableVO();
            baseData.setKey(define.getKey());
            baseData.setCode(define.getCode());
            baseData.setTitle(define.getTitle());
            baseData.setDesc(define.getDesc());
            baseData.setTableType(define.getDataTableType());
            baseData.setGroupKey(groupKey);
            list.add(baseData);
        }
        return list;
    }

    @Override
    public List<MistoreTableVO> getAllDataTablesByScheme(String dataSchemeKey) {
        ArrayList<MistoreTableVO> list = new ArrayList<MistoreTableVO>();
        List qList = this.runtimeDataSchemeService.getDataTableByScheme(dataSchemeKey);
        for (DataTable define : qList) {
            MistoreTableVO baseData = new MistoreTableVO();
            baseData.setKey(define.getKey());
            baseData.setCode(define.getCode());
            baseData.setTitle(define.getTitle());
            baseData.setDesc(define.getDesc());
            baseData.setGroupKey(define.getDataGroupKey());
            baseData.setTableType(define.getDataTableType());
            list.add(baseData);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getAllFormGroupsByScheme(String formSchemeKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        for (FormGroupDefine oldGroup : qList) {
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getRootFormGroupsByScheme(String formSchemeKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        for (FormGroupDefine oldGroup : qList) {
            if (!StringUtils.isEmpty((String)oldGroup.getParentKey())) continue;
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreGroupVO> getFormGroupsByParent(String groupKey) {
        ArrayList<MistoreGroupVO> list = new ArrayList<MistoreGroupVO>();
        List qList = this.runTimeViewController.getChildFormGroups(groupKey);
        for (FormGroupDefine oldGroup : qList) {
            MistoreGroupVO group = new MistoreGroupVO();
            group.setKey(oldGroup.getKey());
            group.setCode(oldGroup.getCode());
            group.setTitle(oldGroup.getTitle());
            group.setParent(oldGroup.getParentKey());
            list.add(group);
        }
        return list;
    }

    @Override
    public List<MistoreFormVO> getFormsByGroup(String groupKey, boolean isRecursion) throws Exception {
        ArrayList<MistoreFormVO> list = new ArrayList<MistoreFormVO>();
        List qList = this.runTimeViewController.getAllFormsInGroup(groupKey, isRecursion);
        for (FormDefine oldGroup : qList) {
            MistoreFormVO form = new MistoreFormVO();
            form.setKey(oldGroup.getKey());
            form.setCode(oldGroup.getFormCode());
            form.setTitle(oldGroup.getTitle());
            form.setGroupKey(groupKey);
            list.add(form);
        }
        return list;
    }

    @Override
    public List<MistoreFormVO> getAllFormsByScheme(String formSchemeKey) {
        ArrayList<MistoreFormVO> list = new ArrayList<MistoreFormVO>();
        List qList = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine oldGroup : qList) {
            MistoreFormVO form = new MistoreFormVO();
            form.setKey(oldGroup.getKey());
            form.setCode(oldGroup.getFormCode());
            form.setTitle(oldGroup.getTitle());
            list.add(form);
        }
        return list;
    }

    @Override
    public List<MidstoreFormTreeVO> getReportTree(String formSchemeKey) {
        ArrayList<MidstoreFormTreeVO> tree = new ArrayList<MidstoreFormTreeVO>();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<FormGroupDefine> groupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        groupDefines.forEach(item -> {
            MidstoreFormTreeVO node = new MidstoreFormTreeVO();
            node.setKey(item.getKey());
            node.setTitle(item.getTitle());
            node.setCode(item.getCode());
            node.setType("NODE_TYPE_GROUP");
            List<Object> forms = new ArrayList();
            try {
                forms = this.runTimeViewController.getAllFormsInGroup(item.getKey(), true).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            node.setExpand(forms.size() > 0);
            ArrayList<MidstoreFormTreeVO> childs = new ArrayList<MidstoreFormTreeVO>();
            forms.forEach(f -> {
                MidstoreFormTreeVO children = new MidstoreFormTreeVO();
                children.setKey(f.getKey());
                children.setTitle(f.getTitle());
                children.setCode(f.getFormCode());
                children.setType("NODE_TYPE_FORM");
                children.setExpand(false);
                children.setFormType(f.getFormType().getValue());
                childs.add(children);
            });
            node.setChildren(childs);
            if (childs.size() > 0 && !flag.get()) {
                ((MidstoreFormTreeVO)childs.get(0)).setSelected(true);
                flag.set(true);
            }
            tree.add(node);
        });
        return tree;
    }

    @Override
    public List<MistoreDataLinkVO> getDataLinkByForm(String formKey) {
        ArrayList<MistoreDataLinkVO> list = new ArrayList<MistoreDataLinkVO>();
        List links = this.runTimeViewController.getAllLinksInForm(formKey);
        if (links != null) {
            for (DataLinkDefine define : links) {
                MistoreDataLinkVO link = new MistoreDataLinkVO();
                link.setKey(define.getKey());
                link.setTitle(define.getTitle());
                link.setPosX(define.getPosX());
                link.setPosY(define.getPosY());
                link.setColNum(define.getColNum());
                link.setRowNum(define.getRowNum());
                link.setLinkExpression(define.getLinkExpression());
                link.setType(define.getType());
                link.setRegionKey(define.getRegionKey());
                link.setFormKey(formKey);
                link.setLinkExpression(define.getLinkExpression());
                if (define.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                    try {
                        DataField fieldDefine = this.runtimeDataSchemeService.getDataField(define.getLinkExpression());
                        DataTable table = this.runtimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
                        MistoreFieldVO field = new MistoreFieldVO();
                        field.setKey(fieldDefine.getKey());
                        field.setCode(fieldDefine.getCode());
                        field.setTitle(fieldDefine.getTitle());
                        if (fieldDefine.getPrecision() != null) {
                            field.setPrecisiion(fieldDefine.getPrecision());
                        }
                        if (fieldDefine.getDecimal() != null) {
                            field.setDecimal(fieldDefine.getDecimal());
                        } else {
                            field.setDecimal(0);
                        }
                        field.setFieldType(fieldDefine.getDataFieldType());
                        field.setLinkType(0);
                        field.setLinkKey(null);
                        field.setTableKey(table.getKey());
                        field.setTableTitle(table.getTitle());
                        link.setField(field);
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                } else if (define.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                    // empty if block
                }
                list.add(link);
            }
        }
        return list;
    }

    @Override
    public Grid2Data getGridDataByForm(String formKey) {
        BigDataDefine formDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runTimeViewController.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }
}

