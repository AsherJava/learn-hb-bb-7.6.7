/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.beans.BeanUtils;

@ApiModel(value="DataFillEntityData", description="\u5355\u5143\u683c\u53ef\u9009\u503c")
public class DataFillEntityData
extends DataFillEntityDataBase
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u663e\u793a\u6807\u9898", name="rowCaption")
    private String rowCaption;

    public DataFillEntityData() {
    }

    public DataFillEntityData(DataFillEntityDataBase dataFillEntityDataBase) {
        BeanUtils.copyProperties(dataFillEntityDataBase, this);
    }

    public String getRowCaption() {
        return this.rowCaption;
    }

    public void setRowCaption(String rowCaption) {
        this.rowCaption = rowCaption;
    }
}

