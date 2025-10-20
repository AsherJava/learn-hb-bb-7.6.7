/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.journalsingle.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailDelCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition;
import com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalPageVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.journalsingle.api.JournalSingleClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface JournalSingleClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/journalSingle";

    @PostMapping(value={"/api/gcreport/v1/journalSingle/add/Merge"})
    public BusinessResponseEntity<Object> addJournalDetailMerge(@RequestBody List<List<JournalSingleVO>> var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/add/Single"})
    public BusinessResponseEntity<Object> addJournalDetailSingle(@RequestBody List<List<JournalSingleVO>> var1, HttpServletRequest var2);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/queryByPageCondi"})
    public BusinessResponseEntity<JournalPageVO> queryByPageCondi(@RequestBody JournalDetailCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/queryByCondi"})
    public BusinessResponseEntity<List<JournalSingleVO>> queryByCondi(@RequestBody JournalDetailCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/queryDetailByID"})
    public BusinessResponseEntity<List<JournalSingleVO>> queryDetailByID(@RequestBody JournalDetailCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/SingleDelete"})
    public BusinessResponseEntity<Object> SingleDeleteByMrecid(@RequestBody JournalDetailDelCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/queryDetailByDims"})
    public BusinessResponseEntity<List<JournalEnvContextVO>> queryJournalByDims(@RequestParam(value="orgid") String var1, @RequestParam(value="periodValue") String var2, @RequestParam(value="gcorgtype") String var3, @RequestParam(value="taskid") String var4, @RequestParam(value="schemeid") String var5, @RequestParam(value="adjust") String var6);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/postData"})
    public BusinessResponseEntity<String> postData(@RequestBody JournalSinglePostCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/getJournalWorkPaperDataVo"})
    public BusinessResponseEntity<List<JournalWorkPaperDataVO>> getJournalWorkPaperData(@RequestBody JournalDetailCondition var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingle/getPenerationData"})
    public BusinessResponseEntity<List<JournalSingleVO>> getPenerationData(@RequestBody JournalDetailCondition var1);
}

