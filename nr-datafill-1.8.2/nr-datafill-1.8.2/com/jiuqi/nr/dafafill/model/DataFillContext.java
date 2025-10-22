/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.service.IDataFillModelHelp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="DataFillContext", description="\u8fd0\u884c\u73af\u5883\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5b9a\u4e49id", name="definitionId", required=false)
    private String definitionId;
    @ApiModelProperty(value="\u6a21\u578b", name="model", required=false)
    public DataFillModel model;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionValues", required=true)
    private List<DFDimensionValue> dimensionValues;
    @JsonIgnore
    private Map<String, Object> caches;

    public List<DFDimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(List<DFDimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public String getDefinitionId() {
        return this.definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public DataFillModel getModel() {
        if (null == this.model) {
            IDataFillModelHelp bean = (IDataFillModelHelp)SpringBeanUtils.getBean(IDataFillModelHelp.class);
            this.model = bean.completion(this);
        }
        return this.model;
    }

    public void setModel(DataFillModel model) {
        this.model = model;
    }

    public String toString() {
        return "DataFillContext [definitionId=" + this.definitionId + ", model=" + this.model + ", dimensionValues=" + this.dimensionValues + "]";
    }

    public Map<String, Object> getCaches() {
        if (this.caches == null) {
            this.caches = new HashMap<String, Object>();
        }
        return this.caches;
    }

    public void setCaches(Map<String, Object> caches) {
        this.caches = caches;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}

