package org.apache.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * @author julu
 * 我的理解：运用适配器，在JVM加载的时候就会将引入的日志包加载进去，这样就可以通过
 * Class.forName在虚拟机中找到自己需要哪种日志，最终将需要该日志的类加载进日志文件中
 * @date 2022/9/4 15:50
 */
final class LogAdapter {

    private static final String LOG4J_SPI = "org.apache.logging.log4j.spi.ExtendedLogger";

    private static final String LOG4J_SLF4J_PROVIDER = "org.apache.logging.slf4j.SLF4JProvider";

    private static final String SLF4J_SPI = "org.slf4j.spi.LocationAwareLogger";

    private static final String SLF4J_API = "org.slf4j.Logger";

    private static final LogApi logApi;

    static {
        if (isPresent(SLF4J_SPI)){
            if (isPresent(LOG4J_SLF4J_PROVIDER) && isPresent(SLF4J_SPI)){
                logApi = LogApi.SLF4J_LAL;
            }
            else {
                logApi = LogApi.LOG4J;
            }
        }
        else if (isPresent(SLF4J_SPI)){
            logApi = LogApi.SLF4J_LAL;
        }
        else if (isPresent(SLF4J_API)){
            logApi = LogApi.SLF4J;
        }
        else {
            logApi = LogApi.JUL;
        }
    }

    private LogAdapter() {}

    public static Log createLog(String name){
        switch (logApi){
            case LOG4J:
                return Log4jAdapter.createLog(name);
            case SLF4J_LAL:
                return Slf4jAdapter.createLocationAwareLog(name);
            case SLF4J:
                return Slf4jAdapter.createLog(name);
            default:
                return JavaUtilAdapter.createLog(name);
        }
    }

    /**
     * 作用：根据类加载器中加载的日志类来选择要使用的日志
     */
    private static boolean isPresent(String className) {
        try {
            //每个类（类型）都有自己对应的Class对象。运行程序时，JVM首先检查是否所要加载的类对应的Class对象是否已经加载。
            // 如果没有加载，JVM就会根据类名查找.class文件，并将其Class对象载入
            Class.forName(className, false, LogAdapter.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


    private enum LogApi{
        LOG4J,
        SLF4J_LAL,
        SLF4J,
        JUL
    }

    private static class Log4jAdapter{

        public static Log createLog(String name){
            return new Log4jLog(name);
        }
    }

    private static class Log4jLog implements Log, Serializable{

        private static final String FQCN = Log4jLog.class.getName();

        private static final LoggerContext loggerContext =
                LogManager.getContext(Log4jLog.class.getClassLoader(), false);

        private final ExtendedLogger logger;

        public Log4jLog(String name){
            this.logger = loggerContext.getLogger(name);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isEnabled(org.apache.logging.log4j.Level.DEBUG);
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isEnabled(org.apache.logging.log4j.Level.TRACE);
        }

        @Override
        public void trace(Object message) {

        }

        @Override
        public void debug(Object message) {

        }
    }

    private static class Slf4jAdapter{

        public static Log createLocationAwareLog(String name){
            Logger logger = LoggerFactory.getLogger(name);
            return (logger instanceof LocationAwareLogger ?
                    new Slf4jLocationAwareLog((LocationAwareLogger) logger) : new Slf4jLog<>(logger));
        }

        public static Log createLog(String name){
            return new Slf4jLog<>(LoggerFactory.getLogger(name));
        }
    }

    private static class JavaUtilAdapter{

        public static Log createLog(String name){
            return new JavaUtilLog(name);
        }
    }

    private static class Slf4jLocationAwareLog extends Slf4jLog<LocationAwareLogger> implements Serializable{

        private static final String FQCN = Slf4jLocationAwareLog.class.getName();

        public Slf4jLocationAwareLog(LocationAwareLogger logger) {
            super(logger);
        }
    }

    private static class Slf4jLog<T extends Logger> implements Log, Serializable{

        protected final String name;

        protected transient T logger;

        public Slf4jLog(T logger){
            this.name = logger.getName();
            this.logger = logger;
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isDebugEnabled();
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isTraceEnabled();
        }

        @Override
        public void trace(Object message) {

        }

        @Override
        public void debug(Object message) {

        }
    }

    private static class JavaUtilLog implements Log, Serializable{

        private String name;

        private transient java.util.logging.Logger logger;

        public JavaUtilLog(String name){
            this.name = name;
            this.logger = java.util.logging.Logger.getLogger(name);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isLoggable(Level.FINE);
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isLoggable(Level.FINEST);
        }

        @Override
        public void trace(Object message) {

        }

        @Override
        public void debug(Object message) {

        }
    }
}
