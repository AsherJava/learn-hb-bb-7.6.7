/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFolder
 *  com.jiuqi.nr.query.datascheme.extend.DataTableNode
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFolder;
import com.jiuqi.nr.query.datascheme.extend.DataTableNode;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaModelMetaDataProvider
implements IDataTableMetaDataProvider {
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private DataModelService dataModelService;

    public List<DataTableNode> getTables(String parent) throws DataTableAdaptException {
        List tableModels = this.dataModelService.getTableModelDefinesByCatalogID(parent);
        ArrayList<DataTableNode> nodes = new ArrayList<DataTableNode>();
        for (TableModelDefine tableModel : tableModels) {
            DataTableNode node = this.createTableNode(tableModel);
            nodes.add(node);
        }
        return nodes;
    }

    private DataTableNode createTableNode(TableModelDefine tableModel) {
        DataTableNode node = new DataTableNode();
        node.setKey(tableModel.getCode());
        node.setName(tableModel.getCode());
        node.setTitle(tableModel.getTitle());
        node.setType("nvwaDataModel");
        return node;
    }

    public List<DataTableFolder> getTableFolders(String parent) throws DataTableAdaptException {
        List catalogs = parent == null ? this.catalogModelService.getRootCatalogModelDefines() : this.catalogModelService.getChildrenCatalogModelDefine(parent);
        ArrayList<DataTableFolder> folders = new ArrayList<DataTableFolder>();
        for (DesignCatalogModelDefine catalog : catalogs) {
            DataTableFolder folder = this.createFolderNode(catalog);
            folders.add(folder);
        }
        return folders;
    }

    private DataTableFolder createFolderNode(DesignCatalogModelDefine catalog) {
        DataTableFolder folder = new DataTableFolder();
        folder.setId(catalog.getID());
        folder.setTitle(catalog.getTitle());
        folder.setType("nvwaDataModel");
        return folder;
    }

    public List<String> getPath(String tableCodeOrKey) throws DataTableAdaptException {
        ArrayList<String> path = new ArrayList<String>();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(tableCodeOrKey);
        if (tableModel == null) {
            tableModel = this.dataModelService.getTableModelDefineById(tableCodeOrKey);
        }
        DesignCatalogModelDefine parent = null;
        if (StringUtils.isNotEmpty((String)tableModel.getCatalogID())) {
            parent = this.catalogModelService.getCatalogModelDefine(tableModel.getCatalogID());
        }
        if (parent == null) {
            return path;
        }
        path.add(parent.getID());
        while (StringUtils.isNotEmpty((String)parent.getParentID())) {
            if ((parent = this.catalogModelService.getCatalogModelDefine(parent.getParentID())) == null) continue;
            path.add(parent.getID());
        }
        Collections.reverse(path);
        return path;
    }

    public List<String> getTitlePath(String tableCodeOrKey) throws DataTableAdaptException {
        ArrayList<String> path = new ArrayList<String>();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(tableCodeOrKey);
        if (tableModel == null) {
            tableModel = this.dataModelService.getTableModelDefineById(tableCodeOrKey);
        }
        DesignCatalogModelDefine parent = null;
        if (StringUtils.isNotEmpty((String)tableModel.getCatalogID())) {
            parent = this.catalogModelService.getCatalogModelDefine(tableModel.getCatalogID());
        }
        if (parent == null) {
            return path;
        }
        path.add(parent.getTitle());
        while (StringUtils.isNotEmpty((String)parent.getParentID())) {
            if ((parent = this.catalogModelService.getCatalogModelDefine(parent.getParentID())) == null) continue;
            path.add(parent.getTitle());
        }
        Collections.reverse(path);
        return path;
    }

    public List<DataTableNode> search(String keyStr) throws DataTableAdaptException {
        List allTables = this.dataModelService.getTableModelDefines();
        ArrayList<DataTableNode> result = new ArrayList<DataTableNode>();
        for (TableModelDefine tableModel : allTables) {
            if (tableModel.getCode().contains(keyStr) || tableModel.getTitle().contains(keyStr)) {
                result.add(this.createTableNode(tableModel));
            }
            if (result.size() < 200) continue;
            break;
        }
        return result;
    }
}

