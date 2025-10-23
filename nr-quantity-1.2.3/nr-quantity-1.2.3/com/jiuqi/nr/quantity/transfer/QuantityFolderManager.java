/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.quantity.transfer;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.service.IQuantityService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityFolderManager
extends AbstractFolderManager {
    private static final Logger logger = LoggerFactory.getLogger(QuantityFolderManager.class);
    @Autowired
    private IQuantityService quantityService;

    private FolderNode quantityInfoToFolder(QuantityInfo quantityInfo) {
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid("QI_" + quantityInfo.getId());
        folderNode.setName(quantityInfo.getName());
        folderNode.setTitle(quantityInfo.getTitle());
        folderNode.setType("\u91cf\u7eb2");
        folderNode.setOrder(quantityInfo.getOrder());
        return folderNode;
    }

    public List<FolderNode> getFolderNodes(String s) throws TransferException {
        ArrayList<FolderNode> folderNodes = new ArrayList<FolderNode>();
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

    public FolderNode getFolderByTitle(String s, String s1, String s2) throws TransferException {
        return null;
    }
}

