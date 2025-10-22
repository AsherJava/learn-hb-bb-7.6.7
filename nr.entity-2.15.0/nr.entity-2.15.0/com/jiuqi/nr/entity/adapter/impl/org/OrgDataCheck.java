/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.entity.adapter.impl.org;

import com.jiuqi.nr.entity.adapter.impl.AbstractDataCheck;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataFilter;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrgDataCheck
extends AbstractDataCheck {
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private List<OrgDataFilter> orgDataFilters;

    @Override
    protected DataModelColumn getCodeColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("ORGCODE");
    }

    @Override
    protected DataModelColumn getKeyColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("CODE");
    }

    @Override
    protected DataModelColumn getNameColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("NAME");
    }

    @Override
    protected DataModelColumn getParentColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("parentcode".toUpperCase());
    }

    @Override
    protected Map<String, List<String>> queryExistData(List<String> codes, String tableName, Date versionTime) {
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptyMap();
        }
        OrgDTO queryDTO = new OrgDTO();
        queryDTO.setCategoryname(tableName);
        queryDTO.setVersionDate(versionTime);
        queryDTO.setOrgOrgcodes(codes);
        queryDTO.setAuthType(OrgDataOption.AuthType.NONE);
        queryDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO list = this.orgDataClient.list(queryDTO);
        return list.getRows().stream().collect(Collectors.groupingBy(OrgDO::getOrgcode, Collectors.mapping(OrgDO::getCode, Collectors.toList())));
    }

    @Override
    protected boolean enableRepeatCheck(String tableName, boolean insert) {
        return !"MD_ORG".equals(tableName);
    }

    @Override
    protected List<CheckFailNodeInfo> checkData(IEntityUpdateParam updateParam, EntityDataRow row) {
        if (CollectionUtils.isEmpty(this.orgDataFilters)) {
            return null;
        }
        if (updateParam.isBatchUpdateModel()) {
            return null;
        }
        ArrayList<CheckFailNodeInfo> infos = new ArrayList<CheckFailNodeInfo>(16);
        for (OrgDataFilter orgDataFilter : this.orgDataFilters) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(row.getEntityKeyData());
            orgDTO.setCategoryname(updateParam.getEntityId());
            orgDTO.setVersionDate(updateParam.getVersionDate());
            orgDTO.putAll(row.getRowData());
            List<CheckFailNodeInfo> failNodeInfos = orgDataFilter.checkData((OrgDO)orgDTO);
            if (failNodeInfos == null) continue;
            infos.addAll(failNodeInfos);
        }
        return infos;
    }
}

