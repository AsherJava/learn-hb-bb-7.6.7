/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.period.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.cache.ClearCache;
import com.jiuqi.nr.period.common.rest.ErrorPosInfo;
import com.jiuqi.nr.period.common.rest.ExportObj;
import com.jiuqi.nr.period.common.rest.PeriodDataObject;
import com.jiuqi.nr.period.common.rest.PeriodObject;
import com.jiuqi.nr.period.common.rest.PeriodPage;
import com.jiuqi.nr.period.common.rest.PeriodY13Obj;
import com.jiuqi.nr.period.common.rest.SimpleTitleObj;
import com.jiuqi.nr.period.common.rest.VailFormObject;
import com.jiuqi.nr.period.common.tree.Data;
import com.jiuqi.nr.period.common.tree.TreeObj;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.Period13Info;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.SearchParam;
import com.jiuqi.nr.period.common.utils.SearchType;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.init.PeriodCreateProcessor;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import com.jiuqi.nr.period.util.TitleState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"/nr/period"})
@Api(tags={"\u65f6\u671f\u7ba1\u7406"})
public class PeriodController {
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDateService;
    @Autowired
    private ClearCache clearCache;
    public static int pageSize = 20;
    @Autowired
    PeriodEngineService periodEngineService;

    @GetMapping(value={"/getPeriodTree/{node}"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u6811\u5f62")
    @RequiresPermissions(value={"nr:period_tree:query"})
    public List<TreeObj> getPeriodTree(@PathVariable String node) throws Exception {
        TreeObj treeNode1;
        List<TreeObj> tree = PeriodUtils.ctrateTree();
        ArrayList<TreeObj> customPeriod = new ArrayList<TreeObj>();
        ArrayList<TreeObj> defaultPeriod = new ArrayList<TreeObj>();
        ArrayList<TreeObj> period13 = new ArrayList<TreeObj>();
        List<IPeriodEntity> defaults = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.DEFAULT));
        List<IPeriodEntity> customs = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.CUSTOM));
        List<IPeriodEntity> period13s = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.PERIOD13));
        for (IPeriodEntity periodDefine : defaults) {
            treeNode1 = PeriodController.getTreeObj(periodDefine);
            defaultPeriod.add(treeNode1);
        }
        for (IPeriodEntity periodDefine : customs) {
            treeNode1 = PeriodController.getTreeObj(periodDefine);
            customPeriod.add(treeNode1);
        }
        for (IPeriodEntity periodDefine : period13s) {
            treeNode1 = PeriodController.getTreeObj(periodDefine);
            treeNode1.getData().setPeriod13Info(this.createPeriod13Info(periodDefine));
            period13.add(treeNode1);
        }
        tree.get(0).setChildren(defaultPeriod);
        tree.get(1).setChildren(customPeriod);
        tree.get(2).setChildren(period13);
        for (TreeObj treeObj : tree) {
            if (treeObj.getCode().equals(node)) {
                treeObj.setSelected(true);
                treeObj.setExpended(true);
                continue;
            }
            if (treeObj.getChildren() == null || treeObj.getChildren().size() == 0) continue;
            for (TreeObj child : treeObj.getChildren()) {
                if (!child.getCode().equals(node)) continue;
                treeObj.setExpended(true);
                child.setSelected(true);
            }
        }
        return tree;
    }

    private Period13Info createPeriod13Info(IPeriodEntity periodEntity) throws Exception {
        Period13Info info = new Period13Info();
        info.setStartYear(periodEntity.getMinYear());
        info.setEndYear(periodEntity.getMaxYear());
        if (periodEntity.getMinFiscalMonth() == 0) {
            info.setPeriod0(true);
        } else {
            info.setPeriod0(false);
        }
        if (periodEntity.getMaxFiscalMonth() > 12) {
            info.setPeriod13(true);
            info.setNum(periodEntity.getMaxFiscalMonth());
        } else {
            info.setPeriod13(false);
        }
        return info;
    }

    private static TreeObj getTreeObj(IPeriodEntity periodDefine) {
        TreeObj treeNode1 = new TreeObj();
        treeNode1.setId(periodDefine.getKey());
        treeNode1.setCode(PeriodUtils.removePerfix(periodDefine.getCode()));
        treeNode1.setTitle(periodDefine.getTitle());
        treeNode1.setIsLeaf(true);
        treeNode1.setType(PeriodUtils.getPeriodType(periodDefine.getType()));
        Data data1 = new Data();
        data1.setKey(periodDefine.getKey());
        data1.setCode(PeriodUtils.removePerfix(periodDefine.getCode()));
        data1.setTitle(periodDefine.getTitle());
        data1.setType(PeriodUtils.getPeriodType(periodDefine.getType()));
        data1.setPeriodPropertyGroup(periodDefine.getPeriodPropertyGroup());
        treeNode1.setData(data1);
        return treeNode1;
    }

    @GetMapping(value={"/queryAllPeriod/{type}"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u6570\u636e")
    @RequiresPermissions(value={"nr:period_data:query"})
    public List<PeriodObject> queryAllPeriod(@PathVariable String type) throws JQException {
        try {
            ArrayList<PeriodObject> periodObjects = new ArrayList<PeriodObject>();
            if (NrPeriodConst.DEFAULT_PERIOD_NODE.equals(type)) {
                List<IPeriodEntity> defaultPeriodList = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.DEFAULT));
                for (IPeriodEntity t2 : defaultPeriodList) {
                    if (PeriodUtils.isPeriod13(t2.getCode(), t2.getPeriodType())) continue;
                    PeriodObject periodObject = PeriodUtils.defineToObject(t2);
                    List<IPeriodRow> periodDataDefines = this.periodDateService.queryPeriodDataByPeriodCodeLanguage(t2.getCode(), this.getLanguage());
                    Date startdate = PeriodUtils.minDefine(periodDataDefines).getStartDate();
                    Date enddate = PeriodUtils.maxDefine(periodDataDefines).getStartDate();
                    periodObject.setStartdate(PeriodUtils.dateToString(startdate));
                    periodObject.setEnddate(PeriodUtils.dateToString(enddate));
                    periodObject.setCode(PeriodUtils.removePerfix(t2.getCode()));
                    periodObjects.add(periodObject);
                }
            } else if (NrPeriodConst.PERIOD_13Y_NODE.equals(type)) {
                List<IPeriodEntity> period13ys = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.PERIOD13));
                for (IPeriodEntity t3 : period13ys) {
                    PeriodObject periodObject = PeriodUtils.defineToObject(t3);
                    List<IPeriodRow> iPeriodRows = this.periodDateService.queryPeriodDataByPeriodCode(t3.getCode());
                    periodObject.setStartdate(t3.getMinYear() + "");
                    periodObject.setEnddate(t3.getMaxYear() + "");
                    periodObject.setPeriod13("period13");
                    periodObjects.add(periodObject);
                }
            } else {
                List<IPeriodEntity> customPeriodList = this.periodService.queryPeriodList(new SearchParam(this.getLanguage(), SearchType.CUSTOM));
                customPeriodList.forEach(t -> {
                    PeriodObject periodObject = PeriodUtils.defineToObject(t);
                    periodObjects.add(periodObject);
                });
            }
            return periodObjects;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_105, (Throwable)e);
        }
    }

    private String getLanguage() {
        return NpContextHolder.getContext().getLocale().getLanguage();
    }

    @PostMapping(value={"/queryPeriodByKey"})
    @ApiOperation(value="\u6839\u636eID\u67e5\u8be2\u65f6\u671f\u6570\u636e")
    public PeriodObject queryPeriodByKey(@RequestBody String key) throws JQException {
        if (StringUtils.isEmpty(key)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            IPeriodEntity queryPeriodByKey = this.periodService.queryPeriodByKeyLanguage(key, this.getLanguage());
            return PeriodUtils.defineToObject(queryPeriodByKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
    }

    @PostMapping(value={"/extensionDefaultPeriod"})
    @ApiOperation(value="\u6269\u5c55\u9ed8\u8ba4\u65f6\u671f")
    public void extensionDefaultPeriod(@RequestBody PeriodObject periodObject) throws JQException {
        if (StringUtils.isEmpty(periodObject.getCode()) || StringUtils.isEmpty(periodObject.getStartdate()) || StringUtils.isEmpty(periodObject.getEnddate())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_100);
        }
        try {
            PeriodDefineImpl ipe = new PeriodDefineImpl();
            ipe.setCode(periodObject.getCode());
            ipe.setType(PeriodUtils.periodOfType(periodObject.getType()));
            this.periodService.extensionDefaultPeriod(ipe, periodObject.getStartdate(), periodObject.getEnddate());
            this.periodService.updateEntityInfo(ipe.getCode());
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_107, (Throwable)e);
        }
    }

    @GetMapping(value={"/queryPeriodDataByPeriodKey/{id}/{page}"})
    @ApiOperation(value="\u6839\u636e\u65f6\u671fID\u67e5\u8be2\u65f6\u671f\u6570\u636e")
    @RequiresPermissions(value={"nr:period_data:query"})
    public PeriodPage queryPeriodDataByPeriodKey(@PathVariable String id, @PathVariable int page) throws JQException {
        if (StringUtils.isEmpty(id) || page < 0) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            PeriodPage periodPage = new PeriodPage();
            List<IPeriodRow> queryPeriodDataByPeriodCode = this.periodDateService.queryPeriodDataByPeriodCodeLanguage(id, this.getLanguage());
            IPeriodEntity periodEntity = this.periodService.queryPeriodByKeyLanguage(id, this.getLanguage());
            PeriodPropertyGroup periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
            List<PeriodDataObject> periodDataObjects = this.periodRowToPeriodDataSelectObject(periodPropertyGroup, queryPeriodDataByPeriodCode, periodEntity);
            ArrayList<PeriodDataObject> datalist = new ArrayList<PeriodDataObject>();
            for (int i = 0; i < periodDataObjects.size(); ++i) {
                if (i < (page - 1) * pageSize || i >= page * pageSize) continue;
                if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
                    periodDataObjects.get(i).setPeriod13("period13data");
                }
                datalist.add(periodDataObjects.get(i));
            }
            periodPage.setList(datalist);
            periodPage.setPage(page);
            periodPage.setPageCount(queryPeriodDataByPeriodCode.size());
            return periodPage;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
    }

    private List<PeriodDataObject> periodRowToPeriodDataSelectObject(PeriodPropertyGroup periodPropertyGroup, List<IPeriodRow> queryPeriodDataByPeriodCode, IPeriodEntity iPeriodEntity) {
        ArrayList<PeriodDataObject> periodDataObjects = new ArrayList<PeriodDataObject>();
        if (periodPropertyGroup == null) {
            queryPeriodDataByPeriodCode.stream().forEach(t -> periodDataObjects.add(PeriodUtils.defineToObject(t, iPeriodEntity)));
        } else {
            switch (periodPropertyGroup) {
                case PERIOD_GROUP_BY_YEAR: {
                    queryPeriodDataByPeriodCode.stream().forEach(t -> {
                        PeriodDataObject periodDataObject = PeriodUtils.defineToObject(t, iPeriodEntity);
                        periodDataObject.setGroup(t.getYear() + PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR.getGroupName());
                        periodDataObjects.add(periodDataObject);
                    });
                    break;
                }
            }
        }
        return periodDataObjects;
    }

    @PostMapping(value={"/updateCustomPeriod"})
    @ApiOperation(value="\u6dfb\u52a0\u6216\u66f4\u65b0\u81ea\u5b9a\u4e49\u65f6\u671f")
    public void updateCustomPeriod(@RequestBody PeriodObject periodObject) throws JQException {
        if (StringUtils.isEmpty(periodObject.getType())) {
            periodObject.setType(PeriodUtils.getPeriodType(PeriodType.CUSTOM));
        }
        if (!PeriodType.CUSTOM.equals((Object)PeriodUtils.periodOfType(periodObject.getType()))) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_102);
        }
        try {
            PeriodDefineImpl ipe = new PeriodDefineImpl();
            BeanUtils.copyProperties(periodObject, ipe);
            ipe.setType(PeriodUtils.periodOfType(periodObject.getType()));
            if (StringUtils.isEmpty(periodObject.getKey())) {
                this.periodService.insertCustomPeriodLanguage(ipe, this.getLanguage());
            } else {
                this.periodService.updateCustomPeriodLanguage(ipe, this.getLanguage());
            }
            this.periodService.updateEntityInfo(ipe.getCode());
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_108, (Throwable)e);
        }
    }

    @GetMapping(value={"/deleteCustomPeriod/{id}"})
    @ApiOperation(value="\u5220\u9664\u81ea\u5b9a\u4e49\u65f6\u671f")
    public void deleteCustomPeriod(@PathVariable String id) throws JQException {
        if (StringUtils.isEmpty(id)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            this.periodService.deleteCustomPeriod(id);
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_109, (Throwable)e);
        }
    }

    @PostMapping(value={"/updateCustomPeriodData"})
    @ApiOperation(value="\u6dfb\u52a0\u6216\u66f4\u65b0\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e")
    public void updateCustomPeriodData(@RequestBody PeriodDataObject periodDataObject) throws JQException {
        if (StringUtils.isEmpty(periodDataObject.getPeriodKey()) || StringUtils.isEmpty(periodDataObject.getStartdate()) || StringUtils.isEmpty(periodDataObject.getEnddate())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            IPeriodEntity iPeriodEntity = this.periodService.queryPeriodByKey(periodDataObject.getPeriodKey());
            int dataType = iPeriodEntity.getDataType();
            if (iPeriodEntity.getPeriodType() != PeriodType.CUSTOM) {
                IPeriodRow iPeriodRow = this.periodDateService.queryPeriodDataByPeriodCodeAndDataCode(periodDataObject.getPeriodKey(), periodDataObject.getCode());
                if (null == iPeriodRow) {
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_105);
                }
                PeriodDataDefineImpl pdf = new PeriodDataDefineImpl();
                BeanUtils.copyProperties(iPeriodRow, pdf);
                pdf.setTitle(periodDataObject.getTitle());
                if (StringUtils.isEmpty(periodDataObject.getSimpleTitle()) || periodDataObject.getSimpleTitle().equals(PeriodUtils.getDefaultShowTitle(iPeriodEntity.getPeriodType(), iPeriodRow))) {
                    pdf.setSimpleTitle(null);
                } else {
                    pdf.setSimpleTitle(periodDataObject.getSimpleTitle());
                }
                this.periodDateService.checkTitle(iPeriodEntity, pdf, this.getLanguage());
                this.periodDateService.updateCustomPeriodLanguage(pdf, periodDataObject.getPeriodKey(), this.getLanguage());
                if (!iPeriodRow.getTitle().equals(periodDataObject.getTitle())) {
                    dataType |= TitleState.TITLE.getValue();
                }
                if (StringUtils.isNotEmpty(pdf.getSimpleTitle())) {
                    dataType |= TitleState.SIMPLE_TITLE.getValue();
                }
            } else {
                IPeriodRow iPeriodRow = this.periodDateService.queryPeriodDataByPeriodCodeAndDataCode(periodDataObject.getPeriodKey(), periodDataObject.getCode());
                this.periodDateService.handleValidDate(periodDataObject);
                PeriodDataDefineImpl pdf = new PeriodDataDefineImpl();
                BeanUtils.copyProperties(periodDataObject, pdf);
                pdf.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataObject.getStartdate(), true));
                pdf.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataObject.getEnddate(), true));
                this.periodDateService.checkTitle(iPeriodEntity, pdf, this.getLanguage());
                dataType |= TitleState.TITLE.getValue();
                if (StringUtils.isEmpty(periodDataObject.getKey())) {
                    this.periodDateService.insertCustomPeriodLanguage(pdf, periodDataObject.getPeriodKey(), this.getLanguage());
                    if (StringUtils.isNotEmpty(pdf.getSimpleTitle()) && !pdf.getTitle().equals(pdf.getSimpleTitle())) {
                        dataType |= TitleState.SIMPLE_TITLE.getValue();
                    }
                } else {
                    this.periodDateService.updateCustomPeriodLanguage(pdf, periodDataObject.getPeriodKey(), this.getLanguage());
                    if (StringUtils.isNotEmpty(pdf.getSimpleTitle()) && !pdf.getSimpleTitle().equals(iPeriodRow.getSimpleTitle())) {
                        dataType |= TitleState.SIMPLE_TITLE.getValue();
                    }
                }
            }
            this.periodService.updateEntityInfo(iPeriodEntity.getCode());
            this.periodService.updatePeriodDate(iPeriodEntity);
            this.periodService.updateDataType(iPeriodEntity, dataType);
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_108, (Throwable)e);
        }
    }

    @PostMapping(value={"/updatePeriodY13Data"})
    @ApiOperation(value="\u66f4\u65b013\u671f\u6570\u636e")
    public void updatePeriodY13Data(@RequestBody PeriodDataObject periodDataObject) throws JQException {
        if (StringUtils.isEmpty(periodDataObject.getPeriodKey()) || StringUtils.isEmpty(periodDataObject.getStartdate()) || StringUtils.isEmpty(periodDataObject.getEnddate())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            IPeriodEntity iPeriodEntity = this.periodService.queryPeriodByKey(periodDataObject.getPeriodKey());
            if (iPeriodEntity.getPeriodType().equals((Object)PeriodType.MONTH) && !iPeriodEntity.getCode().equals(PeriodUtils.getPeriodType(iPeriodEntity.getPeriodType()))) {
                IPeriodRow iPeriodRow = this.periodDateService.queryPeriodDataByPeriodCodeAndDataCode(periodDataObject.getPeriodKey(), periodDataObject.getCode());
                if (null == iPeriodRow) {
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_105);
                }
                int dataType = TitleState.NONE.getValue();
                PeriodDataDefineImpl pdf = new PeriodDataDefineImpl();
                BeanUtils.copyProperties(iPeriodRow, pdf);
                pdf.setTitle(periodDataObject.getTitle());
                if (StringUtils.isNotEmpty(periodDataObject.getSimpleTitle())) {
                    pdf.setSimpleTitle(periodDataObject.getSimpleTitle());
                } else {
                    pdf.setSimpleTitle(PeriodUtils.autoMonthSimpleTitle(PeriodType.MONTH, iPeriodRow.getMonth()));
                }
                int qi = Integer.parseInt(iPeriodRow.getCode().substring(5));
                if (qi < 1 || qi > 12) {
                    pdf.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataObject.getStartdate(), true));
                    pdf.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataObject.getEnddate(), false));
                }
                this.periodDateService.checkTitle(iPeriodEntity, pdf, this.getLanguage());
                this.periodDateService.updatePeriod13DataLanguage(pdf, periodDataObject.getPeriodKey(), this.getLanguage());
                if (!iPeriodRow.getTitle().equals(periodDataObject.getTitle())) {
                    dataType |= TitleState.TITLE.getValue();
                }
                if (StringUtils.isNotEmpty(pdf.getSimpleTitle())) {
                    dataType |= TitleState.SIMPLE_TITLE.getValue();
                }
                this.periodService.updateEntityInfo(iPeriodEntity.getCode());
                this.periodService.updatePeriodDate(iPeriodEntity);
                this.periodService.updateDataType(iPeriodEntity, dataType);
                this.clearCache.clearCache();
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_108, (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteCustomPeriodData"})
    @ApiOperation(value="\u5220\u9664\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e")
    public void deleteCustomPeriodData(@RequestBody PeriodDataObject periodDataObject) throws JQException {
        if (StringUtils.isEmpty(periodDataObject.getPeriodKey()) || StringUtils.isEmpty(periodDataObject.getKey())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            PeriodDataDefineImpl pdf = new PeriodDataDefineImpl();
            BeanUtils.copyProperties(periodDataObject, pdf);
            this.periodDateService.deleteCustomPeriodData(pdf, periodDataObject.getPeriodKey());
            this.periodService.updateEntityInfo(pdf.getCode());
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_112, (Throwable)e);
        }
    }

    @GetMapping(value={"/autoCreateCustomDataCode/{code}"})
    @ApiOperation(value="\u81ea\u52a8\u751f\u6210\u81ea\u5b9a\u4e49\u65f6\u671f\u7f16\u7801")
    public PeriodObject autoCreateCustomDataCode(@PathVariable String code) throws JQException {
        PeriodObject periodObject = null;
        try {
            periodObject = this.periodDateService.autoCreateCustomDataCode(code);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_114, (Throwable)e);
        }
        return periodObject;
    }

    @PostMapping(value={"/handleValidForm"})
    @ApiOperation(value="\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e\u7f16\u7801\u6821\u9a8c")
    public void handleValidForm(@RequestBody VailFormObject vailFormObject) throws JQException {
        if (StringUtils.isNotEmpty(vailFormObject.getValidType()) && StringUtils.isNotEmpty(vailFormObject.getTableCode())) {
            try {
                this.periodDateService.handleValidForm(vailFormObject);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_115, (Throwable)e);
            }
        } else {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_111);
        }
    }

    @PostMapping(value={"/handleValidDate"})
    @ApiOperation(value="\u6821\u9a8c\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4")
    public void handleValidDate(@RequestBody PeriodDataObject periodDataObject) throws JQException {
        if (StringUtils.isEmpty(periodDataObject.getStartdate()) && StringUtils.isEmpty(periodDataObject.getEnddate()) || StringUtils.isEmpty(periodDataObject.getPeriodKey())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_100);
        }
        try {
            this.periodDateService.handleValidDate(periodDataObject);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_116, (Throwable)e);
        }
    }

    @GetMapping(value={"/initPeriodData"})
    @ApiOperation(value="\u6267\u884c\u521d\u59cb\u5316\u63a5\u53e3\u7c7b")
    public void initPeriodData() throws Exception {
        Logger logger = LoggerFactory.getLogger(PeriodCreateProcessor.class);
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u65f6\u671f\u6570\u636e");
        PeriodCreateProcessor bean = (PeriodCreateProcessor)SpringBeanUtils.getBean(PeriodCreateProcessor.class);
        bean.createData();
        for (IPeriodEntity periodEntity : this.periodService.getPeriodList()) {
            this.periodService.updateEntityInfo(periodEntity.getCode());
        }
        logger.info("\u65f6\u671f\u6570\u636e\u521d\u59cb\u5316\u5b8c\u6210");
    }

    @RequestMapping(value={"/exportData"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5bfc\u51fa\u65f6\u671f\u6570\u636e")
    public void exportData(@RequestBody ExportObj exportObj, HttpServletResponse res) throws Exception {
        if (exportObj == null || StringUtils.isEmpty(exportObj.getEntity())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_125);
        }
        this.periodDateService.exportData(exportObj.getEntity(), res);
    }

    @RequestMapping(value={"/importData"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5bfc\u5165\u65f6\u671f\u6570\u636e")
    public List<ErrorPosInfo> importData(String entity, @RequestParam(value="file") MultipartFile file) throws Exception {
        if (StringUtils.isEmpty(entity)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122);
        }
        List<ErrorPosInfo> errorPosInfos = this.periodDateService.importData(entity, file);
        this.periodService.updateEntityInfo(entity);
        this.clearCache.clearCache();
        return errorPosInfos;
    }

    @PostMapping(value={"/updatePeriod13Y"})
    @ApiOperation(value="\u521b\u5efa\u6216\u4fee\u653913\u671f\u65f6\u671f\u53ca\u6570\u636e")
    public void createPeriod13Y(@RequestBody PeriodY13Obj periodY13Obj) throws JQException {
        if (StringUtils.isEmpty(periodY13Obj.getCode()) || StringUtils.isEmpty(periodY13Obj.getTitle())) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_134);
        }
        try {
            if (Integer.parseInt(periodY13Obj.getYearStart()) > Integer.parseInt(periodY13Obj.getYearEnd())) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_135);
            }
            if (periodY13Obj.isPeriod13() && periodY13Obj.getPeriodNum() < 13) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_136);
            }
            List codeDatas = this.periodService.getPeriodList().stream().filter(e -> e.getCode().equals(periodY13Obj.getCode())).collect(Collectors.toList());
            if (codeDatas.isEmpty()) {
                PeriodDefineImpl ipe = new PeriodDefineImpl();
                ipe.setCode(periodY13Obj.getCode());
                ipe.setTitle(periodY13Obj.getTitle());
                ipe.setType(PeriodType.MONTH);
                ipe.setDataType(TitleState.TITLE.getValue() | TitleState.SIMPLE_TITLE.getValue());
                List<PeriodDataDefineImpl> periodY13Data = this.periodService.createPeriodY13Data(ipe, periodY13Obj, true);
                this.periodService.insertPeriodY13(ipe, this.getLanguage());
                this.periodService.insertPeriodY13Datas(ipe.getCode(), periodY13Data);
            } else if (codeDatas.size() == 1) {
                PeriodDefineImpl ipe = new PeriodDefineImpl();
                ipe.setKey(((IPeriodEntity)codeDatas.get(0)).getKey());
                ipe.setCode(((IPeriodEntity)codeDatas.get(0)).getCode());
                ipe.setTitle(periodY13Obj.getTitle());
                ipe.setType(PeriodType.MONTH);
                List<PeriodDataDefineImpl> periodY13Data = this.periodService.createPeriodY13Data(ipe, periodY13Obj, false);
                this.periodService.updatePeriodY13(ipe, this.getLanguage());
                List<IPeriodRow> iPeriodRows = this.periodDateService.queryPeriodDataByPeriodCode(ipe.getCode());
                if (!iPeriodRows.isEmpty()) {
                    List<String> deleteids = iPeriodRows.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    this.periodDateService.deleteCustomPeriodDatas(deleteids, ipe.getCode());
                }
                this.periodService.insertPeriodY13Datas(ipe.getCode(), periodY13Data);
                this.periodService.updateDataType(ipe, TitleState.TITLE.getValue() | TitleState.SIMPLE_TITLE.getValue());
            } else {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_134);
            }
            this.periodService.updateEntityInfo(periodY13Obj.getCode());
            this.clearCache.clearCache();
        }
        catch (Exception e2) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_134, (Throwable)e2);
        }
    }

    @PostMapping(value={"/updateSimpleTitle"})
    @ApiOperation(value="\u66f4\u65b0\u7b80\u79f0")
    public void updateSimpleTitle(@RequestBody SimpleTitleObj simpleTitleObj) throws JQException {
        try {
            this.periodDateService.updateSimpleTitle(simpleTitleObj);
            this.periodService.updateEntityInfo(simpleTitleObj.getPeriodKey());
            this.clearCache.clearCache();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_137, (Throwable)e);
        }
    }
}

