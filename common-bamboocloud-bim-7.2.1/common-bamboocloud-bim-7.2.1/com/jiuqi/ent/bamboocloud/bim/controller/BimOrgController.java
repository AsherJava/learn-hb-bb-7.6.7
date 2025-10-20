/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.log.LogHelper
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.ent.bamboocloud.bim.controller;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import com.jiuqi.ent.bamboocloud.bim.FunctionNotSupportException;
import com.jiuqi.ent.bamboocloud.bim.dto.GeneralDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgCreateDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgExtFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgIdListDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgQueryDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.service.BimOrgExtService;
import com.jiuqi.ent.bamboocloud.bim.util.BimHelper;
import com.jiuqi.np.log.LogHelper;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/bamboocloud"})
public class BimOrgController {
    private static final Logger logger = LoggerFactory.getLogger(BimOrgController.class);
    private static final String NEW_ORG_MUST_TIPS = "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5fc5\u586b";
    @Autowired
    private BimHelper bimHelper;
    @Autowired
    private BimOrgExtService orgExtService;
    @Autowired
    private BimProperties bimProperties;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/OrgCreateService"})
    public String createOrg(HttpServletRequest request) {
        String bimRequestId = "";
        String orgId = "";
        try {
            if ("user".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            this.orgExtService.bindNpUserContext();
            OrgExtFieldDTO extOrg = this.getOrgExtDTO(params, true);
            logger.info("\u521b\u5efa\u7ec4\u7ec7\u673a\u6784OrgCreateService\u5165\u53c2:[{}]", (Object)extOrg);
            this.orgExtService.createBaseOrg(extOrg);
            String title = String.format("\u65b0\u589e-\u7ec4\u7ec7 %s \u6210\u529f", extOrg.getCode());
            LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u8bf7\u6c42\u5bf9\u8c61:\n" + extOrg));
            if (this.orgExtService.isOrgExists(extOrg.getCode())) {
                orgId = extOrg.getCode();
            }
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u7ec4\u7ec7\u673a\u6784OrgCreateService\u51fa\u73b0\u5f02\u5e38: {}", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            String string = this.bimHelper.encrypt(errorJson);
            return string;
        }
        finally {
            this.orgExtService.unbindNpUserContext();
        }
        OrgCreateDTO dto = new OrgCreateDTO(bimRequestId, "0", "success", orgId);
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("\u65b0\u589e\u7ec4\u7ec7\u673a\u6784OrgCreateService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    protected OrgExtFieldDTO getOrgExtDTO(Map<String, Object> params, boolean isCreate) {
        OrgExtFieldDTO result = new OrgExtFieldDTO();
        this.validateOrgParams(params, isCreate);
        for (String orgKey : params.keySet()) {
            Object orgVal = params.get(orgKey);
            switch (orgKey) {
                case "bimOrgId": 
                case "CODE": {
                    result.setCode((String)orgVal);
                    break;
                }
                case "NAME": {
                    result.setName((String)orgVal);
                    break;
                }
                case "SHORT_NAME": {
                    result.setShortName((String)orgVal);
                    break;
                }
                case "PARENT_CODE": {
                    result.setParentCode((String)orgVal);
                    break;
                }
                case "CURRENCY_CODE": {
                    result.setCurrencyCode((String)orgVal);
                    break;
                }
                case "CREDIT_CODE": {
                    result.setCreditCode((String)orgVal);
                    break;
                }
                case "CREATETIME": {
                    result.setCreateTime((String)orgVal);
                }
            }
            if (!this.bimProperties.getExtOrgFieldNames().contains(orgKey)) continue;
            result.setFieldValue(orgKey, orgVal);
        }
        return result;
    }

    private void validateOrgParams(Map<String, Object> params, boolean isCreate) {
        Assert.notNull(params, "\u7ec4\u7ec7\u673a\u6784\u8bf7\u6c42\u4f53\u4e0d\u80fd\u4e3a\u7a7a");
        if (isCreate) {
            Assert.hasText(this.getOrgCode(params), "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5fc5\u586bCODE");
            Assert.hasText(this.getOrgName(params), "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5fc5\u586bNAME");
            Assert.hasText(this.getOrgParentCode(params), "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5fc5\u586bPARENT_CODE");
            Assert.hasText(this.getOrgCurrencyCode(params), "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5fc5\u586bCURRENCY_CODE");
            if (!CollectionUtils.isEmpty(this.bimProperties.getExtOrgFields())) {
                for (SchemaFieldDTO extOrgField : this.bimProperties.getExtOrgFields()) {
                    if (!extOrgField.isRequired()) continue;
                    Assert.hasText(this.getOrgExtFieldVal(params, extOrgField.getName()), NEW_ORG_MUST_TIPS + extOrgField.getName());
                }
            }
        } else {
            Assert.hasText(this.getBimOrgId(params), "\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784\u5fc5\u586bbimOrgId");
        }
    }

    private String getOrgExtFieldVal(Map<String, Object> params, String name) {
        return (String)params.get(name);
    }

    private String getOrgCurrencyCode(Map<String, Object> params) {
        return (String)params.get("CURRENCY_CODE");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/OrgUpdateService"})
    public String updateOrg(HttpServletRequest request) {
        String bimRequestId = "";
        try {
            if ("user".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            this.orgExtService.bindNpUserContext();
            OrgExtFieldDTO extVO = this.getOrgExtDTO(params, false);
            logger.info("\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784OrgUpdateService\u5165\u53c2:[{}]", (Object)extVO);
            this.orgExtService.updateBaseOrg(extVO);
            String title = String.format("\u4fee\u6539-\u7ec4\u7ec7 %s \u6210\u529f", extVO.getCode());
            LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u8bf7\u6c42\u5bf9\u8c61:\n" + extVO));
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784OrgUpdateService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            String string = this.bimHelper.encrypt(errorJson);
            return string;
        }
        finally {
            this.orgExtService.unbindNpUserContext();
        }
        GeneralDTO dto = new GeneralDTO(bimRequestId, "0", "success");
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784OrgUpdateService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/OrgDeleteService"})
    public String deleteOrg(HttpServletRequest request) {
        String bimRequestId = "";
        try {
            if ("user".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            String bimOrgId = this.getBimOrgId(params);
            Assert.hasText(this.getBimOrgId(params), "\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u5fc5\u4f20bimOrgId");
            this.orgExtService.bindNpUserContext();
            logger.info("\u5220\u9664\u7ec4\u7ec7\u673a\u6784OrgDeleteService\u5165\u53c2:[{}]", (Object)JsonUtils.writeValueAsString(params));
            this.orgExtService.deleteBaseOrg(bimOrgId);
            String title = String.format("\u5220\u9664-\u7ec4\u7ec7 %s \u6210\u529f", bimOrgId);
            LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u8bf7\u6c42\u5bf9\u8c61:\n" + JsonUtils.writeValueAsString(params)));
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7ec4\u7ec7\u673a\u6784OrgDeleteService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            String string = this.bimHelper.encrypt(errorJson);
            return string;
        }
        finally {
            this.orgExtService.unbindNpUserContext();
        }
        GeneralDTO dto = new GeneralDTO(bimRequestId, "0", "success");
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("\u5220\u9664\u7ec4\u7ec7\u673a\u6784OrgDeleteService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/QueryAllOrgIdsService"})
    public String getAllOrgIds(HttpServletRequest request) {
        String bimRequestId = "";
        List<String> orgIds = null;
        try {
            if ("user".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            this.orgExtService.bindNpUserContext();
            logger.info("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryAllOrgIdsService\u5165\u53c2:[{}]", (Object)params);
            orgIds = this.orgExtService.queryAllOrgIds();
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryAllOrgIdsService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            String string = this.bimHelper.encrypt(errorJson);
            return string;
        }
        finally {
            this.orgExtService.unbindNpUserContext();
        }
        OrgIdListDTO dto = new OrgIdListDTO(bimRequestId, "0", "success", orgIds);
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryAllOrgIdsService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/QueryOrgByIdService"})
    public String getOrgById(HttpServletRequest request) {
        Map<String, Object> orgJson;
        String bimRequestId = "";
        try {
            if ("user".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            this.orgExtService.bindNpUserContext();
            String bimOrgId = this.getBimOrgId(params);
            logger.info("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryOrgByIdService\u5165\u53c2:[{}]", (Object)params);
            orgJson = this.orgExtService.queryOrgById(bimOrgId);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryOrgByIdService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            String string = this.bimHelper.encrypt(errorJson);
            return string;
        }
        finally {
            this.orgExtService.unbindNpUserContext();
        }
        OrgQueryDTO dto = new OrgQueryDTO(bimRequestId, "0", "success", orgJson);
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784QueryOrgByIdService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    private String getBimOrgId(Map<String, Object> params) {
        return (String)params.get("bimOrgId");
    }

    private String getOrgName(Map<String, Object> params) {
        return (String)params.get("NAME");
    }

    private String getOrgCode(Map<String, Object> params) {
        return (String)params.get("CODE");
    }

    private String getOrgParentCode(Map<String, Object> params) {
        return (String)params.get("PARENT_CODE");
    }
}

