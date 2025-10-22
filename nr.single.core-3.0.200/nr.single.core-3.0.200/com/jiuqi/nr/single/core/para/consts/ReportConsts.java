/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

public class ReportConsts {
    public static final String REPORT_LISTFILE = "BBBT.DBF";
    public static final String REPORTGROUP_FILE = "BBFilter";
    public static final String REPORTMAX_FILE = "MapNexus.Ini";
    public static final String REPORTGROUPSECTIONNAME = "Sys_FilterList";
    public static final String SNCURRENTSETTING = "Sys_CurrentSetting";
    public static final String NNMULPAGEFLAG = "MulPage";
    public static final String NNCUR_FILTERFLAG = "Sys_CurFilter";
    public static final String[] IDX_ARR = new String[]{"\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d", "\u4e03", "\u516b", "\u4e5d", "\u5341"};
    public static final String FIELD_ENUM_SHOW = "enumShowField";
    public static final int BOTTOM_CONFIG_MASK = 0x10000000;
    public static final int TOP_CONFIG_MASK = 0x20000000;
    public static final int RIGHT_CONFIG_MASK = 0x40000000;
    public static final int LEFT_CONFIG_MASK = Integer.MIN_VALUE;
    public static final int VISIBLE_MASK = -1610612736;
    public static final int COLORID_MASK = 0xF000000;
    public static final int COLORBIT = 24;
    public static final int BGSTYLE_MASK = 0xE00000;
    public static final int BGSTYLEBIT = 21;
    public static final int HASEXTATTR_MASK = 0x100000;
    public static final int HORZEDGE_MASK = 786432;
    public static final int HORZEDGEBIT = 18;
    public static final int VERTEDGE_MASK = 196608;
    public static final int VERTEDGEBIT = 16;
    public static final int CANEDITTEXT_MASK = -1610563584;
    public static final int CANSHOWTEXT_MASK = -1610596352;
    public static final int FORBIDEDIT_MASK = 32768;
    public static final int EMPTYCELL_MASK = 16384;
    public static final int CELL3DLOOK_MASK = 8192;
    public static final int VDRAWTEXT_MASK = 4096;
    public static final int AUTOFIT_MASK = 2048;
    public static final int WORDWRAP_MASK = 1024;
    public static final int FONTBOLD_MASK = 512;
    public static final int FONTITALIC_MASK = 256;
    public static final int HTEXTALIGN_MASK = 192;
    public static final int HTEXTALIGNBIT = 6;
    public static final int VTEXTALIGN_MASK = 48;
    public static final int VTEXTALIGNBIT = 4;
    public static final int FSADJUST_MASK = 15;
    public static final int FSADJUSTBIT = 0;
    public static final int FONTNAME_MASK = 255;
    public static final int FONTSIZE_MASK = 65280;
    public static final int FONTCHARTSET_MASK = 0xFF0000;
    public static final int FONTUNDERLINE_MASK = 0x1000000;
    public static final int FONTSTRIKEOUT_MASK = 0x2000000;
    public static final int[] COLORARR1 = new int[]{0, 128, 32768, 32896, 0x800000, 0x800080, 0x808000, 0x808080, 0xC0C0C0, 255, 65280, 65535, 0xFF0000, 0xFF00FF, 0xFFFF00, 0xFFFFFF};
    public static final int[] COLORARR = new int[]{0, 0x800000, 32768, 0x808000, 128, 0x800080, 32896, 0xC0C0C0, 0x808080, 0xFF0000, 65280, 0xFFFF00, 255, 0xFF00FF, 65535, 0xFFFFFF};
    public static final int[] HTEXTALIGN = new int[]{1, 3, 2, 4};
    public static final int[] VTEXTALIGN = new int[]{3, 1, 2, 4};
    public static final String[] FONTNAMESETS = new String[]{"@Fixedsys", "@MingLiU", "@PMingLiU", "@System", "@Terminal", "@\u65b9\u6b63\u8212\u4f53", "@\u65b9\u6b63\u59da\u4f53", "@\u4eff\u5b8b_GB2312", "@\u9ed1\u4f53", "@\u534e\u6587\u5f69\u4e91", "@\u534e\u6587\u4eff\u5b8b", "@\u534e\u6587\u884c\u6977", "@\u534e\u6587\u7ec6\u9ed1", "@\u534e\u6587\u65b0\u9b4f", "@\u534e\u6587\u4e2d\u5b8b", "@\u6977\u4f53_GB2312", "@\u96b6\u4e66", "@\u5b8b\u4f53", "@\u5b8b\u4f53-\u65b9\u6b63\u8d85\u5927\u5b57\u7b26\u96c6", "@\u65b0\u5b8b\u4f53", "@\u5e7c\u5706", "\u5fae\u8f6f\u96c5\u9ed1", "Arial", "Arial Black", "Arial CE", "Arial CYR", "Arial Greek", "Arial Narrow", "Arial Rounded MT Bold", "Arial TUR", "Basemic", "Basemic Symbol", "Basemic Times", "Blackadder ITC", "Bodoni MT", "Bodoni MT Black", "Bodoni MT Condensed", "Book Antiqua", "Bookman Old Style", "Bookshelf Symbol 7", "Bradley Hand ITC", "Calisto MT", "Castellar", "Century Gothic", "Century Schoolbook", "Comic Sans MS", "Copperplate Gothic Bold", "Copperplate Gothic Light", "Courier", "Courier New", "Courier New CE", "Courier New CYR", "Courier New Greek", "Courier New TUR", "Curlz MT", "Edwardian Script ITC", "Elephant", "Engravers MT", "Eras Bold ITC", "Eras Demi ITC", "Eras Light ITC", "Eras Medium ITC", "Felix Titling", "Fixedsys", "Forte", "Franklin Gothic Book", "Franklin Gothic Demi", "Franklin Gothic Demi Cond", "Franklin Gothic Heavy", "Franklin Gothic Medium", "Franklin Gothic Medium Cond", "French Script MT", "Garamond", "Georgia", "Gigi", "Gill Sans MT", "Gill Sans MT Condensed", "Gill Sans MT Ext Condensed Bold", "Gill Sans Ultra Bold", "Gill Sans Ultra Bold Condensed", "Gloucester MT Extra Condensed", "Goudy Old Style", "Goudy Stout", "Haettenschweiler", "Impact", "Imprint MT Shadow", "Kingsoft Phonetic Plain", "Lucida Console", "Lucida Sans", "Lucida Sans Typewriter", "Lucida Sans Unicode", "Maiandra GD", "Marlett", "Microsoft Sans Serif", "MingLiU", "Modern", "Monotype Corsiva", "MS Outlook", "MS Reference Sans Serif", "MS Reference Specialty", "MS Sans Serif", "MS Serif", "OCR A Extended", "Palace Script MT", "Palatino Linotype", "Papyrus", "Perpetua", "Perpetua Titling MT", "PMingLiU", "Pristina", "Rage Italic", "Rockwell", "Rockwell Condensed", "Rockwell Extra Bold", "Roman", "Script", "Script MT Bold", "Small Fonts", "Symbol", "System", "Tahoma", "Terminal", "Times New Roman", "Times New Roman CE", "Times New Roman CYR", "Times New Roman Greek", "Times New Roman TUR", "Trebuchet MS", "Tw Cen MT", "Tw Cen MT Condensed", "Tw Cen MT Condensed Extra Bold", "Verdana", "Webdings", "Wingdings", "Wingdings 2", "Wingdings 3", "\u65b9\u6b63\u8212\u4f53", "\u65b9\u6b63\u59da\u4f53", "\u4eff\u5b8b_GB2312", "\u9ed1\u4f53", "\u534e\u6587\u5f69\u4e91", "\u534e\u6587\u4eff\u5b8b", "\u534e\u6587\u884c\u6977", "\u534e\u6587\u7ec6\u9ed1", "\u534e\u6587\u65b0\u9b4f", "\u534e\u6587\u4e2d\u5b8b", "\u6977\u4f53_GB2312", "\u96b6\u4e66", "\u5b8b\u4f53", "\u5b8b\u4f53-\u65b9\u6b63\u8d85\u5927\u5b57\u7b26\u96c6", "\u65b0\u5b8b\u4f53", "\u5e7c\u5706"};
    public static final long FMCONTROL_EDITORFLAG = 65536L;
    public static final long FMCONTROL_BEVELFLAG = 131073L;
    public static final long FMCONTROL_IMAGEFLAG = 196609L;
    public static final long FMCONTROL_TEXTFLAG = 262145L;
    public static final long FMCONTROL_ENDFLAG = 0L;
    public static final int MAX_IDENTIFIER_LEN = 50;
    public static final int MAX_TABLE_IDLENGTH = 20;
    public static final int MAX_CAPTION_LEN = 150;
    public static final int MAX_TABLE_CAPTION_LEN = 100;
    public static final int MAX_DESCRIPTION_LEN = 255;
    public static final int MAX_MJ_WIDTH = 8;
    public static final String EMPTY_CELL_CODE = "\u2014\u2014";
    public static final String EMPTY_CELL_CODE2 = "\u4e00";
    public static final int HEADER_COLROW_DFTDATA = 69894208;
    public static final int HEADER_COL_DFTDATA = 69763136;
    public static final int HEAD_ROW_DFTDATA = 69763136;
}

