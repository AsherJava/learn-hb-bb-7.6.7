/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2.json;

public class Grid2DataConst {
    static int[][] colors = new int[][]{{-16777211, -16777201, 0xFFFFFF, 0xE0E0E0, 0xC0C0C0, 0x808080, 0x666666, 0x333333}, {0xAA00FF, 0xCC55FF, 0xAAAAFF, 0xAAFFFF, 65535, 43775, 26352, 255}, {0xFF00FF, 0xFF55AA, 0xFFAAAA, 0xFFFFAA, 0x66FFAA, 0x55AAAA, 5596912, 0x5500AA}, {0x800080, 0xAA55AA, 0xFFAAFF, 0xAAFFAA, 57514, 32896, 21930, 128}, {0xFF0055, 0xFF5555, 0xFFAA55, 0xFFFF55, 0x99FF55, 0x55CC55, 0x5566AA, 0x550055}, {0xAA0055, 0xAA5555, 0xAAAA55, 0xCCFF55, 65433, 43622, 21845, 85}, {0xFF0000, 0xFF6600, 0xFFCC00, 0xFFFF00, 0x66FF00, 0x66AA00, 0x555500, 0x550000}, {0x800000, 0xAA5500, 0x808000, 0xAAFF00, 65280, 32768, 21760, 0}};
    static final int[] borderColors = new int[]{-16777201, -16777211, 0xE0E0E0, 0x808080, 0x666666, 0x333333, 0, 65535, 128, 255, 32768, 65280, 0x800000, 0xFF0000, 0x800080, 0xFF00FF};
    public static final String PROPERTY_OPTION = "options";
    public static final String PROPERTY_CELLS = "cells";
    public static final String PROPERTY_MERGE_CELLS = "mergeCells";
    public static final String PROPERTY_ROWS = "rows";
    public static final String PROPERTY_COLS = "cols";
    public static final String PROPERTY_COL_HEADER_COUNT = "colHeaderCount";
    public static final String PROPERTY_ROW_HEADER_COUNT = "rowHeaderCount";
    public static final String PROPERTY_COL_FOOTER_COUNT = "colFooterCount";
    public static final String PROPERTY_ROW_FOOTER_COUNT = "rowFooterCount";
    public static final String PROPERTY_DEFAULT_EDITER_ID = "defaultEditorId";
    public static final String PROPERTY_ROW_COUNT = "rowCount";
    public static final String PROPERTY_COL_COUNT = "colCount";
    public static final String PROPERTY_WIDTH = "width";
    public static final String PROPERTY_HEIGHT = "height";
    public static final String PROPERTY_DEFAULT_STYLE = "defaultStyle";
    public static final String PROPERTY_HEAD_STYLE = "headStyle";
    public static final String PROPERTY_DEFAULT_FONT = "defalutFont";
    public static final String OPTION_LOAD_MODE = "loadMode";
    public static final String OPTION_LOAD_BYROW = "loadByRow";
    public static final String OPTION_SELECTION_MODE = "selectionMode";
    public static final String OPTION_EDIT_MODE = "editMode";
    public static final String OPTION_ENTER_NEXT = "enterNext";
    public static final String OPTION_ROW_SELECTABLE = "rowSelectable";
    public static final String OPTION_COL_SELECTABLE = "colSelectable";
    public static final String OPTION_IGNORE_HIDDEN = "ignoreHidden";
    public static final String OPTION_COL_RESIZEABLE = "colResizeable";
    public static final String OPTION_ROW_RESIZEABLE = "rowResizeable";
    public static final String OPTION_COL_FREE_RESIZEABLE = "colFreeResizeable";
    public static final String OPTION_ROW_FREE_RESIZEABLE = "rowFreeResizeable";
    public static final String OPTION_COL_GRABABLE = "colGrabable";
    public static final String OPTION_SHOW_SELECTION_BORDER = "showSelectionBorder";
    public static final String OPTION_CURRENT_CELL_BORDER_HIDDEN = "currentCellBorderHidden";
    public static final String OPTION_COL_EXCHANGEABLE = "colExchangeable";
    public static final String OPTION_PASS_READ_ONLY = "passReadOnly";
    public static final String OPTION_SHOW_SELECTION_CHANGE = "showSelectionChange";
    public static final String OPTION_SELECTION_COLOR = "selectionColor";
    public static final String OPTION_SELECTION_BORDER_COLOR = "selectionBorderColor";
    public static final String OPTION_SELECTION_BORDER_WIDTH = "selectionBorderWidth";
    public static final String OPTION_CURRENT_CELL_COLOR = "currentCellColor";
    public static final String OPTION_BLUR_CURRENT_CELL_COLOR = "blurCurrentCellColor";
    public static final String OPTION_CURRENT_CELL_BORDER_COLOR = "currentCellBorderColor";
    public static final String OPTION_CURRENT_CELL_SHOW_TYPE = "currentCellShowType";
    public static final String OPTION_MERGE_CELL_SHOW_MODE = "mergeCellShowMode";
    public static final String OPTION_HIDE_SINGLE_SELECT = "hideSingleSelect";
    public static final String OPTION_SIMPLE_INSERT = "simpleInsert";
    public static final String OPTION_TABLE_CELL = "tableCell";
    public static final String OPTION_CELLBOOLATTRS = "cellBoolAttrs";
    public static final String OPTION_CELLBOOLATTRS_FONTITALIC = "fontItalicTure";
    public static final String OPTION_CELLBOOLATTRS_FONTBOLD = "fontBoldTure";
    public static final String OPTION_CELLBOOLATTRS_EDITABLE = "editableFalse";
    public static final String OPTION_CELLBOOLATTRS_SELECTABLE = "selectableFalse";
    public static final String OPTION_CELLBOOLATTRS_VERTTEXT = "vertTextTure";
    public static final String OPTION_SCRIPT = "script";
    public static final String OPTION_SHOW_MERGECHILD_BORDER = "showMergeChildBorder";
    public static final String OPTION_DEFUALT_BORDER_COLOR = "defaultBorderColor";
    public static final String OPTION_DEFUALT_BACK_COLOR = "defaultBackColor";
    public static final String CELLS_ROWLIST = "rowList";
    public static final String PROPERTY_ROW = "row";
    public static final String PROPERTY_COL = "col";
    public static final String PROPERTY_COLOR = "color";
    public static final String PROPERTY_SIZE = "size";
    public static final String PROPERTY_AUTO = "auto";
    public static final String PROPERTY_HIDDEN = "hidden";
    public static final String PROPERTY_CLIENT_SIZE = "clientSize";
    public static final String PROPERTY_GRAB = "grab";
    public static final String PROPERTY_DIRTY = "dirty";
    public static final String PROPERTY_MIN_SIZE = "minSize";
    protected static final String PROPERTY_SHOW_TEXT = "showText";
    protected static final String PROPERTY_EDIT_TEXT = "editText";
    protected static final String PROPERTY_COL_INDEX = "colIndex";
    protected static final String PROPERTY_ROW_INDEX = "rowIndex";
    protected static final String PROPERTY_EDITOR_ID = "editorId";
    protected static final String PROPERTY_HTML = "html";
    protected static final String PROPERTY_CONTROL = "control";
    protected static final String PROPERTY_TREE_IMAGE = "treeImage";
    protected static final String PROPERTY_CHECKED = "checked";
    protected static final String PROPERTY_CHECKABLE = "checkable";
    protected static final String PROPERTY_EXPANDABLE = "expandable";
    protected static final String PROPERTY_EXPANDED = "expanded";
    protected static final String PROPERTY_DEPTH = "depth";
    protected static final String PROPERTY_ISTREEEND = "isTreeEnd";
    protected static final String PROPERTY_MERGE_INFO = "mergeInfo";
    protected static final String PROPERTY_MERGED = "isMerged";
    protected static final String PROPERTY_CLIENT_DATA = "clientData";
    protected static final String PROPERTY_CELL_MODE = "cellMode";
    protected static final String MERGE_INFO_COL = "col";
    protected static final String MERGE_INFO_ROW = "row";
    protected static final String SHORT_PROPERTY_SHOW_TEXT = "1";
    protected static final String SHORT_PROPERTY_EDIT_TEXT = "2";
    protected static final String SHORT_PROPERTY_COL_INDEX = "3";
    protected static final String SHORT_PROPERTY_ROW_INDEX = "4";
    public static final String PROPERTY_BACK_GROUND_COLOR = "backColor";
    public static final String PROPERTY_BACK_STYLE = "backStyle";
    public static final String PROPERTY_BACK_IMAGE = "backImage";
    public static final String PROPERTY_BACK_IMAGE_STYLE = "backImageStyle";
    public static final String PROPERTY_BACK_IMAGE_HORIZION = "backImageHorizion";
    public static final String PROPERTY_BACK_IMAGE_VERTICAL = "backImageVertical";
    public static final String PROPERTY_BACK_IMAGE_BOUNDS = "backImageBounds";
    public static final String PROPERTY_BORDER_COLOR = "borderColor";
    public static final String PROPERTY_BORDER = "border";
    public static final String PROPERTY_SELECTABLE = "selectable";
    public static final String PROPERTY_EDITABLE = "editable";
    public static final String PROPERTY_WRAP_LINE = "wrapLine";
    public static final String PROPERTY_INDENT = "indent";
    public static final String PROPERTY_INDENT_PX = "indentPx";
    public static final String PROPERTY_VERT_ALIGN = "vertAlign";
    public static final String PROPERTY_HORZ_ALIGN = "horzAlign";
    public static final String PROPERTY_VERT_TEXT = "vertText";
    public static final String PROPERTY_MULTI_LINE = "multiLine";
    public static final String PROPERTY_FONT_NAME = "fontName";
    public static final String PROPERTY_FONT_SIZE = "fontSize";
    public static final String PROPERTY_FONT_SIZE_UNIT = "fontSizeUnit";
    public static final String PROPERTY_FIT_FONT_SIZE = "fitFontSize";
    public static final String PROPERTY_DECORATION = "decoration";
    public static final String PROPERTY_FONT_BOLD = "fontBold";
    public static final String PROPERTY_FONT_ITALIC = "fontItalic";
    public static final String PROPERTY_FONT_DECORATION = "decoration";
    public static final String PROPERTY_FONT_COLOR = "fontColor";
    public static final String PROPERTY_FORE_GROUND_COLOR = "foregroundColor";
    public static final String PROPERTY_FONT_STYLE = "fontStyle";
    public static final String PROPERTY_TEXT_STROKE = "textStroke";
    public static final String PROPERTY_TEXT_SHADOW = "textShadow";
    public static final String PROPERTY_ROW_SPAN = "rowSpan";
    public static final String PROPERTY_COL_SPAN = "colSpan";
    public static final String PROPERTY_PADDING = "padding";
    public static final String PROPERTY_SILVERHEAD = "silverHead";
    public static final String PROPERTY_DATATYPE = "dataType";
    public static final String PROPERTY_TEXT_SHADOW_OFFSETX = "offsetX";
    public static final String PROPERTY_TEXT_SHADOW_OFFSETY = "offsetY";
    public static final String PROPERTY_TEXT_SHADOW_BLUR = "blur";
    public static final String PROPERTY_TEXT_SHADOW_COLOR = "color";
    public static final int FONT_STYLE_PLAIN = 1;
    public static final int FONT_STYLE_BOLD = 2;
    public static final int FONT_STYLE_ITALIC = 4;
    public static final int FONT_STYLE_UNDERLINE = 8;
    public static final int FONT_STYLE_INLINE = 16;
    public static final String FONT_STYLE_NAME_PLAIN = "plain";
    public static final String FONT_STYLE_NAME_BOLD = "bold";
    public static final String FONT_STYLE_NAME_ITALIC = "italic";
    public static final String FONT_STYLE_NAME_UNDERLINE = "underline";
    public static final String FONT_STYLE_NAME_INLINE = "inline";

    public static enum BACK_IMAGE_STYLE {
        BACK_IMAGE_STYLE_REPEAT(1),
        BACK_IMAGE_STYLE_REPEAT_X(2),
        BACK_IMAGE_STYLE_REPEAT_Y(3),
        BACK_IMAGE_STYLE_STRETCH(4),
        BACK_IMAGE_STYLE_POSITION(5),
        BACK_IMAGE_STYLE_BOUNDS(6);

        private int index;

        private BACK_IMAGE_STYLE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum DIRECTION {
        DIRECTION_HORIZ(0),
        DIRECTION_VERTI(1);

        private int index;

        private DIRECTION(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum BACK_MODE {
        BACK_MODE_STYLE(1),
        BACK_MODE_COLOR(2),
        BACK_MODE_GRADIRENT(3);

        private int index;

        private BACK_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum Cell_MODE {
        Cell_MODE_NORMAL(1),
        Cell_MODE_TREE(2),
        Cell_MODE_CONTROL(3),
        Cell_MODE_HTML(4);

        private int index;

        private Cell_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum FONT_SIZE_UNIT {
        FONT_SIZE_UNIT_PX(0),
        FONT_SIZE_UNIT_PT(1);

        private int index;

        private FONT_SIZE_UNIT(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum BUTTON {
        BUTTON_LEFT(1),
        BUTTON_RIGHT(2),
        BUTTON_CENTER(4);

        private int index;

        private BUTTON(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum CAUSE {
        CAUSE_MOUSE(1),
        CAUSE_KEY(2),
        CAUSE_CALL(3),
        CAUSE_CLEAR(4),
        CAUSE_CUT(5),
        CAUSE_PASTE(6),
        CAUSE_INPUT(7),
        CAUSE_DELETE(8),
        CAUSE_CLICKFACE(9);

        private int index;

        private CAUSE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum PASTE {
        PASTE_TEXT(1),
        PASTE_STYLE(2),
        PASTE_BOTH(3);

        private int index;

        private PASTE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum COPY {
        COPY_CONTENT(1),
        COPY_STYLE(2),
        COPY_BOTH(3);

        private int index;

        private COPY(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum RESULT {
        RESULT_FALSE(0),
        RESULT_TRUE(1);

        private int index;

        private RESULT(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum MERGE_SHOW_MODE {
        MERGE_SHOW_MODE_NORMAL(1),
        MERGE_SHOW_MODE_CONTRACT(2);

        private int index;

        private MERGE_SHOW_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum ENTER_NEXT {
        ENTER_NEXT_NONE(0),
        ENTER_NEXT_UP(1),
        ENTER_NEXT_LEFT(2),
        ENTER_NEXT_DOWN(3),
        ENTER_NEXT_RIGHT(4);

        private int index;

        private ENTER_NEXT(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum DELETE_DEAL {
        DELETE_DEAL_VERTI(1),
        DELETE_DEAL_HORIZ(2);

        private int index;

        private DELETE_DEAL(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum EDIT_MODE {
        EDIT_MODE_READ_ONLY(1),
        EDIT_MODE_INPUT(2),
        EDIT_MODE_EDIT(3);

        private int index;

        private EDIT_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum SELECTION_MODE {
        SELECTION_MODE_ROW(1),
        SELECTION_MODE_COL(2),
        SELECTION_MODE_MULTI(3),
        SELECTION_MODE_SINGLE(4);

        private int index;

        private SELECTION_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static enum LOAD_MODE {
        LOAD_MODE_NORMAL(1),
        LOAD_MODE_LAZY(2),
        LOAD_MODE_STREAM(3);

        private int index;

        private LOAD_MODE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

