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
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApiModel(value="DataFillDataSaveInfo", description="\u6570\u636e\u4fdd\u5b58\u8bf7\u6c42")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillDataSaveInfo
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u8fd0\u884c\u671f\u73af\u5883", name="context", required=true)
    private DataFillContext context;
    @ApiModelProperty(value="\u65b0\u589e\u884c", name="adds", required=false)
    private List<DataFillDataSaveRow> adds;
    @ApiModelProperty(value="\u4fee\u6539\u884c", name="modifys", required=false)
    private List<DataFillDataSaveRow> modifys;
    @ApiModelProperty(value="\u5220\u9664\u884c", name="deletes", required=false)
    private List<DataFillDataDeleteRow> deletes;

    public DataFillContext getContext() {
        return this.context;
    }

    public void setContext(DataFillContext context) {
        this.context = context;
    }

    public List<DataFillDataSaveRow> getAdds() {
        if (null == this.adds) {
            this.adds = new ArrayList<DataFillDataSaveRow>();
        }
        return this.adds;
    }

    public void setAdds(List<DataFillDataSaveRow> adds) {
        this.adds = adds;
    }

    public List<DataFillDataSaveRow> getModifys() {
        if (null == this.modifys) {
            this.modifys = new ArrayList<DataFillDataSaveRow>();
        }
        return this.modifys;
    }

    public void setModifys(List<DataFillDataSaveRow> modifys) {
        this.modifys = modifys;
    }

    public List<DataFillDataDeleteRow> getDeletes() {
        if (null == this.deletes) {
            this.deletes = new ArrayList<DataFillDataDeleteRow>();
        }
        return this.deletes;
    }

    public void setDeletes(List<DataFillDataDeleteRow> deletes) {
        this.deletes = deletes;
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

