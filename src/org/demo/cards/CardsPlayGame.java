package org.demo.cards;

import java.util.concurrent.locks.Condition;

/**
 * 腾讯笔试题目，使用三个线程模拟斗地主抽牌
 * 思路是 使用三个 condition 作为玩家开始抽牌和抽牌结束锁的获得和释放的条件
 */
public class CardsPlayGame {

	public static void main(String[] args) {
		
		Cards cards = new Cards();
	
		Condition aThenB  = cards.newCondition();
		Condition bThenC  = cards.newCondition();
		Condition cThenA  = cards.newCondition();
			
		Player a = new Player(cards,"PlayA", true, cThenA, aThenB);
		Player b = new Player(cards,"PlayB", false, aThenB, bThenC);
		Player c = new Player(cards,"PlayC", false, bThenC, cThenA);
		
		new Thread(a, a.getName()).start();
		new Thread(b, b.getName()).start();
		new Thread(c, c.getName()).start();
		
	}
}