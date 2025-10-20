/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.dao.DesignExtFormulaDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeExtFormulaDefineDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.service.ExtRuntimeExpressionServiceV2;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeExtFormulaService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class, RuntimeException.class})
@Service
public class DeployPrivateFormulaService {
    @Autowired
    DesignExtFormulaDefineDao designExtFormulaDefineDao;
    @Autowired
    RunTimeExtFormulaDefineDao runTimeExtFormulaDefineDao;
    @Autowired
    RuntimeExtFormulaService runtimeExtFormulaService;
    @Autowired
    ExtRuntimeExpressionServiceV2 extRuntimeExpressionServiceV2;

    public void deploy(String formulaSchemekey, String formKey, String unit) throws Exception {
        if (!StringUtils.hasLength(formulaSchemekey) || !StringUtils.hasLength(unit)) {
            throw new Exception("\u53d1\u5e03\u79c1\u6709\u516c\u5f0f\u5931\u8d25\uff1a\u53c2\u6570\u5f02\u5e38");
        }
        List<DesignFormulaDefine> formulaByUnit = this.designExtFormulaDefineDao.getFormulaByUnit(formulaSchemekey, formKey, unit);
        this.runTimeExtFormulaDefineDao.deleteBySchemeAndFormAndUnit(formulaSchemekey, formKey, unit);
        List collect = formulaByUnit.stream().map(t -> {
            RunTimeFormulaDefineImpl formula = new RunTimeFormulaDefineImpl();
            BeanUtils.copyProperties(t, formula);
            return formula;
        }).collect(Collectors.toList());
        this.runTimeExtFormulaDefineDao.insert(collect.stream().toArray(FormulaDefine[]::new));
        this.runtimeExtFormulaService.onClearCache();
        this.extRuntimeExpressionServiceV2.onClearCache();
    }
}

