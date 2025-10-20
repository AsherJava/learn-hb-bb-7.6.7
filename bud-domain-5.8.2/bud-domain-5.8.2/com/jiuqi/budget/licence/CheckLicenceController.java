/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.budget.common.utils.ResultUtil
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.licence;

import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.common.utils.ResultUtil;
import com.jiuqi.budget.licence.LicenceUtil;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/biz/check"})
public class CheckLicenceController {
    @PostMapping(value={"/licence"})
    public ResultVO<Object> auth(@RequestBody Map<String, String> map) {
        return ResultUtil.ok((Object)LicenceUtil.getAuthValue(map));
    }
}

