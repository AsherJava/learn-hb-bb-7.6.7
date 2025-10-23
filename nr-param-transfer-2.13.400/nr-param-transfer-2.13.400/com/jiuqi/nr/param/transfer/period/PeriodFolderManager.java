/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 */
package com.jiuqi.nr.param.transfer.period;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PeriodFolderManager
extends AbstractFolderManager {
    private static final String CUSTOM_ID = "00000000-0000-0000-0000-000000000001";

    public String getRootParentId() {
        return null;
    }

    public List<FolderNode> getFolderNodes(String s) {
        if (s == null) {
            return this.getCustomGroup();
        }
        return Collections.emptyList();
    }

    public FolderNode getFolderNode(String s) {
        if (CUSTOM_ID.equals(s)) {
            return new FolderNode(CUSTOM_ID, "\u81ea\u5b9a\u4e49\u65f6\u671f", "", null, "1");
        }
        return null;
    }

    public List<FolderNode> getCustomGroup() {
        ArrayList<FolderNode> folderNodes = new ArrayList<FolderNode>();
        folderNodes.add(new FolderNode(CUSTOM_ID, "\u81ea\u5b9a\u4e49\u65f6\u671f", "", null, "1"));
        folderNodes.add(new FolderNode("00000000-0000-0000-0000-000000000002", "\u8d22\u52a1\u65f6\u671f", "", null, "2"));
        return folderNodes;
    }

    public List<FolderNode> getPathFolders(String s) {
        return Collections.emptyList();
    }

    public String addFolder(FolderNode folderNode) {
        return null;
    }

    public void modifyFolder(FolderNode folderNode) {
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) {
        return null;
    }
}

