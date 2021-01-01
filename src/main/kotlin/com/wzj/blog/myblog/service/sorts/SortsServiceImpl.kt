package com.wzj.blog.myblog.service.sorts

import com.wzj.blog.myblog.entity.SortsEntity
import com.wzj.blog.myblog.mapper.ArtitleSortMapper
import com.wzj.blog.myblog.mapper.SortsMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class SortsServiceImpl :SortsService{
    @Autowired
    lateinit var sortMapper: SortsMapper
    override fun addSorts(sortName: String?, sortAlias: String?, sortDescription: String?, parentSortid: Int): Int =sortMapper.addSorts(sortName, sortAlias, sortDescription, parentSortid)

    override fun upSorts(sortName: String?, sortAlias: String?, sortDescription: String?, parentSortid: Int, sortid: Int): Int =sortMapper.upSorts(sortName, sortAlias, sortDescription, parentSortid, sortid)

    override fun queryById(sortid: Int): SortsEntity =sortMapper.queryById(sortid)

    override fun queryforList(): MutableList<SortsEntity> =sortMapper.queryforList()

    override fun deleteById(sortid: Int): Int =sortMapper.deleteById(sortid)

}