package afpaJava_231029_projet_gestion_comptes_bancaires;


public class Compte {
	private String idCompte, codeAgence, typeCompte;
	private Client client;
	private double solde;
	private boolean decouvertAutorise;
	
	public Compte(
			String idCompte, 
			String codeAgence, 
			String typeCompte,  
			Client client, 
			double solde, 
			boolean decouvertAutorise
			) {
		this.idCompte = idCompte;
		this.codeAgence = codeAgence;
		this.typeCompte = typeCompte;
		this.client = client;
		this.solde = solde;
		this.decouvertAutorise = decouvertAutorise;
	}
	
	public String getIdCompte() {
		return idCompte;
	}
	public Client getClient() {
		return client;
	}
	public String getCodeAgence() {
		return codeAgence;
	}
	public double getSolde() {
		return solde;
	}
	public String getTypeCompte() {
		return typeCompte;
	}
	public boolean getDecouvertAutorise() {
		return decouvertAutorise;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public void setDecouvertAutorise(boolean decouvertAutorise) {
		this.decouvertAutorise = decouvertAutorise;
	}
	
	public String toStr() {
		String dec = (decouvertAutorise == true) ? "Déc. autorisé" : "Déc. non-autorisé"; 
		return  "id: " + idCompte + 
				"\tcodeAg: " + codeAgence + 
				"\t" + typeCompte + 
				"\t" + client.getNom() + 
				" " + client.getPrenom() + 
				"\t" + solde + "   "+
				"\t" + dec;

	}
}
