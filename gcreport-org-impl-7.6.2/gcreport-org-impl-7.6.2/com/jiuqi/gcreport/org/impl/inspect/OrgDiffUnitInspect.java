/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrgDiffUnitInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-diffunit";
    }

    public String getTitle() {
        return "\u5dee\u989d\u5355\u4f4d";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        if (StringUtils.isEmpty((CharSequence)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((CharSequence)orgVersionName)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        String formatSQLDate = InspectOrgUtils.getDateFormat(version.getValidTime());
        ArrayList<Map> cols = new ArrayList<Map>();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5dee\u989d\u5355\u4f4d").put("key", (Object)"DIFFUNITID").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"VALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"INVALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8be6\u60c5").put("key", (Object)"INFO").toMap());
        String sql = "select id,code,name,DIFFUNITID,validtime,invalidtime from " + orgType + " b where CODE in (    select distinct PARENTCODE    from " + orgType + " a  where a.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < a.INVALIDTIME) and b.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < b.INVALIDTIME  and DIFFUNITID is null or DIFFUNITID =''";
        String sql2 = "select m.id, m.code, m.name,m.DIFFUNITID,m.VALIDTIME,m.INVALIDTIME  from " + orgType + "  m join " + orgType + " n on m.DIFFUNITID = n.CODE where n.PARENTCODE <> m.code\n and m.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < m.INVALIDTIME ";
        List rows = this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            HashMap<String, String> org = new HashMap<String, String>();
            org.put("ID", rs.getString(1));
            org.put("CODE", rs.getString(2));
            org.put("NAME", rs.getString(3));
            org.put("DIFFUNITID", rs.getString(4));
            org.put("VALIDTIME", rs.getString(5));
            org.put("INVALIDTIME", rs.getString(6));
            org.put("INFO", "\u5dee\u989d\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
            return org;
        });
        List rows2 = this.jdbcTemplate.query(sql2, (rs, rowNum) -> {
            HashMap<String, String> org = new HashMap<String, String>();
            org.put("ID", rs.getString(1));
            org.put("CODE", rs.getString(2));
            org.put("NAME", rs.getString(3));
            org.put("DIFFUNITID", rs.getString(4));
            org.put("VALIDTIME", rs.getString(5));
            org.put("INVALIDTIME", rs.getString(6));
            org.put("INFO", "\u5dee\u989d\u5355\u4f4d\u4e0d\u662f\u76f4\u63a5\u4e0b\u7ea7\u5355\u4f4d");
            return org;
        });
        rows.addAll(rows2);
        HashMap<String, List> result = new HashMap<String, List>();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty((Collection)rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeFix(Map<String, Object> params) {
        return InspectResultVO.unSupportResult();
    }
}

