/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.barcode.obj.BarcodeDrawObject
 *  com.jiuqi.xg.process.obj.BasicDrawObjectFactory
 *  com.jiuqi.xg.process.table.obj.TableDrawObject
 */
package com.jiuqi.nr.definition.facade.print.common.runtime.factory;

import com.jiuqi.nr.definition.facade.print.common.define.element.ReportDrawObject;
import com.jiuqi.nr.definition.facade.print.common.define.element.ReportLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelDrawObject;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.barcode.obj.BarcodeDrawObject;
import com.jiuqi.xg.process.obj.BasicDrawObjectFactory;
import com.jiuqi.xg.process.table.obj.TableDrawObject;

public class ReportDrawObjectFactory
extends BasicDrawObjectFactory {
    public ReportDrawObjectFactory(String nature) {
        super(nature);
    }

    public IDrawObject create(String kind) {
        Object obj = super.create(kind);
        if (obj == null && kind.equals("barcode")) {
            obj = new BarcodeDrawObject();
        } else if (obj == null && kind.equals("table")) {
            obj = new TableDrawObject();
        } else if (obj == null && kind.equals("element_report")) {
            obj = new ReportDrawObject();
        } else if (obj == null && kind.equals("element_wordLabel")) {
            obj = new WordLabelDrawObject();
        } else if (obj == null && kind.equals("element_tableLabel")) {
            obj = new TableLabelDrawObject();
        } else if (obj == null && kind.equals("element_reportLabel")) {
            obj = new ReportLabelDrawObject();
        }
        return obj;
    }
}

