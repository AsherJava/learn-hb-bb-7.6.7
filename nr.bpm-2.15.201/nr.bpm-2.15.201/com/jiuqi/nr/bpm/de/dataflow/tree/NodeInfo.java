/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.tree;

import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import java.io.Serializable;
import java.util.List;

public class NodeInfo
implements Serializable {
    private static final long serialVersionUID = -4833042491556904005L;
    private String systemId;
    private String optionId;
    private String type;
    private List<TreeNodeColorInfo> infoItems;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TreeNodeColorInfo> getInfoItems() {
        return this.infoItems;
    }

    public void setInfoItems(List<TreeNodeColorInfo> infoItems) {
        this.infoItems = infoItems;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOptionId() {
        return this.optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}

