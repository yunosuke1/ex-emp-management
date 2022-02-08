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

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = new BeanPropertyRowMapper<>(Administrator.class);
	
	/**
	 * 管理者情報を登録する
	 * 
	 * @param administrator 登録する管理者情報
	 */
	public void Insert(Administrator administrator) {
		String sql = "INSERT INTO administrators(name,mail_address,password) VALUES(:name,:mailAddress,:password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する(1件も存在しない場合はnullを返す)
	 * 
	 * @param mailAddress メールアドレス
	 * @param Password パスワード
	 * @return　管理者情報
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress,String Password) {
		String sql = "SELECT * FROM administrators WHERE mail_address=:mailAddress AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("password", Password).addValue("mailAddress", mailAddress);
		List<Administrator> administratorList = template.query(sql, param,ADMINISTRATOR_ROW_MAPPER);
		if(administratorList.size()==0) {
			return null;
		}
		return administratorList.get(0);
	}
	
	/**
	 * メールアドレスから管理者情報を取得する(1件も存在しない場合はnullを返す)
	 * 
	 * @param mailAddress メールアドレス
	 * @return　管理者情報
	 */
	public Administrator findByMailAddress(String mailAddress) {
		String sql = "SELECT * FROM administrators WHERE mail_address=:mailAddress";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<Administrator> administratorList = template.query(sql, param,ADMINISTRATOR_ROW_MAPPER);
		if(administratorList.size()==0) {
			return null;
		}
		return administratorList.get(0);
	}
}
