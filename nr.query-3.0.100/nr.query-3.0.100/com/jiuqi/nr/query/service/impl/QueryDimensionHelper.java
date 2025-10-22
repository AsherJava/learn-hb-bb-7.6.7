/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.TableGroupDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.TableGroupDefine;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryEntityData;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.EntityDataObject;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryDimensionHelper {
    private static final Logger log = LoggerFactory.getLogger(QueryDimensionHelper.class);
    @Autowired
    private IDesignTimeViewController viewCtrl;
    @Autowired
    private IRunTimeViewController runTimeViewCtrl;
    @Autowired
    private QueryEntityUtil queryEntityUtil;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    public FormSchemeDefine getScheme(String schemeKey) {
        return this.runTimeViewCtrl.getFormScheme(schemeKey);
    }

    public List<ITree<EntityDataObject>> queryEnumEntityData(List<TableGroupDefine> tableGroups) {
        if (tableGroups == null || tableGroups.isEmpty()) {
            return null;
        }
        ArrayList<ITree<EntityDataObject>> queryEntityList = new ArrayList<ITree<EntityDataObject>>();
        ITree publicEnumNode = new ITree();
        queryEntityList.add(publicEnumNode);
        publicEnumNode.setKey("00000000-0000-0000-0000-000000000000");
        publicEnumNode.setTitle("\u516c\u5171");
        publicEnumNode.setLeaf(true);
        EntityDataObject obj = new EntityDataObject();
        obj.setKey("00000000-0000-0000-0000-000000000000");
        publicEnumNode.setData((INode)obj);
        HashMap<String, ITree<EntityDataObject>> map = new HashMap<String, ITree<EntityDataObject>>();
        for (int i = 0; i < tableGroups.size(); ++i) {
            TableGroupDefine group = tableGroups.get(i);
            ITree entityData = new ITree();
            entityData.setKey(group.getKey());
            entityData.setTitle(group.getTitle());
            entityData.setLeaf(true);
            EntityDataObject data = new EntityDataObject();
            data.setKey(group.getKey());
            entityData.setData((INode)data);
            map.put(entityData.getKey(), entityData);
            String parentId = group.getParentKey();
            ITree<EntityDataObject> parentData = this.findParent(map, parentId);
            if (null == parentData) {
                queryEntityList.add((ITree<EntityDataObject>)entityData);
                continue;
            }
            ArrayList<ITree> childs = parentData.getChildren();
            if (childs == null) {
                childs = new ArrayList<ITree>();
            }
            childs.add(entityData);
            parentData.setChildren(childs);
            parentData.setLeaf(false);
        }
        return queryEntityList;
    }

    private ITree<EntityDataObject> findParent(Map<String, ITree<EntityDataObject>> map, String parentId) {
        if (map.get(parentId) != null) {
            ITree<EntityDataObject> parentData = map.get(parentId);
            return parentData;
        }
        return null;
    }

    public List<ITree<EntityDataObject>> getEnumTree() {
        List<ITree<EntityDataObject>> enumTree = null;
        try {
            List<TableGroupDefine> enumGroups = this.getAllEnumGroups();
            if (enumGroups != null) {
                enumTree = this.queryEnumEntityData(enumGroups);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return enumTree;
    }

    public List<TableGroupDefine> getAllEnumGroups() throws Exception {
        ArrayList<TableGroupDefine> allGroups = new ArrayList<TableGroupDefine>();
        List<TableGroupDefine> enumGroups = this.getEnumGroupByAllTask();
        allGroups.addAll(enumGroups);
        enumGroups.forEach(e -> {
            try {
                List<TableGroupDefine> schemeGroups = this.getEnumGroupByAllSchemeInTask(e.getParentKey());
                allGroups.addAll(schemeGroups);
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
        });
        return allGroups;
    }

    public List<TableGroupDefine> getEnumGroupByAllSchemeInTask(String taskKey) throws Exception {
        List formSchemeDefines = this.viewCtrl.queryFormSchemeByTask(taskKey);
        DesignTaskDefine taskDefine = this.viewCtrl.queryTaskDefine(taskKey);
        ArrayList<TableGroupDefine> groupByFormScheme = new ArrayList<TableGroupDefine>();
        return groupByFormScheme;
    }

    public List<TableGroupDefine> getEnumGroupByAllTask() throws Exception {
        List taskDefines = this.viewCtrl.getAllTaskDefines();
        ArrayList<TableGroupDefine> groupByTask = new ArrayList<TableGroupDefine>();
        return groupByTask;
    }

    public List<JSONObject> getMasterEntity(String masterKeys) throws Exception {
        String[] keys;
        ArrayList<JSONObject> result = new ArrayList<JSONObject>();
        if (masterKeys == null) {
            return null;
        }
        for (String key : keys = masterKeys.split(";")) {
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(key);
            if (entityView == null) continue;
            boolean periodView = this.periodEntityAdapter.isPeriodEntity(key);
            if (periodView) {
                IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityView.getEntityId());
                JSONObject entityObject = new JSONObject();
                entityObject.put("code", (Object)key);
                if (periodEntity.getTitle() != null) {
                    entityObject.put("title", (Object)periodEntity.getTitle());
                }
                entityObject.put("tablekind", (Object)TableKind.TABLE_KIND_ENTITY_PERIOD);
                entityObject.put("tableName", (Object)periodEntity.getCode());
                entityObject.put("istree", (Object)"false");
                result.add(entityObject);
                continue;
            }
            IEntityDefine entityDefine = this.metaService.queryEntity(key);
            TableModelDefine tableModel = this.metaService.getTableModel(entityDefine.getId());
            ColumnModelDefine columnModelDefineByID = this.modelService.getColumnModelDefineByID(tableModel.getBizKeys());
            if (columnModelDefineByID == null) continue;
            JSONObject entityObject = new JSONObject();
            entityObject.put("code", (Object)key);
            if (entityDefine.getTitle() != null) {
                entityObject.put("title", (Object)entityDefine.getTitle());
            }
            entityObject.put("tablekind", (Object)TableKind.TABLE_KIND_ENTITY);
            entityObject.put("tableName", (Object)tableModel.getCode());
            IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
            if (entityTable.getMaxDepth() > 1) {
                entityObject.put("istree", (Object)"true");
            } else {
                entityObject.put("istree", (Object)"false");
            }
            result.add(entityObject);
        }
        return result;
    }

    private List<EntityDataObject> getContentByMasterKeyOld(EntityViewDefine entityView) throws RuntimeException, DataTypeException {
        return null;
    }

    private List<ITree<EntityDataObject>> getRootsOld(EntityViewDefine entityView) throws Exception {
        return null;
    }

    public List<ITree<EntityDataObject>> getChildrenOld(String entityViewKey, String entityKey) throws Exception {
        return null;
    }

    List<QueryEntityData> getParentRows(IEntityRow currentRow, String dimName, IEntityTable eTable, List<QueryEntityData> entityRows) {
        String[] pKeys;
        for (String key : pKeys = currentRow.getParentsEntityKeyDataPath()) {
            DimensionValueSet dimV = new DimensionValueSet();
            dimV.setValue(dimName, (Object)key);
            IEntityRow row = eTable.findByEntityKey(key);
            if (row == null) continue;
            QueryEntityData ed = this.getEntityData(row, eTable);
            entityRows.add(ed);
        }
        QueryEntityData curEd = this.getEntityData(currentRow, eTable);
        entityRows.add(curEd);
        return entityRows;
    }

    QueryEntityData getEntityData(IEntityRow row, IEntityTable entityTable) {
        QueryEntityData entityData = new QueryEntityData();
        entityData.setId(row.getEntityKeyData());
        entityData.setTitle(row.getTitle());
        int childCount = 0;
        if (entityTable.getChildRows(row.getEntityKeyData()) != null) {
            childCount = entityTable.getChildRows(row.getEntityKeyData()).size();
        }
        entityData.setChildCount(childCount);
        entityData.setIsLeaf(childCount == 0);
        return entityData;
    }

    public String initEntityTree(QueryDimensionDefine dimension, DimensionValueSet dateValSet) {
        String entityViewKey = dimension.getViewId();
        if (dimension == null || entityViewKey == null) {
            return null;
        }
        String result = "";
        int count = 0;
        JSONObject object = new JSONObject();
        try {
            List<Object> entityRows = new ArrayList();
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
            IEntityTable entityTable = QueryHelper.getEntityTableOnce(entityView, dateValSet);
            count = entityTable.getTotalCount();
            List<QuerySelectItem> defaultItems = dimension.getDefaultItems();
            LinkedHashMap<String, String> defaultItemKeys = new LinkedHashMap<String, String>();
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<Integer, List<String>> loadingKeys = new LinkedHashMap<Integer, List<String>>();
            if (defaultItems != null && defaultItems.size() > 0) {
                QueryHelper helper = new QueryHelper();
                String dimName = QueryHelper.getDimName(entityView);
                for (QuerySelectItem item : defaultItems) {
                    String key = item.getCode().toString();
                    defaultItemKeys.put(key, key);
                    this.initDefaultKeys(entityTable, key, loadingKeys);
                }
            } else {
                List<QuerySelectItem> selectItems = dimension.getSelectItems();
                if (selectItems != null && selectItems.size() > 0) {
                    QueryHelper helper = new QueryHelper();
                    String dimName = QueryHelper.getDimName(entityView);
                    for (QuerySelectItem item : selectItems) {
                        String key = item.getCode().toString();
                        defaultItemKeys.put(key, key);
                        this.initDefaultKeys(entityTable, key, loadingKeys);
                    }
                }
            }
            List rows = entityTable.getRootRows();
            entityRows = this.getRows(entityTable, rows, defaultItemKeys, 0, null, 0, loadingKeys);
            result = mapper.writeValueAsString(entityRows);
            object.put("count", count);
            object.put("result", (Object)result);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return object.toString();
    }

    private void initDefaultKeys(IEntityTable entityTable, String key, Map<Integer, List<String>> loadingKeys) {
        IEntityRow row = entityTable.findByEntityKey(key);
        if (row != null) {
            String[] parents = row.getParentsEntityKeyDataPath();
            if (parents != null && parents.length > 0) {
                for (int i = 0; i < parents.length; ++i) {
                    this.appendDefaultRow(i, parents[i], loadingKeys);
                }
                this.appendDefaultRow(parents.length, key, loadingKeys);
            } else {
                this.appendDefaultRow(0, key, loadingKeys);
            }
        }
    }

    public String getPeriodListByMasterKey(String entityViewKey) throws Exception {
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
        boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId());
        ArrayList<QueryEntityData> result = new ArrayList<QueryEntityData>();
        if (periodView) {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityView.getEntityId());
            List periodItems = periodProvider.getPeriodItems();
            for (IPeriodRow row : periodItems) {
                QueryEntityData data = new QueryEntityData();
                data.setId(row.getCode());
                data.setTitle(row.getTitle());
                result.add(data);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }

    public String getContentByMasterKey(String entityViewKey) throws Exception {
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
        ObjectMapper mapper = new ObjectMapper();
        IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
        if (entityTable.getMaxDepth() > 1) {
            List<QueryEntityData> result = this.getRoots(entityView);
            return mapper.writeValueAsString(result);
        }
        List<QueryEntityData> result = this.getContentByMasterKey(entityView);
        return mapper.writeValueAsString(result);
    }

    private List<QueryEntityData> getContentByMasterKey(EntityViewDefine entityView) throws RuntimeException, DataTypeException {
        IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
        List rows = entityTable.getAllRows();
        return this.getRows(entityTable, rows, null);
    }

    private List<QueryEntityData> getRoots(EntityViewDefine entityView) throws Exception {
        IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
        List rows = entityTable.getRootRows();
        return this.getRows(entityTable, rows, null);
    }

    private List<QueryEntityData> getRows(IEntityTable entityTable, List<IEntityRow> rows, Map<String, String> defaultKeys) {
        ArrayList<QueryEntityData> entityDatas = new ArrayList<QueryEntityData>();
        if (defaultKeys == null) {
            defaultKeys = new LinkedHashMap<String, String>();
        }
        int defaultSize = defaultKeys.size();
        int inListItemCount = 0;
        for (IEntityRow row : rows) {
            List childs;
            if (StringUtils.isEmpty((String)row.getTitle())) continue;
            QueryEntityData ed = new QueryEntityData();
            entityDatas.add(ed);
            String keyData = row.getEntityKeyData();
            ed.setId(keyData);
            ed.setTitle(row.getTitle());
            int childCount = 0;
            if (defaultSize > 0 && inListItemCount < defaultSize && defaultKeys.containsKey(keyData)) {
                ++inListItemCount;
            }
            if ((childs = entityTable.getChildRows(keyData)) != null) {
                List<QueryEntityData> childRowDatas;
                childCount = childs.size();
                if (inListItemCount < defaultSize && !(childRowDatas = this.getRows(entityTable, childs, defaultKeys)).isEmpty()) {
                    ed.setChilds(childRowDatas);
                }
            }
            ed.setChildCount(childCount);
            ed.setIsLeaf(childCount == 0);
        }
        return entityDatas;
    }

    public List<QueryEntityData> getChildren(String entityViewKey, String entityKey, DimensionValueSet dateValSet, int start) throws Exception {
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
        IEntityTable entityTable = QueryHelper.getEntityTable(entityView, dateValSet);
        List rows = StringUtils.isEmpty((String)entityKey) || entityKey.equals("null") ? entityTable.getRootRows() : entityTable.getChildRows(entityKey.toString());
        return this.getRows(entityTable, rows, null, start, entityKey, 0, null);
    }

    private void appendDefaultRow(int level, String entityKey, Map<Integer, List<String>> loadingKeys) {
        List<String> keys = new ArrayList<String>();
        if (loadingKeys.containsKey(level)) {
            keys = loadingKeys.get(level);
        }
        if (keys.contains(entityKey)) {
            return;
        }
        keys.add(entityKey);
        loadingKeys.put(level, keys);
    }

    private List<QueryEntityData> getRows(IEntityTable entityTable, List<IEntityRow> rows, Map<String, String> defaultKeys, int start, String parentKey, int level, Map<Integer, List<String>> loadingKeys) {
        String keyData;
        List<String> levelDefaultKeys;
        int defaultSize;
        int size;
        ArrayList<QueryEntityData> entityDatas = new ArrayList<QueryEntityData>();
        int loadSize = 200;
        boolean inListItemCount = false;
        int end = size = rows.size();
        boolean isEnd = true;
        if (defaultKeys == null || defaultKeys.size() == 0) {
            defaultKeys = new LinkedHashMap<String, String>();
            if (size - start > loadSize) {
                end = start + loadSize;
                isEnd = end == size - 1;
            }
        }
        boolean hasDefault = (defaultSize = defaultKeys.size()) > 0;
        List<String> list = levelDefaultKeys = hasDefault ? loadingKeys.get(level) : null;
        if ((levelDefaultKeys == null || levelDefaultKeys.size() == 0) && size - start > loadSize) {
            end = start + loadSize;
            isEnd = end == size - 1;
        }
        int index = start;
        int count = 0;
        while (index < end) {
            List<String> nextlevelDefaultKeys;
            IEntityRow row = rows.get(index);
            if (StringUtils.isEmpty((String)row.getTitle())) continue;
            ++count;
            QueryEntityData ed = new QueryEntityData();
            entityDatas.add(ed);
            keyData = row.getEntityKeyData();
            boolean isDefaultKey = levelDefaultKeys != null && levelDefaultKeys.contains(keyData);
            ed.setId(keyData);
            ed.setTitle(row.getTitle());
            int childCount = 0;
            List childs = entityTable.getChildRows(keyData);
            if (childs == null) continue;
            childCount = childs.size();
            List<String> list2 = nextlevelDefaultKeys = isDefaultKey ? loadingKeys.get(level + 1) : null;
            if (nextlevelDefaultKeys != null && nextlevelDefaultKeys.size() > 0) {
                List<QueryEntityData> childRowDatas = this.getRows(entityTable, childs, defaultKeys, 0, keyData, level + 1, loadingKeys);
                if (!childRowDatas.isEmpty()) {
                    ed.setChilds(childRowDatas);
                    ed.setChildCount(childCount);
                    ed.setIsLeaf(childCount == 0);
                    ed.setCode("-1");
                    ++index;
                }
            } else {
                ed.setChildCount(childCount);
                ed.setIsLeaf(childCount == 0);
                ed.setCode("-1");
                ++index;
            }
            if (!hasDefault || !isDefaultKey) continue;
            levelDefaultKeys.remove(keyData);
            if (levelDefaultKeys.size() != 0 || count < loadSize) continue;
            isEnd = index == end;
            break;
        }
        if (!isEnd) {
            QueryEntityData ed = new QueryEntityData();
            entityDatas.add(ed);
            IEntityRow row = rows.get(index);
            keyData = row.getEntityKeyData();
            String pageCode = parentKey + ";" + index + ";laznode";
            ed.setId(keyData);
            ed.setCode(pageCode);
            ed.setTitle("\u70b9\u51fb\u7ee7\u7eed\u52a0\u8f7d...");
            ed.setIsLeaf(true);
        }
        return entityDatas;
    }

    public QueryEntityData getParentPath(String entityViewKey, String entityKey, DimensionValueSet dateValSet) throws Exception {
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
        IEntityTable entityTable = QueryHelper.getEntityTableOnce(entityView, dateValSet);
        IEntityRow curNode = entityTable.findByEntityKey(entityKey.toString());
        QueryEntityData parentPathNode = this.getParentPathRows(entityTable, curNode, null);
        return parentPathNode;
    }

    private QueryEntityData getParentPathRows(IEntityTable entityTable, IEntityRow curNode, List<QueryEntityData> queryRows) {
        try {
            if (curNode.getParentEntityKey() != null) {
                List curLevelRows = entityTable.getChildRows(curNode.getParentEntityKey());
                ArrayList<QueryEntityData> curLevelRowsQuery = new ArrayList<QueryEntityData>();
                for (IEntityRow node : curLevelRows) {
                    QueryEntityData queryNode = new QueryEntityData();
                    curLevelRowsQuery.add(queryNode);
                    if (queryRows != null && node.getEntityKeyData().equals(curNode.getEntityKeyData())) {
                        queryNode.setChilds(queryRows);
                    }
                    List nodeChilds = entityTable.getChildRows(node.getEntityKeyData());
                    queryNode.setId(node.getEntityKeyData());
                    queryNode.setTitle(node.getTitle());
                    queryNode.setChildCount(nodeChilds.size());
                    queryNode.setIsLeaf(nodeChilds.size() == 0);
                }
                IEntityRow parentNode = entityTable.findByEntityKey(curNode.getParentEntityKey());
                QueryEntityData parent = this.getParentPathRows(entityTable, parentNode, curLevelRowsQuery);
                if (parent == null) {
                    parent = new QueryEntityData();
                    parent.setChilds(curLevelRowsQuery);
                    parent.setId(parentNode.getEntityKeyData());
                    parent.setTitle(parentNode.getTitle());
                    parent.setChildCount(curLevelRowsQuery.size());
                    parent.setIsLeaf(curLevelRowsQuery.size() == 0);
                }
                return parent;
            }
            return null;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private String queryPeriodBizKey(String viewKey) {
        IPeriodEntity periodEntity = this.queryPeriodEntity(viewKey);
        String referFieldId = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
        return referFieldId;
    }

    private IPeriodEntity queryPeriodEntity(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine viewDefine = this.iEntityViewRunTimeController.buildEntityView(viewKey);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewDefine.getEntityId());
        return periodEntity;
    }
}

