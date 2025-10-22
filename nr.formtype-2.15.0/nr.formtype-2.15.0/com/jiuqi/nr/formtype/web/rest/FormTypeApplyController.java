/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.formtype.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.internal.impl.FormTypeBaseDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.formtype.web.vo.OrgFormTypeZbVO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/formtype/"})
@Api(tags={"\u62a5\u8868\u7c7b\u578b\u7ec4\u7ec7\u673a\u6784\u6269\u5c55\u670d\u52a1"})
public class FormTypeApplyController {
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private FormTypeBaseDataHelper formTypeBaseDataHelper;
    @Autowired
    private IFormTypeService iFormTypeService;
    private static final Logger logger = LoggerFactory.getLogger(FormTypeApplyController.class);

    @ApiOperation(value="\u662f\u5426\u542f\u7528\u56fd\u8d44\u59d4\u6a21\u5f0f")
    @GetMapping(value={"apply/enable/formtype"})
    public boolean enableNrFormTypeMgr() {
        return this.iFormTypeApplyService.enableNrFormTypeMgr();
    }

    @ApiOperation(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5")
    @GetMapping(value={"apply/get/zb/{orgName}"})
    public OrgFormTypeZbVO getFormTypeZb(@PathVariable String orgName) {
        ZB zb = this.iFormTypeApplyService.getFormTypeZb(orgName);
        boolean exsitData = this.formTypeBaseDataHelper.checkOrgExsitData(orgName);
        return new OrgFormTypeZbVO(orgName, exsitData, zb);
    }

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5")
    @PostMapping(value={"apply/add/zb"})
    public String addFormTypeZb(@RequestBody OrgFormTypeZbVO vo) throws JQException {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(vo.getOrgName());
        PageVO categoryPage = this.orgCategoryClient.list(orgCategoryDO);
        if (categoryPage.getTotal() == 0) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_ONTEXISTS_ERROR);
        }
        OrgCategoryDO orgCategory = (OrgCategoryDO)categoryPage.getRows().get(0);
        ZB zb = orgCategory.getZbByName("BBLX");
        if (null == zb) {
            zb = this.createBBLXZb(vo);
            orgCategory.syncZb(zb);
            R result = this.orgCategoryClient.update(orgCategory);
            logger.info("\u7ec4\u7ec7\u673a\u6784[{}]\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]", (Object)vo.getOrgName(), (Object)vo.getReltablename());
            if (0 != result.getCode()) {
                logger.error("\u7ec4\u7ec7\u673a\u6784[{}]\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]\u5931\u8d25\uff1a{}", vo.getOrgName(), vo.getReltablename(), result.getMsg());
                throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_ADD_BBLX_ERROR, result.getMsg());
            }
            return zb.getId().toString();
        }
        return this.updateFormTypeZb(orgCategory, vo);
    }

    private ZB createBBLXZb(OrgFormTypeZbVO vo) {
        ZB zbDTO = new ZB();
        zbDTO.setId(UUID.randomUUID());
        zbDTO.setName(StringUtils.hasText(vo.getName()) ? vo.getName() : "BBLX");
        zbDTO.setTitle(StringUtils.hasText(vo.getTitle()) ? vo.getTitle() : "\u62a5\u8868\u7c7b\u578b");
        zbDTO.setDatatype(Integer.valueOf(2));
        zbDTO.setPrecision(Integer.valueOf(60));
        zbDTO.setRelatetype(Integer.valueOf(1));
        zbDTO.setReltablename(vo.getReltablename());
        zbDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        zbDTO.setSolidityflag(Integer.valueOf(1));
        this.setDefualtVal(zbDTO, vo);
        zbDTO.setRequiredflag(Integer.valueOf(1));
        return zbDTO;
    }

    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5")
    @PostMapping(value={"apply/update/zb"})
    public String updateFormTypeZb(@RequestBody OrgFormTypeZbVO vo) throws JQException {
        this.checkUpdataFormTypeZb(vo.getOrgName());
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(vo.getOrgName());
        PageVO categoryPage = this.orgCategoryClient.list(orgCategoryDO);
        if (categoryPage.getTotal() == 0) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_ONTEXISTS_ERROR);
        }
        OrgCategoryDO orgCategory = (OrgCategoryDO)categoryPage.getRows().get(0);
        return this.updateFormTypeZb(orgCategory, vo);
    }

    private String updateFormTypeZb(OrgCategoryDO orgCategory, OrgFormTypeZbVO vo) throws JQException {
        List zbs = orgCategory.getZbs();
        String zbid = null;
        for (ZB zb : zbs) {
            if (!zb.getName().equals(vo.getName())) continue;
            zb.setReltablename(vo.getReltablename());
            this.setDefualtVal(zb, vo);
            zbid = zb.getId().toString();
            orgCategory.syncZb(zb);
            break;
        }
        R result = this.orgCategoryClient.update(orgCategory);
        logger.info("\u7ec4\u7ec7\u673a\u6784[{}]\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]", (Object)vo.getOrgName(), (Object)vo.getReltablename());
        if (0 != result.getCode()) {
            logger.error("\u7ec4\u7ec7\u673a\u6784[{}]\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]\u5931\u8d25\uff1a{}", vo.getOrgName(), vo.getReltablename(), result.getMsg());
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_UPDATE_BBLX_ERROR, result.getMsg());
        }
        return zbid;
    }

    private void setDefualtVal(ZB zb, OrgFormTypeZbVO vo) {
        if (StringUtils.hasText(vo.getDefaultValue())) {
            zb.setDefaultVal(vo.getDefaultValue());
        } else {
            List<FormTypeDataDefine> allDatas = this.iFormTypeService.queryFormTypeDatas(vo.getReltablename());
            for (FormTypeDataDefine data : allDatas) {
                if (UnitNature.JCFHB != data.getUnitNatrue()) continue;
                zb.setDefaultVal(data.getCode());
                break;
            }
        }
    }

    private void checkUpdataFormTypeZb(String orgName) throws JQException {
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(orgName);
        if (null == formTypeZb) {
            return;
        }
        if (this.formTypeBaseDataHelper.checkOrgExsitData(orgName)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_UPDATE_BBLX_ED);
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5")
    @PostMapping(value={"apply/delete/zb"})
    public void deleteFormTypeZb(@RequestBody OrgFormTypeZbVO vo) throws JQException {
        this.checkDeleteFormTypeZb(vo.getOrgName());
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(vo.getOrgName());
        PageVO categoryPage = this.orgCategoryClient.list(orgCategoryDO);
        if (categoryPage.getTotal() == 0) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_REMOVE_BBLX_ERROR);
        }
        OrgCategoryDO orgCategory = (OrgCategoryDO)categoryPage.getRows().get(0);
        List zbs = orgCategory.getZbs();
        for (ZB zb : zbs) {
            if (!zb.getName().equals(vo.getName())) continue;
            zbs.remove(zb);
            break;
        }
        orgCategory.setExtinfo(JSONObject.valueToString((Object)orgCategory.getZbs()));
        R result = this.orgCategoryClient.update(orgCategory);
        logger.info("\u7ec4\u7ec7\u673a\u6784[{}]\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]", (Object)vo.getOrgName(), (Object)vo.getReltablename());
        if (0 != result.getCode()) {
            logger.error("\u7ec4\u7ec7\u673a\u6784[{}]\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5[{}]\u5931\u8d25\uff1a{}", vo.getOrgName(), vo.getReltablename(), result.getMsg());
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_DELETE_BBLX_ERROR, result.getMsg());
        }
    }

    private void checkDeleteFormTypeZb(String orgName) throws JQException {
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(orgName);
        if (null == formTypeZb) {
            return;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(orgName);
        PageVO list = this.orgDataClient.list(orgDTO);
        List rows = list.getRows();
        for (OrgDO orgDO : rows) {
            if (!unitNatureGetter.isAutoGenUnitCode(orgDO, formTypeZb)) continue;
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ORG_DELETE_BBLX_ERROR);
        }
    }

    @ApiOperation(value="\u7ec4\u7ec7\u673a\u6784\u68c0\u67e5\u6807\u8bc6\u662f\u5426\u91cd\u590d")
    @GetMapping(value={"apply/org/check/{orgName}/{zbcode}"})
    public boolean checkZbCode(@PathVariable String orgName, @PathVariable String zbcode) {
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        PageVO pageVO = this.orgCategoryClient.list(param);
        if (pageVO != null && pageVO.getTotal() > 0) {
            OrgCategoryDO orgCategory = (OrgCategoryDO)pageVO.getRows().get(0);
            return null != orgCategory.getZbByName(zbcode);
        }
        return false;
    }
}

