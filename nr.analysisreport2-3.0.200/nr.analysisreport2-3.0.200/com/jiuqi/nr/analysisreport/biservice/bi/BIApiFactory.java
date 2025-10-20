/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.ticket.service.TicketService
 */
package com.jiuqi.nr.analysisreport.biservice.bi;

import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BIApiFactory {
    @Autowired
    TicketService tokenServices;
    @Autowired
    BIIntegrationConfig biConfig;
    private static final Logger logger = LoggerFactory.getLogger(BIApiFactory.class);

    public String getItemUrl(String type, String key) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.getTicket("query-bidashboardtree");
        String url = address + "/api/ms/dashboard/chart/item" + "/" + key + ticket;
        if (type == "bireport") {
            url = address + "/api/ms/dashboard/report/item" + "/" + key + ticket;
        }
        return url;
    }

    public String getPageUrl(String type, String key) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.getTicket("query-bidashboardtree");
        String url = address + "/ms/dashboard/chart.jsp" + "/" + key + ticket;
        if (type == "bireport") {
            url = address + "/ms/dashboard/report.jsp" + "/" + key + ticket;
        }
        return url;
    }

    public String getTicket(String fromApiName) {
        try {
            return "?" + this.getTicketNoPreFix(fromApiName);
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }

    public String getToken(String fromApiName) {
        try {
            return "&" + this.getTicketNoPreFix(fromApiName);
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }

    public String getTicketNoPreFix(String fromApiName) {
        try {
            Ticket ticket = this.tokenServices.apply(this.biConfig.getCAIdentify(), null, null, null);
            return "ticket=" + ticket.getId() + "&" + "cs=" + this.biConfig.getCAIdentify() + "&" + "as=" + this.biConfig.getServerIdentify();
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }
}

