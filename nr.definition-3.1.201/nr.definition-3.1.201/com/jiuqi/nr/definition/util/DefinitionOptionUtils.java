/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinitionOptionUtils {
    @Autowired
    private INvwaSystemOptionService optionService;

    public boolean isSpecifyDimensionAssignment() {
        if (!"1".equals(this.optionService.findValueById("@nr/logic/compatibility-mode"))) {
            return false;
        }
        return "1".equals(this.optionService.findValueById("@nr/logic/specify-dimension-assignment"));
    }
}

