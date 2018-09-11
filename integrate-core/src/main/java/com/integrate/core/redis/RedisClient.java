package com.integrate.core.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisClient implements InitializingBean ,DisposableBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private JedisCluster jedisCluster;

	@Value("${redis.servers}")
    private String server;

    @Override
    public void afterPropertiesSet() throws Exception {
    	Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    	String[] servers = server.split(";|,");
        for (String str : servers) {
        	if(StringUtils.isBlank(str)) continue;
            String[] ap = str.split(":");
            HostAndPort hp = new HostAndPort(ap[0], Integer.valueOf(ap[1]));
            jedisClusterNodes.add(hp);
        }
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(1000); 
        config.setMaxIdle(300); 
        config.setMinIdle(100); 
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
    	jedisCluster = new JedisCluster(jedisClusterNodes,1000,config);
    }
    
    public RedisClusterInterface getJedis(){
    	RedisClusterInterface service = (RedisClusterInterface)creatProxyInstance();
        return service;
    }
    
    @Override
    public void destroy() throws Exception {
    }
    
    public  Object creatProxyInstance() {
        return Proxy.newProxyInstance(RedisClusterInterface.class.getClassLoader(), new Class[]{RedisClusterInterface.class} , new ProxyFactory(jedisCluster));
    }

    class ProxyFactory  implements InvocationHandler {
        private JedisCluster jedisCluster;

        public ProxyFactory(JedisCluster jedisCluster){
            this.jedisCluster = jedisCluster;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if (method.getName() == "toString"){
                return this.toString();
            }
           
			Object result= null;
			try {
				result = method.invoke(jedisCluster, args);
			} catch (Exception e) {
				throw e ;
			}finally{
			}
            return result;
        }
    }
   
   
}
