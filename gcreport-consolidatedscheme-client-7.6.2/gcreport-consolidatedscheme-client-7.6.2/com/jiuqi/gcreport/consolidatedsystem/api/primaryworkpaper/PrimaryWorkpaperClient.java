/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.consolidatedsystem.api.primaryworkpaper;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.primaryworkpaper.PrimaryWorkpaperClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface PrimaryWorkpaperClient {
    public static final String CONSOLIDATED_PRIMARTWORKPAPER_API = "/api/gcreport/v1/primaryWorkpaper";

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/addType"})
    public BusinessResponseEntity<PrimaryWorkpaperTypeVO> addPrimaryWorkpaperType(@RequestBody PrimaryWorkpaperTypeVO var1);

    @GetMapping(value={"/api/gcreport/v1/primaryWorkpaper/listTypeTree/{reportSystemId}"})
    public BusinessResponseEntity<List<PrimaryWorkpaperTypeVO>> listPrimaryWorkpaperTypeTree(@PathVariable(value="reportSystemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/deleteTypeById/{id}"})
    public BusinessResponseEntity<String> deletePrimaryWorkpaperTypeById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/updateType"})
    public BusinessResponseEntity<PrimaryWorkpaperTypeVO> updatePrimaryWorkpaperType(@RequestBody PrimaryWorkpaperTypeVO var1);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/{id}/move/{step}"})
    public BusinessResponseEntity<String> moveTypeTreeNode(@PathVariable(value="id") String var1, @PathVariable(value="step") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/listPrimarySettingDatas/{typeId}"})
    public BusinessResponseEntity<List<PrimaryWorkpaperSettingVO>> listPrimarySettingDatas(@PathVariable(value="typeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/getZbCodeToSubjectsMap/{systemId}"})
    public BusinessResponseEntity<Map<String, List<ConsolidatedSubjectVO>>> getZbCodeToSubjectsMap(@PathVariable(value="systemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/savePrimaryWorkpaperSets/{deleteIds}", "/api/gcreport/v1/primaryWorkpaper/savePrimaryWorkpaperSets/"})
    public BusinessResponseEntity<String> savePrimaryWorkpaperSets(@PathVariable(value="deleteIds", required=false) List<String> var1, @RequestBody List<PrimaryWorkpaperSettingVO> var2);

    @PostMapping(value={"/api/gcreport/v1/primaryWorkpaper/deletePrimaryWorkpaperSets"})
    public BusinessResponseEntity<String> deletePrimaryWorkpaperSetsByIds(@RequestBody List<String> var1);
}

