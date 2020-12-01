import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuWindow extends JFrame implements ChangeListener, ActionListener{
	
	JSlider slider;
    JLabel label0, label, label1, label2, label3, label4, label5, label6, label7, label8, label9;
    DrawPanel panel;
    JButton colorbutton, heartbutton, applebutton, penguinbutton, cakebutton;
    JTextField text;
    
    JFileChooser fileChooser;
    
    // public static void main(String[] args) {
    //     MenuWindow menuW = new MenuWindow();

    //     menuW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     menuW.setBounds(10, 10, 300, 200);
    //     menuW.setTitle("Menu Window");
    //     menuW.setVisible(true);
    // }

    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(710, 10, 300, 500);
        this.setTitle("Menu Window");
        this.setVisible(true);
    }
    
    public MenuWindow(DrawPanel thePanel) {
    	
    	this.panel = thePanel;
        
    	//現在の機能を表示する用のラベル
        this.label0 = new JLabel();
        this.label0.setText("機能 : Pen  ");
        this.label0.setHorizontalAlignment(JLabel.CENTER);
        
    	// 0 から 20 までのスライダーを作り、初期値を 10 にする
        this.slider = new JSlider(0, 20, 10);
        // 大目盛りは 5 単位、小目盛りは 1 単位にする
        this.slider.setMajorTickSpacing(5);
        this.slider.setMinorTickSpacing(1);
        // 目盛りを表示する
        this.slider.setPaintTicks(true);
        // 目盛りに数字を表示する
        this.slider.setLabelTable(this.slider.createStandardLabels(5));
        this.slider.setPaintLabels(true);
        this.slider.addChangeListener(this);
        
        //ボタンの作成
        this.colorbutton = new JButton("choose a color");
        this.colorbutton.addActionListener(this);

        //スタンプの文字列入力用
        this.text = new JTextField(15);
        this.text.setText("Hello world!");
        //テキストフィールドの真ん中に表示
        this.text.setHorizontalAlignment(JTextField.CENTER);
        
        //現在のvalueを表示する用のラベル
        this.label = new JLabel();
        this.label.setText("ペンの太さ・文字の大きさ : " + this.slider.getValue());
        this.label.setHorizontalAlignment(JLabel.CENTER);
        
        //現在の色を表示する用のラベル
        this.label1 = new JLabel();
        this.label1.setText("Color : ");
        this.label2 = new JLabel();
        this.label2.setText("     ");
        this.label1.setHorizontalAlignment(JLabel.RIGHT);
        this.label2.setHorizontalAlignment(JLabel.RIGHT);
        
        this.label3 = new JLabel();
        this.label3.setText("Stamp String : ");
        //this.label2.setHorizontalAlignment(JLabel.CENTER);
        //this.label2.setVerticalAlignment(JLabel.CENTER);
        this.label2.setBackground(Color.black);
		this.label2.setOpaque(true);
		
		this.label4 = new JLabel();
		this.label4.setText("____________________________________");
		
		this.label5 = new JLabel();
		this.label5.setText("____________________________________");
		
		this.label6 = new JLabel();
		this.label6.setText("____________________________________");
		
		this.label7 = new JLabel();
		this.label7.setText("  迷路で遊ぼう！好きなステージを選んでね  ");
		
		this.label8 = new JLabel();
		this.label8.setText("  スタンプしたい文字を入力してね！  ");
		
		this.label9 = new JLabel();
		this.label9.setText("____________________________________");
		
		ImageIcon icon1 = new ImageIcon("heart.jpg");
		ImageIcon icon2 = new ImageIcon("apple.jpg");
		ImageIcon icon3 = new ImageIcon("penguin.jpg");
		ImageIcon icon4 = new ImageIcon("cake.jpg");
		this.heartbutton = new JButton(" ハート迷路", icon1);
        this.heartbutton.addActionListener(this);
        this.applebutton = new JButton(" りんご迷路 ", icon2);
        this.applebutton.addActionListener(this);
        this.penguinbutton = new JButton(" ペンギン迷路 ", icon3);
        this.penguinbutton.addActionListener(this);
        this.cakebutton = new JButton(" ケーキ迷路 ", icon4);
        this.cakebutton.addActionListener(this);
        
        
        JPanel p = new JPanel();
        p.add(label0);
        p.add(label6);// 機能を表示
        p.add(label1);
        p.add(label2);
        p.add(colorbutton);
        p.add(label9);
       // p.add(rainbowbutton);
        //p.add(spoitbutton);
        p.add(label8);
        p.add(label3);
        p.add(text);
        p.add(label4);
        p.add(label);
        //p.add(okbutton);
        p.add(slider);
        p.add(label5);
        p.add(label7);
        p.add(heartbutton);
        p.add(applebutton);
        p.add(penguinbutton);
        p.add(cakebutton);

        this.getContentPane().add(p, BorderLayout.CENTER);
        //this.getContentPane().add(label, BorderLayout.PAGE_END);
        //this.getContentPane().add(label3, BorderLayout.BEFORE_LINE_BEGINS);
        //this.getContentPane().add(label, BorderLayout.PAGE_END);
        
        

    	
    }

    public void stateChanged(ChangeEvent e) {
        int theValue = this.slider.getValue();
        this.label.setText("ペンの太さ・文字の大きさ : " + theValue);
        this.panel.setPenWidth(theValue);
        this.panel.setFont(new Font("Arial", Font.PLAIN, (theValue + 15)));
    }

    public void setSliderValue(int newValue) {
        this.slider.setValue(newValue);
    }
    
    public void actionPerformed(ActionEvent e) {
    	//String cmd = e.getActionCommand();
        //layout.show(cardPanel, cmd);
        
    	if(e.getSource() == (colorbutton)) {
    		JColorChooser colorchooser = new JColorChooser();
			Color color = colorchooser.showDialog(this, "choose a color", Color.black);
			this.panel.setPenColor(color);
			this.label2.setBackground(color);
			this.label2.setOpaque(true);
;    	}
    	else if(e.getSource() == (heartbutton)) {
    		panel.openFile(new File("meiro-heart.png"));
    	}
    	else if(e.getSource() == (applebutton)) {
    		panel.openFile(new File("meiro-apple.png"));
    	}
    	else if(e.getSource() == (penguinbutton)) {
    		panel.openFile(new File("meiro-penguin.png"));
    	}
    	else if(e.getSource() == (cakebutton)) {
    		panel.openFile(new File("meiro-cake.png"));
    	}
    }
    public void kinouChanged(String kinou) {
        this.label0.setText("機能 : " + kinou + "  ");
    }
    //現在の色を表示する
    public void setChooseColor(Color newcolor) {
    	this.label2.setBackground(newcolor);
    }
    //コロコロスタンプの文字列を取得
    public void setKoroString() {
    	this.panel.setKoroString(this.text.getText());
    }
    //文字スタンプの座標の取得
    public void setStampCoordinate(int x, int y) {
    	this.panel.drawMojiString(this.text.getText(), x, y);
    }
}
