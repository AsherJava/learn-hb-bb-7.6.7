/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.definition.util.LineProp
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.definition.util.LineProp;
import com.jiuqi.nr.jtable.params.base.RegionExtentLine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(value="RegionExtentGridData", description="\u6d6e\u52a8\u884c\u679a\u4e3e\u586b\u5145\u5ef6\u4f38\u8868\u6837")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionExtentGridData {
    private static final Logger logger = LoggerFactory.getLogger(RegionExtentGridData.class);
    @ApiModelProperty(value="\u679a\u4e3e\u586b\u5145\u884c\u6570\u636e", name="extentLines")
    private List<RegionExtentLine> extentLines;
    @ApiModelProperty(value="\u679a\u4e3e\u586b\u5145\u884c\u8868\u6837", name="griddata")
    private String griddata;

    public RegionExtentGridData(ExtentStyle extentStyle) {
        if (extentStyle != null) {
            this.extentLines = new ArrayList<RegionExtentLine>();
            for (LineProp line : extentStyle.getLineProps()) {
                this.extentLines.add(new RegionExtentLine(line));
            }
            Grid2Data grid2Data = extentStyle.getGriddata();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            mapper.registerModule((Module)module);
            try {
                this.griddata = mapper.writeValueAsString((Object)grid2Data);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    public List<RegionExtentLine> getExtentLines() {
        return this.extentLines;
    }

    public void setExtentLines(List<RegionExtentLine> extentLines) {
        this.extentLines = extentLines;
    }

    public String getGriddata() {
        return this.griddata;
    }

    public void setGriddata(String griddata) {
        this.griddata = griddata;
    }
}

