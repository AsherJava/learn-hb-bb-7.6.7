/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.vo;

import java.util.ArrayList;
import java.util.List;
import nr.midstore.design.domain.CommonParamDTO;

public class MidstoreFormTreeVO
extends CommonParamDTO {
    public static final String NODE_TYPE_GROUP = "NODE_TYPE_GROUP";
    public static final String NODE_TYPE_FORM = "NODE_TYPE_FORM";
    private String type;
    private int formType;
    private boolean expand;
    private boolean selected;
    private List<MidstoreFormTreeVO> children;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<MidstoreFormTreeVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<MidstoreFormTreeVO>();
        }
        return this.children;
    }

    public void setChildren(List<MidstoreFormTreeVO> children) {
        this.children = children;
    }
}

