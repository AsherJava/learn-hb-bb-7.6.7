/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.basedata.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.dao.VaBaseDataDetailDao;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="vaBaseDataDetailServiceImpl")
public class BaseDataDetailServiceImpl
implements BaseDataDetailService {
    @Autowired
    private VaBaseDataDetailDao baseDataDetailDao;
    @Autowired
    private BaseDataDefineService baseDataDefineService;

    @Override
    public R add(BaseDataDetailDO baseDataDO) {
        this.baseDataDetailDao.add(baseDataDO);
        return R.ok();
    }

    @Override
    public R delete(BaseDataDetailDO param) {
        this.baseDataDetailDao.deleteByTableField(param);
        return R.ok();
    }

    @Override
    public List<BaseDataDetailDO> get(BaseDataDetailDO detail) {
        if (detail.getMasterid() == null || detail.getFieldname() == null) {
            return new ArrayList<BaseDataDetailDO>();
        }
        return this.baseDataDetailDao.list(detail);
    }

    @Override
    public List<BaseDataDetailDO> list(BaseDataDetailDO detail) {
        return this.baseDataDetailDao.list(detail);
    }

    @Override
    public void loadDetailData(BaseDataDO param, List<BaseDataCacheDO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList<String> multipleField = new ArrayList<String>();
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setTenantName(param.getTenantName());
        defineParam.setName(param.getTableName());
        defineParam.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO define = this.baseDataDefineService.get(defineParam);
        ArrayNode fields = null;
        try {
            ObjectNode objectNode = JSONUtil.parseObject((String)define.getDefine());
            fields = objectNode.withArray("fieldProps");
            if (fields != null && !fields.isEmpty()) {
                for (JsonNode field : fields) {
                    if (!field.has("multiple") || !field.get("multiple").asBoolean()) continue;
                    multipleField.add(field.get("columnName").asText().toLowerCase());
                }
            } else {
                fields = objectNode.withArray("showFields");
                if (fields == null || fields.isEmpty()) {
                    fields = objectNode.withArray("defaultShowFields");
                }
                if (fields != null) {
                    for (JsonNode field : fields) {
                        if (!field.has("multiple") || !field.get("multiple").asBoolean()) continue;
                        multipleField.add(field.get("columnName").asText().toLowerCase());
                    }
                }
            }
        }
        catch (Exception e) {
            return;
        }
        if (multipleField.isEmpty()) {
            return;
        }
        BaseDataDetailDO detailParam = new BaseDataDetailDO();
        detailParam.setTenantName(param.getTenantName());
        detailParam.setTablename(param.getTableName() + "_SUBLIST");
        if (param.containsKey((Object)"loadAllData") && ((Boolean)param.get((Object)"loadAllData")).booleanValue() || list.size() > 300) {
            this.loadAllDataDetail(detailParam, multipleField, list);
        } else {
            this.loadPartDataDetail(detailParam, multipleField, list);
        }
    }

    private void loadPartDataDetail(BaseDataDetailDO param, List<String> multipleField, List<BaseDataCacheDO> dataList) {
        for (BaseDataDO baseDataDO : dataList) {
            for (String fieldname : multipleField) {
                param.setMasterid(baseDataDO.getId());
                param.setFieldname(fieldname.toUpperCase());
                List<String> listCode = this.baseDataDetailDao.queryValus(param);
                if (listCode != null && !listCode.isEmpty()) {
                    baseDataDO.put(fieldname, listCode);
                    continue;
                }
                baseDataDO.put(fieldname, null);
            }
        }
    }

    private void loadAllDataDetail(BaseDataDetailDO param, List<String> multipleField, List<BaseDataCacheDO> dataList) {
        List<BaseDataDetailDO> details = this.list(param);
        HashMap detailDataQueryMap = new HashMap(dataList.size());
        String fieldName = null;
        for (BaseDataDetailDO detailDO : details) {
            if (detailDataQueryMap.get(detailDO.getMasterid()) == null) {
                detailDataQueryMap.put(detailDO.getMasterid(), new HashMap());
            }
            fieldName = detailDO.getFieldname().toLowerCase();
            Map map = (Map)detailDataQueryMap.get(detailDO.getMasterid());
            if (map.get(fieldName) == null) {
                map.put(fieldName, new ArrayList());
            }
            ((List)map.get(fieldName)).add(detailDO.getFieldvalue());
        }
        Map subMap = null;
        for (BaseDataDO baseDataDO : dataList) {
            subMap = (Map)detailDataQueryMap.get(baseDataDO.getId());
            for (String fieldname : multipleField) {
                if (subMap == null) {
                    baseDataDO.put(fieldname, null);
                    continue;
                }
                List listCode = (List)subMap.get(fieldname);
                if (listCode != null && !listCode.isEmpty()) {
                    baseDataDO.put(fieldname, (Object)listCode);
                    continue;
                }
                baseDataDO.put(fieldname, null);
            }
        }
    }
}

