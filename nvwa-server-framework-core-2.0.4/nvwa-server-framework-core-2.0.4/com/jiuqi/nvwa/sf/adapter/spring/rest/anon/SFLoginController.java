/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.nvwa.sf.adapter.spring.login.SFLoginCheckManage;
import com.jiuqi.nvwa.sf.adapter.spring.rest.anon.dto.SFLoginUser;
import com.jiuqi.nvwa.sf.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class SFLoginController {
    @Autowired
    private SFLoginCheckManage sfLoginCheckManage;

    @PostMapping(value={"/login"})
    public Response services(@RequestBody @SFDecrypt SFLoginUser user) {
        Users.User checkUser = new Users.User();
        checkUser.setName(user.getName());
        checkUser.setPassword(user.getPassword());
        checkUser.setEncoder(user.getEncoder());
        try {
            if (!Framework.getInstance().checkUser(checkUser)) {
                return Response.error("\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef");
            }
        }
        catch (Exception e) {
            return Response.error(e.getMessage());
        }
        String token = this.sfLoginCheckManage.doLogin();
        if (StringUtils.isNotEmpty((String)token)) {
            return Response.ok(token);
        }
        return Response.error("\u767b\u5f55\u72b6\u6001\u5f02\u5e38");
    }
}

