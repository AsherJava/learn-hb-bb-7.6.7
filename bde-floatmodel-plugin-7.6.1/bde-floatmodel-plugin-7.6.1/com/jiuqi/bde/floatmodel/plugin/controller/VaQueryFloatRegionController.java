/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.web.QueryTemplateContentClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.floatmodel.plugin.controller;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO;
import com.jiuqi.bde.floatmodel.plugin.controller.VaQueryConfigInfo;
import com.jiuqi.bde.floatmodel.plugin.handler.VaQueryFloatRegionHandler;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.web.QueryTemplateContentClient;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VaQueryFloatRegionController {
    static final String FLOAT_API = "/api/bde/v1/floatRegion";
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    @Lazy
    private VaQueryFloatRegionHandler floatRegionHandler;
    @Autowired
    @Lazy
    private QueryTemplateContentClient queryTemplateContentClient;

    @PostMapping(value={"/api/bde/v1/floatRegion/parseVaQueryParam"})
    public BusinessResponseEntity<Map<String, Object>> parseVaQueryParam(@RequestBody VaQueryConfigInfo vaQueryConfigInfo) {
        OrgMappingDTO orgMapping = this.getOrgMapping(vaQueryConfigInfo);
        vaQueryConfigInfo.setOrgMapping(orgMapping);
        QueryConfigInfo queryConfigInfo = vaQueryConfigInfo.getQueryConfigInfo();
        if (vaQueryConfigInfo == null) {
            return null;
        }
        VaQueryPluginDataVO vaQueryPluginDataVO = this.floatRegionHandler.getVaQueryPluginDataVO(queryConfigInfo.getPluginData());
        if (vaQueryPluginDataVO == null || queryConfigInfo == null) {
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u67e5\u8be2\u5b9a\u4e49\u7ed3\u679c\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        String queryDefineCode = vaQueryPluginDataVO.getQueryDefineCode();
        if (StringUtils.isEmpty((String)queryDefineCode)) {
            throw new BusinessRuntimeException("\u672a\u914d\u7f6e\u6d6e\u52a8\u884c\u53d6\u6570\u67e5\u8be2\u5b9a\u4e49,\u53d6\u6570\u5931\u8d25\uff01");
        }
        List argMappings = vaQueryPluginDataVO.getArgsMapping();
        TemplateContentVO templateContentVO = (TemplateContentVO)this.queryTemplateContentClient.getTemplateContentByCode(queryDefineCode).getData();
        if (templateContentVO == null) {
            throw new BusinessRuntimeException("\u6839\u636e\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u3010" + queryDefineCode + "\u3011\u672a\u627e\u5230\u67e5\u8be2\u5b9a\u4e49,\u53d6\u6570\u5931\u8d25\uff01");
        }
        return BusinessResponseEntity.ok(this.floatRegionHandler.parseParam(vaQueryConfigInfo, vaQueryPluginDataVO, templateContentVO));
    }

    private OrgMappingDTO getOrgMapping(VaQueryConfigInfo queryConfigInfo) {
        OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(queryConfigInfo.getBblx()).getOrgMapping(queryConfigInfo.getUnitCode());
        if (orgMapping == null) {
            if (queryConfigInfo.getBblx() == null) {
                throw new CheckRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u5355\u4f4d\u3010%1$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", queryConfigInfo.getUnitCode()));
            }
            throw new CheckRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u7c7b\u578b\u3010%1$s\u3011\u62a5\u8868\u5355\u4f4d\u4ee3\u7801\u3010%2$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", queryConfigInfo.getBblx(), queryConfigInfo.getUnitCode()));
        }
        return orgMapping;
    }
}

