/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.nr.definition.option.core.OptionWrapper;
import com.jiuqi.nr.definition.option.core.TaskOption;
import java.util.List;

public interface ITaskOptionController {
    public String getValue(String var1, String var2);

    public void setValue(String var1, String var2, String var3);

    public List<TaskOption> getOptions(String var1);

    public void setOptions(List<TaskOption> var1);

    public OptionWrapper exit(String var1, String var2);
}

