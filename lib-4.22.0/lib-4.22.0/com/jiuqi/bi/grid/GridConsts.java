/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.GridPallette;
import com.jiuqi.bi.grid.IntList;

public final class GridConsts {
    public static final byte[] DEF_CELL_PROP_12 = new byte[]{0, 0, -64, 0, -64, -128, 0, 0, -128, 48, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final byte[] DEF_HEADCELL_PROP_12 = new byte[]{1, 0, -127, 0, -128, -128, 0, 3, -128, 48, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte DEF_FONT_SIZE_I = 10;
    private static final byte DEF_FONT_SIZE_D = 50;
    public static final byte[] DEF_CELL_PROP = new byte[]{0, 0, 0, 0, 0, 0, 0, 50, 10, 64, 0, 0, 124, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0};
    public static final byte[] DEF_HEADCELL_PROP = new byte[]{1, 0, 1, 0, 0, 0, 0, 50, 10, 64, 3, 0, 104, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0};
    public static final int DEF_CELL_BACKCOLOR = 0xFFFFFF;
    public static final int DEF_HEADCELL_BACKCOLOR = 0xF2F2F2;
    public static final int DEF_CELL_EDGECOLOR = 0xD1D1D1;
    public static final int DEF_HEADCELL_EDGECOLOR = 0x3F3F3F;
    public static final int DEFAULT_ROW_HEIGHT = 30;
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
    public static final int CELL_PROPERTY_BUFFSIZE_20 = 26;
    public static final int CELL_PROPERTY_BUFFSIZE_30 = 32;
    public static final int CELL_PROPERTY_BUFFSIZE_CUR = 32;
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
    public static final byte BIFF_CELLPROPERTY2X = 29;
    public static final byte BIFF_CellValueEx = 30;
    public static final byte BIFF_CellCSSEx = 31;
    public static final byte BIFF_CellScriptEx = 32;
    public static final byte BIFF_CellFormats = 33;
    public static final String KEY_GridDataClass = "gridclass";
    public static final String KEY_GridDataRefBase = "gridrefbase";
    public static final String KEY_HorzOffset = "horzoffset";
    public static final String KEY_VertOffset = "vertoffset";
    public static final String KEY_HorzCenter = "horzcenter";
    public static final String KEY_VertCenter = "vertcenter";
    public static final String BIFF_DATA_FLAG = "BIFF";
    public static final int BIFF_DATA_VER = 1;
    public static final int BIFF_DATA_VER_2 = 2;
    public static final int DATA_FILE_VER_10 = 0x1000000;
    public static final int DATA_FILE_VER_11 = 0x1010000;
    public static final int DATA_FILE_VER_12 = 0x1020000;
    public static final int DATA_FILE_VER_20 = 0x2000000;
    public static final int DATA_FILE_VER_21 = 0x2010000;
    public static final int DATA_FILE_VER_22 = 0x2020000;
    public static final int DATA_FILE_VER_30 = 0x3000000;
    public static final int DATA_FILE_VER_31 = 0x3010000;
    public static final int DATA_FILE_VER_311 = 0x3010001;
    public static final int DATA_FILE_VER_3_1_2 = 50397186;
    public static final int DATA_FILE_VER_3_2 = 0x3020000;
    public static final int DATA_FILE_VER_3_2_1 = 50462721;
    public static final int DATA_FILE_VER_CUR = 50462721;
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
    public static final int MAX_COLOR_SIZE = 65536;
    public static final int MAX_FONT_SIZE = 256;

    private GridConsts() {
    }

    public static IntList createEdgePallette() {
        return GridConsts.toPallette(GridPallette.COLOR_PALLETTE_EDGE);
    }

    public static IntList createFontPallette() {
        return GridConsts.toPallette(GridPallette.COLOR_PALLETTE_FONT);
    }

    public static IntList createCellPallette() {
        return GridConsts.toPallette(GridPallette.COLOR_PALLETTE_CELL);
    }

    private static IntList toPallette(int[] colors) {
        IntList pallette = new IntList();
        for (int i = 0; i < colors.length; ++i) {
            pallette.add(colors[i]);
        }
        return pallette;
    }
}

