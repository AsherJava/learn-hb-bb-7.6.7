/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.impl.Actor.AuthorityActorStrategyBase;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class CanUploadActorStrategy
extends AuthorityActorStrategyBase {
    private static final Logger logger = LoggerFactory.getLogger(CanUploadActorStrategy.class);

    public CanUploadActorStrategy() {
        super("\u6709\u4e0a\u62a5\u6743\u9650\u7684\u4eba");
    }

    @Override
    protected Set<String> getCanOperateIdentityKeys(EntityViewDefine entityView, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate, BusinessKeyInfo businessKey) {
        if (!this.entityAuthorityService.isEnableAuthority(entityView.getEntityId())) {
            return Collections.emptySet();
        }
        Set<String> identityKeysByFormSchemeKey = this.nrProcessAuthorityProvider.getCanUploadIdentityKeys(businessKey);
        Set identityKeysByEntity = null;
        try {
            identityKeysByEntity = this.entityAuthorityService.getCanUploadIdentityKeys(entityView.getEntityId(), entityKeyData, queryVersionStartDate, queryVersionDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        identityKeysByEntity.retainAll(identityKeysByFormSchemeKey);
        return identityKeysByEntity;
    }

    @Override
    protected boolean canOperateEntity(EntityViewDefine entityView, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate, BusinessKeyInfo businessKey) {
        if (!this.entityAuthorityService.isEnableAuthority(entityView.getEntityId())) {
            return true;
        }
        boolean canUploadEntity = false;
        try {
            canUploadEntity = this.entityAuthorityService.canUploadEntity(entityView.getEntityId(), entityKeyData, queryVersionStartDate, queryVersionDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        boolean canUpload = this.nrProcessAuthorityProvider.canUpload(businessKey);
        return canUploadEntity && canUpload;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Class<? extends ActorStrategyParameter.Useless> getParameterType() {
        return ActorStrategyParameter.Useless.class;
    }

    @Override
    public String getDescription() {
        return null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected Map<BusinessKey, Boolean> canBatchOperateEntity(EntityViewDefine entityView, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate, List<BusinessKey> businessKeys) {
        String masterEntityKey;
        if (businessKeys.isEmpty() || businessKeys.size() == 0) {
            return Collections.emptyMap();
        }
        BusinessKey businessKey = (BusinessKey)businessKeys.stream().findAny().get();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        HashMap<BusinessKey, Boolean> canOperate = new HashMap<BusinessKey, Boolean>();
        if (!this.entityAuthorityService.isEnableAuthority(entityView.getEntityId())) {
            for (BusinessKey businessKey2 : businessKeys) {
                canOperate.put(businessKey2, true);
            }
        }
        HashSet<String> entityKeyDatas = new HashSet<String>();
        for (BusinessKey business : businessKeys) {
            MasterEntity masterEntity = business.getMasterEntity();
            masterEntityKey = masterEntity.getMasterEntityKey(dwMainDimName);
            entityKeyDatas.add(masterEntityKey);
        }
        HashMap hashMap = new HashMap();
        try {
            Map map = this.entityAuthorityService.canUploadEntity(entityView.getEntityId(), entityKeyDatas, queryVersionDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        for (BusinessKey businessKey1 : businessKeys) {
            void var10_14;
            masterEntityKey = businessKey1.getMasterEntity().getMasterEntityKey(dwMainDimName);
            Boolean hasAuth = (Boolean)var10_14.get(masterEntityKey);
            boolean canAudit = this.nrProcessAuthorityProvider.canUpload(businessKey1);
            canOperate.put(businessKey1, hasAuth != false && canAudit);
        }
        return canOperate;
    }

    @Override
    public boolean isBatch() {
        return true;
    }
}

