/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option;

import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.option.core.TaskOptionVO;
import java.util.List;

public interface TaskOptionService {
    public List<TaskOption> getAllOptions();

    public TaskOptionVO getOptionDefines(String var1);

    public void clear(String var1);
}

