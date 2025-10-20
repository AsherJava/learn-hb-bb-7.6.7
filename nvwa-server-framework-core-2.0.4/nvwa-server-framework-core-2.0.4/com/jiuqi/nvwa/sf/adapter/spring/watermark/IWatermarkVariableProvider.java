/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.NvwaWatermarkVariableDTO;
import java.util.ArrayList;
import java.util.List;

public interface IWatermarkVariableProvider {
    default public List<NvwaWatermarkVariableDTO> getExtendVariableList() {
        return new ArrayList<NvwaWatermarkVariableDTO>();
    }
}

