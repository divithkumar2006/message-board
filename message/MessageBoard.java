package message;

public class MessageBoard {
	private String message;
	 private boolean hasMessage = false;
	 public void put(String msg) {
	 synchronized (this) {
	 while (hasMessage) {
	 try {
	 wait();
	 } catch (InterruptedException e) {
		 Thread.currentThread().interrupt();
	 }
	 }
	 message = msg;
	 hasMessage = true;
	 System.out.println("Produced: " + msg);
	 notify();
	 }
	 }
	 public String get() {
	 synchronized (this) {
	 while (!hasMessage) {
	 try {
	 wait();
	 } catch (InterruptedException e) {
	 Thread.currentThread().interrupt();
	 }
	 }
	 String msg = message;
	 hasMessage = false;
	 notify();
	 return msg;
	 }
	 }
	}
	class Producer extends Thread {
	 private MessageBoard board;
	 public Producer(MessageBoard b) {
		 this.board = b;
		 }
		 @Override
		 public void run() {
		 String msgs[] = {
		 "Exam on Monday",
		 "Holiday on Tuesday",
		 "Workshop on Wednesday"
		 };
		 for (String msg : msgs) {
		 board.put(msg);
		 try {
		 Thread.sleep(1000); // simulate delay
		 } catch (InterruptedException e) {
		 Thread.currentThread().interrupt();
		 }
		 }
		 board.put("DONE"); // signal end of messages
		 }
		}
		class Consumer extends Thread {
		 private MessageBoard board;
		 public Consumer(MessageBoard b) {
		 this.board = b;
		 }
		 @Override
		 public void run() {
		 String msg;
		 while (!(msg = board.get()).equals("DONE")) {
		 System.out.println("Consumed: " + msg);
		 }
		 }
		}
	

