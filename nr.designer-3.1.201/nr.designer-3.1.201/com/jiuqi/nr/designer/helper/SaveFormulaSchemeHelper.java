/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveFormulaSchemeHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    private static final Logger logger = LoggerFactory.getLogger(SaveFormulaSchemeHelper.class);

    public void saveFormulaSchemeObject(FormulaSchemeObject formulaSchemeObj) throws JQException {
        String formulaSchemeID;
        DesignFormulaSchemeDefine designFormulaSchemeDefine = null;
        if (formulaSchemeObj.isIsDeleted() && StringUtils.isNotEmpty((String)(formulaSchemeID = formulaSchemeObj.getID()))) {
            this.nrDesignTimeController.deleteFormulaSchemeDefine(formulaSchemeObj.getID());
        }
        if (formulaSchemeObj.isIsNew()) {
            formulaSchemeID = formulaSchemeObj.getID();
            formulaSchemeObj.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeID);
            if (designFormulaSchemeDefine == null) {
                designFormulaSchemeDefine = this.nrDesignTimeController.createFormulaSchemeDefine();
                designFormulaSchemeDefine.setKey(formulaSchemeObj.getID());
                this.saveFormulaSchemeObjectHelper(designFormulaSchemeDefine, formulaSchemeObj);
                this.nrDesignTimeController.insertFormulaSchemeDefine(designFormulaSchemeDefine);
            }
        }
        if (formulaSchemeObj.isIsDirty()) {
            formulaSchemeID = formulaSchemeObj.getID();
            designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeID);
            this.saveFormulaSchemeObjectHelper(designFormulaSchemeDefine, formulaSchemeObj);
            designFormulaSchemeDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormulaSchemeDefine(designFormulaSchemeDefine);
        }
    }

    private DesignFormulaSchemeDefine saveFormulaSchemeObjectHelper(DesignFormulaSchemeDefine designFormulaSchemeDefine, FormulaSchemeObject formulaSchemeObj) {
        designFormulaSchemeDefine.setTitle(formulaSchemeObj.getTitle());
        designFormulaSchemeDefine.setDefault(formulaSchemeObj.getIsDefault());
        designFormulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.forValue((int)formulaSchemeObj.getFormulaSchemeType()));
        designFormulaSchemeDefine.setOwnerLevelAndId(formulaSchemeObj.getOwnerLevelAndId());
        String formSchemeKey = formulaSchemeObj.getFormSchemeKey();
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            String uuidFormSchemeKey = null;
            try {
                uuidFormSchemeKey = formSchemeKey;
            }
            catch (Exception e) {
                logger.error("\u8868\u5355\u65b9\u6848\u7684key\u683c\u5f0f\u4e0d\u6b63\u786e\uff01");
            }
            if (uuidFormSchemeKey != null) {
                designFormulaSchemeDefine.setFormSchemeKey(uuidFormSchemeKey);
            }
        }
        return designFormulaSchemeDefine;
    }
}

