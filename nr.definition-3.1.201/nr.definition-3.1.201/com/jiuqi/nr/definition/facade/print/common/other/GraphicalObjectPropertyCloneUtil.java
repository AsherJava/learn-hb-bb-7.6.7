/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IGraphicalObject
 *  com.jiuqi.xg.process.obj.TextDrawObject
 *  com.jiuqi.xg.process.obj.TextTemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.other;

import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IGraphicalObject;
import com.jiuqi.xg.process.obj.TextDrawObject;
import com.jiuqi.xg.process.obj.TextTemplateObject;

public class GraphicalObjectPropertyCloneUtil {
    public static void TextPropertyClone(TextDrawObject sText, TextDrawObject tText) {
        GraphicalObjectPropertyCloneUtil.basicPropertyClone((IGraphicalObject)sText, (IDrawObject)tText);
        tText.setID(sText.getID());
        tText.setFont(sText.getFont().clone());
        tText.setArgle(sText.getArgle());
        tText.setIndent(sText.getIndent());
        tText.setCharSet(sText.getCharSet());
        tText.setOffsetX(sText.getOffsetX());
        tText.setOffsetY(sText.getOffsetY());
        tText.setContent(sText.getContent());
        tText.setAutoSize(sText.isAutoSize());
        tText.setAutoWrap(sText.isAutoWrap());
        tText.setLineSpace(sText.getLineSpace());
        tText.setLetterSpace(sText.getLetterSpace());
        tText.setOrientation(sText.getOrientation());
        tText.setHorizonAlignment(sText.getHorizonAlignment());
        tText.setVerticalAlignment(sText.getVerticalAlignment());
        tText.setInsets(sText.getInsets());
    }

    public static void TextPropertyClone(TextTemplateObject sText, TextDrawObject tText) {
        GraphicalObjectPropertyCloneUtil.basicPropertyClone((IGraphicalObject)sText, (IDrawObject)tText);
        tText.setID(sText.getID());
        tText.setFont(sText.getFont().clone());
        tText.setArgle(sText.getArgle());
        tText.setIndent(sText.getIndent());
        tText.setCharSet(sText.getCharSet());
        tText.setOffsetX(sText.getOffsetX());
        tText.setOffsetY(sText.getOffsetY());
        tText.setContent(sText.getContent());
        tText.setAutoSize(sText.isAutoSize());
        tText.setAutoWrap(sText.isAutoWrap());
        tText.setLineSpace(sText.getLineSpace());
        tText.setLetterSpace(sText.getLetterSpace());
        tText.setOrientation(sText.getOrientation());
        tText.setHorizonAlignment(sText.getHorizonAlignment());
        tText.setVerticalAlignment(sText.getVerticalAlignment());
        tText.setInsets(sText.getInsets());
    }

    private static void basicPropertyClone(IGraphicalObject source, IDrawObject target) {
        String[] names;
        target.setX(source.getX());
        target.setY(source.getY());
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setBorder(source.getBorder());
        target.setBackgroundColor(source.getBackgroundColor());
        target.setBackgroundVisible(source.isBackgroundVisible());
        target.setHorizontalScale(source.getHorizontalScale());
        target.setVerticalScale(source.getVerticalScale());
        for (String name : names = source.getPropertyNames()) {
            target.setProperty(name, source.getProperty(name));
        }
    }
}

