/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.feign.context.BusinessFeignHeadersContext
 *  com.jiuqi.common.feign.context.BusinessFeignHeadersContextHolder
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.common.datasync.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.discovery.CommonDataSyncDiscovery;
import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorGather;
import com.jiuqi.common.datasync.feign.CommonDataSyncNvwaFeignClient;
import com.jiuqi.common.datasync.message.CommonDataSyncMessage;
import com.jiuqi.common.datasync.producer.CommonDataSyncMessageProducer;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.common.feign.context.BusinessFeignHeadersContext;
import com.jiuqi.common.feign.context.BusinessFeignHeadersContextHolder;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CommonDataSyncServiceImpl
implements CommonDataSyncService {
    @Autowired
    private CommonDataSyncNvwaFeignClient nvwaFeignClient;
    @Autowired
    private CommonDataSyncExecutorGather dataSyncRunnerGather;
    @Autowired
    private CommonDataSyncDiscovery dataSyncDiscovery;

    @Override
    public void dataSync(CommonDataSyncMessage dataSyncMessage) {
        CommonDataSyncMessageProducer dataSyncMessageProducer = (CommonDataSyncMessageProducer)SpringContextUtils.getBean(CommonDataSyncMessageProducer.class);
        dataSyncMessageProducer.publishDataSyncQueueMessage(dataSyncMessage);
    }

    @Override
    public CommonDataSyncNvwaFeignClient getNvwaFeignClient() {
        return this.nvwaFeignClient;
    }

    @Override
    public NvwaLoginUserDTO initNvwaFeignClientTokenEnv(String baseUrl, String username, String password) throws URISyntaxException {
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        NpContext context = NpContextHolder.getContext();
        userDTO.setUsername(username);
        userDTO.setPwd(password);
        userDTO.setEncrypted(false);
        userDTO.setCheckPwd(false);
        userDTO.setTenant(context.getTenant());
        R responseEntity = this.getNvwaFeignClient().nvwaLogin(new URI(baseUrl), userDTO);
        String token = ConverterUtils.getAsString((Object)responseEntity.get((Object)"token"), null);
        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessRuntimeException("\u6570\u636e\u540c\u6b65\u83b7\u53d6\u4ee4\u724c\u5931\u8d25\u3002");
        }
        BusinessFeignHeadersContext feignHeadersContext = new BusinessFeignHeadersContext();
        feignHeadersContext.putHeader("Authorization", new String[]{token});
        BusinessFeignHeadersContextHolder.setFeignHeadersContext((BusinessFeignHeadersContext)feignHeadersContext);
        return userDTO;
    }

    @Override
    public List<CommonDataSyncExecutorDTO> getDataSyncExecutorDTOs() {
        List<CommonDataSyncExecutorDTO> dataSyncExecutorDTOs = this.dataSyncRunnerGather.getDataSyncExecutors().stream().map(e -> {
            CommonDataSyncExecutorDTO dataSyncExecutorDTO = new CommonDataSyncExecutorDTO();
            dataSyncExecutorDTO.setType(e.type());
            dataSyncExecutorDTO.setTitle(e.title());
            dataSyncExecutorDTO.setDescription(e.description());
            return dataSyncExecutorDTO;
        }).collect(Collectors.toList());
        return dataSyncExecutorDTOs;
    }

    @Override
    public Map<String, List<CommonDataSyncExecutorDTO>> getDiscoveryDataSyncExecutors() {
        Map<String, List<CommonDataSyncExecutorDTO>> discoveryDataSyncExecutorDTOsMap = this.dataSyncDiscovery.getDiscoveryDataSyncExecutorDTOsMap();
        return discoveryDataSyncExecutorDTOsMap;
    }

    @Override
    public CommonDataSyncExecutor findDataSyncExecutorByType(String type) {
        Optional<CommonDataSyncExecutor> dataSyncExecutorOptional = this.dataSyncRunnerGather.getDataSyncExecutors().stream().filter(commonDataSyncExecutor -> commonDataSyncExecutor.type().equals(type)).findAny();
        if (!dataSyncExecutorOptional.isPresent()) {
            return null;
        }
        return dataSyncExecutorOptional.get();
    }
}

