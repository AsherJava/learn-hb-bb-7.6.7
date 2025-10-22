/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.BatchMergeAccess
 *  com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess
 *  com.jiuqi.nr.data.access.param.FormBatchAccessCache
 *  com.jiuqi.nr.data.access.param.IAccessForm
 *  com.jiuqi.nr.data.access.param.IAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.IBatchAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IBatchMergeAccess
 *  com.jiuqi.nr.data.access.service.IDataAccessItemService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Deprecated
public class FMDMAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private static final Logger logger = LoggerFactory.getLogger(FMDMAccessServiceImpl.class);
    private static final String TREE_NO_MANAGEMENT = "\u8be5\u8282\u70b9\u6ca1\u6709\u7ba1\u7406\u6743\u9650\uff0c\u5c01\u9762\u4ee3\u7801\u4e0d\u53ef\u7f16\u8f91";
    private Function<String, String> noAccessResion = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(TREE_NO_MANAGEMENT);
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());

    public int getOrder() {
        return 7;
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination collectionKey, String formKey) {
        if (this.systemIdentityService.isAdmin()) {
            return this.canAccess;
        }
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        if (formDefine != null && FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
            DimensionValueSet masterKey = collectionKey.toDimensionValueSet();
            EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            String periodValue = String.valueOf(masterKey.getValue(periodEntityInfo.getDimensionName()));
            Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
            Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
            String unitKey = String.valueOf(masterKey.getValue(dwEntityInfo.getDimensionName()));
            boolean canEdit = false;
            try {
                canEdit = this.entityAuthorityService.canEditEntity(dwEntityInfo.getKey(), unitKey, startDate, endDate);
                if (canEdit) {
                    return this.canAccess;
                }
                return new AccessCode(this.name(), "2");
            }
            catch (UnauthorizedEntityException e) {
                logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            }
        }
        return this.canAccess;
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public String name() {
        return "fmdm";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessResion.apply(code);
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection collectionKey, List<String> formKeys) {
        if (this.systemIdentityService.isAdmin()) {
            return (masterKey, formKey) -> this.canAccess;
        }
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        Map cacheMap = formBatchAccessCache.getCacheMap();
        String fmdmKey = null;
        for (String key : formKeys) {
            FormDefine formDefine = this.runtimeView.queryFormById(key);
            if (formDefine == null || !FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) continue;
            fmdmKey = formDefine.getKey();
            break;
        }
        if (!StringUtils.isEmpty(fmdmKey)) {
            DimensionValueSet masterKeys = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)collectionKey);
            List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)masterKeys);
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            String periodValue = String.valueOf(masterKeys.getValue(periodEntityInfo.getDimensionName()));
            Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
            try {
                HashSet<String> canEditEntityKeys = new HashSet<String>();
                for (DimensionValueSet dim : dimensionSetList) {
                    String unitKey = String.valueOf(dim.getValue(dwEntityInfo.getDimensionName()));
                    canEditEntityKeys.add(unitKey);
                }
                Map res = this.entityAuthorityService.canEditEntity(dwEntityInfo.getKey(), canEditEntityKeys, endDate);
                for (DimensionValueSet dim : dimensionSetList) {
                    HashMap<String, String> formKeyCache;
                    String unitKey = String.valueOf(dim.getValue(dwEntityInfo.getDimensionName()));
                    Boolean canEdit = (Boolean)res.get(unitKey);
                    if (canEdit == null) {
                        canEdit = false;
                    }
                    if (CollectionUtils.isEmpty(formKeyCache = (HashMap<String, String>)cacheMap.get(dim))) {
                        formKeyCache = new HashMap<String, String>();
                        cacheMap.put(dim, formKeyCache);
                    }
                    if (canEdit.booleanValue()) continue;
                    formKeyCache.put(fmdmKey, "2");
                }
            }
            catch (UnauthorizedEntityException e) {
                logger.error("\u6279\u91cf\u5199\u6743\u9650\u5224\u65ad\u51fa\u9519\uff01", e);
            }
        }
        return formBatchAccessCache;
    }

    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        if (this.systemIdentityService.isAdmin()) {
            return this.canAccess;
        }
        Set accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return this.canAccess;
        }
        HashSet<String> entityIds = new HashSet<String>();
        Set taskKeys = mergeParam.getTaskKeys();
        block2: for (String taskKey : taskKeys) {
            Set formsByTaskKey = mergeParam.getAccessFormsByTaskKey(taskKey);
            if (CollectionUtils.isEmpty(formsByTaskKey)) continue;
            List formKeys = formsByTaskKey.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
            List formDefines = this.runtimeView.queryFormsById(formKeys);
            for (FormDefine formDefine : formDefines) {
                if (!FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) continue;
                entityIds.add(this.runtimeView.queryTaskDefine(taskKey).getDw());
                continue block2;
            }
        }
        if (entityIds.isEmpty()) {
            return this.canAccess;
        }
        DimensionCombination masterKey = mergeParam.getMasterKey();
        FixedDimensionValue dwDimensionValue = masterKey.getDWDimensionValue();
        FixedDimensionValue periodDimensionValue = masterKey.getPeriodDimensionValue();
        if (dwDimensionValue != null && periodDimensionValue != null) {
            Object value = dwDimensionValue.getValue();
            Object periodV = periodDimensionValue.getValue();
            if (value != null && periodV != null) {
                String dwValue = value.toString();
                Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodV.toString(), (boolean)true);
                Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodV.toString(), (boolean)false);
                boolean canEdit = false;
                try {
                    for (String entityId : entityIds) {
                        canEdit = this.entityAuthorityService.canEditEntity(entityId, dwValue, startDate, endDate);
                    }
                    if (!canEdit) {
                        return new AccessCode(this.name(), "2");
                    }
                }
                catch (UnauthorizedEntityException e) {
                    logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
                }
            }
        }
        return this.canAccess;
    }

    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        if (this.systemIdentityService.isAdmin()) {
            return merge -> this.canAccess;
        }
        Set taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<String, Map> entityIdMap = new HashMap<String, Map>();
        String dsEntityId = null;
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            dsEntityId = dsContext.getContextEntityId();
        }
        HashSet<String> dwEntityIds = new HashSet<String>();
        if (StringUtils.isNotEmpty(dsEntityId)) {
            dwEntityIds.add(dsEntityId);
        }
        HashMap<String, Map> dsContextPeriod2EntityKeys = new HashMap<String, Map>();
        boolean hasFmdmForm = false;
        for (String string : taskKeys) {
            Set forms = mergeParam.getAccessFormsByTaskKey(string);
            List formKeys = forms.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
            List formDefines = this.runtimeView.queryFormsById(formKeys);
            Optional<FormDefine> any = formDefines.stream().filter(r -> r.getFormType() == FormType.FORM_TYPE_NEWFMDM).findAny();
            if (!any.isPresent()) continue;
            hasFmdmForm = true;
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(string);
            String dw = taskDefine.getDw();
            dwEntityIds.add(dw);
            Map period2EntityKeys = entityIdMap.computeIfAbsent(dw, k -> new HashMap());
            DimensionCollection dimensionCollection = mergeParam.getMasterKeysByTaskKey(string);
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination fixedDimensionValues : dimensionCombinations) {
                FixedDimensionValue dwDimensionValue = fixedDimensionValues.getDWDimensionValue();
                FixedDimensionValue periodDimensionValue = fixedDimensionValues.getPeriodDimensionValue();
                Map entityAccess = period2EntityKeys.computeIfAbsent(periodDimensionValue.getValue().toString(), k -> new HashMap());
                entityAccess.put(dwDimensionValue.getValue().toString(), null);
                if (!StringUtils.isNotEmpty((String)dsEntityId)) continue;
                Map dsContestEntityAccess = dsContextPeriod2EntityKeys.computeIfAbsent(periodDimensionValue.getValue().toString(), k -> new HashMap());
                dsContestEntityAccess.put(dwDimensionValue.getValue().toString(), null);
            }
        }
        if (hasFmdmForm && StringUtils.isNotEmpty((String)dsEntityId)) {
            entityIdMap.putIfAbsent(dsEntityId, dsContextPeriod2EntityKeys);
        }
        for (Map.Entry entry : entityIdMap.entrySet()) {
            String entityId = (String)entry.getKey();
            EntityViewDefine entityViewDefine = this.viewRunTimeController.buildEntityView(entityId);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            IEntityTable executeReader = null;
            try {
                executeReader = entityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6executeReader\u5931\u8d25\uff01" + e.getMessage());
            }
            Map periods = (Map)entry.getValue();
            for (Map.Entry entry2 : periods.entrySet()) {
                Map entityIds = (Map)entry2.getValue();
                Set entitySet = entityIds.keySet();
                HashSet<String> filterEntitySet = new HashSet<String>();
                for (String entityKey : entitySet) {
                    if (executeReader == null || executeReader.findByEntityKey(entityKey) == null) continue;
                    filterEntitySet.add(entityKey);
                }
                String periodValue = (String)entry2.getKey();
                Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
                try {
                    Map canEditEntity = this.entityAuthorityService.canEditEntity(entityId, filterEntitySet, startDate);
                    entityIds.putAll(canEditEntity);
                }
                catch (UnauthorizedEntityException e) {
                    logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
                }
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        List list = mergeParam.getAccessFormMerges();
        block9: for (IAccessFormMerge accessFormMerge : list) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            String periodValue = masterKey.getPeriodDimensionValue().getValue().toString();
            String dwValue = masterKey.getDWDimensionValue().getValue().toString();
            for (String entityId : dwEntityIds) {
                Boolean canAccess;
                Map entityAccMap;
                Map map = (Map)entityIdMap.get(entityId);
                if (map == null || (entityAccMap = (Map)map.get(periodValue)) == null || (canAccess = (Boolean)entityAccMap.get(dwValue)) == null || canAccess.booleanValue()) continue;
                codeMap.put(accessFormMerge, new AccessCode(this.name(), "2"));
                continue block9;
            }
            codeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }
}

