/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim;

import com.jiuqi.ent.bamboocloud.bim.BimConsts;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

@ConfigurationProperties(prefix="bamboocloud")
public class BimProperties
implements InitializingBean {
    private static final String SM4_ALGORITHM = "SM4";
    private String bimRemoteUser;
    private String bimRemotePwd;
    private String key;
    private List<SchemaFieldDTO> extOrgFields;
    private List<String> extOrgFieldNames;
    private boolean transEncode = true;
    private String openFunc = "all";
    private String transAlgorithm = "AES";
    private String verifyAlgorithm = "MD5";
    private boolean allowUserUpdateOrgcode = true;
    private boolean allowOrgUpdateParentcode = true;
    private String defaultUserOrgCode;
    private String defaultOrgParentCode;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.extOrgFieldNames = !CollectionUtils.isEmpty(this.extOrgFields) ? this.extOrgFields.stream().map(SchemaFieldDTO::getName).collect(Collectors.toList()) : new ArrayList<String>(0);
        if (!BimConsts.SupportAlgorithm.isTransAlgorithmSupport(this.transAlgorithm)) {
            throw new IllegalArgumentException("bamboocloud.transAlgorithm\u6307\u5b9a\u7b97\u6cd5\u4e0d\u652f\u6301\uff0c\u76ee\u524d\u4ec5\u652f\u6301" + BimConsts.SupportAlgorithm.TRANS_ALGORITHM);
        }
        if (!BimConsts.SupportAlgorithm.isVerifyAlgorithmSupport(this.verifyAlgorithm)) {
            throw new IllegalArgumentException("bamboocloud.verifyAlgorithm\u6307\u5b9a\u7b97\u6cd5\u4e0d\u652f\u6301\uff0c\u76ee\u524d\u4ec5\u652f\u6301" + BimConsts.SupportAlgorithm.VERIFY_ALGORITHM);
        }
        if (this.transEncode && SM4_ALGORITHM.equals(this.transAlgorithm) && (this.key == null || this.key.length() != 16)) {
            throw new IllegalArgumentException("bamboocloud.key\u503c\u4e0d\u80fd\u4e3a\u7a7a\u6216\u4e0d\u4e3a16\u4f4d");
        }
    }

    public List<String> getExtOrgFieldNames() {
        return this.extOrgFieldNames;
    }

    public String getBimRemotePwd() {
        return this.bimRemotePwd;
    }

    public String getBimRemoteUser() {
        return this.bimRemoteUser;
    }

    public List<SchemaFieldDTO> getExtOrgFields() {
        return this.extOrgFields;
    }

    public String getKey() {
        return this.key;
    }

    public void setBimRemotePwd(String bimRemotePwd) {
        this.bimRemotePwd = bimRemotePwd;
    }

    public void setBimRemoteUser(String bimRemoteUser) {
        this.bimRemoteUser = bimRemoteUser;
    }

    public void setExtOrgFields(List<SchemaFieldDTO> extOrgFields) {
        this.extOrgFields = extOrgFields;
    }

    public String getOpenFunc() {
        return this.openFunc;
    }

    public void setOpenFunc(String openFunc) {
        this.openFunc = openFunc;
    }

    public void setExtOrgFieldNames(List<String> extOrgFieldNames) {
        this.extOrgFieldNames = extOrgFieldNames;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isTransEncode() {
        return this.transEncode;
    }

    public void setTransEncode(boolean transEncode) {
        this.transEncode = transEncode;
    }

    public String getTransAlgorithm() {
        return this.transAlgorithm;
    }

    public void setTransAlgorithm(String transAlgorithm) {
        this.transAlgorithm = transAlgorithm;
    }

    public String getVerifyAlgorithm() {
        return this.verifyAlgorithm;
    }

    public void setVerifyAlgorithm(String verifyAlgorithm) {
        this.verifyAlgorithm = verifyAlgorithm;
    }

    public boolean isAllowUserUpdateOrgcode() {
        return this.allowUserUpdateOrgcode;
    }

    public void setAllowUserUpdateOrgcode(boolean allowUserUpdateOrgcode) {
        this.allowUserUpdateOrgcode = allowUserUpdateOrgcode;
    }

    public boolean isAllowOrgUpdateParentcode() {
        return this.allowOrgUpdateParentcode;
    }

    public void setAllowOrgUpdateParentcode(boolean allowOrgUpdateParentcode) {
        this.allowOrgUpdateParentcode = allowOrgUpdateParentcode;
    }

    public String getDefaultUserOrgCode() {
        return this.defaultUserOrgCode;
    }

    public void setDefaultUserOrgCode(String defaultUserOrgCode) {
        this.defaultUserOrgCode = defaultUserOrgCode;
    }

    public String getDefaultOrgParentCode() {
        return this.defaultOrgParentCode;
    }

    public void setDefaultOrgParentCode(String defaultOrgParentCode) {
        this.defaultOrgParentCode = defaultOrgParentCode;
    }
}

