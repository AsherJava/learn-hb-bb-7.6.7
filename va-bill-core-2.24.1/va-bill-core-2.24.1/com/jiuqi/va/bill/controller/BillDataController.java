/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataUpdateImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.action.CommitAction;
import com.jiuqi.va.bill.action.DeleteAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.MetaInfoService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataUpdateImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/data"})
public class BillDataController {
    private static final Logger log = LoggerFactory.getLogger(BillDataController.class);
    private static final int MAP_INITIAL = 16;
    @Autowired
    private BillDataService billDataService;
    @Autowired
    private BillCodeClient billCodeClient;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private DeleteAction deleteAction;
    @Autowired
    private CommitAction commitAction;
    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private EnumDataClient enumDataClient;

    @PostMapping(value={"get"})
    public R load(@RequestHeader HttpHeaders headers, @RequestBody BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("billCode", billCode);
                TenantDO tenant = new TenantDO();
                tenant.setExtInfo(extInfo);
                R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
                if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                    return R.error((String)r.get((Object)"msg").toString());
                }
                String value = r.get((Object)"value").toString();
                if (!StringUtils.hasText(value)) {
                    return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billclient.findfailedbybillcode"));
                }
                defineCode = value;
            }
            BillContextImpl contextImpl = new BillContextImpl();
            if (this.isFeignClient(headers)) {
                contextImpl.setDisableVerify(true);
            } else {
                contextImpl.setVerifyCode(params.getVerifyCode());
            }
            Map<String, List<Map<String, Object>>> data = this.billDataService.load(contextImpl, defineCode, billCode);
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"delete"})
    public R delete(@RequestHeader HttpHeaders headers, @RequestBody BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        try {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billCode);
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            BillDefine billDefine = this.billDataService.getBillDefineByCode(tenant);
            BillContextImpl billContextImpl = new BillContextImpl();
            if (this.isFeignClient(headers)) {
                billContextImpl.setDisableVerify(true);
            } else {
                billContextImpl.setVerifyCode(params.getVerifyCode());
            }
            BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, billDefine);
            model.loadByCode(billCode);
            ActionResponse actionResponse = new ActionResponse();
            model.executeAction((Action)this.deleteAction, new ActionRequest(), actionResponse);
            if (actionResponse.getCheckMessages() != null && actionResponse.getCheckMessages().size() > 0) {
                List collect = actionResponse.getCheckMessages().stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
        }
        catch (BillException e) {
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            return R.error((String)(BillCoreI18nUtil.getMessage("va.billcore.deletefailed_1") + e.getMessage()));
        }
        return R.ok();
    }

    @PostMapping(value={"add"})
    public R add(@RequestHeader HttpHeaders headers, @RequestBody BillDataDTO params) {
        if (!StringUtils.hasText(params.getDefineCode())) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"defineCode"}));
        }
        BillContextImpl contextImpl = new BillContextImpl();
        if (this.isFeignClient(headers)) {
            contextImpl.setDisableVerify(true);
        } else {
            contextImpl.setVerifyCode(params.getVerifyCode());
        }
        try {
            Map<String, List<Map<String, Object>>> data = this.billDataService.save(contextImpl, params.getDefineCode(), true, params.getBillData());
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"update"})
    public R update(@RequestHeader HttpHeaders headers, @RequestBody BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("billCode", billCode);
                TenantDO tenant = new TenantDO();
                tenant.setExtInfo(extInfo);
                R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
                if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                    return R.error((String)r.get((Object)"msg").toString());
                }
                String value = r.get((Object)"value").toString();
                if (!StringUtils.hasText(value)) {
                    return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billclient.findfailedbybillcode"));
                }
                defineCode = value;
            }
            BillContextImpl contextImpl = new BillContextImpl();
            if (this.isFeignClient(headers)) {
                contextImpl.setDisableVerify(true);
            } else {
                contextImpl.setVerifyCode(params.getVerifyCode());
            }
            Map<String, List<Map<String, Object>>> data = this.billDataService.save(contextImpl, defineCode, false, params.getBillData());
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/batch/delete"})
    public com.jiuqi.va.biz.utils.R<?> batchDelete(@RequestBody Map<String, Map<String, String>> billCodeMap) {
        HashMap result = new HashMap();
        billCodeMap.forEach((k, v) -> v.forEach((billCode, verifyCode) -> {
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setVerifyCode((String)verifyCode);
            try {
                this.billDataService.delete(contextImpl, (String)k, (String)billCode);
                result.put(billCode, com.jiuqi.va.biz.utils.R.ok((Object)BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillsuccess", new Object[]{billCode})));
                LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)k, (String)billCode, null);
            }
            catch (Exception e) {
                result.put(billCode, com.jiuqi.va.biz.utils.R.error((String)(BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillfailed", new Object[]{billCode}) + e.getMessage()), (Throwable)e));
                LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)k, (String)billCode, (String)String.format("\u5220\u9664\u5355\u636e%s\u5931\u8d25:%s", billCode, e.getMessage()));
            }
        }));
        return com.jiuqi.va.biz.utils.R.ok(result);
    }

    @Deprecated
    @PostMapping(value={"/{billType}/{billCode}/get"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<Map<String, Object>>>> load(@RequestParam(required=false, name="verifyCode") String verifyCode, @PathVariable(value="billType") String billType, @PathVariable(value="billCode") String billCode) {
        try {
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setVerifyCode(verifyCode);
            return com.jiuqi.va.biz.utils.R.ok(this.billDataService.load(contextImpl, billType, billCode));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @Deprecated
    @PostMapping(value={"/inc/{billType}/{billCode}/get"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<List<Object>>>> incLoad(@RequestParam(required=false, name="verifyCode") String verifyCode, @PathVariable(value="billType") String billType, @PathVariable(value="billCode") String billCode) {
        try {
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setVerifyCode(verifyCode);
            return com.jiuqi.va.biz.utils.R.ok(this.billDataService.load(contextImpl, billType, billCode, true));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @Deprecated
    @PostMapping(value={"/{billType}/post"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<Map<String, Object>>>> createSave(@PathVariable(value="billType") String billType, @RequestBody Map<String, List<Map<String, Object>>> billData) {
        try {
            return com.jiuqi.va.biz.utils.R.ok(this.billDataService.save(new BillContextImpl(), billType, true, billData));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @Deprecated
    @PostMapping(value={"/{billType}/{billCode}/put"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<Map<String, Object>>>> updateSave(@RequestParam(required=false, name="verifyCode") String verifyCode, @PathVariable(value="billType") String billType, @PathVariable(value="billCode") String billCode, @RequestBody Map<String, List<Map<String, Object>>> billData) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        try {
            return com.jiuqi.va.biz.utils.R.ok(this.billDataService.save(contextImpl, billType, false, billData));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @Deprecated
    @PostMapping(value={"/{billType}/{billCode}/patch"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<Map<String, Object>>>> update(@RequestParam(required=false, name="verifyCode") String verifyCode, @PathVariable(value="billType") String billType, @PathVariable(value="billCode") String billCode, @RequestBody Map<String, DataUpdateImpl> update) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        try {
            return com.jiuqi.va.biz.utils.R.ok(this.billDataService.update(contextImpl, billType, billCode, update));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @Deprecated
    @PostMapping(value={"/{billType}/{billCode}/delete"})
    public com.jiuqi.va.biz.utils.R<?> delete(@PathVariable(value="billType") String billType, @RequestParam(required=false, name="verifyCode") String verifyCode, @PathVariable(value="billCode") String billCode) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        try {
            this.billDataService.delete(contextImpl, billType, billCode);
            return com.jiuqi.va.biz.utils.R.ok();
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/billdefine"})
    public com.jiuqi.va.biz.utils.R<Map<String, BillDefine>> getBillDefineByCode(@RequestBody TenantDO param) {
        com.jiuqi.va.biz.utils.R r = null;
        try {
            HashMap<String, BillDefine> map = new HashMap<String, BillDefine>();
            r = com.jiuqi.va.biz.utils.R.ok(map);
            BillDefine billDefine = this.billDataService.getBillDefineByCode(param);
            map.put("billDefine", billDefine);
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @PostMapping(value={"/mastertableName"})
    public R getMasterTableName(@RequestBody BillDataDTO params) {
        Map<String, Object> tablesName;
        String defineCode = params.getDefineCode();
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(defineCode) && !StringUtils.hasText(billCode)) {
            return R.error((String)"\u5355\u636e\u7f16\u53f7\u548c\u5355\u636e\u5b9a\u4e49\u6807\u8bc6\u4e0d\u80fd\u90fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(defineCode)) {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billCode);
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
            if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                return R.error((String)r.get((Object)"msg").toString());
            }
            String value = r.get((Object)"value").toString();
            if (!StringUtils.hasText(value)) {
                return R.error((String)"\u6839\u636e\u5355\u636e\u7f16\u53f7\u67e5\u8be2\u5355\u636e\u5b9a\u4e49\u5931\u8d25");
            }
            defineCode = value;
        }
        if (CollectionUtils.isEmpty(tablesName = this.metaInfoService.getTablesName(defineCode))) {
            return R.error((String)"\u83b7\u53d6\u4e3b\u8868\u540d\u79f0\u5931\u8d25");
        }
        String tablename = tablesName.get("name").toString();
        return R.ok().put("data", (Object)tablename);
    }

    @PostMapping(value={"/tablesName"})
    public R getTablesName(@RequestBody BillDataDTO params) {
        Map<String, Object> tablesName;
        String defineCode = params.getDefineCode();
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(defineCode) && !StringUtils.hasText(billCode)) {
            return R.error((String)"\u5355\u636e\u7f16\u53f7\u548c\u5355\u636e\u5b9a\u4e49\u6807\u8bc6\u4e0d\u80fd\u90fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(defineCode)) {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billCode);
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
            if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                return R.error((String)r.get((Object)"msg").toString());
            }
            String value = r.get((Object)"value").toString();
            if (!StringUtils.hasText(value)) {
                return R.error((String)"\u6839\u636e\u5355\u636e\u7f16\u53f7\u67e5\u8be2\u5355\u636e\u5b9a\u4e49\u5931\u8d25");
            }
            defineCode = value;
        }
        if (CollectionUtils.isEmpty(tablesName = this.metaInfoService.getTablesName(defineCode))) {
            return R.error((String)"\u83b7\u53d6\u8868\u540d\u79f0\u5931\u8d25");
        }
        return R.ok(tablesName);
    }

    @PostMapping(value={"/masterTableNames"})
    public R getMasterTableNames(@RequestBody List<BillDataDTO> params) {
        if (CollectionUtils.isEmpty(params)) {
            return R.ok();
        }
        HashMap<String, String> result = new HashMap<String, String>();
        HashMap<String, String> errorResult = new HashMap<String, String>();
        for (BillDataDTO param : params) {
            Map<String, Object> tablesName;
            String defineCode = param.getDefineCode();
            String billCode = param.getBillCode();
            if (!StringUtils.hasText(defineCode) && !StringUtils.hasText(billCode)) continue;
            if (!StringUtils.hasText(defineCode)) {
                String value;
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("billCode", billCode);
                TenantDO tenant = new TenantDO();
                tenant.setExtInfo(extInfo);
                R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
                if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                    errorResult.put(billCode, r.get((Object)"msg").toString());
                }
                if (!StringUtils.hasText(value = r.get((Object)"value").toString())) {
                    errorResult.put(billCode, "\u6839\u636e\u5355\u636e\u7f16\u53f7\u67e5\u8be2\u5355\u636e\u5b9a\u4e49\u5931\u8d25");
                }
                defineCode = value;
            }
            if (CollectionUtils.isEmpty(tablesName = this.metaInfoService.getTablesName(defineCode))) {
                errorResult.put(defineCode, "\u83b7\u53d6\u8868\u540d\u79f0\u5931\u8d25");
                continue;
            }
            String tablename = tablesName.get("name").toString();
            result.put(defineCode, tablename);
        }
        return R.ok().put("data", result).put("errorData", errorResult);
    }

    private boolean isFeignClient(HttpHeaders headers) {
        List list = headers.get((Object)"feignclient");
        return list != null && list.size() > 0 && Boolean.valueOf((String)list.get(0)) != false;
    }

    @PostMapping(value={"/batch/commit"})
    public com.jiuqi.va.biz.utils.R<?> batchCommit(@RequestBody Map<String, List<String>> billCodeMap) {
        MDC.put("traceId", UUID.randomUUID().toString());
        ArrayList failedArray = new ArrayList();
        ArrayList successArray = new ArrayList();
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype("EM_BILLSTATE");
        enumDataDTO.setTenantName(ShiroUtil.getTenantName());
        enumDataDTO.addExtInfo("languageTransFlag", (Object)true);
        List billStateDatas = this.enumDataClient.list(enumDataDTO);
        EnumDataDTO bizEnumData = new EnumDataDTO();
        bizEnumData.setBiztype("EM_BIZSTATE");
        bizEnumData.setTenantName(ShiroUtil.getTenantName());
        bizEnumData.addExtInfo("languageTransFlag", (Object)true);
        List bizStateDatas = this.enumDataClient.list(bizEnumData);
        billCodeMap.forEach((billDefineCode, billCodeList) -> {
            for (String billCode : billCodeList) {
                HashMap<String, String> fail = new HashMap<String, String>(16);
                UserLoginDTO user = ShiroUtil.getUser();
                if (user != null && "super".equalsIgnoreCase(user.getMgrFlag())) {
                    fail.put("bizCode", billCode);
                    fail.put("msg", "\u7cfb\u7edf\u7ba1\u7406\u5458\u65e0\u64cd\u4f5c\u6743\u9650");
                    failedArray.add(fail);
                    continue;
                }
                BillContextImpl context = new BillContextImpl();
                context.setTenantName(Env.getTenantName());
                context.setDisableVerify(true);
                BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)context, (String)billDefineCode);
                try {
                    model.getRuler().getRulerExecutor().setEnable(true);
                    model.loadByCode(billCode);
                    if (!this.judgeState(model)) {
                        DataFieldDefine bizStateField;
                        String info = "";
                        int billState = model.getMaster().getInt("BILLSTATE");
                        Optional<EnumDataDO> billStateEnum = billStateDatas.stream().filter(BillState2 -> billState == Integer.parseInt(BillState2.getVal())).findFirst();
                        if (billStateEnum.isPresent()) {
                            info = "\uff1a" + billStateEnum.get().getTitle();
                        }
                        if ((bizStateField = (DataFieldDefine)model.getMasterTable().getDefine().getFields().find("BIZSTATE")) != null) {
                            int bizState = model.getMaster().getInt("BIZSTATE");
                            Optional<EnumDataDO> bizStateEnum = bizStateDatas.stream().filter(BillState2 -> bizState == Integer.parseInt(BillState2.getVal())).findFirst();
                            if (bizStateEnum.isPresent()) {
                                info = info + "\u3001" + bizStateEnum.get().getTitle();
                            }
                        }
                        BillException billException = new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdatacontroller.bizstatusdissatisfy", new Object[]{info}));
                        ArrayList<CheckResult> list = new ArrayList<CheckResult>();
                        CheckResultImpl cr = new CheckResultImpl();
                        cr.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.billdatacontroller.bizstatusdissatisfy", new Object[]{info}));
                        list.add((CheckResult)cr);
                        billException.setCheckMessages(list);
                        throw billException;
                    }
                    ActionRequest request = new ActionRequest();
                    HashMap<String, Boolean> param = new HashMap<String, Boolean>(16);
                    param.put("confirmedAction", true);
                    request.setParams(param);
                    ActionResponse response = new ActionResponse();
                    model.executeAction((Action)this.commitAction, request, response);
                    if (!response.isSuccess() && response.getCheckMessages() != null && !response.getCheckMessages().isEmpty()) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executefailed"), response.getCheckMessages());
                    }
                    HashMap<String, String> successInfo = new HashMap<String, String>(16);
                    successInfo.put("bizCode", billCode);
                    successArray.add(successInfo);
                }
                catch (BillException e) {
                    HashMap<String, String> failInfo = new HashMap<String, String>(16);
                    StringBuilder msg = new StringBuilder();
                    if (e.getCheckMessages() != null && !e.getCheckMessages().isEmpty()) {
                        for (CheckResult checkResult : e.getCheckMessages()) {
                            msg.append(checkResult.getCheckMessage());
                        }
                    }
                    if (!StringUtils.hasText(msg) && StringUtils.hasText(e.getMessage())) {
                        msg.append(e.getMessage());
                    }
                    failInfo.put("bizCode", billCode);
                    failInfo.put("msg", msg.toString());
                    failedArray.add(failInfo);
                }
                finally {
                    model.getRuler().getRulerExecutor().setEnable(false);
                }
            }
        });
        HashMap result = new HashMap(16);
        result.put("successArray", successArray);
        result.put("failedArray", failedArray);
        return com.jiuqi.va.biz.utils.R.ok(result);
    }

    private boolean judgeState(BillModelImpl model) {
        try {
            int billState = model.getMaster().getInt("BILLSTATE");
            DataFieldDefine bizStateField = (DataFieldDefine)model.getMasterTable().getDefine().getFields().find("BIZSTATE");
            if (bizStateField == null && (billState == BillState.SAVED.getValue() || billState == 33)) {
                return true;
            }
            int bizState = model.getMaster().getInt("BIZSTATE");
            if (billState == BillState.SAVED.getValue() && bizState == 0) {
                return true;
            }
            if (billState == BillState.AUDITPASSED.getValue() && (bizState == 44 || bizState == 59)) {
                return true;
            }
            if (billState == BillState.SENDBACK.getValue() && (bizState == 43 || bizState == 33 || bizState == 58)) {
                return true;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }
}

