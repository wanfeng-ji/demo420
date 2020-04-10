package cn.smbms.service.user;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public User login(String userCode, String userPassword) throws Exception {
		User user = null;
		//调用dao层中的登录方法
		user = userMapper.getLoginUser(userCode);
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}
		return user;
	}
	
	@Override
	public List<User> getUserList(String queryUserName, Integer queryUserRole,
			Integer currentPageNo, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		//起始下标= (当前页码-1)*每页显示的条数
		currentPageNo = (currentPageNo-1)*pageSize;
		return userMapper.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
	}
	@Override
	public int getUserCount(String queryUserName, Integer queryUserRole)
			throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getUserCount(queryUserName, queryUserRole);
	}
	@Override
	public boolean add(User user) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(userMapper.add(user) > 0)
			flag = true;
		return flag;
	}
	@Override
	public User getUserById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getUserById(id);
	}

	@Override
	public boolean modify(User user) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(userMapper.modify(user) > 0)
			flag = true;
		return flag;
	}

	@Override
	public User selectUserCodeExist(String userCode) throws Exception {
		return userMapper.getLoginUser(userCode);
	}
}
