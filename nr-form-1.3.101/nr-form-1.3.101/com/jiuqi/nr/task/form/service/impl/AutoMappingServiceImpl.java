/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.form.controller.dto.AutoMappingDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.service.IAutoMappingService;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import com.jiuqi.nr.task.form.util.TableBeanUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AutoMappingServiceImpl
implements IAutoMappingService {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @Override
    public List<AutoMappingDTO> fixAutoMapping(String formKey, List<AutoMappingDTO> autoMappings) {
        DesignFormDefine formDefine = this.iDesignTimeViewController.getForm(formKey);
        DesignFormSchemeDefine formSchemeDefine = this.iDesignTimeViewController.getFormScheme(formDefine.getFormScheme());
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.getTask(formSchemeDefine.getTaskKey());
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(designTaskDefine.getDw());
        ArrayList<AutoMappingDTO> result = new ArrayList<AutoMappingDTO>();
        List allFields = this.iDesignDataSchemeService.getAllDataFieldByKind(designTaskDefine.getDataScheme(), new DataFieldKind[]{DataFieldKind.FIELD_ZB});
        HashMap<String, DesignDataField> exist = new HashMap<String, DesignDataField>();
        for (DesignDataField obj : allFields) {
            exist.put(obj.getCode(), obj);
        }
        HashMap<String, IEntityDefine> entityCache = new HashMap<String, IEntityDefine>();
        HashMap<String, DesignDataTable> tableCache = new HashMap<String, DesignDataTable>();
        for (AutoMappingDTO vo : autoMappings) {
            DesignDataField dataField = (DesignDataField)exist.get(vo.getDfCode().toUpperCase());
            DesignDataTable dataTable = null;
            if (null != dataField && (dataTable = (DesignDataTable)tableCache.get(dataField.getDataTableKey())) == null) {
                dataTable = this.iDesignDataSchemeService.getDataTable(dataField.getDataTableKey());
                tableCache.put(dataField.getDataTableKey(), dataTable);
            }
            boolean zb = true;
            zb = StringUtils.hasText(vo.getDtCode()) ? dataField != null && dataTable != null && dataTable.getCode().equals(vo.getDtCode()) : dataField != null;
            if (zb) {
                if (StringUtils.isEmpty(vo.getDtCode())) {
                    vo.setFieldKey(dataField.getKey());
                    vo.setDataTableType(dataTable.getDataTableType());
                    DataFieldDTO dto = FieldBeanUtils.toDto(dataField);
                    vo.setDataFieldDTO(dto);
                    vo.setDataTableDTO(TableBeanUtils.toDto(dataTable));
                    if (StringUtils.hasLength(dto.getRefDataEntityKey())) {
                        IEntityDefine entityDefine = (IEntityDefine)entityCache.get(dto.getRefDataEntityKey());
                        if (entityDefine == null) {
                            entityDefine = this.iEntityMetaService.queryEntity(dto.getRefDataEntityKey());
                        }
                        if (null != entityDefine) {
                            entityCache.put(dto.getRefDataEntityKey(), entityDefine);
                            dto.setRefDataEntityTitle(entityDefine.getTitle());
                        }
                    }
                    result.add(vo);
                    continue;
                }
                if (!dataTable.getCode().equals(vo.getDtCode())) continue;
                vo.setFieldKey(dataField.getKey());
                DesignDataTable dataTable1 = (DesignDataTable)tableCache.get(dataField.getDataTableKey());
                if (dataTable1 == null) {
                    dataTable1 = this.iDesignDataSchemeService.getDataTable(dataField.getDataTableKey());
                    tableCache.put(dataField.getDataTableKey(), dataTable1);
                }
                vo.setDataTableType(dataTable1.getDataTableType());
                DataFieldDTO dto = FieldBeanUtils.toDto(dataField);
                vo.setDataFieldDTO(dto);
                vo.setDataTableDTO(TableBeanUtils.toDto(dataTable));
                if (StringUtils.hasLength(dto.getRefDataEntityKey())) {
                    IEntityDefine entityDefine = (IEntityDefine)entityCache.get(dto.getRefDataEntityKey());
                    if (entityDefine == null) {
                        entityDefine = this.iEntityMetaService.queryEntity(dto.getRefDataEntityKey());
                    }
                    if (null != entityDefine) {
                        entityCache.put(dto.getRefDataEntityKey(), entityDefine);
                        dto.setRefDataEntityTitle(entityDefine.getTitle());
                    }
                }
                result.add(vo);
                continue;
            }
            if (!formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) continue;
            IEntityDefine entityDefine = (IEntityDefine)entityCache.get(designTaskDefine.getDw());
            if (entityDefine == null) {
                entityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
                entityCache.put(designTaskDefine.getDw(), entityDefine);
            }
            if (StringUtils.hasText(vo.getDtCode()) && !entityDefine.getCode().equals(vo.getDtCode())) continue;
            List showFields = entityModel.getShowFields();
            for (IEntityAttribute showField : showFields) {
                if (!showField.getCode().equals(vo.getDfCode().toUpperCase())) continue;
                vo.setFieldKey(showField.getCode());
                vo.setFmdm(true);
                vo.setiEntityDefine(entityDefine);
                vo.setiEntityAttribute(showField);
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<AutoMappingDTO> floatAutoMapping(String formKey, List<AutoMappingDTO> autoMappings) {
        List exist;
        ArrayList<AutoMappingDTO> result = new ArrayList<AutoMappingDTO>();
        HashMap<String, List> fieldMap = new HashMap<String, List>();
        DesignFormDefine formDefine = this.iDesignTimeViewController.getForm(formKey);
        DesignFormSchemeDefine formSchemeDefine = this.iDesignTimeViewController.getFormScheme(formDefine.getFormScheme());
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.getTask(formSchemeDefine.getTaskKey());
        HashMap<String, DesignDataTable> tableCodeCache = new HashMap<String, DesignDataTable>();
        for (AutoMappingDTO autoMappingDTO : autoMappings) {
            DesignDataField field;
            DesignDataTable table = (DesignDataTable)tableCodeCache.get(autoMappingDTO.getDtCode());
            if (table == null) {
                table = this.iDesignDataSchemeService.getDataTableByCode(autoMappingDTO.getDtCode());
                tableCodeCache.put(autoMappingDTO.getDtCode(), table);
            }
            if (table == null || !table.getDataSchemeKey().equals(designTaskDefine.getDataScheme()) || table.getDataTableType() != DataTableType.DETAIL && table.getDataTableType() != DataTableType.ACCOUNT || (field = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(table.getKey(), autoMappingDTO.getDfCode())) == null) continue;
            exist = this.iDesignDataSchemeService.getDataFieldByTableCode(autoMappingDTO.getDtCode());
            fieldMap.put(autoMappingDTO.getDtCode(), exist);
        }
        if (fieldMap.isEmpty()) {
            return result;
        }
        for (Map.Entry entry : fieldMap.entrySet()) {
            String dt_code = (String)entry.getKey();
            exist = (List)entry.getValue();
            if (exist.isEmpty()) continue;
            Map<String, DesignDataField> collect = exist.stream().collect(Collectors.toMap(e -> e.getCode().toUpperCase(), e -> e));
            HashMap<String, DesignDataTable> tableKeyCache = new HashMap<String, DesignDataTable>();
            HashMap<String, IEntityDefine> entityCache = new HashMap<String, IEntityDefine>();
            for (AutoMappingDTO obj : autoMappings) {
                if (!obj.getDtCode().equalsIgnoreCase(dt_code) || null == collect.get(obj.getDfCode().toUpperCase())) continue;
                DesignDataField field = collect.get(obj.getDfCode().toUpperCase());
                obj.setFieldKey(field.getKey());
                DesignDataTable dataTable = (DesignDataTable)tableKeyCache.get(field.getDataTableKey());
                if (dataTable == null) {
                    dataTable = this.iDesignDataSchemeService.getDataTable(field.getDataTableKey());
                    tableKeyCache.put(field.getDataTableKey(), dataTable);
                }
                obj.setDataTableType(dataTable.getDataTableType());
                DataFieldDTO dto = FieldBeanUtils.toDto(field);
                obj.setDataFieldDTO(dto);
                obj.setDataTableDTO(TableBeanUtils.toDto(dataTable));
                if (StringUtils.hasText(dto.getRefDataEntityKey())) {
                    IEntityDefine entityDefine = (IEntityDefine)entityCache.get(dto.getRefDataEntityKey());
                    if (entityDefine == null) {
                        entityDefine = this.iEntityMetaService.queryEntity(dto.getRefDataEntityKey());
                        entityCache.put(dto.getRefDataEntityKey(), entityDefine);
                    }
                    if (null != entityDefine) {
                        dto.setRefDataEntityTitle(entityDefine.getTitle());
                    }
                }
                result.add(obj);
            }
        }
        return result;
    }
}

