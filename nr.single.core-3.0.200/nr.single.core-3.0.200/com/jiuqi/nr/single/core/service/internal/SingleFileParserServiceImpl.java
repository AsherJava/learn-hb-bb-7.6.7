/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.single.core.service.internal;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SingleFileParserServiceImpl
implements SingleFileParserService {
    private static final Logger logger = LoggerFactory.getLogger(SingleFileParserServiceImpl.class);

    @Override
    public JIOParamParser getParaParaser(String file) throws Exception {
        SingleFileImpl singleFile = new SingleFileImpl();
        JIOParamParser jioParaser = null;
        String destFile1 = file + OrderGenerator.newOrder();
        String destFile = destFile1 + ".zip";
        singleFile.infoLoad(file);
        singleFile.unMakeJio(file, destFile);
        String path = destFile1 + ".TSK" + File.separatorChar;
        ZipUtil.unzipFile(path, destFile, "GBK");
        SinglePathUtil.deleteFile(destFile);
        jioParaser = this.getParaParaserByTaskDir(path, singleFile);
        return jioParaser;
    }

    @Override
    public JIOParamParser getParaParaserByTaskDir(String taskPath, SingleFile singleFile) throws SingleFileException {
        JIOParamParser jioParaser = null;
        singleFile.writeTaskSign(taskPath);
        List<InOutDataType> getInOutData = singleFile.getInOutData();
        ArrayList<InOutDataType> getInOutData1 = new ArrayList<InOutDataType>();
        getInOutData1.addAll(getInOutData);
        jioParaser = new JIOParamParser(taskPath, false);
        jioParaser.setInOutData(getInOutData1);
        try {
            jioParaser.parse();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            new SingleFileException(e.getMessage(), e);
        }
        return jioParaser;
    }

    @Override
    public SingleFile getSingleFile(String file) throws SingleFileException {
        SingleFileImpl singleFile = new SingleFileImpl();
        singleFile.infoLoad(file);
        return singleFile;
    }
}

