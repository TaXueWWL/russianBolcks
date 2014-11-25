//package ¶íÂÞË¹·œ¿é;

/**
 *ÏÔÊŸÓÎÏ·Ãæ°åTetrisblokœçÃæ£¬ŒÓÈë²Ëµ¥ÒÔŒ°Ê±ŒäŒàÌý
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import ¶íÂÞË¹·œ¿é.Tetrisblok;
import javax.swing.*;
@SuppressWarnings("serial")
public class TetrisFrame extends JFrame implements ActionListener{
	static JMenu game = new JMenu("ÓÎÏ·");
	JMenuItem newgame = game.add("ÐÂÓÎÏ·");
	JMenuItem pause = game.add("ÔÝÍ£");
	JMenuItem goon = game.add("ŒÌÐø");
	JMenuItem exit = game.add("ÍË³ö");
	static JMenu help = new JMenu("°ïÖú");
	JMenuItem about = help.add("¹ØÓÚ");
	Tetrisblok a = new Tetrisblok();
	public TetrisFrame(){
		addKeyListener(a);
		this.add(a);
		newgame.addActionListener(this);//"ÐÂÓÎÏ·"²Ëµ¥Ïî
		pause.addActionListener(this);//"ÔÝÍ£"²Ëµ¥Ïî
		goon.addActionListener(this);//"ŒÌÐø"²Ëµ¥Ïî
		about.addActionListener(this);//"¹ØÓÚ"²Ëµ¥Ïî
		exit.addActionListener(this);//"ÍË³ö"²Ëµ¥Ïî
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newgame)//"ÐÂÓÎÏ·"²Ëµ¥Ïî
		{
			a.newGame();
		}
		else if(e.getSource() == pause)//"ÔÝÍ£"²Ëµ¥Ïî
		{
			a.pauseGame();
		}
		else if(e.getSource() == goon)//"ŒÌÐø"²Ëµ¥Ïî
		{
			a.continueGame();
		}
		else if(e.getSource() == about)//"¹ØÓÚ"²Ëµ¥Ïî
		{
			DisplayToast("°Ž×óÓÒŒüÒÆ¶¯\nÉÏŒüœøÐÐ·œ¿éÐý×ª~\n\r\rMade_by_WWL\nFor SSR_LSC_LWW_CYD_XM");
		}
		else if(e.getSource() == exit)//"ÍË³ö"²Ëµ¥Ïî
		{
			System.exit(0);
		}
	}
	
	public void DisplayToast(String str){
		JOptionPane.showMessageDialog(null, str, "ÓÎÏ·ÌáÊŸ", 
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//"œáÊø"°ŽÅ¥¿ÉÊ¹ÓÃ
		frame.setSize(320, 380);
		frame.setTitle("¶íÂÞË¹·œ¿éV1.0--for XM");
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}




