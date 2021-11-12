package com.modernwave.web.server.framework.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;


public class LogginAspect {
	
	@SuppressWarnings("rawtypes")
	public Object controllerLogging(ProceedingJoinPoint joinPoint)throws Throwable {
		
		Class clazz = joinPoint.getTarget().getClass();
		Logger log = Logger.getLogger(clazz);
		String methodName = joinPoint.getSignature().getName();
		
//		if ((args.length > 0) && (args[0] instanceof HttpServletRequest)) {
////			log.debug("type"+args[0].getClass());
////			req = (HttpServletRequest) args[0];
////			String method = req.getParameter("method");
////			if (method != null) {
////				methodName = method;
////			}
//		}
		StopWatch stopWatch = new StopWatch();
		
		try {
//			
			log.info("¡Þ¡ß¡ß loasdfasdfasdf[Controller] execute[" + methodName	+ "] Start!");
			stopWatch.start();
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			log.info("¡ß¡ß¡ß Logging Aspect[Controller] execute[" + methodName	+ "] Error", e);
			throw e;
		} finally {
			stopWatch.stop();
			long totalTime = stopWatch.getTotalTimeMillis();
			log.info("¡ß¡ß¡ß Logging Aspect[Controller] execute[" + methodName	+ "] End! total Time : " + totalTime);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public Object serviceLogging(ProceedingJoinPoint joinPoint)throws Throwable {
		Class clazz = joinPoint.getTarget().getClass();
		Logger log = Logger.getLogger(clazz);
		String methodName = joinPoint.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			log.info("¡ß¡Þ¡ß Logging Aspect[service] execute[" + methodName	+ "] Start!");
			stopWatch.start();
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			long totalTime = stopWatch.getTotalTimeMillis();
			log.info("¡ß¡ß¡ß Logging Aspect[service] execute[" + methodName	+ "] End! total Time : " + totalTime);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Object daoLogging(ProceedingJoinPoint joinPoint)throws Throwable {
		Class clazz = joinPoint.getTarget().getClass();
		Logger log = Logger.getLogger(clazz);
		String methodName = joinPoint.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			log.info("¡ß¡ß¡Þ Logging Aspect[dao] execute[" + methodName 	+ "] Start!");

			stopWatch.start();
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			long totalTime = stopWatch.getTotalTimeMillis();
			log.info("¡ß¡ß¡ß Logging Aspect[dao] execute[" + methodName	+ "] End! total Time : " + totalTime);
		}
	}
	
}
