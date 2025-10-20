/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.bde.plugin.finacctmid.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.plugin.finacctmid.dto.OrgBookMappingDTO;
import com.jiuqi.gcreport.bde.plugin.finacctmid.dto.OrgBookMappingListDTO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class OrgBookMappingRequest {
    private static final String ORG_BOOK_MAPPING_LIST_API = "/api/bde/v1/orgBookMapping/list";
    private static final String ORG_BOOK_MAPPING_SAVE_API = "/api/bde/v1/orgBookMapping/save";
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private RequestCertifyService certifyService;
    @Qualifier(value="bdeFetchRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    public PageVO<OrgBookMappingDTO> list(OrgBookMappingListDTO dto) {
        String unitDefine = this.getUnitDefine(dto.getTaskKey());
        HashSet unitSet = new HashSet();
        PageVO orgDataPageVo = null;
        OrgDTO condi = null;
        for (String unitCode : dto.getUnitCodes()) {
            condi = new OrgDTO();
            condi.setCategoryname(unitDefine);
            condi.setAuthType(OrgDataOption.AuthType.ACCESS);
            condi.setCode(unitCode);
            orgDataPageVo = this.orgDataClient.listSubordinate(condi);
            unitSet.addAll(orgDataPageVo.getRows().stream().map(OrgDO::getCode).collect(Collectors.toSet()));
        }
        if (CollectionUtils.isEmpty(unitSet)) {
            throw new RuntimeException("\u6ca1\u6709\u7ec4\u7ec7\u673a\u6784\u6743\u9650");
        }
        dto.setUnitCodes(unitSet.stream().collect(Collectors.toList()));
        String object = this.exchangeJSON(ORG_BOOK_MAPPING_LIST_API, dto, HttpMethod.POST);
        if (null == object) {
            return null;
        }
        return (PageVO)JSONUtil.parseObject((String)object, PageVO.class);
    }

    String getUnitDefine(String taskKey) {
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(taskKey);
        TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(taskDefine.getDw());
        return orgTableModelDefine.getName();
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean save(List<OrgBookMappingDTO> orgBookMappingList) {
        this.exchangeJSON(ORG_BOOK_MAPPING_SAVE_API, orgBookMappingList, HttpMethod.POST);
        return true;
    }

    protected <T> String exchangeJSON(String url, T param, HttpMethod httpMethod) {
        String address = this.certifyService.getNvwaUrl();
        if (!StringUtils.hasText(address)) {
            throw new RuntimeException("BDE\u5730\u5740\u4e3a\u7a7a\uff0c\u8bf7\u914d\u7f6eBDE\u5730\u5740\u3002");
        }
        String sendUrl = address + url;
        return this.exchangeStr(sendUrl, param, httpMethod);
    }

    private <T> String exchangeStr(String url, T body, HttpMethod httpMethod) {
        ResponseEntity resultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(body, (MultiValueMap)headers);
        try {
            resultStr = this.restTemplate.exchange(url, httpMethod, entity, String.class, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException("\u53d6\u6570\u5730\u5740\u8fde\u63a5\u8d85\u65f6:" + e.getMessage(), e);
        }
        if (resultStr == null) {
            throw new RuntimeException("\u8c03\u7528BDE\u53d6\u6570\u8bf7\u6c42\u5f02\u5e38\uff01");
        }
        ObjectNode resultObj = JSONUtil.parseObject((String)((String)resultStr.getBody()));
        if (BdeClientUtil.getBoolean((JsonNode)resultObj.get("success")).booleanValue()) {
            return BdeClientUtil.getString((JsonNode)resultObj.get("data"));
        }
        throw new RuntimeException(BdeClientUtil.getString((JsonNode)resultObj.get("errorMessage")));
    }
}

