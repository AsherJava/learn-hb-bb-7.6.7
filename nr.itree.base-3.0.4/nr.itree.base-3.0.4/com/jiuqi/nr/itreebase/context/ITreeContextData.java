/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.context;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.context.ITreeVariableDataDeserializer;
import com.jiuqi.nr.itreebase.context.ITreeVariableDataSerializer;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.BaseNodeJsonDeserializer;
import java.util.List;
import org.json.JSONObject;

public class ITreeContextData
extends NRContext
implements ITreeContext {
    private String contextId;
    private String dataSourceId;
    private List<String> checklist;
    private boolean selectFirstNode = true;
    private JSONObject customVariable;
    private BaseNodeDataImpl actionNode;

    @Override
    public String getContextId() {
        return this.contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    @Override
    public String getDataSourceId() {
        return this.dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Override
    public List<String> getChecklist() {
        return this.checklist;
    }

    public void setChecklist(List<String> checklist) {
        this.checklist = checklist;
    }

    @Override
    public boolean isSelectFirstNode() {
        return this.selectFirstNode;
    }

    public void setSelectFirstNode(boolean selectFirstNode) {
        this.selectFirstNode = selectFirstNode;
    }

    @Override
    @JsonSerialize(using=ITreeVariableDataSerializer.class)
    public JSONObject getCustomVariable() {
        return this.customVariable;
    }

    @JsonDeserialize(using=ITreeVariableDataDeserializer.class)
    public void setCustomVariable(JSONObject customVariable) {
        this.customVariable = customVariable;
    }

    @Override
    public BaseNodeDataImpl getActionNode() {
        if (this.actionNode != null && this.actionNode.size() > 0 && StringUtils.isNotEmpty((String)this.actionNode.getKey())) {
            return this.actionNode;
        }
        return null;
    }

    @JsonDeserialize(using=BaseNodeJsonDeserializer.class)
    public void setActionNode(BaseNodeDataImpl actionNode) {
        this.actionNode = actionNode;
    }
}

