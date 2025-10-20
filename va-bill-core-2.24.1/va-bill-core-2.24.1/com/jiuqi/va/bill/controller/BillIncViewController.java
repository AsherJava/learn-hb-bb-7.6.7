/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillViewDTO
 *  com.jiuqi.va.domain.bill.R
 *  com.jiuqi.va.feign.client.BillViewClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.va.bill.config.VaBillCoreConfig;
import com.jiuqi.va.bill.domain.BillViewPreviewDTO;
import com.jiuqi.va.bill.domain.SublistExportDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillIncEditService;
import com.jiuqi.va.bill.service.impl.BillIncEditServiceImpl;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.ExcelUtil;
import com.jiuqi.va.bill.utils.LogUtils;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillViewDTO;
import com.jiuqi.va.domain.bill.R;
import com.jiuqi.va.feign.client.BillViewClient;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/view/inc"})
public class BillIncViewController {
    private static final Logger logger = LoggerFactory.getLogger(BillIncEditServiceImpl.class);
    @Autowired
    private BillIncEditService billEditService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value(value="${biz.cache.mode:1}")
    private int cacheMode;

    @PostMapping(value={"/get"})
    public R<Map<String, Object>> get(@RequestBody BillViewDTO params) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        contextImpl.setTriggerOrigin(params.getTriggerOrigin());
        contextImpl.setSchemeCode(params.getSchemeCode());
        contextImpl.setContextValue(params.getContextParams());
        try {
            if (StringUtils.hasText(params.getExternalViewName())) {
                return R.ok(this.billEditService.load(contextImpl, params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), params.getSchemeCode(), params.getExternalViewName()));
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
            Map<String, List<List<Object>>> tablesData;
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
            if ((tablesData = e.getFrontTablesData()) != null) {
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
            Map<String, List<List<Object>>> tablesData;
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
            if ((tablesData = e.getFrontTablesData()) != null) {
                map.put("tablesData", tablesData);
            }
            if ((closeTab = e.getCloseTab()) != null) {
                map.put("closeTab", closeTab);
            }
            return new R(501, e.getMessage(), null, map);
        }
    }

    @PostMapping(value={"/sync"})
    public R<Map<String, Object>> sync(@RequestHeader HttpHeaders headers, @RequestBody BillViewDTO params) {
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
            Map data = params.getAction();
            Map modelParams = (Map)data.get("model");
            DataState modelState = (DataState)Convert.cast(modelParams.get("state"), DataState.class);
            String cacheID = params.getCacheID();
            if (BillUtils.isFeignClient(headers)) {
                return R.ok(this.billEditService.sync(contextImpl, cacheID, params.getReqID(), params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), data, params.getExternalViewName()));
            }
            if ((modelState == DataState.NEW || modelState == DataState.EDIT) && this.cacheMode == 1 && VaBillCoreConfig.isRedisEnable()) {
                String url = this.billEditService.getUrl();
                if (!StringUtils.hasText(url)) {
                    return R.ok(this.billEditService.sync(contextImpl, cacheID, params.getReqID(), params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), data, params.getExternalViewName()));
                }
                String cacheUrl = (String)this.stringRedisTemplate.opsForValue().get((Object)cacheID);
                if (!StringUtils.hasText(cacheUrl)) {
                    return R.ok(this.billEditService.sync(contextImpl, cacheID, params.getReqID(), params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), data, params.getExternalViewName()));
                }
                if (cacheUrl.equals(url)) {
                    return R.ok(this.billEditService.sync(contextImpl, cacheID, params.getReqID(), params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), data, params.getExternalViewName()));
                }
                BillViewClient billClient = (BillViewClient)FeignUtil.getDynamicClient(BillViewClient.class, (String)cacheUrl);
                return billClient.sync(params);
            }
            return R.ok(this.billEditService.sync(contextImpl, params.getCacheID(), params.getReqID(), params.getDefineCode(), params.getDefineVer(), params.getBillCode(), params.getViewName(), data, params.getExternalViewName()));
        }
        catch (BillException e) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Map<String, List<List<Object>>> tablesData = e.getFrontTablesData();
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

    @PostMapping(value={"/sublist/export"})
    public void sublistExport(@RequestBody SublistExportDTO dto, HttpServletResponse response) {
        this.billEditService.export(dto, response);
    }

    @PostMapping(value={"/sublist/exportErrorData"})
    public void sublistExportExcel(String titles, String showData, String selecttype, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List titleList = (List)mapper.readValue(titles, List.class);
            List tableDataList = (List)mapper.readValue(showData, List.class);
            ExcelUtil.exportExcel(titleList, tableDataList, selecttype, response);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"/refreshCache/{cacheID}"})
    void refreshCache(@RequestHeader HttpHeaders headers, @PathVariable(value="cacheID") String cacheID) {
        if (BillUtils.isFeignClient(headers)) {
            this.billEditService.refreshCache(cacheID);
            return;
        }
        String url = this.billEditService.getUrl();
        if (!StringUtils.hasText(url)) {
            this.billEditService.refreshCache(cacheID);
            return;
        }
        String cacheUrl = (String)this.stringRedisTemplate.opsForValue().get((Object)cacheID);
        if (!StringUtils.hasText(cacheUrl)) {
            this.billEditService.refreshCache(cacheID);
            return;
        }
        if (cacheUrl.equals(url)) {
            this.billEditService.refreshCache(cacheID);
        } else {
            BillViewClient billClient = (BillViewClient)FeignUtil.getDynamicClient(BillViewClient.class, (String)cacheUrl);
            billClient.refreshCache(cacheID);
        }
    }

    @PostMapping(value={"/delCache/{cacheID}"})
    void delCache(@RequestHeader HttpHeaders headers, @PathVariable(value="cacheID") String cacheID) {
        if (BillUtils.isFeignClient(headers)) {
            this.billEditService.delCache(cacheID);
            return;
        }
        String url = this.billEditService.getUrl();
        if (!StringUtils.hasText(url)) {
            this.billEditService.delCache(cacheID);
            return;
        }
        String cacheUrl = (String)this.stringRedisTemplate.opsForValue().get((Object)cacheID);
        if (!StringUtils.hasText(cacheUrl)) {
            this.billEditService.delCache(cacheID);
            return;
        }
        if (cacheUrl.equals(url)) {
            this.billEditService.delCache(cacheID);
        } else {
            BillViewClient billClient = (BillViewClient)FeignUtil.getDynamicClient(BillViewClient.class, (String)cacheUrl);
            billClient.delCache(cacheID);
        }
    }
}

