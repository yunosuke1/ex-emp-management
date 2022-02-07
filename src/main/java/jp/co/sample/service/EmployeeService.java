package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;
	
	/**
	 * 従業員の情報を全て取得
	 * 
	 * @return 全従業員情報
	 */
	public List<Employee> showList(){
		return repository.findAll();
	}
	
	/**
	 * idから従業員情報を検索
	 * 
	 * @param id
	 * @return 検索した従業員情報
	 */
	public Employee showDetail(Integer id) {
		return repository.load(id);
	}
}
