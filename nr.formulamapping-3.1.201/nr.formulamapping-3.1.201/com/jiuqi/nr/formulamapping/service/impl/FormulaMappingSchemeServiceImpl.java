/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formulamapping.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj;
import com.jiuqi.nr.formulamapping.dao.FormulaMappingSchemeDaoImpl;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.FormulaMappingSchemeService;
import com.jiuqi.nr.formulamapping.service.IFormulaMappingService;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormulaMappingSchemeServiceImpl
implements FormulaMappingSchemeService {
    @Autowired
    private FormulaMappingSchemeDaoImpl formulaMappingSchemeDao;
    @Autowired
    private IFormulaMappingService formulaMappingService;

    @Override
    public FormSchemeAndTaskKeysObj getTaskAndFormSchemeObj(String formulaSchemeKey) {
        return this.formulaMappingSchemeDao.getTaskAndFormSchemeObj(formulaSchemeKey);
    }

    @Override
    public List<FormulaMappingSchemeObj> queryFormulaMappingSchemeObjs() {
        ArrayList<FormulaMappingSchemeObj> list = new ArrayList<FormulaMappingSchemeObj>();
        List<FormulaMappingSchemeDefine> mappingSchemeDefines = this.formulaMappingSchemeDao.queryFormulaMappingSchemeObjs();
        if (mappingSchemeDefines != null && !mappingSchemeDefines.isEmpty()) {
            mappingSchemeDefines.stream().forEach(item -> {
                FormulaMappingSchemeObj formulaMappingSchemeObj = new FormulaMappingSchemeObj();
                formulaMappingSchemeObj.converObj(item);
                formulaMappingSchemeObj.setTargetTitle(this.formulaMappingSchemeDao.queryFormulaMappingSchemeTitle(item.getTargetFSKey()));
                formulaMappingSchemeObj.setSourceTitle(this.formulaMappingSchemeDao.queryFormulaMappingSchemeTitle(item.getSourceFSKey()));
                list.add(formulaMappingSchemeObj);
            });
        }
        return list;
    }

    @Override
    public FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey(String key) {
        return this.formulaMappingSchemeDao.queryFormulaMappingSchemeObjsByKey(key);
    }

    @Override
    public void insertFormulaMappingSchemeDefine(FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws JQException {
        if (StringUtil.isEmpty((String)formulaMappingSchemeDefine.getKey())) {
            formulaMappingSchemeDefine.setKey(String.valueOf(UUID.randomUUID()));
        }
        if (StringUtil.isEmpty((String)formulaMappingSchemeDefine.getOrder())) {
            formulaMappingSchemeDefine.setOrder(OrderGenerator.newOrder());
        }
        try {
            this.formulaMappingSchemeDao.insertFormulaMappingSchemeDefine(formulaMappingSchemeDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_001, (Throwable)e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateFormulaMappingSchemeDefine(FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws JQException {
        try {
            this.formulaMappingSchemeDao.updateFormulaMappingSchemeDefine(formulaMappingSchemeDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_002, (Throwable)e);
        }
        this.formulaMappingService.deleteFormulaMappings(formulaMappingSchemeDefine.getKey());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deletsFormulaMappingSchemeDefine(String[] keys) throws JQException {
        block4: {
            try {
                if (keys != null) {
                    this.formulaMappingSchemeDao.deletsFormulaMappingSchemeDefine(keys);
                    for (String key : keys) {
                        this.formulaMappingService.deleteFormulaMappings(key);
                    }
                    break block4;
                }
                throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_004);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_003, (Throwable)e);
            }
        }
    }
}

