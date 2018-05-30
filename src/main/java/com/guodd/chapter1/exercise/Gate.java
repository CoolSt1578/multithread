package com.guodd.chapter1.exercise;

/**
 * 习题1-7
 * 创建一个Mutex类 完成互斥
 * Created by guo on 2018/5/20.
 */
public class Gate {

	private int counter = 0;
	private String name = "Nobody";
	private String address = "Nowhere";
	private final Mutex mutex = new Mutex();

	public void pass(String name, String address){
		mutex.lock();
		try{
			this.counter++;
			this.name = name;
			this.address = address;
			check();
		}finally {
			mutex.unlock();
		}

	}

	public String toString(){
		String s = null;
		mutex.lock();
		try {
			s = this.name + ", " + this.address;
		}finally {
			mutex.unlock();
		}
		return s;
	}

	private void check() {
		if(name.charAt(0) != address.charAt(0)){
			System.out.println("*******BROKEN*********** { name = " + name + ", address=" + address + "}");
		}
	}
}

class Mutex{

	/**
	 * 锁的状态
	 * true 可进入
	 * false 不可进入
	 */
	private long count;
	/**
	 * 当前锁的占有线程
	 */
	private Thread currentThread;

	public synchronized void lock(){
		//count大于0 或者 当前线程不是占有线程
		while(count > 0 || currentThread != Thread.currentThread()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//成功获取， ++
		count++;
	}

	public synchronized void unlock(){
		if(count == 0){
			return;
		}
		if(currentThread != Thread.currentThread()){
			return;
		}
		assert count > 0 && currentThread == Thread.currentThread();
		count --;
		if(count == 0){
			currentThread = null;
			notifyAll();
		}
	}
}