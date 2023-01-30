package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Demo_comptes_bancaires {
	// utilisation de DecimalFormat afin de respecter les formats des idClients, idComptes, codeAgence.
	static DecimalFormat dec3 = new DecimalFormat("000");
	static DecimalFormat dec6 = new DecimalFormat("000000");
	static DecimalFormat dec11 = new DecimalFormat("00000000000");
	static Random random = new Random();

	
	// classes génériques afin de ne pas répéter les codes d'affichage des listes.
	// Utilisation peu justifiée cependant dans ce cas
	static GenericClass<Agence> genericAgence = new GenericClass<>();
	static GenericClass<Client> genericClient = new GenericClass<>();
	static GenericClass<Compte> genericCompte = new GenericClass<>();

	
	static String listeMenu[] = { "1: Créer une agence",
			  "2: Créer un client",
			  "3: Créer un compte bancaire",
			  " ", 
			  "4: Recherche de compte",
			  "5: Recherche de client", 
			  " ", 
			  "6: Afficher la liste des comptes d'un client",
			  "7: Imprimer les infos client", 
			  " ",
			  "8: Quitter",
			  " ",
			  "10:listeAgences",
			  "11:listeClients",
			  "12:listeComptes",
			  
			};

	static Scanner scan = new Scanner(System.in);
	
	
	public static void main(String[] args) {

		// générations aléatoires d'agences, clients et comptes.
//		generationAgences();
//		genericAgence.afficher(Banque.listeAgences);
//		generationClients();
//		genericClient.afficher(Banque.listeClients);
//		generationComptes();
//		genericCompte.afficher(Banque.listeComptes);
//		System.out.println("---");
//		
//		listeAgencesToCsv();
//		listeClientsToCsv();
//		listeComptesToCsv();

		
		
		
		Banque.listeAgences = csvToListeAgences("C:\\Users\\59013-42-16\\eclipse-workspace\\afpaJava_230129_projet_gestionComptesBancaires\\listeAgences.csv");
		Banque.listeClients = csvToListeClients("C:\\Users\\59013-42-16\\eclipse-workspace\\afpaJava_230129_projet_gestionComptesBancaires\\listeClients.csv");
		Banque.listeComptes = csvToListeComptes("C:\\Users\\59013-42-16\\eclipse-workspace\\afpaJava_230129_projet_gestionComptesBancaires\\listeComptes.csv");
		

		
		
		// menu utilisateur
		afficherMenu("");
		
	}

	
	// génération aléatoire d'agences.
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
	
	
	// génération aléatoire de clients
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
	
	
	// génération aléatoire de comptes.
	// on considère qu'un client ne peut avoir deux types de comptes identiques, donc 3 maximum (un de chaque type)
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
	
	
	
	static void afficherMenu(String message) {
		cls();
		System.out.println(message);
		System.out.println("--- Menu ---");
		for (String e : listeMenu) {
			System.out.println(e);
		}
		int choixMenu = scan.nextInt(); scan.nextLine();
		switch (choixMenu) {
		case 1:
			menu1();
			break;
		case 2:
			menu2();
			break;
		case 3:
			menu3();
			break;
		case 4:
			menu4();
			break;
		case 5:
			menu5();
			break;
		case 6: 
			menu6();
			break;			
		case 7: 
			menu7();
			break;
		case 8: 
			menuQ();
			break;
		case 10: 
			menu10();
			break;
		case 11: 
			menu11();
			break;
		case 12: 
			menu12();
			break;
		}
		
	}

	// pseudo console clear
	static void cls() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	// propostiion retour menuu principal (ENTER)
	static void retour() {
		System.out.println("(Retour : Enter)");
		scan.nextLine();
		afficherMenu("");
	}
	

	
	

	// Création agence
	static void menu1() {
		cls();
		System.out.println("--- Création agence ---");
		System.out.println("Code agence : ");
		String codeAgence = dec3.format(scan.nextInt()); scan.nextLine();
		System.out.println("Nom de l'agence : ");
		String nomAgence = scan.nextLine();
		System.out.println("Adresse : ");
		String adresseAgence = scan.nextLine();
		
		Banque.listeAgences.add(new Agence(codeAgence, nomAgence, adresseAgence));
		listeAgencesToCsv();
		System.out.println("Agence " + codeAgence + "\t" + nomAgence + "\t" + adresseAgence + "\tcréée.");
		retour();
	}

	// Création client
	static void menu2() {
		cls();
		System.out.println("--- Création client ---");
		System.out.println("Nom : ");
		String nom = scan.nextLine();
		System.out.println("Prénom : ");
		String prenom = scan.nextLine();
		String idClient = nom.charAt(0) + "" + prenom.charAt(0) + "" + dec6.format(Banque.listeClients.size());
		System.out.println("Date de naissance : format DD-MM-AAAA");
		String dateNaissanceString = scan.nextLine();
		LocalDate dateNaissance = LocalDate.parse(dateNaissanceString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		System.out.println("email : ");
		String email = scan.nextLine();
		
		Banque.listeClients.add(new Client(idClient.toUpperCase(), nom, prenom, dateNaissance, email));
		listeClientsToCsv();
		System.out.println("Client " + nom + "\t" + prenom + "\t" + idClient + "\tcréé.");
		retour();
	}
	
	// Création compte bancaire
	static void menu3() {
		cls();
		String listeTypesCompte[] = {"Courant","Liv. A","PEL"};
		System.out.println("--- Création compte ---");
		System.out.println("id client :");
		String idClient = scan.nextLine();
		Client client = Banque.rechercheClientIdClient(idClient);
		String idCompte = dec11.format(Banque.listeComptes.size());
		System.out.println("Code agence : ");
		String codeAgence = scan.nextLine();
		System.out.println("Type de compte : ");
		System.out.println("1: Courant");
		System.out.println("2: Liv. A");
		System.out.println("3: PEL");
		int intChoixType = scan.nextInt(); scan.nextLine();
		String type = listeTypesCompte[intChoixType-1];
		if (Banque.checkCompteExistant(client, type) == false) {
			System.out.println("Découvert autorisé (O/n) : ");
			String charDec = scan.nextLine();
			Boolean decouvertAutorise = (charDec.equals("O") == true) ? true : false;
			
			Banque.listeComptes.add(new Compte(idCompte, codeAgence, type, client, 0, decouvertAutorise));
			listeComptesToCsv();
			System.out.println("Compte " + idCompte + "\tclient: " + client.getNom() + " " + client.getPrenom() + "\t" + client.getId_client()+ "\tcréé.");
		} else {
			System.out.println(client.getPrenom() + " " + client.getNom() + " possède déjà un compte du type " + type);
		}
		retour();
	}
	
	
	
	
	
	// recherche de compte (idCompte)
	static void menu4() {
		cls();
		System.out.println("--- Recherche de compte :");
		System.out.println("Entrer n° du compte");
		int idCompte = scan.nextInt(); scan.nextLine();
		if (Banque.rechercheCompte(idCompte) != null) {
			System.out.println(Banque.rechercheCompte(idCompte).toStr());
		} else {
			System.out.println("Compte introuvable");
		}		
		retour();
	}
	
	// recherche de client
	static void menu5() {
		cls();
		System.out.println("--- Recherche de client :");
		System.out.println("Recherche depuis : ");
		System.out.println("1: Nom");
		System.out.println("2: id compte");
		System.out.println("3: id client");
		int intChoixType = scan.nextInt(); scan.nextLine();
		switch (intChoixType) {
		case 1:
			System.out.println("Nom du client : ");
			String nom = scan.nextLine();
			if (Banque.rechercheClientNom(nom).size() > 0) {
				genericClient.afficher(Banque.rechercheClientNom(nom));
			} else {
				System.out.println("Client introuvable");
			}
			break;
		case 2:
			System.out.println("id du compte : ");
			int idCompte = scan.nextInt(); scan.nextLine();
			if (Banque.rechercheClientIdCompte(idCompte) != null) {
				System.out.println(Banque.rechercheClientIdCompte(idCompte).toStr());
			} else {
				System.out.println("Client introuvable");
			}
			break;
		case 3:
			System.out.println("id du client: ");
			String idClient = scan.nextLine();
			if (Banque.rechercheClientIdClient(idClient) != null) {
				System.out.println(Banque.rechercheClientIdClient(idClient).toStr());
			} else {
				System.out.println("Client introuvable");
			}
			break;
		}
		
		retour();
	}
	
	
	static void menu6() {
		cls();
		System.out.println("--- Listing comptes client :");
		System.out.println("Entrer n° client : ");
		String idClient = scan.nextLine();
		genericCompte.afficher(Banque.listeComptesIdClient(idClient));
		retour();
	}
	
	
	
	static void menu7() {
		System.out.println("--- Impression fiche client :");
		System.out.println("Entrer n° client : ");
		String idClient = scan.nextLine();
		
		Client client = Banque.rechercheClientIdClient(idClient);
		
		ArrayList<String> lines = new ArrayList<String>();
        String fileName = "fiche_client_" + client.getNom() + "_" + client.getId_client() + ".txt";
        lines.add("Fiche client");
        lines.add("\n");
        lines.add("Numéro client : " + client.getId_client());
        lines.add("Nom : " + client.getNom());
        lines.add("Prénom : " + client.getPrenom());
        lines.add("Date de naissance " + client.getDateNaissance().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lines.add("\n");
        lines.add("--- Liste de comptes : ---");
        for (Compte compte : Banque.listeComptesIdClient(idClient)) {
        	lines.add(compte.toStr());
        }      
        
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
	
	retour();
	}
		

	static void menuQ() {
		cls();
		System.out.println("Au revoir.");
		System.exit(0);
	}
	
	static void menu10() {
		cls();
		genericAgence.afficher(Banque.listeAgences);
		retour();
	}
	static void menu11() {
		cls();
		genericClient.afficher(Banque.listeClients);
		retour();
	}
	static void menu12() {
		cls();
		genericCompte.afficher(Banque.listeComptes);
		retour();
	}
	
	
	
	
	
	
	static void listeAgencesToCsv() {
	 // CSV header : codeAgence,nom,adresse
	    try {
            FileWriter fileWriter = new FileWriter("listeAgences.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Agence agence: Banque.listeAgences) {
                bufferedWriter.write(agence.getCodeAgence() + "," +agence.getNom() + "," + agence.getAdresse());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
	}
	
	static ArrayList<Agence> csvToListeAgences(String chemin) {
		ArrayList<Agence> res = new ArrayList<Agence>();
	    String line;

		try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
			while ((line = br.readLine()) != null) {
				res.add(new Agence(line.split(",")[0], line.split(",")[1], line.split(",")[2]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	static void listeClientsToCsv() {
		// CSV header : idClient,nom,prenom,dateNaissance(string "dd-MM-yyyy"),email
		try {
			FileWriter fileWriter = new FileWriter("listeClients.csv");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for (Client client : Banque.listeClients) {
				bufferedWriter.write(
						client.getId_client() + "," +
						client.getNom() + "," +
						client.getPrenom() + "," +
						client.getDateNaissance().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
						client.getEmail()
						);
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}
	
	static ArrayList<Client> csvToListeClients(String chemin) {
		ArrayList<Client> res = new ArrayList<Client>();
	    String line;

		try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
			while ((line = br.readLine()) != null) {
				res.add(new Client(line.split(",")[0], line.split(",")[1], line.split(",")[2], LocalDate.parse(line.split(",")[3], DateTimeFormatter.ofPattern("dd-MM-yyyy")), line.split(",")[4]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	static void listeComptesToCsv() {
		// CSV header : idCompte, codeAgence, type, objet client, solde, dec.aut.
		try {
			FileWriter fileWriter = new FileWriter("listeComptes.csv");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for (Compte compte : Banque.listeComptes) {
				bufferedWriter.write(
						compte.getIdCompte() + "," +
						compte.getCodeAgence() + "," +
						compte.getTypeCompte() + "," +
//						compte.getClient().getNom() + "," +
//						compte.getClient().getPrenom() + "," +
						compte.getClient().getId_client() + "," +
						compte.getSolde() + "," +
						compte.getDecouvertAutorise()					
						);
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}
	
	
	static ArrayList<Compte> csvToListeComptes(String chemin) {
		ArrayList<Compte> res = new ArrayList<Compte>();
	    String line;

		try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
			while ((line = br.readLine()) != null) {
				res.add(new Compte( line.split(",")[0], 
						 	 	    line.split(",")[1], 
						 	 	    line.split(",")[2], 
						 	 	    Banque.rechercheClientIdClient(line.split(",")[3]), 
						 	 	    Double.valueOf(line.split(",")[4]),
									Boolean.parseBoolean(line.split(",")[5])
								  )
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
