/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 */
package com.jiuqi.nr.definition.facade.print;

import com.jiuqi.nr.definition.facade.print.NumericTextFormatDefine;
import com.jiuqi.nr.definition.facade.print.PrintBasicInfoDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import java.io.Serializable;
import java.util.List;

public interface PrintSchemeAttributeDefine
extends Serializable {
    public PrintBasicInfoDefine getBasicInfo();

    public PrintPaperDefine getPaper();

    public List<WordLabelDefine> getWordLabels();

    public NumericTextFormatDefine getFormat();

    public WatermarkConfig getMarkConfig();

    public void setMarkConfig(WatermarkConfig var1);
}

