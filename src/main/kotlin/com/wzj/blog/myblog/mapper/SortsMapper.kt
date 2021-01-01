package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.SortsEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
open interface SortsMapper {

    @Insert("insert into ${SqlUtil.Sorts_Table}(SORTID,SORTNAME,SORTALIAS,SORTDESCRIPTION,PARENTSORTID) values(${SqlUtil.seq_sorts},#{sortName},#{sortAlias},#{sortDescription},#{parentSortid})")
    fun addSorts(sortName:String?,sortAlias:String?,sortDescription:String?,parentSortid:Int):Int

    @Update("update  ${SqlUtil.Sorts_Table} set SORTNAME=#{sortName},SORTALIAS=#{sortAlias},SORTDESCRIPTION=#{sortDescription},PARENTSORTID=#{parentSortid} where SORTID=#{sortid}")
    fun upSorts(sortName:String?,sortAlias:String?,sortDescription:String?,parentSortid:Int,sortid:Int,):Int

    @Select("select SORTID,SORTNAME,SORTALIAS,SORTDESCRIPTION,PARENTSORTID from  ${SqlUtil.Sorts_Table} where SORTID=#{sortid}")
    fun queryById(sortid:Int): SortsEntity

    @Select("select SORTID,SORTNAME,SORTALIAS,SORTDESCRIPTION,PARENTSORTID from  ${SqlUtil.Sorts_Table}")
    fun queryforList():MutableList<SortsEntity>

    @Delete("delete ${SqlUtil.Sorts_Table} where SORTID=#{sortid}")
    fun deleteById(sortid: Int):Int



}