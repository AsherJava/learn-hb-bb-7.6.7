/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.conditionalstyle.web;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.CoordObject;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import com.jiuqi.nr.conditionalstyle.utils.CellCoordChangeUtil;
import com.jiuqi.nr.conditionalstyle.web.vo.ConditionalStyleVO;
import com.jiuqi.nr.conditionalstyle.web.vo.FixObjectVO;
import com.jiuqi.nr.conditionalstyle.web.vo.RegionVO;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/conditionalstyle/"})
@Api(tags={"\u6761\u4ef6\u6837\u5f0f\u670d\u52a1"})
public class CSController {
    private static final Logger logger = LoggerFactory.getLogger(CSController.class);
    @Autowired
    IDesignConditionalStyleController conditionalStyleController;
    @Autowired
    IDesignTimeViewController designTimeViewController;

    public void insertCSObjects(List<DesignConditionalStyle> params) throws JQException {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        for (DesignConditionalStyle conditionalStyle : params) {
            conditionalStyle.setKey(UUID.randomUUID().toString());
            conditionalStyle.setOrder(OrderGenerator.newOrder());
        }
        this.conditionalStyleController.insertCS(params);
    }

    @ApiOperation(value="\u5220\u9664\u6761\u4ef6\u6837\u5f0f\u5bf9\u8c61")
    @PostMapping(value={"delete"})
    public void deleteCSObjects(@RequestBody List<ConditionalStyleVO> params) throws JQException {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        ArrayList<DesignConditionalStyle> conditionalStyles = new ArrayList<DesignConditionalStyle>();
        for (ConditionalStyleVO vo : params) {
            conditionalStyles.add(this.transConditionalStyleVO(vo));
        }
        this.conditionalStyleController.deleteCS(conditionalStyles);
    }

    @ApiOperation(value="\u83b7\u53d6\u4e00\u5f20\u8868\u5355\u5185\u6240\u6709\u7684\u6761\u4ef6\u6837\u5f0f\u5bf9\u8c61")
    @GetMapping(value={"getObjectInForm/{formKey}"})
    public List<ConditionalStyleVO> getCSObjectsInForm(@PathVariable(value="formKey") String formKey) {
        ArrayList<ConditionalStyleVO> result = new ArrayList<ConditionalStyleVO>();
        List<DesignConditionalStyle> allCSInForm = this.conditionalStyleController.getAllCSInForm(formKey);
        for (DesignConditionalStyle cs : allCSInForm) {
            result.add(this.transConditionalStyle(cs));
        }
        return result;
    }

    @ApiOperation(value="\u6839\u636e\u8868\u5355\u548c\u5750\u6807\u83b7\u53d6\u5bf9\u5e94\u7684\u6761\u4ef6\u6837\u5f0f\u5bf9\u8c61")
    @PostMapping(value={"getObjectByPos"})
    public List<ConditionalStyleVO> getCSObjectsByXY(@RequestBody ConditionalStyleVO param) {
        ArrayList<ConditionalStyleVO> result = new ArrayList<ConditionalStyleVO>();
        List<DesignConditionalStyle> allCSInForm = this.conditionalStyleController.getCSByPos(param.getFormKey(), param.getPosX(), param.getPosY());
        for (DesignConditionalStyle cs : allCSInForm) {
            result.add(this.transConditionalStyle(cs));
        }
        return result;
    }

    @ApiOperation(value="\u6839\u636e\u8868\u5355\u548c\u6240\u9009\u4e2d\u7684\u533a\u57df\u5224\u65ad\u533a\u57df\u4e2d\u662f\u5426\u6709\u4e0d\u76f8\u540c\u7684\u6761\u4ef6\u6837\u5f0f")
    @PostMapping(value={"checkObjectsInRegion"})
    public boolean checkDifferent(@RequestBody RegionVO param) {
        List<DesignConditionalStyle> allCSInForm = this.conditionalStyleController.getCSByRegion(param.getFormKey(), param.getStart(), param.getEnd());
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
        Map<String, List<DesignConditionalStyle>> order = allCSInForm.stream().collect(Collectors.groupingBy(ConditionalStyle::getStyleExpression));
        CoordObject startCoord = CellCoordChangeUtil.StrToNum(param.getStart());
        CoordObject endCoord = CellCoordChangeUtil.StrToNum(param.getEnd());
        int allCoordNumber = (endCoord.getPosX() - startCoord.getPosX() + 1) * (endCoord.getPosY() - startCoord.getPosY() + 1);
        Set<String> formulas = order.keySet();
        for (String formula : formulas) {
            if (allCoordNumber == order.get(formula).size()) continue;
            return false;
        }
        return this.checkDifferent(order);
    }

    @ApiOperation(value="\u6839\u636e\u8868\u5355\u548c\u6240\u9009\u4e2d\u7684\u533a\u57df\u6765\u83b7\u53d6\u5bf9\u5e94\u7684\u6761\u4ef6\u6837\u5f0f\u5bf9\u8c61")
    @PostMapping(value={"getObjectByRegion"})
    public List<ConditionalStyleVO> getCSObjectsByRegion(@RequestBody RegionVO param) {
        ArrayList<ConditionalStyleVO> result = new ArrayList<ConditionalStyleVO>();
        List<DesignConditionalStyle> allCSInForm = this.conditionalStyleController.getCSByRegion(param.getFormKey(), param.getStart(), param.getEnd());
        for (DesignConditionalStyle cs : allCSInForm) {
            result.add(this.transConditionalStyle(cs));
        }
        return result;
    }

    @ApiOperation(value="\u5bf9\u9009\u4e2d\u7684\u533a\u57df\u8fdb\u884c\u6761\u4ef6\u6837\u5f0f\u8bbe\u7f6e")
    @PostMapping(value={"setObjectByRegion"})
    @Transactional
    public void setCSObjectsInRegion(@RequestBody FixObjectVO params) throws JQException {
        RegionVO region = params.getRegionVO();
        List<DesignConditionalStyle> csByRegion = this.conditionalStyleController.getCSByRegion(region.getFormKey(), region.getStart(), region.getEnd());
        this.conditionalStyleController.deleteCS(csByRegion);
        List<DesignConditionalStyle> insertList = this.getRegionCSObjects(params);
        this.insertCSObjects(insertList);
    }

    public List<DesignConditionalStyle> getRegionCSObjects(FixObjectVO params) {
        ArrayList<DesignConditionalStyle> result = new ArrayList<DesignConditionalStyle>();
        RegionVO region = params.getRegionVO();
        List<ConditionalStyleVO> list = params.getList();
        CoordObject start = CellCoordChangeUtil.StrToNum(region.getStart());
        CoordObject end = CellCoordChangeUtil.StrToNum(region.getEnd());
        List allLinksInForm = this.designTimeViewController.getAllLinksInForm(region.getFormKey());
        ArrayList linkPos = new ArrayList();
        allLinksInForm.forEach(a -> {
            CoordObject coord = new CoordObject();
            coord.setPosX(a.getPosX());
            coord.setPosY(a.getPosY());
            linkPos.add(coord);
        });
        for (int i = start.getPosX(); i <= end.getPosX(); ++i) {
            for (int j = start.getPosY(); j <= end.getPosY(); ++j) {
                CoordObject coordObject = new CoordObject();
                coordObject.setPosX(i);
                coordObject.setPosY(j);
                for (ConditionalStyleVO cs : list) {
                    if (!linkPos.contains(coordObject)) continue;
                    DesignConditionalStyle object = this.transConditionalStyleVO(cs);
                    DesignDataLinkDefine dataLinkDefine = this.designTimeViewController.queryDataLinkDefine(region.getFormKey(), i, j);
                    object.setLinkKey(dataLinkDefine.getKey());
                    object.setPosX(i);
                    object.setPosY(j);
                    result.add(object);
                }
            }
        }
        return result;
    }

    public DesignConditionalStyle transConditionalStyleVO(ConditionalStyleVO param) {
        DesignConditionalStyleImpl result = new DesignConditionalStyleImpl();
        result.setKey(param.getKey());
        result.setFormKey(param.getFormKey());
        result.setPosX(param.getPosX());
        result.setPosY(param.getPosY());
        result.setLinkKey(param.getLinkKey());
        result.setBold(param.getBold());
        result.setItalic(param.getItalic());
        result.setReadOnly(param.getReadOnly());
        result.setForeGroundColor(param.getForeGroundColor());
        result.setFontColor(param.getFontColor());
        result.setStyleExpression(param.getStyleExpression());
        result.setOrder(param.getOrder());
        result.setHorizontalBar(param.getHorizontalBar());
        result.setStrikeThrough(param.getStrikeThrough());
        return result;
    }

    public ConditionalStyleVO transConditionalStyle(DesignConditionalStyle param) {
        ConditionalStyleVO result = new ConditionalStyleVO();
        result.setKey(param.getKey());
        result.setFormKey(param.getFormKey());
        result.setPosX(param.getPosX());
        result.setPosY(param.getPosY());
        result.setLinkKey(param.getLinkKey());
        result.setBold(param.getBold());
        result.setItalic(param.getItalic());
        result.setReadOnly(param.getReadOnly());
        result.setForeGroundColor(param.getForeGroundColor());
        result.setFontColor(param.getFontColor());
        result.setStyleExpression(param.getStyleExpression());
        result.setOrder(param.getOrder());
        result.setUpdateTime(param.getUpdateTime());
        result.setHorizontalBar(param.getHorizontalBar());
        result.setStrikeThrough(param.getStrikeThrough());
        return result;
    }

    private boolean checkDifferent(Map<String, List<DesignConditionalStyle>> order) {
        Set<String> formulas = order.keySet();
        for (String formula : formulas) {
            List<DesignConditionalStyle> CSs = order.get(formula);
            long total1 = 0L;
            for (DesignConditionalStyle cs : CSs) {
                total1 += (long)cs.hashCode();
            }
            long total2 = (long)CSs.get(0).hashCode() * (long)CSs.size();
            if (total1 == total2) continue;
            return false;
        }
        return true;
    }
}

