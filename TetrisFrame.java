//package ����˹����;

/**
 *��ʾ��Ϸ���Tetrisblok���棬����˵��Լ�ʱ�����
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import ����˹����.Tetrisblok;
import javax.swing.*;
@SuppressWarnings("serial")
public class TetrisFrame extends JFrame implements ActionListener{
	static JMenu game = new JMenu("��Ϸ");
	JMenuItem newgame = game.add("����Ϸ");
	JMenuItem pause = game.add("��ͣ");
	JMenuItem goon = game.add("����");
	JMenuItem exit = game.add("�˳�");
	static JMenu help = new JMenu("����");
	JMenuItem about = help.add("����");
	Tetrisblok a = new Tetrisblok();
	public TetrisFrame(){
		addKeyListener(a);
		this.add(a);
		newgame.addActionListener(this);//"����Ϸ"�˵���
		pause.addActionListener(this);//"��ͣ"�˵���
		goon.addActionListener(this);//"����"�˵���
		about.addActionListener(this);//"����"�˵���
		exit.addActionListener(this);//"�˳�"�˵���
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newgame)//"����Ϸ"�˵���
		{
			a.newGame();
		}
		else if(e.getSource() == pause)//"��ͣ"�˵���
		{
			a.pauseGame();
		}
		else if(e.getSource() == goon)//"����"�˵���
		{
			a.continueGame();
		}
		else if(e.getSource() == about)//"����"�˵���
		{
			DisplayToast("�����Ҽ��ƶ�\n�ϼ����з�����ת~\n\r\rMade_by_WWL\nFor SSR_LSC_LWW_CYD_XM");
		}
		else if(e.getSource() == exit)//"�˳�"�˵���
		{
			System.exit(0);
		}
	}
	
	public void DisplayToast(String str){
		JOptionPane.showMessageDialog(null, str, "��Ϸ��ʾ", 
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//"����"��ť��ʹ��
		frame.setSize(320, 380);
		frame.setTitle("����˹����V1.0--for XM");
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}



















