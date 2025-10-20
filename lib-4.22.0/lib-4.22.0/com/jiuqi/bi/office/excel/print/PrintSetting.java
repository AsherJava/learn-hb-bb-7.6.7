/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.HeaderFooterSetting;
import com.jiuqi.bi.office.excel.print.Margin;
import com.jiuqi.bi.office.excel.print.PageStart;
import com.jiuqi.bi.office.excel.print.PaperSize;
import com.jiuqi.bi.office.excel.print.Zoom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private boolean noColor = false;
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

    public JSONObject toJson() {
        JSONObject json_printSetting = new JSONObject();
        json_printSetting.put("landscape", this.landscape);
        json_printSetting.put("zoom", (Object)this.zoom.toJson());
        json_printSetting.put("paperSize", this.paperSize.value());
        json_printSetting.put("pageStart", (Object)this.pageStart.toJson());
        json_printSetting.put("margin", (Object)this.margin.toJson());
        json_printSetting.put("horzCenter", this.horzCenter);
        json_printSetting.put("vertCenter", this.vertCenter);
        json_printSetting.put("headerFooterSetting", (Object)this.headerFooterSetting.toJson());
        json_printSetting.put("leftToRight", this.leftToRight);
        json_printSetting.put("hideCols", this.hideCols);
        json_printSetting.put("hideRows", this.hideRows);
        json_printSetting.put("breakCols", this.breakCols);
        json_printSetting.put("breakRows", this.breakRows);
        json_printSetting.put("printGridlines", this.printGridlines);
        json_printSetting.put("noColor", this.noColor);
        json_printSetting.put("printRowAndColumnHeadings", this.printRowAndColumnHeadings);
        json_printSetting.put("repeatingColStart", this.repeatingColStart);
        json_printSetting.put("repeatingColEnd", this.repeatingColEnd);
        json_printSetting.put("repeatingRowStart", this.repeatingRowStart);
        json_printSetting.put("repeatingRowEnd", this.repeatingRowEnd);
        return json_printSetting;
    }

    public void fromJson(JSONObject json_printSetting) {
        this.landscape = json_printSetting.optBoolean("landscape");
        this.zoom = new Zoom();
        this.zoom.fromJson(json_printSetting.optJSONObject("zoom"));
        this.paperSize = PaperSize.valueOf(json_printSetting.optInt("paperSize", PaperSize.A4_PAPER.value()));
        this.pageStart = new PageStart();
        this.pageStart.fromJson(json_printSetting.optJSONObject("pageStart"));
        this.margin = new Margin();
        this.margin.FromJson(json_printSetting.optJSONObject("margin"));
        this.horzCenter = json_printSetting.optBoolean("horzCenter");
        this.vertCenter = json_printSetting.optBoolean("vertCenter");
        this.headerFooterSetting = new HeaderFooterSetting();
        this.headerFooterSetting.fromJson(json_printSetting.optJSONObject("headerFooterSetting"));
        this.leftToRight = json_printSetting.optBoolean("leftToRight");
        this.hideCols = new ArrayList<Integer>();
        JSONArray json_hideCols = json_printSetting.optJSONArray("hideCols");
        for (int i = 0; i < json_hideCols.length(); ++i) {
            this.hideCols.add(json_hideCols.optInt(i));
        }
        this.hideRows = new ArrayList<Integer>();
        JSONArray json_hideRows = json_printSetting.optJSONArray("hideRows");
        for (int i = 0; i < json_hideRows.length(); ++i) {
            this.hideRows.add(json_hideRows.optInt(i));
        }
        this.breakCols = new ArrayList<Integer>();
        JSONArray json_breakCols = json_printSetting.optJSONArray("breakCols");
        for (int i = 0; i < json_breakCols.length(); ++i) {
            this.breakCols.add(json_breakCols.optInt(i));
        }
        this.breakRows = new ArrayList<Integer>();
        JSONArray json_breakRows = json_printSetting.optJSONArray("breakRows");
        for (int i = 0; i < json_breakRows.length(); ++i) {
            this.breakRows.add(json_breakRows.optInt(i));
        }
        this.printGridlines = json_printSetting.optBoolean("printGridlines");
        this.noColor = json_printSetting.optBoolean("noColor");
        this.printRowAndColumnHeadings = json_printSetting.optBoolean("printRowAndColumnHeadings");
        this.repeatingColStart = json_printSetting.optInt("repeatingColStart");
        this.repeatingColEnd = json_printSetting.optInt("repeatingColEnd");
        this.repeatingRowStart = json_printSetting.optInt("repeatingRowStart");
        this.repeatingRowEnd = json_printSetting.optInt("repeatingRowEnd");
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

