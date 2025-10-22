/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillEntityDataResult", description="\u5355\u5143\u683c\u53ef\u9009\u503c\u8fd4\u56de\u5bf9\u8c61")
public class DataFillEntityDataResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u662f\u5426\u6210\u529f", name="success")
    private boolean success;
    @ApiModelProperty(value="\u63d0\u793a\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u5217\u8868", name="items")
    private List<DataFillEntityData> items;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataFillEntityData> getItems() {
        return this.items;
    }

    public void setItems(List<DataFillEntityData> items) {
        this.items = items;
    }
}

