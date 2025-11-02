package com.cloud.todo.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cloud.todo.entity.Tasks;
import com.cloud.todo.repository.TasksRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class MainController
{
    private TasksRepository repo;

    public MainController(TasksRepository repo)
    {
        this.repo = repo;
    }

    @GetMapping("/")
    public String homePage(Model model)
    {
        List<Tasks> tasks = repo.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("title", "Home");
        return "home";
    }
    
    @GetMapping("/add")
    public String addTaskPage(Model model)
    {
        model.addAttribute("task", new Tasks());
        model.addAttribute("title", "Add");
        return "addTask";
    }

    @PostMapping("/add")
    public String postMethodName(Tasks task)
    {
        task.setCreatedAt(LocalDateTime.now());
        repo.save(task);
        return "redirect:/";
    }
    

    @GetMapping("/edit")
    public String getMethodName(@RequestParam Long id, Model model)
    {
        Tasks task = repo.findById(id).orElse(null);
        model.addAttribute("task", task);
        model.addAttribute("title", "Edit");
        return "addTask";
    }

    @PostMapping("/edit")
    public String updateTask(Tasks task)
    {
        if (task.getCreatedAt() == null)
        {
            task.setCreatedAt(LocalDateTime.now());
        }
        repo.save(task);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id)
    {
        repo.deleteById(id);
        return "redirect:/";
    }
}