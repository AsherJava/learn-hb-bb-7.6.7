/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datapartnerapi.web;

import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryParam;
import com.jiuqi.nr.datapartnerapi.domain.FieldInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterParam;
import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryParam;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfo;
import com.jiuqi.va.domain.common.R;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value={"/api/data-partner-api/formdata"})
@ApiOperation(value="\u62a5\u8868\u53d6\u6570\u63a5\u53e3\uff0c\u5305\u62ec\uff1a\u83b7\u53d6\u7cfb\u7edf\u6240\u6709\u62a5\u8868\u3001\u5bfc\u51fa\u62a5\u8868\u8868\u6837\u3001\u83b7\u53d6\u62a5\u8868\u6307\u6807\u5143\u6570\u636e\u3001\u5bfc\u51fa\u62a5\u8868\u6570\u636e")
public interface FormDataApi {
    @GetMapping(value={"/form/list"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u6240\u6709\u62a5\u8868", response=TaskInfo.class)
    public R listForms(@RequestParam(name="taskKey") String var1, @RequestParam(name="schemeKey") String var2);

    @GetMapping(value={"/form/style"})
    @ApiOperation(value="\u5bfc\u51fa\u62a5\u8868\u8868\u6837\u6587\u4ef6\uff0c\u8fd4\u56de\uff1a\u62a5\u8868code.xlsx")
    public ResponseEntity<?> exportFormStyle(@RequestParam(name="formKey") String var1) throws IOException;

    @GetMapping(value={"/form/field"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u6307\u6807\u6570\u636e\u5143", response=FieldInfo.class)
    public R queryFormFields(@RequestParam(name="formKey") String var1);

    @PostMapping(value={"/form/auth"})
    @ApiOperation(value="\u83b7\u53d6\u8303\u56f4\u5185\u6709\u6743\u9650\u7684\u62a5\u8868")
    public R getAccessForms(@RequestBody FormAuthorityFilterParam var1);

    @PostMapping(value={"/data/field"})
    @ApiOperation(value="\u5bfc\u51fa\u62a5\u8868\u6307\u6807\u6570\u636e\uff0c\u8fd4\u56de\uff1a\u4efb\u52a1code.zip\uff1bzip\u5185\u4e3a\u6bcf\u4e2a\u62a5\u8868\u7684\u6570\u636e\u6587\u4ef6 \u62a5\u8868code.csv")
    public ResponseEntity<?> exportFormData(@RequestBody FormDataQueryParam var1) throws IOException;

    @PostMapping(value={"/data/growth-data"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u6570\u636e\u540c\u671f\u4e0a\u671f\u6570\u636e\u53ca\u589e\u957f\u7387")
    public R queryFieldsGrowthData(@RequestBody FieldGrowthDataQueryParam var1);
}

