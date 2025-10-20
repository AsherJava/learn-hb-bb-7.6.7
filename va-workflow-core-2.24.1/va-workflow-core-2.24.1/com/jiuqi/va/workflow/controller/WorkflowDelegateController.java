/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.delegate.DelegateVO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.springframework.dao.DataAccessException
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.delegate.DelegateVO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/delegate"})
public class WorkflowDelegateController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowDelegateController.class);
    @Autowired
    private DelegateService delegateService;

    @PostMapping(value={"/add"})
    public R add(@RequestBody DelegateDTO delegatedto) {
        try {
            this.delegateService.add(delegatedto);
        }
        catch (DataAccessException e) {
            logger.error("\u65b0\u5efa\u59d4\u6258\u5f02\u5e38", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.adddelegatefailed"));
        }
        catch (Exception e) {
            logger.error("\u65b0\u5efa\u59d4\u6258\u5f02\u5e38", e);
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.adddelegatefailed") + e.getMessage()));
        }
        LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u65b0\u5efa", (String)"\u59d4\u6258", (String)delegatedto.getId(), (String)JSONUtil.toJSONString((Object)delegatedto));
        return R.ok();
    }

    @PostMapping(value={"/update"})
    public R update(@RequestBody DelegateDTO delegatedto) {
        R r;
        try {
            r = this.delegateService.update(delegatedto);
        }
        catch (Exception e) {
            logger.error("\u4fee\u6539\u59d4\u6258\u5f02\u5e38", e);
            return R.error((int)4, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.updatedelegatefailed") + e.getMessage()));
        }
        if (r.getCode() == 0) {
            LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u4fee\u6539", (String)"\u59d4\u6258", (String)delegatedto.getId(), (String)JSONUtil.toJSONString((Object)delegatedto));
        }
        return r;
    }

    @PostMapping(value={"/delete"})
    public R delete(@RequestBody List<DelegateDTO> delegatedtos) {
        R r = this.delegateService.delete(delegatedtos);
        if (r.getCode() == 0) {
            LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u5220\u9664", (String)"\u59d4\u6258", (String)"", (String)JSONUtil.toJSONString(delegatedtos));
        }
        return r;
    }

    @PostMapping(value={"/enable"})
    public R enable(@RequestBody List<DelegateDTO> delegatedtos) {
        R r = this.delegateService.enable(delegatedtos);
        if (r.getCode() == 0) {
            LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u542f\u7528", (String)"\u59d4\u6258", (String)"", (String)JSONUtil.toJSONString(delegatedtos));
        }
        return r;
    }

    @PostMapping(value={"/disable"})
    public R disable(@RequestBody List<DelegateDTO> delegatedtos) {
        R r = this.delegateService.disable(delegatedtos);
        if (r.getCode() == 0) {
            LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u505c\u7528", (String)"\u59d4\u6258", (String)"", (String)JSONUtil.toJSONString(delegatedtos));
        }
        return r;
    }

    @PostMapping(value={"/query"})
    public PageVO<DelegateVO> query(@RequestBody DelegateDTO delegatedto) {
        List list = this.delegateService.query(delegatedto);
        delegatedto.setPagination(false);
        int total = this.delegateService.count(delegatedto);
        return new PageVO(list, total);
    }
}

