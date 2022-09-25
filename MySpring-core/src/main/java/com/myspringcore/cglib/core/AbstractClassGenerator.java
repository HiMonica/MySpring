package com.myspringcore.cglib.core;

import org.springframework.cglib.core.*;
import org.springframework.cglib.core.internal.Function;
import org.springframework.cglib.core.internal.LoadingCache;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author julu
 * @date 2022/9/11 23:17
 */
public abstract class AbstractClassGenerator<T> implements ClassGenerator {

    private static final ThreadLocal CURRENT = new ThreadLocal();

    private static volatile Map<ClassLoader, ClassLoaderData> CACHE = new WeakHashMap<>();

    private static final boolean DEFAULT_USE_CACHE =
            Boolean.parseBoolean(System.getProperty("cglib.useCache", "true"));

    private GeneratorStrategy strategy = DefaultGeneratorStrategy.INSTANCE;

    private NamingPolicy namingPolicy = DefaultNamingPolicy.INSTANCE;

    private Source source;

    private ClassLoader classLoader;

    private Class contextClass;

    private String namePrefix;

    private Object key;

    private boolean attemptLoad;


    protected static class ClassLoaderData {

        private final WeakReference<ClassLoader> classLoader;

        private final Set<String> reservedClassNames = new HashSet<>();

        private final LoadingCache<AbstractClassGenerator, Object, Object> generatedClasses;

        private final Predicate uniqueNamePredicate = new Predicate() {
            @Override
            public boolean evaluate(Object name) {
                return reservedClassNames.contains(name);
            }
        };

        private static final Function<AbstractClassGenerator, Object> GET_KEY = new Function<AbstractClassGenerator, Object>() {
            @Override
            public Object apply(AbstractClassGenerator gen) {
                return gen.key;
            }
        };

        public ClassLoaderData(ClassLoader classLoader) {
            if (classLoader == null) {
                throw new IllegalStateException("classLoader == null is not yet supported");
            }
            this.classLoader = new WeakReference<>(classLoader);
            Function<AbstractClassGenerator, Object> load =
                    new Function<AbstractClassGenerator, Object>() {
                        public Object apply(AbstractClassGenerator gen) {
                            Class klass = gen.generate(ClassLoaderData.this);
                            return gen.wrapCachedClass(klass);
                        }
                    };
            generatedClasses = new LoadingCache<AbstractClassGenerator, Object, Object>(GET_KEY, load);
        }

        public ClassLoader getClassLoader() {
            return classLoader.get();
        }

        public void reserveName(String name){
            reservedClassNames.add(name);
        }

        public Predicate getUniqueNamePredicate(){
            return uniqueNamePredicate;
        }

        public Object get(AbstractClassGenerator gen, boolean useCache){
            if (!useCache){
                return gen.generate(ClassLoaderData.this);
            }
            else {
                Object cachedValue = generatedClasses.get(gen);
                return gen.unwrapCachedValue(cachedValue);
            }
        }
    }

    protected Class generate(ClassLoaderData data) {
        return null;
    }

    protected T wrapCachedClass(Class klass) {
        return (T) new WeakReference<>(klass);
    }

    protected static class Source{
        String name;

        public Source(String name){
            this.name = name;
        }
    }

    protected Object unwrapCachedValue(T cached){
        return ((WeakReference) cached).get();
    }
}

