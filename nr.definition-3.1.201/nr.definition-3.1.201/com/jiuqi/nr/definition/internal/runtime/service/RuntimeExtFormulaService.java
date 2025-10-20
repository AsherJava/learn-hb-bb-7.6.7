/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.cache.RuntimeDefinitionPrivateCache;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeExtFormulaDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtFormulaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class RuntimeExtFormulaService
implements IRuntimeExtFormulaService,
RuntimeDefinitionRefreshListener {
    private static final String IDX_NAME_CODE = "private:code";
    private static final String IDX_NAME_FORM = "private:form";
    private static final String IDX_NAME_TYPE = "private:type";
    private final RunTimeExtFormulaDefineDao extFormulaDao;
    private final RuntimeDefinitionPrivateCache<FormulaDefine> cache;
    private final RuntimeFormulaCacheLoader cacheLoader = new RuntimeFormulaCacheLoader();

    private static String createCodeIndexName(String formulaSchemeKey) {
        return IDX_NAME_CODE.concat("::").concat(formulaSchemeKey);
    }

    private static String createFormIndexName(String formulaSchemeKey) {
        return IDX_NAME_FORM.concat("::").concat(formulaSchemeKey);
    }

    private static String createTypeIndexName(String formulaSchemeKey) {
        return IDX_NAME_TYPE.concat("::").concat(formulaSchemeKey);
    }

    public RuntimeExtFormulaService(RunTimeExtFormulaDefineDao formulaDao, NedisCacheProvider cacheProvider) {
        this.extFormulaDao = formulaDao;
        this.cache = new RuntimeDefinitionPrivateCache<FormulaDefine>(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), FormulaDefine.class);
    }

    @Override
    public FormulaDefine queryFormula(String formulaKey) {
        if (formulaKey == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.cache.getObject(formulaKey);
        if (Objects.isNull(valueWrapper)) {
            return (FormulaDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper revalueWrapperInner = this.cache.getObject(formulaKey);
                if (Objects.nonNull(revalueWrapperInner)) {
                    return (FormulaDefine)revalueWrapperInner.get();
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormula(formulaKey);
                FormulaDefine formula = cacheUnit.objects.stream().filter(t -> formulaKey.equals(t.getKey())).findFirst().orElse(null);
                return formula;
            });
        }
        return (FormulaDefine)valueWrapper.get();
    }

    @Override
    public List<FormulaDefine> queryFormulas(List<String> formulaKeys) {
        if (formulaKeys == null || formulaKeys.isEmpty()) {
            return Collections.emptyList();
        }
        Map cachedFormulas = this.cache.getObjects(formulaKeys);
        LinkedList<FormulaDefine> result = new LinkedList<FormulaDefine>();
        LinkedHashMap loadedFormulas = new LinkedHashMap();
        for (String formulaKey : formulaKeys) {
            Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)cachedFormulas.get(formulaKey);
            if (Objects.nonNull(valueWrapper)) {
                if (null == valueWrapper.get()) continue;
                result.add((FormulaDefine)valueWrapper.get());
                continue;
            }
            if (loadedFormulas.containsKey(formulaKey)) {
                if (null == loadedFormulas.get(formulaKey)) continue;
                result.add((FormulaDefine)loadedFormulas.get(formulaKey));
                continue;
            }
            result.add((FormulaDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getObject(formulaKey);
                if (Objects.nonNull(revalueWrapper)) {
                    if (null == revalueWrapper.get()) {
                        return null;
                    }
                    result.add((FormulaDefine)revalueWrapper.get());
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormula(formulaKey);
                FormulaDefine formula = cacheUnit.objects.stream().filter(t -> formulaKey.equals(t.getKey())).findFirst().orElse(null);
                loadedFormulas.putAll(cacheUnit.objects.stream().collect(Collectors.toMap(t -> t.getKey(), t -> t)));
                loadedFormulas.put(formulaKey, formula);
                return formula;
            }));
        }
        return result;
    }

    @Override
    public FormulaDefine findFormula(String formulaDefineCode, String formulaSchemeKey) {
        if (formulaDefineCode == null || formulaSchemeKey == null) {
            return null;
        }
        HashCacheValue hashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createCodeIndexName(formulaSchemeKey), formulaDefineCode);
        if (!hashValue.isPresent()) {
            return (FormulaDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue rehashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createCodeIndexName(formulaSchemeKey), formulaDefineCode);
                if (rehashValue.isPresent()) {
                    return this.getFormulaDefine((HashCacheValue<Cache.ValueWrapper>)rehashValue);
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormulaScheme(formulaSchemeKey);
                String formulaKey = (String)cacheUnit.codeIndex.get(formulaDefineCode);
                if (formulaKey == null) {
                    return null;
                }
                return cacheUnit.objects.stream().filter(t -> formulaKey.equals(t.getKey())).findFirst().orElse(null);
            });
        }
        return this.getFormulaDefine((HashCacheValue<Cache.ValueWrapper>)hashValue);
    }

    private FormulaDefine getFormulaDefine(HashCacheValue<Cache.ValueWrapper> hashValue) {
        Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashValue.get();
        if (valueWrapper == null) {
            return null;
        }
        return this.queryFormula((String)valueWrapper.get());
    }

    @Override
    public List<FormulaDefine> getFormulasInScheme(String formulaSchemeKey) {
        List<String> formulaKeysInScheme = this.getFormulaKeysInScheme(formulaSchemeKey);
        List<FormulaDefine> formulas = this.queryFormulas(formulaKeysInScheme);
        return formulas.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<String> getFormulaKeysInScheme(String formulaSchemeKey) {
        if (formulaSchemeKey == null) {
            throw new IllegalArgumentException("'formulaSchemeKey' must not be null.");
        }
        HashCacheValue hashValue = this.cache.mGetHashIndexValue(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey));
        if (!hashValue.isPresent()) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue rehashValue = this.cache.mGetHashIndexValue(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey));
                if (rehashValue.isPresent()) {
                    return this.getHashIndex((HashCacheValue<Map<Object, Object>>)rehashValue);
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormulaScheme(formulaSchemeKey);
                return cacheUnit.objects.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            });
        }
        return this.getHashIndex((HashCacheValue<Map<Object, Object>>)hashValue);
    }

    private List<String> getHashIndex(HashCacheValue<Map<Object, Object>> hashValue) {
        ArrayList formulaKeysInScheme = new ArrayList();
        for (Object formulaKeysInForm : ((Map)hashValue.get()).values()) {
            formulaKeysInScheme.addAll((List)formulaKeysInForm);
        }
        return new ArrayList<String>(formulaKeysInScheme);
    }

    @Override
    public List<FormulaDefine> getFormulasInScheme(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        List<String> formulaKeysInType = this.getFormulaKeysInType(formulaSchemeKey, formulaType);
        List<FormulaDefine> formulas = this.queryFormulas(formulaKeysInType);
        return formulas.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<String> getFormulaKeysInType(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        if (formulaSchemeKey == null || formulaType == null) {
            return Collections.emptyList();
        }
        HashCacheValue hashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createTypeIndexName(formulaSchemeKey), formulaType.name());
        if (!hashValue.isPresent()) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue rehashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createTypeIndexName(formulaSchemeKey), formulaType.name());
                if (rehashValue.isPresent()) {
                    return this.getHashIndexs((HashCacheValue<Cache.ValueWrapper>)rehashValue);
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormulaScheme(formulaSchemeKey);
                List formulaKeys = (List)cacheUnit.typeIndex.get(formulaType.name());
                return formulaKeys == null ? Collections.emptyList() : formulaKeys;
            });
        }
        return this.getHashIndexs((HashCacheValue<Cache.ValueWrapper>)hashValue);
    }

    private List<String> getHashIndexs(HashCacheValue<Cache.ValueWrapper> hashValue) {
        Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashValue.get();
        if (valueWrapper == null) {
            return Collections.emptyList();
        }
        return new ArrayList<String>((List)valueWrapper.get());
    }

    private List<String> getFormulaKeysInForm(String formulaSchemeKey, String formkey) {
        if (formulaSchemeKey == null) {
            return Collections.emptyList();
        }
        String cacheFormKey = formkey == null ? "00000000-0000-0000-0000-000000000000" : formkey;
        HashCacheValue hashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey), cacheFormKey);
        if (!hashValue.isPresent()) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue rehashValue = this.cache.getHashIndexValue(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey), cacheFormKey);
                if (rehashValue.isPresent()) {
                    return this.getHashIndexs((HashCacheValue<Cache.ValueWrapper>)rehashValue);
                }
                RuntimeFormulaCacheUnit cacheUnit = this.cacheLoader.loadCacheByFormulaScheme(formulaSchemeKey);
                List formulaKeys = (List)cacheUnit.formIndex.get(cacheFormKey);
                return formulaKeys == null ? Collections.emptyList() : formulaKeys;
            });
        }
        return this.getHashIndexs((HashCacheValue<Cache.ValueWrapper>)hashValue);
    }

    @Override
    public List<FormulaDefine> getFormulasInForm(String formulaSchemekey, String formkey) {
        List<String> formulaKeysInForm = this.getFormulaKeysInForm(formulaSchemekey, formkey);
        List<FormulaDefine> formulas = this.queryFormulas(formulaKeysInForm);
        return formulas.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefine> getFormulasInFormByType(String formulaSchemekey, String formkey) {
        List<String> formulaKeysInForm = this.getFormulaKeysInForm(formulaSchemekey, formkey);
        List<FormulaDefine> formulas = this.queryFormulas(formulaKeysInForm);
        return formulas;
    }

    @Override
    public void ModifyFormulaCheckType(int oldType, int newType) {
        this.extFormulaDao.updateFormulaCheckType(oldType, newType);
        RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            this.cache.clear();
            return null;
        });
    }

    private boolean compareType(FormulaDefine formula, DataEngineConsts.FormulaType formulaType) {
        if (null == formula) {
            return false;
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.CALCULATE)) {
            return formula.getUseCalculate();
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.CHECK)) {
            return formula.getUseCheck();
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.BALANCE)) {
            return formula.getUseBalance();
        }
        return false;
    }

    @Override
    public void refreshFormulaCache(String formulaSchemeKey) throws Exception {
        if (StringUtils.isEmpty((String)formulaSchemeKey)) {
            throw new IllegalArgumentException("'formulaSchemeKey' must not be null.");
        }
        List<FormulaDefine> formulas = this.extFormulaDao.queryFormulaDefineByScheme(formulaSchemeKey);
        RuntimeFormulaCacheUnit cacheUnit = new RuntimeFormulaCacheUnit(formulaSchemeKey, formulas);
        ArrayList<String> indexNames = new ArrayList<String>();
        List<String> formulaKeysInScheme = this.getFormulaKeysInScheme(formulaSchemeKey);
        indexNames.add(RuntimeExtFormulaService.createCodeIndexName(formulaSchemeKey));
        indexNames.add(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey));
        indexNames.add(RuntimeExtFormulaService.createTypeIndexName(formulaSchemeKey));
        this.cache.synchronizedRun("formula", () -> {
            this.cache.removeObjects(formulaKeysInScheme);
            this.cache.removeIndexs(indexNames);
            this.cache.putObjects(cacheUnit.objects);
            this.cache.putHashIndex(RuntimeExtFormulaService.createCodeIndexName(formulaSchemeKey), cacheUnit.codeIndex);
            this.cache.putHashIndex(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey), cacheUnit.formIndex);
            this.cache.putHashIndex(RuntimeExtFormulaService.createTypeIndexName(formulaSchemeKey), cacheUnit.typeIndex);
        });
    }

    public void onClearCache() {
        RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            this.cache.clear();
            return null;
        });
    }

    @Override
    public List<FormulaDefine> searchFormulaInScheme(String formulaCode, String formulaSchemeKey) {
        if (StringUtils.isEmpty((String)formulaCode)) {
            throw new IllegalArgumentException("'formulaCode' must not be null.");
        }
        if (StringUtils.isEmpty((String)formulaSchemeKey)) {
            throw new IllegalArgumentException("'formulaSchemeKey' must not be null.");
        }
        return this.extFormulaDao.searchFormula(formulaCode, formulaSchemeKey);
    }

    public List<FormulaDefine> getPublicFormulasInForm(String formulaSchemeTitle, String formKey) {
        return this.extFormulaDao.getPublicFormulasInForm(formulaSchemeTitle, formKey);
    }

    public List<FormulaDefine> getPublicFormulasInFormByType(String formulaSchemeTitle, String formKey, DataEngineConsts.FormulaType formulaType) {
        List<FormulaDefine> formulas = this.getPublicFormulasInForm(formulaSchemeTitle, formKey);
        return formulas.stream().filter(formula -> this.compareType((FormulaDefine)formula, formulaType)).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefine> getFormulaByUnits(String formulaScheme, Set<String> units) {
        ArrayList<FormulaDefine> result = new ArrayList<FormulaDefine>();
        if (StringUtils.isEmpty((String)formulaScheme) || null == units || units.size() == 0) {
            return result;
        }
        List<FormulaDefine> formulaDefines = this.getFormulasInScheme(formulaScheme);
        for (FormulaDefine formulaDefine : formulaDefines) {
            if (!units.contains(formulaDefine.getUnit())) continue;
            result.add(formulaDefine);
        }
        return result;
    }

    private static class RuntimeFormulaCacheUnit {
        private final String formulaSchemeKey;
        private final List<FormulaDefine> objects;
        private final Map<String, String> codeIndex = new LinkedHashMap<String, String>();
        private final Map<String, List<String>> formIndex = new LinkedHashMap<String, List<String>>();
        private final Map<String, List<String>> typeIndex = new LinkedHashMap<String, List<String>>();

        public RuntimeFormulaCacheUnit(String formulaSchemeKey, List<FormulaDefine> formulas) {
            this.formulaSchemeKey = formulaSchemeKey;
            this.objects = formulas;
            if (formulaSchemeKey != null) {
                this.typeIndex.put(DataEngineConsts.FormulaType.CALCULATE.name(), new ArrayList());
                this.typeIndex.put(DataEngineConsts.FormulaType.CHECK.name(), new ArrayList());
                this.typeIndex.put(DataEngineConsts.FormulaType.BALANCE.name(), new ArrayList());
                for (FormulaDefine formula : formulas) {
                    List<String> formLinkedFormulaKeys;
                    String formulaType;
                    List<String> formulaKeysByCode;
                    String formulaKey = formula.getKey();
                    this.codeIndex.put(formula.getCode(), formulaKey);
                    String formKey = formula.getFormKey();
                    if (formKey == null) {
                        formKey = "00000000-0000-0000-0000-000000000000";
                    }
                    if ((formulaKeysByCode = this.formIndex.get(formKey)) == null) {
                        formulaKeysByCode = new ArrayList<String>();
                        this.formIndex.put(formKey, formulaKeysByCode);
                    }
                    formulaKeysByCode.add(formulaKey);
                    if (formula.getUseCalculate()) {
                        formulaType = DataEngineConsts.FormulaType.CALCULATE.name();
                        formLinkedFormulaKeys = this.typeIndex.get(formulaType);
                        if (formLinkedFormulaKeys == null) {
                            formLinkedFormulaKeys = new ArrayList<String>();
                            this.typeIndex.put(formulaType, formLinkedFormulaKeys);
                        }
                        formLinkedFormulaKeys.add(formulaKey);
                    }
                    if (formula.getUseCheck()) {
                        formulaType = DataEngineConsts.FormulaType.CHECK.name();
                        formLinkedFormulaKeys = this.typeIndex.get(formulaType);
                        if (formLinkedFormulaKeys == null) {
                            formLinkedFormulaKeys = new ArrayList<String>();
                            this.typeIndex.put(formulaType, formLinkedFormulaKeys);
                        }
                        formLinkedFormulaKeys.add(formulaKey);
                    }
                    if (!formula.getUseBalance()) continue;
                    formulaType = DataEngineConsts.FormulaType.BALANCE.name();
                    formLinkedFormulaKeys = this.typeIndex.get(formulaType);
                    if (formLinkedFormulaKeys == null) {
                        formLinkedFormulaKeys = new ArrayList<String>();
                        this.typeIndex.put(formulaType, formLinkedFormulaKeys);
                    }
                    formLinkedFormulaKeys.add(formulaKey);
                }
            }
        }
    }

    private class RuntimeFormulaCacheLoader {
        private RuntimeFormulaCacheLoader() {
        }

        public RuntimeFormulaCacheUnit loadCacheByFormula(String formulaKey) {
            FormulaDefine formula = RuntimeExtFormulaService.this.extFormulaDao.getDefineByKey(formulaKey);
            if (formula == null) {
                RuntimeExtFormulaService.this.cache.putNullObject(formulaKey);
                return new RuntimeFormulaCacheUnit(null, Collections.emptyList());
            }
            if (formula.getFormulaSchemeKey() == null) {
                RuntimeExtFormulaService.this.cache.putObject((IBaseMetaItem)formula);
                return new RuntimeFormulaCacheUnit(null, Arrays.asList(formula));
            }
            RuntimeFormulaCacheUnit cacheUnit = this.loadCacheByFormulaScheme(formula.getFormulaSchemeKey());
            if (cacheUnit.objects.stream().noneMatch(t -> formulaKey.equals(t.getKey()))) {
                RuntimeExtFormulaService.this.cache.putNullObject(formulaKey);
            }
            return cacheUnit;
        }

        public RuntimeFormulaCacheUnit loadCacheByFormulaScheme(String formulaSchemeKey) {
            List<FormulaDefine> formulas = RuntimeExtFormulaService.this.extFormulaDao.queryFormulaDefineByScheme(formulaSchemeKey);
            RuntimeFormulaCacheUnit cacheUnit = new RuntimeFormulaCacheUnit(formulaSchemeKey, formulas);
            RuntimeExtFormulaService.this.cache.putObjects(cacheUnit.objects);
            RuntimeExtFormulaService.this.cache.putHashIndex(RuntimeExtFormulaService.createCodeIndexName(formulaSchemeKey), cacheUnit.codeIndex);
            RuntimeExtFormulaService.this.cache.putHashIndex(RuntimeExtFormulaService.createFormIndexName(formulaSchemeKey), cacheUnit.formIndex);
            RuntimeExtFormulaService.this.cache.putHashIndex(RuntimeExtFormulaService.createTypeIndexName(formulaSchemeKey), cacheUnit.typeIndex);
            return cacheUnit;
        }
    }
}

