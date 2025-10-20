/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.AttrEncryptType
 *  com.jiuqi.np.user.attr.UserExtendedAttribute
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.AttrEncryptType;
import com.jiuqi.np.user.attr.UserExtendedAttribute;
import com.jiuqi.np.user.dto.UserExtendedAttributeDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchUserAttributeDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    String userId;
    Map<String, String> attribute = new HashMap<String, String>();
    String orgCode;
    AttrEncryptType encryptType;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public AttrEncryptType getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(AttrEncryptType encryptType) {
        this.encryptType = encryptType;
    }

    public static List<UserExtendedAttribute> transferFromVo(BatchUserAttributeDTO vo) {
        ArrayList<UserExtendedAttribute> result = new ArrayList<UserExtendedAttribute>();
        vo.getAttribute().forEach((key, value) -> {
            UserExtendedAttributeDTO entity = new UserExtendedAttributeDTO();
            entity.setUserId(vo.getUserId());
            entity.setAttributeName((String)key);
            entity.setAttributeValue((String)value);
            entity.setEncryptType(vo.getEncryptType());
            result.add(entity);
        });
        return result;
    }

    public static BatchUserAttributeDTO transferToVo(List<UserExtendedAttribute> attributes) {
        BatchUserAttributeDTO result = new BatchUserAttributeDTO();
        HashMap<String, String> attr = new HashMap<String, String>();
        for (UserExtendedAttribute attribute : attributes) {
            result.setUserId(attribute.getUserId());
            attr.put(attribute.getAttributeName(), attribute.getAttributeValue());
            result.setEncryptType(attribute.getEncryptType());
        }
        result.setAttribute(attr);
        return result;
    }
}

