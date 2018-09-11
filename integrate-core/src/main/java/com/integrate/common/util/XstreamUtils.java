package com.integrate.common.util;

import com.google.common.collect.Maps;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public abstract class XstreamUtils {

    public static String objectToXml(Object obj) {
        XStream xstream = new XStream(new XppDriver(new NoNameCoder()) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点的转换都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    @SuppressWarnings("rawtypes")
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }

                    @Override
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

        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

    public static <T> T convertFromXml(String xmlStr, Class<T> cls) {
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        T obj = (T)xstream.fromXML(xmlStr);
        return obj;
    }

    public static Map<String, Object> doXMLParse(String strxml) {
        if(StringUtils.isBlank(strxml)) {
            return null;
        }

        Map<String, Object> map = Maps.newHashMap();
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(strxml.getBytes());
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List<Element> children = root.getChildren();
            Iterator<Element> it = children.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                String k = e.getName();
                String v = "";
                List<Element> subChildren = e.getChildren();
                if(subChildren.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(subChildren);
                }

                map.put(k, v);
            }
        } catch (Exception e) {
            throw new BusinessException(SysMsgEnumType.XML_PARSE_ERROR);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new BusinessException(SysMsgEnumType.XML_PARSE_ERROR);
            }
        }

        return map;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List<Element> children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator<Element> it = children.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }
}
