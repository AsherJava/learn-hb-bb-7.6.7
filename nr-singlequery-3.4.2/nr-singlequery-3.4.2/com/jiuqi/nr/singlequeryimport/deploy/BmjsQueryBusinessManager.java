/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.singlequeryimport.deploy;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BmjsQueryBusinessManager
extends AbstractBusinessManager {
    @Autowired
    QueryModleService queryModleService;

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        String[] split;
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        if (null != s && (split = s.split("#")).length == 3) {
            try {
                List<QueryModelNode> groupModel = null;
                groupModel = this.queryModleService.getModel(split[1], split[2]);
                if (!groupModel.isEmpty()) {
                    for (QueryModelNode queryModelNode : groupModel) {
                        BusinessNode businessNode = new BusinessNode();
                        businessNode.setGuid(s + queryModelNode.getId() + "#");
                        businessNode.setOrder(queryModelNode.getOrder());
                        businessNode.setTitle(queryModelNode.getTitle());
                        list.add(businessNode);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        String[] split;
        BusinessNode businessNode = new BusinessNode();
        if (null != s && (split = s.split("#")).length == 4) {
            try {
                QueryModel queryModelByKey = this.queryModleService.getQueryModelByKey(split[3]);
                if (null != queryModelByKey) {
                    businessNode.setGuid(s);
                    businessNode.setName(queryModelByKey.getGroup() + "_" + queryModelByKey.getItemTitle());
                    businessNode.setOrder(queryModelByKey.getOrder());
                    businessNode.setTitle(queryModelByKey.getItemTitle());
                    businessNode.setTypeTitle("\u67e5\u8be2\u6a21\u677f");
                    businessNode.setType("com.jiuqi.nr.hxglb");
                    return businessNode;
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        ArrayList<FolderNode> folderNodes = new ArrayList<FolderNode>();
        try {
            FolderNode folderNode;
            List<QueryModelNode> groupModel;
            String[] split = s.split("#");
            if (split.length == 3 && !(groupModel = this.queryModleService.getModel(split[1], split[2])).isEmpty()) {
                for (QueryModelNode queryModelNode : groupModel) {
                    folderNode = new FolderNode(s + queryModelNode.getId() + "#", queryModelNode.getTitle(), NodeType.SCHEME.name(), s, null);
                    folderNodes.add(folderNode);
                }
            }
            if (split.length == 2 && !(groupModel = this.queryModleService.getGroupModel(split[1])).isEmpty()) {
                for (QueryModelNode queryModelNode : groupModel) {
                    folderNode = new FolderNode(s + queryModelNode.getId() + "#", queryModelNode.getTitle(), NodeType.SCHEME.name(), s, null);
                    folderNodes.add(folderNode);
                }
            }
            if (split.length == 4) {
                folderNodes.add(this.getTaskFolders(split));
                folderNodes.add(this.getSchemeFolders(split));
                folderNodes.add(this.getGroupFolders(split));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return folderNodes;
    }

    FolderNode getGroupFolders(String[] split) throws Exception {
        FolderNode folderNode = null;
        List<QueryModelNode> taskModel = this.queryModleService.getGroupModel(split[1]);
        for (QueryModelNode queryModelNode : taskModel) {
            if (!split[2].equals(queryModelNode.getId())) continue;
            folderNode = new FolderNode(null, queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
        }
        return folderNode;
    }

    FolderNode getSchemeFolders(String[] split) throws Exception {
        FolderNode folderNode = null;
        List<QueryModelNode> taskModel = this.queryModleService.getFormSchemeModel(split[0]);
        for (QueryModelNode queryModelNode : taskModel) {
            if (!split[1].equals(queryModelNode.getId())) continue;
            folderNode = new FolderNode(null, queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
        }
        return folderNode;
    }

    FolderNode getTaskFolders(String[] split) throws Exception {
        FolderNode folderNode = null;
        List<QueryModelNode> taskModel = this.queryModleService.getTaskModel();
        for (QueryModelNode queryModelNode : taskModel) {
            if (!split[0].equals(queryModelNode.getId())) continue;
            folderNode = new FolderNode(null, queryModelNode.getTitle(), NodeType.SCHEME.name(), null, null);
        }
        return folderNode;
    }

    public void moveBusiness(BusinessNode businessNode, String s) {
    }
}

