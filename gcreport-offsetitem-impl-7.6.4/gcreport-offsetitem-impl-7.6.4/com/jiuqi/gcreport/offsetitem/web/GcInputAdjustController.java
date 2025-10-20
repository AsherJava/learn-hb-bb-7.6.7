/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.offsetitem.api.GcInputAdjustClient
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.offsetitem.api.GcInputAdjustClient;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcInputAdjustController
implements GcInputAdjustClient {
    @Autowired
    private GcInputAdjustService inputAdjustService;

    public BusinessResponseEntity<Object> addInputAdjust(List<List<GcInputAdjustVO>> batchlist) {
        this.inputAdjustService.addInputAdjust(batchlist);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<GcInputAdjustVO>> queryDetailByID(GcInputAdjustQueryCondi condi) {
        return BusinessResponseEntity.ok(this.inputAdjustService.queryDetailByMrecid(condi));
    }

    public BusinessResponseEntity<List<List<GcInputAdjustVO>>> queryDetailByMRecidList(GcInputAdjustQueryCondi condi) {
        return BusinessResponseEntity.ok(this.inputAdjustService.queryDetailByMrecidList(condi));
    }

    public BusinessResponseEntity<Object> deleteBySrcId(GcInputAdjustDelCondi condi) {
        this.inputAdjustService.deleteBySrcId(condi);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Map<String, Map<String, Object>>> queryByGroupIds(Map<String, Object> jsonObject, HttpServletRequest request) {
        HashSet<String> groupIds = new HashSet<String>((Collection)jsonObject.get("groupIds"));
        String header = request.getHeader("sys");
        if (!"njdlkadjq".equals(header) || CollectionUtils.isEmpty(groupIds)) {
            return BusinessResponseEntity.ok(new HashMap());
        }
        Map<String, Map<String, Object>> stringMapMap = this.inputAdjustService.queryByGroupIds(groupIds);
        return BusinessResponseEntity.ok(stringMapMap);
    }

    public BusinessResponseEntity<GcInputAdjustVO> consFormulaCalc(GcInputAdjustVO gcInputAdjustVO) {
        return BusinessResponseEntity.ok((Object)this.inputAdjustService.consFormulaCalc(gcInputAdjustVO));
    }
}

