/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.metadata.Head
 *  com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy
 */
package com.jiuqi.common.subject.impl.subject.expimp.style;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectFieldDefineHolder;

public class SubjectExpColumnWidthStyleStrategy
extends AbstractHeadColumnWidthStyleStrategy {
    private SubjectFieldDefineHolder fieldDefineHolder;

    public SubjectExpColumnWidthStyleStrategy(SubjectFieldDefineHolder fieldDefineHolder) {
        this.fieldDefineHolder = fieldDefineHolder;
    }

    protected Integer columnWidth(Head head, Integer columnIndex) {
        if (this.fieldDefineHolder.getDefineList().size() > columnIndex) {
            return this.fieldDefineHolder.getDefineList().get(columnIndex).getWidth();
        }
        return 18;
    }
}

