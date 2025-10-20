/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.multicriteria.client.MultiCriteriaClient
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.multicriteria.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.multicriteria.client.MultiCriteriaClient;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO;
import com.jiuqi.gcreport.multicriteria.service.GcMultiCriteriaService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class MultiCriteriaController
implements MultiCriteriaClient {
    private Logger logger = LoggerFactory.getLogger(MultiCriteriaController.class);
    @Autowired
    private GcMultiCriteriaService multiCriteriaService;

    public BusinessResponseEntity<FormTree> queryFormTree(String schemeId) {
        FormTree formTree = this.multiCriteriaService.queryFormTree(schemeId);
        return BusinessResponseEntity.ok((Object)formTree);
    }

    public BusinessResponseEntity<String> saveAfterForm(GcMulCriAfterFormVO mulCriAfterFormVO) {
        this.multiCriteriaService.saveAfterForm(mulCriAfterFormVO);
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.multicriteria.successfullySaved"));
    }

    public BusinessResponseEntity<List<String>> queryMulCriAfterForms(String taskId, String schemeId) {
        return BusinessResponseEntity.ok(this.multiCriteriaService.queryMulCriAfterForms(taskId, schemeId));
    }

    public BusinessResponseEntity<String> saveSubjectMapping(List<String> deleteIds, List<GcMultiCriteriaVO> multiCriterias) {
        this.multiCriteriaService.deleteSubjectMapping(deleteIds);
        this.multiCriteriaService.saveSubjectMapping(multiCriterias);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<List<GcMultiCriteriaVO>> querySubjectMapping(GcMultiCriteriaConditionVO condition) {
        return BusinessResponseEntity.ok(this.multiCriteriaService.querySubjectMapping(condition));
    }

    public BusinessResponseEntity<String> queryZbTitlesByCode(List<String> zbCodes) {
        return BusinessResponseEntity.ok((Object)this.multiCriteriaService.queryZbTitlesByCode(zbCodes));
    }

    public BusinessResponseEntity<List<GcMultiCriteriaZbDataVO>> queryZbData(GcMultiCriteriaConditionVO condition) {
        return BusinessResponseEntity.ok(this.multiCriteriaService.queryZbData(condition));
    }

    public BusinessResponseEntity<String> saveZbData(GcMultiCriteriaConditionVO condition) {
        this.multiCriteriaService.saveZbData(condition);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<String> queryFormData(String schemeId, String formKey) {
        return BusinessResponseEntity.ok((Object)this.multiCriteriaService.queryFormData(schemeId, formKey, null));
    }
}

