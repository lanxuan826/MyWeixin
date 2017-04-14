package com.weiqiang.utils;

 
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 







import javax.servlet.http.HttpServletRequest;
 







import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weiqiang.vo.TextMeaasge;

 
 
/**
 * ʵ����Ϣ�ĸ�ʽת��(Map���ͺ�XML�Ļ�ת)
 */
public class MessageUtil {
 
    /**
     * ��XMLת����Map����
     */
    public static Map<String, String>xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
         
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();            // ʹ��dom4j����xml
        InputStream ins = request.getInputStream(); // ��request�л�ȡ������
        Document doc = reader.read(ins);
         
        Element root = doc.getRootElement();         // ��ȡ��Ԫ��
        List<Element> list = root.elements();        // ��ȡ���нڵ�
         
        for (Element e : list) {
            map.put(e.getName(), e.getText()); 
            System.out.println(e.getName() + "--->" + e.getText());
        }
        ins.close();
        return map;
    }
     
    /**
     * ���ı���Ϣ����ת����XML
     */
    public static String textMessageToXML(TextMeaasge textMessage){
         
 //       XStream xstream = new XStream();              // ʹ��XStream��ʵ�����ʵ��ת����xml��ʽ    
        xstream.alias("xml", textMessage.getClass()); // ��xml��Ĭ�ϸ��ڵ��滻�ɡ�xml��
        return xstream.toXML(textMessage);
         
    }
    
    
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
                boolean cdata = true;

                //@SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
     
/*    *//**
     * ͼƬ��Ϣ����ת����xml
     * 
     * @param imageMessage ͼƬ��Ϣ����
     * @return xml
     *//*
    public static String messageToXML(ImageMessage imageMessage) {
        xstream.alias("xml", ImageMessage.class);
        return xstream.toXML(imageMessage);
    }

    *//**
     * ������Ϣ����ת����xml
     * 
     * @param voiceMessage ������Ϣ����
     * @return xml
     *//*
    public static String messageToXML(VoiceMessage voiceMessage) {
        xstream.alias("xml", VoiceMessage.class);
        return xstream.toXML(voiceMessage);
    }

    *//**
     * ��Ƶ��Ϣ����ת����xml
     * 
     * @param videoMessage ��Ƶ��Ϣ����
     * @return xml
     *//*
    public static String messageToXML(VideoMessage videoMessage) {
        xstream.alias("xml", VideoMessage.class);
        return xstream.toXML(videoMessage);
    }
*/
    
    
}