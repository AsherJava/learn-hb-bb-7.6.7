/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.basedata.select.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.basedata.select.bean.BaseDataAttribute;
import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.basedata.select.bean.BaseDataNodePage;
import com.jiuqi.nr.basedata.select.exception.BaseDataException;
import com.jiuqi.nr.basedata.select.param.BaseDataOpenResponse;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfoExtend;
import com.jiuqi.nr.basedata.select.param.BaseDataResponse;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectParamService;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDataSelectServiceImpl
implements IBaseDataSelectService {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataSelectServiceImpl.class);
    @Autowired
    private IBaseDataSelectParamService baseDataSelectParamService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static final String ROOT = "root";

    @Override
    public BaseDataResponse getBaseDataTree(BaseDataQueryInfo baseDataQueryInfo) {
        BaseDataResponse baseDataResponse = new BaseDataResponse();
        ITree<BaseDataInfo> baseDataTree = new ITree<BaseDataInfo>();
        String successMessage = "success";
        IEntityTable entityTable = null;
        try {
            entityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, false, false);
        }
        catch (BaseDataException e) {
            baseDataResponse.setMessage(e.getErrorMes());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), (Throwable)((Object)e));
            return baseDataResponse;
        }
        String linkageType = baseDataQueryInfo.getLinkageType();
        List<String> filterFields = this.baseDataSelectParamService.getFilterFields(baseDataQueryInfo.getEntityKey(), baseDataQueryInfo.getFieldList());
        List<IBaseDataSelectFilter> baseDataFilters = this.baseDataSelectParamService.getEnableBaseDataFilterList(baseDataQueryInfo.getBaseDataSelectFilterInfos());
        boolean enableLocate = false;
        if (StringUtils.isNotEmpty((String)baseDataQueryInfo.getEntityKeyData()) && entityTable.findByEntityKey(baseDataQueryInfo.getEntityKeyData()) != null) {
            enableLocate = true;
        }
        if (enableLocate) {
            if (linkageType.equals("2") || linkageType.equals("3")) {
                baseDataTree = this.buildLocateLevelTree(entityTable, baseDataQueryInfo, baseDataQueryInfo.getEntityKeyData(), filterFields);
            } else if (linkageType.equals("1")) {
                baseDataTree = this.getTreeStructure(entityTable, baseDataQueryInfo, ROOT, filterFields);
            } else {
                Map<String, BaseDataNodePage> keyPageMap = this.getNodePage(entityTable, baseDataQueryInfo);
                baseDataTree = this.buildLocateTree(entityTable, baseDataQueryInfo, ROOT, filterFields, keyPageMap);
            }
        } else {
            baseDataTree = baseDataQueryInfo.isAllChildren() ? this.getAllLevelTree(entityTable, baseDataQueryInfo, baseDataFilters, baseDataQueryInfo.getParentKey(), filterFields) : this.getDirectLevelTree(entityTable, baseDataQueryInfo, baseDataFilters, baseDataQueryInfo.getParentKey(), filterFields);
        }
        baseDataResponse.setBaseDataTree(baseDataTree);
        baseDataResponse.setMessage(successMessage);
        return baseDataResponse;
    }

    private ITree<BaseDataInfo> getDirectLevelTree(IEntityTable entityTable, BaseDataQueryInfo baseDataQueryInfo, List<IBaseDataSelectFilter> baseDataFilters, String parentKeyData, List<String> filterFields) {
        ITree<BaseDataInfo> parentITreeNode = new ITree<BaseDataInfo>();
        List<IEntityRow> childRows = new ArrayList();
        if (parentKeyData.equals(ROOT)) {
            parentITreeNode = this.createBaseDataRootNode(baseDataQueryInfo.getEntityKey());
            childRows = entityTable.getRootRows();
        } else {
            IEntityRow parentRow = entityTable.findByEntityKey(parentKeyData);
            if (parentRow == null) {
                return parentITreeNode;
            }
            parentITreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, parentRow, filterFields);
            String linkageType = baseDataQueryInfo.getLinkageType();
            if (parentITreeNode.isLeaf() && !linkageType.equals("2") && !linkageType.equals("3")) {
                childRows.add(parentRow);
            } else {
                childRows = entityTable.getChildRows(parentKeyData);
            }
        }
        ArrayList<ITree<BaseDataInfo>> childITreeNodes = new ArrayList<ITree<BaseDataInfo>>();
        for (IEntityRow entityRow : childRows) {
            ITree<BaseDataInfo> currentTreeNode = null;
            if (!this.passBaseDataFilters(baseDataFilters, entityRow)) continue;
            currentTreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, entityRow, filterFields);
            if (baseDataQueryInfo.isSearchLeaf() && !currentTreeNode.isLeaf()) continue;
            childITreeNodes.add(currentTreeNode);
        }
        parentITreeNode.setSelected(true);
        if (baseDataFilters != null && baseDataFilters.size() > 0) {
            List<ITree<BaseDataInfo>> sortChildITreeNodes = this.sortForBaseDataFilters(baseDataFilters, childITreeNodes);
            this.setChildNodeList(baseDataQueryInfo.getPagerInfo(), parentITreeNode, sortChildITreeNodes, 0);
        } else {
            this.setChildNodeList(baseDataQueryInfo.getPagerInfo(), parentITreeNode, childITreeNodes, 0);
        }
        this.buildReferEntityTitle(baseDataQueryInfo.getEntityKey(), parentITreeNode, filterFields);
        return parentITreeNode;
    }

    private ITree<BaseDataInfo> getAllLevelTree(IEntityTable entityTable, BaseDataQueryInfo baseDataQueryInfo, List<IBaseDataSelectFilter> baseDataFilters, String parentKeyData, List<String> filterFields) {
        ITree<BaseDataInfo> parentITreeNode = new ITree<BaseDataInfo>();
        ArrayList<ITree<BaseDataInfo>> allTreeNodes = new ArrayList<ITree<BaseDataInfo>>();
        if (parentKeyData.equals(ROOT)) {
            parentITreeNode = this.createBaseDataRootNode(baseDataQueryInfo.getEntityKey());
            List rootRows = entityTable.getRootRows();
            for (IEntityRow rootRow : rootRows) {
                if (this.passBaseDataFilters(baseDataFilters, rootRow)) {
                    ITree<BaseDataInfo> rootTreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, rootRow, filterFields);
                    if (!baseDataQueryInfo.isSearchLeaf() || rootTreeNode.isLeaf()) {
                        allTreeNodes.add(rootTreeNode);
                    }
                }
                List allChildRows = entityTable.getAllChildRows(rootRow.getEntityKeyData());
                for (IEntityRow childRow : allChildRows) {
                    if (!this.passBaseDataFilters(baseDataFilters, childRow)) continue;
                    ITree<BaseDataInfo> childTreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, childRow, filterFields);
                    if (baseDataQueryInfo.isSearchLeaf() && !childTreeNode.isLeaf()) continue;
                    allTreeNodes.add(childTreeNode);
                }
            }
        } else {
            IEntityRow parentRow = entityTable.findByEntityKey(parentKeyData);
            if (parentRow == null) {
                return parentITreeNode;
            }
            parentITreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, parentRow, filterFields);
            List<IEntityRow> allChildRows = new ArrayList();
            String linkageType = baseDataQueryInfo.getLinkageType();
            if (parentITreeNode.isLeaf() && !linkageType.equals("2") && !linkageType.equals("3")) {
                allChildRows.add(parentRow);
            } else {
                allChildRows = entityTable.getAllChildRows(parentKeyData);
            }
            for (IEntityRow childRow : allChildRows) {
                if (!this.passBaseDataFilters(baseDataFilters, childRow)) continue;
                ITree<BaseDataInfo> childTreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, childRow, filterFields);
                if (baseDataQueryInfo.isSearchLeaf() && !childTreeNode.isLeaf()) continue;
                allTreeNodes.add(childTreeNode);
            }
        }
        parentITreeNode.setSelected(true);
        if (baseDataFilters != null && baseDataFilters.size() > 0) {
            List<ITree<BaseDataInfo>> sortAllTreeNodes = this.sortForBaseDataFilters(baseDataFilters, allTreeNodes);
            this.setChildNodeList(baseDataQueryInfo.getPagerInfo(), parentITreeNode, sortAllTreeNodes, 0);
        } else {
            this.setChildNodeList(baseDataQueryInfo.getPagerInfo(), parentITreeNode, allTreeNodes, 0);
        }
        this.buildReferEntityTitle(baseDataQueryInfo.getEntityKey(), parentITreeNode, filterFields);
        return parentITreeNode;
    }

    private ITree<BaseDataInfo> getTreeStructure(IEntityTable entityTable, BaseDataQueryInfo baseDataQueryInfo, String parentKeyData, List<String> filterFields) {
        ITree<BaseDataInfo> parentITreeNode = new ITree<BaseDataInfo>();
        List childRows = new ArrayList();
        if (parentKeyData.equals(ROOT)) {
            parentITreeNode = this.createBaseDataRootNode(baseDataQueryInfo.getEntityKey());
            childRows = entityTable.getRootRows();
        } else {
            IEntityRow parentRow = entityTable.findByEntityKey(parentKeyData);
            if (parentRow == null) {
                return parentITreeNode;
            }
            parentITreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, parentRow, filterFields);
            childRows = entityTable.getChildRows(parentKeyData);
        }
        ArrayList<ITree<BaseDataInfo>> childITreeNodes = new ArrayList<ITree<BaseDataInfo>>();
        for (IEntityRow entityRow : childRows) {
            ITree<BaseDataInfo> currentTreeNod = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, entityRow, filterFields);
            boolean addParent = false;
            if (!baseDataQueryInfo.isSearchLeaf() || currentTreeNod.isLeaf()) {
                childITreeNodes.add(currentTreeNod);
                addParent = true;
            }
            ITree<BaseDataInfo> currentTreeNod2 = this.getTreeStructure(entityTable, baseDataQueryInfo, entityRow.getEntityKeyData(), filterFields);
            if (addParent) {
                currentTreeNod.setChildren(currentTreeNod2.getChildren());
                ((BaseDataInfo)currentTreeNod.getData()).setTotalCount(((BaseDataInfo)currentTreeNod2.getData()).getTotalCount());
                if (!currentTreeNod.getKey().equals(baseDataQueryInfo.getEntityKeyData())) continue;
                currentTreeNod.setSelected(true);
                continue;
            }
            childITreeNodes.addAll(currentTreeNod2.getChildren());
        }
        parentITreeNode.setChildren(childITreeNodes);
        ((BaseDataInfo)parentITreeNode.getData()).setTotalCount(childITreeNodes.size());
        this.buildReferEntityTitle(baseDataQueryInfo.getEntityKey(), parentITreeNode, filterFields);
        return parentITreeNode;
    }

    private ITree<BaseDataInfo> buildLocateLevelTree(IEntityTable entityTable, BaseDataQueryInfo baseDataQueryInfo, String entityKeyData, List<String> filterFields) {
        ITree<BaseDataInfo> parentITreeNode = new ITree<BaseDataInfo>();
        IEntityRow nodeRow = entityTable.findByEntityKey(entityKeyData);
        List childRows = new ArrayList();
        if (nodeRow == null) {
            return parentITreeNode;
        }
        if (StringUtils.isEmpty((String)baseDataQueryInfo.getParentKey()) || baseDataQueryInfo.getParentKey().equals(ROOT)) {
            parentITreeNode = this.createBaseDataRootNode(baseDataQueryInfo.getEntityKey());
            childRows = entityTable.getRootRows();
        } else {
            IEntityRow parentRow = entityTable.findByEntityKey(baseDataQueryInfo.getParentKey());
            parentITreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, parentRow, filterFields);
            childRows = entityTable.getChildRows(parentRow.getEntityKeyData());
        }
        int rowNum = 0;
        int currentPage = 0;
        ArrayList<ITree<BaseDataInfo>> childITreeNodes = new ArrayList<ITree<BaseDataInfo>>();
        for (int rowIndex = 0; rowIndex < childRows.size(); ++rowIndex) {
            IEntityRow childRow = (IEntityRow)childRows.get(rowIndex);
            ITree<BaseDataInfo> childNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, childRow, filterFields);
            childITreeNodes.add(childNode);
            if (!childRow.getEntityKeyData().equals(entityKeyData)) continue;
            rowNum = rowIndex + 1;
            childNode.setSelected(true);
        }
        PagerInfo pagerInfo = baseDataQueryInfo.getPagerInfo();
        PagerInfo locatPagerInfo = new PagerInfo();
        if (pagerInfo != null) {
            int pageSize = pagerInfo.getLimit();
            if (rowNum < pageSize) {
                currentPage = 0;
            } else {
                int a = rowNum % pageSize;
                int b = rowNum / pageSize;
                currentPage = a == 0 ? b - 1 : b;
            }
            locatPagerInfo.setLimit(pageSize);
            locatPagerInfo.setOffset(currentPage);
        } else {
            locatPagerInfo = null;
        }
        this.setChildNodeList(locatPagerInfo, parentITreeNode, childITreeNodes, 0);
        this.buildReferEntityTitle(baseDataQueryInfo.getEntityKey(), parentITreeNode, filterFields);
        return parentITreeNode;
    }

    private ITree<BaseDataInfo> buildLocateTree(IEntityTable entityTable, BaseDataQueryInfo baseDataQueryInfo, String parentKeyData, List<String> filterFields, Map<String, BaseDataNodePage> keyPageMap) {
        ITree<BaseDataInfo> parentITreeNode = new ITree<BaseDataInfo>();
        BaseDataNodePage baseDataExpand = keyPageMap.get(parentKeyData);
        if (baseDataExpand != null) {
            List childRows = new ArrayList();
            ArrayList<ITree<BaseDataInfo>> childITreeNodes = new ArrayList<ITree<BaseDataInfo>>();
            if (parentKeyData.equals(ROOT)) {
                parentITreeNode = this.createBaseDataRootNode(baseDataQueryInfo.getEntityKey());
                childRows = entityTable.getRootRows();
            } else {
                IEntityRow parentRow = entityTable.findByEntityKey(parentKeyData);
                if (parentRow == null) {
                    return parentITreeNode;
                }
                parentITreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, parentRow, filterFields);
                childRows = entityTable.getChildRows(parentKeyData);
            }
            for (IEntityRow entityRow : childRows) {
                ITree<BaseDataInfo> childrenTreeNode;
                ITree<BaseDataInfo> currentTreeNode = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, entityRow, filterFields);
                childITreeNodes.add(currentTreeNode);
                if (baseDataExpand == null || baseDataExpand.getChildKeyData() == null || !baseDataExpand.getChildKeyData().equals(entityRow.getEntityKeyData()) || (childrenTreeNode = this.buildLocateTree(entityTable, baseDataQueryInfo, entityRow.getEntityKeyData(), filterFields, keyPageMap)) == null) continue;
                if (childrenTreeNode.getData() != null) {
                    currentTreeNode.setChildren(childrenTreeNode.getChildren());
                    ((BaseDataInfo)currentTreeNode.getData()).setContinuePaging(((BaseDataInfo)childrenTreeNode.getData()).isContinuePaging());
                    ((BaseDataInfo)currentTreeNode.getData()).setCurrentPage(((BaseDataInfo)childrenTreeNode.getData()).getCurrentPage());
                    ((BaseDataInfo)currentTreeNode.getData()).setTotalCount(((BaseDataInfo)childrenTreeNode.getData()).getTotalCount());
                    continue;
                }
                currentTreeNode.setSelected(true);
            }
            this.setChildNodeList(baseDataExpand == null ? null : baseDataExpand.getPagerInfo(), parentITreeNode, childITreeNodes, entityTable.getMaxDepth() == 1 ? 0 : 1);
        }
        this.buildReferEntityTitle(baseDataQueryInfo.getEntityKey(), parentITreeNode, filterFields);
        return parentITreeNode;
    }

    private boolean passBaseDataFilters(List<IBaseDataSelectFilter> baseDataFilters, IEntityRow entityRow) {
        boolean matchSuccess = true;
        if (baseDataFilters != null && baseDataFilters.size() > 0) {
            for (int index = 0; index < baseDataFilters.size(); ++index) {
                if (baseDataFilters.get(index).accept(entityRow)) continue;
                matchSuccess = false;
                break;
            }
        }
        return matchSuccess;
    }

    private List<ITree<BaseDataInfo>> sortForBaseDataFilters(List<IBaseDataSelectFilter> baseDataFilters, List<ITree<BaseDataInfo>> childITreeNodes) {
        ArrayList<ITree<BaseDataInfo>> sortChildITreeNodes = new ArrayList<ITree<BaseDataInfo>>();
        if (baseDataFilters != null && baseDataFilters.size() > 0 && childITreeNodes != null && childITreeNodes.size() > 0) {
            IBaseDataSelectFilter baseDataSelectFilter = baseDataFilters.get(0);
            List<String> entryList = baseDataSelectFilter.getEntryList();
            if (entryList == null) {
                return childITreeNodes;
            }
            if (entryList != null && entryList.size() > 0 && childITreeNodes != null && childITreeNodes.size() > 0) {
                Map childITreeNodeMap = childITreeNodes.stream().collect(Collectors.toMap(ITree::getKey, Function.identity(), (key1, key2) -> key2));
                for (int i = 0; i < entryList.size(); ++i) {
                    String entryKey = entryList.get(i);
                    if (!childITreeNodeMap.containsKey(entryKey)) continue;
                    ITree childITreeNode = (ITree)childITreeNodeMap.get(entryKey);
                    sortChildITreeNodes.add((ITree<BaseDataInfo>)childITreeNode);
                }
            }
        }
        return sortChildITreeNodes;
    }

    private Map<String, BaseDataNodePage> getNodePage(IEntityTable iEntityTable, BaseDataQueryInfo baseDataQueryInfo) {
        String entityKeyData = baseDataQueryInfo.getEntityKeyData();
        IEntityRow nodeRow = iEntityTable.findByEntityKey(entityKeyData);
        HashedMap<String, BaseDataNodePage> keyPageMap = new HashedMap<String, BaseDataNodePage>();
        PagerInfo pagerInfo = baseDataQueryInfo.getPagerInfo();
        if (StringUtils.isEmpty((String)entityKeyData) || nodeRow == null) {
            BaseDataNodePage baseDataExpand = new BaseDataNodePage();
            if (pagerInfo != null) {
                PagerInfo nodePagerInfo = new PagerInfo();
                nodePagerInfo.setLimit(pagerInfo.getLimit());
                nodePagerInfo.setOffset(0);
                baseDataExpand.setPagerInfo(nodePagerInfo);
            }
            baseDataExpand.setParentKeyData(ROOT);
            keyPageMap.put(ROOT, baseDataExpand);
        } else {
            String[] parentsEntityKeyDatas = nodeRow.getParentsEntityKeyDataPath();
            ArrayList<String> parentKeys = new ArrayList<String>();
            parentKeys.add(0, ROOT);
            List<String> alList = Arrays.asList(parentsEntityKeyDatas);
            parentKeys.addAll(alList);
            for (int keyIndex = 0; keyIndex < parentKeys.size(); ++keyIndex) {
                int rowNum = 0;
                int currentPage = 0;
                String preKeyData = (String)parentKeys.get(keyIndex);
                String nextKeyData = "";
                nextKeyData = parentKeys.size() <= keyIndex + 1 ? entityKeyData : (String)parentKeys.get(keyIndex + 1);
                BaseDataNodePage baseDataExpand = new BaseDataNodePage();
                if (pagerInfo != null) {
                    int pageSize = pagerInfo.getLimit();
                    PagerInfo locatePagerInfo = new PagerInfo();
                    locatePagerInfo.setLimit(pageSize);
                    List childRows = new ArrayList();
                    childRows = preKeyData.equals(ROOT) ? iEntityTable.getRootRows() : iEntityTable.getChildRows(preKeyData);
                    for (int rowIndex = 0; rowIndex < childRows.size(); ++rowIndex) {
                        IEntityRow childRow = (IEntityRow)childRows.get(rowIndex);
                        if (!childRow.getEntityKeyData().equals(nextKeyData)) continue;
                        rowNum = rowIndex + 1;
                        break;
                    }
                    if (rowNum < pageSize) {
                        currentPage = 0;
                    } else {
                        int a = rowNum % pageSize;
                        int b = rowNum / pageSize;
                        currentPage = a == 0 ? b - 1 : b;
                    }
                    locatePagerInfo.setOffset(currentPage);
                    baseDataExpand.setPagerInfo(locatePagerInfo);
                }
                baseDataExpand.setParentKeyData(preKeyData);
                baseDataExpand.setChildKeyData(nextKeyData);
                keyPageMap.put(preKeyData, baseDataExpand);
            }
        }
        return keyPageMap;
    }

    private void setChildNodeList(PagerInfo pagerInfo, ITree<BaseDataInfo> parentITreeNode, List<ITree<BaseDataInfo>> childITreeNodes, int type) {
        int total = childITreeNodes.size();
        ((BaseDataInfo)parentITreeNode.getData()).setTotalCount(total);
        if (pagerInfo != null) {
            int currentPage = pagerInfo.getOffset();
            int pageSize = pagerInfo.getLimit();
            ((BaseDataInfo)parentITreeNode.getData()).setCurrentPage(currentPage);
            if (total <= pageSize) {
                parentITreeNode.setChildren(childITreeNodes);
                ((BaseDataInfo)parentITreeNode.getData()).setContinuePaging(false);
            } else {
                int start = currentPage * pageSize;
                int end = start + pageSize;
                if (start > total) {
                    start = total;
                    end = total;
                }
                if (end > total) {
                    end = total;
                }
                int useNum = (currentPage + 1) * pageSize;
                ((BaseDataInfo)parentITreeNode.getData()).setContinuePaging(total - useNum > 0);
                if (type == 0) {
                    List<ITree<BaseDataInfo>> partNodes = childITreeNodes.subList(start, end);
                    parentITreeNode.setChildren(partNodes);
                } else {
                    parentITreeNode.setChildren(childITreeNodes.subList(0, end));
                }
            }
        } else {
            ((BaseDataInfo)parentITreeNode.getData()).setContinuePaging(false);
            parentITreeNode.setChildren(childITreeNodes);
        }
    }

    private ITree<BaseDataInfo> createBaseDataRootNode(String entityKey) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
        BaseDataInfo baseDataInfo = new BaseDataInfo();
        baseDataInfo.setKey(entityDefine.getId());
        baseDataInfo.setCode(entityDefine.getCode());
        baseDataInfo.setTitle(entityDefine.getTitle());
        ITree baseDataTreeInfo = new ITree((INode)baseDataInfo);
        baseDataTreeInfo.setLeaf(false);
        return baseDataTreeInfo;
    }

    private ITree<BaseDataInfo> createBaseDataTreeNode(String entityKey, IEntityTable entityTable, IEntityRow entityRow, List<String> fieldList) {
        String[] parentspath;
        BaseDataInfo baseDataInfo = new BaseDataInfo();
        baseDataInfo.setKey(entityRow.getEntityKeyData());
        baseDataInfo.setCode(entityRow.getCode());
        baseDataInfo.setTitle(entityRow.getTitle());
        baseDataInfo.setParentId(entityRow.getParentEntityKey());
        Object entityOrder = entityRow.getEntityOrder();
        if (entityOrder instanceof Double) {
            baseDataInfo.setOrder((Double)entityOrder);
        }
        if ((parentspath = entityRow.getParentsEntityKeyDataPath()) != null && parentspath.length > 0) {
            baseDataInfo.setParents(Arrays.asList(parentspath));
        }
        int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
        if (fieldList != null && fieldList.size() > 0) {
            for (int index = 0; index < fieldList.size(); ++index) {
                String fieldCode = fieldList.get(index);
                String fieldValue = this.getValue(fieldCode, entityRow, entityTable);
                baseDataInfo.getCodeDataMap().put(fieldCode, fieldValue);
            }
        }
        ITree baseDataTreeInfo = new ITree((INode)baseDataInfo);
        baseDataTreeInfo.setLeaf(directChildCount == 0);
        return baseDataTreeInfo;
    }

    private String getValue(String fieldCode, IEntityRow entityRow, IEntityTable entityTable) {
        boolean isI18NField;
        AbstractData value = entityRow.getValue(fieldCode);
        String fieldValue = value.getAsString();
        if (StringUtils.isEmpty((String)fieldValue) && (isI18NField = entityTable.isI18nAttribute(fieldCode))) {
            fieldValue = entityRow.getTitle();
        }
        return fieldValue;
    }

    private void buildReferEntityTitle(String entityKey, ITree<BaseDataInfo> baseDataTree, List<String> filterFields) {
        if (filterFields == null || filterFields.isEmpty()) {
            return;
        }
        List childrens = baseDataTree.getChildren();
        if (childrens == null || childrens.isEmpty()) {
            return;
        }
        Map<String, String> referEntityIdMap = this.baseDataSelectParamService.getReferEntityIdMap(entityKey, filterFields);
        if (referEntityIdMap == null || referEntityIdMap.size() == 0) {
            return;
        }
        HashMap<String, IEntityTable> referEntityTableMap = new HashMap<String, IEntityTable>();
        for (Map.Entry<String, String> entry : referEntityIdMap.entrySet()) {
            String entityId = entry.getValue();
            if (!StringUtils.isNotEmpty((String)entityId)) continue;
            BaseDataQueryInfo baseDataQueryInfo = new BaseDataQueryInfo();
            baseDataQueryInfo.setEntityKey(entityId);
            baseDataQueryInfo.setEntityAuth(false);
            baseDataQueryInfo.setReadAuth(false);
            IEntityTable iEntityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, true, true);
            referEntityTableMap.put(entityId, iEntityTable);
        }
        if (referEntityTableMap == null || referEntityTableMap.size() == 0) {
            return;
        }
        for (ITree children : childrens) {
            BaseDataInfo baseDataInfo = (BaseDataInfo)children.getData();
            if (baseDataInfo == null) continue;
            Map<String, String> codeDataMap = baseDataInfo.getCodeDataMap();
            for (Map.Entry<String, String> entry : referEntityIdMap.entrySet()) {
                IEntityRow entityRow;
                String code = entry.getKey();
                if (!codeDataMap.containsKey(code)) continue;
                String entityKeyData = codeDataMap.get(code);
                IEntityTable iEntityTable = (IEntityTable)referEntityTableMap.get(referEntityIdMap.get(code));
                if (iEntityTable == null || (entityRow = iEntityTable.quickFindByEntityKey(entityKeyData)) == null) continue;
                String showTitle = entityKeyData + "|" + entityRow.getTitle();
                codeDataMap.put(code, showTitle);
            }
        }
    }

    @Override
    public Map<String, Map<String, String>> getBaseDataAttributeVale(BaseDataQueryInfoExtend baseDataQueryInfoExtend) {
        List<String> fieldList;
        HashMap<String, Map<String, String>> formatMap = new HashMap<String, Map<String, String>>();
        List<String> keys = baseDataQueryInfoExtend.getKeys();
        if (keys != null && keys.size() > 0 && (fieldList = baseDataQueryInfoExtend.getFormatCode()) != null && fieldList.size() > 0) {
            boolean existTitle = fieldList.contains("TITLE");
            BaseDataQueryInfo baseDataQueryInfo = baseDataQueryInfoExtend.getBaseDataQueryInfo();
            List<String> filterFields = this.baseDataSelectParamService.getFilterFields(baseDataQueryInfo.getEntityKey(), fieldList);
            if (filterFields != null && filterFields.size() > 0) {
                IEntityTable entityTable = null;
                try {
                    entityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, false, false);
                }
                catch (BaseDataException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), (Throwable)((Object)e));
                    return formatMap;
                }
                for (String key : keys) {
                    IEntityRow entityRow = entityTable.findByEntityKey(key);
                    if (entityRow == null) continue;
                    HashedMap<String, String> codeDataMap = new HashedMap<String, String>();
                    for (String code : filterFields) {
                        String value = this.getValue(code, entityRow, entityTable);
                        if (!StringUtils.isNotEmpty((String)value)) continue;
                        codeDataMap.put(code, value);
                    }
                    if (existTitle) {
                        codeDataMap.put("TITLE", entityRow.getTitle());
                    }
                    formatMap.put(key, codeDataMap);
                }
            }
        }
        return formatMap;
    }

    @Override
    public List<ITree<BaseDataInfo>> getBaseDataEntry(BaseDataQueryInfoExtend baseDataFieldQueryInfo) {
        ArrayList<ITree<BaseDataInfo>> basedataITrees = new ArrayList<ITree<BaseDataInfo>>();
        List<String> keys = baseDataFieldQueryInfo.getKeys();
        if (keys != null && keys.size() > 0) {
            BaseDataQueryInfo baseDataQueryInfo = baseDataFieldQueryInfo.getBaseDataQueryInfo();
            List<String> filterFields = this.baseDataSelectParamService.getFilterFields(baseDataQueryInfo.getEntityKey(), baseDataQueryInfo.getFieldList());
            IEntityTable entityTable = null;
            try {
                entityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, false, false);
            }
            catch (BaseDataException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), (Throwable)((Object)e));
                return basedataITrees;
            }
            for (String key : keys) {
                IEntityRow entityRow = entityTable.findByEntityKey(key);
                if (entityRow == null) continue;
                ITree<BaseDataInfo> baseDataITree = this.createBaseDataTreeNode(baseDataQueryInfo.getEntityKey(), entityTable, entityRow, filterFields);
                basedataITrees.add(baseDataITree);
            }
        }
        return basedataITrees;
    }

    @Override
    public Map<String, List<String>> getFullPathAttributeVale(BaseDataQueryInfoExtend baseDataQueryInfoExtend) {
        HashedMap<String, List<String>> fullPathMap = new HashedMap<String, List<String>>();
        List<String> keys = baseDataQueryInfoExtend.getKeys();
        List<String> formatCode = baseDataQueryInfoExtend.getFormatCode();
        if (keys == null || keys.isEmpty() || formatCode == null || formatCode.isEmpty()) {
            return fullPathMap;
        }
        BaseDataQueryInfo baseDataQueryInfo = baseDataQueryInfoExtend.getBaseDataQueryInfo();
        IEntityTable entityTable = null;
        try {
            entityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, false, false);
        }
        catch (BaseDataException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), (Throwable)((Object)e));
            return fullPathMap;
        }
        String fullPathCode = formatCode.get(0);
        for (String entityKeyData : keys) {
            String[] parents;
            ArrayList<String> fullPathValues = new ArrayList<String>();
            IEntityRow childRow = entityTable.findByEntityKey(entityKeyData);
            if (childRow == null || (parents = childRow.getParentsEntityKeyDataPath()) == null || parents.length == 0) continue;
            for (int i = 0; i < parents.length; ++i) {
                String parentFieldValue;
                String parentKey = parents[i];
                IEntityRow parentRow = entityTable.findByEntityKey(parentKey);
                if (parentRow == null || !StringUtils.isNotEmpty((String)(parentFieldValue = this.getValue(fullPathCode, parentRow, entityTable)))) continue;
                fullPathValues.add(parentFieldValue);
            }
            String chlidFieldValue = this.getValue(fullPathCode, childRow, entityTable);
            if (StringUtils.isNotEmpty((String)chlidFieldValue)) {
                fullPathValues.add(chlidFieldValue);
            }
            fullPathMap.put(entityKeyData, fullPathValues);
        }
        return fullPathMap;
    }

    @Override
    public BaseDataOpenResponse getBaseDataOpenParam(BaseDataQueryInfo baseDataQueryInfo) {
        BaseDataOpenResponse baseDataOpenResponse = new BaseDataOpenResponse();
        String successMessage = "success";
        IEntityTable entityTable = null;
        try {
            entityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, false, false);
        }
        catch (BaseDataException e) {
            baseDataOpenResponse.setMessage(e.getErrorMes());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), (Throwable)((Object)e));
            return baseDataOpenResponse;
        }
        List<String> filterFields = this.baseDataSelectParamService.getFilterFields(baseDataQueryInfo.getEntityKey(), baseDataQueryInfo.getFieldList());
        baseDataOpenResponse.setShowAttributes(filterFields);
        List<BaseDataAttribute> baseDataAttributes = this.baseDataSelectParamService.getBaseDataAttributes(baseDataQueryInfo.getEntityKey(), baseDataQueryInfo.getDropDownFields());
        baseDataOpenResponse.setBaseDataAttributes(baseDataAttributes);
        baseDataOpenResponse.setMaxDepth(entityTable.getMaxDepth());
        String linkageType = baseDataQueryInfo.getLinkageType();
        if (linkageType.equals("2") || linkageType.equals("3")) {
            baseDataOpenResponse.setMaxDepth(1);
        }
        baseDataOpenResponse.setMessage(successMessage);
        return baseDataOpenResponse;
    }
}

