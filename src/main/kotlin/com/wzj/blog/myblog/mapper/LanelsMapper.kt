package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.LanelsEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface LanelsMapper {
    @Insert("insert into ${SqlUtil.Lanels_Table}(LABELID,LABELNAME,LABELALIAS,LABELDESCRIPTION) values(${SqlUtil.seq_Lanels},#{labelName},#{labelAlias},#{labelDescription})")
    fun addLanels(labelName:String?,labelAlias:String?,labelDescription:String?):Int

    @Update("update ${SqlUtil.Lanels_Table} set LABELNAME=#{labelName},LABELALIAS=#{labelAlias},LABELDESCRIPTION=#{labelDescription} where LABELID=#{labelId} ")
    fun upLanels(labelId:Int,labelName:String?,labelAlias:String?,labelDescription:String?):Int

    @Select("select LABELID,LABELNAME,LABELALIAS,LABELDESCRIPTION from ${SqlUtil.Lanels_Table} where  LABELID=#{labelId}")
    fun queryById(labelId:Int): LanelsEntity

    @Select("select LABELID,LABELNAME,LABELALIAS,LABELDESCRIPTION from ${SqlUtil.Lanels_Table}")
    fun queryByLanels():MutableList<LanelsEntity>

    @Delete("delete  ${SqlUtil.Lanels_Table} where  LABELID=#{labelId}")
    fun deleteById(labelId:Int):Int
}