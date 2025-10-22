/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteStatusAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=15)
@Component
@Deprecated
public class DataPublishReadWriteAccessImpl
implements IReadWriteStatusAccess<Boolean>,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(DataPublishReadWriteAccessImpl.class);
    @Autowired
    private IDataPublishService dataPublishService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public String getName() {
        return "dataPublish";
    }

    @Override
    public boolean isEnable(JtableContext context) {
        String dataPubObj = this.taskOptionController.getValue(context.getTaskKey(), "DATA_PUBLISHING");
        return "1".equals(dataPubObj);
    }

    @Override
    public Boolean getStatus(ReadWriteAccessItem item, JtableContext context) {
        Boolean isDataPublished = false;
        DataPublishParam param = new DataPublishParam();
        param.setContext(context);
        try {
            isDataPublished = this.dataPublishService.isDataPublished(param);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u5355\u4f4d\u53d1\u5e03\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return isDataPublished;
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, Boolean status, JtableContext context) {
        Boolean readable = true;
        String unreadableDesc = "";
        Map map = (Map)item.getParams();
        DataPublishParam param = new DataPublishParam();
        param.setContext(context);
        param.setFormKeys((List)map.get("formKeys"));
        boolean chinese = DataEntryUtil.isChinese();
        if (status.booleanValue()) {
            readable = true;
        } else {
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dimensionValue = ((DimensionValue)context.getDimensionSet().get(targetEntityInfo.getDimensionName())).getValue();
            List<String> readUnPublishFormKeys = this.getReadUnpublishAccessFormKeys(param, targetEntityInfo.getKey(), dimensionValue);
            if (readUnPublishFormKeys.contains(context.getFormKey())) {
                readable = true;
            } else {
                readable = false;
                unreadableDesc = chinese ? "\u65e0\u6743\u9650\u7528\u6237\u4e0d\u53ef\u67e5\u770b\u672a\u53d1\u5e03\u7684\u6570\u636e" : "Unauthorized users cannot view unpublished data";
            }
        }
        return new ReadWriteAccessDesc(readable, unreadableDesc);
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, Boolean status, JtableContext context) {
        Boolean writeable = true;
        String unwriteableDesc = "";
        Map map = (Map)item.getParams();
        DataPublishParam param = new DataPublishParam();
        param.setContext(context);
        param.setFormKeys((List)map.get("formKeys"));
        boolean chinese = DataEntryUtil.isChinese();
        if (status.booleanValue()) {
            writeable = false;
            unwriteableDesc = chinese ? "\u6570\u636e\u5df2\u53d1\u5e03\u4e0d\u53ef\u7f16\u8f91" : "Data has been published and is not editable";
        } else {
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dimensionValue = ((DimensionValue)context.getDimensionSet().get(targetEntityInfo.getDimensionName())).getValue();
            List<String> formKeys = this.getReadUnpublishAccessFormKeys(param, targetEntityInfo.getKey(), dimensionValue);
            if (formKeys.contains(context.getFormKey())) {
                writeable = true;
            } else {
                writeable = false;
                unwriteableDesc = chinese ? "\u6ca1\u6709\u8bbf\u95ee\u672a\u53d1\u5e03\u6570\u636e\u6743\u9650\u4e0d\u53ef\u7f16\u8f91" : "Cannot be edited without access to unpublished data";
            }
        }
        return new ReadWriteAccessDesc(writeable, unwriteableDesc);
    }

    private List<String> getReadUnpublishAccessFormKeys(DataPublishParam param, String entityId, String dimensionValue) {
        ArrayList<String> formKeys = new ArrayList<String>();
        boolean canReadUnPublish = false;
        try {
            canReadUnPublish = this.entityAuthorityService.canReadUnPublishEntity(entityId, dimensionValue, Consts.DATE_VERSION_MIN_VALUE, Consts.DATE_VERSION_MAX_VALUE);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        if (canReadUnPublish) {
            DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
            for (String formKey : param.getFormKeys()) {
                if (!authorityProvider.canReadUnPublish(formKey)) continue;
                formKeys.add(formKey);
            }
        }
        return formKeys;
    }

    private Boolean isHaveUnPublishAccess(EntityViewData dwEntity, Map<String, DimensionValue> dimension, String formKey) {
        String dimensionValue = dimension.get(dwEntity.getDimensionName()).getValue();
        boolean canReadUnPublish = false;
        try {
            canReadUnPublish = this.entityAuthorityService.canReadUnPublishEntity(dwEntity.getKey(), dimensionValue, Consts.DATE_VERSION_MIN_VALUE, Consts.DATE_VERSION_MAX_VALUE);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        if (canReadUnPublish) {
            DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
            return authorityProvider.canReadUnPublish(formKey);
        }
        return false;
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        List<DataPublishBatchReadWriteResult> result = this.dataPublishService.getBatchResult(readWriteAccessCacheParams.getJtableContext());
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(readWriteAccessCacheParams.getJtableContext().getFormSchemeKey());
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == readWriteAccessCacheParams.getFormAccessLevel() || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == readWriteAccessCacheParams.getFormAccessLevel()) {
            FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
            HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
            batchCache.setCanAccess(false);
            batchCache.setCacheMap(cacheItems);
            if (result.size() <= 0) {
                return batchCache;
            }
            List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
            ArrayList<String> dimKeys = null;
            boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)result.get(0).getDimensionSet()), entityList);
            for (DataPublishBatchReadWriteResult item : result) {
                DimensionCacheKey cacheKey;
                HashSet<String> formKeys;
                Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, DimensionValueSetUtil.getDimensionSet((DimensionValueSet)item.getDimensionSet()), entityList);
                if (dimKeys == null) {
                    dimKeys = new ArrayList<String>(currentDimension.keySet());
                }
                if ((formKeys = (HashSet<String>)cacheItems.get(cacheKey = new DimensionCacheKey(currentDimension))) == null) {
                    formKeys = new HashSet<String>();
                    cacheItems.put(cacheKey, formKeys);
                }
                if (item.isPublished()) {
                    formKeys.add(item.getFormKey());
                    continue;
                }
                if (this.isHaveUnPublishAccess(dwEntity, currentDimension, item.getFormKey()).booleanValue()) continue;
                formKeys.add(item.getFormKey());
            }
            batchCache.setDimKeys(dimKeys);
            return batchCache;
        }
        if (Consts.FormAccessLevel.FORM_DATA_READ == readWriteAccessCacheParams.getFormAccessLevel()) {
            FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
            HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
            batchCache.setCanAccess(false);
            batchCache.setCacheMap(cacheItems);
            if (result.size() <= 0) {
                return batchCache;
            }
            List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
            boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)result.get(0).getDimensionSet()), entityList);
            ArrayList<String> dimKeys = null;
            for (DataPublishBatchReadWriteResult item : result) {
                DimensionCacheKey cacheKey;
                HashSet<String> formKeys;
                Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, DimensionValueSetUtil.getDimensionSet((DimensionValueSet)item.getDimensionSet()), entityList);
                if (dimKeys == null) {
                    dimKeys = new ArrayList<String>(currentDimension.keySet());
                }
                if ((formKeys = (HashSet<String>)cacheItems.get(cacheKey = new DimensionCacheKey(currentDimension))) == null) {
                    formKeys = new HashSet<String>();
                    cacheItems.put(cacheKey, formKeys);
                }
                if (item.isPublished() || this.isHaveUnPublishAccess(dwEntity, currentDimension, item.getFormKey()).booleanValue()) continue;
                formKeys.add(item.getFormKey());
            }
            batchCache.setDimKeys(dimKeys);
            return batchCache;
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            HashSet<String> formKeys;
            FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
            Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
            HashSet<String> hashSet = formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
            if (formKeys == null) {
                if (!this.isHaveUnPublishAccess(dwEntity, cacheKey.getDimensionSet(), formKey).booleanValue()) {
                    return new ReadWriteAccessDesc(false, "\u6570\u636e\u5df2\u53d1\u5e03\u6216\u8005\u7528\u6237\u6ca1\u6709\u53d1\u5e03\u6743\u9650\u4e0d\u53ef\u7f16\u8f91");
                }
                return new ReadWriteAccessDesc(true, "");
            }
            boolean notWriteable = formKeys.contains(formKey);
            return new ReadWriteAccessDesc(!notWriteable, notWriteable ? "\u6570\u636e\u5df2\u53d1\u5e03\u6216\u8005\u7528\u6237\u6ca1\u6709\u53d1\u5e03\u6743\u9650\u4e0d\u53ef\u7f16\u8f91" : "");
        }
        if (Consts.FormAccessLevel.FORM_DATA_READ == formAccessLevel) {
            HashSet<String> formKeys;
            FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
            Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
            HashSet<String> hashSet = formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
            if (formKeys == null) {
                if (!this.isHaveUnPublishAccess(dwEntity, cacheKey.getDimensionSet(), formKey).booleanValue()) {
                    return new ReadWriteAccessDesc(false, "\u6570\u636e\u672a\u53d1\u5e03\u4e14\u7528\u6237\u6ca1\u6709\u53d1\u5e03\u6743\u9650\u4e0d\u53ef\u67e5\u770b");
                }
                return new ReadWriteAccessDesc(true, "");
            }
            boolean notReadable = formKeys.contains(formKey);
            return new ReadWriteAccessDesc(!notReadable, notReadable ? "\u6570\u636e\u672a\u53d1\u5e03\u4e14\u7528\u6237\u6ca1\u6709\u53d1\u5e03\u6743\u9650\u4e0d\u53ef\u67e5\u770b" : "");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
    }

    @Override
    public boolean canUseReadTempTable(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        boolean isEnabled = this.isEnable(jtableContext);
        return !isEnabled;
    }
}

