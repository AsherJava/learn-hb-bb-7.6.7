/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.carryover.api.GcCarryOverConfigClient
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.carryover.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.carryover.api.GcCarryOverConfigClient;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Primary
@RestController
public class GcCarryOverConfigController
implements GcCarryOverConfigClient {
    private static final Logger logger = LoggerFactory.getLogger(GcCarryOverConfigController.class);
    @Autowired
    private GcCarryOverConfigService gcCarryOverConfigService;

    public BusinessResponseEntity<String> saveConfig(CarryOverConfigVO configVO) {
        return BusinessResponseEntity.ok((Object)this.gcCarryOverConfigService.saveConfig(configVO));
    }

    public BusinessResponseEntity<String> deleteConfig(String id) {
        Boolean flag = this.gcCarryOverConfigService.deleteConfigById(id);
        String message = flag != false ? "\u5220\u9664\u6210\u529f\uff01" : "\u5220\u9664\u5931\u8d25!";
        return BusinessResponseEntity.ok((Object)message);
    }

    public BusinessResponseEntity<String> updateConfig(CarryOverConfigVO configVO) {
        return BusinessResponseEntity.ok((Object)this.gcCarryOverConfigService.updateConfig(configVO));
    }

    public BusinessResponseEntity<List<CarryOverConfigVO>> listCarryOverConfig() {
        List<CarryOverConfigVO> configVOS = this.gcCarryOverConfigService.listCarryOverConfig();
        return BusinessResponseEntity.ok(configVOS);
    }

    public String getListCarryOverConfig() {
        List<CarryOverConfigVO> configVOS = this.gcCarryOverConfigService.listCarryOverConfig();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(configVOS.stream().map(vo -> {
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("id", vo.getId());
                result.put("title", vo.getTitle());
                return result;
            }).collect(Collectors.toList()));
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    public BusinessResponseEntity<List<CarryOverTypeVO>> listCarryOverType() {
        List<CarryOverTypeVO> carryOverTypeVOList = this.gcCarryOverConfigService.listCarryOverType();
        return BusinessResponseEntity.ok(carryOverTypeVOList);
    }

    public BusinessResponseEntity<String> getConfigById(String id) {
        String optionVO = this.gcCarryOverConfigService.getConfigOptionById(id);
        return BusinessResponseEntity.ok((Object)optionVO);
    }

    public BusinessResponseEntity<Boolean> exchangeSortConfig(String currId, String exchangeId) {
        Boolean isSuccess = this.gcCarryOverConfigService.exchangeSortConfig(currId, exchangeId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> importConfigByJson(boolean isOverwrite, MultipartFile multipartFile) {
        this.gcCarryOverConfigService.importConfigByJson(isOverwrite, multipartFile);
        return BusinessResponseEntity.ok((Object)"\u5bfc\u5165\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public ResponseEntity<Resource> exportConfig() {
        List<CarryOverConfigEO> configEOS = this.gcCarryOverConfigService.listAll();
        if (CollectionUtils.isEmpty(configEOS)) {
            throw new BusinessRuntimeException("\u7cfb\u7edf\u4e2d\u672a\u914d\u7f6e\u5e74\u7ed3\u65b9\u6848\uff0c\u5bfc\u51fa\u5931\u8d25");
        }
        String fileName = "\u5e74\u7ed3\u8bbe\u7f6e\u65b9\u6848.json";
        String encode = "\u5e74\u7ed3\u8bbe\u7f6e\u65b9\u6848.json";
        try {
            encode = URLEncoder.encode(fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        String fileContent = JsonUtils.writeValueAsString(configEOS);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", new String[]{"attachment; filename=\"" + encode + "\""})).body((Object)resource);
    }
}

