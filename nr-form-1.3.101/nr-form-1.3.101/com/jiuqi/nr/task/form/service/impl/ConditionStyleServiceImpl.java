/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.CoordObject
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.conditionalstyle.utils.CellCoordChangeUtil
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.CoordObject;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.utils.CellCoordChangeUtil;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.service.IConditionStyleService;
import com.jiuqi.nr.task.form.util.ConditionStyleBeanUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ConditionStyleServiceImpl
implements IConditionStyleService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignConditionalStyleController designConditionalStyleController;

    @Override
    public void save(String formKey, List<ConditionStyleDTO> conditionStyles) {
        if (!CollectionUtils.isEmpty(conditionStyles)) {
            this.updateConditionStyleTable(conditionStyles);
        }
        this.checkConditionStyle(formKey);
    }

    private void updateConditionStyleTable(List<ConditionStyleDTO> conditionStyles) {
        ArrayList<ConditionStyleDTO> insertConditionStyle = new ArrayList<ConditionStyleDTO>();
        ArrayList<ConditionStyleDTO> updateConditionStyle = new ArrayList<ConditionStyleDTO>();
        ArrayList<ConditionStyleDTO> deleteConditionStyle = new ArrayList<ConditionStyleDTO>();
        for (ConditionStyleDTO conditionStyle : conditionStyles) {
            switch (conditionStyle.getStatus()) {
                case NEW: {
                    insertConditionStyle.add(conditionStyle);
                    break;
                }
                case DELETE: {
                    deleteConditionStyle.add(conditionStyle);
                    break;
                }
                case MODIFY: {
                    updateConditionStyle.add(conditionStyle);
                    break;
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertConditionStyle)) {
            this.insert(insertConditionStyle);
        }
        if (!CollectionUtils.isEmpty(updateConditionStyle)) {
            this.update(updateConditionStyle);
        }
        if (!CollectionUtils.isEmpty(deleteConditionStyle)) {
            this.delete(deleteConditionStyle);
        }
    }

    private void checkConditionStyle(String formKey) {
        List<ConditionStyleDTO> allConditionStyle = this.getByForm(formKey);
        if (!CollectionUtils.isEmpty(allConditionStyle)) {
            List links;
            ArrayList<ConditionStyleDTO> updateConditionStyle = new ArrayList<ConditionStyleDTO>();
            ArrayList<ConditionStyleDTO> deleteConditionStyle = new ArrayList<ConditionStyleDTO>();
            try {
                links = this.designTimeViewController.listDataLinkByForm(formKey);
            }
            catch (Exception e) {
                throw new RuntimeException(String.format("\u67e5\u8be2\u62a5\u8868[%s]\u94fe\u63a5\u51fa\u9519", formKey));
            }
            if (!CollectionUtils.isEmpty(links)) {
                Map<String, DesignDataLinkDefine> linkMap = links.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
                for (ConditionStyleDTO conditionalStyle : allConditionStyle) {
                    String linkKey = conditionalStyle.getLinkKey();
                    if (StringUtils.hasText(linkKey)) {
                        if (linkMap.keySet().contains(linkKey)) {
                            conditionalStyle.setPosX(linkMap.get(linkKey).getPosX());
                            conditionalStyle.setPosY(linkMap.get(linkKey).getPosY());
                            updateConditionStyle.add(conditionalStyle);
                            continue;
                        }
                        deleteConditionStyle.add(conditionalStyle);
                        continue;
                    }
                    deleteConditionStyle.add(conditionalStyle);
                }
            } else {
                this.delete(allConditionStyle);
            }
            if (!CollectionUtils.isEmpty(updateConditionStyle)) {
                this.update(updateConditionStyle);
            }
            if (!CollectionUtils.isEmpty(deleteConditionStyle)) {
                this.delete(deleteConditionStyle);
            }
        }
    }

    @Override
    public void insert(List<ConditionStyleDTO> conditionStyles) {
        try {
            for (ConditionStyleDTO conditionStyle : conditionStyles) {
                conditionStyle.setKey(UUID.randomUUID().toString());
                conditionStyle.setOrder(OrderGenerator.newOrder());
                conditionStyle.setUpdateTime(new Date());
            }
            this.designConditionalStyleController.insertCS(conditionStyles.stream().map(ConditionStyleBeanUtils::toDefine).collect(Collectors.toList()));
        }
        catch (JQException e) {
            throw new FormRuntimeException("\u6761\u4ef6\u6837\u5f0f\u65b0\u589e\u5931\u8d25!", e);
        }
    }

    @Override
    public void update(List<ConditionStyleDTO> conditionStyles) {
        try {
            for (ConditionStyleDTO conditionStyle : conditionStyles) {
                conditionStyle.setUpdateTime(new Date());
            }
            this.designConditionalStyleController.updateCS(conditionStyles.stream().map(ConditionStyleBeanUtils::toDefine).collect(Collectors.toList()));
        }
        catch (JQException e) {
            throw new FormRuntimeException("\u6761\u4ef6\u6837\u5f0f\u66f4\u65b0\u5931\u8d25!", e);
        }
    }

    @Override
    public void delete(List<ConditionStyleDTO> conditionStyles) {
        try {
            this.designConditionalStyleController.deleteCS(conditionStyles.stream().map(ConditionStyleBeanUtils::toDefine).collect(Collectors.toList()));
        }
        catch (JQException e) {
            throw new FormRuntimeException("\u6761\u4ef6\u6837\u5f0f\u5220\u9664\u5931\u8d25!", e);
        }
    }

    @Override
    public List<ConditionStyleDTO> getByForm(String formKey) {
        List conditionalStyles = this.designConditionalStyleController.getAllCSInForm(formKey);
        return this.toDTO(conditionalStyles);
    }

    @Override
    public List<ConditionStyleDTO> getByPos(String formKey, int x, int y) {
        List conditionalStyles = this.designConditionalStyleController.getCSByPos(formKey, x, y);
        return this.toDTO(conditionalStyles);
    }

    @Override
    public List<ConditionStyleDTO> getByRegion(String formKey, String start, String end) {
        List conditionalStyles = this.designConditionalStyleController.getCSByRegion(formKey, start, end);
        return this.toDTO(conditionalStyles);
    }

    @Override
    public boolean checkDifferent(String formKey, String start, String end) {
        List<ConditionStyleDTO> allCSInForm = this.getByRegion(formKey, start, end);
        if (CollectionUtils.isEmpty(allCSInForm)) {
            return true;
        }
        allCSInForm.forEach(conditionalStyle -> {
            if (conditionalStyle.getFontColor() == null) {
                conditionalStyle.setFontColor("");
            }
            if (conditionalStyle.getForeGroundColor() == null) {
                conditionalStyle.setForeGroundColor("");
            }
        });
        Map<String, List<ConditionStyleDTO>> order = allCSInForm.stream().collect(Collectors.groupingBy(ConditionStyleDTO::getStyleExpression));
        CoordObject startCoord = CellCoordChangeUtil.StrToNum((String)start);
        CoordObject endCoord = CellCoordChangeUtil.StrToNum((String)end);
        int allCoordNumber = (endCoord.getPosX() - startCoord.getPosX() + 1) * (endCoord.getPosY() - startCoord.getPosY() + 1);
        Set<String> formulas = order.keySet();
        for (String formula : formulas) {
            if (allCoordNumber == order.get(formula).size()) continue;
            return false;
        }
        return this.checkDifferent(order);
    }

    private boolean checkDifferent(Map<String, List<ConditionStyleDTO>> order) {
        Set<String> formulas = order.keySet();
        for (String formula : formulas) {
            List<ConditionStyleDTO> CSs = order.get(formula);
            long total1 = 0L;
            for (ConditionStyleDTO cs : CSs) {
                total1 += (long)cs.hashCode();
            }
            long total2 = (long)CSs.get(0).hashCode() * (long)CSs.size();
            if (total1 == total2) continue;
            return false;
        }
        return true;
    }

    private List<ConditionStyleDTO> toDTO(List<DesignConditionalStyle> conditionalStyles) {
        if (CollectionUtils.isEmpty(conditionalStyles)) {
            return null;
        }
        return conditionalStyles.stream().map(ConditionStyleBeanUtils::define2DTO).collect(Collectors.toList());
    }
}

