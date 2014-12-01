//package 俄罗斯方块;

/**
 *显示游戏面板Tetrisblok界面，加入菜单以及时间监听
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import 俄罗斯方块.Tetrisblok;
import javax.swing.*;
@SuppressWarnings("serial")
public class TetrisFrame extends JFrame implements ActionListener{
	static JMenu game = new JMenu("游戏");
	JMenuItem newgame = game.add("新游戏");
	JMenuItem pause = game.add("暂停");
	JMenuItem goon = game.add("继续");
	JMenuItem exit = game.add("退出");
	static JMenu help = new JMenu("帮助");
	JMenuItem about = help.add("关于");
	Tetrisblok a = new Tetrisblok();
	public TetrisFrame(){
		addKeyListener(a);
		this.add(a);
		newgame.addActionListener(this);//"新游戏"菜单项
		pause.addActionListener(this);//"暂停"菜单项
		goon.addActionListener(this);//"继续"菜单项
		about.addActionListener(this);//"关于"菜单项
		exit.addActionListener(this);//"退出"菜单项
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newgame)//"新游戏"菜单项
		{
			a.newGame();
		}
		else if(e.getSource() == pause)//"暂停"菜单项
		{
			a.pauseGame();
		}
		else if(e.getSource() == goon)//"继续"菜单项
		{
			a.continueGame();
		}
		else if(e.getSource() == about)//"关于"菜单项
		{
			DisplayToast("按左右键移动\n上键进行方块旋转~\n\r\rMade_by_WWL\nFor SSR_LSC_LWW_CYD_XM");
		}
		else if(e.getSource() == exit)//"退出"菜单项
		{
			System.exit(0);
		}
	}
	
	public void DisplayToast(String str){
		JOptionPane.showMessageDialog(null, str, "游戏提示", 
									JOptionPane.ERROR_MESSAGE);
	}
	
	//Main Method
	public static void main(String[] args){
		TetrisFrame frame = new TetrisFrame();
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		menu.add(game);
		menu.add(help);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//"结束"按钮可使用
		frame.setSize(320, 380);
		frame.setTitle("俄罗斯方块V1.0--for XM");
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}



















