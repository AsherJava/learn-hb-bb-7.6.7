/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

import com.jiuqi.np.grid.GridColor;
import com.jiuqi.np.grid.IntList;

public final class GridConsts {
    public static final byte[] DEF_CELL_PROP = new byte[]{0, 0, -64, 0, -64, -128, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final byte[] DEF_HEADCELL_PROP = new byte[]{1, 0, -127, 0, -128, -128, 0, 3, -128, 48, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int DEF_CELL_BACKCOLOR = -16777211;
    public static final int DEF_HEADCELL_BACKCOLOR = -16777201;
    public static final int DEF_CELL_EDGECOLOR = -16777201;
    public static final int DEF_HEADCELL_EDGECOLOR = 0x808080;
    public static final int DEFAULT_ROW_HEIGHT = 20;
    public static final int DEFAULT_COL_WIDTH = 100;
    public static final byte CBL_Horizontal = 0;
    public static final byte CBL_Vertical = 1;
    public static final byte CBL_BDiagonal = 2;
    public static final byte CBL_FDiagonal = 3;
    public static final byte CBS_Auto = 0;
    public static final byte CBS_Solid = 1;
    public static final byte CBS_Clear = 2;
    public static final byte CBS_DiagCrossLine = 3;
    public static final byte CBS_DiagCrossBlock = 4;
    public static final byte CBS_CruxBlock = 5;
    public static final byte CBS_CrossDot = 6;
    public static final byte CBS_DiagCrossDot = 7;
    public static final byte CBS_HorzLine = 8;
    public static final byte CBS_VertLine = 9;
    public static final byte CBS_BDiagLine = 10;
    public static final byte CBS_FDiagLine = 11;
    public static final byte CBS_HorzLineSparse = 12;
    public static final byte CBS_VertLineSparse = 13;
    public static final byte CBS_HorzDashDot = 14;
    public static final byte CBS_VertDashDot = 15;
    public static final byte CBS_Wall = 16;
    public static final byte CBS_Trees = 17;
    public static final byte CBS_Grass = 18;
    public static final byte CBS_Flowers = 19;
    public static final byte CBS_Rounds = 20;
    public static final byte CBS_Lozenges = 21;
    public static final byte CBS_DiagCrux = 22;
    public static final byte CBS_Hills = 23;
    public static final byte CBS_GridLine = 24;
    public static final byte CBS_DiagBlock = 25;
    public static final byte CBS_HorzDiagBlock = 26;
    public static final byte CBS_VertDiagBlock = 27;
    public static final byte CBS_TLRaised = 28;
    public static final byte CBS_Raised = 29;
    public static final byte CBS_TLSunken = 30;
    public static final byte CBS_Sunken = 31;
    public static final byte CES_Auto = 0;
    public static final byte CES_None = 1;
    public static final byte CES_Single = 2;
    public static final byte CES_SingleDash = 3;
    public static final byte CES_SingleDot = 4;
    public static final byte CES_SingleDashDot = 5;
    public static final byte CES_SingleDashDotDot = 6;
    public static final byte CES_Thick = 7;
    public static final byte CES_ThickDash = 8;
    public static final byte CES_ThickDot = 9;
    public static final byte CES_Double = 10;
    public static final byte CES_DoubleDash = 11;
    public static final byte CES_DoubleDot = 12;
    public static final byte CES_Strong = 13;
    public static final byte CES_StrongDash = 14;
    public static final byte CES_StrongDot = 15;
    public static final byte CDT_Auto = 0;
    public static final byte CDT_Text = 1;
    public static final byte CDT_Number = 2;
    public static final byte CDT_Currency = 3;
    public static final byte CDT_Boolean = 4;
    public static final byte CDT_DateTime = 5;
    public static final byte CDT_Graphic = 6;
    public static final byte CDT_Chart = 7;
    public static final byte CDT_BarCode = 8;
    public static final byte CDT_HotLink = 9;
    public static final byte CDT_Control = 10;
    public static final byte CDT_OLE = 11;
    public static final byte CEM_Auto = 0;
    public static final byte CEM_Edit = 1;
    public static final byte CEM_SpinEdit = 2;
    public static final byte CEM_DropDownList = 3;
    public static final byte CEM_DropDownTree = 4;
    public static final byte CEM_Calendar = 5;
    public static final byte CEM_Calculator = 6;
    public static final byte CEM_CheckBox = 7;
    public static final byte CEM_RadioGroup = 8;
    public static final byte CEM_Button = 9;
    public static final byte FS_Bold = 0;
    public static final byte FS_Italic = 1;
    public static final byte FS_Underline = 2;
    public static final byte FS_StrikeOut = 3;
    public static final byte CTA_Auto = 0;
    public static final byte CTA_Fore = 1;
    public static final byte CTA_Back = 2;
    public static final byte CTA_Center = 3;
    public static final byte CTA_Sparse = 4;
    public static final byte CTA_Extend = 5;
    public static final byte CTA_MightBack = 6;
    public static final byte CIM_Auto = 0;
    public static final byte CIM_Open = 1;
    public static final byte CIM_Close = 2;
    public static final byte CIM_Disable = 3;
    public static final int CELL_PROPERTY_BUFFSIZE = 12;
    public static final int CELL_PROPERTY_BUFFSIZE_EX = 20;
    public static final byte BIFF_EOF = 127;
    public static final byte BIFF_Version = 0;
    public static final byte BIFF_CellPropIndex = 1;
    public static final byte BIFF_ColWidth = 2;
    public static final byte BIFF_RowHeight = 3;
    public static final byte BIFF_CellValue = 4;
    public static final byte BIFF_MergeCell = 5;
    public static final byte BIFF_CellFontName = 6;
    public static final byte BIFF_CellFontColor = 7;
    public static final byte BIFF_CellFontSize = 8;
    public static final byte BIFF_EdgeColor = 9;
    public static final byte BIFF_CellColor = 10;
    public static final byte BIFF_CellProperty = 11;
    public static final byte BIFF_ColVisible = 12;
    public static final byte BIFF_ColAutoSize = 13;
    public static final byte BIFF_RowVisible = 14;
    public static final byte BIFF_RowAutoSize = 15;
    public static final byte BIFF_LockColRow = 16;
    public static final byte BIFF_CurColRow = 17;
    public static final byte BIFF_UseFontSize = 18;
    public static final byte BIFF_CellPropertyEx = 19;
    public static final byte BIFF_Labels = 20;
    public static final byte BIFF_DataHideCols = 21;
    public static final byte BIFF_DataHideRows = 22;
    public static final byte BIFF_GridPagingInfo = 23;
    public static final byte BIFF_CellCSS = 24;
    public static final byte BIFF_Properties = 25;
    public static final byte BIFF_CellScript = 26;
    public static final byte BIFF_ExtDatas = 27;
    public static final byte BIFF_CHARSET = 28;
    public static final String KEY_GridDataClass = "gridclass";
    public static final String KEY_GridDataRefBase = "gridrefbase";
    public static final String KEY_HorzOffset = "horzoffset";
    public static final String KEY_VertOffset = "vertoffset";
    public static final String KEY_HorzCenter = "horzcenter";
    public static final String KEY_VertCenter = "vertcenter";
    public static final String BIFF_DATA_FLAG = "BIFF";
    public static final int BIFF_DATA_VER = 1;
    public static final int BIFF_DATA_VER_2 = 2;
    public static final int BIFF_DATA_FILE_VER_10 = 0x1000000;
    public static final int BIFF_DATA_FILE_VER_11 = 0x1010000;
    public static final int BIFF_DATA_FILE_VER_12 = 0x1020000;
    public static final int BIFF_DATA_FILE_VER_CUR = 0x1020000;
    public static final int[] DEF_16COLOR_PALLETTE = new int[]{-16777201, -16777211, 0xE0E0E0, 0x808080, 0x666666, 0x333333, 0, 65535, 128, 255, 32768, 65280, 0x800000, 0xFF0000, 0x800080, 0xFF00FF};
    public static final int[] DEF_64COLOR_PALLETTE = new int[]{-16777211, -16777201, 0xFFFFFF, 0xE0E0E0, 0xC0C0C0, 0x808080, 0x666666, 0x333333, 0xFF00AA, 0xFF55CC, 0xFFAAAA, 0xFFFFAA, 0xFFFF00, 0xFFAA00, 0xF06600, 0xFF0000, 0xFF00FF, 0xAA55FF, 0xAAAAFF, 0xAAFFFF, 0xAAFF66, 0xAAAA55, 15754837, 0xAA0055, 0x800080, 0xAA55AA, 0xFFAAFF, 0xAAFFAA, 0xAAE000, 0x808000, 0xAA5500, 0x800000, 0x5500FF, 0x5555FF, 0x55AAFF, 0x55FFFF, 0x55FF99, 0x55CC55, 0xAA6655, 0x550055, 0x5500AA, 0x5555AA, 0x55AAAA, 0x55FFCC, 0x99FF00, 0x66AA00, 0x555500, 0x550000, 255, 26367, 52479, 65535, 65382, 43622, 21845, 85, 128, 21930, 32896, 65450, 65280, 32768, 21760, 0};
    public static final byte CTC_NORMAL = 0;
    public static final byte CTC_UPPER_CASE = 1;
    public static final byte CTC_LOWER_CASE = 2;
    public static final byte UST_PREFIX_SIGN = 0;
    public static final byte UST_PREFIX_NAME = 1;
    public static final byte UST_SUFFIX_NAME = 2;
    public static final byte UST_PREFIX_TITLE = 3;
    public static final byte PRINT_OUTPUT_KIND_DEFAULT = 0;
    public static final byte PRINT_OUTPUT_KIND_ALWARS = 1;
    public static final byte PRINT_OUTPUT_KIND_ATTIME = 2;
    public static final byte PRINT_OUTPUT_KIND_NONE = 3;
    public static final String EXTDATA_PrintColMap = "print.colmaps";
    public static final String EXTDATA_PrintRowMap = "print.rowmaps";
    public static final String EXTDATA_ProntRowMap = "print.rowmaps";
    public static final String EXTDATA_PrintOptions = "print.options";

    private GridConsts() {
    }

    public static IntList create16ColorsPallette(int count) {
        IntList result = new IntList();
        int size = count > DEF_16COLOR_PALLETTE.length ? DEF_16COLOR_PALLETTE.length : count;
        for (int i = 0; i < size; ++i) {
            result.add(DEF_16COLOR_PALLETTE[i]);
        }
        return result;
    }

    public static IntList create16ColorsPallette() {
        return GridConsts.create16ColorsPallette(16);
    }

    public static IntList create64ColorsPallette(int count) {
        IntList result = new IntList();
        int size = count > DEF_64COLOR_PALLETTE.length ? DEF_64COLOR_PALLETTE.length : count;
        for (int i = 0; i < size; ++i) {
            result.add(DEF_64COLOR_PALLETTE[i]);
        }
        return result;
    }

    public static IntList create64ColorsPallette() {
        return GridConsts.create64ColorsPallette(64);
    }

    static {
        for (int i = 0; i < 64; ++i) {
            GridConsts.DEF_64COLOR_PALLETTE[i] = GridColor.PALLETTE64[i].toBGR();
        }
    }
}

