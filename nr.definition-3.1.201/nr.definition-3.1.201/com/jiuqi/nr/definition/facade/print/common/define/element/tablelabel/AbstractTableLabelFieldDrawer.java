/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.CellField
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.draw2d.Color
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.draw2d.Graphics
 *  com.jiuqi.xg.draw2d.Image
 *  com.jiuqi.xg.draw2d.geometry.Point
 *  com.jiuqi.xg.draw2d.geometry.Rectangle
 *  com.jiuqi.xg.draw2d.text.TextItem
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.IGraphicalText
 *  com.jiuqi.xg.process.table.util.TableUtil
 *  com.jiuqi.xg.process.util.ProcessUtil
 *  com.jiuqi.xg.process.util.TextureUtil
 *  com.jiuqi.xlib.resource.IResource
 *  com.jiuqi.xlib.resource.IResourceManager
 *  com.jiuqi.xlib.utils.GUID
 *  com.jiuqi.xlib.utils.IOUtils
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.CellField;
import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelDrawObject;
import com.jiuqi.xg.draw2d.Color;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.draw2d.Graphics;
import com.jiuqi.xg.draw2d.Image;
import com.jiuqi.xg.draw2d.geometry.Point;
import com.jiuqi.xg.draw2d.geometry.Rectangle;
import com.jiuqi.xg.draw2d.text.TextItem;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.IGraphicalText;
import com.jiuqi.xg.process.table.util.TableUtil;
import com.jiuqi.xg.process.util.ProcessUtil;
import com.jiuqi.xg.process.util.TextureUtil;
import com.jiuqi.xlib.resource.IResource;
import com.jiuqi.xlib.resource.IResourceManager;
import com.jiuqi.xlib.utils.GUID;
import com.jiuqi.xlib.utils.IOUtils;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTableLabelFieldDrawer {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTableLabelFieldDrawer.class);
    protected static final double A_PIXEL_LENGTH = 0.2645833;
    protected double offsetX;
    protected double offsetY;
    protected Graphics graphics;
    protected TableLabelDrawObject tableLabelDrawObj;

    public void init(TableLabelDrawObject tableLabelObj, Graphics graphics, double offsetX, double offsetY) {
        this.tableLabelDrawObj = tableLabelObj;
        this.graphics = graphics;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void drawFieldContent(CellField field, double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.drawBackground(field);
        this.drawContent(field);
        this.drawForeground(field);
    }

    protected void drawForeground(CellField cellfield) {
    }

    protected void drawBackground(CellField field) {
        int color;
        GridData grid;
        GridCell cell;
        if (this.tableLabelDrawObj.isBackgroundVisible() && (cell = (grid = this.tableLabelDrawObj.getGridData()).getCell(field.left, field.top)).getBackStyle() != 0 && (color = cell.getBackColor()) != -16777211) {
            if (color == -16777201) {
                color = 0xC0C0C0;
            }
            this.graphics.getBrush().setColor(new Color(color));
            this.graphics.getBrush().setOpaque(true);
            this.graphics.getPen().setWidth(0.0);
            this.graphics.drawRect(this.offsetX, this.offsetY, TableUtil.getGridRangeWidth((GridData)grid, (int)field.left, (int)field.right), TableUtil.getGridRangeHeight((GridData)grid, (int)field.top, (int)field.bottom));
        }
    }

    protected void drawContent(CellField field) {
        GridCell cell = this.tableLabelDrawObj.getGridData().getCell(field.left, field.top);
        if (cell.getDataType() != 6) {
            this.drawTextContent(field);
        } else {
            this.drawImageContent(field);
        }
    }

    protected void drawTextContent(CellField field) {
        GridCell cell = this.tableLabelDrawObj.getGridData().getCell(field.left, field.top);
        if (this.isTextContentTobeDrawn(cell)) {
            TextItem textItem = new TextItem();
            this.initTextItem(textItem, cell);
            String content = textItem.getText();
            Rectangle textRect = this.getDefaultBoundingRect(textItem, cell);
            if (cell.getWrapLine()) {
                content = TextureUtil.autoWrap((String)content, (double)textRect.getWidth(), (FontMetrics)FontMetrics.getMetrics((Font)textItem.getFont()));
                textItem.setText(content);
            }
            this.graphics.pushState();
            this.graphics.setClipping(true);
            this.graphics.setClipRect(new Rectangle(this.offsetX, this.offsetY, textItem.getWidth(), textItem.getHeight()));
            textItem.setWidth(textRect.getWidth());
            textItem.setHeight(textRect.getHeight());
            if (textItem.getText() != null) {
                this.graphics.drawTextItem(new Point(textRect.getX() + 0.2645833, textRect.getY() + 0.5291666), textItem);
            }
            this.graphics.popState();
        }
    }

    protected void drawImageContent(CellField field) {
        GridData grid = this.tableLabelDrawObj.getGridData();
        GridCell cell = this.tableLabelDrawObj.getGridData().getCell(field.left, field.top);
        byte[] imageBytes = this.getCellImageBytes(cell);
        if (imageBytes != null) {
            IResourceManager resMgr = ProcessUtil.getGraphicalDocument((IGraphicalElement)this.tableLabelDrawObj).getResourceManager();
            IResource res = resMgr.createResource(2, GUID.newGUID(), null, imageBytes);
            Image drawImg = new Image(res);
            drawImg.setBitsPerPixel(32);
            drawImg.setPaddingType(1);
            double width = TableUtil.getGridRangeWidth((GridData)grid, (int)field.left, (int)field.right);
            double height = TableUtil.getGridRangeHeight((GridData)grid, (int)field.top, (int)field.bottom);
            drawImg.setWidth(width);
            drawImg.setHeight(height);
            drawImg.setSuffix(cell.getImageType().toLowerCase());
            this.graphics.drawImage(drawImg, this.offsetX + 0.38, this.offsetY + 0.4);
        }
    }

    private boolean isTextContentTobeDrawn(GridCell cell) {
        boolean isDrawn = false;
        String content = cell.getShowText();
        if (content != null && !content.equals("0")) {
            isDrawn = true;
        }
        return isDrawn;
    }

    private void initTextItem(TextItem textItem, GridCell cell) {
        GridData grid = this.tableLabelDrawObj.getGridData();
        CellField field = cell.getCellField();
        if (cell.getShowText() != null) {
            Font font = new Font(cell.getFontName(), (double)cell.getFontSize(), cell.getFontColor(), 0);
            font.setItalic(cell.getFontItalic());
            font.setBold(cell.getFontBold());
            font.setStrikeout(cell.getFontStrikeOut());
            font.setUnderline(cell.getFontUnderLine());
            font.setSize(this.graphics.getDevice().getLengthUnit().fromPoint(font.getSize()));
            textItem.setFont(font);
            textItem.setText(cell.getShowText());
            textItem.setWidth(TableUtil.getGridRangeWidth((GridData)grid, (int)field.left, (int)field.right));
            textItem.setHeight(TableUtil.getGridRangeHeight((GridData)grid, (int)field.top, (int)field.bottom));
            textItem.setIndent((double)cell.getIndent());
            textItem.setHorizonAlignment(TableUtil.convertGridCellHorzAlign((GridCell)cell, (int)cell.getHorzAlign()));
            textItem.setVerticalAlignment(TableUtil.convertGridCellVertAlign((GridCell)cell, (int)cell.getVertAlign()));
        }
    }

    private Rectangle getDefaultBoundingRect(TextItem textItem, GridCell cell) {
        Rectangle rect = new Rectangle(this.offsetX, this.offsetY, textItem.getWidth(), textItem.getHeight());
        rect.crop(IGraphicalText.DEFAULT_TEXT_INSETS);
        if (!cell.getVertText()) {
            double indentWidth = textItem.getIndent() * 0.2645833;
            rect.width -= 2.0 * indentWidth;
            rect.x += indentWidth;
        }
        return rect;
    }

    private byte[] getCellImageBytes(GridCell cell) {
        String path = cell.getImageReference();
        byte[] imageBytes = null;
        if (path != null) {
            try {
                imageBytes = IOUtils.getFileContentsData((String)path);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            imageBytes = cell.getImageData();
        }
        return imageBytes;
    }
}

