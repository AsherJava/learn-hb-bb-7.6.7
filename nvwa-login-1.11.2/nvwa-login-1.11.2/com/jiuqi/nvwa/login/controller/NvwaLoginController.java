/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.login.controller;

import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nvwa"})
public class NvwaLoginController {
    @Autowired
    private NvwaLoginService vaLoginService;

    @PostMapping(value={"/login"})
    Object login(@RequestBody NvwaLoginUserDTO userDTO) {
        return MonoVO.just((Object)this.vaLoginService.tryLogin(userDTO, false));
    }

    @GetMapping(value={"/logout"})
    Object logout() {
        return MonoVO.just((Object)this.vaLoginService.logout());
    }

    @PostMapping(value={"/checkLogin"})
    Object checnLogin() {
        return MonoVO.just((Object)R.ok());
    }

    @GetMapping(value={"/getLoginContext"})
    Object getLoginContext() {
        return MonoVO.just((Object)this.vaLoginService.getLoginContext());
    }

    @PostMapping(value={"/changeLoginContext"})
    Object changeLoginContext(@RequestBody Map<String, Object> params) {
        return MonoVO.just((Object)this.vaLoginService.changeLoginContext(this.vaLoginService.checkChangeLoginContext(params)));
    }
}

