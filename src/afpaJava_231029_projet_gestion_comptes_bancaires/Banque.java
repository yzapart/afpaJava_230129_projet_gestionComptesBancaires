package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Banque {
	
	public static ArrayList<Agence> listeAgences = new ArrayList<Agence>();
	public static ArrayList<Client> listeClients = new ArrayList<Client>();
	public static ArrayList<Compte> listeComptes = new ArrayList<Compte>();
	
	static DecimalFormat dec11 = new DecimalFormat("00000000000");

	public static void afficherAgences() {
		for (Agence agence : listeAgences) {
			System.out.println(agence.toStr());
		}
	}
	
	public static boolean checkCompteExistant(Client client, String typeCompte) {
		for (Compte compte : listeComptes) {
			if ((compte.getClient().equals(client) == true ) && (compte.getTypeCompte().equals(typeCompte) == true)) {
				return true;
			}
		}
		return false;
	}
	
	public static Compte rechercheCompte(int intCompte) {
		String idCompte = dec11.format(intCompte);
		for (Compte compte : listeComptes) {
			if (compte.getIdCompte().equals(idCompte)) {
				return compte;
			}
		}
		return null;
	}

	public static ArrayList<Client> rechercheClientNom(String nom) {
		ArrayList<Client> res = new ArrayList<>();
		for (Client client : listeClients) {
			if (client.getNom().toLowerCase().equals(nom.toLowerCase()) == true) {
				res.add(client);
			}
		}
		return res;
	}
	
	public static Client rechercheClientIdCompte(int intIdCompte) {
		String idCompte = dec11.format(intIdCompte);
		for (Compte compte : listeComptes) {
			if (compte.getIdCompte().equals(idCompte) == true) {
				return compte.getClient();
			}
		}
		return null;
	}
	
	public static Client rechercheClientIdClient(String idClient) {
		for (Client client : listeClients) {
			if (client.getId_client().toLowerCase().equals(idClient.toLowerCase()) == true) {
				return client;
			}
		}
		return null;
	}
	
	public static ArrayList<Compte> listeComptesIdClient(String idClient) {
		ArrayList<Compte> res = new ArrayList<>();
		for (Compte compte : listeComptes) {
			if (compte.getClient().getId_client().equals(idClient) == true) {
				res.add(compte);
			}
		}
		return res;
	}
	
	
	
}
