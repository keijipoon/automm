import java.util.*;

public class  Heap{
	public int[] heap;		// ノードIDを格納するヒープ
  	public int num;		// 現在の要素数
  // コンストラクタ
  // デフォールトでは、サイズが1024までのヒープを作成
  	public Heap(int linkMAX){
  		heap = new int [linkMAX];
  		num = 0;
  	}  
	/**
	 * TODO キーを挿入
	 * @param nodeMap
	 * @param nodeID
	 */
	public void insert(Map<Integer, Node> nodeMap,int nodeID){
    	heap[num++] = nodeID;
    	
    	int i = num, j = i / 2;
    	while(i > 1 && nodeMap.get(heap[i-1]).getCost() < nodeMap.get(heap[j-1]).getCost()){
    	  int t = heap[i-1];
    	  heap[i-1] = heap[j-1];
    	  heap[j-1] = t;
    	  i = j;
    	  j = i/2;
    	}
  	}
	
	/**
	 * TODO キーを削除
	 * @param node
	 * @param nodeID
	 */
	public void decreaseKey(Map<Integer, Node> nodeMap, int nodeID){
		int nodePosition = 1;
		//ヒープ内のノード位置を探す
		while(true){
			if(nodeID == heap[nodePosition-1]){
				break;
			}
			nodePosition++;
		}
		//
    	int i=nodePosition,j=i/2;
    	while(i > 1 && nodeMap.get(heap[i-1]).getCost() < nodeMap.get(heap[j-1]).getCost()){
      	  int t = heap[i-1];
      	  heap[i-1] = heap[j-1];
      	  heap[j-1] = t;
      	  i = j;
      	  j = i / 2;
      	}
  	}
	
	/**
	 * TODO 先頭の要素を取り除き、返す
	 * @param nodeMap
	 * @return
	 */
	public int deleteMin(Map<Integer, Node> nodeMap){
    	try{
			int r = heap[0];
	    	heap[0] = heap[--num];
	    	int i = 1, j = i * 2;
	    	while(j <= num){
	    		if(j + 1 <= num && nodeMap.get(heap[j-1]).getCost() > nodeMap.get(heap[j]).getCost()){
	    			j++;
	    		}
	    		if(nodeMap.get(heap[i-1]).getCost() > nodeMap.get(heap[j-1]).getCost()){
					int t = heap[i-1];
					heap[i-1] = heap[j-1];
					heap[j-1] = t;
	    	  	}
	    	  	i = j;
	    	  	j = i * 2;
	    	}
	    	return r;
    	}catch (ArrayIndexOutOfBoundsException e){
    		return 0;
    	}
    	
  	}
	public int numValue(){
		return num;
	}
}
