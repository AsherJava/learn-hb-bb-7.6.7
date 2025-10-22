/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 */
package com.jiuqi.nr.definition.internal.impl.print;

import com.jiuqi.nr.definition.facade.print.NumericTextFormatDefine;
import com.jiuqi.nr.definition.facade.print.PrintBasicInfoDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.impl.print.NumericTextFormatDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintBasicInfoDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintPaperDefineImpl;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import java.util.ArrayList;
import java.util.List;

public class PrintSchemeAttributeDefineImpl
implements PrintSchemeAttributeDefine {
    private static final long serialVersionUID = 1371491754932463465L;
    private PrintBasicInfoDefine basicInfo = new PrintBasicInfoDefineImpl();
    private PrintPaperDefine paper = new PrintPaperDefineImpl();
    private List<WordLabelDefine> wordLabels = new ArrayList<WordLabelDefine>();
    private NumericTextFormatDefine format = new NumericTextFormatDefineImpl();
    private WatermarkConfig markConfig;

    @Override
    public PrintBasicInfoDefine getBasicInfo() {
        return this.basicInfo;
    }

    @Override
    public PrintPaperDefine getPaper() {
        return this.paper;
    }

    @Override
    public List<WordLabelDefine> getWordLabels() {
        return this.wordLabels;
    }

    @Override
    public NumericTextFormatDefine getFormat() {
        return this.format;
    }

    @Override
    public WatermarkConfig getMarkConfig() {
        return this.markConfig;
    }

    @Override
    public void setMarkConfig(WatermarkConfig markConfig) {
        this.markConfig = markConfig;
    }
}

