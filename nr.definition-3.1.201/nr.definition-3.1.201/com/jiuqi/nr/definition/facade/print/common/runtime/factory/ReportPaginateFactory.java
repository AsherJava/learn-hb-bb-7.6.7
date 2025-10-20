/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITransformer
 *  com.jiuqi.xg.process.barcode.impl.BarcodeTransformer
 *  com.jiuqi.xg.process.impl.BasicPaginateFactory
 *  com.jiuqi.xg.process.table.impl.TableTransformer
 */
package com.jiuqi.nr.definition.facade.print.common.runtime.factory;

import com.jiuqi.nr.definition.facade.print.common.define.element.ReportLabelTransformer;
import com.jiuqi.nr.definition.facade.print.common.define.element.ReportTransformer;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelTransformer;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelTransformer;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITransformer;
import com.jiuqi.xg.process.barcode.impl.BarcodeTransformer;
import com.jiuqi.xg.process.impl.BasicPaginateFactory;
import com.jiuqi.xg.process.table.impl.TableTransformer;

public class ReportPaginateFactory
extends BasicPaginateFactory {
    public ReportPaginateFactory(String nature) {
        super(nature);
    }

    public ITransformer create(ITemplateElement<?> element) {
        Object transformer = super.create(element);
        if (transformer == null && element.getKind().equals("barcode")) {
            transformer = new BarcodeTransformer();
        } else if (transformer == null && element.getKind().equals("table")) {
            transformer = new TableTransformer();
        } else if (transformer == null && element.getKind().equals("element_report")) {
            transformer = new ReportTransformer();
        } else if (transformer == null && element.getKind().equals("element_wordLabel")) {
            transformer = new WordLabelTransformer();
        } else if (transformer == null && element.getKind().equals("element_tableLabel")) {
            transformer = new TableLabelTransformer();
        } else if (transformer == null && element.getKind().equals("element_reportLabel")) {
            transformer = new ReportLabelTransformer();
        }
        return transformer;
    }
}

