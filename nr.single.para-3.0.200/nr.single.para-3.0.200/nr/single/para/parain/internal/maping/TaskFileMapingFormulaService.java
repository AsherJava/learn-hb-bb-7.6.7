/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.single.core.para.FormulaInfo
 *  nr.single.map.data.facade.SingleFileFormulaInfo
 *  nr.single.map.data.internal.SingleFileFormulaItemImpl
 *  nr.single.map.data.internal.SingleFileTableFormulaInfoImpl
 */
package nr.single.para.parain.internal.maping;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.single.core.para.FormulaInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import nr.single.map.data.internal.SingleFileTableFormulaInfoImpl;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingFormulaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingFormulaService
implements ITaskFileMapingFormulaService {
    private static final Logger log = LoggerFactory.getLogger(TaskFileMapingFormulaService.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IFormulaDesignTimeController formulaController;

    @Override
    public void mapingFormulaDefines(TaskImportContext importContext, String formSchemeKey) throws JQException {
        importContext.getMapScheme().getFormulaInfos().clear();
        List formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        HashMap<String, DesignFormulaSchemeDefine> formulaSchemeDic = new HashMap<String, DesignFormulaSchemeDefine>();
        for (DesignFormulaSchemeDefine scheme : formulaSchemes) {
            formulaSchemeDic.put(scheme.getTitle(), scheme);
        }
        HashMap<String, DesignFormDefine> formCache = new HashMap<String, DesignFormDefine>();
        List oldFormList = this.viewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (DesignFormDefine form : oldFormList) {
            formCache.put(form.getFormCode(), form);
        }
        Map fmlMgr = importContext.getParaInfo().getFmlMgr();
        for (Map.Entry entry : fmlMgr.entrySet()) {
            String fmlGroupName = (String)entry.getKey();
            int id1 = fmlGroupName.indexOf("\uff3b");
            int id2 = fmlGroupName.indexOf("\uff3d");
            if (id1 >= 0 && id2 > 0) {
                fmlGroupName = fmlGroupName.substring(id1 + "\uff3b".length(), id2 - 1);
            }
            DesignFormulaSchemeDefine scheme = null;
            if (formulaSchemeDic.containsKey(fmlGroupName)) {
                scheme = (DesignFormulaSchemeDefine)formulaSchemeDic.get(fmlGroupName);
            }
            boolean isNewScheme = null == scheme;
            SingleFileFormulaInfo singleFormulaInfo = importContext.getMapScheme().getNewFormulaInfo();
            singleFormulaInfo.setSingleSchemeName(fmlGroupName);
            if (!isNewScheme) {
                singleFormulaInfo.setNetSchemeName(fmlGroupName);
                singleFormulaInfo.setNetSchemeKey(scheme.getKey());
            }
            importContext.getMapScheme().getFormulaInfos().add(singleFormulaInfo);
            Map schemeFormMap = (Map)entry.getValue();
            for (Map.Entry entry2 : schemeFormMap.entrySet()) {
                String formCode = (String)entry2.getKey();
                log.info("\u751f\u6210\u516c\u5f0f\u65b9\u6848:" + fmlGroupName + ",,\u8868\u5355\uff1a" + formCode + ",\u65f6\u95f4:" + new Date().toString());
                DesignFormDefine form = null;
                if (formCache.containsKey(formCode)) {
                    form = (DesignFormDefine)formCache.get(formCode);
                } else {
                    form = this.viewController.queryFormByCodeInFormScheme(formSchemeKey, formCode);
                    formCache.put(formCode, form);
                }
                SingleFileTableFormulaInfoImpl singleTableFormulas = new SingleFileTableFormulaInfoImpl();
                singleTableFormulas.setSingleTableCode(formCode);
                if (null != form) {
                    singleTableFormulas.setNetFormCode(formCode);
                    singleTableFormulas.setNetFormKey(form.getKey());
                }
                singleFormulaInfo.getTableFormulaInfos().put(formCode, singleTableFormulas);
                Map<Object, Object> oldFormulaDic = null;
                oldFormulaDic = null != scheme ? this.getFormulasFromSchemeAndForm(scheme.getKey(), form) : new HashMap();
                List singleFormulas = (List)entry2.getValue();
                for (FormulaInfo singleFormula : singleFormulas) {
                    DesignFormulaDefine formula = null;
                    boolean isFormulaNew = isNewScheme;
                    String aCode = singleFormula.getUserLevel() + singleFormula.getCode();
                    if (!isNewScheme && oldFormulaDic.containsKey(aCode)) {
                        formula = (DesignFormulaDefine)oldFormulaDic.get(aCode);
                    }
                    isFormulaNew = null == formula;
                    SingleFileFormulaItemImpl singleFormulaMapItem = new SingleFileFormulaItemImpl();
                    singleFormulaMapItem.setSingleFormulaCode(aCode);
                    singleFormulaMapItem.setSingleFormulaExp(singleFormula.getExpression());
                    if (null != formula) {
                        singleFormulaMapItem.setNetFormulaCode(formula.getCode());
                        singleFormulaMapItem.setNetFormulaExp(formula.getExpression());
                        singleFormulaMapItem.setNetFormulaKey(formula.getKey());
                    }
                    if (null != scheme) {
                        singleFormulaMapItem.setSingleSchemeName(fmlGroupName);
                        singleFormulaMapItem.setSingleTableCode(formCode);
                        singleFormulaMapItem.setNetSchemeName(fmlGroupName);
                        singleFormulaMapItem.setNetSchemeKey(scheme.getKey());
                    }
                    if (null != form) {
                        singleFormulaMapItem.setNetFormCode(form.getFormCode());
                        singleFormulaMapItem.setNetFormKey(form.getKey());
                    }
                    singleTableFormulas.getFormulaItems().put(singleFormulaMapItem.getSingleFormulaCode(), singleFormulaMapItem);
                }
            }
        }
    }

    private Map<String, DesignFormulaDefine> getFormulasFromSchemeAndForm(String formulaSchemeKey, DesignFormDefine form) throws JQException {
        HashMap<String, DesignFormulaDefine> oldFormulaDic = new HashMap<String, DesignFormulaDefine>();
        List oldFormulas = null;
        oldFormulas = null == form ? this.formulaController.getAllSoftFormulasInForm(formulaSchemeKey, null) : this.formulaController.getAllSoftFormulasInForm(formulaSchemeKey, form.getKey());
        if (null != oldFormulas) {
            for (DesignFormulaDefine formula : oldFormulas) {
                oldFormulaDic.put(formula.getCode(), formula);
            }
        }
        return oldFormulaDic;
    }

    private FormulaCheckType getFormulaCheckType(String atype, boolean isCheck) {
        FormulaCheckType result = FormulaCheckType.FORMULA_CHECK_NONE;
        if (isCheck && !StringUtils.isEmpty((String)atype)) {
            if (atype.equals("1")) {
                result = FormulaCheckType.FORMULA_CHECK_ERROR;
            } else if (atype.equals("0")) {
                result = FormulaCheckType.FORMULA_CHECK_WARNING;
            } else if (atype.equals("2")) {
                result = FormulaCheckType.FORMULA_CHECK_HINT;
            }
        }
        return result;
    }
}

