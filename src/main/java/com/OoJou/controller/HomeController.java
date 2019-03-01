package com.OoJou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ddoa")
public class HomeController {
    @RequestMapping("/test")
    public ModelAndView test() {
    	ModelAndView mv = new ModelAndView("test");
    	return mv;
    }
	
    @RequestMapping("/login")
    public ModelAndView login() {
    	ModelAndView mv = new ModelAndView("login");
    	return mv;
    }
    
    @RequestMapping("/register")
    public ModelAndView register() {
    	ModelAndView mv = new ModelAndView("register");
    	return mv;
	}
    
    @RequestMapping("/forget")
    public ModelAndView forget() {
    	ModelAndView mv = new ModelAndView("forget");
    	return mv;
    }
    
    @RequestMapping("/forget_answer")
    public ModelAndView forget_answer() {
    	ModelAndView mv = new ModelAndView("forget_answer");
    	return mv;
    }
    
    @RequestMapping("/forget_first")
    public ModelAndView forget_first() {
    	ModelAndView mv = new ModelAndView("forget_first");
    	return mv;
    }
    
    @RequestMapping("/forget_reset")
    public ModelAndView forget_reset() {
    	ModelAndView mv = new ModelAndView("forget_reset");
    	return mv;
    }
    
    @RequestMapping("/index")
    public ModelAndView index() {
    	ModelAndView mv = new ModelAndView("index");
    	return mv;
    }
    
    @RequestMapping("/views/file")
    public ModelAndView views_file() {
    	ModelAndView mv = new ModelAndView("views/file");
    	return mv;
    }
    
    @RequestMapping("/views/form")
    public ModelAndView views_form() {
    	ModelAndView mv = new ModelAndView("views/form");
    	return mv;
    }
    
    @RequestMapping("/views/manage_department")
    public ModelAndView views_manage_department() {
    	ModelAndView mv = new ModelAndView("views/manage_department");
    	return mv;
    }
    
    @RequestMapping("/views/manage_dictionary")
    public ModelAndView views_manage_dictionary() {
    	ModelAndView mv = new ModelAndView("views/manage_dictionary");
    	return mv;
    }
    
    @RequestMapping("/views/manage_file")
    public ModelAndView views_manage_file() {
    	ModelAndView mv = new ModelAndView("views/manage_file");
    	return mv;
    }
    
    @RequestMapping("/views/manage_notice")
    public ModelAndView views_manage_notice() {
    	ModelAndView mv = new ModelAndView("views/manage_notice");
    	return mv;
    }
    
    @RequestMapping("/views/manage_task")
    public ModelAndView views_manage_task() {
    	ModelAndView mv = new ModelAndView("views/manage_task");
    	return mv;
    }
    
    @RequestMapping("/views/manage_user")
    public ModelAndView views_manage_user() {
    	ModelAndView mv = new ModelAndView("views/manage_user");
    	return mv;
    }
    
    @RequestMapping("/views/notice")
    public ModelAndView views_notice() {
    	ModelAndView mv = new ModelAndView("views/notice");
    	return mv;
    }
    
    @RequestMapping("/views/show")
    public ModelAndView views_show() {
    	ModelAndView mv = new ModelAndView("views/show");
    	return mv;
    }
    
    @RequestMapping("/views/task_completed")
    public ModelAndView views_task_completed() {
    	ModelAndView mv = new ModelAndView("views/task_completed");
    	return mv;
    }
    
    @RequestMapping("/views/task_deal")
    public ModelAndView views_task_deal() {
    	ModelAndView mv = new ModelAndView("views/task_deal");
    	return mv;
    }
    
    @RequestMapping("/views/task_request")
    public ModelAndView views_task_request() {
    	ModelAndView mv = new ModelAndView("views/task_request");
    	return mv;
    }
    
    
    @RequestMapping("/views/user_info")
    public ModelAndView views_user_info() {
    	ModelAndView mv = new ModelAndView("views/user_info");
    	return mv;
    }
    
    @RequestMapping("/views/user_pass")
    public ModelAndView views_user_pass() {
    	ModelAndView mv = new ModelAndView("views/user_pass");
    	return mv;
    }
    
    /**
     * 详情页
     */
    @RequestMapping("/views/task_deal_details")
    public ModelAndView views_task_deal_details() {
    	ModelAndView mv = new ModelAndView("views/task_deal_details");
    	return mv;
    }
    @RequestMapping("/views/task_complete_details")
    public ModelAndView views_task_complete_details() {
    	ModelAndView mv = new ModelAndView("views/task_complete_details");
    	return mv;
    }
    
}
