/*
 * $Id: LatLong2XY.java,v 1.3 2002/03/17 14:07:31 nie Exp $
 */

package jp.jasminesoft.gcat.scalc;

/** 
 * 緯度経度値から平面直角座標値を算出するための
 * ユーティリティです。
 * 緯度経度値の入力は 10 進数表記で、ddd.mmss となる。 
 *
 * @version $Revision: 1.3 $ $Date: 2002/03/17 14:07:31 $
 * @author  Yoshinori Nie
 */

public class LatLong2XY {
    /** MeshCodeManager */
    private MeshCodeManager meshman;

    /** 入力された緯度 */
    private double lat;

    /** 入力された経度 */
    private double lng;

    /** 入力された、あるいは自動算出された平面直角座標系の系番号 */
    private int kei;

    /** ラジアンに変換された緯度 */
    private double r_lat;

    /** ラジアンに変換された経度 */
    private double r_lng;

    /** 算出された X 座標 */
    private double x;

    /** 算出された Y 座標 */
    private double y;

    /** 再計算が必要かどうかを示すフラグ */
    private boolean needsCalcFlag;

    /**
     * デフォルトコンストラクタ
     */
    public LatLong2XY() {
	this(-1);
    }

    /**
     * コンストラクタ。
     * 引数に -1 を入力した場合は、自動算出される。
     *
     * @param kei 系番号
     */
    public LatLong2XY(int kei) {
	meshman = MeshCodeManager.getInstance();
	setNeedsCalcFlag(true);
	this.kei = kei;
    }

    /**
     * 緯度をセットする。
     * 入力は、ddd.mmss 形式となる。(度分秒)
     *
     * @param lat 緯度
     */
    public void setLatitude(double lat) {
	this.lat = lat * 10000.0;
	setNeedsCalcFlag(true);
    }

    /**
     * 経度をセットする。
     * 入力は、ddd.mmss 形式となる。(度分秒)
     *
     * @param lng 経度
     */
    public void setLongitude(double lng) {
	this.lng = lng * 10000.0;
	setNeedsCalcFlag(true);
    }

    /**
     * 系番号をセットする。
     * 引数に -1 を入力した場合は、自動算出される。
     *
     * @param kei 系番号
     */
    public void setKei(int kei) {
	this.kei = kei;
	setNeedsCalcFlag(true);
    }

    /**
     * 現在、セットされている緯度を返す。
     *
     * @return 緯度
     */
    public double getLatitude() {
	return lat;
    }

    /**
     * 現在、セットされている経度を返す。
     *
     * @return 経度
     */
    public double getLongitude() {
	return lng;
    }

    /**
     * 現在、セットされている系番号を返す。
     *
     * @return 系番号
     */
    public int getKei() {
	return kei;
    }

    /**
     * 平面直角座標系の X 値を返す。
     *
     * @return 平面直角座標系の X 値
     */
    public double getX() {
	if (needsCalcFlag)
	    calc();
	return x;
    }

    /**
     * 平面直角座標系の Y 値を返す。
     *
     * @return 平面直角座標系の Y 値
     */
    public double getY() {
	if (needsCalcFlag)
	    calc();
	return y;
    }

    /**
     * 計算必要性フラグの変更
     */
    private synchronized void setNeedsCalcFlag(boolean b) {
	needsCalcFlag = b;
    }

    /**
     * 座標変換 (緯度経度 -> 平面直角座標)
     * 計算が失敗した場合、X, Y には Double.MIN_VALUE が格納される。
     */
    private void calc() {
	Radians rcnv_lat = new Radians(lat);
	Radians rcnv_lng = new Radians(lng);
	r_lat = rcnv_lat.getRadian();
	r_lng = rcnv_lng.getRadian();
	if (kei == -1) {
	    kei = meshman.getProperKei(lat/10000, lng/10000);
	}

	if (kei > 0) {
	    //System.out.println("r_lat:"+r_lat+",r_lng:"+r_lng+",kei:"+kei);
	    XY conv = new XY(r_lat, r_lng, kei);
	    x = conv.getX();
	    y = conv.getY();
	} else {
	    x = Double.MIN_VALUE;
	    y = Double.MIN_VALUE;
	}
	setNeedsCalcFlag(false);
    }

    /**
     * 算出された X,Y 座標値が有効かどうかを判断する。
     * ここでは縮率を求め、それが規定値におさまっていれば有効とする。
     *
     * @return 算出された X,Y 座標値が有効とみなされれば<code>true</code>を返す。
     * そうでなければ<code>false</code>を返す。
     * @see jp.jasminesoft.gcat.scalc.Rate
     */
    public boolean isInvalid() {
	if (needsCalcFlag)
	    calc();
	Rate rate = new Rate(r_lat, r_lng, x);
	//System.out.println("M:"+rate.getM());
	return rate.hanteiM();
    }

    /**
     * テストルーチン
     * ここで出力された値と、
     * http://vldb.gsi.go.jp/sokuchi/surveycalc/bl2xyf.html
     * の結果を比較し、同一であれば大丈夫とみなしたい。
     */
    public static void main(String[] argv) {
	/*
	 * 緯度経度 -> 平面直角座標 変換テスト
	 * 下記の例では、X, Y ともに 0m となる。
	 */
	double lng = 131.0;
	double lat = 33.0;
	int kei = 2;
	//double lng = 129.36256;
	//double lat = 33.052452;
	//int kei = -1;
	if (argv.length >= 3) {
	    lng = Double.parseDouble(argv[0]);
	    lat = Double.parseDouble(argv[1]);
	    kei = Integer.parseInt(argv[2]);
	}
	System.out.println("Lat:"+lat+", Lng:"+lng+", kei:"+kei);
	LatLong2XY prg = new LatLong2XY(kei);
	prg.setLongitude(lng);
	prg.setLatitude(lat); 
	System.out.println("X:"+prg.getX()+",Y:"+prg.getY()+" ["+prg.isInvalid()+"]");
    }
}
