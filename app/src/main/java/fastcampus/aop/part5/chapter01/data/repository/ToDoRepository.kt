package fastcampus.aop.part5.chapter01.data.repository

import fastcampus.aop.part5.chapter01.data.entity.ToDoEntity

/**
 * 1. insertToDoList
 * 2. getToDoList
 */

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun getToDoItem(id: Long): ToDoEntity?

    suspend fun insertToDoItem(toDoEntity: ToDoEntity): Long

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoEntity: ToDoEntity)

    suspend fun deleteToDoItem(id: Long)

    suspend fun deleteAll()

}