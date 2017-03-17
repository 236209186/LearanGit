package park.interceptor;

import park.util.Constants;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invoker) throws Exception {
		Object loginUserName = ActionContext.getContext().getSession().get(Constants.USER_NAME);
		if(null == loginUserName){  
            return Action.LOGIN;    
        }  
        return invoker.invoke();  
	}

}
