/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.common.action.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcActionApi {
    public static final String API_PATH = "/api/gcreport/v1/gcactions/";

    @PostMapping(value={"/api/gcreport/v1/gcactions/{actionCode}"})
    public BusinessResponseEntity<Object> executeAction(@PathVariable(value="actionCode") String var1, @RequestBody(required=false) String var2, HttpServletRequest var3);

    @PostMapping(value={"/api/gcreport/v1/gcactions/visible/{actionCode}"})
    public BusinessResponseEntity<Boolean> isVisibleAction(@PathVariable(value="actionCode") String var1, @RequestBody(required=false) String var2);

    @PostMapping(value={"/api/gcreport/v1/gcactions/enable/{actionCode}"})
    public BusinessResponseEntity<Boolean> isEnableAction(@PathVariable(value="actionCode") String var1, @RequestBody(required=false) String var2);
}

