/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.query.deserializer.QueryEntityDataDeserializer;
import com.jiuqi.nr.query.serializer.QueryEntityDataSerializer;
import java.util.List;

@JsonSerialize(using=QueryEntityDataSerializer.class)
@JsonDeserialize(using=QueryEntityDataDeserializer.class)
public class QueryEntityData
implements INode {
    public static final String CONST_QED_ID = "id";
    public static final String CONST_QED_TITLE = "title";
    public static final String CONST_QED_ISLEAF = "isLeaf";
    public static final String CONST_QED_CHILDCOUNT = "childcount";
    public static final String CONST_QED_CHILDS = "childs";
    public static final String CONST_QED_CODE = "code";
    private String id;
    private String Title;
    private boolean isLeaf;
    private int childCount;
    String code;
    List<QueryEntityData> childs;

    public void setChilds(List<QueryEntityData> childs) {
        this.childs = childs;
    }

    public List<QueryEntityData> getChilds() {
        return this.childs;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public String getKey() {
        return this.id.toString();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

