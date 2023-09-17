package cn.daqi.mock.gateway;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;


@Slf4j
public class QMockInterceptor implements HandlerInterceptor{

    // 在Controller执行之前调用，如果返回false，controller不执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("====== 执行Mock服务: START ======");

        // 获取基本请求信息
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.info("拦截URI为".concat(requestURI).concat(",方法为").concat(requestMethod));

        String query = URLDecoder.decode(request.getQueryString());
        Map<String, String[]> param = request.getParameterMap();

        JSONObject resBody = new JSONObject();
        resBody.put("uri", requestURI);
        resBody.put("method", requestMethod);

        JSONObject resResult = new JSONObject();
        resResult.put("code", 200);
        resResult.put("data", resBody);

        // 封装返回数据
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();

        printWriter.write(JSONObject.toJSONString(resResult));
        printWriter.flush();
        printWriter.close();

        log.info("====== 执行Mock服务: END ======");

        // 只做匹配数据返回，所以不需要执行任何controller
        return false;
    }

    // controller执行之后，且页面渲染之前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {}

    // 页面渲染之后调用，一般用于资源清理操作
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {}

}
