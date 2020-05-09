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
     * ȷ����������΢�ŷ�����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println(request);
        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        System.out.println("get");
        // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }

    /**
     * ����΢�ŷ�������������Ϣ
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO ��Ϣ�Ľ��ա�������Ӧ
    	Map<String, String> requestMap = SignUtil.parseRequest(request.getInputStream());
    	// ׼���ظ������ݰ�
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
	 * ���ڴ������е�ʱ�����Ϣ�ظ�
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
	 * ����Ϣ������Ϊxml���ݰ�
	 * @param msg
	 * @return
	 */
	private static String beanToXml(BaseMessage msg) {
		// TODO Auto-generated method stub
		XStream stream = new XStream();
		// ������Ҫ����@XStreamAlias("xml")ע�͵���
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
		TextMessage tm = new TextMessage(requestMap, "����ɹ���");
		return tm;
	}

}
