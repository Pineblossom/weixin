package com.souvc.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souvc.weixin.util.SignUtil;
import com.thoughtworks.xstream.XStream;

import entity.BaseMessage;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.VideoMessage;
import entity.VoiceMessage;

/**
 * Servlet implementation class CoreServlet
 */
@WebServlet("/wx")
public class CoreServlet extends HttpServlet {

    private static final long serialVersionUID = 4323197796926899691L;

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println(request);
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        System.out.println("get");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
    	Map<String, String> requestMap = SignUtil.parseRequest(request.getInputStream());
    	// 准备回复的数据包
    	String respXml = CoreServlet.getRespose(requestMap);
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
//    	String respXml = "<xml><ToUserName><![CDATA["+ requestMap.get("FromUserName") +"]]></ToUserName><FromUserName><![CDATA["+ requestMap.get("ToUserName") +"]]></FromUserName><CreateTime>"+ System.currentTimeMillis()/1000 +"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[why?]]></Content></xml>";
    	System.out.println(respXml);
    	PrintWriter out = response.getWriter();
    	out.print(respXml);
    	out.flush();
    	out.close();
    }

	/**
	 * 用于处理所有的时间和消息回复
	 * @param requestMap
	 * @return fan'hui
	 */
	private static String getRespose(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		System.out.println(requestMap);
		String msgType = requestMap.get("MsgType");
		BaseMessage msg = null;
		switch (msgType) {
		case "text": {
			msg = dealTextMessage(requestMap);
			break;
		}
		case "image": {
					
			break;
		}
		case "voice": {
			
			break;
		}
		case "video": {
			
			break;
		}
		case "shortvideo": {
			
			break;
		}
		case "location": {
			
			break;
		}
		case "link": {
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + msgType);
		}
		if (msg != null) {
			return beanToXml(msg);
		}
		return null;
	}

	/**
	 * 把消息对象处理为xml数据包
	 * @param msg
	 * @return
	 */
	private static String beanToXml(BaseMessage msg) {
		// TODO Auto-generated method stub
		XStream stream = new XStream();
		// 设置需要处理@XStreamAlias("xml")注释的类
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		String xml = stream.toXML(msg);
		System.out.println(xml);
		return xml;
	}

	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		TextMessage tm = new TextMessage(requestMap, "处理成功。");
		return tm;
	}

}
