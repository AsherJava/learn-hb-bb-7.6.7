/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.executor.INvwaAuthUpdate
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 *  com.jiuqi.nvwa.certification.service.INvwaCertifyService
 *  com.jiuqi.nvwa.sf.ServiceUrl
 *  com.jiuqi.nvwa.sf.adapter.spring.Response
 *  com.jiuqi.nvwa.sf.adapter.spring.rest.ServiceController
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.impl.adaptor.update.dao.BdeNvwaAuthUpdateDao;
import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.BdeNvwaAppUpdateDTO;
import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.BdeNvwaCertifyUpdateDTO;
import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.SfServiceDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.mappingscheme.impl.service.executor.INvwaAuthUpdate;
import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import com.jiuqi.nvwa.certification.service.INvwaCertifyService;
import com.jiuqi.nvwa.sf.ServiceUrl;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.rest.ServiceController;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BdeNvwaAuthUpdate
implements INvwaAuthUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BdeNvwaAuthUpdate.class);
    @Autowired
    private BdeNvwaAuthUpdateDao authUpdateDao;
    @Autowired
    private INvwaCertifyService certifyService;
    @Autowired
    private INvwaAppService appService;
    @Autowired
    private ServiceController serviceController;
    @Value(value="${jiuqi.np.user.system[0].name:}")
    private String username;

    public void nvwaAuthUpdate(String dataSourceCode) {
        try {
            this.doUpdate(dataSourceCode);
        }
        catch (Exception e) {
            logger.error("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", (Object)e.getMessage(), (Object)e);
        }
    }

    public void doUpdate(String dataSourceCode) {
        BdeNvwaCertifyUpdateDTO oldGcNvwaCertify;
        BdeNvwaCertifyUpdateDTO oldSysCertify2;
        boolean tableExists = TableParseUtils.tableExist((String)dataSourceCode, (List)CollectionUtils.newArrayList((Object[])new String[]{"NVWA_CERTIFY_SERVICE", "NVWA_APP_SERVICE"}));
        if (!tableExists) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5e95\u8868\u6821\u9a8c\u5931\u8d25\uff0c\u8df3\u8fc7\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        List<BdeNvwaCertifyUpdateDTO> oldSysCertifyList = this.authUpdateDao.listCertify(dataSourceCode);
        Map<String, BdeNvwaCertifyUpdateDTO> oldSysCertifyMap = this.authUpdateDao.listCertify(dataSourceCode).stream().collect(Collectors.toMap(BdeNvwaCertifyUpdateDTO::getId, item -> item));
        Map<String, NvwaCertify> existsCertifyCodeMap = this.certifyService.selectAll().stream().collect(Collectors.toMap(NvwaCertify::getCode, item -> item));
        for (BdeNvwaCertifyUpdateDTO oldSysCertify2 : oldSysCertifyList) {
            if (existsCertifyCodeMap.containsKey(oldSysCertify2.getCode())) {
                logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u8ba4\u8bc1\u670d\u52a1{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)oldSysCertify2.getCode(), (Object)existsCertifyCodeMap.get(oldSysCertify2.getCode()).getTitle());
                continue;
            }
            try {
                this.certifyService.saveOrUpdate((NvwaCertify)BeanConvertUtil.convert((Object)oldSysCertify2, NvwaCertify.class, (String[])new String[0]));
            }
            catch (Exception e) {
                logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u8ba4\u8bc1\u670d\u52a1{}{}\u4fdd\u5b58\u5931\u8d25\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)oldSysCertify2.getCode(), (Object)existsCertifyCodeMap.get(oldSysCertify2.getCode()).getTitle());
            }
        }
        List<BdeNvwaAppUpdateDTO> oldSysAppList = this.authUpdateDao.listApp(dataSourceCode);
        oldSysCertify2 = null;
        for (BdeNvwaAppUpdateDTO appUpdateDTO : oldSysAppList) {
            oldSysCertify2 = oldSysCertifyMap.get(appUpdateDTO.getCsId());
            if (oldSysCertify2 == null) {
                logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5e94\u7528\u670d\u52a1{}\u5bf9\u7528\u7684\u5386\u53f2\u7cfb\u7edf\u8ba4\u8bc1\u670d\u52a1{}\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)appUpdateDTO.getCode(), (Object)appUpdateDTO.getCsId());
                continue;
            }
            if (!existsCertifyCodeMap.containsKey(oldSysCertify2.getCode())) {
                logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5e94\u7528\u670d\u52a1{}\u5bf9\u7528\u7684\u8ba4\u8bc1\u670d\u52a1{}\u5728\u65b0\u7cfb\u7edf\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)appUpdateDTO.getCode(), (Object)oldSysCertify2.getCode());
                continue;
            }
            appUpdateDTO.setCsId(existsCertifyCodeMap.get(oldSysCertify2.getCode()).getId());
            Map<String, NvwaApp> existsNvwaApp = this.appService.selectByCsId(appUpdateDTO.getCsId()).stream().collect(Collectors.toMap(NvwaApp::getCode, item -> item));
            if (existsNvwaApp.containsKey(appUpdateDTO.getCode())) {
                logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5e94\u7528\u670d\u52a1{}\u5728\u8ba4\u8bc1\u670d\u52a1{}\u4e0b\u5df2\u7ecf\u5b58\u5728\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)appUpdateDTO.getCode(), (Object)oldSysCertify2.getCode());
                continue;
            }
            this.appService.saveOrUpdate((NvwaApp)BeanConvertUtil.convert((Object)appUpdateDTO, NvwaApp.class, (String[])new String[0]));
        }
        List currentList = this.appService.selectByCsId("CURENT_SERVICE_ID");
        if (CollectionUtils.isEmpty((Collection)currentList)) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5f53\u524d\u670d\u52a1\u4e0b\u4e0d\u5b58\u5728\u5e94\u7528\u670d\u52a1\uff0c\u81ea\u52a8\u8df3\u8fc7");
            return;
        }
        NvwaCertify clientCertity = this.certifyService.selectByCode("CLIENT");
        if (clientCertity != null) {
            NvwaApp bdeApp = null;
            for (NvwaApp nvwaApp : currentList) {
                if (!nvwaApp.getCode().equals("BDE")) continue;
                bdeApp = nvwaApp;
                this.initNewServiceCertify(currentList, clientCertity, bdeApp);
                return;
            }
        }
        if ((oldGcNvwaCertify = (BdeNvwaCertifyUpdateDTO)oldSysCertifyList.stream().filter(item -> "CURENT_SERVICE_ID".equals(item.getCode())).findFirst().orElse(null)) == null) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5386\u53f2\u670d\u52a1\u4e0b\u4e0d\u5b58\u5728\u3010\u5973\u5a32\u5f53\u524d\u670d\u52a1\u3011\uff0c\u81ea\u52a8\u8df3\u8fc7");
            return;
        }
        NvwaApp bdeApp = this.findNvwaApp(currentList, oldGcNvwaCertify.getCode());
        if (bdeApp == null) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5386\u53f2\u670d\u52a1\u7684\u3010\u5973\u5a32\u5f53\u524d\u670d\u52a1|{}\u3011\u5728\u5347\u7ea7\u540e\u7684\u8ba4\u8bc1\u670d\u52a1\u4e2d\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u5e94\u7528\u670d\u52a1\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)oldGcNvwaCertify.getCode());
            return;
        }
        NvwaCertify nvwaCertify = this.certifyService.selectAll().stream().filter(item -> "CURENT_SERVICE_ID".equals(item.getCode())).findFirst().orElse(null);
        if (nvwaCertify == null) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5f53\u524d\u670d\u52a1\u7684\u3010\u5973\u5a32\u5f53\u524d\u670d\u52a1\u6807\u8bc6\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)oldGcNvwaCertify.getCode());
            return;
        }
        BdeNvwaCertifyUpdateDTO oldNvwaCertify = oldSysCertifyList.stream().filter(item -> nvwaCertify.getCode().equals(item.getCode())).findFirst().orElse(null);
        if (oldNvwaCertify == null) {
            logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u5f53\u524d\u670d\u52a1\u7684\u3010\u670d\u52a1\u6807\u8bc6|{}\u3011\u5728\u5386\u53f2\u670d\u52a1\u4e2d\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u8ba4\u8bc1\u670d\u52a1\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)nvwaCertify.getCode());
        }
        NvwaCertify currNvwaCertify = (NvwaCertify)BeanConvertUtil.convert((Object)oldNvwaCertify, NvwaCertify.class, (String[])new String[0]);
        this.initNewServiceCertify(currentList, currNvwaCertify, bdeApp);
    }

    private void initNewServiceCertify(List<NvwaApp> currentList, NvwaCertify clientCertity, NvwaApp bdeApp) {
        BdeCommonUtil.initNpUser((String)this.username);
        Response response = this.serviceController.services(true);
        Map<String, SfServiceDTO> serviceMap = ((List)JsonUtils.readValue((String)((String)response.getData()), (TypeReference)new TypeReference<List<SfServiceDTO>>(){})).stream().collect(Collectors.toMap(SfServiceDTO::getServiceName, item -> item));
        if (serviceMap != null) {
            SfServiceDTO bdeService = serviceMap.get("bde") == null ? serviceMap.get("datacenter") : serviceMap.get("bde");
            SfServiceDTO gcService = null;
            if (serviceMap.get("gcreport-pro") != null) {
                gcService = serviceMap.get("gcreport-pro");
            } else if (serviceMap.get("gcreport-std") != null) {
                gcService = serviceMap.get("gcreport-std");
            } else if (serviceMap.size() == 2) {
                for (SfServiceDTO service : serviceMap.values()) {
                    if ("bde".equals(service.getServiceName()) || "datacenter".equals(service.getServiceName())) continue;
                    gcService = service;
                    break;
                }
            }
            if (gcService != null) {
                ServiceUrl gcServiceUrl = new ServiceUrl();
                gcServiceUrl.setFrontendURL(clientCertity.getFrontendURL());
                gcServiceUrl.setUrl(clientCertity.getUrl());
                if (this.findNvwaApp(currentList, gcService.getServiceName()) == null) {
                    this.serviceController.serciceUrl(gcServiceUrl, gcService.getServiceName());
                    logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u521d\u59cb\u68c0\u67e5\uff0c\u5408\u5e76\u5e94\u7528\u670d\u52a1\u6267\u884c\u5347\u7ea7", (Object)JsonUtils.writeValueAsString((Object)gcServiceUrl));
                } else {
                    logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u521d\u59cb\u68c0\u67e5\uff0c\u5408\u5e76\u5e94\u7528\u670d\u52a1\u5df2\u7ecf\u5b58\u5728\uff0c\u4e0d\u518d\u6267\u884c\u5347\u7ea7", (Object)JsonUtils.writeValueAsString((Object)gcServiceUrl));
                }
            }
            if (bdeService != null) {
                ServiceUrl bdeServiceUrl = new ServiceUrl();
                bdeServiceUrl.setFrontendURL(bdeApp.getFrontendURL());
                bdeServiceUrl.setUrl(bdeApp.getUrl());
                if (this.findNvwaApp(currentList, bdeService.getServiceName()) == null) {
                    this.serviceController.serciceUrl(bdeServiceUrl, bdeService.getServiceName());
                    logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u521d\u59cb\u68c0\u67e5\uff0cBDE\u5e94\u7528\u670d\u52a1\u6267\u884c\u5347\u7ea7", (Object)JsonUtils.writeValueAsString((Object)bdeServiceUrl));
                } else {
                    logger.info("\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7-\u521d\u59cb\u68c0\u67e5\uff0cBDE\u5e94\u7528\u670d\u52a1\u5df2\u7ecf\u5b58\u5728\uff0c\u4e0d\u518d\u6267\u884c\u5347\u7ea7", (Object)JsonUtils.writeValueAsString((Object)bdeServiceUrl));
                }
            }
        }
    }

    private NvwaApp findNvwaApp(List<NvwaApp> currentList, String appCode) {
        if (CollectionUtils.isEmpty(currentList)) {
            return null;
        }
        for (NvwaApp nvwaApp : currentList) {
            if (!nvwaApp.getCode().equalsIgnoreCase(appCode)) continue;
            return nvwaApp;
        }
        return null;
    }
}

