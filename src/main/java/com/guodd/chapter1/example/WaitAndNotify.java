package com.guodd.chapter1.example;

/**
 * Created by guo on 2018/5/8.
 */
public class WaitAndNotify {

	public static void main(String[] args) {

		User user = new User();

		Thread a = new Thread(){
			@Override
			public void run() {
				System.out.println("Thread a come in ...");
				user.say();
				System.out.println("Thread a end ...");
			}
		};


		Thread b = new Thread(){
			@Override
			public void run() {
				System.out.println("Thread b come in ...");
				user.doSomething();
				System.out.println("Thread b end ...");
			}
		};

		a.start();
		b.start();
	}

}