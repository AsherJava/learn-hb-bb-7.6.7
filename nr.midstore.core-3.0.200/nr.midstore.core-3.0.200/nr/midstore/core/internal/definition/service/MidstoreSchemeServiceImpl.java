/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.midstore.core.internal.definition.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.midstore.core.definition.dao.IMidstoreDataDao;
import nr.midstore.core.definition.db.Convert;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.definition.MidstoreSchemeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreSchemeServiceImpl
implements IMidstoreSchemeService {
    @Autowired
    private IMidstoreDataDao<MidstoreSchemeDO> midstoreDataDao;
    private final Function<MidstoreSchemeDO, MidstoreSchemeDTO> toDto = Convert::mds2Do;
    private final Function<List<MidstoreSchemeDO>, List<MidstoreSchemeDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public MidstoreSchemeDTO getByKey(String schemeKey) {
        MidstoreSchemeDO obj = this.midstoreDataDao.get(schemeKey);
        return this.toDto.apply(obj);
    }

    @Override
    public MidstoreSchemeDTO getByCode(String schemeCode) {
        List<MidstoreSchemeDO> objs = this.midstoreDataDao.getByCode(schemeCode);
        ArrayList list = new ArrayList();
        list.addAll(this.list2Dto.apply(objs));
        MidstoreSchemeDTO result = null;
        if (list.size() > 0) {
            result = (MidstoreSchemeDTO)list.get(0);
        }
        return result;
    }

    @Override
    public List<MidstoreSchemeDTO> list(MidstoreSchemeDTO midstoreDataDTO) {
        ArrayList<MidstoreSchemeDTO> list = new ArrayList<MidstoreSchemeDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreSchemeDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getCode())) {
            List<MidstoreSchemeDO> objs = this.midstoreDataDao.getByCode(midstoreDataDTO.getCode());
            list.addAll((Collection<MidstoreSchemeDTO>)this.list2Dto.apply(objs));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getGroupKey())) {
            List<MidstoreSchemeDO> objs = this.midstoreDataDao.getByParentKey(midstoreDataDTO.getGroupKey());
            list.addAll((Collection<MidstoreSchemeDTO>)this.list2Dto.apply(objs));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getTablePrefix())) {
            List<MidstoreSchemeDO> objs = this.midstoreDataDao.getByField("MDS_TABLEPREFIX", midstoreDataDTO.getTablePrefix());
            list.addAll((Collection<MidstoreSchemeDTO>)this.list2Dto.apply(objs));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getTaskKey())) {
            List<MidstoreSchemeDO> objs = this.midstoreDataDao.getByField("MDS_TASKKEY", midstoreDataDTO.getTaskKey());
            list.addAll((Collection<MidstoreSchemeDTO>)this.list2Dto.apply(objs));
        } else {
            list.addAll((Collection<MidstoreSchemeDTO>)this.list2Dto.apply(this.midstoreDataDao.getAll()));
        }
        return list;
    }

    @Override
    public void add(MidstoreSchemeDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreSchemeDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreSchemeDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreSchemeDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreSchemeDTO> list2 = new ArrayList<MidstoreSchemeDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreSchemeDTO> list2 = new ArrayList<MidstoreSchemeDTO>();
            for (int i = 0; i < midstoreDataDTOs.size(); ++i) {
                if (list2.size() >= 600) {
                    this.midstoreDataDao.batchInsert(list2);
                    list2.clear();
                }
                list2.add(midstoreDataDTOs.get(i));
            }
            if (list2.size() > 0) {
                this.midstoreDataDao.batchInsert(list2);
            }
        }
    }

    @Override
    public void batchUpdate(List<MidstoreSchemeDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreSchemeDTO> list2 = new ArrayList<MidstoreSchemeDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreSchemeDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreSchemeDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public List<MidstoreSchemeDTO> list(List<String> schemeKeys) {
        ArrayList<MidstoreSchemeDTO> list = new ArrayList<MidstoreSchemeDTO>();
        list.addAll((Collection)this.list2Dto.apply(this.midstoreDataDao.batchGet(schemeKeys)));
        return list;
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

