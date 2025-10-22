/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.filterTemplate.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.filterTemplate.exception.FilterTemplateException;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.filterTemplate.util.ObjConvert;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateSearchVO;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateVO;
import com.jiuqi.nr.filterTemplate.web.vo.SaveTipsVO;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@ApiOperation(value="\u8fc7\u6ee4\u6a21\u677f")
@RequestMapping(value={"api/v1/filterTemplate"})
public class FilterTemplateController {
    @Autowired
    private IFilterTemplateService filterTemplateService;
    private static final ObjConvert objConvert = new ObjConvert();

    @ApiOperation(value="\u65b0\u589e\u4e00\u4e2a\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/insert"}, method={RequestMethod.POST})
    public String insert(@RequestBody @SFDecrypt FilterTemplateVO filterTemplateVO) throws JQException {
        String newTemplateID;
        try {
            newTemplateID = this.filterTemplateService.insert(new FilterTemplateDTO(filterTemplateVO));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_INSERT_FAILED, e.getMessage());
        }
        return newTemplateID;
    }

    @ApiOperation(value="\u5220\u9664\u4e00\u4e2a\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/delete/{filterTemplateID}"}, method={RequestMethod.GET})
    public void delete(@PathVariable String filterTemplateID) throws JQException {
        try {
            this.filterTemplateService.delete(filterTemplateID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_DELETE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u4e00\u4e2a\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/update"}, method={RequestMethod.POST})
    public void update(@RequestBody @SFDecrypt FilterTemplateVO filterTemplateVO) throws JQException {
        try {
            this.filterTemplateService.update(new FilterTemplateDTO(filterTemplateVO));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_UPDATE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bf9\u6a21\u677f\u505a\u64cd\u4f5c\u65f6\u5019\u7684\u63d0\u793a \u5230\u4efb\u52a1")
    @RequestMapping(value={"/saveTips/{filterTemplateID}"}, method={RequestMethod.GET})
    public SaveTipsVO tips(@PathVariable String filterTemplateID) throws JQException {
        SaveTipsVO saveTipsVO;
        try {
            saveTipsVO = this.filterTemplateService.saveTips(filterTemplateID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_UPDATE_FAILED, e.getMessage());
        }
        return saveTipsVO;
    }

    @ApiOperation(value="\u6839\u636e\u5b9e\u4f53\u67e5\u8be2\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/queryByEntity/{entityID}"}, method={RequestMethod.GET})
    public List<FilterTemplateVO> getByEntity(@PathVariable String entityID) throws JQException {
        List<FilterTemplateDTO> filterTemplates;
        try {
            filterTemplates = this.filterTemplateService.getByEntity(entityID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_QUERY_FAILED, e.getMessage());
        }
        return objConvert.FilterTemplateDTO2VO(filterTemplates);
    }

    @ApiOperation(value="\u8fc7\u6ee4\u6a21\u677f\u590d\u5236")
    @RequestMapping(value={"/copy/{filterTemplateID}"}, method={RequestMethod.GET})
    public FilterTemplateVO copy(@PathVariable String filterTemplateID) throws JQException {
        FilterTemplateDTO entityViewDefineData;
        try {
            entityViewDefineData = this.filterTemplateService.copy(filterTemplateID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_COPY_FAILED, e.getMessage());
        }
        if (entityViewDefineData != null) {
            return new FilterTemplateVO(entityViewDefineData);
        }
        return null;
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/queryByTask/{taskID}"}, method={RequestMethod.GET})
    public List<FilterTemplateVO> getByTask(@PathVariable String taskID) throws JQException {
        List<FilterTemplateDTO> filterTemplate;
        try {
            filterTemplate = this.filterTemplateService.getByTaskRefEntity(taskID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_QUERY_FAILED, e.getMessage());
        }
        return objConvert.FilterTemplateDTO2VO(filterTemplate);
    }

    @ApiOperation(value="\u6839\u636e\u94fe\u63a5\u67e5\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/queryByDataLink/{fieldKey}"}, method={RequestMethod.GET})
    public List<FilterTemplateVO> getByDataLink(@PathVariable String fieldKey) throws JQException {
        List<FilterTemplateDTO> filterTemplate;
        try {
            filterTemplate = this.filterTemplateService.getByDataLinkRefEntity(fieldKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_QUERY_FAILED, e.getMessage());
        }
        return objConvert.FilterTemplateDTO2VO(filterTemplate);
    }

    @ApiOperation(value="\u6839\u636e\u6a21\u677fID\u67e5\u8fc7\u6ee4\u6a21\u677f")
    @RequestMapping(value={"/queryById/{id}"}, method={RequestMethod.GET})
    public FilterTemplateVO getById(@PathVariable String id) throws JQException {
        FilterTemplateDTO filterTemplate;
        try {
            filterTemplate = this.filterTemplateService.getFilterTemplate(id);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_QUERY_FAILED, e.getMessage());
        }
        return new FilterTemplateVO(filterTemplate);
    }

    @ApiOperation(value="\u6a21\u7cca\u641c\u7d22\u8fc7\u6ee4\u6a21\u677f\u548c\u5b9e\u4f53")
    @RequestMapping(value={"/search"}, method={RequestMethod.POST})
    public List<FilterTemplateSearchVO> search(@RequestBody FilterTemplateSearchVO searchVO) throws JQException {
        List<FilterTemplateSearchVO> searchResults;
        try {
            searchResults = this.filterTemplateService.search(searchVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FilterTemplateException.FILTER_TEMPLATE_QUERY_FAILED, e.getMessage());
        }
        return searchResults;
    }
}

