/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping2.dto.NVWADataUploadParam
 *  com.jiuqi.nr.mapping2.service.NvdataMappingService
 *  com.jiuqi.nr.mapping2.util.NvMappingMatchRule
 *  com.jiuqi.nr.mapping2.util.NvdataMappingContext
 *  com.jiuqi.nr.mapping2.util.NvdataParamType
 */
package com.jiuqi.nr.param.transfer.nvwaMapping;

import com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.dto.NVWADataUploadParam;
import com.jiuqi.nr.mapping2.service.NvdataMappingService;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;
import com.jiuqi.nr.mapping2.util.NvdataMappingContext;
import com.jiuqi.nr.mapping2.util.NvdataParamType;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvdataMappingMatchContext;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvdataMappingMatchRuleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NvdataMappingServiceImpl
implements NvdataMappingService {
    private static final Logger logger = LoggerFactory.getLogger(NvdataMappingServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired(required=false)
    List<NvdataMappingMatchRuleService> nvdataMappingMatchRuleList;

    public Map<String, List<String>> getMappingParams(NVWADataUploadParam nvwaDataUploadParam) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        if (nvwaDataUploadParam.isOrg()) {
            // empty if block
        }
        if (nvwaDataUploadParam.isBasedata()) {
            result.put(NvdataParamType.BASE_DATA_DEFINE.getFactoryId(), NvdataParamType.BASE_DATA_DEFINE.getValue());
        }
        if (nvwaDataUploadParam.isFormula() || nvwaDataUploadParam.isZb()) {
            ArrayList<String> baseData = new ArrayList<String>();
            baseData.addAll(NvdataParamType.FORM.getValue());
            baseData.add("TASK");
            if (nvwaDataUploadParam.isFormula()) {
                baseData.addAll(NvdataParamType.FORMULA_FORM.getValue());
                result.put(NvdataParamType.FORMULA_FORM.getFactoryId(), baseData);
            }
        }
        if (nvwaDataUploadParam.isZb()) {
            result.put(NvdataParamType.FORM_FIELD.getFactoryId(), NvdataParamType.FORM_FIELD.getValue());
        }
        if (nvwaDataUploadParam.isPeriod()) {
            result.put(NvdataParamType.PERIOD.getFactoryId(), null);
        }
        return result;
    }

    public void saveNvdataMapping(Map<String, List<TransferResourceDataBean>> param, NvdataMappingContext nvdataMappingContext) throws Exception {
        if (CollectionUtils.isEmpty(param)) {
            throw new Exception("nvdata\u6587\u4ef6\u751f\u6210\u6620\u5c04\u65b9\u6848\uff0c\u53c2\u6570\u6587\u4ef6\u89e3\u6790\u540e\u4e3a\u7a7a\uff0c\u7ed3\u675f\uff01");
        }
        NvMappingMatchRule nvMappingMatchRule = nvdataMappingContext.getNvMappingMatchRule();
        if (CollectionUtils.isEmpty(this.nvdataMappingMatchRuleList)) {
            throw new Exception(String.format("nvdata\u6587\u4ef6\u751f\u6210\u6620\u5c04\u65b9\u6848\uff0c%s\u89c4\u5219\u5bf9\u5e94\u7684\u5b9e\u73b0\u7c7b\u4e3a\u7a7a\uff0c\u7ed3\u675f\uff01", nvMappingMatchRule.getTitle()));
        }
        String mappingSchemeKey = nvdataMappingContext.getMappingSchemeKey();
        String thisFormSchemeSchemeKey = nvdataMappingContext.getFormSchemeSchemeKey();
        FormSchemeDefine thisFormSchemeScheme = this.runTimeViewController.getFormScheme(thisFormSchemeSchemeKey);
        TaskDefine thisTaskDefine = this.runTimeViewController.queryTaskDefine(thisFormSchemeScheme.getTaskKey());
        logger.info("\u6839\u636envdata\u751f\u6210\u6620\u5c04\u65b9\u6848\uff0c\u5f53\u524d\u6620\u5c04\u65b9\u6848\u6240\u5c5e\u4efb\u52a1\u4e3a{}\u3001{}\uff0c \u6240\u5c5e\u62a5\u8868\u65b9\u6848\u4e3a{}\u3001{}", thisTaskDefine.getKey(), thisTaskDefine.getTitle(), thisFormSchemeScheme.getKey(), thisFormSchemeScheme.getTitle());
        NvdataMappingMatchContext nvdataMappingMatchContext = new NvdataMappingMatchContext(mappingSchemeKey, thisTaskDefine.getKey(), thisTaskDefine.getTitle(), thisFormSchemeSchemeKey, thisFormSchemeScheme.getTitle(), thisTaskDefine.getDataScheme(), nvdataMappingContext.getNvMappingInsertRule());
        List collect = this.nvdataMappingMatchRuleList.stream().filter(a -> a.getNvMappingMatchRule() == nvMappingMatchRule).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            throw new Exception(String.format("nvdata\u6587\u4ef6\u751f\u6210\u6620\u5c04\u65b9\u6848\uff0c%s\u89c4\u5219\u5bf9\u5e94\u7684\u5b9e\u73b0\u7c7b\u4e3a\u7a7a\uff0c\u7ed3\u675f\uff01", nvMappingMatchRule.getTitle()));
        }
        ((NvdataMappingMatchRuleService)collect.get(0)).saveMappingMatchRule(param, nvdataMappingMatchContext);
    }
}

