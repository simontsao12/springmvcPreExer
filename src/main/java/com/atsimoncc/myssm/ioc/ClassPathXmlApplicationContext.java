package com.atsimoncc.myssm.ioc;

import com.atsimoncc.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ClassPathXmlApplicationContext
 * Package: com.atsimoncc.myssm.io
 * Description:
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    private Map<String, Object> beanMap = new HashMap<>();

    // path 的預設值
    private String path = "applicationContext.xml";

    public ClassPathXmlApplicationContext() {
        this("applicationContext.xml");
    }
    public ClassPathXmlApplicationContext(String path) {
        if (StringUtil.isEmpty(path)) {
            throw new RuntimeException("IOC容器配置文件沒有指定...");
        }
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            // 1. 創建 DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 2. 創建 DocumentBuilder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // 3. Document(org.w3c.dom)
            Document document = documentBuilder.parse(inputStream);
            // 4. 獲取所有的 bean 標籤
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode; // import org.w3c.dom
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class beanClass = Class.forName(className);
                    // 創建 bean 實例
                    Object beanObj = beanClass.getDeclaredConstructor().newInstance();
                    // 將 bean 實例保存到 map 中
                    beanMap.put(beanId, beanObj);
                    // 到目前為止此處需要注意的是 bean 和 bean 之間的依賴關係還沒有設置
                }
            }
            // 5. 組裝 bean 之間的依賴關係
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode; // import org.w3c.dom
                    String beanId = beanElement.getAttribute("id");
                    NodeList beanChildNodes = beanElement.getChildNodes();
                    for (int j = 0; j < beanChildNodes.getLength(); j++) {
                        Node beanChildNode = beanChildNodes.item(j);
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())) { // 抓元素節點
                            Element propertyElement = (Element) beanChildNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            // 從 beanMap 中找到 propertyRef 的實例
                            Object refObj = beanMap.get(propertyRef);
                            // 將 refObj 設置到當前 bean 對應的實例的 property 屬性上
                            Object beanObj = beanMap.get(beanId);
                            Class beanClazz = beanObj.getClass();
                            Field propertyField = beanClazz.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj, refObj);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
