/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.MeasureUnitInfoCache;
import nr.single.para.parain.internal.cache.TableInfoDefine;

public class SchemeInfoCache {
    private Map<String, TableInfoDefine> entityTableCache = new HashMap<String, TableInfoDefine>();
    private Map<String, BaseDataDefineDO> baseDataCache = new HashMap<String, BaseDataDefineDO>();
    private String formSchemeFieldGroupKey = null;
    private DesignFormSchemeDefine formScheme = null;
    private Map<String, List<TableInfoDefine>> schemeTableCache = null;
    private Map<String, TableInfoDefine> taskTableCache = null;
    private Map<String, List<FieldInfoDefine>> tableFieldCache = null;
    private Map<String, List<FieldInfoDefine>> schemeFieldCache = null;
    private Map<String, List<FieldInfoDefine>> titleFieldCache = null;
    private Map<String, List<FieldInfoDefine>> matchTitleFieldCache = null;
    private Map<String, List<FieldInfoDefine>> codeFieldCache = null;
    private Map<String, List<FieldInfoDefine>> AliasFieldCache = null;
    private Map<String, String> formGroupMapDataGroups = null;
    private Map<String, FieldInfoDefine> idFieldCache = null;
    private Map<String, List<DesignDataLinkDefine>> formLinkCache;
    private String MeasureUnit;
    private String MeasureUnitTableKey;
    private String MeasureUnitCode;
    Map<String, MeasureUnitInfoCache> measureCahce;

    public DesignFormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(DesignFormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public Map<String, TableInfoDefine> getTableCache() {
        return this.entityTableCache;
    }

    public void setTableCache(Map<String, TableInfoDefine> tableCache) {
        this.entityTableCache = tableCache;
    }

    public Map<String, List<TableInfoDefine>> getSchemeTableCache() {
        if (this.schemeTableCache == null) {
            this.schemeTableCache = new HashMap<String, List<TableInfoDefine>>();
        }
        return this.schemeTableCache;
    }

    public void setSchemeTableCache(Map<String, List<TableInfoDefine>> schemeTableCache) {
        this.schemeTableCache = schemeTableCache;
    }

    public Map<String, List<FieldInfoDefine>> getSchemeFieldCache() {
        if (this.schemeFieldCache == null) {
            this.schemeFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.schemeFieldCache;
    }

    public void setSchemeFieldCache(Map<String, List<FieldInfoDefine>> schemeFieldCache) {
        this.schemeFieldCache = schemeFieldCache;
    }

    public Map<String, List<FieldInfoDefine>> getTitleFieldCache() {
        if (this.titleFieldCache == null) {
            this.titleFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.titleFieldCache;
    }

    public void setTitleFieldCache(Map<String, List<FieldInfoDefine>> titleFieldCache) {
        this.titleFieldCache = titleFieldCache;
    }

    public Map<String, TableInfoDefine> getTaskTableCache() {
        if (this.taskTableCache == null) {
            this.taskTableCache = new HashMap<String, TableInfoDefine>();
        }
        return this.taskTableCache;
    }

    public void setTaskTableCache(Map<String, TableInfoDefine> taskTableCache) {
        this.taskTableCache = taskTableCache;
    }

    public Map<String, List<FieldInfoDefine>> getTableFieldCache() {
        if (this.tableFieldCache == null) {
            this.tableFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.tableFieldCache;
    }

    public void setTableFieldCache(Map<String, List<FieldInfoDefine>> tableFieldCache) {
        this.tableFieldCache = tableFieldCache;
    }

    public BaseDataDefineDO queryBaseDataDefineByCode(String baseDataName) {
        BaseDataDefineDO table = null;
        if (this.baseDataCache.containsKey(baseDataName)) {
            table = this.baseDataCache.get(baseDataName);
        }
        return table;
    }

    public TableInfoDefine queryTableDefinesByCode(String tableCode) {
        TableInfoDefine table = null;
        if (this.entityTableCache.containsKey(tableCode)) {
            table = this.entityTableCache.get(tableCode);
        }
        return table;
    }

    public String getFormSchemeFieldGroupKey() {
        return this.formSchemeFieldGroupKey;
    }

    public void setFormSchemeFieldGroupKey(String formSchemeFieldGroupKey) {
        this.formSchemeFieldGroupKey = formSchemeFieldGroupKey;
    }

    public void ClearData() {
        this.entityTableCache.clear();
    }

    public Map<String, MeasureUnitInfoCache> getMeasureCahce() {
        if (this.measureCahce == null) {
            this.measureCahce = new HashMap<String, MeasureUnitInfoCache>();
        }
        return this.measureCahce;
    }

    public void setMeasureCahce(Map<String, MeasureUnitInfoCache> measureCahce) {
        this.measureCahce = measureCahce;
    }

    public String getMeasureUnitTableKey() {
        return this.MeasureUnitTableKey;
    }

    public void setMeasureUnitTableKey(String measureUnitTableKey) {
        this.MeasureUnitTableKey = measureUnitTableKey;
    }

    public String getMeasureUnitCode() {
        return this.MeasureUnitCode;
    }

    public void setMeasureUnitCode(String measureUnitTCode) {
        this.MeasureUnitCode = measureUnitTCode;
    }

    public String getMeasureUnit() {
        return this.MeasureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.MeasureUnit = measureUnit;
    }

    public Map<String, List<FieldInfoDefine>> getCodeFieldCache() {
        if (this.codeFieldCache == null) {
            this.codeFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.codeFieldCache;
    }

    public void setCodeFieldCache(Map<String, List<FieldInfoDefine>> codeFieldCache) {
        this.codeFieldCache = codeFieldCache;
    }

    public Map<String, BaseDataDefineDO> getBaseDataCache() {
        if (this.baseDataCache == null) {
            this.baseDataCache = new HashMap<String, BaseDataDefineDO>();
        }
        return this.baseDataCache;
    }

    public void setBaseDataCache(Map<String, BaseDataDefineDO> baseDataCache) {
        this.baseDataCache = baseDataCache;
    }

    public Map<String, List<FieldInfoDefine>> getAliasFieldCache() {
        if (this.AliasFieldCache == null) {
            this.AliasFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.AliasFieldCache;
    }

    public void setAliasFieldCache(Map<String, List<FieldInfoDefine>> aliasFieldCache) {
        this.AliasFieldCache = aliasFieldCache;
    }

    public Map<String, String> getFormGroupMapDataGroups() {
        if (this.formGroupMapDataGroups == null) {
            this.formGroupMapDataGroups = new HashMap<String, String>();
        }
        return this.formGroupMapDataGroups;
    }

    public void setFormGroupMapDataGroups(Map<String, String> formGroupMapDataGroups) {
        this.formGroupMapDataGroups = formGroupMapDataGroups;
    }

    public Map<String, List<FieldInfoDefine>> getMatchTitleFieldCache() {
        if (this.matchTitleFieldCache == null) {
            this.matchTitleFieldCache = new HashMap<String, List<FieldInfoDefine>>();
        }
        return this.matchTitleFieldCache;
    }

    public void setMatchTitleFieldCache(Map<String, List<FieldInfoDefine>> matchTitleFieldCache) {
        this.matchTitleFieldCache = matchTitleFieldCache;
    }

    public Map<String, FieldInfoDefine> getIdFieldCache() {
        if (this.idFieldCache == null) {
            this.idFieldCache = new HashMap<String, FieldInfoDefine>();
        }
        return this.idFieldCache;
    }

    public void setIdFieldCache(Map<String, FieldInfoDefine> idFieldCache) {
        this.idFieldCache = idFieldCache;
    }

    public Map<String, List<DesignDataLinkDefine>> getFormLinkCache() {
        if (this.formLinkCache == null) {
            this.formLinkCache = new HashMap<String, List<DesignDataLinkDefine>>();
        }
        return this.formLinkCache;
    }

    public void setFormLinkCache(Map<String, List<DesignDataLinkDefine>> formLinkCache) {
        this.formLinkCache = formLinkCache;
    }
}

