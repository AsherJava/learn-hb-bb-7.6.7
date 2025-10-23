/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.topic.extend.report;

public class GuidHandler {
    private static final String tag = "@";
    private String id;
    private TreeNodeType type;
    private String itemId;

    public static String build(String id, TreeNodeType type) {
        return id + tag + type.name();
    }

    public static GuidHandler getInstance(String itemId) {
        if (itemId.indexOf(tag) > -1) {
            String[] split = itemId.split(tag);
            GuidHandler guidHandler = new GuidHandler();
            guidHandler.itemId = itemId;
            guidHandler.id = split[0];
            guidHandler.type = TreeNodeType.formValue(split[1]);
            return guidHandler;
        }
        return null;
    }

    public static String getTag() {
        return tag;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreeNodeType getType() {
        return this.type;
    }

    public void setType(TreeNodeType type) {
        this.type = type;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public static enum TreeNodeType {
        TG,
        TK,
        RS,
        RG;


        public static TreeNodeType formValue(String type) {
            for (TreeNodeType e : TreeNodeType.values()) {
                if (!type.equals(e.name())) continue;
                return e;
            }
            return null;
        }
    }
}

