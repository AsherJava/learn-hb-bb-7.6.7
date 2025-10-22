/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class UnitWriteFormAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String ASSERTINFO1 = "formSchemeKey is must not be null!";
    private static final String ASSERTINFO2 = "masterKeys is must not be null!";
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u8be5\u5355\u4f4d\u5bf9\u62a5\u8868\u6ca1\u6709\u6743\u9650\u4e0d\u53ef\u7f16\u8f91");
    private final AccessCode canAccess = new AccessCode(this.name());

    @Override
    public String name() {
        return "unitWriteForm";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.readable(formSchemeKey, masterKey, formKey);
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.getAccessCodeByType(formSchemeKey, masterKey, AccessType.READ);
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.getAccessCodeByType(formSchemeKey, masterKey, AccessType.WRITE);
    }

    private AccessCode getAccessCodeByType(String formSchemeKey, DimensionCombination masterKey, AccessType accessType) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKey, (String)ASSERTINFO2);
        boolean systemIdentity = this.systemIdentityService.isAdmin();
        if (systemIdentity) {
            return this.canAccess;
        }
        AccessCode accessCode = new AccessCode(this.name());
        Optional<DataDimension> entity = this.getDimensionEntity(formSchemeKey, DimensionType.UNIT);
        Optional<DataDimension> period = this.getDimensionEntity(formSchemeKey, DimensionType.PERIOD);
        if (!entity.isPresent() || !period.isPresent()) {
            return accessCode;
        }
        boolean hasAuth = false;
        try {
            hasAuth = this.hasAuth(formSchemeKey, masterKey.toDimensionValueSet(), entity.get().getDimKey(), period.get().getDimKey(), accessType);
        }
        catch (ParseException e) {
            this.logger.error("\u8ba1\u7b97\u5355\u4f4d\u6743\u9650\u5f02\u5e38!", e);
        }
        if (!hasAuth) {
            accessCode = new AccessCode(this.name(), "2");
        }
        return accessCode;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return this.getBatchReadable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return this.getUnitBatchAccess(formSchemeKey, masterKeys, AccessType.READ);
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return this.getUnitBatchAccess(formSchemeKey, masterKeys, AccessType.WRITE);
    }

    private IBatchAccess getUnitBatchAccess(String formSchemeKey, DimensionCollection masterKeys, AccessType accessType) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO2);
        UnitBatchAccessCache unitBatchAccessCache = new UnitBatchAccessCache(this.name(), formSchemeKey);
        boolean systemIdentity = this.systemIdentityService.isAdmin();
        if (systemIdentity) {
            return unitBatchAccessCache;
        }
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entityViewDefine = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        Optional<DataDimension> period = this.getDimensionEntity(formSchemeKey, DimensionType.PERIOD);
        if (!period.isPresent() || entityViewDefine == null) {
            return unitBatchAccessCache;
        }
        String unitEntityId = entityViewDefine.getEntityId();
        String dimName = this.entityMetaService.getDimensionName(unitEntityId);
        List dimension = masterKeys.getDimensionCombinations();
        HashMap<String, Set> periodEntityCache = new HashMap<String, Set>();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(period.get().getDimKey());
        HashMap<String, List> map = new HashMap<String, List>();
        for (DimensionCombination dimensionCombination : dimension) {
            Object value = dimensionCombination.getValue("DATATIME");
            if (value == null) continue;
            String periodValue = value.toString();
            Object dwObj = dimensionCombination.getValue(dimName);
            if (dwObj == null) continue;
            String unitDimValue = dwObj.toString();
            List dimensionCombinations = map.computeIfAbsent(periodValue + "-" + unitDimValue, k -> new ArrayList());
            dimensionCombinations.add(dimensionCombination);
            Set set = periodEntityCache.computeIfAbsent(periodValue, k -> new HashSet());
            set.add(unitDimValue);
        }
        for (Map.Entry entry : periodEntityCache.entrySet()) {
            String periodValue = (String)entry.getKey();
            Set entityIds = (Set)entry.getValue();
            try {
                Date[] periodData = periodProvider.getPeriodDateRegion(periodValue);
                Map<String, Boolean> entityAcc = new HashMap();
                switch (accessType) {
                    case VISIT: 
                    case READ: {
                        for (String string : entityIds) {
                            boolean b = this.entityAuthorityService.canReadEntity(unitEntityId, string, periodData[0], periodData[1]);
                            entityAcc.put(string, b);
                        }
                        break;
                    }
                    case WRITE: 
                    case SYS_WRITE: {
                        entityAcc = this.entityAuthorityService.canWriteEntity(unitEntityId, entityIds, periodData[1]);
                        break;
                    }
                    default: {
                        throw new NotSupportedException("\u4e0d\u652f\u6301\u7684\u6743\u9650\u7c7b\u578b");
                    }
                }
                for (Map.Entry entry2 : entityAcc.entrySet()) {
                    List dimensionCombinations;
                    Boolean value = (Boolean)entry2.getValue();
                    if (Boolean.TRUE.equals(value) || (dimensionCombinations = (List)map.get(periodValue + "-" + (String)entry2.getKey())) == null) continue;
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        unitBatchAccessCache.getCacheMap().put(dimensionCombination.toDimensionValueSet(), "2");
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u6743\u9650\u5224\u65ad\u5f02\u5e38!", e);
            }
        }
        return unitBatchAccessCache;
    }

    @Override
    public AccessLevel getLevel() {
        return AccessLevel.UNIT;
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    private Optional<DataDimension> getDimensionEntity(String formSchemeKey, DimensionType dimType) {
        FormSchemeDefine formSchem = this.runtimeView.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formSchem.getTaskKey());
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List dimesion = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        return dimesion.stream().filter(e -> e.getDimensionType().equals((Object)dimType)).findFirst();
    }

    private Boolean hasAuth(String formSchemeKey, DimensionValueSet masterKey, String unitEntityId, String periodEntityId, AccessType accessType) throws ParseException {
        String dimName = this.entityMetaService.getDimensionName(unitEntityId);
        String unitDimValue = masterKey.getValue(dimName).toString();
        String periodValue = masterKey.getValue("DATATIME").toString();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodEntityId);
        Date[] periodData = periodProvider.getPeriodDateRegion(periodValue);
        if (periodData.length <= 1) {
            return true;
        }
        Date queryVersionStartDate = periodData[0];
        Date queryVersionEndDate = periodData[1];
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entityViewDefine = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        boolean hasAuth = false;
        if (StringUtils.isNotEmpty((String)unitDimValue)) {
            boolean systemIdentity = this.systemIdentityService.isAdmin();
            if (!systemIdentity) {
                try {
                    if (this.entityAuthorityService.isEnableAuthority(entityViewDefine.getEntityId())) {
                        switch (accessType) {
                            case VISIT: 
                            case READ: {
                                return this.entityAuthorityService.canReadEntity(entityViewDefine.getEntityId(), unitDimValue, queryVersionStartDate, queryVersionEndDate);
                            }
                            case WRITE: 
                            case SYS_WRITE: {
                                return this.entityAuthorityService.canWriteEntity(entityViewDefine.getEntityId(), unitDimValue, queryVersionStartDate, queryVersionEndDate);
                            }
                        }
                    }
                    hasAuth = true;
                }
                catch (UnauthorizedEntityException e) {
                    this.logger.error(e.getMessage(), e);
                }
            } else {
                hasAuth = true;
            }
        }
        return hasAuth;
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        boolean systemIdentity = this.systemIdentityService.isAdmin();
        if (systemIdentity) {
            return this.canAccess;
        }
        String userId = NpContextHolder.getContext().getUserId();
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccess;
        }
        Optional first = taskKeys.stream().findFirst();
        TaskDefine define = this.runtimeView.queryTaskDefine((String)first.get());
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(define.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(define.getDateTime());
        Object dwValue = masterKey.getValue(dwDimensionName);
        Object periodValue = masterKey.getValue(periodDimensionName);
        if (dwValue == null || periodValue == null) {
            return this.canAccess;
        }
        HashSet<String> canUse = new HashSet<String>();
        for (String taskKey : taskKeys) {
            Date[] periodData;
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
            if (canUse.contains(taskDefine.getDw())) continue;
            canUse.add(taskDefine.getDw());
            if (!this.entityAuthorityService.isEnableAuthority(taskDefine.getDw())) continue;
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
            try {
                periodData = periodProvider.getPeriodDateRegion(periodValue.toString());
            }
            catch (ParseException e) {
                this.logger.error("\u83b7\u53d6\u65f6\u671f\u6570\u636e\u5f02\u5e38!", e);
                continue;
            }
            if (periodData.length <= 1) continue;
            Date queryVersionStartDate = periodData[0];
            Date queryVersionEndDate = periodData[1];
            try {
                boolean writeEntity = this.entityAuthorityService.canWriteEntity(taskDefine.getDw(), dwValue.toString(), queryVersionStartDate, queryVersionEndDate);
                if (writeEntity) continue;
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("\u7528\u6237 {} \u4efb\u52a1 {} \u5355\u4f4d\u6743\u9650\u5224\u65ad\u65e0\u6743\u9650 {} {} {} {}", userId, taskDefine.getTitle(), taskDefine.getDw(), dwValue, queryVersionStartDate, queryVersionEndDate);
                }
                return new AccessCode(this.name(), "2");
            }
            catch (UnauthorizedEntityException e) {
                this.logger.error("\u6743\u9650\u5224\u65ad\u5f02\u5e38\u5f02\u5e38!", e);
            }
        }
        return this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return new CanAccessBatchMergeAccess(this.name());
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return new CanAccessBatchMergeAccess(this.name());
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        String dwDimensionName;
        boolean systemIdentity = this.systemIdentityService.isAdmin();
        if (systemIdentity) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        Set<String> taskKeys = mergeParam.getTaskKeys();
        Optional first = taskKeys.stream().findFirst();
        if (first.isPresent()) {
            String taskKey = (String)first.get();
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
            dwDimensionName = this.dataAccesslUtil.getDwDimensionName(taskDefine.getDw());
            if (dwDimensionName == null) {
                return new CanAccessBatchMergeAccess(this.name());
            }
        } else {
            return new CanAccessBatchMergeAccess(this.name());
        }
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        Map<String, Map<String, Set<String>>> unAccessCache = this.buildUnitAccessWithTask(mergeParam, dwDimensionName);
        block0: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            Set<String> mergeTaskKeys = accessFormMerge.getTaskKeys();
            Object dwValue = masterKey.getValue(dwDimensionName);
            FixedDimensionValue periodDimensionValue = masterKey.getPeriodDimensionValue();
            Object periodValue = periodDimensionValue.getValue();
            for (String mergeTaskKey : mergeTaskKeys) {
                Set<String> unAccessDws;
                Map<String, Set<String>> periodMap = unAccessCache.get(mergeTaskKey);
                if (periodMap == null || (unAccessDws = periodMap.get(periodValue.toString())) == null || !unAccessDws.contains(dwValue.toString())) continue;
                codeMap.put(accessFormMerge, new AccessCode(this.name(), "2"));
                continue block0;
            }
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    private Map<String, Map<String, Set<String>>> buildUnitAccessWithTask(IBatchAccessFormMerge mergeParam, String dwDimensionName) {
        String userId = NpContextHolder.getContext().getUserId();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        HashMap<String, Map<String, Set<String>>> unAccessCache = new HashMap<String, Map<String, Set<String>>>();
        for (String taskKey : taskKeys) {
            Object periodValue;
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
            if (!this.entityAuthorityService.isEnableAuthority(taskDefine.getDw())) continue;
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            List combinations = masterKeysByTaskKey.getDimensionCombinations();
            HashMap<String, Set> period2DwValues = new HashMap<String, Set>();
            for (DimensionCombination combination : combinations) {
                Object dwValue = combination.getValue(dwDimensionName);
                periodValue = combination.getPeriodDimensionValue().getValue();
                if (dwValue == null || periodValue == null) continue;
                period2DwValues.computeIfAbsent(periodValue.toString(), k -> new HashSet()).add(dwValue.toString());
            }
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
            for (Map.Entry period2DwEntry : period2DwValues.entrySet()) {
                Date[] periodData;
                periodValue = (String)period2DwEntry.getKey();
                try {
                    periodData = periodProvider.getPeriodDateRegion((String)periodValue);
                }
                catch (ParseException e) {
                    throw new AccessException("\u6743\u9650\u5224\u65ad\u5931\u8d25,\u65f6\u671f\u89e3\u6790\u9519\u8bef", e);
                }
                if (periodData != null && periodData.length > 1) {
                    Date queryVersionEndDate = periodData[1];
                    try {
                        Map access = this.entityAuthorityService.canWriteEntity(taskDefine.getDw(), (Set)period2DwEntry.getValue(), queryVersionEndDate);
                        for (Map.Entry entry : access.entrySet()) {
                            String dwValue = (String)entry.getKey();
                            Boolean canWrite = (Boolean)entry.getValue();
                            if (canWrite.booleanValue()) continue;
                            unAccessCache.computeIfAbsent(taskKey, k -> new HashMap()).computeIfAbsent(periodValue, k -> new HashSet()).add(dwValue);
                            if (!this.logger.isDebugEnabled()) continue;
                            this.logger.debug("\u7528\u6237 {} \u4efb\u52a1 {} \u5b9e\u4f53 {} \u5355\u4f4d {} \u7248\u672c {} \u6743\u9650\u5224\u65ad\u65e0\u6743\u9650", userId, taskDefine.getTitle(), taskDefine.getDw(), dwValue, queryVersionEndDate);
                        }
                        continue;
                    }
                    catch (UnauthorizedEntityException e) {
                        this.logger.error("\u6743\u9650\u5224\u65ad\u5f02\u5e38\u5f02\u5e38!", e);
                        throw new AccessException("\u83b7\u53d6\u65f6\u671f\u6570\u636e\u5f02\u5e38\uff0c\u65e0\u6cd5\u5224\u65ad\u6743\u9650");
                    }
                }
                throw new AccessException("\u83b7\u53d6\u65f6\u671f\u6570\u636e\u5f02\u5e38\uff0c\u65e0\u6cd5\u5224\u65ad\u6743\u9650");
            }
        }
        return unAccessCache;
    }
}

