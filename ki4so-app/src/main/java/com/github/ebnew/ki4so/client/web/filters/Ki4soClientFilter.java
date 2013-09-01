package com.github.ebnew.ki4so.client.web.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.github.ebnew.ki4so.client.key.DefaultKeyServiceImpl;
import com.github.ebnew.ki4so.client.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * ki4so�ͻ���Ӧ�õĹ��������Ӷ�ʵ�ּ���ki4so�����¼ϵͳ��
 * �˹��������밲װ�����Լ�ʵ�֡�
 * @author Administrator
 */
public class Ki4soClientFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(Ki4soClientFilter.class.getName());
	
	/**
	 * �ڿͻ��˵�session�е��û���Ϣ������Ƶ����֤��������ܡ�
	 */
	public static final String USER_STATE_IN_SESSION_KEY = "ki4so_client_user_info_session_key";
	
	/**
	 * ki4so������������ַ��
	 */
	private String ki4soServerHost = "http://localhost:8080/ki4so-web/";
	
	/**
	 * ki4so��������¼URL��ַ��
	 */
	private String ki4soServerLoginUrl = ki4soServerHost+"login.do";
	
	/**
	 * ki4so��������ȡӦ����Կ��Ϣ��URL��ַ��
	 */
	private String ki4soServerFetchKeyUrl = ki4soServerHost+"fetchKey.do";
	
	/**
	 * ��Ӧ����ki4so�������ϵ�Ӧ��IDֵ��
	 */
	private String ki4soClientAppId = "1001";
	
	/**
	 * ��Ӧ�ö�Ӧ�ļ���key.
	 */
	private Ki4soKey ki4soKey;
	
	/**
	 * ��Կ��ȡ����
	 */
	private KeyService keyService = null;
	
	/**
	 * ��Ӧ���Ƿ��¼����
	 */
	private boolean localAppLogined = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ki4soServerHost = getInitParameterWithDefalutValue(filterConfig, "ki4soServerHost", ki4soServerHost);
		ki4soClientAppId = getInitParameterWithDefalutValue(filterConfig, "ki4soClientAppId", ki4soClientAppId);
		ki4soServerLoginUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerLoginUrl", ki4soServerLoginUrl);
		ki4soServerFetchKeyUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerFetchKeyUrl", ki4soServerFetchKeyUrl);
		keyService = new DefaultKeyServiceImpl(ki4soServerFetchKeyUrl);
		logger.info("the ki4so sever is :"+this.ki4soServerHost+", please check this service is ok.");
		try{
			ki4soKey = keyService.findKeyByAppId(ki4soClientAppId);
		}catch (Exception e) {
			logger.log(Level.SEVERE, "fetch ki4so key info error", e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//���Ѿ���¼������ֱ�ӷ��أ�����������������
		if(localAppLogined){
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpSession session = servletRequest.getSession();
		//����Ӧ��δ��¼��
		if(session.getAttribute(USER_STATE_IN_SESSION_KEY)==null){
			//���û��key�����ȡһ�Ρ�
			if(ki4soKey==null){
				try{
					ki4soKey = keyService.findKeyByAppId(ki4soClientAppId);
					
					
				}catch (Exception e) {
					logger.log(Level.SEVERE, "fetch ki4so key info error", e);
				}
			}
		}
		

	}

	@Override
	public void destroy() {
		this.ki4soKey = null;
	}
	
	/**
	 * ��ȡ����������ֵ������Ĭ��ֵ����û�����ã���ʹ��Ĭ��ֵ��
	 * @param filterConfig
	 * @param paramName
	 * @param defalutValue
	 * @return
	 */
	private String getInitParameterWithDefalutValue(FilterConfig filterConfig, String paramName, String defalutValue){
		String value = filterConfig.getInitParameter(paramName);
		if(StringUtils.isEmpty(value)){
			value = defalutValue;
		}
		return value;
	}

}
