/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.workflow2.actors.common.ActorUtil;
import com.jiuqi.nr.workflow2.actors.common.MasterEntityUtil;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class AuthorityEexcutor
extends ExecutorBase {
    private AuthorityItem authority;
    private IPeriodEntityAdapter periodAdapter;
    private IEntityAuthorityService entityAuthorityService;
    private DefinitionAuthorityProvider definitionAuthorityProvider;

    public void setAuthority(AuthorityItem authority) {
        this.authority = authority;
    }

    public void setPeriodEntityAdapter(IPeriodEntityAdapter periodAdapter) {
        this.periodAdapter = periodAdapter;
    }

    public void setEntityAuthorityService(IEntityAuthorityService entityAuthorityService) {
        this.entityAuthorityService = entityAuthorityService;
    }

    public void setDefinitionAuthorityProvider(DefinitionAuthorityProvider definitionAuthorityProvider) {
        this.definitionAuthorityProvider = definitionAuthorityProvider;
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey rtBusinessKey) {
        ActorUtil.assertActorIsFromContext(actor);
        if (actor.getIdentityId() == null) {
            return false;
        }
        IBusinessKey businessKey = rtBusinessKey.getBusinessKey();
        boolean canOperateMd = false;
        String mdEntityId = MasterEntityUtil.getTaskMasterEntityId(rtBusinessKey.getTask());
        FixedDimensionValue mdDimValue = businessKey.getBusinessObject().getDimensions().getDWDimensionValue();
        if (!this.entityAuthorityService.isEnableAuthority(mdEntityId)) {
            canOperateMd = true;
        } else {
            FixedDimensionValue datatimeDimValue = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue();
            Date[] dates = this.getPeriodDateRegion(datatimeDimValue.getEntityID(), (String)datatimeDimValue.getValue());
            canOperateMd = this.canActEntity(mdEntityId, (String)mdDimValue.getValue(), dates[0], dates[1]);
        }
        if (!canOperateMd) {
            return false;
        }
        switch (rtBusinessKey.getWorkflowSettings().getWorkflowObjectType()) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                return this.canActFormScheme(rtBusinessKey.getFormSchemeKey(), mdEntityId, (String)mdDimValue.getValue());
            }
            case FORM: {
                String formKey = this.assertFormObject(businessKey.getBusinessObject()).getFormKey();
                return this.canActForm(formKey, mdEntityId, (String)mdDimValue.getValue());
            }
            case FORM_GROUP: {
                String formGroupKey = this.assertFormGroupObject(businessKey.getBusinessObject()).getFormGroupKey();
                return this.canActFormGroup(formGroupKey, mdEntityId, (String)mdDimValue.getValue());
            }
        }
        throw new IllegalArgumentException("unknow WorkflowObjectType: " + rtBusinessKey.getWorkflowSettings().getWorkflowObjectType());
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey rtBusinessKey) {
        IBusinessKey businessKey = rtBusinessKey.getBusinessKey();
        Set<String> canActMdIdentities = null;
        String mdEntityId = MasterEntityUtil.getTaskMasterEntityId(rtBusinessKey.getTask());
        FixedDimensionValue mdDimValue = businessKey.getBusinessObject().getDimensions().getDWDimensionValue();
        if (this.entityAuthorityService.isEnableAuthority(mdEntityId)) {
            FixedDimensionValue datatimeDimValue = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue();
            Date[] dates = this.getPeriodDateRegion(datatimeDimValue.getEntityID(), (String)datatimeDimValue.getValue());
            canActMdIdentities = this.getCanActMdIdentities(mdEntityId, (String)mdDimValue.getValue(), dates[0], dates[1]);
        }
        Set<String> canActTaskIdentities = null;
        switch (rtBusinessKey.getWorkflowSettings().getWorkflowObjectType()) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                canActTaskIdentities = this.getCanActFormSchemeIdentities(rtBusinessKey.getFormSchemeKey(), mdEntityId, (String)mdDimValue.getValue());
                break;
            }
            case FORM: {
                String formKey = this.assertFormObject(businessKey.getBusinessObject()).getFormKey();
                canActTaskIdentities = this.getCanActFormIdentities(formKey, mdEntityId, (String)mdDimValue.getValue());
                break;
            }
            case FORM_GROUP: {
                String formGroupKey = this.assertFormGroupObject(businessKey.getBusinessObject()).getFormGroupKey();
                canActTaskIdentities = this.getCanActFormGroupIdentities(formGroupKey, mdEntityId, (String)mdDimValue.getValue());
                break;
            }
            default: {
                throw new IllegalArgumentException("unknow WorkflowObjectType: " + rtBusinessKey.getWorkflowSettings().getWorkflowObjectType());
            }
        }
        if (canActMdIdentities != null) {
            canActTaskIdentities.retainAll(canActMdIdentities);
        }
        return canActTaskIdentities;
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection rtBusinessKeys) {
        ActorUtil.assertActorIsFromContext(actor);
        if (actor.getIdentityId() == null) {
            return new BusinessObjectSet();
        }
        IBusinessKeyCollection businessKeys = rtBusinessKeys.getBusinessKeys();
        if (businessKeys.getBusinessObjects().size() == 0) {
            return new BusinessObjectSet();
        }
        BusinessObjectSet canActObjectSet = new BusinessObjectSet();
        Set<String> canActEntities = null;
        String mdEntityId = MasterEntityUtil.getTaskMasterEntityId(rtBusinessKeys.getTask());
        if (this.entityAuthorityService.isEnableAuthority(mdEntityId)) {
            FixedDimensionValue datatimeDimValue = null;
            Iterator iterator = businessKeys.getBusinessObjects().iterator();
            if (iterator.hasNext()) {
                IBusinessObject businessObject = (IBusinessObject)iterator.next();
                datatimeDimValue = businessObject.getDimensions().getPeriodDimensionValue();
            }
            Date[] dates = this.getPeriodDateRegion(rtBusinessKeys.getTask().getDateTime(), (String)datatimeDimValue.getValue());
            canActEntities = this.getCanActMdCodes(mdEntityId, dates[0], dates[1]);
        }
        switch (rtBusinessKeys.getWorkflowSettings().getWorkflowObjectType()) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                FixedDimensionValue mdDimValue;
                for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
                    boolean canActFormscheme;
                    mdDimValue = businessObject.getDimensions().getDWDimensionValue();
                    if (!canActEntities.contains((String)mdDimValue.getValue()) || !(canActFormscheme = this.canActFormScheme(rtBusinessKeys.getFormSchemeKey(), mdEntityId, (String)mdDimValue.getValue()))) continue;
                    canActObjectSet.add((Object)businessObject);
                }
                break;
            }
            case FORM: {
                FixedDimensionValue mdDimValue;
                for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
                    String formKey;
                    Boolean canActForm;
                    mdDimValue = businessObject.getDimensions().getDWDimensionValue();
                    if (!canActEntities.contains((String)mdDimValue.getValue()) || !(canActForm = Boolean.valueOf(this.canActForm(formKey = this.assertFormObject(businessObject).getFormKey(), mdEntityId, (String)mdDimValue.getValue()))).booleanValue()) continue;
                    canActObjectSet.add((Object)businessObject);
                }
                break;
            }
            case FORM_GROUP: {
                FixedDimensionValue mdDimValue;
                for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
                    String formGroupKey;
                    boolean canActFormGroup;
                    mdDimValue = businessObject.getDimensions().getDWDimensionValue();
                    if (!canActEntities.contains((String)mdDimValue.getValue()) || !(canActFormGroup = this.canActFormGroup(formGroupKey = this.assertFormGroupObject(businessObject).getFormGroupKey(), mdEntityId, (String)mdDimValue.getValue()))) continue;
                    canActObjectSet.add((Object)businessObject);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("unknow WorkflowObjectType: " + rtBusinessKeys.getWorkflowSettings().getWorkflowObjectType());
            }
        }
        return canActObjectSet;
    }

    private Date[] getPeriodDateRegion(String entityId, String period) {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(entityId);
        try {
            return periodProvider.getPeriodDateRegion(period);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684\u65f6\u671f\u3002", e);
        }
    }

    private boolean canActEntity(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) {
        try {
            switch (this.authority) {
                case SUBMIT: {
                    return this.entityAuthorityService.canSubmitEntity(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case REPORT: {
                    return this.entityAuthorityService.canUploadEntity(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case AUDIT: {
                    return this.entityAuthorityService.canAuditEntity(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case READ: {
                    return this.entityAuthorityService.canReadEntity(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
            }
            return false;
        }
        catch (UnauthorizedEntityException e) {
            return true;
        }
    }

    private Set<String> getCanActMdIdentities(String entityId, String entityKeyData, Date queryVersionStartDate, Date queryVersionDate) {
        try {
            switch (this.authority) {
                case SUBMIT: {
                    return this.entityAuthorityService.getCanSubmitIdentityKeys(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case REPORT: {
                    return this.entityAuthorityService.getCanUploadIdentityKeys(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case AUDIT: {
                    return this.entityAuthorityService.getCanAuditIdentityKeys(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
                case READ: {
                    return this.entityAuthorityService.getCanReadIdentityKeys(entityId, entityKeyData, queryVersionStartDate, queryVersionDate);
                }
            }
            return null;
        }
        catch (UnauthorizedEntityException e) {
            return null;
        }
    }

    private Set<String> getCanActMdCodes(String entityId, Date queryVersionStartDate, Date queryVersionDate) {
        try {
            switch (this.authority) {
                case SUBMIT: {
                    return this.entityAuthorityService.getCanSubmitEntityKeys(entityId, queryVersionStartDate, queryVersionDate);
                }
                case REPORT: {
                    return this.entityAuthorityService.getCanUploadEntityKeys(entityId, queryVersionStartDate, queryVersionDate);
                }
                case AUDIT: {
                    return this.entityAuthorityService.getCanAuditEntityKeys(entityId, queryVersionStartDate, queryVersionDate);
                }
                case READ: {
                    return this.entityAuthorityService.getCanReadEntityKeys(entityId, queryVersionStartDate, queryVersionDate);
                }
            }
            return null;
        }
        catch (UnauthorizedEntityException e) {
            return null;
        }
    }

    private boolean canActFormScheme(String formSchemeKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.canSubmitFormScheme(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.canUploadFormScheme(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.canAuditFormScheme(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.canReadFormScheme(formSchemeKey);
            }
        }
        return false;
    }

    private Set<String> getCanActFormSchemeIdentities(String formSchemeKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.getCanSubmitIdentityKeys(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.getCanUploadIdentityKeys(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.getCanAuditIdentityKeys(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.getCanReadIdentityKeys(formSchemeKey, mdEntityKeyData, mdEntityId);
            }
        }
        return null;
    }

    private boolean canActForm(String formKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.canSubmitForm(formKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.canUploadForm(formKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.canAuditForm(formKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.canReadForm(formKey, mdEntityKeyData, mdEntityId);
            }
        }
        return false;
    }

    private Set<String> getCanActFormIdentities(String formKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.getFormCanSubmitIdentityKeys(formKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.getFormCanUploadIdentityKeys(formKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.getFormCanAuditIdentityKeys(formKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.getFormCanReadIdentityKeys(formKey, mdEntityKeyData, mdEntityId);
            }
        }
        return null;
    }

    private boolean canActFormGroup(String formGroupKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.canSubmitFormGroup(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.canUploadFormGroup(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.canAuditFormGroup(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.canReadFormGroup(formGroupKey, mdEntityKeyData, mdEntityId);
            }
        }
        return false;
    }

    private Set<String> getCanActFormGroupIdentities(String formGroupKey, String mdEntityId, String mdEntityKeyData) {
        switch (this.authority) {
            case SUBMIT: {
                return this.definitionAuthorityProvider.getFormGroupCanSubmitIdentityKeys(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case REPORT: {
                return this.definitionAuthorityProvider.getFormGroupCanUploadIdentityKeys(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case AUDIT: {
                return this.definitionAuthorityProvider.getFormGroupCanAuditIdentityKeys(formGroupKey, mdEntityKeyData, mdEntityId);
            }
            case READ: {
                return this.definitionAuthorityProvider.getFormGroupCanReadIdentityKeys(formGroupKey, mdEntityKeyData, mdEntityId);
            }
        }
        return null;
    }

    private IFormObject assertFormObject(IBusinessObject businessObject) {
        if (businessObject instanceof IFormObject) {
            return (IFormObject)businessObject;
        }
        throw new IllegalArgumentException("BusinessObject must be instance of IFormObject\u3002");
    }

    private IFormGroupObject assertFormGroupObject(IBusinessObject businessObject) {
        if (businessObject instanceof IFormGroupObject) {
            return (IFormGroupObject)businessObject;
        }
        throw new IllegalArgumentException("BusinessObject must be instance of IFormGroupObject\u3002");
    }

    @Override
    boolean isActive() {
        return true;
    }

    @Override
    boolean isDillwithBusinessKey() {
        return true;
    }

    public static enum AuthorityItem {
        SUBMIT,
        REPORT,
        AUDIT,
        READ;

    }
}

