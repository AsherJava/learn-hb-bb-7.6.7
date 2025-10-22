/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.TableModelDefineImpl
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.entity.adapter.impl.enumdata;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.adapter.impl.enumdata.EnumDataResultSet;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityModelImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.TableModelDefineImpl;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.feign.client.EnumDataClient;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@ConditionalOnProperty(prefix="jiuqi.nr.entity.enumType", name={"enable"}, havingValue="true")
public class EnumDataAdapterImpl
implements IEntityAdapter {
    @Autowired
    private EnumDataClient enumDataClient;
    public static final String ID_COLUMN = "ID";
    public static final String CODE_COLUMN = "CODE";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String SHORTNAME_COLUMN = "SHORTNAME";

    @Override
    public List<IEntityDefine> getEntities(int dimensionFlag) {
        if (dimensionFlag != 0) {
            return Collections.emptyList();
        }
        EnumDataDTO param = new EnumDataDTO();
        param.setStatus(Integer.valueOf(0));
        return this.queryEnumDefine(param);
    }

    @NotNull
    private List<IEntityDefine> queryEnumDefine(EnumDataDTO param) {
        ArrayList<IEntityDefine> list = new ArrayList<IEntityDefine>();
        List enumData = this.enumDataClient.listBiztype(param);
        HashSet<String> ids = new HashSet<String>();
        for (EnumDataDO enumDatum : enumData) {
            if (ids.contains(enumDatum.getBiztype())) continue;
            list.add(this.buildDefine(enumDatum));
            ids.add(enumDatum.getBiztype());
        }
        return list;
    }

    private IEntityDefine buildDefine(EnumDataDO enumDataDO) {
        EntityDefineImpl define = new EntityDefineImpl();
        define.setId(enumDataDO.getBiztype());
        define.setTitle(enumDataDO.getDescription());
        define.setCode(enumDataDO.getBiztype());
        define.setVersion(1);
        define.setAuthFlag(false);
        define.setCategory("ENUM");
        define.setDimensionFlag(0);
        define.setDimensionName(enumDataDO.getDescription());
        define.setIncludeSubTreeEntity(0);
        define.setIsolation(0);
        return define;
    }

    @Override
    public IEntityDefine getEntity(String tableName) {
        EnumDataDTO param = new EnumDataDTO();
        param.setBiztype(tableName);
        List<IEntityDefine> list = this.queryEnumDefine(param);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public IEntityDefine getEntityByCode(String tableCode) {
        return this.getEntity(tableCode);
    }

    @Override
    public IEntityModel getEntityModel(String entityId) {
        EnumDataDTO param = new EnumDataDTO();
        param.setBiztype(entityId);
        param.setStatus(Integer.valueOf(0));
        List list = this.enumDataClient.listBiztype(param);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return this.buildModel((EnumDataDO)list.get(0));
    }

    private IEntityModel buildModel(EnumDataDO enumDataDO) {
        EntityModelImpl entityModel = new EntityModelImpl();
        entityModel.setEntityId(enumDataDO.getBiztype());
        EntityAttributeImpl idAttribute = EnumDataAdapterImpl.buildColumn(enumDataDO, ID_COLUMN, "\u4e3b\u952e", 0.0);
        EntityAttributeImpl codeAttribute = EnumDataAdapterImpl.buildColumn(enumDataDO, CODE_COLUMN, "\u6807\u8bc6", 1.0);
        EntityAttributeImpl nameAttribute = EnumDataAdapterImpl.buildColumn(enumDataDO, TITLE_COLUMN, "\u6807\u9898", 2.0);
        EntityAttributeImpl sortNameAttribute = EnumDataAdapterImpl.buildColumn(enumDataDO, SHORTNAME_COLUMN, "\u7b80\u79f0", 2.0);
        ArrayList<IEntityAttribute> attributes = new ArrayList<IEntityAttribute>(3);
        attributes.add(idAttribute);
        attributes.add(codeAttribute);
        attributes.add(nameAttribute);
        attributes.add(sortNameAttribute);
        ArrayList<IEntityAttribute> showAttributes = new ArrayList<IEntityAttribute>(3);
        showAttributes.add(codeAttribute);
        showAttributes.add(nameAttribute);
        showAttributes.add(sortNameAttribute);
        entityModel.setEntityAttributes(attributes);
        entityModel.setShowFields(showAttributes);
        entityModel.setBizKeyField(codeAttribute);
        entityModel.setRecKeyField(idAttribute);
        entityModel.setNameField(nameAttribute);
        entityModel.setCodeField(codeAttribute);
        return entityModel;
    }

    @NotNull
    private static EntityAttributeImpl buildColumn(EnumDataDO enumDataDO, String field, String fieldName, double order) {
        EntityAttributeImpl idAttribute = new EntityAttributeImpl();
        idAttribute.setID(field);
        idAttribute.setTableID(enumDataDO.getBiztype());
        idAttribute.setCode(field);
        idAttribute.setName(field);
        idAttribute.setTitle(fieldName);
        idAttribute.setCatagory(enumDataDO.getBiztype());
        idAttribute.setColumnType(ColumnModelType.STRING);
        idAttribute.setPrecision(100);
        idAttribute.setDecimal(0);
        idAttribute.setOrder(order);
        return idAttribute;
    }

    @Override
    public List<IEntityRefer> getEntityRefer(String entityId) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getPath(String entityId) {
        return Collections.emptyList();
    }

    @Override
    public List<IEntityDefine> fuzzySearchEntityByKeyWords(EntitySearchBO bo) {
        if (bo.getDimensionFlag() != null && bo.getDimensionFlag() == 1) {
            return Collections.emptyList();
        }
        if (bo.getCalibre() != null && bo.getCalibre() == 0) {
            return Collections.emptyList();
        }
        EnumDataDTO param = new EnumDataDTO();
        param.setSearchKey(bo.getKeyWords());
        param.setStatus(Integer.valueOf(0));
        return this.queryEnumDefine(param);
    }

    @Override
    public TableModelDefine getTableByEntityId(String entityId) {
        IEntityDefine entity = this.getEntity(entityId);
        TableModelDefineImpl define = new TableModelDefineImpl();
        define.setID(entityId);
        define.setBizKeys(ID_COLUMN);
        define.setCode(entityId);
        define.setKind(TableModelKind.DEFAULT);
        define.setName(entityId);
        define.setTitle(entity.getDesc());
        define.setDictType(TableDictType.NORMAL);
        return define;
    }

    @Override
    public List<IEntityDefine> getSubTreeEntities(String baseTreeEntityId) {
        ArrayList<IEntityDefine> list = new ArrayList<IEntityDefine>(1);
        IEntityDefine entity = this.getEntity(baseTreeEntityId);
        list.add(entity);
        return list;
    }

    @Override
    public IEntityDefine getBaseEntityBySubTreeEntityId(String subTreeEntityId) {
        return this.getEntity(subTreeEntityId);
    }

    @Override
    public String getDimensionName(String entityId) {
        return entityId;
    }

    @Override
    public String getEntityCode(String entityId) {
        return entityId;
    }

    @Override
    public String getDimensionNameByCode(String entityCode) {
        return entityCode;
    }

    @Override
    public String getEntityIdByCode(String entityCode) {
        return entityCode;
    }

    @Override
    public boolean isDBMode(String entityId, Date date) {
        return false;
    }

    @Override
    public EntityResultSet getAllRows(IEntityQueryParam query) {
        List<EnumDataDO> list = this.queryData(query);
        return new EnumDataResultSet(list);
    }

    private List<EnumDataDO> queryData(IEntityQueryParam query) {
        EnumDataDTO param = new EnumDataDTO();
        param.setBiztype(query.getEntityId());
        param.setStatus(Integer.valueOf(0));
        return this.enumDataClient.list(param);
    }

    @Override
    public EntityResultSet getRootRows(IEntityQueryParam query) {
        return this.getAllRows(query);
    }

    @Override
    public EntityResultSet getChildRows(IEntityQueryParam query, String ... entityKeyDatas) {
        return new EnumDataResultSet(Collections.emptyList());
    }

    @Override
    public EntityResultSet getAllChildRows(IEntityQueryParam query, String entityKeyData) {
        return new EnumDataResultSet(Collections.emptyList());
    }

    @Override
    public EntityResultSet findByEntityKeys(IEntityQueryParam query) {
        return this.filterVal(query, query.getMasterKey());
    }

    @NotNull
    private EnumDataResultSet filterVal(IEntityQueryParam query, List<String> values) {
        List<EnumDataDO> list = this.queryData(query);
        Iterator<EnumDataDO> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (values.contains(iterator.next().getVal())) continue;
            iterator.remove();
        }
        return new EnumDataResultSet(list);
    }

    @Override
    public String getParent(IEntityQueryParam query, String queryCode) {
        return "";
    }

    @Override
    public String getParent(IEntityQueryParam query, Map<String, Object> rowData) {
        return "";
    }

    @Override
    public int getMaxDepth(IEntityQueryParam query) {
        return 0;
    }

    @Override
    public int getMaxDepthByEntityKey(IEntityQueryParam query, String entityKeyData) {
        return 0;
    }

    @Override
    public EntityResultSet findByCode(IEntityQueryParam query) {
        return this.filterVal(query, query.getCodes());
    }

    @Override
    public int getDirectChildCount(IEntityQueryParam query, String entityKeyData) {
        return 0;
    }

    @Override
    public int getAllChildCount(IEntityQueryParam query, String entityKeyData) {
        return 0;
    }

    @Override
    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam query, String parentKey) {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam query, String parentKey) {
        return Collections.emptyMap();
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, String entityKeyData) {
        return new String[0];
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, Map<String, Object> rowData) {
        return new String[0];
    }

    @Override
    public int getTotalCount(IEntityQueryParam query) {
        return this.queryData(query).size();
    }

    @Override
    public EntityUpdateResult insertRows(IEntityUpdateParam addParam) throws EntityUpdateException {
        EntityUpdateResult result = new EntityUpdateResult();
        List<EntityDataRow> modifyRows = addParam.getModifyRows();
        for (EntityDataRow row : modifyRows) {
            EnumDataDO param = new EnumDataDO();
            param.setBiztype(addParam.getEntityId());
            param.setTitle(row.getTitle());
            param.setVal(row.getEntityKeyData());
            this.enumDataClient.add(param);
        }
        return result;
    }

    @Override
    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws EntityUpdateException {
        EntityUpdateResult result = new EntityUpdateResult();
        List<EntityDataRow> deleteRows = deleteParam.getDeleteRows();
        for (EntityDataRow row : deleteRows) {
            EnumDataDO param = new EnumDataDO();
            param.setBiztype(deleteParam.getEntityId());
            param.setId(UUID.fromString(row.getEntityKeyData()));
            param.setStatus(Integer.valueOf(0));
            this.enumDataClient.update(param);
        }
        return result;
    }

    @Override
    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        EntityUpdateResult result = new EntityUpdateResult();
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        for (EntityDataRow row : modifyRows) {
            EnumDataDO param = new EnumDataDO();
            param.setBiztype(updateParam.getEntityId());
            param.setId(UUID.fromString(row.getEntityKeyData()));
            param.setTitle(row.getTitle());
            param.setVal(row.getValue(CODE_COLUMN).toString());
            this.enumDataClient.update(param);
        }
        return result;
    }

    @Override
    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) throws EntityUpdateException {
        return new EntityCheckResult();
    }

    @Override
    public EntityResultSet simpleQuery(IEntityQueryParam query) {
        return this.findByEntityKeys(query);
    }

    @Override
    public List<IEntityGroup> getRootEntityGroups() {
        return Collections.emptyList();
    }

    @Override
    public IEntityGroup queryEntityGroup(String groupId) {
        return null;
    }

    @Override
    public List<IEntityGroup> getChildrenEntityGroups(String groupId) {
        return Collections.emptyList();
    }

    @Override
    public List<IEntityDefine> getEntitiesInGroup(String groupId) {
        if (!"29b66bd9-e6fc-49f1-8e6a-a5c45e455f11".equals(groupId)) {
            return Collections.emptyList();
        }
        EnumDataDTO param = new EnumDataDTO();
        param.setStatus(Integer.valueOf(0));
        return this.queryEnumDefine(param);
    }

    @Override
    public void importEntityData(String entityId, InputStream inputStream) throws JQException {
    }

    @Override
    public void exportEntityData(String entityId, OutputStream outputStream) throws JQException {
    }

    @Override
    public void exportEntityDefine(String entityId, OutputStream outputStream) throws JQException {
    }

    @Override
    public IEntityDefine importEntityDefine(InputStream inputStream) throws JQException {
        return null;
    }

    @Override
    public boolean isEnableAuthority(String entityId) {
        return false;
    }

    @Override
    public boolean canReadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canEditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public Map<String, Boolean> canReadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Boolean> canEditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Boolean> canAuditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Boolean> canSubmitEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Boolean> canUploadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public boolean canWriteEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public Map<String, Boolean> canWriteEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public boolean canReadUnPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    @Override
    public Set<String> getCanReadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanEditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanWriteEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanAuditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanSubmitEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanUploadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    @Override
    public void grantAllPrivilegesToEntityTable(String entityId) {
    }

    @Override
    public void grantAllPrivilegesToEntityData(String entityId, String ... entityKeyData) {
    }

    @Override
    public String getId() {
        return "ENUM";
    }

    @Override
    public String getTitle() {
        return "\u679a\u4e3e";
    }

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public boolean enableSelect() {
        return false;
    }
}

