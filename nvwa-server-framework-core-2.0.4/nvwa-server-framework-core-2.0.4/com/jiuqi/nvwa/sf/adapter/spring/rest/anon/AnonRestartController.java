/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import java.util.Date;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonRestartController {
    Logger logger = LoggerFactory.getLogger(AnonRestartController.class);

    @GetMapping(value={"/restart"})
    public Response needRestart() {
        Framework framework = Framework.getInstance();
        JSONObject data = new JSONObject();
        if (!framework.isModuleValidate() || !framework.isLicenceValidate()) {
            data.put("needRestart", false);
            return Response.ok(data.toString());
        }
        data.put("needRestart", true);
        return Response.ok(data.toString());
    }

    @PostMapping(value={"/restart"})
    public Response restart() {
        Framework framework = Framework.getInstance();
        Date startTime = new Date();
        this.logger.info("*****************\u670d\u52a1\u91cd\u542f\u5f00\u59cb*****************");
        try {
            framework.tryInitModules();
        }
        catch (Exception e) {
            this.logger.error("restart error", e);
            return Response.error(e.getMessage());
        }
        this.logger.info("*****************\u670d\u52a1\u91cd\u542f\u7ed3\u675f\uff0c\u8017\u65f6{}\u79d2*****************", (Object)((new Date().getTime() - startTime.getTime()) / 1000L));
        return Response.ok("");
    }
}

