package fastcampus.aop.part5.chapter01.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.aop.part5.chapter01.data.entity.ToDoEntity
import fastcampus.aop.part5.chapter01.data.local.db.dao.ToDoDao

@Database(
    entities = [ToDoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ToDoDataBase.db"
    }

    abstract fun toDoDao(): ToDoDao

}