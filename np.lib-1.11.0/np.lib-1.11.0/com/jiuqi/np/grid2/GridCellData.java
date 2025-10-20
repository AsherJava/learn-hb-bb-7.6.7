/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.graphics.ImageDescriptor;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.np.grid2.GridCellStyleData;
import com.jiuqi.np.grid2.LogUtil;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import com.jiuqi.np.grid2.json.Grid2DataConst;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GridCellData
implements Serializable {
    private static final long serialVersionUID = 4304962685965071208L;
    private int dataType;
    private String formatter;
    private String showText;
    private String editText;
    private int rowIndex;
    private int colIndex;
    private String title;
    private ImageDescriptor treeCellImage;
    private boolean expended;
    private int layer = 0;
    private boolean isTreeEnd = false;
    private int treeStyle;
    private GridCellData parent;
    private Point mergeInfo;
    private boolean overFlowShowText = false;
    private boolean overFlowEditText = false;
    private boolean overFlowDataEx = false;
    private GridCellStyleData cellStyleData;
    private boolean merged;
    private int cellMode = Grid2DataConst.Cell_MODE.Cell_MODE_NORMAL.getIndex();
    private boolean checked = false;
    private boolean checkable = true;
    private boolean expandable = true;
    private int depth = 0;
    private Map<String, Object> cellDatas;
    private JSONObject persistenceData;

    public int getCellMode() {
        return this.cellMode;
    }

    public void setCellMode(int cellMode) {
        this.cellMode = cellMode;
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public boolean isExpandable() {
        return this.expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isShowTextOverFlow() {
        return this.overFlowShowText;
    }

    public boolean isEditTextOverFlow() {
        return this.overFlowEditText;
    }

    public boolean isDataExOverFlow() {
        return this.overFlowDataEx;
    }

    public void saveDataExToStream(Stream2 stream) throws Exception {
        byte[] data;
        String pData = this.persistenceData.toString();
        if (pData == null) {
            pData = "";
        }
        if ((data = pData.getBytes("UTF-8")).length > 127) {
            stream.write((byte)0);
            this.overFlowDataEx = true;
        } else {
            stream.write((byte)data.length);
            stream.write(data, 0, data.length);
            this.overFlowDataEx = false;
        }
    }

    public void saveToStream(Stream2 stream) throws Exception {
        byte[] stringData;
        if (this.showText == null) {
            this.showText = "";
        }
        if ((stringData = this.showText.getBytes("UTF-8")).length > 127) {
            stream.write((byte)0);
            this.overFlowShowText = true;
        } else {
            stream.write((byte)stringData.length);
            stream.write(stringData, 0, stringData.length);
            this.overFlowShowText = false;
        }
        if (this.editText == null) {
            this.editText = "";
        }
        if ((stringData = this.editText.getBytes("UTF-8")).length > 127) {
            stream.write((byte)0);
            this.overFlowEditText = true;
        } else {
            stream.write((byte)stringData.length);
            stream.write(stringData, 0, stringData.length);
            this.overFlowEditText = false;
        }
        stream.writeInt(this.rowIndex);
        stream.writeInt(this.colIndex);
        stream.writeInt(this.cellStyleData.getRowSpan());
        stream.writeInt(this.cellStyleData.getColSpan());
        stream.write((byte)(this.isMerged() ? 1 : 0));
        stream.write((byte)(this.cellStyleData.getBackGroundColor() == -1 ? 1 : 0));
        stream.writeInt(this.cellStyleData.getBackGroundColor());
        stream.write((byte)(this.cellStyleData.getForeGroundColor() == -1 ? 1 : 0));
        stream.writeInt(this.cellStyleData.getForeGroundColor());
        stream.write((byte)this.cellStyleData.getBackGroundStyle());
        stream.write((byte)this.cellStyleData.getFontName().getBytes("UTF-8").length);
        stream.write(this.cellStyleData.getFontName().getBytes("UTF-8"), 0, this.cellStyleData.getFontName().getBytes("UTF-8").length);
        stream.writeInt(this.cellStyleData.getFontSize());
        stream.writeInt(this.cellStyleData.getFontStyle());
        stream.write((byte)(this.cellStyleData.getRightBorderColor() == -1 ? 1 : 0));
        stream.writeInt(this.cellStyleData.getRightBorderColor());
        stream.write((byte)this.cellStyleData.getRightBorderStyle());
        stream.write((byte)(this.cellStyleData.getBottomBorderColor() == -1 ? 1 : 0));
        stream.writeInt(this.cellStyleData.getBottomBorderColor());
        stream.write((byte)this.cellStyleData.getBottomBorderStyle());
        stream.write((byte)(this.cellStyleData.isSelectable() ? 1 : 0));
        stream.write((byte)(this.cellStyleData.isEditable() ? 1 : 0));
        stream.write((byte)(this.cellStyleData.isWrapLine() ? 1 : 0));
        stream.writeInt(this.cellStyleData.getIndent());
        stream.write((byte)this.cellStyleData.getVertAlign());
        stream.write((byte)this.cellStyleData.getHorzAlign());
        stream.write((byte)(this.cellStyleData.isVertText() ? 1 : 0));
        stream.write((byte)(this.cellStyleData.isSilverHead() ? 1 : 0));
        stream.write((byte)(this.cellStyleData.isMultiLine() ? 1 : 0));
        stream.write(this.cellStyleData.getHorizon().byteValue());
        stream.write(this.cellStyleData.getVectical().byteValue());
        if (this.cellStyleData.getGradientColor1() != null && this.cellStyleData.getGradientColor2() != null) {
            stream.write((byte)1);
            stream.writeInt(this.cellStyleData.getGradientColor1());
            stream.writeInt(this.cellStyleData.getGradientColor2());
            stream.write(this.cellStyleData.getGradientDirection().byteValue());
        } else {
            stream.write((byte)0);
        }
        stream.write((byte)this.cellStyleData.getBackgroundImageStyle());
    }

    public void loadDataExFromStream(Stream2 stream) throws StreamException2 {
        byte length = stream.read();
        if (length == -1 || length == 0) {
            return;
        }
        byte[] text = new byte[length];
        for (int i = 0; i < text.length; ++i) {
            text[i] = 32;
        }
        stream.readBuffer(text, 0, length);
        try {
            this.persistenceData = new JSONObject(new String(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException i) {
        }
        catch (JSONException e) {
            LogUtil.log(e);
        }
    }

    public void loadFromStream(Stream2 stream) throws StreamException2 {
        byte length = stream.read();
        byte[] text = new byte[length];
        for (int i = 0; i < text.length; ++i) {
            text[i] = 32;
        }
        stream.readBuffer(text, 0, length);
        try {
            this.setShowText(new String(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            LogUtil.log(e);
        }
        length = stream.read();
        text = new byte[length];
        for (int i = 0; i < text.length; ++i) {
            text[i] = 32;
        }
        stream.readBuffer(text, 0, length);
        try {
            this.setEditText(new String(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            LogUtil.log(e);
        }
        this.setRowIndex(stream.readInt());
        this.setColIndex(stream.readInt());
        this.setRowSpan(stream.readInt());
        this.setColSpan(stream.readInt());
        this.setMerged(this.readBool(stream));
        if (stream.read() == 1) {
            stream.skip(4L);
        } else {
            this.setBackGroundColor(stream.readInt());
        }
        if (stream.read() == 1) {
            stream.skip(4L);
        } else {
            this.setForeGroundColor(stream.readInt());
        }
        this.setBackGroundStyle(stream.read());
        length = stream.read();
        text = new byte[length];
        for (int i = 0; i < text.length; ++i) {
            text[i] = 32;
        }
        stream.readBuffer(text, 0, length);
        try {
            this.setFontName(new String(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            LogUtil.log(e);
        }
        this.setFontSize(stream.readInt());
        this.setFontStyle(stream.readInt());
        if (stream.read() == 1) {
            stream.skip(4L);
        } else {
            this.setRightBorderColor(stream.readInt());
        }
        this.setRightBorderStyle(stream.read());
        if (stream.read() == 1) {
            stream.skip(4L);
        } else {
            this.setBottomBorderColor(stream.readInt());
        }
        this.setBottomBorderStyle(stream.read());
        this.setSelectable(this.readBool(stream));
        this.setEditable(this.readBool(stream));
        this.setWrapLine(this.readBool(stream));
        this.setIndent(stream.readInt());
        this.setVertAlign(stream.read());
        this.setHorzAlign(stream.read());
        this.setVertText(this.readBool(stream));
        this.setSilverHead(this.readBool(stream));
        this.setMultiLine(this.readBool(stream));
        this.setBackImagePosition(Integer.valueOf(stream.read()), Integer.valueOf(stream.read()));
        if (stream.read() == 1) {
            int color1 = stream.readInt();
            int color2 = stream.readInt();
            byte dir = stream.read();
            this.setGradientColor1(color1);
            this.setGradientColor2(color2);
            this.setGradientDirection(Integer.valueOf(dir));
        }
        this.setBackgroundImageStyle(stream.read());
    }

    private boolean readBool(Stream2 stream) throws StreamException2 {
        return stream.read() == 1;
    }

    private void setDefaultCellInfo(int col, int row) {
        this.setRowIndex(row);
        this.setColIndex(col);
        this.setMerged(false);
        this.cellDatas = new HashMap<String, Object>();
        this.persistenceData = new JSONObject();
    }

    public GridCellData(int col, int row) {
        this.setDefaultCellInfo(col, row);
        this.setDefaultCellStyle();
    }

    public GridCellData(GridCellData copyFrom, int col, int row) {
        this.setDefaultCellInfo(col, row);
        this.copyCellStyle(copyFrom);
        this.setRowSpan(1);
        this.setColSpan(1);
    }

    public void copyCellData(GridCellData copyFrom) {
        this.setShowText(copyFrom.getShowText());
        this.setEditText(copyFrom.getEditText());
        this.setFormatter(copyFrom.getFormatter());
        try {
            this.setDataExFromString(copyFrom.getDataExString());
        }
        catch (JSONException jSONException) {
            // empty catch block
        }
        this.copyCellStyle(copyFrom);
    }

    public void copyCellStyle(GridCellData copyFrom) {
        try {
            this.setCellStyleData((GridCellStyleData)copyFrom.getCellStyleData().clone());
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
    }

    public boolean isExpended() {
        return this.expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public boolean isTreeEnd() {
        return this.isTreeEnd;
    }

    public void setTreeEnd(boolean isTreeEnd) {
        this.isTreeEnd = isTreeEnd;
    }

    public GridCellData getParent() {
        return this.parent;
    }

    public void setParent(GridCellData parent) {
        this.parent = parent;
    }

    public void setBackImagePosition(Integer horizon, Integer vectical) {
        this.cellStyleData.setBackImagePosition(horizon, vectical);
    }

    public Integer getGradientColor1() {
        return this.cellStyleData.getGradientColor1();
    }

    public void setGradientColor1(Integer gradientColor1) {
        this.cellStyleData.setGradientColor1(gradientColor1);
    }

    public Integer getGradientColor2() {
        return this.cellStyleData.getGradientColor2();
    }

    public void setGradientColor2(Integer gradientColor2) {
        this.cellStyleData.setGradientColor2(gradientColor2);
    }

    public Integer getGradientDirection() {
        return this.cellStyleData.getGradientDirection();
    }

    public void setGradientDirection(Integer gradientDirection) {
        this.cellStyleData.setGradientDirection(gradientDirection);
    }

    public int getBackgroundImageStyle() {
        return this.cellStyleData.getBackgroundImageStyle();
    }

    public void setBackgroundImageStyle(int style) {
        this.cellStyleData.setBackgroundImageStyle(style);
    }

    public Integer getHorizon() {
        return this.cellStyleData.getHorizon();
    }

    public Integer getVectical() {
        return this.cellStyleData.getVectical();
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Object getCellData(String key) {
        return this.cellDatas.get(key);
    }

    public void setCellData(String key, Object value) {
        this.cellDatas.put(key, value);
    }

    public String getFormatter() {
        return this.formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public String getShowText() {
        return this.showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditText() {
        return this.editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowSpan() {
        return this.cellStyleData.getRowSpan();
    }

    public void setRowSpan(int rowSpan) {
        this.cellStyleData.setRowSpan(rowSpan);
    }

    public int getColSpan() {
        return this.cellStyleData.getColSpan();
    }

    public void setColSpan(int colSpan) {
        this.cellStyleData.setColSpan(colSpan);
    }

    public boolean isMerged() {
        return this.merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public void setMergeInfo(Point point) {
        this.mergeInfo = point;
    }

    public Point getMergeInfo() {
        if (this.mergeInfo != null) {
            return new Point(this.mergeInfo.x, this.mergeInfo.y);
        }
        return null;
    }

    public ImageDescriptor getBackImage() {
        return this.cellStyleData.getBackImage();
    }

    public void setBackImage(ImageDescriptor backImage) {
        this.cellStyleData.setBackImage(backImage);
    }

    public int getBackGroundColor() {
        return this.cellStyleData.getBackGroundColor();
    }

    public void setBackGroundColor(int backGroundColor) {
        this.cellStyleData.setBackGroundColor(backGroundColor);
    }

    public int getForeGroundColor() {
        return this.cellStyleData.getForeGroundColor();
    }

    public void setForeGroundColor(int foreGroundColor) {
        this.cellStyleData.setForeGroundColor(foreGroundColor);
    }

    public int getBackGroundStyle() {
        return this.cellStyleData.getBackGroundStyle();
    }

    public void setBackGroundStyle(int backGroundStyle) {
        this.cellStyleData.setBackGroundStyle(backGroundStyle);
    }

    public String getFontName() {
        return this.cellStyleData.getFontName();
    }

    public void setFontName(String fontName) {
        this.cellStyleData.setFontName(fontName);
    }

    public int getFontSize() {
        return this.cellStyleData.getFontSize();
    }

    public void setFontSize(int fontSize) {
        this.cellStyleData.setFontSize(fontSize);
    }

    public final void setFontHeight(int value) {
        this.setFontSize(value * 72 / 96);
    }

    public final int getFontHeight() {
        return this.getFontSize() * 96 / 72;
    }

    public int getFontStyle() {
        return this.cellStyleData.getFontStyle();
    }

    public void setFontStyle(int fontStyle) {
        this.cellStyleData.setFontStyle(fontStyle);
    }

    public int getRightBorderColor() {
        return this.cellStyleData.getRightBorderColor();
    }

    public void setRightBorderColor(int rightBorderColor) {
        this.cellStyleData.setRightBorderColor(rightBorderColor);
    }

    public int getRightBorderStyle() {
        return this.cellStyleData.getRightBorderStyle();
    }

    public void setRightBorderStyle(int rightBorderStyle) {
        this.cellStyleData.setRightBorderStyle(rightBorderStyle);
    }

    public int getBottomBorderColor() {
        return this.cellStyleData.getBottomBorderColor();
    }

    public void setBottomBorderColor(int bottomBorderColor) {
        this.cellStyleData.setBottomBorderColor(bottomBorderColor);
    }

    public int getBottomBorderStyle() {
        return this.cellStyleData.getBottomBorderStyle();
    }

    public void setBottomBorderStyle(int bottomBorderStyle) {
        this.cellStyleData.setBottomBorderStyle(bottomBorderStyle);
    }

    public int getDiagonalBorderColor() {
        return this.cellStyleData.getDiagonalBorderColor();
    }

    public void setDiagonalBorderColor(int color) {
        this.cellStyleData.setDiagonalBorderColor(color);
    }

    public int getDiagonalBorderStyle() {
        return this.cellStyleData.getDiagonalBorderStyle();
    }

    public void setDiagonalBorderStyle(int style) {
        this.cellStyleData.setDiagonalBorderStyle(style);
    }

    public void setInverseDiagonalBorderStyle(int style) {
        this.cellStyleData.setInverseDiagonalBorderStyle(style);
    }

    public int getInverseDiagonalBorderStyle() {
        return this.cellStyleData.getInverseDiagonalBorderStyle();
    }

    public int getInverseDiagonalBorderColor() {
        return this.cellStyleData.getInverseDiagonalBorderColor();
    }

    public void setInverseDiagonalBorderColor(int color) {
        this.cellStyleData.setInverseDiagonalBorderColor(color);
    }

    public boolean isSelectable() {
        return this.cellStyleData.isSelectable();
    }

    public void setSelectable(boolean selectable) {
        this.cellStyleData.setSelectable(selectable);
    }

    public boolean isEditable() {
        return this.cellStyleData.isEditable();
    }

    public void setEditable(boolean editable) {
        this.cellStyleData.setEditable(editable);
    }

    public boolean isWrapLine() {
        return this.cellStyleData.isWrapLine();
    }

    public void setWrapLine(boolean wrapLine) {
        this.cellStyleData.setWrapLine(wrapLine);
    }

    public int getIndent() {
        return this.cellStyleData.getIndent();
    }

    public void setIndent(int indent) {
        this.cellStyleData.setIndent(indent);
    }

    public int getVertAlign() {
        return this.cellStyleData.getVertAlign();
    }

    public void setVertAlign(int vertAlign) {
        this.cellStyleData.setVertAlign(vertAlign);
    }

    public int getHorzAlign() {
        return this.cellStyleData.getHorzAlign();
    }

    public void setHorzAlign(int horzAlign) {
        this.cellStyleData.setHorzAlign(horzAlign);
    }

    public boolean isVertText() {
        return this.cellStyleData.isVertText();
    }

    public void setVertText(boolean vertText) {
        this.cellStyleData.setVertText(vertText);
    }

    public boolean isSilverHead() {
        return this.cellStyleData.isSilverHead();
    }

    public void setSilverHead(boolean silverHead) {
        this.cellStyleData.setSilverHead(silverHead);
    }

    public boolean isMultiLine() {
        return this.cellStyleData.isMultiLine();
    }

    public void setMultiLine(boolean multiLine) {
        this.cellStyleData.setMultiLine(multiLine);
    }

    public GridCellStyleData getCellStyleData() {
        return this.cellStyleData;
    }

    public void setCellStyleData(GridCellStyleData cellStyleData) {
        this.cellStyleData = cellStyleData;
    }

    private void setDefaultCellStyle() {
        this.setCellStyleData(new GridCellStyleData());
    }

    public void setPersistenceData(String key, String value) {
        try {
            this.persistenceData.put(key, (Object)value);
        }
        catch (JSONException jSONException) {
            // empty catch block
        }
    }

    public String getPersistenceData(String key) {
        try {
            return (String)this.persistenceData.get(key);
        }
        catch (JSONException e) {
            return null;
        }
    }

    public String getDataExString() {
        return this.persistenceData.toString();
    }

    public void setDataExFromString(String json) throws JSONException {
        this.persistenceData = new JSONObject(json);
    }

    public void setTreeStyle(int style) {
        this.treeStyle = style;
    }

    public int getTreeStyle() {
        return this.treeStyle;
    }

    public void setChecked(boolean value) {
        this.checked = value;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public ImageDescriptor getTreeImage() {
        return this.treeCellImage;
    }

    public void setTreeImage(ImageDescriptor image) {
        this.treeCellImage = image;
    }

    public boolean isFitFontSize() {
        return this.cellStyleData.isFitFontSize();
    }

    public void setFitFontSize(boolean fitFontSize) {
        this.cellStyleData.setFitFontSize(fitFontSize);
    }

    public String toString() {
        return "\nrowIndex :" + this.rowIndex + "\ncolIndex :" + this.colIndex + "\nrowSpan :" + this.getRowSpan() + "\ncolSpan :" + this.getColSpan() + "\nshowText :" + this.showText + "\neditText :" + this.editText + "\nmergeInfo :" + this.mergeInfo + "\nparent : {" + this.parent + "}";
    }
}

