package com.jy.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jy.model.User;

public class AppBaseFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        // 如果session不为空，则可以浏览其他页面
        String url = request.getRequestURI();
       String qs=request.getQueryString();
        //这里判断目录，后缀名，当然也可以写在web.xml中，用url-pattern进行拦截映射
        if ((!url.equals("/scms/"))
				&&!url.equals("/scms/apk/appDown.jsp")
        		&&!url.equals("/scms/login.do")&&!url.equals("/scms/login.do?method=login")&&!url.equals("/scms/showShippingOrder.jsp")
        		&&(!url.equals("/scms/login.jsp"))&&(!url.equals("/scms/weChart.do"))&&(!url.equals("/scms/app.do")&&(!url.equals("/scms/dataHandling.do"))&&(!url.equals("/scms/dmsinterface.do"))&&(!url.equals("/scms/druid/index.html")))) {
        	if (session.getAttribute(SessionInfo.SessionName) == null) {
        		session.invalidate();
                RequestDispatcher requestDp = request
						.getRequestDispatcher("error/sessionOutTime.jsp");
				requestDp.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
        	if(url.equals("/scms/login.do")&&qs==null){
        		if(session.getAttribute(SessionInfo.SessionName) != null){
        			RequestDispatcher requestDp = request
    						.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp");
        			requestDp.forward(request, response);
        		}else{
        			chain.doFilter(request, response);
        		}
        	}else{
        		chain.doFilter(request, response);
            }
        }
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
