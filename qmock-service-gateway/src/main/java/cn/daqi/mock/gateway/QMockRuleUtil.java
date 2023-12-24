package cn.daqi.mock.gateway;

import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.entity.QMockApiRuleEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/12/02
 * @ Des : 规则匹配处理工具类
 */
public class QMockRuleUtil {

    // 获取请求路径中参数，返回String
    public static String getJsonStrByQueryUrl(String paramStr){
        JSONObject strObject = getJsonObjcetByQueryUrl(paramStr);
        return strObject.toJSONString();
    }

    // 获取获取请求路径中参数，返回JSON
    public static JSONObject getJsonObjcetByQueryUrl(String paramStr){
        //String paramStr = "a=a1&b=b1&c=c1";
        String[] params = paramStr.split("&");
        JSONObject obj = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    // 获取POST方法的中的Body数据
    public static String getBodyString(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    // Mock Rule 匹配逻辑
    public static JSONObject matchFilter(QMockApiEntity mockApiEntity, List<QMockApiRuleEntity> mockApiRuleEntities, JSONObject reqParamsOrBody){

        JSONObject resResult = new JSONObject();

        for(QMockApiRuleEntity ruleEntity:mockApiRuleEntities){
            if((ruleEntity.getReqFilter().isEmpty() || ruleEntity.getReqFilter() == null ) && reqParamsOrBody == null) {
                resResult = ruleEntity.getResBody();
                break;
            }

            if(!ruleEntity.getReqFilter().isEmpty() && reqParamsOrBody != null) {

                if (ruleEntity.getReqFilter().size() > reqParamsOrBody.size()){
                    continue;
                }

                JSONArray filters = ruleEntity.getReqFilter();
                Boolean assertResult = false;
                for(int i=0; i< filters.size(); i++) {
                    String key = filters.getJSONObject(i).getString("key");
                    Boolean jsParamsHasKey = reqParamsOrBody.containsKey(key);
                    Object jj = null;
                    if(jsParamsHasKey) {
                        jj = reqParamsOrBody.get(key);
                    }
                    Object value = filters.getJSONObject(i).get("value");

                    Boolean ab = reqParamsOrBody.get(key).equals(filters.getJSONObject(i).get("value"));

                    if(reqParamsOrBody.containsKey(key) && reqParamsOrBody.get(key).toString().equals(filters.getJSONObject(i).get("value"))) {
                        assertResult = true;
                    } else {
                        assertResult = false;
                    }
                }

                if( assertResult ) {
                    resResult = ruleEntity.getResBody();
                    break;
                }
            }
        }
        // 全没有返回默认
        if (resResult.isEmpty()) {
            resResult = mockApiEntity.getResDefault();
        }

        return resResult;
    }

    // Response Body返回动态语法处理
    // 目前仅处理到一级结构
    public static JSONObject doBody(JSONObject jsonBody, JSONObject reqParamsOrBody){
        JSONObject jsbody = jsonBody;

        for (String key:jsbody.keySet()) {

            // 增则判断是否有需要进行动态处理的
            String regex = "@[a-zA-z]*\\(.*\\)";
            if (jsbody.get(key) instanceof String && ((String) jsbody.get(key)).matches(regex)) {
                String rgxMethod = getMatcher(regex,jsbody.get(key).toString());
                String rgxName = rgxMethod.substring(0,rgxMethod.indexOf("("));
                String rgxParam = rgxMethod.substring(rgxMethod.indexOf("(")+1,rgxMethod.indexOf(")"));

                switch(rgxName){
                    case "getInput" :
                        jsbody.put(key, getInput(rgxParam,reqParamsOrBody));
                        break;
                    case "getTimeStampMS" :
                        jsbody.put(key, getTimeStampMS());
                        break;
                    default : //可选
                        jsbody.put(key,"未匹配任何动态方法");
                }
            }
        }

        return jsbody;
    }

    public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(0);
        }
        return result.substring(1);
    }

    // 获取输入参数
    public static Object getInput(String key, JSONObject input){
        return input.get(key);
    }

    // 获取毫秒时间戳
    public static Object getTimeStampMS() {
        return System.currentTimeMillis();
    }
}
