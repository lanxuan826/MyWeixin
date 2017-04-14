package com.weiqiang.servelt;


 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
 




import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.weiqiang.utils.CheckUtil;
import com.weiqiang.utils.MessageUtil;
import com.weiqiang.vo.TextMeaasge;
 
/**
 * ΢����Ϣ�Ľ��պ���Ӧ
 */
public class WeixinServlet extends HttpServlet {
 
    /**
     * ����΢�ŷ��������͵�4������������echostr
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // ����΢�ŷ�������Get�����͵�4������
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
         
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // У��ͨ����ԭ������echostr��������
        }
    }
 
    /**
     * ���ղ�����΢�ſͻ��˷��͵�����
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
             
            String message = null;
            if ("text".equals(msgType)) {                // ���ı���Ϣ���д���
                TextMeaasge text = new TextMeaasge();
                text.setFromUserName(toUserName);         // ���ͺͻظ��Ƿ����
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());
                text.setContent("�㷢�͵���Ϣ�ǣ�" + content);
                message = MessageUtil.textMessageToXML(text);
                System.out.println(message);            
            }
            out.print(message);                            // ����Ӧ���͸�΢�ŷ�����
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
 
}