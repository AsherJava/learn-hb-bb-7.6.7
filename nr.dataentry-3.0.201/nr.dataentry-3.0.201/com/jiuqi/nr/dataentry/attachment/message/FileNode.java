/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import java.util.List;

public class FileNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String formKey;
    private String type;
    private float maxSize;
    private float minSize;
    private List<String> types;
    private int num;
    private String dataLinkKey;
    private boolean readable;
    private boolean writeable;
    private SecretLevelItem secretLevelItem;
    private String message;

    public FileNode(String key, String code, String title, String formKey, String type) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.formKey = formKey;
        this.type = type;
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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getType() {
        return this.type;
    }

    public float getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
    }

    public float getMinSize() {
        return this.minSize;
    }

    public void setMinSize(float minSize) {
        this.minSize = minSize;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public boolean isReadable() {
        return this.readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SecretLevelItem getSecretLevelItem() {
        return this.secretLevelItem;
    }

    public void setSecretLevelItem(SecretLevelItem secretLevelItem) {
        this.secretLevelItem = secretLevelItem;
    }
}

