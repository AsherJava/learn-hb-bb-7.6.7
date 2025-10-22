/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.xg.draw2d.Font
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.consts.GraphObjectType;
import com.jiuqi.nr.single.core.para.parser.print.GBezierLine;
import com.jiuqi.nr.single.core.para.parser.print.GBitMap;
import com.jiuqi.nr.single.core.para.parser.print.GEllipse;
import com.jiuqi.nr.single.core.para.parser.print.GLineItem;
import com.jiuqi.nr.single.core.para.parser.print.GPoints;
import com.jiuqi.nr.single.core.para.parser.print.GPolyLine;
import com.jiuqi.nr.single.core.para.parser.print.GRectangleItem;
import com.jiuqi.nr.single.core.para.parser.print.GRoundRect;
import com.jiuqi.nr.single.core.para.parser.print.GText;
import com.jiuqi.nr.single.core.para.parser.print.GraphGroup;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import com.jiuqi.nr.single.core.para.parser.print.IGraphItem;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.xg.draw2d.Font;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintReadUtil {
    private static final Logger logger = LoggerFactory.getLogger(PrintReadUtil.class);

    public static Font readFont(Stream is) throws IOException, StreamException {
        Font font = new Font();
        String fontName = ReadUtil.readStreams(is);
        Byte charSet = ReadUtil.readByteValue(is);
        long fontHeight = ReadUtil.readIntValue(is);
        Byte fontStyle = ReadUtil.readByteValue(is);
        long fontColor = ReadUtil.readIntValue(is);
        if (StringUtils.isNotEmpty((String)fontName)) {
            font.setName(fontName);
        }
        font.setSize((double)(-fontHeight));
        font.setColor((int)fontColor);
        byte style = fontStyle;
        if (style >= 0) {
            if (style % 2 == 1) {
                font.setBold(true);
            }
            if ((style >> 1) % 2 == 1) {
                font.setItalic(true);
            }
            if ((style >> 2) % 2 == 1) {
                font.setUnderline(true);
            }
            if ((style >> 3) % 2 == 1) {
                font.setStrikeout(true);
            }
        }
        return font;
    }

    public static IGraphItem loadGraphItem(Stream is) throws IOException, StreamException {
        GraphItem item = PrintReadUtil.loadGraphItem2(is);
        return item;
    }

    public static GraphItem loadGraphItem2(Stream is) throws IOException, StreamException {
        GraphItem item = null;
        int type = ReadUtil.readSmallIntValue(is);
        if (type != GraphObjectType.GTCUSTOM.ordinal()) {
            if (type == GraphObjectType.GTGROUP.ordinal()) {
                item = new GraphGroup();
            } else if (type == GraphObjectType.GTLINE.ordinal()) {
                item = new GLineItem();
            } else if (type == GraphObjectType.GTPOINTS.ordinal()) {
                item = new GPoints();
            } else if (type == GraphObjectType.GTPOLY_LINE.ordinal()) {
                item = new GPolyLine();
            } else if (type == GraphObjectType.GTBEZIER_LINE.ordinal()) {
                item = new GBezierLine();
            } else if (type == GraphObjectType.GTRECTANGLE.ordinal()) {
                item = new GRectangleItem();
            } else if (type == GraphObjectType.GTROUND_RECT.ordinal()) {
                item = new GRoundRect();
            } else if (type == GraphObjectType.GTELLIPSE.ordinal()) {
                item = new GEllipse();
            } else if (type == GraphObjectType.GTTEXT.ordinal()) {
                item = new GText();
            } else if (type == GraphObjectType.GTBITMAP.ordinal()) {
                item = new GBitMap();
            } else {
                logger.info("\u6709\u65b0\u63a7\u4ef6\u3010" + type + "\u3011\u672a\u652f\u6301\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            }
        }
        if (null != item) {
            item.loadFromStream(is);
        }
        return item;
    }
}

