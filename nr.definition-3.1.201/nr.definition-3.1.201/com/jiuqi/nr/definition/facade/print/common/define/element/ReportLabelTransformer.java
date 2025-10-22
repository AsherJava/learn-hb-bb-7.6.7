/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.impl.TextTransformer
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.nr.definition.facade.print.common.define.element.ReportLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.impl.TextTransformer;

public class ReportLabelTransformer
extends TextTransformer {
    protected void initialize(ITemplateObject source, IDrawObject target) {
        super.initialize(source, target);
        ReportLabelTemplateObject sText = (ReportLabelTemplateObject)source;
        ReportLabelDrawObject tText = (ReportLabelDrawObject)target;
        tText.setAutoSize(sText.isAutoSize());
    }
}

