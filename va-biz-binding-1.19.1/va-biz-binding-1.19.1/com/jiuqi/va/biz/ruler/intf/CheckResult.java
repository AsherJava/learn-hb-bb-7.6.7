/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.data.DataTarget;
import java.util.stream.Stream;

public interface CheckResult {
    public String getFormulaName();

    public String getCheckMessage();

    public Stream<DataTarget> getTargetList();
}

