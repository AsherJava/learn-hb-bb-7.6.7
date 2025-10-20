/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  org.json.JSONObject
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthInfoController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NvwaLoginService vaLoginService;

    @RequestMapping(value={"/api/gcreport/v1//authcInfo"}, method={RequestMethod.GET})
    public ResponseEntity<String> authentication() throws Exception {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        ContextIdentity identity = context.getIdentity();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject authcInfoObject = new JSONObject();
        authcInfoObject.put("userId", (Object)user.getId());
        authcInfoObject.put("username", (Object)(StringUtils.isEmpty((String)user.getFullname()) ? user.getName() : user.getFullname()));
        authcInfoObject.put("currentUser", (Object)this.objectMapper.writeValueAsString((Object)user));
        authcInfoObject.put("tenant", (Object)context.getTenant());
        if (null != identity) {
            // empty if block
        }
        return new ResponseEntity((Object)authcInfoObject.toString(), (MultiValueMap)headers, HttpStatus.OK.value());
    }

    @RequestMapping(value={"/api/gcreport/v1/systemOptions/getAllOnline"}, method={RequestMethod.GET})
    public BusinessResponseEntity<Long> onlineNumber() {
        return BusinessResponseEntity.ok((Object)this.vaLoginService.getLoginSessionCount(null));
    }

    @GetMapping(value={"/api/checkToken"})
    public BusinessResponseEntity<Boolean> checkToken(@RequestHeader(value="Authorization") String token) {
        return BusinessResponseEntity.ok((Object)true);
    }
}

