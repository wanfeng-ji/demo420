package cn.smbms.controller;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController{
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
   //pageIndex 就是在页码选择的页码数 1 23 4...
	@RequestMapping(value="/list.html")
	public String getUserList(Model model,
							  @RequestParam(value="queryname",required=false) String queryUserName,
							  @RequestParam(value="queryUserRole",required=false) String queryUserRole,
							  @RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.info("getUserList ---- > queryUserName: " + queryUserName);
		logger.info("getUserList ---- > queryUserRole: " + queryUserRole);
		logger.info("getUserList ---- > pageIndex: " + pageIndex);
		//页面我们查询的时候选择的是查询用户角色的名字,字符串需要转为integer类型
		Integer _queryUserRole = null;		
		List<User> userList = null;
		List<Role> roleList = null;
		//设置页面容量-每页显示的条数默认是5
    	int pageSize = Constants.pageSize;
    	//当前页码
    	int currentPageNo = 1;
	
		if(queryUserName == null){
			queryUserName = "";
		}
		if(queryUserRole != null && !queryUserRole.equals("")){
			_queryUserRole = Integer.parseInt(queryUserRole);
		}
		
    	if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			return "redirect:/syserror.html";
    		}
    	}	
    	//总数量（表）	
    	int totalCount = 0;
		try {
			totalCount = userService.getUserCount(queryUserName,_queryUserRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//总页数
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);
    	int totalPageCount = pages.getTotalPageCount();
    	
    	//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
    	
		try {
			userList = userService.getUserList(queryUserName,_queryUserRole,currentPageNo,pageSize);
			 
			roleList = roleService.getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("userList", userList); 
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", queryUserName);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "userlist";
	}
	
//	@RequestMapping(value="/add.html",method=RequestMethod.GET)
//	public String addUser(){
////		public String addUser(@ModelAttribute("user") User user){
//		return "useradd";
//	}
//	@RequestMapping(value="/addsave.html",method=RequestMethod.POST)
//	public String addUserSave(User user,HttpSession session){
//		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
//		user.setCreationDate(new Date());
//		try {
//			if(userService.add(user)) {
//				return "redirect:/sys/user/list.html";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "useradd";
//	}
//	
//	@RequestMapping(value="/modify/{id}",method=RequestMethod.GET)
//	public String getUserById(@PathVariable String id,Model model/*,HttpServletRequest request*/){
//		System.out.println("--------进来了=================================================================");
//		logger.debug("getProviderById id===================== "+id);
//		User user = new User();
//		try {
//		   user = userService.getUserById(Integer.parseInt(id));
//		   System.out.println(user.getId()+"---------");
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute(user);
//		return "usermodify";
//	}
//	@RequestMapping(value="/modifysave.html",method=RequestMethod.POST)
//	public String modifyUserSave(User user,HttpSession session) {
//		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
//		user.setModifyDate(new Date());
//		try {
//			if(userService.modify(user)){
//				return "redirect:/sys/user/list.html";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "usermodify";
//	}
//	
//	@RequestMapping(value="/view/{id}",method=RequestMethod.GET)
//	public String view(@PathVariable String id,Model model){
//		logger.debug("view id===================== "+id);
//		User user = new User();
//		try {
//			user = userService.getUserById(Integer.parseInt(id));
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		model.addAttribute(user);
//		return "userview";
//	}
////	@RequestMapping(value="/view.json/{id}",method=RequestMethod.GET)
////	@ResponseBody
////	public Object view(@PathVariable String id){
////		logger.debug("view id===================== "+id);
////		String cjson="";
////		if (id==null || "".equals(id)) {
////			return "nodata";
////		}
////		try {
////			User user = userService.getUserById(Integer.parseInt(id));
////			cjson=JSON.toJSONString(user);
////		
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////			return "falied";
////		}
////		return cjson;
////	}
//	@RequestMapping(value="/ucexist.json")
//	@ResponseBody
//	public Object userCodeIsExit(@RequestParam String userCode){
//		logger.debug("userCodeIsExit userCode===================== "+userCode);
//		HashMap<String, String> resultMap = new HashMap<String, String>();
//		//isNullOrEmpty返回值是true/false ,字符串是否为null,是否有值
//		if(StringUtils.isNullOrEmpty(userCode)){
//			resultMap.put("userCode", "exist");
//		}else{
//			User user = null;
//			try {
//				user = userService.selectUserCodeExist(userCode);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(null != user)
//				resultMap.put("userCode", "exist");
//			else
//				resultMap.put("userCode", "noexist");
//		}
//		return JSONArray.toJSONString(resultMap);
//	}
}