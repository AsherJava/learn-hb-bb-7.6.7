/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.service.ICalibreDataManageService;
import com.jiuqi.nr.calibre2.vo.BatchCalibreDataOptionsVO;
import com.jiuqi.nr.calibre2.vo.CalibreBatchBuildVO;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.common.itree.ITree;
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
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u53e3\u5f84\u6570\u636e\u670d\u52a1"})
public class CalibreDataController {
    @Autowired
    private ICalibreDataManageService dataManageService;

    @ApiOperation(value="\u641c\u7d22\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/search"})
    public List<ITree<CalibreDataVO>> search(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.search(calibreDataVO);
    }

    @ApiOperation(value="\u4fdd\u5b58\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/insert"})
    public CalibreDataVO insert(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.insert(calibreDataVO);
    }

    @ApiOperation(value="\u4fee\u6539\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/update"})
    public CalibreDataVO update(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.update(calibreDataVO);
    }

    @ApiOperation(value="\u5220\u9664\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/batch_delete"})
    public List<UpdateResult> batchDelete(@RequestBody BatchCalibreDataOptionsVO batchCalibreDataOptionsVO) throws JQException {
        return this.dataManageService.batchDelete(batchCalibreDataOptionsVO, false);
    }

    @ApiOperation(value="\u6279\u91cf\u79fb\u52a8")
    @PostMapping(value={"data/move"})
    public List<UpdateResult> batchMove(@RequestBody BatchCalibreDataOptionsVO batchCalibreDataOptionsVO) throws JQException {
        return this.dataManageService.batchMove(batchCalibreDataOptionsVO);
    }

    @ApiOperation(value="\u6279\u91cf\u6dfb\u52a0\u53e3\u5f84")
    @PostMapping(value={"data/batchAdd"})
    public List<UpdateResult> batchAdd(@RequestBody BatchCalibreDataOptionsVO batchCalibreDataOptionsVO) throws JQException {
        return this.dataManageService.batchAdd(batchCalibreDataOptionsVO);
    }

    @ApiOperation(value="\u521d\u59cb\u5316\u53e3\u5f84\u6570\u636e\u6811\u5f62")
    @PostMapping(value={"data/tree/init"})
    public List<ITree<CalibreDataVO>> initDataTree(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.initTree(calibreDataVO);
    }

    @ApiOperation(value="\u52a0\u8f7d\u53e3\u5f84\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9")
    @PostMapping(value={"data/tree/children"})
    public List<ITree<CalibreDataVO>> loadDataChildren(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.loadChildren(calibreDataVO);
    }

    @ApiOperation(value="\u5b9a\u4f4d\u53e3\u5f84\u6811\u5f62\u8282\u70b9")
    @PostMapping(value={"data/tree/location"})
    public List<ITree<CalibreDataVO>> location(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.locationTree(calibreDataVO);
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u7684\u7236\u8282\u70b9\u8def\u5f84")
    @PostMapping(value={"data/path"})
    public CalibreDataVO getDataPath(@RequestBody CalibreDataVO calibreDataVO) {
        return this.dataManageService.getDataPath(calibreDataVO);
    }

    @ApiOperation(value="\u67e5\u8be2\u5355\u4e2a\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/get"})
    public CalibreDataVO queryCalibre(@RequestBody CalibreDataVO calibreDataVO) {
        return this.dataManageService.queryCalibre(calibreDataVO);
    }

    @ApiOperation(value="\u5224\u65ad\u53e3\u5f84\u5185\u662f\u5426\u542b\u6709\u6570\u636e")
    @GetMapping(value={"data/exsit/{code}"})
    public Boolean existCalibre(@PathVariable String code) {
        return this.dataManageService.existCalibreData(code);
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u53e3\u5f84")
    @GetMapping(value={"data/all/{code}"})
    public List<CalibreDataVO> listAll(@PathVariable String code) {
        return this.dataManageService.listAll(code);
    }

    @ApiOperation(value="\u641c\u7d22\u7b80\u5355\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"data/easy/search"})
    public List<CalibreDataVO> easySearch(@RequestBody CalibreDataVO calibreDataVO) throws JQException {
        return this.dataManageService.easysearch(calibreDataVO);
    }

    @ApiOperation(value="\u6279\u91cf\u751f\u6210\u53e3\u5f84")
    @PostMapping(value={"data/batchBuild"})
    public List<CalibreDataVO> batchBuild(@RequestBody CalibreBatchBuildVO batchBuildVO) {
        return this.dataManageService.batchBuild(batchBuildVO);
    }
}

