/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.log.LogHelper
 *  org.apache.shiro.util.StringUtils
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.np.log.LogHelper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrgCurrencyInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-currency";
    }

    public String getTitle() {
        return "\u5e01\u79cd";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        if (!StringUtils.hasText((String)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ArrayList<Map> cols = new ArrayList<Map>();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u672c\u4f4d\u5e01").put("key", (Object)"CURRENCYID").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u62a5\u8868\u5e01\u79cd").put("key", (Object)"CURRENCYIDS").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"VALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"INVALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8be6\u60c5").put("key", (Object)"INFO").toMap());
        String currencySql = "select id, code, name,currencyid,currencyids,VALIDTIME,INVALIDTIME from " + orgType;
        List rows = this.jdbcTemplate.queryForList(currencySql).stream().filter(map -> {
            StringBuilder tips = new StringBuilder();
            String currencyids = (String)map.get("CURRENCYIDS");
            String currencyid = (String)map.get("CURRENCYID");
            Timestamp validtime = (Timestamp)map.get("VALIDTIME");
            Timestamp invalidtime = (Timestamp)map.get("INVALIDTIME");
            map.put("VALIDTIME", DateUtils.format((Date)validtime));
            map.put("INVALIDTIME", DateUtils.format((Date)invalidtime));
            boolean flag = false;
            if (!StringUtils.hasText((String)currencyids)) {
                tips.append("\u62a5\u8868\u5e01\u79cd\u4e3a\u7a7a\n");
                flag = true;
            } else {
                String[] split = currencyids.split(";");
                List<String> strings = Arrays.asList(split);
                tips.append("\u62a5\u8868\u5e01\u79cd\u4e0d\u5305\u542b\u672c\u4f4d\u5e01\n");
                boolean bl = flag = !strings.contains(currencyid);
            }
            if (!StringUtils.hasText((String)currencyid)) {
                tips.append("\u672c\u4f4d\u5e01\u4e3a\u7a7a\n");
                flag = true;
            }
            map.put("INFO", tips.toString());
            return flag;
        }).collect(Collectors.toList());
        HashMap<String, List<Object>> result = new HashMap<String, List<Object>>();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeFix(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        if (!StringUtils.hasText((String)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        String currencySql = "select id, code, name,currencyid,currencyids,VALIDTIME,INVALIDTIME from " + orgType + " where currencyid is not null or currencyid <> '' ";
        ArrayList values = new ArrayList();
        ArrayList logs = new ArrayList();
        this.jdbcTemplate.queryForList(currencySql).stream().forEach(map -> {
            StringBuilder tips = new StringBuilder();
            String id = (String)map.get("ID");
            String currencyids = (String)map.get("CURRENCYIDS");
            String currencyid = (String)map.get("CURRENCYID");
            String code = (String)map.get("CODE");
            boolean needFix = false;
            if (!StringUtils.hasText((String)currencyids)) {
                tips.append("\u62a5\u8868\u5e01\u79cd\u4e3a\u7a7a\n");
                currencyids = "";
                needFix = true;
            } else {
                String[] split = currencyids.split(";");
                List<String> strings = Arrays.asList(split);
                tips.append("\u62a5\u8868\u5e01\u79cd\u4e0d\u5305\u542b\u672c\u4f4d\u5e01\n");
                boolean bl = needFix = !strings.contains(currencyid);
            }
            if (needFix) {
                String newCurrencyids = currencyid + ";" + currencyids;
                values.add(new Object[]{newCurrencyids, id});
                logs.add(id);
            }
        });
        String fixSql = "update " + orgType + " set CURRENCYIDS = ? where ID = ? ";
        int[] ints = this.jdbcTemplate.batchUpdate(fixSql, values);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-\u5e01\u79cd\u6570\u636e-\u673a\u6784\u7c7b\u578b" + orgType), (String)"");
        HashMap result = new HashMap();
        vo.setResult(result);
        vo.setTaskState(TaskStateEnum.SUCCESS);
        InspectOrgUtils.clearOrgCache(orgType);
        return vo;
    }
}

