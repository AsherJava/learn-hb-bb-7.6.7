/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.formulaschemeconfig.init;

import com.jiuqi.gcreport.formulaschemeconfig.init.service.FormulaSchemeConfigBillBblxInitService;
import com.jiuqi.gcreport.formulaschemeconfig.init.service.FormulaSchemeConfigBillFetchInitService;
import com.jiuqi.gcreport.formulaschemeconfig.init.service.FormulaSchemeConfigEntityIdInitService;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigInit
implements ModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeConfigInit.class);

    public void init(ServletContext servletContext) throws Exception {
    }

    public void initWhenStarted(ServletContext servletContext) throws Exception {
        try {
            ((FormulaSchemeConfigBillFetchInitService)ApplicationContextRegister.getBean(FormulaSchemeConfigBillFetchInitService.class)).doInit();
            ((FormulaSchemeConfigEntityIdInitService)ApplicationContextRegister.getBean(FormulaSchemeConfigEntityIdInitService.class)).doInit();
            ((FormulaSchemeConfigBillBblxInitService)ApplicationContextRegister.getBean(FormulaSchemeConfigBillBblxInitService.class)).doInit();
        }
        catch (Exception e) {
            this.logger.error("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u6267\u884c\u5f02\u5e38", e);
        }
    }
}

