package org.demo.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Cards extends ReentrantLock{
	
	int count = 54;
	final List<Integer> cards = new ArrayList<Integer>(count);
	
	public volatile Integer currentCard = 0;

	/**
	 * 初始化牌堆
	 */
	public Cards() {
		for(int i=0; i<count; i++)
			cards.add(i);
	}

	/**
	 * 抽一张牌
	 * @return
	 */
	protected Integer drawCard() {
		if(currentCard<54)
			return cards.get(currentCard++);
		else 
			return null;
	}

	/**
	 * 抽三张牌
	 * @return
	 */
	public List<Integer> drawThreeCards() {
		List<Integer> threeCards = new ArrayList<Integer>();
		for(int i=0; i<3; i++) {
			Integer card = this.drawCard();
			if(card!=null)
				threeCards.add(card);
		}
		return threeCards; 
	}

	/**
	 * 当前牌堆剩余卡牌数量
	 * @return
	 */
	public int getRemainCardCount() {
		return count - currentCard;
	}
}