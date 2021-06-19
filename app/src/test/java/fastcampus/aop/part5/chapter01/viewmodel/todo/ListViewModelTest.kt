package fastcampus.aop.part5.chapter01.viewmodel.todo

import fastcampus.aop.part5.chapter01.data.entity.ToDoEntity
import fastcampus.aop.part5.chapter01.domain.todo.GetToDoItemUseCase
import fastcampus.aop.part5.chapter01.domain.todo.InsertToDoListUseCase
import fastcampus.aop.part5.chapter01.presentation.list.ListViewModel
import fastcampus.aop.part5.chapter01.presentation.list.ToDoListState
import fastcampus.aop.part5.chapter01.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

/**
 * [ListViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 */

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val list = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(list)
    }

    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()

        viewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(list)
            )
        )

    }

    @Test
    fun `test Item update`(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(1)?.hasCompleted ?: false == todo.hasCompleted)
    }

    @Test
    fun `test Item Delete All`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }

}