/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.domain.FMDMAttributeDO;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FMDMAttributeServiceImpl
implements IFMDMAttributeService {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public List<IFMDMAttribute> list(FMDMAttributeDTO fmdmAttribute) {
        ArrayList<IFMDMAttribute> fmdmAttributes = new ArrayList<IFMDMAttribute>();
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230'%s'\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        Iterator attributes = entityModel.getAttributes();
        IEntityAttribute parentField = entityModel.getParentField();
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        HashSet<String> entityAttributeKeys = new HashSet<String>();
        while (attributes.hasNext()) {
            IEntityAttribute entityAttribute = (IEntityAttribute)attributes.next();
            FMDMAttributeDO attribute = this.transferAttribute((ColumnModelDefine)entityAttribute, referMap);
            if (attribute.getCode().equals(parentField.getCode())) {
                attribute.setReferEntity(true);
                attribute.setReferEntityId(entityId);
            }
            attribute.setEntityId(entityId);
            fmdmAttributes.add(attribute);
            entityAttributeKeys.add(attribute.getCode());
        }
        List<IFMDMAttribute> allFieldDefine = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        List zbAttribute = allFieldDefine.stream().filter(e -> !entityAttributeKeys.contains(e.getCode())).collect(Collectors.toList());
        fmdmAttributes.addAll(zbAttribute);
        return fmdmAttributes;
    }

    private String getEntityIdByFormSchemeKey(String formSchemeKey) {
        String entityId = Utils.getEntityId();
        if (StringUtils.hasText(entityId)) {
            return entityId;
        }
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
        entityId = formSchemeDefine.getDw();
        if (!StringUtils.hasText(entityId)) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            entityId = designTaskDefine.getDw();
        }
        return entityId;
    }

    private Optional<DesignFormDefine> getFormDefine(String formSchemeKey) {
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeKey);
        return formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
    }

    private List<IFMDMAttribute> getAllFieldDefine(String formSchemeKey) {
        ArrayList<IFMDMAttribute> attributes = new ArrayList<IFMDMAttribute>();
        List<String> fieldKeys = this.getZbs(formSchemeKey);
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return attributes;
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(fieldKeys);
        if (CollectionUtils.isEmpty(dataFields)) {
            return attributes;
        }
        Map<String, DataField> referFieldMap = dataFields.stream().filter(e -> StringUtils.hasText(e.getRefDataEntityKey())).collect(Collectors.toMap(Basic::getKey, e -> e));
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys.toArray(new String[0]));
        if (CollectionUtils.isEmpty(deployInfo)) {
            return attributes;
        }
        Map<String, String> columnToField = deployInfo.stream().collect(Collectors.toMap(DataFieldDeployInfo::getColumnModelKey, DataFieldDeployInfo::getDataFieldKey));
        List columnKeys = deployInfo.stream().map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList());
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByIDs(columnKeys);
        for (ColumnModelDefine columnModelDefine : columnModelDefines) {
            DataField dataField;
            String dataFieldKey;
            FMDMAttributeDO attribute = this.transferAttribute(columnModelDefine, null);
            attributes.add(attribute);
            if (!attribute.isReferEntity() || !StringUtils.hasText(dataFieldKey = columnToField.get(columnModelDefine.getID())) || (dataField = referFieldMap.get(dataFieldKey)) == null) continue;
            attribute.setReferEntityId(dataField.getRefDataEntityKey());
        }
        return attributes;
    }

    private List<String> getZbs(String formSchemeKey) {
        return this.getAllFMDMLinks(formSchemeKey).stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
    }

    private List<DesignDataLinkDefine> getAllFMDMLinks(String formSchemeKey) {
        Optional<DesignFormDefine> formDefine = this.getFormDefine(formSchemeKey);
        if (!formDefine.isPresent()) {
            return Collections.emptyList();
        }
        return this.designTimeViewController.getAllLinksInForm(formDefine.get().getKey());
    }

    @Override
    public List<IFMDMAttribute> listShowAttribute(FMDMAttributeDTO fmdmAttribute) {
        ArrayList<IFMDMAttribute> fmdmAttributes = new ArrayList<IFMDMAttribute>();
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        IEntityAttribute parentField = entityModel.getParentField();
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        List showFields = entityModel.getShowFields();
        HashSet<String> entityAttributeKeys = new HashSet<String>();
        for (IEntityAttribute showField : showFields) {
            FMDMAttributeDO attribute = this.transferAttribute((ColumnModelDefine)showField, referMap);
            attribute.setEntityId(entityId);
            if (attribute.getCode().equals(parentField.getCode())) {
                attribute.setReferEntity(true);
                attribute.setReferEntityId(entityId);
            }
            fmdmAttributes.add(attribute);
            entityAttributeKeys.add(attribute.getCode());
        }
        List<IFMDMAttribute> allFieldDefine = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        List zbAttributes = allFieldDefine.stream().filter(e -> !entityAttributeKeys.contains(e.getCode())).collect(Collectors.toList());
        fmdmAttributes.addAll(zbAttributes);
        return fmdmAttributes;
    }

    @Override
    public IFMDMAttribute queryByCode(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fmdmAttribute.getCode());
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute parentField = entityModel.getParentField();
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        if (columnModelDefine != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute(columnModelDefine, referMap);
            attributeDO.setEntityId(entityId);
            if (attributeDO.getCode().equals(parentField.getCode())) {
                attributeDO.setReferEntity(true);
                attributeDO.setReferEntityId(attributeDO.getEntityId());
            }
            return attributeDO;
        }
        List<IFMDMAttribute> allFieldDefine = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        Optional<IFMDMAttribute> findAttribute = allFieldDefine.stream().filter(e -> fmdmAttribute.getCode().equals(e.getCode())).findAny();
        return findAttribute.orElse(null);
    }

    @Override
    public IFMDMAttribute queryByZbKey(FMDMAttributeDTO fmdmAttributeDTO) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttributeDTO.getFormSchemeKey());
        List<DesignDataLinkDefine> allFMDMLinks = this.getAllFMDMLinks(fmdmAttributeDTO.getFormSchemeKey());
        List codes = allFMDMLinks.stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        if (codes.contains(fmdmAttributeDTO.getZBKey())) {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
            ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fmdmAttributeDTO.getCode());
            List entityRefer = this.entityMetaService.getEntityRefer(entityId);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            IEntityAttribute parentField = entityModel.getParentField();
            Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
            FMDMAttributeDO attributeDO = this.transferAttribute(columnModelDefine, referMap);
            attributeDO.setEntityId(entityId);
            if (attributeDO.getCode().equals(parentField.getCode())) {
                attributeDO.setReferEntity(true);
                attributeDO.setReferEntityId(attributeDO.getEntityId());
            }
            return attributeDO;
        }
        DataField dataField = this.runtimeDataSchemeService.getDataField(fmdmAttributeDTO.getZBKey());
        if (dataField == null) {
            return null;
        }
        String[] filedKeys = new String[]{dataField.getKey()};
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(filedKeys);
        if (CollectionUtils.isEmpty(deployInfo)) {
            return null;
        }
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfo.get(0)).getColumnModelKey());
        FMDMAttributeDO attribute = this.transferAttribute(columnModelDefine, null);
        if (attribute.isReferEntity()) {
            attribute.setReferEntityId(dataField.getRefDataEntityKey());
        }
        return attribute;
    }

    @Override
    public IFMDMAttribute getFMDMParentField(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        IEntityAttribute parentField = entityModel.getParentField();
        if (parentField != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute((ColumnModelDefine)parentField, null);
            attributeDO.setReferEntity(true);
            attributeDO.setReferEntityId(entityId);
            attributeDO.setEntityId(entityId);
            return attributeDO;
        }
        return null;
    }

    @Override
    public IFMDMAttribute getFMDMBizField(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        if (bizKeyField != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute((ColumnModelDefine)bizKeyField, referMap);
            attributeDO.setEntityId(entityId);
            return attributeDO;
        }
        return null;
    }

    @Override
    public IFMDMAttribute getFMDMCodeField(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        IEntityAttribute codeField = entityModel.getCodeField();
        if (codeField != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute((ColumnModelDefine)codeField, referMap);
            attributeDO.setEntityId(entityId);
            return attributeDO;
        }
        return null;
    }

    @Override
    public IFMDMAttribute getFMDMTitleField(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
        IEntityAttribute nameField = entityModel.getNameField();
        if (nameField != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute((ColumnModelDefine)nameField, referMap);
            attributeDO.setEntityId(entityId);
            return attributeDO;
        }
        return null;
    }

    private FMDMAttributeDO transferAttribute(ColumnModelDefine columnModelDefine, Map<String, IEntityRefer> referMap) {
        FMDMAttributeDO attribute = Utils.transferAttribute(columnModelDefine);
        if (StringUtils.hasText(columnModelDefine.getReferTableID())) {
            IEntityRefer refer;
            if (referMap != null && (refer = referMap.get(columnModelDefine.getCode())) != null) {
                attribute.setReferEntityId(refer.getReferEntityId());
            }
            attribute.setReferEntity(true);
        }
        return attribute;
    }
}

