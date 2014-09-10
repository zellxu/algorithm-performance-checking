import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformanceTimer {
	static PrintWriter log;
	
	public static void main(String[] args){
		PerformanceTimer pt = new PerformanceTimer();
		try {
			 log = new PrintWriter("log.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		pt.listTime();
		pt.arrayTime();
		log.close();
	}
	
	public PerformanceTimer(){}
	
	public void listTime(){
		Integer[] array;
		LinkedList<Integer> list;
		long startTime, endTime;
		for(int n=5; n<25; n++){
			array = new Integer[(int) Math.pow(2, n)];
			Arrays.fill(array, 0);
			list = new LinkedList<Integer>(array);
			log.print("n="+n);
			startTime = System.nanoTime();
			list.traverse();
			endTime = System.nanoTime();
			log.println("\ttime="+ (endTime-startTime));
			log.flush();
		}
	}

	public void arrayTime(){
		Integer[] array;
		for(int n=5; n<25; n++){
			array = new Integer[(int) Math.pow(2, n)];
			log.println("n="+n);
			log.print("Sequential Order:");
			fillArray(1,array);
			traverseArray(array);
			log.print("Stride-2 Order:");
			fillArray(2,array);
			traverseArray(array);
			log.print("Stride-4 Order:");
			fillArray(4,array);
			traverseArray(array);
			log.print("Random Order:");
			fillArray(-1,array);
			traverseArray(array);
			log.flush();
		}
	}
	
	private void traverseArray(Integer[] array) {
		long startTime, endTime;
		int i = 0;
		startTime = System.nanoTime();
		while(i!=-1)
			i = array[i];
		endTime = System.nanoTime();
		log.println("\ttime="+(endTime-startTime));
	}

	private void fillArray(int n, Integer[] array){
		if(n==-1){
			ArrayList<Integer> choices = new ArrayList<Integer>();
			for(int i=1; i<array.length-1; i++)
				choices.add(i);
			int i = 0;
			while(choices.size()>0){
				int rand = (int) (Math.random()*choices.size());
				array[i]=choices.remove(rand);
				i=array[i];
			}
			array[i]=array.length-1;
			array[array.length-1]=-1;
			return;
		}
		for(int i=0; i<array.length; i++)
			array[i]=i+n;
		for(int i=0; i<n-1; i++)
			array[array.length-n+i]=i+1;
		array[array.length-1]=-1;
	}
	
	/**
	 * Simplified version of LinkedList
	 * @author xiangxu
	 *
	 * @param <T> data
	 */
	private class LinkedList<T>{
		Node head, tail, current;
		T data;
		
		public LinkedList(T[] array) {
			for(T t : array)
				this.add(t);
			current = head;
		}

		public void add(T t){
			if (head==null){
				head = new Node(t);
				tail = head;
			}
			else{
				tail.next = new Node(t);
				tail=tail.next;
			}
		}
		
		public void traverse() {
			while(current!=null){
				data = current.t;
				current = current.next;
			}
		}
		
		private class Node{
			T t;
			Node next;
			
			public Node(T t){
				this.t=t;
				next = null;
			}
		}
	}
	
}
