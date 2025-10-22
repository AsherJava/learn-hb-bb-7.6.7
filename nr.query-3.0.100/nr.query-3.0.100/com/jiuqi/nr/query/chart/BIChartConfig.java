/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.ticket.service.TicketService
 */
package com.jiuqi.nr.query.chart;

import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class BIChartConfig {
    private static final Logger logger = LoggerFactory.getLogger(BIChartConfig.class);
    @Autowired
    TicketService tokenServices;
    @Autowired
    private DataSetSystemOptionController dataset;
    @Autowired
    BIIntegrationConfig biConfig;
    private String tokenUrl;
    private String ssoUrl;
    private String appId;
    private String appKey;
    private String appSecret;
    private String datasettype;
    private String dashboard;

    public String getTokenUrl() {
        return this.tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getSSOUrl() {
        return this.ssoUrl;
    }

    public void setSSOUrl(String url) {
        this.ssoUrl = url;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appid) {
        this.appId = appid;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appkey) {
        this.appKey = appkey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getDatasettype() {
        return this.dataset.getServiceName();
    }

    public void setDatasettype(String datasettype) {
        this.datasettype = datasettype;
    }

    public String getDashboard() {
        return this.biConfig.getBiAddress();
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public String getTicket(String fromApiName, String cs) {
        try {
            Ticket ticket = this.tokenServices.apply(cs, null, null, null);
            return ticket.getId();
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }
}

