# AutoMM
Automated Map Matching

## マップマッチングとは / What is map matching? 
自動車の走行データ、自転車の走行データなどのGPSデータは、プローブデータと呼ばれます。
これらは往々にして、GPSの誤差により少しずれた位置にプロットされてしまうことがあります。
それを正しく道路の上にプロットされるように修正する作業を、「マップマッチング」と呼びます。
カーナビなどに使われている技術です。

## マップマッチングのアルゴリズム / Algorithm
以下のように、様々なアルゴリズムが開発されているようです。

### 幾何解析(Geometric) マップマッチング
- Point to Point
- Point to Curve
- Curve to Curve
- Road Reduction Filter(RRF)

　幾何解析マップマッチング手法はリンクの道路ネットワークデータの形状のみを考慮した手法である。幾何解析手法で最も利用される手法は point to point といわれるマッチング手法である。

　この手法は測位点をネットワーク上の最も近いノードにマッチングするという簡単な手法であり、実行も容易で計算もとても速い.

### 位相幾何(Topological)解析 マップマッチング
- Weighted topological algorithm
- Simplified algorithm by Meng (2006)
- Algorithm based Various Similarity Criteria by Quddus et at.(2003)

　位相解析マップマッチング手法は幾何的な情報に加え、リンクの接続性、連続性をもとにマッチングを行う。このマッチングでは、連続する測位位置データが道路ネットワーク上のどのリンク上にマッピングされるかを、距離だけでなく、リンクデータと位置データの交差角度、相対角度距離などから決定する (リンク決定フェイズとする)。

　その後、速度と進行方向角度によってリンク上のどの位置にあるのかを決定する (位置決定フェイズとする)。 リンクとリンク上の位置を決定したのち、測位データが現在のリンク上にあるのか、つまり交差点に入ったかどうかを確認する (リンク確認フェイズとする)。

### 確率的(Probabilistic) マップマッチング
- Elliptical/rectangular confidence region

　確率的手法マップマッチング手法ではセンサーから取得された測位データ周辺に楕円や円形の信頼域を定義することが必要となる。この技術は DR センサからの位置をマッチングさせるのに初めて導入された。 また GPS センサから取得されたデータに対しても利用され、エラー領域は GPS 測位データの誤差分散から導くことが提案された。

### 高度な技術を利用したマップマッチング
- Kalman filter
- Dempster-Shafer’s mathematical theory of evidence •Flexible state-space model and particle filter •Interacting multiple model
- Fuzzy logic model

カルマンフィルタ、パーティクルフィルタ、DDR などより高度な概念を用いたマップマッチング手法がある。
カルマンフィルタとはシステムや観測値に様々なランダムなノイズがのっているときにそれぞれのノイズの大きさに応じて適切な重みづけを行い、
変化していくシステムの状態を精度よく推定するための手法である。


## 本ライブラリの計算ロジック / Computation logic of this library
### rtree でできること
- 近隣検索
- 交差点探索
- 多次元インデックス
- クラスタ化されたインデックス (Python のピクルスをインデックスエントリで直接格納)
- バルクローディング
- 削除
- ディスクのシリアル化
- カスタムストレージの実装(例えばZODBで空間インデックスを実装するため)

### rtree を用いてのマップマッチング / Map matching with rtree
1.マップマッチングしたいGPSデータを取り込む。

2.地図空間上にインデックス（番号）を振る。

3.インデックスを利用して、近接する道路の緯度経度を取得。

メリットとしては、
インデックスがあることで、すべての点と比較するのではなく、
ある限定したエリア内で最も近い道路の緯度経度を取得してくれます。

この手法は、前段落で説明したアルゴリズムの
幾何解析(Geometric) マップマッチングのうちの point to curve　という手法です。

## 参考文献 / References cited
BinN studies シリーズ ネットワーク行動学 -都市と移動- p.84 - p.106

http://bin.t.u-tokyo.ac.jp/kaken/data/full-20140926.pdf
