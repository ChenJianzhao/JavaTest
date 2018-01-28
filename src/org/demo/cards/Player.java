package org.demo.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class Player implements Runnable{

	private Cards cards = null;
	private boolean root = false;
	private String playName = null;
	Condition prePlayer, nextPlayer = null;
	
	private List<Integer> myCards = new ArrayList<Integer>();

	/**
	 *
	 * @param cards			牌堆
	 * @param playName		玩家名称
	 * @param root			是否是地主
	 * @param prePlayer		上一个抽牌的玩家，调用此 condition.await() 排队等待上一玩家抽完牌的通知
	 * @param nextPlayer	下一个抽牌的玩家，调用此 condition.signal() 通知下一玩家开始抽牌
	 */
	public Player(Cards cards, String playName, boolean root, Condition prePlayer, Condition nextPlayer) {
		this.cards = cards;
		this.root = root;
		this.playName = playName;
		this.prePlayer = prePlayer; 
		this.nextPlayer = nextPlayer;
	}
	
	@Override
	public void run() {
		
		try {
			drawThreeCards();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void drawThreeCards() throws InterruptedException {

		/*
		 * 地主等待 100ms, 让非地主线程先获得锁，再让出锁，并等待自己被唤醒的 condition
		 */
		if(root)
			Thread.sleep(100);
			
		cards.lock();

		/*
		 * 非地主等待被唤醒的 condition
		 */
		if(!root)
			prePlayer.await();
		
		try{
			List<Integer> temCard = null;
			do{

				/*
				 * 抽三张牌的过程
				 */
				System.out.println("--------------" + playName + " start draw card --------------");
				
				temCard = cards.drawThreeCards();
				for(Integer i : temCard) 
					System.out.println(playName + " draw card:" + i);
				myCards.addAll(temCard);
				
				System.out.println("--------------" + playName + " end draw card --------------");
				System.out.println();

				/*
				 * 通知下一个玩家竞争锁，并释放自己的锁，等待下一轮抽牌被唤醒
				 */
				nextPlayer.signal();
				prePlayer.await();
				
			}while(cards.getRemainCardCount()>0); 	// 牌堆还有剩余牌时才继续抽牌过程

		}finally{
			
			nextPlayer.signal();
			cards.unlock();
		}
		
	}

	public String getName() {
		return playName;
	}
	
}