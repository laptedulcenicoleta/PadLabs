package classes;

public class Informer {

	public static void main(String[] args) throws InterruptedException {
		new InformerThread(0, "conf0.xml").start();
		Thread.sleep(100);
		new InformerThread(1, "conf1.xml").start();
		Thread.sleep(100);
		new InformerThread(2, "conf2.xml").start();
		Thread.sleep(100);
		new InformerThread(3, "conf3.xml").start();
		Thread.sleep(100);
		new InformerThread(4, "conf4.xml").start();
		Thread.sleep(100);
		new InformerThread(5, "conf5.xml").start();
	}

}
