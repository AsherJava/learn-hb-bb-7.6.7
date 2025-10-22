/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.Paper
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.xg.process.Paper;

public class PrintSchemeConsts {
    public static final String PRINTSCHEME_LISTFILE = "ParamSet.Lst";
    public static final String PRINTSCHEME_SECTION = "PrtSettings";
    public static final String PRINTSCHEME_OPTION_COUNT = "Count";
    public static final String PRINTSCHEME_OPTION_ITEM = "Item_";
    public static final String PRINTSCHEME_OPTION_CURRENT = "Current";
    public static final String PRINTSCHEME_DEFAULTPS = "\u9ed8\u8ba4\u6253\u5370\u65b9\u6848";
    public static final String PRINTSCHEME_TEXT_FILELDDATALEFTMARK = "<D>";
    public static final String PRINTSCHEME_TEXT_FILELDDATARIGHTMARK = "</D>";
    public static final String PRINTSCHEME_TEXT_JEDW = "\u91d1\u989d\u5355\u4f4d";

    public static enum LineStyle {
        PSSOLID(4),
        PSDASH(0),
        PSDOT(1),
        PSDASH_DOT(2),
        PSDASH_DOTDOT(3),
        PSCLEAR(4),
        PSINSIDE_FRAME(4),
        PSUSER_STYLE(-1),
        PSALTERNATE(4);

        private int style;

        private LineStyle(int style) {
            this.style = style;
        }

        public int getStyle() {
            return this.style;
        }
    }

    public static enum PaperMapping {
        PAPER_SIZE_DEFAULT(Paper.A4_PAPER),
        PAPER_SIZE_LETTER(Paper.LETTER_PAPER),
        PAPER_SIZE_LETTERSMALL(Paper.LETTER_PAPER),
        PAPER_SIZE_TABLOID(Paper.TABLOID_PAPER),
        PAPER_SIZE_LEDGER(Paper.LEDGER_PAPER),
        PAPER_SIZE_LEGAL(Paper.LEGAL_PAPER),
        PAPER_SIZE_STATEMENT(Paper.A4_PAPER),
        PAPER_SIZE_EXECUTIVE(Paper.EXECUTIVE_PAPER),
        PAPER_SIZE_A3(Paper.A3_PAPER),
        PAPER_SIZE_A4(Paper.A4_PAPER),
        PAPER_SIZE_A4SMALL(Paper.A4_PAPER),
        PAPER_SIZE_A5(Paper.A5_PAPER),
        PAPER_SIZE_B4(Paper.B4_PAPER),
        PAPER_SIZE_B5(Paper.B5_PAPER);

        private Paper paper;

        private PaperMapping(Paper paper) {
            this.paper = paper;
        }

        public Paper getPaper() {
            return this.paper;
        }
    }
}

