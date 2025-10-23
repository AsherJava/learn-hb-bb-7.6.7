/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.fix.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDetailsDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IDataSchemeDeployFixService {
    public List<DeployExCheckResultDTO> doDataSchemeDeployExCheck(String var1);

    public List<DeployFixResultDTO> doDataSchemeDeployFix(List<DeployFixParamDTO> var1, Consumer<ProgressItem> var2);

    public List<DeployExCheckResultDTO> doDataSchemeDeployExCheck(EnumMap<DeployExType, DeployFixType> var1);

    public List<DeployFixResultDTO> doDsDeployFixWithPercentage(List<DeployFixParamDTO> var1, Consumer<ProgressItem> var2);

    public List<DeployFixResultDTO> doDataSchemeDeployCheckAndFix(String var1, EnumMap<DeployExType, DeployFixType> var2, Consumer<ProgressItem> var3);

    public List<DeployFixResultDTO> doDataSchemeDeployCheckAndFix(EnumMap<DeployExType, DeployFixType> var1, Consumer<ProgressItem> var2);

    public List<DeployFixDetailsDTO> doDataSchemeDeployFix(String var1, BiConsumer<Integer, String> var2) throws JQException;
}

