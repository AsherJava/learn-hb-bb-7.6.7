/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.context.EnvCenter
 *  com.jiuqi.gcreport.listedcompanyauthz.api.FListedCompanyClient
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyAuthzParam
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyParam
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiImplicitParam
 *  io.swagger.annotations.ApiImplicitParams
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiResponse
 *  io.swagger.annotations.ApiResponses
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.listedcompanyauthz.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.context.EnvCenter;
import com.jiuqi.gcreport.listedcompanyauthz.api.FListedCompanyClient;
import com.jiuqi.gcreport.listedcompanyauthz.base.BeanUtil;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyAuthzParam;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyParam;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam;
import com.jiuqi.nvwa.sf.anno.Licence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags={"\u4e0a\u5e02\u516c\u53f8\u4e1a\u52a1API"})
@RestController
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.listedcompany")
public class ListedCompanyController
implements FListedCompanyClient {
    @Autowired
    private FListedCompanyAuthzService authzService;
    @Autowired
    private FListedCompanyService companyService;

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u6240\u6709\u4e0a\u5e02\u516c\u53f8\u5217\u8868")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u5217\u8868")})
    public BusinessResponseEntity<List<ListedCompanyVO>> getListedCompanyDatas(ListedCompanyParam param) {
        List<ListedCompanyEO> list = this.companyService.query(BeanUtil.companyParam2VO(param));
        return BusinessResponseEntity.ok(BeanUtil.companyEO2VO(list));
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636eID\u83b7\u53d6\u4e0a\u5e02\u516c\u53f8\u4fe1\u606f")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u4fe1\u606f")})
    @ApiImplicitParams(value={@ApiImplicitParam(name="id", value="\u4e0a\u5e02\u516c\u53f8\u6570\u636e\u4e3b\u952e", required=true)})
    public BusinessResponseEntity<ListedCompanyVO> getListedCompanyByID(String id) {
        if (StringUtils.isEmpty(id)) {
            return BusinessResponseEntity.error((String)"\u6570\u636e\u4e3b\u952e\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        ListedCompanyEO eo = this.companyService.get(id);
        return BusinessResponseEntity.ok((Object)BeanUtil.companyEO2VO(eo));
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u4fdd\u5b58\u4e0a\u5e02\u516c\u53f8\u4fe1\u606f")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u4fe1\u606f")})
    public BusinessResponseEntity<List<ListedCompanyVO>> saveListedCompany(List<ListedCompanyVO> params) {
        boolean save = this.companyService.save(params);
        if (save) {
            return BusinessResponseEntity.ok(params);
        }
        return BusinessResponseEntity.error((String)JsonUtils.writeValueAsString(params));
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636eID\u5220\u9664\u4e0a\u5e02\u516c\u53f8")
    @ApiResponses(value={@ApiResponse(code=200, message="\u5220\u9664\u4e0a\u5e02\u516c\u53f8\u6267\u884c\u7ed3\u679c")})
    @ApiImplicitParams(value={@ApiImplicitParam(name="ids", value="\u4e0a\u5e02\u516c\u53f8\u6570\u636e\u4e3b\u952e\u96c6\u5408(\u7528,\u5206\u5272)", required=true)})
    public BusinessResponseEntity<String> deleteListedCompany(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return BusinessResponseEntity.error((String)"\u5220\u9664\u4e3b\u952e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String[] idList = ids.split(",");
        int i = 0;
        for (String id : idList) {
            ListedCompanyEO eo = new ListedCompanyEO();
            eo.setId(id);
            i += this.companyService.delete(eo);
        }
        if (i > 0) {
            return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
        }
        return BusinessResponseEntity.error((String)"\u5220\u9664\u5931\u8d25");
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u6709\u6743\u9650\u7684\u6240\u6709\u4e0a\u5e02\u516c\u53f8\u5217\u8868")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u5217\u8868")})
    public BusinessResponseEntity<List<ListedCompanyVO>> getListedCompanyByLoginUser(ListedCompanyParam param) {
        ListedCompanyVO paramVo = BeanUtil.companyParam2VO(param);
        paramVo.setUserName(EnvCenter.getCurrentUser().getName());
        List<ListedCompanyEO> list = this.companyService.query(paramVo);
        return BusinessResponseEntity.ok(BeanUtil.companyEO2VO(list));
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u5f53\u524d\u4e0a\u5e02\u516c\u53f8\u6240\u6709\u6743\u9650\u7528\u6237\u5217\u8868")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u5217\u8868")})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> getListedCompanyAuthzs(ListedCompanyAuthzParam param) {
        List<ListedCompanyAuthzVO> list = this.authzService.query(BeanUtil.authzParam2VO(param));
        return BusinessResponseEntity.ok(list);
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u4fdd\u5b58\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u4fe1\u606f")
    @ApiResponses(value={@ApiResponse(code=200, message="\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u4fe1\u606f")})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> saveListedCompanyAuthz(List<ListedCompanyAuthzVO> vos) {
        boolean save = this.authzService.save(vos);
        if (save) {
            return BusinessResponseEntity.ok();
        }
        return BusinessResponseEntity.error((String)JsonUtils.writeValueAsString(vos));
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636eID\u5220\u9664\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u4fe1\u606f")
    @ApiResponses(value={@ApiResponse(code=200, message="\u5220\u9664\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u4fe1\u606f\u6267\u884c\u7ed3\u679c")})
    @ApiImplicitParams(value={@ApiImplicitParam(name="ids", value="\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u6570\u636e\u4e3b\u952e\u96c6\u5408(\u7528,\u5206\u5272)", required=true)})
    public BusinessResponseEntity<String> deleteListedCompanyAuthz(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return BusinessResponseEntity.error((String)"\u5220\u9664\u4e3b\u952e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String[] idList = ids.split(",");
        int i = 0;
        for (String id : idList) {
            ListedCompanyAuthzEO eo = new ListedCompanyAuthzEO();
            eo.setId(id);
            i += this.authzService.delete(eo);
        }
        if (i > 0) {
            return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
        }
        return BusinessResponseEntity.error((String)"\u5220\u9664\u5931\u8d25");
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u8bf7\u4f7f\u7528\u901a\u7528\u5bfc\u5165\uff08\u5177\u4f53\u8054\u7cfb\u738b\u7ee7\u7ea2\uff09")
    @ApiImplicitParams(value={@ApiImplicitParam(name="executor", value="ListedCompanyImportExcutor"), @ApiImplicitParam(name="sn", value="1.0"), @ApiImplicitParam(name="importParam", value="\u5355\u4f4d\u4ee3\u7801(\u6682\u4e0d\u4f7f\u7528)")})
    public BusinessResponseEntity<String> ImportFile(String orgid, MultipartFile file) {
        throw new RuntimeException("\u8bf7\u4f7f\u7528\u901a\u7528\u5bfc\u5165\u5bfc\u51fa\u529f\u80fd");
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u8bf7\u4f7f\u7528\u901a\u7528\u5bfc\u51fa\uff08\u5177\u4f53\u8054\u7cfb\u738b\u7ee7\u7ea2\uff09")
    @ApiImplicitParams(value={@ApiImplicitParam(name="executor", value="ListedCompanyExportExcutor"), @ApiImplicitParam(name="sn", value="1.0"), @ApiImplicitParam(name="importParam", value="\u5355\u4f4d\u4ee3\u7801")})
    public void exportFile(String orgid, HttpServletResponse response) throws IOException {
        throw new RuntimeException("\u8bf7\u4f7f\u7528\u901a\u7528\u5bfc\u5165\u5bfc\u51fa\u529f\u80fd");
    }

    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u6839\u636e\u5355\u4f4d\u83b7\u53d6\u6240\u6709\u7528\u6237\u5217\u8868")
    @ApiResponses(value={@ApiResponse(code=200, message="\u7528\u6237\u5217\u8868")})
    public BusinessResponseEntity<List<ListedCompanyAuthzVO>> getUserByOrg(UserQueryParam param) {
        List<ListedCompanyAuthzVO> list = this.authzService.queryUserByOrgs(param);
        return BusinessResponseEntity.ok(list);
    }
}

