import java.util.*;
public class Dijkstra{
	public static int costMax = 9999999;//初期値 コストの最大値
	public static double pathLength = 9999999;//経路距離
	public static ArrayList<Integer> linkIDList = new ArrayList<Integer>();//経路
	
	/**
	 * TODO ノードから行ける先のリンクを設定する．
	 * @param linkList
	 * @param NodeMap
	 */
	public static void set(Map<Integer, Link> linkMap, Map<Integer, Node> nodeMap){
    	
		for(Iterator<Integer> i = linkMap.keySet().iterator();i.hasNext();){
			Integer o = i.next();
			Link link = linkMap.get(o);		
			Node node = nodeMap.get(link.getUpNodeID());
			node.getNextLinkList().add(link);
		}
    }
	
	public static void exeDijkstra(Map<Integer, Link> linkMap, Map<Integer, Node> nodeMap,int startNodeID,int endNodeID) {
	    int newNodeID = startNodeID;  //新たに加えられたノード
	    int nodeCount = 1;   //ノードの数だけカウントしていく．起点ノードがすでに含まれているので１からカウントする	
        Heap heap = new Heap(linkMap.size());//heap構造を考慮
        Node node;
        //ノード属性をリセット
        for(Iterator<Integer> i = nodeMap.keySet().iterator(); i.hasNext();){
        	Integer o = i.next(); 
        	node = nodeMap.get(o);        	
        	node.setNodeSet(3);
        	node.setCost(costMax);
        	node.setBeforeLinkID(Integer.MAX_VALUE);
        }
        
        node = nodeMap.get(newNodeID);
        node.setCost(0);
        //
        do{
        	node = nodeMap.get(newNodeID);
        	node.setNodeSet(1);                         //start nodeを集合1に含める
	        for(Link link :  node.getNextLinkList()){//newNodeIDから出ているリンク数だけ繰り返し計算
         		Node nextNode = nodeMap.get(link.getDnNodeID()); //新たに加えられたノード集合1のノードから出ているリンクの終点ノード,newNodeIDから出ているi番目のリンクIDの終点ノード(nextNodeID)を取り出す
        		int nextCost = node.getCost() + link.getLinkCost(); //開始点からnextLinkIDを経由した場合のnextNodeIDまでのコストを計算
        		
        		if(nextNode.getNodeSet() == 3){//nextNodeIDが初めて(ノード集合3）かどうかを判定
        			nextNode.setNodeSet(2); //ノード集合2とする
        			nextNode.setCost(nextCost); //nextNodeIDまでの最短経路コストの候補としてnextCostを代入する
        			nextNode.setBeforeLinkID(link.getLinkID());
        			heap.insert(nodeMap, nextNode.getNodeID()); //ヒープにnextNodeIDを新しく確保
        		}else if(nextCost < nextNode.getCost() && nextNode.getNodeSet() != 1){//以前に計算したnextNodeIDまでのコストと今回計算したnextCostを比較．今回が小さければ以下に進む．
        			nextNode.setCost(nextCost);//nextNodeIDまでの最短経路コストの候補としてnextCostを入れ替える
        			nextNode.setBeforeLinkID(link.getLinkID());//nextLinkID;#nextNodeIDまでの最短経路コストの候補としてnextCostに対応するbeforeLinkIDを入れ替える
        			heap.decreaseKey(nodeMap, nextNode.getNodeID());//ヒープに入れていたnextNodeIDまでのコストを変更する
        		}	
	        }
	        newNodeID = heap.deleteMin(nodeMap);//最も総コストが少ないノードをnewNodeIDとする.
	        Node newNode = nodeMap.get(newNodeID);
	        if(newNode != null){
		        newNode.setNodeSet(1);
		        nodeCount++;
	        }
	    }while(nodeCount < nodeMap.size() && node.getCost() < costMax && newNodeID != endNodeID && newNodeID != 0);
    } 
// 結果出力 =======================================================================
    public static int getCost(Map<Integer, Node> nodeMap ,int dnNodeID) {
    	int minCost = nodeMap.get(dnNodeID).getCost();
    	return minCost;
    }
//  経路計算 =======================================================================
    public static List<Integer> exePath(Map<Integer, Link> linkMap, Map<Integer, Node> nodeMap,int startNodeID,int endNodeID) {
    	int nodeID = endNodeID;//終点ノードまでに通過するノード    	
    	pathLength = 0;
    	while(startNodeID != nodeID){
    		Node node = nodeMap.get(nodeID);
    		int linkID = node.getBeforeLinkID();
    		linkIDList.add(0,linkID);
    		Link link = linkMap.get(linkID);
    		pathLength = pathLength + link.getDistance();
    		nodeID = link.getUpNodeID();
    	}
    	return linkIDList;
    }
    
//  結果出力 =======================================================================
    public static ArrayList<Integer> getPath() {
    	return linkIDList;
    }
    public static double getPathLength() {
    	return pathLength;
    }
    public static void clearPath() {
    	linkIDList.clear();
    }

}
