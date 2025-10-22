/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  io.swagger.annotations.Api
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.io.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.params.input.QueryParms;
import com.jiuqi.nr.io.service.DataFileImportService;
import com.jiuqi.nr.io.util.ParamUtil;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags={"DataFileImportController-\u6570\u636e\u5bfc\u5165"})
public class DataFileImportController {
    private static final Logger log = LoggerFactory.getLogger(DataFileImportController.class);
    @Autowired
    private DataFileImportService dataFileService;

    @PostMapping(value={"/io/import"})
    public List<Map<String, Object>> dataImport(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        QueryParms param = null;
        AsyncTaskMonitor monitor = null;
        List<Map<String, Object>> result = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            param = (QueryParms)mapper.readValue(paramJson, QueryParms.class);
            monitor = null;
            result = this.dataFileService.dataImport(ParamUtil.getAllParam(param), file, monitor);
        }
        catch (Exception e) {
            log.info("\u89e3\u6790\u8bf7\u6c42\u53c2\u6570\u51fa\u9519{}", e);
            throw e;
        }
        return result;
    }
}

