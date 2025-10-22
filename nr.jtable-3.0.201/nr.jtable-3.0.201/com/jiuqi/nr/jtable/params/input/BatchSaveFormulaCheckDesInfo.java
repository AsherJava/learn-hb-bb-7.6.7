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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="BatchSaveFormulaCheckDesInfo", description="\u6279\u91cf\u4fdd\u5b58\u516c\u5f0f\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u53c2\u6570")
public class BatchSaveFormulaCheckDesInfo
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5ba1\u6838\u64cd\u4f5c(\u5355\u8868\u5ba1\u6838\u3001\u5168\u5ba1\u3001\u6279\u91cf\u5ba1\u6838)", name="actionName", required=true)
    private String actionName;
    @ApiModelProperty(value="\u5f02\u6b65\u4efb\u52a1key", name="asyncTaskKey")
    private String asyncTaskKey;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u516c\u5f0f\u65b9\u6848keys", name="formulaSchemeKeys", required=true)
    private String formulaSchemeKeys;
    @ApiModelProperty(value="\u9700\u8981\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u7684\u516c\u5f0f\uff08\u6309\u62a5\u8868\u5206\u7ec4\uff09", name="formulas")
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    @ApiModelProperty(value="\u9700\u8981\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u7684\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b", name="checkTypes")
    private List<Integer> checkTypes = new ArrayList<Integer>();
    @ApiModelProperty(value="\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u6587\u672c", name="unitTitle")
    private String description;
    @ApiModelProperty(value="\u5141\u8bb8\u4e0a\u62a5\u540e\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e", name="allowAddErrorMsgAfterReport")
    private boolean allowAddErrorMsgAfterReport;
    @ApiModelProperty(value="\u662f\u5426\u4e3a\u4e0a\u62a5\u524d\u5ba1\u6838", name="isUpload")
    private boolean upload;
    @ApiModelProperty(value="\u662f\u5426\u8986\u76d6\u51fa\u9519\u8bf4\u660e", name="coverOriginalDes")
    private boolean coverOriginalDes;
    @ApiModelProperty(value="\u9700\u8981\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u7684\u5355\u4f4d\u5217\u8868", name="selectedUnits")
    private List<String> selectedUnits;
    private String descriptionFilterType;
    private String descriptionFilterContent;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getDescriptionFilterType() {
        return this.descriptionFilterType;
    }

    public void setDescriptionFilterType(String descriptionFilterType) {
        this.descriptionFilterType = descriptionFilterType;
    }

    public String getDescriptionFilterContent() {
        return this.descriptionFilterContent;
    }

    public void setDescriptionFilterContent(String descriptionFilterContent) {
        this.descriptionFilterContent = descriptionFilterContent;
    }

    public boolean isCoverOriginalDes() {
        return this.coverOriginalDes;
    }

    public void setCoverOriginalDes(boolean coverOriginalDes) {
        this.coverOriginalDes = coverOriginalDes;
    }

    public List<String> getSelectedUnits() {
        return this.selectedUnits;
    }

    public void setSelectedUnits(List<String> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public boolean isUpload() {
        return this.upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public boolean isAllowAddErrorMsgAfterReport() {
        return this.allowAddErrorMsgAfterReport;
    }

    public void setAllowAddErrorMsgAfterReport(boolean allowAddErrorMsgAfterReport) {
        this.allowAddErrorMsgAfterReport = allowAddErrorMsgAfterReport;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public Map<String, List<String>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<String>> formulas) {
        this.formulas = formulas;
    }

    public List<Integer> getCheckTypes() {
        return this.checkTypes;
    }

    public void setCheckTypes(List<Integer> checkTypes) {
        this.checkTypes = checkTypes;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextEntityId;
    }
}

