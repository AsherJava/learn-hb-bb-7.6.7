/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.ent.bamboocloud.bim.controller;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaAccountDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaAllDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaOrgDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaParentDTO;
import com.jiuqi.ent.bamboocloud.bim.util.BimHelper;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/bamboocloud"})
public class BimSchemaController {
    private static final Logger logger = LoggerFactory.getLogger(BimSchemaController.class);
    private static final String STRING_TYPE = "String";
    private static final String BOOLEAN_TYPE = "boolean";
    private BimHelper bimHelper;
    private BimProperties bimProperties;

    @PostMapping(value={"/SchemaService"})
    public String getSchemaService(HttpServletRequest request) {
        Set<SchemaFieldDTO> account;
        String bimRequestId = "";
        try {
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.getBimRequestId(params);
        }
        catch (Exception e) {
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        SchemaParentDTO dto = null;
        if ("user".equals(this.bimProperties.getOpenFunc())) {
            account = this.assembleAccount();
            dto = new SchemaAccountDTO(bimRequestId, account);
        }
        if ("org".equals(this.bimProperties.getOpenFunc())) {
            Set<SchemaFieldDTO> org = this.assembleOrganization();
            dto = new SchemaOrgDTO(bimRequestId, org);
        }
        if ("all".equals(this.bimProperties.getOpenFunc())) {
            account = this.assembleAccount();
            Set<SchemaFieldDTO> org = this.assembleOrganization();
            dto = new SchemaAllDTO(bimRequestId, account, org);
        }
        String json = JsonUtils.writeValueAsString((Object)dto);
        logger.info("SchemaService\u67e5\u8be2\u660e\u6587json --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    private String getBimRequestId(Map<String, Object> params) {
        return (String)params.get("bimRequestId");
    }

    protected Set<SchemaFieldDTO> assembleOrganization() {
        HashSet<SchemaFieldDTO> orgs = new HashSet<SchemaFieldDTO>();
        orgs.add(new SchemaFieldDTO(false, "CODE", true, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "NAME", true, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "PARENT_CODE", true, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "CURRENCY_CODE", true, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "SHORT_NAME", false, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "CREDIT_CODE", false, STRING_TYPE));
        orgs.add(new SchemaFieldDTO(false, "CREATETIME", false, STRING_TYPE));
        if (!CollectionUtils.isEmpty(this.bimProperties.getExtOrgFields())) {
            for (SchemaFieldDTO extOrgField : this.bimProperties.getExtOrgFields()) {
                if (StringUtils.isEmpty((CharSequence)extOrgField.getType())) {
                    extOrgField.setType(STRING_TYPE);
                }
                orgs.add(extOrgField);
            }
        }
        return orgs;
    }

    protected Set<SchemaFieldDTO> assembleAccount() {
        HashSet<SchemaFieldDTO> account = new HashSet<SchemaFieldDTO>(6);
        account.add(new SchemaFieldDTO(false, "NAME", true, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "PHONE", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "CODE", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "NICK_NAME", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "password", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "ORGCODE", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "EMAIL", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "ID_NUMBER", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "SECURITY_LEVEL", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "ENABLE", false, BOOLEAN_TYPE));
        account.add(new SchemaFieldDTO(false, "CREATE_TIME", false, STRING_TYPE));
        account.add(new SchemaFieldDTO(false, "UPDATE_TIME", false, STRING_TYPE));
        return account;
    }

    @Autowired
    public void setBimHelper(BimHelper bambooCloudBimHelper) {
        this.bimHelper = bambooCloudBimHelper;
    }

    @Autowired
    public void setDataSyncProperties(BimProperties bambooCloudDataSyncProperties) {
        this.bimProperties = bambooCloudDataSyncProperties;
    }
}

