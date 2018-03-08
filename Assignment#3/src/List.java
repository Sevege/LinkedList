import java.io.*;
import java.util.Scanner;
/**
 * Assignme-Linked List
 * @author yingdong
 */
public class List{
	private Node head;
	public class Node{
		private int number;
		private double price;
		private Node next;
		Node(int n, double p){
			number = n;
			price = p;
			next = null;
		}
	}
	/**
	 * add node to the end of the list
	 * @param number amount of widgets
	 * @param price price of widgets
	 */
	public void add(int number, double price){
		Node node = new Node(number,price);
		if(head==null){
			head = node;
			return;
		}
		Node l1 = head;
			while(l1.next!=null){
			l1 = l1.next;
			}
			l1.next = node;
	}
	/**
	 * remove the node from the front of the list
	 */
	public void remove(){
		head = head.next;
	}
	/**
	 * get the amount of widgets in head node
	 * @return int amount of widgets
	 */
	public int getAmount(){
		return head.number;
	}
	/**
	 * get the price of widegets in head node
	 * @return
	 */
	public double getPrice(){
		return head.price;
	}
	/**
	 * set the amount of widegets in head node after sales
	 * @param amount
	 */
	public void setAmount(int amount){
			head.number = amount;
	}
	/**
	 * print the amount and price of widgets left in stock
	 */
	public void print(){
		Node l1 = head;
		while(l1!=null){
			System.out.printf("%d widgets at %.2f each\n",l1.number,l1.price);
			l1=l1.next;
		}
	}
	/**
	 * return true if the list is not empty
	 * @return true if the list is not empty
	 */
	public boolean isEmpty(){
		return head==null;
	}
	/**
	 *process datas
	 * @param args
	 */
	public static void main(String[]args){
		List list = new List();
		try{
		//instantiate data file reader
		Scanner data = new Scanner(new File("resource/data.txt"));
		//instantiate count for promotion
		int countP=0;
		//instantiate promotion
		int promotion=0;
		//true if file doesn't reach the end
		while(data.hasNext()){
			//record reader
			Scanner record = new Scanner(data.nextLine());
			//first character of record
			String input = record.next();
			if(input.equals("R")){
				//read amount of the widget
				int amount = Integer.parseInt(record.next());
				//price of the widget
				double price = Double.parseDouble(record.next());
				//add node to the list
				list.add(amount, price);
				//print the amount and price of widget
				System.out.printf("%d widgets at %.2f each\n",amount,price);
			}
			else if(input.equals("S")){
				//read amount of sold widgets
				int amount = Integer.parseInt(record.next());
				//instantiate total sales of sold widgets 
				double sales =0;
				System.out.println(amount+" widgets sold");
				/*condition:true if the amount of sold widgets 
				 * is greater than 0 AND list is not empty*/
				while(amount>0&&!list.isEmpty()){
					//condition:true if the amount is greater
					//than or equal to amount of widgets in 
					//the first node of the list
					if(amount>=list.getAmount()){
						//calculate the sales by total amount of 
						//widgets time price in the first node
						sales +=list.getAmount()*list.getPrice();
						System.out.printf("%d at %.2f each\tSales:$%.2f\n",list.getAmount(),list.getPrice(),(list.getAmount()*list.getPrice()));
						//minus the amount in the fisrt node from amount
						amount -= list.getAmount();
						//remove first node
						list.remove();
					}
					else{
						//reset the amount in the first node to 
						//the amount of first node - amount
						list.setAmount(list.getAmount()-amount);
						System.out.printf("%d at %.2f each\tSales:$%.2f\n",amount,list.getPrice(),(amount*list.getPrice()));
						//calculate sales
						sales +=amount*list.getPrice();
						//set amount to 0
						amount =0;
					}
				}
				//if there is amount left unable to fulfill
				//print the amount that is not available
				if(amount>0){
					System.out.printf("remainder of %d Widgets not available\n",amount);
				}
				//if count of promotion is greater than 0,
				//apply promotion to total sales
				if(countP>0){
					System.out.println("Promotion Apply");
					sales =sales* ((100.00-promotion)/100.00);
					countP--;
				}
				System.out.printf("Total Sales:$%.2f\n",sales);
			}
			else if(input.equals("P")){
				//read promotion from record
				promotion = Integer.parseInt(record.next().replace("%", ""));
				System.out.println(promotion+"% off for the next two customers");
				//promotiom is available for the next two customers
				countP=2;
			}
			System.out.println("*************************************");
		record.close();
		}
		System.out.println("Widgets left in stock");
		//print widgets left in stock
		list.print();
		data.close();
	}catch (FileNotFoundException e) {
		System.out.println("File not found");
	}
	}
}