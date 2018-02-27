package com.jy.action;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Company;
import com.jy.model.Confluence;
import com.jy.model.Detaileds;
import com.jy.model.Income;
import com.jy.model.Mingxi;
import com.jy.model.ShippingOrder;
import com.jy.service.CompanyInfoService;
import com.jy.service.ConfluenceService;

@Controller
@RequestMapping(value = "/conf.do")
public class ConfluenceController {
	@Resource
	private  ConfluenceService cs; 

	@RequestMapping(params = "method=get")
	public String getCompanyQuery() {
		return "conf/confuence";
	}
	// 全部查询
		@RequestMapping(params = "method=getConfluence")
		public @ResponseBody
		Map getCompany(String rows, String page, 
				String direction, String fee_date,String dd  
				 
				) {
			Integer rows1 = 0;// 当前有几行
			Integer page1 = 1;// 当前有几页
			if (rows != null && !"".equals(rows)) {
				rows1 = Integer.parseInt(rows);
			}
			if (page != null && !"".equals(page)) {
				page1 = Integer.parseInt(page);
			}

			List<Confluence> list = cs.getConfluence((page1 - 1) * rows1, rows1,
					direction, fee_date,dd);
			
			int count = cs.getConfluenceInfo(direction, fee_date,dd);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", count);
			map.put("rows", list);
			return map;
		}
/**
 * 
 * 明细表
 */
		@RequestMapping(params = "method=gets")
		public String getDetaileds() {
			return "conf/mingxi";
		}
		// 全部查询
			@RequestMapping(params = "method=getMingxi")
			public @ResponseBody
			Map getDetailedsInfo(String rows, String page, 
					String subject
					) {
				Integer rows1 = 0;// 当前有几行
				Integer page1 = 1;// 当前有几页
				if (rows != null && !"".equals(rows)) {
					rows1 = Integer.parseInt(rows);
				}
				if (page != null && !"".equals(page)) {
					page1 = Integer.parseInt(page);
				}
				
				List<Mingxi> list = cs.getMingxi((page1 - 1) * rows1, rows1,
						subject);
				
				int count = cs.getMingxiInfo(subject );
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("total", count);
				map.put("rows", list);
				return map;
			}
}