/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.biz.utils.Utils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillEditService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.biz.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping(value={"/bill/edit"})
public class BillEditController {
    @Autowired
    private BillEditService billEditService;

    @PostMapping(value={"/{defineCode}/post"})
    public R<Map<String, Object>> create(@PathVariable(value="defineCode") String defineCode, @RequestParam(required=false, name="defineVer", defaultValue="0") long defineVer, @RequestParam(required=false, name="viewName") String viewName, @RequestParam(required=false, name="verifyCode") String verifyCode, @RequestParam(required=false, name="triggerOrigin") String triggerOrigin, @RequestBody(required=false) Map<String, Object> initValues) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        contextImpl.setTriggerOrigin(triggerOrigin);
        try {
            return R.ok(this.billEditService.add((BillContext)contextImpl, defineCode, defineVer, viewName, null, initValues));
        }
        catch (BillException e) {
            Boolean closeTab;
            Map<String, List<Map<String, Object>>> tablesData;
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
            if (checkMessages != null) {
                return new R(501, e.getMessage(), null, map);
            }
            return new R((Throwable)e, 501, map);
        }
        catch (Exception e) {
            HashMap map = new HashMap();
            return new R(501, e.getMessage(), null, map);
        }
    }

    @PostMapping(value={"/{defineCode}/{dataId}/post"})
    public R<Map<String, Object>> create(@PathVariable(value="defineCode") String defineCode, @PathVariable(value="dataId") String dataId, @RequestParam(required=false, name="defineVer", defaultValue="0") long defineVer, @RequestParam(required=false, name="viewName") String viewName, @RequestParam(required=false, name="verifyCode") String verifyCode, @RequestParam(required=false, name="triggerOrigin") String triggerOrigin) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        contextImpl.setTriggerOrigin(triggerOrigin);
        try {
            return R.ok(this.billEditService.edit(contextImpl, defineCode, defineVer, dataId, viewName));
        }
        catch (Exception e) {
            return R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/{defineCode}/{dataId}/get"})
    public R<Map<String, Object>> load(@PathVariable(value="defineCode") String defineCode, @PathVariable(value="dataId") String dataId, @RequestParam(required=false, name="defineVer", defaultValue="0") long defineVer, @RequestParam(required=false, name="viewName") String viewName, @RequestParam(required=false, name="verifyCode") String verifyCode, @RequestParam(required=false, name="triggerOrigin") String triggerOrigin) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        contextImpl.setTriggerOrigin(triggerOrigin);
        try {
            return R.ok(this.billEditService.load(contextImpl, defineCode, defineVer, dataId, viewName));
        }
        catch (Exception e) {
            return R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/{defineCode}/get"})
    public R<Map<String, Object>> loadDefine(@PathVariable(value="defineCode") String defineCode, @RequestParam(required=false, name="defineVer", defaultValue="0") long defineVer, @RequestParam(required=false, name="viewName") String viewName, @RequestParam(required=false, name="verifyCode") String verifyCode, @RequestParam(required=false, name="triggerOrigin") String triggerOrigin) {
        return this.load(defineCode, null, defineVer, viewName, verifyCode, triggerOrigin);
    }

    @PostMapping(value={"/{defineCode}/{dataId}/patch"})
    public R<Map<String, Object>> sync(@PathVariable(value="defineCode") String defineCode, @PathVariable(value="dataId") String dataId, @RequestParam(required=false, name="defineVer", defaultValue="0") long defineVer, @RequestParam(required=false, name="viewName") String viewName, @RequestParam(required=false, name="verifyCode") String verifyCode, @RequestParam(required=false, name="triggerOrigin") String triggerOrigin, @RequestBody Map<String, Object> action) {
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(verifyCode);
        contextImpl.setTriggerOrigin(Utils.isEmpty((String)triggerOrigin) ? (String)action.get("triggerOrigin") : triggerOrigin);
        try {
            return R.ok(this.billEditService.sync(contextImpl, defineCode, defineVer, dataId, viewName, action, null));
        }
        catch (BillException e) {
            Boolean closeTab;
            Map<String, List<Map<String, Object>>> tablesData;
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
            if (checkMessages != null) {
                return new R(501, e.getMessage(), null, map);
            }
            return new R((Throwable)e, 501, map);
        }
        catch (Exception e) {
            return R.error((Throwable)e);
        }
    }
}

