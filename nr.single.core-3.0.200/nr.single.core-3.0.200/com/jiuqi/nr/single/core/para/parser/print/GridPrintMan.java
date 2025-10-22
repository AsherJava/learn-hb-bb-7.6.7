/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.print.GText;
import com.jiuqi.nr.single.core.para.parser.print.GraphGroup;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintPage;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextsDef;
import com.jiuqi.nr.single.core.para.parser.print.IGraphItem;
import com.jiuqi.nr.single.core.para.parser.print.PrintPageData;
import com.jiuqi.nr.single.core.para.parser.print.PrintReadUtil;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridPrintMan {
    private boolean privateRowFirst;
    private PrintPageData pageData = new PrintPageData();
    private ParaInfo parainfo;
    private List<Integer> headerCols = new ArrayList<Integer>();
    private List<Integer> headerRows = new ArrayList<Integer>();
    private int horzPages = 0;
    private int vertPages = 0;
    private Grid2Data gridData;
    private GridPrintPage[][] pages;
    private GraphItem[][] graphs;
    private List<Integer> colAxis = null;
    private List<Integer> rowAxis = null;
    private List<Integer> colWidths = null;
    private List<Integer> rowHeights = null;
    private GridPrintTextsDef textsDef = new GridPrintTextsDef();
    private int privateGridLineGap;
    private int privateColCount;
    private int privateRowCount;
    private String privateTitle;

    public final boolean getRowFirst() {
        return this.privateRowFirst;
    }

    public final void setRowFirst(boolean value) {
        this.privateRowFirst = value;
    }

    public GridPrintPage[][] getPages() {
        return this.pages;
    }

    public GraphItem[][] getGraphs() {
        return this.graphs;
    }

    public final PrintPageData getPageData() {
        return this.pageData;
    }

    public final int getGridLineGap() {
        return this.privateGridLineGap;
    }

    public final void setGridLineGap(int value) {
        this.privateGridLineGap = value;
    }

    public final int getColCount() {
        return this.privateColCount;
    }

    public final void setColCount(int value) {
        this.privateColCount = value;
    }

    public final int getRowCount() {
        return this.privateRowCount;
    }

    public final void setRowCount(int value) {
        this.privateRowCount = value;
    }

    public final String getTitle() {
        return this.privateTitle;
    }

    public final void setTitle(String value) {
        this.privateTitle = value;
    }

    public final int getHorzPages() {
        return this.horzPages;
    }

    public final int getVertPages() {
        return this.vertPages;
    }

    public final List<Integer> getHeaderCols() {
        return this.headerCols;
    }

    public final List<Integer> getHeaderRows() {
        return this.headerRows;
    }

    public final List<Integer> getColAxis() {
        if (this.colAxis == null) {
            this.GetAxis();
        }
        return this.colAxis;
    }

    public final List<Integer> getRowAxis() {
        if (this.rowAxis == null) {
            this.GetAxis();
        }
        return this.rowAxis;
    }

    public final List<Integer> getColWidths() {
        if (this.colWidths == null) {
            this.GetAxis();
        }
        return this.colWidths;
    }

    public final List<Integer> getRowHeights() {
        if (this.rowHeights == null) {
            this.GetAxis();
        }
        return this.rowHeights;
    }

    public final GridPrintTextsDef getTextsDef() {
        return this.textsDef;
    }

    public final Grid2Data getGridData() {
        return this.gridData;
    }

    public final void IniPrint(ParaInfo paraInfo) {
        this.parainfo = paraInfo;
    }

    public GridPrintMan(Grid2Data gridData) {
        this.gridData = gridData;
    }

    public final void LoadFromJQT(Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        if (jqtFile.getPageDataBlock() != 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getPageDataBlock());
            this.pageData.loadFromStream(stream);
            this.setGridLineGap(ReadUtil.readIntValue(stream));
            this.setColCount(ReadUtil.readIntValue(stream));
            this.setRowCount(ReadUtil.readIntValue(stream));
            this.setTitle(ReadUtil.readStreams(stream));
        }
        if (jqtFile.getSplitInfoBlock() != 0) {
            int i;
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getSplitInfoBlock());
            this.setRowFirst(ReadUtil.readShortValue(stream) == 1);
            int total = ReadUtil.readIntValue(stream);
            for (i = 0; i < total; ++i) {
                this.getHeaderCols().add(Integer.valueOf(ReadUtil.readShortValue(stream)));
            }
            total = ReadUtil.readIntValue(stream);
            for (i = 0; i < total; ++i) {
                this.getHeaderRows().add(ReadUtil.readIntValue(stream));
            }
        }
        if (jqtFile.getPrintPagesBlock() != 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getPrintPagesBlock());
            this.horzPages = ReadUtil.readIntValue(stream);
            this.vertPages = ReadUtil.readIntValue(stream);
            this.SetPageColRow(this.horzPages, this.vertPages);
            for (int i = 0; i < this.horzPages; ++i) {
                for (int j = 0; j < this.vertPages; ++j) {
                    short type = ReadUtil.readShortValue(stream);
                    GridPrintPage graph = this.pages[i][j];
                    graph.loadFromStream(stream);
                }
            }
        }
        if (jqtFile.getGraphItemsBlock() != 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getGraphItemsBlock());
            int colCount = ReadUtil.readIntValue(stream);
            int rowCount = ReadUtil.readIntValue(stream);
            this.graphs = new GraphItem[colCount][rowCount];
            for (int i = 0; i < colCount; ++i) {
                for (int j = 0; j < rowCount; ++j) {
                    this.graphs[i][j] = PrintReadUtil.loadGraphItem2(stream);
                }
            }
        }
        if (jqtFile.getDftTextsBlock() != 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getDftTextsBlock());
            this.textsDef = new GridPrintTextsDef();
            this.textsDef.ini(stream);
        }
    }

    public final void LoadDefaultFMDMFromJQT(Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        Stream is = stream;
        is.seek(0L, 0);
        if (jqtFile.getPageDataBlock() != 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getPageDataBlock());
            this.pageData.loadFromStream(stream);
            this.setGridLineGap(ReadUtil.readIntValue(stream));
            this.setColCount(ReadUtil.readIntValue(stream));
            this.setRowCount(ReadUtil.readIntValue(stream));
            this.setTitle(ReadUtil.readStreams(stream));
        }
        if (jqtFile.getGraphItemsBlock() != 0) {
            is.seek(0L, 0);
            ReadUtil.skipStream(is, jqtFile.getGraphItemsBlock());
            if (jqtFile.gethGraphItemsBlock() != 0L) {
                is = ReadUtil.decompressData(is);
            }
            this.graphs = new GraphItem[1][1];
            GraphItem graphItem = PrintReadUtil.loadGraphItem2(is);
            if (!(graphItem instanceof GraphGroup)) {
                throw new IOException("\u6587\u4ef6\u683c\u5f0f\u9519\u8bef");
            }
            List<GraphItem> items = ((GraphGroup)graphItem).getItems();
            for (IGraphItem iGraphItem : items) {
                if (!(iGraphItem instanceof GText)) continue;
                String string = ((GText)iGraphItem).getText();
            }
            this.graphs[0][0] = graphItem;
        }
    }

    public final void LoadFromStream(Stream mask0) throws IOException, StreamException {
        int i;
        int HorzPages = 0;
        int VertPages = 0;
        ArrayList<Integer> HeaderCols = new ArrayList<Integer>();
        ArrayList<Integer> HeaderRows = new ArrayList<Integer>();
        this.pageData.getPaperData().setPaperType((short)ReadUtil.readSmallIntValue(mask0));
        this.pageData.getPaperData().setOrientation((short)ReadUtil.readSmallIntValue(mask0));
        this.pageData.getPaperData().setPaperWidth(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperData().setPaperHeight(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setLeftMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setTopMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setRightMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setBottomMargin(ReadUtil.readShortValue(mask0));
        this.pageData.setGlobalProp((short)ReadUtil.readSmallIntValue(mask0));
        this.pageData.setVertCenter(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setHorzCenter(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setFitInPage(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setiReserved1(ReadUtil.readByteValue(mask0));
        this.pageData.setiReserved2((short)ReadUtil.readSmallIntValue(mask0));
        this.pageData.setwReserved1(ReadUtil.readShortValue(mask0));
        this.pageData.setwReserved2(ReadUtil.readShortValue(mask0));
        this.setGridLineGap(ReadUtil.readIntValue(mask0));
        this.setColCount(ReadUtil.readIntValue(mask0));
        this.setRowCount(ReadUtil.readIntValue(mask0));
        this.setTitle(ReadUtil.readStreams(mask0));
        this.setRowFirst(ReadUtil.readSmallIntValue(mask0) == 1);
        int total = ReadUtil.readIntValue(mask0);
        for (i = 0; i < total; ++i) {
            HeaderCols.add(ReadUtil.readSmallIntValue(mask0));
        }
        total = ReadUtil.readIntValue(mask0);
        for (i = 0; i < total; ++i) {
            HeaderRows.add(ReadUtil.readSmallIntValue(mask0));
        }
        HorzPages = ReadUtil.readIntValue(mask0);
        VertPages = ReadUtil.readIntValue(mask0);
        this.SetPageColRow(HorzPages, VertPages);
        for (i = 0; i < HorzPages; ++i) {
            for (int j = 0; j < VertPages; ++j) {
                int type = ReadUtil.readSmallIntValue(mask0);
                GridPrintPage graph = this.pages[i][j];
                graph.loadFromStream(mask0);
            }
        }
        int colCount = ReadUtil.readIntValue(mask0);
        int rowCount = ReadUtil.readIntValue(mask0);
        this.graphs = new GraphItem[colCount][rowCount];
        for (int i2 = 0; i2 < colCount; ++i2) {
            for (int j = 0; j < rowCount; ++j) {
                this.graphs[i2][j] = PrintReadUtil.loadGraphItem2(mask0);
            }
        }
        this.textsDef = new GridPrintTextsDef();
        this.textsDef.ini(mask0);
    }

    public final void LoadNotDefaultFMDMFromStream(Stream mask0) throws IOException, StreamException {
        this.pageData.getPaperData().setPaperType(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperData().setOrientation(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperData().setPaperWidth(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperData().setPaperHeight(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setLeftMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setTopMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setRightMargin(ReadUtil.readShortValue(mask0));
        this.pageData.getPaperMargin().setBottomMargin(ReadUtil.readShortValue(mask0));
        this.pageData.setGlobalProp(ReadUtil.readShortValue(mask0));
        this.pageData.setVertCenter(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setHorzCenter(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setFitInPage(ReadUtil.readByteValue(mask0) == 1);
        this.pageData.setiReserved1(ReadUtil.readByteValue(mask0));
        this.pageData.setiReserved2(ReadUtil.readShortValue(mask0));
        this.pageData.setwReserved1(ReadUtil.readShortValue(mask0));
        this.pageData.setwReserved2(ReadUtil.readShortValue(mask0));
        ReadUtil.skipStream(mask0, 12);
        ReadUtil.skipStream(mask0);
        GraphItem graphItem = PrintReadUtil.loadGraphItem2(mask0);
        this.graphs = new GraphItem[1][1];
        if (!(graphItem instanceof GraphGroup)) {
            throw new IOException("\u6587\u4ef6\u683c\u5f0f\u9519\u8bef");
        }
        List<GraphItem> items = ((GraphGroup)graphItem).getItems();
        for (IGraphItem iGraphItem : items) {
            if (!(iGraphItem instanceof GText)) continue;
            String string = ((GText)iGraphItem).getText();
        }
        this.graphs[0][0] = graphItem;
    }

    private void SetPageColRow(int horzPages, int vertPages) {
        this.horzPages = horzPages;
        this.vertPages = vertPages;
        this.pages = new GridPrintPage[horzPages][vertPages];
        for (int i = 0; i < horzPages; ++i) {
            for (int j = 0; j < vertPages; ++j) {
                GridPrintPage gridPrintPage = new GridPrintPage();
                gridPrintPage.setGridData(this.gridData);
                this.pages[i][j] = gridPrintPage;
            }
        }
    }

    private void SetGraphGroupColRow(int x, int y) {
        this.graphs = new GraphGroup[x][y];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                GraphGroup graphGroup = new GraphGroup();
                this.graphs[i][j] = graphGroup;
            }
        }
    }

    private void GetAxis() {
        for (int i = 0; i < this.horzPages; ++i) {
            for (int j = 0; j < this.vertPages; ++j) {
                int t;
                GridPrintPage gridPrintPage = this.pages[i][j];
                if (null == this.colAxis) {
                    this.colAxis = new ArrayList<Integer>();
                }
                if (null == this.colWidths) {
                    this.colWidths = new ArrayList<Integer>();
                }
                for (t = 0; t < gridPrintPage.getPrtCols().size(); ++t) {
                    if (this.colAxis.contains(gridPrintPage.getPrtCols().get(t))) continue;
                    this.colAxis.add(gridPrintPage.getPrtCols().get(t));
                    this.colWidths.add(gridPrintPage.getPrtWidths().get(t));
                }
                if (null == this.rowAxis) {
                    this.rowAxis = new ArrayList<Integer>();
                }
                if (null == this.rowHeights) {
                    this.rowHeights = new ArrayList<Integer>();
                }
                for (t = 0; t < gridPrintPage.getPrtRows().size(); ++t) {
                    if (this.rowAxis.contains(gridPrintPage.getPrtRows().get(t))) continue;
                    this.rowAxis.add(gridPrintPage.getPrtRows().get(t));
                    this.rowHeights.add(gridPrintPage.getPrtHeights().get(t));
                }
            }
        }
    }
}

