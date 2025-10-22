/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.onekeymerge.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GCDiffProcessClient {
    public static final String API_PATH = "/api/gcreport/v1/diffProcess/";

    @PostMapping(value={"/api/gcreport/v1/diffProcess/queryDiffProcessReports/{schemeId}/{dataTime}"})
    public BusinessResponseEntity<List<FormTreeVo>> queryDiffProcessReports(@PathVariable(value="schemeId") String var1, @PathVariable(value="dataTime") String var2);

    @PostMapping(value={"/api/gcreport/v1/diffProcess/queryDifferenceIntermediateDatas"})
    public BusinessResponseEntity<List<GcDiffProcessDataVO>> queryDifferenceIntermediateDatas(@RequestBody GcDiffProcessCondition var1);

    @PostMapping(value={"/api/gcreport/v1/diffProcess/transferGroupWithinToOutside"})
    public BusinessResponseEntity<String> transferGroupWithinToOutside(@RequestBody GcDiffProcessCondition var1);
}

