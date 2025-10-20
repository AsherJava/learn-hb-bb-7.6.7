/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 */
package com.jiuqi.common.expimp.dataexport.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    public int index() default -1;

    public String[] title() default {""};

    public CellType cellType() default CellType.STRING;

    public HorizontalAlignment horizontalAlignment() default HorizontalAlignment.GENERAL;

    public VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    public String dataFormat() default "General";

    public boolean hidden() default false;

    public boolean locked() default false;

    public boolean quotePrefix() default false;

    public boolean wrapped() default false;

    public short rotation() default 0;

    public short indent() default 0;

    public BorderStyle borderLeft() default BorderStyle.THIN;

    public BorderStyle borderRight() default BorderStyle.THIN;

    public BorderStyle borderTop() default BorderStyle.THIN;

    public BorderStyle borderBottom() default BorderStyle.THIN;

    public short leftBorderColor() default 0;

    public short rightBorderColor() default 0;

    public short topBorderColor() default 0;

    public short bottomBorderColor() default 0;

    public FillPatternType fillPatternType() default FillPatternType.SOLID_FOREGROUND;

    public short fillBackgroundColor() default 9;

    public short fillForegroundColor() default 9;

    public boolean shrinkToFit() default false;

    public String fontName() default "\u5b8b\u4f53";

    public short fontHeightInPoints() default 13;

    public boolean fontItalic() default false;

    public boolean fontStrikeout() default false;

    public short fontColor() default 0;

    public short fontTypeOffset() default 0;

    public byte fontUnderline() default 0;

    public int fontCharset() default 0;

    public boolean fontBold() default false;
}

