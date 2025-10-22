/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.print.vo.PrintWordVo
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.print.vo.PrintWordVo;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.DimensionShowType;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDirection;
import com.jiuqi.nr.query.block.QueryFieldPosition;
import com.jiuqi.nr.query.chart.ChartType;
import com.jiuqi.nr.query.common.BackGroundType;
import com.jiuqi.nr.query.common.BlockTitleAlign;
import com.jiuqi.nr.query.common.BlockTitleRule;
import com.jiuqi.nr.query.common.ImageLayout;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.deserializer.BlockInfoDeserializer;
import com.jiuqi.nr.query.serializer.BlockInfoSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=BlockInfoSerializer.class)
@JsonDeserialize(using=BlockInfoDeserializer.class)
public class BlockInfo {
    private static final Logger log = LoggerFactory.getLogger(BlockInfo.class);
    public static final String BLOKINFO_BLOCKTYPE = "blocktype";
    public static final String BLOKINFO_ISDATASET = "isDataSet";
    public static final String BLOKINFO_POSX = "posx";
    public static final String BLOKINFO_POSY = "posy";
    public static final String BLOKINFO_WIDTH = "width";
    public static final String BLOKINFO_HEIGHT = "height";
    public static final String BLOCKINFO_BLOCKTITLEALIGN = "blocktitlealign";
    public static final String BLOCKINFO_CODE = "code";
    public static final String BLOCKINFO_ORDER = "order";
    public static final String BLOCKINFO_HEADBACKCOLOR = "headbackcolor";
    public static final String BLOCKINFO_FONTCOLOR = "fontcolor";
    public static final String BLOCKINFO_FONTSIZE = "fontsize";
    public static final String BLOCKINFO_FONTNAME = "fontname";
    public static final String BLOCKINFO_ROWHEIGHT = "rowheight";
    public static final String BLOCKINFO_SHOWROWNUM = "showrownum";
    public static final String BLOCKINFO_BLOCKTITLERULE = "blocktitlerule";
    public static final String BLOKINFO_BACKGROUNDTYPE = "backgroundtype";
    public static final String BLOCKINFO_BACKCOLOR = "backcolor";
    public static final String BLOCKINFO_BACKGROUNDIMAGE = "backgroundimage";
    public static final String BLOCKINFO_IMAGELAYOUT = "imagelayout";
    public static final String BLOCKINFO_OPACITY = "opacity";
    public static final String BLOCKINFO_QUERYDIMENSSIONS = "queryDimensions";
    public static final String BLOCKINFO_BLOCKEXTENSION = "blockExtension";
    public static final String BLOCKINFO_FORMSCHEMEKEY = "schemeId";
    public static final String BLOCKINFO_FORMTASKKEY = "taskId";
    public static final String BLOCKINFO_SHOWTOTAL = "showTotal";
    public static final String BLOCKINFO_SHOWSUBTOTAL = "showsubtotal";
    public static final String BLOCKINFO_SHOWNULLROW = "shownullrow";
    public static final String BLOCKINFO_SHOWZEROROW = "showzerorow";
    public static final String BLOCKINFO_SHOWDETAIL = "showDetail";
    public static final String BLOCKINFO_ISPAGING = "ispaging";
    public static final String BLOCKINFO_PAGESIZE = "pagesize";
    public static final String BLOCKINFO_LINKEDBLOCK = "linkedid";
    public static final String BLOKINFO_CHARTTYPE = "chartType";
    public static final String BLOCKINFO_SHOWSPACERCOLOUR = "showSpacerColour";
    public static final String BLOCKINFO_DIMENSIONSHOWTYPE = "dimensionshowtype";
    public static final String BLOCKINFO_ISTRANSPOSE = "isTranspose";
    public static final String BLOKINFO_QUERYDIRECTION = "queryDirection";
    public static final String BLOKINFO_FIELDPOSITION = "fieldPosition";
    public static final String BLOKINFO_BORDERTYPE = "bordershowtype";
    public static final String BLOKINFO_BORDERCOLOR = "bordercolor";
    public static final String BLOKINFO_BORDERCORNER = "cornersize";
    public static final String BLOKINFO_TOTALCOUNT = "totalCount";
    public static final String BLOKINFO_DIMVALUE = "dimValue";
    public static final String BLOKINFO_DEPTH = "depth";
    public static final String BLOCKINFO_NODEDIMSET = "nodeDimSet";
    public static final String BLOCKINFO_TREELOAD = "treeLoad";
    public static final String BLOCKINFO_CUSTOMFILEDNAME = "customFieldName";
    public static final String BLOCKINFO_WORDLABELS = "wordLabels";
    public static final String BLOCKINFO_EXPORTGRIDTITLE = "exportGridTitle";
    public static final String BLOCKINFO_EXPORTGRIDCONDITION = "exportGridCondition";
    public static final String BLOCKINFO_COLUMNWIDTHS = "columnWidths";
    public static final String BLOCKINFO_SHOWINDEX = "showIndex";
    public static final String BLOCKINFO_INSERTROWS = "customEntryInsertRows";
    public static final String BLOCKINFO_INSERTROWBIZ = "insertRowBizKey";
    public static final String BLOCKINFO_CUSTOMFIELDMERGERANGE = "mergerange";
    private QueryBlockType blockType = QueryBlockType.QBT_GRID;
    private boolean isDataSet;
    private String customFieldName;
    private boolean exportGridTitle = true;
    private boolean exportGridCondition;
    private ChartType chartType;
    private Integer posX = 0;
    private Integer posY = 0;
    private Integer width = 300;
    private Integer height = 300;
    private BlockTitleRule borderShowType = BlockTitleRule.ALWAYS;
    String borderColor;
    Integer cornerSize;
    private BlockTitleAlign titleAlign = BlockTitleAlign.CENTER;
    private String code;
    private String order;
    private String headBackColor;
    private String fontColor;
    private String fontSize = "16";
    private String fontName = "Microsoft Yahei";
    private BlockTitleRule _titleRule = BlockTitleRule.HIDE;
    private String backColor;
    private String backgroundImage;
    private String dimValue;
    private String nodeDimSet;
    private Integer depth;
    private ImageLayout backgroundImageLayout = ImageLayout.CENTER;
    private Double opacity = 1.0;
    private Boolean showSpacerColour = false;
    private Boolean showIndex = false;
    private boolean showSum;
    private boolean showSubtotal = true;
    private Boolean showTotal = true;
    private Boolean showDetail = true;
    private boolean showNullRow = false;
    private boolean showZeroRow = false;
    private boolean isPaging = true;
    private int pageSize = 20;
    private int totalCount;
    BackGroundType backGroundType;
    private List<QueryDimensionDefine> queryDimensions;
    private String blockExtension;
    private String schemeId;
    private String taskId;
    private String linkedBlock;
    private DimensionShowType dimLevelShowType;
    private boolean isTreeLoad = false;
    private int rowHeight = 33;
    private boolean isShowRowNum;
    private boolean isTranspose;
    @Deprecated
    private QueryDirection queryDirection = QueryDirection.COLDIRECTION;
    private QueryFieldPosition fieldPosition;
    private String customFieldMergeRange;
    private List<WordLabelDefine> wordLabels;
    private List<ColumnWidth> columnWidths;
    private Map<String, Integer> customEntryInsertRows;
    private Map<String, List<String>> insertRowBizKey;

    public BlockTitleRule getBorderShowType() {
        return this.borderShowType;
    }

    public void setBorderShowType(BlockTitleRule borderShowType) {
        this.borderShowType = borderShowType;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public Integer getCornerSize() {
        return this.cornerSize;
    }

    public void setCornerSize(Integer cornerSize) {
        this.cornerSize = cornerSize;
    }

    public Boolean getShowIndex() {
        return this.showIndex;
    }

    public void setShowIndex(Boolean showIndex) {
        this.showIndex = showIndex;
    }

    public Boolean getShowSpacerColour() {
        return this.showSpacerColour;
    }

    public void setShowSpacerColour(Boolean showSpacerColour) {
        this.showSpacerColour = showSpacerColour;
    }

    public QueryBlockType getBlockType() {
        return this.blockType;
    }

    public void setBlockType(QueryBlockType blockType) {
        this.blockType = blockType;
    }

    public ChartType getChartType() {
        return this.chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public Integer getPosX() {
        return this.posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return this.posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BlockTitleAlign getTitleAlign() {
        return this.titleAlign;
    }

    public void setTitleAlign(BlockTitleAlign titleAlign) {
        this.titleAlign = titleAlign;
    }

    public void setShowSum(boolean showSum) {
        this.showSum = showSum;
    }

    public boolean isShowSum() {
        return this.showSum;
    }

    public boolean isShowSubTotal() {
        return this.showSubtotal;
    }

    public void setShowSubTotal(boolean showSubTotal) {
        this.showSubtotal = showSubTotal;
    }

    public Boolean isShowTotal() {
        return this.showTotal;
    }

    public void setShowTotal(Boolean showTotal) {
        this.showTotal = showTotal;
    }

    public Boolean isShowDetail() {
        return this.showDetail;
    }

    public void setShowDetail(Boolean showDetail) {
        this.showDetail = showDetail;
    }

    public boolean isShowNullRow() {
        return this.showNullRow;
    }

    public void setShowNullRow(boolean showNullRow) {
        this.showNullRow = showNullRow;
    }

    public boolean isShowZeroRow() {
        return this.showZeroRow;
    }

    public void setShowZeroRow(boolean showZeroRow) {
        this.showZeroRow = showZeroRow;
    }

    public boolean isPaging() {
        return this.isPaging;
    }

    public void setIsPaging(boolean isPaging) {
        this.isPaging = isPaging;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getHeadBackColor() {
        return this.headBackColor;
    }

    public void setHeadBackColor(String headBackColor) {
        this.headBackColor = headBackColor;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public BlockTitleRule getTitleRule() {
        return this._titleRule;
    }

    public void setTitleRule(BlockTitleRule _titleRule) {
        this._titleRule = _titleRule;
    }

    public BackGroundType getBackGroudType() {
        return this.backGroundType;
    }

    public void setBackGroudType(BackGroundType type) {
        this.backGroundType = type;
    }

    public String getBackColor() {
        return this.backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public ImageLayout getBackgroundImageLayout() {
        return this.backgroundImageLayout;
    }

    public void setBackgroundImageLayout(ImageLayout backgroundImageLayout) {
        this.backgroundImageLayout = backgroundImageLayout;
    }

    public Double getOpacity() {
        return this.opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public List<QueryDimensionDefine> getQueryDimensions() {
        return this.queryDimensions;
    }

    public void setQueryDimensions(List<QueryDimensionDefine> queryDimensions) {
        this.queryDimensions = queryDimensions;
    }

    public String getQueryDimensionsStr() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.queryDimensions);
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }

    public void SetQueryDimensionsStr(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryDimensionDefine.class});
            this.setQueryDimensions((List)mapper.readValue(str, javaType));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getBlockExtension() {
        return this.blockExtension;
    }

    public void setBlockExtension(String blockExtension) {
        this.blockExtension = blockExtension;
    }

    public String getFormSchemeKey() {
        return this.schemeId;
    }

    public void setFormSchemeKey(String sid) {
        this.schemeId = sid;
    }

    public String getFormTaskKey() {
        return this.taskId;
    }

    public void setFormTaskKey(String taskId) {
        this.taskId = taskId;
    }

    public void setLinkedBlock(String id) {
        this.linkedBlock = id;
    }

    public String getLinkedBlock() {
        return this.linkedBlock;
    }

    public void setDimLevelShowType(int dimLevelShowType) {
        this.dimLevelShowType = this.parseToType(dimLevelShowType);
    }

    public int getDimLevelShowType() {
        if (this.dimLevelShowType == DimensionShowType.TREE) {
            return 1;
        }
        if (this.dimLevelShowType == DimensionShowType.LIST) {
            return 2;
        }
        return -1;
    }

    private DimensionShowType parseToType(int val) {
        if (val == 1) {
            return DimensionShowType.TREE;
        }
        if (val == 2) {
            return DimensionShowType.LIST;
        }
        return DimensionShowType.NONE;
    }

    public boolean isTreeLoad() {
        return this.isTreeLoad;
    }

    public void setTreeLoad(boolean treeLoad) {
        this.isTreeLoad = treeLoad;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public void setRowHeight(int height) {
        this.rowHeight = height;
    }

    public boolean getShowRowNum() {
        return this.isShowRowNum;
    }

    public void setShowRowNum(boolean show) {
        this.isShowRowNum = show;
    }

    public boolean isTranspose() {
        return this.isTranspose;
    }

    public void setTranspose(boolean transpose) {
        this.isTranspose = transpose;
    }

    public QueryDirection getQueryDirection() {
        return this.queryDirection;
    }

    public void setQueryDirection(String queryDirection) {
        if (QueryDirection.COLDIRECTION.getValue().equals(queryDirection)) {
            this.queryDirection = QueryDirection.COLDIRECTION;
        }
        if (QueryDirection.ROWDIRECTION.getValue().equals(queryDirection)) {
            this.queryDirection = QueryDirection.ROWDIRECTION;
        }
    }

    public QueryFieldPosition getFieldPosition() {
        return this.fieldPosition;
    }

    public int getFieldPositionValue() {
        if (this.fieldPosition == QueryFieldPosition.UP) {
            return 1;
        }
        if (this.fieldPosition == QueryFieldPosition.DOWN) {
            return 2;
        }
        return -1;
    }

    public void setFieldPosition(QueryFieldPosition pos) {
        this.fieldPosition = pos;
    }

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getNodeDimSet() {
        return this.nodeDimSet;
    }

    public void setNodeDimSet(String nodeDimSet) {
        this.nodeDimSet = nodeDimSet;
    }

    public boolean getIsDataSet() {
        return this.isDataSet;
    }

    public void setIsDateSet(boolean isdataset) {
        this.isDataSet = isdataset;
    }

    public String getCustomFieldName() {
        return this.customFieldName;
    }

    public void setCustomFieldName(String customFieldName) {
        this.customFieldName = customFieldName;
    }

    public void setCustomFieldMergeRange(String range) {
        this.customFieldMergeRange = range;
    }

    public String getCustomFieldMergeRange() {
        return this.customFieldMergeRange;
    }

    public void setWordLabels(List<WordLabelDefine> labels) {
        this.wordLabels = labels;
    }

    public List<WordLabelDefine> getWordLabels() {
        return this.wordLabels;
    }

    public boolean getExportGridTitle() {
        return this.exportGridTitle;
    }

    public List<PrintWordVo> getPrintWords() {
        ArrayList<PrintWordVo> printWords = new ArrayList<PrintWordVo>();
        this.wordLabels.forEach(w -> printWords.add(PrintWordVo.toPrintWordVo((WordLabelDefine)w)));
        return printWords;
    }

    public void setWordLabelsStr(String str) {
        try {
            if (str == null || "".equals(str)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{PrintWordVo.class});
            List printWords = (List)mapper.readValue(str, javaType);
            ArrayList<WordLabelDefine> labels = new ArrayList<WordLabelDefine>();
            printWords.forEach(p -> labels.add(PrintWordVo.toWordLabelDefine((PrintWordVo)p)));
            this.setWordLabels(labels);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setExportGridTitle(boolean exportGridTitle) {
        this.exportGridTitle = exportGridTitle;
    }

    public boolean getExportGridCondition() {
        return this.exportGridCondition;
    }

    public void setExportGridCondition(boolean exportGridCondition) {
        this.exportGridCondition = exportGridCondition;
    }

    public List<ColumnWidth> getColumnWidth() {
        return this.columnWidths;
    }

    public void setColumnWidth(List<ColumnWidth> columnWidth) {
        this.columnWidths = columnWidth;
    }

    public Map<String, Integer> getCustomEntryInsertRows() {
        return this.customEntryInsertRows;
    }

    public void setCustomEntryInsertRows(Map<String, Integer> customEntryInsertRows) {
        this.customEntryInsertRows = customEntryInsertRows;
    }

    public static Map<String, Integer> getInsertRows(String customEntryInsertRows) {
        if (customEntryInsertRows == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map insertRows = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Integer.class});
            insertRows = (Map)objectMapper.readValue(customEntryInsertRows, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return insertRows;
    }

    public Map<String, List<String>> getInsertRowBizKey() {
        return this.insertRowBizKey;
    }

    public void setInsertRowBizKey(Map<String, List<String>> insertRowBizKey) {
        this.insertRowBizKey = insertRowBizKey;
    }

    public static Map<String, List<String>> getInsertRowBizKey(String insertRowBizKeyStr) {
        if (insertRowBizKeyStr == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map insertRowBizKey = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, List.class});
            insertRowBizKey = (Map)objectMapper.readValue(insertRowBizKeyStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return insertRowBizKey;
    }
}

