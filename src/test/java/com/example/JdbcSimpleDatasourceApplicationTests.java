package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.controller.UserController;
import com.example.pojo.Labels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcSimpleDatasourceApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    UserController userController;

    HashMap<String, Object> information = new HashMap<String, Object>() {{
        put("name", "zhangsan");
        put("telephone", "13675169891");
    }};

    @Test
    public void springDataSourceTest() {
        //输出为true
        try {
            Labels labels = new Labels();
            List<Labels> list = labels.selectAll();
            List<Labels> result = new ArrayList<>();

            dfs(list, result);
            JSONObject jsonObject = new JSONObject();
//            System.out.println(result);
            // 对result的内容进自定义实现。并且有target的内容放入的是child的递归中的。
            for (Labels label : result) {
                jsonObject = (JSONObject) JSON.toJSON(label);
                jsonObject.put("NAME", jsonObject.get("name"));
                jsonObject.remove("name");
                JSONArray json = (JSONArray)JSONObject.toJSON(label.getChildren());
                json = dfsChangeName("name", "NAME", json, 1);
                jsonObject.put("children", json);
            }
            System.out.println(jsonObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private  JSONArray dfsChangeName(String oldName, String newName, JSONArray json, int level) {
        if (level == 4) {
            //处理的是名字，有targetInfo的信息，可以进行一个处理！
            JSONArray result = new JSONArray();
            if (json != null) {
                for (Object o : json) {
                    JSONObject jsonObject = (JSONObject) o;
                    JSONArray targetInfo = jsonObject.getJSONArray("targetInfo");
                    JSONArray phoneJson = new JSONArray();
                    JSONObject item = new JSONObject();
                    for (Object targetName : targetInfo) {
                        item = (JSONObject) targetName;
                        item.put(newName, item.get(oldName));
                        item.remove(oldName);
                        // 处理号码数据
                        String[] telephones = item.getString("telephone").split(",");
                        for (int i = 0; i < telephones.length; i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put(newName, telephones[i]);
                            phoneJson.add(jsonObject1);
                        }
                        item.put("children", phoneJson);// name:zhangsan, children:{name:13675169891}
                    }
                    jsonObject.put("children", item);
                }
                return result;
            } else {
                return json;
            }
        }


        if (level <= 2) {
            //
            if (json != null) {
                for (Object o : json) {
                    JSONObject jsonObject = (JSONObject) o;
                    jsonObject.put(newName, jsonObject.get(oldName));
                    jsonObject.remove(oldName);
                    dfsChangeName(oldName, newName, (JSONArray) jsonObject.get("children"), ++level);
                }
            } else {
                return json;
            }
        } else if (level == 3) { // 需要对children赋值
            if (json != null) {
                for (Object o : json) {
                    JSONObject jsonObject = (JSONObject) o;
                    jsonObject.put(newName, jsonObject.get(oldName));
                    jsonObject.remove(oldName);
                }
                dfsChangeName(oldName, newName, json, ++level);
            } else {
                return json;
            }
        }
        return json;
    }

    // 生成一个targetInfo的内容。
    public void dfs( List<Labels> list, List<Labels> result) {
        for (Labels item : list) {
            //第一层内容，依次的递归下去。
            if (item.getParentId() == null) {
                dfs(list, item, result);
                result.add(item);
            }
        }
    }

    public void dfs(List<Labels> list, Labels item, List<Labels> result) {
        if (item.getTreeLevel().equals("3")) {
            if (item.getName().equals("海洛因")) {
                item.getTargetInfo().add(information);
            }
            return;
        }

        for (Labels labels : list) {
            if (item.getId().equals(labels.getParentId())) {
                item.getChildren().add(labels);
                dfs(list, labels, result);
            }
        }
    }

}

//    @Test
//    public void testUser() {
//        JSONObject liuyifei = userController.getUserInfo("liuyifei");
//        System.out.println(liuyifei);
//    }
//}