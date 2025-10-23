/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.calibre2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.vo.BatchCalibreDataOptionsVO;
import com.jiuqi.nr.calibre2.vo.CalibreBatchBuildVO;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface ICalibreDataManageService {
    public List<ITree<CalibreDataVO>> search(CalibreDataVO var1) throws JQException;

    public List<CalibreDataVO> easysearch(CalibreDataVO var1) throws JQException;

    public List<CalibreDataVO> searchData(CalibreDataVO var1) throws JQException;

    public Boolean existCalibreData(String var1);

    public List<UpdateResult> copyCalibreData(String var1, String var2) throws JQException;

    public CalibreDataVO insert(CalibreDataVO var1) throws JQException;

    public CalibreDataVO update(CalibreDataVO var1) throws JQException;

    public UpdateResult delete(CalibreDataVO var1) throws JQException;

    public List<UpdateResult> batchMove(BatchCalibreDataOptionsVO var1) throws JQException;

    public List<UpdateResult> batchDelete(BatchCalibreDataOptionsVO var1, Boolean var2) throws JQException;

    public List<ITree<CalibreDataVO>> initTree(CalibreDataVO var1) throws JQException;

    public List<ITree<CalibreDataVO>> loadChildren(CalibreDataVO var1) throws JQException;

    public List<ITree<CalibreDataVO>> locationTree(CalibreDataVO var1) throws JQException;

    public CalibreDataVO getDataPath(CalibreDataVO var1);

    public CalibreDataVO queryCalibre(CalibreDataVO var1);

    public List<UpdateResult> batchAdd(BatchCalibreDataOptionsVO var1) throws JQException;

    public List<CalibreDataVO> listAll(String var1);

    public List<CalibreDataVO> batchBuild(CalibreBatchBuildVO var1);
}

