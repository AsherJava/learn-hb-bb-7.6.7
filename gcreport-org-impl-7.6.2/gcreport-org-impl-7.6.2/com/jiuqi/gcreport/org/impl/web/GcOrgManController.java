/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.env.EnvCenter
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.gcreport.org.api.intf.GcOrgClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgEditVO
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam
 *  com.jiuqi.gcreport.org.api.vo.FrontEndParams
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.env.EnvCenter;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.org.api.intf.GcOrgClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgEditVO;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.vo.FrontEndParams;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgGenerateService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgManController
implements GcOrgClient {
    @Autowired
    private GcOrgGenerateService gcOrgGenerateService;
    private static final Logger logger = LoggerFactory.getLogger(GcOrgManController.class);

    GcOrgManController() {
    }

    private GcOrgMangerCenterTool getTool(String orgType, String org_ver) {
        return GcOrgMangerCenterTool.getInstance(orgType, org_ver);
    }

    public BusinessResponseEntity<OrgToJsonVO> getOrgByCode(GcOrgApiParam param) {
        try {
            return BusinessResponseEntity.ok((Object)this.getTool(param.getOrgType(), param.getOrgVerCode()).getOrgByCode(param.getOrgCode()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgByCodes(GcOrgApiParam param) {
        try {
            return BusinessResponseEntity.ok(this.getTool(param.getOrgType(), param.getOrgVerCode()).getOrgsByIds(param.getOrgCodes()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> listOrgBySearch(GcOrgApiParam param) {
        return BusinessResponseEntity.ok(this.getTool(param.getOrgType(), param.getOrgVerCode()).listOrgBySearch(param.getSearchText()));
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> listOrgByParent(GcOrgApiParam param) {
        return BusinessResponseEntity.ok(this.getTool(param.getOrgType(), param.getOrgVerCode()).listChildOrgByParent(param.getOrgParentCode()));
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgTree(GcOrgApiParam param) {
        List<OrgToJsonVO> orgTree = this.getTool(param.getOrgType(), param.getOrgVerCode()).getOrgTree(param.getOrgParentCode());
        return BusinessResponseEntity.ok(orgTree);
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> listOrg(GcOrgApiParam param) {
        return BusinessResponseEntity.ok(this.getTool(param.getOrgType(), param.getOrgVerCode()).listOrg());
    }

    public BusinessResponseEntity<OrgToJsonVO> createOrg(GcOrgEditVO vo) {
        GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), vo.getOrgVerCode());
        OrgToJsonVO orgByCode = tool.getOrgByCode(vo.getOrg().getCode());
        if (null != orgByCode) {
            return BusinessResponseEntity.error((String)"\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u91cd\u590d!");
        }
        tool.add(vo.getOrg());
        return BusinessResponseEntity.ok((Object)vo.getOrg());
    }

    public BusinessResponseEntity<OrgToJsonVO> updateOrg(GcOrgEditVO vo) throws BusinessRuntimeException {
        try {
            if (!StringUtils.isEmpty((String)vo.getOrgVerCode())) {
                this.updateOneOrg(vo.getOrgType(), vo.getOrgVerCode(), vo.getOrg());
            } else if (vo.getVersions() != null) {
                vo.getOrg().setCode(vo.getOrgCode());
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                List versionVOList = vo.getVersions();
                for (OrgVersionVO orgVersionVO : versionVOList) {
                    GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), df.format(orgVersionVO.getValidTime()));
                    OrgToJsonVO orgByID = tool.getOrgByCode(vo.getOrgCode());
                    if (null == orgByID) continue;
                    orgByID.getDatas().forEach((key, val) -> {
                        if (!vo.getOrg().getDatas().containsKey(key)) {
                            vo.getOrg().getDatas().put(key, val);
                        }
                    });
                    OrgToJsonVO orgByCode = tool.getOrgByCode(vo.getOrg().getCode());
                    vo.getOrg().setFieldValue("ver", orgByCode.getFieldValue("ver"));
                    this.updateOneOrg(vo.getOrgType(), df.format(orgVersionVO.getValidTime()), vo.getOrg());
                }
            }
        }
        catch (Exception e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u66f4\u65b0\u5f02\u5e38", e);
            throw new BusinessRuntimeException(e.getMessage());
        }
        return BusinessResponseEntity.ok((Object)vo.getOrg());
    }

    private void updateOneOrg(String orgType, String orgVer, OrgToJsonVO vo) {
        GcOrgMangerCenterTool tool = this.getTool(orgType, orgVer);
        vo.setFieldValue("UPDATETIME", (Object)System.currentTimeMillis());
        List<OrgToJsonVO> hbs = tool.diffUnitExist(vo.getDiffUnitId());
        if (!CollectionUtils.isEmpty(hbs) && !hbs.get(0).getCode().equals(vo.getCode())) {
            throw new RuntimeException(tool.getOrgByID(vo.getDiffUnitId()).getTitle() + " \u5df2\u88ab\u8bbe\u7f6e\u4e3a[" + hbs.get(0).getTitle() + "(" + hbs.get(0).getCode() + ")]\u7684\u5dee\u989d\u5355\u4f4d");
        }
        OrgToJsonVO old = tool.getOrgByCode(vo.getCode());
        String diffUnitId = vo.getDiffUnitId();
        String baseUnitId = vo.getBaseUnitId();
        List<OrgToJsonVO> children = tool.listChildOrgByParent(vo.getCode());
        if (!(!CollectionUtils.isEmpty(children) || StringUtils.isEmpty((String)diffUnitId) && StringUtils.isEmpty((String)baseUnitId))) {
            throw new BusinessRuntimeException("\u5355\u6237\u5355\u4f4d\u4e0d\u5141\u8bb8\u914d\u7f6e\u672c\u90e8\u6216\u8005\u5dee\u989d\u5355\u4f4d");
        }
        tool.update(vo);
        logger.info(EnvCenter.getCurrentUser().getFullname() + ":\u5408\u5e76\u5355\u4f4d\u529f\u80fd\u4fee\u6539\u4e86\u7ec4\u7ec7\u673a\u6784:" + vo.getTitle());
        logger.info("\u4fee\u6539\u524d" + old.getDatas().toString());
        logger.info("\u4fee\u6539\u540e" + vo.getDatas().toString());
    }

    public BusinessResponseEntity<String> deleteOrgWithProgress(GcOrgEditVO vo) {
        if (StringUtils.isEmpty((String)vo.getOrgCode())) {
            return BusinessResponseEntity.error((String)"\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u6570\u636e");
        }
        GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), vo.getOrgVerCode());
        System.out.println(tool.toString());
        try {
            List<OrgToJsonVO> orgToJsonVOS = tool.listAllChildOrgByParent(vo.getOrgCode());
            FrontEndParams params = new FrontEndParams(vo.getSn(), orgToJsonVOS.size(), new ProgressDataImpl(vo.getSn()));
            tool.setFrontEnd(params);
            tool.delete(vo.getOrgCode());
            params.getProgressData().setSuccessFlagAndRefresh(true);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7ec4\u7ec7\u673a\u6784", e);
            if (tool.getFrontEnd() != null) {
                tool.getFrontEnd().getProgressData().setSuccessFlagAndRefresh(false);
            }
            return BusinessResponseEntity.error((String)("\u5220\u9664\u5931\u8d25\uff1a" + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<String> deleteOrg(GcOrgEditVO vo) {
        try {
            if (StringUtils.isEmpty((String)vo.getOrgCode())) {
                return BusinessResponseEntity.error((String)"\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u6570\u636e");
            }
            String[] ids = vo.getOrgCode().split(";");
            if (ids.length < 1) {
                return BusinessResponseEntity.error((String)"\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u6570\u636e");
            }
            if (!StringUtils.isEmpty((String)vo.getOrgVerCode())) {
                this.deleteOneOrg(vo.getOrgType(), vo.getOrgVerCode(), ids);
            } else if (vo.getVersions() != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                List versionVOList = vo.getVersions();
                for (OrgVersionVO orgVersionVO : versionVOList) {
                    this.deleteOneOrg(vo.getOrgType(), df.format(orgVersionVO.getValidTime()), ids);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7ec4\u7ec7\u673a\u6784", e);
            return BusinessResponseEntity.error((String)("\u5220\u9664\u5931\u8d25\uff1a" + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    private void deleteOneOrg(String orgType, String orgVer, String[] ids) {
        GcOrgMangerCenterTool tool = this.getTool(orgType, orgVer);
        for (String id : ids) {
            String org = id;
            if (org == null) continue;
            OrgToJsonVO orgByID = tool.getOrgByID(org);
            tool.deleteSingleUnit(org);
            logger.info(EnvCenter.getCurrentUser().getFullname() + ":\u5408\u5e76\u5355\u4f4d\u529f\u80fd\u5220\u9664\u4e86\u7ec4\u7ec7\u673a\u6784:" + orgByID.getTitle() + ". \u7248\u672c\uff1a" + orgVer);
        }
    }

    public BusinessResponseEntity<OrgToJsonVO> batchOrg(GcOrgEditVO vo) throws BusinessRuntimeException {
        GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), vo.getOrgVerCode());
        try {
            return BusinessResponseEntity.ok((Object)tool.batchSaveOrg(vo.getOrg()));
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }

    public BusinessResponseEntity<Object> generateByFormula(@RequestBody GcOrgTypeCopyApiParam param) throws BusinessRuntimeException {
        this.gcOrgGenerateService.generateOrgTree(param);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> generateByFormulaPreview(@RequestBody GcOrgTypeCopyApiParam param) throws BusinessRuntimeException {
        return BusinessResponseEntity.ok(this.gcOrgGenerateService.generateOrgTreePreview(param));
    }

    public BusinessResponseEntity<List<OrgToJsonVO>> getOrgSByBatchIDS(@PathVariable(value="orgType") String orgType, @PathVariable(value="yyyyTmmmm") String yyyyTmmmm, @RequestBody List<String> orgIds) {
        try {
            GcOrgClient gcOrgCenterBase = (GcOrgClient)SpringBeanUtils.getBean(GcOrgClient.class);
            ArrayList orgToJsonVOList = new ArrayList();
            int pageSize = 1000;
            int orgIdsSize = orgIds.size();
            if (orgIdsSize > pageSize) {
                int page = orgIdsSize / pageSize;
                int sing = orgIdsSize % pageSize;
                if (page > 0) {
                    for (int i = 0; i < page; ++i) {
                        List<String> uuidList = orgIds.subList(i * pageSize, (i + 1) * pageSize);
                        uuidList.forEach(code -> {
                            GcOrgApiParam param = new GcOrgApiParam();
                            param.setOrgType(orgType);
                            param.setOrgVerCode(yyyyTmmmm);
                            param.setOrgCode(code);
                            orgToJsonVOList.add(gcOrgCenterBase.getOrgByCode(param).getData());
                        });
                    }
                }
                GcOrgApiParam param = new GcOrgApiParam();
                param.setOrgType(orgType);
                param.setOrgVerCode(yyyyTmmmm);
                param.setOrgCodes(orgIds.subList(page * pageSize, page * pageSize + sing));
                orgToJsonVOList.addAll((Collection)gcOrgCenterBase.getOrgByCodes(param).getData());
            } else {
                GcOrgApiParam param = new GcOrgApiParam();
                param.setOrgType(orgType);
                param.setOrgVerCode(yyyyTmmmm);
                param.setOrgCodes(orgIds);
                orgToJsonVOList.addAll((Collection)gcOrgCenterBase.getOrgByCodes(param).getData());
            }
            return BusinessResponseEntity.ok(orgToJsonVOList);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

