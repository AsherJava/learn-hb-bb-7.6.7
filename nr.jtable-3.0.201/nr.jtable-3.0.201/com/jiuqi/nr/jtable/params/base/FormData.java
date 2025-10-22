/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.MeasureViewData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(value="FormData", description="\u62a5\u8868\u5c5e\u6027")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FormData {
    private static final Logger logger = LoggerFactory.getLogger(FormData.class);
    @ApiModelProperty(value="\u62a5\u8868key", name="key")
    private String key;
    @ApiModelProperty(value="\u62a5\u8868code", name="code")
    private String code;
    @ApiModelProperty(value="\u62a5\u8868\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u62a5\u8868\u7f16\u53f7", name="serialNumber")
    private String serialNumber;
    @ApiModelProperty(value="\u62a5\u8868\u7c7b\u578b\uff0c\u53c2\u7167\uff08com.jiuqi.nr.definition.common.FormType\uff09", name="formType")
    private String formType;
    @ApiModelProperty(value="\u62a5\u8868\u662f\u5426\u6c47\u603b", name="isDataSum")
    private boolean isDataSum;
    @ApiModelProperty(value="\u62a5\u8868\u91cf\u7eb2\u89c6\u56fe\u5217\u8868", name="measures")
    private List<MeasureViewData> measures;
    @ApiModelProperty(value="\u62a5\u8868title", name="measureValues", example="419c0472-2ca8-4c00-b284-79075de90dd7: \"WANYUAN\"")
    private Map<String, String> measureValues;
    @ApiModelProperty(value="\u62a5\u8868\u662f\u5426\u5206\u6790", name="analysisForm")
    private boolean analysisForm;
    @ApiModelProperty(value="\u62a5\u8868\u662f\u5426\u9700\u8981\u91cd\u65b0\u52a0\u8f7d", name="needReload")
    private boolean needReload = false;
    @ApiModelProperty(value="\u5206\u6790\u62a5\u544a\u6a21\u677fKey", name="analysisReportKey")
    private String analysisReportKey;

    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean isDataSum() {
        return this.isDataSum;
    }

    public void setDataSum(boolean isDataSum) {
        this.isDataSum = isDataSum;
    }

    public List<MeasureViewData> getMeasures() {
        return this.measures;
    }

    public void setMeasures(List<MeasureViewData> measures) {
        this.measures = measures;
    }

    public Map<String, String> getMeasureValues() {
        return this.measureValues;
    }

    public void setMeasureValues(Map<String, String> measureValues) {
        this.measureValues = measureValues;
    }

    public String getAnalysisReportKey() {
        return this.analysisReportKey;
    }

    public void setAnalysisReportKey(String analysisReportKey) {
        this.analysisReportKey = analysisReportKey;
    }

    public void init(FormDefine formDefine) {
        String[] measureStrs;
        this.key = formDefine.getKey();
        this.code = formDefine.getFormCode();
        this.title = formDefine.getTitle();
        this.serialNumber = formDefine.getSerialNumber();
        if (formDefine.getFormType() != null) {
            FormType formDefineType = formDefine.getFormType();
            this.formType = formDefineType.name();
            if (formDefineType == FormType.FORM_TYPE_FLOAT) {
                IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
                List<RegionData> regions = jtableParamService.getRegions(this.key);
                for (RegionData region : regions) {
                    if (region.getType() == 2) {
                        this.formType = "FORM_TYPE_FLOAT_COLUMN";
                        break;
                    }
                    if (region.getType() != 3) continue;
                    this.formType = "FORM_TYPE_FLOAT_ROW";
                    break;
                }
            }
        }
        if (StringUtils.isNotEmpty((String)formDefine.getMeasureUnit()) && (measureStrs = formDefine.getMeasureUnit().split(";")).length == 2) {
            String tableKey = measureStrs[0];
            String measureValue = measureStrs[1];
            if (!measureValue.equalsIgnoreCase("NotDimession")) {
                this.measures = new ArrayList<MeasureViewData>();
                this.measureValues = new HashMap<String, String>();
                DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
                TableModelDefine tableModelDefine = dataModelService.getTableModelDefineById(tableKey);
                if (tableModelDefine != null) {
                    MeasureViewData measureViewData = new MeasureViewData(tableModelDefine);
                    this.measures.add(measureViewData);
                    this.measureValues.put(measureViewData.getKey(), measureValue);
                }
            }
        }
        this.isDataSum = formDefine.getIsGather();
        this.analysisForm = formDefine.isAnalysisForm();
        this.analysisReportKey = (String)formDefine.getExtensionProp("reportKey");
    }

    public boolean isNeedReload() {
        return this.needReload;
    }

    public void setNeedReload(boolean needReload) {
        this.needReload = needReload;
    }
}

