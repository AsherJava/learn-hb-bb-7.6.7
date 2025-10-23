/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.Identity
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.Identity;
import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.actors.common.ActorUtil;
import com.jiuqi.nr.workflow2.actors.common.MasterEntityUtil;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CurrentUnitEexcutor
extends ExecutorBase {
    private UnitLevel unitLevel;
    private OrgIdentityService orgIdentityService;
    private IDataDefinitionRuntimeController dataDefinitionController;
    private IEntityViewRunTimeController entityViewController;
    private IEntityDataService entityDataService;
    private IdentityService identityService;
    private IEntityMetaService entityMetaService;

    public void setUnitLevel(UnitLevel unitLevel) {
        this.unitLevel = unitLevel;
    }

    public void setOrgIdentityService(OrgIdentityService orgIdentityService) {
        this.orgIdentityService = orgIdentityService;
    }

    public void setDataDefinitionController(IDataDefinitionRuntimeController dataDefinitionController) {
        this.dataDefinitionController = dataDefinitionController;
    }

    public void setEntityViewController(IEntityViewRunTimeController entityViewController) {
        this.entityViewController = entityViewController;
    }

    public void setEntityDataService(IEntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey rtBusinessKey) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        IBusinessKey businessKey = rtBusinessKey.getBusinessKey();
        FixedDimensionValue mdDimValue = businessKey.getBusinessObject().getDimensions().getDWDimensionValue();
        String currentUnitCode = (String)mdDimValue.getValue();
        if (currentUnitCode == null || currentUnitCode.length() == 0) {
            return false;
        }
        String entityTableKey = MasterEntityUtil.getTaskMasterEntityTableKey(this.entityMetaService, rtBusinessKey.getTask());
        Set<String> grantedEntityKeyDatas = this.getActorGrantedEntityKeys(actor, entityTableKey);
        if (this.unitLevel == UnitLevel.SELF) {
            return grantedEntityKeyDatas.contains(currentUnitCode);
        }
        FixedDimensionValue datatimeDimValue = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue();
        IEntityTable table = this.createUnitQuery(rtBusinessKey.getTask(), (String)datatimeDimValue.getValue());
        IEntityRow row = table.findByEntityKey(currentUnitCode);
        if (row == null) {
            return false;
        }
        if (this.unitLevel == UnitLevel.PARENT) {
            return grantedEntityKeyDatas.contains(row.getParentEntityKey());
        }
        if (this.unitLevel == UnitLevel.PARENTS) {
            for (String parentUnitCode : row.getParentsEntityKeyDataPath()) {
                if (!grantedEntityKeyDatas.contains(parentUnitCode)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey rtBusinessKey) {
        IBusinessKey businessKey = rtBusinessKey.getBusinessKey();
        FixedDimensionValue mdDimValue = businessKey.getBusinessObject().getDimensions().getDWDimensionValue();
        String currentUnitCode = (String)mdDimValue.getValue();
        if (currentUnitCode == null || currentUnitCode.length() == 0) {
            return Collections.emptySet();
        }
        if (this.unitLevel == UnitLevel.SELF) {
            List userIdsOwnToUnit = this.identityService.getUserIdAndIdentityMappUserIdByOrgCode(currentUnitCode);
            Collection userIdsManageToUnit = this.orgIdentityService.getUserIdAndIdentityMappUserIdByOrgCode(currentUnitCode);
            HashSet<String> userIds = new HashSet<String>();
            userIds.addAll(userIdsOwnToUnit);
            userIds.addAll(userIdsManageToUnit);
            return userIds;
        }
        FixedDimensionValue datatimeDimValue = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue();
        IEntityTable table = this.createUnitQuery(rtBusinessKey.getTask(), (String)datatimeDimValue.getValue());
        IEntityRow row = table.findByEntityKey(currentUnitCode);
        if (row == null) {
            return Collections.emptySet();
        }
        if (this.unitLevel == UnitLevel.PARENT) {
            String parentUnitCode = row.getParentEntityKey();
            if (parentUnitCode == null) {
                return Collections.emptySet();
            }
            List userIdsOwnToUnit = this.identityService.getUserIdAndIdentityMappUserIdByOrgCode(parentUnitCode);
            Collection userIdsManageToUnit = this.orgIdentityService.getUserIdAndIdentityMappUserIdByOrgCode(parentUnitCode);
            HashSet<String> userIds = new HashSet<String>();
            userIds.addAll(userIdsOwnToUnit);
            userIds.addAll(userIdsManageToUnit);
            return userIds;
        }
        if (this.unitLevel == UnitLevel.PARENTS) {
            if (row.getParentsEntityKeyDataPath().length == 0) {
                return Collections.emptySet();
            }
            HashSet<String> userIds = new HashSet<String>();
            for (String parentUnitCode : row.getParentsEntityKeyDataPath()) {
                List userIdsOwnToUnit = this.identityService.getUserIdAndIdentityMappUserIdByOrgCode(parentUnitCode);
                Collection userIdsManageToUnit = this.orgIdentityService.getUserIdAndIdentityMappUserIdByOrgCode(parentUnitCode);
                userIds.addAll(userIdsOwnToUnit);
                userIds.addAll(userIdsManageToUnit);
            }
            return userIds;
        }
        return Collections.emptySet();
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection rtBusinessKeys) {
        List childRows;
        Iterator table;
        Object businessObject2;
        FixedDimensionValue datatimeDimValue;
        if (actor.getIdentityId() == null) {
            return new BusinessObjectSet();
        }
        IBusinessKeyCollection businessKeys = rtBusinessKeys.getBusinessKeys();
        if (businessKeys.getBusinessObjects().size() == 0) {
            return new BusinessObjectSet();
        }
        String entityTableKey = MasterEntityUtil.getTaskMasterEntityTableKey(this.entityMetaService, rtBusinessKeys.getTask());
        Set<String> grantedEntityKeyDatas = this.getActorGrantedEntityKeys(actor, entityTableKey);
        HashSet<Object> allowEntityKeyDataSet = new HashSet();
        if (this.unitLevel == UnitLevel.SELF) {
            allowEntityKeyDataSet = new HashSet<String>(grantedEntityKeyDatas);
        } else if (this.unitLevel == UnitLevel.PARENT) {
            datatimeDimValue = null;
            Iterator iterator = rtBusinessKeys.getBusinessKeys().getBusinessObjects().iterator();
            if (iterator.hasNext()) {
                businessObject2 = (IBusinessObject)iterator.next();
                datatimeDimValue = businessObject2.getDimensions().getPeriodDimensionValue();
            }
            table = this.createUnitQuery(rtBusinessKeys.getTask(), (String)datatimeDimValue.getValue());
            for (String entityCode : grantedEntityKeyDatas) {
                childRows = table.getChildRows(entityCode);
                for (IEntityRow child : childRows) {
                    allowEntityKeyDataSet.add(child.getEntityKeyData());
                }
            }
        } else if (this.unitLevel == UnitLevel.PARENTS) {
            datatimeDimValue = null;
            table = rtBusinessKeys.getBusinessKeys().getBusinessObjects().iterator();
            if (table.hasNext()) {
                businessObject2 = (IBusinessObject)table.next();
                datatimeDimValue = businessObject2.getDimensions().getPeriodDimensionValue();
            }
            table = this.createUnitQuery(rtBusinessKeys.getTask(), (String)datatimeDimValue.getValue());
            for (String entityCode : grantedEntityKeyDatas) {
                childRows = table.getAllChildRows(entityCode);
                for (IEntityRow child : childRows) {
                    allowEntityKeyDataSet.add(child.getEntityKeyData());
                }
            }
        }
        BusinessObjectSet canActObjectSet = new BusinessObjectSet();
        for (Object businessObject2 : businessKeys.getBusinessObjects()) {
            FixedDimensionValue mdDimValue = businessObject2.getDimensions().getDWDimensionValue();
            if (!allowEntityKeyDataSet.contains((String)mdDimValue.getValue())) continue;
            canActObjectSet.add(businessObject2);
        }
        return canActObjectSet;
    }

    @Override
    boolean isActive() {
        return true;
    }

    private IEntityTable createUnitQuery(TaskDefine task, String period) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.None);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)period);
        query.setMasterKeys(masterKeys);
        String entityId = MasterEntityUtil.getTaskMasterEntityId(task);
        EntityViewDefine entityView = this.entityViewController.buildEntityView(entityId, task.getFilterExpression());
        query.setEntityView(entityView);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        context.setVarDimensionValueSet(masterKeys);
        try {
            return query.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new ProcessRuntimeException(null, "\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u53d1\u751f\u9519\u8bef\u3002", (Throwable)e);
        }
    }

    @Override
    boolean isDillwithBusinessKey() {
        return true;
    }

    private Set<String> getActorGrantedEntityKeys(IActor actor, String entityId) {
        Optional identity;
        HashSet<String> grantedEntityKeyDatas = new HashSet<String>(this.orgIdentityService.getGrantedOrg(actor.getIdentityId()));
        String ownOrgCode = null;
        if (ActorUtil.actorIsFromContext(actor)) {
            ownOrgCode = ActorUtil.getOrgnizationCodeFromContxt();
        }
        if (ownOrgCode == null && (identity = this.identityService.get(actor.getIdentityId())).isPresent()) {
            ownOrgCode = ((Identity)identity.get()).getOrgCode();
        }
        grantedEntityKeyDatas.add(ownOrgCode);
        return grantedEntityKeyDatas;
    }

    public static enum UnitLevel {
        SELF,
        PARENT,
        PARENTS;

    }
}

