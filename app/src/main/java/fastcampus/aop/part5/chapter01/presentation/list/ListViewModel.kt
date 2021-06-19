package fastcampus.aop.part5.chapter01.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fastcampus.aop.part5.chapter01.data.entity.ToDoEntity
import fastcampus.aop.part5.chapter01.domain.todo.DeleteAllToDoItemUseCase
import fastcampus.aop.part5.chapter01.domain.todo.GetToDoListUseCase
import fastcampus.aop.part5.chapter01.domain.todo.UpdateToDoUseCase
import fastcampus.aop.part5.chapter01.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 필요한 UseCase
 * 1. [GetToDoListUseCase]
 * 2. [UpdateToDoUseCase]
 * 3. [DeleteAllToDoItemUseCase]
 */
internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase
): BaseViewModel() {

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Suceess(getToDoListUseCase()))
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
        updateToDoUseCase(toDoEntity)
    }

    fun deleteAll() = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        deleteAllToDoItemUseCase()
        _toDoListLiveData.postValue(ToDoListState.Suceess(getToDoListUseCase()))
    }

}