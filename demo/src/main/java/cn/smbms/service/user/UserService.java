package cn.smbms.service.user;
import java.util.List;

import cn.smbms.pojo.User;

public interface UserService {
	
	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public User login(String userCode, String userPassword) throws Exception;
	
	/**
	 * 根据条件查询用户列表
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public List<User> getUserList(String queryUserName, Integer queryUserRole, Integer currentPageNo, Integer pageSize) throws Exception;
	/**
	 * 根据条件查询用户表记录数
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public int getUserCount(String queryUserName, Integer queryUserRole) throws Exception;
	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 */
	public boolean add(User user) throws Exception;
	/**
	 * 根据ID查找user
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) throws Exception;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(User user) throws Exception;
	/**
	 * 用户添加的时候 ajax验证用户编码
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User selectUserCodeExist(String userCode) throws Exception;
}
