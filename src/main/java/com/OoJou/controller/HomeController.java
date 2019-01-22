package com.OoJou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ddoa")
public class HomeController {
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
}
