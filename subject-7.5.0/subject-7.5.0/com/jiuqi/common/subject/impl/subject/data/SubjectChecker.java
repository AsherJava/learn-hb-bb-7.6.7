/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.data;

import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;

public interface SubjectChecker {
    public void doCreateCheck(SubjectDTO var1);

    public void doModifyCheck(SubjectDTO var1);

    public void doDeleteCheck(SubjectDTO var1);

    public int getOrder();
}

