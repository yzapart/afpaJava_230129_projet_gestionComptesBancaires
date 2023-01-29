package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {

	private String idClient, nom, prenom, email;
	private LocalDate dateNaissance;
	private boolean courant, livret, PEL;
	
	public Client(String id_client, 
				  String nom, 
				  String prenom, 
				  LocalDate dateNaissance, 
				  String email
				  ) {
		this.idClient = id_client;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
	}
	
	public String getId_client() {
		return idClient;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String toStr() {
		return "id: " + idClient +  
				"\t" + nom +  
				"\t" + prenom +  
				"\t" + dateNaissance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +  
				"\t" + email;
	}
}
