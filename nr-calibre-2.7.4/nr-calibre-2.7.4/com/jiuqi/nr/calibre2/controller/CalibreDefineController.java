/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.exception.CalibreDefineException;
import com.jiuqi.nr.calibre2.exception.CalibreDefineServiceException;
import com.jiuqi.nr.calibre2.service.ICalibreDefineManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u53e3\u5f84\u5b9a\u4e49\u670d\u52a1"})
public class CalibreDefineController {
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private ICalibreDefineManageService calibreDefineManageService;

    @ApiOperation(value="\u65b0\u589e\u53e3\u5f84")
    @PostMapping(value={"insert_define"})
    public UpdateResult insertCalibre(@RequestBody CalibreDefineDTO calibreDefineDTO) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> add = this.calibreDefineService.add(calibreDefineDTO);
            result = add.getData();
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.INSERT_CHILDREN_ERROR, e.getMessage());
        }
        return result;
    }

    @ApiOperation(value="\u5220\u9664\u53e3\u5f84")
    @PostMapping(value={"delete_define/{defineKey}"})
    public UpdateResult deleteCalibre(@PathVariable String defineKey) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> delete = this.calibreDefineService.delete(defineKey);
            result = delete.getData();
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.DELETE_CHILDREN_ERROR, e.getMessage());
        }
        return result;
    }

    @ApiOperation(value="\u590d\u5236\u53e3\u5f84")
    @PostMapping(value={"copy_define"})
    public List<UpdateResult> copyCalibre(@RequestBody CalibreDefineDTO calibreDefineDTO) throws JQException {
        List<UpdateResult> data;
        try {
            Result<List<UpdateResult>> copy = this.calibreDefineManageService.copy(calibreDefineDTO);
            data = copy.getData();
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.INSERT_COPY_ERROR, e.getMessage());
        }
        return data;
    }

    @ApiOperation(value="\u641c\u7d22\u53e3\u5f84")
    @PostMapping(value={"get_define"})
    public CalibreDefineDTO searchCalibre(@RequestBody CalibreDefineDTO calibreDefineDTO) {
        Result<CalibreDefineDTO> result = this.calibreDefineService.get(calibreDefineDTO);
        return result.getData();
    }

    @ApiOperation(value="\u4fee\u6539\u53e3\u5f84")
    @PostMapping(value={"update_define"})
    public UpdateResult updateCalibre(@RequestBody CalibreDefineDTO calibreDefineDTO) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> update = this.calibreDefineService.update(calibreDefineDTO);
            result = update.getData();
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.UPDATE_CHILDREN_ERROR, e.getMessage());
        }
        return result;
    }

    @ApiOperation(value="\u641c\u7d22\u53e3\u5f84\u96c6\u5408")
    @PostMapping(value={"list_define"})
    public List<CalibreDefineDTO> listGroup(@RequestBody CalibreDefineDTO groupDTO) {
        Result<List<CalibreDefineDTO>> result = this.calibreDefineService.list(groupDTO);
        return result.getData();
    }

    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u53e3\u5f84")
    @PostMapping(value={"batch_delete_define"})
    public List<UpdateResult> batchCalibre(@RequestBody List<String> keys) throws JQException {
        List<UpdateResult> updateResultList;
        try {
            Result<List<UpdateResult>> result = this.calibreDefineManageService.batchDeleteCalibreDefine(keys);
            updateResultList = result.getData();
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.DELETE_CHILDREN_ERROR, e.getMessage());
        }
        return updateResultList;
    }

    @ApiOperation(value="\u79fb\u52a8\u53e3\u5f84")
    @PostMapping(value={"move_define"})
    public int[] moveCalibre(@RequestBody CalibreDefineDTO calibreDefineDTO) throws Exception {
        return this.calibreDefineManageService.moveCalibre(calibreDefineDTO);
    }

    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u5b58\u5728\u76f8\u540ccode\u7684\u53e3\u5f84")
    @GetMapping(value={"is_same_code/{code}"})
    public Boolean isSameCode(@PathVariable String code) throws JQException {
        try {
            return this.calibreDefineManageService.isSameCalibreCode(code);
        }
        catch (CalibreDefineServiceException e) {
            throw new JQException((ErrorEnum)CalibreDefineException.DELETE_CHILDREN_ERROR);
        }
    }
}

