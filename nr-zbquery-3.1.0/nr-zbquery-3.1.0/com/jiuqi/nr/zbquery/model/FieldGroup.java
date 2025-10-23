/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import java.util.ArrayList;
import java.util.List;

public class FieldGroup
extends QueryObject {
    private FieldGroupType groupType = FieldGroupType.ZB;
    private List<QueryObject> children = new ArrayList<QueryObject>();
    private DimensionAttributeField dimAttribute;
    private boolean displayIndent;
    private boolean displayTiered;
    private boolean enablePierce;

    public FieldGroup() {
        this.setType(QueryObjectType.GROUP);
    }

    public FieldGroupType getGroupType() {
        return this.groupType;
    }

    public void setGroupType(FieldGroupType groupType) {
        this.groupType = groupType;
    }

    public List<QueryObject> getChildren() {
        return this.children;
    }

    public void setChildren(List<QueryObject> children) {
        this.children = children;
    }

    public DimensionAttributeField getDimAttribute() {
        return this.dimAttribute;
    }

    public void setDimAttribute(DimensionAttributeField dimAttribute) {
        this.dimAttribute = dimAttribute;
    }

    public boolean isDisplayIndent() {
        return this.displayIndent;
    }

    public void setDisplayIndent(boolean displayIndent) {
        this.displayIndent = displayIndent;
    }

    public boolean isDisplayTiered() {
        return this.displayTiered;
    }

    public void setDisplayTiered(boolean displayTiered) {
        this.displayTiered = displayTiered;
    }

    public boolean isEnablePierce() {
        return this.enablePierce;
    }

    public void setEnablePierce(boolean enablePierce) {
        this.enablePierce = enablePierce;
    }
}

