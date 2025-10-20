/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.gcreport.billextract.impl.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class BillExtractUtil {
    public static final String FN_DELIMITER_DOT = ".";

    public static final Map<String, OrgDO> queryOrgMap(String orgType, Date versionDate) {
        Assert.isNotEmpty((String)orgType);
        Assert.isNotNull((Object)versionDate);
        OrgDTO orgCondi = new OrgDTO();
        orgCondi.setCategoryname(orgType);
        orgCondi.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgCondi.setVersionDate(versionDate);
        return ((OrgDataService)ApplicationContextRegister.getBean(OrgDataService.class)).list(orgCondi).getRows().stream().collect(Collectors.toMap(OrgDO::getCode, item -> item, (k1, k2) -> k2));
    }

    public static final String queryOrgTypeByColumn(DataModelColumn column) {
        Assert.isNotNull((Object)column);
        String mapping = column.getMapping();
        if (StringUtils.isEmpty((String)mapping)) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u5efa\u6a21\u4fe1\u606f\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5173\u8054\u5355\u4f4d\u4fe1\u606f\uff0c\u8bf7\u68c0\u67e5\u5355\u636e\u5efa\u6a21\u914d\u7f6e", column.getColumnName()));
        }
        int dotIndex = mapping.indexOf(FN_DELIMITER_DOT);
        if (dotIndex > 0) {
            return mapping.substring(0, dotIndex);
        }
        throw new BusinessRuntimeException(String.format("\u6839\u636e\u5efa\u6a21\u4fe1\u606f\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5355\u4f4d\u7c7b\u578b\uff0c\u8bf7\u68c0\u67e5\u5355\u636e\u5efa\u6a21\u914d\u7f6e", column.getColumnName()));
    }
}

