/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.context;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.io.Serializable;
import java.util.List;
import org.json.JSONObject;

public interface ITreeContext
extends Serializable {
    public String getContextId();

    public String getDataSourceId();

    public List<String> getChecklist();

    public boolean isSelectFirstNode();

    public JSONObject getCustomVariable();

    public IBaseNodeData getActionNode();
}

