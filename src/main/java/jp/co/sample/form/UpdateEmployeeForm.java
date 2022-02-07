package jp.co.sample.form;

/**
 * 更新する従業員情報受け取るフォーム
 * 
 * @author yunosuke
 *
 */
public class UpdateEmployeeForm {
	private String id;
	private String dependentsCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}
}
