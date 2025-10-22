/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.component.NvwaCurCertifyTicketCache
 *  com.jiuqi.nvwa.certification.exception.CertifyRuntimeException
 *  com.jiuqi.nvwa.certification.extend.INvwaAppExtendService
 *  com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService
 *  com.jiuqi.nvwa.certification.manage.impl.NvwaAppRequestManageImpl
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 *  com.jiuqi.nvwa.certification.service.INvwaCertifyService
 *  com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTicketDTO
 *  com.jiuqi.nvwa.ticket.service.TicketService
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.component.NvwaCurCertifyTicketCache;
import com.jiuqi.nvwa.certification.exception.CertifyRuntimeException;
import com.jiuqi.nvwa.certification.extend.INvwaAppExtendService;
import com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService;
import com.jiuqi.nvwa.certification.manage.impl.NvwaAppRequestManageImpl;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import com.jiuqi.nvwa.certification.service.INvwaCertifyService;
import com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTicketDTO;
import com.jiuqi.nvwa.ticket.service.TicketService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Deprecated
public class BdeNvwaAppRequestManageImpl
extends NvwaAppRequestManageImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeNvwaAppRequestManageImpl.class);
    @Autowired
    private INvwaAppService nvwaAppService;
    @Autowired
    private INvwaCertifyService nvwaCertifyService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private NvwaCurCertifyTicketCache nvwaCurCertifyTicketCache;
    private List<INvwaCertifyExtendService> nvwaCertifyExtendServices;
    private Map<String, INvwaCertifyExtendService> nvwaCertifyExtendServicesMap = new HashMap<String, INvwaCertifyExtendService>();
    private List<INvwaAppExtendService> nvwaAppExtendServices;
    private Map<String, INvwaAppExtendService> nvwaAppExtendServicesMap = new HashMap<String, INvwaAppExtendService>();

    public BdeNvwaAppRequestManageImpl(@Autowired List<INvwaCertifyExtendService> nvwaCertifyExtendServices, @Autowired List<INvwaAppExtendService> nvwaAppExtendServices) {
        super(nvwaCertifyExtendServices, nvwaAppExtendServices);
        nvwaAppExtendServices.sort(Comparator.comparingInt(INvwaAppExtendService::getOrder));
        nvwaCertifyExtendServices.sort(Comparator.comparingInt(INvwaCertifyExtendService::getOrder));
        this.nvwaCertifyExtendServices = nvwaCertifyExtendServices;
        this.nvwaAppExtendServices = nvwaAppExtendServices;
        for (INvwaCertifyExtendService nvwaCertifyExtendService : this.nvwaCertifyExtendServices) {
            this.nvwaCertifyExtendServicesMap.put(nvwaCertifyExtendService.getType(), nvwaCertifyExtendService);
        }
        for (INvwaAppExtendService nvwaAppExtendService : this.nvwaAppExtendServices) {
            this.nvwaAppExtendServicesMap.put(nvwaAppExtendService.getType(), nvwaAppExtendService);
        }
    }

    public NvwaTicketDTO applyTicket(String appId) {
        NvwaApp nvwaApp = null;
        if (StringUtils.hasLength(appId)) {
            nvwaApp = this.nvwaAppService.selectById(appId);
        }
        if (null == nvwaApp) {
            nvwaApp = this.nvwaAppService.selectByCode(appId);
        }
        if (null == nvwaApp) {
            throw new CertifyRuntimeException("appId or appCode :" + appId + ";\u7533\u8bf7\u7968\u636e\u8fc7\u7a0b\u4e2d\u65e0\u6cd5\u627e\u7684\u5bf9\u5e94\u7684\u5e94\u7528\u670d\u52a1!");
        }
        if (!"BDE".equals(nvwaApp.getCode())) {
            return super.applyTicket(appId);
        }
        NvwaCertify nvwaCertify = this.nvwaCertifyService.selectById(nvwaApp.getCsId());
        INvwaCertifyExtendService nvwaCertifyExtendService = this.nvwaCertifyExtendServicesMap.get(nvwaCertify.getType());
        NvwaTicketDTO nvwaApplyTicketDTO = null;
        try {
            boolean openApply = nvwaCertifyExtendService.openApply();
            if (!openApply) {
                throw new CertifyRuntimeException("\u8ba4\u8bc1\u670d\u52a1\uff1a" + nvwaCertify.getTitle() + ",\u7c7b\u578b\uff1a" + nvwaCertifyExtendService.getType() + ":\u672a\u652f\u6301\u7533\u8bf7\u7968\u636e!\u53ea\u652f\u6301\u4ece\u8ba4\u8bc1\u4e2d\u5fc3\u9a8c\u8bc1\u7968\u636e!");
            }
            nvwaApplyTicketDTO = nvwaCertifyExtendService.applyTicket(nvwaCertify, nvwaApp, "admin");
        }
        catch (Exception e) {
            LOGGER.error(nvwaCertifyExtendService.getType() + "\u4f5c\u4e3a\u8ba4\u8bc1\u4e2d\u5fc3\u9881\u53d1ticket\u51fa\u73b0\u9519\u8bef!", e);
        }
        if (nvwaApplyTicketDTO == null) {
            throw new CertifyRuntimeException("\u8ba4\u8bc1\u4e2d\u5fc3\u9881\u53d1ticket\u51fa\u73b0\u9519\u8bef!");
        }
        LOGGER.debug("\u8ba4\u8bc1\u4e2d\u5fc3\u9881\u53d1ticket:\u7c7b\u578b:" + nvwaCertifyExtendService.getType() + " : " + nvwaCertifyExtendService.getTitle() + " : ticketId: " + nvwaApplyTicketDTO.getId() + " : userName: " + nvwaApplyTicketDTO.getUser().getName());
        return nvwaApplyTicketDTO;
    }
}

