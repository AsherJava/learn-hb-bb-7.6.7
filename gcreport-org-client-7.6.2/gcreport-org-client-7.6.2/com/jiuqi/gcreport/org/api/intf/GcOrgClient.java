/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgEditVO;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcOrgClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgOperApi";

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/orgTree"})
    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgTree(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/orgCollection"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listOrg(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/orgChildren"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listOrgByParent(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/orgsWithCondition"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listOrgBySearch(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/get"})
    public BusinessResponseEntity<OrgToJsonVO> getOrgByCode(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/queryByCodes"})
    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgByCodes(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/add"})
    public BusinessResponseEntity<OrgToJsonVO> createOrg(@Valid @RequestBody GcOrgEditVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/update"})
    public BusinessResponseEntity<OrgToJsonVO> updateOrg(@Valid @RequestBody GcOrgEditVO var1) throws BusinessRuntimeException;

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/delete"})
    public BusinessResponseEntity<String> deleteOrg(@RequestBody GcOrgEditVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/deleteWithProgress"})
    public BusinessResponseEntity<String> deleteOrgWithProgress(@RequestBody GcOrgEditVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/batch"})
    public BusinessResponseEntity<OrgToJsonVO> batchOrg(@RequestBody GcOrgEditVO var1) throws BusinessRuntimeException;

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/generate"})
    public BusinessResponseEntity<Object> generateByFormula(@RequestBody GcOrgTypeCopyApiParam var1) throws BusinessRuntimeException;

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/generate/preview"})
    public BusinessResponseEntity<Object> generateByFormulaPreview(@RequestBody GcOrgTypeCopyApiParam var1) throws BusinessRuntimeException;

    @PostMapping(value={"/api/gcreport/v1/gcOrgOperApi/{orgType}/{yyyyTmmmm}"})
    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgSByBatchIDS(@PathVariable(value="orgType") String var1, @PathVariable(value="yyyyTmmmm") String var2, @RequestBody List<String> var3);
}

