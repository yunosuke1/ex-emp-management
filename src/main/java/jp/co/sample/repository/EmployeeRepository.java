package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

	/**
	 * 従業員一覧情報を入社日順（降順）で取得する（従業員が存在しない場合はサイズ0の従業員一覧を返す）。
	 * 
	 * @return　従業員一覧情報のリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}

	/**
	 * 主キーから従業員情報を取得する。
	 * 
	 * @param id 従業員id
	 * @return 従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/**
	 * 従業員情報を変更する。
	 * 
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET name=:name, image=:image, gender=:gender, hire_date=:hireDate, mail_address=:mailAdress, zip_code=:zipCode, address=:adress, telephone=:telephone, salary=:salary, characteristics=:characteristics, dependents_count=:dependentsCount WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}
}
