package com.guodd.chapter1.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * Created by guo on 2018/5/20.
 */
public class SemaphoreMain {

	public static void main(String[] args) {
		BounderResource resource = new BounderResource(3);
		for(int i=0; i<10; i++){
			new UserThread(resource).start();
		}
	}
}

class BounderResource{

	private Logger log = LoggerFactory.getLogger(SemaphoreMain.class);

	private final Semaphore semaphore;
	private final int permits;
	private final static Random random = new Random(31415926);

	// 构造函数
	public BounderResource(int permits){
		this.permits = permits;
		this.semaphore = new Semaphore(permits);
	}

	public void use() throws InterruptedException {
		try {
			semaphore.acquire();
			doUse();
		} finally {
			semaphore.release();
		}
	}

	private void doUse() throws InterruptedException {
		System.out.println("Begin: user = " + (permits - semaphore.availablePermits()));
		Thread.sleep(random.nextInt(1000));
		System.out.println("End: user = " + (permits - semaphore.availablePermits()));
	}

}

class UserThread extends Thread{

	private static final Random random = new Random(26535);
	private BounderResource bounderResource;

	public UserThread(BounderResource resource){
		this.bounderResource = resource;
	}

	@Override
	public void run() {
		try {
			while (true){
				bounderResource.use();
				Thread.sleep(random.nextInt(3000));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}