package com.ziqiang.util.thread;

import java.util.concurrent.LinkedBlockingQueue;

import com.ziqiang.util.Constant;
import com.ziqiang.util.HttpTookit;

/**
 * @desc	连接提交到百度收录线程
 * @author	ljt
 * @time	2015年8月30日下午12:50:57
 */
public class LinkCommitThread implements Runnable{
	
	private static LinkedBlockingQueue<String> htmlQueue = new LinkedBlockingQueue<String>();
	
	public void run() {
		while(true){
			try {
				String articleId = htmlQueue.take();
				String result = HttpTookit.post("http://data.zz.baidu.com/urls?site=coriger.com&token=VKpzlNyCL5hyLOiz",
						Constant.HOST_URL+"/article/html/"+articleId);
				System.out.println("推送响应："+result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void addLink(String articleId) {
		htmlQueue.offer(articleId);
	}
	
}
