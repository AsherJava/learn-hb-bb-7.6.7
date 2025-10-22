/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.entity.adapter.IEntityAdapter
 *  com.jiuqi.nr.entity.bo.EntitySearchBO
 *  com.jiuqi.nr.entity.engine.exception.EntityUpdateException
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl
 *  com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl
 *  com.jiuqi.nr.entity.internal.model.impl.EntityModelImpl
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityGroup
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.param.IEntityDeleteParam
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nr.entity.param.IEntityUpdateParam
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreTableColumn;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreAdapterUtil;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreDataAdapter;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
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
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalibreAdapter
implements IEntityAdapter {
    public static final String ADAPTER_ID = "CB";
    public static final String ADAPTER_TITLE = "\u53e3\u5f84";
    public static final int ADAPTER_ORDER = 3;
    @Autowired
    private CalibreDataAdapter calibreDataAdapter;
    @Autowired
    private ICalibreDefineService calibreDefineService;

    public EntityResultSet getAllRows(IEntityQueryParam query) {
        return this.calibreDataAdapter.getAllRows(query);
    }

    public EntityResultSet getRootRows(IEntityQueryParam query) {
        return this.calibreDataAdapter.getRootRows(query);
    }

    public EntityResultSet getChildRows(IEntityQueryParam query, String ... entityKeyData) {
        if (entityKeyData == null) {
            return null;
        }
        return this.calibreDataAdapter.getChildRows(query, entityKeyData);
    }

    public EntityResultSet getAllChildRows(IEntityQueryParam query, String entityKeyData) {
        return this.calibreDataAdapter.getAllChildRows(query, entityKeyData);
    }

    public EntityResultSet findByEntityKeys(IEntityQueryParam query) {
        return this.calibreDataAdapter.findByEntityKeys(query);
    }

    public String getParent(IEntityQueryParam query, String queryCode) {
        return this.calibreDataAdapter.getParent(query, queryCode);
    }

    public String getParent(IEntityQueryParam query, Map<String, Object> rowData) {
        return rowData.get(CalibreDataMapper.FIELD_PARENT) == null ? null : rowData.get(CalibreDataMapper.FIELD_PARENT).toString();
    }

    public int getMaxDepth(IEntityQueryParam query) {
        return this.calibreDataAdapter.getMaxDepth(query);
    }

    public int getMaxDepthByEntityKey(IEntityQueryParam query, String entityKeyData) {
        return this.calibreDataAdapter.getMaxDepthByEntityKey(query, entityKeyData);
    }

    public EntityResultSet findByCode(IEntityQueryParam query) {
        return this.calibreDataAdapter.findByCode(query);
    }

    public int getDirectChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.calibreDataAdapter.getDirectChildCount(query, entityKeyData);
    }

    public int getAllChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.calibreDataAdapter.getAllChildCount(query, entityKeyData);
    }

    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.calibreDataAdapter.getDirectChildCountByParent(query, parentKey);
    }

    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.calibreDataAdapter.getAllChildCountByParent(query, parentKey);
    }

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, String entityKeyData) {
        return this.calibreDataAdapter.getParentsEntityKeyDataPath(query, entityKeyData);
    }

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, Map<String, Object> rowData) {
        return this.calibreDataAdapter.getParentsEntityKeyDataPath(query, rowData);
    }

    public int getTotalCount(IEntityQueryParam query) {
        return this.calibreDataAdapter.getTotalCount(query);
    }

    public EntityUpdateResult insertRows(IEntityUpdateParam addParam) throws EntityUpdateException {
        return this.calibreDataAdapter.insertRows(addParam);
    }

    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws EntityUpdateException {
        return this.calibreDataAdapter.deleteRows(deleteParam);
    }

    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        return this.calibreDataAdapter.updateRows(updateParam);
    }

    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) throws EntityUpdateException {
        return this.calibreDataAdapter.rowsCheck(updateParam, insert);
    }

    public EntityResultSet simpleQuery(IEntityQueryParam query) {
        return this.findByCode(query);
    }

    public List<IEntityGroup> getRootEntityGroups() {
        return null;
    }

    public IEntityGroup queryEntityGroup(String groupId) {
        return null;
    }

    public List<IEntityGroup> getChildrenEntityGroups(String groupId) {
        return null;
    }

    public List<IEntityDefine> getEntitiesInGroup(String groupId) {
        return null;
    }

    public void importEntityData(String entityId, InputStream inputStream) throws JQException {
    }

    public void exportEntityData(String entityId, OutputStream outputStream) throws JQException {
    }

    public void exportEntityDefine(String entityId, OutputStream outputStream) throws JQException {
    }

    public IEntityDefine importEntityDefine(InputStream inputStream) throws JQException {
        return null;
    }

    public List<IEntityDefine> getEntities(int dimensionFlag) {
        return null;
    }

    public IEntityDefine getEntity(String subTreeEntityId) {
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(subTreeEntityId);
        if (calibreDefine == null) {
            return null;
        }
        return this.buildDefine(calibreDefine);
    }

    private CalibreDefineDTO getCalibreDefine(String subTreeEntityId) {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setCode(subTreeEntityId);
        Result<CalibreDefineDTO> calibreDefineDTOResult = this.calibreDefineService.get(calibreDefineDTO);
        if (calibreDefineDTOResult.getCode() == 0) {
            return null;
        }
        return calibreDefineDTOResult.getData();
    }

    private EntityDefineImpl buildDefine(CalibreDefineDTO calibreDefine) {
        EntityDefineImpl define = new EntityDefineImpl();
        define.setId(calibreDefine.getKey());
        define.setTitle(calibreDefine.getName());
        define.setCode(calibreDefine.getCode());
        define.setDimensionFlag(1);
        define.setIncludeSubTreeEntity(0);
        define.setDimensionName(calibreDefine.getCode());
        define.setTree(calibreDefine.getStructType() != null && calibreDefine.getStructType() == 1);
        define.setCategory(ADAPTER_ID);
        return define;
    }

    public IEntityDefine getEntityByCode(String subTreeEntityCode) {
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(subTreeEntityCode);
        if (calibreDefine == null) {
            return null;
        }
        return this.buildDefine(calibreDefine);
    }

    public IEntityModel getEntityModel(String entityId) {
        EntityModelImpl entityModel = new EntityModelImpl();
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(entityId);
        if (calibreDefine == null) {
            return null;
        }
        entityModel.setEntityId(entityId);
        EntityAttributeImpl parentAttribute = new EntityAttributeImpl();
        parentAttribute.setCode(CalibreTableColumn.PARENT.getCode());
        parentAttribute.setName(CalibreTableColumn.PARENT.getTitle());
        parentAttribute.setID(UUID.randomUUID().toString());
        EntityAttributeImpl nameAttribute = new EntityAttributeImpl();
        nameAttribute.setCode(CalibreTableColumn.NAME.getCode());
        nameAttribute.setName(CalibreTableColumn.NAME.getTitle());
        nameAttribute.setID(UUID.randomUUID().toString());
        EntityAttributeImpl codeAttribute = new EntityAttributeImpl();
        codeAttribute.setCode(CalibreTableColumn.CODE.getCode());
        codeAttribute.setName(CalibreTableColumn.CODE.getTitle());
        codeAttribute.setID(UUID.randomUUID().toString());
        EntityAttributeImpl keyAttribute = new EntityAttributeImpl();
        keyAttribute.setCode(CalibreTableColumn.KEY.getCode());
        keyAttribute.setName(CalibreTableColumn.KEY.getTitle());
        keyAttribute.setID(UUID.randomUUID().toString());
        EntityAttributeImpl orderAttribute = new EntityAttributeImpl();
        keyAttribute.setCode(CalibreTableColumn.ORDER.getCode());
        keyAttribute.setName(CalibreTableColumn.ORDER.getTitle());
        keyAttribute.setID(UUID.randomUUID().toString());
        entityModel.setParentField((IEntityAttribute)parentAttribute);
        entityModel.setNameField((IEntityAttribute)nameAttribute);
        entityModel.setCodeField((IEntityAttribute)codeAttribute);
        entityModel.setRecKeyField((IEntityAttribute)keyAttribute);
        entityModel.setBizKeyField((IEntityAttribute)codeAttribute);
        entityModel.setOrderField((IEntityAttribute)orderAttribute);
        return entityModel;
    }

    public List<IEntityRefer> getEntityRefer(String entityId) {
        return null;
    }

    public List<String> getPath(String entityId) {
        return null;
    }

    public List<IEntityDefine> fuzzySearchEntityByKeyWords(EntitySearchBO bo) {
        return null;
    }

    public TableModelDefine getTableByEntityId(String entityId) {
        return null;
    }

    public List<IEntityDefine> getSubTreeEntities(String baseTreeEntityId) {
        return null;
    }

    public IEntityDefine getBaseEntityBySubTreeEntityId(String subTreeEntityId) {
        return this.getEntity(subTreeEntityId);
    }

    public String getDimensionName(String entityId) {
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(entityId);
        if (calibreDefine == null) {
            return null;
        }
        return calibreDefine.getCode();
    }

    public String getEntityCode(String entityId) {
        return this.getDimensionName(entityId);
    }

    public String getDimensionNameByCode(String entityCode) {
        return entityCode;
    }

    public String getEntityIdByCode(String entityCode) {
        return CalibreAdapterUtil.getEntityIdByCalibreCode(entityCode);
    }

    public boolean isDBMode(String entityId, Date date) {
        return false;
    }

    public boolean isEnableAuthority(String entityId) {
        return false;
    }

    public boolean canReadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canEditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public Map<String, Boolean> canReadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public Map<String, Boolean> canEditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public Map<String, Boolean> canAuditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public Map<String, Boolean> canSubmitEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public Map<String, Boolean> canUploadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public boolean canWriteEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public Map<String, Boolean> canWriteEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptyMap();
    }

    public boolean canSubmitEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canSubmitEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canUploadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canUploadEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canAuditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canAuditEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public boolean canReadUnPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return false;
    }

    public Set<String> getCanReadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanEditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanWriteEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanAuditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanSubmitEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    public Set<String> getCanUploadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return Collections.emptySet();
    }

    public Set<String> getCanUploadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanAuditIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanSubmitIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public Set<String> getCanReadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return null;
    }

    public void grantAllPrivilegesToEntityTable(String entityId) {
    }

    public void grantAllPrivilegesToEntityData(String entityId, String ... entityKeyData) {
    }

    public String getId() {
        return ADAPTER_ID;
    }

    public String getTitle() {
        return ADAPTER_TITLE;
    }

    public int getOrder() {
        return 3;
    }

    public boolean enableSelect() {
        return false;
    }
}

