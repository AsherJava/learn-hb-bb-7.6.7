/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.List;

public interface ITreeNodeIconColorService {
    public List<TreeNodeColorInfo> getStateNodeIconColor(String var1);

    public List<TreeNodeColorInfo> getAllNodeIconColor();

    public TreeNodeColorInfo getNodeIconColorByUploadState(UploadState var1);

    public TreeNodeColorInfo getNodeIconColorByUploadState(String var1);

    public boolean isNodeIconType();
}

