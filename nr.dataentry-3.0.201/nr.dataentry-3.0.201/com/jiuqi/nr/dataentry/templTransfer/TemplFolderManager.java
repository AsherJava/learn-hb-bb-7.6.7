/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.dataentry.templTransfer;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TemplFolderManager
extends AbstractFolderManager {
    public List<FolderNode> getFolderNodes(String s) throws TransferException {
        return new ArrayList<FolderNode>();
    }

    public FolderNode getFolderNode(String s) throws TransferException {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return new ArrayList<FolderNode>();
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

