/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.formtype.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.common.RelatedState;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import java.util.List;
import java.util.UUID;

public interface IFormTypeService {
    public FormTypeDefine createFormType();

    public FormTypeDataDefine createFormTypeData();

    public List<FormTypeDataDefine> createDefaultFormTypeDatas(String var1);

    default public void insertFormType(FormTypeDefine define) throws JQException {
        this.insertFormType(define, false);
    }

    public void insertFormType(FormTypeDefine var1, boolean var2) throws JQException;

    public void insertFormTypeNoCheck(FormTypeDefine var1, boolean var2) throws JQException;

    public void createDefaultFormType() throws JQException;

    public void insertFormTypeData(FormTypeDataDefine var1) throws JQException;

    public void insertFormTypeData(List<FormTypeDataDefine> var1) throws JQException;

    public void updateFormType(FormTypeDefine var1) throws JQException;

    public void formTypeExchange(String var1, String var2) throws JQException;

    public void updateFormTypeData(FormTypeDataDefine var1) throws JQException;

    public void updateFormTypeData(List<FormTypeDataDefine> var1) throws JQException;

    public void deleteFormType(FormTypeDefine var1) throws JQException;

    public void deleteFormTypeData(FormTypeDataDefine var1) throws JQException;

    public void deleteFormTypeData(List<FormTypeDataDefine> var1) throws JQException;

    public FormTypeDefine queryById(String var1) throws JQException;

    public FormTypeDefine queryFormTypeOnlyById(String var1);

    public FormTypeDefine queryFormType(String var1) throws JQException;

    public List<FormTypeDefine> queryAllFormType();

    public List<FormTypeDefine> queryByGroup(String var1);

    public List<FormTypeDataDefine> queryFormTypeDatas(String var1);

    public FormTypeDataDefine queryFormTypeData(String var1, String var2);

    public List<FormTypeDefine> search(String var1);

    public boolean checkCode(String var1);

    public boolean checkDataCode(String var1, String var2);

    public void moveData(UUID var1, String var2, boolean var3);

    public void insertDefaultDatas(String var1) throws JQException;

    public boolean checkRelated(String var1);

    public RelatedState checkRelatedState(String var1);
}

