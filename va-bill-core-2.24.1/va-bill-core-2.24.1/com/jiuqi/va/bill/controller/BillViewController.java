/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.biz.utils.Utils
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.va.bill.domain.BillViewDTO;
import com.jiuqi.va.bill.domain.BillViewPreviewDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillEditService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.LogUtils;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.biz.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/view"})
public class BillViewController {
    @Autowired
    private BillEditService billEditService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BillViewController.class);

    @PostMapping(value={"/get"})
    public R<Map<String, Object>> get(@RequestBody BillViewDTO params) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        contextImpl.setContextValue(params.getContextParams());
        contextImpl.setSchemeCode(params.getSchemeCode());
        try {
            if (StringUtils.hasText(params.getExternalViewName())) {
                if (StringUtils.hasText(params.getDataId())) {
                    contextImpl.setContextValue("loadById", true);
                    return R.ok(this.billEditService.load(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getDataId(), params.getViewName(), params.getSchemeCode(), params.getExternalViewName()));
                }
                return R.ok(this.billEditService.load(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), params.getSchemeCode(), params.getExternalViewName()));
            }
            if (StringUtils.hasText(params.getDataId())) {
                contextImpl.setContextValue("loadById", true);
                return R.ok(this.billEditService.load(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getDataId(), params.getViewName(), params.getSchemeCode()));
            }
            return R.ok(this.billEditService.load(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), params.getSchemeCode()));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            HashMap map = new HashMap();
            return new R(501, e.getMessage(), null, map);
        }
    }

    @PostMapping(value={"/add"})
    public R<Map<String, Object>> add(@RequestBody BillViewDTO params) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        contextImpl.setContextValue(params.getContextParams());
        contextImpl.setSchemeCode(params.getSchemeCode());
        try {
            if (StringUtils.hasText(params.getExternalViewName())) {
                return R.ok(this.billEditService.add(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getViewName(), params.getInitValues(), params.getExternalViewName()));
            }
            return R.ok(this.billEditService.add((BillContext)contextImpl, params.getDefineCode(), params.getDefineVer(), params.getViewName(), params.getSchemeCode(), params.getInitValues()));
        }
        catch (BillException e) {
            Boolean closeTab;
            Map<String, List<Map<String, Object>>> tablesData;
            if (e.getLevel() == 0) {
                logger.error("\u65b0\u5efa\u5355\u636e\u5931\u8d25", e);
            } else if (e.getLevel() == 1) {
                logger.debug("\u65b0\u5efa\u5355\u636e\u5931\u8d25", e);
            }
            HashMap<String, Object> map = new HashMap<String, Object>();
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null) {
                map.put("checkMessages", checkMessages);
            }
            if ((tablesData = e.getTablesData()) != null) {
                map.put("tablesData", tablesData);
            }
            if ((closeTab = e.getCloseTab()) != null) {
                map.put("closeTab", closeTab);
            }
            return new R(501, e.getMessage(), null, map);
        }
        catch (Exception e) {
            logger.error("\u65b0\u5efa\u5355\u636e\u5931\u8d25", e);
            return new R(501, e.getMessage(), null, new HashMap());
        }
    }

    @PostMapping(value={"/edit"})
    public R<Map<String, Object>> edit(@RequestBody BillViewDTO params) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        contextImpl.setContextValue(params.getContextParams());
        try {
            return R.ok(this.billEditService.edit(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), params.getSchemeCode()));
        }
        catch (BillException e) {
            Boolean closeTab;
            Map<String, List<Map<String, Object>>> tablesData;
            if (e.getLevel() == 0) {
                logger.error("\u4fee\u6539\u5355\u636e\u5931\u8d25", e);
            } else if (e.getLevel() == 1) {
                logger.debug("\u4fee\u6539\u5355\u636e\u5931\u8d25", e);
            }
            HashMap<String, Object> map = new HashMap<String, Object>();
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null) {
                map.put("checkMessages", checkMessages);
            }
            if ((tablesData = e.getTablesData()) != null) {
                map.put("tablesData", tablesData);
            }
            if ((closeTab = e.getCloseTab()) != null) {
                map.put("closeTab", closeTab);
            }
            return new R(501, e.getMessage(), null, map);
        }
    }

    @PostMapping(value={"/sync"})
    public R<Map<String, Object>> sync(@RequestBody BillViewDTO params) {
        String traceId = Utils.getTraceId();
        if (!StringUtils.hasText(traceId)) {
            Utils.setTraceId((String)Guid.newGuid());
        }
        LogUtils.logUserLocalIp(this.stringRedisTemplate);
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        contextImpl.setContextValue(params.getContextParams());
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        contextImpl.setSchemeCode(params.getSchemeCode());
        try {
            return R.ok(this.billEditService.sync(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), params.getAction(), params.getExternalViewName()));
        }
        catch (BillException e) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Map<String, List<Map<String, Object>>> tablesData = e.getTablesData();
            if (tablesData != null) {
                map.put("tablesData", tablesData);
            }
            if (e.isInvisible()) {
                if (e.getLevel() == 0) {
                    logger.error("\u5355\u636e\u6267\u884c\u5f02\u5e38", e);
                } else if (e.getLevel() == 1) {
                    logger.debug("\u5355\u636e\u6267\u884c\u5f02\u5e38", e);
                }
                return new R((Throwable)e, e.getMessage(), 1, map);
            }
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null) {
                map.put("checkMessages", checkMessages);
            } else if (e.getLevel() == 0) {
                logger.error("\u5355\u636e\u6267\u884c\u5f02\u5e38", e);
            } else if (e.getLevel() == 1) {
                logger.debug("\u5355\u636e\u6267\u884c\u5f02\u5e38", e);
            }
            Boolean closeTab = e.getCloseTab();
            if (closeTab != null) {
                map.put("closeTab", closeTab);
            }
            return new R(501, e.getMessage(), null, map);
        }
        catch (Exception e) {
            logger.error("\u5355\u636e\u6267\u884c\u5f02\u5e38", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/preview"})
    public R<Map<String, Object>> preview(@RequestBody BillViewPreviewDTO params) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(null);
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        return R.ok(this.billEditService.load(contextImpl, params.getDefine(), null, params.getViewName()));
    }
}

