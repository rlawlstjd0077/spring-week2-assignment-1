package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * {@code TaskService} 클래스는 {@code Task}를 관리합니다.
 *
 * @see Task
 */
@Service
public class TaskService {
    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private final IdGenerable taskIdGenerator;

    private TaskService(IdGenerable taskIdGenerator) {
        this.taskIdGenerator = taskIdGenerator;
    }

    /**
     * 모든 task를 반환합니다.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * 주어진 id와 일치하는 task를 찾아 반환합니다.
     *
     * @param   id
     *          찾고자 하는 task id
     *
     * @return  id와 일치하는 task
     *
     * @throws  TaskNotFoundException
     *          존재하지 않는 id가 주어진 경우
     */
    public Task getTask(long id) {
        return findTaskById(id).orElseThrow(() -> new TaskNotFoundException("존재하지 않는 id가 주어져 task를 찾을 수 없습니다. id", id));
    }

    /**
     * 새로운 task를 생성합니다.
     *
     * @param   title
     *          새로운 task의 title
     *
     * @return  생성된 새로운 task
     */
    public Task createNewTask(String title) {
        Task task = new Task(taskIdGenerator.generateNewId(), title);
        tasks.put(task.getId(), task);
        System.out.println("Completed to create task - " + task.toString());
        return task;
    }

    /**
     * 주어진 id와 일치하는 task를 수정합니다.
     *
     * @param   id
     *          수정하고자 하는 task의 id
     *
     * @param   newTitle
     *          수정하고자 하는 title
     * @return  수정된 새로운 task
     *
     * @throws  TaskNotFoundException
     *          존재하지 않는 id가 주어진 경우
     */
    public Task updateTask(long id, String newTitle) {
        Task task =  findTaskById(id).orElseThrow(() -> new TaskNotFoundException("존재하지 않는 id가 주어져 task를 수정할 수 없습니다. id", id));
        task.setTitle(newTitle);
        System.out.println("Completed to update task - " + task.toString());
        return task;
    }

    /**
     * 주어진 id와 일치하는 task를 삭제합니다.
     *
     * @param   id
     *          삭제하고자 하는 task의 id
     *
     * @throws  TaskNotFoundException
     *          존재하지 않는 id가 주어진 경우
     */
    public void deleteTask(long id) {
        Task task =  findTaskById(id).orElseThrow(() -> new TaskNotFoundException("존재하지 않는 id가 주어져 task를 삭할 수 없습니다. id", id));
        tasks.remove(task.getId());
    }

    /**
     * 주어진 id와 일치하는 task를 반환합니다.
     *
     * @param   id
     *          찾고자 하는 task의 id
     *
     * @return  주어진 id와 일치하는 task 혹은 null
     */
    private Optional<Task> findTaskById(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    /**
     * 모든 task를 삭제하고 초기화합니다.
     */
    public void clearTasks() {
        tasks.clear();
        taskIdGenerator.resetId();
    }
}
