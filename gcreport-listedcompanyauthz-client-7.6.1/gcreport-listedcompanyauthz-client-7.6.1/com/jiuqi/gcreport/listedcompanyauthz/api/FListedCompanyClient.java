/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.listedcompanyauthz.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyAuthzParam;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyParam;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public interface FListedCompanyClient {
    public static final String LISTEDCOMPANY_PATH = "/api/gcreport/v1/listedcompany";
    public static final String LISTEDCOMPANY_CURRUSER_PATH = "/api/gcreport/v1/listedcompanybycurruser";
    public static final String LISTEDCOMPANY_AUTHZ_PATH = "/api/gcreport/v1/listedcompany/authz";

    @PostMapping(value={"/api/gcreport/v1/listedcompany"})
    public BusinessResponseEntity<List<ListedCompanyVO>> getListedCompanyDatas(@RequestBody ListedCompanyParam var1);

    @GetMapping(value={"/api/gcreport/v1/listedcompany/get/{id}"})
    public BusinessResponseEntity<ListedCompanyVO> getListedCompanyByID(@PathVariable(name="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/listedcompany/save"})
    public BusinessResponseEntity<List<ListedCompanyVO>> saveListedCompany(@RequestBody List<ListedCompanyVO> var1);

    @GetMapping(value={"/api/gcreport/v1/listedcompany/delete/{ids}"})
    public BusinessResponseEntity<String> deleteListedCompany(@PathVariable(name="ids") String var1);

    @PostMapping(value={"/api/gcreport/v1/listedcompanybycurruser"})
    public BusinessResponseEntity<List<ListedCompanyVO>> getListedCompanyByLoginUser(@RequestBody ListedCompanyParam var1);

    @PostMapping(value={"/api/gcreport/v1/listedcompany/authz"})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> getListedCompanyAuthzs(@RequestBody ListedCompanyAuthzParam var1);

    @PostMapping(value={"/api/gcreport/v1/listedcompany/authz/save"})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> saveListedCompanyAuthz(@RequestBody List<ListedCompanyAuthzVO> var1);

    @GetMapping(value={"/api/gcreport/v1/listedcompany/authz/delete/{ids}"})
    public BusinessResponseEntity<String> deleteListedCompanyAuthz(@PathVariable(name="ids") String var1);

    @GetMapping(value={"/api/gcreport/v1/listedcompany/authz/import/{orgid}", "/api/gcreport/v1/listedcompany/authz/import"})
    public BusinessResponseEntity<String> ImportFile(@PathVariable(name="orgid") String var1, MultipartFile var2);

    @GetMapping(value={"/api/gcreport/v1/listedcompany/authz/export/{orgid}", "/api/gcreport/v1/listedcompany/authz/export"})
    public void exportFile(@PathVariable(name="orgid") String var1, HttpServletResponse var2) throws IOException;

    @PostMapping(value={"/api/gcreport/v1/listedcompany/findUsers"})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> getUserByOrg(@RequestBody UserQueryParam var1);
}

