/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.glue.client.basedata.datainto.IBaseDataProductService
 *  com.jiuqi.nvwa.glue.client.basedata.datainto.domain.VaDaPushResult
 *  com.jiuqi.nvwa.glue.client.common.SyncContext
 *  com.jiuqi.nvwa.glue.data.impl.GlueBaseDataDTO
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.executor.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.converter.NvwaBaseDataConverter;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaBaseDataDTO;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.nvwa.glue.client.basedata.datainto.IBaseDataProductService;
import com.jiuqi.nvwa.glue.client.basedata.datainto.domain.VaDaPushResult;
import com.jiuqi.nvwa.glue.client.common.SyncContext;
import com.jiuqi.nvwa.glue.data.impl.GlueBaseDataDTO;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class NvwaBaseDataDataSyncExecutor
implements CommonDataSyncExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(NvwaBaseDataDataSyncExecutor.class);
    @Autowired
    private IBaseDataProductService productService;
    @Autowired
    private CommonDataSyncService dataSyncService;

    @Override
    public String title() {
        return "\u5973\u5a32\u5e73\u53f0\u57fa\u7840\u6570\u636e\u540c\u6b65";
    }

    @Override
    public String type() {
        return "NvwaBaseData";
    }

    @Override
    public String description() {
        return "<p>\u63a5\u53e3\u53c2\u6570\u793a\u4f8b\u63cf\u8ff0: \u5408\u5e76\u62a5\u8868(\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1agcreport-servcie)\u62bd\u53d6\u4e00\u672c\u8d26\u670d\u52a1\uff08\u88ab\u8c03\u7528\u65b9\u670d\u52a1url\uff1ahttp://10.2.41.67:9730\uff09\u7684MD_ACCTSUBJECT\u57fa\u7840\u6570\u636e</p><p>\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1agcreport-service</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u5730\u5740\uff1ahttp://10.2.41.67:9730</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u7528\u6237\uff1aadmin</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u5bc6\u7801\uff1adcP@ssw0rd</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\uff1aMD_ACCOUNTSUBJECT=MD_ACCTSUBJECT,MD_CURRENCY,MD_TB=MD_TEST_TB</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\u63cf\u8ff0\uff1a\u683c\u5f0f\u4e3a\u8c03\u7528\u65b9\u670d\u52a1\u57fa\u7840\u6570\u636e\u6807\u8bc6=\u88ab\u8c03\u7528\u65b9\u7684\u670d\u52a1\u57fa\u7840\u6570\u636e\u6807\u8bc6, \u672c\u793a\u4f8b\u4e3a\u5408\u5e76\u670d\u52a1\u62bd\u53d6\u4e00\u672c\u8d26\u7684MD_ACCTSUBJECT\u3001MD_CURRENCY\u3001MD_TEST_TB\u57fa\u7840\u6570\u636e\u9879\u5206\u522b\u5bf9\u5e94\u5b58\u50a8\u5230\u5408\u5e76\u670d\u52a1MD_ACCOUNTSUBJECT\u3001MD_CURRENCY\u3001MD_TB\u57fa\u7840\u6570\u636e\u4e2d\u3002</p>";
    }

    @Override
    public void execute(CommonDataSyncExecutorContext context) {
        CommonDataSyncSettingItemDTO itemDTO = context.getDataSyncSettingItemDTO();
        String param = ConverterUtils.getAsString((Object)itemDTO.getParam());
        if (ObjectUtils.isEmpty(param)) {
            return;
        }
        List<String> basedataTableNameStrs = Arrays.stream(param.split(",")).collect(Collectors.toList());
        this.dataSync(itemDTO, basedataTableNameStrs);
    }

    @Transactional(rollbackFor={Exception.class})
    public void dataSync(CommonDataSyncSettingItemDTO itemDTO, List<String> basedataTableNameStrs) {
        if (CollectionUtils.isEmpty(basedataTableNameStrs)) {
            return;
        }
        HashMap<String, String> basedataTableNameMap = new HashMap<String, String>();
        basedataTableNameStrs.stream().forEach(basedataTableNameStr -> {
            if (basedataTableNameStr.indexOf("=") != -1) {
                String[] split = basedataTableNameStr.split("=");
                basedataTableNameMap.put(split[0], split[1]);
            } else {
                basedataTableNameMap.put((String)basedataTableNameStr, (String)basedataTableNameStr);
            }
        });
        basedataTableNameMap.forEach((targetBaseDataTableName, srcBaseDataTableName) -> this.dataSync(itemDTO, (String)targetBaseDataTableName, (String)srcBaseDataTableName));
    }

    @Transactional(rollbackFor={Exception.class})
    public void dataSync(CommonDataSyncSettingItemDTO itemDTO, String targetBaseDataTableName, String srcBaseDataTableName) {
        try {
            this.LOGGER.debug("\u5f00\u59cb\u62bd\u53d6\u7c7b\u578b\u4e3a\u3010" + targetBaseDataTableName + "\u3011\u57fa\u7840\u6570\u636e\uff0c\u6765\u6e90\u7c7b\u578b\u4e3a\u3010" + srcBaseDataTableName + "\u3011\u3002");
            VaDaPushResult result = new VaDaPushResult(new SyncContext(), targetBaseDataTableName);
            List<DataSyncNvwaBaseDataDTO> nvwaBaseDataDTOs = this.fetchDataSyncDatas(itemDTO, srcBaseDataTableName);
            this.saveDataSyncDatas(result, nvwaBaseDataDTOs);
            this.LOGGER.debug("\u3010" + targetBaseDataTableName + "\u3011\u57fa\u7840\u6570\u636e\u62bd\u53d6\u5b8c\u6210\uff0c\u6765\u6e90\u7c7b\u578b\u4e3a\u3010" + srcBaseDataTableName + "\u3011\u3002\u5177\u4f53\u88c5\u5165\u7ed3\u679c\u4e3a\uff1a" + result.toString());
        }
        catch (Exception e) {
            this.LOGGER.error("\u62bd\u53d6\u7c7b\u578b\u4e3a\u3010" + targetBaseDataTableName + "\u3011\u57fa\u7840\u6570\u636e\u5f02\u5e38\u7ed3\u675f\uff0c\u6765\u6e90\u7c7b\u578b\u4e3a\u3010" + srcBaseDataTableName + "\u3011\u3002 \u9519\u8bef\u4fe1\u606f\u4e3a" + e.getMessage() + "\u3002");
        }
    }

    public List<DataSyncNvwaBaseDataDTO> fetchDataSyncDatas(CommonDataSyncSettingItemDTO itemDTO, String srcBaseDataTableName) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return null;
        }
        if (ObjectUtils.isEmpty(srcBaseDataTableName)) {
            throw new IllegalArgumentException("\u57fa\u7840\u6570\u636e\u540c\u6b65\u4efb\u52a1\u7684\u6765\u6e90\u57fa\u7840\u6570\u636e\u8868\u540d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        List nvwaBaseDataDTOs = Collections.emptyList();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaBaseDataDTO>> nvwaBaseDataDTOsResponseEntity = this.dataSyncService.getNvwaFeignClient().getNvwaBaseDataDTOs(new URI(itemDTO.getUrl()), srcBaseDataTableName, userDTO);
            if (nvwaBaseDataDTOsResponseEntity.getData() != null) {
                nvwaBaseDataDTOs = (List)nvwaBaseDataDTOsResponseEntity.getData();
            }
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        return nvwaBaseDataDTOs;
    }

    private void saveDataSyncDatas(VaDaPushResult result, List<DataSyncNvwaBaseDataDTO> nvwaBaseDataDTOs) {
        NvwaBaseDataDataSyncExecutor syncExecutor = (NvwaBaseDataDataSyncExecutor)SpringContextUtils.getBean(NvwaBaseDataDataSyncExecutor.class);
        if (CollectionUtils.isEmpty(nvwaBaseDataDTOs)) {
            return;
        }
        AtomicInteger currentIndex = new AtomicInteger(0);
        nvwaBaseDataDTOs.stream().forEach(nvwaBaseDataDTO -> {
            try {
                currentIndex.addAndGet(1);
                syncExecutor.insertOrUpdateBaseData(result, currentIndex, (DataSyncNvwaBaseDataDTO)nvwaBaseDataDTO);
            }
            catch (Exception e) {
                this.LOGGER.error(e.getMessage(), e);
            }
        });
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdateBaseData(VaDaPushResult result, AtomicInteger currentIndex, DataSyncNvwaBaseDataDTO basedata) throws Exception {
        this.LOGGER.info("\u62bd\u53d6\u7b2c" + currentIndex + "\u4e2a\u57fa\u7840\u6570\u636e\u9879\u505a\u66f4\u65b0,code:" + basedata.getCode());
        GlueBaseDataDTO glueBaseData = NvwaBaseDataConverter.convertGlueDTO(basedata);
        this.productService.saveResult(result, Arrays.asList(glueBaseData));
    }
}

