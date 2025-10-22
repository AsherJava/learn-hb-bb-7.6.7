/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IDrawElement
 *  com.jiuqi.xg.process.IPlotter
 *  com.jiuqi.xg.process.barcode.impl.BarcodePlotter
 *  com.jiuqi.xg.process.impl.BasicPaintFactory
 *  com.jiuqi.xg.process.table.impl.TablePlotter
 */
package com.jiuqi.nr.definition.facade.print.common.runtime.factory;

import com.jiuqi.nr.definition.facade.print.common.define.element.ReportPlotter;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelPlotter;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelPlotter;
import com.jiuqi.xg.process.IDrawElement;
import com.jiuqi.xg.process.IPlotter;
import com.jiuqi.xg.process.barcode.impl.BarcodePlotter;
import com.jiuqi.xg.process.impl.BasicPaintFactory;
import com.jiuqi.xg.process.table.impl.TablePlotter;

public class ReportPaintFactory
extends BasicPaintFactory {
    public ReportPaintFactory(String nature) {
        super(nature);
    }

    public IPlotter create(IDrawElement element) {
        Object plotter = super.create(element);
        if (plotter == null && element.getKind().equals("barcode")) {
            plotter = new BarcodePlotter();
        } else if (plotter == null && element.getKind().equals("table")) {
            plotter = new TablePlotter();
        } else if (plotter == null && element.getKind().equals("element_report")) {
            plotter = new ReportPlotter();
        } else if (plotter == null && element.getKind().equals("element_wordLabel")) {
            plotter = new WordLabelPlotter();
        } else if (plotter == null && element.getKind().equals("element_tableLabel")) {
            plotter = new TableLabelPlotter();
        } else if (plotter == null && element.getKind().equals("element_reportLabel")) {
            plotter = new WordLabelPlotter();
        }
        return plotter;
    }
}

