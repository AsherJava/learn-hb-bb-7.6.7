/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 */
package com.jiuqi.gcreport.formulaschemeconfig.efdcconfigservice;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCEFDCConfigServiceImpl
implements IEFDCConfigService {
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private IFormulaRunTimeController formCtrl;
    private Logger logger = LoggerFactory.getLogger(GCEFDCConfigServiceImpl.class);

    public List<FormulaSchemeDefine> getRPTFormulaScheme(QueryObjectImpl queryObject, Map<String, String> map, String entityId) {
        try {
            String schemeId = queryObject.getFormSchemeKey();
            String orgId = queryObject.getMainDim();
            if (StringUtils.isEmpty((String)schemeId) || StringUtils.isEmpty((String)orgId)) {
                return null;
            }
            FormulaSchemeConfigDTO schemeConfigDTO = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(schemeId, orgId, map);
            if (null == schemeConfigDTO || CollectionUtils.isEmpty((Collection)schemeConfigDTO.getFetchAfterSchemeId())) {
                return null;
            }
            ArrayList<FormulaSchemeDefine> formulaSchemeDefines = new ArrayList<FormulaSchemeDefine>();
            for (String fetchAfterSchemeId : schemeConfigDTO.getFetchAfterSchemeId()) {
                FormulaSchemeDefine formulaSchemeDefine = this.formCtrl.queryFormulaSchemeDefine(fetchAfterSchemeId);
                if (null == formulaSchemeDefine) continue;
                formulaSchemeDefines.add(formulaSchemeDefine);
            }
            return formulaSchemeDefines;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5f02\u5e38\uff1a" + e.getMessage());
            return null;
        }
    }

    public FormulaSchemeDefine getSoluctionByDimensions(QueryObjectImpl queryObject, Map<String, String> map, String entityId) {
        try {
            String schemeId = queryObject.getFormSchemeKey();
            String orgId = queryObject.getMainDim();
            if (StringUtils.isEmpty((String)schemeId) || StringUtils.isEmpty((String)orgId)) {
                return null;
            }
            FormulaSchemeConfigDTO schemeConfigDTO = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(schemeId, orgId, map);
            FormulaSchemeDefine formulaSchemeDefine = this.formCtrl.queryFormulaSchemeDefine(schemeConfigDTO.getFetchSchemeId());
            if (!StringUtils.isEmpty((String)schemeConfigDTO.getFetchSchemeId()) && formulaSchemeDefine == null) {
                this.logger.info("\u53d6\u6570\u65b9\u6848\u914d\u7f6e\u6709\u8bef\uff1afetchSchemeId" + schemeConfigDTO.getFetchSchemeId());
            }
            return formulaSchemeDefine;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u53d6\u6570\u65b9\u6848\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    public Boolean existSolution(String taskKey) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return false;
        }
        List<FormulaSchemeConfigEO> formulaSchemeConfigEOS = this.formulaSchemeConfigService.getByTaskId(taskKey);
        return !CollectionUtils.isEmpty(formulaSchemeConfigEOS);
    }
}

