package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository


/**
 * 图片表操作接口
 */
@Mapper
@Repository
interface ImageMapper{

   //{str,jdbcType=VARCHAR}

   @Insert("insert into ${SqlUtil.Image_Lob_Table}(IMAGE_TYPE,IMAGE_DATA,IMAGE_NAME,IMAGE_SUFFIX,USER_ID,IMAGE_ADDRESS,IMAGE_PATH) values(#{image_type},#{image_data,jdbcType=VARCHAR},#{image_name,jdbcType=VARCHAR},#{image_suffix,jdbcType=VARCHAR},#{user_id},#{image_address,jdbcType=VARCHAR},#{image_path,jdbcType=VARCHAR})")
   fun insertImage(image: ImageEntity):Int

   @Update("update ${SqlUtil.Image_Lob_Table} set USER_ID=#{user_id},IMAGE_DATA=#{image_data,jdbcType=VARCHAR},IMAGE_TYPE=#{image_type},IMAGE_NAME=#{image_name,jdbcType=VARCHAR},IMAGE_SUFFIX=#{image_suffix,jdbcType=VARCHAR},IMAGE_ADDRESS=#{image_address,jdbcType=VARCHAR},IMAGE_PATH=#{image_path,jdbcType=VARCHAR} where IMAGE_ID=#{image_id}")
   fun updateImage(image: ImageEntity):Int

   @Select("select IMAGE_ID,IMAGE_TYPE,IMAGE_DATA,IMAGE_NAME,IMAGE_SUFFIX,USER_ID,IMAGE_ADDRESS,IMAGE_PATH from  ${SqlUtil.Image_Lob_Table} where IMAGE_ID=#{imageid} ")
   fun queryById(imageid: Int):ImageEntity

   @Select("select IMAGE_ID,IMAGE_TYPE,IMAGE_DATA,IMAGE_NAME,IMAGE_SUFFIX,USER_ID,IMAGE_ADDRESS,IMAGE_PATH from ${SqlUtil.Image_Lob_Table} where USER_ID=#{userid} ")
   fun queryByUserId(userid: Int):MutableList<ImageEntity>

   @Select("select * from ${SqlUtil.Image_Lob_Table}")
   fun queryforList():MutableList<ImageEntity>

   @Delete("delete  ${SqlUtil.Image_Lob_Table} where IMAGE_ID=#{imageid}")
   fun deleteById(imageid: Int):Int

   @Delete("delete  ${SqlUtil.Image_Lob_Table} where USER_ID=#{userid}")
   fun deleteByUserId(userid: Int):Int


}

