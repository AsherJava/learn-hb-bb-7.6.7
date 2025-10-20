/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.common.BaseDataMenuUtil;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataContextController")
@RequestMapping(value={"/baseData/context"})
public class BaseDataContextController {
    private static Logger logger = LoggerFactory.getLogger(BaseDataContextController.class);
    @Autowired
    private BaseDataContextService baseDataContextService;

    @GetMapping(value={"/getLoginUnit"})
    Object getLoginUnit() {
        String loginUnit = null;
        try {
            loginUnit = ShiroUtil.getUser().getLoginUnit();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        R r = R.ok();
        r.put("loginUnit", (Object)loginUnit);
        return MonoVO.just((Object)r);
    }

    @GetMapping(value={"/getLoginToken"})
    Object getLoginToken() {
        String token = "-";
        try {
            token = ShiroUtil.getSubjct().getSession().getId().toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        R r = R.ok();
        r.put("token", (Object)token);
        return MonoVO.just((Object)r);
    }

    @PostMapping(value={"/getModifyEnv"})
    Object getModifyEnv(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataContextService.getCurrEnv(param));
    }

    @GetMapping(value={"/getMenuExtend"})
    Object getMenuExtend() {
        return MonoVO.just(BaseDataMenuUtil.getMenuExtends());
    }

    @GetMapping(value={"/getLanguage"})
    Object getLanguage() {
        return MonoVO.just((Object)LocaleContextHolder.getLocale().toLanguageTag().toLowerCase());
    }
}

