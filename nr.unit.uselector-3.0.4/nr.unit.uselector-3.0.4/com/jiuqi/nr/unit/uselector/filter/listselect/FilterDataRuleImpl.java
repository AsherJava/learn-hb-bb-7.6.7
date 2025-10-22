/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.dataio.IRowData;
import com.jiuqi.nr.unit.uselector.filter.listselect.FilterDataRule;

public class FilterDataRuleImpl
implements FilterDataRule {
    private boolean codeExactMatch;
    private boolean titleExactMatch;

    @Override
    public boolean matchRowWithCondition(IRowData condition, IEntityRow row) {
        return this.exactMatchCode(condition.getCode(), row) || this.exactMatchTitle(condition.getTitle(), row) || this.fuzzyMatchCode(condition.getCode(), row) || this.fuzzyMatchTitle(condition.getTitle(), row);
    }

    @Override
    public boolean isCodeExactMatch() {
        return this.codeExactMatch;
    }

    @Override
    public boolean isTitleExactMatch() {
        return this.titleExactMatch;
    }

    private boolean exactMatchCode(String codeValue, IEntityRow row) {
        this.codeExactMatch = StringUtils.isNotEmpty((String)codeValue) && row.getCode().equalsIgnoreCase(codeValue);
        return this.codeExactMatch;
    }

    private boolean exactMatchTitle(String titleValue, IEntityRow row) {
        this.titleExactMatch = StringUtils.isNotEmpty((String)titleValue) && row.getTitle().equals(titleValue);
        return this.titleExactMatch;
    }

    private boolean fuzzyMatchCode(String codeValue, IEntityRow row) {
        return StringUtils.isNotEmpty((String)codeValue) && row.getCode().toLowerCase().contains(codeValue.toLowerCase());
    }

    private boolean fuzzyMatchTitle(String titleValue, IEntityRow row) {
        return StringUtils.isNotEmpty((String)titleValue) && row.getTitle().contains(titleValue);
    }
}

