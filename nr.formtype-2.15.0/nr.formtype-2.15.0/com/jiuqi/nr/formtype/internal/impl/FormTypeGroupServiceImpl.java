/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeGroupDefineImpl;
import com.jiuqi.nr.formtype.internal.dao.FormTypeGroupDao;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormTypeGroupServiceImpl
implements IFormTypeGroupService {
    @Autowired
    private FormTypeGroupDao formTypeGroupDao;
    @Autowired
    private IFormTypeService iFormTypeService;
    private final Logger logger = LoggerFactory.getLogger(FormTypeGroupServiceImpl.class);

    @Override
    public FormTypeGroupDefine createFormTypeGroup() {
        FormTypeGroupDefineImpl formTypeGroupDefineImpl = new FormTypeGroupDefineImpl();
        formTypeGroupDefineImpl.setId(UUIDUtils.getKey());
        return formTypeGroupDefineImpl;
    }

    @Override
    public void insertFormTypeGroup(FormTypeGroupDefine define) throws JQException {
        if (!StringUtils.hasLength(define.getId())) {
            define.setId(UUIDUtils.getKey());
        }
        try {
            this.formTypeGroupDao.insert(define);
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5931\u8d25\uff1a{}[{}]:{}.", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.ADD_GROUP_ERROR, (Throwable)e);
        }
    }

    @Override
    public void updateFormTypeGroup(FormTypeGroupDefine define) throws JQException {
        try {
            this.formTypeGroupDao.update(define);
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5931\u8d25\uff1a{}[{}]:{}.", define.getTitle(), define.getCode(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_GROUP_ERROR, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void updateFormTypeGroup(FormTypeGroupDefine[] defines) throws JQException {
        try {
            this.formTypeGroupDao.update(defines);
        }
        catch (Exception e) {
            this.logger.error("\u6279\u91cf\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5931\u8d25\uff1a{}.", e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.UPDATE_GROUP_ERROR, (Throwable)e);
        }
    }

    @Override
    public void deleteFormTypeGroup(String id) throws JQException {
        this.doDeleteCheck(id);
        try {
            this.formTypeGroupDao.delete(id);
        }
        catch (Exception e) {
            this.logger.error("\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5931\u8d25\uff1a{}.", e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DELETE_GROUP_ERROR, (Throwable)e);
        }
    }

    private void doDeleteCheck(String id) throws JQException {
        List<FormTypeGroupDefine> groups = this.formTypeGroupDao.getByGroup(id);
        if (!CollectionUtils.isEmpty(groups)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DELETE_GROUP_CHECK_ERROR);
        }
        List<FormTypeDefine> defines = this.iFormTypeService.queryByGroup(id);
        if (!CollectionUtils.isEmpty(defines)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.DELETE_GROUP_CHECK_ERROR);
        }
    }

    @Override
    public FormTypeGroupDefine queryById(String id) {
        return this.formTypeGroupDao.getById(id);
    }

    @Override
    public List<FormTypeGroupDefine> queryByParentId(String groupId) {
        return this.formTypeGroupDao.getByGroup(groupId);
    }

    @Override
    public List<FormTypeGroupDefine> queryAll() {
        return this.formTypeGroupDao.getAll();
    }
}

