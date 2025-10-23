/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.form.controller.dto.AutoMappingDTO;
import java.util.List;

public interface IAutoMappingService {
    public List<AutoMappingDTO> fixAutoMapping(String var1, List<AutoMappingDTO> var2);

    public List<AutoMappingDTO> floatAutoMapping(String var1, List<AutoMappingDTO> var2);
}

