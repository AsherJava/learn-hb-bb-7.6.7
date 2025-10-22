/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.nr.single.core.para.parser.print.PrintPageData;
import java.awt.Font;

public class StrsPrintPageData {
    private PrintPageData privatePrintPapeData;
    private String privateTitle;
    private int privateLineSpace;
    private Font privateFont;

    public final PrintPageData getPrintPapeData() {
        return this.privatePrintPapeData;
    }

    public final void setPrintPapeData(PrintPageData value) {
        this.privatePrintPapeData = value;
    }

    public final String getTitle() {
        return this.privateTitle;
    }

    public final void setTitle(String value) {
        this.privateTitle = value;
    }

    public final int getLineSpace() {
        return this.privateLineSpace;
    }

    public final void setLineSpace(int value) {
        this.privateLineSpace = value;
    }

    public Font getFont() {
        return this.privateFont;
    }

    public final void setFont(Font value) {
        this.privateFont = value;
    }
}

