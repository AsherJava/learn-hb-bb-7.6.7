/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import java.util.HashMap;
import java.util.Map;

public class GridEnums {
    private static final Map<Object, int[]> Enum2Values = new HashMap<Object, int[]>();

    public static <E extends Enum<E>> E getEnumValue(Class<E> clazz, int value) {
        int index = -1;
        int[] values = Enum2Values.get(clazz);
        if (values == null) {
            index = value;
        } else {
            for (int i = 0; i < values.length; ++i) {
                if (values[i] != value) continue;
                index = i;
                break;
            }
        }
        if (index == -1) {
            return null;
        }
        return (E)((Enum[])clazz.getEnumConstants())[index];
    }

    public static <E extends Enum<E>> int getIntValue(E value) {
        int[] values = Enum2Values.get(value.getClass());
        if (values == null) {
            return value.ordinal();
        }
        return values[value.ordinal()];
    }

    static {
        Enum2Values.put(EditMode.class, new int[]{0, 4, 8});
        Enum2Values.put(EnterNext.class, new int[]{0, 512, 1024, 1536, 2048});
        Enum2Values.put(DataType.class, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        Enum2Values.put(BackgroundStyle.class, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26});
    }

    public static enum GradientDirection {
        UP2DOWN,
        LEFT2RIGHT;

    }

    public static enum GridEventCause {
        Program,
        KeyPressed,
        LeftButton,
        RightButton;

    }

    public static enum BackgroundImagePositionHorizon {
        LEFT,
        CENTER,
        RIGHT;

    }

    public static enum BackgroundImagePositionVectical {
        TOP,
        CENTER,
        BOTTOM;

    }

    public static enum BackgroundImageStyle {
        NONE,
        REPEAT_BOTH,
        FILL_CONTAINER;

    }

    public static enum ImeMode_TITLE {
        \u81ea\u52a8,
        \u6253\u5f00\u8f93\u5165\u6cd5,
        \u5173\u95ed\u8f93\u5165\u6cd5,
        \u7981\u6b62\u4f7f\u7528\u8f93\u5165\u6cd5;

    }

    public static enum ImeMode {
        Auto,
        Open,
        Close,
        Disable;

    }

    public static enum OutputKind_TITLE {
        \u9ed8\u8ba4\u6253\u5370\u8f93\u51fa,
        \u603b\u662f\u6253\u5370\u8f93\u51fa,
        \u6709\u65f6\u6253\u5370\u8f93\u51fa,
        \u603b\u662f\u4e0d\u8f93\u51fa;

    }

    public static enum OutputKind {
        PRINT_OUTPUT_KIND_DEFAULT,
        PRINT_OUTPUT_KIND_ALWARS,
        PRINT_OUTPUT_KIND_ATTIME,
        PRINT_OUTPUT_KIND_NONE;

    }

    public static enum DataType_TITLE {
        \u81ea\u52a8\u5224\u65ad,
        \u6587\u5b57,
        \u6570\u503c,
        \u8d27\u5e01,
        \u5e03\u5c14,
        \u65f6\u95f4\u65e5\u671f,
        \u56fe\u50cf,
        \u56fe\u8868,
        \u6761\u5f62\u7801,
        \u94fe\u63a5,
        \u63a7\u4ef6,
        OLE\u5bf9\u8c61;

    }

    public static enum DataType {
        Auto,
        Text,
        Number,
        Currency,
        Boolean,
        DateTime,
        Graphic,
        Chart,
        BarCode,
        HotLink,
        Control,
        OLE;

    }

    public static enum TextAlignment_TITLE {
        \u81ea\u52a8\u5224\u65ad,
        \u524d\u7aef\u5bf9\u9f50,
        \u540e\u7aef\u5bf9\u9f50,
        \u5c45\u4e2d\u5bf9\u9f50,
        \u5747\u5300\u5206\u6563\u5bf9\u9f50,
        \u62c9\u4f38\u5bf9\u9f50,
        \u81ea\u52a8\u5224\u65ad\u7684\u540e\u7aef\u5bf9\u9f50;

    }

    public static enum TextAlignment {
        Auto,
        Fore,
        Back,
        Center,
        Sparse,
        Extend,
        MightBack;

    }

    public static enum GridBorderStyle {
        AUTO(-1),
        NONE(0),
        SOLID(1),
        DASH(2),
        BOLD(4),
        DOUBLE(8);

        private int style;

        private GridBorderStyle(int style) {
            this.style = style;
        }

        public int getStyle() {
            return this.style;
        }

        public int getValue() {
            return this.getStyle();
        }
    }

    public static enum BorderStyle_TITLE {
        \u81ea\u52a8\u8fb9\u6846,
        \u65e0\u8fb9\u6846,
        \u5355\u7ebf\u8fb9\u6846,
        \u865a\u7ebf\u578b\u5355\u8fb9\u6846,
        \u70b9\u7ebf\u578b\u5355\u8fb9\u6846,
        \u70b9\u5212\u7ebf\u578b\u5355\u8fb9\u6846,
        \u53cc\u70b9\u5212\u7ebf\u578b\u5355\u8fb9\u6846,
        \u5bbd\u7ebf\u8fb9\u6846\u4e24\u7ebf\u8fde\u5728\u4e00\u8d77,
        \u865a\u7ebf\u578b\u5bbd\u7ebf\u8fb9\u6846,
        \u70b9\u7ebf\u578b\u5bbd\u7ebf\u8fb9\u6846,
        \u53cc\u7ebf\u8fb9\u6846\u4e24\u7ebf\u9694\u5f00,
        \u865a\u7ebf\u578b\u53cc\u7ebf\u8fb9\u6846,
        \u70b9\u7ebf\u578b\u53cc\u7ebf\u8fb9\u6846,
        \u4e09\u7ebf\u8fb9\u6846,
        \u865a\u7ebf\u578b\u4e09\u7ebf\u8fb9\u6846,
        \u70b9\u7ebf\u578b\u4e09\u7ebf\u8fb9\u6846;

    }

    public static enum BorderStyle {
        Auto,
        None,
        Single,
        SingleDash,
        SingleDot,
        SingleDashDot,
        SingleDashDotDot,
        Thick,
        ThickDash,
        ThickDot,
        Double,
        DoubleDash,
        DoubleDot,
        Strong,
        StrongDash,
        StrongDot;

    }

    public static enum JIOBackgroundstyle {
        NONE(0),
        FILL(1),
        HORZ_LINE(7),
        VERT_LINE(8),
        FDIAG_LINE(10),
        BDIAG_LINE(9),
        DIAG_CROSS_BLOCK3(3),
        DIAG_CROSS_LINE(2);

        int map;

        private JIOBackgroundstyle(int map) {
            this.map = map;
        }

        public int getMap() {
            return this.map;
        }

        public static JIOBackgroundstyle valueOf(int ordinal) {
            if (ordinal < 0 || ordinal >= JIOBackgroundstyle.values().length) {
                return NONE;
            }
            return JIOBackgroundstyle.values()[ordinal];
        }
    }

    public static enum BackgroundStyle_TITLE {
        \u900f\u660e\u65e0\u80cc\u666f,
        \u5355\u5143\u683c\u989c\u8272\u5b9e\u5fc3\u586b\u5145,
        \u4ea4\u53c9\u659c\u7ebf,
        X\u89c4\u5219\u6392\u5217,
        \u4ea4\u53c9\u6392\u5217,
        \u89c4\u5219\u6392\u5217\u7684\u70b9\u9635,
        \u4ea4\u53c9\u6392\u5217\u7684\u70b9\u9635,
        \u6a2a\u7ebf,
        \u7ad6\u7ebf,
        \u53cd\u659c\u7ebf,
        \u659c\u7ebf,
        \u7a00\u758f\u6a2a\u7ebf,
        \u7a00\u758f\u7ad6\u7ebf,
        \u6a2a\u5411\u70b9\u5212\u7ebf,
        \u7ad6\u5411\u70b9\u5212\u7ebf,
        \u7816\u5899\u5f62\u56fe\u6848,
        \u6811\u5f62\u56fe\u6848,
        \u8349\u5730\u56fe\u6848,
        \u5c0f\u82b1\u6735\u56fe\u6848,
        \u5c0f\u5706\u5708\u56fe\u6848,
        \u83f1\u5f62\u56fe\u6848,
        \u659c\u5341\u5b57\u67b6,
        \u5c71\u5f62\u56fe\u6848,
        \u65b9\u683c\u7ebf,
        \u4ea4\u53c9\u6392\u5217\u7684\u65b9\u5757,
        \u6a2a\u5411\u659c\u6392\u957f\u65b9\u5757,
        \u7ad6\u5411\u659c\u6392\u957f\u65b9\u5757;

    }

    public static enum BackgroundStyle {
        NONE,
        FILL,
        DIAG_CROSS_LINE,
        DIAG_CROSS_BLOCK3,
        CRUX_BLOCK,
        CROSS_DOT,
        DIAG_CROSS_DOT,
        HORZ_LINE,
        VERT_LINE,
        BDIAG_LINE,
        FDIAG_LINE,
        HORZ_LINE_SPARSE,
        VERT_LINE_SPARSE,
        HORZ_DASH_DOT,
        VERT_DASH_DOT,
        WALL,
        TREES,
        GRASS,
        FLOWERS,
        ROUNDS,
        LOZENGES,
        DIAG_CRUX,
        HILLS,
        GRID_LINE,
        DIAG_BLOCK,
        HORIZ_DIAG_BLOCK,
        VERT_DIAG_BLOCK;

    }

    public static enum EnterNext_TITLE {
        \u4e0b,
        \u53f3,
        \u4e0a,
        \u5de6,
        \u65e0;

    }

    public static enum EnterNext {
        DOWN,
        RIGHT,
        UP,
        LEFT,
        NONE;

    }

    public static enum EditMode_TITLE {
        \u8bbe\u8ba1,
        \u8f93\u5165,
        \u53ea\u8bfb;

    }

    public static enum EditMode {
        DESIGN,
        INPUT,
        READONLY;

    }

    public static enum SelectionMode_TITLE {
        \u591a\u9009,
        \u5355\u9009,
        \u884c\u9009;

    }

    public static enum SelectionMode {
        MULTI,
        SINGLE,
        ROW;

    }
}

