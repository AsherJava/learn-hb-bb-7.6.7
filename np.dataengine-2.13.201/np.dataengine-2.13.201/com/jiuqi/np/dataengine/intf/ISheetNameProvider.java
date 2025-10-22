/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.parser.IContext;

public interface ISheetNameProvider {
    default public String getSheetName(IContext context, String reportName) {
        return reportName;
    }
}

