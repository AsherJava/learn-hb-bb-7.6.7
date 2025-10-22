/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.mobile.approval.client.FormDataQueryClient
 *  com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FormDataReturnInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.mobile.approval.web;

import com.jiuqi.gcreport.mobile.approval.client.FormDataQueryClient;
import com.jiuqi.gcreport.mobile.approval.service.FormDataQueryService;
import com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FormDataQueryRestController
implements FormDataQueryClient {
    @Autowired
    FormDataQueryService formDataQueryService;
    private Logger logger = LoggerFactory.getLogger(FormDataQueryRestController.class);

    @ResponseBody
    public FormDataReturnInfo formDataQuery(@RequestBody FormDataQueryInfo formDataQueryInfo) {
        FormDataReturnInfo formDataReturnInfo = new FormDataReturnInfo();
        formDataReturnInfo.setQueryInfo(formDataQueryInfo);
        try {
            formDataReturnInfo.setFieldDatas(this.formDataQueryService.formDataQuery(formDataQueryInfo));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
        return formDataReturnInfo;
    }

    public void previewFile(@PathVariable String fileKey, HttpServletResponse response) {
        this.formDataQueryService.previewFile(fileKey, response);
    }

    public ActionDataReturnInfo queryWorkflowDataInfo(QueryParamInfo param) {
        return this.formDataQueryService.queryWorkflowDataInfo(param);
    }
}

