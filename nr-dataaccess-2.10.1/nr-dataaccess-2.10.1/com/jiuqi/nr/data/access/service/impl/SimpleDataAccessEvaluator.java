/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionException
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.access.inner.DimResources
 *  com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionException;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.access.inner.DimResources;
import com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SimpleDataAccessEvaluator
implements DataPermissionEvaluator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataAccessEvaluator.class);
    private final IDataAccessServiceProvider dataAccessProvider;
    private final EvaluatorParam evaluatorParam;
    private final DimensionCollection dimensionCollection;
    private final DimensionCombination dimensionCombination;
    private final Collection<String> resourcesIds;
    private EnumMap<AuthType, IBatchAccessResult> type2AccRes;
    private EnumMap<AuthType, IBatchZBAccessResult> type2ZbAccRes;
    private static final String ERROR_INFO = "\u672a\u652f\u6301\u7684\u6743\u9650\u7c7b\u578b";

    public SimpleDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam) {
        this.dataAccessProvider = dataAccessProvider;
        this.evaluatorParam = evaluatorParam;
        this.dimensionCollection = null;
        this.dimensionCombination = null;
        this.resourcesIds = null;
    }

    public SimpleDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam, DimensionCollection collection, Collection<String> resourcesIds) {
        this.dataAccessProvider = dataAccessProvider;
        this.evaluatorParam = evaluatorParam;
        this.dimensionCollection = collection;
        this.dimensionCombination = null;
        this.resourcesIds = resourcesIds;
    }

    public SimpleDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam, DimensionCombination combination, Collection<String> resourcesIds) {
        this.dataAccessProvider = dataAccessProvider;
        this.evaluatorParam = evaluatorParam;
        this.dimensionCombination = combination;
        this.dimensionCollection = null;
        this.resourcesIds = resourcesIds;
    }

    public boolean haveAccess(DimensionCombination masterKey, String resourcesId, AuthType authType) throws DataPermissionException {
        return this.haveAccess(masterKey, resourcesId, authType, null);
    }

    public boolean haveAccess(DimensionCombination masterKey, String resourcesId, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        try {
            if (this.evaluatorParam.getResourceType() == ResouceType.FORM.getCode()) {
                return this.formAccess(masterKey, resourcesId, authType, ignoreItems);
            }
            if (this.evaluatorParam.getResourceType() == ResouceType.ZB.getCode()) {
                return this.zbAccess(masterKey, resourcesId, authType, ignoreItems);
            }
            return true;
        }
        catch (Exception e) {
            LOGGER.error("\u6743\u9650\u5224\u65ad\u53d1\u751f\u9519\u8bef authType {} masterKey {} resourcesId {} ", authType, masterKey, resourcesId, e);
            throw new DataPermissionException((Throwable)e);
        }
    }

    private boolean zbAccess(DimensionCombination masterKey, String resourcesId, AuthType authType, Set<String> ignoreItems) throws Exception {
        IAccessResult accessResult;
        boolean scoped;
        IDataAccessService dataAccessService = ignoreItems != null ? this.dataAccessProvider.getZBDataAccessService(this.evaluatorParam.getTaskId(), ignoreItems) : this.dataAccessProvider.getZBDataAccessService(this.evaluatorParam.getTaskId());
        boolean bl = scoped = (this.dimensionCombination != null || this.dimensionCollection != null) && !CollectionUtils.isEmpty(this.resourcesIds);
        if (scoped) {
            IBatchZBAccessResult batchRes;
            if (this.type2ZbAccRes == null) {
                this.type2ZbAccRes = new EnumMap(AuthType.class);
            }
            if ((batchRes = this.type2ZbAccRes.get(authType)) == null) {
                DimensionCollection dimensions = this.getAccessDimensionCollection();
                batchRes = SimpleDataAccessEvaluator.getZbBatchAccessResult(dimensions, this.resourcesIds, authType, dataAccessService);
                this.type2ZbAccRes.put(authType, batchRes);
            }
            IAccessResult access = batchRes.getAccess(masterKey, resourcesId);
            return access.haveAccess();
        }
        switch (authType) {
            case VISIBLE: {
                accessResult = dataAccessService.zbVisible(masterKey, resourcesId);
                break;
            }
            case READABLE: {
                accessResult = dataAccessService.zbReadable(masterKey, resourcesId);
                break;
            }
            case WRITEABLE: {
                accessResult = dataAccessService.zbWriteable(masterKey, resourcesId);
                break;
            }
            case SYS_WRITEABLE: {
                accessResult = dataAccessService.zbSysWriteable(masterKey, resourcesId);
                break;
            }
            default: {
                throw new UnsupportedOperationException(ERROR_INFO);
            }
        }
        return accessResult.haveAccess();
    }

    private boolean formAccess(DimensionCombination masterKey, String resourcesId, AuthType authType, Set<String> ignoreItems) throws Exception {
        IAccessResult accessResult;
        boolean scoped;
        IDataAccessService dataAccessService = ignoreItems != null ? this.dataAccessProvider.getDataAccessService(this.evaluatorParam.getTaskId(), this.evaluatorParam.getFormSchemeId(), ignoreItems) : this.dataAccessProvider.getDataAccessService(this.evaluatorParam.getTaskId(), this.evaluatorParam.getFormSchemeId());
        boolean bl = scoped = (this.dimensionCombination != null || this.dimensionCollection != null) && !CollectionUtils.isEmpty(this.resourcesIds);
        if (scoped) {
            IBatchAccessResult batchRes;
            if (this.type2AccRes == null) {
                this.type2AccRes = new EnumMap(AuthType.class);
            }
            if ((batchRes = this.type2AccRes.get(authType)) == null) {
                DimensionCollection dimensions = this.getAccessDimensionCollection();
                batchRes = SimpleDataAccessEvaluator.getBatchAccessResult(dimensions, this.resourcesIds, authType, dataAccessService);
                this.type2AccRes.put(authType, batchRes);
            }
            IAccessResult access = batchRes.getAccess(masterKey, resourcesId);
            return access.haveAccess();
        }
        switch (authType) {
            case VISIBLE: {
                accessResult = dataAccessService.visible(masterKey, resourcesId);
                break;
            }
            case READABLE: {
                accessResult = dataAccessService.readable(masterKey, resourcesId);
                break;
            }
            case WRITEABLE: {
                accessResult = dataAccessService.writeable(masterKey, resourcesId);
                break;
            }
            case SYS_WRITEABLE: {
                accessResult = dataAccessService.sysWriteable(masterKey, resourcesId);
                break;
            }
            default: {
                throw new UnsupportedOperationException(ERROR_INFO);
            }
        }
        return accessResult.haveAccess();
    }

    private DimensionCollection getAccessDimensionCollection() {
        if (this.dimensionCollection == null) {
            DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
            for (FixedDimensionValue fixedDimensionValue : Objects.requireNonNull(this.dimensionCombination)) {
                builder.setEntityValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), new Object[]{fixedDimensionValue.getValue()});
            }
            return builder.getCollection();
        }
        return this.dimensionCollection;
    }

    public DataPermission haveAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType) throws DataPermissionException {
        return this.haveAccess(collection, resourcesIds, authType, null);
    }

    public DataPermission haveAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        boolean isForm;
        DataPermission dataPermission = new DataPermission();
        dataPermission.setResourceIds(resourcesIds);
        ArrayList<DataPermissionResource> accessResources = new ArrayList<DataPermissionResource>();
        ArrayList<DataPermissionResource> noAccessResources = new ArrayList<DataPermissionResource>();
        IBatchAccessResult batchAccessResult = null;
        IBatchZBAccessResult batchZbAccessResult = null;
        boolean bl = isForm = this.evaluatorParam.getResourceType() == ResouceType.FORM.getCode();
        if (isForm) {
            IDataAccessService dataAccessService = this.dataAccessProvider.getDataAccessService(this.evaluatorParam.getTaskId(), this.evaluatorParam.getFormSchemeId(), ignoreItems);
            batchAccessResult = SimpleDataAccessEvaluator.getBatchAccessResult(collection, resourcesIds, authType, dataAccessService);
        } else {
            ArrayList<String> taskKeys = new ArrayList<String>();
            if (StringUtils.hasLength(this.evaluatorParam.getTaskId())) {
                taskKeys.add(this.evaluatorParam.getTaskId());
            }
            IDataAccessService zbDataAccessService = this.dataAccessProvider.getZBDataAccessService(taskKeys, ignoreItems);
            switch (authType) {
                case VISIBLE: {
                    batchZbAccessResult = zbDataAccessService.getZBVisitAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case READABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBReadAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case WRITEABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBWriteAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case SYS_WRITEABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBSysWriteAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                default: {
                    throw new UnsupportedOperationException(ERROR_INFO);
                }
            }
        }
        try {
            for (DimensionCombination dimensionCombination : collection.getDimensionCombinations()) {
                for (String resourcesId : resourcesIds) {
                    IAccessResult resultAccess = isForm ? batchAccessResult.getAccess(dimensionCombination, resourcesId) : batchZbAccessResult.getAccess(dimensionCombination, resourcesId);
                    String message = resultAccess.getMessage();
                    boolean access = resultAccess.haveAccess();
                    DataPermissionResource resource = new DataPermissionResource();
                    resource.setDimensionCombination(dimensionCombination);
                    resource.setResourceId(resourcesId);
                    if (access) {
                        accessResources.add(resource);
                        continue;
                    }
                    resource.setMessage(message);
                    if (!isForm) continue;
                    noAccessResources.add(resource);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("\u6743\u9650\u5224\u65ad\u53d1\u751f\u9519\u8bef authType {} masterKey {} resourcesId {} ", authType, collection, resourcesIds, e);
            throw new DataPermissionException((Throwable)e);
        }
        dataPermission.setAccessResources(accessResources);
        dataPermission.setUnAccessResources(noAccessResources);
        return dataPermission;
    }

    public MergeDataPermission mergeAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType) throws DataPermissionException {
        return this.mergeAccess(collection, resourcesIds, authType, null);
    }

    public MergeDataPermission mergeAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        boolean isForm;
        MergeDataPermission dataPermission = new MergeDataPermission();
        dataPermission.setResourceIds(new ArrayList<String>(resourcesIds));
        IBatchAccessResult batchAccessResult = null;
        IBatchZBAccessResult batchZbAccessResult = null;
        boolean bl = isForm = this.evaluatorParam.getResourceType() == ResouceType.FORM.getCode();
        if (isForm) {
            IDataAccessService dataAccessService = this.dataAccessProvider.getDataAccessService(this.evaluatorParam.getTaskId(), this.evaluatorParam.getFormSchemeId(), ignoreItems);
            batchAccessResult = SimpleDataAccessEvaluator.getBatchAccessResult(collection, resourcesIds, authType, dataAccessService);
        } else {
            ArrayList<String> taskKeys = new ArrayList<String>();
            if (StringUtils.hasLength(this.evaluatorParam.getTaskId())) {
                taskKeys.add(this.evaluatorParam.getTaskId());
            }
            IDataAccessService zbDataAccessService = this.dataAccessProvider.getZBDataAccessService(taskKeys, ignoreItems);
            switch (authType) {
                case VISIBLE: {
                    batchZbAccessResult = zbDataAccessService.getZBVisitAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case READABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBReadAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case WRITEABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBWriteAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                case SYS_WRITEABLE: {
                    batchZbAccessResult = zbDataAccessService.getZBSysWriteAccess(collection, new ArrayList<String>(resourcesIds));
                    break;
                }
                default: {
                    throw new UnsupportedOperationException(ERROR_INFO);
                }
            }
        }
        List combinations = collection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(combinations)) {
            return dataPermission;
        }
        DimensionCombination tmp = (DimensionCombination)combinations.get(0);
        ENameSet names = tmp.toDimensionValueSet().getDimensionSet().getDimensions();
        try {
            String dwDimName = tmp.getDWDimensionValue().getName();
            UnitDimensionMerger unitDimensionMerger = new UnitDimensionMerger(dataPermission.getResourceIds(), names, dwDimName);
            UnitDimensionMerger unitDimensionUnAccessMerger = new UnitDimensionMerger(dataPermission.getResourceIds(), names, dwDimName);
            for (DimensionCombination dimensionCombination : combinations) {
                ArrayList<String> accessResourceIds = new ArrayList<String>();
                ArrayList<String> unAccessResourceIds = new ArrayList<String>();
                for (String resourcesId : resourcesIds) {
                    IAccessResult resultAccess = isForm ? batchAccessResult.getAccess(dimensionCombination, resourcesId) : batchZbAccessResult.getAccess(dimensionCombination, resourcesId);
                    if (resultAccess.haveAccess()) {
                        accessResourceIds.add(resourcesId);
                        continue;
                    }
                    unAccessResourceIds.add(resourcesId);
                }
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                unitDimensionMerger.addUnitDimension(dimensionValueSet, accessResourceIds);
                unitDimensionUnAccessMerger.addUnitDimension(dimensionValueSet, unAccessResourceIds);
            }
            names.remove(dwDimName);
            List<UnitPermission> unitPermissions = this.getUnitPermissions(unitDimensionMerger.getDimForms(), dwDimName, names);
            dataPermission.setAccessResources(unitPermissions);
            List<UnitPermission> unitUnAccPermissions = this.getUnitPermissions(unitDimensionUnAccessMerger.getDimForms(), dwDimName, names);
            dataPermission.setUnAccessResources(unitUnAccPermissions);
        }
        catch (Exception e) {
            LOGGER.error("\u6743\u9650\u5224\u65ad\u53d1\u751f\u9519\u8bef authType {} masterKey {} resourcesId {} ", authType, collection, resourcesIds, e);
            throw new DataPermissionException((Throwable)e);
        }
        return dataPermission;
    }

    private List<UnitPermission> getUnitPermissions(List<DimResources> dimForms, String dwDimName, ENameSet names) {
        ArrayList<UnitPermission> unitPermissions = new ArrayList<UnitPermission>();
        for (DimResources dimForm : dimForms) {
            UnitPermission unitPermission = new UnitPermission();
            List forms = dimForm.getForms();
            unitPermission.setResourceIds(forms);
            List mergeValues = dimForm.getMergeValues();
            HashMap<String, DimensionValue> masterKeys = new HashMap<String, DimensionValue>();
            DimensionValue dwValue = new DimensionValue();
            dwValue.setName(dwDimName);
            dwValue.setValue(String.join((CharSequence)";", mergeValues));
            masterKeys.put(dwDimName, dwValue);
            List otherValues = dimForm.getOtherValues();
            for (int i = 0; i < names.size(); ++i) {
                DimensionValue otherValue = new DimensionValue();
                otherValue.setName(names.get(i));
                otherValue.setValue((String)otherValues.get(i));
                masterKeys.put(names.get(i), otherValue);
            }
            unitPermission.setMasterKey(masterKeys);
            unitPermissions.add(unitPermission);
        }
        return unitPermissions;
    }

    private static IBatchAccessResult getBatchAccessResult(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, IDataAccessService dataAccessService) {
        IBatchAccessResult batchAccessResult;
        switch (authType) {
            case VISIBLE: {
                batchAccessResult = dataAccessService.getVisitAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case READABLE: {
                batchAccessResult = dataAccessService.getReadAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case WRITEABLE: {
                batchAccessResult = dataAccessService.getWriteAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case SYS_WRITEABLE: {
                batchAccessResult = dataAccessService.getSysWriteAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            default: {
                throw new UnsupportedOperationException(ERROR_INFO);
            }
        }
        return batchAccessResult;
    }

    private static IBatchZBAccessResult getZbBatchAccessResult(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, IDataAccessService dataAccessService) {
        IBatchZBAccessResult batchAccessResult;
        switch (authType) {
            case VISIBLE: {
                batchAccessResult = dataAccessService.getZBVisitAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case READABLE: {
                batchAccessResult = dataAccessService.getZBReadAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case WRITEABLE: {
                batchAccessResult = dataAccessService.getZBWriteAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            case SYS_WRITEABLE: {
                batchAccessResult = dataAccessService.getZBSysWriteAccess(collection, new ArrayList<String>(resourcesIds));
                break;
            }
            default: {
                throw new UnsupportedOperationException(ERROR_INFO);
            }
        }
        return batchAccessResult;
    }
}

