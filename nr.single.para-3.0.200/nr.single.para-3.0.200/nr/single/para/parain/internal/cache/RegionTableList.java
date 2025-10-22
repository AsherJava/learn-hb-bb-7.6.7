/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import java.util.HashMap;
import java.util.Map;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.RegionTableCache;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import org.apache.commons.lang3.StringUtils;

public class RegionTableList {
    private Map<String, RegionTableCache> tableCodeMap = new HashMap<String, RegionTableCache>();
    private Map<String, RegionTableCache> tableIDMap = new HashMap<String, RegionTableCache>();
    private Map<String, FieldInfoDefine> AllFieldIDMap = new HashMap<String, FieldInfoDefine>();
    private Map<String, FieldInfoDefine> AllFieldCodeMap = new HashMap<String, FieldInfoDefine>();
    private Map<String, FieldInfoDefine> AllFieldAlisMap = new HashMap<String, FieldInfoDefine>();
    private Map<String, RegionTableCache> AllFieldTableMap = new HashMap<String, RegionTableCache>();
    private Map<String, RegionTableCache> AllFieldAlisTableMap = new HashMap<String, RegionTableCache>();
    Map<String, FieldInfoDefine> fieldMap = new HashMap<String, FieldInfoDefine>();
    private boolean isTableNew;
    private RegionTableCache curTableCache;
    private String fileFlag;
    private String regionTableCode;

    public Map<String, RegionTableCache> getTableCodeMap() {
        return this.tableCodeMap;
    }

    public Map<String, RegionTableCache> getTableIDMap() {
        return this.tableIDMap;
    }

    public void addTableDefine(TableInfoDefine table, DesignDataRegionDefine region) {
        if (!this.tableCodeMap.containsKey(table.getCode())) {
            RegionTableCache cache = new RegionTableCache();
            cache.setRegionDefine(region);
            cache.setTableDefine(table);
            this.tableCodeMap.put(table.getCode(), cache);
            this.tableIDMap.put(table.getKey(), cache);
        }
    }

    public void addFieldDefine(FieldInfoDefine field) {
        RegionTableCache cache = this.tableIDMap.get(field.getOwnerTableKey());
        if (null != cache) {
            cache.addFieldDefine(field);
            this.AllFieldIDMap.put(field.getKey(), field);
            this.AllFieldCodeMap.put(field.getCode(), field);
            this.AllFieldAlisMap.put(field.getAlis(), field);
            this.AllFieldTableMap.put(field.getCode(), cache);
            this.AllFieldAlisTableMap.put(field.getAlis(), cache);
            if (StringUtils.isNotEmpty((CharSequence)field.getAlis())) {
                this.fieldMap.put(field.getAlis(), field);
            } else {
                this.fieldMap.put(field.getCode(), field);
            }
        }
    }

    public FieldInfoDefine getFieldDefine(String fieldKey) {
        FieldInfoDefine field = null;
        if (this.AllFieldIDMap.containsKey(fieldKey)) {
            field = this.AllFieldIDMap.get(fieldKey);
        }
        return field;
    }

    public FieldInfoDefine getFieldByCode(String fieldCode) {
        FieldInfoDefine field = null;
        if (this.AllFieldCodeMap.containsKey(fieldCode)) {
            field = this.AllFieldCodeMap.get(fieldCode);
        }
        return field;
    }

    public FieldInfoDefine getFieldByAlis(String fieldAlis) {
        FieldInfoDefine field = null;
        if (this.AllFieldAlisMap.containsKey(fieldAlis)) {
            field = this.AllFieldAlisMap.get(fieldAlis);
        }
        return field;
    }

    public RegionTableCache getRegionTableCacheById(String tableId) {
        RegionTableCache cache = null;
        if (this.tableIDMap.containsKey(tableId)) {
            cache = this.tableIDMap.get(tableId);
        }
        return cache;
    }

    public RegionTableCache getFieldDefineByCode(String fieldCode) {
        RegionTableCache cache = null;
        if (this.AllFieldTableMap.containsKey(fieldCode)) {
            cache = this.AllFieldTableMap.get(fieldCode);
        }
        return cache;
    }

    public RegionTableCache getFieldDefineByAlis(String fieldAlis) {
        RegionTableCache cache = null;
        if (this.AllFieldAlisTableMap.containsKey(fieldAlis)) {
            cache = this.AllFieldAlisTableMap.get(fieldAlis);
        }
        return cache;
    }

    public RegionTableCache getNewCache(DesignDataRegionDefine regionDefine, TableInfoDefine tableDefine) {
        RegionTableCache cache = new RegionTableCache(regionDefine, tableDefine);
        this.addTableCache(cache);
        return cache;
    }

    public RegionTableCache getNewCache(RegionTableCache oldCache) {
        RegionTableCache cache = null;
        if (null == oldCache) {
            cache = new RegionTableCache();
        } else {
            cache = new RegionTableCache();
            cache.setRegionDefine(oldCache.getRegionDefine());
            int acount = this.tableCodeMap.size();
            String code = oldCache.getMainTableCode() + "_" + String.valueOf(acount);
            while (this.tableCodeMap.containsKey(code)) {
                code = oldCache.getMainTableCode() + "_" + String.valueOf(++acount);
            }
            cache.setMainTableCode(oldCache.getMainTableCode());
            cache.setTableCode(code);
        }
        return cache;
    }

    public boolean getNeedNewCache(RegionTableCache oldCache) {
        return oldCache.getFieldIDMap().size() >= 950;
    }

    public void addTableCache(RegionTableCache cache) {
        if (!this.tableCodeMap.containsKey(cache.getTableCode())) {
            this.tableCodeMap.put(cache.getTableCode(), cache);
            this.tableIDMap.put(cache.getTableDefine().getKey(), cache);
        }
    }

    public int getTableCount() {
        return this.tableCodeMap.size();
    }

    public Map<String, FieldInfoDefine> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, FieldInfoDefine> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public boolean isTableNew() {
        return this.isTableNew;
    }

    public void setTableNew(boolean isTableNew) {
        this.isTableNew = isTableNew;
    }

    public RegionTableCache getCurTableCache() {
        return this.curTableCache;
    }

    public void setCurTableCache(RegionTableCache curTableCache) {
        this.curTableCache = curTableCache;
    }

    public String getFileFlag() {
        return this.fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getRegionTableCode() {
        return this.regionTableCode;
    }

    public void setRegionTableCode(String regionTableCode) {
        this.regionTableCode = regionTableCode;
    }
}

