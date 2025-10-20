/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf;

import com.jiuqi.common.subject.impl.subject.data.DataType;

public interface ISubjectExpImpFieldDefine {
    public String getCode();

    public String getName();

    public DataType getDataType();

    public boolean isRequired();

    public Integer getWidth();

    public String getRemark();

    public String getExample();
}

