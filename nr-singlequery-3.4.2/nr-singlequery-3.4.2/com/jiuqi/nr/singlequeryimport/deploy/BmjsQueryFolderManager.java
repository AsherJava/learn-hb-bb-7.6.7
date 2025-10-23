/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.singlequeryimport.deploy;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BmjsQueryFolderManager
extends AbstractFolderManager {
    @Autowired
    QueryModleService queryModleService;

    public String getRootParentId() {
        return "";
    }

    public List<FolderNode> getFolderNodes(String s) {
        ArrayList<FolderNode> folderNodes = new ArrayList<FolderNode>();
        try {
            if (null == s) {
                List<QueryModelNode> taskModel = this.queryModleService.getTaskModel();
                if (!taskModel.isEmpty()) {
                    for (QueryModelNode queryModelNode : taskModel) {
                        FolderNode folderNode = new FolderNode(queryModelNode.getId() + "#", queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
                        folderNodes.add(folderNode);
                    }
                }
            } else {
                List<QueryModelNode> groupModel;
                FolderNode folderNode;
                List<QueryModelNode> formSchemeModel;
                String[] split = s.split("#");
                if (split.length == 1 && !(formSchemeModel = this.queryModleService.getFormSchemeModel(split[0])).isEmpty()) {
                    for (QueryModelNode queryModelNode : formSchemeModel) {
                        folderNode = new FolderNode(s + queryModelNode.getId() + "#", queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
                        folderNodes.add(folderNode);
                    }
                }
                if (split.length == 2 && !(groupModel = this.queryModleService.getGroupModel(split[1])).isEmpty()) {
                    for (QueryModelNode queryModelNode : groupModel) {
                        folderNode = new FolderNode(s + queryModelNode.getId() + "#", queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
                        folderNodes.add(folderNode);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return folderNodes;
    }

    public FolderNode getFolderNode(String s) throws TransferException {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return null;
    }

    public String addFolder(FolderNode folderNode) throws TransferException {
        return null;
    }

    public void modifyFolder(FolderNode folderNode) throws TransferException {
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) {
        return null;
    }
}

