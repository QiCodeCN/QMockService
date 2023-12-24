package cn.daqi.mock.gateway;
import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.entity.QMockApiRuleEntity;
import cn.daqi.mock.gateway.service.QMockService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
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
        List<QMockApiEntity> mockApiEntities = qMockService.selectApiByPath(requestURI, requestMethod);

        if(mockApiEntities.size() == 1) {
            // 取得 Mock api 唯一值
            QMockApiEntity mockApiEntity = mockApiEntities.get(0);

            // 根据 api id 查询规则列表
            List<QMockApiRuleEntity> mockApiRuleEntities = qMockService.selectApiRuleList(mockApiEntity.getId());
            log.info("Mock规则个数:" + mockApiRuleEntities.size());

            // 根据不同的方法做不同的处理，目前只支持常用的GET和POST
            if ("GET".equals(requestMethod.toUpperCase())){
                try {

                    if (request.getParameterMap().size()!=0) {
                        reqParamsOrBody = QMockRuleUtil.getJsonObjcetByQueryUrl(URLDecoder.decode(request.getQueryString(),"utf-8"));
                    }
                    resResult = QMockRuleUtil.matchFilter(mockApiEntity,mockApiRuleEntities,reqParamsOrBody);
                }
                catch (JSONException e){
                    log.error(e.toString());
                }
            } else if ("POST".equals(requestMethod.toUpperCase())){
                try {
                    String strbody = QMockRuleUtil.getBodyString(request);
                    if (!strbody.isEmpty()) {
                        reqParamsOrBody = JSON.parseObject(strbody);
                    }

                    resResult = QMockRuleUtil.matchFilter(mockApiEntity,mockApiRuleEntities,reqParamsOrBody);
                }
                    catch (JSONException e){
                    log.error(e.toString());
                }
            } else {
                resResult.put("code", 4008);
                resResult.put("msg", "Mock暂未支持的请求方法");
            }

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

        JSONObject newResResult = QMockRuleUtil.doBody(resResult, reqParamsOrBody);

        // 封装返回数据
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        PrintWriter printWriter = response.getWriter();

        printWriter.write(JSONObject.toJSONString(newResResult));
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
