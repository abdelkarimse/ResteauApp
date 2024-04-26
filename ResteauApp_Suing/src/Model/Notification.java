package Model;
public class Notification {
    private int id;
    private int idN;

    private int aff;
    private int op;

    public Notification(int idN,int id, int aff, int op) {
		super();
		this.idN = idN;

		this.id = id;
		this.aff = aff;
		this.op = op;
	}

	@Override
	public String toString() {
	    String notificationType = (this.op == 1) ? "sur le restaurant" : "sur la livraison";
	    return "Nouvelle commande de lid  " + this.getId() + " " + notificationType ;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getIdN() {
		return idN;
	}

	public void setIdN(int id) {
		this.idN = id;
	}

	public int getAff() {
		return aff;
	}

	public void setAff(int aff) {
		this.aff = aff;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}

	

}