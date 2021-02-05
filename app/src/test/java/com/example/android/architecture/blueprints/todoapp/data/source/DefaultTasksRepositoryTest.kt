package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

private val task1 = Task("Task1", "Description1")
private val task2 = Task("Task2", "Description2")
private val task3 = Task("Task3", "Description3")
private val remoteTasks = listOf(task1, task2, task3).sortedBy { it.id }
private val localTasks = listOf(task3).sortedBy { it.id }
private val newTasks = listOf(task3).sortedBy { it.id }

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var taskLocalDataSource: FakeDataSource

    private lateinit var taskRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        taskLocalDataSource = FakeDataSource(localTasks.toMutableList())

        taskRepository = DefaultTasksRepository(tasksRemoteDataSource, taskLocalDataSource, Dispatchers.Unconfined)
    }

    @Test
    fun getTasks_requestAllTasksFromRemoteDataSource() = runBlockingTest {
        val tasks = taskRepository.getTasks(true) as Result.Success

        assertThat(tasks.data, IsEqual(remoteTasks))
    }

}