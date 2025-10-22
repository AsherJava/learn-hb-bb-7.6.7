/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.bean.TagRange;

public class TagObject
extends TagImpl {
    private static final long serialVersionUID = -7072639824675001734L;
    private TagRange tagRange;

    public TagRange getTagRange() {
        return this.tagRange;
    }

    public void setTagRange(TagRange tagRange) {
        this.tagRange = tagRange;
    }

    public static TagObject buildTagObj(TagImpl impl) {
        TagObject tagObj = new TagObject();
        tagObj.setKey(impl.getKey());
        tagObj.setTitle(impl.getTitle());
        tagObj.setDescription(impl.getDescription());
        tagObj.setCategory(impl.getCategory());
        tagObj.setFormula(impl.getFormula());
        tagObj.setOwner(impl.getOwner());
        tagObj.setShared(impl.getShared());
        tagObj.setOrder(impl.getOrder());
        tagObj.setViewKey(impl.getViewKey());
        tagObj.setRangeModify(impl.getRangeModify());
        return tagObj;
    }
}

