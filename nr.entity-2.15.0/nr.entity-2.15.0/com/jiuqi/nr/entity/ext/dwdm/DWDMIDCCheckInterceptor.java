/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.extend.OrgDataParamInterceptor
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.ext.dwdm;

import com.jiuqi.nr.entity.adapter.impl.org.OrgDataFilter;
import com.jiuqi.nr.entity.component.idc.IDCUtils;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.ext.dwdm.DWDMOptionQuery;
import com.jiuqi.nr.entity.ext.dwdm.exception.IDCCheckException;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.extend.OrgDataParamInterceptor;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DWDMIDCCheckInterceptor
extends DWDMOptionQuery
implements OrgDataParamInterceptor,
OrgCodeUniqueCheckInterceptor,
OrgDataFilter {
    private static final String SIZE_8_MODEL = "0";
    private static final String SIZE_16_MODEL = "1";
    @Autowired
    private DataModelClient dataModelClient;

    public void modify(OrgDTO param, OrgDataAction action) {
        boolean check;
        if (!this.enableOption() || !this.actionPreCheck(action) || param == null) {
            return;
        }
        DataModelColumn dwdmAttribute = this.getDWDMAttribute(param.getCategoryname());
        if (dwdmAttribute == null) {
            return;
        }
        Object dwdm = param.get((Object)dwdmAttribute.getColumnName().toLowerCase(Locale.ROOT));
        if (dwdm == null) {
            return;
        }
        String model = this.getOption();
        try {
            check = this.IDCCheckOrgCode(dwdm.toString(), model, dwdmAttribute.getColumnTitle());
        }
        catch (Exception e) {
            throw new IDCCheckException(e.getMessage());
        }
        if (!check) {
            throw new IDCCheckException(String.format("%s:'%s'\u672a\u901a\u8fc7IDC\u6821\u9a8c", dwdmAttribute.getColumnTitle(), dwdm));
        }
    }

    public void batchModify(OrgBatchOptDTO param, OrgDataAction action) {
        if (!this.enableOption() || !this.actionPreCheck(action) || param == null) {
            return;
        }
        DataModelColumn dwdmAttribute = null;
        OrgDTO queryParam = param.getQueryParam();
        if (queryParam != null) {
            dwdmAttribute = this.getDWDMAttribute(queryParam.getCategoryname());
        }
        if (dwdmAttribute == null) {
            return;
        }
        String model = this.getOption();
        Map<String, String> illegalOrgCodeMap = this.batchCheck(param, dwdmAttribute, model);
        if (!CollectionUtils.isEmpty(illegalOrgCodeMap)) {
            String illegalOrgCode = String.join((CharSequence)",", new ArrayList<String>(illegalOrgCodeMap.values()));
            throw new IDCCheckException(String.format("%s'%s'\u672a\u901a\u8fc7IDC\u6821\u9a8c", dwdmAttribute.getColumnTitle(), illegalOrgCode));
        }
    }

    public void check(OrgBatchOptDTO orgBatchOptDTO, Map<String, String> errorResult, OrgCategoryDO orgCategory, OrgDataAction action) {
        if (!this.enableOption() || !this.actionPreCheck(action)) {
            return;
        }
        DataModelColumn dwdmAttribute = null;
        OrgDTO queryParam = orgBatchOptDTO.getQueryParam();
        if (queryParam != null) {
            dwdmAttribute = this.getDWDMAttribute(queryParam.getCategoryname());
        }
        if (dwdmAttribute == null) {
            return;
        }
        String model = this.getOption();
        Map<String, String> illegalOrgCodeMap = this.batchCheck(orgBatchOptDTO, dwdmAttribute, model);
        Set<String> orgCodes = illegalOrgCodeMap.keySet();
        for (String illegalOrgCode : orgCodes) {
            errorResult.put(illegalOrgCode, String.format("%s'%s'\u672a\u901a\u8fc7IDC\u6821\u9a8c", dwdmAttribute.getColumnTitle(), illegalOrgCodeMap.get(illegalOrgCode)));
        }
    }

    private Map<String, String> batchCheck(OrgBatchOptDTO param, DataModelColumn attribute, String model) {
        List dataList = param.getDataList();
        HashMap<String, String> illegalOrgCodes = new HashMap<String, String>(16);
        for (OrgDO orgDO : dataList) {
            Object dwdm = orgDO.get((Object)attribute.getColumnName().toLowerCase(Locale.ROOT));
            if (dwdm == null || this.IDCCheckOrgCode(dwdm.toString(), model, attribute.getColumnTitle())) continue;
            String code = StringUtils.hasText(orgDO.getCode()) ? orgDO.getCode() : dwdm.toString();
            illegalOrgCodes.put(code, dwdm.toString());
        }
        return illegalOrgCodes;
    }

    private boolean IDCCheckOrgCode(String orgCode, String model, String name) {
        if (SIZE_8_MODEL.equals(model)) {
            return IDCUtils.verifyIDCCode(orgCode, name);
        }
        if (SIZE_16_MODEL.equals(model)) {
            return IDCUtils.verifyIDCLCode(orgCode, name);
        }
        return false;
    }

    private boolean actionPreCheck(OrgDataAction action) {
        return action != OrgDataAction.Remove;
    }

    private DataModelColumn getDWDMAttribute(String category) {
        DataModelDTO param = new DataModelDTO();
        param.setName(category);
        DataModelDO dataModelDO = this.dataModelClient.get(param);
        if (dataModelDO == null) {
            return null;
        }
        Map extInfo = dataModelDO.getExtInfo();
        if (CollectionUtils.isEmpty(extInfo)) {
            return null;
        }
        Object attribute = extInfo.get("DWDM_FIELD");
        if (attribute != null) {
            List columns = dataModelDO.getColumns();
            Optional<DataModelColumn> findColumn = columns.stream().filter(e -> attribute.toString().equalsIgnoreCase(e.getColumnName())).findFirst();
            return findColumn.orElse(null);
        }
        return null;
    }

    @Override
    public List<CheckFailNodeInfo> checkData(OrgDO orgDO) {
        if (!this.enableOption()) {
            return null;
        }
        DataModelColumn dwdmAttribute = this.getDWDMAttribute(orgDO.getCategoryname());
        if (dwdmAttribute == null) {
            return null;
        }
        Object dwdm = orgDO.get((Object)dwdmAttribute.getColumnName().toLowerCase(Locale.ROOT));
        if (dwdm == null) {
            return null;
        }
        String model = this.getOption();
        ArrayList<CheckFailNodeInfo> nodes = new ArrayList<CheckFailNodeInfo>();
        boolean check = false;
        String message = String.format("%s'%s'\u672a\u901a\u8fc7IDC\u6821\u9a8c", dwdmAttribute.getColumnTitle(), dwdm);
        try {
            check = this.IDCCheckOrgCode(dwdm.toString(), model, dwdmAttribute.getColumnTitle());
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        if (!check) {
            CheckFailNodeInfo nodeInfo = new CheckFailNodeInfo();
            nodeInfo.setCode(orgDO.getCode());
            nodeInfo.setValue(dwdm.toString());
            nodeInfo.setCheckOption(0);
            nodeInfo.setMessage(message);
            nodeInfo.setAttributeCode(dwdmAttribute.getColumnName());
            nodes.add(nodeInfo);
        }
        return nodes;
    }
}

