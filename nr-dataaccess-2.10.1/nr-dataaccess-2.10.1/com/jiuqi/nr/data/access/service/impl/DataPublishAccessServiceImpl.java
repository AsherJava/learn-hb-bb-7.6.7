/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.data.access.api.IStateDataPublishService;
import com.jiuqi.nr.data.access.api.param.PublishParam;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.common.Const;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataPublishAccessServiceImpl
implements IDataAccessItemService,
IDataAccessExtraResultService<Boolean> {
    @Autowired
    private IStateDataPublishService dataPublishService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewController entityCotroller;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccessCode canAccess = new AccessCode(this.name());

    @Override
    public String name() {
        return "dataPublish";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        Assert.notNull((Object)taskKey, (String)"taskKey is must not be null!");
        return this.dataPublishService.isEnableDataPublis(taskKey);
    }

    @Override
    public AccessLevel getLevel() {
        return AccessLevel.FORM;
    }

    @Override
    public List<String> getCodeList() {
        return Const.DataPublishStatus.getCodeLists();
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        boolean isAccess;
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        Assert.notNull((Object)formKey, (String)"formKey is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(Arrays.asList(formKey));
        publishParam.setFormSchemeKey(formSchemeKey);
        DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
        publishParam.setMasterKey(dimCollection);
        Boolean isPublish = this.dataPublishService.isDataPublished(publishParam);
        if (isPublish.booleanValue()) {
            return accessCode;
        }
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entiview = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        String dimName = null;
        try {
            dimName = this.entityCotroller.getDimensionNameByViewKey(entiview.getEntityId());
        }
        catch (JQException e) {
            this.logger.error("\u83b7\u53d6\u4e3b\u4f53\u7ef4\u5ea6\u5f02\u5e38!", e);
        }
        DimensionValueSet dimensionValueSet = masterKey.toDimensionValueSet();
        String dimValue = dimensionValueSet.getValue(dimName).toString();
        Object value = dimensionValueSet.getValue("DATATIME");
        Date periodValue = null;
        if (value != null) {
            GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)value.toString());
            periodValue = gregorianCalendar.getTime();
        }
        if (!(isAccess = this.isReadUnpublishAccessFormKeys(formKey, entiview, dimValue, periodValue))) {
            accessCode = new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_READ.getCode());
        }
        return accessCode;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        boolean isAccess;
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        Assert.notNull((Object)formKey, (String)"formKey is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(Arrays.asList(formKey));
        publishParam.setFormSchemeKey(formSchemeKey);
        DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
        publishParam.setMasterKey(dimCollection);
        Boolean isPublish = this.dataPublishService.isDataPublished(publishParam);
        if (isPublish.booleanValue()) {
            accessCode = new AccessCode(this.name(), Const.DataPublishStatus.PUBLISH_WRITE.getCode());
            return accessCode;
        }
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entiview = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        String dimName = null;
        try {
            dimName = this.entityCotroller.getDimensionNameByViewKey(entiview.getEntityId());
        }
        catch (JQException e) {
            this.logger.error("\u83b7\u53d6\u4e3b\u4f53\u7ef4\u5ea6\u5f02\u5e38!", e);
        }
        DimensionValueSet dimensionValueSet = masterKey.toDimensionValueSet();
        String dimValue = dimensionValueSet.getValue(dimName).toString();
        Object value = dimensionValueSet.getValue("DATATIME");
        Date periodValue = null;
        if (value != null) {
            GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)value.toString());
            periodValue = gregorianCalendar.getTime();
        }
        if (!(isAccess = this.isReadUnpublishAccessFormKeys(formKey, entiview, dimValue, periodValue))) {
            accessCode = new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_WRITE.getCode());
        }
        return accessCode;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> Const.DataPublishStatus.getMsgByCode(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        Assert.notNull(formKeys, (String)"formKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        Assert.notNull(formKeys, (String)"formKeys is must not be null!");
        FormBatchAccessCache dataPublishBatchAccess = new FormBatchAccessCache(this.name(), formSchemeKey);
        this.initCache(dataPublishBatchAccess, formSchemeKey, masterKeys, formKeys, Const.DataPublishStatus.DEFAULT.getCode(), Const.DataPublishStatus.UNAUTH_READ.getCode());
        return dataPublishBatchAccess;
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        Assert.notNull(formKeys, (String)"formKeys is must not be null!");
        FormBatchAccessCache dataPublishBatchAccess = new FormBatchAccessCache(this.name(), formSchemeKey);
        this.initCache(dataPublishBatchAccess, formSchemeKey, masterKeys, formKeys, Const.DataPublishStatus.PUBLISH_WRITE.getCode(), Const.DataPublishStatus.UNAUTH_WRITE.getCode());
        return dataPublishBatchAccess;
    }

    @Override
    public Optional<Boolean> getExtraResult(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(Arrays.asList(formKey));
        publishParam.setFormSchemeKey(formSchemeKey);
        DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
        publishParam.setMasterKey(dimCollection);
        Boolean isPublish = this.dataPublishService.isDataPublished(publishParam);
        return Optional.ofNullable(isPublish);
    }

    private void initCache(FormBatchAccessCache dataPublishBatchAccess, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys, String unPubMsg, String unAuthMsg) {
        PublishParam publishParam = new PublishParam();
        publishParam.setFormSchemeKey(formSchemeKey);
        publishParam.setMasterKey(masterKeys);
        publishParam.setFormKeys(formKeys);
        List<DataPublishBatchReadWriteResult> batchResult = this.dataPublishService.getBatchResult(publishParam);
        Map<DimensionValueSet, Map<String, String>> cacheMap = dataPublishBatchAccess.getCacheMap();
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entiview = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        String dimName = null;
        try {
            dimName = this.entityCotroller.getDimensionNameByViewKey(entiview.getEntityId());
        }
        catch (JQException e) {
            this.logger.error("\u83b7\u53d6\u4e3b\u4f53\u7ef4\u5ea6\u5f02\u5e38!", e);
        }
        DimensionValueSet valueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeys);
        Object pValue = valueSet.getValue("DATATIME");
        Date periodValue = null;
        if (pValue != null) {
            GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)pValue.toString());
            periodValue = gregorianCalendar.getTime();
        }
        List<String> dwValues = new ArrayList();
        Object dwValueObject = valueSet.getValue(dimName);
        if (dwValueObject instanceof List) {
            dwValues = (List)dwValueObject;
        } else if (dwValueObject instanceof String) {
            dwValues.add((String)dwValueObject);
        }
        HashMap<String, List<String>> accessInfo = new HashMap<String, List<String>>();
        for (String dw : dwValues) {
            List<String> accessFormKeys = this.getReadUnpublishAccessFormKeys(formKeys, entiview, dw, periodValue);
            accessInfo.put(dw, accessFormKeys);
        }
        for (DataPublishBatchReadWriteResult batchReadWriteResult : batchResult) {
            boolean isPublish;
            DimensionValueSet dimensionSet = batchReadWriteResult.getDimensionSet();
            DimensionValueSet dimensionCacheKey = DimensionValueSetUtil.filterDimensionValueSet(dimensionSet, "DP_FORMKEY");
            if (!cacheMap.containsKey(dimensionCacheKey)) {
                cacheMap.put(dimensionCacheKey, new HashMap());
            }
            if (isPublish = batchReadWriteResult.isPublished()) {
                cacheMap.get(dimensionCacheKey).put(batchReadWriteResult.getFormKey(), unPubMsg);
                continue;
            }
            String formKey = batchReadWriteResult.getFormKey();
            String dwValue = dimensionSet.getValue(dimName).toString();
            List accessFormKeys = (List)accessInfo.get(dwValue);
            if (!CollectionUtils.isEmpty(accessFormKeys) && accessFormKeys.contains(formKey)) continue;
            cacheMap.get(dimensionCacheKey).put(batchReadWriteResult.getFormKey(), unAuthMsg);
        }
    }

    private List<String> getReadUnpublishAccessFormKeys(List<String> formKeys, EntityViewDefine entityViewDefine, String dimensionValue, Date periodValue) {
        ArrayList<String> accessFormKeys = new ArrayList<String>();
        boolean canReadUnPublish = false;
        try {
            if (periodValue == null) {
                periodValue = Consts.DATE_VERSION_MAX_VALUE;
            }
            canReadUnPublish = this.entityAuthorityService.canReadUnPublishEntity(entityViewDefine.getEntityId(), dimensionValue, periodValue, periodValue);
        }
        catch (UnauthorizedEntityException e) {
            this.logger.error(e.getMessage(), e);
            throw new AccessException(e);
        }
        if (canReadUnPublish) {
            DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
            for (String formKey : formKeys) {
                if (!authorityProvider.canReadUnPublish(formKey, dimensionValue, entityViewDefine.getEntityId())) continue;
                accessFormKeys.add(formKey);
            }
        }
        return accessFormKeys;
    }

    private boolean isReadUnpublishAccessFormKeys(String formKey, EntityViewDefine entityViewDefine, String dimensionValue, Date queryVersionDate) {
        DefinitionAuthorityProvider authorityProvider;
        boolean canReadUnPublish = false;
        try {
            if (queryVersionDate == null) {
                queryVersionDate = Consts.DATE_VERSION_MAX_VALUE;
            }
            canReadUnPublish = this.entityAuthorityService.canReadUnPublishEntity(entityViewDefine.getEntityId(), dimensionValue, Consts.DATE_VERSION_MIN_VALUE, queryVersionDate);
        }
        catch (UnauthorizedEntityException e) {
            this.logger.error(e.getMessage(), e);
        }
        return canReadUnPublish && (authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class)).canReadUnPublish(formKey, dimensionValue, entityViewDefine.getEntityId());
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return this.canAccess;
        }
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccess;
        }
        boolean empty = true;
        for (String taskKey : taskKeys) {
            boolean enable = this.dataPublishService.isEnableDataPublis(taskKey);
            if (!enable) continue;
            empty = false;
            Set<IAccessForm> forms = mergeParam.getAccessFormsByTaskKey(taskKey);
            for (IAccessForm accessForm : forms) {
                PublishParam publishParam = new PublishParam();
                publishParam.setFormKeys(Collections.singletonList(accessForm.getFormKey()));
                publishParam.setFormSchemeKey(accessForm.getFormSchemeKey());
                DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
                publishParam.setMasterKey(dimCollection);
                if (!this.dataPublishService.isDataPublished(publishParam).booleanValue()) continue;
                return this.canAccess;
            }
            for (IAccessForm accessForm : forms) {
                if (!this.isCanReadUnPublishData(accessForm, masterKey)) continue;
                return this.canAccess;
            }
        }
        if (empty) {
            return this.canAccess;
        }
        return new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_READ.getCode());
    }

    private boolean isCanReadUnPublishData(IAccessForm accessForm, DimensionCombination masterKey) {
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(accessForm.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine entiview = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entiview.getEntityId());
        Object value = masterKey.getValue(dwDimensionName);
        String dwStr = String.valueOf(value);
        Object pValue = masterKey.getValue("DATATIME");
        Date periodValue = null;
        if (pValue != null) {
            GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)pValue.toString());
            periodValue = gregorianCalendar.getTime();
        }
        if (periodValue == null) {
            periodValue = Consts.DATE_VERSION_MAX_VALUE;
        }
        boolean canReadUnPublishEntity = false;
        try {
            canReadUnPublishEntity = this.entityAuthorityService.canReadUnPublishEntity(entiview.getEntityId(), dwStr, periodValue, periodValue);
        }
        catch (UnauthorizedEntityException e) {
            this.logger.error(e.getMessage(), e);
        }
        if (canReadUnPublishEntity) {
            return this.definitionAuthorityProvider.canReadUnPublish(accessForm.getFormKey(), dwStr, entiview.getEntityId());
        }
        return false;
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return this.canAccess;
        }
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccess;
        }
        DimensionCombination masterKey = mergeParam.getMasterKey();
        for (String taskKey : taskKeys) {
            boolean enable = this.dataPublishService.isEnableDataPublis(taskKey);
            if (!enable) continue;
            Set<IAccessForm> forms = mergeParam.getAccessFormsByTaskKey(taskKey);
            for (IAccessForm accessForm : forms) {
                PublishParam publishParam = new PublishParam();
                publishParam.setFormKeys(Collections.singletonList(accessForm.getFormKey()));
                publishParam.setFormSchemeKey(accessForm.getFormSchemeKey());
                DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
                publishParam.setMasterKey(dimCollection);
                if (!this.dataPublishService.isDataPublished(publishParam).booleanValue()) continue;
                return new AccessCode(this.name(), Const.DataPublishStatus.PUBLISH_WRITE.getCode());
            }
            for (IAccessForm accessForm : forms) {
                if (this.isCanReadUnPublishData(accessForm, masterKey)) continue;
                return new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_WRITE.getCode());
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
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        Set<String> taskKeys = mergeParam.getTaskKeys();
        HashMap<String, Boolean> taskEnable = new HashMap<String, Boolean>();
        for (String taskKey : taskKeys) {
            taskEnable.put(taskKey, this.dataPublishService.isEnableDataPublis(taskKey));
        }
        Map<String, Map<String, List<DataPublishBatchReadWriteResult>>> formScheme2FormRes = this.getPublishResMap(mergeParam, taskEnable);
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        block1: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet dimensionValueSet = masterKey.toDimensionValueSet();
            Set<IAccessForm> accessForms = accessFormMerge.getAccessForms();
            HashSet<IAccessForm> tempForms = new HashSet<IAccessForm>();
            HashMap<String, Set> formScheme2Form = new HashMap<String, Set>();
            for (IAccessForm accessForm : accessForms) {
                Boolean enable = (Boolean)taskEnable.get(accessForm.getTaskKey());
                if (enable == null || !enable.booleanValue()) continue;
                tempForms.add(accessForm);
                Set formSet = formScheme2Form.computeIfAbsent(accessForm.getFormSchemeKey(), k -> new HashSet());
                formSet.add(accessForm);
            }
            if (CollectionUtils.isEmpty(tempForms)) {
                codeMap.put(accessFormMerge, this.canAccess);
                continue;
            }
            HashSet<IAccessForm> unPubForm = new HashSet<IAccessForm>();
            for (Map.Entry accFormScheme : formScheme2Form.entrySet()) {
                Set formKeys = (Set)accFormScheme.getValue();
                if (CollectionUtils.isEmpty(formKeys)) continue;
                Map<String, List<DataPublishBatchReadWriteResult>> form2Res = formScheme2FormRes.get(accFormScheme.getKey());
                if (CollectionUtils.isEmpty(form2Res)) {
                    unPubForm.addAll(formKeys);
                    continue;
                }
                for (IAccessForm formKey : formKeys) {
                    List<DataPublishBatchReadWriteResult> res = form2Res.get(formKey.getFormKey());
                    if (res == null) {
                        unPubForm.add(formKey);
                        continue;
                    }
                    for (DataPublishBatchReadWriteResult re : res) {
                        boolean subsetOf = dimensionValueSet.isSubsetOf(re.getDimensionSet());
                        if (!subsetOf) continue;
                        boolean published = re.isPublished();
                        if (published) {
                            codeMap.put(accessFormMerge, this.canAccess);
                            continue block1;
                        }
                        unPubForm.add(formKey);
                    }
                }
            }
            for (IAccessForm accessForm : unPubForm) {
                if (!this.isCanReadUnPublishData(accessForm, masterKey)) continue;
                codeMap.put(accessFormMerge, this.canAccess);
                continue block1;
            }
            codeMap.put(accessFormMerge, new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_READ.getCode()));
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        HashMap<String, Boolean> taskEnable = new HashMap<String, Boolean>();
        for (String taskKey : taskKeys) {
            taskEnable.put(taskKey, this.dataPublishService.isEnableDataPublis(taskKey));
        }
        Map<String, Map<String, List<DataPublishBatchReadWriteResult>>> formScheme2FormRes = this.getPublishResMap(mergeParam, taskEnable);
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        block1: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet dimensionValueSet = masterKey.toDimensionValueSet();
            Set<IAccessForm> accessForms = accessFormMerge.getAccessForms();
            HashSet<IAccessForm> tempForms = new HashSet<IAccessForm>();
            HashMap<String, Set> formScheme2Form = new HashMap<String, Set>();
            for (IAccessForm accessForm : accessForms) {
                Boolean enable = (Boolean)taskEnable.get(accessForm.getTaskKey());
                if (enable == null || !enable.booleanValue()) continue;
                tempForms.add(accessForm);
                Set formSet = formScheme2Form.computeIfAbsent(accessForm.getFormSchemeKey(), k -> new HashSet());
                formSet.add(accessForm);
            }
            if (CollectionUtils.isEmpty(tempForms)) {
                codeMap.put(accessFormMerge, this.canAccess);
                continue;
            }
            HashSet<IAccessForm> unPubForm = new HashSet<IAccessForm>();
            for (Map.Entry accFormScheme : formScheme2Form.entrySet()) {
                Set formKeys = (Set)accFormScheme.getValue();
                if (CollectionUtils.isEmpty(formKeys)) continue;
                Map<String, List<DataPublishBatchReadWriteResult>> form2Res = formScheme2FormRes.get(accFormScheme.getKey());
                if (CollectionUtils.isEmpty(form2Res)) {
                    unPubForm.addAll(formKeys);
                    continue;
                }
                for (IAccessForm formKey : formKeys) {
                    List<DataPublishBatchReadWriteResult> res = form2Res.get(formKey.getFormKey());
                    if (CollectionUtils.isEmpty(res)) {
                        unPubForm.add(formKey);
                        continue;
                    }
                    for (DataPublishBatchReadWriteResult re : res) {
                        boolean subsetOf = dimensionValueSet.isSubsetOf(re.getDimensionSet());
                        if (!subsetOf) continue;
                        boolean published = re.isPublished();
                        if (published) {
                            codeMap.put(accessFormMerge, new AccessCode(this.name(), Const.DataPublishStatus.PUBLISH_WRITE.getCode()));
                            continue block1;
                        }
                        unPubForm.add(formKey);
                    }
                }
            }
            for (IAccessForm accessForm : unPubForm) {
                if (this.isCanReadUnPublishData(accessForm, masterKey)) continue;
                codeMap.put(accessFormMerge, new AccessCode(this.name(), Const.DataPublishStatus.UNAUTH_WRITE.getCode()));
                continue block1;
            }
            codeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    private Map<String, Map<String, List<DataPublishBatchReadWriteResult>>> getPublishResMap(IBatchAccessFormMerge mergeParam, Map<String, Boolean> taskEnable) {
        HashMap<String, Map<String, List<DataPublishBatchReadWriteResult>>> formScheme2FormRes = new HashMap<String, Map<String, List<DataPublishBatchReadWriteResult>>>();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        for (String taskKey : taskKeys) {
            Boolean enable = taskEnable.get(taskKey);
            if (enable == null || !enable.booleanValue()) continue;
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeys) {
                PublishParam publishParam = new PublishParam();
                publishParam.setFormSchemeKey(formSchemeKey);
                publishParam.setMasterKey(mergeParam.getMasterKeysByTaskKey(taskKey));
                List<DataPublishBatchReadWriteResult> batchResult = this.dataPublishService.getBatchResult(publishParam);
                Map form2Res = formScheme2FormRes.computeIfAbsent(formSchemeKey, k -> new HashMap());
                for (DataPublishBatchReadWriteResult dataPublishBatchReadWriteResult : batchResult) {
                    List list = form2Res.computeIfAbsent(dataPublishBatchReadWriteResult.getFormKey(), k -> new ArrayList());
                    list.add(dataPublishBatchReadWriteResult);
                }
            }
        }
        return formScheme2FormRes;
    }
}

