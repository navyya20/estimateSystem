package jp.co.interline.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jp.co.interline.dto.UserInformDTO;



//ログインインターセプター
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	//コントローラーのメソッドの実行前に処理
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
		
		logger.debug("LoginInterceptor 実行");
		
		
		//セッションのログイン情報を取得
		//セッションのログイン情報を取得
		HttpSession session = request.getSession();
		UserInformDTO user=(UserInformDTO)session.getAttribute("userInform");
		//ログインをしなった場合、ログインページに移動
		if(user==null ) {
			System.out.println("session정보가 없습니다.");
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		logger.debug("LoginInterceptor:{} ",user.getUserId());
		
		System.out.println(request.getRequestURL());
		
		
		/*
		 * if (user.getAuth()==1 && request.getRequestURI().contains("/admin/")) {
		 * response.sendRedirect(request.getContextPath()+"/staff/dashboard"); }else if
		 * (user.getAuth()==0 && request.getRequestURI().contains("/staff/")) {
		 * response.sendRedirect(request.getContextPath()+"/admin/dashboard"); }else if
		 * (request.getRequestURI().contains("/both/")) { return
		 * super.preHandle(request, response, handler); }
		 */
		 
		
		
		//ログインをした場合、リクエストした経路に実行
		return super.preHandle(request, response, handler);
		
	}
}
