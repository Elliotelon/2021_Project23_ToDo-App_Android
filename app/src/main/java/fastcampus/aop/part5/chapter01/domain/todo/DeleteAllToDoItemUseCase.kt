package fastcampus.aop.part5.chapter01.domain.todo

import fastcampus.aop.part5.chapter01.data.repository.ToDoRepository
import fastcampus.aop.part5.chapter01.domain.UseCase


internal class DeleteAllToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke() {
        return toDoRepository.deleteAll()
    }

}