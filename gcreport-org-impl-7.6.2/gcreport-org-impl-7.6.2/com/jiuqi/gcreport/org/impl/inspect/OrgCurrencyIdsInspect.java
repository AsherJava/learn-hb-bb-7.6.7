/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrgCurrencyIdsInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgCategoryService orgCategoryService;

    private GcOrgBaseTool getBaseOrgTool() {
        return GcOrgBaseTool.getInstance();
    }

    private GcOrgVerTool getOrgVerTool() {
        return GcOrgVerTool.getInstance();
    }

    private GcOrgMangerCenterTool getCenterTool(String orgType, String org_ver) {
        return GcOrgMangerCenterTool.getInstance(orgType, org_ver);
    }

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-currencyids";
    }

    public String getTitle() {
        return "\u62a5\u8868\u5e01\u79cd\u591a\u9009\u5347\u7ea7";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        ArrayList<Map> cols = new ArrayList<Map>();
        ArrayList rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u540d\u79f0").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8be6\u60c5").put("key", (Object)"INFO").toMap());
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setName(orgType);
        PageVO pageVO = this.orgCategoryService.list(categoryDO);
        if (!CollectionUtils.isEmpty((Collection)pageVO.getRows())) {
            for (OrgCategoryDO row : pageVO.getRows()) {
                ZB zbByName = row.getZbByName("CURRENCYIDS");
                if (zbByName == null || zbByName.getMultiple() != null && zbByName.getMultiple() == 1) continue;
                try {
                    this.fixDefine(row);
                    BusinessLogUtils.operate((String)"\u5408\u5e76\u68c0\u67e5", (String)(row.getName() + "\u4fee\u590d\u62a5\u8868\u5e01\u79cd\u591a\u9009\u5b9a\u4e49"), (String)"\u6210\u529f");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("CODE", row.getName());
                    data.put("NAME", row.getTitle());
                    data.put("INFO", "\u62a5\u8868\u5e01\u79cd\u5b9a\u4e49\u4e0d\u662f\u591a\u9009");
                    rows.add(data);
                }
            }
        }
        HashMap result = new HashMap();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeFix(Map<String, Object> params) {
        String orgType = (String)params.get("orgType");
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setName(orgType);
        PageVO pageVO = this.orgCategoryService.list(categoryDO);
        if (!CollectionUtils.isEmpty((Collection)pageVO.getRows())) {
            for (OrgCategoryDO row : pageVO.getRows()) {
                Integer multiple;
                if (!row.getName().toUpperCase().startsWith("MD_ORG")) {
                    return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + row.getName()));
                }
                ZB zbByName = row.getZbByName("CURRENCYIDS");
                if (zbByName == null || (multiple = zbByName.getMultiple()) != 1) continue;
                String sql = "select ID,CURRENCYIDS from " + row.getName();
                List maps = this.jdbcTemplate.queryForList(sql);
                List values = Collections.synchronizedList(new ArrayList());
                List existList = this.jdbcTemplate.queryForList("select MASTERID,FIELDVALUE from " + row.getName() + "_SUBLIST where FIELDNAME = 'CURRENCYIDS'");
                ConcurrentHashMap<String, String> existMap = new ConcurrentHashMap<String, String>();
                for (Map stringObjectMap : existList) {
                    String masterid = (String)stringObjectMap.get("MASTERID");
                    String filedValue = (String)stringObjectMap.get("FIELDVALUE");
                    existMap.put(masterid, masterid + "_" + filedValue);
                }
                maps.parallelStream().forEach(map -> {
                    Object orgid = map.get("ID");
                    String currencyids = (String)map.get("CURRENCYIDS");
                    if (!StringUtils.isEmpty((String)currencyids)) {
                        String[] strings;
                        for (String currency : strings = currencyids.split(";")) {
                            Object[] subData = new Object[]{UUIDUtils.newUUIDStr(), orgid, "CURRENCYIDS", currency, OrderNumUtil.getOrderNumByCurrentTimeMillis()};
                            if ((orgid + "_" + currency).equalsIgnoreCase((String)existMap.get(orgid))) continue;
                            values.add(subData);
                        }
                    }
                });
                String tableName = row.getName() + "_SUBLIST";
                String updateSql = "insert into " + tableName + " (ID, MASTERID, FIELDNAME, FIELDVALUE, ORDERNUM) values (?,?,?,?,?)";
                this.jdbcTemplate.batchUpdate(updateSql, values);
                InspectOrgUtils.clearOrgCache(row.getName());
                try {
                    LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-\u62a5\u8868\u5e01\u79cd\u6570\u636e-\u673a\u6784\u7c7b\u578b" + orgType), (String)"");
                }
                catch (Exception exception) {}
            }
        }
        InspectResultVO vo = new InspectResultVO();
        HashMap result = new HashMap();
        vo.setResult(result);
        vo.setTaskState(TaskStateEnum.SUCCESS);
        return vo;
    }

    private void fixDefine(OrgCategoryDO categoryDO) {
        ZB zbByName = categoryDO.getZbByName("CURRENCYIDS");
        if (null != zbByName) {
            zbByName.setMultiple(Integer.valueOf(1));
            zbByName.setRequiredflag(Integer.valueOf(0));
        }
        categoryDO.syncZb(zbByName);
        this.orgCategoryService.update(categoryDO);
    }
}

