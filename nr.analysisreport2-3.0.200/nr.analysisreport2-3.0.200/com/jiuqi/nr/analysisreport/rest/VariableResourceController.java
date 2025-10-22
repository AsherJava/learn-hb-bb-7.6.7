/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.analysisreport.rest;

import com.jiuqi.nr.analysisreport.support.ReportExprParseSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/analysis/variable"})
public class VariableResourceController {
    private Logger logger = LoggerFactory.getLogger(VariableResourceController.class);
    @Autowired
    private ReportExprParseSupport reportExprParseSupport;

    @GetMapping(value={"/getAllResources"})
    public Map<String, Object> getAllResources() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        List<Map<String, Object>> resources = this.reportExprParseSupport.getResources();
        res.put("data", resources);
        res.put("success", true);
        return res;
    }
}

