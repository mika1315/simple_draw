import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * 
 */

/**
 * @author g1720505
 *
 */
public class SimpleDraw extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	int lastx=0, lasty=0, newx, newy, last_rectx=0, last_recty=0, 
			new_rectx, new_recty, new_clickx, new_clicky;
	boolean isDot = false; // Dot が指定されたら true
	boolean isNormal = false; // Normal が指定されたら true
	boolean isEraser = false; // Eraser が指定されたら true
	boolean isRect = false; // Rect が指定されたら true
	boolean isFillRect = false; // FillRect が指定されたら true
	boolean isOval = false; // Oval が指定されたら true
	boolean isFillOval = false; //FillOval が指定されたら true
	boolean isStraight = false; // Straight が指定されたら true
	boolean isSpoit = false;// Spoit が指定されたら true
	boolean isRainbow = false; // Rainbow が指定されたら true
	boolean isKoroString = false; // KoroString が指定されたら true
	boolean isStamp = false; // Stamp が指定されたら true
	boolean isFlowerStamp = false; // FlowerStamp が指定されたら true
	boolean isBearStamp = false; // BearStamp が指定されたら true
	boolean isSushiStamp = false; // SushiStamp が指定されたら true
	boolean isSelectStamp = false; // SelectStamp が指定されたら true
	boolean isCopy = false; // Copy が指定されたら true
	boolean isPaste = false; // Paste が指定されたら true
	DrawPanel panel;
	
	// ファイル指定のためのダイアログパネルのインスタンス
    JFileChooser fileChooser;
	// メニュー用のウィンドウ
    MenuWindow menuW;
    // spoitcolor用
    Color spoitcolor;
    // ペンの色の初期値は黒
    Color color = Color.black;
    //背景の色の初期値は白
    Color bg_color = Color.white;
    // コピーした領域をimageとして保存
    Image copyImage;
    
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		newx=e.getX();
		newy=e.getY();
		if(isRect) {
			this.isRect = true;
			panel.rubberBandRect(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandRect(last_rectx, last_recty, newx, newy);
		}
		else if(isFillRect) {
			this.isFillRect = true;
			panel.rubberBandfillRect(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandfillRect(last_rectx, last_recty, newx, newy);
		}
		else if(isOval) {
			this.isOval = true;
			panel.rubberBandOval(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandOval(last_rectx, last_recty, newx, newy);
		}
		else if(isFillOval) {
			this.isFillOval = true;
			panel.rubberBandfillOval(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandfillOval(last_rectx, last_recty, newx, newy);
		}
		else if(isStraight) {
			this.isStraight = true;
			panel.rubberBandLine(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandLine(last_rectx, last_recty, newx, newy);
		}
		//else if(isSpoit)
			//this.isSpoit = true;
		else if(isDot)
			panel.drawDot(lastx, lasty, newx, newy);
		else if(isEraser)
			panel.drawEraser(lastx, lasty, newx, newy);
		else if(isRainbow) 
			panel.drawRainbowLine(lastx, lasty, newx, newy);
		else if(isKoroString)
			panel.drawCharsFromString(newx, newy);
		else if(isStamp)
			this.isStamp = true;
		else if(isFlowerStamp)
			this.isFlowerStamp = true;
		else if(isBearStamp)
			this.isBearStamp = true;
		else if(isSushiStamp)
			this.isSushiStamp = true;
		else if(isSelectStamp)
			this.isSelectStamp = true;
		// コピペ用
		else if(isCopy) {
			this.isCopy = true;
			panel.rubberBandRect(last_rectx, last_recty, lastx, lasty);
			panel.rubberBandRect(last_rectx, last_recty, newx, newy);
		}
		else if(isPaste)
			this.isPaste = true;
		else {
			this.isNormal = true;
			panel.drawLine(lastx, lasty, newx, newy);
		}
		lastx=newx;
		lasty=newy;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		new_clickx = e.getX();
		new_clicky = e.getY();
		if(isStamp) 
			menuW.setStampCoordinate(new_clickx, new_clicky);
		else if(isFlowerStamp) {
			panel.drawImage(new File("flower.jpg"), new_clickx, new_clicky);
		}
		else if(isBearStamp) {
			panel.drawImage(new File("shirokuma.jpg"), new_clickx, new_clicky);
		}
		else if(isSushiStamp) {
			panel.drawImage(new File("sushi.jpg"), new_clickx, new_clicky);
		}
		else if(isSelectStamp)
			panel.drawSelectImage(fileChooser.getSelectedFile(), new_clickx, new_clicky);
		else if(isSpoit) {
			spoitcolor = panel.getRGB(new_clickx, new_clicky);
			panel.setPenColor(spoitcolor);
			menuW.setChooseColor(spoitcolor);
		}
		// コピペ用
		else if(isPaste)
			panel.pasteImage(copyImage, new_clickx, new_clicky);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		lastx = e.getX();
		lasty = e.getY();
		last_rectx = e.getX();
		last_recty = e.getY();
		panel.copyBufferImage(); // "undo" のために今のバッファを保存
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		new_rectx = e.getX();
		new_recty = e.getY();
		if(isRect) {
			panel.rubberBandRect(last_rectx, last_recty, lastx, lasty);
			panel.drawRect(Math.min(new_rectx, last_rectx), Math.min(new_recty, last_recty), 
					Math.abs(new_rectx - last_rectx), Math.abs(new_recty - last_recty));
		}
		else if(isFillRect) {
			panel.rubberBandfillRect(last_rectx, last_recty, lastx, lasty);
			panel.drawFillRect(Math.min(new_rectx, last_rectx), Math.min(new_recty, last_recty), 
					Math.abs(new_rectx - last_rectx), Math.abs(new_recty - last_recty));
		}
		else if(isOval) {
			panel.rubberBandOval(last_rectx, last_recty, lastx, lasty);
			panel.drawOval(Math.min(new_rectx, last_rectx), Math.min(new_recty, last_recty), 
					Math.abs(new_rectx - last_rectx), Math.abs(new_recty - last_recty));
		}
		else if(isFillOval) {
			panel.rubberBandfillOval(last_rectx, last_recty, lastx, lasty);
			panel.drawFillOval(Math.min(new_rectx, last_rectx), Math.min(new_recty, last_recty), 
					Math.abs(new_rectx - last_rectx), Math.abs(new_recty - last_recty));
		}
		else if(isStraight) {
			panel.rubberBandLine(last_rectx, last_recty, lastx, lasty);
			panel.drawLine(last_rectx, last_recty, new_rectx, new_recty);
		}
		//コピペ用
		else if(isCopy) {
			panel.rubberBandRect(last_rectx, last_recty, lastx, lasty);
			copyImage = panel.copyImage(last_rectx, last_recty, 
					Math.abs(new_rectx - last_rectx), Math.abs(new_recty - last_recty));
		}
		else if(isPaste)
			this.isPaste= true;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	private void addMenuItem
	(JMenu targetMenu, String itemName, String actionName, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(itemName);
		menuItem.setActionCommand(actionName);
		menuItem.addActionListener(listener);
		targetMenu.add(menuItem);
	}
	
	private void initMenu() {
		JMenuBar menubar=new JMenuBar();
		JMenu menuFile = new JMenu("File");
		this.addMenuItem(menuFile,"New","New",this);
		this.addMenuItem(menuFile,"Open...","Open",this);
		this.addMenuItem(menuFile,"Save...","Save",this);
		menubar.add(menuFile);
		
		JMenu menuPen = new JMenu("Pen");
		this.addMenuItem(menuPen, "Normal", "normal", this);
		this.addMenuItem(menuPen, "Dot", "dot", this);
		JMenu menuColor = new JMenu("Color");
		this.addMenuItem(menuColor, "Choose Color", "color", this);
		this.addMenuItem(menuColor, "Spoit", "spoit", this);
		this.addMenuItem(menuColor, "Black", "black", this);
		this.addMenuItem(menuColor, "Blue", "blue", this);
		this.addMenuItem(menuColor, "Yellow", "yellow", this);
		this.addMenuItem(menuColor, "Green", "green", this);
		this.addMenuItem(menuColor, "Red", "red", this);
		this.addMenuItem(menuColor, "Rainbow", "rainbow", this); //虹色
		menuPen.add(menuColor);
		
		JMenu menuWidth = new JMenu("Width");
		this.addMenuItem(menuWidth, "width1", "width1", this);
		this.addMenuItem(menuWidth, "width5", "width5", this);
		this.addMenuItem(menuWidth, "width10", "width10", this);
		this.addMenuItem(menuWidth, "width20", "width20", this);
		menuPen.add(menuWidth);
		menubar.add(menuPen);
		
		JMenu menuEraser = new JMenu("Eraser");
		this.addMenuItem(menuEraser, "width1", "e_width1", this);
		this.addMenuItem(menuEraser, "width5", "e_width5", this);
		this.addMenuItem(menuEraser, "width10", "e_width10", this);
		this.addMenuItem(menuEraser, "width20", "e_width20", this);
		menubar.add(menuEraser);
		
		JMenu menuKoro = new JMenu("KoroKoroStamp");
		this.addMenuItem(menuKoro, "String", "korostring", this);
		this.addMenuItem(menuKoro, "♡ Heart", "heart", this);
		this.addMenuItem(menuKoro, "☆ Star", "star", this);
        menubar.add(menuKoro);
        
        JMenu menuStamp = new JMenu("Stamp");
        JMenu menuString = new JMenu("String");
        JMenu menuFontSize = new JMenu("Font Size");
		this.addMenuItem(menuFontSize, "size1", "size1", this);
		this.addMenuItem(menuFontSize, "size5", "size5", this);
		this.addMenuItem(menuFontSize, "size10", "size10", this);
		this.addMenuItem(menuFontSize, "size20", "size20", this);
		menuString.add(menuFontSize);
		menuStamp.add(menuString);
		//ImageIcon icon1 = new ImageIcon("flower.jpg");
		//ImageIcon icon2 = new ImageIcon("shirokuma.jpg");
		//ImageIcon icon3 = new ImageIcon("sushi.jpg");
		//JMenuItem menuitem1 = new JMenuItem(" Flower", icon1);
		//JMenuItem menuitem2 = new JMenuItem(" Bear", icon2);
		//JMenuItem menuitem3 = new JMenuItem(" Sushi", icon3);
		//menuStamp.add(menuitem1);
		//menuStamp.add(menuitem2);
		//menuStamp.add(menuitem3);
		this.addMenuItem(menuStamp, "Flower", "flower", this);
		this.addMenuItem(menuStamp, "Bear", "bear", this);
		this.addMenuItem(menuStamp, "Sushi", "sushi", this);
		this.addMenuItem(menuStamp, "Choose image", "select", this);
        menubar.add(menuStamp);
		
		JMenu menuFigure = new JMenu("Figure");
		this.addMenuItem(menuFigure, "□ Rect", "rect1", this);
		this.addMenuItem(menuFigure, "■ Fill Rect", "rect2", this);
		this.addMenuItem(menuFigure, "○ Oval", "oval", this);
		this.addMenuItem(menuFigure, "● Fill Oval", "oval2", this);
		this.addMenuItem(menuFigure, "/ Straight Line", "straight", this);
		menubar.add(menuFigure);
		
		JMenu menuBackground = new JMenu("Background");
		this.addMenuItem(menuBackground, "Choose color", "bg_color", this);
		menubar.add(menuBackground);
		
		JMenu menuCopy = new JMenu("Copy&Paste");
        this.addMenuItem(menuCopy, "Copy", "copy", this);
        this.addMenuItem(menuCopy, "Paste", "paste", this);
        menubar.add(menuCopy);
        
		JMenu menuUndo = new JMenu("Undo");
        this.addMenuItem(menuUndo, "Undo", "undo", this);
        menubar.add(menuUndo);
        
        this.setJMenuBar(menubar);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("width1")){
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			panel.setPenWidth(1);
			menuW.setSliderValue(1);
		}
		else if(arg0.getActionCommand().equals("width5")){
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			panel.setPenWidth(5);
			menuW.setSliderValue(5);
		}
		else if(arg0.getActionCommand().equals("width10")){
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			panel.setPenWidth(10);
			menuW.setSliderValue(10);
		}
		else if(arg0.getActionCommand().equals("width20")){
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			panel.setPenWidth(20);
			menuW.setSliderValue(20);
		}
		else if(arg0.getActionCommand().equals("normal")) {
			this.isNormal = true;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = true;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
		}
		else if(arg0.getActionCommand().equals("dot")) {
			this.isNormal = false;
			this.isDot = true;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = true;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
		}
		else if(arg0.getActionCommand().equals("color")){
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			
			JColorChooser colorchooser = new JColorChooser();
			color = colorchooser.showDialog(this, "choose a color", Color.black);
			panel.setPenColor(color);
			menuW.setChooseColor(color);
			menuW.kinouChanged("Pen");
		}
		else if(arg0.getActionCommand().equals("spoit")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = true;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			//panel.setPenColor(spoitcolor);
			//menuW.setChooseColor(spoitcolor);
		}
		else if(arg0.getActionCommand().equals("black")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			color = Color.black;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
		}
		else if(arg0.getActionCommand().equals("blue")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			color = Color.blue;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
		}
		else if(arg0.getActionCommand().equals("yellow")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			color = Color.yellow;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
		}
		else if(arg0.getActionCommand().equals("green")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			color = Color.green;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
		}
		else if(arg0.getActionCommand().equals("red")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			color = Color.red;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
		}
		else if(arg0.getActionCommand().equals("rainbow")) {
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = true;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
		}
		else if(arg0.getActionCommand().equals("e_width1")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = true;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Eraser");
			menuW.setSliderValue(1);
			panel.setEraserColor(bg_color);
			//menuW.setChooseColor(bg_color);
			panel.setPenWidth(1);			
		}
		else if(arg0.getActionCommand().equals("e_width5")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = true;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Eraser");
			menuW.setSliderValue(5);
			panel.setEraserColor(bg_color);
			//menuW.setChooseColor(bg_color);
			panel.setPenWidth(5);			
		}
		else if(arg0.getActionCommand().equals("e_width10")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = true;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Eraser");
			menuW.setSliderValue(10);
			panel.setEraserColor(bg_color);
			//menuW.setChooseColor(bg_color);
			panel.setPenWidth(10);			
		}
		else if(arg0.getActionCommand().equals("e_width20")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = true;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Eraser");
			menuW.setSliderValue(20);
			panel.setEraserColor(bg_color);
			//menuW.setChooseColor(bg_color);
			panel.setPenWidth(20);			
		}
		else if(arg0.getActionCommand().equals("New")){
			this.isNormal = true;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Pen");
			bg_color = Color.white;
			color = Color.black;
			panel.setPenColor(color);
			menuW.setChooseColor(color);
			panel.clearBuffer(panel.getWidth(), panel.getHeight());
		}
		else if(arg0.getActionCommand().equals("Open")){
			int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
                panel.openFile(fileChooser.getSelectedFile());			  
		}
		else if(arg0.getActionCommand().equals("Save")) {
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
                panel.saveFile(fileChooser.getSelectedFile());
        }
		else if(arg0.getActionCommand().equals("korostring")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = true;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.setKoroString();
			menuW.kinouChanged("KoroKoroStamp String");
			//panel.setKoroString("OchanomizuUniversity");
		}
		else if(arg0.getActionCommand().equals("heart")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = true;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			panel.setKoroString("♡");
			menuW.kinouChanged("KoroKoroStamp ♡");
		}
		else if(arg0.getActionCommand().equals("star")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = true;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			panel.setKoroString("☆");
			menuW.kinouChanged("KoroKoroStamp ☆");
		}
		else if(arg0.getActionCommand().equals("flower")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = true;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Stamp Flower");
		}
		else if(arg0.getActionCommand().equals("bear")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = true;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Stamp Bear");
		}
		else if(arg0.getActionCommand().equals("sushi")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = true;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Stamp Sushi");
		}
		else if(arg0.getActionCommand().equals("select")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            	this.isSelectStamp = true;
			this.isCopy = false;
			this.isPaste = false;
			menuW.kinouChanged("Stamp selected picture");
		}
		else if(arg0.getActionCommand().equals("size1")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = true;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.setSliderValue(1);
			panel.setFont(new Font("Dialog", Font.PLAIN, 16));
			menuW.kinouChanged("Stamp String");
		}
		else if(arg0.getActionCommand().equals("size5")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = true;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.setSliderValue(5);
			panel.setFont(new Font("Dialog", Font.PLAIN, 20));
			menuW.kinouChanged("Stamp String");
		}
		else if(arg0.getActionCommand().equals("size10")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = true;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.setSliderValue(10);
			panel.setFont(new Font("Dialog", Font.PLAIN, 25));
			menuW.kinouChanged("Stamp String");
		}
		else if(arg0.getActionCommand().equals("size20")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = true;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.setSliderValue(20);
			panel.setFont(new Font("Dialog", Font.PLAIN, 35));
			menuW.kinouChanged("Stamp String");
		}
		else if(arg0.getActionCommand().equals("rect1")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = true;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.kinouChanged("Rect");
			//panel.setPenColor(color);
		}
		else if(arg0.getActionCommand().equals("rect2")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = true;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.kinouChanged("Fill Rect");
			//panel.setPenColor(color);
		}
		else if(arg0.getActionCommand().equals("oval")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = true;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.kinouChanged("Oval");
			//panel.setPenColor(color);
		}
		else if(arg0.getActionCommand().equals("oval2")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = true;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.kinouChanged("Fill Oval");
			//panel.setPenColor(color);
		}
		else if(arg0.getActionCommand().equals("straight")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = true;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = false;
			//panel.setPenColor(color);
			menuW.kinouChanged("Straight Line");
			//panel.setPenColor(color);
		}
		else if(arg0.getActionCommand().equals("bg_color")){
			JColorChooser bg_colorchooser = new JColorChooser();
			bg_color = bg_colorchooser.showDialog(this, "choose a background color", Color.white);
			panel.setEraserColor(bg_color);
			panel.setBackgroundColor(bg_color, panel.getWidth(), panel.getHeight());
		}
		else if(arg0.getActionCommand().equals("copy")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = true;
			this.isPaste = false;
			menuW.kinouChanged("Copy");
		}
		else if(arg0.getActionCommand().equals("paste")){
			this.isNormal = false;
			this.isDot = false;
			this.isEraser = false;
			this.isRect = false;
			this.isFillRect = false;
			this.isOval = false;
			this.isFillOval = false;
			this.isStraight = false;
			this.isSpoit = false;
			this.isRainbow = false;
			this.isKoroString = false;
			this.isStamp = false;
			this.isFlowerStamp = false;
			this.isBearStamp = false;
			this.isSushiStamp = false;
			this.isSelectStamp = false;
			this.isCopy = false;
			this.isPaste = true;
			menuW.kinouChanged("Paste");
		}
		else if(arg0.getActionCommand().equals("undo")){
			panel.undoDrawing();
		}
	}

	private Color Color(int i, int i2, int i3) {
		// TODO Auto-generated method stub
		return null;
	}

	private void init() {
		this.setTitle("Simple Draw");
		this.setSize(700, 500);
		panel=new DrawPanel();
		panel.setPenColor(Color.black);
		this.getContentPane().add(panel);
		panel.addMouseMotionListener(this);
		panel.addMouseListener(this);
		this.initMenu();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// バッファ用の Image と Graphics を用意する
		panel.createBuffer(panel.getWidth(), panel.getHeight());
        
		// ファイルを指定するためのダイアログパネルのインスタンスを用意する
        fileChooser = new JFileChooser();
        
		menuW = new MenuWindow(panel);
        menuW.init();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDraw frame=new SimpleDraw();
		frame.init();


	}


}
