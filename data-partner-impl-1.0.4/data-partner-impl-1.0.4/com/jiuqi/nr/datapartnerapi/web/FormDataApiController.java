/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryParam
 *  com.jiuqi.nr.datapartnerapi.domain.FieldInfo
 *  com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterParam
 *  com.jiuqi.nr.datapartnerapi.domain.FormDataQueryParam
 *  com.jiuqi.nr.datapartnerapi.domain.FormInfo
 *  com.jiuqi.nr.datapartnerapi.domain.TaskInfo
 *  com.jiuqi.nr.datapartnerapi.web.FormDataApi
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.http.ContentDisposition
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datapartnerapi.web;

import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryParam;
import com.jiuqi.nr.datapartnerapi.domain.FieldInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterParam;
import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryParam;
import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfo;
import com.jiuqi.nr.datapartnerapi.service.FormDataApiService;
import com.jiuqi.nr.datapartnerapi.web.FormDataApi;
import com.jiuqi.va.domain.common.R;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormDataApiController
implements FormDataApi {
    @Autowired
    private FormDataApiService formDataApiService;
    private static final Logger logger = LoggerFactory.getLogger(FormDataApiController.class);

    public R listForms(@RequestParam(name="taskKey") String taskKey, @RequestParam(name="schemeKey") String schemeKey) {
        try {
            List<TaskInfo> taskInfos = this.formDataApiService.queryAllForms(taskKey, schemeKey);
            return R.ok().put("data", taskInfos);
        }
        catch (Exception e) {
            String errorMessage = "\u83b7\u53d6\u7cfb\u7edf\u6240\u6709\u62a5\u8868\u51fa\u9519\uff1a" + e.getMessage();
            logger.error(errorMessage, e);
            return R.error().put("data", (Object)errorMessage);
        }
    }

    public ResponseEntity<?> exportFormStyle(@RequestParam(name="formKey") String formKey) throws IOException {
        try {
            FormInfo formInfo = this.formDataApiService.queryFormByKey(formKey);
            if (formInfo == null) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u6839\u636e\u62a5\u8868key\u3010" + formKey + "\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u5b9a\u4e49\uff01")));
            }
            String formCode = formInfo.getFormCode();
            String fileName = formCode + ".xlsx";
            byte[] bytes = this.formDataApiService.downloadFormStyle(formInfo);
            if (bytes == null) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u6839\u636e\u62a5\u8868key\u3010" + formKey + "\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u5b9a\u4e49\uff01")));
            }
            if (bytes.length == 0) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)"\u5bfc\u51fa\u6587\u4ef6\u4e3a\u7a7a\uff01"));
            }
            return this.buildDownloadResponse(bytes, fileName);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u62a5\u8868\u8868\u6837\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u5bfc\u51fa\u62a5\u8868\u8868\u6837\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u51fa\u9519\uff1a" + e.getMessage())));
        }
    }

    public R queryFormFields(@RequestParam(name="formKey") String formKey) {
        try {
            List<FieldInfo> fieldInfos = this.formDataApiService.queryFormFields(formKey);
            return R.ok().put("data", fieldInfos);
        }
        catch (Exception e) {
            String errorMessage = "\u83b7\u53d6\u62a5\u8868\u6307\u6807\u6570\u636e\u5143\u65f6\u51fa\u9519\uff1a" + e.getMessage();
            logger.error(errorMessage, e);
            return R.error().put("data", (Object)errorMessage);
        }
    }

    public R getAccessForms(FormAuthorityFilterParam formAuthorityFilterParam) {
        FormAuthorityFilterDTO formAuthorityFilterDTO = new FormAuthorityFilterDTO(formAuthorityFilterParam);
        return R.ok().put("data", this.formDataApiService.getAccessForms(formAuthorityFilterDTO));
    }

    public ResponseEntity<?> exportFormData(@RequestBody FormDataQueryParam formDataQueryParam) throws IOException {
        FormDataQueryDTO formDataQueryDTO = new FormDataQueryDTO(formDataQueryParam);
        try {
            String taskKey = formDataQueryDTO.getTaskKey();
            TaskInfo taskInfo = this.formDataApiService.queryTaskByKey(taskKey);
            if (taskInfo == null) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u6839\u636e\u4efb\u52a1key\u3010" + taskKey + "\u3011\u672a\u67e5\u8be2\u5230\u4efb\u52a1\u5b9a\u4e49\uff01")));
            }
            String taskCode = taskInfo.getTaskCode();
            String fileName = taskCode + ".zip";
            byte[] bytes = this.formDataApiService.exportTaskData(formDataQueryDTO);
            if (bytes == null) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u6839\u636e\u4efb\u52a1key\u3010" + taskKey + "\u3011\u672a\u67e5\u8be2\u5230\u4efb\u52a1\u5b9a\u4e49\uff01")));
            }
            if (bytes.length == 0) {
                return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)"\u5bfc\u51fa\u6587\u4ef6\u4e3a\u7a7a\uff01"));
            }
            return this.buildDownloadResponse(bytes, fileName);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u62a5\u8868\u6570\u636e\u8fc7\u7a0b\u4e2d\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            return ResponseEntity.internalServerError().body((Object)R.error().put("data", (Object)("\u5bfc\u51fa\u62a5\u8868\u6570\u636e\u8fc7\u7a0b\u4e2d\u65f6\u51fa\u9519\uff1a" + e.getMessage())));
        }
    }

    public R queryFieldsGrowthData(FieldGrowthDataQueryParam fieldGrowthDataQueryParam) {
        FieldGrowthDataQueryDTO fieldGrowthDataQueryDTO = new FieldGrowthDataQueryDTO(fieldGrowthDataQueryParam);
        try {
            Map<String, FieldGrowthData> fieldGrowthInfoMap = this.formDataApiService.queryFieldsGrowthData(fieldGrowthDataQueryDTO);
            if (fieldGrowthInfoMap == null) {
                return R.error().put("data", (Object)("\u6839\u636e\u4f20\u5165\u7684\u62a5\u8868\u65b9\u6848key\uff1a\u3010" + fieldGrowthDataQueryDTO.getFormSchemeKey() + "\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\uff01"));
            }
            return R.ok().put("data", fieldGrowthInfoMap);
        }
        catch (Exception e) {
            String errorMessage = "\u83b7\u53d6\u62a5\u8868\u6307\u6807\u540c\u671f\u4e0a\u671f\u6570\u636e\u65f6\u51fa\u9519\uff1a" + e.getMessage();
            logger.error(errorMessage, e);
            return R.error().put("data", (Object)errorMessage);
        }
    }

    private ResponseEntity<InputStreamResource> buildDownloadResponse(byte[] bytes, String fileName) {
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder((String)"attachment").filename(fileName, StandardCharsets.UTF_8).build());
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(headers)).contentLength((long)bytes.length).body((Object)resource);
    }
}

