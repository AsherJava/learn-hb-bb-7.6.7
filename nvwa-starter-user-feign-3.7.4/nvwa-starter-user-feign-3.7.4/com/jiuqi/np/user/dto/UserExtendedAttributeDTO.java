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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class UserExtendedAttributeDTO
implements UserExtendedAttribute {
    private static final long serialVersionUID = 1141244868208251395L;
    private String id;
    private String userId;
    private String attributeName;
    private String attributeValue;
    private String attributeTitle;
    private int dataType;
    private AttrEncryptType encryptType;

    public UserExtendedAttributeDTO() {
    }

    public UserExtendedAttributeDTO(String userId, String attributeName, String attributeValue) {
        this.userId = userId;
        this.attributeName = ObjectUtils.isEmpty(attributeName) ? "" : attributeName.toUpperCase();
        this.attributeValue = attributeValue;
    }

    public static UserExtendedAttributeDTO from(UserExtendedAttribute attribute) {
        UserExtendedAttributeDTO entity = new UserExtendedAttributeDTO();
        entity.setId(attribute.getId());
        entity.setAttributeName(attribute.getAttributeName());
        entity.setAttributeValue(attribute.getAttributeValue());
        entity.setUserId(attribute.getUserId());
        return entity;
    }

    public UserExtendedAttributeDTO(UserExtendedAttributeDTO userExtendedAttributeDTO) {
        this.userId = userExtendedAttributeDTO.getUserId();
        this.id = userExtendedAttributeDTO.getId();
        this.attributeName = userExtendedAttributeDTO.getAttributeName();
        this.attributeValue = userExtendedAttributeDTO.getAttributeValue();
        this.attributeTitle = userExtendedAttributeDTO.getAttributeTitle();
        this.dataType = userExtendedAttributeDTO.getDataType();
        this.encryptType = userExtendedAttributeDTO.getEncryptType();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttributeName() {
        return ObjectUtils.isEmpty(this.attributeName) ? "" : this.attributeName.toUpperCase();
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = ObjectUtils.isEmpty(attributeName) ? "" : attributeName.toUpperCase();
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public String getAttributeTitle() {
        return !StringUtils.hasLength(this.attributeTitle) ? this.attributeValue : this.attributeTitle;
    }

    public void setAttributeTitle(String attributeTitle) {
        this.attributeTitle = attributeTitle;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public AttrEncryptType getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(AttrEncryptType encryptType) {
        this.encryptType = encryptType;
    }
}

