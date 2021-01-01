package com.wzj.blog.myblog.service.lanels

import com.wzj.blog.myblog.entity.LanelsEntity
import com.wzj.blog.myblog.mapper.LanelsMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class LanelsServiceImpl :LanelsService {
    @Autowired
    lateinit var lanelsMapper:LanelsMapper
    override fun addLanels(labelName: String?, labelAlias: String?, labelDescription: String?): Int =lanelsMapper.addLanels(labelName, labelAlias, labelDescription)

    override fun upLanels(labelId: Int, labelName: String?, labelAlias: String?, labelDescription: String?): Int =lanelsMapper.upLanels(labelId, labelName, labelAlias, labelDescription)

    override fun queryById(labelId: Int): LanelsEntity=lanelsMapper.queryById(labelId)

    override fun queryByLanels(): MutableList<LanelsEntity> =lanelsMapper.queryByLanels()

    override fun deleteById(labelId: Int): Int =lanelsMapper.deleteById(labelId)


}