/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
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
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
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
public class OrgDuplicateInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-duplicate";
    }

    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u91cd\u590d";
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
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        String formatSQLDate = InspectOrgUtils.getDateFormat(version.getValidTime());
        ArrayList<Map> cols = new ArrayList<Map>();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u7236\u7ea7\u4ee3\u7801").put("key", (Object)"PARENTCODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8def\u5f84").put("key", (Object)"PARENTS").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"VALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"INVALIDTIME").toMap());
        String sql = "select id, code, name,parentCode,parents,VALIDTIME,INVALIDTIME from " + orgType + " b  where b.CODE in (select a.code from " + orgType + " a    where a.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < a.INVALIDTIME group by a.code having count(a.code) > 1)  and b.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < b.INVALIDTIME ";
        List rows = this.jdbcTemplate.queryForList(sql);
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

