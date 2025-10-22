/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.TableInfoDefine;

public class FormInfoCache {
    private List<DesignDataField> insertDataFields = new ArrayList<DesignDataField>();
    private List<DesignDataField> updateDataFields = new ArrayList<DesignDataField>();
    private List<DesignFieldDefine> insertFields = new ArrayList<DesignFieldDefine>();
    private List<DesignFieldDefine> updateFields = new ArrayList<DesignFieldDefine>();
    private List<DesignDataLinkDefine> insertLinks = new ArrayList<DesignDataLinkDefine>();
    private List<DesignDataLinkDefine> updateLinks = new ArrayList<DesignDataLinkDefine>();
    private List<DesignDataRegionDefine> formRegions = new ArrayList<DesignDataRegionDefine>();
    private List<DesignDataRegionDefine> insertRegions = new ArrayList<DesignDataRegionDefine>();
    private List<DesignDataRegionDefine> updateRegions = new ArrayList<DesignDataRegionDefine>();
    private List<String> deleteRegions = new ArrayList<String>();
    private List<String> deleteLinks = new ArrayList<String>();
    private List<DesignDataLinkDefine> oldLinkList = new ArrayList<DesignDataLinkDefine>();
    private Map<String, DesignDataLinkDefine> oldLinkCache = new HashMap<String, DesignDataLinkDefine>();
    private Map<String, Map<String, DesignDataLinkDefine>> oldRegionLinkCache = new HashMap<String, Map<String, DesignDataLinkDefine>>();
    private Map<String, DesignDataLinkDefine> formLinksMap = new HashMap<String, DesignDataLinkDefine>();
    private Map<String, TableInfoDefine> tablesInformCache = new HashMap<String, TableInfoDefine>();
    private Map<String, DesignDataRegionDefine> regionsInformCache = new HashMap<String, DesignDataRegionDefine>();
    private Map<String, FieldInfoDefine> fieldsInFormCache = new HashMap<String, FieldInfoDefine>();
    private Map<String, List<TableInfoDefine>> regionTableCache = new HashMap<String, List<TableInfoDefine>>();
    private Map<String, Map<String, TableInfoDefine>> groupTitleTableCache = new HashMap<String, Map<String, TableInfoDefine>>();
    private Map<String, Map<String, TableInfoDefine>> groupKeyTableCache = new HashMap<String, Map<String, TableInfoDefine>>();
    private Map<String, String> tableAndGroupMap = new HashMap<String, String>();
    private Map<String, List<FieldInfoDefine>> regiogFieldCache = new HashMap<String, List<FieldInfoDefine>>();
    private List<String> deleteTableKeys;
    private List<String> deleteTableGroupKeys;
    private Object formGrid;

    public void ClearData() {
        this.insertDataFields.clear();
        this.updateDataFields.clear();
        this.insertFields.clear();
        this.updateFields.clear();
        this.insertLinks.clear();
        this.updateLinks.clear();
        this.deleteLinks.clear();
        this.oldLinkList.clear();
        this.oldLinkCache.clear();
        this.oldRegionLinkCache.clear();
        this.insertRegions.clear();
        this.updateRegions.clear();
        this.formRegions.clear();
        this.formLinksMap.clear();
        this.tablesInformCache.clear();
        this.regionsInformCache.clear();
        this.fieldsInFormCache.clear();
        this.regionTableCache.clear();
        this.regiogFieldCache.clear();
        this.getDeleteTableKeys().clear();
        this.formGrid = null;
    }

    public List<DesignDataRegionDefine> getInsertRegions() {
        if (this.insertRegions == null) {
            this.insertRegions = new ArrayList<DesignDataRegionDefine>();
        }
        return this.insertRegions;
    }

    public void setInsertRegions(List<DesignDataRegionDefine> insertRegions) {
        this.insertRegions = insertRegions;
    }

    public List<DesignDataRegionDefine> getUpdateRegions() {
        return this.updateRegions;
    }

    public void setUpdateRegions(List<DesignDataRegionDefine> updateRegions) {
        this.updateRegions = updateRegions;
    }

    public List<String> getDeleteRegions() {
        return this.deleteRegions;
    }

    public void setDeleteRegions(List<String> deleteRegions) {
        this.deleteRegions = deleteRegions;
    }

    public List<DesignFieldDefine> getInsertFields() {
        return this.insertFields;
    }

    public void setInsertFields(List<DesignFieldDefine> insertFields) {
        this.insertFields = insertFields;
    }

    public List<DesignFieldDefine> getUpdateFields() {
        return this.updateFields;
    }

    public void setUpdateFields(List<DesignFieldDefine> updateFields) {
        this.updateFields = updateFields;
    }

    public List<DesignDataLinkDefine> getInsertLinks() {
        return this.insertLinks;
    }

    public void setInsertLinks(List<DesignDataLinkDefine> insertLinks) {
        this.insertLinks = insertLinks;
    }

    public List<DesignDataLinkDefine> getUpdateLinks() {
        return this.updateLinks;
    }

    public void setUpdateLinks(List<DesignDataLinkDefine> updateLinks) {
        this.updateLinks = updateLinks;
    }

    public List<String> getDeleteLinks() {
        return this.deleteLinks;
    }

    public void setDeleteLinks(List<String> deleteLinks) {
        this.deleteLinks = deleteLinks;
    }

    public List<DesignDataLinkDefine> getOldLinkList() {
        return this.oldLinkList;
    }

    public void setOldLinkList(List<DesignDataLinkDefine> oldLinkList) {
        this.oldLinkList = oldLinkList;
    }

    public Map<String, DesignDataLinkDefine> getOldLinkCache() {
        if (this.oldLinkCache == null) {
            this.oldLinkCache = new HashMap<String, DesignDataLinkDefine>();
        }
        return this.oldLinkCache;
    }

    public void setOldLinkCache(Map<String, DesignDataLinkDefine> oldLinkCache) {
        this.oldLinkCache = oldLinkCache;
    }

    public Map<String, DesignDataLinkDefine> getFormLinksMap() {
        return this.formLinksMap;
    }

    public void setFormLinksMap(Map<String, DesignDataLinkDefine> formLinksMap) {
        this.formLinksMap = formLinksMap;
    }

    public Map<String, TableInfoDefine> getTablesInformCache() {
        return this.tablesInformCache;
    }

    public void setTablesInformCache(Map<String, TableInfoDefine> tablesInformCache) {
        this.tablesInformCache = tablesInformCache;
    }

    public Map<String, DesignDataRegionDefine> getRegionsInformCache() {
        return this.regionsInformCache;
    }

    public void setRegionsInformCache(Map<String, DesignDataRegionDefine> regionsInformCache) {
        this.regionsInformCache = regionsInformCache;
    }

    public Map<String, FieldInfoDefine> getFieldsInFormCache() {
        return this.fieldsInFormCache;
    }

    public void setFieldsInFormCache(Map<String, FieldInfoDefine> fieldsInFormCache) {
        this.fieldsInFormCache = fieldsInFormCache;
    }

    public Map<String, List<TableInfoDefine>> getRegionTableCache() {
        return this.regionTableCache;
    }

    public void setRegionTableCache(Map<String, List<TableInfoDefine>> regionTableCache) {
        this.regionTableCache = regionTableCache;
    }

    public Map<String, List<FieldInfoDefine>> getRegiogFieldCache() {
        return this.regiogFieldCache;
    }

    public void setRegiogFieldCache(Map<String, List<FieldInfoDefine>> regiogFieldCache) {
        this.regiogFieldCache = regiogFieldCache;
    }

    public List<DesignDataRegionDefine> getFormRegions() {
        return this.formRegions;
    }

    public void setFormRegions(List<DesignDataRegionDefine> formRegions) {
        this.formRegions = formRegions;
    }

    public List<String> getDeleteTableKeys() {
        if (this.deleteTableKeys == null) {
            this.deleteTableKeys = new ArrayList<String>();
        }
        return this.deleteTableKeys;
    }

    public void setDeleteTableKeys(List<String> deleteTableKeys) {
        this.deleteTableKeys = deleteTableKeys;
    }

    public Object getFormGrid() {
        return this.formGrid;
    }

    public void setFormGrid(Object formGrid) {
        this.formGrid = formGrid;
    }

    public List<DesignDataField> getInsertDataFields() {
        return this.insertDataFields;
    }

    public void setInsertDataFields(List<DesignDataField> insertDataFields) {
        this.insertDataFields = insertDataFields;
    }

    public List<DesignDataField> getUpdateDataFields() {
        return this.updateDataFields;
    }

    public void setUpdateDataFields(List<DesignDataField> updateDataFields) {
        this.updateDataFields = updateDataFields;
    }

    public Map<String, Map<String, TableInfoDefine>> getGroupTitleTableCache() {
        if (this.groupTitleTableCache == null) {
            this.groupTitleTableCache = new HashMap<String, Map<String, TableInfoDefine>>();
        }
        return this.groupTitleTableCache;
    }

    public String getTableTitleByGroupKey(String groupKey, String tableTitle) {
        String newTitle = tableTitle;
        Map<String, Map<String, TableInfoDefine>> tableCahce = this.getGroupTitleTableCache();
        Map<String, TableInfoDefine> groupTitleTables = null;
        if (tableCahce.containsKey(groupKey) && (groupTitleTables = tableCahce.get(groupKey)).containsKey(tableTitle)) {
            int num = 1;
            while (groupTitleTables.containsKey(newTitle = tableTitle + String.valueOf(num))) {
                Log.info((String)("\u5206\u7ec4\u4e0b\u5b58\u5728\u91cd\u540d\u6307\u6807\u8868\uff1a" + groupKey + "," + newTitle));
                ++num;
            }
        }
        return newTitle;
    }

    public void addTableToGroup(String groupKey, TableInfoDefine table) {
        Map<String, Map<String, TableInfoDefine>> tableCahce = this.getGroupTitleTableCache();
        Map<Object, Object> groupTitleTables = null;
        if (tableCahce.containsKey(groupKey)) {
            groupTitleTables = tableCahce.get(groupKey);
        } else {
            groupTitleTables = new HashMap();
            tableCahce.put(groupKey, groupTitleTables);
        }
        if (!groupTitleTables.containsKey(table.getTitle())) {
            groupTitleTables.put(table.getTitle(), table);
        }
        this.getTableAndGroupMap().put(table.getKey(), groupKey);
        Map<Object, Object> groupKeyTables = null;
        if (this.getGroupKeyTableCache().containsKey(groupKey)) {
            groupKeyTables = this.getGroupKeyTableCache().get(groupKey);
        } else {
            groupKeyTables = new HashMap();
            this.getGroupKeyTableCache().put(groupKey, groupKeyTables);
        }
        if (!groupKeyTables.containsKey(table.getKey())) {
            groupKeyTables.put(table.getKey(), table);
        }
    }

    public void removeTableKeyFormGroup(String groupKey, String tableKey) {
        Map<String, TableInfoDefine> groupKeyTables;
        Map<String, Map<String, TableInfoDefine>> tableCahce = this.getGroupKeyTableCache();
        String newGroupKey = groupKey;
        if (StringUtils.isEmpty((String)newGroupKey) && this.getTableAndGroupMap().containsKey(tableKey)) {
            newGroupKey = this.getTableAndGroupMap().get(tableKey);
        }
        if (StringUtils.isNotEmpty((String)newGroupKey) && tableCahce.containsKey(newGroupKey) && (groupKeyTables = tableCahce.get(newGroupKey)).containsKey(tableKey)) {
            groupKeyTables.remove(tableKey);
        }
    }

    public void setGroupTitleTableCache(Map<String, Map<String, TableInfoDefine>> groupTitleTableCache) {
        this.groupTitleTableCache = groupTitleTableCache;
    }

    public Map<String, Map<String, DesignDataLinkDefine>> getOldRegionLinkCache() {
        if (this.oldRegionLinkCache == null) {
            this.oldRegionLinkCache = new HashMap<String, Map<String, DesignDataLinkDefine>>();
        }
        return this.oldRegionLinkCache;
    }

    public void setOldRegionLinkCache(Map<String, Map<String, DesignDataLinkDefine>> oldRegionLinkCache) {
        this.oldRegionLinkCache = oldRegionLinkCache;
    }

    public List<String> getDeleteTableGroupKeys() {
        if (this.deleteTableGroupKeys == null) {
            this.deleteTableGroupKeys = new ArrayList<String>();
        }
        return this.deleteTableGroupKeys;
    }

    public void setDeleteTableGroupKeys(List<String> deleteTableGroupKeys) {
        this.deleteTableGroupKeys = deleteTableGroupKeys;
    }

    public Map<String, Map<String, TableInfoDefine>> getGroupKeyTableCache() {
        if (this.groupKeyTableCache == null) {
            this.groupKeyTableCache = new HashMap<String, Map<String, TableInfoDefine>>();
        }
        return this.groupKeyTableCache;
    }

    public void setGroupKeyTableCache(Map<String, Map<String, TableInfoDefine>> groupKeyTableCache) {
        this.groupKeyTableCache = groupKeyTableCache;
    }

    public Map<String, String> getTableAndGroupMap() {
        if (this.tableAndGroupMap == null) {
            this.tableAndGroupMap = new HashMap<String, String>();
        }
        return this.tableAndGroupMap;
    }

    public void setTableAndGroupMap(Map<String, String> tableAndGroupMap) {
        this.tableAndGroupMap = tableAndGroupMap;
    }
}

