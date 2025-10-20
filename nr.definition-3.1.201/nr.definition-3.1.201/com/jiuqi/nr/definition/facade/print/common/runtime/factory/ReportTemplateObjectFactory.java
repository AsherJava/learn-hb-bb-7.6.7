/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.barcode.obj.BarcodeTemplateObject
 *  com.jiuqi.xg.process.obj.BasicTemplateObjectFactory
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.runtime.factory;

import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.barcode.obj.BarcodeTemplateObject;
import com.jiuqi.xg.process.obj.BasicTemplateObjectFactory;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;

public class ReportTemplateObjectFactory
extends BasicTemplateObjectFactory {
    public ReportTemplateObjectFactory(String nature) {
        super(nature);
    }

    public ITemplateObject create(String kind) {
        Object obj = super.create(kind);
        if (obj == null && kind.equals("barcode")) {
            obj = new BarcodeTemplateObject();
        } else if (obj == null && kind.equals("table")) {
            obj = new TableTemplateObject();
        } else if (obj == null && kind.equals("element_report")) {
            obj = new ReportTemplateObject();
        } else if (obj == null && kind.equals("element_wordLabel")) {
            obj = new WordLabelTemplateObject();
        } else if (obj == null && kind.equals("element_tableLabel")) {
            obj = new TableLabelTemplateObject();
        } else if (obj == null && kind.equals("element_reportLabel")) {
            obj = new ReportLabelTemplateObject();
        }
        return obj;
    }
}

