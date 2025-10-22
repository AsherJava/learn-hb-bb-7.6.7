/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="\u6279\u91cf\u5220\u9664\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f", description="\u6279\u91cf\u5220\u9664\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f")
public class BatchDeleteFileInfo {
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848key", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formscheme", required=true)
    private String formscheme;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCollection", required=true)
    private DimensionCollection dimensionCollection;
    @ApiModelProperty(value="\u591a\u9009\u8868\u5355key", name="formKeys")
    private List<String> formKeys = new ArrayList<String>();

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

