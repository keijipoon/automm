import java.sql.Timestamp;

class PassNode {      
	private int nodeID;//ノード	
	private int linkID;//リンク	
	private Timestamp passTime;//出発ノードからのコスト
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public int getLinkID() {
		return linkID;
	}
	public void setLinkID(int linkID) {
		this.linkID = linkID;
	}
	public Timestamp getPassTime() {
		return passTime;
	}
	public void setPassTime(Timestamp passTime) {
		this.passTime = passTime;
	}
    
}