/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncParamDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncUtils
 *  com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bizmeta.service.join;

import com.jiuqi.va.bizmeta.service.IMetaParamSyncService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncUtils;
import com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component(value="VaParamSync#metadata")
public class VaParamSyncClientImpl
implements VaParamSyncFeignClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaParamSyncClientImpl.class);
    private IMetaParamSyncService paramSyncService = null;

    private IMetaParamSyncService getService() {
        if (this.paramSyncService == null) {
            this.paramSyncService = (IMetaParamSyncService)ApplicationContextRegister.getBean(IMetaParamSyncService.class);
        }
        return this.paramSyncService;
    }

    public VaParamSyncResponseDO export(VaParamSyncParamDO param) {
        try {
            return this.getService().export(param);
        }
        catch (Exception e) {
            LOGGER.error("\u5bfc\u51fa\u5931\u8d25: ", e);
            return null;
        }
    }

    public R getImportGroups(VaParamSyncMainfestDO params, String metaType) {
        try {
            List<VaParamSyncMetaGroupDO> result = this.getService().getImportGroups(metaType, params);
            return R.ok(Stream.of(result).collect(Collectors.toMap(o -> "groups", o -> result)));
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u5bfc\u5165\u5206\u7ec4\u5931\u8d25: ", e);
            return R.error((String)e.getMessage());
        }
    }

    public R importParam(MultipartFile multipartFile, String params) {
        try {
            VaParamSyncParamDO paramDO = (VaParamSyncParamDO)JSONUtil.parseObject((String)params, VaParamSyncParamDO.class);
            Map fileMap = VaParamSyncUtils.uncompress((InputStream)multipartFile.getInputStream());
            if (fileMap.size() == 0) {
                return R.error((String)"\u89e3\u538b\u5931\u8d25\uff0c\u6587\u4ef6\u4e0d\u5b58\u5728");
            }
            return this.getService().importParam(paramDO, fileMap);
        }
        catch (IOException e) {
            LOGGER.error("\u5bfc\u5165\u5931\u8d25: ", e);
            return R.error((String)e.getMessage());
        }
    }
}

