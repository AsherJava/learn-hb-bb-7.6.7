/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.print.viewer.IElementConfiger
 */
package com.jiuqi.nr.definition.facade.print.common.runtime.factory;

import com.jiuqi.nr.definition.facade.print.common.define.element.ReportElementConfig;
import com.jiuqi.nr.definition.facade.print.common.define.element.ReportLabelElementConfig;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelElementConfig;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelElementConfig;
import com.jiuqi.xg.print.viewer.IElementConfiger;

public class ReportElementConfigerFactory {
    public static IElementConfiger getElementConfiger(String nature, String kind) {
        if (kind.equals("element_report")) {
            return new ReportElementConfig(nature);
        }
        if (kind.equals("element_wordLabel")) {
            return new WordLabelElementConfig(nature);
        }
        if (kind.equals("element_reportLabel")) {
            return new ReportLabelElementConfig(nature);
        }
        if (kind.equals("element_tableLabel")) {
            return new TableLabelElementConfig(nature);
        }
        return null;
    }
}

