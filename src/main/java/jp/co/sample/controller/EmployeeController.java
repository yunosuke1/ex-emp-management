package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	
	@ModelAttribute
	public UpdateEmployeeForm  setUpEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 全従業員のリストを取得してリクエストスコープに格納
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = service.showList();
		model.addAttribute("employeeList",employeeList);
		return "employee/list";
	}
	
	/**
	 * 全従業員のリストを取得してリクエストスコープに格納
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(Integer id,Model model) {
		Employee employee = service.showDetail(id);
		model.addAttribute("employee",employee);
		return "employee/detail";
	}
	
	/**
	 * 全従業員のリストを取得してリクエストスコープに格納
	 * 
	 * @param model
	 * @return リスト表示画面
	 */
	@RequestMapping("/update")
	public String update(String id,String dependentsCount) {
		System.out.println(id);
		System.out.println(dependentsCount);
		Employee employee = service.showDetail(Integer.parseInt(id));
		employee.setDependentsCount(Integer.parseInt(dependentsCount));
		service.update(employee);
		
		return "redirect:/employee/showList";
	}
	
	
}
