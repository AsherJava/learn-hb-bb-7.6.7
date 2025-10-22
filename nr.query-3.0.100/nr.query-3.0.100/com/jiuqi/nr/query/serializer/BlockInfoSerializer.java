/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.query.block.BlockInfo;
import java.io.IOException;
import java.util.ArrayList;

public class BlockInfoSerializer
extends JsonSerializer<BlockInfo> {
    public void serialize(BlockInfo define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("backcolor", (Object)define.getBackColor());
        gen.writeStringField("backgroundimage", define.getBackgroundImage());
        gen.writeObjectField("blocktitlealign", (Object)define.getTitleAlign().getName());
        gen.writeObjectField("blocktitlerule", (Object)define.getTitleRule().getName());
        gen.writeObjectField("code", (Object)define.getCode());
        gen.writeObjectField("fontcolor", (Object)define.getFontColor());
        gen.writeObjectField("fontname", (Object)define.getFontName());
        gen.writeObjectField("fontsize", (Object)define.getFontSize());
        gen.writeObjectField("headbackcolor", (Object)define.getHeadBackColor());
        gen.writeObjectField("imagelayout", (Object)define.getBackgroundImageLayout().getName());
        gen.writeObjectField("opacity", (Object)define.getOpacity());
        gen.writeObjectField("order", (Object)define.getOrder());
        gen.writeObjectField("queryDimensions", define.getQueryDimensions());
        gen.writeObjectField("blockExtension", (Object)define.getBlockExtension());
        gen.writeObjectField("schemeId", (Object)define.getFormSchemeKey());
        gen.writeObjectField("taskId", (Object)define.getFormTaskKey());
        gen.writeObjectField("posx", (Object)define.getPosX());
        gen.writeObjectField("posy", (Object)define.getPosY());
        gen.writeObjectField("width", (Object)define.getWidth());
        gen.writeObjectField("height", (Object)define.getHeight());
        gen.writeObjectField("blocktype", (Object)define.getBlockType());
        gen.writeObjectField("isDataSet", (Object)define.getIsDataSet());
        gen.writeObjectField("chartType", (Object)define.getChartType());
        gen.writeObjectField("showsubtotal", (Object)define.isShowSubTotal());
        gen.writeObjectField("showTotal", (Object)define.isShowSum());
        gen.writeObjectField("shownullrow", (Object)define.isShowNullRow());
        gen.writeObjectField("showzerorow", (Object)define.isShowZeroRow());
        gen.writeObjectField("showDetail", (Object)define.isShowDetail());
        gen.writeObjectField("ispaging", (Object)define.isPaging());
        gen.writeObjectField("pagesize", (Object)define.getPageSize());
        gen.writeObjectField("linkedid", (Object)define.getLinkedBlock());
        gen.writeObjectField("showSpacerColour", (Object)define.getShowSpacerColour());
        gen.writeObjectField("dimensionshowtype", (Object)define.getDimLevelShowType());
        gen.writeObjectField("rowheight", (Object)define.getRowHeight());
        gen.writeObjectField("showrownum", (Object)define.getShowRowNum());
        gen.writeObjectField("isTranspose", (Object)define.isTranspose());
        gen.writeStringField("queryDirection", define.getQueryDirection().getValue());
        gen.writeObjectField("fieldPosition", (Object)define.getFieldPositionValue());
        gen.writeObjectField("backgroundtype", (Object)define.getBackGroudType());
        gen.writeObjectField("bordershowtype", (Object)define.getBorderShowType());
        gen.writeObjectField("bordercolor", (Object)define.getBorderColor());
        gen.writeObjectField("cornersize", (Object)define.getCornerSize());
        gen.writeObjectField("totalCount", (Object)define.getTotalCount());
        gen.writeObjectField("customFieldName", (Object)define.getCustomFieldName());
        gen.writeObjectField("exportGridTitle", (Object)define.getExportGridTitle());
        gen.writeObjectField("exportGridCondition", (Object)define.getExportGridCondition());
        gen.writeObjectField("columnWidths", define.getColumnWidth());
        if (null == define.getWordLabels()) {
            define.setWordLabels(new ArrayList<WordLabelDefine>());
        }
        gen.writeObjectField("wordLabels", define.getPrintWords());
        gen.writeObjectField("columnWidths", define.getColumnWidth());
        gen.writeObjectField("showIndex", (Object)define.getShowIndex());
        gen.writeObjectField("customEntryInsertRows", define.getCustomEntryInsertRows());
        gen.writeObjectField("insertRowBizKey", define.getInsertRowBizKey());
        gen.writeObjectField("mergerange", (Object)define.getCustomFieldMergeRange());
        gen.writeEndObject();
    }
}

