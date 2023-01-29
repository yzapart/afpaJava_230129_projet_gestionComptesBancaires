package afpaJava_231029_projet_gestion_comptes_bancaires;

public class Agence {
	private String codeAgence, nom, adresse ;
	
	public Agence(String codeAgence, String nom, String adresse) {
		this.codeAgence = codeAgence;
		this.nom = nom;
		this.adresse = adresse;
	}
	
	public String getCodeAgence() {
		return codeAgence;
	}
	public String getAdresse() {
		return adresse;
	}
	public String getNom() {
		return nom;
	}
	public String toStr() {
		return "codeAgence: " + codeAgence + "\t" + nom + "\t" + adresse;
	}
	
}
