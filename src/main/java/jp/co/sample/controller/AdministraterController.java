package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者関連の処理の制御を行うコントローラー
 * 
 * @author yunosuke
 *
 */
@Controller
@RequestMapping("/")
public class AdministraterController {
	@Autowired
	private AdministratorService service;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministrator() {
		return new InsertAdministratorForm();
	}

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面に遷移
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログイン処理
	 * 
	 * @return ログイン画面or従業員情報一覧ページ
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator admin = service.login(form.getMailAddress(), form.getPassword());

		// ログイン失敗
		if (admin == null) {
			model.addAttribute("noAdmin", "メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		}

		// ログイン成功
		session.setAttribute("adminName", admin.getName());
		return "forward:/employee/showList";
	}

	/**
	 * 管理者情報入力画面に遷移
	 * 
	 * @return 管理者情報入力画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * ユーザ登録
	 * 
	 * @param form ユーザ情報
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {

		// 空白エラーチェック
		if (result.hasErrors()) {
			return "administrator/insert";
		}

		// email重複チェック
		if (service.findByMailAdress(form.getMailAddress()) != null) {
			model.addAttribute("Duplicate","このアドレスは既に使われています");
			return "administrator/insert";
		}

		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		service.insert(administrator);

		return "administrator/login";
	}

	/**
	 * ログアウト
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "administrator/login";
	}

}
