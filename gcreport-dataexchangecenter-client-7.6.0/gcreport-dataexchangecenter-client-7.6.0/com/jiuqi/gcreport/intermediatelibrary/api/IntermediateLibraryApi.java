/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.intermediatelibrary.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IntermediateLibraryApi {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/intermediate";

    @PostMapping(value={"/api/gcreport/v1/intermediate/addProgramme"})
    public BusinessResponseEntity<Object> addProgramme(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getProgrammeOfName"})
    public BusinessResponseEntity<Object> getProgrammeOfName(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/updateProgramme"})
    public BusinessResponseEntity<Object> updateProgramme(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/deleteProgramme"})
    public BusinessResponseEntity<Object> deleteProgramme(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getAllProgramme"})
    public BusinessResponseEntity<List<ILVO>> getAllProgramme();

    @PostMapping(value={"/api/gcreport/v1/intermediate/getProgrammeForId"})
    public BusinessResponseEntity<ILVO> getProgrammeForId(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getFieldOfProgrammeId"})
    public BusinessResponseEntity<ILFieldPageVO> getFieldOfProgrammeId(@RequestBody ILFieldCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/addProgrammeOfField"})
    public BusinessResponseEntity<Object> addProgrammeOfField(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/deleteProgrammeOfField"})
    public BusinessResponseEntity<Object> deleteProgrammeOfField(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/deleteAllProgrammeOfField"})
    public BusinessResponseEntity<Object> deleteAllProgrammeOfField(@RequestBody ILCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/programmeAsync"})
    public BusinessResponseEntity<Object> programmeAsync(@RequestBody String var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getFieldGroupMessage"})
    public BusinessResponseEntity<List<ILTreeVO>> getFieldGroupMessage(@RequestBody ILFieldCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getFieldForGroup"})
    public BusinessResponseEntity<ILFieldPageVO> getFieldForGroup(@RequestBody ILFieldCondition var1);

    @PostMapping(value={"/api/gcreport/v1/intermediate/getZbPickerList"})
    public BusinessResponseEntity<List<ZbPickerEntity>> getZbPickerList(@RequestBody ILFieldCondition var1);

    @GetMapping(value={"/api/gcreport/v1/intermediate/getTaskInfo"})
    public BusinessResponseEntity<Map<String, Object>> getTaskInfo(@RequestParam String var1);

    @GetMapping(value={"/api/gcreport/v1/intermediate/getAllSourceType"})
    public BusinessResponseEntity<List<Map<String, String>>> getAllSourceType();
}

