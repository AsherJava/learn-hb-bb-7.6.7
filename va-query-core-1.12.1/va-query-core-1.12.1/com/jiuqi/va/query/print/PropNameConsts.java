/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.kernel.geom.PageSize
 */
package com.jiuqi.va.query.print;

import com.itextpdf.kernel.geom.PageSize;

public class PropNameConsts {

    public static enum PrintFloatContainerPropEnum {
        height,
        width,
        top,
        right,
        left,
        bottom;

    }

    public static enum PrintSchemePropEnum {
        name,
        title,
        document;

    }

    public static enum BarcodeEnum {
        barCodeType,
        width,
        height;

    }

    public static enum TableLayoutUnitEnum {
        size,
        scale,
        auto;

    }

    public static enum GridColPropEnum {
        width;

    }

    public static enum GridRowPropEnum {
        height;

    }

    public static enum GridCellPropEnum {
        rowIndex,
        columnIndex,
        spanColumn,
        spanRow,
        expression,
        padding,
        font,
        rowType,
        verticalAlignment,
        horizontalAlignment,
        backgroundColor;

    }

    public static enum BorderStyleTypeEnum {
        rightBorderStyle,
        bottomBorderStyle,
        leftBorderStyle,
        topBorderStyle;

    }

    public static enum BorderColorTypeEnum {
        rightBorderColor,
        bottomBorderColor,
        leftBorderColor,
        topBorderColor;

    }

    public static enum BorderTypeEnum {
        dotted,
        dashed,
        solid,
        _double;

    }

    public static enum BorderPropEnum {
        style,
        size;

    }

    public static enum FontPropEnum {
        fontFamily,
        size,
        color,
        bold,
        italic;

    }

    public static enum QueryItemEnum {
        name,
        title,
        params;

    }

    public static enum QueryTableControlPropEnum {
        queryItem;

    }

    public static enum ColumnsObjEnum {
        width,
        colHiddenExpression;

    }

    public static enum GridControlPropEnum {
        rows,
        columns,
        columnsObj,
        rowsObj,
        border,
        cells,
        printEmptyTableFlag,
        bindChildTable,
        floatRowFilterExpressionList;

    }

    public static enum TextControlPropEnum {
        foldLine,
        rowSpace,
        font,
        underlined,
        removelined,
        textAlignment;

    }

    public static enum BarCodeTypeEnum {
        CODE128,
        CODE39,
        CODABAR,
        EAN13,
        INTER25,
        POSTNET,
        QR;

    }

    public static enum LayoutDataEnum {
        layoutData,
        type,
        tableLayoutData,
        xyLayoutData;

    }

    public static enum TableLayoutEnum {
        rows,
        columns,
        gap;

    }

    public static enum LayoutEnum {
        layout,
        type,
        tableLayout,
        XYLayout;

    }

    public static enum PageTypeEnum {
        A2,
        A4,
        A8,
        Custom;


        public PageSize valueOf() {
            switch (this) {
                case A2: {
                    return PageSize.A2;
                }
                case A8: {
                    return PageSize.A8;
                }
                case Custom: {
                    return new PageSize(0.0f, 0.0f);
                }
            }
            return PageSize.A4;
        }
    }

    public static enum PrintDirectionEnum {
        horizontal,
        vertical;

    }

    public static enum PagePropEnum {
        direction,
        pageType,
        width,
        height,
        waterMark;

    }

    public static enum VerticalAlignEnum {
        TOP,
        MIDDLE,
        BOTTOM;

    }

    public static enum HorizontalAlignEnum {
        LEFT,
        CENTER,
        RIGHT;

    }

    public static enum TextAlignment {
        LEFT,
        CENTER,
        RIGHT;

    }

    public static enum TableLayoutDataEnum {
        spanColumn,
        spanRow,
        rowIndex,
        columnIndex;

    }

    public static enum PrintRangeEnum {
        all,
        head,
        end,
        no;

    }

    public static enum CommonPropNameEnum {
        id,
        type,
        printRange,
        children,
        expression,
        compiledExpression,
        hAlign,
        vAlign,
        padding,
        controlHide,
        pagingPrintFlag;

    }
}

