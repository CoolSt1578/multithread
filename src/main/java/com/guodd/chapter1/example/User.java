package com.guodd.chapter1.example;

/**
 * Created by guo on 2018/5/8.
 */
public class User {

	/**
	 * 获取到user实例到锁，执行say()方法，调用wait()方法，立即释放了持有到锁，
	 * 其他线程才可以进入doSomething()这个同步方法
	 */
	public synchronized void say(){
		System.out.println("say...");
		try {
			this.wait();
			System.out.println("wait, then say sleep start ...");
			Thread.sleep(5000);
			System.out.println("say sleep end ...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这里的notify()方法不会立即释放user这个实例的锁，需要等到5s睡眠，即方法执行完以后才会释放
	 * 然后原先执行say()方法的线程才会被从等待队列中唤醒，执行后续方法
	 */
	public synchronized void doSomething(){
		System.out.println("do...");
		this.notify();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
