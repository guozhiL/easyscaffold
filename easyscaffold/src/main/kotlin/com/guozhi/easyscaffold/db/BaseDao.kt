package com.guozhi.easyscaffold.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 *  author: liuguozhi
 *  create: 2022/6/9 22:43
 *  desc  : 定义通用的数据库操作接口
 */
interface BaseDao<T> {

    /**
     * 插入一条数据
     * 冲突策略：替换
     * @return 返回插入的id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    /**
     * 插入一个集合的数据
     * 冲突策略：替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<T>)

    /**
     * 更新一个数据
     * 冲突策略：替换
     * @return 受影响的行数
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T):Int

    /**
     * 删除一个数据
     * @return 受影响的行数
     */
    @Delete
    suspend fun delete(entity: T): Int

}