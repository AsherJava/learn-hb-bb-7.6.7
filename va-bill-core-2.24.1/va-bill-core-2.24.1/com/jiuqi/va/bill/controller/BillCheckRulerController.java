/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.ruler.impl.FormulaRulerItem
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/bill"}, produces={"text/html"})
public class BillCheckRulerController {
    @Autowired
    private ModelManager modelManager;
    private static final Logger logger = LoggerFactory.getLogger(BillCheckRulerController.class);

    @GetMapping(value={"/rulers/{tenantName}"})
    @ResponseBody
    public String getRulers(@PathVariable(value="tenantName", required=true) String tenantName) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
        StringBuffer sbHtml = this.handleRulers();
        return sbHtml.toString();
    }

    @GetMapping(value={"/rulers"})
    @ResponseBody
    public String getRulers() {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)"__default_tenant__");
        StringBuffer sbHtml = this.handleRulers();
        return sbHtml.toString();
    }

    private StringBuffer handleRulers() {
        List modelTypes = this.modelManager.stream().collect(Collectors.toList());
        HashSet rulerIds = new HashSet();
        ArrayList rulers = new ArrayList();
        for (ModelType modelType : modelTypes) {
            try {
                if (!modelType.getModelDefineClass().isAssignableFrom(BillDefineImpl.class)) continue;
                BillDefineImpl billDefine = (BillDefineImpl)modelType.getModelDefineClass().newInstance();
                modelType.initModelDefine((ModelDefine)billDefine, modelType.getName());
                RulerDefineImpl ruler = billDefine.getRuler();
                List itemList = ruler.getItemList();
                itemList.forEach(o -> {
                    if (o instanceof FormulaRulerItem) {
                        return;
                    }
                    if (rulerIds.add(o.getName()) && o.getTriggerTypes().size() > 1) {
                        rulers.add(o);
                    }
                });
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sbHtml.append("<title>\u89c4\u5219</title></head><body>");
        sbHtml.append("<table border=\"1\">");
        sbHtml.append("<tr><th>\u7c7b\u8def\u5f84</th><th>\u89c4\u5219\u540d\u79f0</th><th>\u89e6\u53d1\u65f6\u673a</th></tr>");
        rulers.forEach(o -> sbHtml.append("<tr><td>" + o.getClass().getName() + "</td><td>" + o.getTitle() + "</td><td>" + StringUtils.arrayToDelimitedString(o.getTriggerTypes().toArray(), ", ") + "</td></tr>"));
        sbHtml.append("</table>");
        sbHtml.append("</body></html>");
        return sbHtml;
    }
}

