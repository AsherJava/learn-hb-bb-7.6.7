/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.graphics.Point
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import java.io.Serializable;

public class ImportErrorDataInfo
extends SaveErrorDataInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Point excelLocation;

    public Point getExcelLocation() {
        return this.excelLocation;
    }

    public void setExcelLocation(Point excelLocation) {
        this.excelLocation = excelLocation;
    }
}

