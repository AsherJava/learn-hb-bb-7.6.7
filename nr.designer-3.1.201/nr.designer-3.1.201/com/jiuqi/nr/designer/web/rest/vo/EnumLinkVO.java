/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.SimpleDataField;
import java.util.List;

public class EnumLinkVO {
    private SimpleDataField checkedDataField;
    private List<SimpleDataField> currentFiledList;

    public SimpleDataField getCheckedDataField() {
        return this.checkedDataField;
    }

    public void setCheckedDataField(SimpleDataField checkedDataField) {
        this.checkedDataField = checkedDataField;
    }

    public List<SimpleDataField> getCurrentFiledList() {
        return this.currentFiledList;
    }

    public void setCurrentFiledList(List<SimpleDataField> currentFiledList) {
        this.currentFiledList = currentFiledList;
    }
}

