/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.List;

public interface IParameterDependAnalyzer {
    public List<ParameterDependMember> findDepends(ParameterCalculator var1, ParameterModel var2) throws ParameterException;

    public List<String> findAffects(ParameterCalculator var1, ParameterModel var2) throws ParameterException;
}

