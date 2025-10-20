/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.workingpaper.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeDelParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO;
import com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO;
import com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.workingpaper.api.WorkingPaperClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface WorkingPaperClient {
    public static final String WORKPAPER_API_BASE_PATH = "/api/gcreport/v1/workingpaper";
    public static final String INPUTADJUST_API_BASE_PATH = "/api/gcreport/v1/workingpaper/ryInputAdjust";
    public static final String RYORG_API_BASE_PATH = "/api/gcreport/v1/workingpaper/ryOrg";
    public static final String ORGFILTER_API_BASE_PATH = "/api/gcreport/v1/workingpaper/orgfilter";
    public static final String QUERY_API_BASE_PATH = "/api/gcreport/v1/workingpaper/orgfilter/scheme";
    public static final String ORG_ENUM_API_BASE_PATH = "/api/gcreport/v1/workingpaper/orgfilter/enums";

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperHeaderVO"})
    public BusinessResponseEntity<WorkingPaperTableVO> getWorkingPaperHeaderVO(@RequestBody WorkingPaperQueryCondition var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperDataVO"})
    public void getWorkingPaperDataVO(HttpServletRequest var1, HttpServletResponse var2, @RequestBody WorkingPaperQueryCondition var3) throws IOException;

    @GetMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperQueryWays/{workingPaperTypeCode}"})
    public BusinessResponseEntity<WorkingPaperQueryWayVO> getWorkPaperQueryWays(@PathVariable(value="workingPaperTypeCode") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/addWorkPaperQueryWay"})
    public BusinessResponseEntity<WorkingPaperQueryWayItemVO> addOrUpdateWorkPaperQueryWay(@RequestBody WorkingPaperQueryWayItemVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/deleteWorkPaperQueryWay/{currWayId}"})
    public BusinessResponseEntity<Boolean> deleteWorkPaperQueryWay(@PathVariable(value="currWayId") String var1);

    @GetMapping(value={"/api/gcreport/v1/workingpaper/queryWorkPaperQueryWay/{currWayId}"})
    public BusinessResponseEntity<WorkingPaperQueryWayItemVO> queryWorkPaperQueryWay(@PathVariable(value="currWayId") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/exchangeSortWorkPaperQueryWay/{currWayId}/{exchangeWayId}"})
    public BusinessResponseEntity<Boolean> exchangeSortWorkPaperQueryWay(@PathVariable(value="currWayId") String var1, @PathVariable(value="exchangeWayId") String var2);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperPentrationDatas"})
    public BusinessResponseEntity<Object> getWorkPaperPentrationDatas(HttpServletRequest var1, HttpServletResponse var2, @RequestBody WorkingPaperPentrationQueryCondtion var3);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getSubjectZbPentrateParams"})
    public BusinessResponseEntity<SubjectZbPentrationParamsVO> getSubjectZbPentrateParams(@RequestParam(value="zbBoundIndexPath", required=false) String var1, @RequestBody WorkingPaperQueryCondition var2);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperRyPentrationDatas"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperRyPentrationDatas(@RequestBody WorkingPaperPentrationQueryCondtion var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperDxAndRyPentrationDatas"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperDxAndRyPentrationDatas(HttpServletRequest var1, HttpServletResponse var2, @RequestBody WorkingPaperPentrationQueryCondtion var3);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getWorkPaperUnOffsetPentrationDatas"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperUnOffsetPentrationDatas(HttpServletRequest var1, HttpServletResponse var2, @RequestBody WorkingPaperPentrationQueryCondtion var3);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/checkOrgIdSupSubRelation"})
    public BusinessResponseEntity<String> checkOrgIdSupSubRelation(@RequestBody WorkingPaperQueryCondition var1);

    @GetMapping(value={"/api/gcreport/v1/workingpaper/jzdfWorkpaperColumnSelect"})
    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryJzdfWorkpaperColumnSelect();

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getSystemId"})
    public BusinessResponseEntity<WorkingPaperTableVO> getSystemId(@RequestBody WorkingPaperQueryCondition var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/getSystemIdBySchemeID/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<ConsolidatedOptionVO> getSystemIdBySchemeID(@PathVariable(value="schemeId") String var1, @PathVariable(value="periodStr") String var2);

    @GetMapping(value={"/api/gcreport/v1/workingpaper/viewKey/{taskId}/{orgType}"})
    public BusinessResponseEntity<String> getViewKeyByTaskIdAndOrgType(@PathVariable(value="taskId") String var1, @PathVariable(value="orgType") String var2);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/scheme/add"})
    public BusinessResponseEntity<String> addQueryScheme(@RequestBody ArbitrarilyMergeQuerySchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/scheme/update"})
    public BusinessResponseEntity<String> updateQueryScheme(@RequestBody ArbitrarilyMergeQuerySchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/scheme/del/{id}"})
    public BusinessResponseEntity<String> delQuerySchemeById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/scheme/get/{id}"})
    public BusinessResponseEntity<ArbitrarilyMergeQuerySchemeVO> getQuerySchemeById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/scheme/getList/{resourceId}"})
    public BusinessResponseEntity<List<ArbitrarilyMergeQuerySchemeVO>> getQuerySchemeByResourceId(@PathVariable(value="resourceId") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/add"})
    public BusinessResponseEntity<String> addOrgFilterSetting(@RequestBody ArbitrarilyMergeOrgFilterSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/update"})
    public BusinessResponseEntity<String> updateOrgFilterSetting(@RequestBody ArbitrarilyMergeOrgFilterSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/del/{id}"})
    public BusinessResponseEntity<String> delOrgFilterSettingById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/get/{id}"})
    public BusinessResponseEntity<ArbitrarilyMergeOrgFilterSettingVO> getOrgFilterSettingById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/getList"})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterSettingVO>> getOrgFilterSettingAll();

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/getEnableList"})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterSettingVO>> getEnableList();

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/updOrder"})
    public BusinessResponseEntity<String> getEnableList(@RequestBody List<ArbitrarilyMergeOrgFilterSettingVO> var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/getExpressList/{id}"})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO>> getExpressList(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/assemblyFormula"})
    public BusinessResponseEntity<String> assemblyFormula(@RequestBody List<ArbitrarilyMergeOrgFilterSettingVO> var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/filterGcOrg"})
    public BusinessResponseEntity<Set<String>> executeFormula(@RequestBody FilteringRequestParam var1);

    @GetMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/enums/getDataType"})
    public BusinessResponseEntity<Object> getDataType();

    @GetMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/enums/getControlType"})
    public BusinessResponseEntity<Object> getDataControlType();

    @GetMapping(value={"/api/gcreport/v1/workingpaper/orgfilter/enums/getDataSourceType"})
    public BusinessResponseEntity<Object> getDataSourceType();

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryInputAdjust/add"})
    public BusinessResponseEntity<Object> addRyInputAdjust(@RequestBody List<List<ArbitrarilyMergeInputAdjustVO>> var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryInputAdjust/delete"})
    public BusinessResponseEntity<String> deleteRyOffsetEntrysByMrecid(@RequestBody ArbitrarilyMergeDelParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryInputAdjust/query"})
    public BusinessResponseEntity<List<ArbitrarilyMergeInputAdjustVO>> queryRyDetailByMrecid(@RequestBody ArbitrarilyMergeInputAdjustQueryCondi var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryInputAdjust/ryOffsetEntryByMap"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getRyOffsetEntry(@RequestBody WorkingPaperPentrationQueryCondtion var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryInputAdjust/ryOffsetEntryOther"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getRyOffsetEntryOther(@RequestBody WorkingPaperPentrationQueryCondtion var1);

    @PostMapping(value={"/api/gcreport/v1/workingpaper/ryOrg/getMergeRangeOrgByCodeWithoutChaE"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> getMergeRangeOrgsWithOutCE(@RequestBody ArbitrarilyMergeOrgParamVO var1);
}

