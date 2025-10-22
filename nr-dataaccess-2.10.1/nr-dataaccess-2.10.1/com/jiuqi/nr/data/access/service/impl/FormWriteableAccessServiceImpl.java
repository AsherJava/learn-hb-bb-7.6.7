/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class FormWriteableAccessServiceImpl
implements IDataAccessItemService {
    protected DefinitionAuthorityProvider authorityProvider;
    @Autowired
    IRunTimeViewController runtimeView;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private final AccessCode canAccess = new AccessCode(this.name());
    protected Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u7528\u6237\u5bf9\u62a5\u8868\u6ca1\u6709\u7f16\u8f91\u6743\u9650");
    private static final String NAME = "FORM_WRITE_ABLE";

    public FormWriteableAccessServiceImpl(DefinitionAuthorityProvider authorityProvider) {
        this.authorityProvider = authorityProvider;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int getOrder() {
        return 6;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.readable(formSchemeKey, masterKey, formKey);
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formKey, "formKey is must not be null!");
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entityId);
        String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
        boolean canWrite = this.authorityProvider.canReadForm(formKey, dwCode, entityId);
        AccessCode accessCode = new AccessCode(this.name());
        if (!canWrite) {
            accessCode = new AccessCode(this.name(), "2");
        }
        return accessCode;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formKey, "formKey is must not be null!");
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entityId);
        String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
        boolean canWrite = this.authorityProvider.canWriteForm(formKey, dwCode, entityId);
        AccessCode accessCode = new AccessCode(this.name());
        if (!canWrite) {
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
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        return this.getBatchReadable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        Map<DimensionValueSet, Map<String, String>> cacheMap = formBatchAccessCache.getCacheMap();
        List masterKeysSplit = masterKeys.getDimensionCombinations();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entityId);
        List<String> orgCodes = this.getDimKeySet(masterKeys, dwDimensionName);
        Map batchCanRead = this.authorityProvider.batchQueryCanReadFormWithDuty(formKeys, orgCodes, entityId);
        if (CollectionUtils.isEmpty(batchCanRead)) {
            return formBatchAccessCache;
        }
        for (DimensionCombination dimensionCombination : masterKeysSplit) {
            String dwCode = String.valueOf(dimensionCombination.getValue(dwDimensionName));
            Map formAccMap = (Map)batchCanRead.get(dwCode);
            if (CollectionUtils.isEmpty(formAccMap)) continue;
            HashMap<String, String> readMap = new HashMap<String, String>();
            for (String formKey : formKeys) {
                boolean canRead = (Boolean)formAccMap.get(formKey);
                if (canRead) continue;
                readMap.put(formKey, "2");
            }
            cacheMap.put(dimensionCombination.toDimensionValueSet(), readMap);
        }
        return formBatchAccessCache;
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        Assert.isTrue(!CollectionUtils.isEmpty(formKeys), "formKeys is must not be empty!");
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        Map<DimensionValueSet, Map<String, String>> cacheMap = formBatchAccessCache.getCacheMap();
        List masterKeysSplit = masterKeys.getDimensionCombinations();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entityId);
        List<String> orgCodes = this.getDimKeySet(masterKeys, dwDimensionName);
        Map batchCanWrite = this.authorityProvider.batchQueryCanWriteFormWithDuty(formKeys, orgCodes, entityId);
        if (CollectionUtils.isEmpty(batchCanWrite)) {
            return formBatchAccessCache;
        }
        for (DimensionCombination dimensionCombination : masterKeysSplit) {
            HashMap<String, String> writeMap = new HashMap<String, String>();
            String dwCode = String.valueOf(dimensionCombination.getValue(dwDimensionName));
            Map formAccMap = (Map)batchCanWrite.get(dwCode);
            if (CollectionUtils.isEmpty(formAccMap)) continue;
            for (String formKey : formKeys) {
                boolean canWrite = (Boolean)formAccMap.get(formKey);
                if (canWrite) continue;
                writeMap.put(formKey, "2");
            }
            cacheMap.put(dimensionCombination.toDimensionValueSet(), writeMap);
        }
        return formBatchAccessCache;
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.readable(mergeParam);
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        AccessCode accessCode = new AccessCode(this.name());
        if (mergeParam == null) {
            return accessCode;
        }
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return accessCode;
        }
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        HashMap nameMap = new HashMap();
        HashMap entityIDMap = new HashMap();
        formSchemeKeys.forEach(a -> {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(a);
            entityIDMap.put(a, formScheme.getDw());
            String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
            nameMap.put(a, dwDimensionName);
        });
        DimensionCombination masterKey = mergeParam.getMasterKey();
        for (IAccessForm accessForm : accessForms) {
            String entityId = (String)entityIDMap.get(accessForm.getFormSchemeKey());
            String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
            String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
            boolean canReadForm = this.authorityProvider.canReadForm(accessForm.getFormKey(), dwCode, entityId);
            if (!canReadForm) continue;
            return accessCode;
        }
        return new AccessCode(this.name(), "2");
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        AccessCode accessCode = new AccessCode(this.name());
        if (mergeParam == null) {
            return accessCode;
        }
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return accessCode;
        }
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        HashMap nameMap = new HashMap();
        HashMap entityIDMap = new HashMap();
        formSchemeKeys.forEach(a -> {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(a);
            entityIDMap.put(a, formScheme.getDw());
            String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
            nameMap.put(a, dwDimensionName);
        });
        DimensionCombination masterKey = mergeParam.getMasterKey();
        for (IAccessForm accessForm : accessForms) {
            String entityId = (String)entityIDMap.get(accessForm.getFormSchemeKey());
            String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
            String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
            boolean canReadForm = this.authorityProvider.canWriteForm(accessForm.getFormKey(), dwCode, entityId);
            if (canReadForm) continue;
            return new AccessCode(this.name(), "2");
        }
        return accessCode;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        Assert.notNull((Object)mergeParam, "mergeParam is must not be null!");
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        HashMap nameMap = new HashMap();
        HashMap entityIDMap = new HashMap();
        formSchemeKeys.forEach(a -> {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(a);
            entityIDMap.put(a, formScheme.getDw());
            String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
            nameMap.put(a, dwDimensionName);
        });
        HashMap formKeys = new HashMap();
        HashMap orgCodes = new HashMap();
        for (IAccessFormMerge iAccessFormMerge : accessFormMerges) {
            for (IAccessForm accessForm : iAccessFormMerge.getAccessForms()) {
                String formSchemeKey = accessForm.getFormSchemeKey();
                if (Objects.isNull(formKeys.get(formSchemeKey))) {
                    formKeys.put(formSchemeKey, new HashSet());
                }
                ((Set)formKeys.get(formSchemeKey)).add(accessForm.getFormKey());
                if (Objects.isNull(orgCodes.get(formSchemeKey))) {
                    orgCodes.put(formSchemeKey, new HashSet());
                }
                DimensionCombination masterKey = iAccessFormMerge.getMasterKey();
                String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
                String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
                ((Set)orgCodes.get(formSchemeKey)).add(dwCode);
            }
        }
        HashMap<String, Map> batchCanReads = new HashMap<String, Map>();
        for (String formSchemeKey : formSchemeKeys) {
            Map batchCanRead = this.authorityProvider.batchQueryCanReadFormWithDuty(new ArrayList((Collection)formKeys.get(formSchemeKey)), new ArrayList((Collection)orgCodes.get(formSchemeKey)), (String)entityIDMap.get(formSchemeKey));
            batchCanReads.put(formSchemeKey, batchCanRead);
        }
        HashMap<IAccessFormMerge, AccessCode> hashMap = new HashMap<IAccessFormMerge, AccessCode>();
        AccessCode noAccess = new AccessCode(this.name(), "2");
        block3: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            Set<IAccessForm> accessForms = accessFormMerge.getAccessForms();
            if (CollectionUtils.isEmpty(accessForms)) {
                hashMap.put(accessFormMerge, this.canAccess);
                continue;
            }
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            for (IAccessForm accessForm : accessForms) {
                String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
                String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
                Map dwForms = (Map)((Map)batchCanReads.get(accessForm.getFormSchemeKey())).get(dwCode);
                if (!Objects.nonNull(dwForms) || !Objects.nonNull(dwForms.get(accessForm.getFormKey())) || !((Boolean)dwForms.get(accessForm.getFormKey())).booleanValue()) continue;
                hashMap.put(accessFormMerge, this.canAccess);
                continue block3;
            }
            hashMap.put(accessFormMerge, noAccess);
        }
        if (hashMap.isEmpty()) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        return new ReadBatchMergeAccess(hashMap);
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchVisible(mergeParam);
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        Assert.notNull((Object)mergeParam, "mergeParam is must not be null!");
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        HashMap nameMap = new HashMap();
        HashMap entityIDMap = new HashMap();
        formSchemeKeys.forEach(a -> {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(a);
            entityIDMap.put(a, formScheme.getDw());
            String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
            nameMap.put(a, dwDimensionName);
        });
        HashMap formKeys = new HashMap();
        HashMap orgCodes = new HashMap();
        for (IAccessFormMerge iAccessFormMerge : accessFormMerges) {
            for (IAccessForm accessForm : iAccessFormMerge.getAccessForms()) {
                String formSchemeKey = accessForm.getFormSchemeKey();
                if (Objects.isNull(formKeys.get(formSchemeKey))) {
                    formKeys.put(formSchemeKey, new HashSet());
                }
                ((Set)formKeys.get(formSchemeKey)).add(accessForm.getFormKey());
                if (Objects.isNull(orgCodes.get(formSchemeKey))) {
                    orgCodes.put(formSchemeKey, new HashSet());
                }
                DimensionCombination masterKey = iAccessFormMerge.getMasterKey();
                String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
                String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
                ((Set)orgCodes.get(formSchemeKey)).add(dwCode);
            }
        }
        HashMap<String, Map> batchCanWrites = new HashMap<String, Map>();
        for (String formSchemeKey : formSchemeKeys) {
            Map batchCanWrite = this.authorityProvider.batchQueryCanWriteFormWithDuty(new ArrayList((Collection)formKeys.get(formSchemeKey)), new ArrayList((Collection)orgCodes.get(formSchemeKey)), (String)entityIDMap.get(formSchemeKey));
            batchCanWrites.put(formSchemeKey, batchCanWrite);
        }
        HashMap<IAccessFormMerge, AccessCode> hashMap = new HashMap<IAccessFormMerge, AccessCode>();
        AccessCode noAccess = new AccessCode(this.name(), "2");
        block3: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            Set<IAccessForm> accessForms = accessFormMerge.getAccessForms();
            if (CollectionUtils.isEmpty(accessForms)) {
                hashMap.put(accessFormMerge, this.canAccess);
                continue;
            }
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            for (IAccessForm accessForm : accessForms) {
                String dwDimensionName = (String)nameMap.get(accessForm.getFormSchemeKey());
                String dwCode = String.valueOf(masterKey.getValue(dwDimensionName));
                Map dwForms = (Map)((Map)batchCanWrites.get(accessForm.getFormSchemeKey())).get(dwCode);
                if (!Objects.isNull(dwForms) && !Objects.isNull(dwForms.get(accessForm.getFormKey())) && ((Boolean)dwForms.get(accessForm.getFormKey())).booleanValue()) continue;
                hashMap.put(accessFormMerge, noAccess);
                continue block3;
            }
            hashMap.put(accessFormMerge, this.canAccess);
        }
        if (hashMap.isEmpty()) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        return new WriteBatchMergeAccess(hashMap);
    }

    private List<String> getDimKeySet(DimensionCollection dimensionCollection, String dimensionName) {
        HashSet dimKeySet = new HashSet();
        dimensionCollection.getDimensionCombinations().forEach(o -> {
            Object value = o.getValue(dimensionName);
            if (value instanceof String) {
                dimKeySet.add((String)value);
            } else if (value instanceof List) {
                dimKeySet.addAll((List)value);
            }
        });
        return new ArrayList<String>(dimKeySet);
    }

    private static class WriteBatchMergeAccess
    implements IBatchMergeAccess {
        private final Map<IAccessFormMerge, AccessCode> canWrites;

        public WriteBatchMergeAccess(Map<IAccessFormMerge, AccessCode> canWrites) {
            this.canWrites = canWrites;
        }

        @Override
        public AccessCode getAccessCode(IAccessFormMerge merge) {
            AccessCode accessCode = this.canWrites.get(merge);
            if (Objects.isNull(accessCode)) {
                return new AccessCode(FormWriteableAccessServiceImpl.NAME, "2");
            }
            return this.canWrites.get(merge);
        }
    }

    private static class ReadBatchMergeAccess
    implements IBatchMergeAccess {
        private final Map<IAccessFormMerge, AccessCode> canReads;

        public ReadBatchMergeAccess(Map<IAccessFormMerge, AccessCode> canReads) {
            this.canReads = canReads;
        }

        @Override
        public AccessCode getAccessCode(IAccessFormMerge merge) {
            AccessCode accessCode = this.canReads.get(merge);
            if (Objects.isNull(accessCode)) {
                return new AccessCode(FormWriteableAccessServiceImpl.NAME);
            }
            return this.canReads.get(merge);
        }
    }
}

