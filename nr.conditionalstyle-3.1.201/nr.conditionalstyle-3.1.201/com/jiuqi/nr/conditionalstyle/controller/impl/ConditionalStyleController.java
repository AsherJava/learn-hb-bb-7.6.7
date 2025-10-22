/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.conditionalstyle.controller.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.CoordObject;
import com.jiuqi.nr.conditionalstyle.service.ConditionStyleService;
import com.jiuqi.nr.conditionalstyle.utils.CellCoordChangeUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConditionalStyleController
implements IConditionalStyleController {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalStyleController.class);
    @Autowired
    private ConditionStyleService conditionStyleService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<ConditionalStyle> getCSByTask(String taskKey) {
        ArrayList<ConditionalStyle> result = new ArrayList<ConditionalStyle>();
        Set<String> collect = this.runTimeViewController.queryAllFormDefinesByTask(taskKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        collect.forEach(formKey -> {
            List<ConditionalStyle> allCSInForm = this.conditionStyleService.getCSByForm((String)formKey);
            if (!CollectionUtils.isEmpty(allCSInForm)) {
                result.addAll(allCSInForm);
            }
        });
        return result;
    }

    @Override
    public List<ConditionalStyle> getCSByPos(String formKey, int posX, int posY) {
        List<ConditionalStyle> csByPos = this.conditionStyleService.getCSByPos(formKey, posX, posY);
        csByPos.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return csByPos;
    }

    @Override
    public List<ConditionalStyle> getCSByDataLink(DataLinkDefine dataLinkDefine) {
        List<ConditionalStyle> result = new ArrayList<ConditionalStyle>();
        if (dataLinkDefine == null) {
            return result;
        }
        DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        if (dataRegionDefine == null) {
            return result;
        }
        result = this.conditionStyleService.getCSByPos(dataRegionDefine.getFormKey(), dataLinkDefine.getPosX(), dataLinkDefine.getPosY());
        result.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return result;
    }

    @Override
    public List<ConditionalStyle> getAllCSInForm(String formKey) {
        List<ConditionalStyle> csByForm = this.conditionStyleService.getCSByForm(formKey);
        csByForm.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return csByForm;
    }

    @Override
    public List<ConditionalStyle> getCSByRegion(String formKey, String start, String end) {
        ArrayList<ConditionalStyle> result = new ArrayList<ConditionalStyle>();
        CoordObject startCoord = CellCoordChangeUtil.StrToNum(start);
        CoordObject endCoord = CellCoordChangeUtil.StrToNum(end);
        for (int i = startCoord.getPosX(); i <= endCoord.getPosX(); ++i) {
            for (int j = startCoord.getPosY(); j <= endCoord.getPosY(); ++j) {
                List<ConditionalStyle> csByPos = this.getCSByPos(formKey, i, j);
                if (CollectionUtils.isEmpty(csByPos)) continue;
                result.addAll(csByPos);
            }
        }
        result.sort(Comparator.comparing(ConditionalStyle::getOrder));
        return result;
    }
}

