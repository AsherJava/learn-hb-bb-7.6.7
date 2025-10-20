/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.util.AttachmentObj;
import java.util.ArrayList;
import org.springframework.util.StringUtils;

public class AttachObj {
    private final ArrayList<String> type = new ArrayList();
    private String maxNumber = "-1";
    private String minNumber = "-1";
    private String maxSize = "-1";
    private String minSize = "-1";
    private String groupKey;

    public AttachObj(AttachmentObj obj) {
        if (null != obj) {
            this.type.addAll(obj.getDocument());
            this.type.addAll(obj.getVedio());
            this.type.addAll(obj.getImg());
            this.type.addAll(obj.getZip());
            this.type.addAll(obj.getStadio());
            if (StringUtils.hasText(obj.getMaxNumber())) {
                this.maxNumber = obj.getMaxNumber();
            }
            if (StringUtils.hasText(obj.getMinNumber())) {
                this.minNumber = obj.getMinNumber();
            }
            if (StringUtils.hasText(obj.getMaxSize())) {
                this.maxSize = obj.getMaxSize();
            }
            if (StringUtils.hasText(obj.getMinSize())) {
                this.minSize = obj.getMinSize();
            }
            this.groupKey = obj.getGroupKey();
        }
        if (this.type.isEmpty()) {
            this.type.add("*");
        }
    }

    public ArrayList<String> getType() {
        return this.type;
    }

    public String getMaxNumber() {
        return this.maxNumber;
    }

    public String getMinNumber() {
        return this.minNumber;
    }

    public String getMaxSize() {
        return this.maxSize;
    }

    public String getMinSize() {
        return this.minSize;
    }

    public String getGroupKey() {
        return this.groupKey;
    }
}

