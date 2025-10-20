/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonModuleController {
    Logger logger = LoggerFactory.getLogger(AnonModuleController.class);

    @GetMapping(value={"/module"})
    public Response getModule() {
        Framework framework = Framework.getInstance();
        JSONObject data = new JSONObject();
        try {
            FrameworkOperator.generateModuleInfo(framework, data);
            String property = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.databaseLimitMode", "false");
            data.put("databaseLimitMode", (Object)property);
            return Response.ok(data.toString());
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u6a21\u5757\u4fe1\u606f\u51fa\u9519", e);
            return Response.error(e.getMessage());
        }
    }
}

