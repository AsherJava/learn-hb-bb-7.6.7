/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.service;

import com.jiuqi.nr.task.i18n.bean.vo.I18nFormSaveVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.exception.I18nException;

public interface I18nService {
    public I18nInitVO init();

    public I18nInitExtendResultVO initExtend(I18nInitExtendQueryVO var1);

    public I18nResultVO query(I18nQueryVO var1) throws I18nException;

    public void save(I18nResultVO var1) throws I18nException;

    public void deploy() throws I18nException;

    public void styleSave(I18nFormSaveVO var1) throws I18nException;
}

