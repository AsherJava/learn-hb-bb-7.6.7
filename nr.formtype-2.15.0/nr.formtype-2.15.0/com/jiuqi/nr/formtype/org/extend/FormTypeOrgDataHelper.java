/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOrgDataHelper {
    @Autowired
    private OrgDataClient orgDataClient;

    public List<OrgDO> getOrgDataByOrgCodes(String categoryname, Date versionDate, List<String> orgcodes) {
        OrgDTO oldOrgDataDTO = new OrgDTO();
        oldOrgDataDTO.setCategoryname(categoryname);
        oldOrgDataDTO.setVersionDate(versionDate);
        oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
        oldOrgDataDTO.setOrgOrgcodes(orgcodes);
        PageVO oldList = this.orgDataClient.list(oldOrgDataDTO);
        oldOrgDataDTO.setOrgOrgcodes(null);
        oldOrgDataDTO.setOrgCodes(orgcodes);
        PageVO oldList2 = this.orgDataClient.list(oldOrgDataDTO);
        if (oldList != null && oldList.getTotal() > 0) {
            if (oldList2 != null && oldList2.getTotal() > 0) {
                oldList.getRows().addAll(oldList2.getRows());
            }
        } else {
            oldList = oldList2;
        }
        if (null == oldList) {
            return Collections.emptyList();
        }
        return oldList.getRows();
    }

    public static String getVersionTitle(Map<String, Object> orgDO) {
        return (String)orgDO.get("versiontitle");
    }

    public static Date getVersionDate(Map<String, Object> orgDO) {
        return (Date)orgDO.get("versionDate");
    }

    public OrgDO getOldOrgData(OrgDO orgDO) {
        return this.getOrgData(orgDO.getCategoryname(), orgDO.getCode(), FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)orgDO));
    }

    public OrgDO getOldParentOrgData(OrgDO orgDO) {
        if (!StringUtils.hasText(orgDO.getParentcode())) {
            return null;
        }
        return this.getOrgData(orgDO.getCategoryname(), orgDO.getParentcode(), FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)orgDO));
    }

    public OrgDO getOrgDataByCode(String categoryname, String code, Date versionDate) {
        return this.getOrgData(categoryname, code, versionDate);
    }

    public OrgDO getOrgData(String categoryname, String code, Date versionDate) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(categoryname);
        orgDTO.setCode(code);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setVersionDate(versionDate);
        return this.orgDataClient.get(orgDTO);
    }

    public List<OrgDO> getOrgDataByParentCode(String categoryname, String parentcode, Date versionDate) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(categoryname);
        orgDTO.setParentcode(parentcode);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setVersionDate(versionDate);
        PageVO list = this.orgDataClient.list(orgDTO);
        return list.getRows();
    }

    protected Function<OrgDO, OrgDO> getParentGetter(List<OrgDO> orgDatas) {
        HashMap<String, OrgDO> map = new HashMap<String, OrgDO>();
        if (!CollectionUtils.isEmpty(orgDatas)) {
            for (OrgDO orgDO : orgDatas) {
                map.put(orgDO.getCode(), orgDO);
            }
        }
        return data -> {
            if (!StringUtils.hasText(data.getParentcode())) {
                return null;
            }
            if (map.containsKey(data.getParentcode())) {
                return (OrgDO)map.get(data.getParentcode());
            }
            OrgDO orgDataByCode = this.getOrgDataByCode(data.getCategoryname(), data.getParentcode(), FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)data));
            map.put(data.getParentcode(), orgDataByCode);
            return orgDataByCode;
        };
    }

    protected Function<OrgDO, List<OrgDO>> getChildrenGetter(List<OrgDO> orgDatas) {
        Map<String, List<OrgDO>> orgDatasMap = null == orgDatas ? null : orgDatas.stream().filter(data -> StringUtils.hasLength(data.getParentcode())).collect(Collectors.groupingBy(OrgDO::getParentcode));
        return data -> {
            List<OrgDO> children;
            ArrayList<OrgDO> result = new ArrayList<OrgDO>();
            if (null != orgDatasMap && orgDatasMap.containsKey(data.getCode())) {
                result.addAll((Collection)orgDatasMap.get(data.getCode()));
            }
            if (!CollectionUtils.isEmpty(children = this.getOrgDataByParentCode(data.getCategoryname(), data.getCode(), FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)data)))) {
                result.addAll(children);
            }
            return result;
        };
    }
}

