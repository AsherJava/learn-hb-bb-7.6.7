/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service;

import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import java.util.List;

public interface InputDataSchemeService {
    public List<InputDataSchemeVO> listInputDataScheme();

    public InputDataSchemeVO getInputDataSchemeByDataSchemeKey(String var1);

    public void createInputDataScheme(InputDataSchemeVO var1);

    public void deleteInputDataSchemeByDataSchemeKey(String var1);
}

