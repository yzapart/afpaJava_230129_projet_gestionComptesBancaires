package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Menu {
	
	static DecimalFormat dec3 = new DecimalFormat("000");
	static DecimalFormat dec6 = new DecimalFormat("000000");
	static DecimalFormat dec11 = new DecimalFormat("00000000000");
	
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
//		case 7: 
//			menu7();
//			break;
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
		
		Banque.listeClients.add(new Client(idClient, nom, prenom, dateNaissance, email));
		
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
			retour();
		} else {
			System.out.println(client.getPrenom() + " " + client.getNom() + " possède déjà un compte du type " + type);
		}
		System.out.println("Découvert autorisé (O/n) : ");
		String charDec = scan.nextLine();
		Boolean decouvertAutorise = (charDec.equals("O") == true) ? true : false;
		
		Banque.listeComptes.add(new Compte(idCompte, codeAgence, type, client, 0, decouvertAutorise));
		
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

}