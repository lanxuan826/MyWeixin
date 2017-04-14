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
 * 实现消息的格式转换(Map类型和XML的互转)
 */
public class MessageUtil {
 
    /**
     * 将XML转换成Map集合
     */
    public static Map<String, String>xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
         
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();            // 使用dom4j解析xml
        InputStream ins = request.getInputStream(); // 从request中获取输入流
        Document doc = reader.read(ins);
         
        Element root = doc.getRootElement();         // 获取根元素
        List<Element> list = root.elements();        // 获取所有节点
         
        for (Element e : list) {
            map.put(e.getName(), e.getText()); 
            System.out.println(e.getName() + "--->" + e.getText());
        }
        ins.close();
        return map;
    }
     
    /**
     * 将文本消息对象转换成XML
     */
    public static String textMessageToXML(TextMeaasge textMessage){
         
 //       XStream xstream = new XStream();              // 使用XStream将实体类的实例转换成xml格式    
        xstream.alias("xml", textMessage.getClass()); // 将xml的默认根节点替换成“xml”
        return xstream.toXML(textMessage);
         
    }
    
    
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
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
     * 图片消息对象转换成xml
     * 
     * @param imageMessage 图片消息对象
     * @return xml
     *//*
    public static String messageToXML(ImageMessage imageMessage) {
        xstream.alias("xml", ImageMessage.class);
        return xstream.toXML(imageMessage);
    }

    *//**
     * 语音消息对象转换成xml
     * 
     * @param voiceMessage 语音消息对象
     * @return xml
     *//*
    public static String messageToXML(VoiceMessage voiceMessage) {
        xstream.alias("xml", VoiceMessage.class);
        return xstream.toXML(voiceMessage);
    }

    *//**
     * 视频消息对象转换成xml
     * 
     * @param videoMessage 视频消息对象
     * @return xml
     *//*
    public static String messageToXML(VideoMessage videoMessage) {
        xstream.alias("xml", VideoMessage.class);
        return xstream.toXML(videoMessage);
    }
*/
    
    
}