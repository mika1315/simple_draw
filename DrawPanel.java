import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	// for Double Buffer
	BufferedImage bufferImage=null;
	Graphics2D bufferGraphics=null;
	
	// for Double Buffer
	BufferedImage copybufferImage=null;	
	//Graphics2D bufferGraphics=null;
	
	// "undo" 機能のため 1 つ前のイメージを保存する buffer
    BufferedImage prevBufferImage = null;
    Graphics2D prevBufferGraphics = null;

	//ペンの色設定用の instance variables
	Color currentColor = Color.black;
	float currentWidth=10.0f;
    float currentRainbowColor = 0.0f;
    Color eraserColor = Color.white;
        
    // フォントサイズ用
    Font currentFont = new Font("Dialog", Font.PLAIN, 25);
    
    //画像スタンプ用
    Image image;
    
    //背景の色設定用
    Color currentBackgroundColor = Color.white;
    
    // 文字をコロコロスタンプ風に表示するための instance variables
    int stringLen;     // 文字列の長さ
    String str;        // 表示したい文字列
    int currentIndex;  // 現在表示している文字の文字列内でのインデックス
    int counter = 0;       // MAX_COUNT 回に 1 回だけ文字を表示するためのカウンター
    final int MAX_COUNT = 5;  // counter の最大値

 // バッファ用の image と graphics を用意する
    public void createBuffer(int width, int height) {
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        bufferGraphics = bufferImage.createGraphics();
        bufferGraphics.setBackground(Color.white);
        bufferGraphics.clearRect(0,0, width, height); // buffer clear

        // "undo" 用に image と graphics を用意する
        prevBufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        prevBufferGraphics = prevBufferImage.createGraphics();
        // 最初は bufferImage をそのままコピーしておく。
        copyBufferImage();
    }

    // bufferImage から "undo" 用の prevBufferImage にコピーする
    public void copyBufferImage() {
        prevBufferGraphics.drawImage(bufferImage, 0, 0, this);
    }

    // "undo" 機能
    public void undoDrawing() {
        bufferGraphics.drawImage(prevBufferImage, 0, 0, this);
        repaint();
    }
    // "New" 機能
    public void clearBuffer(int width, int height) {
    	currentBackgroundColor = Color.white;
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setBackground(currentBackgroundColor);
    	bufferGraphics.clearRect(0,0, width, height); // buffer clear
    	repaint();
    }
    
    // 指定した領域をimageとして保存（コピー）
    public Image copyImage(int x, int y, int width, int height) {
    	ImageFilter filter = new CropImageFilter(x,y,width,height);
    	FilteredImageSource fis =
    		      new FilteredImageSource(bufferImage.getSource(),filter);
    	Image clip = createImage(fis);
    	return clip;
    }
    // 指定した位置にペースト
    public void pasteImage(Image image, int x, int y) {
    	if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
		bufferGraphics.drawImage(image,x,y,this);
		repaint(); //画像を表示するためにpaintComponentを呼ぶ
    }
    // ラバーバンド用
    public void rubberBandRect(int x1, int y1, int x2, int y2) {
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    	bufferGraphics.setXORMode(bufferGraphics.getBackground());
		bufferGraphics.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2)); // バッファに描画する
		repaint();
		bufferGraphics.setPaintMode();
    }
    public void rubberBandfillRect(int x1, int y1, int x2, int y2) {
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    	bufferGraphics.setXORMode(bufferGraphics.getBackground());
		bufferGraphics.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2)); // バッファに描画する
		repaint();
		bufferGraphics.setPaintMode();
    }
    public void rubberBandOval(int x1, int y1, int x2, int y2) {
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    	bufferGraphics.setXORMode(bufferGraphics.getBackground());
		bufferGraphics.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2)); // バッファに描画する
		repaint();
		bufferGraphics.setPaintMode();
    }
    public void rubberBandfillOval(int x1, int y1, int x2, int y2) {
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    	bufferGraphics.setXORMode(bufferGraphics.getBackground());
		bufferGraphics.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2)); // バッファに描画する
		repaint();
		bufferGraphics.setPaintMode();
    }
    public void rubberBandLine(int x1, int y1, int x2, int y2) {
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    	bufferGraphics.setXORMode(bufferGraphics.getBackground());
		bufferGraphics.drawLine(x1, y1, x2, y2); // バッファに描画する
		repaint();
		bufferGraphics.setPaintMode();
    }
	// 画像ファイルの読み込み
	public void openFile(File file2open){
		BufferedImage pictureImage;
		try {
			pictureImage = ImageIO.read(file2open);
		} catch(Exception e){
			System.out.println("Error: reading file="+file2open.getName());
			return;
		}
		//画像に合わせたサイズでbufferImageとbufferGraphicsを作りなおして画像を読み込む
		this.createBuffer(pictureImage.getWidth(),pictureImage.getHeight());
		bufferGraphics.drawImage(pictureImage,0,0,this);
		repaint(); //画像を表示するためにpaintComponentを呼ぶ
	}
	
	// 画像ファイルの保存
	public void saveFile(File file2save) {
		try {
			ImageIO.write(bufferImage, "jpg", file2save);
		} catch (Exception e) {
			System.out.println("Error: writing file="+file2save.getName());
			return;
		}
	}

	// 今の penColor を返す
    public Color getPenColor() {
        return this.currentColor;
    }

	public void setPenColor(Color newColor) {
		currentColor = newColor;
	}
	public void setEraserColor(Color newColor) {
		eraserColor = newColor;
	}
	public void setPenWidth(float newWidth) {
		currentWidth = newWidth;
	}
	public void setFont(Font newFont) {
		currentFont = newFont;
	}
	// 背景の色を設定する
	public void setBackgroundColor(Color newBackgroundColor, int width, int height) {
		currentBackgroundColor = newBackgroundColor;
        bufferGraphics.setBackground(currentBackgroundColor);
        bufferGraphics.clearRect(0,0, width, height); // buffer clear
        repaint();
	}
	//public void drawRainbowLine(int x1, int y1, int x2, int y2) {
		//final float 
		
	//}
	public void drawRainbowLine(int x1, int y1, int x2, int y2) {
        // HSB だと、色を 0 から 1 で指定できる
        final float MAX_COLOR = 1.0f;
        final float MIN_COLOR = 0.0f;
        // 色の指定を 0.01 刻みで変化させる
        final float COLOR_STEP = 0.01f;
        if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
        // 虹色を COLOR_STEP 分進める
        if(currentRainbowColor < MAX_COLOR)
            currentRainbowColor += COLOR_STEP;
        else
            currentRainbowColor = MIN_COLOR;
        // HSB で指定された色に対応した Color を得る
        currentColor = Color.getHSBColor(currentRainbowColor,
                                                1.0f,
                                                1.0f);
        bufferGraphics.setColor(currentColor);
        bufferGraphics.setStroke(new BasicStroke(currentWidth, BasicStroke.CAP_ROUND,
                                                 BasicStroke.JOIN_MITER));
        bufferGraphics.drawLine(x1, y1, x2, y2);
        repaint();  // 再描画するため paintComponent を呼び出す
    }
	// コロコロスタンプ風に表示したい文字列を設定する
    public void setKoroString(String theStr) {
        this.str = theStr;
        this.stringLen = this.str.length();
        this.currentIndex = 0;
    }

    // コロコロスタンプ風に文字列を表示する
    public void drawCharsFromString(int x, int y) {
        
        if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }

        if (counter == 0) {
            counter++;   // counter を 1 つ進める
            bufferGraphics.setColor(currentColor);
            bufferGraphics.setFont(currentFont);
            // 座標(x, y)にprintStringを表示する
            bufferGraphics.drawString(this.str.substring(this.currentIndex,
                                                         this.currentIndex+1),
                                      x, y);
            repaint();  // 再描画するため paintComponent を呼び出す

            // インデックスを 1 つ進める
            if(this.currentIndex < (this.stringLen - 1))
                this.currentIndex++;
            else
                this.currentIndex = 0;
        } else {
            // counter が 0 以外なら何もせず counter を進める
            if (counter < MAX_COUNT)
                counter++;
            else
                counter = 0;
        }
    }
    // Dotの線用
    public void drawDot(int x1, int y1, int x2, int y2) {
        if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
        if (counter == 0) {
            counter++;   // counter を 1 つ進める
            bufferGraphics.setColor(currentColor);
            bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
            bufferGraphics.drawLine(x1, y1, x2, y2);
            repaint();  // 再描画するため paintComponent を呼び出す
        } else {
            // counter が 0 以外なら何もせず counter を進める
            if (counter < MAX_COUNT)
                counter++;
            else
                counter = 0;
        }
    }
    //文字のスタンプ用
    public void drawMojiString(String theStr, int x, int y) {
    	if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
    	this.str = theStr;
    	bufferGraphics.setColor(currentColor);
    	bufferGraphics.setFont(currentFont);
        bufferGraphics.setStroke(new BasicStroke(currentWidth, BasicStroke.CAP_ROUND,
                                                 BasicStroke.JOIN_MITER));
        bufferGraphics.drawString(this.str, x, y);
        repaint();  // 再描画するため paintComponent を呼び出す
    }
    //画像のスタンプ用
    public void drawImage(File fileimage, int x, int y) {
    	BufferedImage pictureImage;
    	if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
		try {
			pictureImage = ImageIO.read(fileimage);
		} catch(Exception e){
			System.out.println("Error: reading file="+fileimage.getName());
			return;
		}
		bufferGraphics.drawImage(pictureImage,x,y,this);
		repaint(); //画像を表示するためにpaintComponentを呼ぶ
	}
    // 好きな画像をスタンプにする
    public void drawSelectImage(File fileimage, int x, int y) {
    	BufferedImage pictureImage;
    	if(null == bufferGraphics) {
            this.createBuffer(this.getWidth(), this.getHeight());
            // バッファをまだ作っていなければ作る
        }
		try {
			pictureImage = ImageIO.read(fileimage);
		} catch(Exception e){
			System.out.println("Error: reading file="+fileimage.getName());
			return;
		}
		bufferGraphics.drawImage(pictureImage,x,y,this);
		repaint(); //画像を表示するためにpaintComponentを呼ぶ
	}
	public void drawLine(int x1, int y1, int x2, int y2) {
		//Graphics2D g = (Graphics2D)this.getGraphics();
		//Graphics g = this.getGraphics();
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(currentColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.drawLine(x1, y1, x2, y2); // バッファに描画する
		repaint();//再描画するためpaintComponentを呼び出す。
		//g.setColor(currentColor);
		//g.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		//g.drawLine(x1, y1, x2, y2);
	}
	public void drawEraser(int x1, int y1, int x2, int y2) {
		//Graphics2D g = (Graphics2D)this.getGraphics();
		//Graphics g = this.getGraphics();
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(eraserColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.drawLine(x1, y1, x2, y2); // バッファに描画する
		repaint();//再描画するためpaintComponentを呼び出す。
		//g.setColor(currentColor);
		//g.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		//g.drawLine(x1, y1, x2, y2);
	}
	//四角形を描写
	public void drawRect(int x1, int y1, int x2, int y2) {
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(currentColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.drawRect(x1, y1, x2, y2); // バッファに描画する
		repaint();
	}
	public void drawFillRect(int x1, int y1, int x2, int y2) {
		//Graphics2D g = (Graphics2D)this.getGraphics();
		//Graphics g = this.getGraphics();
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(currentColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.fillRect(x1, y1, x2, y2); // バッファに描画する
		repaint();//再描画するためpaintComponentを呼び出す。
	}
	public void drawOval(int x1, int y1, int x2, int y2) {
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(currentColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.drawOval(x1, y1, x2, y2); // バッファに描画する
		repaint();
	}
	public void drawFillOval(int x1, int y1, int x2, int y2) {
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		bufferGraphics.setColor(currentColor);
		bufferGraphics.setStroke(new BasicStroke(currentWidth ,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		bufferGraphics.fillOval(x1, y1, x2, y2); // バッファに描画する
		repaint();
	}
	// スポイト機能、抽出した色を返す
	public Color getRGB(int x, int y) {
		if(null==bufferGraphics) {
		 	this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る	
		}
		Color spoitcolor = new Color(bufferImage.getRGB(x, y));
		return spoitcolor;
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//他に描画するものがあるかもしれないので親を呼んでおく
		if(null!=bufferImage) g.drawImage(bufferImage, 0,0,this);//バッファを表示する
	}
}
