/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooterSetting;
import com.jiuqi.nvwa.cellbook.excel.print.Margin;
import com.jiuqi.nvwa.cellbook.excel.print.PageStart;
import com.jiuqi.nvwa.cellbook.excel.print.PaperSize;
import com.jiuqi.nvwa.cellbook.excel.print.Zoom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class PrintSetting
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 3000573763095122347L;
    private boolean landscape = false;
    private Zoom zoom = Zoom.NONE;
    private PaperSize paperSize = PaperSize.A4_PAPER;
    private PageStart pageStart = new PageStart();
    private Margin margin = new Margin();
    private boolean horzCenter;
    private boolean vertCenter;
    private HeaderFooterSetting headerFooterSetting = new HeaderFooterSetting();
    private boolean leftToRight;
    private List<Integer> hideCols = new ArrayList<Integer>();
    private List<Integer> hideRows = new ArrayList<Integer>();
    private List<Integer> breakCols = new ArrayList<Integer>();
    private List<Integer> breakRows = new ArrayList<Integer>();
    private boolean printGridlines = true;
    private boolean noColor = true;
    private boolean printRowAndColumnHeadings;
    private int repeatingColStart;
    private int repeatingColEnd;
    private int repeatingRowStart;
    private int repeatingRowEnd;

    public boolean isLandscape() {
        return this.landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public Zoom getZoom() {
        return this.zoom;
    }

    public void setZoom(Zoom zoom) {
        this.zoom = zoom;
    }

    public PaperSize getPaperSize() {
        return this.paperSize;
    }

    public void setPaperSize(PaperSize paperSize) {
        this.paperSize = paperSize;
    }

    public PageStart getPageStart() {
        return this.pageStart;
    }

    public Margin getMargin() {
        return this.margin;
    }

    public boolean isHorzCenter() {
        return this.horzCenter;
    }

    public void setHorzCenter(boolean horzCenter) {
        this.horzCenter = horzCenter;
    }

    public boolean isVertCenter() {
        return this.vertCenter;
    }

    public void setVertCenter(boolean vertCenter) {
        this.vertCenter = vertCenter;
    }

    public HeaderFooterSetting getHeaderFooterSetting() {
        return this.headerFooterSetting;
    }

    public boolean isLeftToRight() {
        return this.leftToRight;
    }

    public void setLeftToRight(boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public List<Integer> getHideCols() {
        return this.hideCols;
    }

    public List<Integer> getHideRows() {
        return this.hideRows;
    }

    public List<Integer> getBreakCols() {
        return this.breakCols;
    }

    public List<Integer> getBreakRows() {
        return this.breakRows;
    }

    public boolean isPrintGridlines() {
        return this.printGridlines;
    }

    public void setPrintGridlines(boolean printGridlines) {
        this.printGridlines = printGridlines;
    }

    public boolean isNoColor() {
        return this.noColor;
    }

    public void setNoColor(boolean noColor) {
        this.noColor = noColor;
    }

    public boolean isPrintRowAndColumnHeadings() {
        return this.printRowAndColumnHeadings;
    }

    public void setPrintRowAndColumnHeadings(boolean printRowAndColumnHeadings) {
        this.printRowAndColumnHeadings = printRowAndColumnHeadings;
    }

    public int getRepeatingColStart() {
        return this.repeatingColStart;
    }

    public void setRepeatingColStart(int repeatingColStart) {
        this.repeatingColStart = repeatingColStart;
    }

    public int getRepeatingColEnd() {
        return this.repeatingColEnd;
    }

    public void setRepeatingColEnd(int repeatingColEnd) {
        this.repeatingColEnd = repeatingColEnd;
    }

    public int getRepeatingRowStart() {
        return this.repeatingRowStart;
    }

    public void setRepeatingRowStart(int repeatingRowStart) {
        this.repeatingRowStart = repeatingRowStart;
    }

    public int getRepeatingRowEnd() {
        return this.repeatingRowEnd;
    }

    public void setRepeatingRowEnd(int repeatingRowEnd) {
        this.repeatingRowEnd = repeatingRowEnd;
    }

    public Object clone() {
        try {
            PrintSetting cloned = (PrintSetting)super.clone();
            cloned.zoom = (Zoom)this.zoom.clone();
            cloned.pageStart = (PageStart)this.pageStart.clone();
            cloned.margin = (Margin)this.margin.clone();
            cloned.headerFooterSetting = (HeaderFooterSetting)this.headerFooterSetting.clone();
            cloned.hideCols = new ArrayList<Integer>();
            cloned.hideCols.addAll(this.hideCols);
            cloned.hideRows = new ArrayList<Integer>();
            cloned.hideRows.addAll(this.hideRows);
            cloned.breakCols = new ArrayList<Integer>();
            cloned.breakCols.addAll(this.breakCols);
            cloned.breakRows = new ArrayList<Integer>();
            cloned.breakRows.addAll(this.breakRows);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

