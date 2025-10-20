/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  com.jiuqi.gcreport.onekeymerge.api.GCDiffProcessClient
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.onekeymerge.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.gcreport.onekeymerge.api.GCDiffProcessClient;
import com.jiuqi.gcreport.onekeymerge.service.GcDiffProcessService;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GCDiffProcessController
implements GCDiffProcessClient {
    @Autowired
    private GcDiffProcessService gcDiffProcessService;

    public BusinessResponseEntity<List<FormTreeVo>> queryDiffProcessReports(@PathVariable String schemeId, @PathVariable String dataTime) {
        List<FormTreeVo> formTree = this.gcDiffProcessService.queryDiffProcessReports(schemeId, dataTime);
        return BusinessResponseEntity.ok(this.getFormTreeVos(formTree));
    }

    private List<FormTreeVo> getFormTreeVos(List<FormTreeVo> formTree) {
        ArrayList<FormTreeVo> formTreeVos = new ArrayList<FormTreeVo>();
        for (FormTreeVo treeVo : formTree) {
            FormTreeVo formTreeVo = new FormTreeVo(treeVo.getId(), treeVo.getTitle(), treeVo.getCode(), treeVo.getSerialNumber(), new ArrayList());
            ArrayList<FormTreeVo> childrenFormTrees = new ArrayList<FormTreeVo>();
            for (FormTreeVo child : treeVo.getChildren()) {
                FormTreeVo childrenTreeVo = new FormTreeVo(child.getId(), child.getTitle(), child.getCode(), child.getSerialNumber(), new ArrayList());
                childrenFormTrees.add(childrenTreeVo);
            }
            formTreeVo.setChildren(childrenFormTrees);
            formTreeVos.add(formTreeVo);
        }
        return formTreeVos;
    }

    public BusinessResponseEntity<List<GcDiffProcessDataVO>> queryDifferenceIntermediateDatas(@RequestBody GcDiffProcessCondition condition) {
        return BusinessResponseEntity.ok(this.gcDiffProcessService.queryDifferenceIntermediateDatas(condition));
    }

    public BusinessResponseEntity<String> transferGroupWithinToOutside(@RequestBody GcDiffProcessCondition condition) {
        this.gcDiffProcessService.transferGroupWithinToOutside(condition);
        return BusinessResponseEntity.ok((Object)"\u8f6c\u5165\u96c6\u56e2\u5916\u6210\u529f");
    }
}

