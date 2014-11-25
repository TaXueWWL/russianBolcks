//package ¶íÂÞË¹·œ¿é;

/**¶íÂÞË¹·œ¿é
 *date 14.11.17
 *@version 1.0
 *
 *@author ÎäÎÄÁŒ
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

 class Tetrisblok extends JPanel implements KeyListener{
	/**
	 *¶íÂÞË¹·œ¿éÀàTetrisblokŒÌ³ÐJPanel£¬Í¬Ê±ÊµÏÖŒüÅÌÊÂŒþœÓ¿ÚKeyListener
	 */
	private static final long serialVersionUID = 1L;
	private int blockType;//·œ¿éÀàÐÍ
	private int turnState;//·œ¿éÐý×ª×ŽÌ¬
	private int score = 0;//·ÖÊý
	private int nextblockType = -1, nextturnState = -1;//ÏÂÒ»·œ¿éµÄÀàÐÍºÍ×ŽÌ¬
	private int x, y;//µ±Ç°·œ¿éµÄÎ»ÖÃ
	private Timer timer;//¶šÊ±Æ÷
	//ÓÎÏ·µØÍŒ£¬ŽæŽ¢ÒÑŸ­·ÅÏÂµÄ·œ¿é£š1£©Œ°Î§Çœ£š2£©£¬¿Õ°×ŽŠÎª£š0£©
	int[][] map = new int[12][21];
	//·œ¿éµÄÐÎ×Ž£¬ÓÐµ¹Z,Z,L,J,I,Ìï£¬ºÍT 7ÖÖ
	
 /**
  *ÈýÎ¬Êý×éshapesŽæŽ¢7ÖÖ·œ¿éÐÎ×ŽŒ°ÆäÐý×ª±äÐÎ£¬ŽúÂëÈçÏÂ
  */
  private final int shapes[][][] = new int[][][]{
	//³€ÌõTÐÎ ***
	//         ¡Á
	{	{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0},
		{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0}},		
	//µ¹Z×ÖÐÎ  **
	//        **
	{	{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0}},
	//Z×ÖÐÎ	  **
	//         **
	{	{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0}},
	//J×ÖÐÎ   *
	//        ***
	{	{0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0},
		{1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
		{1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0}},
	//Ìï×ÖÐÎ  **
	//		  **
	{	{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0}},
	//L×ÖÐÎ   *
	//		  *	
	//		  *¡Á	
	{	{1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0},
		{1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0},
		{0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0}},
	//¡Í×ÖÐÎ  *
	//		 ***
	{	{0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0}}};
		
 /**
  *²úÉúÏÂÒ»·œ¿éŒ°ÆäÐý×ª×ŽÌ¬
  */
  public void newblock(){
	//Ã»ÓÐÏÂÒ»·œ¿é
	if(nextblockType == -1 && nextturnState == -1){
		blockType = (int)(Math.random()*1000)%7;
		turnState = (int)(Math.random()*1000)%4;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}	
	else{//ÒÑÓÐÏÂÒ»·œ¿é
		blockType = nextblockType;
		turnState = nextturnState;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}
	x = 4;	y = 0;//ÆÁÄ»ÉÏ·œÖÐÑë
	if(gameover(x, y) == 1){//ÓÎÏ·œáÊø
		newmap();
		drawwall();
		score = 0;
		JOptionPane.showMessageDialog(null, "GAME OVER...");
	}
  }
 /**
  *»­Î§Çœ
  */
  public void drawwall(){
	int i, j;
	for(i = 0; i < 12; i++){
		map[i][20] = 2;
	}
	for(j = 0; j < 21; j++){//ÔÚ0ÁÐºÍ11ÁÐ
		map[11][j] = 2;
		map[0][j] = 2;
	}
			
  }
  //³õÊŒ»¯µØÍŒ
  public void newmap(){
	int i, j;
	for(i = 0; i < 12; i++){
		for(j = 0; j < 21; j++){
			map[i][j] = 0;
		}
	}
  }
  /**
   *Tetrisblok()¹¹Ôì·œ·š²úÉúÒ»žöÐÂµÄÏÂÂä·œ¿é£¬²¢Æô¶¯¶šÊ±Æ÷¡£
   *¶šÊ±Æ÷Ž¥·¢ÊÂŒþÍê³ÉÆÁÄ»ÆÁÄ»ÖØ»­£¬ÅÐ¶Ïµ±Ç°·œ¿éÊÇ·ñ¿ÉÒÔÏÂÂä
   *²úÉúÐÂµÄ·œ¿é
   */
   Tetrisblok(){
	newblock();
	newmap();
	drawwall();
	timer = new Timer(500, new TimerListener());//0.5s
	timer.start();
   }
   //¶šÊ±Æ÷ŒàÌýÊÂŒþ
   class TimerListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(blow(x, y + 1, blockType, turnState) == 1){//¿ÉÒÔÏÂÂä
			y = y + 1;//µ±Ç°·œ¿éÏÂÂä
		}
		if(blow(x, y + 1, blockType, turnState) == 0){//²»¿ÉÒÔÏÂÂä
			add(x, y, blockType, turnState);//¹Ì¶šµ±Ç°·œ¿é
			delline();//ÏûÈ¥ÂúÐÐ
			newblock();//²úÉúÐÂµÄ·œ¿é
		}
		repaint();
	}
   }
   //²Ëµ¥ÊÂŒþ,ŽïµœÓÎÏ·ÔÝÍ£ºÍŒÌÐø
public void newGame()//ÐÂÓÎÏ·
   {
		newblock();
		newmap();
		drawwall();
   }
   public void pauseGame()//ÔÝÍ£ÓÎÏ·
   {
		timer.stop();
   }
   public void continueGame()//ŒÌÐøÓÎÏ·
   {
		timer.start();
   }
   /**
    *turn()Ðý×ªµ±Ç°·œ¿é£¬Ðý×ªŽÎÊýŒÓÒ»ºó£¬blowÅÐ¶ÏÊÇ·ñ¿ÉÒÔ
	*Ðý×ª£¬²»¿ÉÒÔÔòÐý×ªŽÎÊý»ÖžŽÎªÔ­ÀŽµÄÖµ
	*/
	
	//Ðý×ªµ±Ç°·œ¿é
	public void turn(){
		int tempturnState = turnState;
		turnState = (turnState + 1) % 4;
		if(blow(x, y, blockType, turnState) == 1){//¿ÉÒÔÐý×ª
		}
		if(blow(x, y, blockType, turnState) == 0){//²»¿ÉÐý×ª
			turnState = tempturnState;//œ«Ðý×ªŽÎÊý»ÖžŽÎªÔ­ÀŽµÄÖµ
		}
		repaint();
	}
	//·œÏòÒÆ¶¯·œ¿é£¬ÅÐ¶Ï¿ÉÒÆ¶¯ºóÖØ»­
   public void left(){
	if(blow(x - 1, y, blockType, turnState) == 1){
		x = x - 1;
	}
	repaint();
   }
   
   public void right(){
	if(blow(x + 1, y, blockType, turnState) == 1){
		x = x + 1;
	}
	repaint();
   }
   
   public void down(){
	if(blow(x, y + 1, blockType, turnState) == 1){//¿ÉÒÔÏÂÂä
		y = y + 1;
	}
	if(blow(x, y + 1, blockType, turnState) == 0){//²»ÄÜÏÂÂä
		add(x, y, blockType, turnState);
		newblock();
		delline();
	}
	repaint();
   }
   //ÅÐ¶ÏÒÆ¶¯»òÐý×ªºóÎ»ÖÃÊÇ·ñºÏ·š£¬ÊÇ·ñÓëÇœ±ÚÅö×²
   public int blow(int x, int y, int blockType, int turnState){
		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				if(((shapes[blockType][turnState][a * 4 + b] == 1) &&
				(map[x + b + 1][y + a] == 1)) || 
				((shapes[blockType][turnState][a * 4 + b] == 1) &&
				(map[x + b + 1][y + a] == 2))){
					return 0;
				}
			}
		}
		return 1;
   }
   //delline()ÏûÈ¥ÂúÐÐ£¬µÚdÐÐÂúÔòÉÏ·œ·œ¿éÏÂÒÆ
   public void delline(){
		int c = 0;
		for(int b = 0; b < 21; b++){
			for(int a = 0; a < 12; a++){
				if(map[a][b] == 1){
					c += 1;
					if(c == 10){//žÃÐÐÂú
						score += 10;
						for(int d = b; d > 0; d--){
							for(int e = 0; e < 12; e++){
								//ÉÏ·œ·œ¿éÏÂÒÆ
								map[e][d] = map[e][d - 1];
							}
						}
					}
				}
			}
			c = 0;
		}
   }
   //gameover
   public int gameover(int x, int y){
		if(blow(x, y, blockType, turnState) == 0){
			return 1;
		}
		return 0;
   }
   //ÌíŒÓµ±Ç°·œ¿éµœµØÍŒ
   public void add(int x, int y, int blockType, int turnState){
		int j = 0;
		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				if(shapes[blockType][turnState][j] == 1){
					map[x + b + 1][y + a] = shapes[blockType][turnState][j];
				}
				j++;
			}
		}
   }
   //paint()ÖØ»­ÆÁÄ»
   public void paint(Graphics g){
		super.paint(g);//µ÷ÓÃžžÀàµÄpaint£š£©·œ·š£¬ÊµÏÖ³õÊŒ»¯ÇåÆÁ
		int i, j;
		//»­µ±Ç°·œ¿é
		for(j = 0; j < 16; j++){
			if(shapes[blockType][turnState][j] == 1){
				g.fillRect((j % 4 + x + 1) * 15, (j / 4 + y) * 15, 15, 15);  
			}
		}
		//»­ÒÑŸ­¹Ì¶šµÄ·œ¿éºÍÎ§Çœ
		for(j = 0; j < 21; j++){
			for(i = 0; i < 12; i++){
				if(map[i][j] == 1){
					//»­·œ¿é
					g.fillRect(i * 15, j * 15, 15, 15);
				}
				if(map[i][j] == 2){
					//»­Î§Çœ
					g.fillRect(i * 15, j * 15, 15, 15);
				}
			}
		}
		g.drawString("SCORE= " + score, 225, 15);
		g.drawString("nextBlockShape ", 225, 50);
		//ÔÚŽ°¿ÚÓÒ²àÇøÓò»æÖÆÏÂÒ»·œ¿é
		for(j = 0; j < 16; j++){
			if(shapes[nextblockType][nextturnState][j] == 1){
				g.fillRect(225 + (j % 4) * 15, (j / 4) * 15 + 100, 15, 15);
			}
		}
   }
   //ŒüÅÌŒàÌý
   public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_DOWN:
				down();
				break;
			case KeyEvent.VK_UP:
				turn();
				break;
			case KeyEvent.VK_RIGHT:
				right();
				break;
			case KeyEvent.VK_LEFT:
				left();
				break;	
		}
   }
   //No use to type other keys
   public void keyReleased(KeyEvent e){
   }
   public void keyTyped(KeyEvent e){
   }

}
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 

