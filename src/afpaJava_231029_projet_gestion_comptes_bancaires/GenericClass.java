package afpaJava_231029_projet_gestion_comptes_bancaires;

import java.util.ArrayList;

public class GenericClass<T> {

	public void save(T o, ArrayList<T> list) {
		list.add(o);
	}

	public void afficher(ArrayList<T> list) {
		for (T e : list) {
			if (e instanceof Agence) {
				System.out.println(((Agence) e).toStr());
			}
			if (e instanceof Client) {
				System.out.println(((Client) e).toStr());
			}
			if (e instanceof Compte) {
				System.out.println(((Compte) e).toStr());
			}
		}
	}

}
