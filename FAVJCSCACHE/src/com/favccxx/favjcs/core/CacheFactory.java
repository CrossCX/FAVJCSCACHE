package com.favccxx.favjcs.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;

import com.favccxx.favjcs.service.CacheBoyService;

public class CacheFactory {
	
	private static Logger logger = Logger.getLogger(CacheFactory.class);
	
	private static Map<String, CacheWrapper> hashMapWrapper = new HashMap<String, CacheWrapper>();
	
	/**
	 * ��ȡһ������ΪcacheName�Ļ��������������ڣ�����null
	 * @param cacheName
	 * @return
	 */
	public static CacheWrapper getCacheWrapper(String cacheName){
		logger.debug("Get CacheWrapper, The cacheName is : " + cacheName);
		return hashMapWrapper.get(cacheName);
	}
	
	/**
	 * �������еĻ���
	 */
	public static void clearCache(){
		Object[] cacheArray = hashMapWrapper.keySet().toArray();
		for(int i=0, l=cacheArray.length; i<l; i++){
			try {
				String cacheName = cacheArray[i].toString();
				logger.debug("The cache is below to clear, And the name is : " + cacheName);
				CacheWrapper cacheWrapper = hashMapWrapper.get(cacheName);
				cacheWrapper.jcsCache.clear();
			} catch (CacheException e) {
				logger.debug("Clear Cache Error!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡһ������ΪcacheName�Ļ��������������ڣ��򴴽�һ���µĻ������
	 * @param cacheName
	 * @return
	 */
	private static CacheWrapper createCacheWrapper(String cacheName){
		JCS cache = null;
		try{
			cache = JCS.getInstance(cacheName);
			return new CacheWrapper(cache);
		}catch(CacheException e){
			return null;
		}
	}
	
	/**
	 * �����������
	 * @param cacheName
	 */
	private static void createHashMapWrapper(String cacheName){
		hashMapWrapper.put(cacheName, createCacheWrapper(cacheName));
	}
	
	/**
	 * ��ʼ���������
	 */
	public static void initCache(){
		logger.debug("By Start initCache Method, We create all the Cache Object");
		createHashMapWrapper("coolBoyCache");
		CacheBoyService.putCacheBoy();
		createHashMapWrapper("beautifulGirl");
	}

}
