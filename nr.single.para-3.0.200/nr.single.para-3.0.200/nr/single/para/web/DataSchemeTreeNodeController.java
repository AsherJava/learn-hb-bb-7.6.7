/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.ITreeIterator
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.internal.tree.DesignDataSchemeTreeServiceImpl
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.single.para.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.ITreeIterator;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DesignDataSchemeTreeServiceImpl;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.para.upload.param.SchemeNodeFilterByPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/single/dataScheme/query"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u6811\u578b\u67e5\u8be2"})
public class DataSchemeTreeNodeController {
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private DesignDataSchemeTreeServiceImpl dataSchemeTreeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    private static final String[] common = new String[]{"N", "H", "J", "Y", "X", "R", "Z"};

    @PostMapping(value={"/getRoot/{periodType}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u6811\u6839\u6811\u578b")
    public List<ITree<DataSchemeNode>> getRoot(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param, @PathVariable(value="periodType") String jioPeriodType) {
        if (!Arrays.asList(common).contains(jioPeriodType)) {
            jioPeriodType = "B";
        }
        SchemeNodeFilterByPeriod filter = new SchemeNodeFilterByPeriod(this.dataSchemeService, jioPeriodType);
        List rootTree = this.dataSchemeTreeService.getSchemeGroupRootTree(NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), (NodeFilter)filter);
        return this.getAuthNode(rootTree);
    }

    @PostMapping(value={"/getChild/{periodType}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u6811\u5b50\u8282\u70b9")
    public List<ITree<DataSchemeNode>> getChild(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param, @PathVariable(value="periodType") String jioPeriodType) {
        if (!Arrays.asList(common).contains(jioPeriodType)) {
            jioPeriodType = "B";
        }
        SchemeNodeFilterByPeriod filter = new SchemeNodeFilterByPeriod(this.dataSchemeService, jioPeriodType);
        List ChildTree = this.dataSchemeTreeService.getSchemeGroupChildTree((DataSchemeNode)param.getDataSchemeNode(), NodeType.SCHEME_GROUP.getValue() + NodeType.SCHEME.getValue(), (NodeFilter)filter);
        return this.getAuthNode(ChildTree);
    }

    @PostMapping(value={"/getLocation/{periodType}"})
    @ApiOperation(value="\u6570\u636e\u65b9\u6848\u6811\u6570\u636e\u5b9a\u4f4d")
    public List<ITree<DataSchemeNode>> getLocation(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param, @PathVariable(value="periodType") String jioPeriodType) {
        if (!Arrays.asList(common).contains(jioPeriodType)) {
            jioPeriodType = "B";
        }
        SchemeNodeFilterByPeriod filter = new SchemeNodeFilterByPeriod(this.dataSchemeService, jioPeriodType);
        List specified = this.dataSchemeTreeService.getSchemeGroupSpecifiedTree((DataSchemeNode)param.getDataSchemeNode(), NodeType.SCHEME_GROUP.getValue() + NodeType.SCHEME.getValue(), (NodeFilter)filter);
        return this.getAuthNode(specified);
    }

    private List<ITree<DataSchemeNode>> getAuthNode(List<ITree<DataSchemeNode>> param) {
        List allDataScheme = this.dataSchemeService.getAllDataScheme();
        Map<String, DesignDataScheme> collect = allDataScheme.stream().collect(Collectors.toMap(Basic::getKey, DesignDataScheme2 -> DesignDataScheme2));
        for (ITree<DataSchemeNode> iTree : param) {
            ITreeIterator iterator = iTree.iterator(null);
            while (iterator.hasNext()) {
                ITree next = (ITree)iterator.next();
                DataSchemeNode data = (DataSchemeNode)next.getData();
                if (data.getType() != NodeType.SCHEME.getValue()) continue;
                DesignDataScheme dataScheme = collect.get(data.getKey());
                if (dataScheme != null && dataScheme.getType() == DataSchemeType.QUERY) {
                    next.setDisabled(true);
                    continue;
                }
                if (this.dataSchemeAuthService.canWriteScheme(data.getKey())) continue;
                next.setDisabled(true);
            }
        }
        return param;
    }
}

