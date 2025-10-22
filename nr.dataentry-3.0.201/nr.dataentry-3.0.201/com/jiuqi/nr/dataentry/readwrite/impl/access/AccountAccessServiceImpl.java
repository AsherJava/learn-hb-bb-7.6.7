/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.BatchMergeAccess
 *  com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.param.FormBatchAccessCache
 *  com.jiuqi.nr.data.access.param.IAccessForm
 *  com.jiuqi.nr.data.access.param.IAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.IBatchAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IBatchMergeAccess
 *  com.jiuqi.nr.data.access.service.IDataAccessItemService
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.readwrite.bean.BatchAccountDataReadWriteResult;
import com.jiuqi.nr.dataentry.service.IAccountDataReadOnlyService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Arrays;
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
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class AccountAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private IAccountDataReadOnlyService accountDataReadOnlyService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IRunTimeViewController controller;
    private final Logger logger = LoggerFactory.getLogger(AccountAccessServiceImpl.class);
    public static final String ACCT_READ_MSG = "\u53f0\u8d26\u5386\u53f2\u6570\u636e\u4e0d\u53ef\u7f16\u8f91";
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(ACCT_READ_MSG);
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());

    public String name() {
        return "accountData";
    }

    public int getOrder() {
        return 12;
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    public AccessLevel getLevel() {
        return AccessLevel.FORM;
    }

    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKeys is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        try {
            DimensionValueSet queryKey;
            Boolean readOnly;
            FormDefine formDefine = this.controller.queryFormById(formKey);
            if (formDefine != null && FormType.FORM_TYPE_ACCOUNT.equals((Object)formDefine.getFormType()) && (readOnly = this.accountDataReadOnlyService.readOnly(queryKey = new DimensionValueSet(masterKey.toDimensionValueSet()), formKey)).booleanValue()) {
                accessCode = new AccessCode(this.name(), "2");
                return accessCode;
            }
            return accessCode;
        }
        catch (Exception e) {
            this.logger.error("\u53f0\u8d26\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38\uff01", e);
            return accessCode;
        }
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        FormBatchAccessCache batchCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        Map cacheMap = batchCache.getCacheMap();
        DimensionValueSet masterKeySet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKeys);
        List<BatchAccountDataReadWriteResult> res = this.accountDataReadOnlyService.batchReadOnlyAccResult(masterKeySet, formKeys);
        Map<String, String> formMap = null;
        EntityDimData unit = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        List dimensionList = masterKeys.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionList) {
            DimensionValueSet dimKey = dimensionCombination.toDimensionValueSet();
            String dw = String.valueOf(dimKey.getValue(unit.getDimensionName()));
            for (BatchAccountDataReadWriteResult re : res) {
                String resUnit = String.valueOf(re.getMasterKey().getValue(unit.getDimensionName()));
                if (!dw.equals(resUnit)) continue;
                if (cacheMap.containsKey(dimKey)) {
                    formMap = (Map)cacheMap.get(dimKey);
                } else {
                    formMap = new HashMap();
                    cacheMap.put(dimKey, formMap);
                }
                formMap.put(re.getFormKey(), "2");
            }
        }
        return batchCache;
    }

    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        Set accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return this.canAccess;
        }
        DimensionValueSet master = mergeParam.getMasterKey().toDimensionValueSet();
        List<String> forms = accessForms.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
        List<BatchAccountDataReadWriteResult> res = this.accountDataReadOnlyService.batchReadOnlyAccResult(master, forms);
        for (BatchAccountDataReadWriteResult re : res) {
            if (!master.isSubsetOf(re.getMasterKey())) continue;
            return new AccessCode(this.name(), "2");
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
        Set taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccessBatchMergeAccess;
        }
        List accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<String, Set> form2Dim = new HashMap<String, Set>();
        for (String taskKey : taskKeys) {
            Set formsByTaskKey = mergeParam.getAccessFormsByTaskKey(taskKey);
            if (CollectionUtils.isEmpty(formsByTaskKey)) continue;
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            DimensionValueSet masterKeySet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKeysByTaskKey);
            List<String> formIds = formsByTaskKey.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
            List<BatchAccountDataReadWriteResult> res = this.accountDataReadOnlyService.batchReadOnlyAccResult(masterKeySet, formIds);
            for (BatchAccountDataReadWriteResult re : res) {
                Set dimensionValueSets = form2Dim.computeIfAbsent(re.getFormKey(), l -> new HashSet());
                dimensionValueSets.add(re.getMasterKey());
            }
        }
        AccessCode noAccess = new AccessCode(this.name(), "2");
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        block2: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet set = masterKey.toDimensionValueSet();
            Set accTasks = accessFormMerge.getTaskKeys();
            for (String accTask : accTasks) {
                Set formsByTaskKey = accessFormMerge.getAccessFormsByTaskKey(accTask);
                for (IAccessForm iAccessForm : formsByTaskKey) {
                    Set dimensionValueSets = (Set)form2Dim.get(iAccessForm.getFormKey());
                    if (dimensionValueSets == null || !dimensionValueSets.contains(set)) continue;
                    codeMap.put(accessFormMerge, noAccess);
                    continue block2;
                }
            }
            codeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }
}

