//package ����˹����;

/**����˹����
 *date 14.11.17
 *@version 1.0
 *
 *@author ������
 **/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

 class Tetrisblok extends JPanel implements KeyListener{
	/**
	 *����˹������Tetrisblok�̳�JPanel��ͬʱʵ�ּ����¼��ӿ�KeyListener
	 */
	private static final long serialVersionUID = 1L;
	private int blockType;//��������
	private int turnState;//������ת״̬
	private int score = 0;//����
	private int nextblockType = -1, nextturnState = -1;//��һ��������ͺ�״̬
	private int x, y;//��ǰ�����λ��
	private Timer timer;//��ʱ��
	//��Ϸ��ͼ���洢�Ѿ����µķ��飨1����Χǽ��2�����հ״�Ϊ��0��
	int[][] map = new int[12][21];
	//�������״���е�Z,Z,L,J,I,���T 7��
	
 /**
  *��ά����shapes�洢7�ַ�����״������ת���Σ���������
  */
  private final int shapes[][][] = new int[][][]{
	//����T�� ***
	//         ��
	{	{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0},
		{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0}},		
	//��Z����  **
	//        **
	{	{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0}},
	//Z����	  **
	//         **
	{	{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0}},
	//J����   *
	//        ***
	{	{0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0},
		{1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
		{1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0}},
	//������  **
	//		  **
	{	{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0}},
	//L����   *
	//		  *	
	//		  *��	
	{	{1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0},
		{1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0},
		{0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0}},
	//������  *
	//		 ***
	{	{0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0}}};
		
 /**
  *������һ���鼰����ת״̬
  */
  public void newblock(){
	//û����һ����
	if(nextblockType == -1 && nextturnState == -1){
		blockType = (int)(Math.random()*1000)%7;
		turnState = (int)(Math.random()*1000)%4;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}	
	else{//������һ����
		blockType = nextblockType;
		turnState = nextturnState;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}
	x = 4;	y = 0;//��Ļ�Ϸ�����
	if(gameover(x, y) == 1){//��Ϸ����
		newmap();
		drawwall();
		score = 0;
		JOptionPane.showMessageDialog(null, "GAME OVER...");
	}
  }
 /**
  *��Χǽ
  */
  public void drawwall(){
	int i, j;
	for(i = 0; i < 12; i++){
		map[i][20] = 2;
	}
	for(j = 0; j < 21; j++){//��0�к�11��
		map[11][j] = 2;
		map[0][j] = 2;
	}
			
  }
  //��ʼ����ͼ
  public void newmap(){
	int i, j;
	for(i = 0; i < 12; i++){
		for(j = 0; j < 21; j++){
			map[i][j] = 0;
		}
	}
  }
  /**
   *Tetrisblok()���췽������һ���µ����䷽�飬��������ʱ����
   *��ʱ�������¼������Ļ��Ļ�ػ����жϵ�ǰ�����Ƿ��������
   *�����µķ���
   */
   Tetrisblok(){
	newblock();
	newmap();
	drawwall();
	timer = new Timer(500, new TimerListener());//0.5s
	timer.start();
   }
   //��ʱ�������¼�
   class TimerListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(blow(x, y + 1, blockType, turnState) == 1){//��������
			y = y + 1;//��ǰ��������
		}
		if(blow(x, y + 1, blockType, turnState) == 0){//����������
			add(x, y, blockType, turnState);//�̶���ǰ����
			delline();//��ȥ����
			newblock();//�����µķ���
		}
		repaint();
	}
   }
   //�˵��¼�,�ﵽ��Ϸ��ͣ�ͼ���
   public void newGame()//����Ϸ
   {
		newblock();
		newmap();
		drawwall();
   }
   public void pauseGame()//��ͣ��Ϸ
   {
		timer.stop();
   }
   public void continueGame()//������Ϸ
   {
		timer.start();
   }
   /**
    *turn()��ת��ǰ���飬��ת������һ��blow�ж��Ƿ����
	*��ת������������ת�����ָ�Ϊԭ����ֵ
	*/
	
	//��ת��ǰ����
	public void turn(){
		int tempturnState = turnState;
		turnState = (turnState + 1) % 4;
		if(blow(x, y, blockType, turnState) == 1){//������ת
		}
		if(blow(x, y, blockType, turnState) == 0){//������ת
			turnState = tempturnState;//����ת�����ָ�Ϊԭ����ֵ
		}
		repaint();
	}
	//�����ƶ����飬�жϿ��ƶ����ػ�
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
	if(blow(x, y + 1, blockType, turnState) == 1){//��������
		y = y + 1;
	}
	if(blow(x, y + 1, blockType, turnState) == 0){//��������
		add(x, y, blockType, turnState);
		newblock();
		delline();
	}
	repaint();
   }
   //�ж��ƶ�����ת��λ���Ƿ�Ϸ����Ƿ���ǽ����ײ
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
   //delline()��ȥ���У���d�������Ϸ���������
   public void delline(){
		int c = 0;
		for(int b = 0; b < 21; b++){
			for(int a = 0; a < 12; a++){
				if(map[a][b] == 1){
					c += 1;
					if(c == 10){//������
						score += 10;
						for(int d = b; d > 0; d--){
							for(int e = 0; e < 12; e++){
								//�Ϸ���������
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
   //��ӵ�ǰ���鵽��ͼ
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
   //paint()�ػ���Ļ
   public void paint(Graphics g){
		super.paint(g);//���ø����paint����������ʵ�ֳ�ʼ������
		int i, j;
		//����ǰ����
		for(j = 0; j < 16; j++){
			if(shapes[blockType][turnState][j] == 1){
				g.fillRect((j % 4 + x + 1) * 15, (j / 4 + y) * 15, 15, 15);  
			}
		}
		//���Ѿ��̶��ķ����Χǽ
		for(j = 0; j < 21; j++){
			for(i = 0; i < 12; i++){
				if(map[i][j] == 1){
					//������
					g.fillRect(i * 15, j * 15, 15, 15);
				}
				if(map[i][j] == 2){
					//��Χǽ
					g.fillRect(i * 15, j * 15, 15, 15);
				}
			}
		}
		g.drawString("SCORE= " + score, 225, 15);
		g.drawString("nextBlockShape ", 225, 50);
		//�ڴ����Ҳ����������һ����
		for(j = 0; j < 16; j++){
			if(shapes[nextblockType][nextturnState][j] == 1){
				g.fillRect(225 + (j % 4) * 15, (j / 4) * 15 + 100, 15, 15);
			}
		}
   }
   //���̼���
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
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
