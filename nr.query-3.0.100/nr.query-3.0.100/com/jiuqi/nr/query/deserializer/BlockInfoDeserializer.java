/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.BlockInfo;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.QueryFieldPosition;
import com.jiuqi.nr.query.block.QueryGridPage;
import com.jiuqi.nr.query.chart.ChartType;
import com.jiuqi.nr.query.common.BackGroundType;
import com.jiuqi.nr.query.common.BlockTitleAlign;
import com.jiuqi.nr.query.common.BlockTitleRule;
import com.jiuqi.nr.query.common.ImageLayout;
import com.jiuqi.nr.query.common.QueryBlockType;
import java.io.IOException;

public class BlockInfoDeserializer
extends JsonDeserializer<BlockInfo> {
    public BlockInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        BlockInfo blockInfo = new BlockInfo();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("backcolor");
        String backcolor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("backgroundimage");
        String backgroundimage = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("blocktitlealign");
        String blocktitlealign = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("blocktitlerule");
        String blocktitlerule = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fontcolor");
        String fontcolor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fontname");
        String fontName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fontsize");
        String fontSize = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("headbackcolor");
        String headbackcolor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("imagelayout");
        String imagelayout = target != null && !target.isNull() ? target.asText() : "CENTER";
        target = jNode.findValue("opacity");
        Double opacity = target != null && !target.isNull() ? Double.valueOf(target.asText()) : null;
        target = jNode.findValue("order");
        String order = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("queryDimensions");
        String queryDimensions = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("blockExtension");
        String blockExtension = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("schemeId");
        String formSchemeKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("taskId");
        String formTaskKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("posx");
        Integer posx = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.findValue("posy");
        Integer posy = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.findValue("width");
        Integer width = target != null && !target.isNull() ? target.asInt() : 300;
        target = jNode.findValue("height");
        Integer height = target != null && !target.isNull() ? target.asInt() : 300;
        target = jNode.findValue("blocktype");
        String type = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("chartType");
        String chartType = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("showsubtotal");
        boolean subtotal = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("showDetail");
        boolean showDetail = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("showTotal");
        boolean total = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("shownullrow");
        boolean nullrow = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("showzerorow");
        boolean zerorow = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("ispaging");
        boolean isPaging = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("pagesize");
        int pageSize = target != null && !target.isNull() ? target.asInt() : QueryGridPage.CONST_DEFAULTPAGESIZE;
        target = jNode.findValue("linkedid");
        String linkedBlock = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("showSpacerColour");
        boolean showSpacerColour = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("dimensionshowtype");
        int dimshowtype = target != null && !target.isNull() ? target.asInt() : -1;
        target = jNode.findValue("rowheight");
        int rowheight = target != null && !target.isNull() ? target.asInt() : 33;
        target = jNode.findValue("showrownum");
        boolean showrownum = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("isTranspose");
        boolean isTranspose = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("queryDirection");
        String queryDirection = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldPosition");
        int fieldPos = target != null && !target.isNull() ? target.asInt() : 1;
        target = jNode.findValue("isDataSet");
        boolean isdataset = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("backgroundtype");
        String backGroundType = target != null && !target.isNull() ? target.asText() : "Color";
        target = jNode.findValue("bordershowtype");
        String borderType = target != null && !target.isNull() ? target.asText() : "autoHide";
        target = jNode.findValue("bordercolor");
        String borderColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("cornersize");
        Integer borderCorner = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.findValue("dimValue");
        String dimValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("depth");
        int depth = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.findValue("nodeDimSet");
        String nodeDimSet = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("treeLoad");
        boolean isTreeLoad = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("customFieldName");
        String customFieldName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("exportGridTitle");
        boolean exportGridTitle = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("exportGridCondition");
        boolean exportGridCondition = target != null && !target.isNull() ? target.asBoolean() : true;
        target = jNode.findValue("wordLabels");
        String wordLabels = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("columnWidths");
        String columnWidths = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("showIndex");
        boolean showIndex = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("totalCount");
        Integer totalCount = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.findValue("customEntryInsertRows");
        String insertRows = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("insertRowBizKey");
        String insertRowsBiz = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("mergerange");
        String mergeRange = target != null && !target.isNull() ? target.asText() : null;
        blockInfo.setCustomFieldMergeRange(mergeRange);
        blockInfo.setTotalCount(totalCount);
        blockInfo.setBlockType(QueryBlockType.getType(type));
        blockInfo.setIsDateSet(isdataset);
        blockInfo.setBorderColor(borderColor);
        blockInfo.setCornerSize(borderCorner);
        blockInfo.setBorderShowType(BlockTitleRule.valueOf(borderType));
        blockInfo.setBackGroudType(BackGroundType.valueOf(backGroundType));
        blockInfo.setFieldPosition(fieldPos == 1 ? QueryFieldPosition.UP : QueryFieldPosition.DOWN);
        blockInfo.setQueryDirection(queryDirection);
        blockInfo.setTranspose(isTranspose);
        blockInfo.setShowRowNum(showrownum);
        blockInfo.setRowHeight(rowheight);
        blockInfo.setDimLevelShowType(dimshowtype);
        blockInfo.setPageSize(pageSize);
        blockInfo.setIsPaging(isPaging);
        blockInfo.setShowZeroRow(zerorow);
        blockInfo.setShowSubTotal(subtotal);
        blockInfo.setShowSum(total);
        blockInfo.setShowNullRow(nullrow);
        blockInfo.setBackColor(backcolor);
        blockInfo.setBackgroundImage(backgroundimage);
        blockInfo.setTitleAlign(BlockTitleAlign.valueOf(blocktitlealign));
        blockInfo.setTitleRule(BlockTitleRule.valueOf(blocktitlerule));
        blockInfo.setCode(code);
        blockInfo.setFontColor(fontcolor);
        blockInfo.setFontName(fontName);
        blockInfo.setFontSize(fontSize);
        blockInfo.setFormSchemeKey(formSchemeKey);
        blockInfo.setFormTaskKey(formTaskKey);
        blockInfo.setHeadBackColor(headbackcolor);
        blockInfo.setBackgroundImageLayout(ImageLayout.valueOf(imagelayout));
        blockInfo.setOpacity(opacity);
        blockInfo.setOrder(order);
        if (queryDimensions != null) {
            blockInfo.SetQueryDimensionsStr(queryDimensions);
        }
        blockInfo.setBlockExtension(blockExtension);
        blockInfo.setPosX(posx);
        blockInfo.setPosY(posy);
        blockInfo.setWidth(width);
        blockInfo.setHeight(height);
        if (chartType != null && !"".equals(chartType)) {
            blockInfo.setChartType(ChartType.valueOf(chartType));
        }
        blockInfo.setLinkedBlock(linkedBlock);
        blockInfo.setShowSpacerColour(showSpacerColour);
        blockInfo.setShowDetail(showDetail);
        blockInfo.setDimValue(dimValue);
        blockInfo.setDepth(depth);
        blockInfo.setNodeDimSet(nodeDimSet);
        blockInfo.setTreeLoad(isTreeLoad);
        blockInfo.setCustomFieldName(customFieldName);
        blockInfo.setExportGridTitle(exportGridTitle);
        blockInfo.setExportGridCondition(exportGridCondition);
        blockInfo.setWordLabelsStr(wordLabels);
        blockInfo.setColumnWidth(ColumnWidth.getColumnWidths(columnWidths));
        blockInfo.setShowIndex(showIndex);
        blockInfo.setCustomEntryInsertRows(BlockInfo.getInsertRows(insertRows));
        blockInfo.setInsertRowBizKey(BlockInfo.getInsertRowBizKey(insertRowsBiz));
        return blockInfo;
    }
}

