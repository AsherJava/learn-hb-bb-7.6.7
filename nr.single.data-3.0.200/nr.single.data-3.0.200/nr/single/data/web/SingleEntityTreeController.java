/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.single.data.web;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import io.swagger.annotations.Api;
import java.util.List;
import nr.single.data.bean.CheckParam;
import nr.single.data.bean.CheckResultNode;
import nr.single.data.bean.NrSingleDataErrorEnum;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/single/EntityTree"})
@Api(tags={"\u5355\u4f4d\u6811\u5f62\u670d\u52d9"})
public class SingleEntityTreeController {
    private static final Logger logger = LoggerFactory.getLogger(SingleEntityTreeController.class);
    @Autowired
    private IEntityTreeCheckService treeCheckSevice;

    @GetMapping(value={"/CheckTask/{taskKey}"})
    public List<CheckResultNode> checkEntityTreeByTask(@PathVariable String taskKey, String periodCode) throws Exception {
        if (StringUtils.isNotEmpty((CharSequence)taskKey)) {
            AsyncTaskMonitor monitor = null;
            return this.treeCheckSevice.CheckTreeNodeByTask(taskKey, periodCode, monitor);
        }
        throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_000);
    }

    @PostMapping(value={"/CheckErrors"})
    public List<CheckResultNode> checkEntityTree(@RequestBody CheckParam checkParam) throws Exception {
        if (StringUtils.isNotEmpty((CharSequence)checkParam.getTaskKey())) {
            AsyncTaskMonitor monitor = null;
            if (StringUtils.isEmpty((CharSequence)checkParam.getFormSchemeKey())) {
                return this.treeCheckSevice.CheckTreeNodeByTask(checkParam.getTaskKey(), checkParam.getPeriodCode(), monitor);
            }
            return this.treeCheckSevice.CheckTreeNodeByTask(checkParam.getTaskKey(), checkParam.getFormSchemeKey(), checkParam.getPeriodCode(), false, monitor);
        }
        throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_000);
    }
}

