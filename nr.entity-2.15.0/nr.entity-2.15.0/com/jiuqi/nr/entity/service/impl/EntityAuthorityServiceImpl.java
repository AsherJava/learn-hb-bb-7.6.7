/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.EntityUtils
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityAuthorityServiceImpl
implements IEntityAuthorityService {
    @Autowired
    private AdapterService adapterService;

    @Override
    public boolean isEnableAuthority(String entityId) {
        return this.adapterService.getEntityAuthorityAdapter(entityId).isEnableAuthority(EntityUtils.getId((String)entityId));
    }

    @Override
    public boolean isSystemIdentity() {
        return false;
    }

    @Override
    public boolean canReadEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canReadEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canEditEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canEditEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canReadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canReadEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canEditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canEditEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canAuditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canAuditEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canSubmitEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canSubmitEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canUploadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canUploadEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canWriteEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canWriteEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canWriteEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canWriteEntity(EntityUtils.getId((String)entityId), entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canSubmitEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, String identityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canSubmitEntity(EntityUtils.getId((String)entityId), entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canUploadEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, String identityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canUploadEntity(EntityUtils.getId((String)entityId), entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canAuditEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, String identityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canAuditEntity(EntityUtils.getId((String)entityId), entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanReadEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanEditEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanEditEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanWriteEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanWriteEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanAuditEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanSubmitEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadEntityKeys(String entityId, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanUploadEntityKeys(EntityUtils.getId((String)entityId), queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanSubmitIdentityKeys(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanUploadIdentityKeys(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanAuditIdentityKeys(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).getCanReadIdentityKeys(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public void grantAllPrivilegesToEntityTable(String entityId) {
        this.adapterService.getEntityAuthorityAdapter(entityId).grantAllPrivilegesToEntityTable(EntityUtils.getId((String)entityId));
    }

    @Override
    public void grantAllPrivilegesToEntityData(String entityId, String ... entityKeyDatas) {
        this.adapterService.getEntityAuthorityAdapter(entityId).grantAllPrivilegesToEntityData(EntityUtils.getId((String)entityId), entityKeyDatas);
    }

    @Override
    public boolean canPublishEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canPublishEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canReadUnPublishEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.adapterService.getEntityAuthorityAdapter(entityId).canReadUnPublishEntity(EntityUtils.getId((String)entityId), entityKeyData, queryVersionDate);
    }
}

