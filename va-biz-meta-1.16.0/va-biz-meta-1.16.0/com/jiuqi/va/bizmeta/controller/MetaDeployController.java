/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployVO
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.domain.metadeploy.MetaDataDeployDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.service.IMetaDeployService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployVO;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping(value={"/biz/meta/publish"})
public class MetaDeployController {
    private static final Logger logger = LoggerFactory.getLogger(MetaDeployController.class);
    @Autowired
    private IMetaDeployService metaDeployService;
    @Autowired
    private MetaDataUpdateService metaDataUpdateService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping(value={"/get"})
    public MetaDataDeployVO getAllDeployData(@RequestBody MetaModelDTO infoDTO) {
        MetaDataDeployVO dataDeployVO = new MetaDataDeployVO();
        if (this.metaDataUpdateService.isControllerUpdating() || this.metaDataUpdateService.isUpdating()) {
            dataDeployVO.setFlag(Boolean.valueOf(false));
            dataDeployVO.setMessage("\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            return dataDeployVO;
        }
        if ("all".equals(infoDTO.getMetaType())) {
            infoDTO.setMetaType(null);
        }
        MetaDataDeployDTO dataDeployDTO = this.metaDeployService.getDeployDatas(ShiroUtil.getUser().getId().toString(), infoDTO);
        dataDeployVO.setDeployDatas(dataDeployDTO.getDeployDatas());
        return dataDeployVO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/dep"})
    public MetaDataDeployVO publishMetaData(@RequestBody MetaDataDeployDTO dataDeployDTO) {
        MetaDataDeployVO deployVO = new MetaDataDeployVO();
        String uuid = "000000000000";
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
            RequestContextUtil.setAttribute((String)"PUB_LOG_UUID", (Object)uuid);
        }
        String pathStr = dataDeployDTO.getDeployDatas().stream().map(MetaDataDeployDim::getPath).collect(Collectors.joining("\n"));
        logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u53c2\u6570({}\u6761\u6570\u636e)\uff1a\n{}", uuid, dataDeployDTO.getDeployDatas().size(), pathStr);
        try {
            logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u5f00\u59cb", (Object)uuid);
            this.redisTemplate.opsForValue().set((Object)"META_DATA_DEPLOY_CACHE_KEY", (Object)"1", 30L, TimeUnit.SECONDS);
            if (this.metaDataUpdateService.isControllerUpdating() || this.metaDataUpdateService.isUpdating()) {
                deployVO.setFlag(Boolean.valueOf(false));
                deployVO.setMessage("\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
                logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5", (Object)uuid);
                MetaDataDeployVO metaDataDeployVO = deployVO;
                return metaDataDeployVO;
            }
            MetaDataDeployDTO deployDTO = this.metaDeployService.publishMetaData(ShiroUtil.getUser().getId().toString(), dataDeployDTO);
            deployVO.setSuccessData(deployDTO.getSuccessData());
            deployVO.setFailedData(deployDTO.getFailedData());
            deployVO.setFlag(Boolean.valueOf(true));
        }
        catch (Exception e) {
            deployVO.setFlag(Boolean.valueOf(false));
            deployVO.setMessage("\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
            logger.error("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u51fa\u73b0\u5f02\u5e38\uff1a{}", uuid, e.getMessage(), e);
        }
        finally {
            this.redisTemplate.delete((Object)"META_DATA_DEPLOY_CACHE_KEY");
            logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u7ed3\u675f", (Object)uuid);
        }
        return deployVO;
    }

    @PostMapping(value={"/depByUniqueCode"})
    public MetaDataDeployVO publishMetaDataByUniqueCode(@RequestBody MetaInfoDTO param) {
        MetaDataDeployVO dataDeployVO = new MetaDataDeployVO();
        String uniqueCode = param.getUniqueCode();
        if (!StringUtils.hasText(uniqueCode)) {
            dataDeployVO.setFlag(Boolean.valueOf(false));
            dataDeployVO.setMessage("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570@uniqueCode");
            return dataDeployVO;
        }
        if (this.metaDataUpdateService.isControllerUpdating() || this.metaDataUpdateService.isUpdating()) {
            dataDeployVO.setFlag(Boolean.valueOf(false));
            dataDeployVO.setMessage("\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            return dataDeployVO;
        }
        MetaDataDeployDTO todoDeployData = this.metaDeployService.getDeployDataByUniqueCode(param.getUniqueCode());
        if (todoDeployData == null || CollectionUtils.isEmpty(todoDeployData.getDeployDatas())) {
            dataDeployVO.setFlag(Boolean.valueOf(false));
            dataDeployVO.setMessage("\u5f85\u53d1\u5e03\u6570\u636e\u4e3a\u7a7a");
            return dataDeployVO;
        }
        return this.publishMetaData(todoDeployData);
    }
}

