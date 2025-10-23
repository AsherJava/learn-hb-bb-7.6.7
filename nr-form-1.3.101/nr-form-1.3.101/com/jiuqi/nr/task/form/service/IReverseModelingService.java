/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.form.controller.dto.ReverseFieldCodeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeCheckDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import java.util.List;

public interface IReverseModelingService {
    public ReverseModeDTO reverseModeling(ReverseModeParam var1);

    public void checkFieldCode(ReverseModeCheckDTO var1);

    public void checkTableCode(ReverseModeCheckDTO var1);

    public List<ReverseFieldCodeDTO> queryAllFieldCodes(String var1);
}

