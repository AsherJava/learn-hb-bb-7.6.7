/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.jiuqi.gcreport.invest.investbill.bill.task.InvestBillModuleInitiator;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillModuleInitiatorMain
implements ModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(InvestBillModuleInitiatorMain.class);
    @Autowired
    private List<InvestBillModuleInitiator> investBillModuleInitiators;

    public void init(ServletContext servletContext) throws Exception {
        if (null == this.investBillModuleInitiators) {
            return;
        }
        for (InvestBillModuleInitiator investBillModuleInitiator : this.investBillModuleInitiators) {
            try {
                investBillModuleInitiator.init(servletContext);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    public void initWhenStarted(ServletContext servletContext) throws Exception {
        if (null == this.investBillModuleInitiators) {
            return;
        }
        for (InvestBillModuleInitiator investBillModuleInitiator : this.investBillModuleInitiators) {
            try {
                investBillModuleInitiator.initWhenStarted(servletContext);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }
}

