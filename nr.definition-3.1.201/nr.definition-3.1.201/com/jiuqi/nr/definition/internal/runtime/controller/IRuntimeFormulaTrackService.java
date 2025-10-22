/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

@Deprecated
public interface IRuntimeFormulaTrackService {
    @Deprecated
    public List<FormulaTrackDefine> getFormulaTrackByForm(String var1, String var2);

    @Deprecated
    public List<FormulaTrackDefine> getFormulaTrackByScheme(String var1);

    @Deprecated
    public List<String> queryDataLinkKeyByFormulaTracks(String var1, String var2, DataEngineConsts.FormulaType var3, int var4);

    @Deprecated
    default public List<FormulaTrackDefine> queryFormulaTracks(String dataLinkCode, String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        return this.getFormulaTrackByScheme(formulaSchemeKey).stream().filter(o -> Objects.equals(o.getDataLinkCode(), dataLinkCode)).filter(o -> Objects.equals(o.getFormulaType(), formulaType.getValue())).collect(Collectors.toList());
    }

    @Deprecated
    default public List<FormulaTrackDefine> queryFormulaTracks(String dataLinkCode, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        return this.getFormulaTrackByForm(formulaSchemeKey, formKey).stream().filter(o -> Objects.equals(o.getDataLinkCode(), dataLinkCode)).filter(o -> Objects.equals(o.getFormulaType(), formulaType.getValue())).collect(Collectors.toList());
    }

    @Deprecated
    default public List<FormulaTrackDefine> queryFormulaTracks(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, int direction) {
        return this.getFormulaTrackByForm(formulaSchemeKey, formKey).stream().filter(o -> Objects.equals(o.getFormulaType(), formulaType.getValue())).filter(o -> Objects.equals(o.getFormulaDataDirection(), direction)).collect(Collectors.toList());
    }

    @Deprecated
    default public List<FormulaTrackDefine> queryFormulaTracks(List<String> linkCodes, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, Integer direction) {
        if (CollectionUtils.isEmpty(linkCodes)) {
            return Collections.emptyList();
        }
        return this.getFormulaTrackByForm(formulaSchemeKey, formKey).stream().filter(o -> linkCodes.contains(o.getDataLinkCode())).filter(o -> Objects.equals(o.getFormulaType(), formulaType.getValue())).filter(o -> Objects.equals(o.getFormulaDataDirection(), direction)).collect(Collectors.toList());
    }
}

