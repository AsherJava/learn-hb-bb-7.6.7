/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.param.transfer.definition.spi.IFormSchemeRelateBusiness
 */
package com.jiuqi.gcreport.formulaschemeconfig.transfermodule;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.transfermodule.FormulaSchemeConfigTransferModule;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.param.transfer.definition.spi.IFormSchemeRelateBusiness;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigFormSchemeRelateBusiness
implements IFormSchemeRelateBusiness {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaSchemeConfigFormSchemeRelateBusiness.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public String getCode() {
        return "FORMULA_SCHEME_CONFIG";
    }

    public String getTitle() {
        return "\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848";
    }

    public List<ResItem> getRelatedBusinessForFormScheme(String formSchemeId) {
        ArrayList<ResItem> formulaSchemeConfigItems = new ArrayList<ResItem>();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeId);
        TaskOrgLinkListStream taskOrgLinkListStream = this.iRunTimeViewController.listTaskOrgLinkStreamByTask(formSchemeDefine.getTaskKey());
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
            String entity = taskOrgLinkDefine.getEntity();
            if (StringUtils.isEmpty((String)entity)) continue;
            String guid = FormulaSchemeConfigTransferModule.buildNodeId(FormulaSchemeConfigTransferModule.Level.ENTITY, formSchemeId + ":" + entity);
            ResItem resItem = new ResItem(guid, "gcreport-formulaschemeconfig", "CATEGORY_FORMULASCHEMECONFIG");
            formulaSchemeConfigItems.add(resItem);
        }
        return formulaSchemeConfigItems;
    }
}

