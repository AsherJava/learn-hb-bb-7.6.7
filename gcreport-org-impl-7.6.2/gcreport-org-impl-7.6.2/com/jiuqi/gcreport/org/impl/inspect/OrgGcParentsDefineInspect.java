/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  com.jiuqi.va.organization.service.impl.OrgDataServiceImpl
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.impl.OrgDataServiceImpl;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class OrgGcParentsDefineInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgDataServiceImpl orgDataService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;
    @Autowired
    private FGcOrgQueryDao queryDao;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-gcparents-define";
    }

    public String getTitle() {
        return "gcparents\u5b57\u6bb5\u5b9a\u4e49\u68c0\u67e5";
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgTypeParam = (String)params.get("orgType");
        ArrayList<Map> cols = new ArrayList<Map>();
        ArrayList rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b").put("key", (Object)"ORGTYPE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u9519\u8bef\u4fe1\u606f").put("key", (Object)"INFO").toMap());
        List list = this.jdbcTemplate.queryForList("select c.name from MD_ORG_CATEGORY c");
        String zbString = "{\"id\":\"25630113-65ae-46c2-a293-77e1a306fed9\", \"name\":\"GCPARENTS\",  \"title\":\"\u7236\u7ea7\u8def\u5f84(GC)\", \"datatype\":2, \"precision\":610 ,\"decimal\":0, \"relatetype\":1, \"requiredflag\":0,\"tenantName\": \"__default_tenant__\", \"uniqueflag\": 0,\"ordernum\": 29}";
        List orgTypes = list.stream().map(o -> (String)o.get("name")).collect(Collectors.toList());
        ZB zb = (ZB)JsonUtils.readValue((String)zbString, ZB.class);
        for (String orgType : orgTypes) {
            if (StringUtils.hasText(orgTypeParam) && !orgTypeParam.equalsIgnoreCase(orgType)) continue;
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setTenantName("__default_tenant__");
            orgCategoryDO.setName(orgType);
            OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
            OrgCategoryDO oldOrgCategoryDO = orgCategoryService.get(orgCategoryDO);
            ZB oldZ = oldOrgCategoryDO.getZbByName(zb.getName());
            if (oldZ != null) continue;
            HashMap<String, String> row = new HashMap<String, String>();
            row.put("ORGTYPE", orgType);
            row.put("INFO", "\u7f3a\u5c11gcparents");
            rows.add(row);
        }
        HashMap result = new HashMap();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    public InspectResultVO executeFix(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgTypeParam = (String)params.get("orgType");
        ArrayList<Map> cols = new ArrayList<Map>();
        ArrayList rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b").put("key", (Object)"ORGTYPE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u9519\u8bef\u4fe1\u606f").put("key", (Object)"INFO").toMap());
        String zbString = "{\"id\":\"25630113-65ae-46c2-a293-77e1a306fed9\", \"name\":\"GCPARENTS\",  \"title\":\"\u7236\u7ea7\u8def\u5f84(GC)\", \"datatype\":2, \"precision\":610 ,\"decimal\":0, \"relatetype\":1, \"requiredflag\":0,\"tenantName\": \"__default_tenant__\", \"uniqueflag\": 0,\"ordernum\": 29}";
        List list = this.jdbcTemplate.queryForList("select c.name from MD_ORG_CATEGORY c");
        List orgTypes = list.stream().map(o -> (String)o.get("name")).collect(Collectors.toList());
        ZB zb = (ZB)JsonUtils.readValue((String)zbString, ZB.class);
        for (String orgType : orgTypes) {
            if (StringUtils.hasText(orgTypeParam) && !orgTypeParam.equalsIgnoreCase(orgType)) continue;
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setTenantName("__default_tenant__");
            orgCategoryDO.setName(orgType);
            OrgCategoryClient orgCategoryClient = (OrgCategoryClient)SpringContextUtils.getBean(OrgCategoryClient.class);
            PageVO list1 = orgCategoryClient.list(orgCategoryDO);
            if (!CollectionUtils.isEmpty((Collection)list1.getRows())) {
                OrgCategoryDO oldOrgCategoryDO = (OrgCategoryDO)list1.getRows().get(0);
                ZB oldZ = ((OrgCategoryDO)list1.getRows().get(0)).getZbByName(zb.getName());
                if (oldZ != null) {
                    zb.setId(oldZ.getId());
                }
                oldOrgCategoryDO.syncZb(zb);
                R update = orgCategoryClient.update(oldOrgCategoryDO);
                if (update.getCode() != 0) {
                    HashMap<String, String> row = new HashMap<String, String>();
                    row.put("ORGTYPE", orgType);
                    row.put("INFO", "\u4fee\u590d\u5931\u8d25\uff1a" + update.getMsg());
                    rows.add(row);
                    LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-GCPARENTS\u5b9a\u4e49-\u673a\u6784\u7c7b\u578b" + orgType + "\u5f02\u5e38"), (String)update.getMsg());
                }
                LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-GCPARENTS\u5b9a\u4e49-\u673a\u6784\u7c7b\u578b" + orgType));
                continue;
            }
            HashMap<String, String> row = new HashMap<String, String>();
            row.put("ORGTYPE", orgType);
            row.put("INFO", "\u4fee\u590d\u5931\u8d25\uff1a\u8981\u4fee\u590d\u7684\u673a\u6784\u7c7b\u578b\u4e0d\u5b58\u5728");
            rows.add(row);
        }
        HashMap result = new HashMap();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }
}

