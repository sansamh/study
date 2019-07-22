package io.sansam.controller;

import io.sansam.service.async.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * TaskController
 * </p>
 *
 * @author houcb
 * @since 2019-05-28 10:39
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private AsyncTaskService asyncTaskService;

    public String doTask() {
        return "done!";
    }
}
