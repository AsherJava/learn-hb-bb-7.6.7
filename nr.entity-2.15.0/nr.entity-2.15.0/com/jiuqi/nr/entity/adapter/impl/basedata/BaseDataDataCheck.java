/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata;

import com.jiuqi.nr.entity.adapter.impl.AbstractDataCheck;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataDataCheck
extends AbstractDataCheck {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    protected DataModelColumn getCodeColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("CODE");
    }

    @Override
    protected DataModelColumn getKeyColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("OBJECTCODE");
    }

    @Override
    protected DataModelColumn getNameColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("NAME");
    }

    @Override
    protected DataModelColumn getParentColumn(Map<String, DataModelColumn> orgColumns) {
        return orgColumns.get("parentcode".toUpperCase(Locale.ROOT));
    }

    @Override
    protected Map<String, List<String>> queryExistData(List<String> codes, String tableName, Date versionTime) {
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(tableName);
        baseDataFilter.setVersionDate(versionTime);
        baseDataFilter.setBaseDataCodes(codes);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        PageVO list = this.baseDataClient.list(baseDataFilter);
        return list.getRows().stream().collect(Collectors.groupingBy(BaseDataDO::getCode, Collectors.mapping(BaseDataDO::getObjectcode, Collectors.toList())));
    }

    @Override
    protected boolean enableRepeatCheck(String tableName, boolean insert) {
        return true;
    }

    @Override
    protected List<CheckFailNodeInfo> checkData(IEntityUpdateParam updateParam, EntityDataRow row) {
        return null;
    }
}

