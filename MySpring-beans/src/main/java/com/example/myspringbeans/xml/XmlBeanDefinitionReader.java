package com.example.myspringbeans.xml;

import com.example.myspringbeans.support.AbstractBeanDefinitionReader;
import com.example.myspringbeans.support.BeanDefinitionReader;
import com.example.myspringbeans.support.BeanDefinitionRegistry;
import com.myspringcore.core.env.Environment;
import com.myspringcore.core.io.NamedThreadLocal;
import com.myspringcore.core.io.Resource;
import com.myspringcore.core.io.ResourceLoader;
import com.myspringcore.core.io.support.EncodedResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionStoreException;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * xml形式的BeanDefinitionReader
 *
 * @author julu
 * @date 2022/11/20 17:31
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private DocumentLoader documentLoader = new DefaultDocumentLoader();

    private ErrorHandler errorHander;

    private Class<? extends BeanDefinitionDocumentReader> documentReaderClass =
            DefaultBeanDefinitionDocumentReader.class;

    /**
     * 给ThreadLocal赋予一个名称
     */
    private final ThreadLocal<Set<EncodedResource>> resourceCurrentlyBeingLoaded =
            new NamedThreadLocal<>("XML bean definition resources currently being loaded");

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return null;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return null;
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        return loadBeanDefinitions(new EncodedResource(resource));
    }

    /**
     * 最终xml文件加载bean的实现方法
     *
     * @param encodedResource
     * @return
     * @throws BeanDefinitionStoreException
     */
    public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException{
        Assert.notNull(encodedResource, "EncodedResource must not be null");

        Set<EncodedResource> currentResources = this.resourceCurrentlyBeingLoaded.get();
        //给Thread设置一个HashSet
        if (currentResources == null){
            currentResources = new HashSet<>(4);
            this.resourceCurrentlyBeingLoaded.set(currentResources);
        }
        if (!currentResources.add(encodedResource)){
            throw new BeanDefinitionStoreException(
                    "Detected cyclic loading of " + encodedResource + " - check your import definitions!");
        }
        try {
            // 从资源文件中获取输入流
            InputStream inputStream = encodedResource.getInputStream();
            try {
                InputSource inputSource = new InputSource(inputStream);
                if (encodedResource.getEncoding() != null){
                    inputSource.setEncoding(encodedResource.getEncoding());
                }
                //前面都是写入资源的一些识别，最最重要的是下面的加载流程
                return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
            }
            finally {
                inputStream.close();
            }

        } catch (IOException e) {
            throw new BeanDefinitionStoreException(
                    "IOException parsing XML document from " + encodedResource.getResource(), e);
        }
        finally {
            currentResources.remove(encodedResource);
            if (currentResources.isEmpty()){
                this.resourceCurrentlyBeingLoaded.remove();
            }
        }
    }

    /**
     * 将资源加载成bean的实现方法
     *
     * @param inputSource 要从中读取的SAX inputSource
     * @param resource XML文件描述符，相当于就是一个XML的资源
     * @return
     */
    private int doLoadBeanDefinitions(InputSource inputSource, Resource resource)
            throws BeanDefinitionStoreException{
        try {
            // 将资源文件解析成document
            Document doc = doLoadDocument(inputSource, resource);
            // 从doc和资源中解析元素，注册到bean factory
            int count = registerBeanDefinitions(doc, resource);
            // TODO: 2022/11/27 日志

            return count;
        }
        catch (BeanDefinitionStoreException ex){
            throw ex;
        }
        catch (SAXParseException ex){
            throw new XmlBeanDefinitionStoreException(resource.getDescription(),
                    "Line " + ex.getLineNumber() + "in XML document from " + resource + "is invalid", ex);
        }
        catch (SAXException ex){
            throw new XmlBeanDefinitionStoreException(resource.getDescription(),
                    "XML document from " + resource + " is invalid", ex);
        }
        catch (ParserConfigurationException ex){
            throw new BeanDefinitionStoreException(resource.getDescription(),
                    "Parser configuration exception parsing XML from " + resource, ex);
        }
        catch (IOException ex){
            throw new BeanDefinitionStoreException(resource.getDescription(),
                    "IOException parsing XML document from " + resource, ex);
        }
        catch (Throwable ex) {
            throw new BeanDefinitionStoreException(resource.getDescription(),
                    "Unexpected exception parsing XML document from " + resource, ex);
        }
    }

    /**
     * 注册给定DOM文档中包含的bean定义
     * @param doc
     * @param resource
     * @return
     */
    private int registerBeanDefinitions(Document doc, Resource resource) {
        //1、使用DefaultBeanDefinitionDocumentReader 实例化 BeanDefinitionDocumentReader
        BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
        //2、记录统计前beanDefinition的加载个数
        int countBefore = getRegistry().getBeanDefinitionCont();
        //3、加载及注册bean，这里使用注册工厂的是DefaultListableBeanFactory
        documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
        //4、记录本次加载的BeanDefinition个数（新值 - 旧值）
        return getRegistry().getBeanDefinitionCont() - countBefore;
    }

    private XmlReaderContext createReaderContext(Resource resource) {
        return new XmlReaderContext(resource, null, null, this, null);
    }


    private Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
        //这里对xml做了验证和转化
        return this.documentLoader.loadDocument(inputSource, getEntityResolver(), this.errorHander,
                getValidationModeForResource(resource), isNamespaceAware());
    }

    protected BeanDefinitionDocumentReader createBeanDefinitionDocumentReader(){
        return BeanUtils.instantiateClass(this.documentReaderClass);
    }

    private boolean isNamespaceAware() {
        return false;
    }

    private int getValidationModeForResource(Resource resource) {
        return 0;
    }

    private EntityResolver getEntityResolver() {
        int validationModeToUse = getValidationMode();

        return null;
    }

    private int getValidationMode() {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        return loadBeanDefinitions(location, null);
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        Assert.notNull(locations, "Location array must not be null");
        int count = 0;
        for (String location : locations) {
            count += loadBeanDefinitions(location);
        }
        return count;
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    public void setValidating(boolean validating){

    }
}
