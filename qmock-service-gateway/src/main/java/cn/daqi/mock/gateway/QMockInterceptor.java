package cn.daqi.mock.gateway;
import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.service.QMockService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


@Slf4j
public class QMockInterceptor implements HandlerInterceptor{

    @Autowired
    QMockService qMockService;

    // 在Controller执行之前调用，如果返回false，controller不执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("====== 执行Mock服务: START ======");

        // 获取基本请求信息
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.info("拦截URI为".concat(requestURI).concat(",方法为").concat(requestMethod));

//        String query = URLDecoder.decode(request.getQueryString());
//        Map<String, String[]> param = request.getParameterMap();

        JSONObject resResult = new JSONObject();
        Integer resCode = 200;
        JSONObject reqParamsOrBody = null;

        // 匹配查询URI的数据，当等于1的时候进行规则查询，否则其他错误请求处理
        List<QMockApiEntity> mockApiEntities = qMockService.findApiByPath(requestURI, requestMethod);

        if(mockApiEntities.size() == 1) {
            // 唯一匹配返回默认配置值
            resResult = mockApiEntities.get(0).getResDefault();
            resCode = mockApiEntities.get(0).getResCode();

        } else if (mockApiEntities.size() > 1) {
            resResult.put("code", 5000);
            resResult.put("data", new JSONObject());
            resResult.put("msg", "MOCK匹配多个URI请检查配置");
        }
        else {
            resResult.put("code", 5000);
            resResult.put("data", new JSONObject());
            resResult.put("msg", "MOCK未匹配任何URI请先添加把");
        }


//        JSONObject resBody = new JSONObject();
//        resBody.put("uri", requestURI);
//        resBody.put("method", requestMethod);


        // 封装返回数据
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
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
