/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.List;

public class SaveResItem {
    private int level;
    private String message;
    private List<String> messages;
    private int rowIndex = -1;
    private String linkKey;
    private DimensionCombination dimension;

    @Deprecated
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public DimensionCombination getDimension() {
        return this.dimension;
    }

    public void setDimension(DimensionCombination dimension) {
        this.dimension = dimension;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        if (message == null) {
            return;
        }
        if (this.messages == null) {
            this.messages = new ArrayList<String>();
        }
        this.messages.add(message);
    }

    public void addMessages(List<String> messages) {
        if (messages == null) {
            return;
        }
        if (this.messages == null) {
            this.messages = new ArrayList<String>();
        }
        this.messages.addAll(messages);
    }
}

