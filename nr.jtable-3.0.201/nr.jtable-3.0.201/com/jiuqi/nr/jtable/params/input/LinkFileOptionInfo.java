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
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="LinkFileOptionInfo", description="\u5355\u5143\u683c\u9644\u4ef6\u64cd\u4f5c")
public class LinkFileOptionInfo
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey", required=true)
    private String dataLinkKey;
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey", required=true)
    private String groupKey;
    @ApiModelProperty(value="\u9644\u4ef6key", name="fileKey", required=true)
    private String fileKey;
    @ApiModelProperty(value="\u6570\u636e\u884cID", name="rowId")
    private String rowId;
    @ApiModelProperty(value="\u56fe\u7247\u578b\u6307\u6807", name="imgFieldType")
    private boolean imgFieldType;
    @ApiModelProperty(value="\u4e0b\u8f7d\u6a21\u677f", name="downLoadTemplate", required=true)
    private String downLoadTemplate;
    @ApiModelProperty(value="\u662f\u5426\u8986\u76d6", name="isCover")
    private boolean covered;
    @ApiModelProperty(value="\u9644\u4ef6\u5bc6\u7ea7", name="fileSecret")
    private String fileSecret;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getFileSecret() {
        return this.fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public boolean isImgFieldType() {
        return this.imgFieldType;
    }

    public void setImgFieldType(boolean imgFieldType) {
        this.imgFieldType = imgFieldType;
    }

    public String getDownLoadTemplate() {
        return this.downLoadTemplate;
    }

    public void setDownLoadTemplate(String downLoadTemplate) {
        this.downLoadTemplate = downLoadTemplate;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

