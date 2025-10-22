/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.efdc.web;

import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.internal.utils.EFDCConstants;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import com.jiuqi.nr.efdc.service.SoluctionQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/efdc"})
@Api(tags={"EFDC"})
public class FormluaSchemeController {
    @Autowired
    private SoluctionQueryService service;

    @PostMapping(value={"/save-formula"})
    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u914d\u7f6e\u4fe1\u606f")
    public NrResult saveFormluas(@Valid @RequestBody List<QueryObjectImpl> obj) {
        NrResult result = this.service.insertSoluction(obj);
        return result;
    }

    @PostMapping(value={"/delete-formula"})
    @ApiOperation(value="\u5220\u9664\u914d\u7f6e\u4fe1\u606f")
    public NrResult deleteFormula(@Valid @RequestBody QueryObjectImpl obj) {
        return this.service.clearByFormScheme(obj, new ArrayList<String>());
    }

    @PostMapping(value={"/delete-choose"})
    @ApiOperation(value="\u5220\u9664\u9009\u4e2d\u7684\u914d\u7f6e\u4fe1\u606f")
    public NrResult deleteFormula(@Valid @RequestBody List<QueryObjectImpl> obj) {
        List<String> collect = obj.stream().map(e -> e.getMainDim()).collect(Collectors.toList());
        return this.service.clearByFormScheme(obj.get(0), collect);
    }

    @PostMapping(value={"/query-formula"})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u914d\u7f6e\u4fe1\u606f")
    public NrResult getForlumlaMapping(@Valid @RequestBody QueryObjectImpl obj) {
        NrResult result = new NrResult();
        if (obj != null) {
            List<QueryObjectImpl> soluctions = this.service.getSoluctions(obj);
            result.setData(soluctions);
            result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        }
        return result;
    }
}

