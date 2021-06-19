package fastcampus.aop.part5.chapter01.di

import android.content.Context
import androidx.room.Room
import fastcampus.aop.part5.chapter01.data.local.db.ToDoDatabase
import fastcampus.aop.part5.chapter01.data.repository.DefaultToDoRepository
import fastcampus.aop.part5.chapter01.data.repository.ToDoRepository
import fastcampus.aop.part5.chapter01.domain.todo.*
import fastcampus.aop.part5.chapter01.domain.todo.DeleteToDoItemUseCase
import fastcampus.aop.part5.chapter01.domain.todo.GetToDoItemUseCase
import fastcampus.aop.part5.chapter01.domain.todo.GetToDoListUseCase
import fastcampus.aop.part5.chapter01.domain.todo.InsertToDoListUseCase
import fastcampus.aop.part5.chapter01.domain.todo.InsertToDoUseCase
import fastcampus.aop.part5.chapter01.presentation.detail.DetailMode
import fastcampus.aop.part5.chapter01.presentation.detail.DetailViewModel
import fastcampus.aop.part5.chapter01.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }

    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()