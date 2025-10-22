/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.common.itree.INode;

public class TransferTreeDTO
implements INode {
    private String key;
    private String code;
    private String title;

    public TransferTreeDTO(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

