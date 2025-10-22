/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.conditionalstyle.controller.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.conditionalstyle.common.CSErrorEnum;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.CoordObject;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.service.DesignConditionStyleService;
import com.jiuqi.nr.conditionalstyle.utils.CellCoordChangeUtil;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DesignConditionalStyleController
implements IDesignConditionalStyleController {
    private static final Logger logger = LoggerFactory.getLogger(DesignConditionalStyleController.class);
    @Autowired
    private DesignConditionStyleService conditionStyleService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public void insertCS(List<DesignConditionalStyle> list) throws JQException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            this.conditionStyleService.insertCS(list.toArray());
            DesignFormDefine formDefine = this.designTimeViewController.queryFormById(list.get(0).getFormKey());
            formDefine.setUpdateTime(new Date());
            this.designTimeViewController.updateFormDefine(formDefine);
        }
        catch (DBParaException e) {
            logger.error(e.getMessage());
            throw new JQException((ErrorEnum)CSErrorEnum.CS_EXCEPTION_001, (Throwable)e);
        }
    }

    @Override
    public void updateCS(List<DesignConditionalStyle> list) throws JQException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            this.conditionStyleService.updateCS(list.toArray());
        }
        catch (DBParaException e) {
            logger.error(e.getMessage());
            throw new JQException((ErrorEnum)CSErrorEnum.CS_EXCEPTION_002, (Throwable)e);
        }
    }

    @Override
    public void deleteCS(List<DesignConditionalStyle> list) throws JQException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            this.conditionStyleService.deleteCS(list);
            DesignFormDefine formDefine = this.designTimeViewController.queryFormById(list.get(0).getFormKey());
            if (null != formDefine) {
                formDefine.setUpdateTime(new Date());
                this.designTimeViewController.updateFormDefine(formDefine);
            }
        }
        catch (DBParaException e) {
            logger.error(e.getMessage());
            throw new JQException((ErrorEnum)CSErrorEnum.CS_EXCEPTION_003, (Throwable)e);
        }
    }

    @Override
    public void deleteCSInForm(String formKey) throws JQException {
        List<DesignConditionalStyle> allCSInForm = this.getAllCSInForm(formKey);
        this.deleteCS(allCSInForm);
    }

    @Override
    public List<DesignConditionalStyle> getCSByTask(String taskKey) {
        ArrayList<DesignConditionalStyle> result = new ArrayList<DesignConditionalStyle>();
        Set<String> collect = this.designTimeViewController.queryAllSoftFormDefinesByTask(taskKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        collect.forEach(formKey -> {
            List<DesignConditionalStyle> allCSInForm = this.conditionStyleService.getCSByForm((String)formKey);
            if (!CollectionUtils.isEmpty(allCSInForm)) {
                result.addAll(allCSInForm);
            }
        });
        return result;
    }

    @Override
    public List<DesignConditionalStyle> getCSByPos(String formKey, int posX, int posY) {
        List<DesignConditionalStyle> csByPos = this.conditionStyleService.getCSByPos(formKey, posX, posY);
        csByPos.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return csByPos;
    }

    @Override
    public List<DesignConditionalStyle> getCSByDataLink(DesignDataLinkDefine dataLinkDefine) {
        List<DesignConditionalStyle> result = new ArrayList<DesignConditionalStyle>();
        if (dataLinkDefine == null) {
            return result;
        }
        DesignDataRegionDefine dataRegionDefine = this.designTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        if (dataRegionDefine == null) {
            return result;
        }
        result = this.conditionStyleService.getCSByPos(dataRegionDefine.getFormKey(), dataLinkDefine.getPosX(), dataLinkDefine.getPosY());
        result.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return result;
    }

    @Override
    public List<DesignConditionalStyle> getAllCSInForm(String formKey) {
        List<DesignConditionalStyle> csByForm = this.conditionStyleService.getCSByForm(formKey);
        csByForm.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return csByForm;
    }

    @Override
    public List<DesignConditionalStyle> getCSByRegion(String formKey, String start, String end) {
        ArrayList<DesignConditionalStyle> result = new ArrayList<DesignConditionalStyle>();
        CoordObject startCoord = CellCoordChangeUtil.StrToNum(start);
        CoordObject endCoord = CellCoordChangeUtil.StrToNum(end);
        for (int i = startCoord.getPosX(); i <= endCoord.getPosX(); ++i) {
            for (int j = startCoord.getPosY(); j <= endCoord.getPosY(); ++j) {
                List<DesignConditionalStyle> csByPos = this.getCSByPos(formKey, i, j);
                if (CollectionUtils.isEmpty(csByPos)) continue;
                result.addAll(csByPos);
            }
        }
        result.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return result;
    }
}

