/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.common.RelatedState;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDataDefineImpl;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDefineImpl;
import com.jiuqi.nr.formtype.internal.dao.FormTypeDao;
import com.jiuqi.nr.formtype.internal.impl.FormTypeBaseDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeCacheService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FormTypeServiceImpl
implements IFormTypeService {
    @Autowired
    private FormTypeDao formTypeDao;
    @Autowired
    private IFormTypeCacheService formTypeCacheService;
    @Autowired
    private FormTypeBaseDataHelper formTypeBaseDataHelper;
    private final Logger logger = LoggerFactory.getLogger(FormTypeServiceImpl.class);

    @Override
    public FormTypeDefine createFormType() {
        FormTypeDefineImpl formTypeDefineImpl = new FormTypeDefineImpl();
        formTypeDefineImpl.setId(UUIDUtils.getKey());
        return formTypeDefineImpl;
    }

    @Override
    public FormTypeDataDefine createFormTypeData() {
        FormTypeDataDefineImpl formTypeDataDefineImpl = new FormTypeDataDefineImpl();
        formTypeDataDefineImpl.setId(UUID.randomUUID());
        return formTypeDataDefineImpl;
    }

    private void checkFormTypeForInsert(FormTypeDefine define) throws JQException {
        if (!Pattern.matches("^MD_BBLX\\w{0,24}", define.getCode())) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.CODE_INVALID_ERROR);
        }
    }

    private void checkFormTypeForUpdate(FormTypeDefine define) throws JQException {
        FormTypeDefine oldDefine = this.formTypeDao.getById(define.getId());
        if (!oldDefine.getCode().equals(define.getCode())) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.CODE_NOCHANGE_ERROR);
        }
    }

    @Override
    public void insertFormType(FormTypeDefine define, boolean insertDefaultDatas) throws JQException {
        if (!StringUtils.hasLength(define.getId())) {
            define.setId(UUIDUtils.getKey());
        }
        try {
            this.checkFormTypeForInsert(define);
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5931\u8d25\uff1a{}[{}]:{}", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DEFINE_ERROR, (Throwable)e);
        }
        this.insertFormTypeNoCheck(define, insertDefaultDatas);
    }

    @Override
    public void insertFormTypeNoCheck(FormTypeDefine define, boolean insertDefaultDatas) throws JQException {
        if (!StringUtils.hasLength(define.getId())) {
            define.setId(UUIDUtils.getKey());
        }
        try {
            String id = this.formTypeBaseDataHelper.saveDefine(define);
            define.setId(id);
            if (!StringUtils.hasText(define.getGroupId())) {
                define.setGroupId("--");
            }
            this.formTypeDao.insert(define);
            if (insertDefaultDatas) {
                this.insertDefaultDatas(define.getCode());
            }
            this.formTypeCacheService.cleanCache();
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5931\u8d25\uff1a{}[{}]:{}", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DEFINE_ERROR, (Throwable)e);
        }
    }

    @Override
    public void insertFormTypeData(FormTypeDataDefine define) throws JQException {
        this.checkDataUnitNatrue(define);
        try {
            this.formTypeBaseDataHelper.addData(define);
        }
        catch (JQException e) {
            this.logger.error("\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", new Object[]{define.getName(), define.getCode(), e});
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", define.getName(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, (Throwable)e);
        }
    }

    public void checkDataUnitNatrue(FormTypeDataDefine define) throws JQException {
        switch (define.getUnitNatrue().getRule()) {
            case NONE: {
                break;
            }
            case UNIQUE: {
                List<FormTypeDataDefine> addDatas = this.formTypeBaseDataHelper.getFormTypeDataDefines(define.getFormTypeCode());
                for (FormTypeDataDefine data : addDatas) {
                    if (define.getCode().equals(data.getCode()) || define.getUnitNatrue() != data.getUnitNatrue() || UnitNature.UnitNatureRule.UNIQUE != data.getUnitNatrue().getRule()) continue;
                    throw new JQException((ErrorEnum)FormTypeExceptionEnum.DATA_UNITNATURE_UNIQUE);
                }
                break;
            }
            default: {
                throw new JQException((ErrorEnum)FormTypeExceptionEnum.DATA_UNITNATURE_UNKNOWN);
            }
        }
    }

    @Override
    @Transactional
    public void updateFormType(FormTypeDefine define) throws JQException {
        try {
            this.checkFormTypeForUpdate(define);
            this.formTypeBaseDataHelper.saveDefine(define);
            this.formTypeDao.update(define);
            this.formTypeCacheService.cleanCache();
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5931\u8d25\uff1a{}[{}]:{}", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_DEFINE_ERROR, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void formTypeExchange(String key, String targetKey) throws JQException {
        try {
            FormTypeDefine define = this.formTypeDao.getById(key);
            FormTypeDefine tagetDefine = this.formTypeDao.getById(targetKey);
            String order = define.getOrder();
            define.setOrder(tagetDefine.getOrder());
            tagetDefine.setOrder(order);
            this.formTypeDao.update(new FormTypeDefine[]{define, tagetDefine});
        }
        catch (Exception e) {
            this.logger.error("\u4ea4\u6362\u62a5\u8868\u7c7b\u578b\u4f4d\u7f6e\u5931\u8d25", e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_DEFINE_ERROR, (Throwable)e);
        }
    }

    @Override
    public void updateFormTypeData(FormTypeDataDefine define) throws JQException {
        this.checkDataForUpdate(define);
        try {
            this.formTypeBaseDataHelper.updateData(define);
        }
        catch (JQException e) {
            this.logger.error("\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", new Object[]{define.getName(), define.getCode(), e});
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", define.getName(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, (Throwable)e);
        }
    }

    private void checkDataForUpdate(FormTypeDataDefine define) throws JQException {
        FormTypeDataDefine data = this.formTypeBaseDataHelper.getFormTypeDataDefine(define.getFormTypeCode(), define.getCode());
        if (data.getUnitNatrue() != define.getUnitNatrue() && this.formTypeBaseDataHelper.checkRelated(define.getFormTypeCode(), true)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DATA_DISABLE_UPDATE);
        }
        this.checkDataUnitNatrue(data);
    }

    @Override
    @Transactional
    public void deleteFormType(FormTypeDefine define) throws JQException {
        try {
            this.formTypeBaseDataHelper.deleteDefine(define);
            this.formTypeDao.delete(define.getId());
            this.formTypeCacheService.cleanCache();
        }
        catch (Exception e) {
            this.logger.error("\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5931\u8d25\uff1a{}[{}]:{}", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DELETE_DEFINE_ERROR, (Throwable)e);
        }
    }

    @Override
    public void deleteFormTypeData(FormTypeDataDefine define) throws JQException {
        this.checkDataForDelete(define.getFormTypeCode());
        try {
            this.formTypeBaseDataHelper.deleteData(define);
        }
        catch (JQException e) {
            this.logger.error("\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", new Object[]{define.getName(), define.getCode(), e});
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25\uff1a{}[{}]:{}", define.getName(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, (Throwable)e);
        }
    }

    @Override
    public void deleteFormTypeData(List<FormTypeDataDefine> defines) throws JQException {
        Set formTypeCodes = defines.stream().map(FormTypeDataDefine::getFormTypeCode).collect(Collectors.toSet());
        for (String formTypeCode : formTypeCodes) {
            this.checkDataForDelete(formTypeCode);
        }
        try {
            this.formTypeBaseDataHelper.deleteData(defines);
        }
        catch (JQException e) {
            this.logger.error("\u6279\u91cf\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25", e);
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u6279\u91cf\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25", e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, (Throwable)e);
        }
    }

    private void checkDataForDelete(String formTypeCode) throws JQException {
        if (this.formTypeBaseDataHelper.checkRelated(formTypeCode, true)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DATA_DISABLE_DELETE);
        }
    }

    @Override
    public FormTypeDefine queryById(String id) throws JQException {
        FormTypeDefine define = this.formTypeDao.getById(id);
        if (null != define) {
            this.formTypeBaseDataHelper.getFormTypeFullInfo(define);
        }
        return define;
    }

    @Override
    public List<FormTypeDefine> queryByGroup(String groupId) {
        List<FormTypeDefine> result = this.formTypeDao.getByGroupId(groupId);
        this.fullInfo(result);
        return result;
    }

    @Override
    public List<FormTypeDataDefine> queryFormTypeDatas(String formTypeCode) {
        return this.formTypeBaseDataHelper.getFormTypeDataDefines(formTypeCode);
    }

    @Override
    public FormTypeDataDefine queryFormTypeData(String formTypeCode, String dataCode) {
        return this.formTypeBaseDataHelper.getFormTypeDataDefine(formTypeCode, dataCode);
    }

    @Override
    public List<FormTypeDefine> search(String keyword) {
        List<FormTypeDefine> result = this.formTypeDao.serach(keyword);
        this.fullInfo(result);
        return result;
    }

    @Override
    public boolean checkCode(String code) {
        return this.formTypeBaseDataHelper.checkCode(code);
    }

    @Override
    public boolean checkDataCode(String tableName, String code) {
        return this.formTypeBaseDataHelper.checkDataCode(tableName, code);
    }

    @Override
    public void moveData(UUID id, String tableName, boolean isUp) {
        this.formTypeBaseDataHelper.moveData(id, tableName, isUp);
    }

    @Override
    public FormTypeDefine queryFormTypeOnlyById(String id) {
        return this.formTypeDao.getById(id);
    }

    @Override
    public List<FormTypeDefine> queryAllFormType() {
        List<FormTypeDefine> all = this.formTypeDao.getAll();
        this.fullInfo(all);
        return all;
    }

    private void fullInfo(List<FormTypeDefine> defines) {
        for (int i = defines.size() - 1; i >= 0; --i) {
            FormTypeDefine define = defines.get(i);
            try {
                this.formTypeBaseDataHelper.getFormTypeFullInfo(define);
                continue;
            }
            catch (JQException e) {
                this.logger.error("\u67e5\u8be2\u6240\u6709\u62a5\u8868\u7c7b\u578b\uff1a{}[{}]\u5bf9\u5e94\u57fa\u7840\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u5c06\u88ab\u5220\u9664\u3002", (Object)define.getTitle(), (Object)define.getCode());
                defines.remove(i);
                try {
                    this.deleteFormType(define);
                    continue;
                }
                catch (JQException jQException) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void insertDefaultDatas(String formTypeCode) throws JQException {
        try {
            this.formTypeBaseDataHelper.addData(formTypeCode, this.createDefaultFormTypeDatas(formTypeCode));
        }
        catch (JQException e) {
            this.logger.error("\u65b0\u589e\u9ed8\u8ba4\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25", e);
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u9ed8\u8ba4\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5931\u8d25", e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_DATA_ERROR, (Throwable)e);
        }
    }

    @Override
    public List<FormTypeDataDefine> createDefaultFormTypeDatas(String formTypeCode) {
        ArrayList<FormTypeDataDefine> datas = new ArrayList<FormTypeDataDefine>();
        datas.add(this.createFormTypeData(formTypeCode, "0", "\u5355\u6237\u8868", UnitNature.JCFHB));
        datas.add(this.createFormTypeData(formTypeCode, "1", "\u96c6\u56e2\u5dee\u989d\u8868", UnitNature.JTCEB));
        datas.add(this.createFormTypeData(formTypeCode, "7", "\u5b8c\u5168\u6c47\u603b\u8868", UnitNature.BZHZB));
        datas.add(this.createFormTypeData(formTypeCode, "9", "\u96c6\u56e2\u5408\u5e76\u8868", UnitNature.JTHZB));
        return datas;
    }

    private FormTypeDataDefine createFormTypeData(String formTypeCode, String code, String name, UnitNature unitNature) {
        FormTypeDataDefine data = this.createFormTypeData();
        data.setId(UUID.randomUUID());
        data.setCode(code);
        data.setName(name);
        data.setUnitNatrue(unitNature);
        data.setFormTypeCode(formTypeCode);
        data.setOrdinal(BigDecimal.valueOf(unitNature.getValue()));
        return data;
    }

    @Override
    public boolean checkRelated(String formTypeCode) {
        return this.formTypeBaseDataHelper.checkRelated(formTypeCode, true);
    }

    @Override
    public RelatedState checkRelatedState(String formTypeCode) {
        return this.formTypeBaseDataHelper.checkRelated(formTypeCode);
    }

    @Override
    public void createDefaultFormType() throws JQException {
        this.formTypeBaseDataHelper.addDefaultBaseDataGroup();
        BaseDataDefineDO baseDataDefine = this.formTypeBaseDataHelper.getBaseDataDefine("MD_BBLX");
        if (null != baseDataDefine) {
            this.logger.info("\u57fa\u7840\u6570\u636e{}[{}]\u5df2\u7ecf\u5b58\u5728", (Object)baseDataDefine.getTitle(), (Object)baseDataDefine.getName());
            return;
        }
        FormTypeDefine formTypeDefine = this.createFormType();
        formTypeDefine.setCode("MD_BBLX");
        formTypeDefine.setTitle("\u62a5\u8868\u7c7b\u578b");
        formTypeDefine.setGroupId("--");
        formTypeDefine.setOrder(OrderGenerator.newOrder());
        this.insertFormTypeNoCheck(formTypeDefine, true);
    }

    @Override
    @Transactional
    public void insertFormTypeData(List<FormTypeDataDefine> defines) throws JQException {
        for (FormTypeDataDefine define : defines) {
            this.insertFormTypeData(define);
        }
    }

    @Override
    @Transactional
    public void updateFormTypeData(List<FormTypeDataDefine> defines) throws JQException {
        for (FormTypeDataDefine define : defines) {
            this.updateFormTypeData(define);
        }
    }

    @Override
    public FormTypeDefine queryFormType(String formTypeCode) throws JQException {
        FormTypeDefine define = this.formTypeDao.getByCode(formTypeCode);
        if (null != define) {
            this.formTypeBaseDataHelper.getFormTypeFullInfo(define);
        }
        return define;
    }
}

