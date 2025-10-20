/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.PageArrayList
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.org.api.intf.GcOrgManageClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgImportParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgManageApiParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.log.LogHelper
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.bi.util.collection.PageArrayList;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.org.api.intf.GcOrgManageClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgImportParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgManageApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.log.LogHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
class GcOrgManageController
implements GcOrgManageClient {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgManageController.class);
    @Autowired
    IDataDefinitionRuntimeController runtimeController;
    @Autowired
    GcFieldManagerService gcFieldManagerService;
    @Autowired
    GcOrgDataService gcOrgDataService;

    GcOrgManageController() {
    }

    public BusinessResponseEntity<List<OrgFiledComponentVO>> queryTableDefine(GcOrgManageApiParam param) {
        try {
            List<OrgFiledComponentVO> filedComponentVOS = this.gcFieldManagerService.getFieldComponent(param.getTableName());
            return BusinessResponseEntity.ok(filedComponentVOS);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<GcOrgFieldVO>> queryFieldsManager(GcOrgManageApiParam param) {
        try {
            List<GcOrgFieldVO> gcOrgFieldVOS = this.gcFieldManagerService.queryAllFieldsByTableName(param.getTableName());
            return BusinessResponseEntity.ok(gcOrgFieldVOS);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<GcOrgFieldVO>> batchUpdate(GcOrgManageApiParam param) {
        this.gcFieldManagerService.batchUpdateFieldVOS(param.getTableName(), param.getFields());
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listBaseOrgValusForPage(GcOrgImportParam param) {
        return BusinessResponseEntity.ok(this.gcFieldManagerService.listOrgValuesByPage(param.getIsAll(), param.getOrgParentCode(), param.getPageNum(), param.getPageSize(), true));
    }

    public BusinessResponseEntity<Map<String, Object>> getOrgFormData(GcOrgApiParam param) {
        return BusinessResponseEntity.ok(this.gcFieldManagerService.getOrgFormData(param));
    }

    public BusinessResponseEntity<Object> uploadData(MultipartFile uploadFile, GcOrgImportParam param) {
        ExportConditionVO vo = new ExportConditionVO();
        vo.setTableName(param.getTableName());
        vo.setOrgType(param.getOrgType());
        vo.setOrgVer(param.getOrgVerCode());
        vo.setExecuteOnDuplicate(param.getExecuteOnDuplicate());
        vo.setSn(param.getSn());
        List<ExportMessageVO> exportMessageVOS = this.gcOrgDataService.uploadOrgData(uploadFile, vo);
        return BusinessResponseEntity.ok(exportMessageVOS);
    }

    public BusinessResponseEntity<Object> uploadBaseOrgData(MultipartFile uploadFile, GcOrgImportParam param) {
        ExportConditionVO vo = new ExportConditionVO();
        vo.setTableName(param.getTableName());
        vo.setOrgType(param.getTableName());
        vo.setOrgVer("19700101");
        vo.setExecuteOnDuplicate(param.getExecuteOnDuplicate());
        vo.setSn(param.getSn());
        List<ExportMessageVO> exportMessage = this.gcOrgDataService.uploadOrgData(uploadFile, vo);
        return BusinessResponseEntity.ok(exportMessage);
    }

    public BusinessResponseEntity<Object> exportExcel(ExportConditionVO conditionVO) {
        List<Map<String, Object>> maps = this.gcFieldManagerService.exportExcel(conditionVO);
        Object fieldComponent = new PageArrayList();
        try {
            fieldComponent = this.gcFieldManagerService.getFieldComponent(conditionVO.getOrgType());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("fields", fieldComponent);
        retMap.put("datas", maps);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5bfc\u51fa-\u673a\u6784\u7c7b\u578b" + conditionVO.getOrgType() + "-\u7248\u672c" + conditionVO.getOrgVer()), (String)"");
        return BusinessResponseEntity.ok(retMap);
    }

    public BusinessResponseEntity<Object> listOrgByFlexibleSearch(GcOrgPublicApiParam param) {
        List<GcOrgCacheVO> data = this.gcOrgDataService.list(OrgParse.toVaJsonVo(param));
        List nodes = data.stream().limit(param.getPageSize().intValue()).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public boolean getEnableMergeUnit() {
        return InspectOrgUtils.enableMergeUnit();
    }
}

