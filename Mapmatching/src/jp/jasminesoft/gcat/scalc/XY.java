/*
 * $Id: XY.java,v 1.2 2002/03/17 10:52:42 nie Exp $
 */

package jp.jasminesoft.gcat.scalc;

/** 
 * XYは、経緯度をXY座標に変換するクラスです。
 *
 * 本パッケージは、琉球大学工学部情報工学科 宮城研究室の成果物
 * を、ジャスミンソフトが整理・統合したものです。再利用を快諾
 * して頂いた宮城 隼夫教授以下、宮城研究室のスタッフにこの場を
 * 借りて感謝致します。
 * 
 * @version $Revision: 1.2 $ $Date: 2002/03/17 10:52:42 $
 * @author  Miho Nagata
 * @author  Yoshinori Nie
 */

class XY extends Pala2 {
    /**
     * 赤道から緯度までの子午線弧長を求めるクラス
     */ 
    ArcLength arc;

    /**
     * 求点の緯度から座標系原点の緯度までの子午線弧長
     */
    double arc_gap;

    /**
     * コンストラクタ
     */
    XY(double setB, double setL, int kei) {
	//スーパークラスPala2に、求点緯度、求点経度、座標系番号を渡します。
	super(setB, setL, kei);

	this.arc = new ArcLength();
	this.arc_gap = arc.getArcGap(setB, gentenB);
    }

    /**
     * 緯度をX座標に変換するメソッド
     */
    double getX(){

	double x1 = Math.pow(lam,2) * prc * Math.sin(b)
	    * Math.cos(b) / 2.0;

	double x2 = Math.pow(lam,4) * prc * Math.sin(b)
	    * Math.pow(Math.cos(b),3) / 24.0
	    * ( 5.0 - Math.pow(t,2) + 9.0 * Math.pow(eta,2)
		+ 4.0 * Math.pow(eta,4));

	double x3 = Math.pow(lam,6) * prc * Math.sin(b)
	    * Math.pow(Math.cos(b),5) / 720.0
	    * (61.0 - 58.0 * Math.pow(t,2) + Math.pow(t,4) 
	       + 270.0 * Math.pow(eta,2) 
               - 330.0 * Math.pow(t,2) * Math.pow(eta,2));

	//System.out.println("arc_gap:"+arc_gap+",x1:"+x1+",x2:"+x2+",x3:"+x3);
	return m0 * (arc_gap + x1 + x2 + x3);

	/*
	double t = Math.tan(b);
	double eta2 = Math.pow(eta, 2);
	double eta4 = Math.pow(eta, 4);
	double cosb = Math.cos(b);

	double x1 = prc * Math.pow(cosb, 2) * t * Math.pow(lam,2) / 2.0;

	double x2 = prc * Math.pow(cosb, 4) * t * Math.pow(lam,4) *
	    (5.0 - Math.pow(t, 2) + 9.0 * eta2 + 4 * eta4) / 24.0;

	double x3 = prc * Math.pow(cosb, 6) * t * Math.pow(lam, 6) *
	    (-61.0 + 58.0 * Math.pow(t, 2) - Math.pow(t, 4)
	     -270.0 * eta2 + 330.0 * Math.pow(t, 2) * eta2) / 720.0;

	System.out.println("arc_gap:"+arc_gap+",x1:"+x1+",x2:"+x2+",x3:"+x3);
	return (arc_gap + x1 + x2 - x3);
	*/
    }

    /**
     * 経度をY座標に変換するメソッド
     */ 
    double getY(){

	double y1 = lam * prc * Math.cos(b)
	    + Math.pow(lam,3) * Math.pow(Math.cos(b),3) * prc / 6.0
	    * (1.0 - Math.pow(t,2) + Math.pow(eta,2));

	double y2 = Math.pow(lam,5) * Math.pow(Math.cos(b),5) * prc / 120.0
	    * ( 5.0 - 18.0 * Math.pow(t,2) + Math.pow(t,4) 
		+ 14.0 * Math.pow(eta,2) 
		- 58.0 * Math.pow(t,2) * Math.pow(eta,2));

	//System.out.println("y1:"+y1+",y2:"+y2);
	return m0 * (y1 + y2);

	/*
	double t = Math.tan(b);
	double cosb = Math.cos(b);

	double y1 = prc * cosb * lam;
	double y2 = prc * Math.pow(cosb, 3) * Math.pow(lam, 3) *
	    (-1.0 + Math.pow(t, 2) - Math.pow(eta, 2)) / 6.0;
	double y3 = prc * Math.pow(cosb, 5) * Math.pow(lam, 5) *
	    (-5.0 + 18.0 * Math.pow(t, 2) - Math.pow(t, 4)
	     -14.0 * Math.pow(eta, 2) + 58.0 * Math.pow(t, 2) * Math.pow(eta, 2)) / 120.0;
	System.out.println("y1:"+y1+",y2:"+y2+",y3:"+y3);
	return m0 * (y1 - y2 - y3);
	*/
    }
}
