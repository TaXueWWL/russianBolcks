//package 俄罗斯方块;

/**俄罗斯方块
 *date 14.11.17
 *@version 1.0
 *
 *@author 武文良
 **/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

 class Tetrisblok extends JPanel implements KeyListener{
	/**
	 *俄罗斯方块类Tetrisblok继承JPanel，同时实现键盘事件接口KeyListener
	 */
	private static final long serialVersionUID = 1L;
	private int blockType;//方块类型
	private int turnState;//方块旋转状态
	private int score = 0;//分数
	private int nextblockType = -1, nextturnState = -1;//下一方块的类型和状态
	private int x, y;//当前方块的位置
	private Timer timer;//定时器
	//游戏地图，存储已经放下的方块（1）及围墙（2），空白处为（0）
	int[][] map = new int[12][21];
	//方块的形状，有倒Z,Z,L,J,I,田，和T 7种
	
 /**
  *三维数组shapes存储7种方块形状及其旋转变形，代码如下
  */
  private final int shapes[][][] = new int[][][]{
	//长条T形 ***
	//         ×
	{	{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0},
		{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0}},		
	//倒Z字形  **
	//        **
	{	{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0}},
	//Z字形	  **
	//         **
	{	{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0}},
	//J字形   *
	//        ***
	{	{0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0},
		{1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
		{1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0}},
	//田字形  **
	//		  **
	{	{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0}},
	//L字形   *
	//		  *	
	//		  *×	
	{	{1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0},
		{1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0},
		{0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0}},
	//⊥字形  *
	//		 ***
	{	{0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
		{1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0}}};
		
 /**
  *产生下一方块及其旋转状态
  */
  public void newblock(){
	//没有下一方块
	if(nextblockType == -1 && nextturnState == -1){
		blockType = (int)(Math.random()*1000)%7;
		turnState = (int)(Math.random()*1000)%4;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}	
	else{//已有下一方块
		blockType = nextblockType;
		turnState = nextturnState;
		nextblockType = (int)(Math.random() * 1000)%7;
		nextturnState = (int)(Math.random() * 1000)%4;
	}
	x = 4;	y = 0;//屏幕上方中央
	if(gameover(x, y) == 1){//游戏结束
		newmap();
		drawwall();
		score = 0;
		JOptionPane.showMessageDialog(null, "GAME OVER...");
	}
  }
 /**
  *画围墙
  */
  public void drawwall(){
	int i, j;
	for(i = 0; i < 12; i++){
		map[i][20] = 2;
	}
	for(j = 0; j < 21; j++){//在0列和11列
		map[11][j] = 2;
		map[0][j] = 2;
	}
			
  }
  //初始化地图
  public void newmap(){
	int i, j;
	for(i = 0; i < 12; i++){
		for(j = 0; j < 21; j++){
			map[i][j] = 0;
		}
	}
  }
  /**
   *Tetrisblok()构造方法产生一个新的下落方块，并启动定时器。
   *定时器触发事件完成屏幕屏幕重画，判断当前方块是否可以下落
   *产生新的方块
   */
   Tetrisblok(){
	newblock();
	newmap();
	drawwall();
	timer = new Timer(500, new TimerListener());//0.5s
	timer.start();
   }
   //定时器监听事件
   class TimerListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(blow(x, y + 1, blockType, turnState) == 1){//可以下落
			y = y + 1;//当前方块下落
		}
		if(blow(x, y + 1, blockType, turnState) == 0){//不可以下落
			add(x, y, blockType, turnState);//固定当前方块
			delline();//消去满行
			newblock();//产生新的方块
		}
		repaint();
	}
   }
   //菜单事件,达到游戏暂停和继续
   public void newGame()//新游戏
   {
		newblock();
		newmap();
		drawwall();
   }
   public void pauseGame()//暂停游戏
   {
		timer.stop();
   }
   public void continueGame()//继续游戏
   {
		timer.start();
   }
   /**
    *turn()旋转当前方块，旋转次数加一后，blow判断是否可以
	*旋转，不可以则旋转次数恢复为原来的值
	*/
	
	//旋转当前方块
	public void turn(){
		int tempturnState = turnState;
		turnState = (turnState + 1) % 4;
		if(blow(x, y, blockType, turnState) == 1){//可以旋转
		}
		if(blow(x, y, blockType, turnState) == 0){//不可旋转
			turnState = tempturnState;//将旋转次数恢复为原来的值
		}
		repaint();
	}
	//方向移动方块，判断可移动后重画
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
	if(blow(x, y + 1, blockType, turnState) == 1){//可以下落
		y = y + 1;
	}
	if(blow(x, y + 1, blockType, turnState) == 0){//不能下落
		add(x, y, blockType, turnState);
		newblock();
		delline();
	}
	repaint();
   }
   //判断移动或旋转后位置是否合法，是否与墙壁碰撞
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
   //delline()消去满行，第d行满则上方方块下移
   public void delline(){
		int c = 0;
		for(int b = 0; b < 21; b++){
			for(int a = 0; a < 12; a++){
				if(map[a][b] == 1){
					c += 1;
					if(c == 10){//该行满
						score += 10;
						for(int d = b; d > 0; d--){
							for(int e = 0; e < 12; e++){
								//上方方块下移
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
   //添加当前方块到地图
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
   //paint()重画屏幕
   public void paint(Graphics g){
		super.paint(g);//调用父类的paint（）方法，实现初始化清屏
		int i, j;
		//画当前方块
		for(j = 0; j < 16; j++){
			if(shapes[blockType][turnState][j] == 1){
				g.fillRect((j % 4 + x + 1) * 15, (j / 4 + y) * 15, 15, 15);  
			}
		}
		//画已经固定的方块和围墙
		for(j = 0; j < 21; j++){
			for(i = 0; i < 12; i++){
				if(map[i][j] == 1){
					//画方块
					g.fillRect(i * 15, j * 15, 15, 15);
				}
				if(map[i][j] == 2){
					//画围墙
					g.fillRect(i * 15, j * 15, 15, 15);
				}
			}
		}
		g.drawString("SCORE= " + score, 225, 15);
		g.drawString("nextBlockShape ", 225, 50);
		//在窗口右侧区域绘制下一方块
		for(j = 0; j < 16; j++){
			if(shapes[nextblockType][nextturnState][j] == 1){
				g.fillRect(225 + (j % 4) * 15, (j / 4) * 15 + 100, 15, 15);
			}
		}
   }
   //键盘监听
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
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
