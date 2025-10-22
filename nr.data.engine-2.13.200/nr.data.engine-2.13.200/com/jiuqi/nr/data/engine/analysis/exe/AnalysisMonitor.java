/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnalysisMonitor
extends AbstractMonitor {
    private DimensionValueSet srcDimension;
    private Set<Object> filteredKeys = new HashSet<Object>();
    private String mainDim;

    public AnalysisMonitor(DimensionValueSet srcDimension) {
        this.srcDimension = srcDimension;
    }

    public void error(FormulaCheckEventImpl event) {
        Object key = event.getRowkey().getValue(this.mainDim);
        if (key != null) {
            this.filteredKeys.add(key);
        }
    }

    public void setMainDim(String mainDim) {
        this.mainDim = mainDim;
    }

    public void finish() {
        super.finish();
        List keys = (List)this.srcDimension.getValue(this.mainDim);
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            if (!this.filteredKeys.contains(it.next())) continue;
            it.remove();
        }
    }
}

