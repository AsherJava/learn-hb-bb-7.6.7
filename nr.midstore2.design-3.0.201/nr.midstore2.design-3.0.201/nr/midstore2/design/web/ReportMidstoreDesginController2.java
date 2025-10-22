/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  nr.midstore2.data.extension.bean.ReportDataSourceDTO
 *  nr.midstore2.data.param.IReportMidstoreParamService
 *  nr.midstore2.data.param.IReportMidstoreSchemeQueryService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.midstore2.design.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.param.IReportMidstoreParamService;
import nr.midstore2.data.param.IReportMidstoreSchemeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/mistore2/report/desgin"})
@Api(tags={"\u4e2d\u95f4\u5e93\u62a5\u8868\u4ea4\u6362\u65b9\u6848\u6269\u5c55"})
public class ReportMidstoreDesginController2 {
    @Autowired
    private IReportMidstoreParamService paramService;
    @Autowired
    private IReportMidstoreSchemeQueryService reportService;

    @GetMapping(value={"/getTaskDimVisible/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u53ef\u89c1\u7684\u60c5\u666fID")
    public List<String> getTaskDimVisible(@PathVariable(value="taskKey") String taskKey) throws JQException {
        return this.paramService.getVisibleEntityIds(taskKey);
    }

    @GetMapping(value={"/getHasDimension/{schemeCode}"})
    @ApiOperation(value="\u83b7\u53d6\u4e2d\u95f4\u5e93\u65b9\u6848\u662f\u5426\u5305\u542b\u60c5\u666f\u5b57\u6bb5")
    public boolean getHasDimension(@PathVariable(value="schemeCode") String schemeCode) throws JQException {
        boolean res = true;
        List extData = this.reportService.getDataSoruceBySchemeCodes(Arrays.asList(schemeCode.split(",")));
        for (ReportDataSourceDTO d : extData) {
            if (d.isUseDimensionField()) continue;
            res = false;
            break;
        }
        return res;
    }
}

