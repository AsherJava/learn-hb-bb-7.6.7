/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.FloatOrderStructure;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="RegionDataCommitSet", description="\u533a\u57df\u6570\u636e\u4fdd\u5b58")
public class RegionDataCommitSet
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey", required=true)
    private String regionKey;
    @ApiModelProperty(value="\u533a\u57df\u4e0b\u94fe\u63a5key\u5217\u8868(\u4e0e\u6570\u636e\u67e5\u8be2\u4e2d\u4e00\u81f4)", name="cells", required=true)
    private Map<String, List<String>> cells = new HashMap<String, List<String>>();
    @ApiModelProperty(value="\u56fa\u5b9a\u884c\u6570\u636e", name="data")
    private List<List<List<Object>>> data = new ArrayList<List<List<Object>>>();
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u65b0\u589e\u6570\u636e", name="newdata")
    private List<List<List<Object>>> newdata = new ArrayList<List<List<Object>>>();
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u4fee\u6539\u6570\u636e", name="modifydata")
    private List<List<List<Object>>> modifydata = new ArrayList<List<List<Object>>>();
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u5220\u9664\u6570\u636e", name="deletedata")
    private List<String> deletedata = new ArrayList<String>();
    @ApiModelProperty(value="\u5f53\u524d\u533a\u57df\u5206\u9875\u7684floatOrder\u8fd0\u7b97\u53c2\u6570", name="floatOrderStructure")
    private FloatOrderStructure floatOrderStructure;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public Map<String, List<String>> getCells() {
        return this.cells;
    }

    public void setCells(Map<String, List<String>> cells) {
        this.cells = cells;
    }

    public List<List<List<Object>>> getData() {
        return this.data;
    }

    public void setData(List<List<List<Object>>> data) {
        this.data = data;
    }

    public List<List<List<Object>>> getNewdata() {
        return this.newdata;
    }

    public void setNewdata(List<List<List<Object>>> newdata) {
        this.newdata = newdata;
    }

    public List<List<List<Object>>> getModifydata() {
        return this.modifydata;
    }

    public void setModifydata(List<List<List<Object>>> modifydata) {
        this.modifydata = modifydata;
    }

    public List<String> getDeletedata() {
        return this.deletedata;
    }

    public void setDeletedata(List<String> deletedata) {
        this.deletedata = deletedata;
    }

    public Map<String, List<List<Object>>> getModifyDataMap(String bizKeyName, String regionkey) {
        HashMap<String, List<List<Object>>> modifyDataMap = new HashMap<String, List<List<Object>>>(1000);
        for (List<List<Object>> modifyDataItem : this.modifydata) {
            String bizKey = modifyDataItem.get(this.cells.get(regionkey).indexOf(bizKeyName)).get(1).toString();
            modifyDataMap.put(bizKey, modifyDataItem);
        }
        return modifyDataMap;
    }

    public FloatOrderStructure getFloatOrderStructure() {
        return this.floatOrderStructure;
    }

    public void setFloatOrderStructure(FloatOrderStructure floatOrderStructure) {
        this.floatOrderStructure = floatOrderStructure;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

