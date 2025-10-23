/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.task.form.field.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.dto.DragNodeDTO;
import com.jiuqi.nr.task.form.dto.DragResultDTO;
import com.jiuqi.nr.task.form.dto.EntityFieldDTO;
import com.jiuqi.nr.task.form.dto.TaskSchemeNodeFilter;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.field.service.IDataFieldService;
import com.jiuqi.nr.task.form.service.AbstractDataSchemeSupport;
import com.jiuqi.nr.task.form.util.EntityFieldBeanUtils;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import com.jiuqi.nr.task.form.util.TableBeanUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFieldServiceImpl
extends AbstractDataSchemeSupport
implements IDataFieldService {
    private final IDesignDataSchemeService designDataSchemeService;
    private final IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDataSchemeTreeService<DataSchemeNode> treeService;
    @Autowired
    private IDesignDataSchemeTreeService designDataSchemeTreeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService dataModelService;

    public DataFieldServiceImpl(IDesignTimeViewController designTimeViewController, IDesignDataSchemeService designDataSchemeService) {
        super(designTimeViewController, designDataSchemeService);
        this.designDataSchemeService = designDataSchemeService;
        this.designTimeViewController = designTimeViewController;
    }

    @Override
    public void saveFields(String formKey, List<DataFieldSettingDTO> dataFieldSetting) {
        Map<String, DataFieldSettingDTO> updateSettingMap;
        List dataFields;
        List updateFields;
        Map<Constants.DataStatus, List<DataFieldSettingDTO>> fieldSettingStatusMap = dataFieldSetting.stream().collect(Collectors.groupingBy(AbstractState::getStatus));
        List newDataFields = fieldSettingStatusMap.getOrDefault(Constants.DataStatus.NEW, Collections.emptyList()).stream().map(setting -> {
            DesignDataField designDataField = this.designDataSchemeService.initDataField();
            FieldBeanUtils.toDefine(setting, designDataField);
            return designDataField;
        }).collect(Collectors.toList());
        if (!newDataFields.isEmpty()) {
            this.designDataSchemeService.insertDataFields(newDataFields);
        }
        if (!(updateFields = (dataFields = this.designDataSchemeService.getDataFields(new ArrayList<String>((updateSettingMap = fieldSettingStatusMap.getOrDefault(Constants.DataStatus.MODIFY, Collections.emptyList()).stream().collect(Collectors.toMap(DataCore::getKey, f -> f))).keySet()))).stream().map(field -> {
            DataFieldSettingDTO fieldSettingDTO = (DataFieldSettingDTO)updateSettingMap.get(field.getKey());
            DesignDataField designDataField = this.designDataSchemeService.initDataField();
            FieldBeanUtils.toDefine(fieldSettingDTO, designDataField);
            return designDataField;
        }).collect(Collectors.toList())).isEmpty()) {
            this.designDataSchemeService.updateDataFields(updateFields);
        }
    }

    @Override
    public DataFieldSettingDTO getFieldSetting(String fieldKey) {
        DesignDataField dataField = this.designDataSchemeService.getDataField(fieldKey);
        return FieldBeanUtils.toSettingDto(dataField, this.entityMetaService, this.periodEngineService);
    }

    @Override
    public List<DataFieldSettingDTO> listFieldsByTable(String tableKey) {
        List dataFields = this.designDataSchemeService.getDataFieldByTable(tableKey);
        return FieldBeanUtils.toSettingList(dataFields, this.entityMetaService, this.periodEngineService, this.designDataSchemeService);
    }

    @Override
    public List<ITree<DataSchemeNode>> getFieldTreeRoot(DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        String dataSchemeKey = param.getDataSchemeKey();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DesignTaskDefine queryTaskDefine = this.designTimeViewController.getTask(param.getDimKey());
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(queryTaskDefine.getKey());
        List<String> taskOrg = taskOrgLinkDefines.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toList());
        int interestType = this.allField();
        interestType = StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.TABLE.getValue() | NodeType.SCHEME.getValue() | NodeType.MD_INFO.getValue() | NodeType.DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD_ZB.getValue() : (StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_ACCOUNT.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.SCHEME.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.TABLE.getValue() | NodeType.TABLE_DIM.getValue() : NodeType.MD_INFO.getValue() | NodeType.SCHEME.getValue() | NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue());
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter(taskOrg, dataSchemeKey, dimensions, FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, true);
        return this.designDataSchemeTreeService.getRootTree(dataSchemeKey, interestType, (NodeFilter)filter);
    }

    private int allField() {
        int interestType = 0;
        for (NodeType value : NodeType.values()) {
            if (value.getValue() <= NodeType.SCHEME_GROUP.getValue() || value.getValue() == NodeType.FMDM_TABLE.getValue()) continue;
            interestType |= value.getValue();
        }
        return interestType;
    }

    @Override
    public List<ITree<DataSchemeNode>> getFieldTreeChild(DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO parent = (DataSchemeNodeDTO)param.getDataSchemeNode();
        int interestType = this.getInterestType(param);
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter(new ArrayList<String>(), param.getDataSchemeKey(), new ArrayList<DesignDataDimension>(), FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, false);
        return this.designDataSchemeTreeService.getChildTree((DataSchemeNode)parent, interestType, (NodeFilter)filter);
    }

    private int getInterestType(DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        int interestType = org.springframework.util.StringUtils.hasLength(param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.TABLE.getValue() | NodeType.SCHEME.getValue() | NodeType.MD_INFO.getValue() | NodeType.DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD_ZB.getValue() : (org.springframework.util.StringUtils.hasLength(param.getFilter()) && FormType.FORM_TYPE_ACCOUNT.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.SCHEME.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.TABLE.getValue() | NodeType.TABLE_DIM.getValue() : NodeType.MD_INFO.getValue() | NodeType.SCHEME.getValue() | NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue());
        return interestType;
    }

    @Override
    public List<DataSchemeNode> filterFieldTree(DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        TreeSearchQuery searchQuery = new TreeSearchQuery(param.getFilter(), param.getDataSchemeKey());
        searchQuery.setSearchType(NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue());
        return this.treeService.search(searchQuery);
    }

    @Override
    public List<ITree<DataSchemeNode>> getFieldTreePath(DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO node = (DataSchemeNodeDTO)param.getDataSchemeNode();
        String dataSchemeKey = param.getDataSchemeKey();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DesignTaskDefine queryTaskDefine = this.designTimeViewController.getTask(param.getDimKey());
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(queryTaskDefine.getKey());
        List<String> taskOrg = taskOrgLinkDefines.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toList());
        int interestType = this.allField();
        if (org.springframework.util.StringUtils.hasLength(param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue()) {
            interestType = NodeType.FIELD_ZB.getValue() | NodeType.DIM.getValue() | NodeType.SCHEME.getValue() | NodeType.TABLE.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.MD_INFO.getValue();
        }
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter(taskOrg, dataSchemeKey, dimensions, FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, true);
        return this.designDataSchemeTreeService.getSpecifiedTree((DataSchemeNode)node, param.getDataSchemeKey(), interestType, (NodeFilter)filter);
    }

    @Override
    public DragResultDTO queryDragResult(List<DragNodeDTO> dragNodes) {
        DragResultDTO res = new DragResultDTO();
        if (dragNodes != null) {
            String refEntityTitle;
            ArrayList<DataFieldDTO> fieldDTOS = new ArrayList<DataFieldDTO>();
            ArrayList<EntityFieldDTO> entityFields = new ArrayList<EntityFieldDTO>();
            res.setFields(fieldDTOS);
            res.setEntityFields(entityFields);
            LinkedHashSet<String> fieldKeys = new LinkedHashSet<String>();
            HashSet<String> tableKeys = new HashSet<String>();
            IEntityModel entityModel = null;
            HashMap<String, IEntityDefine> entityDefineMap = new HashMap<String, IEntityDefine>();
            for (DragNodeDTO dragNode : dragNodes) {
                switch (NodeType.valueOf((int)dragNode.getType())) {
                    case FIELD: 
                    case FIELD_ZB: {
                        fieldKeys.add(dragNode.getKey());
                        if (dragNode.getParentKey() == null) break;
                        tableKeys.add(dragNode.getParentKey());
                        break;
                    }
                    case TABLE: 
                    case DETAIL_TABLE: 
                    case MD_INFO: {
                        tableKeys.add(dragNode.getKey());
                        List dataFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(dragNode.getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
                        List<DataFieldDTO> dtoList = FieldBeanUtils.toDtoList(dataFields);
                        for (DataFieldDTO dataFieldDTO : dtoList) {
                            String refEntityTitle2 = this.getFieldRefEntityTitle(dataFieldDTO.getRefDataEntityKey(), entityDefineMap);
                            if (!org.springframework.util.StringUtils.hasLength(refEntityTitle2)) continue;
                            dataFieldDTO.setRefDataEntityTitle(refEntityTitle2);
                        }
                        fieldDTOS.addAll(dtoList);
                        break;
                    }
                    case ENTITY_ATTRIBUTE: {
                        EntityFieldDTO dto;
                        if (entityModel == null) {
                            entityModel = this.entityMetaService.getEntityModel(dragNode.getParentKey());
                        }
                        if (org.springframework.util.StringUtils.hasLength(refEntityTitle = this.getRefEntityTitle((dto = EntityFieldBeanUtils.toDTO(entityModel.getAttribute(dragNode.getCode()))).getRefDataEntityKey(), entityDefineMap))) {
                            dto.setRefDataEntityTitle(refEntityTitle);
                        }
                        entityFields.add(dto);
                        break;
                    }
                    case DIM: {
                        entityModel = this.entityMetaService.getEntityModel(dragNode.getCode());
                        Iterator attributes = entityModel.getAttributes();
                        while (attributes.hasNext()) {
                            entityFields.add(EntityFieldBeanUtils.toDTO((IEntityAttribute)attributes.next()));
                        }
                        break;
                    }
                }
            }
            if (!tableKeys.isEmpty()) {
                DesignDataTable dataTable = this.designDataSchemeService.getDataTable((String)new ArrayList(tableKeys).get(0));
                res.setTable(TableBeanUtils.toDto(dataTable));
            }
            if (!fieldKeys.isEmpty()) {
                ArrayList keys = new ArrayList(fieldKeys);
                List dataFields = this.designDataSchemeService.getDataFields(keys);
                dataFields.sort(Comparable::compareTo);
                List<DataFieldDTO> dtoList = FieldBeanUtils.toDtoList(dataFields);
                for (DataFieldDTO dataFieldDTO : dtoList) {
                    refEntityTitle = this.getFieldRefEntityTitle(dataFieldDTO.getRefDataEntityKey(), entityDefineMap);
                    if (!org.springframework.util.StringUtils.hasLength(refEntityTitle)) continue;
                    dataFieldDTO.setRefDataEntityTitle(refEntityTitle);
                }
                fieldDTOS.addAll(dtoList);
            }
        }
        return res;
    }

    private String getFieldRefEntityTitle(String refEngityKey, Map<String, IEntityDefine> entityDefineMap) {
        if (org.springframework.util.StringUtils.hasLength(refEngityKey)) {
            if (null != entityDefineMap.get(refEngityKey)) {
                return entityDefineMap.get(refEngityKey).getTitle();
            }
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(refEngityKey);
            if (null != iEntityDefine) {
                entityDefineMap.put(refEngityKey, iEntityDefine);
                return iEntityDefine.getTitle();
            }
        }
        return null;
    }

    private String getRefEntityTitle(String refEngityTableKey, Map<String, IEntityDefine> entityDefineMap) {
        if (org.springframework.util.StringUtils.hasLength(refEngityTableKey)) {
            if (null != entityDefineMap.get(refEngityTableKey)) {
                return entityDefineMap.get(refEngityTableKey).getTitle();
            }
            TableModelDefine tableModelDefineById = this.dataModelService.getTableModelDefineById(refEngityTableKey);
            String entityIdByCode = this.entityMetaService.getEntityIdByCode(tableModelDefineById.getCode());
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(entityIdByCode);
            if (null != iEntityDefine) {
                entityDefineMap.put(refEngityTableKey, iEntityDefine);
                return iEntityDefine.getTitle();
            }
        }
        return null;
    }

    @Override
    public List<DataFieldDTO> listFieldsByRefEntityKeys(List<String> entityKeys) {
        List fields = this.designDataSchemeService.getDataFieldsByEntity(entityKeys);
        return fields.stream().map(FieldBeanUtils::toDto).collect(Collectors.toList());
    }
}

