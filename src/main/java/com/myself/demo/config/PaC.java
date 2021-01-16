package com.myself.demo.config;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class PaC {
    public Object pac(String url){
        try {
            Connection connection = Jsoup.connect(url);
            //Document document = connection.get();
            //获取响应
            Connection.Response response = connection.method(Connection.Method.GET).execute();
            URL conUrl = response.url(); //获取请求的url
            int statusCode = response.statusCode();  //获取请求的状态码
            String contentType = response.contentType();  //获取响应的数据类型
            if(statusCode == 200){
                String html = new String(response.bodyAsBytes(),"gbk");
                Document document = response.parse();  //这里的html和document数据是一样的，但是document是经过格式化的
            }

            //设置信息头有两种方式
            //第一种： 设置一个信息头，想要添加多个信息头，需要多次调用次方法
            //Connection conheader = connection.header("","");
            //第二种： 使用Map<String,String>接收
            //Map<String,String> maps = new HashMap<>().put("","").put("","");
            //Connection conheader = connection.headers(maps);
            //Document document = coheader.get();

            //设置User-Agent库和Referer库，可以有效的减少网络爬虫被封的风险
            //方法： 设置一个静态类存放，每次需要使用时实例化调用
//            BuilderTool builderTool = new BuilderTool();
////            builderTool.setHost("www.baidu.com");
////            Map<String,String> header  = new HashMap<>();
////            header.put("host",builderTool.getHost());
////            header.put("User-Angent",builderTool.getUserAgentList().get(new Random().nextInt(builderTool.getUserAngentSize())));
////            header.put("Referer",builderTool.getRefererList().get(new Random().nextInt(builderTool.getRefererSize())));
////            header.put("Accept-Language",builderTool.getAcceptLanguage());
////            header.put("Accept-Encoding",builderTool.getAcceptEncoding());
////            Connection coheader = connection.headers(header);
////            Document document = coheader.get();

            //提交请求参数常见三种方法
            //Connection data(String key ,String value)
            //Connection data(String......keyVals)
            //Connection data(Map<String,String> data)
//            connection.data("1","123").data("2","234");
            Connection.Response response1 = connection
                    .method(Connection.Method.POST)  //设置请求方式
                    .timeout(3*1000)     //请求超时设置
                    .proxy("127.0.0.1",8080)   //设置代理服务器
                    .header("1","123")         //设置请求头
                    .data("参数","参数值")   //请求参数
                    .ignoreContentType(true)  //忽略响应内容的参数类型
                    .execute();   //执行

            //使用代理服务器
            //代理服务器是网络上提供转接功能的服务器
            //普通爬虫在使用固定IP地址请求时，往往要设置随机的休息时间，代理服务器不需要，可以提高效率
            //在Jsoup中提供了两种方式设置代理服务器
//            Connection proxy(Proxy proxy);
//            Connection proxy(String host,int port);
//            Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",8080));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
