package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;


public class Demo_comptes_bancaires {
	static DecimalFormat dec3 = new DecimalFormat("000");
	static DecimalFormat dec6 = new DecimalFormat("000000");
	static DecimalFormat dec11 = new DecimalFormat("00000000000");
	static Random random = new Random();

	static GenericClass<Agence> genericAgence = new GenericClass<>();
	static GenericClass<Client> genericClient = new GenericClass<>();
	static GenericClass<Compte> genericCompte = new GenericClass<>();

	public static void main(String[] args) {

		generationAgences();
		genericAgence.afficher(Banque.listeAgences);
		generationClients();
		genericClient.afficher(Banque.listeClients);
		generationComptes();
		genericCompte.afficher(Banque.listeComptes);
		System.out.println("---");
		

//		if (Banque.rechercheCompte(5) != null)	System.out.println(Banque.rechercheCompte(5).toStr());
//		genericClient.afficher(Banque.rechercheClientNom("Dion"));
//		if (Banque.rechercheClientIdCompte(11) != null) System.out.println(Banque.rechercheClientIdCompte(11).toStr());
//		System.out.println(Banque.rechercheClientIdClient("VA000004").toStr());
//		genericCompte.afficher(Banque.listeComptesIdClient("MD000009"));
		
		Menu.afficherMenu("");
		
		
	}

	public static void generationAgences() {
		String listeVilles[] = { "Lille", "Paris", "Leforest", "Loos", "Toulouse", "Dunkerque" };
		String listeRues[] = {"Pasteur", "Carnot", "Marceau", "Dali", "Monet", "Legrand", "Blériot" };
		for (int i = 0; i < listeVilles.length; i++) {
			String nom = "CDA " + listeVilles[i];
			String codeAgence = dec3.format(random.nextInt(1, 199));
			String adresse = random.nextInt(1, 29) + " rue " + listeRues[random.nextInt(0,listeRues.length-1)];
			genericAgence.save(new Agence(codeAgence, nom, adresse), Banque.listeAgences);
		}
	}
	
	
	public static void generationClients() {
		String[][] listeNoms = {
				{"Francis", "Cabrel"},
				{"JJ","Goldman"},
				{"Celine","Dion"},
				{"René", "Dion"},
				{"Alex","Vizorek"},
				{"France","Gall"},
				{"Aymeric","Lomp."},
				{"Daniel","Balav."},
				{"Michel","Berger"},
				{"Doully", "Millet"}
		};
		for (int i = 0; i < listeNoms.length; i++) {
			String nom = listeNoms[i][1]; 
			String prenom = listeNoms[i][0];
			String email = prenom.toLowerCase() + "." + nom.toLowerCase() + "@gmail.com";
			LocalDate dateNaissance = LocalDate.of(1950, 01, 01).plusDays(random.nextInt(20000));
//			String idClient = nom.charAt(0) + "" + prenom.charAt(0) + dec6.format(random.nextInt(1, 1999));
			String idClient = nom.charAt(0) + "" + prenom.charAt(0) + dec6.format(i);
			genericClient.save(new Client(idClient, nom, prenom, dateNaissance, email), Banque.listeClients);
		}
	}
	
	public static void generationComptes() {
		String listeTypesCompte[] = {"Courant","Liv. A","PEL"};
		int j = 0;
		for (Client client: Banque.listeClients) {
			int nbComptes = random.nextInt(1, 4);
			for (int i = 0; i < nbComptes; i++) {
//				String idCompte = dec11.format(random.nextInt(1000, 9999999));
				String idCompte = dec11.format(j++);
				String codeAgence = Banque.listeAgences.get(random.nextInt(Banque.listeAgences.size())).getCodeAgence();
				String type = listeTypesCompte[random.nextInt(listeTypesCompte.length)];
				Double solde = Utils.round2dec(random.nextDouble(-5000.00, 20000));
				Boolean decouvertAutorise = (solde < 0) ? true : random.nextBoolean();
				
				if (Banque.checkCompteExistant(client, type) == false) {
					genericCompte.save(new Compte(idCompte, codeAgence, type, client, solde, decouvertAutorise), Banque.listeComptes);					
				} else {
					System.out.println(client.getPrenom() + " " + client.getNom() + " possède déjà un compte du type " + type);
				}
			}
		}
	}
	
	
	
	
	
}
