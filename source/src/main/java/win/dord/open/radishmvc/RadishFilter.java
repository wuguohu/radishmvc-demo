package win.dord.open.radishmvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RadishFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(RadishFilter.class);
	
	private RadishConfig actionConfig = new RadishConfig();
	@Override
	public void init(FilterConfig config) throws ServletException {
		actionConfig.setActionParam(StringUtils.trimToEmpty(config.getInitParameter("actionParam")));
		actionConfig.setViewBase(StringUtils.trimToEmpty(config.getInitParameter("view")));
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;
		String path = httpReq.getServletPath();
		if(RadishConfig.log){
			logger.info("filter path: " + path);
		}
		if(path.indexOf(".") >= 0){
			chain.doFilter(request, response);
		} else {
			try {
				Dispatcher.getInstance().dispatch(httpReq, httpRes, actionConfig);
			} catch (RadishException e) {
				//通用错误
				e.printStackTrace();
			}
			return;
		}
	}

}
