package br.com.herco.todoappmvp.temp_to_remove;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.validators.TaskValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskValidatorTest {

    @Test(expected = TaskException.class)
    public void shouldThrowsTaskExceptionToValidateANullTaskBeforeCreate() throws TaskException {
        TaskModel taskModel = null;
        TaskValidator.validateTaskToCreate(taskModel);
    }

    @Test(expected = TaskException.class)
    public void shouldThrowsTaskExceptionToValidateANullTaskNameBeforeCreate() throws TaskException {
        TaskModel taskModel = new TaskModel(null);
        TaskValidator.validateTaskToCreate(taskModel);
    }

    @Test()
    public void shouldValidateToANotNullTaskBeforeCreate() throws TaskException {
        final String taskName = "Learn unit test with Java";
        TaskModel taskModel = new TaskModel(taskName);
        TaskValidator.validateTaskToCreate(taskModel);

        assertNotNull(taskModel);
    }

    @Test()
    public void shouldValidateANotNullTaskNameBeforeCreate() throws TaskException {
        final String taskName = "Learn unit test with Java";
        TaskModel taskModel = new TaskModel(taskName);
        TaskValidator.validateTaskToCreate(taskModel);

        assertEquals(taskModel.getName(), taskName);
    }


    // = UPDATE =
    @Test(expected = TaskException.class)
    public void shouldThrowsTaskExceptionToValidateANullTaskBeforeUpdate() throws TaskException {
        TaskModel taskModel = null;
        TaskValidator.validateTaskToUpdate(taskModel);
    }

    @Test(expected = TaskException.class)
    public void shouldThrowsTaskExceptionToValidateANullTaskNameBeforeUpdate() throws TaskException {
        TaskModel taskModel = new TaskModel(null);
        TaskValidator.validateTaskToUpdate(taskModel);
    }

    @Test(expected = TaskException.class)
    public void shouldThrowsTaskExceptionToValidateANullTaskIdBeforeUpdate() throws TaskException {
        final String taskName = "Learn unit test with Java";
        TaskModel taskModel = new TaskModel(taskName);

        TaskValidator.validateTaskToUpdate(taskModel);
    }

    @Test()
    public void shouldValidateToANotNullTaskBeforeUpdate() throws TaskException {
        final String taskName = "Learn unit test with Java";
        TaskModel taskModel = new TaskModel(taskName);

        taskModel.setId("d9d9c267-1e56-4b3e-853b-e023a77966ab");
        TaskValidator.validateTaskToUpdate(taskModel);

        assertNotNull(taskModel);
    }
}
