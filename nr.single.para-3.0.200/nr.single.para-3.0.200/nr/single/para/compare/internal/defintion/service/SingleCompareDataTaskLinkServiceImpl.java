/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.ISingleCompareDataTaskLinkService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataTaskLinkDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataTaskLinkServiceImpl
implements ISingleCompareDataTaskLinkService {
    @Autowired
    private ICompareDataDao<CompareDataTaskLinkDO> compareDataDao;
    private final Function<CompareDataTaskLinkDO, CompareDataTaskLinkDTO> toDto = Convert::cdtlDo;
    private final Function<List<CompareDataTaskLinkDO>, List<CompareDataTaskLinkDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataTaskLinkDTO> list(CompareDataTaskLinkDTO compareDataDTO) {
        ArrayList<CompareDataTaskLinkDTO> list = new ArrayList<CompareDataTaskLinkDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataTaskLinkDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataTaskLinkDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataTaskLinkDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataTaskLinkDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataTaskLinkDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataTaskLinkDTO compareDataDTO) throws SingleCompareException {
        if (compareDataDTO != null) {
            if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
                this.compareDataDao.delete(compareDataDTO.getKey());
            } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
                this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
            }
        }
    }

    @Override
    public void batchAdd(List<CompareDataTaskLinkDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataTaskLinkDTO> list2 = new ArrayList<CompareDataTaskLinkDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataTaskLinkDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataTaskLinkDTO> list2 = new ArrayList<CompareDataTaskLinkDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataTaskLinkDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataTaskLinkDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

