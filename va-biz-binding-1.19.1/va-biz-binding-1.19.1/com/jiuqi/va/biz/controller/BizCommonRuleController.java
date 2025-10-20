/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.biz.controller;

import com.jiuqi.va.biz.domain.commrule.BizCommonRuleVO;
import com.jiuqi.va.biz.service.BizCommonRuleService;
import com.jiuqi.va.domain.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/common/rule"})
public class BizCommonRuleController {
    private static final Logger logger = LoggerFactory.getLogger(BizCommonRuleController.class);
    @Autowired
    private BizCommonRuleService bizCommonRuleService;

    @PostMapping(value={"/checkName"})
    R checkName(@RequestBody BizCommonRuleVO bizCommonRuleVO) {
        if (CollectionUtils.isEmpty(bizCommonRuleVO.getRulenames())) {
            return R.error((String)"\u89c4\u5219\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return R.ok().put("result", this.bizCommonRuleService.checkName(bizCommonRuleVO));
    }

    @PostMapping(value={"/save"})
    R save(@RequestBody BizCommonRuleVO bizCommonRuleVO) {
        if (!StringUtils.hasText(bizCommonRuleVO.getBiztype())) {
            return R.error((String)"\u4e1a\u52a1\u5927\u7c7b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            String s = this.bizCommonRuleService.saveRule(bizCommonRuleVO);
            return s == null ? R.ok() : R.error((String)s);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)"\u4fdd\u5b58\u5931\u8d25");
        }
    }

    @PostMapping(value={"/list"})
    R list(@RequestBody BizCommonRuleVO bizCommonRuleVO) {
        if (!StringUtils.hasText(bizCommonRuleVO.getBiztype())) {
            return R.error((String)"\u4e1a\u52a1\u5927\u7c7b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return R.ok().put("data", this.bizCommonRuleService.list(bizCommonRuleVO));
    }

    @PostMapping(value={"/delete"})
    R delete(@RequestBody BizCommonRuleVO vo) {
        String delete = this.bizCommonRuleService.delete(vo);
        return delete == null ? R.ok() : R.error((String)delete);
    }
}

