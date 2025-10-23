/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck
 *  com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient
 *  com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataModifier
 *  com.jiuqi.nr.entity.adapter.impl.org.exception.OrgDataSyncException
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.param.IEntityDeleteParam
 *  com.jiuqi.nr.entity.param.IEntityUpdateParam
 *  com.jiuqi.nr.entity.param.impl.EntityDeleteParam
 *  com.jiuqi.nr.entity.param.impl.EntityUpdateParam
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.service.OrgAuthService
 */
package com.jiuqi.nr.subdatabase.org.ext;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataModifier;
import com.jiuqi.nr.entity.adapter.impl.org.exception.OrgDataSyncException;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.nr.entity.param.impl.EntityDeleteParam;
import com.jiuqi.nr.entity.param.impl.EntityUpdateParam;
import com.jiuqi.nr.subdatabase.facade.SubDataBaseEntityIdProvider;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.service.OrgAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class OrgDataSubTableModifyProvider
extends OrgDataModifier {
    private SubDataBaseEntityIdProvider subDataBaseEntityIdProvider;

    public OrgDataSubTableModifyProvider(SystemIdentityService systemIdentityService, OrgAdapterClient orgAdapterClient, OrgDataCheck orgDataCheck, OrgDataClient orgDataClient, SubDataBaseEntityIdProvider subDataBaseEntityIdProvider, OrgAuthService orgAuthService) {
        super(systemIdentityService, orgAdapterClient, orgDataCheck, orgDataClient, orgAuthService);
        this.subDataBaseEntityIdProvider = subDataBaseEntityIdProvider;
    }

    public EntityUpdateResult insertRows(IEntityUpdateParam updateParam) throws OrgDataSyncException {
        EntityUpdateParam entityUpdateParam = new EntityUpdateParam();
        BeanUtils.copyProperties(updateParam, entityUpdateParam);
        entityUpdateParam.setEntityId(this.getEntityId(updateParam.getEntityId(), updateParam.getContext()));
        return super.insertRows((IEntityUpdateParam)entityUpdateParam);
    }

    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws OrgDataSyncException {
        EntityDeleteParam entityDeleteParam = new EntityDeleteParam();
        BeanUtils.copyProperties(deleteParam, entityDeleteParam);
        entityDeleteParam.setEntityId(this.getEntityId(deleteParam.getEntityId(), deleteParam.getContext()));
        return super.deleteRows((IEntityDeleteParam)entityDeleteParam);
    }

    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws OrgDataSyncException {
        EntityUpdateParam entityUpdateParam = new EntityUpdateParam();
        BeanUtils.copyProperties(updateParam, entityUpdateParam);
        entityUpdateParam.setEntityId(this.getEntityId(updateParam.getEntityId(), updateParam.getContext()));
        return super.updateRows((IEntityUpdateParam)entityUpdateParam);
    }

    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) {
        EntityUpdateParam entityUpdateParam = new EntityUpdateParam();
        BeanUtils.copyProperties(updateParam, entityUpdateParam);
        entityUpdateParam.setEntityId(this.getEntityId(updateParam.getEntityId(), updateParam.getContext()));
        return super.rowsCheck((IEntityUpdateParam)entityUpdateParam, insert);
    }

    private String getEntityId(String entityId, ExecutorContext context) {
        if (this.subDataBaseEntityIdProvider == null) {
            return entityId;
        }
        String subDataBaseEntityId = this.subDataBaseEntityIdProvider.getSubDataBaseEntityId(entityId, context);
        if (!StringUtils.hasText(subDataBaseEntityId) || subDataBaseEntityId.equals(entityId)) {
            return entityId;
        }
        return subDataBaseEntityId;
    }
}

