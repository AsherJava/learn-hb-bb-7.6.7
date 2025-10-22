/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFolder
 *  com.jiuqi.nr.query.datascheme.extend.DataTableNode
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.FilterDTO
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.web.ResourceTreeController
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFolder;
import com.jiuqi.nr.query.datascheme.extend.DataTableNode;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.FilterDTO;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.web.ResourceTreeController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlDSMetaDataProvider
implements IDataTableMetaDataProvider {
    @Autowired
    private ResourceTreeController resourceTreeController;
    @Autowired
    private DataSetStorageProvider dataSetStorageProvider;

    public List<DataTableNode> getTables(String parent) throws DataTableAdaptException {
        ArrayList<DataTableNode> result = new ArrayList<DataTableNode>();
        try {
            List resourceNodes = this.resourceTreeController.getChildrenByTypes(parent, "com.jiuqi.bi.dataset.sq,com.jiuqi.bi.dataset.sql");
            for (ResourceTreeNode node : resourceNodes) {
                if (node.isFolder()) continue;
                DataTableNode tableNode = this.makeTableNode(node);
                result.add(tableNode);
            }
        }
        catch (DataAnalyzeResourceException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
        return result;
    }

    public List<DataTableFolder> getTableFolders(String parent) throws DataTableAdaptException {
        ArrayList<DataTableFolder> result = new ArrayList<DataTableFolder>();
        try {
            List resourceNodes = this.resourceTreeController.getChildrenByTypes(parent, "com.jiuqi.bi.dataset.sq,com.jiuqi.bi.dataset.sql");
            for (ResourceTreeNode node : resourceNodes) {
                if (!node.isFolder()) continue;
                DataTableFolder folderNode = this.makeFolderNode(node);
                result.add(folderNode);
            }
        }
        catch (DataAnalyzeResourceException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
        return result;
    }

    public List<String> getPath(String tableCodeKey) throws DataTableAdaptException {
        try {
            String guid = tableCodeKey;
            DSModel sqlModel = this.dataSetStorageProvider.findModel(tableCodeKey);
            if (sqlModel != null) {
                guid = sqlModel._getGuid();
            }
            List pathNodes = this.resourceTreeController.getPath(guid);
            return pathNodes.stream().map(ResourceTreeNode::getGuid).collect(Collectors.toList());
        }
        catch (DataAnalyzeResourceException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
        catch (DataSetStorageException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
    }

    public List<String> getTitlePath(String tableCodeKey) throws DataTableAdaptException {
        try {
            String guid = tableCodeKey;
            DSModel sqlModel = this.dataSetStorageProvider.findModel(tableCodeKey);
            if (sqlModel != null) {
                guid = sqlModel._getGuid();
            }
            List pathNodes = this.resourceTreeController.getPath(guid);
            return pathNodes.stream().map(ResourceTreeNode::getTitle).collect(Collectors.toList());
        }
        catch (DataAnalyzeResourceException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
        catch (DataSetStorageException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
    }

    public List<DataTableNode> search(String keyStr) throws DataTableAdaptException {
        ArrayList<DataTableNode> result = new ArrayList<DataTableNode>();
        FilterDTO filter = new FilterDTO();
        filter.setFilter(keyStr);
        filter.getResourceTypes().add("com.jiuqi.bi.dataset.sq");
        try {
            List nodes = this.resourceTreeController.search(filter);
            nodes.stream().forEach(o -> result.add(this.makeTableNode(o.getNode())));
        }
        catch (DataAnalyzeResourceException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
        return result;
    }

    private DataTableNode makeTableNode(ResourceTreeNode treeNode) {
        DataTableNode node = new DataTableNode();
        node.setName(treeNode.getName());
        node.setKey(treeNode.getName());
        node.setTitle(treeNode.getTitle());
        node.setType("sqlDataSet");
        return node;
    }

    private DataTableFolder makeFolderNode(ResourceTreeNode treeNode) {
        DataTableFolder folder = new DataTableFolder();
        folder.setId(treeNode.getGuid());
        folder.setTitle(treeNode.getTitle());
        folder.setType("sqlDataSet");
        return folder;
    }
}

