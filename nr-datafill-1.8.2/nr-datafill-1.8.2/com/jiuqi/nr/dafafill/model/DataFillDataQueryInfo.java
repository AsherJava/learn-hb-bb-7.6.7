/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.ExportInfo;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApiModel(value="DataFillDataQueryInfo", description="\u67e5\u8be2\u53c2\u6570")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillDataQueryInfo
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u8fd0\u884c\u671f\u73af\u5883", name="context", required=true)
    private DataFillContext context;
    @ApiModelProperty(value="\u5206\u9875\u76f8\u5173\u4fe1\u606f", name="pagerInfo", required=false)
    private PageInfo pagerInfo;
    @ApiModelProperty(value="\u5bfc\u51fa\u76f8\u5173\u4fe1\u606f", name="exportInfo", required=false)
    private ExportInfo exportInfo;

    public DataFillContext getContext() {
        return this.context;
    }

    public void setContext(DataFillContext context) {
        this.context = context;
    }

    public PageInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PageInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public ExportInfo getExportInfo() {
        return this.exportInfo;
    }

    public void setExportInfo(ExportInfo exportInfo) {
        this.exportInfo = exportInfo;
    }

    public String getContextEntityId() {
        List<QueryField> queryFields = this.context.getModel().getQueryFields();
        Optional<QueryField> first = queryFields.stream().filter(e -> FieldType.MASTER.equals((Object)e.getFieldType())).findFirst();
        if (first.isPresent()) {
            return first.get().getId();
        }
        return null;
    }

    public String getContextFilterExpression() {
        return "";
    }
}

