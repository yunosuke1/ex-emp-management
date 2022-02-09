package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	
	@ModelAttribute
	public UpdateEmployeeForm setUpEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 全従業員のリストを取得してリクエストスコープに格納
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model,@RequestParam(required = false) String page) {
		int dispNum = 10;
		if(page==null) {
			page="1";
		}
	
		int pageNum = Integer.parseInt(page);
		
		//全従業員リスト
		List<Employee> employeeList = service.showList();
		
		//ページング
		int totalPage = (int)employeeList.size()/10 + 1;
		if(pageNum > totalPage) {
			pageNum = 1;
		}
		model.addAttribute("page", pageNum);
		model.addAttribute("totalPage",totalPage);
		System.out.println(pageNum);
		System.out.println(dispNum);
		
		List<Employee> dispEmployeeList = service.showPageList(pageNum,dispNum);
		
		model.addAttribute("employeeList",dispEmployeeList);
		return "employee/list";
	}
	
	/**
	 *idから従業員の情報を取得してリクエストスコープに格納
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(Integer id,Model model) {
		//従業員データ取得
		Employee employee = service.showDetail(id);
		model.addAttribute("employee",employee);
	
		return "employee/detail";
	}
	
	/**
	 * 従業員の情報を更新
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		//idから従業員を検索
		Employee employee = service.showDetail(Integer.parseInt(form.getId()));
		
		//フォームの値を取得したEmployeeオブジェクトにセット
		BeanUtils.copyProperties(form, employee);
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employee.setSalary(Integer.parseInt(form.getSalary()));
		
		service.update(employee);
		return "redirect:/employee/showList";
	}
	
	
}
