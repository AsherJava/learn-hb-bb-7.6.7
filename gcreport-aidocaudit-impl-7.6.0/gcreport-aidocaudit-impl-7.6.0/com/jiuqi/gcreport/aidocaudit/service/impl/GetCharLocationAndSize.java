/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.pdfbox.text.PDFTextStripper
 *  org.apache.pdfbox.text.TextPosition
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.gcreport.aidocaudit.dto.LineTextPosition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class GetCharLocationAndSize
extends PDFTextStripper {
    private final List<LineTextPosition> lineTextPositions = new ArrayList<LineTextPosition>();
    private int pageNum = 0;

    protected void writeString(String text, List<TextPosition> textPositions) {
        LineTextPosition lineTextPosition = new LineTextPosition();
        lineTextPosition.setLineText(text);
        TextPosition textPosition = textPositions.get(0);
        float pageHeight = textPosition.getPageHeight();
        float pageWidth = textPosition.getPageWidth();
        float x = textPosition.getXDirAdj();
        float y = pageHeight - textPosition.getYDirAdj();
        float width = 0.0f;
        float height = 0.0f;
        for (TextPosition textPosition1 : textPositions) {
            width += textPosition1.getWidthDirAdj();
            if (!(textPosition1.getHeightDir() > height)) continue;
            height = textPosition1.getHeightDir();
        }
        lineTextPosition.setX(x);
        lineTextPosition.setY(y);
        lineTextPosition.setWidth(width);
        lineTextPosition.setHeight(height);
        lineTextPosition.setPageHeight(pageHeight);
        lineTextPosition.setPageWidth(pageWidth);
        lineTextPosition.setPageNum(this.pageNum);
        this.lineTextPositions.add(lineTextPosition);
    }

    public List<LineTextPosition> getLineTextPositions() {
        return this.lineTextPositions;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}

