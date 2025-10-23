/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fmdm.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.domain.FMDMAttributeDO;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.fmdm.service.ICommonFMDMAttributeService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractFMDMAttributeQueryService
implements ICommonFMDMAttributeService {
    private static final Logger log = LoggerFactory.getLogger(AbstractFMDMAttributeQueryService.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    protected abstract String getEntityIdByFormSchemeKey(String var1);

    protected abstract String getFMDMFormDefine(String var1);

    protected abstract FormSchemeDefine getFormSchemeDefine(String var1);

    protected abstract TaskDefine getTaskDefine(String var1);

    protected abstract FieldDefine queryFieldInTable(String var1, String var2) throws Exception;

    protected abstract List<? extends DataLinkDefine> getAllFMDMLinks(String var1);

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
        List<IFMDMAttribute> zbAttributes = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        Set allCodes = zbAttributes.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        while (attributes.hasNext()) {
            IEntityAttribute entityAttribute = (IEntityAttribute)attributes.next();
            if (allCodes.contains(entityAttribute.getCode())) continue;
            FMDMAttributeDO attribute = this.transferAttribute((ColumnModelDefine)entityAttribute, entityId, entityRefer);
            if (attribute.getCode().equals(parentField.getCode())) {
                attribute.setReferEntity(true);
                attribute.setReferEntityId(entityId);
            }
            fmdmAttributes.add(attribute);
        }
        fmdmAttributes.addAll(zbAttributes);
        return fmdmAttributes;
    }

    private List<IFMDMAttribute> getAllFieldDefine(String formSchemeKey) {
        ArrayList<IFMDMAttribute> attributes = new ArrayList<IFMDMAttribute>();
        List<DataLinkDefine> zbs = this.getZbs(formSchemeKey);
        if (CollectionUtils.isEmpty(zbs)) {
            return attributes;
        }
        HashSet<String> fieldKeys = new HashSet<String>();
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
        String tableModelId = tableModel.getID();
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(formSchemeKey);
        TaskDefine taskDefine = this.getTaskDefine(formSchemeDefine.getTaskKey());
        DataTable unitInfoTable = this.runtimeDataSchemeService.getDataTableForMdInfo(taskDefine.getDataScheme());
        String unitInfoTableKey = unitInfoTable == null ? null : unitInfoTable.getKey();
        for (DataLinkDefine zb : zbs) {
            if (zb.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)) {
                if (this.dataModelService.getColumnModelDefineByCode(tableModelId, zb.getLinkExpression()) != null || unitInfoTableKey == null) continue;
                try {
                    FieldDefine fieldDefine = this.queryFieldInTable(zb.getLinkExpression(), unitInfoTableKey);
                    if (fieldDefine == null) continue;
                    fieldKeys.add(fieldDefine.getKey());
                }
                catch (Exception e2) {
                    log.error("\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u8868\u6307\u6807\u65f6\u51fa\u9519\uff1a{}", (Object)e2.getMessage());
                }
                continue;
            }
            fieldKeys.add(zb.getLinkExpression());
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(fieldKeys));
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
            FMDMAttributeDO attribute = this.transferAttribute(columnModelDefine, null, null);
            String dataFieldKey = columnToField.get(columnModelDefine.getID());
            attribute.setZBKey(dataFieldKey);
            attributes.add(attribute);
            if (!attribute.isReferEntity() || !StringUtils.hasText(dataFieldKey) || (dataField = referFieldMap.get(dataFieldKey)) == null) continue;
            attribute.setReferEntityId(dataField.getRefDataEntityKey());
        }
        return attributes;
    }

    @Override
    public List<IFMDMAttribute> listShowAttribute(FMDMAttributeDTO fmdmAttribute) {
        ArrayList<IFMDMAttribute> fmdmAttributes = new ArrayList<IFMDMAttribute>();
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        IEntityAttribute parentField = entityModel.getParentField();
        List<? extends DataLinkDefine> links = this.getAllFMDMLinks(fmdmAttribute.getFormSchemeKey());
        links.sort((c1, c2) -> {
            if (c1.getPosY() != c2.getPosY()) {
                return Integer.compare(c1.getPosY(), c2.getPosY());
            }
            return Integer.compare(c1.getPosX(), c2.getPosX());
        });
        List<IFMDMAttribute> zbAttributes = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        Map<String, IFMDMAttribute> zbKeyMap = zbAttributes.stream().collect(Collectors.toMap(IFMDMAttribute::getZBKey, e -> e, (e1, e2) -> e1));
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        HashSet<String> columnsSet = new HashSet<String>(zbAttributes.size());
        for (DataLinkDefine dataLinkDefine : links) {
            IFMDMAttribute attribute;
            String linkExpression = dataLinkDefine.getLinkExpression();
            if (DataLinkType.DATA_LINK_TYPE_FMDM == dataLinkDefine.getType()) {
                FMDMAttributeDO attribute2;
                IEntityAttribute entityAttribute = entityModel.getAttribute(linkExpression);
                if (entityAttribute == null || columnsSet.contains((attribute2 = this.transferAttribute((ColumnModelDefine)entityAttribute, entityId, entityRefer)).getID())) continue;
                if (attribute2.getCode().equals(parentField.getCode())) {
                    attribute2.setReferEntity(true);
                    attribute2.setReferEntityId(entityId);
                }
                fmdmAttributes.add(attribute2);
                columnsSet.add(attribute2.getID());
                continue;
            }
            if (DataLinkType.DATA_LINK_TYPE_FIELD != dataLinkDefine.getType() && DataLinkType.DATA_LINK_TYPE_INFO != dataLinkDefine.getType() || (attribute = zbKeyMap.get(linkExpression)) == null || columnsSet.contains(attribute.getID())) continue;
            fmdmAttributes.add(attribute);
            columnsSet.add(attribute.getID());
        }
        return fmdmAttributes;
    }

    @Override
    public IFMDMAttribute queryByCode(FMDMAttributeDTO fmdmAttribute) {
        List<IFMDMAttribute> allFieldDefine = this.getAllFieldDefine(fmdmAttribute.getFormSchemeKey());
        Optional<IFMDMAttribute> findAttribute = allFieldDefine.stream().filter(e -> fmdmAttribute.getCode().equals(e.getCode())).findAny();
        if (findAttribute.isPresent()) {
            return findAttribute.get();
        }
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fmdmAttribute.getCode());
        if (columnModelDefine != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute(columnModelDefine, entityId, null);
            if (this.isParentField(tableModel, attributeDO)) {
                attributeDO.setReferEntity(true);
                attributeDO.setReferEntityId(attributeDO.getEntityId());
            }
            return attributeDO;
        }
        return null;
    }

    @Override
    public IFMDMAttribute queryByZbKey(FMDMAttributeDTO fmdmAttributeDTO) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttributeDTO.getFormSchemeKey());
        List codes = this.getEntityZbs(fmdmAttributeDTO.getFormSchemeKey()).stream().map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        if (codes.contains(fmdmAttributeDTO.getZBKey())) {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
            ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fmdmAttributeDTO.getCode());
            if (columnModelDefine == null) {
                return null;
            }
            FMDMAttributeDO attributeDO = this.transferAttribute(columnModelDefine, entityId, null);
            if (this.isParentField(tableModel, attributeDO)) {
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
        if (columnModelDefine == null) {
            return null;
        }
        FMDMAttributeDO attribute = this.transferAttribute(columnModelDefine, null, null);
        if (attribute.isReferEntity()) {
            attribute.setReferEntityId(dataField.getRefDataEntityKey());
        }
        attribute.setZBKey(dataField.getKey());
        return attribute;
    }

    @Override
    public IFMDMAttribute getFMDMParentField(FMDMAttributeDTO fmdmAttribute) {
        String entityId = this.getEntityIdByFormSchemeKey(fmdmAttribute.getFormSchemeKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        if (entityDefine == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u5b9a\u4e49", entityId));
        }
        if (!entityDefine.isTree().booleanValue()) {
            return null;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230:%s\u7684\u5b9e\u4f53\u6a21\u578b", entityId));
        }
        IEntityAttribute parentField = entityModel.getParentField();
        if (parentField != null) {
            FMDMAttributeDO attributeDO = this.transferAttribute((ColumnModelDefine)parentField, null, null);
            attributeDO.setReferEntity(true);
            attributeDO.setReferEntityId(entityId);
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
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        if (bizKeyField != null) {
            return this.transferAttribute((ColumnModelDefine)bizKeyField, entityId, null);
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
        IEntityAttribute codeField = entityModel.getCodeField();
        if (codeField != null) {
            return this.transferAttribute((ColumnModelDefine)codeField, entityId, null);
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
        IEntityAttribute nameField = entityModel.getNameField();
        if (nameField != null) {
            return this.transferAttribute((ColumnModelDefine)nameField, entityId, null);
        }
        return null;
    }

    private FMDMAttributeDO transferAttribute(ColumnModelDefine columnModelDefine, String entityId, List<IEntityRefer> entityRefer) {
        FMDMAttributeDO attribute = Utils.transferAttribute(columnModelDefine);
        if (StringUtils.hasText(columnModelDefine.getReferTableID())) {
            Map<String, IEntityRefer> referMap;
            IEntityRefer refer;
            if (StringUtils.hasText(entityId) && entityRefer == null) {
                entityRefer = this.entityMetaService.getEntityRefer(entityId);
            }
            if (!CollectionUtils.isEmpty(entityRefer) && (refer = (referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e))).get(columnModelDefine.getCode())) != null) {
                attribute.setReferEntityId(refer.getReferEntityId());
                attribute.setReferEntity(true);
            }
        }
        attribute.setZBKey(columnModelDefine.getCode());
        attribute.setEntityId(entityId);
        return attribute;
    }

    private List<DataLinkDefine> getZbs(String formSchemeKey) {
        return this.getAllFMDMLinks(formSchemeKey).stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD) || e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_INFO) || e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).collect(Collectors.toList());
    }

    private List<DataLinkDefine> getEntityZbs(String formSchemeKey) {
        return this.getAllFMDMLinks(formSchemeKey).stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).collect(Collectors.toList());
    }

    private boolean isParentField(TableModelDefine tableModel, FMDMAttributeDO attributeDO) {
        return StringUtils.hasText(attributeDO.getReferTableID()) && attributeDO.getReferTableID().equals(tableModel.getID()) && StringUtils.hasText(attributeDO.getReferColumnID()) && attributeDO.getReferColumnID().equals(tableModel.getBizKeys());
    }
}

